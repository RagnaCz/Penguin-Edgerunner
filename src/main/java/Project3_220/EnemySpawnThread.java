package Project3_220;

import javax.swing.*;
import java.util.*;

public class EnemySpawnThread extends Thread{
    private boolean gameStart = true;
    private GamePanel gamepanel;
    private int Level;
    private int Wave;
    private ArrayList<Goblin> EnemyList;
    private int CurrentWave = 0,MaxEnemyInWave,EnemyRequired;
    private boolean gameFinish = false;

    public EnemySpawnThread(GamePanel gamepanel, Player player, int Level, int Wave){
        this.gamepanel = gamepanel;
        this.Level = Level;
        this.Wave = Wave;
        EnemyList = new ArrayList<>();
        Goblin.LoadResource(player, EnemyList, Level);//Create the goblin prototype
    }
    public void run(){
        boolean clearWave = true;
        for(int i=0, enemyNum=(int) Math.ceil(Level/2.0)+2;  i<Wave && gameStart;  i++){
            if (clearWave) {
//                System.out.printf("Wave: %d | Enemy: %d\n", (i+1), enemyNum);
                CurrentWave = i+1;
                MaxEnemyInWave = EnemyRequired = enemyNum;
            }

            for(int j=0; j<enemyNum && gameStart && clearWave; j++){
                try {
                    Thread.sleep(new Random().nextInt(1500,3500));
                }catch (InterruptedException e){e.printStackTrace();}
                finally {
                    if(!GameLoop.getGamePause()) enemySpawn();
                    else j--;
                }
            }

            if(EnemyList.size()==0){
                clearWave=true;
                enemyNum += (1+Level);
            }else {
                clearWave=false;
                i--;
            }

        }

        if(gameStart) gameFinish=true;
    }
    public void enemySpawn(){
        //Create a Goblin
        Goblin temp = Goblin.createGoblin(new Random().nextInt(320,2093));
        gamepanel.add(temp,JLayeredPane.PALETTE_LAYER);
        EnemyList.add(temp);
    }

    public void clearBeforeGameClose(boolean b){Goblin.gameClose(b); if(b)gameStart = false;}

    public int getCurrentWave()     {return CurrentWave;}
    public int getEnemyLeft()       { if(CurrentWave == 1) return MaxEnemyInWave-EnemyRequired;
                                      else                 return MaxEnemyInWave-EnemyRequired-1; }
    public int getMaxEnemyInWave()  {return MaxEnemyInWave;}
    public boolean isGameFinish()   {return gameFinish;}
    public void killEnemy()         { EnemyRequired--; }

}
