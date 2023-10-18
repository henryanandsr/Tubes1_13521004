# Tubes1_13521004
## Adversarial Adjacency-Strategy-Game

## Introduction
The “Adjacency” program was designed as an interactive and strategic board game that is played between two players. Using X’s and O’s on an 8x8 board outputted on a computer screen, the objective of the game is for a player to get more X’s than O’s in total or vice versa after a specific number of rounds.

The initial game starts with 4 X’s in the bottom left corner and 4 O’s in the top right corner.  The first player will begin by placing an X on an empty square (through clicking).  In turn, all O’s that are on adjacent squares are then replaced with X’s.  Play will continue with the second player taking his turn by placing an O on an empty square.  In turn, all X’s that are on adjacent squares are then replaced with O’s.

## Installation (IntelliJ)
To get started, please install the latest version of the Java Development Kit (JDK) and please install a Java IDE such as <a href="https://www.jetbrains.com/idea/">IntelliJ</a>. Please note that the deployment instructions below use IntelliJ as the IDE.

## Deployment (IntelliJ)
1. Clone the repository through Git by running the following command:<br>
`git clone https://github.com/GAIB20/adversarial-adjacency-strategy-game.git`, or simply download the repository.
2. Open the repository folder through IntelliJ.
3. Set up the JDK by going to File -> Project Structure -> Project tab. In the Project tab, go to Project SDK, click New, and browse to the location of your JDK folder.
4. NOTE: JavaFX has been removed starting from JDK 11, and it is now a standalone module. The JavaFX files needed to set up the Adjacency program are located in the repository folder itself. To set it up, go to File -> Project Structure -> Libraries tab.  In the Libraries tab, press the + button, browse to the "javafx-sdk/lib" folder in the repository, and add it to the list of libraries.
5. IMPORTANT: Go to Run -> Edit Configurations, and go to the VM options. In this line, please add in the full path to the lib folder of the javafx-sdk folder on your own computer, and then add the following line <br> `--add-modules=javafx.controls,javafx.fxml`.<br><br> 
For example, I added the following line to my VM options: `--module-path "C:\Jed's Work\CS Side Projects\Adjacency-Strategy-Game\javafx-sdk\lib" --add-modules=javafx.controls,javafx.fxml`
6. Open the Main class in the IntelliJ file interface.

## Member List:
| NIM | Name |
| :---: | :---: |
| 13521004 | Henry Anand Septian Radityo |
| 13521009 | Christophorus Dharma Winaya |
| 13521012 | Haikal Ardzi Shofiyyurrohman |
| 13521117 | Maggie Zeta Rosida Simangunsong |
