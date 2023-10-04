package EasternKingdoms.Location.ElwynnForest;

import Game.Location;
import Game.NPC;

import java.util.ArrayList;
import java.util.List;

public class ElwynnForest extends Location{
    int requiredLevel;
    List<NPC> elwynnForestNPCList = new ArrayList<>();
    {
        elwynnForestNPCList.add(new YoungWolf());
        elwynnForestNPCList.add(new ForestSpider());
        elwynnForestNPCList.add(new KoboldWorker());
        elwynnForestNPCList.add(new DefiasBandit());
        elwynnForestNPCList.add(new MurlocForager());
        elwynnForestNPCList.add(new RiverpawRunt());
        elwynnForestNPCList.add(new Hogger());
    }

    @Override
    public List<NPC> getNpcList() {
        return elwynnForestNPCList;
    }

//    public List<NPC> getElwynnForestNPCList() {
//
//    }



    public ElwynnForest(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }
}
