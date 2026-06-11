# Binary Search Pattern - Part 10

# Hard Family

# Median Of Two Sorted Arrays

---

# Why This Problem Matters

This is one of the most famous interview questions ever asked.

Appears frequently in:

* Google
* Meta
* Uber
* LinkedIn
* Amazon

Many candidates memorize it.

Very few actually understand it.

---

# Problem 20: Median Of Two Sorted Arrays

## LeetCode 4

---

# Problem Explanation

Given:

```text id="m1001"
nums1 = [1,3]

nums2 = [2]
```

Combined:

```text id="m1002"
1 2 3
```

Median:

```text id="m1003"
2
```

---

Example 2

```text id="m1004"
nums1 = [1,2]

nums2 = [3,4]
```

Combined:

```text id="m1005"
1 2 3 4
```

Median:

```text id="m1006"
(2+3)/2

=
2.5
```

---

# What Is The Interviewer Testing?

Most candidates do:

```text id="m1007"
Merge Arrays
```

Interviewer wants:

```text id="m1008"
O(log(min(n,m)))
```

---

# Brute Force

Merge both arrays.

Find median.

---

# Complexity

```text id="m1009"
Time :

O(n+m)
```

Rejected.

---

# The Most Important Observation

Median splits data into:

```text id="m1010"
Left Half

Right Half
```

such that:

```text id="m1011"
All Left

<=

All Right
```

---

# Visualization

Arrays:

```text id="m1012"
1 3

2
```

Combined:

```text id="m1013"
1 | 2 3
```

or

```text id="m1014"
1 2 | 3
```

Median depends only on:

```text id="m1015"
Partition
```

not full merge.

---

# New Binary Search Concept

Until now we searched:

```text id="m1016"
Index

Answer

Value
```

Now we search:

```text id="m1017"
Partition Position
```

---

# The Goal

Find partition:

```text id="m1018"
Left Half Size

=

Right Half Size
```

and

```text id="m1019"
Max Left
<=
Min Right
```

---

# Why Binary Search?

Choose partition in:

```text id="m1020"
Smaller Array
```

Then derive partition in:

```text id="m1021"
Larger Array
```

---

# Visualization

nums1

```text id="m1022"
1 3
```

Partition:

```text id="m1023"
1 | 3
```

---

nums2

```text id="m1024"
2
```

Partition:

```text id="m1025"
| 2
```

---

Combined:

```text id="m1026"
1

|

2 3
```

Invalid.

Need larger left side.

---

Try:

```text id="m1027"
1 3

|

2
```

Combined:

```text id="m1028"
1 2

|

3
```

Valid.

---

# The Four Boundary Values

Suppose partition chosen.

Need:

```java id="m1029"
leftA
rightA

leftB
rightB
```

---

Example

```text id="m1030"
1 3

2
```

Partition:

```text id="m1031"
1 | 3

2 |
```

Values:

```text id="m1032"
leftA = 1

rightA = 3

leftB = 2

rightB = INF
```

---

# Valid Partition Condition

This is the entire problem.

```java id="m1033"
leftA <= rightB

AND

leftB <= rightA
```

If true:

```text id="m1034"
Partition Found
```

---

# Why?

Because:

```text id="m1035"
Everything Left

<=

Everything Right
```

---

# What If Invalid?

---

### Case 1

```java id="m1036"
leftA > rightB
```

Example:

```text id="m1037"
10 | 20

1 | 5
```

Too many elements from A.

Move:

```java id="m1038"
right = partitionA - 1
```

---

### Case 2

```java id="m1039"
leftB > rightA
```

Need more elements from A.

Move:

```java id="m1040"
left = partitionA + 1
```

---

# Dry Run

nums1

```text id="m1041"
1 3
```

nums2

```text id="m1042"
2
```

---

Always binary search smaller array.

Swap.

---

A:

```text id="m1043"
2
```

B:

```text id="m1044"
1 3
```

---

Partition A:

```text id="m1045"
0
```

Partition B:

```text id="m1046"
2
```

Invalid.

---

Partition A:

```text id="m1047"
1
```

Partition B:

```text id="m1048"
1
```

Valid.

---

Median:

```text id="m1049"
max(
 leftA,
 leftB
)
```

=

```text id="m1050"
2
```

---

# Odd Length Formula

If:

```text id="m1051"
Total Length
is odd
```

Median:

```java id="m1052"
max(
 leftA,
 leftB
)
```

---

# Even Length Formula

If:

```text id="m1053"
Total Length
is even
```

Median:

```java id="m1054"
(
max(leftA,leftB)
+
min(rightA,rightB)
)
/2.0
```

---

# What To Say In Interview

Instead of merging arrays, I'll binary search a partition in the smaller array.

The correct partition ensures all elements on the left side are less than or equal to all elements on the right side.

Once that condition is satisfied, the median can be computed directly from the partition boundaries.

---

# Optimal Java Code

```java id="m1055"
class Solution {

    public double findMedianSortedArrays(
            int[] nums1,
            int[] nums2
    ) {

        if(nums1.length
            > nums2.length){

            return findMedianSortedArrays(
                    nums2,
                    nums1
            );
        }

        int n1 = nums1.length;
        int n2 = nums2.length;

        int left = 0;
        int right = n1;

        while(left <= right){

            int cut1 =
                left +
                (right-left)/2;

            int cut2 =
                (n1+n2+1)/2
                - cut1;

            int left1 =
                cut1 == 0
                ? Integer.MIN_VALUE
                : nums1[cut1-1];

            int right1 =
                cut1 == n1
                ? Integer.MAX_VALUE
                : nums1[cut1];

            int left2 =
                cut2 == 0
                ? Integer.MIN_VALUE
                : nums2[cut2-1];

            int right2 =
                cut2 == n2
                ? Integer.MAX_VALUE
                : nums2[cut2];

            if(left1 <= right2
                &&
               left2 <= right1){

                if((n1+n2)%2 == 0){

                    return (
                        Math.max(
                            left1,
                            left2
                        )
                        +
                        Math.min(
                            right1,
                            right2
                        )
                    ) / 2.0;
                }

                return Math.max(
                        left1,
                        left2
                );
            }

            if(left1 > right2){

                right =
                        cut1 - 1;

            }else{

                left =
                        cut1 + 1;
            }
        }

        return 0;
    }
}
```

---

# Complexity

```text id="m1056"
Time :

O(log(min(n,m)))
```

---

```text id="m1057"
Space :

O(1)
```

---

# Why This Is Hard

Because Binary Search isn't being used on:

```text id="m1058"
Values
```

or

```text id="m1059"
Answers
```

It's used on:

```text id="m1060"
Partition Position
```

This is a completely different mindset.

---

# Median Interview Cheat Sheet

## Goal

Find:

```text id="m1061"
Correct Partition
```

---

## Valid Condition

```java id="m1062"
leftA <= rightB

&&

leftB <= rightA
```

---

## Odd Length

Answer:

```java id="m1063"
max(
 leftA,
 leftB
)
```

---

## Even Length

Answer:

```java id="m1064"
(
 max(leftA,leftB)
 +
 min(rightA,rightB)
)/2.0
```

---

# Complete Binary Search Handbook

## Part 1

Classic Search

* Binary Search
* Search Insert Position
* Guess Number

---

## Part 2

Boundary Search

* First Bad Version
* First Occurrence
* Last Occurrence
* Search Range

---

## Part 3

Matrix Family

* Search Matrix
* Search Matrix II

---

## Part 4

Rotated Arrays

* Search Rotated Array
* Find Minimum Rotated Array

---

## Part 5

Peak Family

* Find Peak Element
* Mountain Array

---

## Part 6

Binary Search On Answer

* Koko Eating Bananas

---

## Part 7

Applications

* Ship Packages
* Bouquets

---

## Part 8

Advanced Applications

* Split Array Largest Sum
* Allocate Books
* Painter Partition

---

## Part 9

Value Space Search

* Kth Smallest In Matrix

---

## Part 10

Partition Search

* Median Of Two Sorted Arrays

---

# Binary Search Master Decision Tree

Question asks:

```text id="m1065"
Find Target?
```

Use:

```text id="m1066"
Classic Search
```

---

Question asks:

```text id="m1067"
First/Last?
```

Use:

```text id="m1068"
Boundary Search
```

---

Question asks:

```text id="m1069"
Rotated Array?
```

Use:

```text id="m1070"
One Half Sorted
```

---

Question asks:

```text id="m1071"
Peak?
```

Use:

```text id="m1072"
Slope Search
```

---

Question asks:

```text id="m1073"
Minimum Speed
Capacity
Days
Limit
```

Use:

```text id="m1074"
Can(mid)
```

---

Question asks:

```text id="m1075"
Kth Smallest
```

Use:

```text id="m1076"
Value Space Search
```

---

Question asks:

```text id="m1077"
Median
```

Use:

```text id="m1078"
Partition Search
```

---

# Final Binary Search Rule

Binary Search is not about sorted arrays.

Binary Search is about finding a monotonic structure.

That structure can be:

```text id="m1079"
Indices

Boundaries

Answers

Values

Partitions

Directions
```

The moment you identify the monotonic property, the solution usually becomes a binary search problem.
