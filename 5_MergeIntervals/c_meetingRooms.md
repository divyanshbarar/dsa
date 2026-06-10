# Merge Intervals Pattern - Part 3

# Meeting Room Family

---

# Why This Family Matters

Part 1 taught:

```text id="mr301"
Merge Intervals
```

Part 2 taught:

```text id="mr302"
Interval Intersections
```

Now we move to one of the most important interview patterns:

```text id="mr303"
Active Intervals
```

Questions:

```text id="mr304"
How many meetings
are running simultaneously?
```

This introduces:

```text id="mr305"
Min Heap
```

which later appears in:

* CPU Scheduling
* Employee Free Time
* Merge K Sorted Lists
* Task Scheduling
* Top K Problems

---

# Core Insight

Most interval questions ask:

```text id="mr306"
Do intervals overlap?
```

Meeting Room questions ask:

```text id="mr307"
How many overlaps exist
at the same time?
```

Very different problem.

---

# Problem 6: Meeting Rooms

## LeetCode 252

---

# Problem Explanation

Given:

```text id="mr308"
[[0,30],
 [5,10],
 [15,20]]
```

Determine:

```text id="mr309"
Can one person attend
all meetings?
```

Answer:

```text id="mr310"
false
```

Because:

```text id="mr311"
[0,30]

overlaps

[5,10]
```

---

# What Is The Interviewer Testing?

Can you detect:

```text id="mr312"
Any Overlap
```

after sorting?

---

# Pattern Recognition Clues

### Clue 1

Meetings.

### Clue 2

Single person.

### Clue 3

Can attend all?

Think:

```text id="mr313"
Sort
+
Overlap Detection
```

---

# Brute Force

Compare every meeting with every other meeting.

---

## Complexity

```text id="mr314"
Time : O(n²)
```

---

# Key Observation

After sorting:

```text id="mr315"
Any overlap
must occur
between neighbors.
```

Example:

```text id="mr316"
[0,30]

[5,10]

[15,20]
```

Need only compare:

```text id="mr317"
Current

vs

Previous
```

---

# Overlap Condition

```java id="mr318"
currentStart
<
previousEnd
```

If true:

```text id="mr319"
Conflict Exists
```

---

# Dry Run

Sorted:

```text id="mr320"
[0,30]

[5,10]

[15,20]
```

Check:

```text id="mr321"
5 < 30
```

Overlap.

Answer:

```text id="mr322"
false
```

---

# What To Say In Interview

After sorting meetings by start time, I only need to compare adjacent meetings. If a meeting starts before the previous one ends, attending all meetings becomes impossible.

---

# Optimal Java Code

```java id="mr323"
class Solution {

    public boolean canAttendMeetings(
            int[][] intervals
    ) {

        Arrays.sort(
                intervals,
                (a,b) -> a[0]-b[0]
        );

        for(int i = 1;
            i < intervals.length;
            i++){

            if(intervals[i][0]
                <
               intervals[i-1][1]){

                return false;
            }
        }

        return true;
    }
}
```

---

# Complexity

```text id="mr324"
Time :

O(n log n)

Space :

O(1)
```

---

# Similar Problems

* Calendar Booking
* Merge Intervals
* Conflict Detection

---

# Interview Follow-Up

### Why Sorting Works?

Because once sorted:

```text id="mr325"
Future meetings
cannot overlap
earlier meetings
without first overlapping
their neighbors.
```

---

# Problem 7: Meeting Rooms II

## LeetCode 253

---

# Problem Explanation

Given:

```text id="mr326"
[[0,30],
 [5,10],
 [15,20]]
```

Find:

```text id="mr327"
Minimum Number
Of Meeting Rooms
```

Answer:

```text id="mr328"
2
```

---

# What Is The Interviewer Testing?

This is one of the most important heap problems.

Can you track:

```text id="mr329"
Currently Active Meetings
```

?

---

# Pattern Recognition Clues

### Clue 1

Minimum rooms.

### Clue 2

Simultaneous meetings.

### Clue 3

Overlapping intervals.

Think:

```text id="mr330"
Min Heap
```

---

# Why Merge Intervals Doesn't Work

Merge tells:

```text id="mr331"
Whether overlap exists
```

Meeting Rooms asks:

```text id="mr332"
How many overlaps exist
simultaneously
```

Need more information.

---

# Key Insight

When a meeting ends:

```text id="mr333"
Room becomes free
```

We must always know:

```text id="mr334"
Earliest Ending Meeting
```

This screams:

```text id="mr335"
Min Heap
```

---

# Heap Meaning

Heap stores:

```text id="mr336"
Meeting End Times
```

Top of heap:

```text id="mr337"
Earliest Ending Meeting
```

---

# Algorithm

### Step 1

Sort by start time.

### Step 2

For each meeting:

If:

```java id="mr338"
currentStart
>=
minEnd
```

Room becomes free.

Remove it.

---

### Step 3

Add current meeting end.

---

### Step 4

Heap size equals:

```text id="mr339"
Rooms Currently Used
```

Maximum heap size is answer.

---

# Visualization

Meetings:

```text id="mr340"
[0,30]

[5,10]

[15,20]
```

---

Room 1:

```text id="mr341"
0------30
```

Room 2:

```text id="mr342"
5---10

15--20
```

Need:

```text id="mr343"
2 rooms
```

---

# Dry Run

Sorted:

```text id="mr344"
[0,30]

[5,10]

[15,20]
```

---

Add:

```text id="mr345"
30
```

Heap:

```text id="mr346"
[30]
```

Rooms:

```text id="mr347"
1
```

---

Meeting:

```text id="mr348"
[5,10]
```

Cannot reuse.

Add:

```text id="mr349"
10
```

Heap:

```text id="mr350"
[10,30]
```

Rooms:

```text id="mr351"
2
```

---

Meeting:

```text id="mr352"
[15,20]
```

Reuse:

```text id="mr353"
10
```

Remove.

Add:

```text id="mr354"
20
```

Heap:

```text id="mr355"
[20,30]
```

Still:

```text id="mr356"
2 rooms
```

---

# What To Say In Interview

I'll sort meetings by start time and maintain a min heap containing ending times of active meetings.

Whenever the earliest meeting finishes before the current meeting starts, I can reuse that room.

The heap size represents the number of rooms currently required.

---

# Optimal Java Code

```java id="mr357"
class Solution {

    public int minMeetingRooms(
            int[][] intervals
    ) {

        if(intervals.length == 0){
            return 0;
        }

        Arrays.sort(
                intervals,
                (a,b) -> a[0]-b[0]
        );

        PriorityQueue<Integer> pq =
                new PriorityQueue<>();

        pq.offer(
                intervals[0][1]
        );

        for(int i = 1;
            i < intervals.length;
            i++){

            if(intervals[i][0]
                >=
               pq.peek()){

                pq.poll();
            }

            pq.offer(
                    intervals[i][1]
            );
        }

        return pq.size();
    }
}
```

---

# Complexity

```text id="mr358"
Sorting :

O(n log n)

Heap :

O(n log n)

Overall :

O(n log n)

Space :

O(n)
```

---

# Alternative Solution (Sweep Line)

---

# Key Idea

Separate:

```text id="mr359"
Start Times
```

and

```text id="mr360"
End Times
```

Sort both.

Use:

```text id="mr361"
Two Pointers
```

---

# Visualization

Starts:

```text id="mr362"
0

5

15
```

Ends:

```text id="mr363"
10

20

30
```

Track:

```text id="mr364"
Current Active Meetings
```

---

# Sweep Line Java Code

```java id="mr365"
class Solution {

    public int minMeetingRooms(
            int[][] intervals
    ) {

        int n = intervals.length;

        int[] start =
                new int[n];

        int[] end =
                new int[n];

        for(int i = 0;
            i < n;
            i++){

            start[i] =
                    intervals[i][0];

            end[i] =
                    intervals[i][1];
        }

        Arrays.sort(start);
        Arrays.sort(end);

        int rooms = 0;
        int answer = 0;

        int s = 0;
        int e = 0;

        while(s < n){

            if(start[s]
                <
               end[e]){

                rooms++;

                answer =
                        Math.max(
                                answer,
                                rooms
                        );

                s++;

            }else{

                rooms--;

                e++;
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text id="mr366"
Time :

O(n log n)

Space :

O(n)
```

---

# Heap vs Sweep Line

## Heap

Good for:

```text id="mr367"
Tracking Actual Rooms
```

---

## Sweep Line

Good for:

```text id="mr368"
Counting Overlaps
```

---

# Meeting Room Family Revision Sheet

## Meeting Rooms

Question:

```text id="mr369"
Can Attend All?
```

Use:

```text id="mr370"
Sort
+
Neighbor Check
```

---

## Meeting Rooms II

Question:

```text id="mr371"
How Many Rooms?
```

Use:

```text id="mr372"
Min Heap
```

or

```text id="mr373"
Sweep Line
```

---

# Golden Interview Rule

If interviewer says:

```text id="mr374"
Minimum Rooms

Minimum CPUs

Concurrent Tasks

Active Meetings
```

Think:

```java id="mr375"
PriorityQueue
```

before thinking about Merge Intervals.

Because these questions are about:

```text id="mr376"
Active Intervals
```

not:

```text id="mr377"
Merged Intervals
```
