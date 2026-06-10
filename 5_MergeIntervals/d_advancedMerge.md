# Merge Intervals Pattern - Part 4

# Advanced Meeting Family

---

# Why This Family Matters

Part 3 taught:

```text id="am401"
Meeting Rooms II
```

which introduced:

```text id="am402"
Min Heap
```

This section teaches how interviewers disguise the exact same pattern.

Problems may mention:

```text id="am403"
Employees

CPUs

Servers

Workers

Resources
```

But underneath they are asking:

```text id="am404"
How many intervals
are active simultaneously?
```

---

# Core Pattern

Whenever interviewer asks:

```text id="am405"
Minimum Resources
```

Think:

```java id="am406"
PriorityQueue
```

tracking:

```text id="am407"
Earliest Ending Interval
```

---

# Problem 8: Employee Free Time

## LeetCode 759

---

# Problem Explanation

Each employee has working intervals.

Example:

Employee 1

```text id="am408"
[1,2]

[5,6]
```

Employee 2

```text id="am409"
[1,3]
```

Employee 3

```text id="am410"
[4,10]
```

Find:

```text id="am411"
Common Free Time
```

for everyone.

Output:

```text id="am412"
[3,4]
```

---

# What Is The Interviewer Testing?

Most candidates try:

```text id="am413"
Compare every employee
with every other employee
```

Interviewer wants:

```text id="am414"
Merge All Busy Times
```

then find gaps.

---

# Pattern Recognition Clues

### Clue 1

Multiple schedules.

### Clue 2

Common free time.

### Clue 3

Intervals.

Think:

```text id="am415"
Merge Intervals
```

---

# Key Insight

Don't search for:

```text id="am416"
Free Time
```

Search for:

```text id="am417"
Busy Time
```

first.

Merge busy intervals.

Gaps become free time.

---

# Visualization

Busy:

```text id="am418"
1---3

4---------10
```

Gap:

```text id="am419"
3---4
```

Free Time.

---

# Brute Force

Flatten all schedules.

Sort.

Merge.

Find gaps.

---

## Complexity

```text id="am420"
O(N log N)
```

---

# Optimal Approach

Step 1:

Flatten intervals.

Step 2:

Sort by start.

Step 3:

Merge overlaps.

Step 4:

Gaps between merged intervals are answers.

---

# Dry Run

Intervals:

```text id="am421"
[1,2]

[5,6]

[1,3]

[4,10]
```

Sort:

```text id="am422"
[1,2]

[1,3]

[4,10]

[5,6]
```

Merge:

```text id="am423"
[1,3]

[4,10]
```

Gap:

```text id="am424"
3-4
```

Answer.

---

# What To Say In Interview

Instead of finding free intervals directly, I'll merge all busy intervals. Any gap between merged intervals represents common free time.

---

# Optimal Java Code

```java id="am425"
class Solution {

    public List<Interval>
    employeeFreeTime(
            List<List<Interval>>
            schedule
    ) {

        List<Interval> all =
                new ArrayList<>();

        for(List<Interval> emp
                : schedule){

            all.addAll(emp);
        }

        all.sort(
            (a,b) ->
            a.start - b.start
        );

        List<Interval> result =
                new ArrayList<>();

        Interval prev = all.get(0);

        for(int i = 1;
            i < all.size();
            i++){

            Interval curr =
                    all.get(i);

            if(curr.start
                <=
               prev.end){

                prev.end =
                        Math.max(
                            prev.end,
                            curr.end
                        );

            }else{

                result.add(
                    new Interval(
                        prev.end,
                        curr.start
                    )
                );

                prev = curr;
            }
        }

        return result;
    }
}
```

---

# Complexity

```text id="am426"
Time :

O(N log N)

Space :

O(N)
```

---

# Similar Problems

* Merge Intervals
* Calendar Availability
* Common Meeting Slots

---

# Follow-Up

### What If Each Employee Has Thousands Of Intervals?

Now flattening is expensive.

Use:

```text id="am427"
Min Heap
```

to merge K sorted interval lists.

This becomes:

```text id="am428"
Merge K Sorted Lists
```

---

# Problem 9: Minimum CPUs Required

## Amazon / Google Variant

---

# Problem Explanation

Tasks:

```text id="am429"
[1,4]

[2,5]

[7,9]
```

Need:

```text id="am430"
Minimum CPUs
```

to execute all tasks.

Answer:

```text id="am431"
2
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="am432"
Meeting Rooms II
```

hidden behind different wording?

---

# Pattern Recognition Clues

### Clue 1

Tasks.

### Clue 2

Resources.

### Clue 3

Minimum Servers.

### Clue 4

Minimum CPUs.

Think:

```text id="am433"
Meeting Rooms II
```

---

# Translation Trick

Meeting:

```text id="am434"
Room
```

becomes:

```text id="am435"
CPU
```

Meeting End:

```text id="am436"
Room Free
```

becomes:

```text id="am437"
CPU Free
```

Same problem.

---

# Visualization

Tasks:

```text id="am438"
1-----4

2-----5

7---9
```

Active tasks:

```text id="am439"
2
```

Need:

```text id="am440"
2 CPUs
```

---

# Brute Force

Track every CPU manually.

---

## Complexity

```text id="am441"
O(n²)
```

---

# Optimal Approach

Exactly:

```text id="am442"
Meeting Rooms II
```

Store:

```java id="am443"
End Times
```

inside:

```java id="am444"
Min Heap
```

---

# Dry Run

Task:

```text id="am445"
[1,4]
```

Heap:

```text id="am446"
4
```

---

Task:

```text id="am447"
[2,5]
```

Need another CPU.

Heap:

```text id="am448"
4 5
```

---

Task:

```text id="am449"
[7,9]
```

Reuse CPU.

Remove:

```text id="am450"
4
```

Add:

```text id="am451"
9
```

---

Answer:

```text id="am452"
2
```

---

# What To Say In Interview

This is equivalent to Meeting Rooms II.

I'll maintain a min heap of currently running tasks ordered by finishing time.

Whenever a task completes before the next starts, I can reuse that CPU.

---

# Optimal Java Code

```java id="am453"
class Solution {

    public int minCPUs(
            int[][] tasks
    ) {

        Arrays.sort(
            tasks,
            (a,b) -> a[0]-b[0]
        );

        PriorityQueue<Integer> pq =
                new PriorityQueue<>();

        int answer = 0;

        for(int[] task : tasks){

            while(!pq.isEmpty()
                &&
                pq.peek()
                <= task[0]){

                pq.poll();
            }

            pq.offer(task[1]);

            answer =
                    Math.max(
                            answer,
                            pq.size()
                    );
        }

        return answer;
    }
}
```

---

# Complexity

```text id="am454"
Time :

O(n log n)

Space :

O(n)
```

---

# Problem 10: Car Pooling

## LeetCode 1094

---

# Problem Explanation

Trips:

```text id="am455"
[2,1,5]

2 passengers
from 1 to 5
```

Vehicle Capacity:

```text id="am456"
4
```

Determine:

```text id="am457"
Can all trips
be completed?
```

---

# What Is The Interviewer Testing?

Can you track:

```text id="am458"
Passengers Entering

Passengers Leaving
```

at each location?

---

# Pattern Recognition Clues

### Clue 1

Capacity.

### Clue 2

Passengers.

### Clue 3

Pickup.

### Clue 4

Drop-off.

Think:

```text id="am459"
Sweep Line
```

---

# Key Insight

Instead of tracking passengers continuously:

Store events.

Pickup:

```text id="am460"
+ passengers
```

Drop:

```text id="am461"
- passengers
```

---

# Visualization

Trip:

```text id="am462"
2 passengers

1 -> 5
```

Events:

```text id="am463"
+2 at 1

-2 at 5
```

---

# Sweep Line Approach

Process locations in order.

Maintain:

```java id="am464"
currentPassengers
```

If:

```java id="am465"
currentPassengers
>
capacity
```

Answer:

```text id="am466"
false
```

---

# What To Say In Interview

I'll convert pickups and drop-offs into events and process them in sorted order. This is a classic sweep-line problem.

---

# Optimal Java Code

```java id="am467"
class Solution {

    public boolean carPooling(
            int[][] trips,
            int capacity
    ) {

        int[] stops =
                new int[1001];

        for(int[] trip : trips){

            stops[trip[1]]
                    += trip[0];

            stops[trip[2]]
                    -= trip[0];
        }

        int passengers = 0;

        for(int stop : stops){

            passengers += stop;

            if(passengers
                > capacity){

                return false;
            }
        }

        return true;
    }
}
```

---

# Complexity

```text id="am468"
Time : O(maxLocation)

Space : O(maxLocation)
```

---

# Advanced Meeting Family Revision Sheet

## Employee Free Time

Question:

```text id="am469"
Common Gap?
```

Use:

```text id="am470"
Merge Intervals
```

---

## Minimum CPUs

Question:

```text id="am471"
Minimum Resources?
```

Use:

```text id="am472"
Min Heap
```

---

## Car Pooling

Question:

```text id="am473"
Capacity Check?
```

Use:

```text id="am474"
Sweep Line
```

---

# Hidden Interview Trick

Interviewers often rename:

```text id="am475"
Meeting Rooms II
```

into:

```text id="am476"
CPUs

Servers

Workers

Machines

Conference Rooms
```

But the underlying pattern remains:

```text id="am477"
Track Active Intervals
```

---

# Golden Rule

Whenever interviewer says:

```text id="am478"
Minimum Resources
```

Ask:

```text id="am479"
How many intervals
are active simultaneously?
```

If that's the real question,

think:

```java id="am480"
PriorityQueue
```

immediately.
