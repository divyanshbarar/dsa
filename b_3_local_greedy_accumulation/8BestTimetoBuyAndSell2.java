// Since multiple transactions are allowed,
// we can profit from every upward price movement.

// The key greedy observation is:
// any larger increasing sequence can be decomposed
// into smaller profitable transactions
// without changing total profit.

// Therefore,
// whenever today's price is greater than yesterday's,
// we add that difference to our answer.
class Solution {

    public int maxProfit(int[] prices) {

        int profit = 0;

        for(int i = 1; i < prices.length; i++) {

            // Take every upward movement
            if(prices[i] > prices[i - 1]) {

                profit += prices[i] - prices[i - 1];
            }
        }

        return profit;
    }
}