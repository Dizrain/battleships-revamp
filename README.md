# Battleships Revamp

## Project Description

### Introduction

**Battleships Revamp** is a game that lets the player enjoy their favorite game of Battleships without having to play
with someone else. The game generates ships that are located randomly on the board and the player's goal is to simply
find and destroy all of them. But, there's a catch. The game would be too simple if there woudldn't be any obstacles.
That's why **Battleships Revamp** introduces a feature that isn't present in the original game: mines! You as the player
have to avoid mines at all costs in order to win, but they're hidden in the water so every move has to be calculated.

### How was the game built?

This game, as simple as it might look, required some thinking and problem-solving along the way. The whole game is built
using small parts and then combined them into a whole picture. For example, every game object has its own class and its
own methods, but the use of them is directed mostly by the class *Board*. It is the game's core and most of the
processes are happening there. There's processes like generating a ship, which requires picking a random direction where
the boat is going to face and generating the boat into the available direction. The class *Board* should also take in
consideration the tiles that are already in use and the ones that are either too close to another ship or off-bounds.
There is also the *Tile* class that has important methods that a tile located on the board should have such as its state
and whether it's clicked or not.

**Battleships Revamp** is also using a feature that allows it to play sounds, which makes the game more dynamic. This
technology is called Media and is a library provided by JavaFX.

### Challenges and plans

There was quite a bit of challenges that were faced during development. One of the biggest ones was the ship generation.
It was really important to make the generation work in all scenarios but still keep it random, that's why the largest
portion of the *Board* class is mostly handling the generation of the ships and the different cases where a ship could
face an available direction depending on the neighbour tiles. Another challenge was implementing the "New Game" and
"Cheat Mode" features because it required managing arrays in such a way that will make possible showing different colors
for the tiles and cleaning the board when the game was done.

The main plan for the future is adding a score system that will calculate the player's score depending on how many boats
they destroyed and how many tiles they got right. Also, one other feature that could be added is a choice of difficulty
where the player could choose to play on "Hard" difficulty. Playing in that mode would generate more mines and would
make it possible for them to generate right next to the ships.

## Design

### Explanation of the classes

The structure of the classes is pretty straight forward. The *Main* class is responsible for generating the grid of
tiles and handles sounds and mouse click. Basically, mostly the front-end of the game. The back-end of the game is
mostly located in the *Board* class where it's possible to find the logic for every action in the game. The *Ship*, 
*Mine*, and the three types of ships are all game objects and are implementing the *GameObject* interface.

https://postimg.cc/zHj8c6hk -> UML Diagram

https://postimg.cc/MnhHVkmc -> Screenshot 1
https://postimg.cc/SjNSXNbT -> Screenshot 2