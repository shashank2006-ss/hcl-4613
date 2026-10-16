# College Library System

A console application for a small college library. A librarian can add books, magazines and DVDs, register members, issue and return items, calculate late fees, manage a wait list for items that are already out, and export a report.

Requires Java 17 or newer (developed and tested on Java 21).

## How to run

From the project root (the folder containing `src/`):

```
javac -d out $(find src -name "*.java")
java -cp out com.hel.library.Main
```

On Windows PowerShell:

```
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out com.hel.library.Main
```

## Project layout

```
assignment/
  README.md
  .gitignore
  src/com/hel/library/
    Main.java                      console menu only, no business rules
    model/
      Borrowable.java              isAvailable(), borrow(), giveback()
      LibraryItem.java             abstract base: id counter, validation, equals/hashCode, ordering
      Book.java  Magazine.java  Dvd.java
      Member.java
    exception/
      LibraryException.java        extends RuntimeException; base of all below
      ItemNotFoundException.java
      ItemNotAvailableException.java
      BorrowLimitExceededException.java
      MemberNotFoundException.java
      DuplicateIsbnException.java
    repository/
      Repository.java              Repository<T, ID>
      InMemoryRepository.java      HashMap-backed, id extracted by a Function<T, ID>
    services/
      LibraryService.java          all business rules
    util/
      CollectionUtils.java         generic max(), totalFees() with bounded wildcard
      SearchCache.java             LRU cache of the last 5 searches
```

## Menu

| Choice | Action |
|---|---|
| 1 | Add item (book, magazine or DVD) |
| 2 | Register member |
| 3 | Borrow item (if it is out, you are told and put on the wait list) |
| 4 | Return item (asks days late, prints the late fee, notifies the first waiting member) |
| 5 | Join the wait list for an item |
| 6 | List items (sort by title or newest first) with item counts per type |
| 7 | Search by title (last 5 searches are cached) |
| 8 | Export report to a text file |
| 9 | Late-fee demo, shows polymorphism over the whole catalogue |
| 10 | Remove available items published before a given year |
| 0 | Exit |

Item ids start at 1001. Member ids start at 1.

## Sample run

Menu text is omitted here. Lines after a prompt are what the user typed.

```
Choice: 1   Type: 1   Title: Clean Code   Year: 2008   Author: Robert Martin   ISBN: 111
Added: Book [1001, Clean Code, 2008, Robert Martin]

Choice: 2   Name: Alice   Email: alice@college.edu
Registered: Member [1, Alice, alice@college.edu, borrowed=0]

Choice: 2   Name: Bob   Email: bob@college.edu
Registered: Member [2, Bob, bob@college.edu, borrowed=0]

Choice: 3   Member ID: 1   Item ID: 1001
Issued: Book [1001, Clean Code, 2008, Robert Martin]

Choice: 3   Member ID: 2   Item ID: 1001
Error: Item 1001 is not available
Added to wait list for item 1001 (position 1)

Choice: 4   Item ID: 1001   Days late: 3
Notification: item 1001 is available again. First in line: Bob (member 2, bob@college.edu)
Returned item 1001. Late by 3 days, late fee: 6.0

Choice: 6   Sort by: 1
Book [1001, Clean Code, 2008, Robert Martin] - Available
Items per type:
  Book: 1
  Magazine: 0
  DVD: 0

Choice: 7   Title contains: clean
1 result(s)
  Book [1001, Clean Code, 2008, Robert Martin] - Available
Recent searches: [clean]

Choice: 8   File name: (blank = library_report.txt)
Report written to .../library_report.txt

Choice: 0
Goodbye!
```

## Test cases

Each of these was run by hand through the menu.

| # | Input | Expected result |
|---|---|---|
| 1 | Add book "Clean Code", 2008, Robert Martin, ISBN 111 | Added with id 1001 |
| 2 | Add another book with ISBN 111 | `Duplicate: A book with ISBN 111 already exists` |
| 3 | Add a book with year 1850 (or a year in the future) | `Invalid input: Year must be between 1900 and <current year>`; no id is used up |
| 4 | Add an item with a blank title | `Invalid input: Title must not be blank` |
| 5 | Register member with email `bobatx` | `Invalid input: Email must contain '@'` |
| 6 | Borrow item 9999 | `Not found: Item 9999 does not exist` (ItemNotFoundException) |
| 7 | Borrow with an unknown member id | `Not found: Member <id> does not exist` |
| 8 | Member 1 borrows 1001, 1002, 1003, then 1004 | Fourth is refused: `Borrow limit: Member 1 already holds the maximum of 3 items` |
| 9 | Member 2 borrows item 1001 already issued to member 1 | `Error: Item 1001 is not available`, then added to the wait list at position 1 |
| 10 | Member 2 joins the wait list for 1001 again | `Error: Member 2 is already waiting for item 1001` |
| 11 | Return 1001 after 3 days late (book, 2.0 per day) | Late fee 6.0, notification names Bob as first in line |
| 12 | Return an item that is not issued | `Error: Item 1004 is not currently issued` |
| 13 | Return with -1 days late | `Invalid input: Days late must not be negative: -1`; the item stays issued |
| 14 | Late fees for 2 days: book, magazine, DVD | 4.0, 2.0, 10.0 (menu 9 also prints the total) |
| 15 | Search "code", then "CODE" again | First is computed, second says `(from cache)` |
| 16 | Run 6 different searches, then repeat the first | The first was evicted after the 6th, so it is computed again; recent searches never exceeds 5 |
| 17 | Remove old items before 2000 | Removes available items older than 2000; items on loan are kept |
| 18 | Export report to `/nonexistent_dir/r.txt` | `Error: Could not export report to ... (cause: java.nio.file.NoSuchFileException ...)` |
| 19 | Type `abc` or `99` at the menu | `Please enter a whole number.` / `Unknown choice: 99`; the menu shows again |

## How the requirements are covered

**Part 1, OOP basics.** `LibraryItem` is abstract with private `id`, `title`, `year`, `available`. The id comes from a static counter starting at 1001, and the title and year are validated before the counter moves (title not blank, year from 1900 to the current year, otherwise `IllegalArgumentException`). `lateFee(int)` is abstract and implemented as 2.0, 1.0 and 5.0 per day in `Book`, `Magazine` and `Dvd`. `LibraryItem` implements `Borrowable`. Every class overrides `toString()` and calls `super.toString()` where it helps; reports are built with `StringBuilder`. `Member` validates the email (must contain `@`) and has an overloaded constructor that delegates with `this(...)`.

**Part 2, exceptions.** All exceptions extend `LibraryException`, which extends `RuntimeException`, and each supports a message and a cause. The service throws `ItemNotFoundException`, `ItemNotAvailableException` and `BorrowLimitExceededException` for the three business rules. In `Main` each menu action runs in a try/catch with specific exceptions caught first, there is no empty catch block, and a `finally` block makes sure the menu always comes back. `exportReport` uses try-with-resources and a `BufferedWriter`, and wraps an `IOException` in a `LibraryException` with the original cause.

**Part 3, collections.** `equals` and `hashCode` in `LibraryItem` use the id. ISBNs are tracked in a `Set<String>`. Items and members are kept in `HashMap`s behind the repositories. Natural order is by title ignoring case (ties broken by id); `LibraryItem.BY_YEAR_DESC_THEN_TITLE` is built with `Comparator.comparing(...).thenComparing(...)`. The wait list is a `Map<Integer, Queue<Member>>` of `ArrayDeque`s. `removeOldItems` uses `removeIf`. Getters that expose collections return unmodifiable copies. Statistics use `Map.merge` and `getOrDefault`. Menu 9 demonstrates polymorphism by calling `lateFee` on a `List<LibraryItem>` with no type checks.

**Part 4, generics.** `Repository<T, ID>` has `save`, `findById` (returns `Optional`), `findAll` and `deleteById`, plus a `removeIf` helper. `InMemoryRepository<T, ID>` is backed by a `HashMap` and takes a `Function<T, ID>` to read the id; it is used for both items and members. `CollectionUtils.max` is generic over `Comparable`, and `totalFees(List<? extends LibraryItem>, int)` uses a bounded wildcard. `SearchCache` extends `LinkedHashMap` in access order and overrides `removeEldestEntry` to keep the last 5 searches.

**Part 5, quality.** Constants replace magic numbers, methods are small, and `Main` only does input and output. The git history has one commit per feature.

## Design notes

- The first member in the wait list is notified when an item is returned, but they stay in the queue until they actually borrow it. Borrowing removes them from the queue.
- If a member is refused because the item is out, the menu adds them to the wait list straight away (as in the sample run). Menu 5 does the same on request.
- Items that are currently issued are never removed by "remove old items".
- Adding any item clears the search cache so results are never stale.

## Why an unbounded static map would be a memory leak

A static map lives as long as the program does, so everything stored in it stays strongly reachable and the garbage collector can never free it, even if nothing else uses those entries any more. If every search were stored with no limit, the map would grow with each new search and never shrink, until the program runs out of memory. `SearchCache` avoids this by evicting the least recently used entry once it holds more than 5, so its size is capped.
