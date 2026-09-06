# HashMap Pattern - Part 4

# Character Mapping Pattern

> **This pattern is about mapping one entity to another.**

Unlike previous HashMap patterns:

```text id="hc401"
Element

↓

Frequency
```

or

```text id="hc402"
Value

↓

Index
```

this pattern becomes

```text id="hc403"
Character

↓

Character
```

or

```text id="hc404"
Word

↓

Pattern
```

This family teaches **one-to-one (bijective) mapping**, a very common interview concept.

---

# Why This Pattern Matters

Many interview problems ask questions like:

* Does every character map uniquely?
* Can one word represent one pattern?
* Is the mapping consistent?

Examples:

* Isomorphic Strings
* Word Pattern
* Find the Difference
* Alien Dictionary (foundation)

---

# Pattern Recognition

Whenever interviewer says

```text id="hc405"
Mapping
```

↓

Think

```text id="hc406"
HashMap
```

---

Whenever interviewer says

```text id="hc407"
Replace Characters
```

↓

Think

```text id="hc408"
Character Mapping
```

---

Whenever interviewer says

```text id="hc409"
Pattern
```

↓

Think

```text id="hc410"
Two-way Mapping
```

---

Whenever interviewer says

```text id="hc411"
One-to-One Relationship
```

↓

Think

```text id="hc412"
Two HashMaps
```

---

# The Biggest Idea

Most beginners use

```text id="hc413"
One HashMap
```

Strong candidates know

```text id="hc414"
One HashMap

is

NOT

always enough.
```

Sometimes we must verify mapping in **both directions**.

---

# One-Way Mapping

Suppose

```text id="hc415"
a

↓

x
```

Allowed?

Yes.

---

Suppose later

```text id="hc416"
b

↓

x
```

Problem!

Now two characters map to one.

Not valid.

---

Need

```text id="hc417"
One-to-One Mapping
```

---

# Problem 1 : Isomorphic Strings

## LeetCode 205

---

# Problem Explanation

Given

```text id="hc418"
egg

add
```

Return

```text id="hc419"
true
```

because

```text id="hc420"
e

↓

a
```

```text id="hc421"
g

↓

d
```

---

Example

```text id="hc422"
foo

bar
```

Return

```text id="hc423"
false
```

because

```text id="hc424"
o

↓

a

and

↓

r
```

One character maps to two.

Impossible.

---

Example

```text id="hc425"
ab

aa
```

Return

```text id="hc426"
false
```

because

```text id="hc427"
a

↓

a
```

```text id="hc428"
b

↓

a
```

Two characters map to one.

Not allowed.

---

# What Is The Interviewer Testing?

Can you verify

```text id="hc429"
Bijective Mapping
```

instead of just one-way mapping?

---

# Brute Force

For every character,

check previous mapping.

Very messy.

---

# Better Idea

Use one HashMap.

Fails for

```text id="hc430"
ab

aa
```

because

```text id="hc431"
a

↓

a
```

and

```text id="hc432"
b

↓

a
```

looks valid from one direction.

---

# Correct Idea

Need

Two HashMaps.

---

# Dry Run

Input

```text id="hc433"
paper

title
```

Maps

```text id="hc434"
p

↓

t
```

```text id="hc435"
a

↓

i
```

```text id="hc436"
e

↓

l
```

```text id="hc437"
r

↓

e
```

Both directions valid.

Return

```text id="hc438"
true
```

---

# What To Say In Interview

A valid isomorphic mapping must be one-to-one.

I'll maintain two HashMaps:

* source → target
* target → source

This guarantees consistency in both directions.

---

# Optimal Java Code

```java id="hc439"
class Solution {

    public boolean isIsomorphic(
            String s,
            String t
    ){

        Map<Character,Character> map1 =
                new HashMap<>();

        Map<Character,Character> map2 =
                new HashMap<>();

        for(int i=0;
            i<s.length();
            i++){

            char c1=s.charAt(i);
            char c2=t.charAt(i);

            if(map1.containsKey(c1)
                &&
               map1.get(c1)!=c2){

                return false;
            }

            if(map2.containsKey(c2)
                &&
               map2.get(c2)!=c1){

                return false;
            }

            map1.put(c1,c2);
            map2.put(c2,c1);
        }

        return true;
    }
}
```

---

# Complexity

```text id="hc440"
Time

O(n)
```

```text id="hc441"
Space

O(n)
```

---

# Problem 2 : Word Pattern

## LeetCode 290

---

# Problem Explanation

Pattern

```text id="hc442"
abba
```

Sentence

```text id="hc443"
dog cat cat dog
```

Return

```text id="hc444"
true
```

---

Example

```text id="hc445"
abba
```

Sentence

```text id="hc446"
dog cat cat fish
```

Return

```text id="hc447"
false
```

---

Example

```text id="hc448"
abba
```

Sentence

```text id="hc449"
dog dog dog dog
```

Return

```text id="hc450"
false
```

---

# Pattern

Exactly same as

```text id="hc451"
Isomorphic Strings
```

Only difference

Character

↓

Word

instead of

Character

↓

Character

---

# Dry Run

Pattern

```text id="hc452"
abba
```

Words

```text id="hc453"
dog

cat

cat

dog
```

Maps

```text id="hc454"
a

↓

dog
```

```text id="hc455"
b

↓

cat
```

Everything consistent.

---

# What To Say In Interview

This is identical to Isomorphic Strings.

Instead of characters mapping to characters,

characters now map to words.

The same bidirectional mapping ensures correctness.

---

# Optimal Java Code

```java id="hc456"
class Solution {

    public boolean wordPattern(
            String pattern,
            String s
    ){

        String[] words =
                s.split(" ");

        if(words.length!=
                pattern.length()){

            return false;
        }

        Map<Character,String> map1 =
                new HashMap<>();

        Map<String,Character> map2 =
                new HashMap<>();

        for(int i=0;
            i<pattern.length();
            i++){

            char ch=
                pattern.charAt(i);

            String word=
                words[i];

            if(map1.containsKey(ch)
                &&
               !map1.get(ch)
                     .equals(word)){

                return false;
            }

            if(map2.containsKey(word)
                &&
               map2.get(word)!=ch){

                return false;
            }

            map1.put(ch,word);
            map2.put(word,ch);
        }

        return true;
    }
}
```

---

# Complexity

```text id="hc457"
Time

O(n)
```

```text id="hc458"
Space

O(n)
```

---

# Problem 3 : Find The Difference

## LeetCode 389

---

# Problem Explanation

Given

```text id="hc459"
abcd
```

and

```text id="hc460"
abcde
```

Return

```text id="hc461"
e
```

---

# Brute Force

Count every character repeatedly.

```text id="hc462"
O(n²)
```

---

# Better Idea

Use

```text id="hc463"
Frequency Map
```

Increase counts for

```text id="hc464"
s
```

Decrease counts for

```text id="hc465"
t
```

Character whose frequency becomes

```text id="hc466"
1
```

is answer.

---

# Optimal Java Code

```java id="hc467"
class Solution {

    public char findTheDifference(
            String s,
            String t
    ){

        Map<Character,Integer> map =
                new HashMap<>();

        for(char ch :
                s.toCharArray()){

            map.put(
                ch,
                map.getOrDefault(ch,0)+1
            );
        }

        for(char ch :
                t.toCharArray()){

            if(!map.containsKey(ch)){

                return ch;
            }

            map.put(
                ch,
                map.get(ch)-1
            );

            if(map.get(ch)==0){

                map.remove(ch);
            }
        }

        return ' ';
    }
}
```

---

# Complexity

```text id="hc468"
Time

O(n)
```

```text id="hc469"
Space

O(n)
```

---

# Character Mapping Comparison

| Problem             | Mapping               |
| ------------------- | --------------------- |
| Isomorphic Strings  | Character → Character |
| Word Pattern        | Character → Word      |
| Find The Difference | Character → Frequency |

Notice

the HashMap idea remains the same.

Only the

```text id="hc470"
Value Type
```

changes.

---

# Common Mistakes

### Mistake 1

Using only one HashMap.

Fails for

```text id="hc471"
ab

aa
```

---

### Mistake 2

Comparing Strings using

```java id="hc472"
==
```

Wrong.

Use

```java id="hc473"
.equals()
```

---

### Mistake 3

Forgetting length check in

```text id="hc474"
Word Pattern
```

Always compare

```java id="hc475"
pattern.length()

and

words.length
```

---

# Recognition Checklist

Interview says

```text id="hc476"
Pattern
```

↓

HashMap

---

Interview says

```text id="hc477"
Replace Characters
```

↓

Mapping

---

Interview says

```text id="hc478"
One-to-One
```

↓

Two HashMaps

---

Interview says

```text id="hc479"
Character Correspondence
```

↓

Bidirectional Mapping

---

# One-Minute Revision

Pattern

```text id="hc480"
Character

↓

Character
```

---

Need

```text id="hc481"
Bijection
```

↓

Use

```text id="hc482"
Two HashMaps
```

---

Most Important Problems

```text id="hc483"
Isomorphic Strings

Word Pattern
```

---

Most Common Bug

```text id="hc484"
Using

One HashMap
```

---

# Golden Rule

Whenever the interviewer says:

```text id="hc485"
"Every character (or word) should uniquely correspond to another."
```

Don't think about frequency.

Don't think about indices.

Instead ask yourself:

> **"Do I need to verify this mapping in both directions?"**

If the answer is **yes**, the correct solution almost always uses **two HashMaps** to enforce a one-to-one (bijective) relationship.
