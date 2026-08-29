# AS Academy JavaScript

اپلیکیشن آموزشی جامع JavaScript از مجموعه AS Academy.

## معماری
این پروژه **هسته عمومی برنامه را دوباره پیاده‌سازی نمی‌کند**. اسکلت، معماری و قابلیت‌های مشترک از ریپوی مرکزی `AS-Academy-Core` تأمین می‌شوند. این ریپو فقط محتوای JavaScript، Branding و قابلیت‌های اختصاصی JavaScript را نگه می‌دارد.

## AS Academy Core Usage
این پروژه از Core برای موارد زیر استفاده می‌کند:
- App Shell و Navigation
- Design System
- Drawer و Profile
- Database/Room architecture
- Content Engine و Lesson Renderer
- Progress Tracking
- Quiz و Exercise Engine
- Project Engine
- Search و Bookmark
- Glossary
- Settings
- Content/App Update architecture
- Backup/Restore architecture
- Code Runner Framework

## موارد اختصاصی این ریپو
- JavaScript Course Package
- درس‌ها و فصل‌های JavaScript
- تمرین‌ها و آزمون‌ها
- پروژه‌های JavaScript
- واژه‌نامه JavaScript
- Branding و Assetهای JavaScript
- JavaScript Runner adapter/configuration

## قانون توسعه
هر قابلیت عمومی که برای دوره‌های دیگر نیز قابل استفاده باشد باید در `AS-Academy-Core` اصلاح یا اضافه شود، نه با Copy/Paste در این ریپو.

## مسیر محتوا
```text
course/javascript/
├── manifest.json
├── course.json
├── levels.json
├── chapters.json
├── lessons/
├── exercises/
├── quizzes/
├── projects/
├── glossary/
├── assets/
└── branding/
```

وضعیت: آغاز پیاده‌سازی Course Package روی معماری مرکزی AS Academy Core.
