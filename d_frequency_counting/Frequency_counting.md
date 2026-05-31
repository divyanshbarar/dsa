## Similar Problems

This exact thinking appears in:

* **Ransom Note** (Checking if characters of one string can be fully supplied by another)
* **Number of Good Pairs** (Counting matching value frequencies to calculate combinations)
* **Valid Anagram** (Verifying if two strings share the exact same character frequencies)
* **Find Common Characters** (Finding the minimum shared frequency of characters across multiple strings)
* **Group Anagrams** (Using frequency distributions as unique map keys)
* **Reorganize String** (Using frequency counts to greedily schedule characters without adjacencies)

---

## Pattern Learned: Frequency Counting (Fixed-Size Mapping)

### Core Idea:

Instead of comparing elements across nested collections ($O(n^2)$ time), or sorting them ($O(n \log n)$ time), you **map elements directly to their absolute occurrence counts within a fixed-size frequency array**.

When the universe of possible values is bounded and small (such as the 26 lowercase English letters or a small range of integers), a simple primitive array (`int[] count = new int[26]`) acts as a high-performance, $O(1)$ lookup hash map. This shifts your time complexity to a clean **$O(n)$ time** single pass while utilizing **$O(1)$ space**.

---

### Tips to Look for This Pattern

1. **Alphabet or Limited Range:** The problem explicitly mentions "lowercase English letters," "ASCII characters," or integers restricted to a tight, known boundary.
2. **Permutation / Rearrangement / Matching:** You are asked to check if strings can form each other, are anagrams, or need to be redistributed. The exact original positions of the elements do not matter—only their *quantities* matter.
3. **Combination Tallying:** You need to count matching pairs or groupings based on identical individual values.

---

## The Universal Java Template

```java
public boolean frequencyCountingTemplate(String s, String t) {
    // Fixed size array allocation (O(1) space auxiliary overhead)
    int[] freq = new int[26];
    
    // Step 1: Record counts from the primary dataset
    for (int i = 0; i < s.length(); i++) {
        freq[s.charAt(i) - 'a']++;
    }
    
    // Step 2: Consume, compare, or evaluate counts using the secondary dataset
    for (int i = 0; i < t.length(); i++) {
        int index = t.charAt(i) - 'a';
        freq[index]--;
        if (freq[index] < 0) {
            return false; // Broken invariant condition
        }
    }
    
    return true;
}

```

---

## Each Question and Its Solution

### 1. Ransom Note

* **The Logic:** Count the frequency of every letter available in the `magazine`. Then, iterate through the `ransomNote`, decrementing the counts. If any character's count drops below zero, the magazine lacks the raw material to build the note.

```java
public boolean canConstruct(String ransomNote, String magazine) {
    int[] counts = new int[26];
    
    for (char c : magazine.toCharArray()) {
        counts[c - 'a']++;
    }
    
    for (char c : ransomNote.toCharArray()) {
        counts[c - 'a']--;
        if (counts[c - 'a'] < 0) {
            return false;
        }
    }
    return true;
}

```

### 2. Number of Good Pairs

* **The Logic:** Iterate through the array and track how many times you have seen each number so far. If a number has already appeared $k$ times, introducing the current element creates $k$ new identical pairs dynamically.

```java
public int numIdenticalPairs(int[] nums) {
    int[] freq = new int[101]; // Given constraint: nums[i] is between 1 and 100
    int goodPairs = 0;
    
    for (int num : nums) {
        // Running Contribution: It forms a pair with all previously seen identical numbers
        goodPairs += freq[num];
        freq[num]++;
    }
    return goodPairs;
}

```

### 3. Valid Anagram

* **The Logic:** Two strings are anagrams if their character frequency signatures are completely identical. Increment positions for string `s`, decrement for string `t`, and then confirm the array returns cleanly to all zeros.

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    
    int[] freq = new int[26];
    for (int i = 0; i < s.length(); i++) {
        freq[s.charAt(i) - 'a']++;
        freq[t.charAt(i) - 'a']--;
    }
    
    for (int count : freq) {
        if (count != 0) return false;
    }
    return true;
}

```

### 4. Find Common Characters

* **The Logic:** Keep a global `minFreq` tracker array initialized to maximum values. For each individual string, calculate its unique local frequency array, then update the global tracker to retain only the minimum overlap across all entries.

```java
public List<String> commonChars(String[] words) {
    int[] minFreq = new int[26];
    Arrays.fill(minFreq, Integer.MAX_VALUE);
    
    for (String word : words) {
        int[] localFreq = new int[26];
        for (char c : word.toCharArray()) {
            localFreq[c - 'a']++;
        }
        // Intersect counts by keeping the absolute minimums
        for (int i = 0; i < 26; i++) {
            minFreq[i] = Math.min(minFreq[i], localFreq[i]);
        }
    }
    
    List<String> result = new ArrayList<>();
    for (int i = 0; i < 26; i++) {
        while (minFreq[i] > 0) {
            result.add(String.valueOf((char)(i + 'a')));
            minFreq[i]--;
        }
    }
    return result;
}

```

### 5. Group Anagrams (Slightly Advanced)

* **The Logic:** To group strings together without sorting them individually, generate a unique frequency string signature (e.g., `"1#0#2#0...0#"`) to act as a deterministic, hashable key for a `HashMap`.

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    
    for (String s : strs) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;
        
        // Convert the frequency array into a unique string delimiter key
        StringBuilder sb = new StringBuilder();
        for (int count : freq) {
            sb.append(count).append('#');
        }
        String key = sb.toString();
        
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(s);
    }
    return new ArrayList<>(map.values());
}

```

### 6. Reorganize String (Slightly Advanced)

* **The Logic:** Count the total letter frequencies. If any single character's frequency exceeds `(N + 1) / 2`, it's mathematically impossible to arrange them without adjacencies. Otherwise, fill all the even indices (`0, 2, 4...`) first with the most frequent character, then distribute the remaining characters across the remaining slots.

```java
public String reorganizeString(String s) {
    int[] freq = new int[26];
    int maxCount = 0, letter = 0;
    for (char c : s.toCharArray()) {
        freq[c - 'a']++;
        if (freq[c - 'a'] > maxCount) {
            maxCount = freq[c - 'a'];
            letter = c - 'a';
        }
    }
    
    // Check if configuration is impossible
    if (maxCount > (s.length() + 1) / 2) return "";
    
    char[] res = new char[s.length()];
    int idx = 0;
    
    // Interleave the most frequent character on even slots first
    while (freq[letter] > 0) {
        res[idx] = (char) (letter + 'a');
        idx += 2;
        freq[letter]--;
    }
    
    // Fill the rest of the characters greedily
    for (int i = 0; i < 26; i++) {
        while (freq[i] > 0) {
            if (idx >= res.length) idx = 1; // Switch to odd slots
            res[idx] = (char) (i + 'a');
            idx += 2;
            freq[i]--;
        }
    }
    return String.valueOf(res);
}

```