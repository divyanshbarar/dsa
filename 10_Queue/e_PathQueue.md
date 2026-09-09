# Day 10 — Queue Pattern

# Queue Pattern — Part 5

> **Final Part of Day 10:** Queue + Sliding Window, Deque Simulation, BFS + Priority Queue, and BFS on a Tree.
>
> Problems:
>
> * **12. Moving Average from Data Stream**
> * **13. Reveal Cards in Increasing Order**
> * **14. Find the Safest Path in a Grid**
> * **15. Time Needed to Inform All Employees**
>
> After this part, the complete **Day 10 Queue sheet** is finished. The sheet lists these four problems as Queue/Sliding Window, Simulation/Deque, BFS + Priority Queue, and BFS Tree respectively.  

---

# 12. Moving Average from Data Stream

## Problem

Design a data structure that calculates the **moving average of the last `size` values** from a stream of integers.

You are given a fixed window size.

Every time:

```text
next(value)
```

is called:

1. Add the new value.
2. If the window becomes larger than `size`, remove the oldest value.
3. Return the average of the current window.

---

# Example

Suppose:

```text
size = 3
```

Calls:

```text
next(1)
next(10)
next(3)
next(5)
```

### `next(1)`

Window:

```text
[1]
```

Average:

```text
1 / 1 = 1.0
```

---

### `next(10)`

Window:

```text
[1, 10]
```

Average:

```text
11 / 2 = 5.5
```

---

### `next(3)`

Window:

```text
[1, 10, 3]
```

Average:

```text
14 / 3 = 4.666...
```

---

### `next(5)`

Window should contain only the last 3:

```text
[10, 3, 5]
```

So:

```text
18 / 3 = 6.0
```

The Day 10 sheet classifies this as **Queue + Sliding Window, Easy**. 

---

# Pattern Recognition

Look for:

```text
last K elements
recent K values
moving average
fixed-size window
data stream
```

Think:

```text
QUEUE + SLIDING WINDOW
```

---

# The Key Observation

The values arrive in order:

```text
1 → 10 → 3 → 5
```

The oldest value is always at the front.

So:

```text
new value    → offer()
old value    → poll()
```

That's exactly what a queue is designed for.

---

# Approach 1 — Store Everything and Recalculate

For every `next()`:

```text
1. Add value
2. Find the last K values
3. Calculate their sum
4. Divide by K
```

If there are `n` values and the window size is `k`:

```text
Time per query → O(k)
```

This is wasteful because most values in consecutive windows are the same.

---

# Approach 2 — Queue + Recalculate Sum

We can maintain the current window using a queue:

```text
[10, 3, 5]
```

But every time we need the average, calculate the sum again.

Still:

```text
O(k)
```

per operation.

---

# Optimal Approach — Queue + Running Sum

Maintain:

```text
Queue
+
sum
```

When a value enters:

```text
sum += value
```

When the oldest value leaves:

```text
sum -= oldest
```

Therefore:

```text
average = sum / queue.size()
```

No repeated scanning.

---

# Optimal Java Code

```java
import java.util.*;

class MovingAverage {

    private Queue<Integer> queue;
    private int size;
    private double sum;

    public MovingAverage(int size) {

        this.size = size;
        this.queue = new LinkedList<>();
        this.sum = 0;
    }

    public double next(int value) {

        queue.offer(value);
        sum += value;

        if (queue.size() > size) {

            int removed = queue.poll();

            sum -= removed;
        }

        return sum / queue.size();
    }
}
```

---

# Dry Run

Window size:

```text
3
```

Initial:

```text
queue = []
sum = 0
```

---

## `next(1)`

Add:

```text
queue = [1]
sum = 1
```

Average:

```text
1 / 1 = 1.0
```

---

## `next(10)`

```text
queue = [1, 10]
sum = 11
```

Average:

```text
11 / 2 = 5.5
```

---

## `next(3)`

```text
queue = [1, 10, 3]
sum = 14
```

Average:

```text
14 / 3
= 4.666...
```

---

## `next(5)`

Add:

```text
queue = [1, 10, 3, 5]
sum = 19
```

Queue is too large.

Remove:

```text
1
```

Now:

```text
queue = [10, 3, 5]
sum = 18
```

Average:

```text
18 / 3
= 6.0
```

---

# Interview Explanation

> "I maintain the current window in a queue because values arrive in chronological order and the oldest value must leave first. I also maintain a running sum. When a new value arrives, I add it to the sum, and if the window exceeds its capacity, I remove the oldest value and subtract it from the sum. This gives O(1) time per operation."

---

# Complexity

```text
next() → O(1)
Space  → O(k)
```

This is much better than recalculating the entire window every time.

---

# Common Mistakes

## Mistake 1 — Recalculating the sum

Don't repeatedly do:

```text
sum = 0

for every element in window:
    sum += element
```

Maintain:

```text
running sum
```

instead.

---

## Mistake 2 — Removing the newest element

We need to remove:

```text
oldest
```

Therefore:

```java
queue.poll();
```

not the back.

---

## Mistake 3 — Dividing by fixed `size` immediately

Initially the queue might contain fewer than `size` elements.

For example:

```text
size = 3

queue = [10]
```

Average is:

```text
10 / 1
```

not:

```text
10 / 3
```

So use:

```java
sum / queue.size()
```

---

# Pattern Template

```text
new value
    ↓
queue.offer()
    ↓
sum += value
    ↓
queue.size() > k ?
    ↓
YES
    ↓
oldest = queue.poll()
sum -= oldest
    ↓
sum / queue.size()
```

---

# 13. Reveal Cards In Increasing Order

## Problem

You are given an array of unique integers representing cards.

You need to arrange them so that the following process reveals the cards in **increasing order**:

1. Reveal the top card.
2. Move the next top card to the bottom.
3. Repeat until all cards are revealed.

Return an ordering of the deck that produces increasing reveal order.

The Day 10 sheet classifies this as:

```text
Simulation (Deque)
Medium
```



---

# Example

Input:

```text
[17,13,11,2,3,5,7]
```

One valid ordering is:

```text
[2,13,3,11,5,17,7]
```

If we perform the reveal process:

```text
2
3
5
7
11
13
17
```

which is increasing.

---

# Pattern Recognition

The important clues are:

```text
deck
top
move top to bottom
repeated simulation
```

Think:

```text
DEQUE
```

because we need operations at both ends:

```text
removeFirst()
addLast()
```

---

# The Important Trick

Instead of simulating the reveal process on the original unsorted deck, start with the desired reveal order:

```text
sorted cards
```

Then reverse the process.

Suppose the desired reveal order is:

```text
2
3
5
7
11
13
17
```

We want to figure out where these cards should initially be placed.

---

# Reverse Simulation

The original process is:

```text
Reveal top
Move next top → bottom
```

Reverse that process.

Before placing a card back:

1. Move the last card to the front.
2. Put the current card at the front.

Then continue backward through the sorted cards.

---

# Why Does This Work?

Suppose the desired reveal sequence is:

```text
2 → 3 → 5 → 7 → 11 → 13 → 17
```

We reconstruct the original deck from the end.

Start:

```text
[]
```

Add `17`:

```text
[17]
```

Next `13`:

Move last to front:

```text
[17]
```

Then add `13`:

```text
[13,17]
```

Next `11`:

Move last to front:

```text
[17,13]
```

Add `11`:

```text
[11,17,13]
```

Continue this process.

---

# Approach 1 — Brute Force Permutations

Try different deck arrangements until the reveal sequence is increasing.

For `n` cards:

```text
n!
```

possible arrangements.

Clearly impossible for large `n`.

---

# Approach 2 — Direct Simulation With a List

We can repeatedly manipulate an array/list.

But inserting/removing from the front of an array-backed list can cost:

```text
O(n)
```

and make the solution unnecessarily expensive.

---

# Optimal Approach — Sort + Deque + Reverse Simulation

### Step 1

Sort cards.

```text
[2,3,5,7,11,13,17]
```

### Step 2

Create a deque representing positions in the original deck.

One convenient approach is:

```text
indices = [0,1,2,3,4,5,6]
```

### Step 3

For every sorted card:

```text
position = deque.pollFirst()
answer[position] = card
```

Then simulate:

```text
move next position to back
```

This directly models where each increasing card will be revealed.

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public int[] deckRevealedIncreasing(int[] deck) {

        int n = deck.length;

        Arrays.sort(deck);

        int[] result = new int[n];

        Deque<Integer> positions =
                new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            positions.offerLast(i);
        }

        for (int card : deck) {

            // Position where this card will be revealed
            int position = positions.pollFirst();

            result[position] = card;

            // Move the next position to the back
            if (!positions.isEmpty()) {
                positions.offerLast(
                        positions.pollFirst()
                );
            }
        }

        return result;
    }
}
```

---

# Dry Run

Cards:

```text
[17,13,11,2,3,5,7]
```

Sort:

```text
[2,3,5,7,11,13,17]
```

Positions:

```text
[0,1,2,3,4,5,6]
```

---

## Place `2`

Take position:

```text
0
```

Result:

```text
[2,_,_,_,_,_,_]
```

Move next position:

```text
[2,3,4,5,6,1]
```

---

## Place `3`

Take:

```text
2
```

Result:

```text
[2,_,3,_,_,_,_]
```

Move next:

```text
[4,5,6,1]
```

---

## Place `5`

Eventually the positions determine:

```text
[0,2,4,6,3,5,1]
```

and the resulting deck becomes:

```text
[2,13,3,11,5,17,7]
```

Now simulate the actual reveal:

```text
[2,13,3,11,5,17,7]

Reveal 2
Move 13 → back

[3,11,5,17,7,13]

Reveal 3
Move 11 → back

[5,17,7,13,11]

Reveal 5
...
```

Final reveal sequence:

```text
2 → 3 → 5 → 7 → 11 → 13 → 17
```

Correct.

---

# Interview Explanation

> "The reveal process is deterministic, so instead of guessing the original deck, I sort the cards in the order I want them revealed and simulate the positions of the deck. I maintain a deque of indices. For each sorted card, I take the front index as its reveal position, then move the next index to the back because the next card would be moved to the bottom during the reveal process."

---

# Complexity

Sorting dominates:

```text
Time → O(n log n)
```

Deque operations:

```text
O(n)
```

Overall:

```text
Time  → O(n log n)
Space → O(n)
```

---

# Common Mistakes

## Mistake 1 — Sorting the deck and returning it

Wrong:

```text
[2,3,5,7,11,13,17]
```

The original deck itself is **not** the reveal order.

We need to construct a special ordering.

---

## Mistake 2 — Simulating with repeated array shifting

This can become unnecessarily expensive.

Use:

```text
Deque
```

---

## Mistake 3 — Forgetting the second operation

The process is:

```text
reveal first
move second to back
```

Both operations matter.

---

# Pattern Template

```text
Desired final order
        ↓
Sort
        ↓
Simulate positions using Deque
        ↓
Assign cards to positions
```

---

# Important Insight

This is a great example of:

> **Reverse thinking.**

Instead of asking:

```text
"What happens if I start with this deck?"
```

ask:

```text
"What initial deck would produce my desired reveal order?"
```

This technique appears in many simulation problems.

---

# 14. Find the Safest Path in a Grid

## Problem

You are given an `n × n` grid.

Some cells contain thieves.

You need to travel from:

```text
top-left
```

to:

```text
bottom-right
```

The **safeness factor** of a path is the minimum Manhattan distance from any cell on that path to the nearest thief.

Your goal is:

> Find a path whose minimum safeness factor is as large as possible.

The Day 10 sheet specifically classifies this as:

```text
BFS + Priority Queue
Medium
```



---

# Understand the Problem First

Suppose a path has cell distances:

```text
[5, 4, 3, 6, 7]
```

The path's safeness factor is:

```text
minimum = 3
```

Another path:

```text
[4, 4, 4, 4]
```

has safeness:

```text
4
```

Therefore we prefer the second path.

So this is not:

```text
shortest path
```

It is:

```text
MAXIMIZE THE MINIMUM
```

---

# Pattern Recognition

This is a very important advanced pattern.

Look for:

```text
maximize the minimum
minimum value along a path
safest path
widest path
maximum bottleneck
```

Think:

```text
BFS / Distance Preprocessing
+
Priority Queue
```

---

# Step 1 — Calculate Distance From Every Cell to the Nearest Thief

Before finding the best path, we need:

```text
distance[r][c]
```

for every cell.

This is a classic:

```text
MULTI-SOURCE BFS
```

Why?

Because there can be multiple thieves.

Put **all thieves** into the queue initially.

Every thief has distance:

```text
0
```

Then BFS spreads outward.

---

# Example

Suppose:

```text
T . . .
. . . .
. . . .
. . . T
```

All thieves start at distance `0`.

BFS calculates:

```text
0 1 2 3
1 2 3 2
2 3 2 1
3 2 1 0
```

Each cell now knows its distance from the nearest thief.

---

# Step 2 — Find the Safest Path

Now every cell has a safety value.

Example:

```text
5 4 3
6 2 4
7 5 8
```

A path:

```text
5 → 4 → 3 → 4 → 8
```

has safeness:

```text
3
```

We want the path whose minimum value is maximum.

---

# Why Priority Queue?

Normal BFS optimizes:

```text
minimum number of edges
```

But here we want:

```text
maximum minimum safety
```

So when choosing the next cell, prioritize the path with the **highest safeness factor so far**.

Use:

```java
PriorityQueue
```

as a max heap.

---

# State in Priority Queue

For every state, store:

```text
row
col
currentSafeness
```

Suppose we arrive at:

```text
(r, c)
```

with current path safety:

```text
5
```

and the new cell has safety:

```text
3
```

The path safety becomes:

```text
min(5, 3)
= 3
```

So:

```text
newSafety =
    Math.min(currentSafety, distance[nr][nc]);
```

---

# This Is the Core Formula

```text
pathSafety =
    min(
        safety of every cell in path
    )
```

When extending the path:

```text
newPathSafety =
    min(
        currentPathSafety,
        neighborSafety
    )
```

And we use a max heap to always explore the path with the largest current safety first.

---

# Approach 1 — Brute Force

Enumerate paths from:

```text
(0,0)
```

to:

```text
(n-1,n-1)
```

and calculate the minimum safety of every path.

The number of possible paths can be enormous.

Not practical.

---

# Approach 2 — Binary Search + BFS

We can binary search a candidate safeness factor `k`.

Question:

> Can I reach the destination using only cells whose safety is at least `k`?

If yes:

```text
try larger k
```

If no:

```text
try smaller k
```

This is a valid approach.

---

# Optimal Approach — Multi-Source BFS + Max Heap

Because the sheet specifically teaches:

```text
BFS + Priority Queue
```

we'll use that approach.

### Phase 1

Multi-source BFS:

```text
thieves
   ↓
distance matrix
```

### Phase 2

Max-heap path search:

```text
start
  ↓
highest safety path first
  ↓
destination
```

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public int maximumSafenessFactor(
            List<List<Integer>> grid) {

        int n = grid.size();

        int[][] distance =
                new int[n][n];

        for (int[] row : distance) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue =
                new LinkedList<>();

        // Phase 1:
        // Multi-source BFS from all thieves
        for (int r = 0; r < n; r++) {

            for (int c = 0; c < n; c++) {

                if (grid.get(r).get(c) == 1) {

                    distance[r][c] = 0;

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
                        || newRow >= n
                        || newCol < 0
                        || newCol >= n) {

                    continue;
                }

                if (distance[newRow][newCol] != -1) {
                    continue;
                }

                distance[newRow][newCol] =
                        distance[row][col] + 1;

                queue.offer(
                    new int[]{newRow, newCol}
                );
            }
        }

        // Phase 2:
        // Max-heap based on current path safety
        PriorityQueue<int[]> pq =
                new PriorityQueue<>(
                    (a, b) ->
                        Integer.compare(b[0], a[0])
                );

        boolean[][] visited =
                new boolean[n][n];

        pq.offer(
            new int[]{
                distance[0][0],
                0,
                0
            }
        );

        while (!pq.isEmpty()) {

            int[] current = pq.poll();

            int safety = current[0];
            int row = current[1];
            int col = current[2];

            if (visited[row][col]) {
                continue;
            }

            visited[row][col] = true;

            if (row == n - 1
                    && col == n - 1) {

                return safety;
            }

            for (int[] direction : directions) {

                int newRow =
                        row + direction[0];

                int newCol =
                        col + direction[1];

                if (newRow < 0
                        || newRow >= n
                        || newCol < 0
                        || newCol >= n) {

                    continue;
                }

                if (visited[newRow][newCol]) {
                    continue;
                }

                int newSafety =
                        Math.min(
                            safety,
                            distance[newRow][newCol]
                        );

                pq.offer(
                    new int[]{
                        newSafety,
                        newRow,
                        newCol
                    }
                );
            }
        }

        return -1;
    }
}
```

---

# Dry Run — Conceptual

Suppose after multi-source BFS we have:

```text
distance:

5 4 3
6 5 2
7 6 4
```

Start:

```text
(0,0)
safety = 5
```

Priority queue:

```text
[(5,0,0)]
```

Move to `(0,1)`:

```text
min(5,4) = 4
```

Move to `(1,0)`:

```text
min(5,6) = 5
```

The priority queue prefers:

```text
(1,0) with safety 5
```

because it is safer than:

```text
(0,1) with safety 4
```

Suppose we eventually reach:

```text
(2,2)
```

with path safety:

```text
4
```

Then:

```text
answer = 4
```

---

# Why Is the First Destination From the Max Heap Optimal?

The priority queue always processes the state with the **largest possible current path safety** first.

If we reach the destination with safety `S`, any unexplored path currently has safety:

```text
≤ S
```

Therefore no future path can improve the answer.

This is analogous to Dijkstra's greedy idea, but instead of minimizing total distance, we're maximizing a bottleneck value.

---

# Interview Explanation

> "I solve this in two phases. First, I run multi-source BFS from all thieves to calculate the distance from every cell to its nearest thief. This distance becomes the safety value of the cell. Then I run a max-heap search from the top-left cell, where each state stores the maximum safeness factor achievable along that path. When moving to a neighbor, the new path safety is the minimum of the current path safety and the neighbor's safety. Because the priority queue always expands the path with the highest current safety first, the first time I reach the destination I have the maximum possible safeness factor."

---

# Complexity

Let the grid contain:

```text
n × n
```

cells.

### Multi-Source BFS

```text
O(n²)
```

### Priority Queue Search

There can be O(n²) states and each heap operation costs:

```text
O(log(n²))
= O(log n)
```

Therefore:

```text
Time  → O(n² log n)
Space → O(n²)
```

---

# Common Mistakes

## Mistake 1 — Treating this as normal shortest-path BFS

Normal BFS asks:

```text
minimum number of moves
```

This problem asks:

```text
maximum minimum safety
```

Different objective.

---

## Mistake 2 — Using only the destination's safety

The path's safety depends on the **minimum safety of every cell in the path**.

---

## Mistake 3 — Using sum instead of minimum

Wrong:

```text
current + neighbor
```

Correct:

```text
Math.min(current, neighbor)
```

---

## Mistake 4 — Forgetting the first BFS phase

We need to know the nearest thief distance for every cell before optimizing the path.

---

# Pattern Template

```text
PHASE 1

All sources
    ↓
Multi-Source BFS
    ↓
distance / safety matrix


PHASE 2

Start
    ↓
Max PriorityQueue
    ↓
newSafety = min(
    currentSafety,
    neighborSafety
)
    ↓
Destination
```

---

# Important Pattern

This is called a:

```text
MAX-MIN PATH
```

or:

```text
WIDEST / BOTTLENECK PATH
```

pattern.

Whenever you see:

```text
maximize the minimum
maximize weakest edge
safest path
widest path
maximum bottleneck
```

consider:

```text
PriorityQueue
```

---

# 15. Time Needed to Inform All Employees

## Problem

A company has:

```text
n employees
```

Each employee has:

```text
manager[i]
```

and:

```text
informTime[i]
```

`informTime[i]` represents how many minutes employee `i` needs to inform their direct subordinates.

The head of the company starts the process.

Find the total time required for **everyone** to receive the information.

The Day 10 sheet classifies this as:

```text
BFS Tree
Medium
```



---

# Example

Suppose:

```text
n = 6
headID = 2
```

Hierarchy:

```text
        2
       / \
      0   3
     / \
    1   4
         \
          5
```

Information flows:

```text
2
↓
0
↓
4
↓
5
```

The total time along this path is:

```text
informTime[2]
+ informTime[0]
+ informTime[4]
```

The answer is determined by the **slowest / longest chain**.

---

# Pattern Recognition

Look for:

```text
manager
subordinates
hierarchy
organization
tree
information spreads
time required
```

Think:

```text
TREE
+
BFS
```

---

# Important Observation

The company structure forms a tree/forest.

Every employee except the head has exactly one manager.

So:

```text
manager → subordinate
```

is a directed edge.

Example:

```text
2 → 0
2 → 3
0 → 1
0 → 4
4 → 5
```

---

# What Are We Actually Calculating?

Suppose:

```text
        A
       / \
      B   C
     /
    D
```

and:

```text
A = 2 minutes
B = 3 minutes
C = 5 minutes
D = 0
```

Path to `D`:

```text
A → B → D
```

Time:

```text
2 + 3 = 5
```

Path to `C`:

```text
A → C
```

Time:

```text
2 + 5 = 7
```

Everyone must receive the information.

Therefore:

```text
answer = max(5, 7)
       = 7
```

---

# Approach 1 — Brute Force

For every employee:

1. Walk upward through their managers.
2. Add the `informTime` values.
3. Track the maximum.

For a deeply nested organization, the same manager chain can be recalculated many times.

Worst case:

```text
O(n²)
```

---

# Approach 2 — DFS

Build the tree and recursively calculate:

```text
time =
informTime[current]
+
max(time of children)
```

This gives:

```text
Time  → O(n)
Space → O(n)
```

Very good solution.

---

# Optimal Approach — BFS

Since this is the Queue sheet, use BFS.

Build:

```text
manager
   ↓
subordinates
```

Then start from:

```text
headID
```

and process employees level by level.

For every employee, store:

```text
timeTaken
```

meaning:

> How much time has elapsed when this employee receives the information.

Then for each subordinate:

```text
childTime =
    currentTime + informTime[current]
```

Finally:

```text
answer = maximum timeTaken
```

---

# Building the Tree

Suppose:

```text
manager = [2,2,-1,2,0,4]
```

Then:

```text
manager[0] = 2
manager[1] = 2
manager[2] = -1
manager[3] = 2
manager[4] = 0
manager[5] = 4
```

Build:

```text
2 → [0,1,3]
0 → [4]
4 → [5]
```

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public int numOfMinutes(
            int n,
            int headID,
            int[] manager,
            int[] informTime) {

        List<List<Integer>> tree =
                new ArrayList<>();

        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        // Build manager → subordinate graph
        for (int employee = 0; employee < n; employee++) {

            if (manager[employee] != -1) {

                tree.get(manager[employee])
                        .add(employee);
            }
        }

        Queue<int[]> queue =
                new LinkedList<>();

        // {employee, timeTaken}
        queue.offer(
            new int[]{headID, 0}
        );

        int answer = 0;

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int employee = current[0];
            int time = current[1];

            answer = Math.max(answer, time);

            for (int subordinate :
                    tree.get(employee)) {

                int nextTime =
                        time + informTime[employee];

                queue.offer(
                    new int[]{
                        subordinate,
                        nextTime
                    }
                );
            }
        }

        return answer;
    }
}
```

---

# Dry Run

Suppose:

```text
manager:

[-1, 0, 0, 1]
```

Tree:

```text
        0
       / \
      1   2
     /
    3
```

Suppose:

```text
informTime:

[2, 3, 4, 0]
```

---

## Start

```text
queue = [(0,0)]
answer = 0
```

Employee `0` receives information at:

```text
0 minutes
```

Employee `0` takes:

```text
2 minutes
```

to inform children.

Queue:

```text
[(1,2), (2,2)]
```

---

## Process Employee 1

Employee `1` receives information at:

```text
2
```

Employee `1` takes:

```text
3
```

minutes.

So employee `3` receives information at:

```text
2 + 3 = 5
```

Queue:

```text
[(2,2), (3,5)]
```

---

## Process Employee 2

Receives at:

```text
2
```

No subordinates.

---

## Process Employee 3

Receives at:

```text
5
```

No subordinates.

Therefore:

```text
answer = 5
```

---

# Why Is It Not Simply Sum of All `informTime`?

Because managers inform their subordinates **in parallel**.

Example:

```text
        A
       / \
      B   C
```

Suppose:

```text
A = 2
B = 10
C = 5
```

Timeline:

```text
0 ──2
     ↓
    B and C receive information
     ↓
B works for 10
C works for 5
```

The total is:

```text
2 + max(10,5)
= 12
```

not:

```text
2 + 10 + 5
= 17
```

This is a very important insight.

---

# Interview Explanation

> "The organization forms a tree where each manager points to their direct subordinates. I build this adjacency list and perform BFS starting from the head. For each employee, I store the time at which they receive the information. Every subordinate receives it after the current employee's inform time, so their arrival time is `currentTime + informTime[current]`. Since employees can inform different branches in parallel, the answer is the maximum arrival time among all employees."

---

# Complexity

Let `n` be the number of employees.

Every employee is:

```text
added once
removed once
processed once
```

Therefore:

```text
Time  → O(n)
Space → O(n)
```

---

# Common Mistakes

## Mistake 1 — Summing all inform times

Wrong:

```text
sum of every employee's informTime
```

Information spreads through different branches simultaneously.

We need:

```text
maximum path time
```

---

## Mistake 2 — Starting from employee `0`

The head is:

```text
headID
```

Use:

```java
queue.offer(new int[]{headID, 0});
```

---

## Mistake 3 — Building the tree backwards

If:

```text
manager[employee] = manager
```

we need:

```text
manager → employee
```

---

## Mistake 4 — Forgetting that leaf employees have no subordinates

Their `informTime` doesn't need to contribute further.

---

# Pattern Template

```text
Build Tree:
manager → subordinates

Queue:
(headID, 0)

while queue not empty:

    employee, currentTime = poll()

    answer = max(answer, currentTime)

    for every subordinate:

        nextTime =
            currentTime
            + informTime[employee]

        offer(subordinate, nextTime)
```

---

# Part 5 — Final Summary

```text
┌───────────────────────────────────────────────┐
│              QUEUE PATTERN PART 5             │
├───────────────────────────────────────────────┤
│                                               │
│  12. Moving Average from Data Stream         │
│      ↓                                        │
│      Queue + Sliding Window                   │
│      Running Sum                              │
│      O(1) per operation                       │
│                                               │
│  13. Reveal Cards in Increasing Order        │
│      ↓                                        │
│      Sort + Deque Simulation                  │
│      Reverse Thinking                         │
│                                               │
│  14. Find the Safest Path in a Grid          │
│      ↓                                        │
│      Multi-Source BFS                         │
│      + Priority Queue                         │
│      Max-Min / Bottleneck Path                │
│                                               │
│  15. Time Needed to Inform All Employees     │
│      ↓                                        │
│      Tree BFS                                 │
│      Maximum Path Time                        │
│                                               │
└───────────────────────────────────────────────┘
```

---

# Day 10 — QUEUE SHEET COMPLETE ✅

```text
✅ 1. Binary Tree Level Order Traversal
   Pattern: Tree BFS

✅ 2. Implement Queue using Stacks
   Pattern: Queue Design

✅ 3. Perfect Squares
   Pattern: BFS / Implicit Graph

✅ 4. Sliding Window Maximum
   Pattern: Monotonic Deque

✅ 5. Rotting Oranges
   Pattern: Multi-Source BFS

✅ 6. Course Schedule
   Pattern: Kahn's Algorithm / Topological Sort

✅ 7. Walls and Gates
   Pattern: Multi-Source BFS

✅ 8. Number of Recent Calls
   Pattern: Queue + Sliding Time Window

✅ 9. Dota2 Senate
   Pattern: Queue Simulation

✅ 10. Open the Lock
    Pattern: BFS / Implicit Graph

✅ 11. Design Circular Queue
    Pattern: Circular Queue / Design

✅ 12. Moving Average from Data Stream
    Pattern: Queue + Sliding Window

✅ 13. Reveal Cards in Increasing Order
    Pattern: Deque Simulation

✅ 14. Find the Safest Path in a Grid
    Pattern: Multi-Source BFS + Priority Queue

✅ 15. Time Needed to Inform All Employees
    Pattern: Tree BFS
```

---

# Day 10 — Queue Master Pattern Map

```text
                         QUEUE
                           │
       ┌───────────────────┼────────────────────┐
       │                   │                    │
      BFS                DEQUE                DESIGN
       │                   │                    │
   ┌───┼────┐              │              ┌─────┴─────┐
   │   │    │              │              │           │
 Tree Grid Graph     Sliding Window    Queue using   Circular
   │    │    │        Maximum           Stacks        Queue
   │    │    │
   │    │    └── Open the Lock
   │    │
   │    ├── Rotting Oranges
   │    └── Walls and Gates
   │
   └── Level Order


                 ADVANCED
                    │
          ┌─────────┼─────────┐
          │         │         │
      Topological  Priority   Time
         Sort       Queue    Window
          │           │         │
    Course Schedule  Safest   Recent
                     Path     Calls
```

---

# Queue Interview Cheat Sheet

## Pattern 1 — Level Order

```text
"level by level"
"layer by layer"
```

↓

```text
BFS + Queue
```

---

## Pattern 2 — Shortest Path

```text
"minimum moves"
"minimum steps"
"shortest path"
```

↓

```text
BFS
```

provided every edge/move has equal cost.

---

## Pattern 3 — Multiple Sources

```text
"multiple starting points"
"spread simultaneously"
"nearest source"
"minimum time to spread"
```

↓

```text
Multi-Source BFS
```

---

## Pattern 4 — Sliding Window Maximum

```text
"maximum in every window"
```

↓

```text
Monotonic Deque
```

---

## Pattern 5 — Dependencies

```text
"prerequisite"
"dependency"
"ordering"
"cycle in directed graph"
```

↓

```text
Indegree + Queue
↓
Kahn's Algorithm
↓
Topological Sort
```

---

## Pattern 6 — Recent Events

```text
"last K"
"recent requests"
"time window"
```

↓

```text
Queue + Sliding Window
```

---

## Pattern 7 — Circular Queue

```text
"fixed capacity"
"reuse space"
"circular"
"ring buffer"
```

↓

```text
Array + Modulo
```

---

## Pattern 8 — Max-Min Path

```text
"maximize minimum"
"safest path"
"widest path"
"bottleneck"
```

↓

```text
Priority Queue
```

Often combined with:

```text
BFS / distance preprocessing
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

for (Node source : sources) {

    queue.offer(source);
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

# Monotonic Deque Master Template

### Maximum

```java
Deque<Integer> deque =
        new ArrayDeque<>();

for (int i = 0; i < n; i++) {

    while (!deque.isEmpty()
            && deque.peekFirst() < i - k + 1) {

        deque.pollFirst();
    }

    while (!deque.isEmpty()
            && nums[deque.peekLast()] <= nums[i]) {

        deque.pollLast();
    }

    deque.offerLast(i);

    if (i >= k - 1) {

        int maximum =
                nums[deque.peekFirst()];
    }
}
```

Remember:

```text
Maximum → decreasing deque
Minimum → increasing deque
```

---

# Kahn's Algorithm Master Template

```java
Queue<Integer> queue =
        new LinkedList<>();

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

if (processed == n) {
    // No cycle
} else {
    // Cycle exists
}
```

---

# Queue vs Stack — Final Comparison

```text
STACK
↓
LIFO
↓
Last In → First Out
↓
DFS / Undo / Matching / Monotonic Stack
```

```text
QUEUE
↓
FIFO
↓
First In → First Out
↓
BFS / Level Order / Simulation
```

```text
DEQUE
↓
Both Ends
↓
Sliding Window / Monotonic Queue
```

```text
PRIORITY QUEUE
↓
Highest/Lowest Priority First
↓
Best-first / Greedy / Bottleneck Problems
```

---

# Final Day 10 Golden Rules

> **1. Level by level → BFS + Queue**

> **2. Minimum moves with equal-cost edges → BFS**

> **3. Multiple sources spreading simultaneously → Multi-Source BFS**

> **4. Maximum/minimum inside a sliding window → Monotonic Deque**

> **5. Dependencies + cycle detection → Kahn's Algorithm**

> **6. Recent events inside a time range → Queue + Sliding Window**

> **7. Fixed-size reusable queue → Circular Queue + Modulo**

> **8. Maximize the minimum value along a path → Max-Heap / Priority Queue**

---

# Day 10 Complete 🎯

```text
15 / 15 Problems Completed

Queue Fundamentals              ✅
BFS                              ✅
Tree BFS                         ✅
Grid BFS                         ✅
Multi-Source BFS                 ✅
Implicit Graph BFS               ✅
Topological Sort                 ✅
Kahn's Algorithm                 ✅
Monotonic Deque                  ✅
Queue Simulation                 ✅
Sliding Time Window              ✅
Circular Queue                   ✅
Priority Queue                   ✅
Max-Min Path                     ✅
```

> **The biggest thing to remember from Day 10 is not the `Queue` syntax. It's pattern recognition: identify what the queue represents, then choose the right BFS/Deque/Priority Queue variation.**
