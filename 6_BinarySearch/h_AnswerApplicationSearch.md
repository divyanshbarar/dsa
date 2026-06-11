# Binary Search Pattern - Part 8

# Binary Search On Answer (Advanced)

# Split Array Largest Sum

---

# Why This Problem Matters

This is one of the most important interview questions.

Many famous problems are actually the same pattern:

```text id="s801"
Split Array Largest Sum

Allocate Books

Painter Partition

Load Balancing

Work Distribution
```

Master this once.

Solve all of them.

---

# Problem 18: Split Array Largest Sum

## LeetCode 410

---

# Problem Explanation

Given:

```text id="s802"
nums

[7,2,5,10,8]
```

Need:

```text id="s803"
k = 2
```

Split array into:

```text id="s804"
Exactly K Parts
```

Minimize:

```text id="s805"
Largest Subarray Sum
```

---

# Example

Split:

```text id="s806"
[7,2,5]

[10,8]
```

Largest sum:

```text id="s807"
18
```

Answer:

```text id="s808"
18
```

---

# What Is The Interviewer Testing?

Most candidates try:

```text id="s809"
DP
```

Interviewer wants:

```text id="s810"
Binary Search On Answer
```

---

# Pattern Recognition Clues

### Clue 1

Minimize Maximum.

### Clue 2

Split Array.

### Clue 3

Allocate Work.

### Clue 4

Distribute Load.

Think:

```text id="s811"
Binary Search On Answer
```

---

# Why Brute Force Fails

Try every split.

Example:

```text id="s812"
n = 1000
```

Number of partitions:

```text id="s813"
Huge
```

Impossible.

---

# Key Insight

Don't search:

```text id="s814"
Where To Split
```

Search:

```text id="s815"
What Is The Answer?
```

---

# Search Space

Minimum possible answer:

```text id="s816"
max(nums)
```

Because:

```text id="s817"
Largest element
must belong
to some partition
```

---

Maximum possible answer:

```text id="s818"
sum(nums)
```

Put everything together.

---

# Example

```text id="s819"
7 2 5 10 8
```

Search space:

```text id="s820"
10

to

32
```

---

# The Magic Question

Suppose answer is:

```text id="s821"
18
```

Can we verify?

YES.

---

# The Can Function

Question:

```text id="s822"
Can I split
the array
into at most k parts

such that

every partition sum
<= limit ?
```

---

# Example

Limit:

```text id="s823"
18
```

Array:

```text id="s824"
7 2 5 10 8
```

---

Build partition:

```text id="s825"
7+2+5
=
14
```

Add:

```text id="s826"
10
```

Would become:

```text id="s827"
24
```

Too large.

Create new partition.

---

Partitions:

```text id="s828"
[7,2,5]

[10,8]
```

Total:

```text id="s829"
2 partitions
```

Valid.

---

# Monotonic Property

Limit:

```text id="s830"
15
```

Need:

```text id="s831"
3 partitions
```

Invalid.

---

Limit:

```text id="s832"
18
```

Need:

```text id="s833"
2 partitions
```

Valid.

---

Limit:

```text id="s834"
25
```

Need:

```text id="s835"
2 partitions
```

Valid.

---

Looks like:

```text id="s836"
F F F T T T T
```

Need:

```text id="s837"
First True
```

---

# Dry Run

Array:

```text id="s838"
7 2 5 10 8
```

k:

```text id="s839"
2
```

---

Try:

```text id="s840"
21
```

Valid.

Search smaller.

---

Try:

```text id="s841"
15
```

Invalid.

Search larger.

---

Try:

```text id="s842"
18
```

Valid.

Search smaller.

---

Try:

```text id="s843"
17
```

Invalid.

---

Answer:

```text id="s844"
18
```

---

# What To Say In Interview

The answer is monotonic.

If a partition limit works, every larger limit also works.

Therefore I can binary search the smallest valid partition sum.

---

# Optimal Java Code

```java id="s845"
class Solution {

    public int splitArray(
            int[] nums,
            int k
    ) {

        int left = 0;
        int right = 0;

        for(int num : nums){

            left =
                Math.max(left, num);

            right += num;
        }

        int answer = right;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(canSplit(
                    nums,
                    k,
                    mid
            )){

                answer = mid;

                right = mid - 1;

            }else{

                left = mid + 1;
            }
        }

        return answer;
    }

    private boolean canSplit(
            int[] nums,
            int k,
            int limit
    ){

        int partitions = 1;

        int currentSum = 0;

        for(int num : nums){

            if(currentSum
                + num
                > limit){

                partitions++;

                currentSum = 0;
            }

            currentSum += num;
        }

        return partitions <= k;
    }
}
```

---

# Complexity

```text id="s846"
Time :

O(n log(sum))
```

---

# Interview Insight

Notice how similar this is to:

```text id="s847"
Ship Packages
```

---

Ship Packages:

```text id="s848"
Can ship
within D days?
```

---

Split Array:

```text id="s849"
Can split
within K partitions?
```

---

Same structure.

Different wording.

---

# Hidden Variants

## Allocate Books

Books:

```text id="s850"
Pages
```

Students:

```text id="s851"
Partitions
```

Need:

```text id="s852"
Minimum Maximum Pages
```

Same problem.

---

## Painter Partition

Boards:

```text id="s853"
Array Elements
```

Painters:

```text id="s854"
Partitions
```

Need:

```text id="s855"
Minimum Maximum Work
```

Same problem.

---

## Load Balancing

Servers:

```text id="s856"
Partitions
```

Jobs:

```text id="s857"
Array Elements
```

Need:

```text id="s858"
Minimum Maximum Load
```

Same problem.

---

# Comparison With Earlier Problems

| Problem       | Answer Space | can(mid)            |
| ------------- | ------------ | ------------------- |
| Koko          | Speed        | Finish in h hours   |
| Ship Packages | Capacity     | Ship in d days      |
| Bouquets      | Days         | Make bouquets       |
| Split Array   | Largest Sum  | Use <= k partitions |

---

# Binary Search On Answer Master Checklist

When interviewer says:

```text id="s859"
Minimum Speed
```

Think:

```java id="s860"
can(speed)
```

---

When interviewer says:

```text id="s861"
Minimum Capacity
```

Think:

```java id="s862"
can(capacity)
```

---

When interviewer says:

```text id="s863"
Minimum Days
```

Think:

```java id="s864"
can(days)
```

---

When interviewer says:

```text id="s865"
Minimum Maximum
```

Think:

```java id="s866"
can(limit)
```

---

# The Most Important Binary Search Pattern

If you remember only one thing from Binary Search:

```text id="s867"
Can(mid)?
```

Because this single transformation solves:

* Koko
* Ship Packages
* Bouquets
* Split Array
* Allocate Books
* Painter Partition
* Aggressive Cows

and dozens of FAANG interview questions.

---

# Binary Search On Answer Template

Memorize this.

```java id="s868"
while(left <= right){

    int mid =
        left +
        (right-left)/2;

    if(can(mid)){

        answer = mid;

        right = mid - 1;

    }else{

        left = mid + 1;
    }
}
```
