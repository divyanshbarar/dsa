# HashMap Pattern - Part 3

# Value → Index Mapping Pattern

> **This is the second most important HashMap pattern after Frequency Maps.**

---

# Why This Pattern Matters

Many interview problems don't ask:

```text id="hv301"
How many times
does this appear?
```

Instead they ask:

```text id="hv302"
Have I seen this before?
```

or

```text id="hv303"
Where did I see this?
```

This changes the HashMap from

```text id="hv304"
Element

↓

Frequency
```

to

```text id="hv305"
Element

↓

Index
```

Once you recognize this pattern, problems like **Two Sum** become almost trivial.

---

# Pattern Recognition

Whenever interviewer says

```text id="hv306"
Find Pair
```

↓

Think

```text id="hv307"
Value

↓

Index
```

---

Whenever interviewer says

```text id="hv308"
Previous Occurrence
```

↓

Think

```text id="hv309"
Store Index
```

---

Whenever interviewer says

```text id="hv310"
Already Seen
```

↓

Think

```text id="hv311"
HashMap
```

---

Whenever interviewer says

```text id="hv312"
Need Position
```

↓

Think

```text id="hv313"
Value → Index
```

---

# Master Template

Every Value → Index problem starts like this.

```java id="hv314"
Map<Integer,Integer> map =
        new HashMap<>();
```

Store

```java id="hv315"
map.put(
    value,
    index
);
```

Lookup

```java id="hv316"
map.containsKey(value)
```

---

# Problem 1 : Two Sum

## LeetCode 1

---

# Problem Explanation

Given

```text id="hv317"
nums

2 7 11 15
```

Target

```text id="hv318"
9
```

Need indices

```text id="hv319"
0

1
```

because

```text id="hv320"
2 + 7 = 9
```

---

# What Is The Interviewer Testing?

Can you replace

```text id="hv321"
Nested Loops
```

with

```text id="hv322"
HashMap Lookup
```

---

# Brute Force

Check every pair.

```java id="hv323"
for(i)

    for(j)

        if(nums[i]+nums[j]
            ==target)
```

---

# Complexity

```text id="hv324"
O(n²)
```

---

# Key Observation

Suppose current number is

```text id="hv325"
7
```

Target

```text id="hv326"
9
```

Need

```text id="hv327"
2
```

Question becomes

```text id="hv328"
Have I already
seen

2 ?
```

That's a HashMap lookup.

---

# Dry Run

Array

```text id="hv329"
2 7 11 15
```

Target

```text id="hv330"
9
```

---

Current

```text id="hv331"
2
```

Need

```text id="hv332"
7
```

Map

```text id="hv333"
{}
```

Not found.

Store

```text id="hv334"
2 → 0
```

---

Current

```text id="hv335"
7
```

Need

```text id="hv336"
2
```

Found.

Answer

```text id="hv337"
0 1
```

Done.

---

# What To Say In Interview

Instead of checking every pair, I'll store previously seen values and their indices in a HashMap.

For each element, I compute its complement and check if that complement already exists in the map.

If it does, I've found the answer in O(1) lookup time.

---

# Optimal Java Code

```java id="hv338"
class Solution {

    public int[] twoSum(
            int[] nums,
            int target
    ){

        Map<Integer,Integer> map =
                new HashMap<>();

        for(int i=0;
            i<nums.length;
            i++){

            int complement =
                    target - nums[i];

            if(map.containsKey(
                    complement
            )){

                return new int[]{
                        map.get(complement),
                        i
                };
            }

            map.put(nums[i],i);
        }

        return new int[0];
    }
}
```

---

# Complexity

```text id="hv339"
Time

O(n)
```

```text id="hv340"
Space

O(n)
```

---

# Most Common Mistake

Wrong order.

Many beginners write

```java id="hv341"
map.put(nums[i],i);

if(map.containsKey(...))
```

This fails when

```text id="hv342"
target

=

2 × nums[i]
```

Correct order

```text id="hv343"
Lookup

↓

Store
```

Always.

---

# Problem 2 : Contains Duplicate

## LeetCode 217

---

# Problem Explanation

Input

```text id="hv344"
1 2 3 1
```

Output

```text id="hv345"
true
```

because

```text id="hv346"
1
```

appears twice.

---

# Brute Force

Compare every pair.

```text id="hv347"
O(n²)
```

---

# Better Idea

Store every number.

If already exists

↓

Duplicate found.

---

# Dry Run

Input

```text id="hv348"
1 2 3 1
```

Set / Map

```text id="hv349"
{}
```

Store

```text id="hv350"
1
```

Store

```text id="hv351"
2
```

Store

```text id="hv352"
3
```

Next

```text id="hv353"
1
```

Already exists.

Return

```text id="hv354"
true
```

---

# Optimal Java Code

Using HashSet (preferred)

```java id="hv355"
class Solution {

    public boolean containsDuplicate(
            int[] nums
    ){

        Set<Integer> set =
                new HashSet<>();

        for(int num : nums){

            if(set.contains(num)){

                return true;
            }

            set.add(num);
        }

        return false;
    }
}
```

---

# Interview Insight

Could use HashMap.

But

```text id="hv356"
Need Only Existence
```

Therefore

```text id="hv357"
HashSet
```

is cleaner.

---

# Complexity

```text id="hv358"
Time

O(n)
```

```text id="hv359"
Space

O(n)
```

---

# Problem 3 : Happy Number

## LeetCode 202

---

# Problem Explanation

Repeatedly replace

number

with

```text id="hv360"
Sum Of Squares
Of Digits
```

Eventually

either

```text id="hv361"
Reach

1
```

or

enter

```text id="hv362"
Cycle
```

Need determine

Happy?

---

Example

```text id="hv363"
19

↓

82

↓

68

↓

100

↓

1
```

Happy.

---

# What Is The Interviewer Testing?

Can you detect

```text id="hv364"
Repeated States
```

using HashSet?

---

# Brute Force

Keep computing forever.

Impossible.

---

# Key Observation

If number repeats,

cycle detected.

Need

```text id="hv365"
Visited Numbers
```

---

# Dry Run

Input

```text id="hv366"
2
```

Sequence

```text id="hv367"
2

↓

4

↓

16

↓

37

↓

58

↓

89

↓

145

↓

42

↓

20

↓

4
```

Already seen

```text id="hv368"
4
```

Cycle.

Return

```text id="hv369"
false
```

---

# What To Say In Interview

This problem is essentially cycle detection.

I'll store every previously generated number in a HashSet.

If a number repeats, we've entered a cycle.

---

# Optimal Java Code

```java id="hv370"
class Solution {

    public boolean isHappy(
            int n
    ){

        Set<Integer> seen =
                new HashSet<>();

        while(n!=1){

            if(seen.contains(n)){

                return false;
            }

            seen.add(n);

            int sum = 0;

            while(n>0){

                int digit =
                        n%10;

                sum += digit*digit;

                n/=10;
            }

            n=sum;
        }

        return true;
    }
}
```

---

# Complexity

```text id="hv371"
Time

O(log n)
```

Average.

---

```text id="hv372"
Space

O(log n)
```

---

# Interview Follow-up

Can solve with

```text id="hv373"
Fast & Slow Pointer
```

also.

---

# Problem 4 : Longest Consecutive Sequence (Intro)

## LeetCode 128

---

# Problem Explanation

Input

```text id="hv374"
100

4

200

1

3

2
```

Longest sequence

```text id="hv375"
1

2

3

4
```

Length

```text id="hv376"
4
```

---

# Brute Force

Sort.

Scan.

Complexity

```text id="hv377"
O(n log n)
```

---

# Better Idea

Store every number

inside

```text id="hv378"
HashSet
```

Now

checking

```text id="hv379"
num+1
```

or

```text id="hv380"
num-1
```

becomes

```text id="hv381"
O(1)
```

---

We'll study the complete solution in the HashSet chapter.

---

# Value → Index Comparison

| Problem             | Stored Value |
| ------------------- | ------------ |
| Two Sum             | Index        |
| Contains Duplicate  | Exists       |
| Happy Number        | Visited      |
| Longest Consecutive | Exists       |

Notice

frequency disappeared.

Instead

we're storing

```text id="hv382"
Information
About Previous Values
```

---

# Common Mistakes

### Mistake 1

Store first

then lookup

in Two Sum.

Wrong order.

---

### Mistake 2

Using HashMap

when HashSet is enough.

---

### Mistake 3

Sorting immediately.

HashMap often removes

need for sorting.

---

# Recognition Checklist

Interview says

```text id="hv383"
Pair
```

↓

HashMap

---

Interview says

```text id="hv384"
Complement
```

↓

HashMap

---

Interview says

```text id="hv385"
Seen Before
```

↓

HashSet

---

Interview says

```text id="hv386"
Duplicate
```

↓

HashSet

---

Interview says

```text id="hv387"
Cycle
```

↓

Visited Set

---

# One-Minute Revision

Pattern

```text id="hv388"
Value

↓

Index
```

---

Most Common Problem

```text id="hv389"
Two Sum
```

---

Template

```java id="hv390"
if(map.containsKey(x)){

    return ...
}

map.put(value,index);
```

---

HashSet Use

```text id="hv391"
Need Only Existence
```

---

HashMap Use

```text id="hv392"
Need Extra Information

(Index, Count, Mapping)
```

---

# Golden Rule

Whenever the interviewer asks:

```text id="hv393"
"Have we already seen this value?"
```

or

```text id="hv394"
"Can you find a complement quickly?"
```

Don't think about nested loops.

Instead ask yourself:

> **"Can I store previously seen values in a HashMap or HashSet and answer this lookup in O(1)?"**

That thought process is the foundation of almost every **Value → Index** interview problem.
