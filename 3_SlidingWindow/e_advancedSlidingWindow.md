# Sliding Window Pattern - Part 5

# Advanced Sliding Window Family

---

# Why These Problems Are Different

Until now, Sliding Window problems were:

```text id="1v6yye"
Expand
Shrink
Track Count
Track Sum
Track Frequency
```

These 4 problems introduce new ideas:

```text id="5o9a7h"
Deque

Prefix Sum Trick

Unique Window + Running Sum

Word Frequency Matching
```

Most candidates know Sliding Window.

Few candidates know these advanced variants.

---

# Problem 14: Sliding Window Maximum

## LeetCode 239

---

# Problem Explanation

Given:

```text id="ccy13m"
nums = [1,3,-1,-3,5,3,6,7]

k = 3
```

Windows:

```text id="98q6iy"
[1,3,-1]  -> 3

[3,-1,-3] -> 3

[-1,-3,5] -> 5

[-3,5,3] -> 5

[5,3,6] -> 6

[3,6,7] -> 7
```

Output:

```text id="kggfkj"
[3,3,5,5,6,7]
```

---

# What Is The Interviewer Testing?

Most candidates think:

```text id="gmjnfh"
Sliding Window
```

and immediately calculate maximum for every window.

Interviewer wants:

```text id="lb9dzg"
Can you maintain maximum efficiently?
```

---

# Pattern Recognition Clues

### Clue 1

Window size K.

### Clue 2

Need maximum for every window.

### Clue 3

Repeated max queries.

Think:

```text id="f7uc4w"
Monotonic Deque
```

---

# Brute Force

For every window:

Scan all K elements.

---

## Complexity

```text id="e8ys4t"
Time :
O(n*k)
```

---

# Why Can We Do Better?

When:

```text id="if9l5i"
5 enters
```

and

```text id="5tfz9l"
3 already exists
```

then:

```text id="zc2kzb"
3 can never become maximum
```

Remove it.

---

# Monotonic Deque

Maintain:

```text id="lj0mga"
Elements in decreasing order
```

Example:

```text id="u9yyu0"
5 3 2
```

Front always stores:

```text id="jlwmws"
Maximum
```

---

# Dry Run

```text id="h55l7k"
1 3 -1

Deque:

3 -1

Maximum = 3
```

---

# What To Say In Interview

Instead of recomputing the maximum for every window, I'll maintain a monotonic decreasing deque.

The front always contains the maximum element for the current window.

---

# Optimal Java Code

```java
class Solution {

    public int[] maxSlidingWindow(
            int[] nums,
            int k
    ) {

        Deque<Integer> dq =
                new ArrayDeque<>();

        int[] answer =
                new int[
                    nums.length-k+1
                ];

        int index = 0;

        for(int right = 0;
            right < nums.length;
            right++){

            while(!dq.isEmpty()
                &&
                nums[dq.peekLast()]
                <= nums[right]){

                dq.pollLast();
            }

            dq.offerLast(right);

            if(dq.peekFirst()
                    <= right-k){

                dq.pollFirst();
            }

            if(right >= k-1){

                answer[index++] =
                        nums[
                            dq.peekFirst()
                        ];
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text id="1xh4gr"
Time  : O(n)

Space : O(k)
```

---

# Similar Problems

* Daily Temperatures
* Largest Rectangle Histogram
* Monotonic Queue Problems

---

# Problem 15: Count Number Of Nice Subarrays

## LeetCode 1248

---

# Problem Explanation

A nice subarray contains:

```text id="j7lq0g"
Exactly K Odd Numbers
```

Example:

```text id="5rk6g8"
nums = [1,1,2,1,1]

k = 3
```

Nice Subarrays:

```text id="c1oq5m"
[1,1,2,1]

[1,2,1,1]
```

Answer:

```text id="9s68mi"
2
```

---

# What Is The Interviewer Testing?

Can you convert:

```text id="gk23m4"
Exactly K
```

into:

```text id="t3y7dt"
At Most K
```

?

This trick appears everywhere.

---

# Key Formula

```text id="jlwm55"
Exactly(K)
=
AtMost(K)
-
AtMost(K-1)
```

Memorize this.

---

# Why Does It Work?

Example:

```text id="l91h3q"
AtMost(3)
```

contains:

```text id="hj53n7"
0 odd
1 odd
2 odd
3 odd
```

Subtract:

```text id="00xw9m"
AtMost(2)
```

Leaves:

```text id="m6r7hj"
Exactly 3 odd
```

---

# Helper Function

Count:

```text id="owjx1z"
At Most K Odd Numbers
```

using Sliding Window.

---

# What To Say In Interview

Instead of directly counting windows with exactly K odd numbers, I'll count windows with at most K odds and subtract windows with at most K-1 odds.

---

# Optimal Java Code

```java
class Solution {

    public int numberOfSubarrays(
            int[] nums,
            int k
    ) {

        return atMost(nums,k)
                -
               atMost(nums,k-1);
    }

    private int atMost(
            int[] nums,
            int k
    ){

        int left = 0;

        int answer = 0;

        for(int right = 0;
            right < nums.length;
            right++){

            if(nums[right] % 2 == 1){
                k--;
            }

            while(k < 0){

                if(nums[left] % 2 == 1){
                    k++;
                }

                left++;
            }

            answer +=
                    right-left+1;
        }

        return answer;
    }
}
```

---

# Complexity

```text id="b2y4yl"
Time  : O(n)

Space : O(1)
```

---

# Interview Gold Nugget

This trick also solves:

```text id="5x1qk9"
Binary Subarrays With Sum

Subarrays With K Distinct Integers
```

---

# Problem 16: Maximum Erasure Value

## LeetCode 1695

---

# Problem Explanation

Given:

```text id="fhig63"
[4,2,4,5,6]
```

Choose a subarray containing:

```text id="eh6rv7"
Unique Elements Only
```

Maximize:

```text id="zkv6gu"
Sum
```

Answer:

```text id="k8mkhz"
17

(2+4+5+6)
```

---

# What Is The Interviewer Testing?

Can you combine:

```text id="uhr9c8"
Longest Substring Without Repeating
```

with:

```text id="7l4hn6"
Running Sum
```

?

---

# Pattern Recognition Clues

### Clue 1

Unique elements.

### Clue 2

Need maximum sum.

### Clue 3

Variable window.

Think:

```text id="u5o5yw"
HashSet
+
Window Sum
```

---

# Optimal Approach

Maintain:

```java
HashSet<Integer>
```

and:

```java
currentSum
```

Shrink when duplicate appears.

---

# What To Say In Interview

This is essentially Longest Substring Without Repeating Characters, but instead of maximizing length, I maximize window sum.

---

# Optimal Java Code

```java
class Solution {

    public int maximumUniqueSubarray(
            int[] nums
    ) {

        Set<Integer> set =
                new HashSet<>();

        int left = 0;

        int sum = 0;

        int answer = 0;

        for(int right = 0;
            right < nums.length;
            right++){

            while(set.contains(
                    nums[right]
            )){

                set.remove(
                        nums[left]
                );

                sum -= nums[left];

                left++;
            }

            set.add(nums[right]);

            sum += nums[right];

            answer =
                    Math.max(
                            answer,
                            sum
                    );
        }

        return answer;
    }
}
```

---

# Complexity

```text id="2i7uy3"
Time  : O(n)

Space : O(n)
```

---

# Problem 17: Substring With Concatenation Of All Words

## LeetCode 30

---

# Problem Explanation

Given:

```text id="ij0v5l"
s = "barfoothefoobarman"

words =
["foo","bar"]
```

Find all starting indices where:

```text id="wxv2a8"
foo + bar

or

bar + foo
```

appears.

Answer:

```text id="34pbfb"
[0,9]
```

---

# Why Is This Hard?

Because:

```text id="u72kn7"
Frequency Matching
```

must happen at:

```text id="zx2yo7"
Word Level
```

not:

```text id="ur81q5"
Character Level
```

---

# What Is The Interviewer Testing?

Can you extend:

```text id="4m6zku"
Anagram Logic
```

to:

```text id="ksk0hf"
Words Instead Of Characters
```

?

---

# Key Insight

Build:

```java
Map<String,Integer>
```

for target words.

Slide using:

```text id="j1ixtr"
wordLength
```

chunks.

---

# Brute Force

Check every index.

Build substring.

Compare frequencies.

---

## Complexity

```text id="fyb1gf"
Very High

O(n*m)
```

---

# Optimal Approach

Use:

```java
HashMap<String,Integer>
```

for:

```text id="fw6k6g"
Expected Words
```

and:

```java
HashMap<String,Integer>
```

for:

```text id="t7u6jq"
Current Window
```

---

# What To Say In Interview

This is an anagram problem at the word level.

Instead of comparing characters, I compare frequencies of complete words inside a sliding window.

---

# Complexity

```text id="v7flur"
Time :
O(n * wordLength)

Space :
O(numberOfWords)
```

---

# Advanced Sliding Window Revision Sheet

## Monotonic Deque

Used In:

* Sliding Window Maximum
* Daily Temperatures
* Histogram Problems

---

## Exactly K Trick

```java
Exactly(K)
=
AtMost(K)
-
AtMost(K-1)
```

Used In:

* Nice Subarrays
* K Distinct Integers
* Binary Subarrays With Sum

---

## Unique Window + Sum

Used In:

* Maximum Erasure Value

---

## Word Frequency Matching

Used In:

* Substring With Concatenation Of All Words

---

# Complete Sliding Window Handbook

## Fixed Window

* Maximum Average Subarray I
* Maximum Number Of Vowels
* Count Good Substrings

---

## Longest Window

* Longest Substring Without Repeating Characters
* Character Replacement
* K Distinct Characters
* Max Consecutive Ones III

---

## Minimum Window

* Minimum Size Subarray Sum
* Minimum Window Substring

---

## Frequency Matching

* Permutation In String
* Find All Anagrams In A String

---

## Advanced

* Sliding Window Maximum
* Count Number Of Nice Subarrays
* Maximum Erasure Value
* Substring With Concatenation Of All Words

---

# Golden Interview Rules

Before coding any Sliding Window problem ask:

```text
1. Fixed or Variable Window?

2. Longest or Shortest?

3. Frequency Matching Needed?

4. Exactly K or At Most K?

5. Need Monotonic Deque?

6. Need Running Sum?
```

These six questions identify almost every Sliding Window pattern asked in interviews.
