# Tic Tac Toe

This is an implementation of TicTacToe, allowing for single player and multiplayer versions. The single player version allows you to select a varying AI difficulty with hard and insane usually ending in a draw or loss for the player.

## Getting Started

To get a local copy up and running, you will need a Java development environment such as JDK.

### Prerequisites

- Java Development Kit (JDK)

### Installing

```bash
git clone https://github.com/PetersonGuo/TicTacToe
cd TicTacToe
```

## Playing the game

Run the following commands to start the game, then follow the prompts
```bash
javac *.java
java Main
```

## About the algorithms
The hard and insane AI utilizes a minimax algorithm, which is an Iterative Depth-First Search algorithm that generates branches by exploring all possible moves. This algorithm traverses all branches to the maximum depth of the board until the game concludes. To determine the optimal move, the AI assigns a score of 1 for an AI win, 0 for a draw, and -1 for a loss. At each move, it assumes the player makes the most optimal move, and the AI does the same to consider the best possible outcome. Once the tree is fully traversed, the AI makes the best possible calculated move.