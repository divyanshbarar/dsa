# Sorting Pattern - Part 3

# Divide & Conquer Family

# Merge Sort

---

# Why Merge Sort Matters

This is the first **efficient sorting algorithm** you'll learn.

Unlike Bubble, Selection, and Insertion Sort:

```text
O(n²)
```

Merge Sort guarantees:

```text
O(n log n)
```

for **every input**.

It is one of the most important interview algorithms because it introduces:

* Divide & Conquer
* Recursion
* Merge Process
* Stable Sorting

Many advanced interview questions are based on Merge Sort:

* Count Inversions
* Reverse Pairs
* Count Smaller Numbers After Self
* Merge K Sorted Arrays
* External Sorting

Understanding Merge Sort is more important than memorizing it.

---

# Problem

Given:

```text
38 27 43 3 9 82 10
```

Sort the array.

Output

```text
3 9 10 27 38 43 82
```

---

# What Is The Interviewer Testing?

They are NOT testing sorting.

They are testing whether you understand:

* Divide & Conquer
* Recursion
* Merging two sorted arrays
* Recursion Tree

---

# Pattern Recognition Clues

Whenever interviewer says:

```text
Recursively Divide
```

or

```text
Merge Sorted Parts
```

Think:

```text
Merge Sort
```

---

# Biggest Idea

Bubble Sort

```text
Swap
```

Selection Sort

```text
Select
```

Insertion Sort

```text
Insert
```

Merge Sort

```text
Divide

↓

Solve

↓

Merge
```

---

# Divide & Conquer

Every Merge Sort follows exactly three steps.

Step 1

```text
Divide
```

Step 2

```text
Sort Left

Sort Right
```

Step 3

```text
Merge
```

That's the entire algorithm.

---

# Visualization

Input

```text
38 27 43 3 9 82 10
```

Divide

```text
38 27 43

3 9 82 10
```

Divide again

```text
38

27 43

3 9

82 10
```

Continue until

```text
Single Element
```

---

Why?

Because

```text
Single Element

is already sorted.
```

This is the **base case**.

---

# Recursion Tree

```text
                 38 27 43 3 9 82 10
                 /                \
        38 27 43                3 9 82 10
        /     \                 /      \
      38     27 43            3 9     82 10
             /   \            / \      / \
           27   43          3   9    82 10
```

Every leaf

```text
Contains

One Element
```

Already sorted.

---

# The Hard Part

Almost everyone understands:

```text
Divide
```

Most candidates fail at:

```text
Merge
```

---

# Merge Process

Suppose Left Array

```text
2 5 8
```

Right Array

```text
1 3 7
```

Need

```text
1 2 3 5 7 8
```

---

# Visualization

Pointer i

↓

```text
2 5 8
```

Pointer j

↓

```text
1 3 7
```

Compare

```text
2

vs

1
```

Smaller

↓

```text
1
```

goes into answer.

---

Now

```text
2

vs

3
```

Take

```text
2
```

Continue.

---

Eventually

```text
1 2 3 5 7 8
```

---

# Merge Dry Run

Left

```text
4 8
```

Right

```text
2 5 9
```

Compare

```text
4

2
```

Take

```text
2
```

Answer

```text
2
```

---

Compare

```text
4

5
```

Take

```text
4
```

Answer

```text
2 4
```

---

Compare

```text
8

5
```

Take

```text
5
```

Answer

```text
2 4 5
```

---

Compare

```text
8

9
```

Take

```text
8
```

Answer

```text
2 4 5 8
```

---

Right array remains

```text
9
```

Copy remaining.

Final

```text
2 4 5 8 9
```

---

# Key Observation

Merge works because

both arrays are

```text
Already Sorted
```

This is why Divide step is important.

---

# What To Say In Interview

Merge Sort recursively divides the array into two halves until every subarray contains only one element.

Since a single element is already sorted, the recursion starts merging adjacent sorted arrays.

The merge operation combines two sorted arrays into one sorted array, eventually producing the completely sorted array.

---

# Optimal Java Code

```java
class MergeSort {

    public static void mergeSort(
            int[] arr,
            int left,
            int right
    ){

        if(left >= right){
            return;
        }

        int mid =
                left +
                (right-left)/2;

        mergeSort(arr,left,mid);

        mergeSort(arr,mid+1,right);

        merge(arr,left,mid,right);
    }

    private static void merge(
            int[] arr,
            int left,
            int mid,
            int right
    ){

        int[] temp =
                new int[right-left+1];

        int i = left;
        int j = mid+1;
        int k = 0;

        while(i<=mid && j<=right){

            if(arr[i] <= arr[j]){

                temp[k++] = arr[i++];

            }else{

                temp[k++] = arr[j++];
            }
        }

        while(i<=mid){

            temp[k++] = arr[i++];
        }

        while(j<=right){

            temp[k++] = arr[j++];
        }

        for(i=left,k=0;
            i<=right;
            i++,k++){

            arr[i]=temp[k];
        }
    }
}
```

---

# Complexity

## Divide

Every level

```text
O(1)
```

---

## Merge

Every level processes

```text
n elements
```

---

## Levels

Tree height

```text
log₂ n
```

---

Overall

```text
O(n log n)
```

---

# Why Always O(n log n)?

Bubble Sort depends on input.

Merge Sort doesn't.

Whether array is

```text
Sorted

Reverse Sorted

Random
```

the recursion tree is identical.

Every level processes

```text
n elements
```

There are

```text
log n
```

levels.

Hence

```text
O(n log n)
```

always.

---

# Why Is Merge Sort Stable?

Suppose

Left

```text
4A
```

Right

```text
4B
```

Comparison

```java
<=
```

means

Left element is copied first.

Result

```text
4A 4B
```

Relative order preserved.

Therefore

```text
Stable
```

---

# Why Isn't Merge Sort In-place?

Because every merge creates

```text
Temporary Array
```

Example

```java
int[] temp
```

Extra memory

```text
O(n)
```

---

# Interview Follow-Ups

### Why is Merge Sort preferred for Linked Lists?

Splitting a linked list is easy.

Merging linked lists requires only pointer changes.

No random access needed.

Hence Merge Sort is the best sorting algorithm for linked lists.

---

### Why is Merge Sort used for External Sorting?

Files are too large to fit into RAM.

Merge Sort naturally works by:

```text
Sort Small Chunks

↓

Merge Chunks
```

making it ideal for disk-based sorting.

---

### Can Merge Sort be implemented iteratively?

Yes.

Using

```text
Bottom-Up Merge Sort
```

instead of recursion.

---

# Bubble vs Insertion vs Merge

| Feature   | Bubble | Insertion | Merge      |
| --------- | ------ | --------- | ---------- |
| Stable    | ✅      | ✅         | ✅          |
| In-place  | ✅      | ✅         | ❌          |
| Best      | O(n)   | O(n)      | O(n log n) |
| Average   | O(n²)  | O(n²)     | O(n log n) |
| Worst     | O(n²)  | O(n²)     | O(n log n) |
| Recursive | ❌      | ❌         | ✅          |

---

# Real Interview Discussion

**Interviewer: Why would you choose Merge Sort instead of Quick Sort?**

Good answer:

> Merge Sort guarantees **O(n log n)** in the worst case and is stable. It is preferred when stability is required, when sorting linked lists, or when dealing with external sorting. The trade-off is that it requires **O(n)** extra memory.

---

# Merge Sort Revision Sheet

Pattern

```text
Divide

↓

Conquer

↓

Merge
```

---

Base Case

```text
Single Element
```

---

Merge Requirement

```text
Both Halves

Already Sorted
```

---

Stable

```text
Yes
```

---

In-place

```text
No
```

---

Extra Space

```text
O(n)
```

---

Time Complexity

```text
Always

O(n log n)
```

---

# Golden Rule

Whenever interviewer says:

```text
Recursively Divide

Merge Sorted Parts

Stable O(n log n)
```

Immediately think:

```text
Merge Sort
```

Remember this mental model:

```text
Split Until

One Element

↓

Merge Small Arrays

↓

Merge Bigger Arrays

↓

Sorted Array
```

If you truly understand the **merge step**, you've understood Merge Sort. Everything else is just recursion around it.
