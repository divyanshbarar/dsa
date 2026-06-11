# Binary Search Pattern - Part 2

# Boundary Search Family

---

# Why This Family Matters

Most candidates know:

```java id="b201"
Find Target
```

Very few understand:

```java id="b202"
Find First Target

Find Last Target

Find First True

Find Last False
```

This family is the foundation of:

* First Bad Version
* Search Range
* Lower Bound
* Upper Bound
* Koko Eating Bananas
* Ship Packages

Almost every advanced Binary Search problem.

---

# The Biggest Binary Search Upgrade

Classic Search:

```java id="b203"
Find Target
```

Boundary Search:

```java id="b204"
Find Boundary
```

Huge difference.

---

# Mental Model

Imagine:

```text id="b205"
F F F F T T T T
```

Need:

```text id="b206"
First T
```

Binary Search can find:

```text id="b207"
Transition Point
```

---

# First True Template

```java id="b208"
int answer = -1;

while(left <= right){

    int mid =
        left + (right-left)/2;

    if(condition(mid)){

        answer = mid;

        right = mid - 1;

    }else{

        left = mid + 1;
    }
}
```

---

# Last True Template

```java id="b209"
int answer = -1;

while(left <= right){

    int mid =
        left + (right-left)/2;

    if(condition(mid)){

        answer = mid;

        left = mid + 1;

    }else{

        right = mid - 1;
    }
}
```

---

# Problem 4: First Bad Version

## LeetCode 278

---

# Problem Explanation

Versions:

```text id="b210"
1 2 3 4 5 6 7
```

Suppose:

```text id="b211"
4
```

is first bad version.

Then:

```text id="b212"
Good Good Good

Bad Bad Bad Bad
```

Need:

```text id="b213"
First Bad Version
```

Answer:

```text id="b214"
4
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="b215"
Monotonic Property
```

?

---

# Pattern Recognition Clues

### Clue 1

First bad.

### Clue 2

After becoming bad:

```text id="b216"
Everything remains bad
```

### Clue 3

Need first occurrence.

Think:

```text id="b217"
First True
```

---

# Visualization

```text id="b218"
F F F T T T T
```

Need:

```text id="b219"
First T
```

---

# Brute Force

Check:

```java id="b220"
isBadVersion(i)
```

from:

```text id="b221"
1 → n
```

---

# Complexity

```text id="b222"
Time : O(n)
```

---

# Key Observation

Whenever:

```java id="b223"
isBadVersion(mid)
```

is true,

mid could be answer.

Search left.

---

# Dry Run

```text id="b224"
1 2 3 4 5 6 7
```

Mid:

```text id="b225"
4
```

Bad.

Store:

```text id="b226"
answer = 4
```

Search left.

---

Mid:

```text id="b227"
2
```

Good.

Search right.

---

Eventually:

```text id="b228"
4
```

---

# What To Say In Interview

The versions form a monotonic sequence: once a version becomes bad, all later versions are bad.

Therefore I can binary search for the first bad version.

---

# Optimal Java Code

```java id="b229"
public class Solution
        extends VersionControl {

    public int firstBadVersion(
            int n
    ) {

        int left = 1;
        int right = n;

        int answer = n;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(isBadVersion(mid)){

                answer = mid;

                right = mid - 1;

            }else{

                left = mid + 1;
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text id="b230"
Time :

O(log n)

Space :

O(1)
```

---

# Interview Insight

This is the first:

```text id="b231"
Find First True
```

problem.

Memorize it.

---

# Problem 5: First Occurrence Of Element

---

# Problem Explanation

Given:

```text id="b232"
1 2 2 2 3 4
```

Target:

```text id="b233"
2
```

Return:

```text id="b234"
1
```

not:

```text id="b235"
2

or

3
```

Need:

```text id="b236"
First Occurrence
```

---

# What Is The Interviewer Testing?

Can you continue searching after finding target?

Most candidates stop too early.

---

# Pattern Recognition Clues

### Clue 1

Duplicates.

### Clue 2

First occurrence.

### Clue 3

Leftmost index.

Think:

```text id="b237"
Boundary Search
```

---

# Visualization

```text id="b238"
1 2 2 2 3 4
```

Need:

```text id="b239"
Leftmost 2
```

---

# Key Insight

When:

```java id="b240"
nums[mid] == target
```

Don't stop.

Store answer.

Search left.

---

# Dry Run

```text id="b241"
1 2 2 2 3 4
```

Mid:

```text id="b242"
2
```

Found.

Store.

Search left.

---

Eventually:

```text id="b243"
index = 1
```

---

# What To Say In Interview

Finding the first occurrence is a boundary-search problem. Even after finding the target, I continue searching left for a smaller valid index.

---

# Optimal Java Code

```java id="b244"
class Solution {

    public int firstOccurrence(
            int[] nums,
            int target
    ) {

        int left = 0;
        int right =
                nums.length - 1;

        int answer = -1;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(nums[mid]
                == target){

                answer = mid;

                right = mid - 1;

            }else if(nums[mid]
                        < target){

                left = mid + 1;

            }else{

                right = mid - 1;
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text id="b245"
Time :

O(log n)

Space :

O(1)
```

---

# Problem 6: Last Occurrence Of Element

---

# Problem Explanation

Given:

```text id="b246"
1 2 2 2 3 4
```

Target:

```text id="b247"
2
```

Return:

```text id="b248"
3
```

Need:

```text id="b249"
Rightmost Occurrence
```

---

# Key Insight

After finding target:

Search:

```text id="b250"
Right
```

instead of left.

---

# Visualization

```text id="b251"
1 2 2 2 3 4
```

Need:

```text id="b252"
Last 2
```

---

# What To Say In Interview

This is the mirror image of first occurrence. Whenever I find the target, I record it and continue searching right.

---

# Optimal Java Code

```java id="b253"
class Solution {

    public int lastOccurrence(
            int[] nums,
            int target
    ) {

        int left = 0;
        int right =
                nums.length - 1;

        int answer = -1;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(nums[mid]
                == target){

                answer = mid;

                left = mid + 1;

            }else if(nums[mid]
                        < target){

                left = mid + 1;

            }else{

                right = mid - 1;
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text id="b254"
Time :

O(log n)

Space :

O(1)
```

---

# Problem 7: Find First And Last Position

## LeetCode 34

---

# Problem Explanation

Given:

```text id="b255"
5 7 7 8 8 10
```

Target:

```text id="b256"
8
```

Output:

```text id="b257"
[3,4]
```

---

# What Is The Interviewer Testing?

Can you reuse:

```text id="b258"
First Occurrence

+
Last Occurrence
```

instead of inventing a new solution?

---

# Pattern Recognition Clues

### Clue 1

Sorted array.

### Clue 2

Duplicates.

### Clue 3

Range.

Think:

```text id="b259"
Two Boundary Searches
```

---

# Optimal Approach

Run:

```java id="b260"
firstOccurrence()
```

and:

```java id="b261"
lastOccurrence()
```

---

# What To Say In Interview

The left boundary and right boundary are independent binary searches. I'll compute each separately and combine the results.

---

# Optimal Java Code

```java id="b262"
class Solution {

    public int[] searchRange(
            int[] nums,
            int target
    ) {

        return new int[]{
            first(nums,target),
            last(nums,target)
        };
    }

    private int first(
            int[] nums,
            int target
    ){

        int left = 0;
        int right =
                nums.length - 1;

        int answer = -1;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(nums[mid] >= target){

                if(nums[mid]
                    == target){

                    answer = mid;
                }

                right = mid - 1;

            }else{

                left = mid + 1;
            }
        }

        return answer;
    }

    private int last(
            int[] nums,
            int target
    ){

        int left = 0;
        int right =
                nums.length - 1;

        int answer = -1;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(nums[mid] <= target){

                if(nums[mid]
                    == target){

                    answer = mid;
                }

                left = mid + 1;

            }else{

                right = mid - 1;
            }
        }

        return answer;
    }
}
```

---

# Boundary Search Revision Sheet

## First Bad Version

Pattern:

```text id="b263"
First True
```

---

## First Occurrence

Pattern:

```text id="b264"
Left Boundary
```

---

## Last Occurrence

Pattern:

```text id="b265"
Right Boundary
```

---

## Search Range

Pattern:

```text id="b266"
First Boundary

+

Last Boundary
```

---

# The Most Important Binary Search Transformation

Classic Search:

```java id="b267"
Find Target
```

Boundary Search:

```java id="b268"
Find Transition Point
```

Example:

```text id="b269"
F F F F T T T T
```

Need:

```text id="b270"
First T
```

This idea powers:

* First Bad Version
* Search Range
* Lower Bound
* Upper Bound
* Koko Eating Bananas
* Ship Packages
* Split Array Largest Sum

---

# Golden Rule

Whenever interviewer says:

```text id="b271"
First

Last

Minimum Valid

Maximum Valid

Boundary
```

Think:

```text id="b272"
Don't stop
when you find the answer.
```

Instead:

```text id="b273"
Store Answer

Continue Searching
```

for a better boundary.
