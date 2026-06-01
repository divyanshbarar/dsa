## Similar Problems

This exact thinking appears in:

* **Contains Duplicate II** (Finding duplicate elements within a maximum index gap of $k$)
* **Longest Substring Without Repeating Characters** (Instantly jumping the left boundary forward past a repeated character)
* **Fruits Into Baskets** (Tracking the last position of each fruit type to quickly skip sections)
* **Longest Repeating Character Replacement** (Optimizing window skips by checking historic bounds)
* **Minimum Window Substring** (Jumping and checking valid configurations via character tracking maps)

---

## Pattern Learned: Last Seen Index Mapping

### Core Idea:

The **Last Seen Index** pattern is a massive performance upgrade over the classic two-pointer sliding window. In a standard sliding window, when a constraint is violated, the `left` pointer incrementally increments step-by-step (`while` loop) to shrink the window.

Instead of stepping slowly, you **store the absolute index where each element was last seen inside a HashMap or integer array**. When a duplicate or invalidating element is hit at the `right` pointer, you look up its historical index and **instantly warp the `left` pointer** directly to `lastSeenIndex + 1`. This eliminates the internal contracting loop, ensuring a highly optimized, clean jump mechanic in **$O(n)$ time** and **$O(\min(n, k))$ space**.

---

### Tips to Look for This Pattern

1. **"Without Repeating" or "Unique":** The problem requires tracking contiguous sub-segments where elements must never collide or duplicate.
2. **Heavy Contract Penalties:** A step-by-step shrinkage of the window would do redundant checks over elements you already know are invalid. You need an immediate structural skip.
3. **Tracking Value Positions, Not Counts:** Instead of incrementing a frequency counter (`count++`), you overwrite a positional marker (`map.put(val, right)`).

---

## The Universal Java Template

```java
public int lastSeenIndexTemplate(int[] nums) {
    int left = 0;
    int maxLen = 0;
    // Maps the element value to its most recent 'right' index location
    Map<Integer, Integer> lastSeen = new HashMap<>();

    for (int right = 0; right < nums.length; right++) {
        int current = nums[right];

        // If we've seen this element before AND it lies inside our active window
        if (lastSeen.containsKey(current) && lastSeen.get(current) >= left) {
            // Instant Skip: Warp the left pointer past the old duplicate element
            left = lastSeen.get(current) + 1;
        }

        // Update or record the element's latest position profile
        lastSeen.put(current, right);

        // Record the optimal calculation
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}

```

---

## Each Question and Its Solution

### 1. Contains Duplicate II

* **The Logic:** As you traverse the array, check if the current number is in the map. If it is, evaluate the difference between the current index `right` and its `lastSeen` index. If the gap is $\le k$, return true. If not, overwrite its location with the new index.

```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Map<Integer, Integer> lastSeen = new HashMap<>();
    
    for (int right = 0; right < nums.length; right++) {
        // If found, check if it satisfies the structural index gap requirement
        if (lastSeen.containsKey(nums[right])) {
            int prevIndex = lastSeen.get(nums[right]);
            if (right - prevIndex <= k) {
                return true;
            }
        }
        // Always map the current item to its latest seen index positioning
        lastSeen.put(nums[right], right);
    }
    return false;
}

```

### 2. Longest Substring Without Repeating Characters

* **The Logic:** Use an integer array (or HashMap) to log the last seen index of characters. When a character is repeated, if its last seen location is greater than or equal to `left`, snap `left` immediately to `lastSeen[character] + 1` to cleanse the window.

```java
public int lengthOfLongestSubstring(String s) {
    int[] lastSeen = new int[128];
    // Fill with -1 initially to indicate the character hasn't been encountered yet
    Arrays.fill(lastSeen, -1);
    
    int left = 0, maxLen = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        
        // If the character was seen inside our current active window bounds
        if (lastSeen[c] >= left) {
            left = lastSeen[c] + 1; // Instant skip past the duplicate element
        }
        
        lastSeen[c] = right;
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}

```

### 3. Fruits Into Baskets

* **The Logic:** We keep a map containing at most 2 fruit types mapped to their *rightmost last seen index*. When a 3rd fruit arrives, we look at both fruits currently in the basket, find the one with the **smallest last seen index** (the one that stopped appearing earliest), delete it from the map, and set `left = oldestLastSeenIndex + 1`.

```java
public int totalFruit(int[] fruits) {
    // Maps fruit type -> its rightmost last seen index position
    Map<Integer, Integer> lastSeen = new HashMap<>();
    int left = 0, maxFruits = 0;
    
    for (int right = 0; right < fruits.length; right++) {
        lastSeen.put(fruits[right], right);
        
        if (lastSeen.size() > 2) {
            // Find the fruit that has the oldest/smallest last seen index to evict
            int oldestIndex = Collections.min(lastSeen.values());
            lastSeen.remove(fruits[oldestIndex]);
            
            // Warp the left boundary right past that evicted fruit's last appearance
            left = oldestIndex + 1;
        }
        
        maxFruits = Math.max(maxFruits, right - left + 1);
    }
    return maxFruits;
}

```

### 4. Character Replacement (Longest Repeating Character Replacement)

* **The Logic:** We expand the window and track character metrics. The key optimization with last-seen concepts in this category is that `maxFreq` (the highest count of a single letter seen) only needs to grow. Since we want a *maximum* window, we never need to shrink the window size. If a window becomes invalid, we simply shift both `left` and `right` together forward by 1 step, maintaining the max size found so far.

```java
public int characterReplacement(String s, int k) {
    int[] counts = new int[26];
    int left = 0, maxFreq = 0;
    
    for (int right = 0; right < s.length(); right++) {
        counts[s.charAt(right) - 'A']++;
        maxFreq = Math.max(maxFreq, counts[s.charAt(right) - 'A']);
        
        // If the current window cannot be made valid with k replacements,
        // we shift the entire window forward without shrinking it (constant length glide)
        if ((right - left + 1) - maxFreq > k) {
            counts[s.charAt(left) - 'A']--;
            left++;
        }
    }
    return s.length() - left;
}

```

### 5. Minimum Window Substring

* **The Logic:** Store the required characters of string `t` in a target array map. As `right` scans through `s`, keep track of matching characters. Instead of shrinking one index at a time when a valid window is found, you can use a list/map tracking the indices of characters present in `t` to skip non-relevant characters entirely, accelerating translation lookups.

```java
public String minWindow(String s, String t) {
    if (s.length() < t.length()) return "";
    
    int[] targetCounts = new int[128];
    for (char c : t.toCharArray()) targetCounts[c]++;
    
    // Filter string s to only look at indices of characters that exist inside string t
    List<Pair<Integer, Character>> filteredS = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (targetCounts[c] > 0) {
            filteredS.add(new Pair<>(i, c));
        }
    }
    
    int[] windowCounts = new int[128];
    int left = 0, requiredMatches = 0;
    for (int i : targetCounts) if (i > 0) requiredMatches++;
    
    int formedMatches = 0, minLen = Integer.MAX_VALUE, startIdx = 0;
    
    // Slide window only across the filtered target positions
    for (int right = 0; right < filteredS.size(); right++) {
        char c = filteredS.get(right).getValue();
        windowCounts[c]++;
        
        if (windowCounts[c] == targetCounts[c]) {
            formedMatches++;
        }
        
        // When valid, look up the mapped original indices to compute exact gaps
        while (left <= right && formedMatches == requiredMatches) {
            int end = filteredS.get(right).getKey();
            int start = filteredS.get(left).getKey();
            
            if (end - start + 1 < minLen) {
                minLen = end - start + 1;
                startIdx = start;
            }
            
            char leftChar = filteredS.get(left).getValue();
            windowCounts[leftChar]--;
            if (windowCounts[leftChar] < targetCounts[leftChar]) {
                formedMatches--;
            }
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : s.substring(startIdx, startIdx + minLen);
}

// Simple Helper Class
class Pair<K, V> {
    private final K key;
    private final V value;
    public Pair(K key, V value) { this.key = key; this.value = value; }
    public K getKey() { return key; }
    public V getValue() { return value; }
}

```