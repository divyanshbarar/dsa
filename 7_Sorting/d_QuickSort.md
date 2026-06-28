# Sorting Pattern - Part 4

# Divide & Conquer Family

# Quick Sort

---

# Why Quick Sort Matters

If Merge Sort is the king of **theory**,

Quick Sort is the king of **practical performance**.

Most programming languages use some variation of Quick Sort because:

* Extremely fast in practice
* Cache friendly
* In-place
* Low memory usage

Java uses:

```text id="qs401"
Dual Pivot QuickSort
```

for primitive arrays.

---

# Problem

Given

```text id="qs402"
10 7 8 9 1 5
```

Sort the array.

Output

```text id="qs403"
1 5 7 8 9 10
```

---

# What Is The Interviewer Testing?

Quick Sort isn't about sorting.

It's about whether you understand

* Partitioning
* Pivot
* Divide & Conquer
* Recursion
* Average vs Worst Case

---

# Pattern Recognition Clues

Whenever interviewer says

```text id="qs404"
Pivot
```

or

```text id="qs405"
Partition
```

or

```text id="qs406"
Quick Sort
```

Immediately think

```text id="qs407"
Choose Pivot

↓

Partition

↓

Recursively Sort
```

---

# Biggest Idea

Merge Sort

```text id="qs408"
Divide First

Merge Later
```

Quick Sort

```text id="qs409"
Partition First

Sort Later
```

This is the biggest conceptual difference.

---

# The Core Idea

Choose one element

called

```text id="qs410"
Pivot
```

Arrange array such that

```text id="qs411"
All Smaller Elements

↓

Left

All Larger Elements

↓

Right
```

Now pivot is already in its final position.

Repeat for both sides.

---

# Visualization

Input

```text id="qs412"
10 7 8 9 1 5
```

Choose pivot

```text id="qs413"
5
```

Partition

```text id="qs414"
1

5

10 7 8 9
```

Now

```text id="qs415"
5
```

never moves again.

Sort left

Sort right.

Done.

---

# Why Is It Called Partition?

Because one operation creates

```text id="qs416"
Left Part

Pivot

Right Part
```

Everything left is

```text id="qs417"
<= Pivot
```

Everything right is

```text id="qs418"
> Pivot
```

---

# Partition Process (Lomuto)

We'll use Lomuto Partition because it is easier to understand and is commonly expected in interviews.

---

Input

```text id="qs419"
10 7 8 9 1 5
```

Pivot

```text id="qs420"
5
```

Initially

```text id="qs421"
i = -1
```

Scan using

```text id="qs422"
j
```

---

Compare

```text id="qs423"
10
```

Greater.

Ignore.

---

Compare

```text id="qs424"
7
```

Greater.

Ignore.

---

Compare

```text id="qs425"
8
```

Greater.

Ignore.

---

Compare

```text id="qs426"
9
```

Greater.

Ignore.

---

Compare

```text id="qs427"
1
```

Smaller.

Increase

```text id="qs428"
i
```

Swap

```text id="qs429"
10

1
```

Array

```text id="qs430"
1 7 8 9 10 5
```

---

End of scan.

Swap pivot with

```text id="qs431"
i+1
```

Result

```text id="qs432"
1 5 8 9 10 7
```

Pivot fixed forever.

---

# Dry Run

Array

```text id="qs433"
4 6 3 2
```

Pivot

```text id="qs434"
2
```

Partition

```text id="qs435"
2 6 3 4
```

Pivot fixed.

Sort remaining.

---

# The Magic Of Quick Sort

Notice something.

Merge Sort

```text id="qs436"
Needs Merge Step
```

Quick Sort

```text id="qs437"
No Merge Step
```

Partition itself prepares the answer.

---

# What To Say In Interview

Quick Sort selects a pivot element and partitions the array so that every smaller element lies to its left and every larger element lies to its right.

Since the pivot reaches its final position after partitioning, we recursively sort only the left and right partitions.

---

# Partition Code (Lomuto)

```java id="qs438"
private static int partition(
        int[] arr,
        int low,
        int high
){

    int pivot = arr[high];

    int i = low - 1;

    for(int j = low;
        j < high;
        j++){

        if(arr[j] <= pivot){

            i++;

            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    int temp = arr[i+1];
    arr[i+1] = arr[high];
    arr[high] = temp;

    return i+1;
}
```

---

# Complete Quick Sort Code

```java id="qs439"
class QuickSort {

    public static void quickSort(
            int[] arr,
            int low,
            int high
    ){

        if(low >= high){
            return;
        }

        int pivotIndex =
                partition(
                    arr,
                    low,
                    high
                );

        quickSort(
                arr,
                low,
                pivotIndex-1
        );

        quickSort(
                arr,
                pivotIndex+1,
                high
        );
    }

    private static int partition(
            int[] arr,
            int low,
            int high
    ){

        int pivot = arr[high];

        int i = low-1;

        for(int j=low;
            j<high;
            j++){

            if(arr[j] <= pivot){

                i++;

                int temp=arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
            }
        }

        int temp=arr[i+1];
        arr[i+1]=arr[high];
        arr[high]=temp;

        return i+1;
    }
}
```

---

# Complexity

## Best Case

Pivot divides array equally.

```text id="qs440"
O(n log n)
```

---

## Average Case

Random pivot.

Still

```text id="qs441"
O(n log n)
```

---

## Worst Case

Already sorted

```text id="qs442"
1 2 3 4 5
```

Pivot

```text id="qs443"
5
```

Every partition becomes

```text id="qs444"
n-1

and

0
```

Recursion

```text id="qs445"
n
```

levels.

Total

```text id="qs446"
O(n²)
```

---

# Recursion Tree

## Best Case

```text id="qs447"
n

↓

n/2

↓

n/4

↓

...

↓

1
```

Height

```text id="qs448"
log n
```

---

## Worst Case

```text id="qs449"
n

↓

n-1

↓

n-2

↓

...

↓

1
```

Height

```text id="qs450"
n
```

---

# Why Is Quick Sort Usually Faster Than Merge Sort?

Even though both average

```text id="qs451"
O(n log n)
```

Quick Sort

* Works in-place
* Better CPU cache locality
* No temporary array
* Smaller constant factors

Hence

```text id="qs452"
Usually Faster
```

---

# Is Quick Sort Stable?

No.

Example

```text id="qs453"
4A 4B 2
```

After partition

```text id="qs454"
2 4B 4A
```

Relative order changed.

Therefore

```text id="qs455"
Not Stable
```

---

# Is Quick Sort In-place?

Yes.

Except recursion stack.

Extra space

```text id="qs456"
O(log n)
```

average.

---

# Interview Follow-Ups

## Why Does Worst Case Occur?

Poor pivot selection.

Choosing

```text id="qs457"
First Element

or

Last Element
```

for an already sorted array causes highly unbalanced partitions.

---

## How To Avoid Worst Case?

Choose

```text id="qs458"
Random Pivot
```

or

```text id="qs459"
Median Of Three
```

Many libraries do this.

---

## What Is Dual Pivot Quick Sort?

Instead of one pivot,

Java uses

```text id="qs460"
Two Pivots
```

The array is partitioned into

```text id="qs461"
Less than Left Pivot

Between Pivots

Greater than Right Pivot
```

This improves practical performance for primitive arrays.

---

# Merge Sort vs Quick Sort

| Feature    | Merge        | Quick      |
| ---------- | ------------ | ---------- |
| Stable     | ✅            | ❌          |
| In-place   | ❌            | ✅          |
| Worst Case | O(n log n)   | O(n²)      |
| Average    | O(n log n)   | O(n log n) |
| Memory     | O(n)         | O(log n)   |
| Used For   | Linked Lists | Arrays     |

---

# Real Interview Discussion

**Interviewer:** Why is Quick Sort faster if both are O(n log n)?

Good answer:

> Although both have O(n log n) average complexity, Quick Sort performs sorting in place, has better cache locality, avoids allocating temporary arrays, and therefore has much smaller constant factors. In practice, it is usually faster for arrays.

---

# Quick Sort Revision Sheet

Pattern

```text id="qs462"
Choose Pivot

↓

Partition

↓

Recursively Sort
```

---

Partition Goal

```text id="qs463"
Left <= Pivot

Right > Pivot
```

---

Stable

```text id="qs464"
No
```

---

In-place

```text id="qs465"
Yes
```

---

Average

```text id="qs466"
O(n log n)
```

---

Worst

```text id="qs467"
O(n²)
```

---

Used By

```text id="qs468"
Java Primitive Arrays

C++ std::sort (Introsort)

Many High Performance Libraries
```

---

# Bubble → Merge → Quick Evolution

```text id="qs469"
Bubble

↓

Keep Swapping
```

```text id="qs470"
Merge

↓

Divide

Merge
```

```text id="qs471"
Quick

↓

Partition

Recurse
```

---

# Golden Rule

Whenever interviewer says:

```text id="qs472"
Pivot

Partition

In-place O(n log n)

Array Sorting
```

Immediately think:

```text id="qs473"
Quick Sort
```

Remember this mental model:

```text id="qs474"
Choose Pivot

↓

Put Pivot In Final Position

↓

Repeat Left

↓

Repeat Right
```

Once you understand **partitioning**, you've understood Quick Sort. The recursion is simply repeating the same idea on smaller subarrays.
