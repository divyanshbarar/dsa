# Binary Search Pattern - Part 3

# Matrix Family

---

# Why This Family Matters

Most candidates see:

```text id="m301"
2D Matrix
```

and immediately think:

```text id="m302"
Nested Loops
```

Interviewers want:

```text id="m303"
Binary Search
```

The trick is realizing:

```text id="m304"
A Matrix
can behave like
a Sorted Array
```

---

# Core Matrix Binary Search Insight

Matrix:

```text id="m305"
1   3   5   7

10 11 16 20

23 30 34 60
```

Can be visualized as:

```text id="m306"
1 3 5 7 10 11 16 20 23 30 34 60
```

A single sorted array.

---

# Pattern Recognition Clues

Think Matrix Binary Search when:

### Clue 1

```text id="m307"
Rows Sorted
```

---

### Clue 2

```text id="m308"
Columns Sorted
```

---

### Clue 3

```text id="m309"
O(log n)
```

required.

---

### Clue 4

```text id="m310"
Search Element
```

inside matrix.

---

# Problem 8: Search A 2D Matrix

## LeetCode 74

---

# Problem Explanation

Given:

```text id="m311"
1   3   5   7

10 11 16 20

23 30 34 60
```

Target:

```text id="m312"
3
```

Return:

```text id="m313"
true
```

---

# Matrix Property

Each row sorted.

And:

```text id="m314"
Last element
of row i

<

First element
of row i+1
```

This is crucial.

---

# What Is The Interviewer Testing?

Can you convert:

```text id="m315"
2D Search
```

into:

```text id="m316"
1D Binary Search
```

?

---

# Brute Force

Scan every cell.

```java id="m317"
for(row){

   for(col){

   }
}
```

---

# Complexity

```text id="m318"
Time :

O(m*n)
```

---

# Better Approach

Binary search each row.

---

# Complexity

```text id="m319"
Time :

O(m log n)
```

---

# Optimal Observation

Matrix behaves like:

```text id="m320"
Single Sorted Array
```

Length:

```text id="m321"
rows * cols
```

---

# Index Mapping Trick

Suppose:

```text id="m322"
rows = 3

cols = 4
```

Virtual Array:

```text id="m323"
0 1 2 3 4 5 6 7 8 9 10 11
```

Need:

```text id="m324"
row
```

and:

```text id="m325"
col
```

from:

```text id="m326"
mid
```

---

# Formula

Row:

```java id="m327"
mid / cols
```

Column:

```java id="m328"
mid % cols
```

---

# Example

```text id="m329"
mid = 6

cols = 4
```

Row:

```text id="m330"
6 / 4 = 1
```

Column:

```text id="m331"
6 % 4 = 2
```

Cell:

```text id="m332"
matrix[1][2]
```

---

# Dry Run

Matrix:

```text id="m333"
1 3 5 7

10 11 16 20

23 30 34 60
```

Target:

```text id="m334"
16
```

---

Search Space:

```text id="m335"
0 → 11
```

Mid:

```text id="m336"
5
```

Cell:

```text id="m337"
11
```

Need larger.

---

Mid:

```text id="m338"
8
```

Cell:

```text id="m339"
23
```

Need smaller.

---

Mid:

```text id="m340"
6
```

Cell:

```text id="m341"
16
```

Found.

---

# What To Say In Interview

Because every row starts after the previous row ends, the entire matrix is globally sorted.

I can treat it as a virtual 1D array and apply classic binary search.

---

# Optimal Java Code

```java id="m342"
class Solution {

    public boolean searchMatrix(
            int[][] matrix,
            int target
    ) {

        int rows =
                matrix.length;

        int cols =
                matrix[0].length;

        int left = 0;

        int right =
                rows * cols - 1;

        while(left <= right){

            int mid =
                    left +
                    (right-left)/2;

            int row =
                    mid / cols;

            int col =
                    mid % cols;

            int value =
                    matrix[row][col];

            if(value == target){

                return true;
            }

            if(value < target){

                left = mid + 1;

            }else{

                right = mid - 1;
            }
        }

        return false;
    }
}
```

---

# Complexity

```text id="m343"
Time :

O(log(m*n))

Space :

O(1)
```

---

# Similar Problems

* Search Insert Position
* Binary Search
* Kth Smallest Matrix

---

# Interview Follow-Up

### Why Is It Log(m*n)?

Because:

```text id="m344"
Binary Search
```

runs over:

```text id="m345"
m*n elements
```

---

# Problem 9: Search A 2D Matrix II

## LeetCode 240

---

# Problem Explanation

Matrix:

```text id="m346"
1  4  7  11 15

2  5  8  12 19

3  6  9  16 22

10 13 14 17 24

18 21 23 26 30
```

Target:

```text id="m347"
5
```

Return:

```text id="m348"
true
```

---

# Important Difference

Unlike LC 74:

```text id="m349"
Rows Sorted

Columns Sorted
```

BUT:

```text id="m350"
Matrix
is NOT globally sorted
```

---

# What Is The Interviewer Testing?

Can you use matrix properties instead of forcing binary search?

---

# Brute Force

Scan all cells.

---

# Complexity

```text id="m351"
O(m*n)
```

---

# Key Observation

Start at:

```text id="m352"
Top Right
```

---

# Why Top Right?

At position:

```text id="m353"
matrix[row][col]
```

If value is:

```text id="m354"
Too Large
```

move:

```text id="m355"
Left
```

---

If value is:

```text id="m356"
Too Small
```

move:

```text id="m357"
Down
```

---

# Visualization

Target:

```text id="m358"
5
```

Start:

```text id="m359"
15
```

Too large.

Move left.

---

```text id="m360"
11
```

Too large.

Move left.

---

```text id="m361"
7
```

Too large.

Move left.

---

```text id="m362"
4
```

Too small.

Move down.

---

```text id="m363"
5
```

Found.

---

# What To Say In Interview

The matrix isn't globally sorted, so the virtual-array trick doesn't work.

Instead, starting from the top-right corner allows me to eliminate either an entire row or an entire column in each step.

---

# Optimal Java Code

```java id="m364"
class Solution {

    public boolean searchMatrix(
            int[][] matrix,
            int target
    ) {

        int rows =
                matrix.length;

        int cols =
                matrix[0].length;

        int row = 0;

        int col =
                cols - 1;

        while(row < rows
            &&
              col >= 0){

            int value =
                    matrix[row][col];

            if(value == target){

                return true;
            }

            if(value > target){

                col--;

            }else{

                row++;
            }
        }

        return false;
    }
}
```

---

# Complexity

```text id="m365"
Time :

O(m+n)

Space :

O(1)
```

---

# Why Not Binary Search?

Because matrix is not:

```text id="m366"
Globally Sorted
```

Only:

```text id="m367"
Row Sorted

Column Sorted
```

---

# Matrix Family Revision Sheet

## Search A 2D Matrix

Property:

```text id="m368"
Globally Sorted
```

Use:

```java id="m369"
Virtual 1D Array
```

Formula:

```java id="m370"
row = mid / cols

col = mid % cols
```

---

## Search A 2D Matrix II

Property:

```text id="m371"
Rows Sorted

Columns Sorted
```

Use:

```text id="m372"
Top Right Walk
```

---

# Matrix Binary Search Cheat Sheet

### Matrix Globally Sorted?

Yes:

```text id="m373"
Binary Search
```

---

### Only Rows & Columns Sorted?

Yes:

```text id="m374"
Top Right Walk
```

---

### Mapping Formula

```java id="m375"
row = mid / cols

col = mid % cols
```

Memorize this.

It appears in many matrix interview problems.

---

# Golden Rule

Whenever interviewer gives:

```text id="m376"
Sorted Matrix
```

Ask:

```text id="m377"
Is the entire matrix
globally sorted?

or

Only rows and columns?
```

That single observation determines the entire solution.
