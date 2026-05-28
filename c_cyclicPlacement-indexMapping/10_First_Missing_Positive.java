// Since we need O(1) extra space,
// we cannot use a HashSet.

// The key observation is:
// for an array of size n,
// the smallest missing positive must lie between 1 and n+1.

// So we use array indices themselves as a hash structure.

// For every valid number x,
// we place it at index x-1.

// After rearrangement,
// the first index where nums[i] != i+1
// gives the missing positive.

class Solution {

    public int firstMissingPositive(int[] nums) {

        int n = nums.length;

        // Place numbers at correct indices
        for(int i = 0; i < n; i++) {

            while(nums[i] >= 1 &&
                  nums[i] <= n &&
                  nums[i] != nums[nums[i] - 1]) {

                int correctIndex = nums[i] - 1;

                // Swap current number to correct position
                swap(nums, i, correctIndex);
            }
        }

        // Find first missing positive
        for(int i = 0; i < n; i++) {

            if(nums[i] != i + 1) {
                return i + 1;
            }
        }

        // All numbers 1..n exist
        return n + 1;
    }

    private void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}