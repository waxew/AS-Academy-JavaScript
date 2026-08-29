package com.asdevelopers.academy.javascript

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.asdevelopers.academy.core.ui.AcademyCourseApp

/**
 * Entry point اپ JavaScript عمداً بسیار کوچک است.
 * Navigation، دیتابیس، UI، Progress و سایر قابلیت‌های عمومی از AS-Academy-Core می‌آیند.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcademyCourseApp(courseId = JavaScriptCourseConfig.COURSE_ID)
        }
    }
}
