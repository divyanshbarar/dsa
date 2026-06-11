# Binary Search Pattern - Part 5

# Peak Family

---

# Why This Family Matters

Until now Binary Search was used for:

```text id="p501"
Finding Target

Finding Boundary

Finding Position
```

This family introduces a completely new idea:

```text id="p502"
Finding Direction
```

You don't know where the answer is.

You only know:

```text id="p503"
Which direction
moves you closer
to the answer.
```

This is one of the most powerful Binary Search patterns.

---

# Core Insight

Classic Binary Search:

```java id="p504"
nums[mid]
== target
```

Peak Binary Search:

```java id="p505"
nums[mid]

vs

nums[mid+1]
```

---

# The Key Observation

Suppose:

```text id="p506"
1 2 3 4 5 3 1
```

At:

```text id="p507"
mid = 4
```

Compare:

```text id="p508"
5

and

3
```

Since:

```text id="p509"
5 > 3
```

You're on:

```text id="p510"
Descending Slope
```

Peak lies:

```text id="p511"
Left Side
```

including mid.

---

# Problem 12: Find Peak Element

## LeetCode 162

---

# Problem Explanation

Given:

```text id="p512"
1 2 3 1
```

Peak:

```text id="p513"
3
```

Index:

```text id="p514"
2
```

---

Peak Definition

```java id="p515"
nums[i]
>
nums[i-1]

AND

nums[i]
>
nums[i+1]
```

---

# What Is The Interviewer Testing?

Can you binary search without searching for a target?

---

# Pattern Recognition Clues

### Clue 1

Peak.

### Clue 2

Local Maximum.

### Clue 3

Mountain Shape.

Think:

```text id="p516"
Slope Analysis
```

---

# Brute Force

Check every element.

---

## Complexity

```text id="p517"
O(n)
```

---

# Key Observation

Compare:

```java id="p518"
nums[mid]

nums[mid+1]
```

---

### Ascending

```java id="p519"
nums[mid]
<
nums[mid+1]
```

Example:

```text id="p520"
1 2 3 4 5
      ^
```

Peak must exist:

```text id="p521"
Right Side
```

Move:

```java id="p522"
left = mid + 1
```

---

### Descending

```java id="p523"
nums[mid]
>
nums[mid+1]
```

Example:

```text id="p524"
5 4 3 2 1
^
```

Peak exists:

```text id="p525"
Left Side
```

Move:

```java id="p526"
right = mid
```

---

# Why Does This Work?

Because:

```text id="p527"
An ascending slope
must eventually end
at a peak.
```

---

# Dry Run

```text id="p528"
1 2 3 1
```

---

Mid:

```text id="p529"
2
```

Value:

```text id="p530"
2
```

Compare:

```text id="p531"
3
```

Ascending.

Move right.

---

Now:

```text id="p532"
3 1
```

Descending.

Move left.

---

Answer:

```text id="p533"
3
```

---

# What To Say In Interview

Instead of searching for a specific value, I'll use the slope around the midpoint.

If I'm climbing upward, a peak must exist on the right.

If I'm descending, a peak must exist on the left.

---

# Optimal Java Code

```java id="p534"
class Solution {

    public int findPeakElement(
            int[] nums
    ) {

        int left = 0;
        int right =
                nums.length - 1;

        while(left < right){

            int mid =
                left +
                (right-left)/2;

            if(nums[mid]
                <
               nums[mid+1]){

                left = mid + 1;

            }else{

                right = mid;
            }
        }

        return left;
    }
}
```

---

# Complexity

```text id="p535"
Time :

O(log n)

Space :

O(1)
```

---

# Problem 13: Peak Index In Mountain Array

## LeetCode 852

---

# Problem Explanation

Given:

```text id="p536"
0 2 5 7 4 3 1
```

Find:

```text id="p537"
Peak Index
```

Answer:

```text id="p538"
3
```

Value:

```text id="p539"
7
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text id="p540"
Exactly Same Pattern
```

as Find Peak Element?

---

# Key Observation

Mountain array guarantees:

```text id="p541"
Exactly One Peak
```

---

# Visualization

```text id="p542"
0 2 5 7 4 3 1
      ^
```

Peak.

---

# What To Say In Interview

This is identical to Find Peak Element except the problem guarantees a single mountain peak.

Therefore the same slope-based binary search works.

---

# Optimal Java Code

```java id="p543"
class Solution {

    public int peakIndexInMountainArray(
            int[] arr
    ) {

        int left = 0;
        int right =
                arr.length - 1;

        while(left < right){

            int mid =
                left +
                (right-left)/2;

            if(arr[mid]
                <
               arr[mid+1]){

                left = mid + 1;

            }else{

                right = mid;
            }
        }

        return left;
    }
}
```

---

# Complexity

```text id="p544"
Time :

O(log n)

Space :

O(1)
```

---

# Problem 14: Find In Mountain Array

## LeetCode 1095

---

# Problem Explanation

Mountain Array:

```text id="p545"
1 2 3 5 3 1
```

Target:

```text id="p546"
3
```

Answer:

```text id="p547"
2
```

Need:

```text id="p548"
Smallest Index
```

containing target.

---

# What Is The Interviewer Testing?

Can you combine:

```text id="p549"
Peak Search

+

Binary Search
```

?

---

# Pattern Recognition Clues

### Clue 1

Mountain Array.

### Clue 2

Need search.

### Clue 3

Peak exists.

Think:

```text id="p550"
3 Binary Searches
```

---

# Master Plan

### Step 1

Find Peak.

```java id="p551"
O(log n)
```

---

### Step 2

Binary Search Left Side.

Ascending.

```java id="p552"
O(log n)
```

---

### Step 3

Binary Search Right Side.

Descending.

```java id="p553"
O(log n)
```

---

# Visualization

```text id="p554"
1 2 3 5 3 1
      ^
```

Peak:

```text id="p555"
5
```

---

Search:

```text id="p556"
1 2 3 5
```

Found:

```text id="p557"
3
```

Return immediately.

---

# What To Say In Interview

A mountain array consists of two sorted halves.

After locating the peak, I can binary search the increasing half and then the decreasing half.

---

# Ascending Binary Search

```java id="p558"
while(left <= right){

    int mid =
        left +
        (right-left)/2;

    if(arr[mid] == target){
        return mid;
    }

    if(arr[mid] < target){

        left = mid + 1;

    }else{

        right = mid - 1;
    }
}
```

---

# Descending Binary Search

```java id="p559"
while(left <= right){

    int mid =
        left +
        (right-left)/2;

    if(arr[mid] == target){
        return mid;
    }

    if(arr[mid] > target){

        left = mid + 1;

    }else{

        right = mid - 1;
    }
}
```

---

# Optimal Java Code

```java id="p560"
class Solution {

    public int findInMountainArray(
            int target,
            MountainArray arr
    ) {

        int peak =
                findPeak(arr);

        int leftResult =
                binarySearchAsc(
                    arr,
                    target,
                    0,
                    peak
                );

        if(leftResult != -1){

            return leftResult;
        }

        return binarySearchDesc(
                arr,
                target,
                peak + 1,
                arr.length()-1
        );
    }
}
```

---

# Complexity

```text id="p561"
Peak Search :

O(log n)

Ascending Search :

O(log n)

Descending Search :

O(log n)

Total :

O(log n)
```

---

# Peak Family Revision Sheet

## Find Peak Element

Goal:

```text id="p562"
Find Any Peak
```

Use:

```java id="p563"
nums[mid]

vs

nums[mid+1]
```

---

## Peak Index In Mountain Array

Goal:

```text id="p564"
Find Mountain Peak
```

Same Template.

---

## Find In Mountain Array

Goal:

```text id="p565"
Search Target
```

Steps:

```text id="p566"
Find Peak

Search Left

Search Right
```

---

# Peak Family Cheat Sheet

### Ascending Slope

```java id="p567"
nums[mid]
<
nums[mid+1]
```

Move:

```java id="p568"
left = mid + 1
```

---

### Descending Slope

```java id="p569"
nums[mid]
>
nums[mid+1]
```

Move:

```java id="p570"
right = mid
```

---

# The Big Insight

Classic Binary Search asks:

```text id="p571"
Where is target?
```

Peak Binary Search asks:

```text id="p572"
Which direction
goes uphill?
```

You are not searching for a value.

You are searching for:

```text id="p573"
The Peak
```

using slope information.

---

# Golden Rule

Whenever interviewer says:

```text id="p574"
Peak

Mountain

Local Maximum

Bitonic Array
```

Think:

```java id="p575"
nums[mid]

vs

nums[mid+1]
```

before thinking about target-based binary search.
