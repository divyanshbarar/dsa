# Day 9 — Stack Pattern

# Stack Pattern — Part 5

> Final 3 problems of the Stack sheet: **Stack Implementation + Path Parsing + Expression Evaluation**.
> After this part, the complete Day 9 Stack sheet is finished.

---

# 13. Implement Stack using Queues

## Problem

Implement a **Stack (LIFO)** using only **Queue (FIFO)** operations.

You need to support:

```text
push(x)
pop()
top()
empty()
```

### Example

```text
push(1)
push(2)
push(3)

Stack:
3 ← top
2
1
```

So:

```text
pop() → 3
top() → 2
```

But a queue naturally behaves like:

```text
1 → 2 → 3
↑
front
```

The challenge is to make the **newest element appear at the front of the queue**.

---

# Pattern Recognition

Whenever you see:

```text
Implement Stack using Queue
```

remember:

```text
Queue  → FIFO
Stack  → LIFO
```

We need to somehow **reverse the queue order**.

The easiest trick:

> When pushing a new element, rotate the existing elements behind it.

### Example

Current queue:

```text
[1, 2]
```

Push `3`:

```text
[1, 2, 3]
```

Rotate the old elements:

```text
[2, 3, 1]
[3, 1, 2]
```

Now:

```text
front
 ↓
[3, 1, 2]
```

So `3` behaves exactly like the top of a stack.

---

# Approach 1 — Two Queues, Costly Pop

Maintain two queues.

For `push()`:

```text
q1.offer(x)
```

For `pop()`:

* Move all elements except the last one to another queue.
* Remove the last element.
* Swap queues.

### Complexity

```text
push → O(1)
pop  → O(n)
top  → O(n)
empty → O(1)
```

This works, but every `pop()` becomes expensive.

---

# Approach 2 — Two Queues, Costly Push

Instead, make `push()` expensive.

Suppose:

```text
q1 = [1, 2]
```

Push `3`.

Move everything to `q2`, put `3` first:

```text
q2 = [3]
```

Then move the old elements:

```text
q2 = [3, 1, 2]
```

Now `pop()` is simply:

```text
q2.poll()
```

### Complexity

```text
push  → O(n)
pop   → O(1)
top   → O(1)
empty → O(1)
```

This is much cleaner.

---

# Optimal Approach — One Queue

We don't even need two queues.

Use one queue.

When pushing:

1. Add the new element.
2. Rotate all previous elements to the back.

### Example

Before:

```text
[1, 2]
```

Push `3`:

```text
[1, 2, 3]
```

Queue size is `3`.

Rotate the first `2` elements:

```text
[2, 3, 1]
[3, 1, 2]
```

Now:

```text
front
 ↓
[3, 1, 2]
```

Therefore:

```text
pop() → 3
```

---

# Optimal Java Code

```java
import java.util.LinkedList;
import java.util.Queue;

class MyStack {

    private Queue<Integer> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(int x) {

        queue.offer(x);

        int size = queue.size();

        for (int i = 0; i < size - 1; i++) {
            queue.offer(queue.poll());
        }
    }

    public int pop() {
        return queue.poll();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
```

---

# Dry Run

Operations:

```text
push(10)
push(20)
push(30)
pop()
top()
```

### push(10)

```text
[10]
```

### push(20)

Initially:

```text
[10, 20]
```

Rotate `10`:

```text
[20, 10]
```

### push(30)

Initially:

```text
[20, 10, 30]
```

Rotate:

```text
[10, 30, 20]
[30, 20, 10]
```

Now:

```text
[30, 20, 10]
```

### pop()

```text
30
```

Queue:

```text
[20, 10]
```

### top()

```text
20
```

Exactly like a stack.

---

# Interview Explanation

> "A queue follows FIFO while a stack follows LIFO. I use one queue and make the newest element the front of the queue. After inserting an element, I rotate all previously existing elements to the back. Therefore, the front always represents the stack top, making pop and top O(1)."

---

# Complexity

| Operation | Complexity |
| --------- | ---------: |
| `push()`  |       O(n) |
| `pop()`   |       O(1) |
| `top()`   |       O(1) |
| `empty()` |       O(1) |
| Space     |       O(n) |

---

# Common Mistakes

### Mistake 1 — Forgetting why rotation is required

Simply doing:

```java
queue.offer(x);
```

doesn't create LIFO behavior.

---

### Mistake 2 — Rotating `size` times

After insertion, if size is `n`, rotate only:

```text
n - 1
```

elements.

Otherwise you'll rotate the newly inserted element as well.

---

### Mistake 3 — Confusing queue front with stack top

Our design guarantees:

```text
queue.front == stack.top
```

---

# Pattern Template

```text
Queue = FIFO
Stack = LIFO

To simulate stack using queue:

1. Insert new element
2. Rotate previous elements
3. New element becomes front
4. pop = poll
5. top = peek
```

---

# 14. Simplify Path

## Problem

Given an **absolute Unix-style file path**, simplify it.

Rules:

```text
/       → directory separator
.       → current directory
..      → parent directory
//      → same as /
```

The final path must:

* start with `/`
* contain single `/` separators
* have no unnecessary `.`
* resolve `..`
* have no trailing `/` unless it is the root

---

# Examples

### Example 1

```text
Input:
"/home/"

Output:
"/home"
```

---

### Example 2

```text
Input:
"/home//foo/"

Output:
"/home/foo"
```

---

### Example 3

```text
Input:
"/home/user/../documents"

Output:
"/home/documents"
```

Because:

```text
/home
   ↓
user
   ↓
..
```

`..` removes `user`.

---

### Example 4

```text
Input:
"/../"

Output:
"/"
```

We cannot go above root.

---

### Example 5

```text
Input:
"/a/./b/../../c/"

Output:
"/c"
```

---

# Pattern Recognition

This problem screams:

```text
STACK
```

Why?

Because:

```text
normal directory → push
..              → pop
.               → ignore
```

This is exactly stack behavior.

Think:

```text
Current Path

/a/b/c
      ↑
     top
```

If we encounter:

```text
..
```

we remove:

```text
c
```

---

# Approach 1 — Brute Force

Try to repeatedly modify the string:

```text
//
/./
/../
```

and continue until the path becomes valid.

The problem is that repeated string modifications can become expensive and complicated.

### Problems

* lots of string manipulation
* difficult edge cases
* potentially O(n²)
* hard to explain in an interview

---

# Approach 2 — Split + List

Split the path:

```text
"/a/b/../c"
```

into:

```text
["", "a", "b", "..", "c"]
```

Then process each component.

Use a list as a stack:

```text
a → push
b → push
.. → pop
c → push
```

Result:

```text
[a, c]
```

Output:

```text
/a/c
```

This is already O(n).

---

# Optimal Approach

Use a `Deque` as a stack.

For every component:

### Case 1 — Empty

```text
""
```

Ignore it.

This handles:

```text
//
```

---

### Case 2 — `.`

Ignore it.

```text
.
```

means current directory.

---

### Case 3 — `..`

Remove the last directory.

```text
stack.removeLast()
```

But only if the stack isn't empty.

---

### Case 4 — Normal directory

Add it to the stack.

---

# Optimal Java Code

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {

    public String simplifyPath(String path) {

        Deque<String> stack = new ArrayDeque<>();

        String[] parts = path.split("/");

        for (String part : parts) {

            // Ignore empty parts and "."
            if (part.isEmpty() || part.equals(".")) {
                continue;
            }

            // Go to parent directory
            if (part.equals("..")) {

                if (!stack.isEmpty()) {
                    stack.removeLast();
                }

            } else {

                // Normal directory
                stack.addLast(part);
            }
        }

        StringBuilder result = new StringBuilder();

        for (String directory : stack) {
            result.append("/").append(directory);
        }

        return result.length() == 0
                ? "/"
                : result.toString();
    }
}
```

---

# Dry Run

Input:

```text
/a/./b/../../c/
```

Split:

```text
["", "a", ".", "b", "..", "..", "c", ""]
```

Process:

### `""`

Ignore.

```text
[]
```

### `"a"`

Push:

```text
[a]
```

### `"."`

Ignore:

```text
[a]
```

### `"b"`

Push:

```text
[a, b]
```

### `".."`

Pop:

```text
[a]
```

### `".."`

Pop:

```text
[]
```

### `"c"`

Push:

```text
[c]
```

Final:

```text
/c
```

---

# Interview Explanation

> "I treat the directory path as a stack. Normal directory names are pushed, `.` is ignored, and `..` pops the previous directory if one exists. Empty components are ignored because they come from repeated slashes. Finally, I construct the canonical path from the stack."

---

# Complexity

Let `n` be the length of the path.

```text
Time  → O(n)
Space → O(n)
```

Every path component is processed essentially once.

---

# Common Mistakes

### Mistake 1 — Popping when stack is empty

For:

```text
/../
```

we cannot go above root.

So:

```java
if (!stack.isEmpty()) {
    stack.removeLast();
}
```

---

### Mistake 2 — Treating `...` as `..`

Only:

```text
..
```

means parent directory.

Something like:

```text
...
```

is a normal directory name.

---

### Mistake 3 — Forgetting repeated `/`

```text
/a//b///c
```

should become:

```text
/a/b/c
```

Empty components should be ignored.

---

### Mistake 4 — Leaving trailing slash

```text
/a/b/
```

must become:

```text
/a/b
```

---

# Pattern Template

```text
for every token:

    if token == "" or token == ".":
        ignore

    else if token == "..":
        if stack not empty:
            pop

    else:
        push(token)

build answer from stack
```

---

# 15. Basic Calculator II

## Problem

Given a string containing:

```text
+
-
*
/
```

and non-negative integers, calculate the result.

There can also be spaces.

### Example

```text
"3+2*2"
```

Answer:

```text
7
```

Because multiplication has higher precedence:

```text
3 + (2 * 2)
= 7
```

---

# More Examples

```text
"3+2*2" → 7
```

```text
" 3/2 " → 1
```

```text
" 3+5 / 2 " → 5
```

---

# The Main Challenge

Normal left-to-right evaluation would give:

```text
3 + 2 * 2

3 + 2 = 5
5 * 2 = 10 ❌
```

But multiplication must happen first:

```text
2 * 2 = 4
3 + 4 = 7
```

So we need to handle **operator precedence**.

---

# Pattern Recognition

When you see:

```text
Expression
+
-
*
/
Operator precedence
```

think:

```text
STACK
```

The important rule:

```text
+ -
```

have lower priority.

```text
* /
```

have higher priority.

---

# Approach 1 — Brute Force

Repeatedly find:

```text
*
/
```

and evaluate them first.

Then evaluate:

```text
+
-
```

Example:

```text
3 + 2 * 2 - 4 / 2
```

First resolve:

```text
2 * 2 = 4
4 / 2 = 2
```

Then:

```text
3 + 4 - 2
```

This requires repeated string manipulation and can become inefficient.

---

# Approach 2 — Two Stacks

Use:

```text
numbers stack
operators stack
```

This is the traditional infix-expression evaluation approach.

For every operator:

* compare precedence
* evaluate higher-precedence operations first
* push the current operator

This works well, but for **Calculator II**, it is more machinery than we need because there are no parentheses.

---

# Optimal Approach

Use **one number stack**.

The key idea:

> Immediately calculate `*` and `/`, but delay `+` and `-`.

Suppose:

```text
3 + 2 * 2
```

Process it.

### `3`

Previous sign is:

```text
+
```

So push:

```text
[3]
```

---

### `2`

Previous sign is:

```text
+
```

Push:

```text
[3, 2]
```

---

### `2`

Previous sign is:

```text
*
```

So:

```text
2 * 2 = 4
```

Replace the previous `2`:

```text
[3, 4]
```

Finally sum:

```text
3 + 4 = 7
```

---

# Why This Works

For:

```text
3 + 2 * 2 - 4 / 2
```

we can represent it as:

```text
3
+2
+4
-4
```

and then immediately resolve multiplication/division:

```text
3
+ (2 * 2)
- (4 / 2)
```

which becomes:

```text
3
+4
-2
```

Then simply sum:

```text
3 + 4 - 2 = 5
```

This is the central trick.

---

# How the Algorithm Works

Maintain:

```text
stack
num
sign
```

### `num`

Build multi-digit numbers.

For:

```text
123
```

we do:

```text
num = 1
num = 12
num = 123
```

---

### `sign`

Stores the **previous operator**.

Initially:

```text
sign = '+'
```

When we reach the next operator, apply the previous sign to the current number.

---

### Previous sign = `+`

```java
stack.push(num);
```

---

### Previous sign = `-`

```java
stack.push(-num);
```

---

### Previous sign = `*`

```java
stack.push(stack.pop() * num);
```

---

### Previous sign = `/`

```java
stack.push(stack.pop() / num);
```

Java integer division naturally truncates toward zero.

---

# Optimal Java Code

```java
import java.util.Stack;

class Solution {

    public int calculate(String s) {

        Stack<Integer> stack = new Stack<>();

        int num = 0;
        char sign = '+';

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            // Build the current number
            if (Character.isDigit(ch)) {

                num = num * 10 + (ch - '0');
            }

            // Process when we reach an operator
            // or the end of the string
            if ((!Character.isDigit(ch) && ch != ' ')
                    || i == s.length() - 1) {

                if (sign == '+') {

                    stack.push(num);

                } else if (sign == '-') {

                    stack.push(-num);

                } else if (sign == '*') {

                    stack.push(stack.pop() * num);

                } else if (sign == '/') {

                    stack.push(stack.pop() / num);
                }

                sign = ch;
                num = 0;
            }
        }

        int result = 0;

        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }
}
```

---

# Dry Run

Let's take:

```text
3+2*2
```

Initial:

```text
stack = []
num = 0
sign = '+'
```

---

## Read `3`

```text
num = 3
```

---

## Read `+`

Previous sign was:

```text
+
```

So:

```text
push(3)
```

Stack:

```text
[3]
```

Now:

```text
sign = '+'
num = 0
```

---

## Read `2`

```text
num = 2
```

---

## Read `*`

Previous sign:

```text
+
```

So:

```text
push(2)
```

Stack:

```text
[3, 2]
```

Now:

```text
sign = '*'
num = 0
```

---

## Read `2`

```text
num = 2
```

This is the final character, so process the previous sign.

Previous sign:

```text
*
```

Therefore:

```text
2 * 2
```

Pop:

```text
2
```

Calculate:

```text
2 * 2 = 4
```

Push:

```text
[3, 4]
```

---

## Final Sum

```text
3 + 4 = 7
```

Answer:

```text
7
```

---

# Another Dry Run

```text
3+2*2-4/2
```

The stack evolves approximately as:

```text
3
```

then:

```text
3, 2
```

then multiplication:

```text
3, 4
```

then subtraction:

```text
3, 4, -4
```

then division:

```text
3, 4, -2
```

Finally:

```text
3 + 4 - 2
= 5
```

---

# Interview Explanation

> "I use a stack to handle operator precedence. I build each complete number and keep the previous operator in `sign`. Addition pushes the number, subtraction pushes its negative, while multiplication and division immediately modify the previous stack value. At the end, summing the stack gives the final result."

---

# Why Do We Push Negative Numbers?

Suppose:

```text
5 - 3
```

Instead of remembering that subtraction must happen later, we store:

```text
5
-3
```

Then:

```text
5 + (-3)
= 2
```

This makes `+` and `-` very easy to handle.

---

# Why Do We Immediately Handle `*` and `/`?

Because they have higher precedence.

For:

```text
3 + 2 * 2
```

we don't want:

```text
3
2
2
```

to remain separate.

When we encounter `*`:

```text
2 * 2
```

becomes:

```text
4
```

So the stack becomes:

```text
[3, 4]
```

Then the final sum naturally respects precedence.

---

# Complexity

Let `n` be the length of the expression.

```text
Time  → O(n)
Space → O(n)
```

Every character is processed once.

---

# Common Mistakes

## Mistake 1 — Evaluating strictly left to right

Wrong:

```text
3 + 2 * 2
```

as:

```text
(3 + 2) * 2
= 10
```

Correct:

```text
3 + (2 * 2)
= 7
```

---

## Mistake 2 — Forgetting the last number

This is extremely common.

Example:

```text
3+2*2
```

There is no operator after the final `2`.

So we need:

```java
|| i == s.length() - 1
```

to process the final number.

---

## Mistake 3 — Not supporting multi-digit numbers

For:

```text
123
```

don't treat them as:

```text
1
2
3
```

Build:

```java
num = num * 10 + (ch - '0');
```

---

## Mistake 4 — Processing spaces as operators

For:

```text
3 + 2
```

the space should do nothing.

That's why we check:

```java
ch != ' '
```

---

## Mistake 5 — Mixing current operator and previous operator

This algorithm stores:

```text
sign = previous operator
```

When the current operator is encountered:

```text
apply sign to num
```

Then:

```text
sign = current operator
```

This distinction is very important.

---

# Pattern Template

```text
stack
num = 0
sign = '+'

for every character:

    if digit:
        build number

    if operator OR end:

        if sign == '+':
            push(num)

        if sign == '-':
            push(-num)

        if sign == '*':
            push(pop() * num)

        if sign == '/':
            push(pop() / num)

        sign = current character
        num = 0

sum stack
```

---

# Important Interview Insight

For:

```text
+ -
```

think:

```text
"Store the signed value."
```

For:

```text
* /
```

think:

```text
"Immediately modify the previous value."
```

That's the entire Calculator II trick.

---

# Day 9 — Stack Sheet COMPLETE

```text
✅ 1. Valid Parentheses
   Pattern: Matching / Nested Structures

✅ 2. Min Stack
   Pattern: Stack + Auxiliary State

✅ 3. Daily Temperatures
   Pattern: Monotonic Decreasing Stack

✅ 4. Next Greater Element I
   Pattern: Monotonic Stack + HashMap

✅ 5. Next Greater Element II
   Pattern: Circular Monotonic Stack

✅ 6. Decode String
   Pattern: Stack + Nested Parsing

✅ 7. Evaluate Reverse Polish Notation
   Pattern: Operand Stack

✅ 8. Remove All Adjacent Duplicates
   Pattern: StringBuilder as Stack

✅ 9. Remove K Digits
   Pattern: Greedy + Monotonic Stack

✅ 10. Largest Rectangle in Histogram
   Pattern: Monotonic Increasing Stack

✅ 11. Asteroid Collision
   Pattern: Stack Simulation

✅ 12. Backspace String Compare
   Pattern: Stack-based String Processing

✅ 13. Implement Stack using Queues
   Pattern: Data Structure Simulation

✅ 14. Simplify Path
   Pattern: Stack + Path Parsing

✅ 15. Basic Calculator II
   Pattern: Stack + Operator Precedence
```

---

# Day 9 — Stack Master Cheat Sheet

## 1. Matching / Nested

```text
Valid Parentheses
```

Think:

```text
Opening → push
Closing → pop
```

---

## 2. Previous State / Minimum

```text
Min Stack
```

Think:

```text
Main Stack
+
Auxiliary Minimum Stack
```

---

## 3. Next Greater

```text
Daily Temperatures
Next Greater Element I
Next Greater Element II
```

Think:

```text
Monotonic Decreasing Stack
```

---

## 4. Nested Parsing

```text
Decode String
```

Think:

```text
count stack
string stack
```

---

## 5. Expression Evaluation

```text
Reverse Polish Notation
Basic Calculator II
```

Think:

```text
Operand Stack
```

or:

```text
Number Stack + Operator Precedence
```

---

## 6. Removal / Undo

```text
Remove Adjacent Duplicates
Backspace String Compare
```

Think:

```text
StringBuilder as Stack
```

---

## 7. Greedy

```text
Remove K Digits
```

Think:

```text
Monotonic Increasing Stack
```

---

## 8. Histogram

```text
Largest Rectangle in Histogram
```

Think:

```text
Previous Smaller
Next Smaller
Monotonic Increasing Stack
```

---

## 9. Simulation

```text
Asteroid Collision
```

Think:

```text
while stack top can collide
```

---

## 10. Data Structure Design

```text
Implement Stack using Queues
```

Think:

```text
Change FIFO → LIFO
```

---

## 11. Path Parsing

```text
Simplify Path
```

Think:

```text
normal → push
..     → pop
.      → ignore
```

---

# Most Important Stack Patterns to Remember

```text
1. Matching brackets
        ↓
     Stack

2. Next greater
        ↓
   Monotonic Stack

3. Next smaller
        ↓
   Monotonic Stack

4. Previous state / undo
        ↓
     Stack

5. Nested parsing
        ↓
     Stack

6. Expression evaluation
        ↓
     Stack

7. Remove previous element
        ↓
     Stack

8. Greedy removal
        ↓
Monotonic Stack

9. Histogram
        ↓
Monotonic Stack

10. Simulation with collisions
        ↓
     Stack
```

---

# Golden Rule for Stack Problems

> **If the current element needs to interact with the most recent unresolved element, think STACK.**

And if the question says:

```text
next greater
next smaller
previous greater
previous smaller
```

immediately ask yourself:

```text
"Can I use a Monotonic Stack?"
```

---

# Extra Stack Problems for Practice

After the 15 sheet questions, these are excellent next problems:

```text
1. Online Stock Span
2. Next Smaller Element
3. Previous Greater Element
4. Trapping Rain Water
5. Largest Rectangle in Histogram
6. Remove Duplicate Letters
7. Basic Calculator
8. Basic Calculator III
9. Score of Parentheses
10. Maximum Frequency Stack
11. Stock Span Problem
12. Celebrity Problem
13. Sum of Subarray Minimums
14. Sum of Subarray Ranges
15. Maximum Width Ramp
```

The **most important ones for interviews** are:

```text
Daily Temperatures
Next Greater Element
Stock Span
Largest Rectangle in Histogram
Trapping Rain Water
Remove K Digits
Basic Calculator
Sum of Subarray Minimums
```

---

# Day 9 Final Revision

Before moving to the next DSA pattern, make sure you can answer these without looking at code:

```text
1. What is LIFO?

2. When should I use a stack?

3. What is a monotonic stack?

4. Increasing vs decreasing monotonic stack?

5. Why do we store indices instead of values sometimes?

6. How does Daily Temperatures work?

7. How does Next Greater Element work?

8. How do you handle a circular array?

9. Why does Remove K Digits use a monotonic stack?

10. How does Largest Rectangle use previous/next smaller?

11. Why does Asteroid Collision need a while loop?

12. How can StringBuilder act as a stack?

13. How can a queue simulate a stack?

14. Why does Simplify Path use a stack?

15. How does Basic Calculator II handle operator precedence?
```

> **Day 9 Stack = COMPLETE ✅**
