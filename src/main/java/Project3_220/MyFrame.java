package Project3_220;

import javax.swing.*;

public abstract class MyFrame extends JFrame implements MyUtil {
    protected int width, height;
    protected MainApplication   MainFrame;
    protected GameFrame         gameFrame;
    protected GameLoop UPS;
    protected JLayeredPane      ContentPane;
    
    public MyFrame(){}
    public MyFrame(String t){ super(t); }
    
    public abstract void Update(int num);
}
