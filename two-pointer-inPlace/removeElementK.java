// The problem is essentially asking us to filter the array in-place.

// Instead of deleting elements,
// which is expensive in arrays,
// we can overwrite unwanted values.

// We maintain a write pointer representing the next valid position.

// As we traverse:
// - if element should be kept,
// we place it at writeIndex
// and move writeIndex forward.

// This compacts all valid elements to the beginning
// without extra space.

class Solution {

    public int removeElement(int[] nums, int val) {

        // Position for next valid element
        int writeIndex = 0;

        // Traverse array
        for(int i = 0; i < nums.length; i++) {

            // Keep valid elements
            if(nums[i] != val) {

                nums[writeIndex] = nums[i];
                writeIndex++;
            }
        }

        return writeIndex;
    }
}