# HashMap Pattern - Part 2

# Frequency Map Pattern

> **This is the single most common HashMap pattern asked in coding interviews.**

---

# Why This Pattern Matters

Many interview problems can be reduced to one simple idea:

```text id="hf201"
Element

↓

Frequency
```

Instead of repeatedly searching the array,

we simply count how many times every element appears.

This reduces many

```text id="hf202"
O(n²)
```

solutions into

```text id="hf203"
O(n)
```

solutions.

---

# Recognition Pattern

Whenever interviewer says

```text id="hf204"
Count
```

↓

Think

```text id="hf205"
Frequency Map
```

---

Whenever interviewer says

```text id="hf206"
Most Frequent
```

↓

Think

```text id="hf207"
Frequency Map
```

---

Whenever interviewer says

```text id="hf208"
Majority
```

↓

Think

```text id="hf209"
Frequency Map
```

---

Whenever interviewer says

```text id="hf210"
Anagram
```

↓

Think

```text id="hf211"
Frequency Map
```

---

# Master Template

Every frequency question follows the same code.

```java id="hf212"
Map<Integer,Integer> map =
        new HashMap<>();

for(int num : nums){

    map.put(
        num,
        map.getOrDefault(num,0)+1
    );
}
```

This is probably the **most important HashMap snippet** in Java interviews.

---

# Why getOrDefault()?

Without it:

```java id="hf213"
if(map.containsKey(num)){

    map.put(
        num,
        map.get(num)+1
    );

}else{

    map.put(num,1);
}
```

---

With it:

```java id="hf214"
map.put(
    num,
    map.getOrDefault(num,0)+1
);
```

Cleaner.

Shorter.

Preferred in interviews.

---

# Problem 1 : Valid Anagram

## LeetCode 242

---

# Problem Explanation

Given

```text id="hf215"
s = "anagram"

t = "nagaram"
```

Return

```text id="hf216"
true
```

because both strings contain exactly the same characters with the same frequencies.

---

Example

```text id="hf217"
listen

silent
```

Valid.

---

Example

```text id="hf218"
cat

rat
```

Not valid.

---

# What Is The Interviewer Testing?

Can you compare

```text id="hf219"
Character Frequencies
```

instead of sorting?

---

# Brute Force

Sort both strings.

Compare.

---

Complexity

```text id="hf220"
O(n log n)
```

---

# Better Idea

Count frequency.

---

Example

```text id="hf221"
anagram
```

Frequency

```text id="hf222"
a -> 3

n -> 1

g ->1

r ->1

m ->1
```

Now process second string.

Decrease frequency.

If every count becomes

```text id="hf223"
0
```

Valid.

---

# Dry Run

String

```text id="hf224"
abca
```

Map

```text id="hf225"
a ->2

b ->1

c ->1
```

Second String

```text id="hf226"
caba
```

Decrease

↓

All become

```text id="hf227"
0
```

Return

```text id="hf228"
true
```

---

# What To Say In Interview

Instead of sorting both strings, I'll compare their character frequencies.

If every character appears the same number of times, the strings are anagrams.

---

# Optimal Java Code

```java id="hf229"
class Solution {

    public boolean isAnagram(
            String s,
            String t
    ){

        if(s.length()!=t.length()){

            return false;
        }

        Map<Character,Integer> map =
                new HashMap<>();

        for(char ch : s.toCharArray()){

            map.put(
                ch,
                map.getOrDefault(ch,0)+1
            );
        }

        for(char ch : t.toCharArray()){

            if(!map.containsKey(ch)){

                return false;
            }

            map.put(
                ch,
                map.get(ch)-1
            );

            if(map.get(ch)==0){

                map.remove(ch);
            }
        }

        return map.isEmpty();
    }
}
```

---

# Complexity

```text id="hf230"
Time

O(n)
```

```text id="hf231"
Space

O(n)
```

---

# Similar Problems

* Find Difference
* Ransom Note
* Group Anagrams

---

# Problem 2 : Majority Element

## LeetCode 169

---

# Problem Explanation

Given

```text id="hf232"
2 2 1 1 1 2 2
```

Majority element

appears more than

```text id="hf233"
n/2
```

times.

Answer

```text id="hf234"
2
```

---

# What Is The Interviewer Testing?

Can you use frequency counting?

(Although there is a better Boyer-Moore algorithm, interviewers often expect HashMap first.)

---

# Brute Force

For every element,

count occurrences.

Complexity

```text id="hf235"
O(n²)
```

---

# Better Idea

Count frequency.

Return first element whose count exceeds

```text id="hf236"
n/2
```

---

# Dry Run

Input

```text id="hf237"
3 2 3
```

Map

```text id="hf238"
3 ->2

2 ->1
```

Majority

```text id="hf239"
3
```

---

# What To Say In Interview

I'll count the frequency of every number.

Once any frequency exceeds n/2, I've found the majority element.

---

# Optimal Java Code (HashMap)

```java id="hf240"
class Solution {

    public int majorityElement(
            int[] nums
    ){

        Map<Integer,Integer> map =
                new HashMap<>();

        int limit =
                nums.length/2;

        for(int num : nums){

            int count =
                    map.getOrDefault(
                            num,
                            0
                    )+1;

            map.put(num,count);

            if(count>limit){

                return num;
            }
        }

        return -1;
    }
}
```

---

# Complexity

```text id="hf241"
Time

O(n)
```

```text id="hf242"
Space

O(n)
```

---

# Interview Follow-up

There exists an

```text id="hf243"
O(1)
```

space solution

called

```text id="hf244"
Boyer Moore Voting
```

We'll study it later.

---

# Problem 3 : Top K Frequent Elements (Frequency Phase)

## LeetCode 347

---

# Problem Explanation

Input

```text id="hf245"
1 1 1 2 2 3
```

Need

```text id="hf246"
k = 2
```

Output

```text id="hf247"
1 2
```

---

# Interview Insight

This problem has

two phases.

Phase 1

```text id="hf248"
Frequency Counting
```

Phase 2

```text id="hf249"
Heap / Bucket Sort
```

Today we learn only Phase 1.

---

# Frequency Map

```text id="hf250"
1 ->3

2 ->2

3 ->1
```

Now another algorithm can easily find

Top K.

---

# Optimal Frequency Code

```java id="hf251"
Map<Integer,Integer> frequency =
        new HashMap<>();

for(int num : nums){

    frequency.put(
        num,
        frequency.getOrDefault(
            num,
            0
        )+1
    );
}
```

---

# Complexity

```text id="hf252"
Time

O(n)
```

---

# Pattern

```text id="hf253"
Element

↓

Count

↓

Another Algorithm
```

---

# Problem 4 : Character Frequency

---

# Problem

Count frequency of every character.

Input

```text id="hf254"
banana
```

Output

```text id="hf255"
b ->1

a ->3

n ->2
```

---

# Optimal Java Code

```java id="hf256"
Map<Character,Integer> map =
        new HashMap<>();

for(char ch :
        str.toCharArray()){

    map.put(
        ch,
        map.getOrDefault(
            ch,
            0
        )+1
    );
}
```

---

# Dry Run

Input

```text id="hf257"
apple
```

Map

```text id="hf258"
a->1

p->2

l->1

e->1
```

Done.

---

# Frequency Pattern Comparison

| Problem          | Key       | Value     |
| ---------------- | --------- | --------- |
| Valid Anagram    | Character | Frequency |
| Majority Element | Number    | Frequency |
| Top K Frequent   | Number    | Frequency |
| Character Count  | Character | Frequency |

Notice

the structure never changes.

Only

```text id="hf259"
Key Type
```

changes.

---

# Common Mistakes

### Mistake 1

Using

```java id="hf260"
containsKey()
```

for every insertion.

Prefer

```java id="hf261"
getOrDefault()
```

---

### Mistake 2

Sorting before thinking.

Many frequency problems can be solved without sorting.

---

### Mistake 3

Counting twice.

One traversal is usually enough.

---

# One-Minute Revision

Pattern

```text id="hf262"
Element

↓

Frequency
```

---

Most Important Method

```java id="hf263"
getOrDefault()
```

---

Template

```java id="hf264"
map.put(
key,
map.getOrDefault(key,0)+1
);
```

---

Recognition

```text id="hf265"
Count

Most Frequent

Majority

Occurrences

Anagram
```

↓

Think

```text id="hf266"
Frequency Map
```

---

# Golden Rule

Whenever the interviewer asks:

```text id="hf267"
"How many times does each element appear?"
```

or

```text id="hf268"
"Which element appears the most?"
```

Don't start sorting.

Instead ask yourself:

> **"Can I solve this by mapping each element to its frequency?"**

If the answer is **yes**, the optimal solution almost always begins with:

```java id="hf269"
map.put(
    key,
    map.getOrDefault(key,0)+1
);
```

Master this one template, and you'll instantly recognize a large family of HashMap interview problems.
