# Fast & Slow Pointers Pattern - Part 1

# Template 1: Cycle Detection Family

## Core Idea

Fast & Slow pointers are used when:

* A structure can form a cycle
* We need O(1) space
* Elements are connected sequentially
* We repeatedly transform values

### Master Template

```java
slow = start;
fast = start;

while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;

    if(slow == fast){
        // cycle found
    }
}
```

---

# Problem 1: Linked List Cycle

## LeetCode 141

---

# Problem Explanation

You are given the head of a linked list.

Determine whether the linked list contains a cycle.

A cycle means some node points back to a previously visited node.

Example:

```text
3 → 2 → 0 → -4
    ↑       ↓
    ← ← ← ←
```

Output:

```text
true
```

---

# What Is The Interviewer Testing?

Most candidates solve it using:

```java
HashSet<ListNode>
```

Interviewer wants to know:

> Can you detect a cycle without storing visited nodes?

---

# Pattern Recognition Clues

### Clue 1

Linked list traversal.

### Clue 2

Possible infinite loop.

### Clue 3

Need cycle detection.

### Clue 4

O(1) space preferred.

Think:

```text
Floyd's Cycle Detection
```

---

# Brute Force

## Idea

Store every node inside HashSet.

If node already exists:

```text
Cycle Found
```

---

## Code

```java
Set<ListNode> visited = new HashSet<>();

while(head != null){

    if(visited.contains(head)){
        return true;
    }

    visited.add(head);

    head = head.next;
}
```

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

# Optimal Approach

## Intuition

Imagine two runners.

Runner A:

```text
1 step
```

Runner B:

```text
2 steps
```

If they run on a circular track:

```text
Fast eventually catches slow
```

Always.

---

## Dry Run

```text
1 → 2 → 3 → 4
    ↑     ↓
    ← ← ←
```

Initial:

```text
slow = 1
fast = 1
```

Iteration 1:

```text
slow = 2
fast = 3
```

Iteration 2:

```text
slow = 3
fast = 2
```

Iteration 3:

```text
slow = 4
fast = 4
```

Meeting point.

Cycle exists.

---

# What To Say In Interview

A HashSet solution works but uses O(n) space.

Using Floyd's Algorithm, I move slow one step and fast two steps.

If a cycle exists, the fast pointer must eventually meet the slow pointer.

If fast reaches null, no cycle exists.

---

# Optimal Java Code

```java
class Solution {

    public boolean hasCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){

            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast){
                return true;
            }
        }

        return false;
    }
}
```

---

# Complexity

```text
Time  : O(n)
Space : O(1)
```

---

# Similar Problems

* Linked List Cycle II
* Find Duplicate Number
* Happy Number

---

# Problem 2: Happy Number

## LeetCode 202

---

# Problem Explanation

Given a number n.

Repeatedly:

1. Take each digit.
2. Square it.
3. Sum the squares.

If eventually you reach:

```text
1
```

Number is happy.

Otherwise it enters a cycle.

Example:

```text
19

1² + 9² = 82

8² + 2² = 68

6² + 8² = 100

1² + 0² + 0² = 1
```

Answer:

```text
true
```

---

# What Is The Interviewer Testing?

Can you recognize that:

```text
Number Transformation
```

is actually:

```text
Linked List Traversal
```

in disguise.

---

# Pattern Recognition Clues

### Clue 1

Repeated transformation.

### Clue 2

Value keeps changing.

### Clue 3

Can enter infinite loop.

### Clue 4

Need cycle detection.

---

# Brute Force

Use HashSet.

Store every generated number.

If repeated:

```text
Cycle found
```

---

## Complexity

```text
Time  : O(log n)
Space : O(log n)
```

---

# Optimal Approach

Treat:

```text
next(number)
```

as:

```text
next node
```

Apply Floyd Cycle Detection.

---

## Helper Function

```java
private int nextNumber(int n){

    int sum = 0;

    while(n > 0){

        int digit = n % 10;

        sum += digit * digit;

        n /= 10;
    }

    return sum;
}
```

---

# What To Say In Interview

Instead of storing all previously seen values, I can treat every transformation as movement to the next node.

If a cycle exists, Floyd's algorithm will detect it.

---

# Optimal Java Code

```java
class Solution {

    public boolean isHappy(int n) {

        int slow = n;
        int fast = n;

        do{

            slow = nextNumber(slow);

            fast = nextNumber(
                    nextNumber(fast)
            );

        }while(slow != fast);

        return slow == 1;
    }

    private int nextNumber(int n){

        int sum = 0;

        while(n > 0){

            int digit = n % 10;

            sum += digit * digit;

            n /= 10;
        }

        return sum;
    }
}
```

---

# Complexity

```text
Time  : O(log n)

Space : O(1)
```

---

# Similar Problems

* Linked List Cycle
* Find Duplicate Number

---

# Problem 3: Find The Duplicate Number

## LeetCode 287

---

# Problem Explanation

Array contains:

```text
n + 1 numbers
```

Range:

```text
1 to n
```

Exactly one number appears twice.

Find duplicate without modifying array.

Example:

```text
[1,3,4,2,2]
```

Output:

```text
2
```

---

# What Is The Interviewer Testing?

Can you convert:

```text
Array
```

into:

```text
Linked List
```

mentally?

---

# Key Observation

Treat:

```java
nums[i]
```

as:

```java
next pointer
```

Example:

```text
index : 0 1 2 3 4

value : 1 3 4 2 2
```

Creates:

```text
0 → 1 → 3 → 2 → 4
            ↑   ↓
            ← ← ←
```

A cycle appears.

Duplicate value becomes cycle entry.

---

# Brute Force

Sort array.

Check adjacent values.

---

## Complexity

```text
Time  : O(n log n)
Space : O(1)
```

---

# Better

HashSet

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

# Optimal Approach

Apply Floyd Cycle Detection.

Then find cycle start.

---

# What To Say In Interview

Because numbers are restricted between 1 and n, each value can be treated as a pointer to another index.

The duplicate number creates a cycle.

Finding the cycle entry gives the duplicate.

---

# Optimal Java Code

```java
class Solution {

    public int findDuplicate(int[] nums) {

        int slow = nums[0];
        int fast = nums[0];

        do{

            slow = nums[slow];

            fast = nums[
                    nums[fast]
            ];

        }while(slow != fast);

        slow = nums[0];

        while(slow != fast){

            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(1)
```

---

# Similar Problems

* Linked List Cycle II
* Happy Number

---

# Problem 4: Circular Array Loop

## LeetCode 457

---

# Problem Explanation

Each element tells how many positions to move.

Positive:

```text
Move Forward
```

Negative:

```text
Move Backward
```

Need to determine whether a valid cycle exists.

Example:

```text
[2,-1,1,2,2]
```

Output:

```text
true
```

---

# What Makes This Hard?

Not every cycle is valid.

Valid cycle rules:

### Rule 1

Must contain more than one element.

Invalid:

```text
3 -> 3
```

---

### Rule 2

Direction must stay same.

Invalid:

```text
Forward
Backward
Forward
```

---

# Pattern Recognition Clues

### Clue 1

Repeated movement.

### Clue 2

Can revisit index.

### Clue 3

Need cycle detection.

### Clue 4

O(1) space.

---

# Brute Force

Start DFS from every index.

Track visited path.

---

## Complexity

```text
Time  : O(n²)
Space : O(n)
```

---

# Optimal Approach

Apply Floyd Cycle Detection.

But ensure:

```text
Direction remains same.
```

And:

```text
Cycle length > 1
```

---

# Helper Function

```java
private int nextIndex(
        int[] nums,
        boolean forward,
        int current){
}
```

Used to validate movement.

---

# What To Say In Interview

This problem resembles linked list cycle detection.

Each index points to another index.

However, I must additionally validate direction consistency and ensure the cycle contains more than one element.

---

# Optimal Java Code

```java
class Solution {

    public boolean circularArrayLoop(int[] nums) {

        int n = nums.length;

        for(int i = 0; i < n; i++){

            boolean forward =
                    nums[i] > 0;

            int slow = i;
            int fast = i;

            while(true){

                slow =
                        nextIndex(
                                nums,
                                forward,
                                slow
                        );

                if(slow == -1){
                    break;
                }

                fast =
                        nextIndex(
                                nums,
                                forward,
                                fast
                        );

                if(fast == -1){
                    break;
                }

                fast =
                        nextIndex(
                                nums,
                                forward,
                                fast
                        );

                if(fast == -1){
                    break;
                }

                if(slow == fast){
                    return true;
                }
            }
        }

        return false;
    }

    private int nextIndex(
            int[] nums,
            boolean forward,
            int current){

        boolean direction =
                nums[current] > 0;

        if(direction != forward){
            return -1;
        }

        int n = nums.length;

        int next =
                ((current + nums[current])
                        % n + n) % n;

        if(next == current){
            return -1;
        }

        return next;
    }
}
```

---

# Complexity

```text
Time  : O(n²)

Space : O(1)
```

---

# Similar Problems

* Linked List Cycle
* Detect Cycle in Circular Linked List
* Find Duplicate Number

---

# Part 1 Revision Sheet

## Pure Cycle Detection

1. Linked List Cycle
2. Happy Number
3. Circular Array Loop

---

## Cycle Detection + Entry Point

1. Find Duplicate Number

---

# Golden Rule

Whenever you see:

```text
Repeated Transformation

Repeated Traversal

Possible Infinite Loop

Need O(1) Space
```

Immediately think:

```text
Fast & Slow Pointer
(Floyd Cycle Detection)
```
