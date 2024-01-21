package Project3_220;

import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class SettingButton extends MyButton{
    private boolean      isIngame = false;
    private JLayeredPane GamePanel;
    private JPanel       PausePanel;

    //Temp Reference;
    private MainApplication TFrame;
    private JLayeredPane TContent;
    private JPanel TMenuPanel;

    private MyButton BackButton;
    private SettingButton Setting;

    private Audio BGMusic;

    public SettingButton(MainApplication Frame, JLayeredPane Content, JPanel MenuPanel){
        this.Frame = TFrame = Frame;
        this.ContentPane = TContent = Content;
        this.MenuPanel = TMenuPanel = MenuPanel;
        set3Icon(BTPath + "SettingButton.png");
        OwnPanel = new JPanel();
        Setting = this;
        AudioCollection sfxCollection = new AudioCollection();

        String[] path = {"SettingText.PNG", "SFXText.PNG", "BGMText.PNG"};
        int[][] bound = {{213,0,304,115},{0,177,220,85},{0,292,220,85}};
        ImageIcon[] icon = MyUtil.importImg(ImgPath, path);
        JLabel[] text = new JLabel[path.length];
        for(int i=0; i<path.length; i++){
            text[i] = new JLabel();
            text[i].setIcon(icon[i]);
            text[i].setBounds(bound[i][0], bound[i][1], bound[i][2], bound[i][3]);
        }

        //JSlider: Extra component
        JSlider SFX = new JSlider(-80,6,SwingConstants.HORIZONTAL);
        SFX.setBounds(235, 214, 350, 10);
        SFX.setName("SFX");
        SFX.setValue(-37);
        SFX.setOpaque(false);
        JSlider BGM = new JSlider(-80,6,SwingConstants.HORIZONTAL);
        BGM.setName("BGM");
        BGM.setValue(-37);
        BGM.setBounds(235, 329, 350, 10);
        BGM.setOpaque(false);

        BGMusic = new Audio(MusPath+"BGM.wav");
        BGMusic.playLoop();
        BGMusic.currentVolume = BGM.getValue();
        BGMusic.fc.setValue(BGMusic.currentVolume);
        AudioCollection.currentVolume = SFX.getValue();

        BGM.addChangeListener(e -> {
            BGMusic.currentVolume = BGM.getValue();
            BGMusic.fc.setValue(BGMusic.currentVolume);
        });

        SFX.addChangeListener(e -> {
            AudioCollection.currentVolume = SFX.getValue();
        });

        class SoundOnOffButton extends MyButton{
            private final ImageIcon[] icon = new ImageIcon[4];
            private final JSlider slider;
            public SoundOnOffButton(JSlider slider){
                this.slider = slider;
                ImageIcon pic = new ImageIcon(MyUtil.BTPath+"NoteSwitch.png");
                Image img;
                for(int i=0; i<2; i++){
                    for(int j=0; j<2; j++){
                        img = MyUtil.crop(pic.getImage(), i*103,j*102, 103,102);
                        icon[i*2+j] = new ImageIcon(img);
                    }
                }
                for(ImageIcon i: icon) if(i==null) System.out.println("y");
                setIcon(icon[2]);
                setSelectedIcon(icon[3]);

                setBorderPainted(false);
                setContentAreaFilled(false);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    if(getIcon()==icon[0]){
                        setIcon(icon[2]);
                        setSelectedIcon(icon[3]);//sound on
                        if(slider.getName() == "BGM")BGMusic.volumeMute(BGM);
                        if(slider.getName() == "SFX")AudioCollection.volumeMute(SFX);
                    }
                    else{
                        setIcon(icon[0]);
                        setSelectedIcon(icon[1]);//sound off
                        if(slider.getName() == "BGM")BGMusic.volumeMute(BGM);
                        if(slider.getName() == "SFX")AudioCollection.volumeMute(SFX);
                    }
                }
            }
        }
        MyButton SFXButton = new SoundOnOffButton(SFX);
        SFXButton.setBounds(600, 169, 103, 102);
        SFXButton.addMouseListener(SFXButton);

        MyButton BGMButton = new SoundOnOffButton(BGM);
        BGMButton.setBounds(600, 284, 103, 102);
        BGMButton.addMouseListener(BGMButton);

        BackButton = new MyButton(){
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
                    if(isIngame==false)Frame.repaint();
                }
            }
        };
        BackButton.setPanel(Frame, ContentPane, MenuPanel, OwnPanel);
        BackButton.addMouseListener(BackButton);

        OwnPanel.setLayout(null);
        OwnPanel.setBounds(275, 57, 703, Frame.getHeight()-57);
        OwnPanel.setOpaque(false);

        for(int i=0; i<path.length; i++) OwnPanel.add(text[i]);
        OwnPanel.add(SFXButton);
        OwnPanel.add(BGMButton);
        OwnPanel.add(SFX);
        OwnPanel.add(BGM);
        OwnPanel.add(BackButton);
    }

    public void SettingConfig(boolean BOOL){
        isIngame = BOOL;
        if(isIngame){
            this.ContentPane = GamePanel;
            this.MenuPanel = PausePanel;
            BackButton.setPanel(Frame, ContentPane, MenuPanel, OwnPanel);

        }else{
            this.ContentPane = TContent;
            this.MenuPanel = TMenuPanel;
            BackButton.setPanel(Frame, ContentPane, MenuPanel, OwnPanel);
        }
    }
    public void setBGMusic(boolean bool){
        if(bool){
            BGMusic.stop();
            BGMusic.setNewFile(MusPath+"BGM2.wav");
            BGMusic.playLoop();BGMusic.fc.setValue(BGMusic.currentVolume);
        }else{
            BGMusic.stop();
            BGMusic.setNewFile(MusPath+"BGM.wav");
            BGMusic.playLoop();BGMusic.fc.setValue(BGMusic.currentVolume);
        }
    }
    public void setUIPanel(JPanel PausePanel)       {  this.PausePanel = PausePanel; }
    public void setGamePanel(JLayeredPane GamePanel){  this.GamePanel = GamePanel;   }
}