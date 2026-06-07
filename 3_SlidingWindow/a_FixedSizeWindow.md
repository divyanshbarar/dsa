# Sliding Window Pattern - Part 1

# Fixed Size Window Family

---

# What Is A Fixed Size Window?

The window size never changes.

Example:

```text
Window Size = K = 3

1 2 3 4 5 6

[1 2 3]
  [2 3 4]
    [3 4 5]
      [4 5 6]
```

We continuously:

```text
Remove Left Element
+
Add Right Element
```

instead of recalculating the entire window.

---

# Master Template

```java
int windowSum = 0;

/* Build first window */

for(int i = 0; i < k; i++){
    windowSum += nums[i];
}

int answer = windowSum;

/* Slide */

for(int right = k; right < nums.length; right++){

    windowSum += nums[right];

    windowSum -= nums[right - k];

    answer = Math.max(answer, windowSum);
}
```

---

# Pattern Recognition Clues

Think Fixed Sliding Window whenever you see:

### Clue 1

```text
Subarray of size K
```

### Clue 2

```text
Substring of length K
```

### Clue 3

```text
Exactly K elements
```

### Clue 4

```text
Maximum/Minimum/Average
within fixed range
```

---

# Problem 1: Maximum Average Subarray I

## LeetCode 643

---

# Problem Explanation

Given an array and integer k.

Find:

```text
Maximum Average
of any subarray
of length exactly k
```

Example:

```text
nums = [1,12,-5,-6,50,3]

k = 4
```

Possible windows:

```text
[1,12,-5,-6]
avg = 0.5

[12,-5,-6,50]
avg = 12.75

[-5,-6,50,3]
avg = 10.5
```

Answer:

```text
12.75
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text
Length exactly K
```

means:

```text
Fixed Sliding Window
```

instead of generating all subarrays.

---

# Pattern Recognition Clues

### Clue 1

Subarray.

### Clue 2

Exactly K elements.

### Clue 3

Need maximum.

Think:

```text
Fixed Window
```

---

# Brute Force

Generate every subarray of size K.

Calculate sum every time.

---

## Code Idea

```java
for every window
    calculate sum again
```

---

## Complexity

```text
Time  : O(n*k)

Space : O(1)
```

---

# Why Can We Do Better?

Notice:

```text
Window 1

1 12 -5 -6

Window 2

12 -5 -6 50
```

Most elements overlap.

Only:

```text
Remove 1
Add 50
```

changes.

---

# Optimal Approach

Maintain:

```java
windowSum
```

When window moves:

```java
windowSum += incomingElement;

windowSum -= outgoingElement;
```

---

# Dry Run

```text
nums = [1,12,-5,-6,50,3]

k = 4

Window Sum

1+12-5-6 = 2

----------------

Add 50
Remove 1

2 + 50 - 1 = 51

----------------

Add 3
Remove 12

51 + 3 - 12 = 42
```

Maximum:

```text
51
```

Average:

```text
51 / 4 = 12.75
```

---

# What To Say In Interview

Since the window size is fixed, recalculating the sum for every window is wasteful.

I'll maintain the current window sum and update it by removing the outgoing element and adding the incoming element.

This reduces complexity from O(n*k) to O(n).

---

# Optimal Java Code

```java
class Solution {

    public double findMaxAverage(
            int[] nums,
            int k
    ) {

        long windowSum = 0;

        for(int i = 0; i < k; i++){
            windowSum += nums[i];
        }

        long maxSum = windowSum;

        for(int right = k;
            right < nums.length;
            right++){

            windowSum += nums[right];

            windowSum -= nums[right - k];

            maxSum =
                    Math.max(
                            maxSum,
                            windowSum
                    );
        }

        return (double) maxSum / k;
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

* Maximum Number of Vowels
* Count Good Substrings
* Sliding Window Maximum

---

# Problem 2: Maximum Number Of Vowels In A Substring Of Given Length

## LeetCode 1456

---

# Problem Explanation

Given:

```text
String s

Window Length = k
```

Find:

```text
Maximum vowels
inside any substring
of length k
```

Example:

```text
s = "abciiidef"

k = 3
```

Windows:

```text
abc -> 1 vowel

bci -> 1 vowel

cii -> 2 vowels

iii -> 3 vowels

iid -> 2 vowels
```

Answer:

```text
3
```

---

# What Is The Interviewer Testing?

Can you convert:

```text
Maximum Average
```

into:

```text
Maximum Count
```

using same template?

---

# Pattern Recognition Clues

### Clue 1

Substring.

### Clue 2

Length exactly K.

### Clue 3

Need maximum count.

Think:

```text
Fixed Window
```

---

# Brute Force

For every substring:

Count vowels again.

---

## Complexity

```text
Time  : O(n*k)

Space : O(1)
```

---

# Optimal Approach

Maintain:

```java
vowelCount
```

For every slide:

```text
Remove left character

Add right character
```

Update count.

---

# Dry Run

```text
abciiidef

k = 3

abc

vowels = 1

----------------

bci

remove a

add i

vowels = 1

----------------

cii

remove b

add i

vowels = 2

----------------

iii

vowels = 3
```

---

# What To Say In Interview

The window size is fixed, so I only need to track how the vowel count changes when a character enters or leaves the window.

---

# Optimal Java Code

```java
class Solution {

    public int maxVowels(
            String s,
            int k
    ) {

        int vowels = 0;

        for(int i = 0; i < k; i++){

            if(isVowel(s.charAt(i))){
                vowels++;
            }
        }

        int answer = vowels;

        for(int right = k;
            right < s.length();
            right++){

            if(isVowel(s.charAt(right))){
                vowels++;
            }

            if(isVowel(
                    s.charAt(right - k)
            )){
                vowels--;
            }

            answer =
                    Math.max(
                            answer,
                            vowels
                    );
        }

        return answer;
    }

    private boolean isVowel(char c){

        return c == 'a' ||
               c == 'e' ||
               c == 'i' ||
               c == 'o' ||
               c == 'u';
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

* Maximum Average Subarray
* Count Good Substrings

---

# Problem 3: Count Good Substrings

## LeetCode 1876

---

# Problem Explanation

A substring is good if:

```text
Length = 3

All characters unique
```

Example:

```text
xyzzaz
```

Good:

```text
xyz
```

Bad:

```text
yzz
```

Answer:

```text
1
```

---

# What Is The Interviewer Testing?

Can you use:

```text
Fixed Window
+
Frequency Tracking
```

together?

---

# Pattern Recognition Clues

### Clue 1

Substring.

### Clue 2

Length exactly 3.

### Clue 3

Need uniqueness.

Think:

```text
Fixed Window
+
Frequency Array
```

---

# Brute Force

Generate every substring of length 3.

Check uniqueness.

---

## Complexity

```text
Time  : O(n)

Space : O(1)
```

(Because size is only 3)

But interviewer expects sliding window thinking.

---

# Optimal Approach

Maintain frequency map of current window.

A window is valid if:

```text
distinctCharacters == 3
```

---

# Dry Run

```text
xyzzaz

xyz

distinct = 3

count = 1

----------------

yzz

distinct = 2

count unchanged
```

---

# What To Say In Interview

Since the window size remains fixed at 3, I can maintain character frequencies while sliding and quickly determine whether all characters are unique.

---

# Optimal Java Code

```java
class Solution {

    public int countGoodSubstrings(
            String s
    ) {

        int[] freq = new int[26];

        int distinct = 0;

        int left = 0;

        int answer = 0;

        for(int right = 0;
            right < s.length();
            right++){

            if(freq[
                s.charAt(right)-'a'
            ]++ == 0){

                distinct++;
            }

            if(right - left + 1 > 3){

                if(--freq[
                    s.charAt(left)-'a'
                ] == 0){

                    distinct--;
                }

                left++;
            }

            if(right - left + 1 == 3
                    &&
                    distinct == 3){

                answer++;
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(26) ≈ O(1)
```

---

# Fixed Window Revision Sheet

## Master Formula

```java
window += incoming;

window -= outgoing;
```

---

## Fixed Window Clues

Look for:

```text
Exactly K

Size K

Length K

K Consecutive Elements
```

---

## Problems In This Family

### Pure Numeric Window

* Maximum Average Subarray I

### Counting Window

* Maximum Number Of Vowels

### Frequency Window

* Count Good Substrings

---

# Golden Interview Rule

If interviewer says:

```text
Subarray of Size K

Substring of Length K

Exactly K Elements
```

DO NOT think:

```text
Two Pointers
```

Think:

```text
Fixed Sliding Window
```

because the window size never changes.
