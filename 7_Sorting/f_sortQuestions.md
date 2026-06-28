# Sorting Pattern - Part 6

# Real Interview Sorting Patterns

> **This is the most important part of Sorting.**

Interviewers rarely ask you to implement Bubble Sort or Merge Sort.

Instead they ask problems where the **first step is sorting**.

If you recognize that pattern, many "Hard" problems become "Easy."

---

# Why This Part Matters

Most candidates think:

```text id="sp601"
Sorting

↓

Return Sorted Array
```

Strong candidates think:

```text id="sp602"
Sorting

↓

Makes Another Algorithm Possible
```

Sorting is usually **not the final solution**.

It is a preprocessing step.

---

# The Master Rule

Whenever you see:

```text id="sp603"
Unordered Data
```

Ask yourself:

> **"If I sort this first, does the problem become easier?"**

This question alone solves dozens of interview problems.

---

# Sorting Pattern Family

```
Sorting

├── Sort + Two Pointers
├── Sort + Greedy
├── Sort + Binary Search
├── Sort + Intervals
├── Sort + Custom Comparator
├── Sort + Heap
├── Sort + Prefix Sum
└── Sort + Sweep Line
```

---

# Pattern 1

# Sort + Two Pointers

---

## Recognition Clues

Whenever interviewer says

```text id="sp604"
Pairs

Triplets

Closest Sum

Difference

Duplicate Removal
```

Think

```text id="sp605"
Sort

+

Two Pointers
```

---

## Example Problems

* Two Sum II
* 3Sum
* 3Sum Closest
* 4Sum
* Container With Most Water
* Boats to Save People

---

## Example

Find pair whose sum equals

```text id="sp606"
10
```

Array

```text id="sp607"
8 1 5 3 7
```

Without sorting

```text id="sp608"
Hard
```

After sorting

```text id="sp609"
1 3 5 7 8
```

Use

```text id="sp610"
Left Pointer

Right Pointer
```

Much easier.

---

# Interview Insight

Sorting converts

```text id="sp611"
O(n²)
```

into

```text id="sp612"
O(n log n)
```

or

```text id="sp613"
O(n)
```

after sorting.

---

# Pattern 2

# Sort + Greedy

---

## Recognition Clues

Interview says

```text id="sp614"
Maximum

Minimum

Intervals

Meetings

Scheduling
```

Think

```text id="sp615"
Sort

+

Greedy
```

---

## Famous Problems

* Meeting Rooms
* Non-overlapping Intervals
* Minimum Arrows
* Merge Intervals
* Job Scheduling
* Activity Selection

---

Example

Meetings

```text id="sp616"
9-10

9-12

10-11

11-12
```

Sort by

```text id="sp617"
Ending Time
```

Then greedily select.

---

# Pattern 3

# Sort + Binary Search

---

## Recognition Clues

Interview says

```text id="sp618"
Closest

Lower Bound

Upper Bound

Binary Search
```

Think

```text id="sp619"
Sort First
```

---

## Famous Problems

* Search Insert Position
* K Closest Elements
* Search Range
* Aggressive Cows

---

Example

Need closest number.

Sort

↓

Binary Search

---

# Pattern 4

# Sort + Intervals

---

Recognition

```text id="sp620"
Intervals

Ranges

Calendar

Bookings
```

Always think

```text id="sp621"
Sort By Start Time
```

---

Example

```text id="sp622"
5-7

1-3

2-6
```

Sort

↓

```text id="sp623"
1-3

2-6

5-7
```

Now merging becomes easy.

---

# Pattern 5

# Sort + Custom Comparator

---

This is one of the most common Java interview topics.

Suppose interviewer asks

```text id="sp624"
Sort Employees
By Salary
```

Need Comparator.

---

## Java Example

```java id="sp625"
Arrays.sort(
    employees,
    (a, b) ->
        Integer.compare(
            a.salary,
            b.salary
        )
);
```

---

Descending

```java id="sp626"
Arrays.sort(
    arr,
    Collections.reverseOrder()
);
```

---

Sort by Length

```java id="sp627"
Arrays.sort(
    words,
    (a,b)->
        a.length()-b.length()
);
```

---

Sort by Two Keys

Salary first.

Age second.

```java id="sp628"
Arrays.sort(
employees,
(a,b)->{

    if(a.salary!=b.salary){

        return Integer.compare(
            a.salary,
            b.salary
        );
    }

    return Integer.compare(
        a.age,
        b.age
    );
});
```

---

# Interview Insight

Whenever object sorting appears,

think

```text id="sp629"
Comparator
```

not Quick Sort.

---

# Pattern 6

# Stable Sorting

---

Recognition

Need

```text id="sp630"
Maintain Previous Order
```

Example

Sort Employees by

```text id="sp631"
Salary
```

Already sorted by

```text id="sp632"
Joining Date
```

Need joining order preserved.

Use

```text id="sp633"
Stable Sort
```

Examples

```text id="sp634"
Merge Sort

TimSort
```

---

# Pattern 7

# Sort + Heap

---

Recognition

Need

```text id="sp635"
Top K

Largest K

Smallest K

Streaming
```

Think

```text id="sp636"
Sort

or

Heap
```

Usually Heap wins.

---

Examples

* Top K Frequent
* K Closest Points
* K Largest Elements

---

# Pattern 8

# Sort + Prefix Sum

---

Recognition

Need

```text id="sp637"
Offline Queries

Range Queries
```

Sort

↓

Prefix Sum

↓

Answer quickly.

---

# Pattern 9

# Sweep Line

---

Recognition

Interview says

```text id="sp638"
Events

Timeline

Bookings

Overlapping
```

Sort events.

Process from left to right.

---

Examples

* Skyline Problem
* Meeting Rooms II
* Maximum Overlap
* Calendar Booking

---

# Most Asked Sorting Interview Questions

---

## Q1

When should you use

```java id="sp639"
Arrays.sort()
```

Use for

```text id="sp640"
Primitive Arrays
```

Java uses

```text id="sp641"
Dual Pivot QuickSort
```

---

## Q2

When should you use

```java id="sp642"
Collections.sort()
```

Objects.

Uses

```text id="sp643"
TimSort
```

---

## Q3

Difference?

| Arrays.sort()       | Collections.sort() |
| ------------------- | ------------------ |
| Arrays              | Lists              |
| Primitive & Objects | Lists Only         |
| QuickSort / TimSort | TimSort            |

---

## Q4

When should you NOT sort?

Need

```text id="sp644"
Streaming Data
```

Sorting entire array repeatedly is expensive.

Use

```text id="sp645"
Heap
```

instead.

---

## Q5

Sort or HashMap?

Need

```text id="sp646"
Fast Lookup
```

HashMap.

Need

```text id="sp647"
Order
```

Sorting.

---

## Q6

Sort or Binary Search?

Need

```text id="sp648"
Multiple Searches
```

Sort once.

Binary search many times.

---

# Java Comparator Cheat Sheet

Ascending

```java id="sp649"
Integer.compare(a,b)
```

---

Descending

```java id="sp650"
Integer.compare(b,a)
```

---

String Length

```java id="sp651"
a.length()-b.length()
```

---

Two Keys

```java id="sp652"
if(a!=b)

return...

else

return...
```

---

# Real FAANG Problems Using Sorting

| Problem                   | Pattern              |
| ------------------------- | -------------------- |
| 3Sum                      | Sort + Two Pointers  |
| Merge Intervals           | Sort + Intervals     |
| Meeting Rooms             | Sort + Greedy        |
| K Closest Elements        | Sort + Binary Search |
| Largest Number            | Custom Comparator    |
| Car Fleet                 | Sort + Greedy        |
| Boats to Save People      | Sort + Two Pointers  |
| Russian Doll Envelopes    | Sort + LIS           |
| Non-overlapping Intervals | Sort + Greedy        |
| Employee Free Time        | Sort + Intervals     |

---

# Master Decision Tree

Interview gives

```text id="sp653"
Pairs
```

↓

Think

```text id="sp654"
Sort

+

Two Pointers
```

---

Interview gives

```text id="sp655"
Intervals
```

↓

Think

```text id="sp656"
Sort By Start Time
```

---

Interview gives

```text id="sp657"
Meetings
```

↓

Think

```text id="sp658"
Sort

+

Greedy
```

---

Interview gives

```text id="sp659"
Objects
```

↓

Think

```text id="sp660"
Comparator
```

---

Interview gives

```text id="sp661"
Top K
```

↓

Think

```text id="sp662"
Heap
```

---

Interview gives

```text id="sp663"
Many Searches
```

↓

Think

```text id="sp664"
Sort

+

Binary Search
```

---

# Sorting Pattern Revision

## Bubble

```text id="sp665"
Largest Bubble
```

---

## Selection

```text id="sp666"
Select Minimum
```

---

## Insertion

```text id="sp667"
Insert Into Sorted Part
```

---

## Merge

```text id="sp668"
Divide

Merge
```

---

## Quick

```text id="sp669"
Pivot

Partition
```

---

## Real Interview

```text id="sp670"
Sort

↓

Enable Another Algorithm
```

---

# Complete Sorting Handbook

### Part 1

Elementary Sorts

* Bubble Sort
* Selection Sort

---

### Part 2

Insertion Sort

---

### Part 3

Merge Sort

---

### Part 4

Quick Sort

---

### Part 5

Merge vs Quick

Interview Guide

---

### Part 6

Sorting Patterns

* Sort + Two Pointers
* Sort + Greedy
* Sort + Binary Search
* Sort + Intervals
* Custom Comparator
* Stable Sorting
* Heap
* Sweep Line

---

# Golden Rule

The biggest mistake candidates make is thinking:

```text id="sp671"
Sorting
is the solution.
```

The strongest candidates think:

```text id="sp672"
Sorting
is the first step
that transforms
a difficult problem
into an easier one.
```

That mindset is what separates intermediate programmers from engineers who consistently recognize interview patterns.
