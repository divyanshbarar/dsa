# HashMap Pattern — Part 6

# Prefix Sum + HashMap Pattern

This is one of the **most important combinations of patterns in DSA**.

The idea is simple:

> **Prefix Sum helps us calculate subarray relationships, and HashMap helps us find previous prefix sums in O(1) average time.**

This pattern is extremely useful when the problem asks about:

* Number of subarrays
* Longest subarray
* Subarray sum = `K`
* Subarray sum divisible by `K`
* Binary array with a target sum
* Continuous subarrays
* Count of subarrays satisfying some sum condition

---

# 1. First Understand Prefix Sum

Suppose:

```text
nums = [2, 4, 1, 3]
```

Prefix sum means:

```text
index:       0   1   2   3
nums:        2   4   1   3
prefix:      2   6   7   10
```

The prefix sum at an index represents:

```text
sum of all elements from index 0 to current index
```

For example:

```text
prefix[2] = 2 + 4 + 1 = 7
```

---

# 2. Finding a Subarray Sum Using Prefix Sum

Suppose we want:

```text
sum from index 1 to 3
```

That is:

```text
4 + 1 + 3 = 8
```

Using prefix sums:

```text
prefix[3] - prefix[0]
= 10 - 2
= 8
```

Therefore:

```text
subarray sum = currentPrefix - previousPrefix
```

This is the fundamental idea behind this entire pattern.

---

# 3. The Most Important Equation

Suppose we want a subarray whose sum is `K`.

We know:

```text
currentPrefix - previousPrefix = K
```

Rearrange:

```text
previousPrefix = currentPrefix - K
```

So when we are currently at:

```text
currentPrefix
```

we simply ask:

```text
Have I seen currentPrefix - K before?
```

If yes:

```text
A subarray with sum K exists.
```

And this is exactly where the HashMap comes in.

---

# 4. What Do We Store in the HashMap?

For counting problems:

```text
prefixSum → frequency
```

Example:

```text
map = {
    0 → 1,
    3 → 2,
    5 → 1
}
```

This means:

```text
prefix sum 0 appeared 1 time
prefix sum 3 appeared 2 times
prefix sum 5 appeared 1 time
```

---

# 5. Why `map.put(0, 1)`?

This is one of the most important things to understand.

Suppose:

```text
nums = [3]
k = 3
```

Current prefix:

```text
3
```

We need:

```text
previousPrefix = 3 - 3
                = 0
```

So `0` must already exist in the map.

Therefore:

```java
map.put(0, 1);
```

This represents:

> Before processing the array, we have seen a prefix sum of `0` exactly once.

It allows us to count subarrays that start from index `0`.

---

# 6. Generic Prefix Sum + HashMap Template

For **counting subarrays with sum K**, memorize this:

```java
Map<Integer, Integer> map = new HashMap<>();

map.put(0, 1);

int prefixSum = 0;
int count = 0;

for (int num : nums) {

    prefixSum += num;

    int required = prefixSum - k;

    if (map.containsKey(required)) {
        count += map.get(required);
    }

    map.put(
        prefixSum,
        map.getOrDefault(prefixSum, 0) + 1
    );
}

return count;
```

The entire logic can be remembered as:

```text
Current Prefix
      ↓
Need
Current Prefix - K
      ↓
Check HashMap
      ↓
Add Frequency
      ↓
Store Current Prefix
```

---

# Problem 1 — Subarray Sum Equals K

## Problem

Given an integer array `nums` and an integer `k`, return the **total number of continuous subarrays whose sum equals `k`**.

### Example

```text
Input:
nums = [1, 1, 1]
k = 2

Output:
2
```

The valid subarrays are:

```text
[1, 1]
[1, 1]
```

Therefore:

```text
Answer = 2
```

---

# Pattern Recognition

Whenever you see:

> Count the number of continuous subarrays whose sum equals K.

Think:

```text
Prefix Sum + HashMap
```

The key equation is:

```text
currentPrefix - previousPrefix = K
```

Therefore:

```text
previousPrefix = currentPrefix - K
```

So we search:

```java
currentPrefix - k
```

inside the HashMap.

---

# Approach 1 — Brute Force

Generate every possible subarray.

For every starting index `i`, keep extending the subarray using `j`.

Maintain the current sum.

### Code

```java
int count = 0;

for (int i = 0; i < nums.length; i++) {

    int sum = 0;

    for (int j = i; j < nums.length; j++) {

        sum += nums[j];

        if (sum == k) {
            count++;
        }
    }
}

return count;
```

### Complexity

```text
Time: O(n²)
Space: O(1)
```

This is better than recalculating every subarray sum from scratch, but still quadratic.

---

# Approach 2 — Prefix Sum Array

We can first create a prefix sum array.

Example:

```text
nums = [1, 2, 3, 4]

prefix = [1, 3, 6, 10]
```

Then calculate every possible subarray sum using:

```text
prefix[j] - prefix[i - 1]
```

### Code

```java
int n = nums.length;

int[] prefix = new int[n];

prefix[0] = nums[0];

for (int i = 1; i < n; i++) {
    prefix[i] = prefix[i - 1] + nums[i];
}

int count = 0;

for (int i = 0; i < n; i++) {

    for (int j = i; j < n; j++) {

        int sum;

        if (i == 0) {
            sum = prefix[j];
        } else {
            sum = prefix[j] - prefix[i - 1];
        }

        if (sum == k) {
            count++;
        }
    }
}

return count;
```

### Complexity

```text
Time: O(n²)
Space: O(n)
```

We improved sum calculation, but we still check every pair of indices.

---

# Approach 3 — Optimal: Prefix Sum + HashMap

Instead of checking every previous prefix sum manually, store them in a HashMap.

At every index:

```text
currentPrefix += nums[i]
```

We need:

```text
currentPrefix - previousPrefix = k
```

Therefore:

```text
previousPrefix = currentPrefix - k
```

So:

```java
int required = prefixSum - k;
```

If `required` exists:

```java
count += map.get(required);
```

Then store the current prefix:

```java
map.put(
    prefixSum,
    map.getOrDefault(prefixSum, 0) + 1
);
```

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int subarraySum(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {

            prefixSum += num;

            int required = prefixSum - k;

            if (map.containsKey(required)) {
                count += map.get(required);
            }

            map.put(
                prefixSum,
                map.getOrDefault(prefixSum, 0) + 1
            );
        }

        return count;
    }
}
```

---

# Dry Run

Consider:

```text
nums = [1, 1, 1]
k = 2
```

Initially:

```text
map = {0=1}
prefixSum = 0
count = 0
```

---

## Index 0

```text
num = 1
```

Update prefix:

```text
prefixSum = 1
```

Required:

```text
1 - 2 = -1
```

`-1` doesn't exist.

Store:

```text
map = {
    0 → 1,
    1 → 1
}
```

---

## Index 1

```text
num = 1
```

Update:

```text
prefixSum = 2
```

Required:

```text
2 - 2 = 0
```

`0` exists.

Frequency:

```text
1
```

Therefore:

```text
count = 1
```

Store:

```text
map = {
    0 → 1,
    1 → 1,
    2 → 1
}
```

We found:

```text
[1, 1]
```

---

## Index 2

```text
num = 1
```

Update:

```text
prefixSum = 3
```

Required:

```text
3 - 2 = 1
```

`1` exists.

Frequency:

```text
1
```

Therefore:

```text
count = 2
```

Final:

```text
Answer = 2
```

---

# Important Concept — Why Store Frequency?

Consider:

```text
nums = [0, 0, 0]
k = 0
```

Every possible subarray has sum `0`.

Valid subarrays:

```text
[0]
[0]
[0]

[0, 0]
[0, 0]

[0, 0, 0]
```

Total:

```text
6
```

Prefix sums:

```text
0
0
0
```

The same prefix sum appears multiple times.

Therefore we cannot simply store:

```text
prefixSum → true
```

We need:

```text
prefixSum → frequency
```

That's why:

```java
map.put(
    prefixSum,
    map.getOrDefault(prefixSum, 0) + 1
);
```

is necessary.

---

# Common Mistake

### ❌ Wrong

```java
map.put(prefixSum, 1);
```

This destroys the frequency information.

### ✅ Correct

```java
map.put(
    prefixSum,
    map.getOrDefault(prefixSum, 0) + 1
);
```

---

# Common Mistake 2 — Forgetting `map.put(0, 1)`

### ❌ Wrong

```java
Map<Integer, Integer> map = new HashMap<>();

int prefixSum = 0;
```

### ✅ Correct

```java
Map<Integer, Integer> map = new HashMap<>();

map.put(0, 1);
```

Without it, subarrays starting at index `0` can be missed.

---

# Common Mistake 3 — Using Sliding Window

A common mistake is to think:

```text
Subarray Sum = K
        ↓
Sliding Window
```

But if the array contains:

```text
negative numbers
```

standard sliding window does not work reliably.

For example:

```text
nums = [1, -1, 1]
```

The sum can increase and decrease unpredictably.

So for:

```text
Subarray Sum = K
```

with arbitrary integers:

```text
Prefix Sum + HashMap
```

is the safer general pattern.

---

# Interview Explanation

If the interviewer asks:

> "Explain your optimal approach."

Say:

> "I'll use prefix sum with a HashMap. At every index, I maintain the current prefix sum. If a subarray ending at the current index has sum K, then the previous prefix sum must be currentPrefix minus K. So I store the frequency of previously seen prefix sums in a HashMap and add the frequency of currentPrefix minus K to the answer. I initialize the map with `(0,1)` to handle subarrays starting from index zero."

---

# Complexity

```text
Time: O(n)
Space: O(n)
```

Why?

We traverse the array once.

HashMap operations such as:

```text
put()
get()
containsKey()
```

take:

```text
Average O(1)
```

Therefore:

```text
O(n)
```

overall.

---

# Problem 2 — Subarray Sums Divisible by K

## Problem

Given an integer array `nums` and an integer `k`, return the number of non-empty subarrays whose sum is divisible by `k`.

### Example

```text
Input:
nums = [4, 5, 0, -2, -3, 1]
k = 5

Output:
7
```

---

# Pattern Recognition

Look for:

```text
subarray
+
sum divisible by K
```

Think:

```text
Prefix Sum + HashMap
```

But there is one important modification.

Instead of storing:

```text
prefixSum
```

we store:

```text
prefixSum % k
```

---

# Core Mathematics

Suppose two prefix sums have the same remainder when divided by `k`.

For example:

```text
prefix1 % k = 2
prefix2 % k = 2
```

Then:

```text
(prefix2 - prefix1) % k = 0
```

Therefore:

```text
prefix2 - prefix1
```

is divisible by `k`.

And:

```text
prefix2 - prefix1
```

is exactly the sum of a subarray.

So:

> If two prefix sums have the same remainder modulo K, the subarray between them has a sum divisible by K.

---

# Example

Suppose:

```text
k = 5
```

Prefix sums:

```text
7
12
17
```

Remainders:

```text
7 % 5  = 2
12 % 5 = 2
17 % 5 = 2
```

All have the same remainder.

Therefore:

```text
12 - 7 = 5
17 - 12 = 5
17 - 7 = 10
```

All are divisible by `5`.

So multiple valid subarrays exist.

---

# Optimal Approach

Maintain:

```text
remainder → frequency
```

Initially:

```java
map.put(0, 1);
```

For every number:

```java
prefixSum += num;
```

Calculate:

```java
remainder = prefixSum % k;
```

If remainder is negative, normalize it:

```java
if (remainder < 0) {
    remainder += k;
}
```

Then:

```java
if (map.containsKey(remainder)) {
    count += map.get(remainder);
}
```

Finally:

```java
map.put(
    remainder,
    map.getOrDefault(remainder, 0) + 1
);
```

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int subarraysDivByK(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {

            prefixSum += num;

            int remainder = prefixSum % k;

            if (remainder < 0) {
                remainder += k;
            }

            if (map.containsKey(remainder)) {
                count += map.get(remainder);
            }

            map.put(
                remainder,
                map.getOrDefault(remainder, 0) + 1
            );
        }

        return count;
    }
}
```

---

# Why Do We Handle Negative Remainders?

Java can produce a negative remainder.

Example:

```text
-2 % 5 = -2
```

But for our HashMap logic, we want remainders in:

```text
0 to k - 1
```

So:

```java
if (remainder < 0) {
    remainder += k;
}
```

Example:

```text
-2 + 5 = 3
```

Now:

```text
remainder = 3
```

---

# Complexity

```text
Time: O(n)
Space: O(k)
```

There can be at most `k` different remainders:

```text
0, 1, 2, ..., k - 1
```

---

# Problem 3 — Binary Subarrays With Sum

## Problem

Given a binary array `nums` containing only `0` and `1`, and an integer `goal`, return the number of non-empty subarrays with sum equal to `goal`.

### Example

```text
Input:
nums = [1, 0, 1, 0, 1]
goal = 2

Output:
4
```

---

# Pattern Recognition

You see:

```text
binary array
+
subarray
+
sum = goal
```

Think:

```text
Prefix Sum + HashMap
```

This is essentially the same pattern as:

```text
Subarray Sum Equals K
```

The only difference is that the array contains only:

```text
0 and 1
```

---

# Why Does Zero Make This Interesting?

Consider:

```text
nums = [1, 0, 0, 1]
```

Once we have a prefix sum of `1`, the zeros don't increase the sum.

So many different subarrays can have the same sum.

This is another reason we need:

```text
prefixSum → frequency
```

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int numSubarraysWithSum(int[] nums, int goal) {

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);

        int prefixSum = 0;
        int count = 0;

        for (int num : nums) {

            prefixSum += num;

            int required = prefixSum - goal;

            if (map.containsKey(required)) {
                count += map.get(required);
            }

            map.put(
                prefixSum,
                map.getOrDefault(prefixSum, 0) + 1
            );
        }

        return count;
    }
}
```

---

# Complexity

```text
Time: O(n)
Space: O(n)
```

---

# Important Observation

This problem can also be solved using:

```text
AtMost(goal) - AtMost(goal - 1)
```

because the array is binary.

This introduces another important pattern:

```text
Prefix Sum + HashMap
```

and:

```text
Sliding Window + AtMost
```

For now, remember the HashMap solution because it connects directly to the previous problem.

---

# Problem 4 — Longest Subarray With Sum K

## Problem

Given an array of integers and an integer `k`, find the **length of the longest subarray whose sum equals `k`**.

### Example

```text
Input:
nums = [1, -1, 5, -2, 3]
k = 3

Output:
4
```

The longest valid subarray is:

```text
[1, -1, 5, -2]
```

Its sum is:

```text
1 - 1 + 5 - 2 = 3
```

Length:

```text
4
```

---

# Pattern Recognition

Look for:

```text
longest subarray
+
sum = K
```

Think:

```text
Prefix Sum + HashMap
```

But there is an important difference from counting.

For counting:

```text
prefixSum → frequency
```

For longest length:

```text
prefixSum → first index
```

🔥 This distinction is extremely important.

---

# Why Store the First Index?

Suppose the same prefix sum appears multiple times.

Example:

```text
prefix = 5
```

appears at:

```text
index 2
index 5
index 8
```

If we want the **longest** subarray, we want the earliest occurrence:

```text
index 2
```

because it gives us the largest possible length.

Therefore:

> For longest subarray problems, store the first occurrence of a prefix sum.

---

# Optimal Approach

At every index:

```text
prefixSum += nums[i]
```

We need:

```text
previousPrefix = prefixSum - k
```

If it exists:

```text
length = i - previousIndex
```

Update:

```text
maxLength = max(maxLength, length)
```

Then store the prefix sum only if it hasn't been seen before.

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int maxSubArrayLen(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, -1);

        int prefixSum = 0;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            prefixSum += nums[i];

            int required = prefixSum - k;

            if (map.containsKey(required)) {

                int length = i - map.get(required);

                maxLength = Math.max(maxLength, length);
            }

            if (!map.containsKey(prefixSum)) {
                map.put(prefixSum, i);
            }
        }

        return maxLength;
    }
}
```

---

# Why `map.put(0, -1)`?

This is the longest-subarray equivalent of:

```java
map.put(0, 1);
```

For counting:

```text
0 → frequency 1
```

For longest:

```text
0 → index -1
```

Why `-1`?

Because it represents a prefix sum of `0` **before the array begins**.

Example:

```text
nums = [3]
k = 3
```

At index:

```text
0
```

Current prefix:

```text
3
```

Required:

```text
3 - 3 = 0
```

Map contains:

```text
0 → -1
```

Therefore:

```text
length = 0 - (-1)
       = 1
```

Correct.

---

# Critical Difference

### Counting Subarrays

Store:

```text
prefixSum → frequency
```

Initialize:

```java
map.put(0, 1);
```

---

### Longest Subarray

Store:

```text
prefixSum → first index
```

Initialize:

```java
map.put(0, -1);
```

---

# Why Should We NOT Update the Index?

Suppose:

```text
prefixSum = 5
```

first appears at:

```text
index 2
```

Later appears at:

```text
index 7
```

For longest subarray, keep:

```text
5 → 2
```

Do NOT replace it with:

```text
5 → 7
```

because:

```text
7
```

would produce a shorter subarray.

Therefore:

```java
if (!map.containsKey(prefixSum)) {
    map.put(prefixSum, i);
}
```

---

# Complexity

```text
Time: O(n)
Space: O(n)
```

---

# Problem 5 — Contiguous Array

## Problem

Given a binary array containing `0` and `1`, find the maximum length of a contiguous subarray with an equal number of `0` and `1`.

### Example

```text
Input:
nums = [0, 1, 0]

Output:
2
```

The valid subarray is:

```text
[0, 1]
```

It contains:

```text
1 zero
1 one
```

---

# Pattern Recognition

The problem does not directly say:

```text
subarray sum = K
```

But it says:

```text
equal number of 0 and 1
```

We can convert it into a prefix sum problem.

---

# Key Trick

Treat:

```text
0 → -1
1 → +1
```

Now:

```text
equal number of 0 and 1
```

means:

```text
sum = 0
```

Example:

```text
[0, 1, 0, 1]
```

Convert:

```text
[-1, +1, -1, +1]
```

Total:

```text
0
```

Therefore the entire array has equal numbers of `0` and `1`.

---

# Why Does This Work?

Suppose:

```text
number of ones = x
number of zeros = x
```

After conversion:

```text
x(+1) + x(-1)
```

which becomes:

```text
x - x = 0
```

So the problem becomes:

> Find the longest subarray whose sum is `0`.

And we already know that pattern:

```text
Prefix Sum + HashMap
```

---

# Optimal Approach

Convert:

```text
0 → -1
1 → +1
```

Maintain prefix sum.

If the same prefix sum appears at two different indices:

```text
prefix[j] = prefix[i]
```

then:

```text
prefix[j] - prefix[i] = 0
```

Therefore the subarray between them has sum `0`.

Store:

```text
prefixSum → first index
```

because we need the longest length.

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int findMaxLength(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, -1);

        int prefixSum = 0;
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == 0) {
                prefixSum--;
            } else {
                prefixSum++;
            }

            if (map.containsKey(prefixSum)) {

                int length = i - map.get(prefixSum);

                maxLength = Math.max(maxLength, length);

            } else {

                map.put(prefixSum, i);
            }
        }

        return maxLength;
    }
}
```

---

# Dry Run

Consider:

```text
nums = [0, 1, 0, 1]
```

Initially:

```text
map = {0 = -1}
prefixSum = 0
maxLength = 0
```

---

## Index 0

```text
nums[0] = 0
```

Treat zero as:

```text
-1
```

Prefix:

```text
-1
```

Not present.

Store:

```text
-1 → 0
```

---

## Index 1

```text
nums[1] = 1
```

Add:

```text
+1
```

Prefix:

```text
0
```

`0` already exists at:

```text
-1
```

Length:

```text
1 - (-1) = 2
```

So:

```text
maxLength = 2
```

---

## Index 2

```text
nums[2] = 0
```

Prefix:

```text
-1
```

`-1` already exists at index `0`.

Length:

```text
2 - 0 = 2
```

---

## Index 3

```text
nums[3] = 1
```

Prefix:

```text
0
```

`0` exists at:

```text
-1
```

Length:

```text
3 - (-1) = 4
```

Final:

```text
Answer = 4
```

---

# Problem 6 — Continuous Subarray Sum

## Problem

Given an integer array `nums` and an integer `k`, return `true` if `nums` has a continuous subarray of at least two elements whose sum is a multiple of `k`.

In other words:

```text
subarray sum % k == 0
```

and:

```text
subarray length >= 2
```

---

# Pattern Recognition

Look for:

```text
continuous subarray
+
multiple of K
```

Think:

```text
Prefix Sum % K + HashMap
```

---

# Core Idea

We calculate:

```text
prefixSum % k
```

If the same remainder occurs again, then the difference between the two prefix sums is divisible by `k`.

Example:

```text
prefix1 % k = 3
prefix2 % k = 3
```

Therefore:

```text
(prefix2 - prefix1) % k = 0
```

So the subarray between them is divisible by `k`.

---

# Important Difference

Here we don't need frequency.

We need:

```text
remainder → first index
```

because we need to ensure:

```text
length >= 2
```

and storing the earliest index gives us the longest possible distance.

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public boolean checkSubarraySum(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, -1);

        int prefixSum = 0;

        for (int i = 0; i < nums.length; i++) {

            prefixSum += nums[i];

            int remainder = prefixSum % k;

            if (remainder < 0) {
                remainder += k;
            }

            if (map.containsKey(remainder)) {

                if (i - map.get(remainder) >= 2) {
                    return true;
                }

            } else {

                map.put(remainder, i);
            }
        }

        return false;
    }
}
```

---

# Important Rule

When solving:

```text
Sum divisible by K
```

think:

```text
prefixSum % K
```

When solving:

```text
Sum equals K
```

think:

```text
prefixSum - K
```

This distinction is extremely important.

---

# Prefix Sum + HashMap — Master Template

At this point, you should recognize that many different-looking questions are actually the same pattern.

---

## Pattern A — Count Subarrays With Sum K

Store:

```text
prefixSum → frequency
```

Template:

```java
map.put(0, 1);

prefixSum += nums[i];

required = prefixSum - k;

count += map.getOrDefault(required, 0);

map.put(
    prefixSum,
    map.getOrDefault(prefixSum, 0) + 1
);
```

---

## Pattern B — Longest Subarray With Sum K

Store:

```text
prefixSum → first index
```

Template:

```java
map.put(0, -1);

prefixSum += nums[i];

required = prefixSum - k;

if (map.containsKey(required)) {
    length = i - map.get(required);
}

if (!map.containsKey(prefixSum)) {
    map.put(prefixSum, i);
}
```

---

## Pattern C — Sum Divisible By K

Store:

```text
remainder → frequency
```

Template:

```java
map.put(0, 1);

prefixSum += nums[i];

remainder = prefixSum % k;

count += map.getOrDefault(remainder, 0);

map.put(
    remainder,
    map.getOrDefault(remainder, 0) + 1
);
```

Remember to normalize negative remainders when needed.

---

## Pattern D — Longest Subarray Divisible By K

Store:

```text
remainder → first index
```

Template:

```java
map.put(0, -1);

prefixSum += nums[i];

remainder = prefixSum % k;

if (map.containsKey(remainder)) {
    length = i - map.get(remainder);
} else {
    map.put(remainder, i);
}
```

---

# The Most Important Pattern Difference

| Problem Type                    | HashMap Stores           | Initial Value |
| ------------------------------- | ------------------------ | ------------- |
| Count subarrays with sum K      | Prefix Sum → Frequency   | `0 → 1`       |
| Longest subarray with sum K     | Prefix Sum → First Index | `0 → -1`      |
| Count subarrays divisible by K  | Remainder → Frequency    | `0 → 1`       |
| Longest subarray divisible by K | Remainder → First Index  | `0 → -1`      |
| Equal 0s and 1s                 | Prefix Sum → First Index | `0 → -1`      |

🔥 This table is worth memorizing.

---

# How To Recognize This Pattern In An Interview

If you hear:

### "How many subarrays..."

Think:

```text
Prefix Sum + HashMap
```

If you hear:

### "Longest subarray..."

Think:

```text
Prefix Sum + HashMap
```

But ask:

> Do I need frequency or first index?

---

If you hear:

### "Sum equals K"

Think:

```text
currentPrefix - K
```

---

If you hear:

### "Sum divisible by K"

Think:

```text
currentPrefix % K
```

---

If you hear:

### "Equal number of X and Y"

Try converting one value:

```text
X → +1
Y → -1
```

Then look for:

```text
sum = 0
```

---

# Common Mistakes

## 1. Forgetting the Initial Prefix

For counting:

```java
map.put(0, 1);
```

For longest:

```java
map.put(0, -1);
```

---

## 2. Using Frequency When You Need Longest

For longest:

```text
prefixSum → first index
```

not:

```text
prefixSum → frequency
```

---

## 3. Updating the First Index

For longest problems:

```java
if (!map.containsKey(prefixSum)) {
    map.put(prefixSum, i);
}
```

Do not overwrite it.

---

## 4. Forgetting Negative Remainders

For modulo problems:

```java
int remainder = prefixSum % k;

if (remainder < 0) {
    remainder += k;
}
```

---

## 5. Assuming Sliding Window Always Works

Sliding Window generally requires a useful monotonic property, such as non-negative numbers.

For arbitrary integers:

```text
Prefix Sum + HashMap
```

is usually the correct pattern for exact-sum subarray problems.

---

# Interview Cheat Sheet

```text
SUBARRAY
   |
   +---- Sum = K
   |       |
   |       +---- Count → Prefix Sum + HashMap Frequency
   |       |
   |       +---- Longest → Prefix Sum + HashMap First Index
   |
   +---- Sum divisible by K
   |       |
   |       +---- Count → Remainder + Frequency
   |       |
   |       +---- Longest → Remainder + First Index
   |
   +---- Equal 0s and 1s
           |
           +---- 0 → -1
           +---- 1 → +1
           +---- Find longest zero-sum subarray
```

---

# 1-Minute Revision

The most important equation:

```text
currentPrefix - previousPrefix = K
```

Therefore:

```text
previousPrefix = currentPrefix - K
```

So:

```java
required = prefixSum - k;
```

For counting:

```text
HashMap:
prefixSum → frequency
```

For longest:

```text
HashMap:
prefixSum → first index
```

For divisibility:

```text
HashMap:
prefixSum % k → frequency/index
```

For equal `0` and `1`:

```text
0 → -1
1 → +1
```

Then solve it as a zero-sum problem.

---

# Golden Rule

> **Whenever a problem asks about a subarray and gives you a condition involving its sum, check whether Prefix Sum + HashMap can turn the problem into finding a previous prefix/remainder.**

The two formulas to remember are:

```text
Sum = K
→
previousPrefix = currentPrefix - K
```

and:

```text
Sum divisible by K
→
previousPrefix % K = currentPrefix % K
```

Once you understand these two ideas, a large number of "different" subarray questions become the **same pattern with a small modification**.

---

# Practice Questions For This Pattern

After the above problems, practice these without looking at the solution:

### Easy

1. Find the number of subarrays with sum `K`.
2. Find the longest subarray with sum `K`.
3. Find whether a subarray with sum `0` exists.
4. Find the longest subarray with equal number of `0` and `1`.

### Medium

5. Subarray Sums Divisible by K.
6. Continuous Subarray Sum.
7. Binary Subarrays With Sum.
8. Maximum Size Subarray Sum Equals K.
9. Count subarrays with equal number of `0`, `1`, and `-1`.
10. Count subarrays whose sum is divisible by a given number.

---

# Final Mental Model

Don't memorize six different solutions.

Memorize this:

```text
1. Calculate Prefix Sum.

2. Ask:
   What previous prefix do I need?

3. Put that information in HashMap.

4. Decide what HashMap stores:
   
   COUNT?
   → frequency

   LONGEST?
   → first index

   DIVISIBLE?
   → remainder

5. Initialize correctly:

   COUNT:
   0 → 1

   LONGEST:
   0 → -1
```

That's the **Prefix Sum + HashMap pattern**.

---

