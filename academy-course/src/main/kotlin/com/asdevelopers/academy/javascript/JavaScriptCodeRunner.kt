package com.asdevelopers.academy.javascript

import android.webkit.WebView
import com.asdevelopers.academy.core.code.CodeRunResult
import com.asdevelopers.academy.core.code.CodeRunner
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import kotlin.coroutines.resume

/**
 * Runner اختصاصی JavaScript. کد آموزشی داخل موتور JavaScript خود WebView اجرا می‌شود؛
 * دسترسی فایل/Content و رابط native برای sandbox آموزشی فعال نمی‌شود.
 */
class JavaScriptCodeRunner(private val webView: WebView) : CodeRunner {
    override val languageId: String = "javascript"

    init {
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = false
        webView.settings.allowContentAccess = false
    }

    override suspend fun run(code: String): CodeRunResult = suspendCancellableCoroutine { continuation ->
        val encoded = JSONArray.quote(code)
        val script = """
            (function(){
              const logs=[];
              const oldLog=console.log;
              console.log=(...args)=>logs.push(args.map(String).join(' '));
              try {
                const value=(0,eval)($encoded);
                if(value!==undefined) logs.push(String(value));
                return JSON.stringify({ok:true,output:logs.join('\\n')});
              } catch(e) {
                return JSON.stringify({ok:false,error:String(e && e.stack ? e.stack : e)});
              } finally { console.log=oldLog; }
            })();
        """.trimIndent()
        webView.post {
            webView.evaluateJavascript(script) { raw ->
                val decoded = runCatching { JSONArray("[$raw]").getString(0) }.getOrDefault(raw)
                val ok = decoded.contains("\"ok\":true")
                val value = Regex("\"(?:output|error)\":\"(.*?)\"").find(decoded)?.groupValues?.getOrNull(1)
                    ?.replace("\\n", "\n")?.replace("\\\"", "\"").orEmpty()
                if (continuation.isActive) continuation.resume(CodeRunResult(success = ok, output = if (ok) value else "", error = if (ok) null else value))
            }
        }
    }
}
