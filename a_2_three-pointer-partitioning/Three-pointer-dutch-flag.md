## Similar Problems

This exact logic helps in:

* **Partition Array** (Splitting an array into two halves based on a pivot)
* **Segregate Even/Odd Numbers** (Grouping numbers by parity)
* **QuickSort Partition** (The foundational step of the QuickSort algorithm)
* **Wiggle Sort** (Arranging numbers in a wave-like/wiggle pattern)
* **Rainbow Sort / Sort Colors** (Sorting an array containing 3 or more distinct categories in-place)

---

## Pattern Learned: Dutch National Flag (Three-Way Array Partitioning)

The **Dutch National Flag (DNF)** pattern is an extension of the classic two-pointer partitioning technique. While standard partitioning divides an array into two groups (e.g., smaller than pivot, larger than pivot), the DNF pattern handles **three distinct categories** simultaneously in a single pass, using **$O(n)$ time** and **$O(1)$ space**.

### Tips to Look for This Pattern

1. **Three Categories/Buckets:** The problem involves sorting or grouping elements that fall into exactly three distinct classes (e.g., `0, 1, 2`, `Red, White, Blue`, or `< pivot, == pivot, > pivot`).
2. **In-place Constraint:** You are required to sort or arrange the array without allocating extra space for a new array ($O(1)$ auxiliary space).
3. **Single Pass:** The optimal solution requires doing this efficiently in a single scan ($O(n)$ time), rather than counting elements or using multiple passes.

---

## The Universal Java Template

The core mechanic uses three pointers: `low` (boundary for the first group), `mid` (the current element scanner), and `high` (boundary for the third group). Everything between `low` and `mid-1` is the second group.

```java
public void threeWayPartition(int[] nums, int pivotValue) {
    int low = 0;
    int mid = 0;
    int high = nums.length - 1;

    while (mid <= high) {
        if (nums[mid] < pivotValue) {
            // Group 1: Element belongs to the lower section
            swap(nums, low, mid);
            low++;
            mid++;
        } else if (nums[mid] == pivotValue) {
            // Group 2: Element belongs to the middle section
            mid++;
        } else {
            // Group 3: Element belongs to the higher section
            swap(nums, mid, high);
            high--; // Do NOT increment mid here; we need to evaluate the swapped element
        }
    }
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}

```

---

## Each Question and Its Solution

### 1. Rainbow Sort (LeetCode 75 - Sort Colors)

* **The Logic:** You are given an array with 0s, 1s, and 2s representing red, white, and blue. We use `0` as our lower threshold and `2` as our higher threshold.

```java
public void sortColors(int[] nums) {
    int low = 0, mid = 0, high = nums.length - 1;
    
    while (mid <= high) {
        if (nums[mid] == 0) {
            swap(nums, low++, mid++);
        } else if (nums[mid] == 1) {
            mid++;
        } else {
            swap(nums, mid, high--);
        }
    }
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i]; nums[i] = nums[j]; nums[j] = temp;
}

```

### 2. QuickSort Partition (Three-Way / Dual-Pivot)

* **The Logic:** Standard QuickSort uses a two-way partition, but it struggles with arrays containing many duplicate elements. Three-way partitioning divides the array into elements `< pivot`, `== pivot`, and `> pivot`, making it incredibly efficient for duplicate keys.

```java
public int[] quickSortPartition(int[] nums, int pivot) {
    int low = 0, mid = 0, high = nums.length - 1;
    
    while (mid <= high) {
        if (nums[mid] < pivot) {
            swap(nums, low++, mid++);
        } else if (nums[mid] == pivot) {
            mid++;
        } else {
            swap(nums, mid, high--);
        }
    }
    // Returns boundaries of the middle (equal elements) section
    return new int[]{low, high};
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i]; nums[i] = nums[j]; nums[j] = temp;
}

```

### 3. Segregate Even/Odd Numbers (Two-Way Partition Variant)

* **The Logic:** While even/odd segregation only requires two groups, it uses a simplified version of the same partitioning mechanics. We maintain a `left` pointer for evens and a `right` pointer for odds.

```java
public int[] exchangeEvenOdd(int[] nums) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        if (nums[left] % 2 == 0) {
            left++; // Already in the correct even territory
        } else if (nums[right] % 2 != 0) {
            right--; // Already in the correct odd territory
        } else {
            // nums[left] is odd and nums[right] is even, swap them
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
    return nums;
}

```

### 4. Partition Array

* **The Logic:** Given a pivot condition (like partitioning an array around value `k` such that elements $< k$ are on the left and elements $\ge k$ are on the right).

```java
public int partitionArray(int[] nums, int k) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        if (nums[left] < k) {
            left++;
        } else if (nums[right] >= k) {
            right--;
        } else {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
    return left; // Returns the starting index of elements >= k
}

```

### 5. Wiggle Sort (In-place Partition Aspect)

* **The Logic:** Wiggle Sort requires sorting an array such that `nums[0] <= nums[1] >= nums[2] <= nums[3]...`. While Wiggle Sort II heavily relies on finding the median via a virtual index three-way partitioning system, a simpler Wiggle Sort variant utilizes localized partitioning logic. By stepping through the array, if the parity of the index does not match the local inequality condition, we swap it with its neighbor.

```java
public void wiggleSort(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) {
        // If i is even, nums[i] must be <= nums[i+1]
        // If i is odd, nums[i] must be >= nums[i+1]
        if ((i % 2 == 0 && nums[i] > nums[i + 1]) || (i % 2 != 0 && nums[i] < nums[i + 1])) {
            int temp = nums[i];
            nums[i] = nums[i + 1];
            nums[i + 1] = temp;
        }
    }
}

```