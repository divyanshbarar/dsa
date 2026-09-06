# HashMap Pattern — Part 5

# Grouping Pattern

> **Core idea: `Key → List of Values`**

So far we learned:

```text
Part 2

Element → Frequency
```

```text
Part 3

Value → Index
```

```text
Part 4

Character → Character
```

Now we introduce a very important structure:

```text
Key → List<Value>
```

This is the **Grouping Pattern**.

---

# 1. What Is The Grouping Pattern?

Suppose we have:

```text
apple
banana
ant
ball
cat
```

We want to group words by their first character.

Result:

```text
a → [apple, ant]

b → [banana, ball]

c → [cat]
```

The HashMap is no longer:

```text
Map<Key, Value>
```

It becomes:

```text
Map<Key, List<Value>>
```

In Java:

```java
Map<Character, List<String>> map
        = new HashMap<>();
```

---

# 2. Recognition Clues

When you see:

```text
Group

Categorize

Bucket

Collect

Same property

Same signature

Same pattern
```

Think:

```text
HashMap

+

List
```

---

# 3. Master Template

The most important Java template is:

```java
map.computeIfAbsent(
    key,
    k -> new ArrayList<>()
).add(value);
```

Read this as:

> "If the key doesn't exist, create a new list. Then add the value to that list."

---

# 4. Understand computeIfAbsent()

Suppose:

```java
Map<Character, List<String>> map =
        new HashMap<>();
```

We process:

```text
apple
```

Key:

```text
a
```

Initially:

```text
{}
```

Execute:

```java
map.computeIfAbsent(
    'a',
    k -> new ArrayList<>()
).add("apple");
```

Now:

```text
a → [apple]
```

Next:

```text
ant
```

The key `a` already exists.

So Java retrieves the existing list:

```text
a → [apple]
```

Then:

```text
ant
```

is added.

Result:

```text
a → [apple, ant]
```

---

# 5. Without computeIfAbsent()

You can also write:

```java
if (!map.containsKey(key)) {

    map.put(
        key,
        new ArrayList<>()
    );
}

map.get(key).add(value);
```

This works.

But:

```java
computeIfAbsent()
```

is much cleaner.

---

# 6. The Most Important Interview Pattern

Remember:

```text
Map<Key, List<Value>>
```

Examples:

```text
Character → Words
```

```text
Anagram Signature → Words
```

```text
Department → Employees
```

```text
City → Users
```

```text
Course → Students
```

This pattern appears far beyond LeetCode.

---

# Problem 1 — Group Anagrams

## LeetCode 49

---

# Problem Explanation

Given:

```text
["eat", "tea", "tan", "ate", "nat", "bat"]
```

Group words that are anagrams.

Expected result:

```text
[
    ["eat", "tea", "ate"],
    ["tan", "nat"],
    ["bat"]
]
```

---

# First Understand The Problem

What makes two strings anagrams?

They have exactly the same characters with the same frequencies.

For example:

```text
eat
```

and

```text
tea
```

Both contain:

```text
a → 1
e → 1
t → 1
```

Therefore:

```text
eat
tea
ate
```

belong to the same group.

---

# What Is The Real Problem?

We need to find a common:

```text
KEY
```

for all anagrams.

Then:

```text
same key
    ↓
same group
```

This is the heart of the problem.

---

# Approach 1 — Brute Force

For every word:

1. Compare it with every other word.
2. Determine whether they are anagrams.
3. Put matching words into the same group.

If there are `n` strings and each string has length `k`, this becomes expensive.

Approximate complexity:

```text
O(n² × k)
```

depending on how the anagram comparison is implemented.

---

# Why Brute Force Is Bad

Suppose:

```text
n = 10,000
```

Comparing every pair means roughly:

```text
10,000 × 10,000
```

comparisons.

We need something better.

---

# Approach 2 — Sort Each String

This is the first major optimization.

Take:

```text
eat
```

Sort:

```text
aet
```

Take:

```text
tea
```

Sort:

```text
aet
```

Take:

```text
ate
```

Sort:

```text
aet
```

Therefore:

```text
aet
```

can be our HashMap key.

---

# Example

Input:

```text
eat
tea
tan
ate
nat
bat
```

After sorting each:

```text
eat → aet
tea → aet

tan → ant
ate → aet

nat → ant

bat → abt
```

Map becomes:

```text
aet → [eat, tea, ate]

ant → [tan, nat]

abt → [bat]
```

Exactly what we need.

---

# Better Solution

```text
Sort each string

↓

Use sorted string as key

↓

Store original string in list
```

---

# Java Code — Better Approach

```java
class Solution {

    public List<List<String>> groupAnagrams(
            String[] strs
    ) {

        Map<String, List<String>> map =
                new HashMap<>();

        for (String str : strs) {

            char[] chars =
                    str.toCharArray();

            Arrays.sort(chars);

            String key =
                    new String(chars);

            map.computeIfAbsent(
                    key,
                    k -> new ArrayList<>()
            ).add(str);
        }

        return new ArrayList<>(
                map.values()
        );
    }
}
```

---

# Complexity

Let:

```text
n = number of strings

k = average string length
```

For every string we sort:

```text
O(k log k)
```

Therefore:

```text
Time = O(n × k log k)
```

Space:

```text
O(n × k)
```

for the groups and keys.

---

# Can We Do Better?

Yes.

This is where the **frequency-map idea from Part 2** comes back.

Instead of sorting:

```text
eat
```

we can count:

```text
a → 1
e → 1
t → 1
```

Then create a unique key from that frequency representation.

Because the alphabet is fixed, we can use an integer array instead of a HashMap for the character frequencies.

---

# Optimal Approach

For lowercase English letters:

```text
a-z
```

we create:

```java
int[26]
```

For:

```text
eat
```

frequency:

```text
a → 1
e → 1
t → 1
```

The frequency array becomes the signature.

Every anagram gets the same signature.

---

# Why This Is Optimal

Sorting:

```text
O(k log k)
```

Frequency counting:

```text
O(k)
```

So:

```text
O(n × k)
```

instead of:

```text
O(n × k log k)
```

---

# Optimal Java Code

```java
class Solution {

    public List<List<String>> groupAnagrams(
            String[] strs
    ) {

        Map<String, List<String>> map =
                new HashMap<>();

        for (String str : strs) {

            int[] count = new int[26];

            for (char ch : str.toCharArray()) {

                count[ch - 'a']++;
            }

            StringBuilder key =
                    new StringBuilder();

            for (int i = 0; i < 26; i++) {

                key.append('#');
                key.append(count[i]);
            }

            map.computeIfAbsent(
                    key.toString(),
                    k -> new ArrayList<>()
            ).add(str);
        }

        return new ArrayList<>(
                map.values()
        );
    }
}
```

---

# Why Add `#`?

This:

```text
1 11
```

could potentially be confused with:

```text
11 1
```

Using a delimiter:

```text
#1#11
```

makes the signature unambiguous.

---

# Dry Run — Optimal Solution

Input:

```text
["eat", "tea", "tan"]
```

---

## `eat`

Count:

```text
a → 1
e → 1
t → 1
```

Signature represents:

```text
a:1
e:1
t:1
```

Map:

```text
signature → [eat]
```

---

## `tea`

Count:

```text
a → 1
e → 1
t → 1
```

Same signature.

Map:

```text
signature → [eat, tea]
```

---

## `tan`

Count:

```text
a → 1
n → 1
t → 1
```

Different signature.

Map:

```text
signature1 → [eat, tea]

signature2 → [tan]
```

Done.

---

# What To Say In An Interview

A strong answer:

> "The key observation is that all anagrams have the same character frequency signature. I'll use that signature as the HashMap key and store all strings with the same signature in the corresponding list."

Then mention optimization:

> "A sorting-based solution takes O(n × k log k). Since the alphabet is fixed, I can count characters in O(k) and reduce the total time to O(n × k)."

That is a very good interview explanation.

---

# Complexity Comparison

| Approach            |           Time |    Space |
| ------------------- | -------------: | -------: |
| Brute Force         |      O(n² × k) |     O(n) |
| Sort + HashMap      | O(n × k log k) | O(n × k) |
| Frequency Signature |   **O(n × k)** | O(n × k) |

---

# Important Interview Lesson

Notice what happened.

We combined two patterns:

```text
HashMap

+

Frequency Counting
```

from Part 2,

with:

```text
HashMap

+

List
```

from Part 5.

This is how interview patterns combine.

---

# Problem 2 — Group Strings By First Character

This is a simpler version of the same pattern.

Given:

```text
["apple", "ant", "banana", "ball", "cat"]
```

Output:

```text
a → [apple, ant]

b → [banana, ball]

c → [cat]
```

---

# Brute Force

Maintain separate groups and repeatedly search for an existing group.

Not ideal.

---

# Optimal Idea

Use:

```text
first character

↓

List of strings
```

---

# Optimal Java Code

```java
Map<Character, List<String>> map =
        new HashMap<>();

for (String word : words) {

    char key = word.charAt(0);

    map.computeIfAbsent(
            key,
            k -> new ArrayList<>()
    ).add(word);
}
```

---

# Complexity

If total characters across all words are `N`:

```text
Time = O(N)
```

Ignoring output construction.

---

# Problem 3 — Group Numbers By Remainder

This is a useful variation.

Given:

```text
1 2 3 4 5 6 7 8
```

Group by:

```text
number % 3
```

Result:

```text
0 → [3, 6]

1 → [1, 4, 7]

2 → [2, 5, 8]
```

---

# Recognition

The interviewer doesn't necessarily say:

> "Use a HashMap."

Instead they might say:

> "Group elements that share the same property."

That is the clue.

Ask:

> **"What can I use as the grouping key?"**

---

# Optimal Java Code

```java
Map<Integer, List<Integer>> map =
        new HashMap<>();

for (int num : nums) {

    int key = num % 3;

    map.computeIfAbsent(
            key,
            k -> new ArrayList<>()
    ).add(num);
}
```

---

# General Template

This is the template you should memorize:

```java
Map<Key, List<Value>> map =
        new HashMap<>();

for (Value value : values) {

    Key key = getKey(value);

    map.computeIfAbsent(
            key,
            k -> new ArrayList<>()
    ).add(value);
}
```

The only difficult part is:

```text
getKey(value)
```

The interview question is often really asking:

> **"Can you identify the correct grouping key?"**

---

# Grouping Pattern Examples

| Problem                  | Grouping Key        |
| ------------------------ | ------------------- |
| Group Anagrams           | Character frequency |
| Group by First Character | First character     |
| Group by Remainder       | `num % k`           |
| Group Employees          | Department          |
| Group Students           | Course              |
| Group Words              | Signature           |
| Group Records            | Category            |

---

# Common Mistakes

## Mistake 1 — Wrong Key

For Group Anagrams, using:

```text
first character
```

doesn't identify anagrams.

Need:

```text
character signature
```

---

## Mistake 2 — Creating New List Every Time

Wrong:

```java
map.put(key, new ArrayList<>());
```

inside every iteration.

This can overwrite the previous group.

Instead:

```java
map.computeIfAbsent(
        key,
        k -> new ArrayList<>()
).add(value);
```

---

## Mistake 3 — Returning Keys Instead Of Groups

If the problem asks for groups:

```java
map.keySet()
```

is not enough.

Usually we need:

```java
map.values()
```

or:

```java
new ArrayList<>(map.values())
```

---

# Interview Recognition Drill

### Question:

"Group all anagrams together."

Think:

```text
Same frequency

↓

Same signature

↓

HashMap<Key, List<String>>
```

---

### Question:

"Group employees by department."

Think:

```text
Department

↓

List<Employee>
```

---

### Question:

"Group numbers based on remainder."

Think:

```text
num % k

↓

List<Integer>
```

---

### Question:

"Collect all values belonging to the same category."

Think:

```text
Category

↓

List<Value>
```

---

# One-Minute Revision

The core structure:

```java
Map<Key, List<Value>>
```

Most important method:

```java
computeIfAbsent()
```

Template:

```java
map.computeIfAbsent(
    key,
    k -> new ArrayList<>()
).add(value);
```

---

# The Real Interview Question

When you see:

```text
GROUP
```

don't immediately start coding.

Ask:

### Step 1

```text
What makes two elements belong
to the same group?
```

### Step 2

Create that property as:

```text
GROUP KEY
```

### Step 3

Use:

```text
HashMap<Key, List<Value>>
```

### Step 4

Add every value to its group.

---

# Golden Rule

The entire Grouping Pattern can be remembered as:

```text
Find Common Property

↓

Turn Property Into Key

↓

HashMap<Key, List<Value>>

↓

Add Value To Group
```

For **Group Anagrams**, the common property is:

```text
Character Frequency
```

For other problems, it could be:

```text
Department
Category
Remainder
First Character
Signature
```

So whenever the interviewer says:

> **"Group these elements based on some common property."**

your immediate thought should be:

```text
What is my KEY?

↓

Map<Key, List<Value>>
```

That is the pattern.

---

# Part 5 Checklist

Before moving on, you should be able to write this from memory:

```java
Map<String, List<String>> map =
        new HashMap<>();

for (String str : strs) {

    String key = getKey(str);

    map.computeIfAbsent(
            key,
            k -> new ArrayList<>()
    ).add(str);
}
```

And more importantly, you should be able to explain:

> **"The hard part is not the HashMap. The hard part is designing the correct key that represents the property shared by all elements in the same group."**

That is the interview-level understanding of the **Grouping Pattern**.
