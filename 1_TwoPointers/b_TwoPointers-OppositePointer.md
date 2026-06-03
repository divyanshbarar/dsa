# Two Pointers Pattern - Part 2

# Template 2: Opposite Direction Pointers

## When To Use

Look for these clues:

* Sorted array
* Pair sum problems
* Triplet problems
* Compare characters from both ends
* Maximize/minimize something between two indices

## Generic Template

```java
int left = 0;
int right = nums.length - 1;

while(left < right){

    if(conditionMet){
        // answer
    }
    else if(needSmallerValue){
        right--;
    }
    else{
        left++;
    }
}
```

---

# Problem 5: Two Sum II - Input Array Is Sorted

## LeetCode 167

---

## Pattern

Opposite Direction Two Pointers

---

## Interview Clues

### Clue 1

Array is sorted.

### Clue 2

Need pair sum.

### Clue 3

Only one valid answer exists.

Whenever you see:

```text
Sorted + Pair Sum
```

Think:

```text
Two Pointers
```

---

## Brute Force

### Idea

Try every pair.

```java
for(int i = 0; i < n; i++){
    for(int j = i + 1; j < n; j++){
        if(nums[i] + nums[j] == target){
            return answer;
        }
    }
}
```

---

## Complexity

```text
Time : O(n²)
Space: O(1)
```

---

## Better Approach

HashMap

---

## Complexity

```text
Time : O(n)
Space: O(n)
```

---

## Optimal Approach

### Observation

Array is sorted.

If current sum is too large:

```text
Move right
```

If current sum is too small:

```text
Move left
```

No need to check all pairs.

---

## Dry Run

```text
2 7 11 15

target = 9

left = 0
right = 3

2 + 15 = 17

Too large

right--

----------------

2 + 11 = 13

Too large

right--

----------------

2 + 7 = 9

Found
```

---

## What To Say In Interview

Since the array is sorted, I can leverage the ordering.

If the current sum is smaller than target, moving left forward increases the sum.

If the sum is larger, moving right backward decreases the sum.

This avoids checking every pair.

---

## Optimal Java Code

```java
class Solution {

    public int[] twoSum(int[] numbers, int target) {

        int left = 0;
        int right = numbers.length - 1;

        while(left < right) {

            int sum = numbers[left] + numbers[right];

            if(sum == target) {
                return new int[]{left + 1, right + 1};
            }

            if(sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[]{};
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

* Pair With Given Sum
* 3Sum
* 4Sum

---

# Problem 6: Reverse String

## LeetCode 344

---

## Pattern

Opposite Direction Pointers

---

## Interview Clues

### Clue 1

Reverse in-place.

### Clue 2

String/Character Array.

### Clue 3

Compare both ends.

---

## Brute Force

Create another string.

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

## Optimal Approach

Swap both ends.

Move inward.

---

## Dry Run

```text
h e l l o

left = 0
right = 4

swap

o e l l h

left++
right--
```

---

## What To Say In Interview

The first character belongs at the last position and vice versa.

Using two pointers allows reversing the string in-place.

---

## Optimal Java Code

```java
class Solution {

    public void reverseString(char[] s) {

        int left = 0;
        int right = s.length - 1;

        while(left < right) {

            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;

            left++;
            right--;
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

# Problem 7: Valid Palindrome

## LeetCode 125

---

## Pattern

Opposite Direction Pointers

---

## Interview Clues

### Clue 1

Compare both ends.

### Clue 2

Ignore special characters.

### Clue 3

Case insensitive.

---

## Brute Force

Build cleaned string.

Reverse it.

Compare.

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

## Optimal Approach

Use two pointers.

Skip non-alphanumeric characters.

Compare lowercase characters.

---

## Dry Run

```text
A man, a plan, a canal: Panama

left -> A
right -> a

Match

Move inward

Continue
```

---

## What To Say In Interview

Instead of creating a new string, I can validate the palindrome in-place using two pointers while skipping invalid characters.

---

## Optimal Java Code

```java
class Solution {

    public boolean isPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while(left < right) {

            while(left < right &&
                    !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            while(left < right &&
                    !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            char l = Character.toLowerCase(s.charAt(left));
            char r = Character.toLowerCase(s.charAt(right));

            if(l != r) {
                return false;
            }

            left++;
            right--;
        }

        return true;
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

# Problem 8: Container With Most Water

## LeetCode 11

---

## Pattern

Opposite Direction Pointers

---

## Interview Clues

### Clue 1

Area between two indices.

### Clue 2

Need maximum value.

### Clue 3

Brute force is O(n²).

---

## Formula

```text
Area =
(width) × min(height[left], height[right])
```

---

## Brute Force

Try every pair.

---

## Complexity

```text
Time : O(n²)
Space: O(1)
```

---

## Optimal Approach

### Key Observation

Water level depends on:

```text
shorter wall
```

The taller wall is never limiting.

Therefore:

```text
Move shorter pointer
```

---

## What To Say In Interview

The area is limited by the shorter wall.

Moving the taller wall cannot increase the height limit, so only the shorter wall has potential to improve the answer.

---

## Optimal Java Code

```java
class Solution {

    public int maxArea(int[] height) {

        int left = 0;
        int right = height.length - 1;

        int answer = 0;

        while(left < right) {

            int width = right - left;

            int area = width *
                    Math.min(height[left], height[right]);

            answer = Math.max(answer, area);

            if(height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return answer;
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

* Trapping Rain Water
* Max Distance Problems

---

# Problem 9: Squares Of A Sorted Array

## LeetCode 977

---

## Pattern

Opposite Direction Pointers

---

## Interview Clues

### Clue 1

Array sorted.

### Clue 2

Negative values exist.

### Clue 3

Need sorted squares.

---

## Observation

Largest square can come from:

```text
left end
or
right end
```

Because:

```text
(-10)^2 > (4)^2
```

---

## Brute Force

Square everything.

Sort again.

---

## Complexity

```text
Time : O(n log n)
Space: O(n)
```

---

## Optimal Approach

Compare:

```java
Math.abs(nums[left])
Math.abs(nums[right])
```

Place larger square from the end.

---

## Dry Run

```text
-7 -3 2 3 11

121 is largest

place at end

49 next

continue
```

---

## What To Say In Interview

Although the original array is sorted, squaring breaks the order because negative values become positive.

The largest square must come from one of the ends.

---

## Optimal Java Code

```java
class Solution {

    public int[] sortedSquares(int[] nums) {

        int n = nums.length;

        int[] answer = new int[n];

        int left = 0;
        int right = n - 1;
        int index = n - 1;

        while(left <= right) {

            int leftSquare =
                    nums[left] * nums[left];

            int rightSquare =
                    nums[right] * nums[right];

            if(leftSquare > rightSquare) {

                answer[index] = leftSquare;
                left++;

            } else {

                answer[index] = rightSquare;
                right--;
            }

            index--;
        }

        return answer;
    }
}
```

---

## Complexity

```text
Time  : O(n)
Space : O(n)
```

---

# Problem 10: 3Sum

## LeetCode 15

---

## Pattern

Sorting + Two Pointers

---

## Interview Clues

### Clue 1

Triplet Sum.

### Clue 2

Need unique triplets.

### Clue 3

Brute force uses 3 loops.

---

## Brute Force

Three nested loops.

---

## Complexity

```text
Time : O(n³)
Space: O(1)
```

---

## Optimal Approach

### Step 1

Sort array.

### Step 2

Fix one number.

### Step 3

Run Two Sum on remaining array.

---

## Dry Run

```text
[-4,-1,-1,0,1,2]

Fix -1

Need pair sum = 1

Use two pointers

Find:

-1 0 1

-1 -1 2
```

---

## What To Say In Interview

I can reduce the three-loop brute force solution by fixing one element and solving the remaining problem as Two Sum using opposite-direction pointers.

---

## Optimal Java Code

```java
class Solution {

    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);

        List<List<Integer>> answer = new ArrayList<>();

        for(int i = 0; i < nums.length - 2; i++) {

            if(i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while(left < right) {

                int sum =
                        nums[i] +
                        nums[left] +
                        nums[right];

                if(sum == 0) {

                    answer.add(
                        Arrays.asList(
                                nums[i],
                                nums[left],
                                nums[right]
                        )
                    );

                    while(left < right &&
                            nums[left] == nums[left + 1]) {
                        left++;
                    }

                    while(left < right &&
                            nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;

                }

                else if(sum < 0) {
                    left++;
                }

                else {
                    right--;
                }
            }
        }

        return answer;
    }
}
```

---

## Complexity

```text
Time  : O(n²)

Space : O(1)
```

(ignoring output list)

---

# Pattern Summary

## Same Template

```java
int left = 0;
int right = n - 1;

while(left < right){
    ...
}
```

Problems:

1. Two Sum II
2. Reverse String
3. Valid Palindrome
4. Container With Most Water
5. Squares of Sorted Array

---

## Advanced Variant

```java
Sort + Fix One Element + Two Pointers
```

Problems:

1. 3Sum
2. 4Sum
3. Closest 3Sum
4. K-Sum Family

```
```
