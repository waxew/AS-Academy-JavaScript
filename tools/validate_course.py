#!/usr/bin/env python3
"""Validate AS Academy JavaScript course JSON structure and references."""

from __future__ import annotations

import json
import sys
from collections import Counter
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1] / "course" / "javascript"


def load_json(path: Path) -> Any:
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        raise ValueError(f"Invalid JSON: {path}: {exc}") from exc


def load_objects(folder: str) -> list[dict[str, Any]]:
    result: list[dict[str, Any]] = []
    for path in sorted((ROOT / folder).glob("*.json")):
        data = load_json(path)
        if not isinstance(data, dict):
            raise ValueError(f"Expected object in {path}")
        data["__file"] = str(path.relative_to(ROOT))
        result.append(data)
    return result


def ensure_unique(items: list[dict[str, Any]], label: str, errors: list[str]) -> None:
    ids = [item.get("id") for item in items]
    duplicates = [value for value, count in Counter(ids).items() if value and count > 1]
    for duplicate in duplicates:
        errors.append(f"Duplicate {label} id: {duplicate}")
    for item in items:
        if not item.get("id"):
            errors.append(f"Missing {label} id in {item.get('__file', 'unknown file')}")


def main() -> int:
    errors: list[str] = []

    manifest = load_json(ROOT / "manifest.json")
    levels = load_json(ROOT / "levels.json")
    chapters = load_json(ROOT / "chapters.json")
    lessons = load_objects("lessons")
    exercises = load_objects("exercises")
    quizzes = load_objects("quizzes")
    projects = load_objects("projects")

    if manifest.get("courseId") != "javascript":
        errors.append("manifest.courseId must be 'javascript'")

    for collection, label in [
        (levels, "level"),
        (chapters, "chapter"),
        (lessons, "lesson"),
        (exercises, "exercise"),
        (quizzes, "quiz"),
        (projects, "project"),
    ]:
        ensure_unique(collection, label, errors)

    level_ids = {item["id"] for item in levels if item.get("id")}
    chapter_ids = {item["id"] for item in chapters if item.get("id")}
    lesson_ids = {item["id"] for item in lessons if item.get("id")}

    for chapter in chapters:
        if chapter.get("levelId") not in level_ids:
            errors.append(
                f"Chapter {chapter.get('id')} references missing levelId {chapter.get('levelId')}"
            )

    chapter_order_keys: list[tuple[str, int]] = []
    for lesson in lessons:
        chapter_id = lesson.get("chapterId")
        if chapter_id not in chapter_ids:
            errors.append(
                f"Lesson {lesson.get('id')} references missing chapterId {chapter_id}"
            )
        order = lesson.get("order")
        if not isinstance(order, int) or order < 1:
            errors.append(f"Lesson {lesson.get('id')} has invalid order {order}")
        else:
            chapter_order_keys.append((str(chapter_id), order))

        blocks = lesson.get("blocks")
        if not isinstance(blocks, list) or not blocks:
            errors.append(f"Lesson {lesson.get('id')} must contain at least one block")
        else:
            block_ids = [block.get("id") for block in blocks if isinstance(block, dict)]
            duplicate_blocks = [value for value, count in Counter(block_ids).items() if value and count > 1]
            for duplicate in duplicate_blocks:
                errors.append(f"Lesson {lesson.get('id')} has duplicate block id {duplicate}")

    for key, count in Counter(chapter_order_keys).items():
        if count > 1:
            errors.append(f"Duplicate lesson order in chapter {key[0]}: {key[1]}")

    for exercise in exercises:
        lesson_id = exercise.get("lessonId")
        if lesson_id and lesson_id not in lesson_ids:
            errors.append(
                f"Exercise {exercise.get('id')} references missing lessonId {lesson_id}"
            )

    question_ids: list[str] = []
    for quiz in quizzes:
        lesson_id = quiz.get("lessonId")
        if lesson_id and lesson_id not in lesson_ids:
            errors.append(f"Quiz {quiz.get('id')} references missing lessonId {lesson_id}")

        questions = quiz.get("questions")
        if not isinstance(questions, list) or not questions:
            errors.append(f"Quiz {quiz.get('id')} must contain questions")
            continue

        for question in questions:
            qid = question.get("id")
            if qid:
                question_ids.append(f"{quiz.get('id')}::{qid}")
            answers = question.get("answers") or []
            if len(answers) < 2:
                errors.append(f"Question {quiz.get('id')}::{qid} needs at least two answers")
            correct = sum(1 for answer in answers if answer.get("isCorrect") is True)
            if correct < 1:
                errors.append(f"Question {quiz.get('id')}::{qid} has no correct answer")

    glossary_ids: list[str] = []
    for path in sorted((ROOT / "glossary").glob("*.json")):
        entries = load_json(path)
        if not isinstance(entries, list):
            errors.append(f"Glossary file must contain an array: {path}")
            continue
        for entry in entries:
            entry_id = entry.get("id")
            if not entry_id:
                errors.append(f"Glossary entry missing id in {path.name}")
            else:
                glossary_ids.append(entry_id)

    for duplicate, count in Counter(glossary_ids).items():
        if count > 1:
            errors.append(f"Duplicate glossary id: {duplicate}")

    if errors:
        print("Course validation FAILED")
        for error in errors:
            print(f"- {error}")
        return 1

    print("Course validation PASSED")
    print(
        f"levels={len(levels)} chapters={len(chapters)} lessons={len(lessons)} "
        f"exercises={len(exercises)} quizzes={len(quizzes)} projects={len(projects)} "
        f"glossary={len(glossary_ids)}"
    )
    return 0


if __name__ == "__main__":
    sys.exit(main())
