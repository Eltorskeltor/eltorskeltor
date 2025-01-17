import java.util.*;
public class Actor {
    private int health; //health value of an object
    private int damage; //max damage that an object can deal
    private String name; //name of an object

    /**
     * Method to grab health of objects
     * 
     * @return health state of object
     */
    public int getHealth(){
        return health;
    }

    /**
     * Sets the health of an object
     * 
     * @param healthT  the health value the object will be set to
     */
    public void setHealth(int healthT){
        health = healthT;
    }

    /**
     * Grabs the damage of an object
     * 
     * @return damage that the object would deal
     */
    public int getDamage(){
        return damage;
    }

    /**
     * Sets the damage that the object will deal
     * 
     * @param damageT the damage that the object will be set to
     */
    public void setDamage(int damageT){
        damage = damageT;
    }

    /**
     * A method for one object to deal damage to another object
     * 
     * @param a the object that the damage will be dealt to
     */
    public void dealDamage(Actor a){
        Random randgen = new Random();
            a.setHealth(a.getHealth() - randgen.nextInt(getDamage()) - 1);
            if(a.getHealth() <= 0){
            System.out.println(name + " has killed " + a.getName() + "!");
            
        }
    }

    /**
     * Sets the name of an object
     * 
     * @param name is the new name for the object 
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Grabs the name of an object
     * 
     * @return the name stored in an object
     */
    public String getName(){
        return name;
    }
}
