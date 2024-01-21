package Project3_220;

import static java.lang.Math.pow;

public class GameLoop extends Thread{
    private   final int FPS = 120;
    protected final int UPS = 600;
    private MyFrame frame;
    private boolean GameOn = true;
    protected static boolean gamePause = false;
    protected static boolean gameClose = false;
    
    public void setFrame(MyFrame frame)         { this.frame = frame;   }
    public void setGameOn(boolean b)            { GameOn = b;           }
    public static void setGamePause(boolean b)  { gamePause = b;        }
    public static void setGameClose(boolean b)  { gameClose = b;        }
    public static boolean getGamePause()        { return gamePause;     }

    public void run(){
        double TimePerFrame = pow(10,9)*1.0/FPS;
        double TimePerUpdate = pow(10,9)*1.0/UPS;
        long previoustime = System.nanoTime();
        double deltaU = 0, deltaF = 0;
        long currentTime, lastcheck=System.currentTimeMillis(), now;
        int update = 0;
        //long now, lastFrame=System.nanoTime();

        while(GameOn){
            currentTime = System.nanoTime();
            now = System.currentTimeMillis();
            deltaU += (currentTime - previoustime)/TimePerUpdate;
            deltaF += (currentTime - previoustime)/TimePerFrame;
            previoustime = currentTime;
            if(deltaU >= 1){
                if (!gamePause) frame.Update(++update);
                deltaU--;
            }
            if(deltaF >= 1) {
                frame.repaint();
                deltaF--;
            }
            if(now - lastcheck >= 1000){
                lastcheck = now;
                update =0;
            }
            /*if(now-lastFrame >= TimePerFrame){
                //call repaint()
                lastFrame = now;
            }*/
        }
    }
}
