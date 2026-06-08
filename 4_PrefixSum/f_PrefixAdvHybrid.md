# Prefix Sum Pattern - Part 6

# Advanced Family

---

# Why This Family Is Different

Until now, Prefix Sum problems were:

```text id="p601"
Range Sum

Subarray Sum = K

Count Subarrays

Modulo Tricks
```

This section introduces problems where Prefix Sum combines with:

```text id="p602"
Sliding Window

Monotonic Stack

HashMap

DP
```

These are common SDE-2 interview questions.

---

# Problem 13: Number Of Subarrays With Bounded Maximum

## LeetCode 795

---

# Problem Explanation

Given:

```text id="p603"
nums

[2,1,4,3]

left = 2

right = 3
```

Count subarrays where:

```text id="p604"
Maximum Element

>= left

and

<= right
```

Answer:

```text id="p605"
3
```

Valid:

```text id="p606"
[2]

[2,1]

[3]
```

---

# What Is The Interviewer Testing?

Most candidates try:

```text id="p607"
Generate All Subarrays

Find Maximum
```

Interviewer wants:

```text id="p608"
Count Valid Windows Efficiently
```

---

# Pattern Recognition Clues

### Clue 1

Count Subarrays.

### Clue 2

Maximum Constraint.

### Clue 3

Range Constraint.

Think:

```text id="p609"
Counting Trick
```

---

# Key Insight

Count:

```text id="p610"
Subarrays

with max <= right
```

minus

```text id="p611"
Subarrays

with max < left
```

Formula:

```java id="p612"
Answer

=

count(max <= right)

-

count(max < left)
```

---

# Why Does This Work?

Suppose:

```text id="p613"
max <= 3
```

contains:

```text id="p614"
1

2

3
```

Suppose:

```text id="p615"
max < 2
```

contains:

```text id="p616"
1
```

Subtract:

```text id="p617"
Leaves

2

3
```

Exactly desired range.

---

# Helper Function

Count:

```text id="p618"
Subarrays

whose max <= bound
```

---

# Optimal Approach

If:

```java id="p619"
nums[i] <= bound
```

extend streak.

Else:

```java id="p620"
reset streak
```

---

# Dry Run

```text id="p621"
nums

2 1 4 3
```

Bound:

```text id="p622"
3
```

Valid streak:

```text id="p623"
2

2 1
```

Count grows.

At:

```text id="p624"
4
```

reset.

---

# What To Say In Interview

Instead of directly counting subarrays whose maximum lies inside the range, I count subarrays with maximum at most right and subtract those whose maximum is less than left.

---

# Optimal Java Code

```java id="p625"
class Solution {

    public int numSubarrayBoundedMax(
            int[] nums,
            int left,
            int right
    ) {

        return count(nums,right)
                -
               count(nums,left-1);
    }

    private int count(
            int[] nums,
            int bound
    ){

        int answer = 0;
        int streak = 0;

        for(int num : nums){

            if(num <= bound){

                streak++;

            }else{

                streak = 0;
            }

            answer += streak;
        }

        return answer;
    }
}
```

---

# Complexity

```text id="p626"
Time : O(n)

Space : O(1)
```

---

# Similar Problems

* Binary Subarrays With Sum
* Nice Subarrays
* Exactly K Distinct

---

# Problem 14: Longest Valid Parentheses

## LeetCode 32

---

# Problem Explanation

Given:

```text id="p627"
(()())
```

Find:

```text id="p628"
Longest Valid
Parentheses Substring
```

Answer:

```text id="p629"
6
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="p630"
Balance Tracking
```

instead of treating it as a string problem?

---

# Pattern Recognition Clues

### Clue 1

Parentheses.

### Clue 2

Longest Valid.

### Clue 3

Balance.

Think:

```text id="p631"
Stack
```

not Prefix Sum.

---

# Prefix Sum Interpretation

Treat:

```text id="p632"
(
=
+1
```

Treat:

```text id="p633"
)
=
-1
```

Example:

```text id="p634"
(()

1 2 1
```

Balance acts like Prefix Sum.

---

# Common Solutions

### Approach 1

Stack

### Approach 2

DP

### Approach 3

Two-Pass Counter

---

# Most Interview-Friendly Solution

Stack.

Store:

```java id="p635"
indices
```

not characters.

---

# Dry Run

```text id="p636"
()()
```

Stack:

```text id="p637"
-1
```

Push.

Match.

Compute length.

---

# What To Say In Interview

A valid substring requires balanced opening and closing brackets. Using a stack of indices allows me to quickly compute the length of valid ranges.

---

# Optimal Java Code

```java id="p638"
class Solution {

    public int longestValidParentheses(
            String s
    ) {

        Stack<Integer> stack =
                new Stack<>();

        stack.push(-1);

        int answer = 0;

        for(int i = 0;
            i < s.length();
            i++){

            if(s.charAt(i) == '('){

                stack.push(i);

            }else{

                stack.pop();

                if(stack.isEmpty()){

                    stack.push(i);

                }else{

                    answer =
                            Math.max(
                                    answer,
                                    i-stack.peek()
                            );
                }
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text id="p639"
Time : O(n)

Space : O(n)
```

---

# Interview Insight

Although usually classified under Stack, the balance idea is actually:

```text id="p640"
Prefix Sum

(+1/-1)
```

in disguise.

---

# Problem 15: Prefix And Suffix Search

## LeetCode 745

---

# Problem Explanation

Design:

```text id="p641"
WordFilter
```

Support:

```java id="p642"
f(prefix,suffix)
```

Return:

```text id="p643"
Highest Index Word
```

matching both.

---

# Example

Words:

```text id="p644"
apple

apply

ape
```

Query:

```text id="p645"
prefix = ap

suffix = le
```

Answer:

```text id="p646"
apple
```

---

# What Is The Interviewer Testing?

Can you preprocess aggressively?

---

# Pattern Recognition Clues

### Clue 1

Many Queries.

### Clue 2

Static Dataset.

### Clue 3

Prefix.

### Clue 4

Suffix.

Think:

```text id="p647"
Precomputation
```

---

# Brute Force

For every query:

Check every word.

---

## Complexity

```text id="p648"
O(n * wordLength)
```

per query.

---

# Optimal Idea

Generate:

```text id="p649"
prefix#suffix
```

combinations.

Store:

```java id="p650"
Map<String,Integer>
```

---

# Example

Word:

```text id="p651"
apple
```

Store:

```text id="p652"
a#e

ap#le

app#ple

...
```

Map points to:

```text id="p653"
highest index
```

---

# What To Say In Interview

Since queries are frequent, I trade memory for speed by precomputing all prefix-suffix combinations and storing the highest matching index.

---

# Optimal Java Code

```java id="p654"
class WordFilter {

    Map<String,Integer> map =
            new HashMap<>();

    public WordFilter(
            String[] words
    ) {

        for(int index = 0;
            index < words.length;
            index++){

            String word =
                    words[index];

            for(int i = 0;
                i <= word.length();
                i++){

                String prefix =
                        word.substring(0,i);

                for(int j = 0;
                    j <= word.length();
                    j++){

                    String suffix =
                            word.substring(j);

                    map.put(
                        prefix + "#" + suffix,
                        index
                    );
                }
            }
        }
    }

    public int f(
            String prefix,
            String suffix
    ) {

        return map.getOrDefault(
                prefix + "#" + suffix,
                -1
        );
    }
}
```

---

# Complexity

```text id="p655"
Build :
O(n * L²)

Query :
O(1)
```

---

# Similar Problems

* Trie
* Search Suggestions
* Autocomplete

---

# Advanced Family Revision Sheet

## Prefix Sum Difference

Used In:

```java id="p656"
prefixSum - k
```

Problems:

* Subarray Sum Equals K

---

## Prefix Sum Modulo

Used In:

```java id="p657"
prefixSum % k
```

Problems:

* Continuous Subarray Sum

---

## Prefix Sum Parity

Used In:

```java id="p658"
prefixSum % 2
```

Problems:

* Odd Sum Subarrays

---

## Exactly K Trick

Used In:

```java id="p659"
AtMost(K)

-

AtMost(K-1)
```

Problems:

* Nice Subarrays
* Binary Subarrays With Sum

---

## Range Counting Trick

Used In:

```java id="p660"
Count(bound2)

-

Count(bound1)
```

Problems:

* Number Of Subarrays With Bounded Maximum

---

# Complete Prefix Sum Handbook

## Basic Family

* Range Sum Query Immutable
* Find Pivot Index

---

## Prefix Sum + HashMap

* Subarray Sum Equals K
* Maximum Size Subarray Sum Equals K

---

## Advanced HashMap

* Continuous Subarray Sum
* Number Of Subarrays With Odd Sum

---

## Counting Family

* Binary Subarrays With Sum
* Count Number Of Nice Subarrays

---

## Hybrid Family

* Minimum Size Subarray Sum
* Maximum Average Subarray I
* Longest Subarray Of 1's After Deleting One Element
* Longest Subarray With Sum At Most K

---

## Advanced Family

* Number Of Subarrays With Bounded Maximum
* Longest Valid Parentheses
* Prefix And Suffix Search

---

# Final Prefix Sum Interview Cheat Sheet

If interviewer says:

```text id="p661"
Range Sum
```

Think:

```java id="p662"
prefix[r]
-
prefix[l-1]
```

---

If interviewer says:

```text id="p663"
Subarray Sum = K
```

Think:

```java id="p664"
prefixSum - k
```

---

If interviewer says:

```text id="p665"
Multiple Of K
```

Think:

```java id="p666"
prefixSum % k
```

---

If interviewer says:

```text id="p667"
Odd / Even
```

Think:

```java id="p668"
prefixSum % 2
```

---

If interviewer says:

```text id="p669"
Exactly K
```

Think:

```java id="p670"
AtMost(K)
-
AtMost(K-1)
```

Master these five transformations and you'll solve the majority of Prefix Sum interview problems.
