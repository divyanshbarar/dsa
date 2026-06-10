# Merge Intervals Pattern - Part 6

# Binary Search Family

---

# Why This Family Matters

Until now we solved interval problems using:

```text id="bs601"
Sorting

Greedy

Merge

Heap

Sweep Line
```

This family introduces:

```text id="bs602"
Intervals
+
Binary Search
```

One of the favorite combinations in:

* Google
* Uber
* Amazon
* Meta

interviews.

---

# Problem 14: Find Right Interval

## LeetCode 436

---

# Problem Explanation

For every interval:

```text id="bs603"
[start,end]
```

Find:

```text id="bs604"
The interval whose start
is the smallest value

>= current end
```

---

# Example

Input:

```text id="bs605"
[1,2]

[2,3]

[3,4]
```

Output:

```text id="bs606"
[1,2,-1]
```

Because:

```text id="bs607"
For [1,2]

Right interval

=
[2,3]

index = 1
```

---

For:

```text id="bs608"
[2,3]
```

Right interval:

```text id="bs609"
[3,4]

index = 2
```

---

For:

```text id="bs610"
[3,4]
```

No interval starts at:

```text id="bs611"
>= 4
```

Answer:

```text id="bs612"
-1
```

---

# What Is The Interviewer Testing?

Most candidates try:

```text id="bs613"
For every interval

scan all intervals
```

Interviewer wants:

```text id="bs614"
Can you search
efficiently?
```

---

# Pattern Recognition Clues

### Clue 1

Find next interval.

### Clue 2

Smallest start >= target.

### Clue 3

Intervals sorted by start.

Think:

```text id="bs615"
Binary Search
```

---

# Brute Force

For every interval:

```text id="bs616"
Check all intervals
```

Find smallest valid start.

---

# Complexity

```text id="bs617"
Time :

O(n²)
```

---

# Key Observation

Need:

```text id="bs618"
Smallest Value

>= target
```

This is:

```text id="bs619"
Lower Bound
```

which is a classic Binary Search problem.

---

# Visualization

Intervals:

```text id="bs620"
[1,2]

[2,3]

[3,4]
```

Store starts:

```text id="bs621"
1 -> index 0

2 -> index 1

3 -> index 2
```

Need right interval for:

```text id="bs622"
[1,2]
```

Search:

```text id="bs623"
First start >= 2
```

Answer:

```text id="bs624"
2
```

Index:

```text id="bs625"
1
```

---

# Optimal Approach

Store:

```java id="bs626"
(start,index)
```

Sort by:

```text id="bs627"
start
```

For every interval:

Binary search:

```java id="bs628"
first start
>=
current end
```

---

# Dry Run

Current:

```text id="bs629"
[2,3]
```

Need:

```text id="bs630"
start >= 3
```

Binary Search:

```text id="bs631"
1

2

3
```

Found:

```text id="bs632"
3
```

Index:

```text id="bs633"
2
```

---

# What To Say In Interview

For each interval I need the smallest start value greater than or equal to its end value.

That is exactly a lower-bound search problem, which can be solved efficiently using Binary Search.

---

# Optimal Java Code

```java id="bs634"
class Solution {

    public int[] findRightInterval(
            int[][] intervals
    ) {

        int n = intervals.length;

        int[][] starts =
                new int[n][2];

        for(int i = 0;
            i < n;
            i++){

            starts[i][0] =
                    intervals[i][0];

            starts[i][1] = i;
        }

        Arrays.sort(
                starts,
                (a,b) -> a[0]-b[0]
        );

        int[] answer =
                new int[n];

        for(int i = 0;
            i < n;
            i++){

            int target =
                    intervals[i][1];

            int left = 0;
            int right = n-1;

            int index = -1;

            while(left <= right){

                int mid =
                        left +
                        (right-left)/2;

                if(starts[mid][0]
                    >= target){

                    index =
                            starts[mid][1];

                    right =
                            mid-1;

                }else{

                    left =
                            mid+1;
                }
            }

            answer[i] = index;
        }

        return answer;
    }
}
```

---

# Complexity

```text id="bs635"
Sorting :

O(n log n)

Binary Search :

n × log n

Overall :

O(n log n)

Space :

O(n)
```

---

# Similar Problems

* Search Insert Position
* Ceiling Of Number
* Next Greater Interval

---

# Interview Follow-Up

### Why Not Use TreeMap?

We can.

Use:

```java id="bs636"
TreeMap
```

and:

```java id="bs637"
ceilingKey()
```

Complexity remains:

```text id="bs638"
O(n log n)
```

---

# Alternative Solution Using TreeMap

---

# Key Idea

Store:

```java id="bs639"
start
→
index
```

inside:

```java id="bs640"
TreeMap
```

Then:

```java id="bs641"
ceilingKey(end)
```

returns:

```text id="bs642"
smallest start >= end
```

---

# Java Code

```java id="bs643"
class Solution {

    public int[] findRightInterval(
            int[][] intervals
    ) {

        TreeMap<Integer,Integer>
                map =
                new TreeMap<>();

        for(int i = 0;
            i < intervals.length;
            i++){

            map.put(
                    intervals[i][0],
                    i
            );
        }

        int[] answer =
                new int[
                    intervals.length
                ];

        for(int i = 0;
            i < intervals.length;
            i++){

            Integer key =
                    map.ceilingKey(
                            intervals[i][1]
                    );

            answer[i] =
                    key == null
                    ? -1
                    : map.get(key);
        }

        return answer;
    }
}
```

---

# Complexity

```text id="bs644"
Time :

O(n log n)

Space :

O(n)
```

---

# Binary Search Family Revision Sheet

## Find Right Interval

Need:

```text id="bs645"
Smallest Start
>= End
```

Use:

```java id="bs646"
Lower Bound
```

---

## Binary Search Trigger Words

### Clue 1

```text id="bs647"
First Value >= X
```

---

### Clue 2

```text id="bs648"
Ceiling
```

---

### Clue 3

```text id="bs649"
Nearest Interval
```

---

### Clue 4

```text id="bs650"
Next Interval
```

---

### Clue 5

```text id="bs651"
Smallest Valid
```

---

# Lower Bound Template

```java id="bs652"
while(left <= right){

    int mid =
            left +
            (right-left)/2;

    if(nums[mid] >= target){

        answer = mid;

        right = mid-1;

    }else{

        left = mid+1;
    }
}
```

---


# Golden Interview Rule

If interviewer says:

```text id="bs655"
Find Next Interval

Smallest Start >= X

Nearest Interval
```

Think:

```java id="bs656"
Sort
+
Binary Search
```

before considering heaps or merge logic.
