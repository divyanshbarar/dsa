# Day 10 — Queue Pattern

# Queue Pattern — Part 3

> **Focus:** Topological Sort, Multi-Source BFS, and Queue Design.
>
> Problems:
>
> * **6. Course Schedule**
> * **7. Walls and Gates**
> * **8. Number of Recent Calls**

These are the next three problems in the Day 10 sheet. The sheet classifies **Course Schedule** as BFS/Topological Sort, **Walls and Gates** as BFS Grid, and **Number of Recent Calls** as Queue Design. 

---

# 6. Course Schedule

## Problem

You are given:

```text
numCourses
prerequisites
```

You need to determine whether it is possible to finish all courses.

A prerequisite:

```text
[course, prerequisite]
```

means:

> To take `course`, you must first complete `prerequisite`.

---

## Example 1

```text
numCourses = 2

prerequisites = [[1,0]]
```

Meaning:

```text
0 → 1
```

Take:

```text
Course 0
   ↓
Course 1
```

No cycle.

Answer:

```text
true
```

---

## Example 2

```text
numCourses = 2

prerequisites = [
    [1,0],
    [0,1]
]
```

Graph:

```text
0 → 1
↑   ↓
└───┘
```

There is a cycle.

We cannot start either course.

Answer:

```text
false
```

The Day 10 sheet identifies this problem as **BFS (Topo Sort), Medium**. 

---

# Pattern Recognition

Whenever you see:

```text
Prerequisites
Dependencies
Tasks that depend on other tasks
Can all tasks be completed?
Detect dependency cycle
```

think:

```text
TOPOLOGICAL SORT
```

And if using BFS:

```text
KAHN'S ALGORITHM
```

---

# What Is Topological Sort?

Topological sorting gives an ordering of nodes such that:

```text
prerequisite comes before dependent
```

For:

```text
0 → 1
1 → 2
2 → 3
```

valid ordering:

```text
0, 1, 2, 3
```

Invalid:

```text
2, 1, 0, 3
```

because `2` depends on `1`.

---

# Critical Condition

Topological sorting is possible only for a:

```text
DIRECTED ACYCLIC GRAPH
```

or:

```text
DAG
```

If there is a cycle:

```text
A → B
↑   ↓
└───C
```

there is no valid ordering.

Therefore:

> **Course Schedule = Detect whether the dependency graph contains a cycle.**

---

# What Is Kahn's Algorithm?

Kahn's Algorithm uses:

```text
Indegree + Queue + BFS
```

---

# What Is Indegree?

Indegree means:

> Number of incoming edges.

Example:

```text
0 → 1 → 2
```

Indegree:

```text
0 → 0
1 → 1
2 → 1
```

Course `0` has no prerequisites.

Therefore it can be taken immediately.

---

# The Core Idea

### Step 1

Calculate indegree of every course.

```text
indegree[i]
```

---

### Step 2

Put all courses with:

```text
indegree == 0
```

into the queue.

These courses have no prerequisites.

---

### Step 3

Remove one course from the queue.

That means:

```text
"We can complete this course."
```

---

### Step 4

Remove its dependency from neighboring courses.

For every neighbor:

```text
indegree[neighbor]--
```

---

### Step 5

If a neighbor becomes:

```text
indegree == 0
```

add it to the queue.

---

### Step 6

Count how many courses we process.

At the end:

```text
processed == numCourses
```

means:

```text
No cycle → true
```

Otherwise:

```text
Cycle exists → false
```

---

# Approach 1 — Brute Force

Try to find an ordering manually by repeatedly searching for a course whose prerequisites are satisfied.

After completing a course, update all dependent courses.

This is essentially repeatedly scanning all courses and prerequisites.

It can become:

```text
O(V × E)
```

or worse depending on implementation.

Not ideal.

---

# Approach 2 — DFS Cycle Detection

We can use DFS with three states:

```text
0 → unvisited
1 → currently visiting
2 → completely processed
```

If during DFS we encounter a node with state:

```text
1
```

we found a cycle.

This gives:

```text
Time  → O(V + E)
Space → O(V + E)
```

Very good approach.

---

# Optimal Approach — BFS + Kahn's Algorithm

Because today's pattern is **Queue**, use:

```text
Indegree
   ↓
Queue
   ↓
BFS
   ↓
Topological Sort
```

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public boolean canFinish(
            int numCourses,
            int[][] prerequisites) {

        List<List<Integer>> graph =
                new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        int[] indegree = new int[numCourses];

        // Build graph
        for (int[] prerequisite : prerequisites) {

            int course = prerequisite[0];
            int prerequisiteCourse = prerequisite[1];

            graph.get(prerequisiteCourse)
                    .add(course);

            indegree[course]++;
        }

        Queue<Integer> queue =
                new LinkedList<>();

        // Courses with no prerequisites
        for (int i = 0; i < numCourses; i++) {

            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int completedCourses = 0;

        while (!queue.isEmpty()) {

            int current = queue.poll();

            completedCourses++;

            for (int next : graph.get(current)) {

                indegree[next]--;

                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return completedCourses == numCourses;
    }
}
```

---

# Dry Run — No Cycle

Input:

```text
numCourses = 4

prerequisites = [
    [1,0],
    [2,1],
    [3,2]
]
```

Graph:

```text
0 → 1 → 2 → 3
```

Indegree:

```text
0 → 0
1 → 1
2 → 1
3 → 1
```

Queue initially:

```text
[0]
```

---

## Process 0

```text
completed = 1
```

Decrease:

```text
indegree[1] = 0
```

Queue:

```text
[1]
```

---

## Process 1

```text
completed = 2
```

Decrease:

```text
indegree[2] = 0
```

Queue:

```text
[2]
```

---

## Process 2

```text
completed = 3
```

Decrease:

```text
indegree[3] = 0
```

Queue:

```text
[3]
```

---

## Process 3

```text
completed = 4
```

Finally:

```text
completed == numCourses
```

Therefore:

```text
true
```

---

# Dry Run — Cycle

Input:

```text
numCourses = 2

prerequisites = [
    [1,0],
    [0,1]
]
```

Graph:

```text
0 → 1
↑   ↓
└───┘
```

Indegree:

```text
0 → 1
1 → 1
```

There is no course with:

```text
indegree == 0
```

Therefore:

```text
queue = []
```

We process:

```text
0 courses
```

But:

```text
numCourses = 2
```

So:

```text
completed != numCourses
```

Answer:

```text
false
```

---

# Interview Explanation

> "I model courses and prerequisites as a directed graph. The indegree of a course represents how many prerequisites are still remaining. I put all zero-indegree courses into a queue because they can be taken immediately. Using Kahn's BFS algorithm, whenever I complete a course, I reduce the indegree of its dependent courses. If a course reaches zero, I add it to the queue. If I can process all courses, there is no cycle; otherwise, a cycle exists."

---

# Complexity

Let:

```text
V = number of courses
E = number of prerequisites
```

```text
Time  → O(V + E)
Space → O(V + E)
```

---

# Common Mistakes

## Mistake 1 — Reversing the edge

For:

```text
[1,0]
```

meaning:

```text
0 must come before 1
```

we need:

```text
0 → 1
```

not:

```text
1 → 0
```

---

## Mistake 2 — Using number of prerequisites incorrectly

`indegree[course]` represents:

```text
How many prerequisites does this course still have?
```

---

## Mistake 3 — Only checking whether the queue becomes empty

The correct cycle check is:

```text
processed == numCourses
```

---

# Kahn's Algorithm Template

```text
1. Build graph
2. Calculate indegree
3. Add all indegree-0 nodes to queue

while queue not empty:

    node = queue.poll()

    processed++

    for neighbor:

        indegree[neighbor]--

        if indegree[neighbor] == 0:
            queue.offer(neighbor)

if processed == total nodes:
    no cycle
else:
    cycle exists
```

---

# 7. Walls and Gates

## Problem

You are given a grid containing:

```text
0    → gate
-1   → wall
INF  → empty room
```

For every empty room, fill it with the distance to its **nearest gate**.

If there is no gate reachable from a room, leave it as `INF`.

The Day 10 sheet classifies this as a **BFS Grid, Medium** problem. 

---

# Example

Input:

```text
[
    [INF, -1,  0, INF],
    [INF, INF, INF, -1],
    [INF, -1, INF, -1],
    [0,   -1, INF, INF]
]
```

Output:

```text
[
    [3, -1,  0, 1],
    [2,  2,  1, -1],
    [1, -1,  2, -1],
    [0, -1,  3,  4]
]
```

---

# Pattern Recognition

Look for:

```text
Multiple gates
Nearest gate
Shortest distance
Grid
```

Think:

```text
MULTI-SOURCE BFS
```

This is very similar to:

```text
Rotting Oranges
```

from Part 2.

The difference is:

```text
Rotting Oranges
→ BFS calculates time

Walls and Gates
→ BFS calculates distance
```

---

# Why Multi-Source BFS?

Suppose we have:

```text
Gate A

Gate B
```

A room may be close to either gate.

We want:

```text
minimum distance to ANY gate
```

So put **all gates** into the queue initially.

Then BFS expands simultaneously from every gate.

The first time we reach a room:

```text
that is its nearest gate
```

because BFS explores by increasing distance.

---

# Approach 1 — Brute Force

For every empty room:

```text
Run BFS to find nearest gate
```

If there are `R × C` rooms, we'd potentially run a large BFS from each one.

Very expensive.

---

# Approach 2 — BFS from Every Gate Separately

Run BFS independently from each gate and update:

```text
distance[row][col]
```

with the minimum distance.

This avoids some brute-force issues but can still repeatedly visit the same cells.

---

# Optimal Approach — Multi-Source BFS

### Step 1

Find all gates:

```text
grid[r][c] == 0
```

Add every gate to the queue.

---

### Step 2

BFS.

For every current cell:

```text
up
down
left
right
```

---

### Step 3

If the neighboring cell is:

```text
INF
```

set:

```text
neighbor = current + 1
```

and add it to the queue.

Because BFS expands by distance:

```text
0
↓
1
↓
2
↓
3
```

the first distance assigned to a room is its shortest distance.

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public void wallsAndGates(int[][] rooms) {

        int rows = rooms.length;
        int cols = rooms[0].length;

        Queue<int[]> queue =
                new LinkedList<>();

        // Add all gates
        for (int r = 0; r < rows; r++) {

            for (int c = 0; c < cols; c++) {

                if (rooms[r][c] == 0) {

                    queue.offer(
                        new int[]{r, c}
                    );
                }
            }
        }

        int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int row = current[0];
            int col = current[1];

            for (int[] direction : directions) {

                int newRow =
                        row + direction[0];

                int newCol =
                        col + direction[1];

                if (newRow < 0
                        || newRow >= rows
                        || newCol < 0
                        || newCol >= cols) {

                    continue;
                }

                // Only process unvisited empty rooms
                if (rooms[newRow][newCol]
                        != Integer.MAX_VALUE) {

                    continue;
                }

                rooms[newRow][newCol] =
                        rooms[row][col] + 1;

                queue.offer(
                    new int[]{newRow, newCol}
                );
            }
        }
    }
}
```

---

# Dry Run

Consider:

```text
[
    [INF, -1, 0],
    [INF, INF, INF],
    [INF, -1, INF]
]
```

Initial queue:

```text
[(0,2)]
```

because:

```text
(0,2) = gate
```

---

## Distance 0

```text
0
```

Queue:

```text
[(0,2)]
```

---

## Distance 1

From `(0,2)`:

```text
(1,2)
```

becomes:

```text
1
```

Queue:

```text
[(1,2)]
```

---

## Distance 2

From `(1,2)`:

```text
(1,1)
(2,2)
```

become:

```text
2
```

---

## Distance 3

From `(1,1)`:

```text
(1,0)
```

becomes:

```text
3
```

Then:

```text
(0,0)
```

becomes:

```text
4
```

The BFS naturally calculates the shortest distance.

---

# Interview Explanation

> "Because there can be multiple gates and every room needs its distance to the nearest gate, I use multi-source BFS. I initially put all gates into the queue with distance zero. Then I expand in four directions. When an unvisited empty room is reached, its distance is the current room's distance plus one. Since BFS explores levels in increasing distance, the first distance assigned to each room is its minimum distance to any gate."

---

# Complexity

For a grid of `R × C`:

```text
Time  → O(R × C)
Space → O(R × C)
```

Every reachable room is processed once.

---

# Common Mistakes

## Mistake 1 — Starting BFS from every empty room

This destroys the efficiency of the multi-source approach.

Instead:

```text
ALL GATES
   ↓
QUEUE
   ↓
BFS
```

---

## Mistake 2 — Treating walls as normal cells

Walls:

```text
-1
```

cannot be traversed.

---

## Mistake 3 — Updating a room repeatedly

Only process:

```text
Integer.MAX_VALUE
```

rooms.

Once assigned a distance, it has already been reached by the shortest path.

---

# Multi-Source BFS Pattern

```text
Find ALL sources
      ↓
Put ALL sources in queue
      ↓
Distance = 0
      ↓
BFS
      ↓
Expand neighbors
      ↓
Distance = current + 1
```

Examples:

```text
Rotting Oranges
Walls and Gates
01 Matrix
Nearest Exit
```

---

# Rotting Oranges vs Walls and Gates

Very important comparison:

| Problem         | Source           | What BFS Measures |
| --------------- | ---------------- | ----------------- |
| Rotting Oranges | Rotten oranges   | Time              |
| Walls and Gates | Gates            | Distance          |
| Both            | Multiple sources | Multi-Source BFS  |

Think:

```text
BFS LEVEL = DISTANCE
```

If the problem says:

```text
"How many minutes?"
```

then:

```text
BFS level = time
```

If it says:

```text
"How far?"
```

then:

```text
BFS level = distance
```

---

# 8. Number of Recent Calls

## Problem

Design a class that records requests.

For every:

```text
ping(t)
```

return the number of requests that happened within:

```text
[t - 3000, t]
```

The Day 10 sheet classifies this as:

```text
Queue Design
Easy
```



---

# Example

Calls:

```text
ping(1)
ping(100)
ping(3001)
ping(3002)
```

For:

```text
ping(3001)
```

valid range:

```text
[1, 3001]
```

All three requests:

```text
1
100
3001
```

are valid.

Answer:

```text
3
```

For:

```text
ping(3002)
```

range:

```text
[2, 3002]
```

`1` is no longer valid.

So:

```text
100
3001
3002
```

Answer:

```text
3
```

---

# Pattern Recognition

Look for:

```text
Recent
Last X milliseconds
Sliding time window
Remove old events
Events arrive in increasing order
```

Think:

```text
QUEUE + SLIDING WINDOW
```

---

# Why Queue?

Requests arrive chronologically:

```text
1 → 100 → 3001 → 3002
```

The oldest request is always at the front.

When it becomes too old:

```text
poll()
```

it out.

This is exactly FIFO.

---

# Approach 1 — Store Everything

Keep every request in an array/list.

For every `ping(t)`:

```text
scan all previous timestamps
count those >= t - 3000
```

This can become:

```text
O(n)
```

per query.

Over many calls:

```text
O(n²)
```

---

# Approach 2 — Queue

Store timestamps in a queue.

For each `ping(t)`:

### Step 1

Add:

```text
t
```

### Step 2

Remove timestamps that are too old:

```text
timestamp < t - 3000
```

### Step 3

Return:

```text
queue.size()
```

Because all remaining timestamps are valid.

---

# Important Boundary Condition

Suppose:

```text
t = 3001
```

Valid range:

```text
[1, 3001]
```

Therefore:

```text
1
```

is still valid.

So remove only:

```text
timestamp < t - 3000
```

NOT:

```text
timestamp <= t - 3000
```

This is a classic interview edge case.

---

# Optimal Java Code

```java
import java.util.*;

class RecentCounter {

    private Queue<Integer> queue;

    public RecentCounter() {
        queue = new LinkedList<>();
    }

    public int ping(int t) {

        queue.offer(t);

        while (!queue.isEmpty()
                && queue.peek() < t - 3000) {

            queue.poll();
        }

        return queue.size();
    }
}
```

---

# Dry Run

Operations:

```text
ping(1)
ping(100)
ping(3001)
ping(3002)
```

---

## `ping(1)`

Add:

```text
[1]
```

Range:

```text
[-2999, 1]
```

Answer:

```text
1
```

---

## `ping(100)`

Add:

```text
[1, 100]
```

Range:

```text
[-2900, 100]
```

Answer:

```text
2
```

---

## `ping(3001)`

Add:

```text
[1, 100, 3001]
```

Range:

```text
[1, 3001]
```

`1` is still valid.

Answer:

```text
3
```

---

## `ping(3002)`

Add:

```text
[1, 100, 3001, 3002]
```

Range:

```text
[2, 3002]
```

Now:

```text
1 < 2
```

so remove:

```text
[100, 3001, 3002]
```

Answer:

```text
3
```

---

# Interview Explanation

> "Since timestamps arrive in increasing order, I maintain them in a queue. On every ping, I add the new timestamp and remove timestamps that are older than the 3000-millisecond window. Because the queue is ordered, old timestamps are always at the front and can be removed efficiently. The remaining queue size is the number of recent requests."

---

# Complexity

Each timestamp:

```text
enters the queue once
leaves the queue once
```

Therefore the amortized complexity is:

```text
Time  → O(1) amortized per ping
Space → O(n)
```

where `n` is the number of timestamps currently inside the 3000ms window.

---

# Common Mistakes

## Mistake 1 — Removing the boundary timestamp

Wrong:

```java
timestamp <= t - 3000
```

Correct:

```java
timestamp < t - 3000
```

because the interval is:

```text
[t - 3000, t]
```

and both endpoints are included.

---

## Mistake 2 — Using a stack

Stack would remove the newest element first.

We need:

```text
oldest → removed first
```

Therefore:

```text
QUEUE
```

---

## Mistake 3 — Scanning the entire history

We don't need timestamps older than the window.

The queue automatically discards them.

---

# Sliding Time Window Template

```java
Queue<Integer> queue = new LinkedList<>();

public int process(int time) {

    queue.offer(time);

    while (!queue.isEmpty()
            && queue.peek() < time - WINDOW) {

        queue.poll();
    }

    return queue.size();
}
```

---

# Part 3 — Pattern Summary

```text
┌───────────────────────────────────────────────┐
│              QUEUE PATTERN PART 3             │
├───────────────────────────────────────────────┤
│                                               │
│  6. Course Schedule                          │
│     ↓                                         │
│     Directed Graph                            │
│     + Indegree                                │
│     + Queue                                   │
│     + Kahn's Algorithm                        │
│     = Topological Sort                        │
│                                               │
│  7. Walls and Gates                          │
│     ↓                                         │
│     Multi-Source BFS                          │
│     Gate = Source                             │
│     BFS Level = Distance                      │
│                                               │
│  8. Number of Recent Calls                   │
│     ↓                                         │
│     Queue + Time Window                       │
│     Remove expired timestamps                 │
│                                               │
└───────────────────────────────────────────────┘
```

---

# Three Interview Triggers

## Trigger 1 — Dependencies

```text
Prerequisites
Dependencies
Course ordering
Can tasks be completed?
```

Think:

```text
Graph
  ↓
Indegree
  ↓
Queue
  ↓
Kahn's Algorithm
  ↓
Topological Sort
```

---

## Trigger 2 — Multiple Sources + Nearest Distance

```text
Multiple gates
Nearest gate
Distance from multiple sources
```

Think:

```text
Multi-Source BFS
```

---

## Trigger 3 — Recent Events

```text
Last 3000 ms
Recent requests
Events within a time range
```

Think:

```text
Queue + Sliding Window
```

---

# BFS Master Template

```java
Queue<Node> queue = new LinkedList<>();

queue.offer(start);

while (!queue.isEmpty()) {

    int size = queue.size();

    for (int i = 0; i < size; i++) {

        Node current = queue.poll();

        // Process current

        // Add valid neighbors
    }
}
```

---

# Multi-Source BFS Master Template

```java
Queue<Node> queue = new LinkedList<>();

// Add ALL sources
for (Node node : sources) {
    queue.offer(node);
}

while (!queue.isEmpty()) {

    int size = queue.size();

    for (int i = 0; i < size; i++) {

        Node current = queue.poll();

        for (Node neighbor : neighbors(current)) {

            if (valid(neighbor)) {

                markVisited(neighbor);

                queue.offer(neighbor);
            }
        }
    }

    distance++;
}
```

---

# Kahn's Algorithm Master Template

```java
int[] indegree = new int[n];

List<List<Integer>> graph =
        new ArrayList<>();

Queue<Integer> queue =
        new LinkedList<>();

// Build graph + indegree

for (int i = 0; i < n; i++) {

    if (indegree[i] == 0) {
        queue.offer(i);
    }
}

int processed = 0;

while (!queue.isEmpty()) {

    int current = queue.poll();

    processed++;

    for (int next : graph.get(current)) {

        indegree[next]--;

        if (indegree[next] == 0) {
            queue.offer(next);
        }
    }
}

boolean possible = processed == n;
```

---

# Queue vs BFS vs Deque

Don't mix these concepts.

```text
QUEUE
↓
FIFO data structure
```

```text
BFS
↓
Algorithm
↓
Usually implemented using Queue
```

```text
DEQUE
↓
Double-ended Queue
↓
Can add/remove from both ends
↓
Useful for Monotonic Queue/Deque
```

So:

```text
Queue ≠ BFS
```

Rather:

```text
BFS → commonly uses Queue
```

---

# Day 10 Progress

```text
✅ 1. Binary Tree Level Order Traversal
      → Tree BFS

✅ 2. Implement Queue using Stacks
      → Queue Design

✅ 3. Perfect Squares
      → BFS / Implicit Graph

✅ 4. Sliding Window Maximum
      → Monotonic Deque

✅ 5. Rotting Oranges
      → Multi-Source BFS

✅ 6. Course Schedule
      → Kahn's Algorithm / Topological Sort

✅ 7. Walls and Gates
      → Multi-Source BFS

✅ 8. Number of Recent Calls
      → Queue + Sliding Time Window

⬜ 9. Dota2 Senate
⬜ 10. Open the Lock
⬜ 11. Design Circular Queue
⬜ 12. Moving Average from Data Stream
⬜ 13. Reveal Cards in Increasing Order
⬜ 14. Find the Safest Path in a Grid
⬜ 15. Time Needed to Inform All Employees
```

---

# Next — Queue Pattern Part 4

```text
9. Dota2 Senate
10. Open the Lock
11. Design Circular Queue
```

### Focus

```text
Queue Simulation
BFS Shortest Path
Circular Queue Design
Circular Array
```
