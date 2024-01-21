package Project3_220;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

public class Audio {
    private Clip clip;
    float previousVolume = 0;
    float currentVolume = -17;
    FloatControl fc;
    boolean mute = false;

    public Audio(String filename){
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    
    public void playOnce() { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop() { clip.loop(Clip.LOOP_CONTINUOUSLY);}
    public void stop()     { clip.stop(); }


    public void volumeMute(JSlider slider){
        if(mute == false){
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute=true;
            slider.setValue(slider.getMinimum());
            slider.setEnabled(false);
        }else if(mute == true){
            currentVolume = previousVolume;
            slider.setValue((int) currentVolume);
            fc.setValue(currentVolume);
            mute = false;slider.setEnabled(true);
        }
    }

    public void setNewFile(String filename){
        try
        {
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}