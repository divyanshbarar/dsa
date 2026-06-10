# Merge Intervals Pattern - Part 2

# Intersection Family

---

# Why This Family Matters

Part 1 taught:

```text id="i201"
Merge Overlapping Intervals
```

This family teaches:

```text id="i202"
Find Common Overlap
```

Most candidates incorrectly merge.

Interviewers want:

```text id="i203"
Intersection
```

not:

```text id="i204"
Union
```

---

# Merge vs Intersection

## Merge

Input:

```text id="i205"
[1,5]

[4,8]
```

Output:

```text id="i206"
[1,8]
```

---

## Intersection

Input:

```text id="i207"
[1,5]

[4,8]
```

Output:

```text id="i208"
[4,5]
```

Only common region.

---

# Core Intersection Template

```java id="i209"
while(i < A.length
   && j < B.length){

    int start =
        Math.max(
            A[i][0],
            B[j][0]
        );

    int end =
        Math.min(
            A[i][1],
            B[j][1]
        );

    if(start <= end){

        result.add(
            new int[]{
                start,
                end
            }
        );
    }

    if(A[i][1] < B[j][1]){

        i++;

    }else{

        j++;
    }
}
```

---

# Pattern Recognition Clues

Think Intersection Family when you see:

### Clue 1

```text id="i210"
Two Interval Lists
```

### Clue 2

```text id="i211"
Common Available Time
```

### Clue 3

```text id="i212"
Common Region
```

### Clue 4

```text id="i213"
Intersection
```

### Clue 5

```text id="i214"
Overlap Between Two Schedules
```

---

# Problem 4: Interval List Intersections

## LeetCode 986

---

# Problem Explanation

Given:

```text id="i215"
A

[0,2]
[5,10]
[13,23]
[24,25]
```

and

```text id="i216"
B

[1,5]
[8,12]
[15,24]
[25,26]
```

Find all intersections.

Output:

```text id="i217"
[1,2]

[5,5]

[8,10]

[15,23]

[24,24]

[25,25]
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text id="i218"
Two Sorted Lists
```

which usually implies:

```text id="i219"
Two Pointers
```

---

# Pattern Recognition Clues

### Clue 1

Two interval arrays.

### Clue 2

Sorted intervals.

### Clue 3

Find common overlap.

Think:

```text id="i220"
Two Pointers
```

---

# Brute Force

Compare:

```text id="i221"
Every Interval

with

Every Interval
```

---

## Complexity

```text id="i222"
Time : O(n*m)
```

---

# Key Observation

Each interval can only intersect with nearby intervals.

Because lists are already sorted.

---

# Overlap Formula

Intersection starts at:

```java id="i223"
max(
   start1,
   start2
)
```

Intersection ends at:

```java id="i224"
min(
   end1,
   end2
)
```

---

# Valid Intersection Condition

```java id="i225"
start <= end
```

---

# Visualization

```text id="i226"
A

1 -------- 7


B

5 -------- 10
```

Common:

```text id="i227"
5 ----- 7
```

---

# Pointer Movement Rule

Most important idea.

Suppose:

```text id="i228"
A ends first
```

Then:

```text id="i229"
A can never intersect
future intervals
```

Move:

```java id="i230"
i++
```

---

# Dry Run

```text id="i231"
A

[0,2]

B

[1,5]
```

Start:

```text id="i232"
max(0,1)

=
1
```

End:

```text id="i233"
min(2,5)

=
2
```

Intersection:

```text id="i234"
[1,2]
```

A ends first.

Move:

```text id="i235"
A pointer
```

---

# What To Say In Interview

Since both interval lists are sorted, I can use two pointers.

For each pair, I compute the overlapping region and move the pointer belonging to the interval that ends first.

---

# Optimal Java Code

```java id="i236"
class Solution {

    public int[][] intervalIntersection(
            int[][] firstList,
            int[][] secondList
    ) {

        List<int[]> result =
                new ArrayList<>();

        int i = 0;
        int j = 0;

        while(i < firstList.length
           && j < secondList.length){

            int start =
                    Math.max(
                            firstList[i][0],
                            secondList[j][0]
                    );

            int end =
                    Math.min(
                            firstList[i][1],
                            secondList[j][1]
                    );

            if(start <= end){

                result.add(
                    new int[]{
                        start,
                        end
                    }
                );
            }

            if(firstList[i][1]
                <
               secondList[j][1]){

                i++;

            }else{

                j++;
            }
        }

        return result.toArray(
                new int[
                    result.size()
                ][]
        );
    }
}
```

---

# Complexity

```text id="i237"
Time : O(n+m)

Space : O(1)
```

excluding output.

---

# Similar Problems

* Employee Free Time
* Calendar Availability
* Common Meeting Slots

---

# Interview Follow-Up

### Why Move Smaller End?

Because that interval cannot contribute to future intersections.

This is the same logic as:

```text id="i238"
Two Pointer Problems
```

---

# Problem 5: Common Meeting Slot

## Google Variant

---

# Problem Explanation

Two employees have availability.

Person A:

```text id="i239"
[10,50]

[60,120]

[140,210]
```

Person B:

```text id="i240"
[0,15]

[60,70]
```

Need:

```text id="i241"
First common slot

>= duration
```

Example:

```text id="i242"
duration = 8
```

Answer:

```text id="i243"
[60,68]
```

---

# What Is The Interviewer Testing?

Can you derive:

```text id="i244"
Meeting Scheduling
```

from:

```text id="i245"
Interval Intersection
```

?

---

# Key Insight

This is literally:

```text id="i246"
Interval Intersection
```

plus:

```text id="i247"
Minimum Length Check
```

---

# Overlap Length Formula

```java id="i248"
end - start
```

If:

```java id="i249"
end - start
>= duration
```

answer found.

---

# Dry Run

```text id="i250"
A

[10,50]

B

[0,15]
```

Intersection:

```text id="i251"
10-15
```

Length:

```text id="i252"
5
```

Too small.

---

Next:

```text id="i253"
60-70
```

Length:

```text id="i254"
10
```

Enough.

Answer:

```text id="i255"
60-68
```

---

# What To Say In Interview

I can reuse the interval intersection template.

Whenever an overlap exists, I check whether its duration satisfies the requirement.

---

# Optimal Java Code

```java id="i256"
class Solution {

    public List<Integer>
    minAvailableDuration(
            int[][] slots1,
            int[][] slots2,
            int duration
    ) {

        Arrays.sort(
                slots1,
                (a,b) -> a[0]-b[0]
        );

        Arrays.sort(
                slots2,
                (a,b) -> a[0]-b[0]
        );

        int i = 0;
        int j = 0;

        while(i < slots1.length
           && j < slots2.length){

            int start =
                    Math.max(
                        slots1[i][0],
                        slots2[j][0]
                    );

            int end =
                    Math.min(
                        slots1[i][1],
                        slots2[j][1]
                    );

            if(end - start
                >= duration){

                return Arrays.asList(
                        start,
                        start + duration
                );
            }

            if(slots1[i][1]
                <
               slots2[j][1]){

                i++;

            }else{

                j++;
            }
        }

        return new ArrayList<>();
    }
}
```

---

# Complexity

```text id="i257"
Time :

O(n log n
 +
 m log m)
```

---

# Similar Problems

* Calendar Matching
* Employee Free Time
* Interview Scheduling

---

# Intersection Family Revision Sheet

## Overlap Start

```java id="i258"
max(
    start1,
    start2
)
```

---

## Overlap End

```java id="i259"
min(
    end1,
    end2
)
```

---

## Valid Overlap

```java id="i260"
start <= end
```

---

## Pointer Movement

Move:

```java id="i261"
Interval
with Smaller End
```

---

# Merge vs Intersection

## Merge

```java id="i262"
newStart =
min(...)

newEnd =
max(...)
```

---

## Intersection

```java id="i263"
newStart =
max(...)

newEnd =
min(...)
```

---

# Golden Interview Rule

If interviewer says:

```text id="i264"
Two Schedules

Two Calendars

Common Availability

Intersection
```

Think:

```text id="i265"
Two Pointers
+
Interval Intersection
```

NOT:

```text id="i266"
Merge Intervals
```

because here we want:

```text id="i267"
Common Area
```

instead of:

```text id="i268"
Combined Area
```
