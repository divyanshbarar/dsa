// We greedily maintain the smallest possible values for:
// - first element
// - second element

// If we encounter a number smaller than first,
// we update first.

// Else if number can improve second,
// we update second.

// If we ever find a number larger than both first and second,
// then an increasing triplet exists.

// The greedy insight is:
// keeping smaller candidates increases chances
// of completing the triplet later.

class Solution {

    public boolean increasingTriplet(int[] nums) {

        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        for(int num : nums) {

            // Smallest number till now
            if(num <= first) {

                first = num;
            }

            // Smallest valid second number
            else if(num <= second) {

                second = num;
            }

            // Found third number
            else {

                return true;
            }
        }

        return false;
    }
}