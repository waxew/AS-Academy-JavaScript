# استفاده JavaScript از AS Academy Core

این پروژه `AS-Academy-Core` را به‌صورت Git submodule در مسیر `academy-core/` مصرف می‌کند و کد مشترک در این ریپو کپی نمی‌شود.

## Core commit فعلی
`bdcf36eeb6cbcbea0844f3a4a0e1190ceb0e1ed1`

## اتصال Gradle
در پروژه میزبان، ماژول‌های submodule با نام‌های استاندارد `:course` و `:core` map شده‌اند. این نام‌ها بخشی از قرارداد Build Core هستند و dependency داخلی `core -> course` را بدون fork یا کپی حفظ می‌کنند.

## موارد مصرف‌شده از Core
- AcademyCourseApp / App Shell
- Navigation / Drawer
- Home / Chapter / Lesson
- LessonBlock renderer
- Room / Progress / Bookmark / Notes
- Search FTS
- Settings/DataStore
- Course Loader/Validator
- Quiz/Exercise/Project contracts
- CodeRunner API
- Update/Backup contracts
- Version Catalog

## موارد اختصاصی این ریپو
- `course/javascript/**`
- JavaScript branding/config
- JavaScript examples/exercises/quizzes/projects/glossary
- JavaScript Runner plugin

قانون: هر قابلیت عمومی ابتدا در `AS-Academy-Core` ساخته و تست می‌شود و سپس Course repo فقط آن را مصرف می‌کند.
