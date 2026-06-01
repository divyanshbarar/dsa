## Similar Problems

This exact thinking appears in:

* **Group Anagrams** (Sorting characters to group words that use identical letter pools)
* **Find Duplicate Files in System** (Using file content text strings as uniform unique matching keys)
* **Normalize Email Addresses** (Stripping periods and plus signs to collapse variations into a definitive mailbox destination)
* **Alien Dictionary Variants** (Translating varied relational pairs into an absolute, linearized sequential alphabet alphabet string)
* **Pattern Matching Problems** (Encoding fluctuating string configurations into standardized structural placeholder footprints)

---

## Pattern Learned: Canonical Representation

### Core Idea:

When you are faced with tracking, grouping, or comparing objects that look completely different on the surface but are logically equivalent, you **reduce each object down to its single, standardized "Canonical Representation"**.

Instead of trying to run expensive comparison algorithms between every single pairing profile ($O(n^2)$ time), you pass each element through a normalizing function. This function strips away superficial variations (like character arrangement, capitalizations, formatting, or noise) and outputs a unique, uniform signature string. You then feed this signature string straight into a `HashMap` or `HashSet`. Equivalence is instantly resolved in **$O(1)$ lookup time**, simplifying the broader algorithm down to a clean **$O(n)$ time** pass.

---

### Tips to Look for This Pattern

1. **"Group Together" or "Find Duplicates":** The objective explicitly calls for aggregating or filtering structures based on shared, hidden properties.
2. **Superficial Variance:** The input data has multiple valid representations that point to the exact same functional outcome (e.g., `"A.b+c@gmail.com"` vs `"ab@gmail.com"`).
3. **Deterministic Signature Generation:** You can easily draft a pure, consistent mapping rule that translates any valid variation into one uniform baseline shape.

---

## The Universal Java Template

```java
public List<List<String>> canonicalRepresentationTemplate(String[] items) {
    // Group elements by their deterministic, unique canonical signature
    Map<String, List<String>> canonicalMap = new HashMap<>();

    for (String item : items) {
        // Step 1: Generate the uniform, canonical key for the object
        String canonicalKey = getCanonicalSignature(item);

        // Step 2: Route the element into its matching canonical bucket
        canonicalMap.putIfAbsent(canonicalKey, new ArrayList<>());
        canonicalMap.get(canonicalKey).add(item);
    }

    // Return the aggregated, identical logical groupings
    return new ArrayList<>(canonicalMap.values());
}

private String getCanonicalSignature(String input) {
    // Transformation engine logic goes here
    return input.toLowerCase().trim(); 
}

```

---

## Each Question and Its Solution

### 1. Group Anagrams

* **The Logic:** Words like `"eat"`, `"tea"`, and `"ate"` are variations of the same letter quantities. The most natural canonical representation for an anagram is its **alphabetically sorted character string**. Once sorted, all three variations yield `"aet"`, which we use as our definitive `HashMap` key bucket.

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    
    for (String s : strs) {
        // Generate Canonical Key: Sort the characters alphabetically
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String canonicalKey = new String(chars);
        
        map.putIfAbsent(canonicalKey, new ArrayList<>());
        map.get(canonicalKey).add(s);
    }
    return new ArrayList<>(map.values());
}

```

### 2. Find Duplicate Files in System

* **The Logic:** You receive file paths along with their text contents (e.g., `"root/a 1.txt(abcd)"`). Files are duplicates if they share identical text inside. The canonical representation here is the **raw file content string** itself. We extract `"abcd"` and use it as our mapping key, collecting all file paths that generate this exact text.

```java
public List<List<String>> findDuplicate(String[] paths) {
    Map<String, List<String>> contentMap = new HashMap<>();
    
    for (String path : paths) {
        String[] parts = path.split(" ");
        String directory = parts[0];
        
        for (int i = 1; i < parts.length; i++) {
            int openBracket = parts[i].indexOf('(');
            String fileName = parts[i].substring(0, openBracket);
            // Canonical Key: The raw content inside the brackets
            String fileContent = parts[i].substring(openBracket + 1, parts[i].length() - 1);
            
            String fullPath = directory + "/" + fileName;
            
            contentMap.putIfAbsent(fileContent, new ArrayList<>());
            contentMap.get(fileContent).add(fullPath);
        }
    }
    
    List<List<String>> result = new ArrayList<>();
    for (List<String> group : contentMap.values()) {
        if (group.size() > 1) { // Only return actual duplicates
            result.add(group);
        }
    }
    return result;
}

```

### 3. Normalize Email Addresses (Unique Email Addresses)

* **The Logic:** Emails can have periods (ignored in local names) and plus signs (ignores everything after it up to the `@`). The canonical representation is the **fully stripped local name combined with the untouched domain name**. For example, `"test.email+alex@gmail.com"` maps down cleanly to the canonical key `"testemail@gmail.com"`.

```java
public int numUniqueEmails(String[] emails) {
    Set<String> uniqueCanonicalEmails = new HashSet<>();
    
    for (String email : emails) {
        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];
        
        // 1. Handle the '+' rule: Discard everything after it
        if (local.contains("+")) {
            local = local.substring(0, local.indexOf('+'));
        }
        // 2. Handle the '.' rule: Strip out all periods
        local = local.replace(".", "");
        
        // Generate Canonical Key: Construct the definitive address baseline
        String canonicalEmail = local + "@" + domain;
        uniqueCanonicalEmails.add(canonicalEmail);
    }
    
    return uniqueCanonicalEmails.size();
}

```

### 4. Pattern Matching Problems (Isomorphic String Architecture)

* **The Logic:** To verify if strings like `"paper"` and `"title"` have the exact same mapping footprint without comparing them directly, convert both strings into an abstract, positional index format template code. We map the first seen character to `0`, the second to `1`, and so on. Both `"paper"` and `"title"` reduce down to the same canonical signature string: `"0.1.0.2.3"`.

```java
public boolean isIsomorphic(String s, String t) {
    return getCanonicalSignature(s).equals(getCanonicalSignature(t));
}

private String getCanonicalSignature(String word) {
    Map<Character, Integer> seen = new HashMap<>();
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);
        if (!seen.containsKey(c)) {
            seen.put(c, seen.size()); // Map character to an incremental ID code
        }
        sb.append(seen.get(c)).append(".");
    }
    return sb.toString();
}

```

### 5. Alien Dictionary Variants (Verifying a Foreign Order)

* **The Logic:** You are given an alien dictionary alphabet `order` (e.g., `"habc..."`). To easily sort words based on this custom language structure, map each alien character to its integer priority rank index. The canonical representation of an alien word is its **translated integer sequence array**. By mapping words into standard numerical arrays, we can use traditional comparison logic to verify if the dataset is correctly ordered.

```java
public boolean isAlienSorted(String[] words, String order) {
    int[] alienOrderMap = new int[26];
    for (int i = 0; i < order.length(); i++) {
        alienOrderMap[order.charAt(i) - 'a'] = i;
    }
    
    for (int i = 0; i < words.length - 1; i++) {
        if (isLargerCanonical(words[i], words[i + 1], alienOrderMap)) {
            return false;
        }
    }
    return true;
}

private boolean isLargerCanonical(String w1, String w2, int[] orderMap) {
    int len1 = w1.length(), len2 = w2.length();
    for (int i = 0; i < Math.min(len1, len2); i++) {
        int charOrder1 = orderMap[w1.charAt(i) - 'a'];
        int charOrder2 = orderMap[w2.charAt(i) - 'a'];
        
        if (charOrder1 != charOrder2) {
            return charOrder1 > charOrder2;
        }
    }
    return len1 > len2; // Edge case: "apple" vs "app"
}

```