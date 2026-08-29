# استفاده JavaScript از AS Academy Core

این پروژه اکنون `AS-Academy-Core` را به‌صورت Git submodule در مسیر `academy-core/` مصرف می‌کند. سورس مشترک در این ریپو کپی نشده است.

## Core commit فعلی
`1ed5ab777f7b55245e57c163d55903470d965763`

## مواردی که مستقیماً از Core استفاده می‌شوند
- `AcademyCourseApp` به‌عنوان App Shell
- Navigation و Drawer
- Home / Chapter / Lesson screens
- LessonBlock renderer
- Room database
- Progress و Bookmark
- Search/FTS
- Settings/DataStore
- Course Package Loader/Validator
- Quiz/Exercise/Project contracts
- CodeRunner plugin API
- Update/Backup contracts
- Version Catalog و نسخه‌های Android/Kotlin/Compose

## موارد اختصاصی این ریپو
- `course/javascript/**`
- JavaScript branding/config
- JavaScript examples/exercises/quizzes/projects/glossary
- JavaScript-specific CodeRunner در مرحله فعال‌سازی Runner

قانون: هر قابلیت قابل استفاده در بیش از یک Course ابتدا در Core ساخته می‌شود؛ این ریپو فقط آن را فراخوانی می‌کند.
