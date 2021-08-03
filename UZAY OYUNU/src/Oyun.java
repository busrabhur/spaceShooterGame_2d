
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;//içindeki actionperformed metodu,nesnelerimize hareket kazandırıken işimize yarar
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Oyun extends JPanel implements KeyListener,ActionListener{
    Timer timer=new Timer((int)0.9, this);
    private int harcanan_sure=0;
    private int harcanan_ates=0;
    
    private BufferedImage image; 
    
    
    private ArrayList<Ates>atesler=new ArrayList<Ates>();
    
    private int atesDirY=1;
    
    private int topX=0; 
    private int topDirX=2; 
    
    private int uzayGemisiX=0;
    private int uzayGDirX=20;
    

    
    public boolean kontrolEt(){ 
        for (Ates ates : atesler) {
            if (new Rectangle(ates.getX(), ates.getY(),4,14).intersects(new Rectangle(topX,0,20,20))) {
                return true;
            }
        }
        return false;
    }
    

    public Oyun() { //constructor.
        
        try {
            
            image=ImageIO.read(new FileImageInputStream(new File("roc.png")));//resmi okumuş ve image değişkenine atamış olduk
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //jpanelin arka plan rengini de constructo içinde veriyoruz..
        setBackground(Color.BLACK);
        timer.start();    
    }
   
    
    @Override
    public void paint(Graphics g) { //Jcomponent classından override ettik
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
        harcanan_sure+=1;//paint metodu da her timer çalıştığında yeniden çalıştığı için harcanan süreyi bu şekilde hesaplayabilriz.
        g.setColor(Color.blue);
        g.fillOval(topX,0,20,20); 
        //g.drawImage(image2,topX, 0, image2.getWidth()/25, image.getHeight()/25, this);
        g.drawImage(image, uzayGemisiX, 480, image.getWidth()/7, image.getHeight()/7, this);
        
        
        //ateş işleri
        for (Ates ates : atesler) {
            if (ates.getY()<=0) {
                atesler.remove(ates);
            }
        }
        
        g.setColor(Color.orange);
        
        for (Ates ates : atesler) { 
            g.fillRect(ates.getX(), ates.getY(), 4,14);//ateşin x'i değişmicek,y'si değişecek
        }
        
        if (kontrolEt()) { 
            timer.stop();
            String mesaj="YOU WIN! GAME OVER!\nHarcanan ateş: "+harcanan_ates;
            JOptionPane.showMessageDialog(this,mesaj);
            System.exit(0);
        }
    }
    
    
    @Override
    public void repaint() { //Component classından override ettik
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    
    
    
    
   
   

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) { 
        int c=e.getKeyCode(); 
         
        
         if (c ==KeyEvent.VK_LEFT) { //sola basılmıssa
            if (uzayGemisiX<=0) {
                uzayGemisiX=0;
            }
            else{
                uzayGemisiX-=uzayGDirX;//20 br sola
            }
        }
        
        else if (c ==KeyEvent.VK_RIGHT) {//sağa basılmışsa
             if (uzayGemisiX>=730) {
                 uzayGemisiX=730;
             }
             else{
                 uzayGemisiX+=uzayGDirX; //20br sağa
             }  
        }
         
        else if (c==KeyEvent.VK_CONTROL) {
            atesler.add(new Ates(uzayGemisiX+30, 470));//ateşin başlangıç noktası,y si sürekli güncellencek(timer her çalıştığında)
            harcanan_ates++; //her yeni ateş oluştuğunda(ctrl'ye basıldığında) harcanan ateş sayısına eklenecek.
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override //ActionListener interface inin metodu bu
    public void actionPerformed(ActionEvent e) {
       
        for (Ates ates : atesler) {
           ates.setY(ates.getY()-atesDirY); //ateş yukarı doğru ilerleyecek,y değeri azalıp 0' a yaklaşacak.Çünkü sağ üst köşe (0,0)
            
        }
      
        //top işlemleri
        topX+=topDirX;
        
        if (topX>=750) {
            topDirX=-topDirX;
        }
        if (topX<=0) {
            topDirX=-topDirX;
        }
        this.repaint(); //repaint oyunlarda çok gerekli bir şey,Unutursan hareketi sağlayamazsın
        //çünkü repaint,paint metodunu da otomatik olarak çapırıyor ve şekillerin konumları her timer çalıştığında güncellenmiş oluyor
        //bu sayede de hareket sağlanıyor.
    
    }   }
    

