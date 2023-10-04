package Game;

public class Player {

    int maxHealth = 100;
    int health = maxHealth;
    int level = 1;
    int damage = 15;
    String name;
    NPC targetNPC;
    int experience = 0;
    int experienceToUpLevel = 250;

    boolean isRest = false;

    public boolean isRest() {
        return isRest;
    }

    public void setRest(boolean rest) {
        isRest = rest;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    int coin;

    boolean isFight = false;

    public boolean isFight() {
        return isFight;
    }

    public void setFight(boolean fight) {
        isFight = fight;
    }

    int maxEnergy = 100;

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public int getEnergy() {
        return Energy;
    }

    public void setEnergy(int energy) {
        Energy = energy;
    }

    int Energy = 100;





    public NPC getTargetNPC() {
        return targetNPC;
    }

    public void setTargetNPC(NPC targetNPC) {
        this.targetNPC = targetNPC;
    }



    public int getExperienceToUpLevel() {
        return experienceToUpLevel;
    }

    public void setExperienceToUpLevel(int experienceToUpLevel) {
        this.experienceToUpLevel = experienceToUpLevel;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
    }




    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }



    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
