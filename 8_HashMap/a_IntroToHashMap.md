# HashMap Pattern - Part 1

# Fundamentals & Interview Foundation

---

# Why HashMap Matters

If Binary Search is the king of searching,

and Sliding Window is the king of subarrays,

then **HashMap is the king of optimization.**

A huge number of interview questions become one-pass solutions after introducing a HashMap.

Examples:

* Two Sum
* Group Anagrams
* Subarray Sum Equals K
* Longest Substring Without Repeating Characters
* Top K Frequent Elements
* Valid Anagram
* Isomorphic Strings

Most FAANG interviews contain at least one HashMap-based question.

---

# What Is A HashMap?

A HashMap stores data as

```text id="hm101"
Key

↓

Value
```

Unlike arrays, where elements are accessed using indexes,

HashMap lets us access values using keys.

Example

```java id="hm102"
Map<String, Integer> age =
        new HashMap<>();

age.put("Alice", 25);
age.put("Bob", 30);

System.out.println(
        age.get("Alice")
);
```

Output

```text id="hm103"
25
```

---

# Real Life Analogy

Imagine a dictionary.

Instead of searching every page,

you directly search the word.

```text id="hm104"
Apple

↓

Fruit
```

or

```text id="hm105"
Employee ID

↓

Employee Details
```

The key tells you exactly where to look.

---

# Why Do We Need HashMap?

Suppose we have

```text id="hm106"
5 8 2 10 7
```

Need to check whether

```text id="hm107"
10
```

exists.

---

Using Array

```java id="hm108"
for(int num : arr){

    if(num == 10){
        return true;
    }
}
```

Complexity

```text id="hm109"
O(n)
```

---

Using HashMap

```java id="hm110"
map.containsKey(10)
```

Complexity

```text id="hm111"
O(1)
```

Average.

Huge improvement.

---

# The Biggest Idea

Arrays answer

```text id="hm112"
Index

↓

Value
```

Example

```java id="hm113"
arr[5]
```

---

HashMap answers

```text id="hm114"
Key

↓

Value
```

Example

```java id="hm115"
map.get(key)
```

---

# Internal Picture

Think of a HashMap as many small buckets.

```text id="hm116"
Bucket 0

Bucket 1

Bucket 2

Bucket 3

...

Bucket n
```

Every key is placed into one bucket.

The bucket is chosen using

```text id="hm117"
Hash Function
```

This bucket-based organization enables average O(1) lookup.

---

# High Level Working

Whenever you write

```java id="hm118"
map.put(key, value);
```

HashMap performs

```text id="hm119"
Compute Hash

↓

Find Bucket

↓

Store Key

↓

Store Value
```

Later

```java id="hm120"
map.get(key);
```

HashMap

```text id="hm121"
Computes Same Hash

↓

Goes To Same Bucket

↓

Returns Value
```

---

# Pattern Recognition

Whenever interviewer says

```text id="hm122"
Frequency
```

Think

```text id="hm123"
HashMap
```

---

Whenever interviewer says

```text id="hm124"
Duplicate
```

Think

```text id="hm125"
HashSet

or

HashMap
```

---

Whenever interviewer says

```text id="hm126"
Fast Lookup
```

Think

```text id="hm127"
HashMap
```

---

Whenever interviewer says

```text id="hm128"
Previously Seen
```

Think

```text id="hm129"
Store In HashMap
```

---

Whenever interviewer says

```text id="hm130"
Mapping
```

Think

```text id="hm131"
Key

↓

Value
```

---

# HashMap Recognition Checklist

Interview asks

```text id="hm132"
Count
```

↓

HashMap

---

Interview asks

```text id="hm133"
Frequency
```

↓

HashMap

---

Interview asks

```text id="hm134"
Previous Index
```

↓

HashMap

---

Interview asks

```text id="hm135"
Seen Before
```

↓

HashMap

---

Interview asks

```text id="hm136"
Lookup
```

↓

HashMap

---

Interview asks

```text id="hm137"
Grouping
```

↓

HashMap

---

# Common Operations

## Insert

```java id="hm138"
map.put("Apple", 10);
```

---

## Read

```java id="hm139"
map.get("Apple");
```

---

## Check

```java id="hm140"
map.containsKey("Apple");
```

---

## Remove

```java id="hm141"
map.remove("Apple");
```

---

## Size

```java id="hm142"
map.size();
```

---

## Iterate

```java id="hm143"
for(Map.Entry<String,Integer> entry
        : map.entrySet()){

    System.out.println(
        entry.getKey()
    );

    System.out.println(
        entry.getValue()
    );
}
```

---

# The Most Useful Methods

## get()

Returns value.

```java id="hm144"
map.get(key);
```

---

## put()

Insert or update.

```java id="hm145"
map.put(key, value);
```

---

## containsKey()

Check existence.

```java id="hm146"
map.containsKey(key);
```

---

## getOrDefault()

Most important for frequency questions.

```java id="hm147"
map.getOrDefault(key, 0);
```

We'll use this in almost every HashMap problem.

---

## computeIfAbsent()

Most important for grouping.

```java id="hm148"
map.computeIfAbsent(
    key,
    k -> new ArrayList<>()
);
```

Used heavily in:

```text id="hm149"
Group Anagrams
```

---

# HashMap vs Array

| Array             | HashMap             |
| ----------------- | ------------------- |
| Index → Value     | Key → Value         |
| Fixed Index       | Any Key             |
| O(1) Index Access | O(1) Average Lookup |
| Sequential        | Mapping             |

---

Example

Array

```java id="hm150"
arr[4]
```

HashMap

```java id="hm151"
map.get("Rahul")
```

---

# HashMap vs HashSet

Many beginners confuse them.

---

HashMap

```text id="hm152"
Key

↓

Value
```

Example

```java id="hm153"
Age

↓

25
```

---

HashSet

Only stores

```text id="hm154"
Keys
```

Example

```java id="hm155"
5

7

10
```

No values.

---

Choose HashSet when

```text id="hm156"
Need Only Existence
```

Choose HashMap when

```text id="hm157"
Need Mapping
```

---

# Time Complexity

| Operation     | Average | Worst           |
| ------------- | ------- | --------------- |
| put()         | O(1)    | O(n) / O(log n) |
| get()         | O(1)    | O(n) / O(log n) |
| containsKey() | O(1)    | O(n) / O(log n) |
| remove()      | O(1)    | O(n) / O(log n) |

Average operations are O(1) because HashMap uses hashing and buckets. In heavy collision scenarios, lookups may degrade, though modern Java can convert long collision chains into balanced trees to improve worst-case performance.

---

# Advantages

## Fast Lookup

```text id="hm158"
O(1)
```

Average.

---

## Excellent For

```text id="hm159"
Frequency

Counting

Caching

Memoization

Grouping
```

---

## Generic

Supports

```text id="hm160"
Integer

String

Objects

Custom Classes
```

as keys (provided hashing is implemented correctly).

---

# Common Applications

HashMap is commonly used for:

```text id="hm161"
Frequency Count
```

---

```text id="hm162"
Value

↓

Index
```

---

```text id="hm163"
Grouping
```

---

```text id="hm164"
Caching
```

---

```text id="hm165"
Memoization
```

---

```text id="hm166"
Graph Adjacency
```

---

# Interview Questions

## Why Use HashMap?

Good Answer:

> HashMap provides average O(1) insertion, lookup, and deletion. It's ideal whenever we need fast lookups, frequency counting, mapping between two entities, or remembering previously processed information.

---

## When Should You Think HashMap?

Strong Answer:

Whenever the problem mentions:

* Frequency
* Duplicate detection
* Fast lookup
* Mapping one thing to another
* Previously seen values
* Counting occurrences

HashMap is usually one of the first data structures I consider.

---

## HashMap vs HashSet?

Good Answer:

HashSet stores only unique values.

HashMap stores

```text id="hm167"
Key

↓

Value
```

If I only need to know whether something exists, I use HashSet.

If I need extra information like count, index, or mapping, I use HashMap.

---

# Most Common Beginner Mistakes

### Mistake 1

Using

```java id="hm168"
map.get(key)
```

without checking whether the key exists.

Use

```java id="hm169"
containsKey()
```

or

```java id="hm170"
getOrDefault()
```

instead.

---

### Mistake 2

Writing

```java id="hm171"
if(map.containsKey(key)){

    map.put(
        key,
        map.get(key)+1
    );

}else{

    map.put(key,1);
}
```

Instead simply write

```java id="hm172"
map.put(
    key,
    map.getOrDefault(key,0)+1
);
```

Cleaner and less error-prone.

---

### Mistake 3

Using HashMap when only existence is required.

Use

```text id="hm173"
HashSet
```

instead.

---

# One-Minute Revision

## HashMap

```text id="hm174"
Key

↓

Value
```

---

Average Complexity

```text id="hm175"
O(1)
```

---

Best Methods

```text id="hm176"
put()

get()

containsKey()

getOrDefault()

computeIfAbsent()
```

---

Recognition

```text id="hm177"
Frequency

Duplicate

Lookup

Mapping

Grouping
```

---

# Golden Rule

Whenever you read a problem, ask yourself:

> **"Do I need to remember something I've already seen?"**

If the answer is **yes**, there's a very high chance the optimal solution uses a **HashMap** (or a **HashSet** if you only need existence).

That single question will help you recognize most HashMap interview problems before you even start coding.
