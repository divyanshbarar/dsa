# Sliding Window Pattern - Part 2

# Longest Window Family

---

# Why This Family Matters

This is the most frequently asked Sliding Window category.

Interviewers love these questions because they test:

```text
Can you dynamically grow and shrink a window?
```

Instead of:

```text
Fixed Size K
```

the window size changes.

Goal is usually:

```text
Find Longest Valid Window
```

---

# Master Template

```java
int left = 0;

for(int right = 0;
    right < n;
    right++){

    // expand

    while(windowInvalid){

        // shrink

        left++;
    }

    answer =
        Math.max(
            answer,
            right - left + 1
        );
}
```

---

# Pattern Recognition Clues

Think Variable Sliding Window when you see:

### Clue 1

```text
Longest
```

### Clue 2

```text
Maximum Length
```

### Clue 3

```text
At Most K
```

### Clue 4

```text
Without Repeating
```

### Clue 5

```text
Can change at most K elements
```

---

# Problem 4: Longest Substring Without Repeating Characters

## LeetCode 3

---

# Problem Explanation

Given:

```text
abcabcbb
```

Find:

```text
Longest substring
containing unique characters only
```

Answer:

```text
abc
```

Length:

```text
3
```

---

# What Is The Interviewer Testing?

Can you maintain:

```text
Always Valid Window
```

where:

```text
No Duplicate Characters
```

---

# Pattern Recognition Clues

### Clue 1

Substring.

### Clue 2

Longest.

### Clue 3

Unique characters.

Think:

```text
Sliding Window + HashSet
```

---

# Brute Force

Generate all substrings.

Check uniqueness.

---

## Complexity

```text
Time : O(n³)
```

---

# Optimal Approach

Maintain:

```java
HashSet<Character>
```

Whenever duplicate appears:

```text
Shrink Window
```

until duplicate removed.

---

# Dry Run

```text
abcabcbb

abc

next = a

duplicate

remove a

window = bca
```

---

# What To Say In Interview

I maintain a window that always contains unique characters.

Whenever a duplicate enters the window, I shrink from the left until the window becomes valid again.

---

# Optimal Java Code

```java
class Solution {

    public int lengthOfLongestSubstring(
            String s
    ) {

        Set<Character> set =
                new HashSet<>();

        int left = 0;
        int answer = 0;

        for(int right = 0;
            right < s.length();
            right++){

            while(set.contains(
                    s.charAt(right)
            )){

                set.remove(
                        s.charAt(left)
                );

                left++;
            }

            set.add(
                    s.charAt(right)
            );

            answer =
                    Math.max(
                            answer,
                            right - left + 1
                    );
        }

        return answer;
    }
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(n)
```

---

# Problem 5: Longest Repeating Character Replacement

## LeetCode 424

---

# Problem Explanation

Given:

```text
AABABBA

k = 1
```

You may replace:

```text
At Most K Characters
```

Find longest substring that can become:

```text
Same Character
```

after replacements.

Answer:

```text
4
```

---

# What Is The Interviewer Testing?

Can you identify:

```text
Window Validity Rule
```

instead of focusing on the actual replacement?

---

# Key Insight

Inside window:

```text
Window Length
-
Most Frequent Character Count
```

gives:

```text
Characters needing replacement
```

---

# Window Validity

Valid if:

```java
windowLength - maxFreq <= k
```

---

# Dry Run

```text
AABABBA

Window

AABA

maxFreq = 3

length = 4

4 - 3 = 1

Valid
```

---

# What To Say In Interview

Instead of actually replacing characters, I'll track the most frequent character inside the window.

The remaining characters are the ones that must be replaced.

---

# Optimal Java Code

```java
class Solution {

    public int characterReplacement(
            String s,
            int k
    ) {

        int[] freq =
                new int[26];

        int left = 0;

        int maxFreq = 0;

        int answer = 0;

        for(int right = 0;
            right < s.length();
            right++){

            maxFreq =
                    Math.max(
                            maxFreq,
                            ++freq[
                                s.charAt(right)
                                - 'A'
                            ]
                    );

            while(
                (right - left + 1)
                - maxFreq > k
            ){

                freq[
                    s.charAt(left)
                    - 'A'
                ]--;

                left++;
            }

            answer =
                    Math.max(
                            answer,
                            right - left + 1
                    );
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

# Problem 6: Longest Substring With At Most K Distinct Characters

## LeetCode 340

---

# Problem Explanation

Given:

```text
eceba

k = 2
```

Find longest substring containing:

```text
At Most 2 Distinct Characters
```

Answer:

```text
ece
```

Length:

```text
3
```

---

# What Is The Interviewer Testing?

Can you maintain:

```text
Distinct Count
```

inside a dynamic window?

---

# Window Validity

```java
distinctCount <= k
```

---

# Optimal Approach

Use:

```java
HashMap<Character,Integer>
```

to track frequency.

---

# What To Say In Interview

The window remains valid as long as the number of distinct characters is at most k.

Whenever it exceeds k, I shrink the window until it becomes valid again.

---

# Optimal Java Code

```java
class Solution {

    public int lengthOfLongestSubstringKDistinct(
            String s,
            int k
    ) {

        Map<Character,Integer> map =
                new HashMap<>();

        int left = 0;
        int answer = 0;

        for(int right = 0;
            right < s.length();
            right++){

            map.put(
                s.charAt(right),
                map.getOrDefault(
                    s.charAt(right),
                    0
                ) + 1
            );

            while(map.size() > k){

                char c =
                        s.charAt(left);

                map.put(
                    c,
                    map.get(c)-1
                );

                if(map.get(c) == 0){
                    map.remove(c);
                }

                left++;
            }

            answer =
                    Math.max(
                            answer,
                            right-left+1
                    );
        }

        return answer;
    }
}
```

---

# Complexity

```text
Time  : O(n)

Space : O(k)
```

---

# Problem 7: Max Consecutive Ones III

## LeetCode 1004

---

# Problem Explanation

Given binary array.

You may flip:

```text
At Most K Zeroes
```

Find longest consecutive ones.

Example:

```text
1 1 1 0 0 0 1 1 1 1 0

k = 2
```

Answer:

```text
6
```

---

# Key Insight

Window valid if:

```java
zeroCount <= k
```

---

# What To Say In Interview

Instead of actually flipping zeroes, I'll count how many zeroes are inside the current window.

As long as the count is at most k, the window is valid.

---

# Optimal Java Code

```java
class Solution {

    public int longestOnes(
            int[] nums,
            int k
    ) {

        int left = 0;
        int zeroes = 0;
        int answer = 0;

        for(int right = 0;
            right < nums.length;
            right++){

            if(nums[right] == 0){
                zeroes++;
            }

            while(zeroes > k){

                if(nums[left] == 0){
                    zeroes--;
                }

                left++;
            }

            answer =
                    Math.max(
                            answer,
                            right-left+1
                    );
        }

        return answer;
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

# Problem 8: Longest Substring Without 3 Identical Consecutive Characters

## Google Variant

---

# Problem Explanation

Find longest substring where:

```text
No Character Appears
3 Times Consecutively
```

Example:

```text
aaabbcc
```

Invalid:

```text
aaa
```

---

# Interview Insight

This is a:

```text
Constraint Based Window
```

problem.

Window validity depends on:

```text
Last 3 Characters
```

---

# Simple Observation

Whenever:

```java
s[right]
==
s[right-1]
==
s[right-2]
```

window becomes invalid.

Move left.

---

# Optimal Java Code

```java
class Solution {

    public int longestValid(
            String s
    ) {

        int left = 0;
        int answer = 0;

        for(int right = 0;
            right < s.length();
            right++){

            while(
                right - left + 1 >= 3
                &&
                s.charAt(right)
                ==
                s.charAt(right-1)
                &&
                s.charAt(right)
                ==
                s.charAt(right-2)
            ){

                left++;
            }

            answer =
                    Math.max(
                            answer,
                            right-left+1
                    );
        }

        return answer;
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

# Longest Window Revision Sheet

## Master Formula

```java
while(windowInvalid){

    left++;
}
```

---

## Window Types

### Duplicate Constraint

```text
No Repeating Characters
```

Problem:

* Longest Substring Without Repeating Characters

---

### Frequency Constraint

```text
At Most K Distinct
```

Problem:

* Longest Substring With At Most K Distinct Characters

---

### Replacement Constraint

```text
Can Replace K Characters
```

Problem:

* Longest Repeating Character Replacement

---

### Flip Constraint

```text
Can Flip K Zeroes
```

Problem:

* Max Consecutive Ones III

---

### Custom Constraint

```text
No 3 Consecutive Identical
```

Problem:

* Longest Substring Without 3 Consecutive Identical Characters

---

# Golden Rule

For Longest Window problems:

```java
Expand Right

If Invalid:
    Shrink Left

Update Answer
```

Never shrink first.

Always:

```text
Expand
→ Validate
→ Shrink
→ Record Answer
```
