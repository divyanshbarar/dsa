## Similar Problems

This exact thinking appears in:

* **Subarrays with K Different Integers** (and variations like Count Binary Subarrays with Sum)
* **Number of Nice Subarrays** (Counting subarrays with exactly $k$ odd numbers)
* **Count Homogenous Substrings** (Tracking substrings consisting of the same characters)
* **Max Consecutive Ones (I, II & III)** (Calculating contiguous streams matching conditions)

---

## Pattern Learned: Running Contribution Pattern

### Core Idea:

Instead of trying to find every single individual valid subarray from scratch ($O(n^2)$ or $O(n^3)$), you observe that **the current element at your boundary creates an incremental cascade of new valid windows**.

As you iterate through the array, you calculate how many valid configurations *must* end at the current index `i`. You then take this local count and add it immediately to a global running tally. Whether tracking a contiguous streak length or matching a dynamic prefix sum frequency, the element at index `i` builds directly upon the work computed at index `i-1`. This tracking strategy solves complex combination queries in a highly optimal **$O(n)$ time** single pass.

---

### Tips to Look for This Pattern

1. **"Count the Number of Subarrays/Substrings":** The question asks for a total tally of contiguous blocks meeting a specific criteria, rather than just finding the single maximum length.
2. **Streak Dependence:** Adding a new matching element immediately expands your valid options by the length of the current streak (e.g., if you have `"aaa"` and add an `'a'`, you don't just gain `"aaaa"`, you also form `"aaa"`, `"aa"`, and `"a"` ending at that position).
3. **Difference of Thresholds ("Exactly K"):** When tracking exact matches, the total is often calculated as the contribution of items meeting a relaxed criteria minus a tighter one (e.g., $\text{exactly}(K) = \text{atMost}(K) - \text{atMost}(K-1)$).

---

## The Universal Java Template

### Variant A: Direct Streak Expansion

```java
public int streakContributionTemplate(String s) {
    int totalCount = 0;
    int currentStreak = 0;
    
    for (int i = 0; i < s.length(); i++) {
        if (conditionMet(s, i)) {
            currentStreak++; // The streak grows
        } else {
            currentStreak = 1; // Reset or establish a baseline new streak
        }
        
        // The current streak size is exactly how many valid windows end at index i
        totalCount += currentStreak; 
    }
    return totalCount;
}

```

### Variant B: Sliding Window Contribution

```java
public int slidingWindowContributionTemplate(int[] nums, int k) {
    int totalCount = 0;
    int left = 0;
    int currentMetrics = 0;

    for (int right = 0; right < nums.length; right++) {
        // Expand window context
        currentMetrics += nums[right];

        // Shrink from the left if the window breaks constraints
        while (currentMetrics > k) {
            currentMetrics -= nums[left];
            left++;
        }

        // Running Contribution: All subarrays starting from 'left' up to 'right' 
        // and ending strictly at 'right' are automatically valid.
        totalCount += (right - left + 1);
    }
    return totalCount;
}

```

---

## Each Question and Its Solution

### 1. Count Binary Subarrays (With Sum $K$)

* **The Logic:** Finding subarrays that sum up to *exactly* `goal` can be tricky because of trailing or leading zeros. We can simplify the contribution tracking by calculating the total subarrays with a sum $\le \text{goal}$ and subtracting the total subarrays with a sum $\le \text{goal} - 1$.

```java
public int numSubarraysWithSum(int[] nums, int goal) {
    return atMost(nums, goal) - atMost(nums, goal - 1);
}

private int atMost(int[] nums, int goal) {
    if (goal < 0) return 0;
    int left = 0, currentSum = 0, totalSubarrays = 0;
    
    for (int right = 0; right < nums.length; right++) {
        currentSum += nums[right];
        
        while (currentSum > goal) {
            currentSum -= nums[left];
            left++;
        }
        
        // Contribution logic: number of valid subarrays ending precisely at 'right'
        totalSubarrays += (right - left + 1);
    }
    return totalSubarrays;
}

```

### 2. Number of Nice Subarrays

* **The Logic:** A subarray is "nice" if it contains exactly `k` odd numbers. This maps identically to the binary subarrays problem: treat odd numbers as `1` and even numbers as `0`. We find the total count via $\text{atMost}(k) - \text{atMost}(k - 1)$.

```java
public int numberOfSubarrays(int[] nums, int k) {
    return atMostOdds(nums, k) - atMostOdds(nums, k - 1);
}

private int atMostOdds(int[] nums, int k) {
    if (k < 0) return 0;
    int left = 0, oddCount = 0, result = 0;
    
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] % 2 != 0) oddCount++;
        
        while (oddCount > k) {
            if (nums[left] % 2 != 0) oddCount--;
            left++;
        }
        
        // Every distinct starting point from left to right adds a unique valid subarray
        result += (right - left + 1);
    }
    return result;
}

```

### 3. Count Homogenous Substrings

* **The Logic:** A string is homogenous if all characters are identical. As you scan left-to-right, if the current character matches the previous one, it extends your current streak. The value of that `currentStreak` is immediately added to the total because it reflects the exact number of new homogenous substrings created by introducing that character.

```java
public int countHomogenous(String s) {
    int totalCount = 0;
    int currentStreak = 0;
    int MOD = 1_000_000_007;
    
    for (int i = 0; i < s.length(); i++) {
        // If it matches the previous character, grow the streak. Otherwise, reset to 1.
        if (i > 0 && s.charAt(i) == s.charAt(i - 1)) {
            currentStreak++;
        } else {
            currentStreak = 1;
        }
        
        // Running contribution added incrementally
        totalCount = (totalCount + currentStreak) % MOD;
    }
    
    return totalCount;
}

```

### 4. Max Consecutive Ones III

* **The Logic:** You are allowed to flip at most `k` zeros. Instead of tracking the total count of all combinations, this variant tracks the *maximum window size* generated by running contribution logic. The right pointer expands, and when our zero threshold is breached, the left pointer shifts forward to maintain a running valid state.

```java
public int longestOnes(int[] nums, int k) {
    int left = 0;
    int zeroCount = 0;
    int maxWindow = 0;
    
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] == 0) {
            zeroCount++;
        }
        
        // Shrink window if we've flipped too many zeros
        while (zeroCount > k) {
            if (nums[left] == 0) {
                zeroCount--;
            }
            left++;
        }
        
        // The contribution here updates our global record for the longest valid streak
        maxWindow = Math.max(maxWindow, right - left + 1);
    }
    
    return maxWindow;
}

```