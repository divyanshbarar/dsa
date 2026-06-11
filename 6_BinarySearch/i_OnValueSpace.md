# Binary Search Pattern - Part 9

# Advanced Family

# Kth Smallest Element In A Sorted Matrix

---

# Why This Problem Matters

This question introduces a completely new Binary Search idea.

Until now we binary searched:

```text id="k901"
Index Space
```

or

```text id="k902"
Answer Space
```

This problem teaches:

```text id="k903"
Value Space
```

One of the favorite Google interview patterns.

---

# Problem 19: Kth Smallest Element In A Sorted Matrix

## LeetCode 378

---

# Problem Explanation

Matrix:

```text id="k904"
1   5   9

10 11 13

12 13 15
```

Find:

```text id="k905"
k = 8
```

Output:

```text id="k906"
13
```

Sorted elements:

```text id="k907"
1

5

9

10

11

12

13

13

15
```

8th smallest:

```text id="k908"
13
```

---

# What Is The Interviewer Testing?

Most candidates think:

```text id="k909"
Heap
```

Interviewer wants:

```text id="k910"
Binary Search
```

---

# Pattern Recognition Clues

### Clue 1

Kth Smallest.

### Clue 2

Sorted Matrix.

### Clue 3

Find Rank.

Think:

```text id="k911"
Binary Search On Value Space
```

---

# Brute Force

Flatten matrix.

Sort.

Return:

```text id="k912"
k-1
```

index.

---

# Complexity

```text id="k913"
O(n² log n²)
```

---

# Better Solution

Use Min Heap.

---

# Complexity

```text id="k914"
O(k log n)
```

---

# Interview Insight

Heap is accepted.

But Binary Search is the intended advanced solution.

---

# The Big Observation

Matrix is sorted.

Suppose I guess:

```text id="k915"
mid = 10
```

Can I answer:

```text id="k916"
How many elements
<= 10 ?
```

YES.

That's enough.

---

# The Magic Transformation

Instead of searching:

```text id="k917"
Position
```

Search:

```text id="k918"
Value
```

---

# Search Space

Smallest possible value:

```text id="k919"
matrix[0][0]
```

Largest possible value:

```text id="k920"
matrix[n-1][n-1]
```

---

# Example

```text id="k921"
1  5  9

10 11 13

12 13 15
```

Search:

```text id="k922"
1 → 15
```

---

# The Can Function

Question:

```text id="k923"
How many values
<= mid ?
```

---

# Why This Works

Suppose:

```text id="k924"
mid = 10
```

Count:

```text id="k925"
1

5

9

10
```

Total:

```text id="k926"
4
```

---

Need:

```text id="k927"
k = 8
```

Only:

```text id="k928"
4 elements
```

are <= 10.

Need bigger value.

---

# Monotonic Property

Value:

```text id="k929"
10
```

Count:

```text id="k930"
4
```

---

Value:

```text id="k931"
13
```

Count:

```text id="k932"
8
```

---

Value:

```text id="k933"
15
```

Count:

```text id="k934"
9
```

Counts only increase.

Perfect Binary Search.

---

# Efficient Counting Trick

Naive counting:

```text id="k935"
O(n²)
```

Too slow.

---

# Smart Counting

Start:

```text id="k936"
Bottom Left
```

Example:

```text id="k937"
1  5  9

10 11 13

12 13 15
```

Start:

```text id="k938"
12
```

---

If:

```text id="k939"
12 <= mid
```

then:

```text id="k940"
Entire Column Above
```

also valid.

Count all at once.

Move right.

---

Else:

Move up.

---

# Counting Complexity

```text id="k941"
O(n)
```

---

# Dry Run

k:

```text id="k942"
8
```

Search:

```text id="k943"
1 → 15
```

---

Mid:

```text id="k944"
8
```

Count:

```text id="k945"
2
```

Need larger.

---

Mid:

```text id="k946"
12
```

Count:

```text id="k947"
6
```

Need larger.

---

Mid:

```text id="k948"
13
```

Count:

```text id="k949"
8
```

Valid.

Search smaller.

---

Answer:

```text id="k950"
13
```

---

# What To Say In Interview

Instead of searching matrix positions, I'll binary search the value range.

For every candidate value, I count how many elements are less than or equal to it.

This count is monotonic, allowing binary search.

---

# Optimal Java Code

```java id="k951"
class Solution {

    public int kthSmallest(
            int[][] matrix,
            int k
    ) {

        int n =
                matrix.length;

        int left =
                matrix[0][0];

        int right =
                matrix[n-1][n-1];

        while(left < right){

            int mid =
                left +
                (right-left)/2;

            int count =
                    countLessEqual(
                            matrix,
                            mid
                    );

            if(count < k){

                left = mid + 1;

            }else{

                right = mid;
            }
        }

        return left;
    }

    private int countLessEqual(
            int[][] matrix,
            int target
    ){

        int n =
                matrix.length;

        int row =
                n - 1;

        int col = 0;

        int count = 0;

        while(row >= 0
            &&
              col < n){

            if(matrix[row][col]
                <= target){

                count += row + 1;

                col++;

            }else{

                row--;
            }
        }

        return count;
    }
}
```

---

# Complexity

```text id="k952"
Counting :

O(n)

Binary Search :

O(log(max-min))

Overall :

O(n log(max-min))
```

---

# Interview Follow-Up

### Why Not Heap?

Heap:

```text id="k953"
O(k log n)
```

Binary Search:

```text id="k954"
O(n log(max-min))
```

Often faster when:

```text id="k955"
k is large
```

---

# Binary Search On Value Space

This is a new family.

---

# Classic Binary Search

Search:

```text id="k956"
Index
```

---

# Binary Search On Answer

Search:

```text id="k957"
Answer
```

---

# Binary Search On Value Space

Search:

```text id="k958"
Value
```

while verifying:

```java id="k959"
count <= mid
```

---

# Recognition Checklist

Whenever interviewer says:

### Kth Smallest

```text id="k960"
Sorted Matrix
```

---

### Kth Smallest

```text id="k961"
Sorted Table
```

---

### Kth Smallest

```text id="k962"
Multiplication Table
```

Think:

```text id="k963"
Binary Search
On Value Space
```

---

# Advanced Binary Search Cheat Sheet

| Family              | Search Space     |
| ------------------- | ---------------- |
| Classic Search      | Indices          |
| Boundary Search     | Transition Point |
| Rotated Array       | Sorted Half      |
| Peak Family         | Direction        |
| Koko                | Answer           |
| Ship Packages       | Answer           |
| Split Array         | Answer           |
| Kth Smallest Matrix | Values           |

---

# Golden Rule

When interviewer asks:

```text id="k964"
Kth Smallest
```

don't immediately think:

```text id="k965"
Heap
```

Ask:

```text id="k966"
Can I count
how many values
are <= X ?
```

If yes,

you may have discovered:

```text id="k967"
Binary Search
On Value Space
```

which is often the intended optimal solution.
