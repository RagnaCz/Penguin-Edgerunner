package Project3_220;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public interface MyUtil {
    String BGPath = "src/main/java/Project3_220/Background/";
    String SSPath = "src/main/java/Project3_220/Spritesheet/";
    String BTPath = "src/main/java/Project3_220/Button/";
    String ImgPath = "src/main/java/Project3_220/Image/";
    String MusPath = "src/main/java/Project3_220/Sound/";
    
    static ImageIcon[] importImg(String prepath, String path[]){//importing many images
        ImageIcon[] img = new ImageIcon[path.length];
        for(int i=0; i<path.length; i++) img[i] = new ImageIcon(prepath+path[i]);
        return img;
    }

    static Image crop(Image img, int x, int y, int width,int height){//Crop an image
        JFrame frame = new JFrame();
        return frame.createImage(new FilteredImageSource(img.getSource(), new CropImageFilter(x,y,width,height)));
    }

    static Image flipH(BufferedImage image){//Filp a BufferedImage
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }

    static BufferedImage resizeBuffer(BufferedImage img, int newW, int newH) {//Resize a BufferedImage
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
