# Fast & Slow Pointers Pattern - Part 3

# Template 3: Middle Node Family

## Why This Family Is Important

This is one of the most frequently asked Linked List patterns because many problems secretly require:

```text
Find Middle
↓
Split List
↓
Process Halves
```

Common uses:

* Middle of Linked List
* Palindrome Linked List
* Reorder List
* Sort List (Merge Sort)
* Circular Linked List Middle

---

# Master Template

## Find Middle Node

```java
ListNode slow = head;
ListNode fast = head;

while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;
}

return slow;
```

---

# Problem 8: Middle of the Linked List

## LeetCode 876

---

# Problem Explanation

Given a linked list:

```text
1 → 2 → 3 → 4 → 5
```

Return:

```text
3
```

For even length:

```text
1 → 2 → 3 → 4 → 5 → 6
```

Return:

```text
4
```

(second middle)

---

# What Is The Interviewer Testing?

Can you find middle in:

```text
One Pass
```

without counting nodes?

---

# Pattern Recognition Clues

### Clue 1

Linked List

### Clue 2

Middle Node

### Clue 3

One Pass

Think:

```text
Fast & Slow Pointer
```

---

# Brute Force

### Step 1

Count nodes.

### Step 2

Traverse again.

Reach:

```java
count / 2
```

---

## Complexity

```text
Time  : O(n)
Space : O(1)
```

But requires 2 passes.

---

# Optimal Approach

Slow:

```text
1 step
```

Fast:

```text
2 steps
```

When fast reaches end:

```text
Slow reaches middle
```

---

# Dry Run

```text
1 → 2 → 3 → 4 → 5

slow = 1
fast = 1

Iteration 1

slow = 2
fast = 3

Iteration 2

slow = 3
fast = 5

stop

answer = 3
```

---

# What To Say In Interview

Instead of counting nodes first, I'll use a slow pointer moving one step and a fast pointer moving two steps.

When fast reaches the end, slow naturally lands at the middle.

---

# Optimal Java Code

```java
class Solution {

    public ListNode middleNode(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){

            slow = slow.next;
            fast = fast.next.next;
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

* Palindrome Linked List
* Reorder List
* Sort List

---

# Problem 9: Palindrome Linked List

## LeetCode 234

---

# Problem Explanation

Determine whether a linked list reads the same forward and backward.

Example:

```text
1 → 2 → 2 → 1
```

Answer:

```text
true
```

---

# What Is The Interviewer Testing?

Most candidates use:

```java
ArrayList
```

Interviewer wants:

```text
O(1) Space
```

solution.

---

# Pattern Recognition Clues

### Clue 1

Linked List

### Clue 2

Compare front and back

### Clue 3

O(1) Space

Think:

```text
Find Middle
+
Reverse Second Half
+
Compare
```

---

# Brute Force

Store all values in array.

Check palindrome.

---

## Complexity

```text
Time  : O(n)

Space : O(n)
```

---

# Optimal Approach

### Step 1

Find middle.

### Step 2

Reverse second half.

### Step 3

Compare both halves.

---

# Dry Run

```text
1 → 2 → 2 → 1

Middle:

2

Reverse second half:

1 → 2

Compare:

1 == 1

2 == 2

Palindrome
```

---

# What To Say In Interview

I can avoid extra memory by reversing the second half of the linked list and comparing both halves directly.

---

# Optimal Java Code

```java
class Solution {

    public boolean isPalindrome(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){

            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode prev = null;

        while(slow != null){

            ListNode next = slow.next;

            slow.next = prev;
            prev = slow;
            slow = next;
        }

        ListNode first = head;
        ListNode second = prev;

        while(second != null){

            if(first.val != second.val){
                return false;
            }

            first = first.next;
            second = second.next;
        }

        return true;
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

* Reverse Linked List
* Reorder List

---

# Problem 10: Reorder List

## LeetCode 143

---

# Problem Explanation

Convert:

```text
1 → 2 → 3 → 4 → 5
```

Into:

```text
1 → 5 → 2 → 4 → 3
```

---

# What Is The Interviewer Testing?

Can you combine multiple patterns?

```text
Find Middle
+
Reverse
+
Merge
```

---

# Pattern Recognition Clues

### Clue 1

Need reordering.

### Clue 2

Need last node repeatedly.

### Clue 3

O(1) Space.

---

# Brute Force

Store nodes in array.

Use two pointers.

---

## Complexity

```text
Time  : O(n)

Space : O(n)
```

---

# Optimal Approach

### Step 1

Find middle.

### Step 2

Reverse second half.

### Step 3

Merge alternately.

---

# Visual

```text
1 → 2 → 3 → 4 → 5

Middle

1 → 2 → 3

4 → 5

Reverse

5 → 4

Merge

1 → 5 → 2 → 4 → 3
```

---

# What To Say In Interview

The problem becomes easy after splitting the list into two halves.

Reverse the second half and merge nodes alternately.

---

# Optimal Java Code

```java
class Solution {

    public void reorderList(ListNode head) {

        if(head == null) return;

        ListNode slow = head;
        ListNode fast = head;

        while(fast.next != null &&
              fast.next.next != null){

            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode second = slow.next;
        slow.next = null;

        ListNode prev = null;

        while(second != null){

            ListNode next = second.next;

            second.next = prev;
            prev = second;
            second = next;
        }

        ListNode first = head;
        second = prev;

        while(second != null){

            ListNode temp1 = first.next;
            ListNode temp2 = second.next;

            first.next = second;
            second.next = temp1;

            first = temp1;
            second = temp2;
        }
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

# Problem 11: Sort List

## LeetCode 148

---

# Problem Explanation

Sort a linked list.

Example:

```text
4 → 2 → 1 → 3
```

Output:

```text
1 → 2 → 3 → 4
```

---

# What Is The Interviewer Testing?

Arrays use:

```java
Arrays.sort()
```

Linked Lists are different.

Interviewer wants:

```text
Merge Sort
```

---

# Pattern Recognition Clues

### Clue 1

Linked List

### Clue 2

Sorting

### Clue 3

Need O(n log n)

Think:

```text
Middle Node
+
Merge Sort
```

---

# Optimal Approach

### Step 1

Find middle.

### Step 2

Split list.

### Step 3

Recursively sort.

### Step 4

Merge.

---

# What To Say In Interview

Merge Sort works naturally on linked lists because splitting can be done using slow and fast pointers without extra memory.

---

# Optimal Java Code

```java
class Solution {

    public ListNode sortList(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while(fast != null &&
              fast.next != null){

            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode mid = slow.next;
        slow.next = null;

        ListNode left = sortList(head);
        ListNode right = sortList(mid);

        return merge(left, right);
    }

    private ListNode merge(
            ListNode a,
            ListNode b){

        ListNode dummy =
                new ListNode(0);

        ListNode curr = dummy;

        while(a != null &&
              b != null){

            if(a.val < b.val){

                curr.next = a;
                a = a.next;

            }else{

                curr.next = b;
                b = b.next;
            }

            curr = curr.next;
        }

        curr.next =
                a != null ? a : b;

        return dummy.next;
    }
}
```

---

# Complexity

```text
Time  : O(n log n)

Space : O(log n)
```

(recursion stack)

---

# Problem 12: Find Middle Of Circular Linked List

## GFG Variant

---

# Problem Explanation

Given:

```text
1 → 2 → 3 → 4 → 5
↑               ↓
← ← ← ← ← ← ← ←
```

Return middle node.

---

# What Makes It Different?

There is:

```text
No Null
```

so normal stopping condition fails.

---

# Pattern Recognition Clues

### Clue 1

Circular List

### Clue 2

Need Middle

Think:

```text
Modified Fast Slow
```

---

# Optimal Approach

Stop when:

```java
fast.next == head
```

or

```java
fast.next.next == head
```

---

# Optimal Java Code

```java
class Solution {

    Node findMiddle(Node head){

        if(head == null){
            return null;
        }

        Node slow = head;
        Node fast = head;

        while(fast.next != head &&
              fast.next.next != head){

            slow = slow.next;
            fast = fast.next.next;
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

# Revision Sheet

## Find Middle

```java
while(fast != null &&
      fast.next != null){

    slow = slow.next;
    fast = fast.next.next;
}
```

Used In:

* Middle Of Linked List
* Palindrome Linked List
* Reorder List
* Sort List

---

## Find Middle + Reverse

```text
Find Middle
↓
Reverse Second Half
↓
Compare/Merge
```

Used In:

* Palindrome Linked List
* Reorder List

---

## Find Middle + Split

```text
Find Middle
↓
Split
↓
Recursion
```

Used In:

* Sort List

---

# Golden Rule

Whenever interviewer asks:

```text
Middle Node

Reorder Linked List

Palindrome Linked List

Merge Sort Linked List
```

Think:

```text
Fast & Slow Pointer
to Find Middle First
```
