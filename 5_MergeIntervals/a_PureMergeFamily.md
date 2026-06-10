# Merge Intervals Pattern - Part 1

# Pure Merge Family

---

# Why This Family Matters

This is the foundation of the entire Merge Intervals pattern.

Most interval problems are variations of:

```text id="m101"
Sort

Detect Overlap

Merge
```

If you master these three problems, you'll solve a huge percentage of interval interview questions.

---

# Master Merge Template

```java id="m102"
Arrays.sort(
    intervals,
    (a,b) -> a[0] - b[0]
);

List<int[]> result =
        new ArrayList<>();

for(int[] current : intervals){

    if(result.isEmpty() || result.get(result.size()-1)[1] < current[0]){
        result.add(current);
    }else{

        result.get(result.size()-1)[1] = Math.max(result.get(result.size()-1)[1],current[1]);
    }
}
```

---

# Pattern Recognition Clues

Think Pure Merge Family when you see:

### Clue 1

```text id="m103"
Overlapping Intervals
```

### Clue 2

```text id="m104"
Merge Ranges
```

### Clue 3

```text id="m105"
Combine Meetings
```

### Clue 4

```text id="m106"
Insert Interval
```

### Clue 5

```text id="m107"
Compressed Ranges
```

---

# Problem 1: Merge Intervals

## LeetCode 56

---

# Problem Explanation

Given:

```text id="m108"
[[1,3],
 [2,6],
 [8,10],
 [15,18]]
```

Merge overlapping intervals.

Output:

```text id="m109"
[[1,6],
 [8,10],
 [15,18]]
```

Because:

```text id="m110"
[1,3]

and

[2,6]
```

overlap.

---

# What Is The Interviewer Testing?

Can you recognize that:

```text id="m111"
After Sorting
```

you only need to compare with:

```text id="m112"
Previous Interval
```

not all intervals.

---

# Pattern Recognition Clues

### Clue 1

Intervals given.

### Clue 2

Merge overlaps.

### Clue 3

Output compressed intervals.

Think:

```text id="m113"
Sort + Linear Scan
```

---

# Brute Force

Compare every interval with every other interval.

Merge repeatedly.

---

## Complexity

```text id="m114"
Time : O(n²)

Space : O(n)
```

---

# Key Insight

After sorting:

```text id="m115"
Intervals that overlap
become neighbors.
```

Example:

```text id="m116"
[1,3]

[2,6]

[8,10]
```

Now only check:

```text id="m117"
Current

vs

Last Merged
```

---

# Overlap Condition

Two intervals overlap if:

```java id="m118"
currentStart
<=
lastEnd
```

Example:

```text id="m119"
[1,5]

[4,8]
```

Because:

```text id="m120"
4 <= 5
```

Overlap exists.

---

# Merge Formula

```java id="m121"
mergedEnd =Math.max( lastEnd, currentEnd);
```

---

# Dry Run

Input:

```text id="m122"
[1,3]

[2,6]

[8,10]
```

Result:

```text id="m123"
[1,3]
```

---

Current:

```text id="m124"
[2,6]
```

Overlap:

```text id="m125"
2 <= 3
```

Merge:

```text id="m126"
[1,6]
```

---

Current:

```text id="m127"
[8,10]
```

No overlap:

```text id="m128"
8 > 6
```

Add.

---

Final:

```text id="m129"
[1,6]

[8,10]
```

---

# What To Say In Interview

I'll first sort intervals by start time.

After sorting, any overlap can only occur with the most recently merged interval.

Therefore a single linear scan is sufficient.

---

# Optimal Java Code

```java id="m130"
class Solution {

    public int[][] merge( int[][] intervals) {

        Arrays.sort(intervals,(a,b) -> a[0]-b[0]);

        List<int[]> result =new ArrayList<>();

        for(int[] current : intervals){

            if(result.isEmpty()||result.get(result.size()-1 )[1] < current[0]){
                result.add(current);
            }else{

                result.get( result.size()-1)[1]=Math.max(result.get(result.size()-1)[1], current[1] );
            }
        }

        return result.toArray(
                new int[ result.size()][]
        );
    }
}
```

---

# Complexity

```text id="m131"
Sorting : O(n log n)

Scan    : O(n)

Overall : O(n log n)

Space   : O(n)
```

---

# Similar Problems

* Insert Interval
* Employee Free Time
* Range Compression

---

# Interview Follow-Up

### Why Sort By Start Time?

Because overlap is determined by:

```text id="m132"
Current Start
```

Sorting by end time breaks the merge logic.

---

# Problem 2: Insert Interval

## LeetCode 57

---

# Problem Explanation

Given:

```text id="m133"
intervals

[[1,3],
 [6,9]]
```

Insert:

```text id="m134"
[2,5]
```

Output:

```text id="m135"
[[1,5],
 [6,9]]
```

---

# What Is The Interviewer Testing?

Can you merge while inserting?

Most candidates:

```text id="m136"
Insert

Sort

Merge
```

Interviewer wants:

```text id="m137"
One Pass
```

---

# Pattern Recognition Clues

### Clue 1

Sorted intervals.

### Clue 2

Insert one interval.

### Clue 3

Merge overlaps.

Think:

```text id="m138"
Three Zones
```

---

# Three Zone Strategy

---

## Zone 1

Intervals completely before:

```text id="m139"
newInterval
```

Example:

```text id="m140"
[1,2]

new = [5,7]
```

Add directly.

---

## Zone 2

Intervals overlapping:

```text id="m141"
newInterval
```

Merge.

---

## Zone 3

Intervals completely after:

```text id="m142"
newInterval
```

Add directly.

---

# Visualization

```text id="m143"
Before

Overlap

After
```

---

# Dry Run

```text id="m144"
[[1,3],
 [6,9]]

new

[2,5]
```

Merge:

```text id="m145"
[1,5]
```

Add:

```text id="m146"
[6,9]
```

---

# What To Say In Interview

The intervals are already sorted.

I'll process intervals before the new interval, then merge all overlapping intervals, and finally append remaining intervals.

---

# Optimal Java Code

```java id="m147"
class Solution {

    public int[][] insert(int[][] intervals,int[] newInterval) {

        List<int[]> result =new ArrayList<>();

        int i = 0;
        int n = intervals.length;

        while(i < n && intervals[i][1] < newInterval[0]){
            result.add(intervals[i]);
            i++;
        }

        while(i < n && intervals[i][0] <= newInterval[1]){
            newInterval[0] = Math.min(newInterval[0], intervals[i][0] );
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }

        result.add(newInterval);

        while(i < n){

            result.add(intervals[i]);
            i++;
        }

        return result.toArray( new int[ result.size()][]
        );
    }
}
```

---

# Complexity

```text id="m148"
Time : O(n)

Space : O(n)
```

---

# Similar Problems

* Merge Intervals
* Calendar Booking
* Range Module

---

# Interview Follow-Up

### Why Is Complexity O(n)?

Because intervals are already sorted.

No sorting required.

---

# Problem 3: Summary Ranges

## LeetCode 228

---

# Problem Explanation

Given:

```text id="m149"
[0,1,2,4,5,7]
```

Output:

```text id="m150"
["0->2",
 "4->5",
 "7"]
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="m151"
Consecutive Intervals
```

instead of explicit intervals?

---

# Hidden Interval Representation

Array:

```text id="m152"
0 1 2
```

represents:

```text id="m153"
[0,2]
```

Array:

```text id="m154"
4 5
```

represents:

```text id="m155"
[4,5]
```

---

# Pattern Recognition Clues

### Clue 1

Sorted numbers.

### Clue 2

Consecutive values.

### Clue 3

Compress ranges.

Think:

```text id="m156"
Virtual Intervals
```

---

# Brute Force

Build intervals repeatedly.

---

## Complexity

```text id="m157"
Time : O(n)
```

Even brute force is linear.

---

# Optimal Approach

Track:

```java id="m158"
start
```

Move forward while:

```java id="m159"
nums[i+1]
==
nums[i]+1
```

Build interval.

---

# Dry Run

```text id="m160"
0 1 2 4 5 7
```

Start:

```text id="m161"
0
```

Expand:

```text id="m162"
1

2
```

Range:

```text id="m163"
0->2
```

---

Start:

```text id="m164"
4
```

Expand:

```text id="m165"
5
```

Range:

```text id="m166"
4->5
```

---

Single:

```text id="m167"
7
```

---

# What To Say In Interview

I'll treat consecutive numbers as interval boundaries.

For every sequence of consecutive numbers, I'll create a compressed range.

---

# Optimal Java Code

```java id="m168"
class Solution {

    public List<String> summaryRanges(int[] nums) {

        List<String> result =new ArrayList<>();
        int i = 0;
        while(i < nums.length){

            int start = nums[i];

            while(i + 1 < nums.length && nums[i+1]== nums[i] + 1){
                i++;
            }
            int end = nums[i];
            if(start == end){
                result.add(String.valueOf(start));
            }else{
                result.add( start + "->" + end);
            }
            i++;
        }
        return result;
    }
}
```

---

# Complexity

```text id="m169"
Time : O(n)

Space : O(1)
```

---

# Similar Problems

* Missing Ranges
* Merge Intervals
* Data Compression

---

# Pure Merge Family Revision Sheet

## Merge Intervals

Goal:

```text id="m170"
Combine Overlaps
```

Template:

```java id="m171"
Sort

Compare

Merge
```

---

## Insert Interval

Goal:

```text id="m172"
Insert + Merge
```

Template:

```java id="m173"
Before

Overlap

After
```

---

## Summary Ranges

Goal:

```text id="m174"
Compress Consecutive Numbers
```

Template:

```java id="m175"
Expand Consecutive Sequence
```

---

# Golden Interview Rule

If interviewer says:

```text id="m176"
Intervals

Ranges

Meetings

Overlaps
```

First thought should be:

```java id="m177"
Arrays.sort(
    intervals,
    (a,b) -> a[0]-b[0]
);
```

because sorting is usually the key that unlocks the solution.
