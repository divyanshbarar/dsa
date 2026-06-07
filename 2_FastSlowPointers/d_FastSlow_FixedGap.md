# Fast & Slow Pointers Pattern - Part 4

# Template 4: Fixed Gap Pointer Family

## Core Idea

Instead of moving pointers at different speeds:

```text
Slow = 1 step
Fast = 2 steps
```

We maintain:

```text
Fixed Distance Between Pointers
```

Example:

```java
fast = head;

for(int i = 0; i < k; i++){
    fast = fast.next;
}
```

Now:

```text
distance(slow, fast) = k
```

Move both together.

The gap never changes.

---

# Why Is This Useful?

It helps answer questions like:

```text
Nth Node From End

Node Before Target

Linked List Intersection

Replace Middle Segment
```

without calculating length.

---

# Master Template

```java
ListNode slow = head;
ListNode fast = head;

for(int i = 0; i < k; i++){
    fast = fast.next;
}

while(fast != null){

    slow = slow.next;
    fast = fast.next;
}
```

---

# Problem 13: Remove Nth Node From End

## LeetCode 19

---

# Problem Explanation

Given:

```text
1 → 2 → 3 → 4 → 5
```

n = 2

Remove:

```text
4
```

Output:

```text
1 → 2 → 3 → 5
```

---

# What Is The Interviewer Testing?

Most candidates:

```text
Count Length
↓
Find Target
↓
Delete
```

2 passes.

Interviewer wants:

```text
One Pass
```

---

# Pattern Recognition Clues

### Clue 1

Nth from end.

### Clue 2

Linked list.

### Clue 3

One pass preferred.

Think:

```text
Fixed Gap
```

---

# Brute Force

### Step 1

Count nodes.

### Step 2

Go to:

```java
length - n
```

### Step 3

Delete.

---

## Complexity

```text
Time  : O(n)

Space : O(1)
```

Two passes.

---

# Optimal Approach

Keep:

```text
fast ahead by n nodes
```

When fast reaches end:

```text
slow reaches node before deletion
```

---

# Dry Run

```text
1 → 2 → 3 → 4 → 5

n = 2
```

Move fast:

```text
fast = 3
slow = 1
```

Move together:

```text
slow = 2 fast = 4

slow = 3 fast = 5

slow = 4 fast = null
```

Delete:

```text
slow.next
```

---

# What To Say In Interview

I'll maintain a gap of n nodes between two pointers.

When the leading pointer reaches the end, the trailing pointer naturally reaches the node just before the target.

---

# Optimal Java Code

```java
class Solution {

    public ListNode removeNthFromEnd(
            ListNode head,
            int n
    ) {

        ListNode dummy =
                new ListNode(0);

        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy;

        for(int i = 0; i <= n; i++){
            fast = fast.next;
        }

        while(fast != null){

            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
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

* Kth Node From End
* Delete Middle Node
* Merge In Between Lists

---

# Problem 14: Intersection Of Two Linked Lists

## LeetCode 160

---

# Problem Explanation

Given:

```text
A:
1 → 2 → 3
        \
         7 → 8 → 9

B:
4 → 5
     \
      7 → 8 → 9
```

Return:

```text
Node 7
```

---

# What Is The Interviewer Testing?

Most people use:

```java
HashSet
```

Interviewer wants:

```text
O(1) Space
```

---

# Pattern Recognition Clues

### Clue 1

Two linked lists.

### Clue 2

Need common node.

### Clue 3

O(1) space.

---

# Brute Force

For every node in A:

Traverse B.

---

## Complexity

```text
Time  : O(m*n)

Space : O(1)
```

---

# Better

HashSet.

---

## Complexity

```text
Time  : O(m+n)

Space : O(n)
```

---

# Optimal Approach

### Trick

When A ends:

Jump to B.

When B ends:

Jump to A.

Eventually:

```text
Same Remaining Distance
```

---

# Visual

```text
A + B

B + A
```

Both travel:

```text
LengthA + LengthB
```

---

# What To Say In Interview

Instead of calculating lengths, I'll allow each pointer to traverse both lists.

After covering equal total distance, they must meet at the intersection.

---

# Optimal Java Code

```java
class Solution {

    public ListNode getIntersectionNode(
            ListNode headA,
            ListNode headB
    ) {

        ListNode a = headA;
        ListNode b = headB;

        while(a != b){

            a =
                (a == null)
                ? headB
                : a.next;

            b =
                (b == null)
                ? headA
                : b.next;
        }

        return a;
    }
}
```

---

# Complexity

```text
Time  : O(m+n)

Space : O(1)
```

---

# Similar Problems

* Detect Cycle
* Linked List Cycle II

---

# Problem 15: Merge In Between Linked Lists

## LeetCode 1669

---

# Problem Explanation

Given:

```text
list1

0 → 1 → 2 → 3 → 4 → 5
```

Remove:

```text
[a,b]
```

Suppose:

```text
a = 2
b = 4
```

Remove:

```text
2 → 3 → 4
```

Insert:

```text
list2
```

---

Example:

```text
list2

100 → 101 → 102
```

Result:

```text
0 → 1 → 100 → 101 → 102 → 5
```

---

# What Is The Interviewer Testing?

Pointer manipulation.

Can you reconnect linked list segments correctly?

---

# Pattern Recognition Clues

### Clue 1

Need node before position.

### Clue 2

Need node after position.

### Clue 3

Pointer rewiring.

---

# Brute Force

Store nodes in array.

Rebuild list.

---

## Complexity

```text
Time  : O(n)

Space : O(n)
```

---

# Optimal Approach

Find:

```text
prevA
```

and

```text
nextB
```

Connect:

```text
prevA → list2
```

Find tail of list2.

Connect:

```text
tail → nextB
```

---

# Dry Run

```text
0 → 1 → 2 → 3 → 4 → 5

a=2
b=4
```

Find:

```text
prevA = 1

nextB = 5
```

Attach:

```text
1 → list2
```

Attach tail:

```text
102 → 5
```

Done.

---

# What To Say In Interview

I only need the node before position a and the node after position b.

After locating both, I splice list2 into the gap.

---

# Optimal Java Code

```java
class Solution {

    public ListNode mergeInBetween(
            ListNode list1,
            int a,
            int b,
            ListNode list2
    ) {

        ListNode prevA = null;
        ListNode nextB = null;

        ListNode curr = list1;

        int index = 0;

        while(curr != null){

            if(index == a - 1){
                prevA = curr;
            }

            if(index == b + 1){
                nextB = curr;
                break;
            }

            curr = curr.next;
            index++;
        }

        prevA.next = list2;

        ListNode tail = list2;

        while(tail.next != null){
            tail = tail.next;
        }

        tail.next = nextB;

        return list1;
    }
}
```

---

# Complexity

```text
Time  : O(n+m)

Space : O(1)
```

---

# Fixed Gap Revision Sheet

## Gap Template

```java
ListNode slow = head;
ListNode fast = head;

for(int i = 0; i < k; i++){
    fast = fast.next;
}

while(fast != null){

    slow = slow.next;
    fast = fast.next;
}
```

Used In:

* Remove Nth Node From End
* Kth Node From End
* Delete Nth Node

---

## Equal Distance Trick

```java
while(a != b){

    a =
        (a == null)
        ? headB
        : a.next;

    b =
        (b == null)
        ? headA
        : b.next;
}
```

Used In:

* Intersection Of Linked Lists

---

## Pointer Rewiring

```text
Find Boundary

Cut

Attach

Reconnect
```

Used In:

* Merge In Between Lists
* Linked List Splicing

---

# Fast & Slow Pointer Pattern Complete

## Cycle Detection Family

* Linked List Cycle
* Happy Number
* Circular Array Loop
* Find Duplicate Number

---

## Cycle Entry Family

* Linked List Cycle II
* Detect Loop Length
* Circular Linked List Cycle

---

## Middle Node Family

* Middle Of Linked List
* Palindrome Linked List
* Reorder List
* Sort List
* Circular List Middle

---

## Fixed Gap Family

* Remove Nth Node From End
* Intersection Of Linked Lists
* Merge In Between Lists

---

# Final Interview Rule

When you see Linked List questions, ask:

```text
1. Is there a cycle?

2. Do I need the middle?

3. Do I need a fixed gap?

4. Do I need to reverse half?

5. Do I need pointer rewiring?
```

These five questions alone solve most Fast & Slow Pointer interview problems.
