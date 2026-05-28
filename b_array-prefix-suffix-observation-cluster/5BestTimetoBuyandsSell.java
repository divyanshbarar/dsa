// Brute force would try every buy/sell pair,
// which takes O(n²).

// The key observation is:
// for every selling day,
// we only need the minimum buying price before it.

// So while traversing the array,
// we maintain the minimum price seen so far.

// At each step,
// we calculate profit if we sold today,
// and update the maximum profit.

class Solution {

    public int maxProfit(int[] prices) {

        // Minimum price seen so far
        int minPrice = Integer.MAX_VALUE;

        // Maximum profit till now
        int maxProfit = 0;

        for(int price : prices) {

            // Update minimum buying price
            minPrice = Math.min(minPrice, price);

            // Calculate profit if sold today
            int currentProfit = price - minPrice;

            // Update maximum profit
            maxProfit = Math.max(maxProfit, currentProfit);
        }

        return maxProfit;
    }
}