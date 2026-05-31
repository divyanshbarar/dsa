## Similar Problems

This exact thinking appears in:

* **Maximum Difference** (Finding the maximum $A[j] - A[i]$ such that $j > i$)
* **Maximum Subarray** (Kadane’s Algorithm)
* **Best Time to Buy & Sell Stock** (Both I and II)
* **Maximum Product Subarray** (Tracking running minimums and maximums simultaneously)
* **Prefix/Suffix Optimization Problems** (Precomputing accumulated context from the left or right)

---

## Pattern Learned: Running Minimum / Prefix Information Pattern

### Core Idea:

Instead of using a brute-force nested loop to check every possible pair or subarray ($O(n^2)$ time), you **traverse the array linearly while maintaining a snapshot of the historical context up to that point**.

As you iterate through each element, you treat the current element as the *end* or *right-hand side* of your solution, and you use an accumulated variable (like a `runningMinimum` or `prefixSum`) to instantly provide the optimal *left-hand side* context in $O(1)$ time. This transforms an $O(n^2)$ search space into an optimized **$O(n)$ time** single pass.

---

### Tips to Look for This Pattern

1. **Dependent Pairs ($j > i$):** The problem requires comparing two elements or tracking a window where the order of operations matters sequentially.
2. **Greedy Local Decisions:** At any index `i`, the best possible choice depends entirely on the "best so far" value encountered from index `0` to `i-1`.
3. **Cumulative Metrics:** Phrases like "maximum profit," "largest sum subarray," or "min/max boundary" indicate that holding a single scalar history variable will eliminate redundant inner loops.

---

## The Universal Java Template

```java
public int runningContextTemplate(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    
    int globalMax = Integer.MIN_VALUE; // Or another appropriate baseline
    int runningHistory = nums[0];      // Can track min, sum, or product accumulated so far
    
    for (int i = 1; i < nums.length; i++) {
        // Step 1: Update your local/running context with the current element
        runningHistory = Math.min(runningHistory, nums[i]); // e.g., tracking tracking the lowest valley
        
        // Step 2: Evaluate the potential optimal answer ending at the current index
        int currentPotentialAnswer = nums[i] - runningHistory;
        
        // Step 3: Update the global best
        globalMax = Math.max(globalMax, currentPotentialAnswer);
    }
    
    return globalMax;
}

```

---

## Each Question and Its Solution

### 1. Maximum Difference (Best Time to Buy and Sell Stock I)

* **The Logic:** You want to maximize $nums[i] - nums[j]$ where $i > j$. As you walk forward, keep track of the lowest value seen so far (`minSoFar`). The maximum difference at the current step is simply the current value minus that minimum value.

```java
public int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0) return 0;
    
    int minPrice = prices[0]; // Running minimum
    int maxProfit = 0;        // Global maximum
    
    for (int i = 1; i < prices.length; i++) {
        // If current price is lower, it becomes our new baseline prefix information
        minPrice = Math.min(minPrice, prices[i]);
        
        // Calculate profit if we sold today using our running optimal buy price
        maxProfit = Math.max(maxProfit, prices[i] - minPrice);
    }
    return maxProfit;
}

```

### 2. Maximum Subarray (Kadane’s Algorithm)

* **The Logic:** Instead of a running minimum value, you maintain a running **prefix sum**. At each element, you decide whether to append the current element to the existing prefix sum, or throw away a negative prefix sum history and restart fresh from the current element.

```java
public int maxSubArray(int[] nums) {
    int currentSum = nums[0]; // Running context (max sum subarray ending at i)
    int maxSum = nums[0];     // Global maximum subarray sum
    
    for (int i = 1; i < nums.length; i++) {
        // Decision: Continue the current subarray prefix, or start a new subarray here
        currentSum = Math.max(nums[i], currentSum + nums[i]);
        maxSum = Math.max(maxSum, currentSum);
    }
    return maxSum;
}

```

### 3. Best Time to Buy & Sell Stock II

* **The Logic:** Instead of seeking one global maximum difference, you want to collect *all* upward trends. The running context is purely immediate local history: if today's price is higher than yesterday's price, you log that positive difference immediately to your accumulated sum.

```java
public int maxProfitII(int[] prices) {
    int totalProfit = 0;
    
    // Running context is simply the immediate previous element (prices[i-1])
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            totalProfit += prices[i] - prices[i - 1];
        }
    }
    return totalProfit;
}

```

### 4. Maximum Product Subarray

* **The Logic:** Multiplying two negative numbers creates a positive number. Therefore, a running maximum product could suddenly turn into a minimum if multiplied by a negative number, and vice-versa. The prefix information required here tracks **both** the `runningMax` and the `runningMin` simultaneously.

```java
public int maxProduct(int[] nums) {
    int maxSoFar = nums[0];
    int minSoFar = nums[0]; // Crucial running historical prefix context
    int globalMax = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        int current = nums[i];
        
        // If current is negative, multiplying swaps the maximum and minimum potentials
        if (current < 0) {
            int temp = maxSoFar;
            maxSoFar = minSoFar;
            minSoFar = temp;
        }
        
        // Update both running contexts
        maxSoFar = Math.max(current, maxSoFar * current);
        minSoFar = Math.min(current, minSoFar * current);
        
        globalMax = Math.max(globalMax, maxSoFar);
    }
    return globalMax;
}

```