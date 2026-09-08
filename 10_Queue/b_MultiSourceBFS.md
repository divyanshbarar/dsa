# Day 10 — Queue Pattern

# Queue Pattern — Part 2

> **Focus:** BFS on implicit graphs, Monotonic Deque, and Multi-Source BFS.
>
> Problems:
>
> * **3. Perfect Squares**
> * **4. Sliding Window Maximum**
> * **5. Rotting Oranges**

These are the next three problems in the Day 10 Queue sheet. The sheet classifies them as **BFS**, **Monotonic Deque**, and **BFS Grid** respectively. 

---

# 3. Perfect Squares

## Problem

Given an integer `n`, return the **minimum number of perfect square numbers** whose sum is equal to `n`.

A perfect square is:

```text
1, 4, 9, 16, 25, ...
```

because:

```text
1 = 1 × 1
4 = 2 × 2
9 = 3 × 3
16 = 4 × 4
```

### Example

```text
Input:
12

Output:
3
```

Because:

```text
12 = 4 + 4 + 4
```

Another example:

```text
Input:
13

Output:
2
```

Because:

```text
13 = 9 + 4
```

The sheet classifies **Perfect Squares** as a **BFS, Medium** problem. 

---

# Pattern Recognition

At first glance this looks like a:

```text
Dynamic Programming
```

problem.

And DP is indeed a valid approach.

But the Queue sheet is teaching us another way:

```text
BFS
```

The key phrase is:

> **Minimum number of steps / minimum number of elements required.**

We can model every number as a node.

---

# Think of It as a Graph

Suppose:

```text
n = 12
```

We can subtract any perfect square:

```text
12 - 1 = 11
12 - 4 = 8
12 - 9 = 3
```

So:

```text
12
├── 11
├── 8
└── 3
```

From `8`:

```text
8 - 1 = 7
8 - 4 = 4
```

From `3`:

```text
3 - 1 = 2
```

Eventually:

```text
12 → 8 → 4 → 0
```

That's:

```text
4 + 4 + 4
```

Three steps.

So the problem becomes:

> Find the shortest path from `n` to `0`.

And shortest path in an unweighted graph:

```text
BFS
```

---

# Approach 1 — Brute Force Recursion

Try every possible perfect square.

For:

```text
12
```

try:

```text
1
4
9
```

Then recursively solve the remaining number.

This generates many overlapping subproblems.

### Complexity

Exponential in the worst case.

This approach is not suitable for large `n`.

---

# Approach 2 — Dynamic Programming

Define:

```text
dp[i] = minimum number of perfect squares needed to make i
```

Base case:

```text
dp[0] = 0
```

For every `i`:

```text
dp[i] = min(
    dp[i - 1] + 1,
    dp[i - 4] + 1,
    dp[i - 9] + 1,
    ...
)
```

This gives:

```text
Time  → O(n√n)
Space → O(n)
```

DP is a very good solution.

But because this is our **Queue pattern**, let's understand the BFS solution.

---

# Optimal Approach — BFS

Treat each number as a node.

From a number `current`, we can move to:

```text
current - 1
current - 4
current - 9
current - 16
...
```

as long as the result is non-negative.

Every subtraction represents:

```text
1 perfect square used
```

Therefore:

```text
BFS level = number of perfect squares used
```

The first time we reach `0`, we have the minimum answer.

---

# Example

For:

```text
n = 12
```

Level `0`:

```text
12
```

Level `1`:

```text
11
8
3
```

Level `2`:

```text
10
7
4
2
```

From `4`:

```text
4 - 4 = 0
```

So level `3` reaches:

```text
0
```

Answer:

```text
3
```

---

# Why Do We Need `visited`?

Consider:

```text
12 → 11
12 → 8
```

From different paths, we may reach the same number.

For example:

```text
12 → 11 → 7
12 → 8 → 7
```

There is no reason to process `7` repeatedly.

So maintain:

```java
boolean[] visited
```

Once a number has been visited, don't add it again.

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public int numSquares(int n) {

        Queue<Integer> queue = new LinkedList<>();

        boolean[] visited = new boolean[n + 1];

        queue.offer(n);
        visited[n] = true;

        int steps = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            steps++;

            for (int i = 0; i < size; i++) {

                int current = queue.poll();

                for (int j = 1; j * j <= current; j++) {

                    int next = current - j * j;

                    if (next == 0) {
                        return steps;
                    }

                    if (!visited[next]) {

                        visited[next] = true;

                        queue.offer(next);
                    }
                }
            }
        }

        return -1;
    }
}
```

---

# Dry Run

```text
n = 12
```

Initial:

```text
queue = [12]
steps = 0
```

---

## Level 1

Remove:

```text
12
```

Possible squares:

```text
1 → 11
4 → 8
9 → 3
```

Queue:

```text
[11, 8, 3]
```

```text
steps = 1
```

---

## Level 2

Process:

```text
11
8
3
```

Eventually we reach:

```text
4
```

Queue contains:

```text
...
4
...
```

```text
steps = 2
```

---

## Level 3

Process:

```text
4
```

Choose:

```text
4
```

Therefore:

```text
4 - 4 = 0
```

Return:

```text
3
```

---

# Interview Explanation

> "I model every number from `n` down to `0` as a node. From a number, I can move to the result of subtracting any perfect square. Every edge represents using one square, so the minimum number of squares becomes the shortest path from `n` to `0`. Since all edges have equal cost, I use BFS and stop when I reach zero."

---

# Complexity

There are `n` possible states, and for each state we may try up to `√n` squares.

```text
Time  → O(n√n)
Space → O(n)
```

---

# Common Mistakes

### Mistake 1 — Thinking BFS only applies to trees

BFS can work on:

```text
Trees
Graphs
Grids
Implicit graphs
```

Perfect Squares is an example of an **implicit graph**.

---

### Mistake 2 — Not using visited

Without `visited`, the same numbers can enter the queue many times.

---

### Mistake 3 — Returning when a node is generated without understanding the level

The `steps` variable represents the number of squares used.

---

# Pattern Template

```text
Start State
    ↓
Queue
    ↓
BFS level
    ↓
Generate all valid next states
    ↓
Mark visited
    ↓
Repeat
    ↓
Target reached
```

---

# 4. Sliding Window Maximum

## Problem

Given an integer array and a window of size `k`, find the maximum value in every window.

### Example

```text
nums = [1,3,-1,-3,5,3,6,7]
k = 3
```

Windows:

```text
[1, 3, -1] → 3

[3, -1, -3] → 3

[-1, -3, 5] → 5

[-3, 5, 3] → 5

[5, 3, 6] → 6

[3, 6, 7] → 7
```

Answer:

```text
[3,3,5,5,6,7]
```

The Day 10 sheet classifies this as:

```text
Monotonic Deque
Hard
```



---

# Pattern Recognition

Whenever you see:

```text
Sliding Window
+
Maximum / Minimum
```

immediately think:

```text
MONOTONIC DEQUE
```

This is an extremely important interview pattern.

---

# Why Not a Normal Queue?

Suppose:

```text
window = [1, 3, -1]
```

Maximum:

```text
3
```

Now window moves:

```text
[3, -1, -3]
```

Maximum is still:

```text
3
```

Then:

```text
[-1, -3, 5]
```

Maximum becomes:

```text
5
```

A normal queue doesn't efficiently tell us which element is the maximum.

---

# Approach 1 — Brute Force

For every window:

```text
find maximum by scanning k elements
```

There are approximately:

```text
n - k + 1
```

windows.

Each scan costs:

```text
O(k)
```

Therefore:

```text
Time → O(nk)
Space → O(1)
```

For large `n`, this is too slow.

---

# Approach 2 — Priority Queue

We could maintain a max heap.

For every element:

```text
add element
remove elements outside window
maximum = heap top
```

This gives approximately:

```text
O(n log k)
```

But we need to carefully handle stale elements.

It works, but we can do better.

---

# Optimal Approach — Monotonic Deque

We maintain a deque of **indices**.

The deque has this property:

```text
values are decreasing from front to back
```

Example:

```text
values:

9
7
5
2
```

So:

```text
front → 9 → 7 → 5 → 2
```

The front always contains the maximum.

---

# Why Store Indices?

Because we need to know whether an element has left the sliding window.

Suppose:

```text
k = 3
```

Current window:

```text
[2, 1, 5]
```

We need to know:

> Is the `2` still inside the window?

Values alone aren't enough.

Indices solve this.

---

# Two Important Rules

## Rule 1 — Remove Out-of-Window Indices

If:

```text
deque.front < i - k + 1
```

remove it.

Because it is no longer inside the current window.

---

## Rule 2 — Remove Smaller Elements From the Back

Suppose:

```text
deque:
[5, 3, 2]
```

New element:

```text
6
```

What happens?

`6` is larger than:

```text
2
3
5
```

So all of them become useless for future maximum queries.

Remove them:

```text
[]
```

Add:

```text
[6]
```

This is the monotonic property.

---

# Why Can We Remove Smaller Elements?

Suppose:

```text
5
```

is before:

```text
3
```

in the array.

If `3` is still in the window, then `5` is also older and larger.

Therefore `3` can never become the maximum while `5` is still available.

So `3` is useless.

This is the key insight.

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;

        int[] result = new int[n - k + 1];

        Deque<Integer> deque = new ArrayDeque<>();

        int resultIndex = 0;

        for (int i = 0; i < n; i++) {

            // Remove indices outside the window
            while (!deque.isEmpty()
                    && deque.peekFirst() < i - k + 1) {

                deque.pollFirst();
            }

            // Remove smaller elements
            while (!deque.isEmpty()
                    && nums[deque.peekLast()] <= nums[i]) {

                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Window is ready
            if (i >= k - 1) {

                result[resultIndex++] =
                        nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

---

# Dry Run

```text
nums = [1,3,-1,-3,5,3,6,7]
k = 3
```

---

## Add `1`

```text
deque = [1]
```

Actually the deque stores indices:

```text
deque = [0]
values = [1]
```

---

## Add `3`

`3 > 1`.

Remove `1`:

```text
deque = []
```

Add `3`:

```text
deque = [1]
```

Window:

```text
[1,3]
```

Not complete yet.

---

## Add `-1`

`-1` is smaller than `3`.

Keep `3`.

```text
deque = [1,2]
```

Window:

```text
[1,3,-1]
```

Front:

```text
index 1
value 3
```

Maximum:

```text
3
```

---

## Add `-3`

Window becomes:

```text
[3,-1,-3]
```

Deque:

```text
[1,2,3]
```

Maximum:

```text
3
```

---

## Add `5`

Before adding:

```text
deque values:
3, -1, -3
```

`5` is greater than all of them.

Remove from back:

```text
3 removed
-1 removed
-3 removed
```

Deque:

```text
[]
```

Add `5`:

```text
[5]
```

Maximum:

```text
5
```

---

# Important Visualization

The deque maintains:

```text
MAX
 ↓
[5, 3, 1]
```

not:

```text
[1, 3, 5]
```

So:

```text
front = maximum
```

This is why we call it a:

```text
MONOTONIC DEQUE
```

---

# Interview Explanation

> "I use a deque of indices and maintain the values in decreasing order. Before adding an element, I remove indices that are outside the current window. Then I remove smaller values from the back because they can never become the maximum while the current larger value is in the window. Therefore, the front of the deque always contains the maximum of the current window."

---

# Complexity

Each index:

```text
enters deque once
leaves deque once
```

Therefore:

```text
Time  → O(n)
Space → O(k)
```

This is much better than:

```text
Brute Force → O(nk)
Heap         → O(n log k)
Deque        → O(n)
```

---

# Common Mistakes

## Mistake 1 — Storing values instead of indices

Don't do:

```text
deque = [5, 3, 2]
```

without knowing their positions.

Use:

```java
Deque<Integer> deque
```

where each element is an **index**.

---

## Mistake 2 — Removing from the wrong side

### Out-of-window

Remove from:

```text
front
```

### Smaller elements

Remove from:

```text
back
```

Remember:

```text
front → maximum
back  → smaller candidates
```

---

## Mistake 3 — Using `<` instead of `<=`

We normally remove:

```java
nums[deque.peekLast()] <= nums[i]
```

Equal values can be removed because the newer one is at least as useful and will remain in the window longer.

---

## Mistake 4 — Returning the answer too early

The first complete window occurs when:

```text
i >= k - 1
```

---

# Monotonic Deque Template

For maximum:

```java
Deque<Integer> deque = new ArrayDeque<>();

for (int i = 0; i < n; i++) {

    // Remove out-of-window
    while (!deque.isEmpty()
            && deque.peekFirst() < i - k + 1) {

        deque.pollFirst();
    }

    // Maintain decreasing values
    while (!deque.isEmpty()
            && nums[deque.peekLast()] <= nums[i]) {

        deque.pollLast();
    }

    deque.offerLast(i);

    if (i >= k - 1) {
        answer = nums[deque.peekFirst()];
    }
}
```

For **minimum**, reverse the comparison:

```java
nums[deque.peekLast()] >= nums[i]
```

---

# Maximum vs Minimum

## Sliding Window Maximum

Maintain:

```text
decreasing deque
```

```text
front = largest
```

---

## Sliding Window Minimum

Maintain:

```text
increasing deque
```

```text
front = smallest
```

This distinction is extremely important.

---

# 5. Rotting Oranges

## Problem

You are given a grid containing:

```text
0 → empty cell
1 → fresh orange
2 → rotten orange
```

Every minute:

> A rotten orange causes adjacent fresh oranges to become rotten.

Adjacent means:

```text
up
down
left
right
```

Return the minimum number of minutes required to rot all oranges.

If some fresh orange can never be reached:

```text
return -1
```

The sheet classifies **Rotten Oranges** as a **BFS Grid, Medium** problem. 

---

# Example

Input:

```text
[
    [2,1,1],
    [1,1,0],
    [0,1,1]
]
```

Minute `0`:

```text
2 1 1
1 1 0
0 1 1
```

Minute `1`:

```text
2 2 1
2 1 0
0 1 1
```

Minute `2`:

```text
2 2 2
2 2 0
0 1 1
```

Minute `3`:

```text
2 2 2
2 2 0
0 2 1
```

Minute `4`:

```text
2 2 2
2 2 0
0 2 2
```

Answer:

```text
4
```

---

# Pattern Recognition

The important words are:

```text
spread
every minute
adjacent
simultaneously
minimum time
```

Immediately think:

```text
MULTI-SOURCE BFS
```

This is one of the most important BFS patterns.

---

# Why Multi-Source BFS?

There can be multiple rotten oranges initially.

Example:

```text
2 1 1 2
1 1 1 1
```

Both rotten oranges spread simultaneously.

So instead of starting BFS from one source:

```text
source A
```

we put **all initial rotten oranges** into the queue:

```text
queue = [A, B]
```

Then BFS processes them level by level.

Each BFS level represents:

```text
1 minute
```

---

# Approach 1 — Brute Force Simulation

Repeatedly scan the entire grid.

For every minute:

1. Find rotten oranges.
2. Find adjacent fresh oranges.
3. Mark them rotten.
4. Repeat.

If the grid is:

```text
m × n
```

we may repeatedly scan:

```text
m × n
```

cells for many minutes.

This can become inefficient.

---

# Approach 2 — BFS From Every Rotten Orange Separately

Run BFS independently from every rotten orange.

The problem is that the same fresh orange may be processed multiple times.

Also, independent BFS does not naturally represent simultaneous spreading.

---

# Optimal Approach — Multi-Source BFS

### Step 1

Count all fresh oranges.

```text
freshCount
```

### Step 2

Put **every rotten orange** into the queue.

```text
queue.offer(rottenOrange)
```

### Step 3

Process the queue level by level.

Each level:

```text
1 minute
```

### Step 4

For each rotten orange:

```text
up
down
left
right
```

If a neighbor is fresh:

```text
make it rotten
freshCount--
queue.offer(neighbor)
```

### Step 5

When:

```text
freshCount == 0
```

all oranges are rotten.

---

# Direction Array

Instead of writing:

```text
up
down
left
right
```

manually:

```java
int[][] directions = {
    {-1, 0},
    {1, 0},
    {0, -1},
    {0, 1}
};
```

Meaning:

```text
{-1, 0} → up
{1, 0}  → down
{0,-1}  → left
{0, 1}  → right
```

This is a reusable BFS-grid template.

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public int orangesRotting(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();

        int freshCount = 0;

        // Add all rotten oranges
        // and count fresh oranges
        for (int r = 0; r < rows; r++) {

            for (int c = 0; c < cols; c++) {

                if (grid[r][c] == 2) {

                    queue.offer(new int[]{r, c});

                } else if (grid[r][c] == 1) {

                    freshCount++;
                }
            }
        }

        // No fresh oranges
        if (freshCount == 0) {
            return 0;
        }

        int minutes = 0;

        int[][] directions = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
        };

        while (!queue.isEmpty()) {

            int size = queue.size();

            boolean rottedThisMinute = false;

            for (int i = 0; i < size; i++) {

                int[] current = queue.poll();

                int row = current[0];
                int col = current[1];

                for (int[] direction : directions) {

                    int newRow = row + direction[0];
                    int newCol = col + direction[1];

                    if (newRow < 0
                            || newRow >= rows
                            || newCol < 0
                            || newCol >= cols) {
                        continue;
                    }

                    if (grid[newRow][newCol] != 1) {
                        continue;
                    }

                    grid[newRow][newCol] = 2;

                    freshCount--;

                    rottedThisMinute = true;

                    queue.offer(
                        new int[]{newRow, newCol}
                    );
                }
            }

            if (rottedThisMinute) {
                minutes++;
            }
        }

        return freshCount == 0
                ? minutes
                : -1;
    }
}
```

---

# Dry Run

Input:

```text
[
    [2,1,1],
    [1,1,0],
    [0,1,1]
]
```

Initial rotten:

```text
(0,0)
```

Fresh:

```text
6
```

Queue:

```text
[(0,0)]
```

---

## Minute 1

Process:

```text
(0,0)
```

Neighbors:

```text
(1,0) → fresh
(0,1) → fresh
```

Make rotten:

```text
2 2 1
2 1 0
0 1 1
```

Queue:

```text
[(1,0), (0,1)]
```

Fresh:

```text
4
```

---

## Minute 2

Process:

```text
(1,0)
(0,1)
```

New rotten:

```text
(1,1)
(0,2)
```

Grid:

```text
2 2 2
2 2 0
0 1 1
```

Fresh:

```text
2
```

---

## Minute 3

Process:

```text
(1,1)
(0,2)
```

New rotten:

```text
(2,1)
```

Grid:

```text
2 2 2
2 2 0
0 2 1
```

Fresh:

```text
1
```

---

## Minute 4

Process:

```text
(2,1)
```

New rotten:

```text
(2,2)
```

Grid:

```text
2 2 2
2 2 0
0 2 2
```

Fresh:

```text
0
```

Answer:

```text
4
```

---

# Why `size = queue.size()` Matters

This is critical.

At the beginning of every minute:

```java
int size = queue.size();
```

That tells us:

> These are exactly the oranges that were rotten before this minute started.

We process exactly these nodes.

Any newly rotten oranges are added to the queue:

```java
queue.offer(...)
```

but they are processed in the **next BFS level**.

Therefore:

```text
BFS Level = 1 Minute
```

---

# Interview Explanation

> "This is a multi-source BFS problem because multiple rotten oranges start spreading simultaneously. I first put all rotten oranges into the queue and count the fresh oranges. Then I process the queue level by level, where each level represents one minute. Whenever a rotten orange reaches a fresh neighbor, I mark it rotten, decrease the fresh count, and add it to the queue. At the end, if freshCount is zero I return the elapsed minutes; otherwise I return -1."

---

# Complexity

For a grid of:

```text
rows × cols
```

every cell is processed at most once.

```text
Time  → O(rows × cols)
Space → O(rows × cols)
```

---

# Common Mistakes

## Mistake 1 — Starting BFS from only one rotten orange

Wrong:

```text
choose one rotten orange
→ BFS
```

Correct:

```text
ALL rotten oranges
        ↓
      queue
        ↓
Multi-source BFS
```

---

## Mistake 2 — Incrementing time for every orange

Wrong:

```text
orange processed → minutes++
```

Time should increase **once per BFS level**.

---

## Mistake 3 — Processing newly rotten oranges immediately

Newly rotten oranges belong to the:

```text
NEXT MINUTE
```

This is why:

```java
int size = queue.size();
```

is important.

---

## Mistake 4 — Forgetting unreachable fresh oranges

Example:

```text
2 0 1
```

The fresh orange can never be reached.

Therefore:

```text
return -1
```

---

# Multi-Source BFS Template

This is extremely important.

```java
Queue<Node> queue = new LinkedList<>();

// Add ALL starting nodes
for (...) {

    if (isSource(node)) {
        queue.offer(node);
    }
}

int steps = 0;

while (!queue.isEmpty()) {

    int size = queue.size();

    for (int i = 0; i < size; i++) {

        Node current = queue.poll();

        for (Node neighbor : neighbors(current)) {

            if (isValid(neighbor)) {

                markVisited(neighbor);

                queue.offer(neighbor);
            }
        }
    }

    steps++;
}
```

---

# BFS Types You Should Know

By now, distinguish these three:

## Normal BFS

```text
One starting point
        ↓
BFS
```

Example:

```text
Open the Lock
```

---

## Multi-Source BFS

```text
Multiple starting points
        ↓
BFS
```

Example:

```text
Rotting Oranges
Walls and Gates
```

---

## BFS on Implicit Graph

There isn't an explicit graph structure.

We generate neighbors ourselves.

Example:

```text
Perfect Squares
```

---

# Part 2 Revision

```text
┌──────────────────────────────────────────────┐
│              QUEUE PATTERN PART 2            │
├──────────────────────────────────────────────┤
│                                              │
│  3. Perfect Squares                         │
│     ↓                                        │
│     BFS on Implicit Graph                    │
│     Shortest Path                            │
│                                              │
│  4. Sliding Window Maximum                  │
│     ↓                                        │
│     Monotonic Deque                         │
│     O(n)                                     │
│                                              │
│  5. Rotting Oranges                         │
│     ↓                                        │
│     Multi-Source BFS                        │
│     BFS Level = Time                        │
│                                              │
└──────────────────────────────────────────────┘
```

---

# The 3 Most Important Interview Signals

## Signal 1

```text
Minimum number of moves
Minimum steps
Shortest path
```

Think:

```text
BFS
```

---

## Signal 2

```text
Sliding Window
Maximum / Minimum
```

Think:

```text
Monotonic Deque
```

---

## Signal 3

```text
Multiple starting points
Spread simultaneously
Minimum time to spread
```

Think:

```text
Multi-Source BFS
```

---

# One-Minute Revision

```text
Perfect Squares
    ↓
Treat numbers as states
    ↓
Subtract perfect squares
    ↓
Shortest path n → 0
    ↓
BFS
```

```text
Sliding Window Maximum
    ↓
Need maximum in every window
    ↓
Remove useless smaller elements
    ↓
Maintain decreasing deque
    ↓
O(n)
```

```text
Rotting Oranges
    ↓
Multiple rotten oranges
    ↓
Spread simultaneously
    ↓
Put all sources into queue
    ↓
BFS level = minute
    ↓
Multi-Source BFS
```

---

# Golden Rule

> **Queue problems are not always "just use a Queue." The real skill is recognizing what the queue represents.**

In these three problems:

```text
Perfect Squares
→ Queue represents states waiting to be explored.

Sliding Window Maximum
→ Deque represents useful candidates for the maximum.

Rotting Oranges
→ Queue represents the current BFS frontier / current minute.
```

---

# Next — Queue Pattern Part 3

```text
6. Course Schedule
7. Walls and Gates
8. Number of Recent Calls
```

### Focus

```text
Topological Sort
Graph BFS
Multi-Source BFS
Queue Design
Time-Based Window
```

These problems will introduce **Kahn's Algorithm**, another extremely important Queue pattern for interviews.
