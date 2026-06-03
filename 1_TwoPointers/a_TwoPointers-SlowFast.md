# Two Pointers Pattern - Part 1

# Template 1: Slow & Fast Pointer

## When To Use

Look for these clues:

* In-place modification
* Remove elements
* Move elements
* Maintain relative order
* No extra space allowed

## Generic Template

```java
int slow = 0;

for(int fast = 0; fast < nums.length; fast++) {

    if(validCondition(nums[fast])) {
        nums[slow] = nums[fast];
        slow++;
    }
}
```

---

# Problem 1: Remove Duplicates from Sorted Array

## LeetCode 26

---

## Pattern

Two Pointers (Slow & Fast)

---

## Interview Clues

### Clue 1

Array is sorted.

### Clue 2

Need unique elements only.

### Clue 3

Must modify array in-place.

Whenever you see:

```text
Sorted + Remove Duplicates + In-place
```

Think:

```text
Slow Fast Pointer
```

---

## Brute Force

### Idea

Create a new ArrayList.

Store only unique elements.

Copy back to original array.

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

## Optimal Approach

### Observation

Since the array is sorted:

```text
1 1 2 2 3 3 4
```

Duplicates are always adjacent.

We only need to keep track of the last unique element.

---

## Dry Run

```text
nums = [1,1,2,2,3]

slow = 0

fast = 1

nums[fast] == nums[slow]

skip

----------------

fast = 2

nums[fast] != nums[slow]

slow++

nums[slow] = nums[fast]

Array:

1 2 2 2 3

----------------

fast = 4

nums[fast] != nums[slow]

slow++

nums[slow] = nums[fast]

1 2 3 2 3
```

Answer length:

```text
slow + 1 = 3
```

---

## What To Say In Interview

Since the array is already sorted, duplicates will always appear together.

I'll maintain a slow pointer representing the last unique element and a fast pointer to scan the array.

Whenever a new unique element is found, I'll place it at the next available position and move slow forward.

This gives O(n) time and O(1) space.

---

## Optimal Java Code

```java
class Solution {

    public int removeDuplicates(int[] nums) {

        if(nums.length == 0) {
            return 0;
        }

        int slow = 0;

        for(int fast = 1; fast < nums.length; fast++) {

            if(nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;
    }
}
```

---

## Complexity

```text
Time  : O(n)
Space : O(1)
```

---

## Similar Problems

* Remove Element
* Move Zeroes
* Sort Colors

---

# Problem 2: Move Zeroes

## LeetCode 283

---

## Pattern

Two Pointers (Slow & Fast)

---

## Interview Clues

### Clue 1

Move elements.

### Clue 2

Maintain relative order.

### Clue 3

In-place.

Whenever you see:

```text
Move valid elements to front
```

Think:

```text
Slow Fast
```

---

## Brute Force

### Idea

Create a new array.

Store all non-zero elements.

Fill remaining positions with zeroes.

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

## Optimal Approach

### Observation

All non-zero elements should appear first.

Slow pointer tells where the next non-zero element should be placed.

Fast pointer scans.

---

## Dry Run

```text
0 1 0 3 12

slow = 0

fast = 1

swap(0,1)

1 0 0 3 12

slow = 1

----------------

fast = 3

swap(1,3)

1 3 0 0 12

slow = 2

----------------

fast = 4

swap(2,4)

1 3 12 0 0
```

---

## What To Say In Interview

I need to preserve the order of non-zero elements while moving all zeroes to the end.

A slow pointer tracks the next valid insertion position while a fast pointer scans the array.

Whenever a non-zero value is found, I swap it into the slow pointer position.

---

## Optimal Java Code

```java
class Solution {

    public void moveZeroes(int[] nums) {

        int slow = 0;

        for(int fast = 0; fast < nums.length; fast++) {

            if(nums[fast] != 0) {

                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;

                slow++;
            }
        }
    }
}
```

---

## Complexity

```text
Time  : O(n)
Space : O(1)
```

---

## Similar Problems

* Remove Duplicates
* Remove Element
* Sort Colors

---

# Problem 3: Remove Element

## LeetCode 27

---

## Pattern

Two Pointers (Slow & Fast)

---

## Interview Clues

### Clue 1

Remove specific value.

### Clue 2

In-place.

### Clue 3

Return new length.

This is almost identical to:

```text
Move Zeroes
```

---

## Brute Force

### Idea

Create another array.

Copy only valid elements.

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

## Optimal Approach

### Observation

Keep every element that is not equal to val.

Place valid elements at slow pointer.

---

## Dry Run

```text
nums = [3,2,2,3]

val = 3

slow = 0

fast = 0

skip

----------------

fast = 1

nums[1] = 2

nums[slow] = 2

slow = 1

----------------

fast = 2

nums[1] = 2

slow = 2
```

Final:

```text
2 2
```

Length:

```text
2
```

---

## What To Say In Interview

The problem only requires valid elements to remain at the beginning of the array.

I can use a slow pointer to indicate the next position where a valid element should be written.

The fast pointer scans all elements.

---

## Optimal Java Code

```java
class Solution {

    public int removeElement(int[] nums, int val) {

        int slow = 0;

        for(int fast = 0; fast < nums.length; fast++) {

            if(nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }

        return slow;
    }
}
```

---

## Complexity

```text
Time  : O(n)
Space : O(1)
```

---

## Similar Problems

* Remove Duplicates
* Move Zeroes

---

# Problem 4: Sort Colors

## LeetCode 75

---

## Pattern

Dutch National Flag

Special Two Pointer Variant

---

## Interview Clues

### Clue 1

Only 3 values.

```text
0 1 2
```

### Clue 2

Need sorting.

### Clue 3

One pass preferred.

Whenever you see:

```text
Only 3 categories
```

Think:

```text
Dutch National Flag
```

---

## Brute Force

### Idea

Use sorting.

```java
Arrays.sort(nums);
```

---

## Complexity

```text
Time  : O(n log n)
Space : Depends
```

---

## Better

Count frequency of:

```text
0
1
2
```

Then rewrite array.

---

## Complexity

```text
Time  : O(n)
Space : O(1)
```

---

## Optimal Approach

Three pointers:

```text
low
mid
high
```

Maintain:

```text
0 to low-1       -> all 0s

low to mid-1     -> all 1s

mid to high      -> unknown

high+1 to end    -> all 2s
```

---

## Dry Run

```text
2 0 2 1 1 0

low=0
mid=0
high=5

nums[mid]=2

swap(mid,high)

0 0 2 1 1 2

high--

----------------

nums[mid]=0

swap(low,mid)

low++
mid++
```

Continue until:

```text
0 0 1 1 2 2
```

---

## What To Say In Interview

Since there are only three possible values, I can partition the array into three regions using low, mid and high pointers.

This allows sorting in a single pass without extra space.

---

## Optimal Java Code

```java
class Solution {

    public void sortColors(int[] nums) {

        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while(mid <= high) {

            if(nums[mid] == 0) {

                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;

                low++;
                mid++;
            }

            else if(nums[mid] == 1) {
                mid++;
            }

            else {

                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;

                high--;
            }
        }
    }
}
```

---

## Complexity

```text
Time  : O(n)

Space : O(1)
```

---

## Similar Problems

* Partition Array
* Move Zeroes
* Segregate Even/Odd
* Dutch National Flag Variants

---

# Pattern Summary

## Same Template Problems

### Template: Slow Fast

```java
int slow = 0;

for(int fast = 0; fast < n; fast++) {

    if(valid) {
        nums[slow] = nums[fast];
        slow++;
    }
}
```

Problems:

1. Remove Duplicates
2. Move Zeroes
3. Remove Element

---

### Template: Dutch National Flag

```java
low
mid
high
```

Problems:

1. Sort Colors
2. Three-way Partition
3. Pivot Partition Problems
4. Quick Sort Partition Logic

```
```
