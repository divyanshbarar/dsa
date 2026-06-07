# Sliding Window Pattern - Part 4

# Frequency Matching Family

---

# Why This Family Is Important

Most candidates learn:

```text
Sliding Window
```

as:

```text
Sum
Length
Maximum
Minimum
```

But many FAANG interviews ask:

```text
Anagrams
Permutations
Frequency Matching
```

These require:

```java
int[] freq = new int[26];
```

or

```java
HashMap<Character,Integer>
```

inside a sliding window.

---

# Master Insight

Instead of asking:

```text
What characters exist?
```

We ask:

```text
How many of each character exist?
```

Example:

```text
abc
```

Frequency:

```text
a = 1
b = 1
c = 1
```

---

# Pattern Recognition Clues

Think Frequency Matching when you see:

### Clue 1

```text
Anagram
```

### Clue 2

```text
Permutation
```

### Clue 3

```text
Same Characters
Different Order
```

### Clue 4

```text
Contains Pattern
```

### Clue 5

```text
Frequency Count
```

---

# Master Template

```java
int[] pattern = new int[26];
int[] window = new int[26];

for(char c : p.toCharArray()){
    pattern[c-'a']++;
}

for(int right = 0; right < s.length(); right++){

    window[s.charAt(right)-'a']++;

    if(windowSizeExceeded){

        window[s.charAt(left)-'a']--;
        left++;
    }

    if(Arrays.equals(pattern, window)){
        // match found
    }
}
```

---

# Problem 12: Permutation In String

## LeetCode 567

---

# Problem Explanation

Given:

```text
s1 = "ab"

s2 = "eidbaooo"
```

Determine whether:

```text
Any permutation of s1
exists inside s2
```

Permutations:

```text
ab
ba
```

Inside:

```text
eidbaooo
```

we have:

```text
ba
```

Answer:

```text
true
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text
Permutation
```

means:

```text
Same Frequency
```

not:

```text
Same Order
```

---

# Pattern Recognition Clues

### Clue 1

Permutation.

### Clue 2

Same characters.

### Clue 3

Different order allowed.

### Clue 4

Window size fixed.

Think:

```text
Fixed Window
+
Frequency Matching
```

---

# Brute Force

Generate every substring.

Sort.

Compare.

---

## Complexity

```text
Time :
O(n * k log k)
```

---

# Better

Count frequency for every substring.

---

## Complexity

```text
Time :
O(n * 26)
```

---

# Optimal Approach

Build:

```java
patternFreq
```

for:

```text
s1
```

Maintain:

```java
windowFreq
```

for current window.

If frequencies match:

```text
Permutation Exists
```

---

# Dry Run

```text
s1 = ab

Pattern

a=1
b=1

----------------

Window = ei

No Match

----------------

Window = id

No Match

----------------

Window = db

No Match

----------------

Window = ba

Match
```

---

# What To Say In Interview

Since order does not matter, I only care about character frequencies.

A permutation exists if a window of length s1.length() has the exact same frequency distribution.

---

# Optimal Java Code

```java
class Solution {

    public boolean checkInclusion(
            String s1,
            String s2
    ) {

        if(s1.length() > s2.length()){
            return false;
        }

        int[] pattern =
                new int[26];

        int[] window =
                new int[26];

        for(char c : s1.toCharArray()){
            pattern[c-'a']++;
        }

        int k = s1.length();

        for(int right = 0;
            right < s2.length();
            right++){

            window[
                s2.charAt(right)-'a'
            ]++;

            if(right >= k){

                window[
                    s2.charAt(right-k)-'a'
                ]--;
            }

            if(Arrays.equals(
                    pattern,
                    window
            )){
                return true;
            }
        }

        return false;
    }
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(26)
```

---

# Similar Problems

* Find All Anagrams In String
* Minimum Window Substring

---

# Problem 13: Find All Anagrams In A String

## LeetCode 438

---

# Problem Explanation

Given:

```text
s = "cbaebabacd"

p = "abc"
```

Find all starting indices of:

```text
Anagrams of p
```

Answer:

```text
[0, 6]
```

Because:

```text
cba
bac
```

are both anagrams.

---

# What Is The Interviewer Testing?

Can you derive:

```text
Find All Matches
```

from:

```text
Find One Match
```

?

---

# Key Observation

This is literally:

```text
Permutation In String
```

with different output.

Instead of:

```java
return true;
```

we:

```java
answer.add(startIndex);
```

---

# Pattern Recognition Clues

### Clue 1

Anagram.

### Clue 2

Need all positions.

### Clue 3

Fixed window.

Think:

```text
Same Pattern
As Permutation In String
```

---

# Dry Run

```text
cbaebabacd

Window = cba

Match

Store 0

----------------

Window = bae

No Match

----------------

Window = bac

Match

Store 6
```

---

# What To Say In Interview

This problem is identical to Permutation in String.

The only difference is that instead of returning on the first match, I continue scanning and collect all valid starting positions.

---

# Optimal Java Code

```java
class Solution {

    public List<Integer> findAnagrams(
            String s,
            String p
    ) {

        List<Integer> answer =
                new ArrayList<>();

        if(p.length() > s.length()){
            return answer;
        }

        int[] pattern =
                new int[26];

        int[] window =
                new int[26];

        for(char c : p.toCharArray()){
            pattern[c-'a']++;
        }

        int k = p.length();

        for(int right = 0;
            right < s.length();
            right++){

            window[
                s.charAt(right)-'a'
            ]++;

            if(right >= k){

                window[
                    s.charAt(right-k)-'a'
                ]--;
            }

            if(Arrays.equals(
                    pattern,
                    window
            )){
                answer.add(
                        right-k+1
                );
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(26)
```

---

# Follow-Up Interview Question

## Can We Avoid Arrays.equals()?

Yes.

Instead of comparing:

```java
26 entries
```

every time,

Maintain:

```java
matchedCharacters
```

This reduces constant factors.

Interviewers love this follow-up.

---

# Bonus Problem 14: Group Anagrams

## LeetCode 49

(Not Sliding Window, but same frequency idea)

---

# Problem Explanation

Given:

```text
eat
tea
tan
ate
nat
bat
```

Group anagrams.

Output:

```text
[eat,tea,ate]

[tan,nat]

[bat]
```

---

# Key Insight

Anagrams have identical frequency counts.

Common solution:

```java
HashMap<String,List<String>>
```

using sorted string as key.

---

# Complexity

```text
Time :
O(n * k log k)
```

---

# Better Frequency Key Version

Build:

```text
a#1b#0c#2...
```

as key.

Avoid sorting.

---

# Interview Insight

If you master:

```text
Permutation In String

Find All Anagrams

Group Anagrams
```

you've basically mastered:

```text
Frequency Matching
```

questions.

---

# Frequency Matching Revision Sheet

## Step 1

Build Pattern Frequency

```java
int[] pattern =
        new int[26];
```

---

## Step 2

Maintain Window Frequency

```java
int[] window =
        new int[26];
```

---

## Step 3

Slide Window

```java
Add Right

Remove Left
```

---

## Step 4

Compare Frequencies

```java
Arrays.equals(
    pattern,
    window
)
```

---

# Relationship Between Problems

## Permutation In String

```text
Return:
true / false
```

---

## Find All Anagrams

```text
Return:
all indices
```

---

## Group Anagrams

```text
Return:
groups
```

---

# Golden Interview Rule

If interviewer says:

```text
Anagram

Permutation

Same Characters

Different Order
```

Immediately think:

```text
Frequency Matching

int[26]

Sliding Window
```

NOT:

```text
Sorting Every Substring
```

because that is almost always the brute force solution.
