package EasternKingdoms.RedridgeMountains;

import EasternKingdoms.Location.ElwynnForest.ForestSpider;
import EasternKingdoms.Location.ElwynnForest.YoungWolf;
import Game.Location;
import Game.NPC;

import java.util.ArrayList;
import java.util.List;

public class RedridgeMountains extends Location {
    int requiredLevel;
    String locationName = "Красногорье";

    @Override
    public String getLocationName() {
        return locationName;
    }

    List<NPC> redridgeMountainsNPCList = new ArrayList<>();
    {
        redridgeMountainsNPCList.add(new YoungWolf());
        redridgeMountainsNPCList.add(new ForestSpider());
    }

    @Override
    public List<NPC> getNpcList() {
        return redridgeMountainsNPCList;
    }





    public RedridgeMountains(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public int getRequiredLevel() {
        return requiredLevel;
    }
}
