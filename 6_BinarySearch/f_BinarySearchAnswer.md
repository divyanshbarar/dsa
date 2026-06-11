# Binary Search Pattern - Part 6

# Binary Search On Answer (Foundation)

---

# Why This Family Matters

This is arguably the most important Binary Search pattern for interviews.

Most candidates know:

```java id="k601"
Search Target
```

Strong candidates know:

```java id="k602"
Search Answer
```

This single pattern solves:

* Koko Eating Bananas
* Ship Packages Within D Days
* Minimum Days To Make Bouquets
* Split Array Largest Sum
* Allocate Books
* Painter Partition
* Aggressive Cows

---

# The Biggest Binary Search Upgrade

Classic Binary Search:

```java id="k603"
Find Target
```

Binary Search On Answer:

```java id="k604"
Find Minimum Valid Answer
```

---

# Core Insight

Suppose interviewer asks:

```text id="k605"
Minimum Speed?
```

Can we directly calculate?

No.

But we can answer:

```text id="k606"
Is speed = 10 valid?

Is speed = 20 valid?

Is speed = 30 valid?
```

YES.

---

# The Magic Transformation

Convert:

```text id="k607"
Find Minimum X
```

into:

```java id="k608"
Can(X)?
```

---

# Visualization

```text id="k609"
1 2 3 4 5 6 7 8 9 10

F F F F F T T T T T
```

Need:

```text id="k610"
First True
```

This is Binary Search.

---

# Problem 15: Koko Eating Bananas

## LeetCode 875

---

# Problem Explanation

Koko has:

```text id="k611"
piles = [3,6,7,11]
```

Hours:

```text id="k612"
h = 8
```

Need:

```text id="k613"
Minimum Eating Speed
```

Bananas per hour.

---

# Example

Speed:

```text id="k614"
4
```

Hours Required:

```text id="k615"
3 -> 1 hour

6 -> 2 hours

7 -> 2 hours

11 -> 3 hours
```

Total:

```text id="k616"
8 hours
```

Valid.

Answer:

```text id="k617"
4
```

---

# What Is The Interviewer Testing?

Can you recognize:

```text id="k618"
Binary Search On Answer
```

instead of searching piles?

---

# Pattern Recognition Clues

### Clue 1

Minimum Speed.

### Clue 2

Minimum Capacity.

### Clue 3

Minimum Days.

### Clue 4

Minimum Rate.

Think:

```text id="k619"
Binary Search On Answer
```

---

# Why Normal Binary Search Fails

There is no:

```text id="k620"
Sorted Array Target
```

to search.

Need to search:

```text id="k621"
Possible Speeds
```

instead.

---

# Search Space

Smallest speed:

```text id="k622"
1
```

Largest speed:

```text id="k623"
max(piles)
```

Because:

```text id="k624"
Eating faster
than largest pile
provides no benefit
```

---

# Key Observation

If speed:

```text id="k625"
4
```

works,

then:

```text id="k626"
5

6

7

8
```

also work.

---

# Why?

Higher speed means:

```text id="k627"
Less Time Needed
```

---

# Monotonic Property

```text id="k628"
1 -> Invalid

2 -> Invalid

3 -> Invalid

4 -> Valid

5 -> Valid

6 -> Valid
```

Looks like:

```text id="k629"
F F F T T T
```

Need:

```text id="k630"
First True
```

---

# The Can Function

Question:

```text id="k631"
Can Koko finish
within h hours
using speed k?
```

---

# Hours Formula

For pile:

```text id="k632"
11
```

Speed:

```text id="k633"
4
```

Hours:

```text id="k634"
ceil(11/4)
=
3
```

---

# Java Trick

Instead of:

```java id="k635"
Math.ceil(
 (double)pile/k
)
```

Use:

```java id="k636"
(pile + k - 1) / k
```

Important interview optimization.

---

# Dry Run

Piles:

```text id="k637"
3 6 7 11
```

Hours:

```text id="k638"
8
```

---

Try:

```text id="k639"
speed = 6
```

Hours:

```text id="k640"
1 + 1 + 2 + 2
=
6
```

Valid.

Search smaller.

---

Try:

```text id="k641"
speed = 3
```

Hours:

```text id="k642"
1 + 2 + 3 + 4
=
10
```

Invalid.

Search larger.

---

Eventually:

```text id="k643"
4
```

---

# What To Say In Interview

The answer space is monotonic.

If a speed works, every larger speed also works.

Therefore I can binary search the minimum valid speed.

---

# Optimal Java Code

```java id="k644"
class Solution {

    public int minEatingSpeed(
            int[] piles,
            int h
    ) {

        int left = 1;

        int right = 0;

        for(int pile : piles){

            right =
                    Math.max(
                        right,
                        pile
                    );
        }

        int answer = right;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(canFinish(
                    piles,
                    h,
                    mid
            )){

                answer = mid;

                right = mid - 1;

            }else{

                left = mid + 1;
            }
        }

        return answer;
    }

    private boolean canFinish(
            int[] piles,
            int h,
            int speed
    ){

        long hours = 0;

        for(int pile : piles){

            hours +=
              (pile + speed - 1)
              / speed;
        }

        return hours <= h;
    }
}
```

---

# Complexity

```text id="k645"
Time :

O(n log(maxPile))
```

---

# Why O(log(maxPile))?

Binary Search runs on:

```text id="k646"
Speed Range
```

not:

```text id="k647"
Array Length
```

---

# Interview Follow-Up

### Why Use Long?

Suppose:

```text id="k648"
100000 piles
```

Each:

```text id="k649"
10^9 bananas
```

Hours can overflow:

```java id="k650"
int
```

Use:

```java id="k651"
long
```

---

# Binary Search On Answer Template

This is the template you'll reuse forever.

```java id="k652"
int answer = right;

while(left <= right){

    int mid =
        left +
        (right-left)/2;

    if(can(mid)){

        answer = mid;

        right = mid - 1;

    }else{

        left = mid + 1;
    }
}
```

---

# Recognition Checklist

Whenever interviewer says:

### Minimum

```text id="k653"
Speed
```

---

### Minimum

```text id="k654"
Capacity
```

---

### Minimum

```text id="k655"
Days
```

---

### Minimum

```text id="k656"
Rate
```

---

### Minimum

```text id="k657"
Time
```

Ask:

```text id="k658"
Can I verify
a candidate answer?
```

If YES:

Think:

```text id="k659"
Binary Search
On Answer
```

---

# Koko Revision Sheet

Goal:

```text id="k660"
Minimum Eating Speed
```

Search Space:

```text id="k661"
1

to

maxPile
```

---

Can Function:

```java id="k662"
hours <= h
```

---

Pattern:

```text id="k663"
First Valid Answer
```

---

Visualization:

```text id="k664"
F F F F T T T T
```

Need:

```text id="k665"
First T
```

---

# Most Important Interview Insight

In classic Binary Search:

```text id="k666"
Array is sorted.
```

In Binary Search On Answer:

```text id="k667"
Answers are sorted.
```

That's the mindset shift interviewers are testing.

---

# Golden Rule

Whenever interviewer asks:

```text id="k668"
Minimum X
```

don't immediately think:

```text id="k669"
How do I calculate X?
```

Instead ask:

```text id="k670"
If I guess X,

can I verify
whether it works?
```

If the answer is yes,

you've probably discovered:

```text id="k671"
Binary Search On Answer
```
