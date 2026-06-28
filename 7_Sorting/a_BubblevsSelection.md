# Sorting Pattern - Part 1

# Elementary Sorting Family

* Bubble Sort
* Selection Sort

---

# Why This Family Matters

These algorithms are **rarely used in production**, but they are **extremely common in interviews** because they test your understanding of:

* Swapping
* Comparisons
* Time Complexity
* Stability
* In-place Sorting

Most interviewers don't care whether you remember the code.

They care whether you understand **why the algorithm behaves the way it does**.

---

# Pattern Recognition

Whenever interviewer says:

```text
Sort a very small array
```

or

```text
Implement sorting without library functions
```

Think about the elementary sorting algorithms.

---

# Elementary Sorting Family

| Algorithm      | Main Idea                                        |
| -------------- | ------------------------------------------------ |
| Bubble Sort    | Largest element moves to the end every pass      |
| Selection Sort | Select minimum element and place it correctly    |
| Insertion Sort | Build sorted array gradually (covered in Part 2) |

---

# Problem 1 : Bubble Sort

---

## Problem Explanation

Given an unsorted array:

```text
5 1 4 2 8
```

Sort it in ascending order.

Output:

```text
1 2 4 5 8
```

---

# What Is The Interviewer Testing?

Not whether you know Bubble Sort.

They are testing whether you understand

* Adjacent swapping
* Multiple passes
* Why the largest element reaches the end
* Best-case optimization

---

# Pattern Recognition Clues

### Clue 1

Only compare:

```text
Adjacent Elements
```

---

### Clue 2

Largest element slowly moves right.

---

### Clue 3

Multiple passes over array.

Think:

```text
Bubble Sort
```

---

# Core Idea

Every pass guarantees:

```text
One Largest Element
Reaches Its Correct Position
```

Imagine bubbles in water.

The largest value "floats" to the top (right side).

---

# Visualization

Initial

```text
5 1 4 2 8
```

---

Pass 1

```text
5 1

Swap

↓

1 5 4 2 8
```

---

Continue

```text
1 4 5 2 8

↓

1 4 2 5 8
```

Largest:

```text
8
```

already at end.

---

Pass 2

```text
1 4 2 5 8

↓

1 2 4 5 8
```

Now:

```text
5
```

is fixed.

---

After every pass:

```text
Last Part
Is Already Sorted
```

---

# Dry Run

Input

```text
5 1 4 2 8
```

Pass 1

```text
1 4 2 5 8
```

Pass 2

```text
1 2 4 5 8
```

Pass 3

```text
1 2 4 5 8
```

No swaps.

Stop.

---

# Brute Force Thinking

The naive idea is:

```text
Keep swapping
until array becomes sorted.
```

Bubble Sort formalizes this process.

---

# Interview Insight

Bubble Sort has a hidden optimization.

If one complete pass performs:

```text
Zero Swaps
```

then:

```text
Array Already Sorted
```

No more work is needed.

Without this optimization, Bubble Sort always performs unnecessary passes.

---

# What To Say In Interview

Bubble Sort repeatedly compares adjacent elements and swaps them if they are out of order.

After every pass, the largest unsorted element reaches its correct position.

If a pass performs no swaps, the array is already sorted and the algorithm can terminate early.

---

# Optimal Java Code

```java
class BubbleSort {

    public static void bubbleSort(int[] arr){

        int n = arr.length;

        for(int i = 0; i < n - 1; i++){

            boolean swapped = false;

            for(int j = 0; j < n - i - 1; j++){

                if(arr[j] > arr[j + 1]){

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swapped = true;
                }
            }

            if(!swapped){
                break;
            }
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

```text
O(1)
```

---

# Why Best Case Is O(n)?

Suppose

```text
1 2 3 4 5
```

First pass performs:

```text
No Swaps
```

Algorithm exits immediately.

Only one traversal.

Hence:

```text
O(n)
```

---

# Stability

Bubble Sort is

```text
Stable
```

Equal elements never cross each other.

Example

```text
2A 2B 1
```

becomes

```text
1 2A 2B
```

Relative order is preserved.

---

# In-place?

Yes.

Only temporary variable used.

Space:

```text
O(1)
```

---

# Interview Follow-Ups

### Why is Bubble Sort rarely used?

Because

```text
O(n²)
```

is too slow for large datasets.

---

### When can Bubble Sort be acceptable?

* Tiny arrays
* Teaching algorithms
* Detecting nearly sorted arrays

---

# Problem 2 : Selection Sort

---

# Problem Explanation

Given

```text
64 25 12 22 11
```

Sort it.

Output

```text
11 12 22 25 64
```

---

# What Is The Interviewer Testing?

Can you understand the difference between:

```text
Bubble Sort

vs

Selection Sort
```

Many candidates confuse them.

---

# Pattern Recognition Clues

### Clue 1

Find:

```text
Minimum Element
```

---

### Clue 2

Place it at correct position.

---

### Clue 3

Exactly one swap per pass.

Think:

```text
Selection Sort
```

---

# Core Idea

Instead of swapping repeatedly,

Find

```text
Minimum
```

and move it once.

---

# Visualization

Input

```text
64 25 12 22 11
```

---

Pass 1

Minimum

```text
11
```

Swap

```text
11 25 12 22 64
```

---

Pass 2

Minimum

```text
12
```

Swap

```text
11 12 25 22 64
```

---

Pass 3

Minimum

```text
22
```

Swap

```text
11 12 22 25 64
```

Done.

---

# Dry Run

Array

```text
64 25 12 22 11
```

Minimum

↓

Swap

↓

Next minimum

↓

Swap

↓

Continue.

---

# Interview Insight

Selection Sort performs

```text
Exactly

n-1

Swaps
```

regardless of input.

Bubble Sort may perform many more swaps.

---

# What To Say In Interview

Selection Sort repeatedly selects the smallest element from the unsorted portion and places it at the beginning.

Unlike Bubble Sort, it performs only one swap per iteration.

---

# Optimal Java Code

```java
class SelectionSort {

    public static void selectionSort(int[] arr){

        int n = arr.length;

        for(int i = 0; i < n - 1; i++){

            int minIndex = i;

            for(int j = i + 1; j < n; j++){

                if(arr[j] < arr[minIndex]){

                    minIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}
```

---

# Complexity

| Case    | Complexity |
| ------- | ---------- |
| Best    | O(n²)      |
| Average | O(n²)      |
| Worst   | O(n²)      |

Space

```text
O(1)
```

---

# Why Best Case Is Still O(n²)?

Even if array is already sorted,

Selection Sort still searches for:

```text
Minimum Element
```

during every pass.

Comparisons never reduce.

---

# Stability

Selection Sort is

```text
Not Stable
```

Example

```text
2A 2B 1
```

After swapping

```text
1 2B 2A
```

Relative order changes.

---

# In-place?

Yes.

Only one temporary variable.

---

# Bubble Sort vs Selection Sort

| Feature     | Bubble | Selection    |
| ----------- | ------ | ------------ |
| Swaps       | Many   | One per pass |
| Comparisons | O(n²)  | O(n²)        |
| Stable      | ✅ Yes  | ❌ No         |
| Best Case   | O(n)   | O(n²)        |
| In-place    | ✅ Yes  | ✅ Yes        |

---

# Real Interview Discussion

**Interviewer:** Which one would you choose?

**Answer:**

If I had to choose between Bubble Sort and Selection Sort, I'd generally prefer **Selection Sort** because it performs significantly fewer swaps, which is useful when swap operations are expensive.

However, for a nearly sorted array, **Bubble Sort with the swapped optimization** can finish in **O(n)**, making it the better choice.

In practice, for real applications, I would choose **Insertion Sort**, **Merge Sort**, or **Quick Sort** depending on the constraints.

---

# Elementary Sorting Revision Sheet

## Bubble Sort

Pattern

```text
Largest Element
Moves Right
```

Stable

```text
Yes
```

Best Case

```text
O(n)
```

---

## Selection Sort

Pattern

```text
Select Minimum

Place Correctly
```

Stable

```text
No
```

Best Case

```text
O(n²)
```

---

# Golden Rule

Remember the three elementary sorts like this:

```text
Bubble Sort
↓

Keep Swapping
```

```text
Selection Sort
↓

Keep Selecting
```

```text
Insertion Sort
↓

Keep Inserting
```

Understanding these three ideas is far more valuable than memorizing their code.
