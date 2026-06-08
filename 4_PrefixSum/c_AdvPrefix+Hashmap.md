# Prefix Sum Pattern - Part 3

# Advanced Prefix Sum + HashMap Family

---

# Why This Family Matters

Most candidates stop after learning:

```java
prefixSum - k
```

for:

```text
Subarray Sum Equals K
```

Strong interview candidates learn:

```text
Prefix Sum Modulo

Prefix Sum States

Prefix Sum Parity
```

These ideas solve many advanced subarray problems.

---

# Mental Model Upgrade

Instead of storing:

```java
prefixSum
```

Sometimes store:

```java
prefixSum % k
```

or:

```java
prefixSum % 2
```

or:

```java
prefixSum State
```

This is the core idea behind this section.

---

# Problem 5: Continuous Subarray Sum

## LeetCode 523

---

# Problem Explanation

Given:

```text
nums = [23,2,4,6,7]

k = 6
```

Determine if there exists a:

```text
Continuous Subarray
Length >= 2
```

whose sum is a multiple of:

```text
k
```

Example:

```text
2 + 4

=
6
```

Answer:

```text
true
```

---

# What Is The Interviewer Testing?

Most candidates immediately think:

```text
Subarray Sum = K
```

Wrong.

This question asks:

```text
Subarray Sum

=
n × k
```

where:

```text
n can be anything
```

---

# Pattern Recognition Clues

### Clue 1

Subarray Sum.

### Clue 2

Multiple Of K.

### Clue 3

Length >= 2.

### Clue 4

Need existence only.

Think:

```text
Prefix Sum Modulo
```

---

# Brute Force

Generate every subarray.

Calculate sum.

Check:

```java
sum % k == 0
```

---

## Complexity

```text
Time : O(n²)

Space : O(1)
```

---

# Prefix Sum Visualization

Suppose:

```text
nums

23 2 4 6 7
```

Prefix:

```text
23 25 29 35 42
```

Modulo 6:

```text
5 1 5 5 0
```

Notice:

```text
5 appears twice
```

---

# The Magic Observation

Suppose:

```text
prefix1 % k = 5

prefix2 % k = 5
```

Then:

```text
(prefix2 - prefix1)

% k

=

0
```

Meaning:

```text
Subarray Sum
between them
is divisible by k
```

---

# Mathematical Proof

If:

```text
prefixA = 17

prefixB = 35
```

Both:

```text
% 6

=

5
```

Then:

```text
35 - 17

=

18
```

and:

```text
18 % 6

=

0
```

Valid subarray.

---

# Optimal Approach

Store:

```java
prefixSum % k
```

inside HashMap.

Map stores:

```java
remainder
→
first occurrence index
```

If same remainder appears again:

Valid subarray exists.

---

# Dry Run

```text
nums

23 2 4 6 7

k = 6
```

Map:

```text
0 -> -1
```

---

Prefix:

```text
23

mod = 5
```

Store:

```text
5 -> 0
```

---

Prefix:

```text
25

mod = 1
```

Store:

```text
1 -> 1
```

---

Prefix:

```text
29

mod = 5
```

Already exists.

Distance:

```text
2 - 0

=
2
```

Valid.

Answer:

```text
true
```

---

# What To Say In Interview

A subarray sum is divisible by k when two prefix sums have the same remainder after division by k.

I'll store the earliest occurrence of each remainder and check if the distance between occurrences is at least 2.

---

# Optimal Java Code

```java
class Solution {

    public boolean checkSubarraySum(
            int[] nums,
            int k
    ) {

        Map<Integer,Integer> map =
                new HashMap<>();

        map.put(0,-1);

        int prefix = 0;

        for(int i = 0;
            i < nums.length;
            i++){

            prefix += nums[i];

            int mod = prefix % k;

            if(map.containsKey(mod)){

                if(i - map.get(mod) >= 2){
                    return true;
                }

            }else{

                map.put(mod,i);
            }
        }

        return false;
    }
}
```

---

# Complexity

```text
Time : O(n)

Space : O(k)
```

---

# Similar Problems

* Subarray Sums Divisible By K
* Check Divisibility Problems
* Prefix Modulo Questions

---

# Interview Follow-Up

### Why Store First Occurrence?

Because:

```text
Longest Distance
```

gives highest chance of:

```text
Length >= 2
```

---

# Problem 6: Number Of Subarrays With Odd Sum

## LeetCode 1524

---

# Problem Explanation

Given:

```text
arr

[1,3,5]
```

Count:

```text
Subarrays
with odd sum
```

Subarrays:

```text
[1]

[3]

[5]

[1,3,5]
```

Answer:

```text
4
```

---

# What Is The Interviewer Testing?

Can you transform:

```text
Actual Sum
```

into:

```text
Parity
```

?

---

# Pattern Recognition Clues

### Clue 1

Count subarrays.

### Clue 2

Odd/Even property.

### Clue 3

Need count.

Think:

```text
Prefix Sum Parity
```

---

# Brute Force

Generate every subarray.

Check:

```java
sum % 2 == 1
```

---

## Complexity

```text
Time : O(n²)

Space : O(1)
```

---

# Prefix Sum Visualization

Array:

```text
1 3 5
```

Prefix:

```text
1 4 9
```

Parity:

```text
Odd

Even

Odd
```

Represent:

```text
1

0

1
```

---

# Key Observation

Current Prefix:

```text
Odd
```

Need:

```text
Previous Even
```

because:

```text
Odd - Even

=

Odd
```

---

Current Prefix:

```text
Even
```

Need:

```text
Previous Odd
```

because:

```text
Even - Odd

=

Odd
```

---

# Parity Table

```text
Odd - Even = Odd

Even - Odd = Odd

Odd - Odd = Even

Even - Even = Even
```

Memorize this.

---

# Optimal Approach

Maintain:

```java
oddCount
evenCount
```

Count previous prefix states.

---

# Dry Run

```text
arr

1 3 5
```

Initially:

```text
evenCount = 1

oddCount = 0
```

---

Prefix:

```text
1

Odd
```

Need:

```text
Previous Even
```

Count:

```text
1
```

Answer:

```text
1
```

---

Prefix:

```text
4

Even
```

Need:

```text
Previous Odd
```

Count:

```text
1
```

Answer:

```text
2
```

---

Prefix:

```text
9

Odd
```

Need:

```text
Previous Even
```

Count:

```text
2
```

Answer:

```text
4
```

---

# What To Say In Interview

Instead of tracking exact prefix sums, I only care about whether each prefix sum is odd or even.

An odd subarray is formed whenever the current prefix parity differs from a previous prefix parity.

---

# Optimal Java Code

```java
class Solution {

    public int numOfSubarrays(
            int[] arr
    ) {

        long answer = 0;

        int even = 1;
        int odd = 0;

        int prefix = 0;

        int MOD =
                1_000_000_007;

        for(int num : arr){

            prefix += num;

            if(prefix % 2 == 0){

                answer += odd;

                even++;

            }else{

                answer += even;

                odd++;
            }
        }

        return (int)(answer % MOD);
    }
}
```

---

# Complexity

```text
Time : O(n)

Space : O(1)
```

---

# Similar Problems

* Count Even Sum Subarrays
* Binary Subarrays With Sum
* Nice Subarrays

---

# Prefix Sum State Thinking

Sometimes store:

```java
prefixSum
```

Sometimes store:

```java
prefixSum % k
```

Sometimes store:

```java
prefixSum % 2
```

Interviewers love testing whether you can identify the correct state.

---

# Advanced Prefix Sum Revision Sheet

## Subarray Sum Equals K

Store:

```java
prefixSum
→
frequency
```

Formula:

```java
prefixSum - k
```

---

## Continuous Subarray Sum

Store:

```java
prefixSum % k
```

Formula:

```text
Same Remainder
=
Divisible Subarray
```

---

## Odd Sum Subarrays

Store:

```java
prefixSum % 2
```

Formula:

```text
Different Parity
=
Odd Sum
```

---

# Golden Interview Rules

If interviewer says:

```text
Subarray Sum = K
```

Think:

```java
prefixSum - k
```

---

If interviewer says:

```text
Multiple Of K
```

Think:

```java
prefixSum % k
```

---

If interviewer says:

```text
Odd/Even Sum
```

Think:

```java
prefixSum % 2
```

These three tricks solve most advanced Prefix Sum interview problems.
