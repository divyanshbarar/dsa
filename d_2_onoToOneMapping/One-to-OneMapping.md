## Similar Problems

This exact thinking appears in:

* **Isomorphic Strings** (Checking if characters in one string can be mapped uniquely to characters in another)
* **Word Pattern** (Matching a string pattern of characters to a sequence of words)
* **Encode and Decode TinyURL** (Creating unique, bi-directional shortened keys for URLs)
* **Custom Sort String** (Mapping characters to a new, custom-defined alphabetical order)
* **Alien Dictionary** (Deriving a unique character ordering based on a sorted foreign vocabulary)

---

## Pattern Learned: One-to-One Mapping (Bijective Tracking)

### Core Idea:

When you need to verify a strict relationship between two sets of data (like characters to characters, or characters to words), a single map or frequency array is not enough. You must ensure a **Bijection**—meaning every element in Set $A$ maps to exactly one unique element in Set $B$, and *no two elements in Set $A$ point to the same element in Set $B$*.

To enforce this in **$O(n)$ time**, you track the pairing from both directions simultaneously. You can achieve this either by maintaining two independent HashMaps/arrays (`MapAtoB` and `MapBtoA`), or by ensuring that every time a new value pair is linked, the destination value hasn't already been claimed by a different source.

---

### Tips to Look for This Pattern

1. **"Isomorphic" or "Pattern Matching":** The problem asks if two structures share the exact same structural footprint or substitution cipher.
2. **Strict Exclusivity:** If character `'a'` maps to character `'x'`, then no other character is allowed to map to `'x'`, and `'a'` cannot map to anything else.
3. **Dynamic Translation / Ordering:** You are creating a unique, personalized dictionary or encoding standard on the fly.

---

## The Universal Java Template

```java
public boolean bijectiveMappingTemplate(String[] setA, String[] setB) {
    if (setA.length != setB.length) return false;
    
    // Two maps ensure bidirectional, strict one-to-one validation
    Map<String, String> mapAtoB = new HashMap<>();
    Map<String, String> mapBtoA = new HashMap<>();
    
    for (int i = 0; i < setA.length; i++) {
        String u = setA[i];
        String v = setB[i];
        
        // Check structural integrity from A -> B
        if (mapAtoB.containsKey(u) && !mapAtoB.get(u).equals(v)) return false;
        // Check structural integrity from B -> A
        if (mapBtoA.containsKey(v) && !mapBtoA.get(v).equals(u)) return false;
        
        // Establish the bidirectional bond
        mapAtoB.put(u, v);
        mapBtoA.put(v, u);
    }
    
    return true;
}

```

---

## Each Question and Its Solution

### 1. Isomorphic Strings

* **The Logic:** We must verify if characters in string `s` can be replaced to get string `t`. Because the ASCII character set is small, we can substitute expensive `HashMaps` with two fixed-size integer arrays (`int[256]`) tracking the last seen positions to instantly validate the bijection.

```java
public boolean isIsomorphic(String s, String t) {
    int[] mapS = new int[256];
    int[] mapT = new int[256];
    
    for (int i = 0; i < s.length(); i++) {
        char charS = s.charAt(i);
        char charT = t.charAt(i);
        
        // If their historic mapping positions don't match, the bijection is broken
        if (mapS[charS] != mapT[charT]) {
            return false;
        }
        
        // Record the current position index (plus one to avoid default 0 conflicts)
        mapS[charS] = i + 1;
        mapT[charT] = i + 1;
    }
    return true;
}

```

### 2. Word Pattern

* **The Logic:** This matches a character pattern to a space-separated string of words. We split the string into a word array and map characters to words sequentially, making sure a character doesn't map to a new word, and a word hasn't already been claimed by a different character.

```java
public boolean wordPattern(String pattern, String s) {
    String[] words = s.split(" ");
    if (pattern.length() != words.length) return false;
    
    Map<Character, String> charToWord = new HashMap<>();
    Map<String, Character> wordToChar = new HashMap<>();
    
    for (int i = 0; i < pattern.length(); i++) {
        char c = pattern.charAt(i);
        String word = words[i];
        
        if (charToWord.containsKey(c) && !charToWord.get(c).equals(word)) return false;
        if (wordToChar.containsKey(word) && wordToChar.get(word) != c) return false;
        
        charToWord.put(c, word);
        wordToChar.put(word, c);
    }
    return true;
}

```

### 3. Encode and Decode TinyURL

* **The Logic:** Shortening URLs requires a unique, reversible mapping pattern. We store the relationship in two separate HashMaps: `longToShort` ensures that requesting an encoding for the same URL returns the same key, and `shortToLong` ensures flawless lookup recovery.

```java
public class Codec {
    private Map<String, String> longToShort = new HashMap<>();
    private Map<String, String> shortToLong = new HashMap<>();
    private static final String BASE_URL = "http://tinyurl.com/";

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        }
        // Use the map size to generate a unique, incremental hash key mapping
        String shortUrl = BASE_URL + longToShort.size();
        longToShort.put(longUrl, shortUrl);
        shortToLong.put(shortUrl, longUrl);
        return shortUrl;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return shortToLong.get(shortUrl);
    }
}

```

### 4. Custom Sort String

* **The Logic:** You are given a string `order` representing a custom alphabet ruleset. You want to sort string `s` based on this custom index map. We build a frequency counter of `s`, and then map characters based strictly on the priority defined in `order`.

```java
public String customSortString(String order, String s) {
    int[] count = new int[26];
    for (char c : s.toCharArray()) {
        count[c - 'a']++;
    }
    
    StringBuilder sb = new StringBuilder();
    // Step 1: Map elements strictly in accordance with custom priority mapping
    for (char c : order.toCharArray()) {
        while (count[c - 'a'] > 0) {
            sb.append(c);
            count[c - 'a']--;
        }
    }
    
    // Step 2: Append any remaining items that didn't have specific order instructions
    for (int i = 0; i < 26; i++) {
        while (count[i] > 0) {
            sb.append((char) (i + 'a'));
            count[i]--;
        }
    }
    return sb.toString();
}

```

### 5. Alien Dictionary (Advanced Variation)

* **The Logic:** In an alien language alphabet rules are unknown. However, by performing a adjacent word analysis on a sorted list, we extract directional character mapping pairs (e.g., if `"wrt"` comes before `"wrf"`, then character `'t'` strictly points to character `'f'`). We map these dependencies into a Graph adjacency layout and apply Topological Sort via Kahn's Algorithm / DFS to resolve the definitive sequential map.

```java
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> adjList = new HashMap<>();
    Map<Character, Integer> inDegree = new HashMap<>();
    
    // Initialize standard tracking mappings
    for (String word : words) {
        for (char c : word.toCharArray()) {
            inDegree.put(c, 0);
            adjList.put(c, new HashSet<>());
        }
    }
    
    // Step 1: Discover structural one-to-one mapping rules across words
    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i], w2 = words[i + 1];
        // Check for prefix edge case errors (e.g. "abc" comes before "ab")
        if (w1.length() > w2.length() && w1.startsWith(w2)) return "";
        
        for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
            char parent = w1.charAt(j), child = w2.charAt(j);
            if (parent != child) {
                if (!adjList.get(parent).contains(child)) {
                    adjList.get(parent).add(child);
                    inDegree.put(child, inDegree.get(child) + 1);
                }
                break; // One-to-one relational priority for this pair found, skip down
            }
        }
    }
    
    // Step 2: Process map via Topological sorting rules
    Queue<Character> q = new LinkedList<>();
    for (char c : inDegree.keySet()) {
        if (inDegree.get(c) == 0) q.add(c);
    }
    
    StringBuilder sb = new StringBuilder();
    while (!q.isEmpty()) {
        char cur = q.poll();
        sb.append(cur);
        for (char neighbor : adjList.get(cur)) {
            inDegree.put(neighbor, inDegree.get(neighbor) - 1);
            if (inDegree.get(neighbor) == 0) q.add(neighbor);
        }
    }
    
    return sb.length() == inDegree.size() ? sb.toString() : "";
}

```