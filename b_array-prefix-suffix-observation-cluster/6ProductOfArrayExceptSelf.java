// Problem Statement

// Given an integer array nums,
// return an array answer such that:

// answer[i]

// is equal to the product of all elements of nums
// except nums[i].

// You must solve it:

// without division
// in O(n) time

//--------------------------------------------------------

// INTERVIEW CLUE

// Whenever question asks:

// everything except current element

// Think:

// prefix + suffix information

// VERY important trigger.
//--------------------------------------------------------

// For every index, the required answer is:

// (product of elements before index)
// ×
// (product of elements after index)

// So we can precompute prefix and suffix products.

// To optimize space further,
// instead of storing separate prefix and suffix arrays,
// we store prefix products directly in the answer array.

// Then we traverse from right side while maintaining a running suffix product,
// and multiply it into the answer.

class Solution {

    public int[] productExceptSelf(int[] nums) {

        int n = nums.length;

        int[] answer = new int[n];

        // Step 1:
        // Store prefix products in answer array
        answer[0] = 1;

        for(int i = 1; i < n; i++) {

            answer[i] = answer[i - 1] * nums[i - 1];
        }

        // Step 2:
        // Traverse from right using suffix product
        int suffix = 1;

        for(int i = n - 1; i >= 0; i--) {

            answer[i] *= suffix;

            suffix *= nums[i];
        }

        return answer;
    }
}