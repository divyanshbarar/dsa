# Merge Intervals Pattern - Part 5

# Greedy Interval Family

---

# Why This Family Matters

Most interval questions ask:

```text id="g501"
Can intervals overlap?
```

This family asks:

```text id="g502"
Which intervals should I keep?

Which intervals should I remove?

Which intervals should I choose?
```

This introduces one of the most important greedy interview rules:

```text id="g503"
Sort By End Time
```

not:

```text id="g504"
Sort By Start Time
```

---

# The Most Important Greedy Interval Insight

Suppose:

```text id="g505"
[1,100]

[2,3]

[4,5]

[6,7]
```

If you keep:

```text id="g506"
[1,100]
```

you lose:

```text id="g507"
3 intervals
```

If you keep:

```text id="g508"
[2,3]

[4,5]

[6,7]
```

you gain:

```text id="g509"
3 intervals
```

---

# Golden Greedy Rule

Whenever interviewer asks:

```text id="g510"
Maximum Non-Overlapping Intervals
```

Think:

```java id="g511"
Sort By End Time
```

---

# Problem 11: Non-overlapping Intervals

## LeetCode 435

---

# Problem Explanation

Given:

```text id="g512"
[1,2]

[2,3]

[3,4]

[1,3]
```

Remove minimum intervals so that:

```text id="g513"
No Overlap Exists
```

Answer:

```text id="g514"
1
```

Remove:

```text id="g515"
[1,3]
```

---

# What Is The Interviewer Testing?

Most candidates try:

```text id="g516"
Which interval to remove?
```

Interviewer wants:

```text id="g517"
Which intervals should be kept?
```

---

# Pattern Recognition Clues

### Clue 1

Remove minimum.

### Clue 2

Maximum intervals remaining.

### Clue 3

No overlap.

Think:

```text id="g518"
Activity Selection
```

---

# Key Observation

Min Remove:

```text id="g519"
=
Total
-
Max Keep
```

So solve:

```text id="g520"
Maximum Non-overlapping Intervals
```

instead.

---

# Why Sort By End?

Smaller ending interval leaves:

```text id="g521"
More Space
```

for future intervals.

---

# Dry Run

Sorted By End:

```text id="g522"
[1,2]

[2,3]

[1,3]

[3,4]
```

Keep:

```text id="g523"
[1,2]
```

Keep:

```text id="g524"
[2,3]
```

Skip:

```text id="g525"
[1,3]
```

Keep:

```text id="g526"
[3,4]
```

Removed:

```text id="g527"
1
```

---

# What To Say In Interview

Instead of deciding what to remove, I'll maximize the number of intervals I can keep.

Sorting by end time is optimal because it leaves the most room for future intervals.

---

# Optimal Java Code

```java id="g528"
class Solution {

    public int eraseOverlapIntervals(
            int[][] intervals
    ) {

        Arrays.sort(
                intervals,
                (a,b) -> a[1]-b[1]
        );

        int count = 0;

        int end =
                intervals[0][1];

        for(int i = 1;
            i < intervals.length;
            i++){

            if(intervals[i][0]
                < end){

                count++;

            }else{

                end =
                    intervals[i][1];
            }
        }

        return count;
    }
}
```

---

# Complexity

```text id="g529"
Time :

O(n log n)

Space :

O(1)
```

---

# Similar Problems

* Activity Selection
* Meeting Scheduling
* Maximum Compatible Tasks

---

# Problem 12: Minimum Number Of Arrows To Burst Balloons

## LeetCode 452

---

# Problem Explanation

Each balloon:

```text id="g530"
[start,end]
```

Arrow shot at:

```text id="g531"
x
```

bursts every balloon containing:

```text id="g532"
x
```

Example:

```text id="g533"
[10,16]

[2,8]

[1,6]

[7,12]
```

Answer:

```text id="g534"
2 arrows
```

---

# What Is The Interviewer Testing?

Can you realize:

```text id="g535"
Arrow Position
```

behaves exactly like:

```text id="g536"
Interval End Selection
```

---

# Pattern Recognition Clues

### Clue 1

Minimum arrows.

### Clue 2

Intervals.

### Clue 3

Choose points.

Think:

```text id="g537"
Greedy By End
```

---

# Key Insight

Shoot arrow at:

```text id="g538"
Earliest Ending Balloon
```

This maximizes future coverage.

---

# Visualization

```text id="g539"
1------6

2--------8

7-----------12

10--------------16
```

Arrow:

```text id="g540"
x = 6
```

Bursts:

```text id="g541"
first two balloons
```

---

# Dry Run

Sorted By End:

```text id="g542"
[1,6]

[2,8]

[7,12]

[10,16]
```

Arrow:

```text id="g543"
6
```

Next balloon:

```text id="g544"
7
```

Need second arrow.

Answer:

```text id="g545"
2
```

---

# What To Say In Interview

I'll always shoot at the earliest ending balloon because it covers the largest possible set of future overlapping balloons.

---

# Optimal Java Code

```java id="g546"
class Solution {

    public int findMinArrowShots(
            int[][] points
    ) {

        Arrays.sort(
                points,
                (a,b) ->
                Integer.compare(
                    a[1],
                    b[1]
                )
        );

        int arrows = 1;

        int end =
                points[0][1];

        for(int i = 1;
            i < points.length;
            i++){

            if(points[i][0]
                > end){

                arrows++;

                end =
                    points[i][1];
            }
        }

        return arrows;
    }
}
```

---

# Complexity

```text id="g547"
Time :

O(n log n)

Space :

O(1)
```

---

# Interview Insight

This problem is almost identical to:

```text id="g548"
Non-overlapping Intervals
```

Same greedy logic.

Different wording.

---

# Problem 13: Remove Covered Intervals

## LeetCode 1288

---

# Problem Explanation

Given:

```text id="g549"
[1,4]

[3,6]

[2,8]
```

Remove intervals that are:

```text id="g550"
Completely Covered
```

Answer:

```text id="g551"
2
```

Remaining:

```text id="g552"
[2,8]
```

---

# What Is The Interviewer Testing?

Can you detect:

```text id="g553"
Containment
```

instead of overlap?

---

# Pattern Recognition Clues

### Clue 1

Covered.

### Clue 2

Contained.

### Clue 3

Nested intervals.

Think:

```text id="g554"
Sorting Trick
```

---

# Key Observation

Sort:

```text id="g555"
Start Ascending

End Descending
```

Why?

Example:

```text id="g556"
[1,10]

[1,5]
```

Need larger interval first.

---

# Visualization

Sorted:

```text id="g557"
[1,10]

[1,5]

[2,4]
```

Track:

```text id="g558"
Maximum End Seen
```

---

# Covered Condition

```java id="g559"
currentEnd
<=
maxEndSeen
```

Covered.

---

# Dry Run

```text id="g560"
[1,10]

[1,5]

[2,4]
```

Max End:

```text id="g561"
10
```

---

Current:

```text id="g562"
5
```

Covered.

---

Current:

```text id="g563"
4
```

Covered.

---

Answer:

```text id="g564"
1 interval remains
```

---

# What To Say In Interview

I'll sort intervals by start ascending and end descending.

Then any covered interval can be detected using the maximum end encountered so far.

---

# Optimal Java Code

```java id="g565"
class Solution {

    public int removeCoveredIntervals(
            int[][] intervals
    ) {

        Arrays.sort(
                intervals,
                (a,b) -> {

                    if(a[0] == b[0]){

                        return b[1]-a[1];
                    }

                    return a[0]-b[0];
                }
        );

        int count = 0;

        int maxEnd = 0;

        for(int[] interval
                : intervals){

            if(interval[1]
                > maxEnd){

                count++;

                maxEnd =
                        interval[1];
            }
        }

        return count;
    }
}
```

---

# Complexity

```text id="g566"
Time :

O(n log n)

Space :

O(1)
```

---

# Greedy Family Revision Sheet

## Non-overlapping Intervals

Goal:

```text id="g567"
Maximum Intervals Kept
```

Sort:

```java id="g568"
By End Time
```

---

## Minimum Arrows

Goal:

```text id="g569"
Minimum Points Chosen
```

Sort:

```java id="g570"
By End Time
```

---

## Remove Covered Intervals

Goal:

```text id="g571"
Detect Containment
```

Sort:

```text id="g572"
Start Asc

End Desc
```

---

# Most Important Greedy Interval Rule

If interviewer says:

```text id="g573"
Maximum Intervals

Minimum Removals

Minimum Arrows

Maximum Meetings
```

Think:

```java id="g574"
Arrays.sort(
    intervals,
    (a,b) -> a[1]-b[1]
);
```

because:

```text id="g575"
Earliest Finish Time
```

is one of the most powerful greedy strategies in interviews.

---
