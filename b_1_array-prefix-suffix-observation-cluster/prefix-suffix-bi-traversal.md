## Similar Problems

This exact thinking appears in:

* **Trapping Rain Water** (Computing left/right maximum height boundaries)
* **Prefix Sum / Range Sum Queries** (Precalculating cumulative prefix statistics)
* **Maximum Product Subarray** (Precomputing forward and backward scanning products)
* **Range Product Queries** (Using rolling prefix products to achieve $O(1)$ query times)
* **Rainwater Variations** (Building coordinate arrays mapping structural boundaries from both directions)

---

## Pattern Learned: Bidirectional Precomputation / Prefix-Suffix Accumulation

### Core Idea:

Instead of dynamically calculating constraints for every position using nested loops, you **precompute structural context independently from both ends of the array**.

By running one pass from left-to-right to build a **Prefix** array, and another pass from right-to-left to build a **Suffix** array, you compress global structural context into localized, $O(1)$ lookup values. When you evaluate an individual index, you can instantly combine the total information known from its left with the total information known from its right to make an optimized, absolute local decision in **$O(n)$ time and space**.

---

### Tips to Look for This Pattern

1. **Symmetric Constraints:** The outcome at a specific position depends equally on what lies entirely to its left and what lies entirely to its right (e.g., boundaries, slopes, or products).
2. **Range Query Workloads:** You are asked to answer multiple queries across arbitrary ranges $[L, R]$ where a simple forward scan would be too slow.
3. **The "Surrounded" Metric:** Problems that describe an index being bottlenecked, bounded, trapped, or evaluated based on its absolute global horizons.

---

## The Universal Java Template

```java
public int[] bidirectionalTemplate(int[] nums) {
    int n = nums.length;
    int[] prefix = new int[n];
    int[] suffix = new int[n];
    
    // Step 1: Precompute left-to-right prefix information
    prefix[0] = nums[0];
    for (int i = 1; i < n; i++) {
        prefix[i] = combine(prefix[i - 1], nums[i]); // e.g., Math.max, addition, multiplication
    }
    
    // Step 2: Precompute right-to-left suffix information
    suffix[n - 1] = nums[n - 1];
    for (int i = n - 2; i >= 0; i--) {
        suffix[i] = combine(suffix[i + 1], nums[i]);
    }
    
    // Step 3: Combine both perspectives to construct the final answer
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        result[i] = evaluateContext(prefix[i], suffix[i], nums[i]);
    }
    
    return result;
}

```

---

## Each Question and Its Solution

### 1. Trapping Rain Water

* **The Logic:** The amount of water trapped above any bar `i` is determined by the minimum of the tallest bar to its left and the tallest bar to its right, minus the height of the bar itself. We precalculate these global walls beforehand.

```java
public int trap(int[] height) {
    if (height == null || height.length == 0) return 0;
    int n = height.length;
    
    int[] leftMax = new int[n];
    int[] rightMax = new int[n];
    
    // Precompute tallest bar to the left of i
    leftMax[0] = height[0];
    for (int i = 1; i < n; i++) {
        leftMax[i] = Math.max(leftMax[i - 1], height[i]);
    }
    
    // Precompute tallest bar to the right of i
    rightMax[n - 1] = height[n - 1];
    for (int i = n - 2; i >= 0; i--) {
        rightMax[i] = Math.max(rightMax[i + 1], height[i]);
    }
    
    // Calculate water trapped at each index
    int totalWater = 0;
    for (int i = 0; i < n; i++) {
        totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
    }
    
    return totalWater;
}

```

### 2. Prefix Sum (Range Sum Query - Immutable)

* **The Logic:** To answer how many elements sum up between index `i` and `j` in $O(1)$ time, we precompute a rolling total from the left. The sum of range $[i, j]$ is simply the absolute sum up to `j` minus the absolute sum up to `i-1`.

```java
class NumArray {
    private int[] prefixSums;

    public NumArray(int[] nums) {
        prefixSums = new int[nums.length + 1];
        // Populate prefix sums
        for (int i = 0; i < nums.length; i++) {
            prefixSums[i + 1] = prefixSums[i] + nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        // O(1) evaluation using our precomputed prefix context
        return prefixSums[right + 1] - prefixSums[left];
    }
}

```

### 3. Maximum Product Subarray (Bidirectional Approach)

* **The Logic:** An odd number of negative elements can break a product sequence. However, the maximum product subarray *must* start from either the left boundary or the right boundary. By tracking running products simultaneously from left-to-right and right-to-left, we guarantee capturing the optimal segment.

```java
public int maxProduct(int[] nums) {
    int n = nums.length;
    int maxProduct = nums[0];
    
    int leftProduct = 0;
    int rightProduct = 0;
    
    for (int i = 0; i < n; i++) {
        // If we encounter a 0, reset our prefix/suffix accumulation context to 1
        leftProduct =  (leftProduct == 0 ? 1 : leftProduct) * nums[i];
        rightProduct = (rightProduct == 0 ? 1 : rightProduct) * nums[n - 1 - i];
        
        maxProduct = Math.max(maxProduct, Math.max(leftProduct, rightProduct));
    }
    
    return maxProduct;
}

```

### 4. Range Product Queries (Product of Array Except Self)

* **The Logic:** You need to return an array where `output[i]` is the product of all elements except `nums[i]`. This is cleanly done by calculating the prefix product of everything before `i` and multiplying it by the suffix product of everything after `i`.

```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    
    // Step 1: Use the output array to store the prefix products
    result[0] = 1;
    for (int i = 1; i < n; i++) {
        result[i] = result[i - 1] * nums[i - 1];
    }
    
    // Step 2: Calculate suffix product on the fly and merge it into our prefix results
    int suffixProduct = 1;
    for (int i = n - 1; i >= 0; i--) {
        result[i] = result[i] * suffixProduct;
        suffixProduct *= nums[i];
    }
    
    return result;
}

```