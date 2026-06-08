# Prefix Sum Pattern - Part 2

# Prefix Sum + HashMap Family

---

# Why This Family Is Important

This is the most important Prefix Sum pattern for interviews.

Most candidates understand:

```java
prefix[i]
=
prefix[i-1] + nums[i];
```

Very few understand:

```java
prefixSum - k
```

Once you understand this concept, you'll solve:

* Subarray Sum Equals K
* Maximum Size Subarray Sum Equals K
* Continuous Subarray Sum
* Binary Subarrays With Sum
* Number Of Subarrays With Odd Sum

and many more.

---

# The Most Important Prefix Sum Trick

Suppose:

```text
nums

1 2 3 4
```

Build Prefix:

```text
Index

0 1 2 3

Prefix

1 3 6 10
```

Now imagine:

```text
Current Prefix Sum = 10

Target = 7
```

Ask:

```text
Did we previously see

10 - 7

=
3 ?
```

Yes.

At index:

```text
1
```

Meaning:

```text
Prefix(3)
-
Prefix(1)

=
10 - 3

=
7
```

Subarray:

```text
3 + 4
=
7
```

Found.

---

# Golden Formula

Whenever interviewer says:

```text
Subarray Sum = K
```

Think:

```java
prefixSum - k
```

not:

```java
nested loops
```

---

# Problem 3: Subarray Sum Equals K

## LeetCode 560

---

# Problem Explanation

Given:

```text
nums

[1,1,1]

k = 2
```

Find:

```text
Number Of Subarrays
whose sum equals k
```

Answer:

```text
2
```

Because:

```text
[1,1]

[1,1]
```

---

# What Is The Interviewer Testing?

Most candidates generate:

```text
All Subarrays
```

Interviewer wants:

```text
Can you use Prefix Sum
to avoid recomputing sums?
```

---

# Pattern Recognition Clues

### Clue 1

Subarray Sum.

### Clue 2

Target K.

### Clue 3

Count Total Subarrays.

### Clue 4

Negative Numbers May Exist.

Think:

```text
Prefix Sum + HashMap
```

---

# Brute Force

Generate every subarray.

Compute sum.

---

## Complexity

```text
Time : O(n²)

Space : O(1)
```

---

# Prefix Sum Visualization

```text
nums

1 1 1

Prefix

1 2 3
```

Current Prefix:

```text
3
```

Need:

```text
3 - 2

=
1
```

Have we seen:

```text
1
```

before?

Yes.

Count increases.

---

# Key Observation

Suppose:

```java
currentPrefix = 10

k = 7
```

Need:

```java
previousPrefix = 3
```

because:

```java
10 - 3 = 7
```

---

# Optimal Approach

Store:

```java
prefixSum
```

inside:

```java
HashMap
```

Map stores:

```java
prefixSum -> frequency
```

Whenever:

```java
prefixSum - k
```

exists,

we found valid subarrays.

---

# Dry Run

```text
nums

1 1 1

k = 2
```

Initial:

```text
map

0 -> 1
```

---

Element:

```text
1
```

Prefix:

```text
1
```

Need:

```text
-1
```

Not found.

Store:

```text
1 -> 1
```

---

Element:

```text
1
```

Prefix:

```text
2
```

Need:

```text
0
```

Found:

```text
1 occurrence
```

Answer:

```text
1
```

---

Element:

```text
1
```

Prefix:

```text
3
```

Need:

```text
1
```

Found.

Answer:

```text
2
```

---

# What To Say In Interview

For every index, I compute the current prefix sum.

If a previous prefix sum equal to:

```java
currentPrefix - k
```

exists, then the subarray between those two positions sums to k.

I'll store prefix sums and their frequencies in a HashMap.

---

# Optimal Java Code

```java
class Solution {

    public int subarraySum(
            int[] nums,
            int k
    ) {

        Map<Integer,Integer> map =
                new HashMap<>();

        map.put(0,1);

        int prefix = 0;
        int answer = 0;

        for(int num : nums){

            prefix += num;

            answer +=
                    map.getOrDefault(
                            prefix-k,
                            0
                    );

            map.put(
                    prefix,
                    map.getOrDefault(
                            prefix,
                            0
                    ) + 1
            );
        }

        return answer;
    }
}
```

---

# Complexity

```text
Time : O(n)

Space : O(n)
```

---

# Similar Problems

* Binary Subarrays With Sum
* Number Of Subarrays With Odd Sum
* Continuous Subarray Sum

---

# Follow-Up

### Why Store Frequency?

Example:

```text
prefix = 10

Need:

prefix-k = 5
```

If:

```text
5
```

appears:

```text
3 times
```

Then:

```text
3 different subarrays
```

exist.

---

# Problem 4: Maximum Size Subarray Sum Equals K

## LeetCode 325

---

# Problem Explanation

Given:

```text
nums

[1,-1,5,-2,3]

k = 3
```

Find:

```text
Longest Subarray
whose sum equals k
```

Answer:

```text
4
```

Because:

```text
1 -1 +5 -2

=
3
```

Length:

```text
4
```

---

# What Is The Interviewer Testing?

Previous question asked:

```text
Count Subarrays
```

Now interviewer asks:

```text
Find Longest Subarray
```

Same pattern.

Different HashMap usage.

---

# Pattern Recognition Clues

### Clue 1

Subarray Sum = K.

### Clue 2

Need maximum length.

### Clue 3

Negative numbers exist.

Think:

```text
Prefix Sum + First Occurrence
```

---

# Brute Force

Generate every subarray.

Track longest.

---

## Complexity

```text
Time : O(n²)

Space : O(1)
```

---

# Prefix Sum Visualization

```text
nums

1 -1 5 -2 3
```

Prefix:

```text
1 0 5 3 6
```

At:

```text
prefix = 3
```

Need:

```text
3 - 3

=
0
```

Where was:

```text
0
```

seen?

Before array started.

Length:

```text
4
```

---

# Key Observation

For counting:

```java
prefix -> frequency
```

For longest length:

```java
prefix -> first index
```

Store only first occurrence.

---

# Why First Occurrence?

Example:

```text
Prefix 5
```

appears:

```text
Index 2

Index 7
```

Use:

```text
Index 2
```

because it gives longer length.

---

# Optimal Approach

Store:

```java
prefixSum
```

and

```java
first index
```

inside HashMap.

Whenever:

```java
prefixSum - k
```

exists:

Compute length.

Update answer.

---

# Dry Run

```text
nums

1 -1 5 -2 3

k = 3
```

Prefix:

```text
1
```

Store:

```text
1 -> 0
```

---

Prefix:

```text
0
```

Store:

```text
0 -> -1
```

---

Prefix:

```text
5
```

Store:

```text
5 -> 2
```

---

Prefix:

```text
3
```

Need:

```text
0
```

Found:

```text
index -1
```

Length:

```text
3 - (-1)

=
4
```

Answer:

```text
4
```

---

# What To Say In Interview

To maximize subarray length, I need the earliest occurrence of each prefix sum.

When I find:

```java
prefixSum - k
```

I calculate the distance between current index and the earliest matching prefix.

---

# Optimal Java Code

```java
class Solution {

    public int maxSubArrayLen(
            int[] nums,
            int k
    ) {

        Map<Integer,Integer> map =
                new HashMap<>();

        map.put(0,-1);

        int prefix = 0;

        int answer = 0;

        for(int i = 0;
            i < nums.length;
            i++){

            prefix += nums[i];

            if(map.containsKey(
                    prefix-k
            )){

                answer =
                        Math.max(
                                answer,
                                i -
                                map.get(
                                        prefix-k
                                )
                        );
            }

            map.putIfAbsent(
                    prefix,
                    i
            );
        }

        return answer;
    }
}
```

---

# Complexity

```text
Time : O(n)

Space : O(n)
```

---

# Similar Problems

* Longest Zero Sum Subarray
* Continuous Subarray Sum
* Binary Subarrays With Sum

---

# Prefix Sum + HashMap Revision Sheet

## Counting Subarrays

Store:

```java
prefixSum
→
frequency
```

Used In:

* Subarray Sum Equals K
* Binary Subarrays With Sum
* Odd Sum Subarrays

---

## Longest Subarray

Store:

```java
prefixSum
→
first index
```

Used In:

* Maximum Size Subarray Sum Equals K
* Longest Zero Sum Subarray

---

# Golden Formula

Whenever interviewer says:

```text
Subarray Sum = K
```

Immediately think:

```java
prefixSum - k
```

NOT:

```java
Nested Loops
```

This single trick solves a huge portion of Prefix Sum interview questions.
