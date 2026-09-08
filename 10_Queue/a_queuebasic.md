# Day 10 — Queue Pattern

# Queue Pattern — Part 1

> **Goal:** Understand the Queue pattern deeply and master the first problems from the Day 10 Queue sheet using the same interview-focused approach as Day 9.

According to the Day 10 sheet, the Queue topic covers **FIFO**, different queue types, BFS/level-order traversal, sliding windows, simulations, circular queues, and priority queues. The sheet contains **15 problems** ranging from Easy to Hard.  

---

# 1. What is a Queue?

A **Queue** is a linear data structure that follows:

```text
FIFO
First In → First Out
```

The element that enters first is removed first.

Think about a line at a ticket counter:

```text
Person A → Person B → Person C → Person D
   ↑
 first
```

Person A gets served first.

The basic operations are:

```text
enqueue() → add at rear
dequeue() → remove from front
peek()    → view front
isEmpty() → check empty
```

These are exactly the core operations listed in the sheet. 

---

# Queue Visualization

```text
                QUEUE

Front                           Rear
  ↓                               ↓
┌────┬────┬────┬────┐
│ 10 │ 20 │ 30 │ 40 │
└────┴────┴────┴────┘
  ↑
dequeue()                  enqueue()
                              ↓
                           add here
```

If we call:

```java
queue.poll();
```

we remove:

```text
10
```

If we call:

```java
queue.offer(50);
```

we get:

```text
20 30 40 50
```

---

# Queue in Java

The sheet uses the `Queue` interface with `LinkedList` as the basic Java example. 

```java
Queue<Integer> queue = new LinkedList<>();

queue.offer(10);
queue.offer(20);

System.out.println(queue.peek()); // 10

System.out.println(queue.poll()); // 10
```

After this:

```text
queue = [20]
```

---

# Important Java Queue Methods

| Operation | Java Method | Meaning      |
| --------- | ----------- | ------------ |
| Enqueue   | `offer()`   | Add at rear  |
| Dequeue   | `poll()`    | Remove front |
| Peek      | `peek()`    | View front   |
| Empty     | `isEmpty()` | Check empty  |

For interview problems, prefer:

```java
offer()
poll()
peek()
```

rather than manually managing indexes unless the problem asks you to implement the queue yourself.

---

# Types of Queues

The Day 10 sheet identifies four important queue types. 

```text
1. Simple Queue
2. Circular Queue
3. Deque
4. Priority Queue
```

---

## 1. Simple Queue

Normal FIFO:

```text
10 → 20 → 30 → 40
↑               ↑
front           rear
```

---

## 2. Circular Queue

The rear can wrap around to the beginning.

Useful when we want to efficiently reuse empty spaces.

```text
      ┌───────────────┐
      ↓               │
    [10][20][30][40]──┘
```

This becomes important in:

```text
Design Circular Queue
```

from the sheet.

---

## 3. Deque

Deque = **Double-Ended Queue**

We can add/remove from both ends.

```text
addFirst()
removeFirst()

addLast()
removeLast()
```

Java:

```java
Deque<Integer> deque = new ArrayDeque<>();
```

Deque becomes extremely important for:

```text
Sliding Window Maximum
```

because we need to maintain candidates for the maximum efficiently.

The Day 10 sheet specifically classifies Sliding Window Maximum as a **Monotonic Deque** problem. 

---

## 4. Priority Queue

A Priority Queue does **not** necessarily remove elements in insertion order.

Instead, the highest/lowest priority element comes out first.

Java:

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

This will become important for:

```text
Find the Safest Path in a Grid
```

which the sheet classifies as:

```text
BFS + Priority Queue
```



---

# Queue Pattern Recognition

This is the most important part for interviews.

When should you think:

```text
QUEUE
```

?

The sheet gives several major clues: BFS/level-order traversal, first-come-first-served processing, sliding windows, producer-consumer systems, flood fill, and shortest-path traversal. 

---

# Clue 1 — BFS

If the problem says:

```text
level by level
layer by layer
minimum number of steps
shortest path in an unweighted graph
spread simultaneously
```

think:

```text
QUEUE + BFS
```

---

# Clue 2 — Tree Level Order

If you see:

```text
level order traversal
```

immediately think:

```text
QUEUE
```

Because we process:

```text
level 1
   ↓
level 2
   ↓
level 3
```

---

# Clue 3 — First Come, First Serve

If elements must be processed in the same order they arrive:

```text
Queue
```

The sheet explicitly identifies **order preservation** as a major queue clue. 

---

# Clue 4 — Shortest Path

If every movement has equal cost:

```text
BFS
```

and therefore:

```text
QUEUE
```

Example:

```text
Open the Lock
```

from the Day 10 sheet is classified as BFS. 

---

# Clue 5 — Spread / Infection

If something spreads to neighboring cells simultaneously:

```text
BFS
```

Examples:

```text
Rotting Oranges
Walls and Gates
```

Both appear in the sheet as BFS Grid problems. 

---

# Clue 6 — Sliding Window

If a window moves across an array:

```text
[left ... right]
```

and we need to efficiently remove old elements and add new elements:

```text
Deque
```

The sheet specifically includes:

```text
Sliding Window Maximum
```

as a **Monotonic Deque** problem. 

---

# Queue vs Stack

Very important for interviews.

| Stack              | Queue              |
| ------------------ | ------------------ |
| LIFO               | FIFO               |
| Last In First Out  | First In First Out |
| `push()`           | `offer()`          |
| `pop()`            | `poll()`           |
| `peek()`           | `peek()`           |
| DFS-style problems | BFS-style problems |
| Undo               | Processing order   |

Think:

```text
STACK
Last person enters
→ gets served first
```

versus:

```text
QUEUE
First person enters
→ gets served first
```

---

# Queue Master Pattern

Before solving problems, memorize this:

```text
QUEUE

Add:
offer()

Remove:
poll()

View:
peek()

Check:
isEmpty()
```

For BFS:

```text
1. Put starting node into queue
2. While queue isn't empty
3. Remove front
4. Process it
5. Add unvisited neighbors
```

This single template solves a huge number of interview problems.

---

# Problem 1 — Binary Tree Level Order Traversal

## Problem

Given the root of a binary tree, return its nodes **level by level**.

### Example

```text
        3
       / \
      9   20
         /  \
        15   7
```

Output:

```text
[
    [3],
    [9, 20],
    [15, 7]
]
```

The Day 10 sheet classifies this as:

```text
Tree BFS
Medium
```



---

# Pattern Recognition

The phrase:

```text
LEVEL ORDER
```

should immediately trigger:

```text
QUEUE + BFS
```

Why?

Because a queue naturally processes nodes in the order they are discovered.

---

# How BFS Works

Start with:

```text
3
```

Queue:

```text
[3]
```

Remove:

```text
3
```

Add children:

```text
[9, 20]
```

Remove `9`:

```text
[20]
```

Remove `20` and add its children:

```text
[15, 7]
```

Therefore:

```text
Level 1 → [3]
Level 2 → [9, 20]
Level 3 → [15, 7]
```

---

# The Important Trick — Process One Level at a Time

Suppose:

```text
queue = [9, 20, 15, 7]
```

If we simply process everything, we lose the information about which nodes belong to the same level.

So before processing a level:

```java
int size = queue.size();
```

That `size` tells us:

> How many nodes belong to the current level?

This is one of the most important BFS techniques.

---

# Approach 1 — Brute Force

A recursive DFS can calculate the level of each node and store nodes according to their depth.

For every node:

```text
DFS(node, level)
```

and add the node to:

```text
result[level]
```

This works.

But for a problem explicitly asking for **level order**, BFS is more natural.

---

# Approach 2 — DFS by Level

We can use recursion:

```text
DFS(root, 0)
```

Then:

```text
left  → level + 1
right → level + 1
```

This has:

```text
Time  → O(n)
Space → O(h)
```

for recursion, excluding the result.

But it doesn't directly use the queue pattern the problem is testing.

---

# Optimal Approach — BFS + Queue

Use:

```text
Queue<TreeNode>
```

Algorithm:

```text
1. If root == null → return empty
2. Add root to queue
3. While queue isn't empty:
      currentLevel = new list
      size = queue.size()

      repeat size times:
          node = queue.poll()
          add node.val to currentLevel

          add left child
          add right child

      add currentLevel to result
```

---

# Optimal Java Code

```java
import java.util.*;

class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);

        while (!queue.isEmpty()) {

            int size = queue.size();

            List<Integer> currentLevel = new ArrayList<>();

            for (int i = 0; i < size; i++) {

                TreeNode node = queue.poll();

                currentLevel.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(currentLevel);
        }

        return result;
    }
}
```

---

# Dry Run

Tree:

```text
        3
       / \
      9   20
         /  \
        15   7
```

Initial:

```text
queue = [3]
result = []
```

---

## Level 1

```text
size = 1
```

Poll:

```text
3
```

Add children:

```text
queue = [9, 20]
```

Current level:

```text
[3]
```

Result:

```text
[
    [3]
]
```

---

## Level 2

```text
size = 2
```

Process `9`:

```text
queue = [20]
```

Process `20`:

```text
queue = [15, 7]
```

Current level:

```text
[9, 20]
```

Result:

```text
[
    [3],
    [9, 20]
]
```

---

## Level 3

```text
size = 2
```

Process:

```text
15
7
```

Current level:

```text
[15, 7]
```

Final:

```text
[
    [3],
    [9, 20],
    [15, 7]
]
```

---

# Interview Explanation

> "Since the problem asks for level-order traversal, I use BFS with a queue. I add the root to the queue, then process nodes level by level. Before processing each level, I store the current queue size, which tells me exactly how many nodes belong to that level. While processing them, I add their children to the queue for the next level."

---

# Complexity

Let `n` be the number of nodes.

```text
Time  → O(n)
Space → O(n)
```

Every node enters and leaves the queue once.

The queue can contain O(n) nodes in a wide tree.

---

# Common Mistakes

## Mistake 1 — Forgetting `size`

Wrong:

```java
while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    ...
}
```

This processes everything but doesn't separate levels.

Correct:

```java
int size = queue.size();

for (int i = 0; i < size; i++) {
    ...
}
```

---

## Mistake 2 — Adding children before processing the level

The key is:

```text
Current queue
    ↓
capture size
    ↓
process exactly size nodes
    ↓
children become next level
```

---

## Mistake 3 — Using DFS automatically

DFS can solve it, but when you see:

```text
level order
```

your first thought should be:

```text
BFS + Queue
```

---

# BFS Template

Memorize this template.

```java
Queue<Node> queue = new LinkedList<>();

queue.offer(start);

while (!queue.isEmpty()) {

    int size = queue.size();

    for (int i = 0; i < size; i++) {

        Node current = queue.poll();

        // process current

        // add neighbors / children
    }
}
```

This template will appear repeatedly in:

```text
Binary Tree Level Order
Rotting Oranges
Walls and Gates
Open the Lock
Course Schedule
Perfect Squares
Time Needed to Inform All Employees
```

The Day 10 sheet contains all of these BFS-related problems.   

---

# Problem 2 — Implement Queue using Stacks

## Problem

Implement a **Queue** using only **Stacks**.

You need to support:

```text
push(x)
pop()
peek()
empty()
```

The Day 10 sheet classifies this as a:

```text
Design
Easy
```

problem. 

---

# The Challenge

Stack:

```text
LIFO
Last In First Out
```

Queue:

```text
FIFO
First In First Out
```

We need to transform:

```text
STACK → QUEUE
```

---

# Example

Queue should behave like:

```text
push(1)
push(2)
push(3)

pop() → 1
```

But a stack gives:

```text
push(1)
push(2)
push(3)

pop() → 3
```

Wrong.

So we need a mechanism to reverse the order.

---

# Pattern Recognition

This is the opposite of the Day 9 problem:

```text
Implement Stack using Queues
```

There:

```text
Queue → Stack
```

Today:

```text
Stack → Queue
```

The fundamental idea is:

> **Use two stacks to reverse the order.**

---

# Approach 1 — Two Stacks, Costly Push

Suppose:

```text
push(1)
push(2)
push(3)
```

We maintain the first stack in queue order.

For `push(3)`:

```text
stack1 = [1, 2]
```

Move everything:

```text
stack2 = [2, 1]
```

Push `3`:

```text
stack2 = [2, 1, 3]
```

Move back:

```text
stack1 = [3, 1, 2]
```

Depending on the stack orientation, this approach becomes unnecessarily complicated.

---

# Approach 2 — Two Stacks, Costly Pop

A much better idea:

```text
inputStack
outputStack
```

### Push

Always push into:

```text
inputStack
```

So:

```text
push(1)
push(2)
push(3)
```

gives:

```text
inputStack:

top
 ↓
3
2
1
```

---

# How Do We Get `1` First?

Move everything from `inputStack` to `outputStack`.

```text
inputStack:

3
2
1
```

After transfer:

```text
outputStack:

1
2
3
```

Now:

```text
outputStack.pop()
```

returns:

```text
1
```

Exactly what a queue needs.

---

# Important Optimization

We don't transfer elements every time.

Only transfer when:

```text
outputStack is empty
```

This is the key optimization.

---

# Why?

Suppose:

```text
push(1)
push(2)
push(3)
```

Transfer once:

```text
outputStack = [1, 2, 3]
```

Now we can do:

```text
pop() → 1
pop() → 2
pop() → 3
```

without transferring again.

---

# Optimal Approach

Maintain:

```text
inputStack
outputStack
```

### `push(x)`

```text
inputStack.push(x)
```

### `pop()`

If `outputStack` is empty:

```text
move everything from inputStack → outputStack
```

Then:

```text
outputStack.pop()
```

### `peek()`

Same transfer logic, then:

```text
outputStack.peek()
```

### `empty()`

```text
inputStack.isEmpty()
&&
outputStack.isEmpty()
```

---

# Optimal Java Code

```java
import java.util.Stack;

class MyQueue {

    private Stack<Integer> inputStack;
    private Stack<Integer> outputStack;

    public MyQueue() {
        inputStack = new Stack<>();
        outputStack = new Stack<>();
    }

    public void push(int x) {
        inputStack.push(x);
    }

    public int pop() {

        moveIfNeeded();

        return outputStack.pop();
    }

    public int peek() {

        moveIfNeeded();

        return outputStack.peek();
    }

    public boolean empty() {

        return inputStack.isEmpty()
                && outputStack.isEmpty();
    }

    private void moveIfNeeded() {

        if (outputStack.isEmpty()) {

            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
    }
}
```

---

# Dry Run

Operations:

```text
push(1)
push(2)
push(3)
peek()
pop()
pop()
push(4)
pop()
```

---

## `push(1)`

```text
input = [1]
output = []
```

---

## `push(2)`

```text
input = [1, 2]
output = []
```

---

## `push(3)`

```text
input = [1, 2, 3]
output = []
```

---

## `peek()`

Output stack is empty.

Transfer:

```text
input:
3
2
1
```

to:

```text
output:
1
2
3
```

Now:

```text
peek() → 1
```

---

## `pop()`

```text
output.pop()
```

returns:

```text
1
```

Remaining:

```text
output:
2
3
```

---

## `pop()`

Returns:

```text
2
```

Remaining:

```text
output:
3
```

---

## `push(4)`

Push into input:

```text
input:
4

output:
3
```

---

## `pop()`

Output is **not empty**, so don't transfer.

```text
output.pop() → 3
```

Correct queue behavior.

---

# Why Don't We Move `4` Immediately?

Because:

```text
output = [3]
input = [4]
```

The `3` entered the queue before `4`.

Therefore:

```text
3
```

must come out first.

Only after `output` becomes empty do we move:

```text
4
```

to the output stack.

This is the core idea.

---

# Interview Explanation

> "I use two stacks: an input stack for newly pushed elements and an output stack for elements ready to be removed. When the output stack is empty, I transfer all elements from the input stack to it, which reverses their order. This makes the oldest element available at the top of the output stack. I only perform the transfer when necessary."

---

# Complexity

### `push()`

```text
O(1)
```

### `pop()`

Usually:

```text
O(1)
```

But occasionally we transfer `n` elements:

```text
O(n)
```

### Amortized Complexity

Over many operations:

```text
push → O(1) amortized
pop  → O(1) amortized
peek → O(1) amortized
```

Why?

Because each element can be:

```text
inputStack → outputStack
```

only once.

### Space

```text
O(n)
```

---

# Common Mistakes

## Mistake 1 — Transferring every time

Don't do:

```text
input → output
```

for every `pop()`.

Only do it when:

```text
outputStack.isEmpty()
```

---

## Mistake 2 — Moving elements back after every pop

Once elements are in:

```text
outputStack
```

leave them there.

---

## Mistake 3 — Forgetting both stacks in `empty()`

Correct:

```java
return inputStack.isEmpty()
        && outputStack.isEmpty();
```

---

# Queue Design Pattern

Memorize:

```text
             QUEUE USING STACKS

              push(x)
                 ↓
           inputStack
                 ↓
       outputStack empty?
          ↙             ↘
        YES              NO
         ↓                ↓
 transfer input       do nothing
         ↓
   outputStack
         ↓
      pop/peek
```

---

# Queue Pattern — Part 1 Revision

We have now covered:

```text
1. Queue Fundamentals
2. Queue Types
3. Queue Pattern Recognition

4. Binary Tree Level Order Traversal
   → BFS + Queue

5. Implement Queue using Stacks
   → Two Stacks
```

---

# Day 10 Sheet Roadmap

The uploaded sheet contains these 15 problems:    

```text
⬜ 1. Binary Tree Level Order Traversal
      Pattern: Tree BFS
      Difficulty: Medium

⬜ 2. Implement Queue using Stacks
      Pattern: Design
      Difficulty: Easy

⬜ 3. Perfect Squares
      Pattern: BFS
      Difficulty: Medium

⬜ 4. Sliding Window Maximum
      Pattern: Monotonic Deque
      Difficulty: Hard

⬜ 5. Rotting Oranges
      Pattern: BFS Grid
      Difficulty: Medium

⬜ 6. Course Schedule
      Pattern: BFS / Topological Sort
      Difficulty: Medium

⬜ 7. Walls and Gates
      Pattern: BFS Grid
      Difficulty: Medium

⬜ 8. Number of Recent Calls
      Pattern: Queue Design
      Difficulty: Easy

⬜ 9. Dota2 Senate
      Pattern: Queue Simulation
      Difficulty: Medium

⬜ 10. Open the Lock
       Pattern: BFS
       Difficulty: Medium

⬜ 11. Design Circular Queue
       Pattern: Design
       Difficulty: Medium

⬜ 12. Moving Average from Data Stream
       Pattern: Queue + Sliding Window
       Difficulty: Easy

⬜ 13. Reveal Cards in Increasing Order
       Pattern: Simulation + Deque
       Difficulty: Medium

⬜ 14. Find the Safest Path in a Grid
       Pattern: BFS + Priority Queue
       Difficulty: Medium

⬜ 15. Time Needed to Inform All Employees
       Pattern: BFS Tree
       Difficulty: Medium
```

---

# Queue Pattern Map

```text
                         QUEUE
                           │
          ┌────────────────┼────────────────┐
          │                │                │
         BFS             Design          Sliding
          │                │              Window
          │                │                │
    ┌─────┼─────┐      ┌───┴────┐      Monotonic
    │     │     │      │        │        Deque
  Tree   Grid  Graph  Circular  Queue
    │     │     │      Queue    using
    │     │     │               Stacks
    │     │     │
    │     │     └── Course Schedule
    │     │
    │     ├── Rotting Oranges
    │     └── Walls and Gates
    │
    └── Level Order
```

---

# One-Minute Queue Revision

```text
QUEUE
↓
FIFO
↓
offer() → add
poll()  → remove
peek()  → front
```

If you see:

```text
Level by level
        ↓
      BFS
        ↓
      Queue
```

If you see:

```text
Shortest path
Equal edge cost
        ↓
      BFS
        ↓
      Queue
```

If you see:

```text
Spread simultaneously
        ↓
      BFS
        ↓
      Queue
```

If you see:

```text
Sliding Window Maximum
        ↓
   Monotonic Deque
```

If you see:

```text
Queue using Stacks
        ↓
   Two Stacks
```

If you see:

```text
Circular Queue
        ↓
Circular array / pointers
```

If you see:

```text
Priority-based traversal
        ↓
PriorityQueue
```

---

# Golden Rule

> **If elements must be processed in the order they arrive, think Queue.**

And:

> **If the problem asks you to explore level by level, layer by layer, or find the minimum number of steps in an unweighted graph/grid, think BFS + Queue.**

---

# Next — Queue Pattern Part 2

```text
3. Perfect Squares
4. Sliding Window Maximum
5. Rotting Oranges
```

### Focus

```text
BFS
Monotonic Deque
Multi-Source BFS
Shortest Path
```

These three problems are where the basic Queue concept starts turning into **real interview patterns**.
