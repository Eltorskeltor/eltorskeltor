import java.util.*;
public class Monster extends Actor{

    private int[] location = new int[2]; //location storage for monsters

    /**
     * Loot generator for player upon monster dying
     * @param a the player to be given loot
     */
    public void getLoot(Hero a){
        String randLoot = "";
        Random lootGen = new Random(); //random to decide on what loot to give
        switch (lootGen.nextInt(7)) { //randomly gives between 1 of 3 possible loot
            case 0:
            case 1:
            case 2:
                randLoot = "gold";
                System.out.println("You have found gold!");
                break;
            case 3:
            case 4:
                randLoot = "potion";
                System.out.println("You have found potion!");
                break;
            case 5:
            case 6:
                randLoot = "weapon";
                System.out.println("You have found weapon!");
                break;
        }
        a.addInv(randLoot); //calls for add loot method for player
    }
    
    /**
     * Grabs location of the monster
     * @return location of monster
     */
    public int[] getLocation(){
        return location;
    }

    /**
     * Sets a random location in the dungeon for the monster to hang out
     * @param size //how big  the dungeon is to make sure it keeps monster in boundaries
     */
    public void setLocation(int size){
        Random randgen = new Random();
        int row = randgen.nextInt(size); //randomly assigns a row and column
        int column = randgen.nextInt(size);
        if(row == 0 && column == 0){ //if placed on starting location then moves it one space
            switch(randgen.nextInt(2)){
            case 0:
                row+=1;
                break;
            case 1:
                column+=1;
                break;
            }
        }
        if(row==size-1 && column==size-1){ //if placed on exit location then moves it one space
            switch (randgen.nextInt(2)) {
                case 0:
                    row-=1;    
                    break;
                case 1:
                    column-=1;
                    break;
            }
        }
        location[0] = row; //assigns row and column to location
        location[1] = column;
    }
}