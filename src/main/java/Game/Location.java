package Game;

import java.util.List;

public abstract class Location {
    int requiredLevel;
    String locationName;

    public String getLocationName() {
        return locationName;
    }

    List<NPC> npcList;

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public List<NPC> getNpcList() {
        return npcList;
    }
}
