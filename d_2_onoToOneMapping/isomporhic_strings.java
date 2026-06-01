// This is a one-to-one mapping problem.

// For every character in the first string,
// I need to ensure it always maps to the same
// character in the second string.

// Additionally,
// two different characters cannot map to the same
// target character.

// So I maintain:
// - a forward mapping
// - a set of already used target characters

// If either constraint is violated,
// the strings are not isomorphic.

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {

    public boolean isIsomorphic(String s, String t) {

        Map<Character, Character> map = new HashMap<>();

        Set<Character> used = new HashSet<>();

        for(int i = 0; i < s.length(); i++) {

            char source = s.charAt(i);
            char target = t.charAt(i);

            // Existing mapping
            if(map.containsKey(source)) {

                if(map.get(source) != target) {
                    return false;
                }
            }
            else {

                // Target already used
                if(used.contains(target)) {
                    return false;
                }

                map.put(source, target);
                used.add(target);
            }
        }

        return true;
    }
}