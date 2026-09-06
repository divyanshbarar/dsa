# HashMap Pattern — Part 7

# Advanced HashMap Patterns + Important Interview Problems

Now that we understand the major HashMap foundations:

```text
Part 1 → HashMap Fundamentals
Part 2 → Frequency Map
Part 3 → Value → Index Mapping
Part 4 → Character Mapping
Part 5 → Grouping
Part 6 → Prefix Sum + HashMap
```

we can move to **advanced HashMap problems**.

These problems are important because they don't always scream:

> "Use a HashMap."

Instead, you have to recognize that HashMap can help us store **previous states, frequencies, complements, mappings, or unique elements**.

---

# 1. Advanced HashMap Pattern — Previous State

The general idea is:

```text
Current element
      ↓
What information from the past do I need?
      ↓
Store that information in HashMap
      ↓
Lookup in O(1)
```

This is the common theme behind many HashMap problems.

For example:

```text
Two Sum
```

We ask:

```text
What number do I need?
```

Answer:

```text
target - current
```

So:

```text
value → index
```

---

Another example:

```text
Longest Consecutive Sequence
```

We ask:

```text
Does x - 1 exist?
Does x + 1 exist?
```

So:

```text
value → existence
```

---

Another:

```text
Longest Substring Without Repeating Characters
```

We ask:

```text
Where was this character last seen?
```

So:

```text
character → last index
```

---

# Problem 1 — Longest Consecutive Sequence

## Problem

Given an unsorted integer array, return the length of the longest consecutive sequence.

The elements in the sequence must be consecutive integers.

### Example

```text
Input:
nums = [100, 4, 200, 1, 3, 2]

Output:
4
```

The longest consecutive sequence is:

```text
1, 2, 3, 4
```

Length:

```text
4
```

---

# Pattern Recognition

The array is:

```text
UNSORTED
```

but we need:

```text
CONSECUTIVE VALUES
```

A natural solution is sorting:

```text
[100, 4, 200, 1, 3, 2]

↓ sort

[1, 2, 3, 4, 100, 200]
```

But sorting costs:

```text
O(n log n)
```

The optimal solution uses a HashSet.

Think:

> Can I check whether a number exists in O(1)?

Yes:

```java
Set<Integer> set = new HashSet<>();
```

---

# Approach 1 — Brute Force

For every number, try to find:

```text
num + 1
num + 2
num + 3
...
```

using a linear search.

### Complexity

```text
Time: O(n²)
Space: O(1)
```

This is not practical for large input.

---

# Approach 2 — Sorting

Sort the array first.

Example:

```text
[100, 4, 200, 1, 3, 2]

↓
[1, 2, 3, 4, 100, 200]
```

Now traverse the array and count consecutive numbers.

### Code

```java
import java.util.Arrays;

class Solution {

    public int longestConsecutive(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int longest = 1;
        int current = 1;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] == nums[i - 1] + 1) {

                current++;

            } else if (nums[i] != nums[i - 1]) {

                current = 1;
            }

            longest = Math.max(longest, current);
        }

        return longest;
    }
}
```

### Complexity

```text
Time: O(n log n)
Space: O(1)
```

---

# Approach 3 — Optimal HashSet

Put every number into a HashSet.

Then for every number `num`, ask:

```text
Does num - 1 exist?
```

If yes:

```text
num is NOT the beginning of a sequence.
```

If no:

```text
num IS the beginning.
```

Then start counting:

```text
num
num + 1
num + 2
num + 3
...
```

---

# Why Check `num - 1`?

Suppose:

```text
1, 2, 3, 4
```

If we are at:

```text
2
```

then:

```text
1 exists
```

So `2` is not the beginning.

Same for:

```text
3
4
```

Only:

```text
1
```

has:

```text
0 does not exist
```

Therefore we start counting only from `1`.

This prevents unnecessary repeated work.

---

# Optimal Java Code

```java
import java.util.HashSet;
import java.util.Set;

class Solution {

    public int longestConsecutive(int[] nums) {

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {

            // Start only if num is the beginning
            // of a sequence.
            if (!set.contains(num - 1)) {

                int current = num;
                int length = 1;

                while (set.contains(current + 1)) {

                    current++;
                    length++;
                }

                longest = Math.max(longest, length);
            }
        }

        return longest;
    }
}
```

---

# Dry Run

```text
nums = [100, 4, 200, 1, 3, 2]
```

HashSet:

```text
{100, 4, 200, 1, 3, 2}
```

Check `1`:

```text
0 doesn't exist
```

So start sequence:

```text
1 → 2 → 3 → 4
```

Length:

```text
4
```

Check `2`:

```text
1 exists
```

Don't start.

Check `3`:

```text
2 exists
```

Don't start.

Check `4`:

```text
3 exists
```

Don't start.

Check `100`:

```text
99 doesn't exist
```

Start:

```text
100
```

Length:

```text
1
```

Check `200`:

```text
199 doesn't exist
```

Length:

```text
1
```

Final:

```text
4
```

---

# Interview Explanation

Say:

> "I'll use a HashSet to get O(1) average lookup. For every number, I'll start a sequence only when `num - 1` doesn't exist, meaning the current number is the beginning of a consecutive sequence. Then I'll keep checking `num + 1`, `num + 2`, and so on. This ensures each sequence is traversed only from its starting point."

---

# Complexity

```text
Time: O(n)
Space: O(n)
```

Why is it O(n)?

Although there is a `while` loop inside the `for` loop, every number belongs to a sequence that is traversed only when its smallest element is found.

Therefore the total work remains approximately:

```text
O(n)
```

---

# Problem 2 — Longest Substring Without Repeating Characters

## Problem

Given a string `s`, find the length of the longest substring without repeating characters.

### Example

```text
Input:
s = "abcabcbb"

Output:
3
```

The longest substring is:

```text
"abc"
```

Length:

```text
3
```

---

# Pattern Recognition

Look for:

```text
substring
+
longest
+
no repeating characters
```

Think:

```text
Sliding Window + HashMap
```

HashMap stores:

```text
character → last index
```

This is a very important combination:

> **Sliding Window + HashMap**

---

# Brute Force

Generate every substring.

For each substring, check whether all characters are unique.

### Complexity

```text
Time: O(n³)
Space: O(n)
```

We can improve this by using a Set.

---

# Better Approach — Sliding Window + HashSet

Maintain a window:

```text
[left ... right]
```

If a duplicate appears:

```text
remove characters from left
```

until the duplicate disappears.

### Complexity

```text
Time: O(n)
Space: O(n)
```

This is already optimal asymptotically.

But HashMap allows us to jump `left` directly.

---

# Optimal Approach — Sliding Window + HashMap

Store:

```text
character → last index
```

Suppose:

```text
s = "abcba"
```

When we reach the second:

```text
b
```

we know the previous `b` was at index `1`.

Instead of moving `left` one step at a time, we can jump:

```text
left = previousIndex + 1
```

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int lengthOfLongestSubstring(String s) {

        Map<Character, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            if (map.containsKey(ch)) {

                left = Math.max(left, map.get(ch) + 1);
            }

            map.put(ch, right);

            maxLength = Math.max(
                maxLength,
                right - left + 1
            );
        }

        return maxLength;
    }
}
```

---

# Why `Math.max()` for Left?

This is a very important detail.

Consider:

```text
s = "abba"
```

When we process the second `a`, its previous index is:

```text
0
```

But `left` may already be:

```text
2
```

We must never move `left` backward.

Therefore:

```java
left = Math.max(left, map.get(ch) + 1);
```

is safer than:

```java
left = map.get(ch) + 1;
```

---

# Dry Run

```text
s = "abcabcbb"
```

Initially:

```text
left = 0
maxLength = 0
```

### `a`

```text
window = "a"
length = 1
```

### `b`

```text
window = "ab"
length = 2
```

### `c`

```text
window = "abc"
length = 3
```

### `a`

Previous `a` was at index `0`.

Move:

```text
left = 1
```

Window:

```text
"bca"
```

Length:

```text
3
```

### `b`

Previous `b` was at index `1`.

Move:

```text
left = 2
```

Window:

```text
"cab"
```

Length:

```text
3
```

Final:

```text
3
```

---

# Interview Explanation

Say:

> "I'll maintain a sliding window containing unique characters. The HashMap stores the last index of each character. When I see a duplicate, I can move the left pointer directly to one position after its previous occurrence. I use Math.max so the left pointer never moves backward."

---

# Complexity

```text
Time: O(n)
Space: O(min(n, character-set-size))
```

---

# Problem 3 — Minimum Window Substring

## Problem

Given strings `s` and `t`, find the minimum window in `s` that contains all characters of `t`.

### Example

```text
Input:

s = "ADOBECODEBANC"
t = "ABC"

Output:

"BANC"
```

---

# Pattern Recognition

Look for:

```text
minimum window
+
contains required characters
```

Think:

```text
Sliding Window + HashMap
```

This is one of the most important advanced sliding-window problems.

---

# Core Idea

We need to know:

```text
How many times does each character of t occur?
```

So create:

```text
requiredFrequency
```

Example:

```text
t = "AABC"
```

Map:

```text
A → 2
B → 1
C → 1
```

Then expand the right side until the current window satisfies all requirements.

Once valid:

```text
Try shrinking from the left.
```

This gives the minimum window.

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public String minWindow(String s, String t) {

        if (s.length() < t.length()) {
            return "";
        }

        Map<Character, Integer> required = new HashMap<>();

        for (char ch : t.toCharArray()) {
            required.put(
                ch,
                required.getOrDefault(ch, 0) + 1
            );
        }

        Map<Character, Integer> window = new HashMap<>();

        int left = 0;

        int formed = 0;
        int requiredCharacters = required.size();

        int minLength = Integer.MAX_VALUE;
        int minStart = 0;

        for (int right = 0; right < s.length(); right++) {

            char ch = s.charAt(right);

            window.put(
                ch,
                window.getOrDefault(ch, 0) + 1
            );

            if (required.containsKey(ch)
                    && window.get(ch).intValue()
                    == required.get(ch).intValue()) {

                formed++;
            }

            while (left <= right && formed == requiredCharacters) {

                if (right - left + 1 < minLength) {

                    minLength = right - left + 1;
                    minStart = left;
                }

                char leftChar = s.charAt(left);

                window.put(
                    leftChar,
                    window.get(leftChar) - 1
                );

                if (required.containsKey(leftChar)
                        && window.get(leftChar)
                        < required.get(leftChar)) {

                    formed--;
                }

                left++;
            }
        }

        if (minLength == Integer.MAX_VALUE) {
            return "";
        }

        return s.substring(
            minStart,
            minStart + minLength
        );
    }
}
```

---

# Complexity

```text
Time: O(n)
Space: O(k)
```

where `k` is the number of distinct characters involved.

---

# Problem 4 — Isomorphic Strings

## Problem

Two strings are isomorphic if characters in the first string can be replaced to get the second string while preserving the order.

Example:

```text
Input:

s = "egg"
t = "add"

Output:
true
```

Mapping:

```text
e → a
g → d
```

---

# Pattern Recognition

This is:

```text
Character Mapping
+
One-to-One Relationship
```

We covered the basic version earlier.

The key requirement is:

> Two different characters cannot map to the same character.

For example:

```text
s = "foo"
t = "bar"
```

`o` would need to map to both:

```text
a
r
```

which is impossible.

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public boolean isIsomorphic(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Character> sToT = new HashMap<>();
        Map<Character, Character> tToS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {

            char a = s.charAt(i);
            char b = t.charAt(i);

            if (sToT.containsKey(a)
                    && sToT.get(a) != b) {

                return false;
            }

            if (tToS.containsKey(b)
                    && tToS.get(b) != a) {

                return false;
            }

            sToT.put(a, b);
            tToS.put(b, a);
        }

        return true;
    }
}
```

---

# Complexity

```text
Time: O(n)
Space: O(k)
```

---

# Problem 5 — First Unique Character in a String

## Problem

Given a string `s`, find the index of the first non-repeating character.

If none exists, return `-1`.

### Example

```text
Input:
s = "leetcode"

Output:
0
```

Because:

```text
l
```

appears only once.

---

# Pattern Recognition

Look for:

```text
first
+
unique/non-repeating
```

Think:

```text
Frequency Map
+
Second Traversal
```

---

# Optimal Approach

### Step 1

Count frequency of every character.

### Step 2

Traverse again.

Return the first character whose frequency is:

```text
1
```

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public int firstUniqChar(String s) {

        Map<Character, Integer> frequency = new HashMap<>();

        for (char ch : s.toCharArray()) {

            frequency.put(
                ch,
                frequency.getOrDefault(ch, 0) + 1
            );
        }

        for (int i = 0; i < s.length(); i++) {

            if (frequency.get(s.charAt(i)) == 1) {
                return i;
            }
        }

        return -1;
    }
}
```

---

# Complexity

```text
Time: O(n)
Space: O(k)
```

---

# Problem 6 — Find All Anagrams in a String

## Problem

Given strings `s` and `p`, find all start indices of `p`'s anagrams in `s`.

### Example

```text
Input:

s = "cbaebabacd"
p = "abc"

Output:

[0, 6]
```

Because:

```text
"cba"
```

is an anagram of:

```text
"abc"
```

and:

```text
"bac"
```

is also an anagram.

---

# Pattern Recognition

Look for:

```text
anagram
+
substring/window
```

Think:

```text
Sliding Window
+
Frequency Map
```

This is another very important combination.

---

# Core Idea

The window size must always equal:

```text
p.length()
```

We maintain character frequencies inside the current window.

If:

```text
window frequency == pattern frequency
```

then the current window is an anagram.

---

# Optimal Java Code

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {

    public List<Integer> findAnagrams(String s, String p) {

        List<Integer> result = new ArrayList<>();

        if (s.length() < p.length()) {
            return result;
        }

        Map<Character, Integer> required = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (char ch : p.toCharArray()) {

            required.put(
                ch,
                required.getOrDefault(ch, 0) + 1
            );
        }

        int left = 0;

        for (int right = 0; right < s.length(); right++) {

            char rightChar = s.charAt(right);

            window.put(
                rightChar,
                window.getOrDefault(rightChar, 0) + 1
            );

            if (right - left + 1 > p.length()) {

                char leftChar = s.charAt(left);

                window.put(
                    leftChar,
                    window.get(leftChar) - 1
                );

                if (window.get(leftChar) == 0) {
                    window.remove(leftChar);
                }

                left++;
            }

            if (right - left + 1 == p.length()
                    && window.equals(required)) {

                result.add(left);
            }
        }

        return result;
    }
}
```

---

# Complexity

```text
Time: O(n)
Space: O(k)
```

---

# Problem 7 — Ransom Note

## Problem

Given two strings `ransomNote` and `magazine`, determine whether the ransom note can be constructed using the letters from the magazine.

Each letter from the magazine can be used only once.

### Example

```text
Input:

ransomNote = "a"
magazine = "b"

Output:
false
```

---

# Pattern Recognition

Look for:

```text
characters
+
frequency
+
availability
```

Think:

```text
Frequency Map
```

---

# Optimal Java Code

```java
import java.util.HashMap;
import java.util.Map;

class Solution {

    public boolean canConstruct(
        String ransomNote,
        String magazine
    ) {

        Map<Character, Integer> map = new HashMap<>();

        for (char ch : magazine.toCharArray()) {

            map.put(
                ch,
                map.getOrDefault(ch, 0) + 1
            );
        }

        for (char ch : ransomNote.toCharArray()) {

            if (!map.containsKey(ch)
                    || map.get(ch) == 0) {

                return false;
            }

            map.put(ch, map.get(ch) - 1);
        }

        return true;
    }
}
```

---

# Complexity

```text
Time: O(n + m)
Space: O(k)
```

---

# Problem 8 — Jewels and Stones

## Problem

You are given:

```text
jewels
stones
```

Each character in `jewels` represents a jewel.

Return how many stones are jewels.

### Example

```text
Input:

jewels = "aA"
stones = "aAAbbbb"

Output:
3
```

---

# Pattern Recognition

This is simply:

```text
Fast lookup
```

Put jewels into a HashSet.

Then check every stone.

---

# Optimal Java Code

```java
import java.util.HashSet;
import java.util.Set;

class Solution {

    public int numJewelsInStones(
        String jewels,
        String stones
    ) {

        Set<Character> set = new HashSet<>();

        for (char ch : jewels.toCharArray()) {
            set.add(ch);
        }

        int count = 0;

        for (char ch : stones.toCharArray()) {

            if (set.contains(ch)) {
                count++;
            }
        }

        return count;
    }
}
```

---

# Complexity

```text
Time: O(n + m)
Space: O(k)
```

---

# HashMap / HashSet Pattern Recognition

At this point, you should start recognizing the following signals.

---

## Signal 1 — "Have I Seen This Before?"

Use:

```text
HashSet
```

Example:

```text
Contains Duplicate
Longest Consecutive Sequence
Jewels and Stones
```

---

## Signal 2 — "How Many Times?"

Use:

```text
HashMap
```

Example:

```text
Valid Anagram
Majority Element
First Unique Character
Ransom Note
```

---

## Signal 3 — "Where Did I See This?"

Use:

```text
HashMap:
value → index
```

Example:

```text
Two Sum
Longest Substring Without Repeating Characters
```

---

## Signal 4 — "What Do I Need?"

Use:

```text
HashMap:
value → information
```

Example:

```text
Two Sum
```

Need:

```text
target - current
```

---

## Signal 5 — "Group These Together"

Use:

```text
HashMap:
key → List
```

Example:

```text
Group Anagrams
```

---

## Signal 6 — "Subarray Sum"

Think:

```text
Prefix Sum + HashMap
```

---

## Signal 7 — "Substring + Longest/Minimum"

Think:

```text
Sliding Window + HashMap
```

---

# Most Important Advanced Combinations

| Problem Type           | Main Pattern             | HashMap Stores       |
| ---------------------- | ------------------------ | -------------------- |
| Two Sum                | HashMap                  | value → index        |
| Group Anagrams         | HashMap                  | key → list           |
| Subarray Sum K         | Prefix Sum + HashMap     | prefix → frequency   |
| Longest Subarray K     | Prefix Sum + HashMap     | prefix → first index |
| Longest Substring      | Sliding Window + HashMap | char → last index    |
| Minimum Window         | Sliding Window + HashMap | char → frequency     |
| Find Anagrams          | Sliding Window + HashMap | char → frequency     |
| Isomorphic Strings     | Mapping                  | char → char          |
| First Unique Character | Frequency                | char → count         |
| Longest Consecutive    | HashSet                  | value → existence    |
| Jewels and Stones      | HashSet                  | value → existence    |

---

# Interview Decision Tree

When you see a new problem:

```text
                HASHMAP / HASHSET
                       |
          ┌────────────┼────────────┐
          ↓            ↓            ↓
       Frequency     Lookup       Mapping
          |            |            |
          ↓            ↓            ↓
       count       exists?       A → B
          |
          ↓
    HashMap Frequency
```

For subarrays:

```text
             SUBARRAY
                 |
        ┌────────┴────────┐
        ↓                 ↓
      SUM              OTHER
        |
   ┌────┴────┐
   ↓         ↓
 SUM = K   DIVISIBLE K
   |         |
   ↓         ↓
Prefix +   Prefix %
HashMap      K
```

For strings:

```text
             STRING
                |
       ┌────────┴─────────┐
       ↓                  ↓
   Frequency          Substring
       |                  |
       ↓                  ↓
    HashMap        Sliding Window
                         +
                      HashMap
```

---

# Common Interview Mistakes

## Mistake 1 — Using HashMap Everywhere

HashMap is powerful, but don't blindly use it.

Ask:

> What information am I storing and why?

If you only need existence:

```text
HashSet
```

is often cleaner.

---

## Mistake 2 — Storing Too Much Information

For:

```text
Longest Consecutive Sequence
```

you only need:

```text
existence
```

So:

```java
Set<Integer>
```

is enough.

---

## Mistake 3 — Forgetting the Difference Between Count and First Index

This is one of the most important rules:

```text
COUNT
→ frequency

LONGEST
→ first occurrence
```

---

## Mistake 4 — Moving Sliding Window Left Backward

For problems like:

```text
Longest Substring Without Repeating Characters
```

use:

```java
left = Math.max(left, previousIndex + 1);
```

---

## Mistake 5 — Updating First Occurrence

For longest prefix/subarray problems:

```java
if (!map.containsKey(prefix)) {
    map.put(prefix, i);
}
```

Do not overwrite the earliest index.

---

# One-Minute Revision

Remember these HashMap questions:

```text
"How many?"
→ Frequency Map

"Have I seen it?"
→ HashSet

"Where did I see it?"
→ HashMap → index

"What do I need?"
→ Complement / required value

"Group similar things."
→ HashMap → List

"Subarray sum?"
→ Prefix Sum + HashMap

"Longest subarray?"
→ Prefix Sum + first index

"Substring + unique?"
→ Sliding Window + HashMap

"Substring + minimum?"
→ Sliding Window + Frequency Map

"Consecutive numbers?"
→ HashSet
```

---

# Golden Rule

> **HashMap is not the pattern itself. The pattern is what information you store in the HashMap.**

Always ask:

```text
What do I know now?
        ↓
What information from the past do I need?
        ↓
Can HashMap store that information?
        ↓
Can I retrieve it in O(1)?
```

If yes, HashMap is probably the right tool.

---

# HashMap Pattern Complete

You have now covered:

```text
Part 1 → HashMap Fundamentals
Part 2 → Frequency Map
Part 3 → Value → Index Mapping
Part 4 → Character Mapping
Part 5 → Grouping
Part 6 → Prefix Sum + HashMap
Part 7 → Advanced HashMap Patterns
```

## Important Problems To Master

Before moving on, make sure you can solve these without looking at the solution:

```text
1. Two Sum
2. Valid Anagram
3. Group Anagrams
4. Majority Element
5. Top K Frequent Elements
6. Longest Consecutive Sequence
7. Subarray Sum Equals K
8. Subarray Sums Divisible by K
9. Longest Subarray Sum K
10. Contiguous Array
11. Continuous Subarray Sum
12. Longest Substring Without Repeating Characters
13. Minimum Window Substring
14. Find All Anagrams in a String
15. First Unique Character
16. Ransom Note
17. Isomorphic Strings
18. Word Pattern
19. Jewels and Stones
```

These cover almost all of the **major HashMap recognition patterns** you should know for interviews.

---

# Final HashMap Cheat Sheet

```text
                    HASHMAP
                       |
       ┌───────────────┼────────────────┐
       ↓               ↓                ↓
   FREQUENCY         MAPPING          LOOKUP
       |               |                |
       ↓               ↓                ↓
   count chars       A → B          value → index
       |               |                |
       ↓               ↓                ↓
 Anagram          Isomorphic         Two Sum
 Majority         Word Pattern
       |
       ↓
    GROUPING
       |
       ↓
 key → List
       |
       ↓
 Group Anagrams


                  SUBARRAY
                      |
              Prefix Sum + Map
                      |
           ┌──────────┴─────────┐
           ↓                    ↓
        Sum = K             Divisible K
           |                    |
           ↓                    ↓
     prefix - K             prefix % K
           |
      ┌────┴────┐
      ↓         ↓
    Count     Longest
      |         |
 frequency   first index


                  STRING
                     |
             Sliding Window
                     +
                  HashMap
                     |
             ┌───────┴────────┐
             ↓                ↓
         Longest           Minimum
             |                |
      No Repeating       Required Chars
```

## Master the storage decision:

```text
Need COUNT?
→ HashMap<Value, Frequency>

Need FIRST/LONGEST?
→ HashMap<Value, FirstIndex>

Need EXISTENCE?
→ HashSet<Value>

Need MAPPING?
→ HashMap<A, B>

Need GROUPING?
→ HashMap<Key, List<Value>>

Need SUBARRAY SUM?
→ Prefix Sum + HashMap

Need SUBSTRING WINDOW?
→ Sliding Window + HashMap
```

**Next Pattern → Stack Pattern — Part 1: Stack Fundamentals + Monotonic Stack Recognition**
