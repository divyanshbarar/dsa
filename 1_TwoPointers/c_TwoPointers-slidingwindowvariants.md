# Two Pointers Pattern - Part 3

# Template 3: Sliding Window

## When To Use

Look for these clues:

* Contiguous subarray
* Contiguous substring
* Longest window
* Shortest window
* Count windows
* Sum/Product constraints

Keywords:

```text id="6b59z0"
subarray
substring
continuous
contiguous
window
```

---

## Generic Sliding Window Template

```java id="4a3o7m"
int left = 0;

for(int right = 0; right < n; right++) {

    // Expand window

    while(windowInvalid) {
        left++;
    }

    answer = updateAnswer();
}
```

---

# Problem 11: Longest Substring Without Repeating Characters

## LeetCode 3

---

## Pattern

Sliding Window

---

## Interview Clues

### Clue 1

Substring

### Clue 2

Longest

### Clue 3

No repeating characters

Whenever you see:

```text id="mkhmjm"
Longest Substring
```

Think:

```text id="wztpgz"
Sliding Window
```

---

## Brute Force

Generate all substrings.

Check uniqueness.

---

## Complexity

```text id="1x2o3l"
Time : O(n³)
Space: O(1)
```

---

## Better

Generate substring.

Use HashSet.

---

## Complexity

```text id="qwtfmg"
Time : O(n²)
Space: O(n)
```

---

## Optimal Approach

Maintain:

```java id="m9xg5s"
HashSet<Character>
```

Window always contains unique characters.

If duplicate appears:

Shrink from left.

---

## Dry Run

```text id="b6g2lh"
abcabcbb

a
ab
abc

next = a

duplicate

remove a

window becomes

bca
```

---

## What To Say In Interview

Since we're looking for the longest valid contiguous substring, I'll maintain a sliding window.

The window will always contain unique characters.

Whenever a duplicate appears, I'll shrink the window until it becomes valid again.

---

## Optimal Java Code

```java id="w3q3t8"
class Solution {

    public int lengthOfLongestSubstring(String s) {

        Set<Character> set = new HashSet<>();

        int left = 0;
        int answer = 0;

        for(int right = 0; right < s.length(); right++) {

            while(set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }

            set.add(s.charAt(right));

            answer = Math.max(
                    answer,
                    right - left + 1
            );
        }

        return answer;
    }
}
```

---

## Complexity

```text id="0j8s0q"
Time  : O(n)
Space : O(n)
```

---

## Similar Problems

* Longest Repeating Character Replacement
* Minimum Window Substring
* Fruit Into Baskets

---

# Problem 12: Minimum Size Subarray Sum

## LeetCode 209

---

## Pattern

Sliding Window

---

## Interview Clues

### Clue 1

Minimum length

### Clue 2

Subarray

### Clue 3

Sum >= target

---

## Brute Force

Generate all subarrays.

Calculate sum.

---

## Complexity

```text id="0f2m2l"
Time : O(n²)
Space: O(1)
```

---

## Optimal Approach

Expand window until:

```text id="z12rzr"
sum >= target
```

Then shrink to minimize length.

---

## Dry Run

```text id="38n6ua"
target = 7

2 3 1 2 4 3

window:

2+3+1+2 = 8

Valid

Shrink

Find smaller valid window
```

---

## What To Say In Interview

Once the window reaches the required sum, expanding further only increases its size.

Therefore I'll shrink from the left while maintaining validity.

---

## Optimal Java Code

```java id="vazs0o"
class Solution {

    public int minSubArrayLen(
            int target,
            int[] nums
    ) {

        int left = 0;
        int sum = 0;

        int answer = Integer.MAX_VALUE;

        for(int right = 0; right < nums.length; right++) {

            sum += nums[right];

            while(sum >= target) {

                answer = Math.min(
                        answer,
                        right - left + 1
                );

                sum -= nums[left];
                left++;
            }
        }

        return answer == Integer.MAX_VALUE
                ? 0
                : answer;
    }
}
```

---

## Complexity

```text id="w6h8m6"
Time  : O(n)
Space : O(1)
```

---

# Problem 13: Subarray Product Less Than K

## LeetCode 713

---

## Pattern

Sliding Window

---

## Interview Clues

### Clue 1

Subarray

### Clue 2

Product

### Clue 3

Positive numbers

### Clue 4

Count subarrays

---

## Brute Force

Generate all subarrays.

Compute product.

---

## Complexity

```text id="6te0rj"
Time : O(n²)
Space: O(1)
```

---

## Key Observation

If current window product is valid:

```text id="w8by91"
All subarrays ending at right
are also valid.
```

Count:

```java id="7ub2iq"
right - left + 1
```

---

## Dry Run

```text id="4m57w0"
10 5 2 6

k = 100

window:

10

count += 1

10 5

count += 2

10 5 2

product invalid

shrink
```

---

## What To Say In Interview

Since all values are positive, once the product exceeds k, moving left forward is guaranteed to decrease the product.

This makes Sliding Window possible.

---

## Optimal Java Code

```java id="hhmtvc"
class Solution {

    public int numSubarrayProductLessThanK(
            int[] nums,
            int k
    ) {

        if(k <= 1) {
            return 0;
        }

        int left = 0;

        int product = 1;

        int answer = 0;

        for(int right = 0; right < nums.length; right++) {

            product *= nums[right];

            while(product >= k) {
                product /= nums[left];
                left++;
            }

            answer +=
                    right - left + 1;
        }

        return answer;
    }
}
```

---

## Complexity

```text id="kz31ln"
Time  : O(n)
Space : O(1)
```

---

# Problem 14: Trapping Rain Water

## LeetCode 42

---

## Pattern

Advanced Two Pointers

---

## Interview Clues

### Clue 1

Water trapped between bars

### Clue 2

Need total quantity

### Clue 3

Brute force possible

### Clue 4

Space optimization possible

---

## Formula

Water at index i:

```text id="jry7qs"
min(leftMax,rightMax)
-
height[i]
```

---

## Brute Force

For every index:

Find:

```text id="c33v9s"
leftMax
rightMax
```

---

## Complexity

```text id="q6n5ki"
Time : O(n²)
Space: O(1)
```

---

## Better

Precompute:

```java id="ig1yzv"
leftMax[]
rightMax[]
```

---

## Complexity

```text id="4gjmbj"
Time : O(n)
Space: O(n)
```

---

## Optimal Approach

Maintain:

```java id="lu0qmy"
leftMax
rightMax
```

with two pointers.

---

## Key Observation

If:

```text id="85z9je"
leftMax < rightMax
```

Then left side answer is already determined.

---

## What To Say In Interview

Water depends on the smaller boundary.

If leftMax is smaller than rightMax, the amount of water on the left side is fixed and can be calculated immediately.

---

## Optimal Java Code

```java id="3g7jow"
class Solution {

    public int trap(int[] height) {

        int left = 0;
        int right = height.length - 1;

        int leftMax = 0;
        int rightMax = 0;

        int water = 0;

        while(left < right) {

            if(height[left] < height[right]) {

                leftMax =
                        Math.max(
                                leftMax,
                                height[left]
                        );

                water +=
                        leftMax - height[left];

                left++;

            } else {

                rightMax =
                        Math.max(
                                rightMax,
                                height[right]
                        );

                water +=
                        rightMax - height[right];

                right--;
            }
        }

        return water;
    }
}
```

---

## Complexity

```text id="m8r6q7"
Time  : O(n)
Space : O(1)
```

---

## Similar Problems

* Container With Most Water
* Elevation Problems

---

# Problem 15: Longest Mountain In Array

## LeetCode 845

---

## Pattern

Hybrid Two Pointer

---

## Interview Clues

### Clue 1

Increasing then decreasing

### Clue 2

Peak exists

### Clue 3

Need longest mountain

---

## Mountain Definition

```text id="i1q6v0"
strictly increasing

then

strictly decreasing
```

---

## Brute Force

Check every index as peak.

Expand both sides.

---

## Complexity

```text id="f6zhd5"
Time : O(n²)
Space: O(1)
```

---

## Optimal Approach

Find peak.

Expand left.

Expand right.

Compute mountain length.

Move forward.

---

## Dry Run

```text id="sm5tnr"
2 1 4 7 3 2 5

Peak = 7

Expand left

1 4 7

Expand right

7 3 2

Length = 5
```

---

## What To Say In Interview

A valid mountain must have a peak.

I'll identify peaks and expand outward to determine the full mountain length.

---

## Optimal Java Code

```java id="grvh7u"
class Solution {

    public int longestMountain(int[] arr) {

        int n = arr.length;

        int answer = 0;

        int i = 1;

        while(i < n - 1) {

            boolean peak =
                    arr[i] > arr[i - 1]
                    &&
                    arr[i] > arr[i + 1];

            if(!peak) {
                i++;
                continue;
            }

            int left = i;
            int right = i;

            while(left > 0
                    &&
                    arr[left] > arr[left - 1]) {
                left--;
            }

            while(right < n - 1
                    &&
                    arr[right] > arr[right + 1]) {
                right++;
            }

            answer =
                    Math.max(
                            answer,
                            right - left + 1
                    );

            i = right;
        }

        return answer;
    }
}
```

---

## Complexity

```text id="cb83ln"
Time  : O(n)

Space : O(1)
```

---

# Sliding Window Master Sheet

## Window Expansion

```java id="ukz0ly"
for(int right = 0; right < n; right++) {
}
```

---

## Window Shrinking

```java id="a8jx7v"
while(windowInvalid) {
    left++;
}
```

---

## Longest Window Problems

Goal:

```text id="m8n4ti"
maximize length
```

Examples:

* Longest Substring Without Repeating Characters
* Longest Repeating Character Replacement
* Max Consecutive Ones

---

## Smallest Window Problems

Goal:

```text id="zqjlwm"
minimize length
```

Examples:

* Minimum Size Subarray Sum
* Minimum Window Substring

---

## Counting Window Problems

Goal:

```text id="od1djk"
count all windows
```

Examples:

* Subarray Product Less Than K
* Binary Subarrays With Sum

---

# Two Pointer Pattern Revision

## Slow Fast Pointer

* Remove Duplicates
* Move Zeroes
* Remove Element

---

## Dutch National Flag

* Sort Colors

---

## Opposite Direction

* Two Sum II
* Reverse String
* Valid Palindrome
* Container With Most Water
* Squares of Sorted Array

---

## Sort + Two Pointers

* 3Sum

---

## Sliding Window

* Longest Substring Without Repeating Characters
* Minimum Size Subarray Sum
* Subarray Product Less Than K

---

## Advanced Two Pointers

* Trapping Rain Water
* Longest Mountain In Array

```
```
