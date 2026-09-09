# Day 10 — Queue Pattern

# Queue Pattern — Part 4

> **Focus:** Queue Simulation, BFS Shortest Path, and Circular Queue Design.
>
> Problems:
>
> * **9. Dota2 Senate**
> * **10. Open the Lock**
> * **11. Design Circular Queue**

These are the next three problems from the Day 10 Queue sheet. The sheet classifies them as **Queue Simulation**, **BFS**, and **Design** respectively. 

---

# 9. Dota2 Senate

## Problem

There are two parties:

```text
R → Radiant
D → Dire
```

The senators are represented by a string.

For example:

```text
"RDD"
```

means:

```text
R
D
D
```

Each senator gets a turn in the order they appear.

A senator can:

1. **Ban another senator's right to vote**
2. If all senators of one party are banned, that party wins.

Return:

```text
"Radiant"
```

or:

```text
"Dire"
```

---

# Example

Input:

```text
"RD"
```

Process:

```text
R → bans D
```

Only `R` remains.

Answer:

```text
"Radiant"
```

---

# Another Example

```text
"RDD"
```

The first `R` can ban one `D`.

Then the remaining `D` senators continue.

Eventually:

```text
Dire
```

wins.

The Day 10 sheet identifies this problem as **Queue Simulation, Medium**. 

---

# Pattern Recognition

The important clues are:

```text
process in original order
remove / ban elements
removed element doesn't get another turn
process repeats cyclically
```

Think:

```text
QUEUE SIMULATION
```

The queue is perfect because:

```text
front senator → gets current turn
```

After surviving:

```text
front senator → goes to back
```

So the process continues cyclically.

---

# Key Observation

Suppose:

```text
"RDD"
```

Instead of actually deleting characters from a string repeatedly, maintain:

```text
Radiant queue
Dire queue
```

Store their positions.

Example:

```text
R D D
0 1 2
```

Queues:

```text
Radiant → [0]
Dire    → [1, 2]
```

Now compare:

```text
0 vs 1
```

Radiant's senator comes first.

Radiant gets to act first.

After using the turn, the surviving senator gets a new future position:

```text
index + n
```

This is the trick.

---

# Why `index + n`?

Suppose:

```text
n = 5
```

and a senator at index:

```text
2
```

survives this round.

After everyone gets their current turn, that senator should come back at the end.

So assign:

```text
2 + 5 = 7
```

Conceptually:

```text
0 1 2 3 4
```

then:

```text
5 6 7 8 9
```

The new position:

```text
7
```

means:

> This senator gets another turn after the current round.

---

# Approach 1 — Brute Force String Simulation

We could repeatedly:

```text
find next senator
ban opponent
remove opponent
repeat
```

This requires frequent string manipulation.

For a large input, repeatedly modifying the string can become inefficient.

---

# Approach 2 — One Queue

We could maintain the entire sequence in one queue and track the number of active senators of each party.

But deciding which opponent to ban and preserving the correct future order becomes more complicated.

---

# Optimal Approach — Two Queues of Indices

Maintain:

```text
Radiant Queue
Dire Queue
```

Each queue stores the indices of senators belonging to that party.

At every round:

```text
r = radiant.peek()
d = dire.peek()
```

Compare:

```text
r < d
```

If true:

```text
Radiant acts first
```

Otherwise:

```text
Dire acts first
```

The senator who acts first survives and gets:

```text
index + n
```

The other senator is removed permanently.

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public String predictPartyVictory(String senate) {

        int n = senate.length();

        Queue<Integer> radiant = new LinkedList<>();
        Queue<Integer> dire = new LinkedList<>();

        for (int i = 0; i < n; i++) {

            if (senate.charAt(i) == 'R') {
                radiant.offer(i);
            } else {
                dire.offer(i);
            }
        }

        while (!radiant.isEmpty()
                && !dire.isEmpty()) {

            int r = radiant.poll();
            int d = dire.poll();

            if (r < d) {

                // Radiant acts first
                radiant.offer(r + n);

            } else {

                // Dire acts first
                dire.offer(d + n);
            }
        }

        return radiant.isEmpty()
                ? "Dire"
                : "Radiant";
    }
}
```

---

# Dry Run

Input:

```text
"RDD"
```

Indices:

```text
R → 0
D → 1
D → 2
```

Queues:

```text
Radiant → [0]
Dire    → [1, 2]
```

---

## Round 1

Take:

```text
R = 0
D = 1
```

Compare:

```text
0 < 1
```

Radiant acts first.

So:

```text
D at index 1
```

is banned.

Radiant survives and returns later:

```text
0 + 3 = 3
```

Queues:

```text
Radiant → [3]
Dire    → [2]
```

---

## Round 2

Take:

```text
R = 3
D = 2
```

Now:

```text
3 > 2
```

Dire acts first.

Radiant is banned.

Dire survives:

```text
2 + 3 = 5
```

Queues:

```text
Radiant → []
Dire    → [5]
```

Radiant is empty.

Therefore:

```text
Dire
```

wins.

---

# Interview Explanation

> "I use two queues containing the indices of Radiant and Dire senators. At every turn I compare the front indices. The senator with the smaller index acts first and bans the opponent. The surviving senator gets another turn in the next cycle, so I add its index plus `n` back to its queue. When one queue becomes empty, the other party wins."

---

# Complexity

Each senator can participate in multiple rounds, but every ban permanently removes one senator.

Therefore:

```text
Time  → O(n)
Space → O(n)
```

---

# Common Mistakes

## Mistake 1 — Comparing characters instead of positions

The important information is:

```text
WHO GETS TO ACT FIRST?
```

That depends on their current position.

---

## Mistake 2 — Not adding `n`

If a senator survives:

```text
index
```

they need to return to the back:

```text
index + n
```

---

## Mistake 3 — Removing from the wrong party

If:

```text
r < d
```

Radiant acts first.

Therefore:

```text
Dire is banned
```

---

# Pattern Template

```text
Queue A → positions of A
Queue B → positions of B

while both queues are non-empty:

    a = A.poll()
    b = B.poll()

    if a < b:
        A.offer(a + n)
    else:
        B.offer(b + n)

winner = non-empty queue
```

---

# Important Insight

This problem looks like:

```text
String Simulation
```

but the real pattern is:

```text
QUEUE + POSITION
```

Whenever a process repeats cyclically and surviving elements need to come back later, think:

```text
Queue + modified position
```

---

# 10. Open the Lock

## Problem

You have a lock with four wheels:

```text
0000
```

Each wheel contains:

```text
0 → 1 → 2 → ... → 9 → 0
```

In one move, you can rotate **one wheel by one position**.

Some combinations are deadends.

You need to find the **minimum number of moves** required to reach a target combination.

If the target cannot be reached:

```text
return -1
```

The Day 10 sheet classifies **Open the Lock** as a **BFS, Medium** problem. 

---

# Example

Start:

```text
0000
```

Target:

```text
0009
```

We can rotate the last wheel backward:

```text
0000
→
0009
```

So answer:

```text
1
```

---

# Pattern Recognition

Look for:

```text
minimum moves
minimum turns
minimum transformations
each move has equal cost
states
deadends
```

Think:

```text
BFS
```

This is a classic **BFS on an implicit graph**.

---

# Why Is It a Graph?

Every lock combination is a node.

For:

```text
0000
```

we can make 8 possible moves:

```text
1000
9000

0100
0900

0010
0090

0001
0009
```

Therefore:

```text
0000
├── 1000
├── 9000
├── 0100
├── 0900
├── 0010
├── 0090
├── 0001
└── 0009
```

Each move has cost:

```text
1
```

So we need the shortest path:

```text
0000 → target
```

That means:

```text
BFS
```

---

# Approach 1 — Brute Force DFS

Try every possible sequence of rotations.

Problem:

```text
many possible paths
```

We could repeatedly revisit the same lock combination.

Without careful visited handling, the search can explode.

---

# Approach 2 — DFS + Visited

DFS can explore all reachable states.

But DFS does **not naturally guarantee the shortest path**.

We would need to track minimum distances, making it more complicated.

---

# Optimal Approach — BFS

Every BFS level represents:

```text
1 move
2 moves
3 moves
...
```

So the first time we reach the target:

```text
minimum number of moves
```

---

# Number of Possible States

There are:

```text
10 × 10 × 10 × 10
```

possible combinations.

Therefore:

```text
10000 states
```

This is small enough for BFS.

---

# Algorithm

### Step 1

Put:

```text
0000
```

into the queue.

### Step 2

Create a `Set` of deadends.

### Step 3

Create a `visited` set.

### Step 4

For every state:

* rotate each wheel forward
* rotate each wheel backward
* generate 8 neighbors

### Step 5

Ignore:

```text
deadends
visited states
```

### Step 6

When target is reached:

```text
return moves
```

---

# Generating Neighbors

Suppose:

```text
0000
```

For the first wheel:

```text
1000
9000
```

For second:

```text
0100
0900
```

For third:

```text
0010
0090
```

For fourth:

```text
0001
0009
```

Total:

```text
8 neighbors
```

---

# Wheel Rotation Trick

Suppose current digit is:

```text
0
```

Forward:

```text
(0 + 1) % 10 = 1
```

Backward:

```text
(0 + 9) % 10 = 9
```

Suppose current digit is:

```text
9
```

Forward:

```text
(9 + 1) % 10 = 0
```

Backward:

```text
(9 + 9) % 10 = 8
```

So modulo handles wrap-around elegantly.

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public int openLock(
            String[] deadends,
            String target) {

        Set<String> dead =
                new HashSet<>(Arrays.asList(deadends));

        if (dead.contains("0000")) {
            return -1;
        }

        Queue<String> queue =
                new LinkedList<>();

        Set<String> visited =
                new HashSet<>();

        queue.offer("0000");
        visited.add("0000");

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {

                String current = queue.poll();

                if (current.equals(target)) {
                    return moves;
                }

                for (int wheel = 0; wheel < 4; wheel++) {

                    char[] chars =
                            current.toCharArray();

                    // Rotate forward
                    chars[wheel] =
                            chars[wheel] == '9'
                                    ? '0'
                                    : (char)(chars[wheel] + 1);

                    String next =
                            new String(chars);

                    if (!dead.contains(next)
                            && visited.add(next)) {

                        queue.offer(next);
                    }

                    // Rotate backward
                    chars =
                            current.toCharArray();

                    chars[wheel] =
                            chars[wheel] == '0'
                                    ? '9'
                                    : (char)(chars[wheel] - 1);

                    next = new String(chars);

                    if (!dead.contains(next)
                            && visited.add(next)) {

                        queue.offer(next);
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}
```

---

# Dry Run

Suppose:

```text
start = "0000"
target = "0009"
```

Initial:

```text
queue = ["0000"]
moves = 0
```

---

## Level 0

Process:

```text
0000
```

Generate:

```text
1000
9000
0100
0900
0010
0090
0001
0009
```

`0009` is the target.

Therefore:

```text
answer = 1
```

---

# Another Important Example

Suppose target is:

```text
0202
```

BFS explores:

```text
0000
 ↓
all states 1 move away
 ↓
all states 2 moves away
 ↓
...
```

The first time:

```text
0202
```

appears, that level is the minimum number of moves.

---

# Interview Explanation

> "I model every four-digit lock combination as a graph node. From each state there are eight possible neighboring states because each of the four wheels can be rotated forward or backward. Since every move has equal cost and we need the minimum number of moves, I use BFS. I maintain a visited set to avoid processing the same combination repeatedly and a deadend set to prevent invalid states."

---

# Complexity

There are at most:

```text
10,000
```

states.

Each state has:

```text
8
```

neighbors.

Therefore:

```text
Time  → O(10,000 × 8)
      → O(1) with respect to fixed lock size
```

More generally, for `d` wheels:

```text
Time → O(10^d × d)
```

Space:

```text
O(10^d)
```

---

# Common Mistakes

## Mistake 1 — Using DFS

The problem asks:

```text
minimum moves
```

So:

```text
BFS
```

is the natural choice.

---

## Mistake 2 — Forgetting `visited`

The graph contains cycles.

For example:

```text
0000
→ 0001
→ 0000
```

Without `visited`, BFS can repeatedly revisit states.

---

## Mistake 3 — Forgetting deadends

A deadend state cannot be entered.

---

## Mistake 4 — Incorrect `9 → 0`

Wheel rotation wraps around:

```text
9 → 0
0 → 9
```

---

# BFS Shortest Path Template

```text
start
  ↓
Queue
  ↓
BFS Level 0
  ↓
Generate neighbors
  ↓
BFS Level 1
  ↓
Generate neighbors
  ↓
...
  ↓
Target
```

The key:

```text
BFS level = number of moves
```

---

# 11. Design Circular Queue

## Problem

Design a queue with a fixed capacity `k`.

The queue should support:

```text
enQueue(value)
deQueue()
Front()
Rear()
isEmpty()
isFull()
```

The Day 10 sheet classifies **Design Circular Queue** as a **Design, Medium** problem. 

---

# Why Circular?

Consider a normal array:

```text
[10, 20, 30, 40, _]
 ↑              ↑
front           rear
```

Suppose we remove:

```text
10
20
```

Now:

```text
[_, _, 30, 40, _]
```

There is free space at the beginning.

If we simply keep moving `rear` forward, we may incorrectly think the queue is full.

A circular queue reuses that space.

---

# Circular Idea

Imagine the array connected in a circle:

```text
       ┌───────────────────┐
       ↓                   │
    [0][1][2][3][4]────────┘
```

After reaching the last index:

```text
4
```

we return to:

```text
0
```

using:

```text
(index + 1) % capacity
```

---

# Pattern Recognition

Whenever you see:

```text
fixed-size queue
reuse empty spaces
circular
ring buffer
```

think:

```text
CIRCULAR QUEUE
```

---

# Approach 1 — Brute Force

Use a normal array and shift elements after every dequeue.

Example:

```text
[10,20,30,40]
```

After removing `10`:

```text
[20,30,40]
```

Shift everything left.

This makes:

```text
deQueue → O(n)
```

Not ideal.

---

# Approach 2 — Array + Front/Rear

Maintain:

```text
front
rear
```

and use modulo:

```text
rear = (rear + 1) % capacity
```

But we need to carefully determine:

```text
empty?
full?
```

Using only front and rear can require an extra slot or special conditions.

---

# Optimal Approach — Array + Front + Size

A cleaner design is:

```text
array
front
size
capacity
```

We don't actually need to track `rear`.

The rear index can be calculated as:

```text
(front + size) % capacity
```

This makes the design very easy to reason about.

---

# State Representation

Suppose:

```text
capacity = 5
```

Initially:

```text
front = 0
size = 0
```

Queue:

```text
[_, _, _, _, _]
```

---

# Enqueue

To insert:

```text
value
```

calculate:

```text
rear = (front + size) % capacity
```

Place the value there.

Then:

```text
size++
```

---

# Dequeue

Remove:

```text
array[front]
```

Then:

```text
front = (front + 1) % capacity
size--
```

No shifting required.

---

# Front

If:

```text
size == 0
```

return:

```text
-1
```

Otherwise:

```text
array[front]
```

---

# Rear

The last element is located at:

```text
(front + size - 1) % capacity
```

---

# Optimal Java Code

```java
class MyCircularQueue {

    private int[] queue;
    private int front;
    private int size;
    private int capacity;

    public MyCircularQueue(int k) {

        queue = new int[k];

        capacity = k;

        front = 0;

        size = 0;
    }

    public boolean enQueue(int value) {

        if (isFull()) {
            return false;
        }

        int rear =
                (front + size) % capacity;

        queue[rear] = value;

        size++;

        return true;
    }

    public boolean deQueue() {

        if (isEmpty()) {
            return false;
        }

        front =
                (front + 1) % capacity;

        size--;

        return true;
    }

    public int Front() {

        if (isEmpty()) {
            return -1;
        }

        return queue[front];
    }

    public int Rear() {

        if (isEmpty()) {
            return -1;
        }

        int rear =
                (front + size - 1)
                % capacity;

        return queue[rear];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}
```

---

# Dry Run

Capacity:

```text
3
```

Initially:

```text
array = [_, _, _]
front = 0
size = 0
```

---

## `enQueue(10)`

Rear:

```text
(0 + 0) % 3 = 0
```

Array:

```text
[10, _, _]
```

```text
size = 1
front = 0
```

---

## `enQueue(20)`

Rear:

```text
(0 + 1) % 3 = 1
```

Array:

```text
[10, 20, _]
```

```text
size = 2
```

---

## `enQueue(30)`

Rear:

```text
(0 + 2) % 3 = 2
```

Array:

```text
[10, 20, 30]
```

```text
size = 3
```

Queue is full.

---

## `deQueue()`

Remove:

```text
10
```

Move front:

```text
front = (0 + 1) % 3
      = 1
```

Now:

```text
array = [10,20,30]
             ↑
           front
```

Logically the queue is:

```text
20 → 30
```

Even though `10` is still physically present in the array, it is no longer part of the queue because:

```text
size = 2
```

---

## `enQueue(40)`

Rear:

```text
(front + size) % capacity
= (1 + 2) % 3
= 0
```

So we reuse index `0`.

Array:

```text
[40, 20, 30]
```

Logical queue:

```text
20 → 30 → 40
```

This is the circular behavior.

---

# Circular Visualization

```text
Initial:

[10][20][30]
 ↑
front


deQueue(10):

[10][20][30]
     ↑
   front


enQueue(40):

[40][20][30]
     ↑
   front
```

The physical array looks unusual, but logically:

```text
20 → 30 → 40
```

is the correct queue.

---

# Why `size` Is Useful

Without `size`, it becomes harder to distinguish:

```text
front == rear
```

meaning:

```text
empty?
```

or:

```text
full?
```

With `size`:

```text
size == 0
```

means:

```text
EMPTY
```

and:

```text
size == capacity
```

means:

```text
FULL
```

Very clean.

---

# Interview Explanation

> "I implement the circular queue using a fixed-size array and maintain the front index and current size. The rear position is calculated using `(front + size) % capacity`. On dequeue, I move the front using modulo instead of shifting elements. This allows the queue to reuse freed positions at the beginning of the array and keeps every operation O(1)."

---

# Complexity

Every operation is constant time:

```text
enQueue → O(1)
deQueue → O(1)
Front   → O(1)
Rear    → O(1)
isEmpty → O(1)
isFull  → O(1)
```

Space:

```text
O(k)
```

where `k` is the queue capacity.

---

# Common Mistakes

## Mistake 1 — Forgetting modulo

Wrong:

```java
front = front + 1;
```

Correct:

```java
front = (front + 1) % capacity;
```

Otherwise the index eventually goes out of bounds.

---

## Mistake 2 — Shifting elements

A circular queue exists specifically to avoid:

```text
O(n)
```

shifting.

---

## Mistake 3 — Wrong rear formula

The last element is:

```text
(front + size - 1) % capacity
```

The next insertion position is:

```text
(front + size) % capacity
```

Don't confuse these.

---

## Mistake 4 — Forgetting empty/full conditions

Always check:

```java
isEmpty()
isFull()
```

before modifying the queue.

---

# Circular Queue Template

```text
front = 0
size = 0
capacity = k

ENQUEUE:
    if full → false

    rear = (front + size) % capacity
    array[rear] = value
    size++

DEQUEUE:
    if empty → false

    front = (front + 1) % capacity
    size--

FRONT:
    array[front]

REAR:
    array[(front + size - 1) % capacity]

EMPTY:
    size == 0

FULL:
    size == capacity
```

---

# Part 4 — Pattern Summary

```text
┌───────────────────────────────────────────────┐
│              QUEUE PATTERN PART 4             │
├───────────────────────────────────────────────┤
│                                               │
│  9. Dota2 Senate                             │
│     ↓                                         │
│     Queue Simulation                          │
│     + Position Tracking                       │
│                                               │
│  10. Open the Lock                           │
│      ↓                                        │
│      BFS + Implicit Graph                     │
│      Shortest Path                            │
│                                               │
│  11. Design Circular Queue                   │
│      ↓                                        │
│      Circular Array                           │
│      Modulo                                   │
│      O(1) Operations                          │
│                                               │
└───────────────────────────────────────────────┘
```

---

# Three Important Interview Triggers

## Trigger 1 — Cyclic Simulation

```text
Process people in order
Remove/bans people
Survivors get another turn
```

Think:

```text
QUEUE
+
POSITION
```

Example:

```text
Dota2 Senate
```

---

## Trigger 2 — Minimum Moves

```text
Minimum transformations
Minimum turns
Minimum moves
Every move has equal cost
```

Think:

```text
BFS
```

If the states aren't explicitly given:

```text
BFS on Implicit Graph
```

Example:

```text
Open the Lock
Perfect Squares
```

---

## Trigger 3 — Fixed Capacity + Reuse Space

```text
Circular
Ring buffer
Fixed-size queue
Reuse freed positions
```

Think:

```text
Circular Queue
+
Modulo
```

---

# BFS vs Queue Simulation

Don't confuse these.

### Dota2 Senate

```text
Queue
↓
Maintain order
↓
Simulate turns
```

No shortest-path problem.

---

### Open the Lock

```text
States
↓
Neighbors
↓
Minimum moves
↓
BFS
```

Here the queue is being used as part of a **graph traversal algorithm**.

---

# Circular Array Formula Cheat Sheet

These formulas are worth memorizing:

### Move forward

```text
(index + 1) % capacity
```

### Move backward

```text
(index - 1 + capacity) % capacity
```

### Next insertion position

```text
(front + size) % capacity
```

### Last element

```text
(front + size - 1) % capacity
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

✅ 9. Dota2 Senate
      → Queue Simulation

✅ 10. Open the Lock
       → BFS / Implicit Graph

✅ 11. Design Circular Queue
       → Circular Queue / Design

⬜ 12. Moving Average from Data Stream
⬜ 13. Reveal Cards in Increasing Order
⬜ 14. Find the Safest Path in a Grid
⬜ 15. Time Needed to Inform All Employees
```

---

# One-Minute Revision

```text
Dota2 Senate
    ↓
Two queues
    ↓
Compare positions
    ↓
Winner goes back with index + n
```

```text
Open the Lock
    ↓
Each lock state = node
    ↓
8 possible moves
    ↓
Minimum moves
    ↓
BFS
```

```text
Circular Queue
    ↓
Fixed array
    ↓
front + size
    ↓
Modulo %
    ↓
O(1) operations
```

---

# Golden Rule

> **Queue is not just FIFO. In interviews, learn to recognize what the queue is representing.**

```text
Queue
├── BFS
│   ├── Tree
│   ├── Graph
│   ├── Grid
│   └── Implicit States
│
├── Multi-Source BFS
│   ├── Rotting Oranges
│   └── Walls and Gates
│
├── Topological Sort
│   └── Kahn's Algorithm
│
├── Monotonic Deque
│   └── Sliding Window Maximum
│
├── Simulation
│   └── Dota2 Senate
│
├── Time Window
│   └── Number of Recent Calls
│
└── Design
    ├── Queue using Stacks
    └── Circular Queue
```

---

# Next — Queue Pattern Part 5

```text
12. Moving Average from Data Stream
13. Reveal Cards in Increasing Order
14. Find the Safest Path in a Grid
15. Time Needed to Inform All Employees
```

### Focus

```text
Queue + Sliding Window
Deque Simulation
BFS + Priority Queue
BFS Tree
Weighted / Priority-Based Search
```

> **After Part 5, all 15 Queue problems from the Day 10 sheet will be completed.**
