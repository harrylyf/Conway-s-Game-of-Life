# Conway's Game of Life

Author: Yufan Liu

## The Game of Life
The universe of the Game of Life is a two-dimensional orthogonal grid of square cells. Each of the cell has one of two possible states, alive or dead. Every cell interacts with its eight neighbours. At each step in time, the following transitions occur:

1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
2. Any live cell with two or three live neighbours lives on to the next generation.
3. Any live cell with more than three live neighbours dies, as if by overpopulation.
4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

For more information, visit the [Wikipedia](http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) page.

## Features
The following are the features of this version of the Game of Life:

1. Change the size of the field from 10x10 to 500x500
2. Manually set/clear any cell in the field using mouse click and mouse track. (to clear, just click on the active cell)
3. Clear the entire field
4. Randomly fill the field
5. Advance the game by pressing "Next Generation"
6. Customize the values of the "survive" and "birth" thresholds.
7. "Torus" Mode
8. A start/stop button that advances the game automatically using a separate thread with a delay between updates settable between 10 milliseconds and 1 second.

