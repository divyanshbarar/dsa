# Two Pointers Pattern

# 1. Pattern Recognition

## What is Two Pointers?

Two pointers means using two indices to traverse an array/string in a way that avoids unnecessary nested loops.

Typical goal:

* Reduce O(n²) brute force to O(n)
* Solve in-place problems
* Exploit sorted arrays
* Process subarrays efficiently

---

# 2. Interview Clues

If you see:

### Clue A: Sorted Array

Examples:

* Two Sum II
* 3Sum
* Squares of Sorted Array

Think:

```java
left = 0
right = n - 1
```

---

### Clue B: Remove / Move Elements In Place

Examples:

* Move Zeroes
* Remove Duplicates
* Remove Element

Think:

```java
slow
fast
```

---

### Clue C: Reverse / Compare Ends

Examples:

* Reverse String
* Valid Palindrome

Think:

```java
left++
right--
```

---

### Clue D: Find Longest/Shortest Window

Examples:

* Longest Substring Without Repeating Characters
* Minimum Size Subarray Sum
* Subarray Product Less Than K

Think:

Sliding Window

---

### Clue E: Pair/Triplet Sum

Examples:

* Two Sum II
* 3Sum

Think:

Sort + Two Pointers

---

# 3. Templates

# Template 1: Opposite Direction Pointers

Used when:

* Array sorted
* Comparing ends
* Pair problems

```java
int left = 0;
int right = arr.length - 1;

while(left < right){

    if(conditionMet){
        // answer
    }
    else if(needSmallerValue){
        right--;
    }
    else{
        left++;
    }
}
```

Problems:

* Two Sum II
* Reverse String
* Valid Palindrome
* Container With Most Water
* Squares of Sorted Array
* Trapping Rain Water
* 3Sum

---

# Template 2: Slow Fast Pointer

Used when:

* In-place modification
* Removing elements
* Rearranging array

```java
int slow = 0;

for(int fast = 0; fast < n; fast++){

    if(valid){
        nums[slow] = nums[fast];
        slow++;
    }
}
```

Problems:

* Remove Duplicates
* Move Zeroes
* Remove Element
* Sort Colors

---

# Template 3: Sliding Window

Used when:

* Contiguous subarray
* Contiguous substring
* Longest/Shortest window

```java
int left = 0;

for(int right = 0; right < n; right++){

    while(windowInvalid){
        left++;
    }

    answer = update();
}
```

Problems:

* Longest Substring Without Repeating Characters
* Minimum Size Subarray Sum
* Subarray Product Less Than K

---

# GROUP 1: SLOW FAST POINTER PROBLEMS

---

# Problem 1: Remove Duplicates From Sorted Array

LeetCode 26

## Clues

* Sorted array
* In-place
* Remove duplicates

## Brute Force

Store unique values in another array.

Time: O(n)
Space: O(n)

## Optimal

Use slow pointer as position for next unique element.

## Interview Line

Since duplicates are adjacent in a sorted array, I can maintain a slow pointer for unique elements and a fast pointer for scanning.

## Complexity

Time: O(n)

Space: O(1)

---

# Problem 2: Move Zeroes

LeetCode 283

## Clues

* In-place
* Preserve order

## Brute Force

Create new array.

Time: O(n)

Space: O(n)

## Optimal

Slow pointer = next non-zero position.

Fast pointer scans.

## Interview Line

I need stable ordering while moving zeroes to the end, so I'll place non-zero elements using a slow pointer.

## Complexity

Time: O(n)

Space: O(1)

---

# Problem 3: Remove Element

LeetCode 27

## Clues

* In-place removal
* Return new length

## Optimal

Same as Remove Duplicates.

Copy valid elements forward.

## Complexity

Time: O(n)

Space: O(1)

---

# Problem 4: Sort Colors

LeetCode 75

## Pattern

Dutch National Flag

Three pointers:

```java
low
mid
high
```

## Clues

Values only:

```text
0 1 2
```

## Interview Line

Since only three distinct values exist, I can partition the array into three regions using three pointers.

## Complexity

Time: O(n)

Space: O(1)

---

# GROUP 2: OPPOSITE DIRECTION POINTERS

---

# Problem 5: Two Sum II

LeetCode 167

## Clues

* Sorted array
* Pair sum

## Brute Force

Nested loops

O(n²)

## Better

HashMap

O(n)

Space O(n)

## Optimal

Left + Right pointers

## Complexity

Time O(n)

Space O(1)

---

# Problem 6: Reverse String

LeetCode 344

## Clues

Reverse in-place

## Optimal

Swap ends.

Move inward.

Time O(n)

Space O(1)

---

# Problem 7: Valid Palindrome

LeetCode 125

## Clues

Compare both ends.

Ignore special characters.

## Optimal

Two pointers.

Skip invalid chars.

Compare lowercase values.

Time O(n)

Space O(1)

---

# Problem 8: Container With Most Water

LeetCode 11

## Key Insight

Area =

```text
width × min(height)
```

## Interview Line

Moving the taller line never helps because the shorter line limits the area.

Move the shorter pointer.

## Complexity

Time O(n)

Space O(1)

---

# Problem 9: Squares of Sorted Array

LeetCode 977

## Clues

Sorted array

Negative numbers exist

## Observation

Largest square comes from either end.

## Optimal

Compare:

```java
abs(left)
abs(right)
```

Fill answer from back.

## Complexity

Time O(n)

Space O(n)

---

# Problem 10: Trapping Rain Water

LeetCode 42

## Key Formula

```text
water =
min(leftMax, rightMax)
-height[i]
```

## Optimal

Maintain:

```java
leftMax
rightMax
```

Two pointers.

## Complexity

Time O(n)

Space O(1)

---

# Problem 11: 3Sum

LeetCode 15

## Clues

Triplet sum

Duplicates

## Brute Force

Three loops

O(n³)

## Optimal

Sort array.

Fix one element.

Run Two Sum on remaining part.

## Complexity

Time O(n²)

Space O(1)

---

# GROUP 3: SLIDING WINDOW

---

# Problem 12: Longest Substring Without Repeating Characters

LeetCode 3

## Clues

Longest substring

No duplicates

## Template

Expand right.

Shrink left while duplicate exists.

## Complexity

Time O(n)

Space O(128)

---

# Problem 13: Minimum Size Subarray Sum

LeetCode 209

## Clues

Smallest window

Sum >= target

## Template

Expand until valid.

Shrink to minimize.

## Complexity

Time O(n)

Space O(1)

---

# Problem 14: Subarray Product Less Than K

LeetCode 713

## Clues

Contiguous subarray

Positive numbers

## Template

Maintain product.

Shrink while product >= k.

## Complexity

Time O(n)

Space O(1)

---

# GROUP 4: HYBRID TWO POINTER

---

# Problem 15: Longest Mountain In Array

LeetCode 845

## Clues

Increasing then decreasing

Peak exists

## Idea

Find peak.

Expand left.

Expand right.

Compute length.

## Complexity

Time O(n)

Space O(1)

---

# Revision Sheet

## Slow Fast

* Remove Duplicates
* Move Zeroes
* Remove Element
* Sort Colors

---

## Opposite Direction

* Two Sum II
* Reverse String
* Valid Palindrome
* Container With Most Water
* Squares of Sorted Array
* Trapping Rain Water
* 3Sum

---

## Sliding Window

* Longest Substring Without Repeating Characters
* Minimum Size Subarray Sum
* Subarray Product Less Than K

---

## Hybrid

* Longest Mountain In Array

---

# Interview Rule

Before coding always say:

1. Let me identify the pattern.
2. Let me discuss brute force.
3. Can we improve time complexity?
4. Can we improve space complexity?
5. Then code the optimal solution.
