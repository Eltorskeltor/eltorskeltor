import java.util.ArrayList;

public class Hero extends Actor{
    private int[] location ={0,0}; //location of the hero

    ArrayList<String> inv = new ArrayList<String>(); //inventory list for hero

    /**
     * Hero creation method
     * Sets the name of the hero, assigns its starting damage and health
     * 
     * @param name the name given by the user to their hero
     */
    public Hero(String name){
        setName(name);
        setDamage(10);
        setHealth(100);
    }
    
    /**
     * Use item method for the hero to use their inventory during battle
     * The the items are removed from the player inventory after use
     * Each item calls its relevant method
     * @param item item to be used
     */
    public void useItem(String item){
        item = item.toLowerCase();
        if (inv.contains(item)) {
                if(item.contains("potion")){
                    heal();
                    inv.remove(item);
                }
                else if(item.contains("weapon")){
                    increaseDamage();
                    inv.remove(item);
                }
                else if(item.contains("gold")){
                    System.out.println("You cannot use gold here.");
                }
        }
    }

    /**
     * Method to add an item to the player inventory
     * @param item item to be added
     */
    public void addInv(String item){
        inv.add(item);
    }

    /**
     * Method to get the inventory list
     * @return the inventory list
     */
    public ArrayList<String> getInvList(){
        return inv;
    }

    /**
     * Method to heal the player upon using a potion
     * Adds 25 to their current health using the set and get methods
     */
    public void heal(){
        System.out.print("Your health went from: " + getHealth());
        setHealth(getHealth()+25);
        System.out.println(" to: " + getHealth());
    }

    /**
     * Method to increase player damage upon using a new weapon
     * Adds 5 to the max damage a player can do using the set and get methods
     */
    public void increaseDamage(){
        System.out.print("Your max damage went from: " + getDamage());
        setDamage(getDamage() + 5);
        System.out.println(" to: " + getDamage());
    }

    /**
     * Gets the current location of the hero
     * @return location the location of the hero
     */
    public int[] getLocation(){
        return location;
    }

    /**
     * Updates the players location using the direction the plan to head
     * @param direction dictates which part of the location array changes
     * @param size makes sure they dont leave the dungeon
     * @return new location of player
     */
    public int[] move(String direction, int size){
        int[] currentLocation = location; //local variable for player location

        if(direction.contains("south")){ //check direction
            if(currentLocation[0] != size-1){ //check if against boundary
                location[0] += 1; //update location
                setHealth(getHealth()-2); //reduce health
                System.out.println("Your health is: " + getHealth()); //tell user current health
            }
            else{ //if against boundary
                System.out.println("There is a wall in the way.");
            }
        }

        if(direction.contains("east")){
            if(currentLocation[1] != size-1){
                location[1] += 1;
                setHealth(getHealth()-2);
                System.out.println("Your health is: " + getHealth());
            }
            else{
                System.out.println("There is a wall in the way.");
            }
        }

        if(direction.contains("west")){
            if(currentLocation[1] != 0){
                location[1] -= 1;
                setHealth(getHealth()-2);
                System.out.println("Your health is: " + getHealth());
            }
            else{
                System.out.println("There is a wall in the way.");
            }
        }

        if(direction.contains("north")){
            if(currentLocation[0] != 0){
                location[0] -= 1;
                setHealth(getHealth()-2);
                System.out.println("Your health is: " + getHealth());
            }
            else{
                System.out.println("There is a wall in the way.");
            }
        }
        return location;
    }
}
