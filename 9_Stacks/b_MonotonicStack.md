# Day 9 — Stack Pattern

# Stack Pattern — Part 2

> **Focus:** Monotonic Stack — Daily Temperatures, Next Greater Element I, Next Greater Element II
> **Goal:** Learn the most important Stack pattern for interview problems involving **next greater / smaller elements**.

---

# 1. Monotonic Stack

A **Monotonic Stack** is a stack that maintains its elements in a specific order.

Usually:

* **Decreasing Stack** → useful for **Next Greater Element**
* **Increasing Stack** → useful for **Next Smaller Element**

The biggest idea is:

> Instead of comparing an element with every element on its right/left, keep only the elements that are still capable of becoming an answer.

This converts many `O(n²)` problems into **O(n)**.

---

# 2. Pattern Recognition

Whenever you see:

```text
Next Greater
Next Smaller
Previous Greater
Previous Smaller
Warmer Day
First Greater Element
Nearest Smaller Element
Nearest Greater Element
```

Immediately think:

```text
MONOTONIC STACK
```

For example:

```text
Find the first greater element to the right
```

Naive thinking:

```text
i → i+1 → i+2 → i+3 → ...
```

This can become:

```text
O(n²)
```

Monotonic Stack thinking:

```text
Keep unresolved elements
        ↓
Current element arrives
        ↓
Resolve all elements smaller than current
        ↓
Push current
```

---

# 3. The Core Monotonic Stack Rule

## Next Greater Element

Suppose:

```text
nums = [2, 1, 4]
```

For `1`, the next greater element is `4`.

When we encounter `4`:

```text
4 > 1
```

So `4` becomes the answer for `1`.

Then:

```text
4 > 2
```

So `4` also becomes the answer for `2`.

Therefore:

```text
Stack:
[2, 1]

Current:
4

4 > 1 → pop 1 → answer = 4
4 > 2 → pop 2 → answer = 4
```

This is why the stack is extremely powerful.

---

# 4. Why Is It O(n)?

At first glance, this looks like nested loops:

```java
while (...) {
    stack.pop();
}
```

But it is still `O(n)`.

Why?

Every element:

```text
is pushed once
+
is popped at most once
```

Therefore:

```text
n pushes + n pops
= 2n operations
= O(n)
```

### Golden Rule

> **If every element is pushed and popped at most once, the monotonic stack solution is O(n).**

---

# 5. Increasing vs Decreasing Stack

This is one of the most important things to memorize.

| Problem          | Stack      |
| ---------------- | ---------- |
| Next Greater     | Decreasing |
| Previous Greater | Decreasing |
| Next Smaller     | Increasing |
| Previous Smaller | Increasing |

### Next Greater

We want to remove smaller elements:

```java
while (!stack.isEmpty() && current > stack.peek()) {
    stack.pop();
}
```

Therefore:

```text
Decreasing Stack
```

### Next Smaller

We want to remove greater elements:

```java
while (!stack.isEmpty() && current < stack.peek()) {
    stack.pop();
}
```

Therefore:

```text
Increasing Stack
```

---

# Problem 3 — Daily Temperatures

## 1. Problem Statement

You are given an array of daily temperatures.

For every day, find how many days you need to wait until a **warmer temperature**.

If there is no warmer day:

```text
0
```

### Example

```text
temperatures = [73,74,75,71,69,72,76,73]
```

Answer:

```text
[1,1,4,2,1,1,0,0]
```

Explanation:

```text
73 → 74
     ↓
     1 day

74 → 75
     ↓
     1 day

75 → 76
     ↓
     4 days

71 → 72
     ↓
     2 days

69 → 72
     ↓
     1 day
```

---

# 2. Pattern Recognition

Look at the wording:

```text
How many days until a warmer temperature?
```

This means:

```text
Next Greater Element
```

The only difference is that instead of returning the greater temperature, we need:

```text
distance between indices
```

Therefore:

```text
Next Greater
+
Distance
+
Monotonic Stack
```

---

# 3. Important Observation

We should store **indices**, not temperatures.

Why?

Suppose:

```text
temperatures = [73,74,75]
```

If we only store:

```text
73
74
75
```

we cannot calculate:

```text
number of days
```

But if we store:

```text
0
1
2
```

then:

```text
currentIndex - previousIndex
```

gives us the answer.

### Therefore:

> **If the answer requires distance or position, store indices in the stack.**

---

# 4. Approach 1 — Brute Force

For every day:

1. Look at the next day.
2. Keep moving right.
3. Stop when we find a warmer temperature.
4. Store the distance.

Example:

```text
73 74 75 71 69 72 76 73
↑
```

Search:

```text
74 > 73
```

Answer:

```text
1
```

For `75`:

```text
71 ❌
69 ❌
72 ❌
76 ✅
```

Distance:

```text
6 - 2 = 4
```

---

## Brute Force Code

```java
class Solution {

    public int[] dailyTemperatures(int[] temperatures) {

        int n = temperatures.length;
        int[] answer = new int[n];

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                if (temperatures[j] > temperatures[i]) {
                    answer[i] = j - i;
                    break;
                }
            }
        }

        return answer;
    }
}
```

---

## Complexity

```text
Time:  O(n²)
Space: O(1)
```

The problem with brute force is that for every element we may scan almost the entire remaining array.

---

# 5. Better Observation

Instead of repeatedly asking:

```text
"What is the next warmer day for me?"
```

we can process the array while keeping track of days that are still **waiting for a warmer temperature**.

Suppose:

```text
73
74
75
71
69
72
76
```

When we encounter `74`:

```text
73 is waiting
```

Since:

```text
74 > 73
```

we immediately know:

```text
answer[73] = distance to 74
```

When we encounter `72`:

```text
71 is waiting
69 is waiting
```

Since:

```text
72 > 69
72 > 71
```

we can resolve both.

This leads directly to the optimal monotonic-stack solution.

---

# 6. Optimal Approach — Monotonic Stack

We maintain a stack containing **indices of unresolved days**.

The stack maintains temperatures in decreasing order.

For:

```text
[73,74,75,71,69,72,76,73]
```

Start:

```text
stack = []
```

Process `73`:

```text
stack = [0]
```

Temperature:

```text
[73]
```

---

Process `74`.

Compare:

```text
74 > temperatures[0]
74 > 73
```

So day `0` has found its warmer day.

```text
answer[0] = 1 - 0
           = 1
```

Pop `0`.

Then push `1`.

```text
stack = [1]
```

---

Process `75`.

```text
75 > 74
```

Resolve day `1`:

```text
answer[1] = 2 - 1
           = 1
```

Push `2`.

```text
stack = [2]
```

---

Process `71`.

```text
71 < 75
```

Nothing can be resolved.

```text
stack = [2,3]
```

Temperatures:

```text
75
71
```

This is decreasing.

---

Process `69`.

```text
69 < 71
```

Push.

```text
stack = [2,3,4]
```

---

Process `72`.

Now:

```text
72 > 69
```

Resolve index `4`:

```text
answer[4] = 5 - 4
           = 1
```

Pop `4`.

Again:

```text
72 > 71
```

Resolve index `3`:

```text
answer[3] = 5 - 3
           = 2
```

Pop `3`.

But:

```text
72 < 75
```

So stop.

Push `5`.

```text
stack = [2,5]
```

---

Process `76`.

```text
76 > 72
```

Resolve:

```text
answer[5] = 6 - 5
           = 1
```

Then:

```text
76 > 75
```

Resolve:

```text
answer[2] = 6 - 2
           = 4
```

Push `6`.

---

Process `73`.

```text
73 < 76
```

Push `7`.

The remaining elements have no warmer day.

They remain:

```text
0
```

---

# 7. Optimal Java Code

```java
import java.util.Stack;

class Solution {

    public int[] dailyTemperatures(int[] temperatures) {

        int n = temperatures.length;
        int[] answer = new int[n];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            while (!stack.isEmpty()
                    && temperatures[i] > temperatures[stack.peek()]) {

                int previousIndex = stack.pop();

                answer[previousIndex] = i - previousIndex;
            }

            stack.push(i);
        }

        return answer;
    }
}
```

---

# 8. Why Do We Use `>`?

Notice:

```java
temperatures[i] > temperatures[stack.peek()]
```

NOT:

```java
>=
```

Because the problem asks for a:

```text
WARMER
```

temperature.

For example:

```text
[75,75,76]
```

The second `75` is not warmer than the first `75`.

So equal temperatures cannot resolve the answer.

---

# 9. Interview Explanation

If the interviewer asks:

> "Explain your approach."

Say:

> "The problem is essentially a Next Greater Element problem because for every day I need to find the first warmer temperature to its right. I maintain a monotonic decreasing stack containing indices of days that are still waiting for a warmer temperature. When the current temperature is greater than the temperature at the stack top, the current day is the next warmer day for that index, so I pop it and store the index difference. Each index is pushed and popped at most once, so the solution is O(n)."

That's a very strong interview explanation.

---

# 10. Complexity

```text
Time:  O(n)

Space: O(n)
```

Why time is `O(n)`:

```text
Every index:
    pushed once
    popped at most once
```

---

# 11. Common Mistakes

### Mistake 1 — Store temperatures instead of indices

Wrong:

```java
Stack<Integer> stack;
stack.push(temperatures[i]);
```

You cannot easily calculate:

```text
number of days
```

Better:

```java
stack.push(i);
```

---

### Mistake 2 — Using `>=`

Wrong:

```java
temperatures[i] >= temperatures[stack.peek()]
```

We need strictly warmer.

Correct:

```java
temperatures[i] > temperatures[stack.peek()]
```

---

### Mistake 3 — Forgetting the distance

The answer is not:

```java
temperatures[i]
```

It is:

```java
i - previousIndex
```

---

### Mistake 4 — Thinking the `while` loop makes it O(n²)

It doesn't.

Every index can only be popped once.

Therefore:

```text
O(n)
```

---

# Problem 4 — Next Greater Element I

## 1. Problem Statement

You are given two arrays:

```text
nums1
nums2
```

`nums1` is a subset of `nums2`.

For every element in `nums1`, find its **next greater element in nums2**.

The next greater element means:

> The first element to the right that is greater than the current element.

If none exists:

```text
-1
```

---

# 2. Example

```text
nums1 = [4,1,2]

nums2 = [1,3,4,2]
```

For `4`:

```text
4 → no greater element
```

Answer:

```text
-1
```

For `1`:

```text
1 → 3
```

Answer:

```text
3
```

For `2`:

```text
2 → nothing
```

Answer:

```text
-1
```

Final:

```text
[-1,3,-1]
```

---

# 3. Pattern Recognition

The important words are:

```text
next greater element
```

Immediately think:

```text
Monotonic Stack
```

But there is an additional clue:

```text
nums1 is a subset of nums2
```

That means:

```text
Calculate answers for nums2
        ↓
Store answers
        ↓
Query answers for nums1
```

Therefore:

```text
Monotonic Stack
+
HashMap
```

---

# 4. Approach 1 — Brute Force

For every element in `nums1`:

1. Find that element in `nums2`.
2. Scan to its right.
3. Find the first greater element.
4. Store it.

Example:

```text
nums2 = [1,3,4,2]

For 1:
    3 > 1
    answer = 3

For 4:
    2 < 4
    no greater
    answer = -1
```

---

## Brute Force Code

```java
class Solution {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        int[] result = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {

            int current = nums1[i];
            int index = -1;

            // Find current element in nums2
            for (int j = 0; j < nums2.length; j++) {

                if (nums2[j] == current) {
                    index = j;
                    break;
                }
            }

            result[i] = -1;

            // Find next greater element
            for (int j = index + 1; j < nums2.length; j++) {

                if (nums2[j] > current) {
                    result[i] = nums2[j];
                    break;
                }
            }
        }

        return result;
    }
}
```

---

# 5. Complexity

Let:

```text
m = nums1.length
n = nums2.length
```

Worst case:

```text
Time: O(m × n)
Space: O(1)
```

We can do much better.

---

# 6. Better Observation

Instead of solving the problem separately for every `nums1` element, solve the **entire `nums2` array once**.

While processing `nums2`:

```text
Whenever current > stack.top
```

the current element is the next greater element for the stack top.

So we can create:

```text
value → next greater value
```

using a `HashMap`.

Example:

```text
nums2 = [1,3,4,2]
```

Process:

```text
1
```

Stack:

```text
[1]
```

Process `3`:

```text
3 > 1
```

Therefore:

```text
map[1] = 3
```

Process `4`:

```text
4 > 3
```

Therefore:

```text
map[3] = 4
```

Process `2`:

```text
2 < 4
```

Push it.

At the end:

```text
map:
1 → 3
3 → 4
4 → -1
2 → -1
```

Now answering `nums1` becomes a simple HashMap lookup.

---

# 7. Optimal Approach

### Step 1

Traverse `nums2`.

### Step 2

Maintain a decreasing stack.

### Step 3

When:

```java
current > stack.peek()
```

the current value is the next greater element for the popped value.

Store:

```text
value → next greater
```

in a HashMap.

### Step 4

After processing `nums2`, unresolved elements have:

```text
-1
```

### Step 5

Traverse `nums1` and get answers from the HashMap.

---

# 8. Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Solution {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        Map<Integer, Integer> map = new HashMap<>();

        Stack<Integer> stack = new Stack<>();

        for (int num : nums2) {

            while (!stack.isEmpty()
                    && num > stack.peek()) {

                map.put(stack.pop(), num);
            }

            stack.push(num);
        }

        // Remaining elements don't have a greater element
        while (!stack.isEmpty()) {
            map.put(stack.pop(), -1);
        }

        int[] result = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {

            result[i] = map.get(nums1[i]);
        }

        return result;
    }
}
```

---

# 9. Dry Run

```text
nums2 = [1,3,4,2]
```

### `1`

```text
stack = [1]
```

---

### `3`

```text
3 > 1
```

Therefore:

```text
map[1] = 3
```

Stack:

```text
[3]
```

---

### `4`

```text
4 > 3
```

Therefore:

```text
map[3] = 4
```

Stack:

```text
[4]
```

---

### `2`

```text
2 < 4
```

Push:

```text
stack = [4,2]
```

---

Remaining:

```text
4 → -1
2 → -1
```

Final map:

```text
1 → 3
3 → 4
4 → -1
2 → -1
```

Now:

```text
nums1 = [4,1,2]
```

Lookup:

```text
4 → -1
1 → 3
2 → -1
```

Answer:

```text
[-1,3,-1]
```

---

# 10. Why Do We Need HashMap?

This is an important interview question.

The stack calculates the next greater element for **every element in `nums2`**.

But the output only asks for elements from:

```text
nums1
```

So we need a fast way to retrieve:

```text
element → answer
```

That's exactly what a HashMap provides.

```text
HashMap:
value → next greater value
```

Lookup:

```text
O(1) average
```

---

# 11. Interview Explanation

Say:

> "Since nums1 is a subset of nums2, instead of solving the next greater problem independently for every element of nums1, I first calculate the next greater element for every element in nums2 using a decreasing monotonic stack. Whenever the current element is greater than the stack top, I know it is the next greater element for that popped value, so I store that relationship in a HashMap. Finally, I use the HashMap to answer each element of nums1 in constant average time."

---

# 12. Complexity

Let:

```text
n = nums2.length
m = nums1.length
```

```text
Time:  O(n + m)

Space: O(n)
```

Why?

Every element in `nums2`:

```text
pushed once
popped at most once
```

Then `nums1` is traversed once.

---

# 13. Important Constraint

The simple:

```text
value → answer
```

HashMap approach relies on the problem's elements being **distinct**.

For the standard Next Greater Element I problem, this is guaranteed.

If duplicates were allowed, mapping only by value would not always be enough; we'd need to reason using indices/occurrences.

---

# Problem 5 — Next Greater Element II

## 1. Problem Statement

Now the array is **circular**.

For every element, find the next greater element while considering that after the last element, we come back to the first.

If there is no greater element:

```text
-1
```

---

# 2. Example

```text
nums = [1,2,1]
```

For first `1`:

```text
1 → 2
```

Answer:

```text
2
```

For `2`:

```text
No greater element
```

Answer:

```text
-1
```

For the last `1`:

After reaching the end:

```text
1 → back to 1 → 2
```

Therefore:

```text
2
```

Final:

```text
[2,-1,2]
```

---

# 3. Pattern Recognition

Keywords:

```text
Next Greater
Circular Array
```

Immediately think:

```text
Monotonic Stack
+
Circular Traversal
```

The main difficulty is:

```text
What happens after the last element?
```

---

# 4. Approach 1 — Brute Force

For every index:

1. Move right.
2. If we reach the end, wrap around.
3. Search up to `n - 1` elements.
4. Stop at the first greater element.

Example:

```text
[1,2,1]
```

For last `1`:

```text
index 2
↓
wrap
↓
index 0 → 1
index 1 → 2
```

So answer is:

```text
2
```

---

## Brute Force Code

```java
class Solution {

    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {

            result[i] = -1;

            for (int j = 1; j < n; j++) {

                int index = (i + j) % n;

                if (nums[index] > nums[i]) {
                    result[i] = nums[index];
                    break;
                }
            }
        }

        return result;
    }
}
```

---

# 5. Complexity

```text
Time:  O(n²)
Space: O(1)
```

Again, we can use a monotonic stack.

---

# 6. Better Observation

The only new difficulty compared with normal Next Greater Element is:

```text
CIRCULAR ARRAY
```

Instead of physically creating:

```text
[1,2,1,1,2,1]
```

we can simulate it.

Use:

```java
i % n
```

For:

```text
i = 0 → 0 % 3 = 0
i = 1 → 1 % 3 = 1
i = 2 → 2 % 3 = 2
i = 3 → 3 % 3 = 0
i = 4 → 4 % 3 = 1
i = 5 → 5 % 3 = 2
```

So:

```text
0 1 2 0 1 2
```

This gives us two passes without creating a second array.

---

# 7. Optimal Approach

We simulate the array twice:

```text
2 × n
```

iterations.

At each iteration:

```java
currentIndex = i % n;
```

The stack stores indices from the **first pass** that still need a greater element.

### Important

We only push indices during the first pass:

```java
if (i < n) {
    stack.push(currentIndex);
}
```

Why?

The second pass exists only to resolve existing elements.

We don't want to add duplicates to the stack.

---

# 8. Optimal Java Code

```java
import java.util.Arrays;
import java.util.Stack;

class Solution {

    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;

        int[] result = new int[n];

        Arrays.fill(result, -1);

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < 2 * n; i++) {

            int currentIndex = i % n;

            while (!stack.isEmpty()
                    && nums[currentIndex] > nums[stack.peek()]) {

                int index = stack.pop();

                result[index] = nums[currentIndex];
            }

            // Push only during the first pass
            if (i < n) {
                stack.push(currentIndex);
            }
        }

        return result;
    }
}
```

---

# 9. Dry Run

```text
nums = [1,2,1]
```

Initially:

```text
result = [-1,-1,-1]
stack = []
```

---

### i = 0

```text
currentIndex = 0 % 3 = 0
current = 1
```

Push:

```text
stack = [0]
```

---

### i = 1

```text
currentIndex = 1
current = 2
```

Compare:

```text
2 > 1
```

Resolve index `0`:

```text
result[0] = 2
```

Push `1`.

```text
stack = [1]
```

---

### i = 2

```text
currentIndex = 2
current = 1
```

```text
1 > 2 ❌
```

Push `2`.

```text
stack = [1,2]
```

---

Now the first pass is complete.

We have:

```text
i = 3
```

---

### i = 3

```text
currentIndex = 3 % 3 = 0
current = 1
```

Compare:

```text
1 > 1 ❌
```

Do not push.

---

### i = 4

```text
currentIndex = 4 % 3 = 1
current = 2
```

Compare with stack top:

```text
2 > 1
```

Resolve index `2`:

```text
result[2] = 2
```

Stack:

```text
[1]
```

Now:

```text
2 > 2 ❌
```

Stop.

---

### i = 5

```text
currentIndex = 2
current = 1
```

No resolution.

Final:

```text
[2,-1,2]
```

---

# 10. Why Two Passes?

Consider:

```text
[5,1,2]
```

For the last `2`, the next greater element is:

```text
5
```

But `5` appears **before** it in the normal array.

Because the array is circular:

```text
5 → 1 → 2
↑       ↓
└───────┘
```

So the second pass allows elements near the end to look at elements near the beginning.

---

# 11. Why `i % n`?

Instead of creating:

```text
[5,1,2,5,1,2]
```

we use:

```java
i % n
```

This maps:

```text
0 → 0
1 → 1
2 → 2
3 → 0
4 → 1
5 → 2
```

So we effectively simulate:

```text
[5,1,2,5,1,2]
```

without extra memory.

---

# 12. Why Only Push During First Pass?

This:

```java
if (i < n) {
    stack.push(currentIndex);
}
```

is important.

If we pushed during both passes, we would push the same indices multiple times.

We only need:

```text
First pass → identify unresolved elements
Second pass → resolve them
```

---

# 13. Complexity

```text
Time:  O(n)

Space: O(n)
```

Even though we loop:

```text
2n
```

times, that's still:

```text
O(n)
```

And every index is pushed only once and popped at most once.

---

# 14. Interview Explanation

Say:

> "This is a Next Greater Element problem on a circular array. I use a decreasing monotonic stack and simulate two passes over the array. For each iteration I use `i % n` to wrap around to the beginning. During the first pass I push indices that are still unresolved. During the second pass I only resolve those indices without pushing new ones. Each index is pushed and popped at most once, so the overall complexity is O(n)."

---

# 15. Common Mistakes

### Mistake 1 — Only traversing once

Wrong:

```java
for (int i = 0; i < n; i++)
```

That doesn't allow the last elements to see the beginning.

Use:

```java
for (int i = 0; i < 2 * n; i++)
```

---

### Mistake 2 — Forgetting modulo

Wrong:

```java
nums[i]
```

When `i >= n`, this causes an index error.

Correct:

```java
nums[i % n]
```

---

### Mistake 3 — Pushing during both passes

Wrong:

```java
stack.push(currentIndex);
```

every iteration.

Correct:

```java
if (i < n) {
    stack.push(currentIndex);
}
```

---

### Mistake 4 — Using `>=`

We need:

```text
strictly greater
```

So:

```java
nums[currentIndex] > nums[stack.peek()]
```

---

# 16. Monotonic Stack Template

This is the template you should memorize.

## Next Greater Element — Normal Array

```java
Stack<Integer> stack = new Stack<>();

for (int i = 0; i < n; i++) {

    while (!stack.isEmpty()
            && nums[i] > nums[stack.peek()]) {

        int index = stack.pop();

        // nums[i] is the next greater element
    }

    stack.push(i);
}
```

---

# 17. Next Greater Element — Circular Array

```java
Stack<Integer> stack = new Stack<>();

for (int i = 0; i < 2 * n; i++) {

    int currentIndex = i % n;

    while (!stack.isEmpty()
            && nums[currentIndex] > nums[stack.peek()]) {

        int index = stack.pop();

        // nums[currentIndex] is the next greater element
    }

    if (i < n) {
        stack.push(currentIndex);
    }
}
```

---

# 18. Next Smaller Template

For **Next Smaller**:

```java
Stack<Integer> stack = new Stack<>();

for (int i = 0; i < n; i++) {

    while (!stack.isEmpty()
            && nums[i] < nums[stack.peek()]) {

        int index = stack.pop();

        // nums[i] is the next smaller element
    }

    stack.push(i);
}
```

---

# 19. The Most Important Monotonic Stack Rules

Memorize this table:

| Requirement      | Pattern          |
| ---------------- | ---------------- |
| Next Greater     | Decreasing Stack |
| Next Smaller     | Increasing Stack |
| Previous Greater | Decreasing Stack |
| Previous Smaller | Increasing Stack |
| Need distance    | Store indices    |
| Need position    | Store indices    |
| Circular array   | `2 * n` + `% n`  |
| Subset queries   | Stack + HashMap  |

---

# 20. One-Minute Revision

### Daily Temperatures

```text
Question:
How many days until warmer?

Pattern:
Next Greater

Stack:
Decreasing

Store:
Indices

Answer:
i - previousIndex

Complexity:
O(n)
```

---

### Next Greater Element I

```text
Question:
Next greater in nums2 for nums1 elements?

Pattern:
Next Greater + Query

Stack:
Decreasing

Extra:
HashMap

Map:
value → next greater

Complexity:
O(n + m)
```

---

### Next Greater Element II

```text
Question:
Next greater in circular array?

Pattern:
Next Greater + Circular

Stack:
Decreasing

Technique:
2n traversal

Wrap:
i % n

Complexity:
O(n)
```

---

# 21. Pattern Transformation

This is how you should think during an interview:

```text
"Next greater"
       ↓
Monotonic Stack
       ↓
Need distance?
       ↓
Store indices
       ↓
Circular?
       ↓
2n + modulo
       ↓
Subset query?
       ↓
HashMap
```

---

# 22. Golden Rule 🔥

> **Don't memorize three separate solutions. Memorize the Monotonic Stack pattern.**

The three problems are basically the same idea:

```text
Daily Temperatures
        ↓
Next Greater + Distance

Next Greater Element I
        ↓
Next Greater + HashMap

Next Greater Element II
        ↓
Next Greater + Circular Array
```

Once you understand:

```text
while (current > stack.peek())
```

and why we pop unresolved elements, a huge family of Stack problems becomes much easier.

---


