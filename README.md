MazeSolver
==========

A small project I did in a night as an excuse to implement DFS and BFS.

You can load a .maze file and see it on the screen. You can then choose
between a stack-based (DFS) search or a queue-based (BFS) search. Once
you have picked a mode you can click step to see what the next step
that the alogorithm would pick or hit "Toggle Animation" to run step
on a timer (hitting it again will stop the timer.)

The program will tell you when it finds a solution. It will stop the
algorithm. If it traverses the entire maze that is accessible from the
start point without finding the end it will report back that no
solution was found.

If you want to add your own maze files you can add create a file like

############
#.#........#
#.#.######.#
#.#....#...#
#.###.*#.#.#
#...####.#.#
#.#.#..#.#.#
#.#.#.##.#.#
#o#......#.#
############

At the moment the program only works with 12x10 mazes, only because I
hard-coded those values to make the initial setup easier. I might fix
that later.

LEGEND
# - Wall
. - Open space
o - Start Position
* - End Position (optional)
