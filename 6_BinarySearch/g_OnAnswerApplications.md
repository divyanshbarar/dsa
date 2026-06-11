# Binary Search Pattern - Part 7

# Binary Search On Answer (Applications)

---

# Why This Part Matters

Part 6 taught:

```text id="b701"
Koko Eating Bananas
```

The real goal wasn't Koko.

The goal was learning:

```java id="b702"
can(mid)
```

because this single template solves:

* Ship Packages
* Bouquets
* Allocate Books
* Painter Partition
* Aggressive Cows
* Split Array Largest Sum

---

# Master Template

```java id="b703"
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

# Problem 16: Capacity To Ship Packages Within D Days

## LeetCode 1011

---

# Problem Explanation

Packages:

```text id="b704"
1 2 3 4 5 6 7 8 9 10
```

Days:

```text id="b705"
5
```

Need:

```text id="b706"
Minimum Ship Capacity
```

---

# Example

Capacity:

```text id="b707"
15
```

Day 1:

```text id="b708"
1+2+3+4+5
=
15
```

Day 2:

```text id="b709"
6+7
=
13
```

Day 3:

```text id="b710"
8
```

Day 4:

```text id="b711"
9
```

Day 5:

```text id="b712"
10
```

Works.

Answer:

```text id="b713"
15
```

---

# What Is The Interviewer Testing?

Can you transform:

```text id="b714"
Minimum Capacity
```

into:

```java id="b715"
Can(capacity)?
```

---

# Pattern Recognition Clues

### Clue 1

Minimum Capacity.

### Clue 2

Minimum Weight Limit.

### Clue 3

Minimum Resource.

Think:

```text id="b716"
Binary Search On Answer
```

---

# Search Space

Minimum Capacity:

```text id="b717"
max(weights)
```

Why?

Because largest package must fit.

---

Maximum Capacity:

```text id="b718"
sum(weights)
```

Ship everything in one day.

---

# The Can Function

Question:

```text id="b719"
Can I ship
within D days
using capacity X?
```

---

# Simulation Logic

Keep adding packages.

If capacity exceeded:

```text id="b720"
New Day
```

---

# Dry Run

Capacity:

```text id="b721"
15
```

Days used:

```text id="b722"
5
```

Valid.

---

Capacity:

```text id="b723"
10
```

Days used:

```text id="b724"
7
```

Invalid.

---

Monotonic:

```text id="b725"
10 -> F

11 -> F

12 -> F

15 -> T

16 -> T
```

Need:

```text id="b726"
First True
```

---

# What To Say In Interview

The answer space is monotonic.

If a capacity works, every larger capacity also works.

Therefore I can binary search the minimum valid capacity.

---

# Optimal Java Code

```java id="b727"
class Solution {

    public int shipWithinDays(
            int[] weights,
            int days
    ) {

        int left = 0;
        int right = 0;

        for(int w : weights){

            left =
                Math.max(left, w);

            right += w;
        }

        int answer = right;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(canShip(
                    weights,
                    days,
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

    private boolean canShip(
            int[] weights,
            int days,
            int capacity
    ){

        int usedDays = 1;

        int currentLoad = 0;

        for(int weight : weights){

            if(currentLoad
                + weight
                > capacity){

                usedDays++;

                currentLoad = 0;
            }

            currentLoad += weight;
        }

        return usedDays <= days;
    }
}
```

---

# Complexity

```text id="b728"
Time :

O(n log(sum))
```

---

# Interview Insight

Koko:

```text id="b729"
Minimum Speed
```

Ship Packages:

```text id="b730"
Minimum Capacity
```

Same pattern.

---

# Problem 17: Minimum Number Of Days To Make m Bouquets

## LeetCode 1482

---

# Problem Explanation

Flowers:

```text id="b731"
1 10 3 10 2
```

Need:

```text id="b732"
m = 3 bouquets
```

Each bouquet:

```text id="b733"
k = 1 flower
```

Answer:

```text id="b734"
3 days
```

---

# Meaning

Flower blooms on:

```text id="b735"
bloomDay[i]
```

Need:

```text id="b736"
Minimum Days
```

to create:

```text id="b737"
m bouquets
```

---

# What Is The Interviewer Testing?

Can you identify:

```text id="b738"
Days
```

as the answer space?

---

# Pattern Recognition Clues

### Clue 1

Minimum Days.

### Clue 2

Earliest Time.

### Clue 3

Smallest Valid Day.

Think:

```text id="b739"
Binary Search On Answer
```

---

# Search Space

Minimum:

```text id="b740"
min(bloomDay)
```

---

Maximum:

```text id="b741"
max(bloomDay)
```

---

# Can Function

Question:

```text id="b742"
Can I make
m bouquets
by day X?
```

---

# Key Observation

Flower usable if:

```java id="b743"
bloomDay[i]
<= day
```

---

# Bouquet Logic

Need:

```text id="b744"
k consecutive flowers
```

---

# Dry Run

Flowers:

```text id="b745"
1 10 3 10 2
```

Day:

```text id="b746"
3
```

Available:

```text id="b747"
Y N Y N Y
```

Bouquets:

```text id="b748"
3
```

Valid.

---

Day:

```text id="b749"
2
```

Available:

```text id="b750"
Y N N N Y
```

Only:

```text id="b751"
2 bouquets
```

Invalid.

---

Monotonic:

```text id="b752"
1 -> F

2 -> F

3 -> T

4 -> T

5 -> T
```

Need:

```text id="b753"
First True
```

---

# What To Say In Interview

If a certain day works, every later day also works because more flowers become available.

That creates a monotonic search space suitable for binary search.

---

# Optimal Java Code

```java id="b754"
class Solution {

    public int minDays(
            int[] bloomDay,
            int m,
            int k
    ) {

        long need =
                (long)m * k;

        if(need > bloomDay.length){

            return -1;
        }

        int left =
                Integer.MAX_VALUE;

        int right =
                Integer.MIN_VALUE;

        for(int day : bloomDay){

            left =
                Math.min(left, day);

            right =
                Math.max(right, day);
        }

        int answer = right;

        while(left <= right){

            int mid =
                left +
                (right-left)/2;

            if(canMake(
                    bloomDay,
                    m,
                    k,
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

    private boolean canMake(
            int[] bloomDay,
            int m,
            int k,
            int day
    ){

        int bouquets = 0;

        int flowers = 0;

        for(int bloom : bloomDay){

            if(bloom <= day){

                flowers++;

                if(flowers == k){

                    bouquets++;

                    flowers = 0;
                }

            }else{

                flowers = 0;
            }
        }

        return bouquets >= m;
    }
}
```

---

# Complexity

```text id="b755"
Time :

O(n log(maxDay))
```

---

# Binary Search On Answer Comparison

| Problem       | Answer Space | can(mid)           |
| ------------- | ------------ | ------------------ |
| Koko          | Speed        | Finish in h hours? |
| Ship Packages | Capacity     | Ship in d days?    |
| Bouquets      | Days         | Make m bouquets?   |

---

# Recognition Cheat Sheet

Whenever interviewer says:

```text id="b756"
Minimum Speed
```

Think:

```java id="b757"
can(speed)
```

---

Whenever interviewer says:

```text id="b758"
Minimum Capacity
```

Think:

```java id="b759"
can(capacity)
```

---

Whenever interviewer says:

```text id="b760"
Minimum Days
```

Think:

```java id="b761"
can(days)
```

---

# Most Important Interview Insight

The actual answer is usually hard to compute.

But verifying a guessed answer is easy.

That is exactly when:

```text id="b762"
Binary Search On Answer
```

becomes the optimal solution.

---

# Golden Rule

Whenever you hear:

```text id="b763"
Minimum

Maximum

Earliest

Smallest Valid
```

ask:

```text id="b764"
Can I verify
a candidate answer?
```

If yes:

```java id="b765"
Binary Search
On Answer
```

should be your first thought.
