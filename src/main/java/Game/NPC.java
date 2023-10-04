package Game;

public class NPC {
    int health;
    int currentHealth;

    int coin;

    public int getCoin() {
        return coin;
    }

    int damageDone;
    String name;
    int Experience;

    public int getExperience() {
        return Experience;
    }

    public int getHealth() {
        return health;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getDamageDone() {
        return damageDone;
    }

    public String getName() {
        return name;
    }

    public NPC createNewNPC(){
        return new NPC();
    }
}
