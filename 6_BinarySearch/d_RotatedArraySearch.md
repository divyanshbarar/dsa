# Binary Search Pattern - Part 4

# Rotated Array Family

---

# Why This Family Matters

This is one of the most frequently asked Binary Search patterns.

Companies love it because:

```text id="r401"
Array is not fully sorted

BUT

Binary Search still works
```

Most candidates panic when they see:

```text id="r402"
Rotated Array
```

Strong candidates immediately think:

```text id="r403"
One Half Is Always Sorted
```

---

# The Most Important Insight

Original Array:

```text id="r404"
1 2 3 4 5 6 7
```

Rotated:

```text id="r405"
4 5 6 7 1 2 3
```

Notice:

```text id="r406"
Entire Array

Not Sorted
```

But:

```text id="r407"
Left Half

or

Right Half

is ALWAYS Sorted
```

This is the key.

---

# Problem 10: Search In Rotated Sorted Array

## LeetCode 33

---

# Problem Explanation

Given:

```text id="r408"
4 5 6 7 0 1 2
```

Target:

```text id="r409"
0
```

Return:

```text id="r410"
4
```

---

# What Is The Interviewer Testing?

Can you still apply:

```text id="r411"
Binary Search
```

when array isn't fully sorted?

---

# Pattern Recognition Clues

### Clue 1

Rotated Array.

### Clue 2

Distinct Elements.

### Clue 3

Search Target.

Think:

```text id="r412"
One Half Sorted
```

---

# Brute Force

Linear Scan.

---

## Complexity

```text id="r413"
O(n)
```

---

# Key Observation

At every step:

```text id="r414"
mid
```

splits array into:

```text id="r415"
Left Half

Right Half
```

One of them is guaranteed sorted.

---

# How To Detect Sorted Half

### Left Half Sorted

```java id="r416"
nums[left]
<=
nums[mid]
```

---

### Right Half Sorted

```java id="r417"
nums[mid]
<=
nums[right]
```

---

# Visualization

```text id="r418"
4 5 6 7 0 1 2
```

Mid:

```text id="r419"
7
```

Left:

```text id="r420"
4 5 6 7
```

Sorted.

---

Target:

```text id="r421"
0
```

Not inside:

```text id="r422"
4 → 7
```

Discard left.

---

Continue.

---

# Decision Tree

If:

```java id="r423"
nums[left]
<=
nums[mid]
```

Left sorted.

---

Target inside?

```java id="r424"
target >= nums[left]
&&
target < nums[mid]
```

Search left.

Else:

Search right.

---

# Dry Run

```text id="r425"
4 5 6 7 0 1 2
```

Target:

```text id="r426"
0
```

---

Mid:

```text id="r427"
7
```

Left sorted.

Target not inside.

Search right.

---

New Range:

```text id="r428"
0 1 2
```

Mid:

```text id="r429"
1
```

Target inside left half.

Search left.

---

Found.

---

# What To Say In Interview

Although the entire array isn't sorted, one side around the midpoint is always sorted.

I identify the sorted half and determine whether the target belongs there. This lets me discard half of the search space each iteration.

---

# Optimal Java Code

```java id="r430"
class Solution {

    public int search(
            int[] nums,
            int target
    ) {

        int left = 0;
        int right =
                nums.length - 1;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(nums[mid]
                == target){

                return mid;
            }

            if(nums[left]
                <= nums[mid]){

                if(target >= nums[left]
                    &&
                   target < nums[mid]){

                    right = mid - 1;

                }else{

                    left = mid + 1;
                }

            }else{

                if(target > nums[mid]
                    &&
                   target <= nums[right]){

                    left = mid + 1;

                }else{

                    right = mid - 1;
                }
            }
        }

        return -1;
    }
}
```

---

# Complexity

```text id="r431"
Time :

O(log n)

Space :

O(1)
```

---

# Interview Follow-Up

### What If Duplicates Exist?

Example:

```text id="r432"
1 1 1 1 2 1
```

Now:

```text id="r433"
nums[left]
==
nums[mid]
```

Cannot determine sorted half.

Worst case:

```text id="r434"
O(n)
```

---

# Problem 11: Find Minimum In Rotated Sorted Array

## LeetCode 153

---

# Problem Explanation

Given:

```text id="r435"
4 5 6 7 0 1 2
```

Return:

```text id="r436"
0
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="r437"
Rotation Point
```

?

---

# Visualization

Original:

```text id="r438"
0 1 2 4 5 6 7
```

Rotated:

```text id="r439"
4 5 6 7 0 1 2
```

Minimum:

```text id="r440"
0
```

appears exactly where rotation occurred.

---

# Brute Force

Scan entire array.

---

## Complexity

```text id="r441"
O(n)
```

---

# Key Observation

Compare:

```java id="r442"
nums[mid]
```

with:

```java id="r443"
nums[right]
```

---

### Case 1

```java id="r444"
nums[mid]
>
nums[right]
```

Minimum must be:

```text id="r445"
Right Side
```

---

Example:

```text id="r446"
4 5 6 7 0 1 2
```

Mid:

```text id="r447"
7
```

Right:

```text id="r448"
2
```

Since:

```text id="r449"
7 > 2
```

Minimum lies right.

---

### Case 2

```java id="r450"
nums[mid]
<
nums[right]
```

Minimum lies:

```text id="r451"
Left Side

including mid
```

---

# Dry Run

```text id="r452"
4 5 6 7 0 1 2
```

---

Mid:

```text id="r453"
7
```

Right:

```text id="r454"
2
```

Search right.

---

Range:

```text id="r455"
0 1 2
```

Mid:

```text id="r456"
1
```

Right:

```text id="r457"
2
```

Search left.

---

Eventually:

```text id="r458"
0
```

---

# What To Say In Interview

The minimum element is the only place where sorted order breaks.

By comparing the midpoint with the rightmost element, I can determine which side contains the rotation point.

---

# Optimal Java Code

```java id="r459"
class Solution {

    public int findMin(
            int[] nums
    ) {

        int left = 0;
        int right =
                nums.length - 1;

        while(left < right){

            int mid =
                left +
                (right-left)/2;

            if(nums[mid]
                > nums[right]){

                left = mid + 1;

            }else{

                right = mid;
            }
        }

        return nums[left];
    }
}
```

---

# Complexity

```text id="r460"
Time :

O(log n)

Space :

O(1)
```

---

# Why Right = Mid ?

Not:

```java id="r461"
mid - 1
```

Because:

```text id="r462"
mid itself
could be minimum
```

---

# Rotated Array Visualization

Example:

```text id="r463"
4 5 6 7 0 1 2
```

Pivot:

```text id="r464"
0
```

Everything before pivot:

```text id="r465"
Greater
```

Everything after pivot:

```text id="r466"
Smaller
```

Binary Search locates this boundary.

---

# Rotated Family Revision Sheet

## Search In Rotated Array

Goal:

```text id="r467"
Find Target
```

Key Idea:

```text id="r468"
One Half Always Sorted
```

---

## Find Minimum

Goal:

```text id="r469"
Find Rotation Point
```

Key Idea:

```java id="r470"
nums[mid]

vs

nums[right]
```

---

# Master Decision Tree

### Search Problem?

Use:

```text id="r471"
Which Half Is Sorted?
```

---

### Minimum Problem?

Use:

```text id="r472"
Where Is Pivot?
```

---

# Most Important Interview Insight

Normal Binary Search asks:

```text id="r473"
Which Side Contains Target?
```

Rotated Binary Search asks:

```text id="r474"
Which Side Is Sorted?
```

First.

Then:

```text id="r475"
Does Target Belong There?
```

Second.

---

# Golden Rule

Whenever interviewer says:

```text id="r476"
Rotated Sorted Array
```

Immediately think:

```text id="r477"
One Half
Is Always Sorted
```

That single observation solves almost every rotated-array interview problem.
