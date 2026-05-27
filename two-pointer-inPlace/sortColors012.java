//dutch national flag algo

// We divide array into 4 regions.

// [0 region | 1 region | unknown | 2 region]



// Since the array contains only 3 distinct values,
// we don't need full sorting.

// We can partition the array into regions:
// - left for 0s
// - middle for 1s
// - right for 2s

// Using three pointers:
// - low
// - mid
// - high

// we process each element exactly once.

// The key insight is:
// 0s move left,
// 2s move right,
// and 1s naturally stay in middle.

class Solution {

    public void sortColors(int[] nums) {

        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while(mid <= high) {

            // Case 1: 0 belongs to left side
            if(nums[mid] == 0) {

                swap(nums, low, mid);

                low++;
                mid++;
            }

            // Case 2: 1 already in correct region
            else if(nums[mid] == 1) {

                mid++;
            }

            // Case 3: 2 belongs to right side
            else {

                swap(nums, mid, high);

                high--;

                // Don't increment mid here
            }
        }
    }

    private void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}