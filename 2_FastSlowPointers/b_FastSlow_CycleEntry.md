# Fast & Slow Pointers Pattern - Part 2

# Template 2: Cycle Entry Family

## Core Idea

In Part 1 we learned:

```java
slow == fast
```

means a cycle exists.

Now the question becomes:

```text
Where does the cycle start?
```

This leads to one of the most important interview tricks:

```java
slow = head;

while(slow != fast){
    slow = slow.next;
    fast = fast.next;
}
```

The meeting point becomes:

```text
Cycle Entry
```

---

# Mathematical Intuition

Suppose:

```text
L = distance from head to cycle start

C = cycle length

X = distance from cycle start to meeting point
```

When slow and fast meet:

```text
2 × slowDistance
=
fastDistance
```

After simplification:

```text
L = k*C - X
```

Meaning:

If one pointer starts from head and another starts from meeting point:

```text
Both reach cycle start together.
```

This proof powers:

1. Linked List Cycle II
2. Find Duplicate Number
3. Detect Cycle Start Variants

---

# Problem 5: Linked List Cycle II

## LeetCode 142

---

# Problem Explanation

Given a linked list.

Return:

```text
Node where cycle begins
```

If no cycle exists:

```text
return null
```

Example:

```text
3 → 2 → 0 → -4
    ↑       ↓
    ← ← ← ←
```

Answer:

```text
Node 2
```

---

# What Is The Interviewer Testing?

Part 1 asked:

```text
Does a cycle exist?
```

Now interviewer asks:

```text
Can you find exactly where it begins?
```

---

# Pattern Recognition Clues

### Clue 1

Linked List

### Clue 2

Cycle exists

### Clue 3

Need cycle entry

### Clue 4

O(1) space preferred

Think:

```text
Floyd + Reset Slow
```

---

# Brute Force

Store nodes inside HashSet.

First repeated node:

```text
Cycle Entry
```

---

## Complexity

```text
Time  : O(n)

Space : O(n)
```

---

# Optimal Approach

### Step 1

Detect cycle.

### Step 2

Reset:

```java
slow = head;
```

### Step 3

Move both one step.

Meeting point becomes:

```text
Cycle Start
```

---

# Dry Run

```text
1 → 2 → 3 → 4 → 5
        ↑       ↓
        ← ← ← ←
```

Meeting:

```text
slow = 5
fast = 5
```

Reset:

```text
slow = 1
fast = 5
```

Move both:

```text
2 , 3
3 , 4
4 , 5
3 , 3
```

Cycle start found.

---

# What To Say In Interview

After Floyd detects a cycle, a mathematical property guarantees that resetting one pointer to head and moving both one step at a time causes them to meet at the cycle entry.

---

# Optimal Java Code

```java
class Solution {

    public ListNode detectCycle(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){

            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast){

                slow = head;

                while(slow != fast){

                    slow = slow.next;
                    fast = fast.next;
                }

                return slow;
            }
        }

        return null;
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

* Find Duplicate Number
* Detect Loop Length
* Circular Loop Start

---

# Problem 6: Detect Loop Length In Linked List

## GFG

---

# Problem Explanation

Given a linked list with a cycle.

Return:

```text
Length of cycle
```

Example:

```text
1 → 2 → 3 → 4 → 5
        ↑       ↓
        ← ← ← ←
```

Cycle:

```text
3 → 4 → 5 → 3
```

Answer:

```text
3
```

---

# What Is The Interviewer Testing?

Many people stop after:

```java
slow == fast
```

Interviewer wants to know:

```text
Can you extract more information?
```

---

# Pattern Recognition Clues

### Clue 1

Cycle already exists.

### Clue 2

Need loop size.

### Clue 3

Floyd already available.

---

# Brute Force

Store nodes in HashMap.

Record index.

When repeated:

```text
currentIndex - firstIndex
```

---

## Complexity

```text
Time  : O(n)

Space : O(n)
```

---

# Optimal Approach

After meeting:

Move one pointer around cycle.

Count steps.

---

# Dry Run

```text
3 → 4 → 5 → 3
```

Start:

```text
count = 1
```

Move:

```text
4
count = 2

5
count = 3

3
stop
```

Answer:

```text
3
```

---

# What To Say In Interview

Once slow and fast meet, we know we are inside the cycle.

Traversing until we return to the same node gives the exact cycle length.

---

# Optimal Java Code

```java
class Solution {

    static int countNodesinLoop(Node head){

        Node slow = head;
        Node fast = head;

        while(fast != null && fast.next != null){

            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast){

                int count = 1;

                fast = fast.next;

                while(fast != slow){

                    count++;
                    fast = fast.next;
                }

                return count;
            }
        }

        return 0;
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

* Linked List Cycle
* Linked List Cycle II

---

# Problem 7: Detect Cycle In Circular Linked List

## GFG Variant

---

# Problem Explanation

A circular linked list is a linked list where:

```text
Last Node → Head
```

Example:

```text
1 → 2 → 3 → 4
↑           ↓
← ← ← ← ← ←
```

Need to determine whether a cycle exists.

---

# What Is Different?

Normal linked list:

```text
Can end at null
```

Circular linked list:

```text
Never reaches null
```

Need custom handling.

---

# Pattern Recognition Clues

### Clue 1

Circular structure.

### Clue 2

Infinite traversal possible.

### Clue 3

Need cycle detection.

Think:

```text
Floyd
```

---

# Brute Force

HashSet.

Store nodes.

Repeated node:

```text
Cycle Found
```

---

## Complexity

```text
Time  : O(n)

Space : O(n)
```

---

# Optimal Approach

Exactly same as Floyd.

The cycle exists by definition.

Need to verify traversal safely.

---

# What To Say In Interview

Since nodes form a circular structure, Floyd's algorithm naturally applies.

The fast pointer eventually catches the slow pointer.

---

# Optimal Java Code

```java
boolean hasCircularCycle(Node head){

    if(head == null){
        return false;
    }

    Node slow = head;
    Node fast = head;

    do{

        slow = slow.next;
        fast = fast.next.next;

    }while(slow != fast);

    return true;
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(1)
```

---

# Advanced Interview Follow-Up

## Why Resetting Works?

Most interviewers ask this.

Remember:

```text
Distance from Head
=
Distance from Meeting Point
to Cycle Start
```

(mod cycle length)

Therefore:

```java
slow = head;
```

and moving both one step guarantees meeting at cycle entry.

---

# Revision Sheet

## Cycle Detection

```java
while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;

    if(slow == fast){
        cycle found
    }
}
```

Used In:

* Linked List Cycle
* Happy Number
* Circular Array Loop
* Find Duplicate Number

---

## Cycle Entry

```java
slow = head;

while(slow != fast){

    slow = slow.next;
    fast = fast.next;
}
```

Used In:

* Linked List Cycle II
* Find Duplicate Number

---

## Cycle Length

```java
count = 1;

fast = fast.next;

while(fast != slow){

    count++;
    fast = fast.next;
}
```

Used In:

* Detect Loop Length
* Cycle Analysis Problems

---

# Golden Rule

Whenever interviewer asks:

```text
Where does the cycle start?

How long is the cycle?

Can you find duplicate without extra memory?
```

Think:

```text
Floyd's Cycle Detection
+
Cycle Entry Mathematics
```
