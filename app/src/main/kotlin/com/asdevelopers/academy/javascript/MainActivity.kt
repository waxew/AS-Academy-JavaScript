package com.asdevelopers.academy.javascript

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.asdevelopers.academy.core.ui.AcademyCourseApp

/**
 * Entry point دوره JavaScript. تمام معماری عمومی از Core می‌آید و فقط Runner مخصوص
 * JavaScript در این اپ تزریق می‌شود تا کدهای داخل درس قابل اجرا باشند.
 */
class MainActivity : ComponentActivity() {
    private lateinit var runnerWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runnerWebView = WebView(applicationContext)
        val runner = JavaScriptCodeRunner(runnerWebView)
        setContent { AcademyCourseApp(courseId = JavaScriptCourseConfig.COURSE_ID, codeRunner = runner) }
    }

    override fun onDestroy() {
        runnerWebView.stopLoading()
        runnerWebView.destroy()
        super.onDestroy()
    }
}
