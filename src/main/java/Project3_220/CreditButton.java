package Project3_220;

import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class CreditButton extends MyButton{
    private JLabel credits;
    
    public CreditButton(MainApplication Frame, JLayeredPane Content, JPanel MenuPanel){
        this.Frame = Frame;
        this.ContentPane = Content;
        this.MenuPanel = MenuPanel;
        set3Icon(BTPath + "CreditButton.png");
        OwnPanel = new JPanel();
        
        Image img = new ImageIcon(ImgPath+"CreditPage.PNG").getImage().getScaledInstance(729, 450, Image.SCALE_SMOOTH);
        credits = new JLabel(new ImageIcon(img));
        credits.setBounds(0, 0, 730, 450);
        
        MyButton BackButton = new MyButton(){
            @Override
            public void setPanel(MainApplication Frame, JLayeredPane Content, JPanel MenuPanel, JPanel OwnPanel){
                this.Frame = Frame;
                this.ContentPane = Content;
                this.MenuPanel = MenuPanel;
                this.OwnPanel = OwnPanel;
                set3Icon(BTPath+"BackButton.png");
                setBounds(213, 472, 304, 98);
                setBorderPainted(false);
                setContentAreaFilled(false);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    setSelected(Entered = false);
                    ContentPane.remove(OwnPanel);
                    ContentPane.add(MenuPanel, JLayeredPane.DRAG_LAYER);
                    Frame.repaint();
                }
            }
        };
        BackButton.setPanel(Frame, Content, MenuPanel, OwnPanel);
        BackButton.addMouseListener(BackButton);
        
        OwnPanel.setLayout(null);
        OwnPanel.setBounds(275, 57, 729, Frame.getHeight()-57);
        OwnPanel.setOpaque(false);
        
        OwnPanel.add(credits);
        OwnPanel.add(BackButton);
    }
}
