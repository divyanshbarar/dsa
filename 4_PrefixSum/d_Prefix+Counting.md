# Prefix Sum Pattern - Part 4

# Counting Family

---

# Why This Family Is Important

Most interview candidates learn:

```java id="wh5f2n"
prefixSum - k
```

for:

```text id="uqp4y7"
Subarray Sum Equals K
```

But many advanced problems ask:

```text id="ubj95z"
Exactly K
```

instead of:

```text id="hmzqlt"
Sum Equals K
```

Examples:

* Exactly K Ones
* Exactly K Odds
* Exactly K Distinct Integers
* Binary Subarrays With Sum

This family teaches one of the highest ROI formulas in DSA.

---

# Golden Formula

```java id="d7zj4r"
Exactly(K)
=
AtMost(K)
-
AtMost(K-1)
```

Memorize this.

It appears everywhere.

---

# Why Does It Work?

Suppose:

```text id="bfjy4e"
AtMost(3)
```

contains:

```text id="shg4ow"
0

1

2

3
```

And:

```text id="x8vwj9"
AtMost(2)
```

contains:

```text id="56xvvr"
0

1

2
```

Subtract:

```text id="f4u8kd"
AtMost(3)

-

AtMost(2)
```

Leaves:

```text id="1o1jqf"
Exactly 3
```

Only.

---

# Problem 7: Binary Subarrays With Sum

## LeetCode 930

---

# Problem Explanation

Given a binary array:

```text id="8pzbq9"
nums

[1,0,1,0,1]
```

Goal:

```text id="n6j0e9"
Count subarrays
whose sum = goal
```

Example:

```text id="upzq4e"
goal = 2
```

Answer:

```text id="hpr41w"
4
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text id="0yrsj8"
Exactly K
```

and convert it into:

```text id="jlwm70"
AtMost(K)
```

?

---

# Pattern Recognition Clues

### Clue 1

Binary Array.

### Clue 2

Count Subarrays.

### Clue 3

Exactly Goal.

### Clue 4

Positive Values Only.

Think:

```text id="n6r81e"
Sliding Window Count Trick
```

or:

```text id="s7kdph"
Prefix Sum HashMap
```

---

# Brute Force

Generate every subarray.

Calculate sum.

---

## Complexity

```text id="h9vnv6"
Time : O(n²)

Space : O(1)
```

---

# Approach 1: Prefix Sum + HashMap

---

# Prefix Sum Visualization

```text id="zzw3qo"
nums

1 0 1 0 1
```

Prefix:

```text id="ebgmwq"
1 1 2 2 3
```

Need:

```java id="4ayz1j"
prefixSum - goal
```

Same idea as:

```text id="2a3l8t"
Subarray Sum Equals K
```

---

# Optimal Java Code (Prefix Sum)

```java id="mnh8qu"
class Solution {

    public int numSubarraysWithSum(
            int[] nums,
            int goal
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
                            prefix-goal,
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

```text id="n7qvfx"
Time : O(n)

Space : O(n)
```

---

# Approach 2: Sliding Window Counting Trick

---

# Key Observation

Binary array means:

```text id="5nqoyx"
All Elements >= 0
```

Therefore:

```text id="qhk6mg"
AtMost(K)
```

is possible.

Use:

```java id="q9llmd"
Exactly(K)

=

AtMost(K)

-

AtMost(K-1)
```

---

# AtMost Helper

Count:

```text id="7f8yk7"
Subarrays
with sum <= k
```

---

# Optimal Java Code

```java id="oowyhu"
class Solution {

    public int numSubarraysWithSum(
            int[] nums,
            int goal
    ) {

        return atMost(nums,goal)
                -
               atMost(nums,goal-1);
    }

    private int atMost(
            int[] nums,
            int goal
    ){

        if(goal < 0){
            return 0;
        }

        int left = 0;
        int sum = 0;
        int answer = 0;

        for(int right = 0;
            right < nums.length;
            right++){

            sum += nums[right];

            while(sum > goal){

                sum -= nums[left];
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

# What To Say In Interview

This problem can be solved using Prefix Sum + HashMap.

However, because the array is binary, I can also use the Exactly(K) = AtMost(K) - AtMost(K-1) sliding window trick.

---

# Similar Problems

* Nice Subarrays
* K Distinct Integers
* Count Subarrays With Sum K

---

# Problem 8: Count Number Of Nice Subarrays

## LeetCode 1248

---

# Problem Explanation

A nice subarray contains:

```text id="jlwm99"
Exactly K Odd Numbers
```

Example:

```text id="2r7hvt"
nums

[1,1,2,1,1]

k = 3
```

Answer:

```text id="0hjlwm"
2
```

Subarrays:

```text id="2x44tl"
[1,1,2,1]

[1,2,1,1]
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="3g9lpu"
Exactly K Odds
```

as:

```text id="9j9dgn"
Exactly K Event
```

?

---

# Pattern Recognition Clues

### Clue 1

Exactly K.

### Clue 2

Count Subarrays.

### Clue 3

Odd Numbers.

Think:

```text id="8xmg0o"
AtMost Trick
```

---

# Brute Force

Generate every subarray.

Count odds.

---

## Complexity

```text id="g63sgx"
Time : O(n²)

Space : O(1)
```

---

# Key Insight

Convert:

```text id="6ql5mt"
Odd
```

into:

```text id="yv0avt"
1
```

Convert:

```text id="lcn4pd"
Even
```

into:

```text id="vjlwm3"
0
```

Now problem becomes:

```text id="p5w90f"
Exactly K Ones
```

---

# Prefix Sum Visualization

Array:

```text id="ijg8p2"
1 1 2 1 1
```

Convert:

```text id="kdn9pf"
1 1 0 1 1
```

Need:

```text id="ljlwm1"
Sum = 3
```

Now it's basically:

```text id="rwjlwm"
Binary Subarray Sum
```

again.

---

# Optimal Approach

Use:

```java id="2uhxoq"
Exactly(K)

=

AtMost(K)

-

AtMost(K-1)
```

---

# Dry Run

```text id="zjlwm2"
nums

1 1 2 1 1

k = 3
```

Count:

```text id="jlwm56"
AtMost(3)
```

Then:

```text id="jlwm57"
AtMost(2)
```

Subtract.

Get:

```text id="jlwm58"
Exactly(3)
```

---

# What To Say In Interview

Instead of directly counting subarrays with exactly k odd numbers, I'll count subarrays with at most k odds and subtract subarrays with at most k-1 odds.

---

# Optimal Java Code

```java id="jlwm59"
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

```text id="jlwm60"
Time : O(n)

Space : O(1)
```

---

# Why Interviewers Love This Problem

Because it tests:

```text id="jlwm61"
Pattern Recognition
```

not coding.

Most people try:

```text id="jlwm62"
Direct Counting
```

Strong candidates immediately see:

```text id="jlwm63"
Exactly(K)

=

AtMost(K)

-

AtMost(K-1)
```

---

# Counting Family Revision Sheet

## Subarray Sum Equals K

Formula:

```java id="jlwm64"
prefixSum - k
```

---

## Binary Subarrays With Sum

Formula:

```java id="jlwm65"
Exactly(K)

=

AtMost(K)

-

AtMost(K-1)
```

---

## Nice Subarrays

Formula:

```java id="jlwm66"
Exactly(K Odds)

=

AtMost(K Odds)

-

AtMost(K-1 Odds)
```

---

# Most Important Interview Formula

```java id="jlwm67"
Exactly(K)

=

AtMost(K)

-

AtMost(K-1)
```

Used In:

* Binary Subarrays With Sum
* Nice Subarrays
* K Distinct Integers
* Character Replacement Variants
* Many Sliding Window Problems

Memorize it. It appears surprisingly often in SDE interviews.

---

# Golden Rule

If interviewer says:

```text id="jlwm68"
Count

Exactly K

Subarrays
```

Ask yourself:

```text id="jlwm69"
Can I convert

Exactly(K)

into

AtMost(K)
-
AtMost(K-1)?
```

If yes, the problem often becomes dramatically easier.
