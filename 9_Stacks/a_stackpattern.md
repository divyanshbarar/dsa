# Day 9 — Stack Pattern

# Stack Pattern — Part 1

> **Goal:** Learn how to recognize Stack problems, understand the LIFO pattern, and master the first two problems from the sheet using the same **Brute → Better → Optimal → Java Code → Dry Run → Interview Explanation → Complexity → Mistakes** format.

The Day-9 Stack sheet contains **15 problems**, covering classic stacks, monotonic stacks, expression evaluation, string parsing, greedy + stack, and stack simulation.   

---

# 1. What is a Stack?

A **Stack** is a linear data structure that follows:

```text
LIFO
Last In → First Out
```

Think about a stack of plates.

```text
        ┌───────┐
        │   5   │ ← TOP
        ├───────┤
        │   4   │
        ├───────┤
        │   3   │
        ├───────┤
        │   2   │
        ├───────┤
        │   1   │
        └───────┘
```

If we remove one plate:

```text
5
```

comes out first.

Then:

```text
4
```

Then:

```text
3
```

So:

```text
Last Added
    ↓
   5
   4
   3
   2
   1
    ↓
First Removed
```

---

# 2. Basic Stack Operations

The four operations you must know:

```text
push()
pop()
peek()
isEmpty()
```

### `push()`

Adds an element.

```java
stack.push(10);
```

---

### `pop()`

Removes the top element.

```java
stack.pop();
```

---

### `peek()`

Returns the top element without removing it.

```java
stack.peek();
```

---

### `isEmpty()`

Checks whether the stack is empty.

```java
stack.isEmpty();
```

---

# 3. Java Stack

For interview problems, you will commonly see:

```java
Stack<Integer> stack = new Stack<>();
```

Example:

```java
Stack<Integer> stack = new Stack<>();

stack.push(10);
stack.push(20);
stack.push(30);
```

Stack:

```text
30 ← TOP
20
10
```

Now:

```java
stack.pop();
```

removes:

```text
30
```

Now:

```text
20 ← TOP
10
```

---

# 4. Stack Complexity

| Operation   | Complexity |
| ----------- | ---------: |
| `push()`    |       O(1) |
| `pop()`     |       O(1) |
| `peek()`    |       O(1) |
| `isEmpty()` |       O(1) |

This is one reason stacks are so useful.

---

# 5. When Should You Think About Stack?

This is the most important part for interviews.

Do **not** think:

> "The question says Stack, so I'll use Stack."

Instead, recognize the behavior.

---

## Clue 1 — Nested Structures

Examples:

```text
()
[]
{}
```

or:

```text
((()))
```

or:

```text
{[()]}
```

Think:

```text
Stack
```

Because the most recently opened structure must be closed first.

---

## Clue 2 — Previous State

If the problem says:

```text
undo
back
previous
reverse
remove previous
```

think:

```text
Stack
```

---

## Clue 3 — Next Greater / Smaller

If you see:

```text
Next Greater Element
Next Smaller Element
Previous Greater Element
Previous Smaller Element
```

think:

```text
Monotonic Stack
```

This becomes one of the most important Stack patterns.

---

## Clue 4 — Expression Evaluation

If you see:

```text
infix
postfix
prefix
operators
parentheses
```

think:

```text
Stack
```

---

## Clue 5 — Backspace / Removal

If characters are being removed based on what came immediately before them:

```text
Stack
```

is often a natural fit.

---

## Clue 6 — Need to Remember Previous Elements

Sometimes the problem requires:

```text
current element
+
some previous element
```

and the previous elements need to be processed in reverse order.

Think:

```text
Stack
```

---

# 6. Major Stack Patterns

The Day-9 sheet can be divided into these patterns.

```text
                    STACK
                      |
       ┌──────────────┼──────────────┐
       ↓              ↓              ↓
    Classic       Monotonic       Parsing
     Stack          Stack          / Math
       |              |              |
       ↓              ↓              ↓
 Parentheses     Next Greater     RPN
 Min Stack       Daily Temp.      Calculator
 Decode String   Histogram
       |
       ↓
   Simulation
       |
       ↓
 Asteroid Collision
 Backspace Compare
 Implement Stack
```

The sheet specifically includes problems such as Valid Parentheses, Min Stack, Daily Temperatures, Next Greater Element I/II, Decode String, Reverse Polish Notation, Remove K Digits, Largest Rectangle, Asteroid Collision, Backspace String Compare, Implement Stack using Queues, Simplify Path, and Basic Calculator II.   

---

# PART 1 — Classic Stack

We will start with the two classic Stack problems from the sheet:

```text
1. Valid Parentheses
2. Min Stack
```

These establish the foundation before moving into **Monotonic Stack**.

---

# Problem 1 — Valid Parentheses

## Problem

Given a string containing only:

```text
()
{}
[]
```

determine whether the brackets are valid.

A valid string must satisfy:

1. Every opening bracket has a corresponding closing bracket.
2. Brackets must close in the correct order.
3. Every closing bracket must match the most recent unmatched opening bracket.

---

## Example 1

```text
Input:
s = "()"

Output:
true
```

---

## Example 2

```text
Input:
s = "()[]{}"

Output:
true
```

---

## Example 3

```text
Input:
s = "(]"

Output:
false
```

---

## Example 4

```text
Input:
s = "([{}])"

Output:
true
```

---

# Pattern Recognition

This is one of the easiest Stack patterns to recognize.

Look for:

```text
opening bracket
+
closing bracket
+
matching
+
nested
```

Immediately think:

```text
STACK
```

Why?

Because:

```text
Last Opened
     ↓
First Closed
```

which is exactly:

```text
LIFO
```

---

# The Core Idea

Suppose:

```text
s = "([{}])"
```

Read from left to right.

First:

```text
(
```

Push it.

```text
Stack:
(
```

Next:

```text
[
```

Push.

```text
Stack:
[
(
```

Next:

```text
{
```

Push.

```text
Stack:
{
[
(
```

Now we see:

```text
}
```

The top of the stack is:

```text
{
```

Perfect match.

Pop.

Now:

```text
Stack:
[
(
```

Then:

```text
]
```

matches:

```text
[
```

Pop.

Then:

```text
)
```

matches:

```text
(
```

Pop.

Stack is empty.

Therefore:

```text
true
```

---

# Approach 1 — Brute Force

One possible brute-force idea is repeatedly remove valid pairs:

```text
()
[]
{}
```

For example:

```text
([{}])
```

Remove:

```text
{}
```

then:

```text
([])
```

then:

```text
()
```

then:

```text
""
```

This can work, but repeatedly modifying/searching the string is inefficient.

### Complexity

Depending on implementation:

```text
Time: O(n²)
Space: O(n)
```

Not ideal.

---

# Approach 2 — Stack of Characters

Use a stack.

For every character:

### If it is an opening bracket:

```text
(
[
{
```

push it.

### If it is a closing bracket:

```text
)
]
}
```

check:

```text
Is stack empty?
```

If yes:

```text
false
```

Otherwise:

```text
Does top match current closing bracket?
```

If no:

```text
false
```

If yes:

```text
pop()
```

At the end:

```text
stack.isEmpty()
```

must be true.

---

# Optimal Java Code

```java
import java.util.Stack;

class Solution {

    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {

            // Opening brackets
            if (ch == '(' || ch == '[' || ch == '{') {

                stack.push(ch);

            } else {

                // Closing bracket without opening bracket
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if ((ch == ')' && top != '(') ||
                    (ch == ']' && top != '[') ||
                    (ch == '}' && top != '{')) {

                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
```

---

# Better Version — Push Expected Closing Bracket

There is an even cleaner way to think about this.

Instead of pushing:

```text
(
[
{
```

push what we **expect to see later**.

For:

```text
(
```

push:

```text
)
```

For:

```text
[
```

push:

```text
]
```

For:

```text
{
```

push:

```text
}
```

Then whenever we encounter a closing bracket, simply check:

```text
current == stack.peek()
```

This removes the multiple comparison conditions.

---

# Optimal Clean Java Code

```java
import java.util.Stack;

class Solution {

    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                stack.push(')');
            }
            else if (ch == '[') {
                stack.push(']');
            }
            else if (ch == '{') {
                stack.push('}');
            }
            else {

                if (stack.isEmpty() || stack.pop() != ch) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
```

### This is the version I would use in an interview.

---

# Dry Run

Consider:

```text
s = "{[()]}"
```

Start:

```text
Stack = []
```

### Character `{`

Push:

```text
Stack = [}]
```

### Character `[`

Push:

```text
Stack = [}, ]]
```

### Character `(`

Push:

```text
Stack = [}, ], )]
```

### Character `)`

Expected:

```text
)
```

Top:

```text
)
```

Match.

Pop:

```text
Stack = [}, ]]
```

### Character `]`

Top:

```text
]
```

Match.

Pop:

```text
Stack = [}]
```

### Character `}`

Top:

```text
}
```

Match.

Pop:

```text
Stack = []
```

End:

```text
stack.isEmpty() = true
```

Answer:

```text
true
```

---

# Invalid Example

```text
s = "([)]"
```

Process:

```text
(
[
)
```

Stack before `)`:

```text
[
(
```

Top:

```text
[
```

But current closing bracket is:

```text
)
```

Expected:

```text
]
```

Mismatch.

Therefore:

```text
false
```

---

# Interview Explanation

Say this:

> "This is a classic LIFO problem because the most recently opened bracket must be closed first. I'll use a stack to store the expected closing brackets. For every opening bracket, I push its corresponding closing bracket. For every closing bracket, I check whether it matches the top of the stack. At the end, the stack must be empty."

This is a **very strong interview explanation** because you explain *why* Stack is being used.

---

# Complexity

```text
Time: O(n)
Space: O(n)
```

Every character is:

```text
push → at most once
pop  → at most once
```

Therefore:

```text
O(n)
```

---

# Common Mistakes

## Mistake 1 — Only Checking Counts

This is wrong:

```text
"([)]"
```

It contains the same number of:

```text
(
)
[
]
```

but is still invalid.

Order matters.

---

## Mistake 2 — Forgetting Empty Stack

For:

```text
")"
```

there is no opening bracket.

So:

```java
stack.pop();
```

would be invalid.

Always check:

```java
stack.isEmpty()
```

---

## Mistake 3 — Forgetting Final Stack Check

Consider:

```text
"((("
```

Every opening bracket was pushed.

But nothing was closed.

Therefore:

```text
return stack.isEmpty();
```

is necessary.

---

# Pattern Template

For bracket matching:

```java
Stack<Character> stack = new Stack<>();

for (char ch : s.toCharArray()) {

    if (isOpening(ch)) {

        stack.push(expectedClosing(ch));

    } else {

        if (stack.isEmpty() || stack.pop() != ch) {
            return false;
        }
    }
}

return stack.isEmpty();
```

Memorize this pattern.

---

# Problem 2 — Min Stack

## Problem

Design a stack that supports:

```text
push()
pop()
top()
getMin()
```

and `getMin()` must return the minimum element in:

```text
O(1)
```

time.

---

## Example

Operations:

```text
push(-2)
push(0)
push(-3)
getMin()
pop()
top()
getMin()
```

Expected:

```text
getMin() → -3
pop()    → -3
top()    → 0
getMin() → -2
```

---

# Why Is This Problem Interesting?

A normal Stack already gives:

```text
push() → O(1)
pop()  → O(1)
top()  → O(1)
```

But:

```text
getMin()
```

is the challenge.

If we scan the entire stack:

```text
O(n)
```

But the problem wants:

```text
O(1)
```

So we need additional information.

---

# Pattern Recognition

Look for:

```text
Stack
+
extra information
+
O(1) query
```

Think:

```text
Custom Stack
```

We need to remember:

```text
What is the minimum value at every level of the stack?
```

---

# Approach 1 — Brute Force

Use a normal Stack.

For `getMin()`:

```text
traverse entire stack
```

and find the minimum.

Example:

```text
[-2, 0, -3]
```

Traverse:

```text
-2
0
-3
```

Minimum:

```text
-3
```

### Complexity

```text
push → O(1)
pop  → O(1)
top  → O(1)
getMin → O(n)
```

This violates the required O(1) `getMin()`.

---

# Approach 2 — Two Stacks

Use:

```text
Stack 1 → actual values
Stack 2 → minimum values
```

Suppose we push:

```text
5
```

Then:

```text
Values:
[5]

Min:
[5]
```

Push:

```text
3
```

Now:

```text
Values:
[5, 3]

Min:
[5, 3]
```

Push:

```text
7
```

Minimum is still:

```text
3
```

So:

```text
Values:
[5, 3, 7]

Min:
[5, 3, 3]
```

Now `getMin()` is simply:

```java
minStack.peek();
```

which is:

```text
O(1)
```

---

# Optimal Java Code

```java
import java.util.Stack;

class MinStack {

    private Stack<Integer> stack;
    private Stack<Integer> minStack;

    public MinStack() {

        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {

        stack.push(val);

        if (minStack.isEmpty()) {

            minStack.push(val);

        } else {

            minStack.push(
                Math.min(val, minStack.peek())
            );
        }
    }

    public void pop() {

        stack.pop();
        minStack.pop();
    }

    public int top() {

        return stack.peek();
    }

    public int getMin() {

        return minStack.peek();
    }
}
```

---

# Dry Run

Operations:

```text
push(5)
push(3)
push(7)
push(2)
```

---

## After `push(5)`

Normal stack:

```text
5
```

Min stack:

```text
5
```

Minimum:

```text
5
```

---

## After `push(3)`

Normal:

```text
3 ← TOP
5
```

Min:

```text
3 ← TOP
5
```

Minimum:

```text
3
```

---

## After `push(7)`

Normal:

```text
7 ← TOP
3
5
```

Min:

```text
3 ← TOP
3
5
```

Minimum remains:

```text
3
```

---

## After `push(2)`

Normal:

```text
2 ← TOP
7
3
5
```

Min:

```text
2 ← TOP
3
3
5
```

Minimum:

```text
2
```

---

## Now `pop()`

Remove:

```text
2
```

Normal:

```text
7
3
5
```

Min:

```text
3
3
5
```

Now:

```text
getMin() = 3
```

---

# Why Does the Min Stack Work?

This is the key idea.

At every level, `minStack` stores:

```text
minimum value from bottom up to this level
```

For example:

```text
Values:

5
3
7
2
```

Min stack:

```text
5
3
3
2
```

Meaning:

```text
After 5 → min = 5
After 3 → min = 3
After 7 → min = 3
After 2 → min = 2
```

Therefore:

```java
minStack.peek()
```

always gives the current minimum.

---

# Alternative Approach — Store Pair

Instead of using two stacks, we can store:

```text
(value, currentMinimum)
```

inside one stack.

Example:

```text
(5,5)
(3,3)
(7,3)
(2,2)
```

Then the top pair tells us both:

```text
current value
current minimum
```

---

# Optimal Java Code — Pair Version

```java
import java.util.Stack;

class MinStack {

    private Stack<int[]> stack;

    public MinStack() {

        stack = new Stack<>();
    }

    public void push(int val) {

        int currentMin;

        if (stack.isEmpty()) {
            currentMin = val;
        } else {
            currentMin = Math.min(
                val,
                stack.peek()[1]
            );
        }

        stack.push(new int[]{val, currentMin});
    }

    public void pop() {

        stack.pop();
    }

    public int top() {

        return stack.peek()[0];
    }

    public int getMin() {

        return stack.peek()[1];
    }
}
```

This is also:

```text
push → O(1)
pop → O(1)
top → O(1)
getMin → O(1)
```

---

# Interview Explanation

Say:

> "A normal stack can give me push, pop, and top in O(1), but finding the minimum would take O(n). To make getMin O(1), I'll maintain another stack where each position stores the minimum value seen up to that point. Whenever I push, I calculate the new minimum using the current value and the previous minimum. Whenever I pop, I pop from both stacks."

---

# Complexity

```text
push   → O(1)
pop    → O(1)
top    → O(1)
getMin → O(1)

Space → O(n)
```

---

# Common Mistakes

## Mistake 1 — Recalculating Minimum on Every `getMin()`

This gives:

```text
O(n)
```

and defeats the purpose of the problem.

---

## Mistake 2 — Not Updating Min Stack on Pop

If you push to both stacks:

```text
stack.push(val);
minStack.push(min);
```

you must also pop from both:

```text
stack.pop();
minStack.pop();
```

Otherwise their states become inconsistent.

---

## Mistake 3 — Forgetting Duplicate Minimums

Consider:

```text
push(2)
push(2)
push(3)
```

If you pop one `2`, the minimum should still be:

```text
2
```

This is why the minimum stack stores the minimum at **every level**, rather than storing only when a new minimum appears.

---

# Classic Stack Template

For problems where we need:

```text
normal stack
+
additional information
```

think:

```text
┌──────────────────────┐
│     Main Stack       │
│                      │
│      current         │
│      current         │
│      current         │
└──────────────────────┘

          +

┌──────────────────────┐
│   Auxiliary Stack    │
│                      │
│   extra information  │
│   extra information  │
│   extra information  │
└──────────────────────┘
```

Examples:

```text
Min Stack
Max Stack
Frequency Stack
```

---

# Problem Comparison

| Problem           | Main Pattern  | Important Idea                   |
| ----------------- | ------------- | -------------------------------- |
| Valid Parentheses | Classic Stack | Last opened bracket closes first |
| Min Stack         | Custom Stack  | Maintain minimum at every level  |

---

# Stack Recognition — Part 1

After these two problems, your mental model should be:

```text
                STACK
                  |
       ┌──────────┴──────────┐
       ↓                     ↓
   Matching              Extra State
       |                     |
       ↓                     ↓
 Parentheses             Min Stack
       |                     |
       ↓                     ↓
 LIFO Matching        Auxiliary Stack
```

---

# What To Say in an Interview

When you see **Valid Parentheses**:

> "The problem has nested matching elements, so I need LIFO behavior. I'll use a stack to keep track of the most recently opened bracket."

When you see **Min Stack**:

> "The normal stack operations are O(1), but minimum lookup would be O(n). I'll maintain additional minimum information so that getMin can also be answered in O(1)."

---

# Important Stack Templates

## Template 1 — Matching

```java
Stack<Character> stack = new Stack<>();

for (char ch : s.toCharArray()) {

    if (isOpening(ch)) {

        stack.push(ch);

    } else {

        if (stack.isEmpty()) {
            return false;
        }

        if (!matches(stack.pop(), ch)) {
            return false;
        }
    }
}

return stack.isEmpty();
```

---

# Template 2 — Auxiliary Stack

```java
Stack<Integer> stack = new Stack<>();
Stack<Integer> helper = new Stack<>();

public void push(int value) {

    stack.push(value);

    int extraInformation;

    if (helper.isEmpty()) {
        extraInformation = value;
    } else {
        extraInformation = calculate(
            value,
            helper.peek()
        );
    }

    helper.push(extraInformation);
}
```

The `helper` stack can store:

```text
minimum
maximum
frequency
other required state
```

---

# Day 9 Problem Roadmap

We will cover the sheet in this order:

```text
PART 1
├── Stack Fundamentals
├── 1. Valid Parentheses
└── 2. Min Stack

PART 2
├── 3. Daily Temperatures
├── 4. Next Greater Element I
└── 5. Next Greater Element II

PART 3
├── Monotonic Stack Deep Dive
├── Pattern Recognition
└── Advanced Variations

PART 4
├── 6. Decode String
├── 7. Evaluate Reverse Polish Notation
└── 8. Remove All Adjacent Duplicates

PART 5
├── 9. Remove K Digits
├── 10. Largest Rectangle in Histogram
└── Advanced Monotonic Stack

PART 6
├── 11. Asteroid Collision
├── 12. Backspace String Compare
└── Stack Simulation

PART 7
├── 13. Implement Stack using Queues
├── 14. Simplify Path
└── 15. Basic Calculator II

PART 8
└── Complete Stack Revision
```

The ordering above follows the problem set and its pattern labels from the uploaded Day-9 sheet.  

---

# One-Minute Revision

```text
STACK
↓
LIFO
↓
Last In → First Out
```

### Think Stack when you see:

```text
Nested
Matching
Previous
Undo
Backspace
Reverse
Next Greater
Next Smaller
Expression
```

### Classic Stack:

```text
Valid Parentheses
```

### Custom Stack:

```text
Min Stack
```

### Future:

```text
Next Greater
        ↓
Monotonic Stack
```

---

# Golden Rule

> **If the problem asks you to process the most recent unresolved element first, think STACK.**

And for interviews:

```text
Don't just say:

"I'll use a Stack."

Say:

"The problem requires LIFO behavior because
the most recently encountered/opened element
needs to be processed first, so Stack is a
natural fit."
```

That is the difference between **knowing the data structure** and **recognizing the pattern**.

---

# End of Stack Part 1
