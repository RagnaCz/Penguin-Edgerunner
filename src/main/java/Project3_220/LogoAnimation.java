package Project3_220;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.*;

public class LogoAnimation extends JLabel implements MyUtil {
    protected int maxindex = 1, Index = 0;
    protected int pwidth, pheight;
    protected ImageIcon[] icon;

    public LogoAnimation(int max, int pwidth, int pheight){
        maxindex = max; icon = new ImageIcon[maxindex];
        this.pwidth = pwidth;
        this.pheight = pheight;
        setPreferredSize(new Dimension(pwidth,pheight));
    }
    public void setSpriteSheet(String path, int col, int row, int width, int height, int hint){
        ImageIcon pic = new ImageIcon(path);
        Image img;
        
        for(int i=0, n=1; i<row; i++){
            for(int j=0; j<col; j++,n++){
                if(n<=maxindex){
                    img = MyUtil.crop(pic.getImage(), j*width,i*height, width,height);
                    if(width!=pwidth || height!=pheight) img = img.getScaledInstance(pwidth, pheight, hint);
                    icon[i*(row+1)+j] = new ImageIcon(img);
                }
                else{ j=col; i=row; }
            }
        }
        
        setIcon(icon[0]);
    }
    public void update(){
        Index = (Index+1)%maxindex;
        setIcon(icon[Index]);
    }
}
