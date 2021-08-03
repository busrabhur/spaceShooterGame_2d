
import java.awt.HeadlessException;
import javax.swing.JFrame;

public class OyunEkrani extends JFrame{ //oyunumzda jframe üzerinde jpanel olacak ve işlemler genel olarak jpanel E odaklanacak

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }
    
    public static void main(String[] args) { 
        //static main metodu içinde,içinde bulunduğumuz classın objesini oluturabiliriz.
        OyunEkrani ekran=new OyunEkrani("Uzay Oyunu");
        ekran.setResizable(false); //kullanıcı oyun ekran boyutlarını kenarlarından genişletemeyecek
        ekran.setFocusable(false); 
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Oyun oyun=new Oyun();
        oyun.requestFocus();
        oyun.addKeyListener(oyun);
        oyun.setFocusable(true);
        oyun.setFocusTraversalKeysEnabled(false);
        
        
        ekran.add(oyun);
        ekran.setVisible(true); //
      
        
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
