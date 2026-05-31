## Similar Problems

This exact logic appears in:

* **Remove Duplicates from Sorted Array** (and **Keep Duplicates At Most Twice**)
* **Remove Element** (In-place filtering of a target value)
* **Move Zeroes** (Pushing specific elements to the end while maintaining order)
* **Partition Array / Sort Colors** (Two or three-way partitioning)
* **Stable Filtering Problems** (Filtering arrays based on a predicate while maintaining relative order)

---

## Pattern Learned: In-place Filtering / Compaction

Unlike the **Cyclic Placement** pattern (which relies on numbers matching their exact index homes), the **In-place Filtering / Compaction** pattern uses a **Two-Pointer (Read/Write)** strategy.

### Tips to Spot This Pattern

Look for these dead giveaways in a problem description:

1. **Strict Space Constraint:** The problem explicitly demands an **$O(1)$ auxiliary space** solution, meaning you cannot allocate a new array or collection.
2. **In-place Modification:** The problem statement tells you to modify the input array "in-place" and often asks you to return the new "effective size" of the array.
3. **Order Matters (Stable Filtering):** Elements that survive the filtering or compaction process must maintain their original relative order.
4. **A "Predicate" Condition:** You are looping through elements to determine if they should be "kept" or "discarded" based on a condition (e.g., uniqueness, non-zero, matching a target).

---

## The Universal Java Template

The core mechanic relies on a **Fast Pointer (`read`)** that scans every element, and a **Slow Pointer (`write`)** that marks the boundary where valid elements are preserved.

```java
public int filterPatternTemplate(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    
    // The write pointer tracks where the next 'valid' element should be placed
    int write = 0; 
    
    // The read pointer scans through the entire array
    for (int read = 0; read < nums.length; read++) {
        // Condition/Predicate: Determine if nums[read] is a keeper
        if (isValidElement(nums, read, write)) { 
            nums[write] = nums[read];
            write++; // Advance the write boundary
        }
    }
    
    // 'write' now represents the length of the newly compacted array
    return write; 
}

```

---

## Each Question and Its Solution

### 1. Keep Duplicates At Most Twice (Remove Duplicates from Sorted Array II)

* **The Logic:** Since the array is sorted, we can look backwards to check if an element has already appeared twice. The first two elements (`write < 2`) are always allowed. For any element after, it's valid if it is different from the element placed two steps behind our current write boundary (`nums[read] != nums[write - 2]`).

```java
public int removeDuplicates(int[] nums) {
    if (nums.length <= 2) return nums.length;
    
    int write = 2; // First two elements are automatically kept
    
    for (int read = 2; read < nums.length; read++) {
        // Compare current element with the element 2 positions behind the write boundary
        if (nums[read] != nums[write - 2]) {
            nums[write] = nums[read];
            write++;
        }
    }
    return write;
}

```

### 2. Remove Element

* **The Logic:** You are given a target value `val`. Your goal is to strip it out. An element is "valid" and needs to be written to the front of the array as long as `nums[read] != val`.

```java
public int removeElement(int[] nums, int val) {
    int write = 0;
    
    for (int read = 0; read < nums.length; read++) {
        // If it's not the element we want to discard, overwrite it into the write index
        if (nums[read] != val) {
            nums[write] = nums[read];
            write++;
        }
    }
    return write;
}

```

### 3. Move Zeroes

* **The Logic:** This is a stable filtering problem where zeros are filtered out to the right side. The read/write phase compacts all non-zero elements to the front. A quick final loop fills the remaining positions up to `nums.length` with zeros.

```java
public void moveZeroes(int[] nums) {
    int write = 0;
    
    // Phase 1: Stable filter all non-zero elements to the front
    for (int read = 0; read < nums.length; read++) {
        if (nums[read] != 0) {
            nums[write] = nums[read];
            write++;
        }
    }
    
    // Phase 2: Fill the remaining indices with zeros
    while (write < nums.length) {
        nums[write] = 0;
        write++;
    }
}

```

### 4. Partition Array (By Odd and Even)

* **The Logic:** If you need to stables-filter elements based on parity (e.g., keeping all evens first, then odds), you can track them dynamically. Here we filter all even elements to the front first. To make it a true two-way partition smoothly, we can gather the discarded elements or do a traditional two-pointer swap if relative stability between the categories isn't strictly requested.

```java
public int[] sortArrayByParity(int[] nums) {
    int write = 0;
    
    // Filter out and place all even numbers first
    for (int read = 0; read < nums.length; read++) {
        if (nums[read] % 2 == 0) {
            // Swap to preserve all numbers in-place
            int temp = nums[write];
            nums[write] = nums[read];
            nums[read] = temp;
            write++;
        }
    }
    return nums;
}

```