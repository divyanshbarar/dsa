# Day 9 — Stack Pattern

# Stack Pattern — Part 4

> **Focus:** Advanced Monotonic Stack, Stack Simulation, and String Simulation
> **Problems:** Largest Rectangle in Histogram, Asteroid Collision, Backspace String Compare

---

# 1. What We Are Learning

The previous parts covered:

```text
Part 1
    ↓
Basic Stack
Valid Parentheses
Min Stack

Part 2
    ↓
Monotonic Stack
Daily Temperatures
Next Greater Element I
Next Greater Element II

Part 3
    ↓
Stack for Parsing / Greedy
Decode String
RPN
Remove Duplicates
Remove K Digits
```

Now we move to some of the most important Stack interview problems.

Especially:

```text
Largest Rectangle in Histogram
        ↓
Advanced Monotonic Stack

Asteroid Collision
        ↓
Stack Simulation

Backspace String Compare
        ↓
Stack / String Simulation
```

---

# Problem 10 — Largest Rectangle in Histogram

## 1. Problem Statement

You are given an array representing the heights of bars in a histogram.

Every bar has width `1`.

Find the **largest rectangular area** that can be formed.

Example:

```text
heights = [2,1,5,6,2,3]
```

The largest rectangle is:

```text
height = 5
width = 2
```

Therefore:

```text
area = 5 × 2
     = 10
```

Answer:

```text
10
```

---

# 2. Visual Intuition

For:

```text
[2,1,5,6,2,3]
```

Think:

```text
        █
    █   █
    █ █ █
█   █ █ █
█ █ █ █ █ █
────────────
2 1 5 6 2 3
```

The rectangle using:

```text
5 and 6
```

has:

```text
height = 5
width = 2
```

So:

```text
5 × 2 = 10
```

---

# 3. Pattern Recognition

This problem is one of the classic signals for:

```text
Largest Rectangle
Histogram
Previous Smaller
Next Smaller
```

Immediately think:

```text
MONOTONIC STACK
```

The key question is:

> For every bar, how far can this bar extend to the left and right while remaining the minimum-height bar?

Suppose:

```text
heights = [2,1,5,6,2,3]
```

For height `5`:

```text
left boundary  → index 1
right boundary → index 4
```

So the width is:

```text
4 - 1 - 1
= 2
```

Area:

```text
5 × 2
= 10
```

---

# 4. Core Idea

For every bar we want:

```text
Previous Smaller Element
        +
Next Smaller Element
```

If the current bar has height `h`:

```text
left smaller  = L
right smaller = R
```

Then:

```text
width = R - L - 1
```

and:

```text
area = h × width
```

The challenge is finding these boundaries efficiently.

---

# 5. Approach 1 — Brute Force

For every bar:

1. Treat it as the minimum height.
2. Expand left while bars are at least as tall.
3. Expand right while bars are at least as tall.
4. Calculate the rectangle.

For:

```text
[2,1,5,6,2,3]
```

Take `5`:

```text
5
```

Expand right:

```text
5,6
```

Cannot include `2`.

Therefore:

```text
area = 5 × 2
     = 10
```

---

## Brute Force Code

```java
class Solution {

    public int largestRectangleArea(int[] heights) {

        int n = heights.length;
        int maxArea = 0;

        for (int i = 0; i < n; i++) {

            int left = i;
            int right = i;

            while (left >= 0
                    && heights[left] >= heights[i]) {
                left--;
            }

            while (right < n
                    && heights[right] >= heights[i]) {
                right++;
            }

            int width = right - left - 1;

            int area = heights[i] * width;

            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}
```

---

# 6. Complexity

```text
Time:  O(n²)
Space: O(1)
```

For every bar, we may scan almost the entire array.

---

# 7. Better Approach — Precompute Boundaries

Instead of expanding left and right repeatedly, we can calculate:

```text
previous smaller index
```

for every element.

And:

```text
next smaller index
```

for every element.

Both can be calculated using monotonic stacks.

Then:

```text
width = nextSmaller[i] - previousSmaller[i] - 1
```

and:

```text
area = heights[i] × width
```

This gives:

```text
O(n)
```

but requires multiple arrays and multiple passes.

This is already optimal in time, but we can simplify the implementation further.

---

# 8. Optimal Approach — One-Pass Monotonic Stack

Instead of explicitly calculating:

```text
previous smaller
next smaller
```

we can calculate the area exactly when a smaller bar appears.

Maintain an:

```text
Increasing Monotonic Stack
```

The stack contains indices whose bars are in increasing height order.

Example:

```text
heights = [2,1,5,6,2,3]
```

Process:

```text
2
```

Stack:

```text
[0]
```

Process:

```text
1
```

Since:

```text
1 < 2
```

the bar `2` can no longer extend to the right.

So calculate its area.

This is the key insight:

> **When a smaller bar arrives, it tells us that the previous taller bar has found its right boundary.**

---

# 9. Why Do We Need the Index?

Suppose:

```text
stack = [5,6]
```

and current height is:

```text
2
```

We need to know how wide the popped bar can extend.

The index tells us:

```text
left boundary
right boundary
```

Therefore:

```text
Stack<Integer>
```

stores indices.

---

# 10. Width Calculation

Suppose we pop index:

```text
currentIndex = 4
```

and:

```text
stack.peek() = 1
```

The current smaller bar is at:

```text
right = 4
```

The previous smaller bar is:

```text
left = 1
```

Therefore:

```text
width = right - left - 1
```

So:

```text
width = 4 - 1 - 1
      = 2
```

---

# 11. Important Case — Empty Stack

Suppose after popping:

```text
stack.isEmpty()
```

Then there is no smaller bar on the left.

So the rectangle can extend all the way to index `0`.

Therefore:

```text
width = i
```

when processing the current index `i`.

More generally:

```java
int width = stack.isEmpty()
        ? i
        : i - stack.peek() - 1;
```

---

# 12. Sentinel Trick

There is another very useful trick.

After processing all bars, some increasing bars may still remain in the stack.

Example:

```text
[2,4,5]
```

Nothing smaller arrives to force them out.

We can pretend there is a final bar:

```text
0
```

So:

```text
[2,4,5,0]
```

The `0` forces every remaining bar to be popped.

This is called a:

```text
Sentinel
```

---

# 13. Optimal Java Code

```java
import java.util.Stack;

class Solution {

    public int largestRectangleArea(int[] heights) {

        int n = heights.length;

        Stack<Integer> stack = new Stack<>();

        int maxArea = 0;

        for (int i = 0; i <= n; i++) {

            int currentHeight =
                    (i == n) ? 0 : heights[i];

            while (!stack.isEmpty()
                    && currentHeight < heights[stack.peek()]) {

                int height = heights[stack.pop()];

                int width;

                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }

                int area = height * width;

                maxArea = Math.max(maxArea, area);
            }

            stack.push(i);
        }

        return maxArea;
    }
}
```

There is one issue with this direct sentinel implementation:

```java
stack.push(i);
```

at `i == n` pushes the sentinel index after all real bars have been processed. It doesn't affect the result, but it is unnecessary.

A cleaner version is:

```java
import java.util.Stack;

class Solution {

    public int largestRectangleArea(int[] heights) {

        int n = heights.length;

        Stack<Integer> stack = new Stack<>();

        int maxArea = 0;

        for (int i = 0; i <= n; i++) {

            int currentHeight =
                    (i == n) ? 0 : heights[i];

            while (!stack.isEmpty()
                    && currentHeight < heights[stack.peek()]) {

                int height = heights[stack.pop()];

                int width = stack.isEmpty()
                        ? i
                        : i - stack.peek() - 1;

                int area = height * width;

                maxArea = Math.max(maxArea, area);
            }

            if (i < n) {
                stack.push(i);
            }
        }

        return maxArea;
    }
}
```

Use this version in interviews.

---

# 14. Dry Run

```text
heights = [2,1,5,6,2,3]
```

Start:

```text
stack = []
maxArea = 0
```

---

## `i = 0`, height = 2

Push:

```text
stack = [0]
```

---

## `i = 1`, height = 1

Current:

```text
1 < 2
```

Pop index `0`.

```text
height = 2
```

Stack is empty.

Therefore:

```text
width = 1
area = 2 × 1
     = 2
```

```text
maxArea = 2
```

Push index `1`.

```text
stack = [1]
```

---

## `i = 2`, height = 5

```text
5 > 1
```

Push:

```text
stack = [1,2]
```

---

## `i = 3`, height = 6

```text
6 > 5
```

Push:

```text
stack = [1,2,3]
```

---

## `i = 4`, height = 2

Now:

```text
2 < 6
```

Pop `6`.

Previous smaller:

```text
index = 2
```

Width:

```text
4 - 2 - 1
= 1
```

Area:

```text
6 × 1
= 6
```

---

Still:

```text
2 < 5
```

Pop `5`.

Stack:

```text
[1]
```

Width:

```text
4 - 1 - 1
= 2
```

Area:

```text
5 × 2
= 10
```

Therefore:

```text
maxArea = 10
```

Push index `4`.

```text
stack = [1,4]
```

---

## `i = 5`, height = 3

```text
3 > 2
```

Push:

```text
stack = [1,4,5]
```

---

## Sentinel — `i = 6`

Current height:

```text
0
```

Pop `3`:

```text
width = 6 - 4 - 1
      = 1

area = 3
```

Pop `2`:

```text
width = 6 - 1 - 1
      = 4

area = 2 × 4
     = 8
```

Final:

```text
maxArea = 10
```

---

# 15. The Deepest Insight

This is the most important thing to understand.

The stack is maintaining:

```text
Increasing heights
```

Example:

```text
2
5
6
```

When:

```text
2
```

arrives:

```text
2 < 6
```

So `6` cannot continue further right.

Then:

```text
2 < 5
```

So `5` cannot continue further right either.

Therefore the current smaller element acts as the:

```text
RIGHT BOUNDARY
```

for all taller bars being popped.

The remaining stack top acts as the:

```text
LEFT BOUNDARY
```

---

# 16. Interview Explanation

Say:

> "For every bar, I need to know the largest range in which that bar can be the minimum height. I maintain an increasing monotonic stack of indices. When I encounter a smaller height, every taller bar on top of the stack has found its right boundary, so I pop it and calculate its maximum width using the current index as the right boundary and the new stack top as the previous smaller element. I also process a final zero-height sentinel to flush the remaining bars. Each index is pushed and popped once, giving O(n) time."

---

# 17. Complexity

```text
Time:  O(n)
Space: O(n)
```

---

# 18. Common Mistakes

### Mistake 1 — Using a decreasing stack

For Largest Rectangle:

```text
Use increasing stack
```

because we need:

```text
Previous Smaller
Next Smaller
```

---

### Mistake 2 — Wrong width formula

Remember:

```text
width = right - left - 1
```

Not:

```text
right - left
```

---

### Mistake 3 — Forgetting remaining bars

For:

```text
[2,4,5]
```

there is no smaller bar after `5`.

You must flush the stack.

Use the sentinel:

```text
0
```

---

### Mistake 4 — Storing heights instead of indices

You need indices to calculate:

```text
width
```

Therefore:

```java
Stack<Integer> stack
```

stores indices.

---

# Problem 11 — Asteroid Collision

## 1. Problem Statement

You are given asteroids moving in a straight line.

The sign represents direction:

```text
positive → moving right
negative → moving left
```

The absolute value represents size.

When two asteroids collide:

```text
smaller asteroid → destroyed
larger asteroid → survives
same size → both destroyed
```

Asteroids moving in the same direction never collide.

---

# 2. Example

```text
asteroids = [5,10,-5]
```

Movement:

```text
5  →
10 →
    ← -5
```

`10` and `-5` collide.

Since:

```text
10 > 5
```

`-5` is destroyed.

Result:

```text
[5,10]
```

---

# 3. Pattern Recognition

Think:

```text
Collision
Simulation
Previous elements
```

A new asteroid can collide with the asteroid immediately before it.

If that asteroid is destroyed, the new asteroid may then collide with the one before that.

That is exactly:

```text
LIFO
```

Therefore:

```text
STACK
```

---

# 4. When Can a Collision Actually Happen?

This is extremely important.

A collision occurs only when:

```text
previous asteroid → right
current asteroid   → left
```

Therefore:

```text
previous > 0
AND
current < 0
```

In code:

```java
stack.peek() > 0 && asteroid < 0
```

Examples:

```text
5 →    -3 ←
```

Collision:

```text
YES
```

But:

```text
-5 ←    3 →
```

They are moving away from each other.

```text
NO
```

And:

```text
5 →    3 →
```

Same direction.

```text
NO
```

---

# 5. Approach 1 — Brute Force

Repeatedly scan the array and resolve collisions whenever a valid pair appears.

After a collision, the array changes.

You may need to scan again because the surviving asteroid can collide with another asteroid behind it.

Worst-case:

```text
O(n²)
```

---

# 6. Better Approach — Simulate with a Result List

We can process asteroids from left to right and maintain all surviving asteroids.

A list can represent the current state.

However, we repeatedly need:

```text
last surviving asteroid
```

and:

```text
remove last
```

which is exactly stack behavior.

So we naturally arrive at the optimal Stack solution.

---

# 7. Optimal Approach — Stack Simulation

For every asteroid:

### Case 1 — No collision possible

Push it.

```text
positive
```

or:

```text
negative
```

when the stack top is not positive.

---

### Case 2 — Collision possible

Condition:

```text
stack.peek() > 0
AND
current < 0
```

Now compare sizes.

Let:

```text
top = stack.peek()
current = asteroid
```

Because current is negative, its size is:

```java
Math.abs(current)
```

---

## If Top Is Smaller

Example:

```text
10 →    -5 ←
```

`-5` is destroyed.

Do not push it.

---

## If Top Is Larger

Example:

```text
5 →    -10 ←
```

`5` is destroyed.

Pop it.

Then the `-10` may collide with the next asteroid.

Therefore:

```text
while (...)
```

is required.

---

## If Same Size

Example:

```text
5 →    -5 ←
```

Both are destroyed.

Pop the top and mark current as destroyed.

---

# 8. Optimal Java Code

```java
import java.util.Stack;

class Solution {

    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stack = new Stack<>();

        for (int asteroid : asteroids) {

            boolean destroyed = false;

            while (!stack.isEmpty()
                    && stack.peek() > 0
                    && asteroid < 0) {

                int top = stack.peek();

                if (top < Math.abs(asteroid)) {

                    // Top asteroid is destroyed
                    stack.pop();

                } else if (top == Math.abs(asteroid)) {

                    // Both are destroyed
                    stack.pop();
                    destroyed = true;
                    break;

                } else {

                    // Current asteroid is destroyed
                    destroyed = true;
                    break;
                }
            }

            if (!destroyed) {
                stack.push(asteroid);
            }
        }

        int[] result = new int[stack.size()];

        for (int i = 0; i < stack.size(); i++) {
            result[i] = stack.get(i);
        }

        return result;
    }
}
```

---

# 9. Dry Run

```text
asteroids = [5,10,-5]
```

Start:

```text
stack = []
```

---

### `5`

No collision.

```text
stack = [5]
```

---

### `10`

Both moving right.

No collision.

```text
stack = [5,10]
```

---

### `-5`

Current is moving left.

Stack top:

```text
10 → 
```

So collision occurs.

Compare:

```text
10 > 5
```

Current `-5` is destroyed.

```text
stack = [5,10]
```

Final:

```text
[5,10]
```

---

# 10. More Interesting Example

```text
[10,2,-5]
```

Process:

```text
10
```

```text
[10]
```

Process:

```text
2
```

```text
[10,2]
```

Process:

```text
-5
```

Collision:

```text
2 →    -5 ←
```

`2 < 5`

So:

```text
2 destroyed
```

Stack:

```text
[10]
```

Now `-5` may collide again:

```text
10 →    -5 ←
```

Since:

```text
10 > 5
```

`-5` is destroyed.

Final:

```text
[10]
```

This is exactly why we need a `while` loop.

---

# 11. Interview Explanation

Say:

> "I process the asteroids from left to right and maintain the surviving asteroids in a stack. A collision is possible only when the stack top is moving right and the current asteroid is moving left. I repeatedly compare their sizes. If the stack asteroid is smaller, I pop it and continue because the current asteroid may collide with another asteroid. If they are equal, both are destroyed. Otherwise the current asteroid is destroyed. Each asteroid is pushed and popped at most once, so the solution is O(n)."

---

# 12. Complexity

```text
Time:  O(n)
Space: O(n)
```

Even though we have a `while` loop, each asteroid can be removed from the stack only once.

---

# 13. Common Mistakes

### Mistake 1 — Checking every pair

Not every asteroid can collide.

Only:

```text
positive → negative
```

can collide.

---

### Mistake 2 — Forgetting repeated collisions

Example:

```text
[10,2,-5]
```

After destroying `2`, `-5` must be checked against `10`.

That's why:

```java
while (...)
```

is required.

---

### Mistake 3 — Forgetting equal sizes

```text
5
-5
```

Both disappear.

---

### Mistake 4 — Accidentally destroying same-direction asteroids

```text
5
10
```

No collision.

```text
-5
-10
```

No collision.

---

# Problem 12 — Backspace String Compare

## 1. Problem Statement

You are given two strings.

The character:

```text
#
```

represents a backspace.

For example:

```text
"ab#c"
```

becomes:

```text
"ac"
```

because:

```text
a b # c
  ↑
remove b
```

Return whether the two final strings are equal.

---

# 2. Example

```text
s = "ab#c"
t = "ad#c"
```

Process `s`:

```text
ab#c
 ↓
ac
```

Process `t`:

```text
ad#c
 ↓
ac
```

Therefore:

```text
true
```

---

# 3. Pattern Recognition

The key operation is:

```text
character
    ↓
backspace
    ↓
remove previous surviving character
```

This is the same Stack idea we saw in:

```text
Remove Adjacent Duplicates
```

We need:

```text
push character
pop previous character
```

Therefore:

```text
STACK
```

---

# 4. Approach 1 — Brute Force

Process each string by repeatedly finding:

```text
#
```

and deleting it together with the previous character.

For:

```text
abc#d##
```

repeatedly modify the string.

This can result in:

```text
O(n²)
```

depending on the string implementation.

---

# 5. Better Approach — Build the Final String

Use a `StringBuilder`.

For every character:

```text
normal character → append
#                → delete last
```

This is already:

```text
O(n)
```

and is the cleanest solution.

---

# 6. Optimal Approach — StringBuilder as Stack

We don't actually need a `Stack<Character>`.

Use:

```java
StringBuilder
```

as a stack.

Operations:

```text
push → append()
pop  → deleteCharAt(length - 1)
peek → charAt(length - 1)
```

---

# 7. Important Edge Case

Suppose:

```text
s = "###abc"
```

There may be a backspace when the current result is empty.

For:

```text
#
```

if the stack is empty:

```text
do nothing
```

Therefore:

```java
if (stack.length() > 0)
```

must be checked.

---

# 8. Optimal Java Code

```java
class Solution {

    public boolean backspaceCompare(String s, String t) {

        return build(s).equals(build(t));
    }

    private String build(String s) {

        StringBuilder stack = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '#') {

                if (stack.length() > 0) {
                    stack.deleteCharAt(stack.length() - 1);
                }

            } else {

                stack.append(ch);
            }
        }

        return stack.toString();
    }
}
```

---

# 9. Dry Run

```text
s = "ab#c"
```

Start:

```text
""
```

Read `a`:

```text
"a"
```

Read `b`:

```text
"ab"
```

Read `#`:

```text
"a"
```

Read `c`:

```text
"ac"
```

Final:

```text
"ac"
```

---

For:

```text
t = "ad#c"
```

```text
a
↓
ad
↓
a
↓
ac
```

Final:

```text
"ac"
```

Therefore:

```text
"ac".equals("ac")
```

returns:

```text
true
```

---

# 10. Complexity

For strings of lengths `n` and `m`:

```text
Time:  O(n + m)
Space: O(n + m)
```

---

# 11. Interview Explanation

Say:

> "I treat each string as a stack. Normal characters are pushed into a StringBuilder, while a `#` removes the most recently surviving character if one exists. After processing both strings, I compare their resulting strings. This directly simulates the backspace behavior in O(n) time."

---

# 12. Common Mistakes

### Mistake 1 — Removing a character when stack is empty

For:

```text
"#"
```

there is nothing to delete.

So simply ignore the backspace.

---

### Mistake 2 — Comparing original strings

For:

```text
"ab#c"
"ac"
```

the original strings are different.

But after processing:

```text
"ac"
"ac"
```

They are equal.

---

### Mistake 3 — Overcomplicating with Stack<Character>

This works:

```java
Stack<Character>
```

but:

```java
StringBuilder
```

is simpler and efficient for this problem.

---

# 13. Three Problems — Pattern Connection

These three problems look unrelated:

```text
Largest Rectangle in Histogram
Asteroid Collision
Backspace String Compare
```

But the Stack reasoning is:

```text
Largest Rectangle
        ↓
Keep unresolved increasing heights
        ↓
Smaller element resolves previous bars
```

```text
Asteroid Collision
        ↓
Keep surviving asteroids
        ↓
New asteroid may destroy previous survivors
```

```text
Backspace
        ↓
Keep surviving characters
        ↓
# removes previous survivor
```

The common concept is:

> **Maintain the elements that currently survive, and remove them when the new element proves they can no longer remain.**

---

# 14. Stack Pattern Comparison

| Problem            | Stack Stores        | Main Operation          |
| ------------------ | ------------------- | ----------------------- |
| Largest Rectangle  | Indices             | Pop taller bars         |
| Asteroid Collision | Surviving asteroids | Pop destroyed asteroids |
| Backspace Compare  | Characters          | Pop previous character  |

---

# 15. Important Monotonic Stack Rule

For:

```text
Largest Rectangle
```

we use:

```text
Increasing Stack
```

because we want:

```text
Previous Smaller
Next Smaller
```

Remember:

```text
Next Greater
    ↓
Decreasing Stack

Next Smaller
    ↓
Increasing Stack
```

---

# 16. The Histogram Template 🔥

This is worth memorizing.

```java
Stack<Integer> stack = new Stack<>();

for (int i = 0; i <= n; i++) {

    int currentHeight =
            (i == n) ? 0 : heights[i];

    while (!stack.isEmpty()
            && currentHeight < heights[stack.peek()]) {

        int height = heights[stack.pop()];

        int width = stack.isEmpty()
                ? i
                : i - stack.peek() - 1;

        int area = height * width;

        // update maximum
    }

    if (i < n) {
        stack.push(i);
    }
}
```

---

# 17. The Asteroid Template 🔥

```java
Stack<Integer> stack = new Stack<>();

for (int asteroid : asteroids) {

    boolean destroyed = false;

    while (!stack.isEmpty()
            && stack.peek() > 0
            && asteroid < 0) {

        if (stack.peek() < Math.abs(asteroid)) {

            stack.pop();

        } else if (stack.peek() == Math.abs(asteroid)) {

            stack.pop();
            destroyed = true;
            break;

        } else {

            destroyed = true;
            break;
        }
    }

    if (!destroyed) {
        stack.push(asteroid);
    }
}
```

---

# 18. String-as-Stack Template 🔥

Whenever you see:

```text
Remove previous character
Backspace
Undo last character
Adjacent duplicate
```

think:

```java
StringBuilder stack = new StringBuilder();
```

Then:

```java
// Push
stack.append(ch);

// Pop
stack.deleteCharAt(stack.length() - 1);

// Peek
stack.charAt(stack.length() - 1);
```

---

# 19. One-Minute Revision

## Largest Rectangle

```text
Question:
Largest rectangle in histogram?

Pattern:
Monotonic Stack

Stack:
Increasing

Store:
Indices

Need:
Previous Smaller + Next Smaller

Width:
right - left - 1

Complexity:
O(n)
```

---

## Asteroid Collision

```text
Question:
Which asteroids survive?

Pattern:
Stack Simulation

Collision:
positive → negative

Smaller:
Destroyed

Equal:
Both destroyed

Need repeated collisions:
while loop

Complexity:
O(n)
```

---

## Backspace String Compare

```text
Question:
Are final strings equal after #?

Pattern:
String Stack

Normal char:
Push

#:
Pop

Implementation:
StringBuilder

Complexity:
O(n + m)
```

---

# 20. Golden Rules 🔥

> **Histogram → Increasing Monotonic Stack**

> **Collision → Stack Simulation**

> **Backspace → StringBuilder as Stack**

> **If a new element can invalidate/remove previous elements, think Stack.**

> **If every element is pushed and popped at most once, look for O(n).**

---

# Day 9 Progress

```text
✅ 1. Valid Parentheses
✅ 2. Min Stack

✅ 3. Daily Temperatures
✅ 4. Next Greater Element I
✅ 5. Next Greater Element II

✅ 6. Decode String
✅ 7. Evaluate Reverse Polish Notation
✅ 8. Remove All Adjacent Duplicates
✅ 9. Remove K Digits

✅ 10. Largest Rectangle in Histogram
✅ 11. Asteroid Collision
✅ 12. Backspace String Compare

⬜ 13. Implement Stack using Queues
⬜ 14. Simplify Path
⬜ 15. Basic Calculator II
```

---


