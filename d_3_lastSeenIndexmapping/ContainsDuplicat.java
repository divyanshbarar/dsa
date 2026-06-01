// The brute force approach compares every pair,
// which takes O(n²).

// The key observation is that for any value,
// only its most recent occurrence matters.

// So I store the last seen index of each number
// in a HashMap.

// When I encounter a number again,
// I compute the distance between the current index
// and its previous occurrence.

// If the distance is within k,
// I return true.

// Otherwise I update the latest index and continue.

import java.util.HashMap;
import java.util.Map;

class Solution {

    public boolean containsNearbyDuplicate(
        int[] nums,
        int k
    ) {

        Map<Integer, Integer> lastSeen =
            new HashMap<>();

        for(int i = 0; i < nums.length; i++) {

            if(lastSeen.containsKey(nums[i])) {

                int previousIndex =
                    lastSeen.get(nums[i]);

                if(i - previousIndex <= k) {
                    return true;
                }
            }

            // Update latest position
            lastSeen.put(nums[i], i);
        }

        return false;
    }
}