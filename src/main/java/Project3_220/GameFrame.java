package Project3_220;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

public class GameFrame extends MyFrame{
    /*
    Frequently used listener
    - MouseListener
    - KeyListener
    - WindowListener
    * Others e.g. ItemListener, ChangeListener
     */
    private static final Color c1 = new Color(19, 43, 48);
    private static final Color c2 = new Color(109, 139, 116);
    private static final Color c3 = new Color(239, 234, 216);

    private JPanel          PregamePanel;
    private JPanel          BgPanel;
    private JLabel[]         Background;
    private GamePanel       gamePanel;
    private Player           player;
    private UIPanel          uiPanel;
    private SettingButton     Setting;
    
    //Data before start game
    private String  UserName;
    private int     Level = 0;
    private int     wavelength = 5;
    private boolean             gameStart=false;
    private EnemySpawnThread    Spawn;
    private WindowAdapter       mywindow;

    public GameFrame(GameLoop UPS, int frameWidth, int frameHeight , MainApplication Menuframe, SettingButton Setting){
        super("Penguin Edgerunner Gameplay");
        this.UPS = UPS;
        width  = frameWidth;
        height = frameHeight;
        this.MainFrame = Menuframe;
        this.Setting = Setting;
        
        setSize(width,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        setMinimumSize(new Dimension(640,360));
        gameFrame = this;
        
        ContentPane = new JLayeredPane();
        setContentPane(ContentPane);
        ContentPane.setLayout(null);
        ContentPane.setBounds(0, 0, width, height);
        setBackground(new Color(42,28,43));
        Setting.setGamePanel(ContentPane);

        class MyWindowListener extends WindowAdapter{
            @Override
            public void windowClosing( WindowEvent e ){
                UPS.setFrame(MainFrame);
                MainFrame.setVisible(true);
            }
        }
        addWindowListener( mywindow = new MyWindowListener() );

        AddComponents();

	    setVisible(true);
    }
    
    public final void AddComponents(){
        JLabel           Text;
        JTextField       nameTextField;
        JPanel           Difficultypanel;
        ButtonGroup      DifficultyGroup;
        JToggleButton[]  DifficultyButton;
        JComboBox        Wave;
        MyButton         BackButton,PlayButton;

        BgPanel = new JPanel();
        PregamePanel = new JPanel();
        
        Text = new JLabel("Enter Your Name");
        Text.setBounds(290, 110, 700, 90);
        Text.setHorizontalAlignment(JTextField.CENTER);
        Text.setVerticalAlignment(JTextField.CENTER);
        Text.setFont(new Font("SansSerif",Font.BOLD,70));
        Text.setForeground(c1);
        Text.setBackground(c3);
        Text.setOpaque(true);
        Text.setBorder(new LineBorder(c2,5));

        //JTextField requirement
        nameTextField = new JTextField();
        nameTextField.setBounds(340, 231, 600, 70);
        nameTextField.setHorizontalAlignment(JTextField.CENTER);
        nameTextField.setFont(new Font("SansSerif",Font.BOLD,50));
        nameTextField.setForeground(c1);
        nameTextField.setBackground(new Color(255, 249, 227));
        nameTextField.setBorder(new LineBorder(c1,5));
        nameTextField.setDisabledTextColor(Color.RED);

        Difficultypanel = new JPanel();
        Difficultypanel.setAlignmentY(CENTER_ALIGNMENT);
        Difficultypanel.setBounds(190, 332, 900, 130);
        Difficultypanel.setBackground(c3);
        Difficultypanel.setBorder(new LineBorder(c2,5));
          JLabel TextDiff = new JLabel("Difficulty : ");
          TextDiff.setFont(new Font("SansSerif",Font.BOLD,30));
          TextDiff.setForeground(c1);
        Difficultypanel.add(TextDiff);

        //JComboBox requirement
        String [] Waveqty = {"5","8","10","12","15"};
        Wave = new JComboBox(Waveqty);
        Wave.setFont(new Font("SansSerif",Font.BOLD,30));
        Wave.addItemListener((ItemEvent e) -> {
            wavelength = Integer.parseInt(e.getItem().toString());
        });
        Wave.setFocusable(false);

        //JRadioButton requirement
        String [] Difficulty = {"Medium","Hard","Expert","Insane","Hell"};
        DifficultyButton = new JRadioButton[Difficulty.length];
        DifficultyGroup = new ButtonGroup();
        for(int i = 0 ; i < Difficulty.length ; i++){
            DifficultyButton[i] = new JRadioButton(Difficulty[i]);
            DifficultyButton[i].setFont(new Font("SansSerif",Font.BOLD,30));
            DifficultyGroup.add(DifficultyButton[i]);
            DifficultyButton[i].setOpaque(false);
            DifficultyButton[i].setForeground(c1);
            DifficultyButton[i].setBorderPainted(false);
            DifficultyButton[i].setFocusable(false);
            if(i==0) DifficultyButton[i].setSelected(true);
            Difficultypanel.add(DifficultyButton[i]);
        }
        for(int i=0; i<Difficulty.length; i++) {
            int finalI = i;
            DifficultyButton[i].addItemListener((ItemEvent e) -> {Level = finalI;});
        }
        JLabel TextLast = new JLabel("Last Wave : ");
        TextLast.setFont(new Font("SansSerif",Font.BOLD,30));
        TextLast.setForeground(c1);

        Difficultypanel.add(TextLast);
        Difficultypanel.add(Wave);
        
        //Button to go back to main menu
        BackButton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered){
                    UPS.setFrame(MainFrame);
                    gameFrame.dispose();
                    MainFrame.setVisible(true);
                }
            }
        };
        BackButton.set3Icon(BTPath+"BackButton.png");
        BackButton.setBounds(300, 493, 304, 98);
        BackButton.setBorderPainted(false);
        BackButton.setContentAreaFilled(false);
        BackButton.addMouseListener(BackButton);

        PlayButton = new MyButton(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(Entered&&!nameTextField.getText().equals("")){
                    if(nameTextField.getText().length()>10) UserName = nameTextField.getText().substring(0, 10);
                    else UserName = nameTextField.getText();
                    setSelected(Entered = false);
                    gameFrame.ContentPane.remove(PregamePanel);
                    Loading();
                    gameFrame.removeWindowListener( mywindow );
                    gameFrame.addWindowListener(new WindowAdapter(){
                        @Override
                        public void windowClosing(WindowEvent e) {
                            setGameStart(false);
                            clearCache();
                            UPS.setFrame(MainFrame);
                            GameLoop.setGamePause(false);
                            MainFrame.setVisible(true);
                            MainFrame.refresh();
                        }
                    });
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) { if(!nameTextField.getText().equals("")){
                this.setSelected(Entered = true); this.setEnabled(true);}
                else{this.setEnabled(false);}
            }
            @Override
            public void mouseExited(MouseEvent e)  { if(!nameTextField.getText().equals(""))this.setSelected(Entered = false); }
        };
        PlayButton.set3Icon(BTPath+"PlayButton.png");
        PlayButton.setBounds(678, BackButton.getY(), 304, 98);
        PlayButton.setBorderPainted(false);
        PlayButton.setContentAreaFilled(false);
        PlayButton.setEnabled(false);
        PlayButton.setDisabledIcon(new ImageIcon(BTPath+"PlayButtonDisible.png"));
        PlayButton.addMouseListener(PlayButton);
        
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {changed();}
            @Override
            public void removeUpdate(DocumentEvent e)  {changed();}
            @Override
            public void insertUpdate(DocumentEvent e)  {changed();}
            public void changed() {
               if (nameTextField.getText().equals("")){PlayButton.setEnabled(false);}
               else {PlayButton.setEnabled(true);}
            }
          });
        
        Background = new JLabel[4];
        for(int i = 0 ; i < Background.length ; i++){
            Background[i] = new JLabel();
            Background[i].setIcon(new ImageIcon(BGPath+"GameBG"+Integer.toString(i+1)+".png"));
            Background[i].setBounds(0,0,1280,720);
        }
        
        BgPanel.setBounds(0, 0, width, height);
        BgPanel.setLayout(null);
        PregamePanel.setBounds(0, 0, width, height);
        PregamePanel.setLayout(null);
        PregamePanel.setOpaque(false);
        
        PregamePanel.add(Text);
        PregamePanel.add(nameTextField);
        PregamePanel.add(Difficultypanel);
        PregamePanel.add(BackButton);
        PregamePanel.add(PlayButton);
        for(JLabel i : Background) BgPanel.add(i);
        
        ContentPane.add(PregamePanel,JLayeredPane.PALETTE_LAYER);
        ContentPane.add(BgPanel,JLayeredPane.DEFAULT_LAYER);
    }
    
    private void Loading(){
        try { Thread.sleep(3000); } catch (InterruptedException ex) {}
        gamePanel = new GamePanel(gameFrame);
        player = new Player(gameFrame,gamePanel);
        Spawn = new EnemySpawnThread(gamePanel,player,Level,wavelength);
        uiPanel = new UIPanel(player,width,height,ContentPane,gameFrame,Setting,MainFrame,UPS,Spawn);
        Spawn.clearBeforeGameClose(false);
        player.setEnemyThread(Spawn);

        gamePanel.add(player,JLayeredPane.POPUP_LAYER);
        ContentPane.add(gamePanel, JLayeredPane.MODAL_LAYER);
        Setting.setUIPanel(uiPanel.getPausePanel());
        Setting.SettingConfig(true);
        Setting.setBGMusic(true);

        Spawn.start();

        gameStart = true;

        if(ContentPane.getLayer(gamePanel)<=ContentPane.getLayer(BgPanel)){
            ContentPane.setLayer(gamePanel,JLayeredPane.MODAL_LAYER);
            ContentPane.setLayer(BgPanel,  JLayeredPane.DEFAULT_LAYER);
            System.out.println("reset the layer");
        }
        player.setFocusable(true);
        player.requestFocus();
        player.addKeyListener(player);
    }
    @Override
    public void Update(int num){
        if(gameStart) {
            player.updateAni(num);
            uiPanel.UpdateStatus(num);
        }
    }

    public void setGameStart(boolean b) {  gameStart = b;  }
    public void clearCache()            {  Spawn.clearBeforeGameClose(true);  }
    public String getPlayerName()       {  return UserName;  }
}