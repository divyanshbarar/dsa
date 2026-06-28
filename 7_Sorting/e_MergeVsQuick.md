# Sorting Pattern - Part 5

# Merge Sort vs Quick Sort

# Interview Master Guide

---

# Why This Part Matters

This is one of the most common theory questions in coding interviews.

Interviewers rarely ask:

```text id="cmp501"
Implement Merge Sort
```

Instead they ask:

```text id="cmp502"
Why would you choose Merge Sort over Quick Sort?

or

Why is Quick Sort faster in practice even though both are O(n log n)?
```

This document will teach you exactly how to answer.

---

# The Biggest Difference

Merge Sort says:

```text id="cmp503"
Divide

↓

Sort

↓

Merge
```

Quick Sort says:

```text id="cmp504"
Choose Pivot

↓

Partition

↓

Sort
```

The merge step is the biggest difference.

---

# Mental Model

## Merge Sort

Imagine two already sorted decks of cards.

```text id="cmp505"
1 4 7

2 5 8
```

Simply merge them.

---

## Quick Sort

Imagine choosing one card.

Everything smaller goes left.

Everything larger goes right.

Repeat.

---

# Interview Question 1

## Why Is Quick Sort Faster?

Most candidates answer:

```text id="cmp506"
Because it is O(n log n)
```

Wrong.

Merge Sort is also:

```text id="cmp507"
O(n log n)
```

---

Correct answer:

Quick Sort is usually faster because

* It sorts in-place.
* It doesn't allocate temporary arrays.
* It has better CPU cache locality.
* It performs fewer memory allocations.
* Smaller constant factors.

Therefore its practical performance is usually better.

---

# Interview Question 2

## Why Isn't Merge Sort Faster?

Merge Sort requires

```java id="cmp508"
temp[]
```

during every merge.

Memory access becomes expensive.

Although asymptotic complexity is the same,

memory operations increase runtime.

---

# Interview Question 3

## Why Is Merge Sort Always O(n log n)?

Bubble Sort depends on input.

Quick Sort depends on pivot.

Merge Sort doesn't depend on anything.

Every recursion level processes

```text id="cmp509"
n elements
```

Tree height

```text id="cmp510"
log n
```

Therefore

```text id="cmp511"
O(n log n)
```

always.

---

# Interview Question 4

## Why Can Quick Sort Become O(n²)?

Consider

```text id="cmp512"
1 2 3 4 5
```

Pivot

```text id="cmp513"
Last Element
```

Partition becomes

```text id="cmp514"
4 Elements

↓

1 Element
```

instead of

```text id="cmp515"
2

↓

2
```

Tree becomes

```text id="cmp516"
n
```

levels deep.

Hence

```text id="cmp517"
O(n²)
```

---

# Visualization

Good Pivot

```text id="cmp518"
8 Elements

↓

4

↓

2

↓

1
```

Height

```text id="cmp519"
log n
```

---

Bad Pivot

```text id="cmp520"
8

↓

7

↓

6

↓

5
```

Height

```text id="cmp521"
n
```

---

# Interview Question 5

## Which One Is Stable?

Merge Sort

```text id="cmp522"
Stable
```

Quick Sort

```text id="cmp523"
Not Stable
```

---

# Why?

Merge

```java id="cmp524"
<=
```

keeps left element first.

Quick Sort swaps elements across partitions.

Equal elements may change order.

---

# Example

Original

```text id="cmp525"
4A

4B

2
```

Merge Sort

```text id="cmp526"
2

4A

4B
```

Quick Sort

Possible

```text id="cmp527"
2

4B

4A
```

---

# Interview Question 6

## Which One Is In-place?

Merge Sort

```text id="cmp528"
No
```

Needs

```java id="cmp529"
temp[]
```

---

Quick Sort

```text id="cmp530"
Yes
```

Only recursion stack.

---

# Interview Question 7

## Which Uses Less Memory?

Merge Sort

```text id="cmp531"
O(n)
```

Quick Sort

```text id="cmp532"
O(log n)
```

Average recursion stack.

Winner

```text id="cmp533"
Quick Sort
```

---

# Interview Question 8

## Which Is Better For Linked Lists?

Correct answer

```text id="cmp534"
Merge Sort
```

---

# Why?

Linked List has no random access.

Quick Sort requires frequent partition traversal.

Merge Sort only changes pointers.

Very efficient.

---

# Interview Question 9

## Which Is Better For Arrays?

Correct answer

```text id="cmp535"
Quick Sort
```

---

# Why?

Arrays have

```text id="cmp536"
Random Access
```

Quick Sort benefits greatly from this.

---

# Interview Question 10

## Which One Is Used For External Sorting?

Correct answer

```text id="cmp537"
Merge Sort
```

---

# Why?

Huge files don't fit into memory.

Merge Sort naturally works like

```text id="cmp538"
Sort Chunks

↓

Merge Chunks
```

Perfect for disk storage.

---

# Interview Question 11

## Which One Is Easier To Parallelize?

Correct answer

```text id="cmp539"
Merge Sort
```

---

Why?

Left

and

Right

halves are completely independent.

Both can run simultaneously.

---

# Interview Question 12

## Why Doesn't Java Use Merge Sort For Primitive Arrays?

Primitive arrays require:

```text id="cmp540"
Speed
```

Quick Sort is usually faster.

Therefore Java uses

```text id="cmp541"
Dual Pivot QuickSort
```

---

# Interview Question 13

## Why Doesn't Java Use Quick Sort For Objects?

Objects require

```text id="cmp542"
Stable Sorting
```

Example

Employees

```text id="cmp543"
Salary
```

already sorted by

```text id="cmp544"
Joining Date
```

Stable sorting preserves previous order.

Therefore Java uses

```text id="cmp545"
TimSort
```

---

# TimSort

TimSort combines

```text id="cmp546"
Merge Sort

+

Insertion Sort
```

---

Why?

Insertion Sort is excellent for

```text id="cmp547"
Small

Nearly Sorted
```

arrays.

Merge Sort handles large sections.

Together

they outperform both individually.

---

# Real Java

```java id="cmp548"
Arrays.sort(int[])
```

Uses

```text id="cmp549"
Dual Pivot QuickSort
```

---

```java id="cmp550"
Arrays.sort(Object[])
```

Uses

```text id="cmp551"
TimSort
```

---

```java id="cmp552"
Collections.sort()
```

Uses

```text id="cmp553"
TimSort
```

---

# Complete Comparison

| Feature          | Merge Sort | Quick Sort |
| ---------------- | ---------- | ---------- |
| Best             | O(n log n) | O(n log n) |
| Average          | O(n log n) | O(n log n) |
| Worst            | O(n log n) | O(n²)      |
| Stable           | ✅          | ❌          |
| In-place         | ❌          | ✅          |
| Memory           | O(n)       | O(log n)   |
| Arrays           | Good       | Excellent  |
| Linked List      | Excellent  | Poor       |
| External Sorting | Excellent  | Poor       |
| Practical Speed  | Good       | Excellent  |

---

# Decision Tree

Need

```text id="cmp554"
Guaranteed

O(n log n)
```

Choose

```text id="cmp555"
Merge Sort
```

---

Need

```text id="cmp556"
Fastest Average Case
```

Choose

```text id="cmp557"
Quick Sort
```

---

Need

```text id="cmp558"
Stable Sort
```

Choose

```text id="cmp559"
Merge Sort
```

---

Need

```text id="cmp560"
In-place
```

Choose

```text id="cmp561"
Quick Sort
```

---

Need

```text id="cmp562"
Linked List
```

Choose

```text id="cmp563"
Merge Sort
```

---

Need

```text id="cmp564"
Primitive Arrays
```

Choose

```text id="cmp565"
Quick Sort
```

---

Need

```text id="cmp566"
Objects
```

Choose

```text id="cmp567"
TimSort
```

---

# FAANG Interview Cheat Sheet

## Bubble Sort

```text id="cmp568"
Teaching
```

---

## Selection Sort

```text id="cmp569"
Minimum Swaps
```

---

## Insertion Sort

```text id="cmp570"
Nearly Sorted Arrays
```

---

## Merge Sort

```text id="cmp571"
Stable

Guaranteed O(n log n)

Linked Lists
```

---

## Quick Sort

```text id="cmp572"
Fastest Practical Sort

Arrays
```

---

# What To Say In Interview

**Interviewer:** Merge Sort or Quick Sort?

Strong Answer:

> If I need guaranteed **O(n log n)** performance or stability, I would choose Merge Sort. It is also the preferred algorithm for linked lists and external sorting.

> If I'm sorting arrays and care about practical performance with low memory usage, I would choose Quick Sort because it is in-place, cache-friendly, and generally faster despite having an O(n²) worst case.

---

# One-Minute Revision

## Merge Sort

```text id="cmp573"
Divide

↓

Merge
```

Stable

```text id="cmp574"
Yes
```

Memory

```text id="cmp575"
O(n)
```

Worst

```text id="cmp576"
O(n log n)
```

---

## Quick Sort

```text id="cmp577"
Pivot

↓

Partition
```

Stable

```text id="cmp578"
No
```

Memory

```text id="cmp579"
O(log n)
```

Worst

```text id="cmp580"
O(n²)
```

---

# The Golden Interview Rule

Whenever an interviewer asks:

```text id="cmp581"
Which sorting algorithm would you choose?
```

**Never answer with just the algorithm name.**

Instead explain your choice based on:

* **Time complexity**
* **Space complexity**
* **Stability**
* **Input type (array vs linked list)**
* **Whether worst-case guarantees matter**

This demonstrates engineering judgment, which interviewers value more than memorized facts.
