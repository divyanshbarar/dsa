// Two strings are anagrams if they contain
// the same characters with the same frequencies.

// A useful observation is that sorting two
// anagram strings produces the same result.

// For example:

// eat -> aet
// tea -> aet

// So I use the sorted string as a signature.

// Strings sharing the same signature belong
// to the same anagram group.

// I store groups in a HashMap where:

// signature -> list of strings
class Solution {

    public List<List<String>> groupAnagrams(
        String[] strs
    ) {

        Map<String, List<String>> map =
            new HashMap<>();

        for(String str : strs) {

            char[] chars = str.toCharArray();

            Arrays.sort(chars);

            String signature =
                new String(chars);

            map.computeIfAbsent(
                signature,
                k -> new ArrayList<>()
            ).add(str);
        }

        return new ArrayList<>(map.values());
    }
}