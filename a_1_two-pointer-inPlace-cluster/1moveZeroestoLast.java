
// Brute force would use an extra array to store non-zero elements.

// But since the problem asks for in-place modification,
// we can use a write pointer.

// We iterate through the array and place non-zero elements
// at the earliest available position.

// After all non-zero elements are placed,
// we fill the remaining indices with 0.

class Solution {

    public void moveZeroes(int[] nums) {

        // Position where next non-zero element should go
        int writeIndex = 0;

        // Step 1:
        // Move all non-zero elements forward
        for(int i = 0; i < nums.length; i++) {

            if(nums[i] != 0) {
                nums[writeIndex] = nums[i];
                writeIndex++;
            }
        }

        // Step 2:
        // Fill remaining positions with zero
        while(writeIndex < nums.length) {
            nums[writeIndex] = 0;
            writeIndex++;
        }
    }
}