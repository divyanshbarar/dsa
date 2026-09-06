# Day 9 — Stack Pattern

# Stack Pattern — Part 3

> **Focus:** Stack for Parsing, String Processing, Expression Evaluation, and Greedy + Stack
> **Problems:** Decode String, Evaluate Reverse Polish Notation, Remove All Adjacent Duplicates, Remove K Digits

---

# 1. Stack Pattern — Beyond Next Greater

So far we used Stack for:

```text
LIFO
↓
Matching brackets
↓
Min Stack
↓
Monotonic Stack
↓
Next Greater / Smaller
```

Now we use Stack for a different category:

```text
Parsing
String processing
Expression evaluation
Nested structures
Greedy decisions
```

The common idea is:

> **When the current element depends on something that happened earlier and we need to preserve that previous state, Stack is often useful.**

---

# 2. Pattern Recognition

Think Stack when you see:

```text
Nested string
Nested expression
Decode / encode
Remove adjacent elements
Evaluate expression
Reverse Polish Notation
Undo previous decision
Remove digits to make something smaller
```

Especially remember:

```text
Nested structure
      ↓
Stack
```

and:

```text
Remove previous element when current element conflicts with it
      ↓
Stack
```

---

# Problem 6 — Decode String

## 1. Problem Statement

You are given an encoded string.

The encoding format is:

```text
k[encoded_string]
```

where:

```text
k = number of repetitions
```

For example:

```text
3[a]
```

means:

```text
aaa
```

And:

```text
2[bc]
```

means:

```text
bcb c
```

or simply:

```text
bcbc
```

---

# 2. Example

Input:

```text
3[a]2[bc]
```

Decode:

```text
aaa + bcbc
```

Output:

```text
aaabcbc
```

Another example:

```text
3[a2[c]]
```

First:

```text
2[c] → cc
```

Then:

```text
a + cc → acc
```

Then:

```text
3[acc]
```

Result:

```text
accaccacc
```

---

# 3. Pattern Recognition

Look at:

```text
3[a2[c]]
```

The structure is nested:

```text
3[
    a
    2[
        c
    ]
]
```

Whenever you see:

```text
Nested
+
Need to remember previous state
```

think:

```text
STACK
```

The challenge is that when we encounter `[` we need to remember:

```text
What string was I building before this?
What is the repetition count?
```

Therefore we need two stacks:

```text
countStack
stringStack
```

---

# 4. Approach 1 — Brute Force

One possible approach is recursive parsing.

Whenever we encounter:

```text
k[
```

we recursively decode everything inside the brackets.

For:

```text
3[a2[c]]
```

we recursively solve:

```text
a2[c]
```

then:

```text
acc
```

and finally repeat it three times.

This is conceptually simple, but managing nested parsing manually becomes more complicated.

---

# 5. Better Approach — Recursion

We can create a recursive function:

```text
decode(index)
```

The function processes characters until it reaches:

```text
]
```

When it sees:

```text
number[
```

it recursively decodes the content inside the brackets.

Conceptually:

```text
decode(3[a2[c]])
        ↓
decode(a2[c])
        ↓
decode(c)
        ↓
cc
        ↓
acc
        ↓
accaccacc
```

This works well.

However, because the problem naturally behaves like nested LIFO state, we can solve it iteratively using Stack.

---

# 6. Optimal Approach — Two Stacks

Maintain:

```text
countStack
stringStack
```

Also maintain:

```text
currentNumber
currentString
```

---

## When We See a Digit

Build the complete number.

For:

```text
123[
```

we need:

```text
123
```

not:

```text
1
2
3
```

So:

```java
currentNumber = currentNumber * 10 + digit;
```

---

## When We See `[`

We need to save the current state.

Push:

```text
currentNumber
currentString
```

Then reset:

```text
currentNumber = 0
currentString = ""
```

---

## When We See a Letter

Append it to:

```text
currentString
```

---

## When We See `]`

The current bracket is complete.

Suppose:

```text
repeatCount = countStack.pop()
previousString = stringStack.pop()
```

Then:

```text
currentString =
previousString + currentString repeated repeatCount times
```

---

# 7. Dry Run

Input:

```text
3[a2[c]]
```

Initial:

```text
currentNumber = 0
currentString = ""
```

---

### Read `3`

```text
currentNumber = 3
```

---

### Read `[`

Push:

```text
countStack = [3]
stringStack = [""]
```

Reset:

```text
currentNumber = 0
currentString = ""
```

---

### Read `a`

```text
currentString = "a"
```

---

### Read `2`

```text
currentNumber = 2
```

---

### Read `[`

Push:

```text
countStack = [3,2]
stringStack = ["","a"]
```

Reset:

```text
currentNumber = 0
currentString = ""
```

---

### Read `c`

```text
currentString = "c"
```

---

### Read `]`

Pop:

```text
count = 2
previousString = "a"
```

Repeat:

```text
"c" × 2 = "cc"
```

Combine:

```text
"a" + "cc"
```

Therefore:

```text
currentString = "acc"
```

---

### Read final `]`

Pop:

```text
count = 3
previousString = ""
```

Repeat:

```text
"acc" × 3
```

Result:

```text
accaccacc
```

---

# 8. Optimal Java Code

```java
import java.util.Stack;

class Solution {

    public String decodeString(String s) {

        Stack<Integer> countStack = new Stack<>();
        Stack<String> stringStack = new Stack<>();

        StringBuilder currentString = new StringBuilder();

        int currentNumber = 0;

        for (char ch : s.toCharArray()) {

            if (Character.isDigit(ch)) {

                currentNumber = currentNumber * 10
                        + (ch - '0');

            } else if (ch == '[') {

                countStack.push(currentNumber);
                stringStack.push(currentString.toString());

                currentNumber = 0;
                currentString = new StringBuilder();

            } else if (ch == ']') {

                int count = countStack.pop();
                String previousString = stringStack.pop();

                StringBuilder decoded = new StringBuilder();

                for (int i = 0; i < count; i++) {
                    decoded.append(currentString);
                }

                currentString =
                        new StringBuilder(previousString)
                                .append(decoded);

            } else {

                currentString.append(ch);
            }
        }

        return currentString.toString();
    }
}
```

---

# 9. Complexity

Let:

```text
n = size of encoded string
```

The complexity depends on the size of the **decoded output**.

```text
Time:  O(output size)

Space: O(n + output size)
```

The important interview point is:

> You cannot generally do better than the output size because we actually need to construct the decoded string.

---

# 10. Common Mistakes

### Mistake 1 — Forgetting multi-digit numbers

For:

```text
12[a]
```

don't do:

```java
currentNumber = ch - '0';
```

Instead:

```java
currentNumber = currentNumber * 10 + digit;
```

---

### Mistake 2 — Not resetting the number

After:

```text
[
```

reset:

```java
currentNumber = 0;
```

---

### Mistake 3 — Losing the previous string

Before entering brackets:

```text
3[abc]
```

we must remember whatever string existed before `[abc]`.

That's why:

```java
stringStack.push(currentString.toString());
```

is required.

---

# 11. Interview Explanation

Say:

> "The string contains nested structures, so I use two stacks to preserve the state before entering each bracket. One stack stores repetition counts and the other stores the string built before the corresponding bracket. When I encounter `]`, I pop both states, repeat the current substring the required number of times, and append it to the previous string."

---

# Problem 7 — Evaluate Reverse Polish Notation

## 1. Problem Statement

You are given an arithmetic expression in **Reverse Polish Notation (RPN)**.

Example:

```text
2 1 + 3 *
```

Instead of:

```text
(2 + 1) * 3
```

RPN writes:

```text
2 1 + 3 *
```

Operators come **after** their operands.

---

# 2. Example

Input:

```text
["2","1","+","3","*"]
```

Evaluation:

```text
2 + 1
= 3

3 * 3
= 9
```

Output:

```text
9
```

---

# 3. Pattern Recognition

The important clue:

```text
Evaluate expression
+
Operators appear after operands
```

Think:

```text
STACK
```

Why?

For every operator we need the **two most recent operands**.

Stack naturally gives us:

```text
last operand
second-last operand
```

---

# 4. Critical Operand Order

This is the biggest trap.

Suppose:

```text
["4","2","-"]
```

We have:

```text
4 - 2
```

When popping:

```java
int b = stack.pop();
int a = stack.pop();
```

Then:

```text
a - b
```

NOT:

```text
b - a
```

Same for division:

```text
a / b
```

---

# 5. Approach 1 — Brute Force

You could repeatedly search the expression for:

```text
operand operand operator
```

and replace the three values with the result.

For example:

```text
2 1 + 3 *
```

First:

```text
2 1 +
```

becomes:

```text
3
```

Then:

```text
3 3 *
```

becomes:

```text
9
```

This requires repeated modifications/searching and is unnecessary.

---

# 6. Better Approach — Recursive Evaluation

Another possibility is to process tokens recursively.

However, RPN already has a natural left-to-right evaluation order, so recursion adds complexity without providing an advantage.

---

# 7. Optimal Approach — Stack

Process every token.

### If token is a number

Push it:

```text
stack.push(number)
```

### If token is an operator

Pop:

```text
b
a
```

Calculate:

```text
a operator b
```

Push result.

---

# 8. Dry Run

Input:

```text
2 1 + 3 *
```

Start:

```text
stack = []
```

Read `2`:

```text
[2]
```

Read `1`:

```text
[2,1]
```

Read `+`:

Pop:

```text
b = 1
a = 2
```

Calculate:

```text
2 + 1 = 3
```

Push:

```text
[3]
```

Read `3`:

```text
[3,3]
```

Read `*`:

```text
b = 3
a = 3
```

Calculate:

```text
3 * 3 = 9
```

Push:

```text
[9]
```

Final answer:

```text
9
```

---

# 9. Optimal Java Code

```java
import java.util.Stack;

class Solution {

    public int evalRPN(String[] tokens) {

        Stack<Integer> stack = new Stack<>();

        for (String token : tokens) {

            if (token.equals("+")
                    || token.equals("-")
                    || token.equals("*")
                    || token.equals("/")) {

                int b = stack.pop();
                int a = stack.pop();

                int result;

                switch (token) {

                    case "+":
                        result = a + b;
                        break;

                    case "-":
                        result = a - b;
                        break;

                    case "*":
                        result = a * b;
                        break;

                    default:
                        result = a / b;
                }

                stack.push(result);

            } else {

                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }
}
```

---

# 10. Complexity

If there are `n` tokens:

```text
Time:  O(n)
Space: O(n)
```

Each token is processed exactly once.

---

# 11. Common Mistakes

### Mistake 1 — Reversing subtraction

Wrong:

```java
b - a
```

Correct:

```java
a - b
```

---

### Mistake 2 — Reversing division

Wrong:

```java
b / a
```

Correct:

```java
a / b
```

---

### Mistake 3 — Treating operators as numbers

Check operators first.

Otherwise:

```text
Integer.parseInt("+")
```

will fail.

---

# 12. Interview Explanation

Say:

> "RPN is naturally evaluated using a stack. Whenever I see a number, I push it. Whenever I see an operator, I pop the two most recent operands, apply the operator in the correct order, and push the result back. After processing all tokens, the stack contains the final result. This takes O(n) time."

---

# Problem 8 — Remove All Adjacent Duplicates

## 1. Problem Statement

Given a string, repeatedly remove **adjacent equal characters**.

Example:

```text
abbaca
```

First:

```text
abbaca
 ↑↑
```

Remove:

```text
bb
```

Remaining:

```text
aaca
```

Now:

```text
aa
```

Remove:

```text
ca
```

Final:

```text
ca
```

---

# 2. Pattern Recognition

The important clue is:

```text
Remove previous character if current character matches it
```

This is perfect for Stack.

Think:

```text
Current character
       ↓
Compare with previous surviving character
       ↓
Same?
 /   \
Yes   No
 ↓     ↓
Pop   Push
```

---

# 3. Approach 1 — Brute Force

Repeatedly scan the string:

1. Find adjacent duplicates.
2. Remove them.
3. Start scanning again.
4. Continue until no duplicates remain.

Example:

```text
abbaca
```

Remove:

```text
bb
```

Then scan again.

This can require repeated string modifications.

---

## Complexity

Depending on implementation:

```text
Time: O(n²)
Space: O(n)
```

---

# 4. Better Approach

We don't need to repeatedly restart the scan.

While processing each character, we only care about:

```text
previous surviving character
```

That means we can maintain the current result as we go.

A `StringBuilder` can act like a stack.

---

# 5. Optimal Approach — StringBuilder as Stack

For every character:

### If current character equals last character

Remove the last character.

### Otherwise

Append current character.

Example:

```text
abbaca
```

Start:

```text
""
```

`a`:

```text
"a"
```

`b`:

```text
"ab"
```

Next `b`:

```text
last = b
current = b
```

Remove:

```text
"a"
```

Next `a`:

```text
last = a
current = a
```

Remove:

```text
""
```

Next `c`:

```text
"c"
```

Next `a`:

```text
"ca"
```

Answer:

```text
ca
```

---

# 6. Optimal Java Code

```java
class Solution {

    public String removeDuplicates(String s) {

        StringBuilder stack = new StringBuilder();

        for (char ch : s.toCharArray()) {

            int size = stack.length();

            if (size > 0
                    && stack.charAt(size - 1) == ch) {

                stack.deleteCharAt(size - 1);

            } else {

                stack.append(ch);
            }
        }

        return stack.toString();
    }
}
```

---

# 7. Complexity

```text
Time:  O(n)
Space: O(n)
```

Each character is added and removed at most once.

---

# 8. Why Is StringBuilder a Stack?

Because we only need:

```text
push → append()
pop  → delete last character
peek → charAt(length - 1)
```

So:

```text
StringBuilder
```

can simulate:

```text
Stack<Character>
```

with less overhead.

---

# 9. Interview Explanation

Say:

> "The key observation is that after processing the prefix of the string, I only need the last surviving character to decide whether the current character forms a duplicate. So I use a StringBuilder as a stack. If the current character matches the last character, I remove the last character; otherwise I append it. Each character is processed in constant amortized time, giving O(n)."

---

# Problem 9 — Remove K Digits

## 1. Problem Statement

You are given a non-negative number as a string and an integer `k`.

Remove exactly `k` digits so that the resulting number is the **smallest possible number**.

Example:

```text
num = "1432219"
k = 3
```

Remove:

```text
4
3
2
```

Result:

```text
1219
```

---

# 2. Pattern Recognition

This problem is extremely important because it combines:

```text
Greedy
+
Monotonic Stack
```

The key question is:

> Which digit should we remove?

Suppose:

```text
143
```

If we can remove one digit:

```text
Remove 4 → 13
Remove 1 → 43
Remove 3 → 14
```

Clearly:

```text
13
```

is smallest.

Why remove `4`?

Because:

```text
1 > 4
```

Wait, that's false.

The actual useful rule is:

If:

```text
previous digit > current digit
```

then removing the previous larger digit makes the number smaller.

Example:

```text
143
 ↑↓
 4 > 3
```

Remove `4`:

```text
13
```

---

# 3. The Greedy Rule

While processing digits:

```text
If previous digit > current digit
```

then:

```text
Remove previous digit
```

because keeping a larger digit before a smaller digit makes the number unnecessarily large.

This naturally gives us:

```text
Monotonic Increasing Stack
```

---

# 4. Approach 1 — Brute Force

Try every possible combination of `k` digits to remove.

For example:

```text
num = "1432219"
k = 3
```

There are many possible sets of removed digits.

Generate every possible resulting number:

```text
choose n-k digits
```

and find the minimum.

This becomes combinatorial.

---

## Complexity

Approximately:

```text
O(C(n,k) × n)
```

which is impractical.

---

# 5. Better Approach

We can greedily decide which digits to remove.

Scan left to right.

Whenever:

```text
stack.top() > current
```

remove the stack top.

Example:

```text
1432219
```

Process:

```text
1
```

Stack:

```text
1
```

Process `4`:

```text
1 < 4
```

Push:

```text
14
```

Process `3`:

```text
4 > 3
```

Remove `4`:

```text
1
```

Push `3`:

```text
13
```

This is exactly what we want.

---

# 6. Optimal Approach

Maintain an increasing stack.

For each digit:

```text
while:
    k > 0
    AND stack is not empty
    AND stack.top() > current
```

pop the top.

Then push current digit.

After processing all digits, there may still be removals left.

Example:

```text
num = "12345"
k = 2
```

The stack is already increasing:

```text
12345
```

No opportunity existed during traversal.

But we still must remove two digits.

To minimize the number:

```text
12345
```

remove from the end:

```text
123
```

Therefore:

```text
if k > 0:
    remove last k digits
```

Finally remove leading zeroes.

---

# 7. Dry Run

```text
num = "1432219"
k = 3
```

Start:

```text
stack = ""
k = 3
```

### `1`

```text
stack = "1"
```

### `4`

```text
1 < 4
```

```text
stack = "14"
```

### `3`

```text
4 > 3
```

Pop `4`.

```text
stack = "1"
k = 2
```

Push `3`:

```text
stack = "13"
```

### `2`

```text
3 > 2
```

Pop:

```text
stack = "1"
k = 1
```

Push:

```text
"12"
```

### `2`

```text
2 > 2 ❌
```

Push:

```text
"122"
```

### `1`

```text
2 > 1
```

Pop:

```text
"12"
k = 0
```

Push:

```text
"121"
```

### `9`

```text
"1219"
```

Final:

```text
1219
```

---

# 8. Optimal Java Code

```java
class Solution {

    public String removeKdigits(String num, int k) {

        StringBuilder stack = new StringBuilder();

        for (char digit : num.toCharArray()) {

            while (k > 0
                    && stack.length() > 0
                    && stack.charAt(stack.length() - 1) > digit) {

                stack.deleteCharAt(stack.length() - 1);
                k--;
            }

            stack.append(digit);
        }

        // If removals are still left,
        // remove from the end.
        while (k > 0 && stack.length() > 0) {

            stack.deleteCharAt(stack.length() - 1);
            k--;
        }

        // Remove leading zeroes
        int start = 0;

        while (start < stack.length()
                && stack.charAt(start) == '0') {

            start++;
        }

        String result = stack.substring(start);

        return result.isEmpty() ? "0" : result;
    }
}
```

---

# 9. Complexity

```text
Time:  O(n)

Space: O(n)
```

Why is it `O(n)` despite the `while` loop?

Because every digit can be removed at most once.

Therefore:

```text
n pushes
+
n pops
=
O(n)
```

---

# 10. Common Mistakes

### Mistake 1 — Always removing the largest digit

That's not the correct rule.

We remove a previous digit when:

```text
previous > current
```

because the larger digit appears before a smaller digit.

---

### Mistake 2 — Forgetting remaining `k`

Example:

```text
12345
k = 2
```

No previous digit is greater than the current one.

But we still have:

```text
k = 2
```

So remove from the end:

```text
123
```

---

### Mistake 3 — Forgetting leading zeroes

Example:

```text
num = "10200"
k = 1
```

Result after removal could be:

```text
0200
```

But the correct representation is:

```text
200
```

---

### Mistake 4 — Returning empty string

If everything gets removed:

```text
num = "10"
k = 2
```

return:

```text
"0"
```

not:

```text
""
```

---

# 11. Interview Explanation

Say:

> "This is a greedy problem that can be solved with a monotonic increasing stack. While scanning from left to right, whenever the previous digit is greater than the current digit, removing that previous digit makes the resulting number smaller, so I pop it while I still have removals available. After processing all digits, if removals remain, I remove digits from the end. Finally I remove leading zeroes and return zero if the result is empty."

---

# 12. Four Problems — One Mental Model

These four problems look completely different:

```text
Decode String
Evaluate RPN
Remove Adjacent Duplicates
Remove K Digits
```

But their Stack logic is different in only one important way:

| Problem             | What Stack Stores       |
| ------------------- | ----------------------- |
| Decode String       | Previous string + count |
| RPN                 | Operands                |
| Adjacent Duplicates | Surviving characters    |
| Remove K Digits     | Candidate digits        |

---

# 13. Pattern Recognition Cheat Sheet

### Nested Structure

```text
3[a2[c]]
```

Think:

```text
Stack
```

---

### Expression Evaluation

```text
2 1 + 3 *
```

Think:

```text
Operand Stack
```

---

### Remove Previous Character

```text
abbaca
```

Think:

```text
Character Stack
```

---

### Make Number Smaller

```text
1432219
```

Think:

```text
Greedy + Monotonic Increasing Stack
```

---

# 14. Stack Templates

## Decode String

```text
number
   ↓
[
   ↓
save state
   ↓
decode
   ↓
]
   ↓
restore state
```

---

## RPN

```java
if number:
    push

if operator:
    b = pop
    a = pop
    result = a operator b
    push(result)
```

---

## Adjacent Duplicates

```java
if stack.top() == current:
    pop
else:
    push
```

---

## Remove K Digits

```java
while (k > 0
       && stack not empty
       && stack.top() > current) {

    pop();
    k--;
}

push(current);
```

---

# 15. One-Minute Revision

```text
Decode String
    ↓
Nested structure
    ↓
Two Stacks
    ↓
count + previous string
```

```text
RPN
    ↓
Expression evaluation
    ↓
Operand Stack
    ↓
pop b
pop a
a operator b
```

```text
Remove Adjacent Duplicates
    ↓
Current conflicts with previous survivor
    ↓
Character Stack
    ↓
Same → pop
Different → push
```

```text
Remove K Digits
    ↓
Make number smallest
    ↓
Greedy
    ↓
Increasing Stack
    ↓
previous > current → remove previous
```

---

# 16. Golden Rules 🔥

### Rule 1

> **Nested state → Stack**

### Rule 2

> **Expression operands → Stack**

### Rule 3

> **Remove previous element based on current → Stack**

### Rule 4

> **Make a sequence lexicographically/numerically smaller → Think Greedy + Monotonic Stack**

### Rule 5

> **If every element is pushed and popped at most once → Usually O(n)**

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

⬜ 10. Largest Rectangle in Histogram
⬜ 11. Asteroid Collision
⬜ 12. Backspace String Compare
⬜ 13. Implement Stack using Queues
⬜ 14. Simplify Path
⬜ 15. Basic Calculator II
```

-