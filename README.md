# Campus Course & Records Manager (CCRM)

A console-based Java SE application to manage students, courses, enrollments, grades, transcripts, imports/exports, and backups.

**Student:** Abhijit Mishra  
**Registration number:** 24BEC10033  
**Main class:** `edu.ccrm.cli.CCRMApp`

---

## How to Run

### Prerequisites
- JDK 17+ (verified with Temurin JDK 21)
- Any editor/IDE (Eclipse, IntelliJ, VS Code) or just the command line.

### Compile & Run (CLI)

Windows PowerShell:

```powershell
$sources = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName
javac -d out $sources
java -ea -cp out edu.ccrm.cli.CCRMApp
```

macOS/Linux:

```bash
# from the project root
javac -d out $(find src -name "*.java")
java -ea -cp out edu.ccrm.cli.CCRMApp
```

- The flag `-ea` enables **assertions** (used for invariants).

### Sample Data
Importable CSVs live in `test-data/`:
- `students.csv`
- `courses.csv`

---

## Minimum Demo Flow
1. On start, `AppConfig` (Singleton) loads a default data folder under `./data` (created on demand).
2. CLI menu allows managing Students/Courses/Enrollment/Grades, Import/Export, Backup, Reports, and Exit.
3. Enroll a student, record marks, print transcript (polymorphic `toString()` used).
4. Export data and run Backup (creates a timestamped folder).
5. Program prints a short platform note summarizing Java SE vs ME vs EE.

---

## Evolution of Java (very short bullets)
- 1995: Java 1.0 – write once, run anywhere, JVM.
- 2004–2011: Generics, Enums, Annotations (Java 5/6), improved concurrency.
- 2014–2017: Lambdas/Streams (Java 8), Date/Time API.
- 2018–2021: Local-variable type inference (`var`), modules, switch improvements.
- 2022–2025: Records, pattern matching (instanceof), sealed classes (preview), virtual threads (Loom).

---

## Java ME vs Java SE vs Java EE (Jakarta EE)

| Feature | Java ME | Java SE | Java EE (Jakarta EE) |
|---|---|---|---|
| Target | Embedded/mobile & constrained devices | Desktop/server general-purpose | Enterprise apps (web, transactions, messaging) |
| APIs | Subset, device profiles | Core language + standard libraries | Adds enterprise APIs (Servlets/JAX-RS/JPA/JMS) |
| Packaging | MIDlets | JARs | WAR/EAR |
| Footprint | Small | Medium | Larger |
| Typical Use | Feature phones/IoT | CLI apps, libraries | Enterprise backends |

**Platform note (SE vs ME vs EE):** CCRM is a **Java SE** console app. It does not rely on app servers or enterprise containers.

---

## JDK, JRE, JVM: What/How
- **JVM**: The virtual machine that executes bytecode.
- **JRE**: JVM + standard libraries to *run* Java apps (deprecated as a separate download in modern JDKs).
- **JDK**: JRE + compiler/tools to *develop* Java apps.
- Relationship: Source → `javac` → bytecode (class files) → executed by JVM.

---

## Install & Configure Java on Windows
1. Download and install a JDK (e.g., Adoptium Temurin 17).
2. Add `JAVA_HOME` and update `PATH` to include `bin`.
3. Verify:
   ```bash
   java -version
   javac -version
   ```
4. **Screenshot to provide:** place your verification in `screenshots/` (see placeholders).

### Using Eclipse (quick)
1. File → New → Java Project → Name: `ccrm` → Finish.
2. Create source folders matching `src/` and packages.
3. Add a Run Configuration with Main class `edu.ccrm.cli.CCRMApp`.
4. **Screenshots to add:** Project setup, first run.

---

## OOP Design Decisions

Class inheritance models a genuine **is-a** relationship: `Student` and `Instructor` are specialized `Person` types and share encapsulated identity fields. Interfaces model capabilities that can be implemented by otherwise unrelated classes. `Searchable<T>` supplies reusable search behavior, while `ProfileLabel` and `RecordLabel` intentionally define the same default method; `Student.label()` explicitly resolves that default-method diamond.

`CourseCode` is immutable. Its only field is `final`, the wrapped `String` is itself immutable, and no setter exposes mutation; therefore no additional defensive copy is needed.

## Errors vs Exceptions

An **Error** represents a serious JVM or environment failure, such as `OutOfMemoryError`, that application code generally should not try to recover from. An **Exception** represents a condition application code may handle. This project uses checked exceptions for recoverable enrollment and I/O failures, unchecked exceptions for invalid arguments or invalid state, multi-catch in the enrollment CLI, and `finally` in the backup workflow.

## Mapping: Syllabus Topic to Code

| Topic | File/class/method |
|---|---|
| Encapsulation | `domain.Person`, `domain.Course` private fields and accessors |
| Inheritance and abstraction | Abstract `Person`; concrete `Student` and `Instructor`; constructor calls to `super` |
| Polymorphism | `Person` references in `CCRMApp.listStudents`; overridden `summary()` and `toString()` |
| Interfaces and default-method diamond | `Searchable<T>`, `ProfileLabel`, `RecordLabel`, and `Student.label()` |
| Enums with constructors/fields | `Semester` display/order fields; `Grade` point values |
| Immutability | `CourseCode` final class and final value |
| Static nested and inner classes | `Course.Builder`; `Transcript.InnerLine` |
| Anonymous inner class | Backup confirmation callback in `CCRMApp.backupMenu()` |
| Lambdas, functional interfaces, Streams | Service filters, comparators/predicates, and GPA distribution report |
| Singleton and Builder | `AppConfig`, `DataStore`, and `Course.Builder` |
| Date/Time API | `Enrollment.enrolledAt`, `Person.createdAt`, timestamped backup folders |
| NIO.2 and recursion | `ImportExportService`, `BackupService`, and `FileUtils.recursiveSize()` |
| Checked/unchecked/custom exceptions | Enrollment exceptions, I/O exceptions, and `IllegalArgumentException` |
| Assertions | `Course` and `CourseCode` constructors; enable with `java -ea` |
| Arrays utilities | `ArrayDemos.run()` sorting and binary search |
| Operators and precedence | `ArrayDemos` and `CCRMApp.main()` examples |
| Loops and labeled jumps | CLI `while`/`do-while`, enhanced `for`, and labeled `break`/`continue` |
| Overloading and overriding | `StudentService.add(...)`; domain `summary()`/`toString()` overrides |
| Upcast/downcast/`instanceof` | `CCRMApp.listStudents()` |

---

## USAGE (Sample Flow)
1. Start the app, choose **Import Data** to load `test-data/students.csv` and `test-data/courses.csv`.
2. **Manage Students** to list them; **Manage Courses** to list courses and try search/filter.
3. **Enrollment/Grades** → enroll, set marks, print transcript.
4. **Export Data** → write out to `data/export/`.
5. **Backup** → creates `data/backup/YYYYMMDD-HHmmss/` and prints recursive size.

See [USAGE.md](USAGE.md) for the short command reference and `test-data/` for import-ready CSV files.

---

## Project Report

The submission-ready PDF report is available at [docs/CCRM_Project_Report_Abhijit_Mishra.pdf](docs/CCRM_Project_Report_Abhijit_Mishra.pdf).

---

## Required Screenshots

Add genuine screenshots from your own machine to `screenshots/` before final academic submission:

1. `java -version` and `javac -version` verification.
2. Eclipse project setup.
3. Eclipse run configuration and running CLI.
4. Program menus and sample operations.
5. Generated export/backup folder structure.

The checklist in `screenshots/README.txt` tracks the screenshots that still need to be supplied. Unrelated images are intentionally excluded.

---

## Acknowledgements
- [Oracle Java documentation](https://docs.oracle.com/en/java/) for Java SE API references.
- [Eclipse IDE documentation](https://help.eclipse.org/) for project and run-configuration terminology.

> **Academic integrity:** Review, understand, and adapt every part of the project before submission. Add your own screenshots and disclose or cite any assistance required by your institution. If your evaluator prohibits LLM-assisted work, do not submit this version as your own.
