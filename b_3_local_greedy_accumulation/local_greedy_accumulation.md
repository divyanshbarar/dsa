## Similar Problems

This exact thinking appears in:

* **Jump Game I & II** (Tracking the maximum reachable index from your current position)
* **Gas Station** (Accumulating net gas balances to find a valid starting circuit)
* **Candy** (Distributing minimum candies based on local neighbor comparisons)
* **Greedy Interval Problems** (e.g., Non-overlapping Intervals, Minimum Meeting Rooms)

---

## Pattern Learned: Local Greedy Accumulation

### Core Idea:

Instead of evaluating all global pathways or future combinations using heavy search algorithms ($O(2^n)$ or $O(n^2)$), you make an irrevocable, optimal choice at your current local position.

If local profit/progress always contributes positively to your objective, you **accumulate it immediately** and update your global tracking horizons. By confirming that a local failure point strictly invalidates a whole sub-segment, you can skip re-evaluating that entire segment. This converts global optimization problems into streamlined **$O(n)$ time** single-pass algorithms.

---

### Tips to Look for This Pattern

1. **No Backtracking Required:** Once a local decision is made or a boundary is pushed, you never need to turn back and revise that choice.
2. **Segment Invalidation:** If a condition fails at index `i` (e.g., running out of gas or failing to reach the next index), it implies that *no starting position within the current looked-at segment could have bypassed this failure*.
3. **Horizon Scanning:** The problem requires you to constantly manage a dynamic boundary, such as `maxReachable` space, `currentGasBalance`, or `localPeak`.

---

## The Universal Java Template

```java
public int localGreedyTemplate(int[] nums) {
    int globalAccumulator = 0;
    int currentContextValue = 0;
    int failingCheckpoint = 0;

    for (int i = 0; i < nums.length; i++) {
        // Step 1: Accumulate local progress / update horizons
        currentContextValue += nums[i]; 
        
        // Step 2: Check if the local greedy choice has hit a structural wall
        if (currentContextValue < failingCheckpoint) {
            // Local failure tells us the entire segment up to 'i' is invalid
            globalAccumulator = i + 1; // Greedily reset baseline forward
            currentContextValue = 0;   // Clear out the failed accumulator context
        }
    }
    
    return globalAccumulator;
}

```

---

## Each Question and Its Solution

### 1. Jump Game

* **The Logic:** At each index, you calculate the maximum index you can reach (`i + nums[i]`). You greedily update your furthest horizon (`maxReach`). If at any point your current index `i` exceeds `maxReach`, it means you have stepped into an unreachable dead zone, and you can immediately conclude it's impossible.

```java
public boolean canJump(int[] nums) {
    int maxReach = 0;
    
    for (int i = 0; i < nums.length; i++) {
        // If the current index is past our maximum accumulated reach, we are stuck
        if (i > maxReach) {
            return false;
        }
        // Greedily update the furthest horizon we can touch
        maxReach = Math.max(maxReach, i + nums[i]);
    }
    
    return true;
}

```

### 2. Gas Station

* **The Logic:** You calculate your `totalTank` across the entire journey to verify if a solution is physically possible. Simultaneously, you track a `currentTank`. If `currentTank` drops below 0 at station `i`, it proves that starting at *any* station from your current starting point up to `i` is a failure. You greedily move your start candidate to `i + 1` and reset your local accumulator.

```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int totalTank = 0;
    int currentTank = 0;
    int startingStation = 0;
    
    for (int i = 0; i < gas.length; i++) {
        int netProfit = gas[i] - cost[i];
        totalTank += netProfit;
        currentTank += netProfit;
        
        // If we run out of fuel, the current starting point is a localized failure
        if (currentTank < 0) {
            // Greedily discard this entire segment and try starting at the next station
            startingStation = i + 1;
            currentTank = 0;
        }
    }
    
    // If the global sum is negative, a full circuit is completely impossible
    return totalTank >= 0 ? startingStation : -1;
}

```

### 3. Candy

* **The Logic:** This utilizes a two-pass local greedy strategy. First, walk from left to right: if a child has a higher rating than their left neighbor, greedily give them one more candy than the left neighbor. Then, walk from right to left: if a child has a higher rating than their right neighbor, make sure they have more candies than the right neighbor.

```java
public int candy(int[] ratings) {
    int n = ratings.length;
    int[] candies = new int[n];
    Arrays.fill(candies, 1); // Every child gets at least 1 candy initially
    
    // Left-to-Right Pass: Satisfy left neighbor constraint locally
    for (int i = 1; i < n; i++) {
        if (ratings[i] > ratings[i - 1]) {
            candies[i] = candies[i - 1] + 1;
        }
    }
    
    // Right-to-Left Pass: Satisfy right neighbor constraint locally
    int totalCandies = candies[n - 1];
    for (int i = n - 2; i >= 0; i--) {
        if (ratings[i] > ratings[i + 1]) {
            candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }
        totalCandies += candies[i]; // Accumulate into the absolute global count
    }
    
    return totalCandies;
}

```

### 4. Greedy Interval Problems (Non-overlapping Intervals)

* **The Logic:** To maximize the number of non-overlapping intervals (or minimize removals), sort intervals by their **end times**. You greedily select the interval that finishes earliest. This leaves the maximum possible room for all subsequent intervals.

```java
public int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Step 1: Sort the intervals by their end times ascending
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
    
    int count = 0;
    int endHorizon = intervals[0][1]; // Local tracking boundary
    
    for (int i = 1; i < intervals.length; i++) {
        // If the current interval starts before the previous one ends, they overlap
        if (intervals[i][0] < endHorizon) {
            count++; // Accumulate the removal count greedily
        } else {
            // No overlap, greedily update our endpoint horizon to the current one
            endHorizon = intervals[i][1];
        }
    }
    
    return count;
}

```