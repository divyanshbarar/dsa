// Instead of generating all subarrays,
// we observe that consecutive zeros form predictable counts.

// If current zero streak length is k,
// then the current element contributes exactly k
// new zero-filled subarrays ending at this index.

// So while traversing,
// we maintain the current zero streak length.

// For every zero:
// - increment streak
// - add streak to answer

// For non-zero:
// reset streak.
class Solution {

    public long zeroFilledSubarray(int[] nums) {

        long answer = 0;

        long streak = 0;

        for(int num : nums) {

            // Extend zero streak
            if(num == 0) {

                streak++;

                answer += streak;
            }

            // Break streak
            else {

                streak = 0;
            }
        }

        return answer;
    }
}