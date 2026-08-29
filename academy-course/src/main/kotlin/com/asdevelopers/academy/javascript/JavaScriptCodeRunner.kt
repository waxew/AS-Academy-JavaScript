package com.asdevelopers.academy.javascript

import android.webkit.WebView
import com.asdevelopers.academy.core.code.CodeRunResult
import com.asdevelopers.academy.core.code.CodeRunner
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume

/**
 * Runner اختصاصی JavaScript برای دوره AS Academy.
 *
 * کد کاربر داخل موتور JavaScript خود WebView اجرا می‌شود. دسترسی فایل و Content غیرفعال
 * است و هیچ JavaScriptInterface به برنامه متصل نمی‌شود؛ بنابراین نمونه‌های آموزشی به API
 * بومی برنامه دسترسی مستقیم ندارند.
 */
class JavaScriptCodeRunner(private val webView: WebView) : CodeRunner {
    override val languageId: String = "javascript"

    init {
        // فقط موتور JavaScript برای اجرای مثال‌ها لازم است.
        webView.settings.javaScriptEnabled = true

        // Runner آموزشی نباید فایل‌های دستگاه یا ContentProviderها را بخواند.
        webView.settings.allowFileAccess = false
        webView.settings.allowContentAccess = false
    }

    override suspend fun run(code: String): CodeRunResult = suspendCancellableCoroutine { continuation ->
        // JSONObject.quote رشته را به literal امن JavaScript تبدیل می‌کند و نیاز به JSONArray.quote ندارد.
        val encodedCode = JSONObject.quote(code)
        val script = """
            (function(){
              const logs=[];
              const oldLog=console.log;
              console.log=(...args)=>logs.push(args.map(String).join(' '));
              try {
                const value=(0,eval)($encodedCode);
                if(value!==undefined) logs.push(String(value));
                return JSON.stringify({ok:true,output:logs.join('\\n')});
              } catch(e) {
                return JSON.stringify({ok:false,error:String(e && e.stack ? e.stack : e)});
              } finally {
                console.log=oldLog;
              }
            })();
        """.trimIndent()

        // WebView فقط روی Main Thread قابل استفاده است؛ post اجرای callback را به همان thread می‌برد.
        webView.post {
            webView.evaluateJavascript(script) { rawResult ->
                // evaluateJavascript نتیجه را به شکل JSON-encoded string برمی‌گرداند.
                val decoded = runCatching { JSONObject("{\"value\":$rawResult}").getString("value") }
                    .getOrDefault(rawResult)
                val payload = runCatching { JSONObject(decoded) }.getOrNull()
                val success = payload?.optBoolean("ok", false) == true
                val output = payload?.optString(if (success) "output" else "error").orEmpty()

                // اگر coroutine لغو شده باشد، نتیجه دیررس WebView نادیده گرفته می‌شود.
                if (continuation.isActive) {
                    continuation.resume(
                        CodeRunResult(
                            success = success,
                            output = if (success) output else "",
                            error = if (success) null else output
                        )
                    )
                }
            }
        }
    }
}
