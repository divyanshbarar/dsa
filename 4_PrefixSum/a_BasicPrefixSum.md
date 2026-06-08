# Prefix Sum Pattern - Part 1

# Basic Prefix Sum Family

---

# Why Prefix Sum Exists

Many array problems repeatedly ask:

```text
What is the sum between
index L and index R?
```

Without Prefix Sum:

```text
L → R
```

must be traversed every time.

With Prefix Sum:

```text
Answer in O(1)
```

after preprocessing.

---

# Core Prefix Sum Formula

Given:

```text
nums

1 2 3 4 5
```

Build:

```text
prefix

1 3 6 10 15
```

Meaning:

```text
prefix[i]
=
sum of elements
from 0 to i
```

Example:

```text
prefix[3]

=
1+2+3+4

=
10
```

---

# Most Important Formula

Need:

```text
sum(2,4)

=
3+4+5
```

Use:

```text
prefix[4]
-
prefix[1]

=
15 - 3

=
12
```

Formula:

```java
sum(l,r)
=
prefix[r]
-
prefix[l-1]
```

This formula powers most Prefix Sum questions.

---

# Problem 1: Range Sum Query – Immutable

## LeetCode 303

---

# Problem Explanation

You are given an integer array.

Many queries arrive:

```text
sumRange(left,right)
```

Example:

```text
nums

[-2,0,3,-5,2,-1]
```

Query:

```text
sumRange(0,2)

=
-2+0+3

=
1
```

Need to answer many such queries efficiently.

---

# What Is The Interviewer Testing?

Most candidates do:

```java
for(int i=left;i<=right;i++){
    sum+=nums[i];
}
```

for every query.

Interviewer wants:

```text
Can you preprocess once
and answer instantly?
```

---

# Prefix Sum Visualization

```text
nums

Index

0  1  2   3  4  5

-2 0  3  -5  2 -1
```

Build Prefix:

```text
prefix

-2 -2 1 -4 -2 -3
```

Now:

```text
sum(2,4)

=
3 + (-5) + 2

=
0
```

Using Prefix:

```text
prefix[4]
-
prefix[1]

=
(-2)
-
(-2)

=
0
```

---

# Pattern Recognition Clues

### Clue 1

Many range sum queries.

### Clue 2

Array never changes.

### Clue 3

Need repeated lookups.

### Clue 4

Preprocessing allowed.

Think:

```text
Prefix Sum
```

---

# Brute Force

## Idea

For every query:

```java
for(int i=l;i<=r;i++){
    sum += nums[i];
}
```

---

## Complexity

```text
Preprocessing : O(1)

Query Time : O(n)

Space : O(1)
```

---

# Why Can We Do Better?

Notice:

```text
sum(0,5)

sum(0,4)

sum(0,3)
```

share most calculations.

Let's store them.

---

# Optimal Approach

Build Prefix Array.

```java
prefix[i]
=
prefix[i-1]
+
nums[i]
```

Query:

```java
prefix[right]
-
prefix[left-1]
```

---

# Dry Run

```text
nums

1 2 3 4 5
```

Build:

```text
prefix

1 3 6 10 15
```

Need:

```text
sum(1,3)
```

Answer:

```text
10 - 1

=
9
```

Which is:

```text
2+3+4
=
9
```

---

# What To Say In Interview

Since the array is immutable, I can preprocess cumulative sums once.

Each query then becomes the difference of two prefix sums, reducing query complexity from O(n) to O(1).

---

# Optimal Java Code

```java
class NumArray {

    private int[] prefix;

    public NumArray(int[] nums) {

        prefix = new int[nums.length];

        prefix[0] = nums[0];

        for(int i = 1;
            i < nums.length;
            i++){

            prefix[i] =
                    prefix[i-1]
                    + nums[i];
        }
    }

    public int sumRange(
            int left,
            int right
    ) {

        if(left == 0){
            return prefix[right];
        }

        return prefix[right]
                -
                prefix[left-1];
    }
}
```

---

# Complexity

```text
Build : O(n)

Query : O(1)

Space : O(n)
```

---

# Similar Problems

* Range Sum Query 2D
* Prefix Sum Matrix
* Subarray Sum Equals K

---

# Follow-Up

### What If Array Changes?

Example:

```text
Update Index 5
```

Now Prefix Sum becomes invalid.

Need:

```text
Fenwick Tree (BIT)

or

Segment Tree
```

---

# Problem 2: Find Pivot Index

## LeetCode 724

---

# Problem Explanation

Find an index where:

```text
Left Sum
=
Right Sum
```

Example:

```text
nums

[1,7,3,6,5,6]
```

At index:

```text
3
```

Left:

```text
1+7+3

=
11
```

Right:

```text
5+6

=
11
```

Answer:

```text
3
```

---

# What Is The Interviewer Testing?

Can you convert:

```text
Repeated Sum Calculation
```

into:

```text
Constant Time Lookup
```

using Prefix Sum?

---

# Pattern Recognition Clues

### Clue 1

Left sum.

### Clue 2

Right sum.

### Clue 3

Need efficient computation.

Think:

```text
Prefix Sum
```

---

# Brute Force

For every index:

Calculate:

```text
Left Sum
```

and

```text
Right Sum
```

again.

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

1 7 3 6 5 6
```

Total:

```text
28
```

At index:

```text
6
```

Left:

```text
11
```

Right:

```text
28 - 11 - 6

=
11
```

Balanced.

---

# Key Observation

If:

```text
totalSum
```

is known,

Then:

```java
rightSum
=
totalSum
-
leftSum
-
nums[i]
```

No need for another loop.

---

# Optimal Approach

Maintain:

```java
leftSum
```

while iterating.

Compute:

```java
rightSum
=
totalSum
-
leftSum
-
nums[i]
```

Check equality.

---

# Dry Run

```text
nums

1 7 3 6 5 6

total = 28
```

Index 0:

```text
left = 0

right = 27
```

Not Equal.

---

Index 1:

```text
left = 1

right = 20
```

Not Equal.

---

Index 3:

```text
left = 11

right = 11
```

Found.

---

# What To Say In Interview

Instead of recalculating left and right sums repeatedly, I maintain a running left sum and derive the right sum using the total array sum.

This reduces complexity from O(n²) to O(n).

---

# Optimal Java Code

```java
class Solution {

    public int pivotIndex(
            int[] nums
    ) {

        int totalSum = 0;

        for(int num : nums){
            totalSum += num;
        }

        int leftSum = 0;

        for(int i = 0;
            i < nums.length;
            i++){

            int rightSum =
                    totalSum
                    -
                    leftSum
                    -
                    nums[i];

            if(leftSum == rightSum){
                return i;
            }

            leftSum += nums[i];
        }

        return -1;
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

* Equilibrium Index
* Product Except Self
* Left Right Sum Difference

---

# Interview Follow-Up

### Can We Solve Without Prefix Array?

Yes.

This problem only needs:

```text
Running Prefix Sum
```

not a complete prefix array.

This is a common optimization interviewers like to discuss.

---

# Basic Prefix Sum Revision Sheet

## Build Prefix

```java
prefix[i]
=
prefix[i-1]
+
nums[i];
```

---

## Range Sum Formula

```java
sum(l,r)
=
prefix[r]
-
prefix[l-1];
```

---

## Running Prefix Pattern

```java
leftSum += nums[i];
```

Used In:

* Pivot Index
* Equilibrium Index
* Product Problems

---

# Golden Rule

Whenever interviewer says:

```text
Range Sum

Left Sum

Right Sum

Repeated Sum Queries
```

Immediately think:

```text
Prefix Sum
```

before considering nested loops.
