package Project3_220;

import javax.swing.*;
import java.util.ArrayList;

public class AudioCollection implements MyUtil {
    private JSlider slider;
    private static boolean mute = false;
    static float previousVolume = 0;
    static float currentVolume = -17;

    private static String [] Filename = {"SFX_SwordSwing","SFX_SwordSlash","SFX_Player_hit","SFX_HitEnemy","SFX_Roll"};

    public AudioCollection(){}

    public static void playSoundEffect(int index){
        Audio temp = new Audio(MusPath + Filename[index] + ".wav");
        temp.fc.setValue(currentVolume);
        temp.playOnce();
    }

    public static void volumeMute(JSlider slider){
        if(!mute){
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            mute=true;
            slider.setValue(slider.getMinimum());
            slider.setEnabled(false);
        }else if(mute){
            currentVolume = previousVolume;
            slider.setValue((int) currentVolume);
            mute = false;slider.setEnabled(true);
        }
    }
}
