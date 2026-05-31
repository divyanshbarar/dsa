## Similar Problems

This exact thinking appears in:

* **Cyclic Sort** (The fundamental sorting mechanism for bounded ranges)
* **Find All Missing Numbers** (Identifying gaps in an expected dense range)
* **Find the Duplicate Number** (Spotting values competing for the same home index)
* **Set Mismatch** (Locating both the duplicated value and the missing slots in tandem)

---

## Pattern Learned: Cyclic Placement / Index Mapping Pattern

### Core Idea:

When you are given an array of size $n$ containing elements that fall strictly within a known, contiguous range (typically $[1, n]$ or $[0, n]$), **the array indices themselves can be reused as a zero-overhead hash map**.

Instead of allocating an external hash set or boolean tracking array ($O(n)$ space), you run a process that attempts to place every number at its matching "home index" (e.g., the number `v` belongs at index `v - 1`). You continuously swap elements into their correct homes until a value cannot move forward—either because it is already correctly placed, or because its home index is already occupied by an identical value (a duplicate). This achieves an optimal **$O(n)$ time** complexity using strictly **$O(1)$ auxiliary space**.

---

### Tips to Look for This Pattern

1. **Bounded Integer Range:** The problem description strictly limits the input elements to numbers between $1$ and $n$, or $0$ and $n$ (where $n$ is the array length).
2. **Strict In-Place Constraints:** The problem specifies that you must solve it in $O(1)$ auxiliary space while running in linear $O(n)$ time.
3. **Missing or Duplicated Elements:** The objective asks you to find elements that are skipped, omitted, or repeated within that exact range sequence.

---

## The Universal Java Template

The core engine relies on a `while` loop that only advances its scanning pointer `i` once the current slot `nums[i]` contains a value that cannot be swapped any further.

```java
public void cyclicSortTemplate(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        // Map the current value to its designated 'home' index
        int correctIndex = nums[i] - 1; // Adjust to nums[i] if range is [0, n]
        
        // Check if the number is valid and not already sitting in its correct home
        if (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[correctIndex]) {
            // Swap the element to its correct position
            int temp = nums[i];
            nums[i] = nums[correctIndex];
            nums[correctIndex] = temp;
        } else {
            // Only move forward when the current slot is resolved or blocked by a duplicate
            i++;
        }
    }
}

```

---

## Each Question and Its Solution

### 1. Cyclic Sort

* **The Logic:** This is the baseline application. We run the pointer through the array, executing the home swaps. At the conclusion of the single loop pass, the array is perfectly sorted.

```java
public void sortArray(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int correct = nums[i] - 1;
        if (nums[i] != nums[correct]) {
            int temp = nums[i]; nums[i] = nums[correct]; nums[correct] = temp;
        } else {
            i++;
        }
    }
}

```

### 2. Find All Missing Numbers

* **The Logic:** Run the cyclic sort template first. Once the sorting phase completes, any number that is missing will leave a structural mismatch behind: index `i` will contain a value that is *not* equal to `i + 1`. We do a quick second pass to collect these missing targets.

```java
public List<Integer> findDisappearedNumbers(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int correct = nums[i] - 1;
        if (nums[i] != nums[correct]) {
            int temp = nums[i]; nums[i] = nums[correct]; nums[correct] = temp;
        } else { i++; }
    }
    
    List<Integer> missingNumbers = new ArrayList<>();
    // Second pass: Find where the index mapping does not match the actual value
    for (i = 0; i < nums.length; i++) {
        if (nums[i] != i + 1) {
            missingNumbers.add(i + 1);
        }
    }
    return missingNumbers;
}

```

### 3. Find the Duplicate Number

* **The Logic:** As you attempt to swap `nums[i]` into its correct home index, look at who is already living there. If the destination home index already contains the correct value (`nums[i] == nums[correct]`), you cannot swap them. This confirms that the current value `nums[i]` is a duplicate, allowing for an early return.

```java
public int findDuplicate(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        // If the number is not in its correct position
        if (nums[i] != i + 1) {
            int correct = nums[i] - 1;
            // Check if its home index is already holding that number
            if (nums[i] != nums[correct]) {
                int temp = nums[i]; nums[i] = nums[correct]; nums[correct] = temp;
            } else {
                return nums[i]; // Caught the duplicate tracking collision early!
            }
        } else {
            i++;
        }
    }
    return -1;
}

```

### 4. Set Mismatch

* **The Logic:** An array originally containing $[1, n]$ has one duplicate error, which inevitably causes one number to go missing. After running the cyclic sort, we scan the elements. Where `nums[i] != i + 1`, the value physically sitting in the slot is the duplicate, and the expected value `i + 1` is the missing key.

```java
public int[] findErrorNums(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int correct = nums[i] - 1;
        if (nums[i] != nums[correct]) {
            int temp = nums[i]; nums[i] = nums[correct]; nums[correct] = temp;
        } else { i++; }
    }
    
    // Scan for mismatched pairs
    for (i = 0; i < nums.length; i++) {
        if (nums[i] != i + 1) {
            return new int[]{nums[i], i + 1}; // {Duplicate, Missing}
        }
    }
    return new int[]{-1, -1};
}

```