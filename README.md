# AS Academy JavaScript

اپ آموزشی JavaScript از مجموعه AS Academy، از مبانی تا سطح تخصصی و پروژه‌محور.

## معماری
این پروژه Course Repository است. هسته Android از [`AS-Academy-Core`](https://github.com/waxew/AS-Academy-Core) به‌صورت Git submodule مصرف می‌شود و کدهای مشترک در این ریپو تکرار نمی‌شوند.

## Core مورد استفاده
- App Shell / Compose UI
- Navigation / Drawer
- Room / Progress / Bookmark / Notes
- Search FTS
- Settings
- Lesson Renderer
- Course Loader + Validator
- Quiz / Exercise / Project contracts
- CodeRunner API
- Update / Backup contracts

جزئیات: `docs/CORE-USAGE.md`

## محتوای اختصاصی JavaScript
`course/javascript/` شامل Manifest، Levelها، Chapterها، Lessonها، Exerciseها، Quizها، Projectها و Glossary است.

## Build
```bash
git clone --recurse-submodules https://github.com/waxew/AS-Academy-JavaScript.git
cd AS-Academy-JavaScript
gradle :app:assembleDebug
```

GitHub Actions نیز در هر Push پروژه را همراه با Core checkout و APK Debug را Build می‌کند.
