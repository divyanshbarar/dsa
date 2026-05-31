## Similar Problems

This exact thinking appears in:

* **Longest Increasing Subsequence (LIS)** (Finding the length of the longest subsequence that is strictly increasing)
* **Russian Doll Envelopes** (2D variant of LIS sorting by width and finding LIS on heights)
* **Patience Sorting** (The underlying sorting algorithm that mimics stacking cards into piles)
* **Greedy Subsequence Problems** (Optimizing lengths by making local, optimal substitutions)

---

## Pattern Learned: Greedy Candidate Tracking (with Binary Search)

### Core Idea:

Instead of relying on a standard dynamic programming approach that takes $O(n^2)$ time, the **Greedy Candidate Tracking** pattern maintains an active list of the "most promising" candidates for subsequences.

As you iterate through the array, you attempt to extend your current longest sequence. If the current element cannot extend it, you use **Binary Search** to find its place inside your tracking list and overwrite the smallest element that is greater than or equal to it. This greedy replacement lowers the threshold values for future elements, opening up the possibility for even longer subsequences down the road. This strategy optimizes the runtime dramatically down to **$O(n \log n)$ time**.

---

### Tips to Look for This Pattern

1. **Subsequence vs. Subarray:** The problem asks for a *subsequence* (elements do not need to be contiguous, but must preserve their relative order).
2. **Optimization of Choices:** You need to find a maximum or minimum length based on an inequality condition (like $A[i] < A[j]$).
3. **Hidden 1D Profiles:** The problem can be reduced to tracking an ordered list of thresholds where a faster search mechanism (like `Arrays.binarySearch()`) can be leveraged.

---

## The Universal Java Template

```java
public int greedyCandidateTemplate(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    
    // tails array stores the smallest tail of all increasing subsequences found so far
    int[] tails = new int[nums.length];
    int len = 0; // Represents the size of our active candidate list
    
    for (int x : nums) {
        int i = 0, j = len;
        
        // Binary search to find the correct insertion point for x in tails[0...len]
        while (i < j) {
            int mid = (i + j) / 2;
            if (tails[mid] < x) { // Change to <= if looking for non-decreasing
                i = mid + 1;
            } else {
                j = mid;
            }
        }
        
        // Update the candidate list
        tails[i] = x;
        
        // If x was placed at the end, it means we extended our longest subsequence
        if (i == len) {
            len++;
        }
    }
    
    return len;
}

```

---

## Each Question and Its Solution

### 1. Longest Increasing Subsequence

* **The Logic:** We directly track the smallest tail of all increasing subsequences of various lengths. For every number, if it is larger than all current tails, it starts a new length bracket. Otherwise, it updates an existing bracket to be more competitive (smaller).

```java
public int lengthOfLIS(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    
    int[] tails = new int[nums.length];
    int size = 0;
    
    for (int x : nums) {
        int i = 0, j = size;
        while (i < j) {
            int mid = (i + j) / 2;
            if (tails[mid] < x) {
                i = mid + 1;
            } else {
                j = mid;
            }
        }
        
        tails[i] = x;
        if (i == size) size++;
    }
    
    return size;
}

```

### 2. Russian Doll Envelopes

* **The Logic:** You can fit an envelope into another if both dimensions are strictly greater. We sort the envelopes primarily by width in ascending order, and secondarily by height in **descending order**. Why descending? It prevents an envelope of the same width from being nested into another of the same width. Once sorted, finding the answer is simply running the standard 1D LIS pattern on the heights.

```java
public int maxEnvelopes(int[][] envelopes) {
    if (envelopes == null || envelopes.length == 0) return 0;
    
    // Step 1: Sort width ascending, height descending
    Arrays.sort(envelopes, (a, b) -> {
        if (a[0] == b[0]) {
            return b[1] - a[1]; // Height descending
        }
        return a[0] - a[0]; // Width ascending
    });
    
    // Step 2: Perform standard LIS on heights
    int[] tails = new int[envelopes.length];
    int size = 0;
    
    for (int[] envelope : envelopes) {
        int h = envelope[1];
        int i = 0, j = size;
        while (i < j) {
            int mid = (i + j) / 2;
            if (tails[mid] < h) {
                i = mid + 1;
            } else {
                j = mid;
            }
        }
        tails[i] = h;
        if (i == size) size++;
    }
    
    return size;
}

```

### 3. Patience Sorting

* **The Logic:** This is the underlying algorithm structure of Greedy Candidate Tracking. Imagine dealing playing cards into piles. You can only place a card on top of an existing pile if that card is smaller than or equal to the top card of the pile. Otherwise, you must create a new pile to the right. To minimize the number of piles, you greedily pick the leftmost valid pile using binary search. The total number of piles at the end equals the length of the LIS.

```java
public int patienceSortLength(int[] cards) {
    List<Integer> piles = new ArrayList<>();
    
    for (int card : cards) {
        // Use Java's built-in binary search to find the candidate pile
        int idx = Collections.binarySearch(piles, card);
        
        // If not found, binarySearch returns (-(insertion point) - 1)
        if (idx < 0) {
            idx = -(idx + 1);
        }
        
        // If idx equals piles size, we must open a brand new pile
        if (idx == piles.size()) {
            piles.add(card);
        } else {
            // Otherwise, replace the top of the existing pile with this smaller candidate
            piles.set(idx, card);
        }
    }
    
    return piles.size();
}

```