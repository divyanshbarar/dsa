// Whenever you see:sorted array

// think:
// duplicates become adjacent
// two pointers become useful
// comparisons become local

// Since the array is sorted,
// duplicates will always appear together.

// We use a write pointer to track where the next
// unique element should go.

// Whenever current element differs from previous,
// it means we found a new unique value,
// so we place it at writeIndex.

class Solution {

    public int removeDuplicates(int[] nums) {

        // Edge case
        if(nums.length == 0) {
            return 0;
        }

        // First element is always unique
        int writeIndex = 1;

        // Start from second element
        for(int i = 1; i < nums.length; i++) {

            // New unique element found
            if(nums[i] != nums[i - 1]) {

                nums[writeIndex] = nums[i];
                writeIndex++;
            }
        }

        return writeIndex;
    }
}