// To form the word "balloon",
// certain characters are required multiple times.

// So first I count the frequency of every character in the input string.

// Then for each required character,
// I calculate how many complete copies it can contribute.

// For example:
// 'l' and 'o' are required twice,
// so their frequency must be divided by 2.

// The answer is the minimum among all required character counts,
// because the scarcest character becomes the bottleneck.

class Solution {

    public int maxNumberOfBalloons(String text) {

        int[] freq = new int[26];

        // Count frequency
        for(char ch : text.toCharArray()) {
            freq[ch - 'a']++;
        }

        return Math.min(
            Math.min(freq['b' - 'a'], freq['a' - 'a']),
            Math.min(
                Math.min(freq['l' - 'a'] / 2,
                         freq['o' - 'a'] / 2),
                freq['n' - 'a']
            )
        );
    }
}