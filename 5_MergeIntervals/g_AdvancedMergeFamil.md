# Merge Intervals Pattern - Part 7

# Advanced Interval Family

---

# Why This Family Matters

These are the interval questions that separate:

```text id="a701"
SDE-1
```

from:

```text id="a702"
SDE-2
```

because they combine:

```text id="a703"
Intervals

+
Heap

+
TreeMap

+
Sweep Line

+
Ordered Data Structures
```

---

# Pattern Roadmap

Problems:

```text id="a704"
1. Merge K Sorted Interval Lists

2. Range Module

3. My Calendar I

4. My Calendar II

5. Skyline Problem
```

---

# Problem 15: Merge K Sorted Interval Lists

## Google / Amazon Variant

---

# Problem Explanation

Given:

```text id="a705"
List 1

[1,3]
[5,7]

List 2

[2,4]
[8,10]

List 3

[6,9]
```

Need:

```text id="a706"
Single Merged List
```

Output:

```text id="a707"
[1,4]

[5,10]
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text id="a708"
Merge K Sorted Lists
```

hidden inside interval problems?

---

# Pattern Recognition Clues

### Clue 1

Multiple sorted interval lists.

### Clue 2

Need global ordering.

### Clue 3

K sources.

Think:

```text id="a709"
Min Heap
```

---

# Brute Force

Flatten everything.

Sort.

Merge.

---

# Complexity

```text id="a710"
O(N log N)
```

---

# Better Insight

Each list is already sorted.

Don't sort again.

Use:

```text id="a711"
K-Way Merge
```

---

# Heap Contents

Store:

```java id="a712"
start

listIndex

intervalIndex
```

---

# Dry Run

Heap:

```text id="a713"
[1,3]

[2,4]

[6,9]
```

Take:

```text id="a714"
[1,3]
```

Push next from same list.

Continue.

---

# What To Say In Interview

Since each interval list is already sorted, I'll perform a K-way merge using a min heap and then apply the standard merge-interval logic.

---

# Optimal Java Code

```java id="a715"
class Node {

    int list;
    int index;
    int[] interval;

    Node(
        int list,
        int index,
        int[] interval
    ){

        this.list = list;
        this.index = index;
        this.interval = interval;
    }
}
```

Core Idea:

```java id="a716"
PriorityQueue<Node>
```

followed by:

```java id="a717"
Standard Merge Template
```

---

# Complexity

```text id="a718"
Time :

O(N log K)

Space :

O(K)
```

---

# Similar Problems

* Merge K Sorted Lists
* Employee Free Time
* External Sorting

---

# Problem 16: Range Module

## LeetCode 715

---

# Problem Explanation

Design a data structure supporting:

```java id="a719"
addRange(left,right)

removeRange(left,right)

queryRange(left,right)
```

Example:

```text id="a720"
addRange(10,20)

queryRange(12,15)

true
```

---

# What Is The Interviewer Testing?

Can you dynamically maintain:

```text id="a721"
Merged Intervals
```

after updates?

---

# Pattern Recognition Clues

### Clue 1

Add Interval.

### Clue 2

Remove Interval.

### Clue 3

Query Interval.

Think:

```text id="a722"
Ordered Interval Structure
```

---

# Why ArrayList Fails

Every operation becomes:

```text id="a723"
O(n)
```

or worse.

Need:

```text id="a724"
TreeMap
```

---

# Key Insight

Store:

```java id="a725"
start
→
end
```

inside:

```java id="a726"
TreeMap
```

---

# Example

Current:

```text id="a727"
[10,20]
```

Add:

```text id="a728"
[15,30]
```

Merge:

```text id="a729"
[10,30]
```

using neighboring entries.

---

# What To Say In Interview

I'll maintain disjoint intervals inside a TreeMap. Each operation only interacts with nearby intervals, giving logarithmic access.

---

# Data Structure

```java id="a730"
TreeMap<Integer,Integer>
```

---

# Complexity

```text id="a731"
Add :

O(log n)

Query :

O(log n)

Remove :

O(log n)
```

---

# Similar Problems

* My Calendar
* Booking Systems
* Memory Allocators

---

# Problem 17: My Calendar I

## LeetCode 729

---

# Problem Explanation

Design:

```java id="a732"
book(start,end)
```

Return:

```text id="a733"
true
```

if booking possible.

Else:

```text id="a734"
false
```

---

# Example

Book:

```text id="a735"
10-20
```

Success.

---

Book:

```text id="a736"
15-25
```

Fail.

---

# What Is The Interviewer Testing?

Can you efficiently detect overlap?

---

# Pattern Recognition Clues

### Clue 1

Calendar.

### Clue 2

Booking.

### Clue 3

No overlaps allowed.

Think:

```text id="a737"
TreeMap Neighbor Search
```

---

# Key Observation

New booking only needs checking against:

```text id="a738"
Previous Interval

and

Next Interval
```

---

# Why?

Because intervals remain ordered.

---

# Visualization

Existing:

```text id="a739"
10-----20

30-----40
```

Insert:

```text id="a740"
22-----28
```

Only compare:

```text id="a741"
10-20

30-40
```

---

# What To Say In Interview

Using TreeMap, I can locate neighboring intervals in logarithmic time and check whether the new booking overlaps either of them.

---

# Optimal Java Code

```java id="a742"
class MyCalendar {

    TreeMap<Integer,Integer>
            map =
            new TreeMap<>();

    public boolean book(
            int start,
            int end
    ){

        Integer prev =
                map.floorKey(start);

        Integer next =
                map.ceilingKey(start);

        if(prev != null
            &&
            map.get(prev)
            > start){

            return false;
        }

        if(next != null
            &&
            next < end){

            return false;
        }

        map.put(start,end);

        return true;
    }
}
```

---

# Complexity

```text id="a743"
Time :

O(log n)

Space :

O(n)
```

---

# Similar Problems

* Meeting Rooms
* Range Module
* Scheduling Systems

---

# Problem 18: My Calendar II

## LeetCode 731

---

# Problem Explanation

Now:

```text id="a744"
Double Booking
```

is allowed.

But:

```text id="a745"
Triple Booking
```

is NOT allowed.

---

# Example

Bookings:

```text id="a746"
10-20

15-25
```

Allowed.

---

Booking:

```text id="a747"
17-22
```

Not Allowed.

Because:

```text id="a748"
Triple Overlap
```

occurs.

---

# What Is The Interviewer Testing?

Can you track:

```text id="a749"
Overlap Of Overlaps
```

?

---

# Key Insight

Maintain:

```text id="a750"
Booked Intervals
```

and:

```text id="a751"
Double Booked Intervals
```

---

# Booking Rule

Before adding:

Check whether new interval intersects:

```text id="a752"
Any Double Booked Interval
```

If yes:

```text id="a753"
Triple Booking
```

Reject.

---

# What To Say In Interview

I'll maintain all bookings and all double-booked regions. Any overlap with a double-booked region immediately creates a triple booking and must be rejected.

---

# Complexity

```text id="a754"
Time :

O(n)

per booking
```

---

# Similar Problems

* Resource Scheduling
* Calendar Systems
* Concurrent Reservation Systems

---

# Problem 19: Skyline Problem

## LeetCode 218

---

# Problem Explanation

Buildings:

```text id="a755"
[2,9,10]

[3,7,15]

[5,12,12]
```

Need skyline:

```text id="a756"
Critical Points
```

---

# Output

```text id="a757"
[2,10]

[3,15]

[7,12]

[12,0]
```

---

# What Is The Interviewer Testing?

Can you process:

```text id="a758"
Events
```

instead of intervals?

---

# Pattern Recognition Clues

### Clue 1

Buildings.

### Clue 2

Height Changes.

### Clue 3

Critical Points.

Think:

```text id="a759"
Sweep Line
```

---

# Key Insight

Convert building:

```text id="a760"
[start,end,height]
```

into:

```text id="a761"
Start Event

End Event
```

---

# Example

Building:

```text id="a762"
[2,9,10]
```

Events:

```text id="a763"
(2,+10)

(9,-10)
```

---

# Data Structure

Need:

```text id="a764"
Current Maximum Height
```

Use:

```java id="a765"
TreeMap
```

or

```java id="a766"
Max Heap
```

---

# Sweep Line Visualization

Events:

```text id="a767"
2 +10

3 +15

7 -15

9 -10
```

Track:

```text id="a768"
Maximum Active Height
```

Whenever it changes:

```text id="a769"
Skyline Point Found
```

---

# What To Say In Interview

I'll convert buildings into start and end events, process them in sorted order, and maintain active building heights. Whenever the maximum active height changes, a skyline point is generated.

---

# Complexity

```text id="a770"
Time :

O(n log n)

Space :

O(n)
```

---

# Advanced Interval Family Revision Sheet

## Merge K Sorted Interval Lists

Use:

```java id="a771"
PriorityQueue
```

Goal:

```text id="a772"
Global Ordering
```

---

## Range Module

Use:

```java id="a773"
TreeMap
```

Goal:

```text id="a774"
Dynamic Interval Updates
```

---

## My Calendar I

Use:

```java id="a775"
floorKey()

ceilingKey()
```

Goal:

```text id="a776"
Overlap Detection
```

---

## My Calendar II

Use:

```text id="a777"
Double Bookings
```

Goal:

```text id="a778"
Prevent Triple Booking
```

---

## Skyline

Use:

```text id="a779"
Sweep Line
```

Goal:

```text id="a780"
Track Active Heights
```

---

# Complete Merge Intervals Handbook

## Part 1

Pure Merge Family

* Merge Intervals
* Insert Interval
* Summary Ranges

---

## Part 2

Intersection Family

* Interval List Intersections
* Meeting Slot Variant

---

## Part 3

Meeting Room Family

* Meeting Rooms
* Meeting Rooms II

---

## Part 4

Advanced Meeting Family

* Employee Free Time
* Minimum CPUs
* Car Pooling

---

## Part 5

Greedy Family

* Non-overlapping Intervals
* Minimum Arrows
* Remove Covered Intervals

---

## Part 6

Binary Search Family

* Find Right Interval

---

## Part 7

Advanced Interval Family

* Merge K Sorted Interval Lists
* Range Module
* My Calendar I
* My Calendar II
* Skyline Problem

---

# Final Interval Interview Cheat Sheet

If interviewer says:

```text id="a781"
Merge Overlaps
```

Think:

```java id="a782"
Sort By Start
```

---

If interviewer says:

```text id="a783"
Common Overlap
```

Think:

```java id="a784"
Two Pointers
```

---

If interviewer says:

```text id="a785"
Minimum Rooms
```

Think:

```java id="a786"
PriorityQueue
```

---

If interviewer says:

```text id="a787"
Maximum Intervals
```

Think:

```java id="a788"
Sort By End
```

---

If interviewer says:

```text id="a789"
Next Interval
```

Think:

```java id="a790"
Binary Search
```

---

If interviewer says:

```text id="a791"
Dynamic Intervals
```

Think:

```java id="a792"
TreeMap
```

Master these six transformations and you'll solve the vast majority of interval-based interview problems.
