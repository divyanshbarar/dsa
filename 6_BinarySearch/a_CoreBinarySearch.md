# Binary Search Pattern - Part 1

# Classic Binary Search Family

---

# Why This Family Matters

This is the foundation of all Binary Search problems.

Every advanced Binary Search question eventually comes back to:

```java id="bs101"
left

mid

right
```

and learning:

```text id="bs102"
When to move left?

When to move right?
```

---

# Core Binary Search Template

```java id="bs103"
int left = 0;
int right = nums.length - 1;

while(left <= right){

    int mid =
            left +
            (right-left)/2;

    if(nums[mid] == target){

        return mid;
    }

    if(nums[mid] < target){

        left = mid + 1;

    }else{

        right = mid - 1;
    }
}

return -1;
```

---

# Pattern Recognition Clues

Think Classic Binary Search when:

### Clue 1

```text id="bs104"
Sorted Array
```

---

### Clue 2

```text id="bs105"
Find Target
```

---

### Clue 3

```text id="bs106"
Search Element
```

---

### Clue 4

```text id="bs107"
O(log n)
```

mentioned in problem.

---

# Golden Interview Rule

If array is:

```text id="bs108"
Sorted
```

and interviewer asks:

```text id="bs109"
Find Something
```

Think:

```text id="bs110"
Binary Search
```

before HashMap.

---

# Problem 1: Binary Search

## LeetCode 704

---

# Problem Explanation

Given:

```text id="bs111"
nums

[-1,0,3,5,9,12]
```

Find:

```text id="bs112"
target = 9
```

Return:

```text id="bs113"
index
```

Output:

```text id="bs114"
4
```

---

# What Is The Interviewer Testing?

Do you know:

```text id="bs115"
Classic Binary Search
```

?

This is the most fundamental interview question.

---

# Brute Force

Scan entire array.

```java id="bs116"
for(int i=0;i<n;i++){

    if(nums[i]==target){
        return i;
    }
}
```

---

# Complexity

```text id="bs117"
Time : O(n)

Space : O(1)
```

---

# Key Observation

Array is sorted.

After checking:

```text id="bs118"
mid
```

half of array becomes useless.

---

# Visualization

```text id="bs119"
-1 0 3 5 9 12

target = 9
```

Mid:

```text id="bs120"
5
```

Since:

```text id="bs121"
9 > 5
```

Discard left half.

---

Remaining:

```text id="bs122"
9 12
```

Find answer.

---

# Dry Run

```text id="bs123"
left = 0

right = 5
```

---

Mid:

```text id="bs124"
2
```

Value:

```text id="bs125"
3
```

Move:

```text id="bs126"
left = 3
```

---

Mid:

```text id="bs127"
4
```

Value:

```text id="bs128"
9
```

Found.

---

# What To Say In Interview

Since the array is sorted, after comparing the target with the middle element, one half can never contain the answer and can be discarded.

This reduces the search space by half every iteration.

---

# Optimal Java Code

```java id="bs129"
class Solution {

    public int search(
            int[] nums,
            int target
    ) {

        int left = 0;
        int right =
                nums.length - 1;

        while(left <= right){

            int mid =
                    left +
                    (right-left)/2;

            if(nums[mid] == target){

                return mid;
            }

            if(nums[mid] < target){

                left = mid + 1;

            }else{

                right = mid - 1;
            }
        }

        return -1;
    }
}
```

---

# Complexity

```text id="bs130"
Time :

O(log n)

Space :

O(1)
```

---

# Interview Follow-Up

### Why Not

```java id="bs131"
(left+right)/2
```

?

Because:

```text id="bs132"
Integer Overflow
```

can happen.

Use:

```java id="bs133"
left + (right-left)/2
```

---

# Problem 2: Search Insert Position

## LeetCode 35

---

# Problem Explanation

Given:

```text id="bs134"
[1,3,5,6]
```

Target:

```text id="bs135"
5
```

Answer:

```text id="bs136"
2
```

---

Target:

```text id="bs137"
2
```

Answer:

```text id="bs138"
1
```

because:

```text id="bs139"
2 should be inserted
at index 1
```

---

# What Is The Interviewer Testing?

Can you think beyond:

```text id="bs140"
Target Exists
```

?

Need:

```text id="bs141"
Target OR Insertion Point
```

---

# Pattern Recognition Clues

### Clue 1

Sorted array.

### Clue 2

Insert position.

### Clue 3

Smallest valid index.

Think:

```text id="bs142"
Lower Bound
```

---

# Key Insight

Answer equals:

```text id="bs143"
First Position

>= target
```

---

# Visualization

```text id="bs144"
1 3 5 6

target = 2
```

Need:

```text id="bs145"
First value >= 2
```

Which is:

```text id="bs146"
3
```

Index:

```text id="bs147"
1
```

---

# Brute Force

Scan until:

```text id="bs148"
nums[i] >= target
```

---

# Complexity

```text id="bs149"
Time : O(n)
```

---

# Optimal Approach

Binary Search.

Store candidate answer.

Continue searching left.

---

# Dry Run

```text id="bs150"
1 3 5 6

target = 2
```

Mid:

```text id="bs151"
5
```

Valid candidate.

Search left.

---

Mid:

```text id="bs152"
3
```

Better candidate.

Search left.

---

Answer:

```text id="bs153"
index = 1
```

---

# What To Say In Interview

I need the first position where the value becomes greater than or equal to the target.

This is a classic lower-bound binary search.

---

# Optimal Java Code

```java id="bs154"
class Solution {

    public int searchInsert(
            int[] nums,
            int target
    ) {

        int left = 0;
        int right =
                nums.length - 1;

        int answer =
                nums.length;

        while(left <= right){

            int mid =
                    left +
                    (right-left)/2;

            if(nums[mid] >= target){

                answer = mid;

                right = mid - 1;

            }else{

                left = mid + 1;
            }
        }

        return answer;
    }
}
```

---

# Complexity

```text id="bs155"
Time :

O(log n)

Space :

O(1)
```

---

# Interview Insight

This is your first:

```text id="bs156"
Boundary Search
```

question.

Very important.

---

# Problem 3: Guess Number Higher Or Lower

## LeetCode 374

---

# Problem Explanation

Pick a number:

```text id="bs157"
1 → n
```

Use API:

```java id="bs158"
guess(mid)
```

Returns:

```text id="bs159"
-1

0

1
```

Meaning:

```text id="bs160"
Higher

Correct

Lower
```

Find secret number.

---

# What Is The Interviewer Testing?

Can you identify:

```text id="bs161"
Search Space
```

instead of array?

---

# Pattern Recognition Clues

### Clue 1

Search between:

```text id="bs162"
1 and n
```

---

### Clue 2

Higher / Lower.

---

### Clue 3

Monotonic direction.

Think:

```text id="bs163"
Binary Search
```

---

# Key Observation

Array doesn't exist.

But search space exists:

```text id="bs164"
1

2

3

...

n
```

---

# Visualization

Secret:

```text id="bs165"
73
```

Range:

```text id="bs166"
1 → 100
```

---

Guess:

```text id="bs167"
50
```

Need higher.

Discard:

```text id="bs168"
1 → 50
```

---

Guess:

```text id="bs169"
75
```

Need lower.

Discard:

```text id="bs170"
75 → 100
```

---

Continue.

---

# Dry Run

```text id="bs171"
left = 1

right = 100
```

Mid:

```text id="bs172"
50
```

Need higher.

---

Mid:

```text id="bs173"
75
```

Need lower.

---

Mid:

```text id="bs174"
62
```

Need higher.

---

Eventually:

```text id="bs175"
73
```

Found.

---

# What To Say In Interview

Although there is no array, the search space from 1 to n is sorted.

After every guess, half the remaining possibilities can be discarded.

---

# Optimal Java Code

```java id="bs176"
public class Solution
        extends GuessGame {

    public int guessNumber(
            int n
    ) {

        int left = 1;
        int right = n;

        while(left <= right){

            int mid =
                    left +
                    (right-left)/2;

            int result =
                    guess(mid);

            if(result == 0){

                return mid;
            }

            if(result > 0){

                left = mid + 1;

            }else{

                right = mid - 1;
            }
        }

        return -1;
    }
}
```

---

# Complexity

```text id="bs177"
Time :

O(log n)

Space :

O(1)
```

---

# Classic Family Revision Sheet

## Binary Search

Need:

```text id="bs178"
Exact Target
```

Template:

```java id="bs179"
nums[mid] == target
```

---

## Search Insert Position

Need:

```text id="bs180"
First Position

>= target
```

Template:

```java id="bs181"
Lower Bound
```

---

## Guess Number

Need:

```text id="bs182"
Search Space
```

Template:

```java id="bs183"
Binary Search On Range
```

---

# Most Important Lesson From Part 1

Binary Search is NOT about arrays.

Binary Search is about:

```text id="bs184"
Discarding Half
Of Search Space
```

Every iteration.

That idea will power:

* First Bad Version
* Rotated Arrays
* Koko Eating Bananas
* Ship Packages
* Split Array Largest Sum

and almost every advanced Binary Search interview problem.

---

# Golden Rule

Whenever interviewer gives:

```text id="bs185"
Sorted Data

or

Higher / Lower Feedback

or

Search Space
```

Ask yourself:

```text id="bs186"
Can I eliminate
half the possibilities?
```

If yes:

```text id="bs187"
Binary Search
```

is probably the correct pattern.
