# Sorting Pattern - Part 2

# Insertion Sort

---

# Why This Algorithm Matters

If there is **one elementary sorting algorithm** you should truly understand for interviews, it is **Insertion Sort**.

Unlike Bubble Sort and Selection Sort, Insertion Sort is actually used in real-world sorting libraries.

For example:

* Java's **TimSort** uses Insertion Sort for small partitions.
* Python's **sorted()** also uses TimSort.
* Many hybrid sorting algorithms switch to Insertion Sort when subarrays become small.

**Interview Tip:** When an interviewer asks, *"Which elementary sorting algorithm would you choose?"*, the answer is almost always **Insertion Sort**.

---

# Problem

Given an unsorted array:

```text id="is201"
5 2 4 6 1 3
```

Sort it in ascending order.

Output:

```text id="is202"
1 2 3 4 5 6
```

---

# What Is The Interviewer Testing?

The interviewer is testing whether you understand:

* Growing a sorted portion
* Element shifting
* Why it performs well on nearly sorted arrays
* Stability
* In-place sorting

---

# Pattern Recognition Clues

Whenever interviewer says:

```text id="is203"
Nearly Sorted Array
```

or

```text id="is204"
Insert One Element
Into Correct Position
```

Think:

```text id="is205"
Insertion Sort
```

---

# Core Idea

Bubble Sort:

```text id="is206"
Keep Swapping
```

Selection Sort:

```text id="is207"
Keep Selecting
```

Insertion Sort:

```text id="is208"
Keep Inserting
```

---

Imagine sorting playing cards.

You receive cards one by one.

Every new card is inserted into its proper place.

That's exactly Insertion Sort.

---

# Visualization

Input

```text id="is209"
5 2 4 6 1 3
```

Initially

```text id="is210"
5
```

is already sorted.

---

Insert

```text id="is211"
2
```

Shift

```text id="is212"
5
```

Result

```text id="is213"
2 5
```

---

Insert

```text id="is214"
4
```

Shift

```text id="is215"
5
```

Result

```text id="is216"
2 4 5
```

---

Insert

```text id="is217"
6
```

Already larger.

Result

```text id="is218"
2 4 5 6
```

---

Insert

```text id="is219"
1
```

Shift

```text id="is220"
6

5

4

2
```

Result

```text id="is221"
1 2 4 5 6
```

---

Insert

```text id="is222"
3
```

Shift

```text id="is223"
6

5

4
```

Result

```text id="is224"
1 2 3 4 5 6
```

Done.

---

# Key Observation

After every iteration,

```text id="is225"
Left Side
```

is completely sorted.

Right side remains unsorted.

Visualization:

```text id="is226"
Sorted | Unsorted
```

Initially

```text id="is227"
5 | 2 4 6 1 3
```

After one iteration

```text id="is228"
2 5 | 4 6 1 3
```

After two

```text id="is229"
2 4 5 | 6 1 3
```

Eventually

```text id="is230"
1 2 3 4 5 6 |
```

Entire array sorted.

---

# Dry Run

Input

```text id="is231"
8 5 2 9
```

Current element

```text id="is232"
5
```

Shift

```text id="is233"
8
```

Insert

```text id="is234"
5
```

Result

```text id="is235"
5 8 2 9
```

---

Current element

```text id="is236"
2
```

Shift

```text id="is237"
8

5
```

Insert

```text id="is238"
2
```

Result

```text id="is239"
2 5 8 9
```

Done.

---

# Brute Force Thinking

Imagine you already have a sorted list.

Whenever a new number comes,

find its correct position

and insert it.

Insertion Sort automates this idea.

---

# What To Say In Interview

Insertion Sort maintains a sorted portion of the array.

During each iteration, it removes one element from the unsorted portion, shifts larger elements to the right, and inserts the current element into its correct position.

---

# Optimal Java Code

```java id="is240"
class InsertionSort {

    public static void insertionSort(int[] arr){

        int n = arr.length;

        for(int i = 1; i < n; i++){

            int current = arr[i];

            int j = i - 1;

            while(j >= 0 && arr[j] > current){

                arr[j + 1] = arr[j];

                j--;
            }

            arr[j + 1] = current;
        }
    }
}
```

---

# Complexity

| Case    | Complexity |
| ------- | ---------- |
| Best    | O(n)       |
| Average | O(n²)      |
| Worst   | O(n²)      |

Space

```text id="is241"
O(1)
```

---

# Why Best Case Is O(n)?

Suppose array is already sorted.

```text id="is242"
1 2 3 4 5
```

Every comparison immediately succeeds.

No shifting.

Each element checked once.

Hence

```text id="is243"
O(n)
```

---

# Why Worst Case Is O(n²)?

Reverse sorted array

```text id="is244"
5 4 3 2 1
```

Every insertion shifts all previous elements.

Example

```text id="is245"
1st insertion

1 shift

2nd insertion

2 shifts

3rd insertion

3 shifts
```

Total

```text id="is246"
1+2+3+...
```

equals

```text id="is247"
O(n²)
```

---

# Stability

Insertion Sort is

```text id="is248"
Stable
```

Equal elements never change order.

Example

```text id="is249"
4A 4B 2
```

After sorting

```text id="is250"
2 4A 4B
```

Order preserved.

---

# In-place?

Yes.

Only one temporary variable.

Space

```text id="is251"
O(1)
```

---

# Why Insertion Sort Is Better Than Bubble Sort

Bubble Sort

```text id="is252"
Repeated Swaps
```

Insertion Sort

```text id="is253"
Repeated Shifts
```

Shifting is cheaper than multiple swaps.

Therefore Insertion Sort is generally faster.

---

# Why Java Uses Insertion Sort

Suppose Merge Sort divides into:

```text id="is254"
8 elements

↓

4

↓

2

↓

1
```

For tiny arrays,

Merge Sort overhead is expensive.

Instead,

Java switches to

```text id="is255"
Insertion Sort
```

because it is faster on small arrays.

This is one reason **TimSort** performs so well.

---

# Bubble vs Selection vs Insertion

| Feature       | Bubble | Selection | Insertion |
| ------------- | ------ | --------- | --------- |
| Stable        | ✅      | ❌         | ✅         |
| In-place      | ✅      | ✅         | ✅         |
| Best Case     | O(n)   | O(n²)     | O(n)      |
| Average       | O(n²)  | O(n²)     | O(n²)     |
| Worst         | O(n²)  | O(n²)     | O(n²)     |
| Swaps         | Many   | Few       | Very Few  |
| Nearly Sorted | Good   | Poor      | Excellent |

---

# Real Interview Questions

### Why is Insertion Sort faster than Bubble Sort?

Because it shifts elements instead of repeatedly swapping adjacent elements.

---

### Why does Insertion Sort perform well on nearly sorted arrays?

Very few elements need shifting.

The inner loop exits almost immediately.

Hence almost linear time.

---

### Which sorting algorithm is used inside TimSort?

```text id="is256"
Insertion Sort
```

for small partitions.

---

### Which sorting algorithm is preferred for Linked Lists?

Insertion Sort.

Reason:

```text id="is257"
Insertion in Linked List

O(1)
```

after locating the position.

No expensive array shifting.

---

### Can Insertion Sort sort online data?

Yes.

It is an

```text id="is258"
Online Algorithm
```

because it can process elements one at a time without requiring the entire input beforehand.

Example:

```text id="is259"
Receive

5

↓

Receive

2

↓

Receive

8

↓

Receive

1
```

The sorted sequence can be maintained continuously.

---

# Interview Discussion

**Interviewer:** When would you choose Insertion Sort over Merge Sort?

**Answer:**

I would choose Insertion Sort when:

* The dataset is very small.
* The array is already or nearly sorted.
* Memory usage must remain O(1).
* Data arrives incrementally (online processing).

For large random datasets, Merge Sort or Quick Sort is generally preferred because they provide O(n log n) performance.

---

# Insertion Sort Revision Sheet

Pattern

```text id="is260"
Grow Sorted Portion
```

---

Key Idea

```text id="is261"
Shift

Don't Swap
```

---

Best Case

```text id="is262"
O(n)
```

---

Worst Case

```text id="is263"
O(n²)
```

---

Stable

```text id="is264"
Yes
```

---

In-place

```text id="is265"
Yes
```

---

Used In

```text id="is266"
TimSort

Hybrid Sorting Algorithms
```

---

# Golden Rule

Remember the three elementary sorting algorithms like this:

```text id="is267"
Bubble Sort

↓

Largest Element Bubbles
```

```text id="is268"
Selection Sort

↓

Find Minimum
```

```text id="is269"
Insertion Sort

↓

Insert Current Element
Into Sorted Portion
```

Understanding these three mental models is more valuable than memorizing their implementations.
