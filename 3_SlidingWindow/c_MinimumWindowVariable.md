# Sliding Window Pattern - Part 3

# Minimum Window Family

---

# Why This Family Is Different

In Longest Window problems:

```text
Expand Window
Shrink Only When Invalid
```

Goal:

```text
Maximize Length
```

But here:

```text
Expand Window
Shrink Aggressively
```

Goal:

```text
Minimize Length
```

This mindset shift is extremely important.

Many candidates solve Longest Window correctly but fail Minimum Window problems because they keep expanding when they should be shrinking.

---

# Master Template

```java
int left = 0;

for(int right = 0; right < n; right++){

    expandWindow();

    while(windowValid()){

        answer = updateMinimum();

        shrinkWindow();

        left++;
    }
}
```

---

# Pattern Recognition Clues

Think Minimum Window whenever you see:

### Clue 1

```text
Smallest
```

### Clue 2

```text
Minimum Length
```

### Clue 3

```text
Shortest Subarray
```

### Clue 4

```text
Find First Window Meeting Condition
```

### Clue 5

```text
Sum >= target
```

---

# Problem 9: Minimum Size Subarray Sum

## LeetCode 209

---

# Problem Explanation

Given:

```text
target = 7

nums = [2,3,1,2,4,3]
```

Find:

```text
Smallest Contiguous Subarray
whose sum >= target
```

Possible windows:

```text
2 3 1 2 = 8
length = 4

4 3 = 7
length = 2
```

Answer:

```text
2
```

---

# What Is The Interviewer Testing?

Most candidates generate:

```text
All Subarrays
```

Interviewer wants:

```text
Can you dynamically maintain
a valid window?
```

---

# Pattern Recognition Clues

### Clue 1

Subarray.

### Clue 2

Minimum length.

### Clue 3

Sum constraint.

### Clue 4

Positive numbers.

Think:

```text
Variable Sliding Window
```

---

# Brute Force

Generate every subarray.

Calculate sum.

Track minimum.

---

## Code Idea

```java
for(i)
    for(j)
```

---

## Complexity

```text
Time  : O(n²)

Space : O(1)
```

---

# Why Positive Numbers Matter

This is the hidden clue.

If all numbers are positive:

```text
Expand Window
→ Sum Always Increases

Shrink Window
→ Sum Always Decreases
```

This makes Sliding Window possible.

---

# Optimal Approach

Expand until:

```text
sum >= target
```

Now:

```text
Window Valid
```

Try shrinking.

Maybe we can find a smaller valid window.

Keep shrinking until invalid.

Repeat.

---

# Dry Run

```text
target = 7

2 3 1 2 4 3

Window:

2+3+1+2 = 8

Valid

Length = 4

----------------

Remove 2

3+1+2 = 6

Invalid

Expand

----------------

3+1+2+4 = 10

Valid

Length = 4

Shrink

1+2+4 = 7

Valid

Length = 3

Shrink

2+4 = 6

Invalid

Expand

----------------

2+4+3 = 9

Valid

Length = 3

Shrink

4+3 = 7

Valid

Length = 2
```

Answer:

```text
2
```

---

# What To Say In Interview

Since all values are positive, increasing the window increases the sum and shrinking decreases the sum.

Therefore, once the sum becomes at least target, I can safely shrink the window to find the smallest valid subarray.

---

# Optimal Java Code

```java
class Solution {

    public int minSubArrayLen(
            int target,
            int[] nums
    ) {

        int left = 0;

        int sum = 0;

        int answer =
                Integer.MAX_VALUE;

        for(int right = 0;
            right < nums.length;
            right++){

            sum += nums[right];

            while(sum >= target){

                answer =
                        Math.min(
                                answer,
                                right-left+1
                        );

                sum -= nums[left];

                left++;
            }
        }

        return answer ==
                Integer.MAX_VALUE
                ? 0
                : answer;
    }
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(1)
```

---

# Similar Problems

* Minimum Window Substring
* Smallest Distinct Window
* Smallest Subarray With Given Sum

---

# Interview Follow-Up 1

## What If Array Contains Negative Numbers?

Example:

```text
2 -5 10
```

Now:

```text
Expand Window
```

does NOT guarantee:

```text
Sum Increases
```

because:

```text
+ (-5)
```

can decrease the sum.

Sliding Window breaks.

Need:

```text
Prefix Sum
+
Deque
```

or

```text
Binary Search
```

depending on problem.

---

# Interview Follow-Up 2

## Why Doesn't Sliding Window Work For Every Subarray Problem?

Sliding Window works only when:

```text
Window Expansion
and
Window Shrinking

change the property
monotonically
```

Example:

```text
Positive Numbers
```

Good.

Example:

```text
Negative Numbers
```

Bad.

---

# Minimum Window Mindset

For Longest Window:

```java
while(windowInvalid){
    shrink();
}
```

For Minimum Window:

```java
while(windowValid){
    shrink();
}
```

This single change solves many interview questions.

---

# Bonus Problem 10: Minimum Window Substring

## LeetCode 76

(HIGHLY RECOMMENDED)

---

# Problem Explanation

Given:

```text
s = "ADOBECODEBANC"

t = "ABC"
```

Find:

```text
Smallest Window
containing all characters
of t
```

Answer:

```text
BANC
```

---

# Why This Problem Matters

This is one of the most asked Sliding Window interview questions.

Companies:

* Amazon
* Adobe
* Atlassian
* Microsoft
* Walmart
* Flipkart

love this problem.

---

# Pattern Recognition Clues

### Clue 1

Minimum Window.

### Clue 2

Need all characters.

### Clue 3

Frequency Matching.

### Clue 4

Shrink aggressively.

---

# Key Insight

Maintain:

```java
HashMap<Character,Integer>
```

Track:

```text
Matched Characters
```

Once all characters matched:

```text
Shrink
Shrink
Shrink
```

to find smallest valid window.

---

# Complexity

```text
Time  : O(n)

Space : O(128)
```

---

# Bonus Problem 11: Smallest Distinct Window

## Common Interview Variant

Given:

```text
aabcbcdbca
```

Find smallest substring containing:

```text
All Distinct Characters
```

Answer:

```text
dbca
```

---

# Pattern

Exactly same as:

```text
Minimum Window Substring
```

Only matching condition changes.

---

# Minimum Window Revision Sheet

## Longest Window

```java
while(windowInvalid){

    shrink();
}
```

Goal:

```text
Maximum Length
```

---

## Minimum Window

```java
while(windowValid){

    updateAnswer();

    shrink();
}
```

Goal:

```text
Minimum Length
```

---

# Sliding Window Master Classification

## Fixed Window

Questions:

* Maximum Average Subarray I
* Maximum Number of Vowels
* Count Good Substrings

---

## Longest Window

Questions:

* Longest Substring Without Repeating Characters
* Character Replacement
* K Distinct Characters
* Max Consecutive Ones III

---

## Minimum Window

Questions:

* Minimum Size Subarray Sum
* Minimum Window Substring
* Smallest Distinct Window

---

# Golden Interview Rule

If interviewer says:

```text
Longest
```

Think:

```text
Expand Aggressively
Shrink Carefully
```

If interviewer says:

```text
Shortest
Minimum
Smallest
```

Think:

```text
Expand Until Valid
Shrink Aggressively
```

That one distinction solves almost every Sliding Window interview problem.
