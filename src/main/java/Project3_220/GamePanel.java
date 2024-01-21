package Project3_220;

import javax.swing.*;

public class GamePanel extends JLayeredPane implements MyUtil {
    private GameFrame gameFrame;
    private JLabel [] BGLabel;
    private int LastBgActive = 1;

    public GamePanel(GameFrame gameFrame){
        this.gameFrame=gameFrame;
        setBounds(-640, 0, 1280*2, 720);
        setLayout(null);

        JLayeredPane BGPanel = new JLayeredPane();
        BGPanel.setBounds(0, 0, 1280*2, 720);

        //add the 'Ground'
        for(int i=0; i<2; i++){
            JLabel g = new JLabel(new ImageIcon(BGPath+"GameBG1.png"));
            g.setBounds(i*1280, 0, 1280, 720);
            add(g,JLayeredPane.DRAG_LAYER);
        }

        //add Background
        BGLabel = new JLabel[6];
        for(int i = 0 ; i < 6 ; i=i+2){
                BGLabel[i] = new JLabel();BGLabel[i+1] = new JLabel();
                BGLabel[i] = new JLabel(new ImageIcon(BGPath+"GameBG"+(i/2+2)+".png"));
                BGLabel[i+1] = new JLabel(new ImageIcon(BGPath+"GameBG"+(i/2+2)+".png"));
                BGLabel[i].setBounds(0, 0, 1280, 720);BGLabel[i+1].setBounds(1280, 0, 1280, 720);
                BGPanel.add(BGLabel[i],JLayeredPane.DRAG_LAYER);BGPanel.add(BGLabel[i+1],JLayeredPane.DRAG_LAYER);
        }

        add(BGPanel,JLayeredPane.DEFAULT_LAYER);
    }

    //Control camera angle
    public void setlocation(int xonscreen, int width, boolean facingLeft){
        if(facingLeft){
            if(xonscreen<gameFrame.getX()+gameFrame.getWidth()/5 && getX()+1<=0) {
                if(LastBgActive==4){BGLabel[4].setLocation(BGLabel[4].getX() - 1, 0);
                    BGLabel[5].setLocation(BGLabel[5].getX() - 1, 0);}
                if(LastBgActive==2||LastBgActive==4){BGLabel[2].setLocation(BGLabel[2].getX() - 1, 0);
                    BGLabel[3].setLocation(BGLabel[3].getX() - 1, 0);}
                if(LastBgActive==4) LastBgActive=1;
                LastBgActive++;
                setLocation(getX() + 1, getY());
            }
        }
        else {
            if(xonscreen+width>gameFrame.getX()+gameFrame.getWidth()*4/5 &&
              getX()+getWidth()-1>gameFrame.getWidth()) {
                if(LastBgActive==4){BGLabel[4].setLocation(BGLabel[4].getX() + 1, 0);
                    BGLabel[5].setLocation(BGLabel[5].getX() + 1, 0);}
                if(LastBgActive==2||LastBgActive==4){BGLabel[2].setLocation(BGLabel[2].getX() + 1, 0);
                    BGLabel[3].setLocation(BGLabel[3].getX() + 1, 0);}
                if(LastBgActive==4) LastBgActive=1;
                LastBgActive++;
                setLocation(getX() - 1, getY());
            }
        }
    }
}
