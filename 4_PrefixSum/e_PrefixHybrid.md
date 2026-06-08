# Prefix Sum Pattern - Part 5

# Hybrid Family (Prefix Sum vs Sliding Window)

---

# Why This Family Exists

Many interview candidates make this mistake:

```text id="a1x001"
Array Problem
=
Prefix Sum
```

Wrong.

Sometimes:

```text id="a1x002"
Sliding Window
```

is better.

Sometimes:

```text id="a1x003"
Prefix Sum
```

is better.

Strong candidates know:

```text id="a1x004"
WHEN to use each pattern
```

This section teaches that.

---

# Decision Tree

## If Array Contains Only Positive Numbers

Think:

```text id="a1x005"
Sliding Window
```

because:

```text id="a1x006"
Expand
→ Sum Increases

Shrink
→ Sum Decreases
```

---

## If Negative Numbers Exist

Think:

```text id="a1x007"
Prefix Sum
```

because Sliding Window breaks.

---

# Problem 9: Minimum Size Subarray Sum

## LeetCode 209

---

# Problem Explanation

Given:

```text id="a1x008"
target = 7

nums

[2,3,1,2,4,3]
```

Find:

```text id="a1x009"
Minimum Length
Subarray
whose sum >= target
```

Answer:

```text id="a1x010"
2

[4,3]
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text id="a1x011"
Positive Numbers
```

which immediately suggests:

```text id="a1x012"
Sliding Window
```

instead of Prefix Sum.

---

# Pattern Recognition Clues

### Clue 1

Minimum Length.

### Clue 2

Positive Numbers.

### Clue 3

Sum Constraint.

Think:

```text id="a1x013"
Sliding Window
```

---

# Brute Force

Generate all subarrays.

---

## Complexity

```text id="a1x014"
Time : O(n²)
```

---

# Optimal Approach

Expand until:

```text id="a1x015"
sum >= target
```

Then shrink aggressively.

---

# What To Say In Interview

Since all numbers are positive, increasing the window increases the sum and shrinking decreases the sum.

Therefore Sliding Window is more efficient than Prefix Sum.

---

# Optimal Java Code

```java id="a1x016"
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

```text id="a1x017"
Time : O(n)

Space : O(1)
```

---

# Interview Follow-Up

### What If Negative Numbers Exist?

Example:

```text id="a1x018"
2 -5 10
```

Now:

```text id="a1x019"
Expand
```

does NOT guarantee:

```text id="a1x020"
Sum Increases
```

Sliding Window fails.

Need:

```text id="a1x021"
Prefix Sum
+
Deque
```

---

# Problem 10: Maximum Average Subarray I

## LeetCode 643

---

# Problem Explanation

Given:

```text id="a1x022"
nums

[1,12,-5,-6,50,3]

k = 4
```

Find:

```text id="a1x023"
Maximum Average
of any subarray
of size K
```

---

# Pattern Recognition Clues

### Clue 1

Exactly K.

### Clue 2

Subarray.

### Clue 3

Maximum.

Think:

```text id="a1x024"
Fixed Sliding Window
```

NOT Prefix Sum.

---

# Why Not Prefix Sum?

You can solve using Prefix Sum:

```java id="a1x025"
sum(l,r)
=
prefix[r]
-
prefix[l-1]
```

But every window still needs checking.

Sliding Window is cleaner.

---

# Brute Force

Calculate every window sum separately.

---

## Complexity

```text id="a1x026"
Time : O(n*k)
```

---

# Optimal Approach

Maintain:

```java id="a1x027"
windowSum
```

Slide window.

---

# What To Say In Interview

Since the window size is fixed, Sliding Window naturally maintains the current sum in O(1) per move.

---

# Optimal Java Code

```java id="a1x028"
class Solution {

    public double findMaxAverage(
            int[] nums,
            int k
    ) {

        long sum = 0;

        for(int i = 0;
            i < k;
            i++){

            sum += nums[i];
        }

        long maxSum = sum;

        for(int i = k;
            i < nums.length;
            i++){

            sum += nums[i];
            sum -= nums[i-k];

            maxSum =
                    Math.max(
                            maxSum,
                            sum
                    );
        }

        return (double)
                maxSum / k;
    }
}
```

---

# Complexity

```text id="a1x029"
Time : O(n)

Space : O(1)
```

---

# Problem 11: Longest Subarray Of 1's After Deleting One Element

## LeetCode 1493

---

# Problem Explanation

Given:

```text id="a1x030"
1 1 0 1
```

Delete one element.

Find:

```text id="a1x031"
Longest Subarray
containing only 1's
```

Answer:

```text id="a1x032"
3
```

Delete:

```text id="a1x033"
0
```

Result:

```text id="a1x034"
1 1 1
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text id="a1x035"
At Most One Zero
```

?

---

# Pattern Recognition Clues

### Clue 1

Delete One Element.

### Clue 2

Binary Array.

### Clue 3

Longest Window.

Think:

```text id="a1x036"
Sliding Window
```

---

# Key Insight

Deleting:

```text id="a1x037"
one element
```

means window can contain:

```text id="a1x038"
At Most One Zero
```

---

# Optimal Approach

Maintain:

```java id="a1x039"
zeroCount <= 1
```

---

# What To Say In Interview

Instead of physically deleting an element, I allow one zero inside the window.

The longest valid window gives the answer.

---

# Optimal Java Code

```java id="a1x040"
class Solution {

    public int longestSubarray(
            int[] nums
    ) {

        int left = 0;
        int zeroes = 0;
        int answer = 0;

        for(int right = 0;
            right < nums.length;
            right++){

            if(nums[right] == 0){
                zeroes++;
            }

            while(zeroes > 1){

                if(nums[left] == 0){
                    zeroes--;
                }

                left++;
            }

            answer =
                    Math.max(
                            answer,
                            right-left
                    );
        }

        return answer;
    }
}
```

---

# Complexity

```text id="a1x041"
Time : O(n)

Space : O(1)
```

---

# Problem 12: Longest Subarray With Sum At Most K

## Classic Interview Variant

---

# Problem Explanation

Given:

```text id="a1x042"
nums

[1,2,1,0,1,1,0]

k = 4
```

Find:

```text id="a1x043"
Longest Subarray
whose sum <= k
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="a1x044"
Positive Numbers
```

again?

---

# Pattern Recognition Clues

### Clue 1

Longest Subarray.

### Clue 2

At Most K.

### Clue 3

Positive Values.

Think:

```text id="a1x045"
Sliding Window
```

---

# Brute Force

Generate every subarray.

---

## Complexity

```text id="a1x046"
Time : O(n²)
```

---

# Optimal Approach

Maintain:

```java id="a1x047"
sum <= k
```

If sum exceeds:

```text id="a1x048"
Shrink
```

---

# What To Say In Interview

Because all values are non-negative, Sliding Window guarantees monotonic movement of the sum, allowing an O(n) solution.

---

# Optimal Java Code

```java id="a1x049"
class Solution {

    public int longestSubarrayAtMostK(
            int[] nums,
            int k
    ) {

        int left = 0;
        int sum = 0;
        int answer = 0;

        for(int right = 0;
            right < nums.length;
            right++){

            sum += nums[right];

            while(sum > k){

                sum -= nums[left];
                left++;
            }

            answer =
                    Math.max(
                            answer,
                            right-left+1
                    );
        }

        return answer;
    }
}
```

---

# Complexity

```text id="a1x050"
Time : O(n)

Space : O(1)
```

---

# Hybrid Family Revision Sheet

## Positive Numbers

Think:

```text id="a1x051"
Sliding Window
```

Problems:

* Minimum Size Subarray Sum
* Longest Subarray ≤ K
* Max Consecutive Ones
* Nice Subarrays

---

## Fixed Size K

Think:

```text id="a1x052"
Fixed Window
```

Problems:

* Maximum Average Subarray I
* Maximum Vowels
* Count Good Substrings

---

## Negative Numbers Exist

Think:

```text id="a1x053"
Prefix Sum
```

Problems:

* Subarray Sum Equals K
* Maximum Size Subarray Sum Equals K
* Continuous Subarray Sum

---

# Interview Decision Tree

### Question 1

Array contains only:

```text id="a1x054"
Positive Numbers?
```

Yes:

```text id="a1x055"
Sliding Window
```

---

### Question 2

Need:

```text id="a1x056"
Range Sum Query?
```

Yes:

```text id="a1x057"
Prefix Sum
```

---

### Question 3

Need:

```text id="a1x058"
Subarray Sum = K
```

Yes:

```java id="a1x059"
prefixSum - k
```

---

### Question 4

Need:

```text id="a1x060"
Exactly K?
```

Yes:

```java id="a1x061"
AtMost(K)
-
AtMost(K-1)
```

---

# Golden Rule

Strong interview candidates don't ask:

```text id="a1x062"
How do I solve this?
```

They ask:

```text id="a1x063"
Which pattern owns this problem?
```

For these four problems:

```text id="a1x064"
Sliding Window
```

owns them much more naturally than Prefix Sum.
