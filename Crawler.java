import java.util.*;

public class Crawler {
    static boolean playing = true; //loop for playing game
    static Scanner scnr = new Scanner(System.in); //scanner for entire class
    static int size = 0; //boundary limit for game

    static ArrayList<Monster> Enemies = new ArrayList<Monster>(); //list of monsters

    public static void main(String[] args) throws Exception { //main method

        System.out.println("What would you like to name your Character?"); //hero creation
        Hero player = new Hero(scnr.nextLine());

        boolean loopControl = true; //while true loops will keep going

        while(loopControl){ //dungeon generation
            System.out.println("How big of a dungeon would you like to explore? Choose a whole number from 6-10");

            if(scnr.hasNextInt()){ //check for proper input
                int temp = scnr.nextInt();
                if(temp>5 && temp<11){ //check if proper size
                    size = temp;
                    loopControl = false;
                }
                else{
                    System.out.println("Please choose a whole number from 6-10");
                }
            }
            else{
                System.out.println("That was not a whole number.");
                scnr.nextLine();
            }
        }
        
        addEnemies((size * size) / 6); //call method to generate enemies
        placeEnemies(); //call method to place enemies in dungeon

        while(playing){ //actual game loop
            int[] spot = player.getLocation(); //create local reference for player location

            for(int row = 0; row < size; row++){ //grid making loop
                for(int col = 0; col < size; col++){

                    if (spot[0] == row && spot[1] == col) { //generate player location
                        System.out.print(" x ");
                    }

                    else{ //all other locations
                        System.out.print("[] ");
                    }

                    if(col == size-1){ //move to next row
                        System.out.println("");
                    }
                }
            }

            //alert players for nearby monsters using detectEnemies method.
            System.out.println("You have " + detectEnemies(player) + " enemies near you.");

            //prompt and grab for direction to move
            System.out.println("What direction would you like to move? North/East/South/West");
            String input = scnr.next().toLowerCase();
            
            //if a valid direction, continue with game
            if(input.contains("south") || input.contains("east") || input.contains("west") || input.contains("north")){
            player.move(input, size); //call player move method

                if(spot[0] == size-1 && spot[1]==size-1){  //check if user found exit
                        System.out.println("You have found the exit!");
                        int goldFound = 0;
                        for(int temp = 0; temp < player.getInvList().size(); temp++){ //get gold for extra points for player
                            if(player.getInvList().get(temp) == "gold"){
                                goldFound++;
                            }
                        }
                        System.out.println("Congrats, you escaped with " + goldFound + " gold pieces!");
                        playing = false;
                    }

                else if(player.getHealth()<= 0){ //if player ran out of health from moving
                    System.out.println("You ran out of food and starved. Woe is you!");
                    playing = false;
                }

                else{ //check if player has found a monster, and fight if they have
                    battle(player);
                }                
            }

            else{ //if they input an invalid direction
                System.out.println("Not a valid direction");
            }
        }
        scnr.close(); //close class scanner
    }

    /**
     * A method to add a number of enemies to the enemies list based on size inputed
     * @param amount the number of enemies to generate
     */
    static public void addEnemies(int amount){
        Random randMon = new Random(); //to randomize what monster is made
        for(int temp=0; temp<amount; temp++){ //making the monster
            switch(randMon.nextInt(2)){

                case 0:
                    Monster basilisk = new Monster(); //lower health higher damage monster
                    basilisk.setName("Basilisk"); //set the name, health and damage of monster
                    basilisk.setHealth(20);
                    basilisk.setDamage(8);
                    Enemies.add(basilisk); //add monster to the enemies list
                    break;

                case 1:
                    Monster minotaur = new Monster();
                    minotaur.setName("Minotaur");
                    minotaur.setHealth(30);
                    minotaur.setDamage(5);
                    Enemies.add(minotaur);
                    break;
            }
        }
    }

    /**
     * Method to place the enemies in the dungeon
     * If multiple monsters occupy the same location
     * it moves one of the monsters and then checks for another repeat
     */
    static public void placeEnemies(){
        for(int temp = 0; temp < size; temp++){ //loop to set a location for each monster initally
            Enemies.get(temp).setLocation(size);
            }

        for(int dupCheck = 0; dupCheck < Enemies.size(); dupCheck++){ //loop to check if monsters have duplicate rooms
            for(int temp = 0; temp < Enemies.size(); temp++){ //loop to check against each monster

                int[] enemy1 = Enemies.get(temp).getLocation(); //grab monster locations for comparing
                int[] enemy2 = Enemies.get(dupCheck).getLocation();
                
                //if monsters have same room and are not the same object
                if(enemy1[0] == enemy2[0] && enemy1[1] == enemy2[1] && temp != dupCheck){
                    Enemies.get(dupCheck).setLocation(Enemies.size()); //sets a new location one of the monsters
                    dupCheck = 0; //resets check value
                    temp = 0; //resets second check value
                }
            }
        }
    }

    /**
     * Enemy detection and battle method
     * First the method checks if the player is in the same location as an enemy
     * If they are then it starts a battle loop Where the player can choose to attack or use an item.
     * After each attack they are prompted again until the battle ends. If the player wins they get
     * an item. If they lose the game ends.
     * @param player So the game knows which object to reference
     */
    static public void battle(Hero player){
        int[] playerLocation = player.getLocation(); //local location for referencing
        for(int enemy = 0; enemy < Enemies.size(); enemy++){ //loop for each enemy to check
            int[] enemyLocation = Enemies.get(enemy).getLocation(); //local variable for enemy location

            if(playerLocation[0] == enemyLocation[0] && playerLocation[1] == enemyLocation[1]){ //if in same spot
                    //Start fight loop
                System.out.println("You have ran into a " + Enemies.get(enemy).getName() + "! Prepare for battle!");
                boolean fight = true;
                    //Fight loop
                while(fight && (player.getHealth() > 0)){
                    System.out.println("What would you like to do? Attack/Inv");
                    String choice = scnr.next().toLowerCase();
                    if(choice.equals("attack")){ //Both objects call the deal damage method on the other
                        player.dealDamage(Enemies.get(enemy));
                        Enemies.get(enemy).dealDamage(player);

                        if(Enemies.get(enemy).getHealth() <=0){ //if enemy dies remove enemy from enemies array
                            fight = false;
                            Enemies.get(enemy).getLoot(player);
                            Enemies.remove(enemy);
                        }

                        System.out.println("Your health is: " + player.getHealth()); //let player know current health

                        if(player.getHealth() <=0){ //if player dies
                            playing = false; //close game loop
                            System.out.println("You have been slain! Oh woe is you!");
                        }
                    }

                    if(choice.equals("inv") || choice.equals("inventory")){ //if using an item
                        System.out.println("You have these items in your inventory: ");
                        for(int temp = 0; temp < player.getInvList().size(); temp++){
                            System.out.println(player.getInvList().get(temp)); //tell player their inventory list
                        }

                        boolean choose = true;
                        while(choose){ //using item loop
                            System.out.println("Please say what item you would like to use, or say 'exit'");
                            String pick = scnr.next();
                            //if a valid item to use
                            if(pick.contains("potion") || pick.contains("gold") || pick.contains("weapon")){
                                player.useItem(pick);                                
                            }

                            //close loop on exit command
                            if(pick.contains("exit")){
                                choose = false;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to alert player of monsters in the adjacent rooms
     * @param a the hero to reference
     * @return the amount of monsters located nearby
     */
    static public int detectEnemies(Hero a){
        int detected = 0; //value to return
        int[] playerLocation = a.getLocation(); //local variable for reference
        for(int enemy = 0; enemy < Enemies.size(); enemy++){ //loop through all monsters
            int[] enemyLocation = Enemies.get(enemy).getLocation(); //local variable for reference
                    //checks each room adjacent to player for monster
            if((playerLocation[0] == enemyLocation[0] +1) && (playerLocation[1] == enemyLocation[1]) ){
                detected+=1;
            }
            else if((playerLocation[0] == enemyLocation[0] -1) && (playerLocation[1] == enemyLocation[1]) ){
                detected+=1;
            }
            else if((playerLocation[1] == enemyLocation[1] -1) && (playerLocation[0] == enemyLocation[0]) ){
                detected+=1;
            }
            else if((playerLocation[1] == enemyLocation[1] +1) && (playerLocation[0] == enemyLocation[0]) ){
                detected+=1;
            }
        }

        return detected;
    }
}
