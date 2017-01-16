package projekt;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import projekt.OpenGlListener;

public class MainWindow extends javax.swing.JFrame{

    private final GLJPanel glPanel;
    private OpenGlListener openGlListener;
    private final FPSAnimator animator;
    private boolean fullscreen = false;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {

        initComponents();
        setSize(800, 600);
        setTitle("Projekt1");
        
        // vycentruje okno na stred plochy
        setLocationRelativeTo(null);

        // vytvori sa viditelny panel na ktorom sa bude zobrazovat nas graficky vystup
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // tento panel sa umiestni na halvne okno aplikacie
       capabilities.setDepthBits(24);
        
        glPanel = new GLJPanel(capabilities);
        
        add(glPanel, BorderLayout.CENTER);
        
        animator = new FPSAnimator(glPanel, 60, true);
        openGlListener = new OpenGlListener(animator);
        
        

        glPanel.addGLEventListener(openGlListener);
        glPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                MainWindow.this.keyPressed(e);
            }
        });

        animator.start();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING:
     * Do NOT modify this code. The content of this method is always regenerated by the
     * Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
    }// </editor-fold>  
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true); 
           }
        });
    }
    

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;

               case KeyEvent.VK_A:
                toggleAnimation();
                break;
                
            case KeyEvent.VK_F:
                toggleFullScreen();
                break;
                
            case KeyEvent.VK_M:
                openGlListener.togglePolygonMode();
                break;
                
            case KeyEvent.VK_LEFT:
                openGlListener.increaseYaw();
                break;
                
            case KeyEvent.VK_RIGHT:
                openGlListener.decreaseYaw();
                break;
                
            case KeyEvent.VK_UP:
                openGlListener.increasePitch();
                break;
                
            case KeyEvent.VK_DOWN:
                openGlListener.decreasePitch();
                break;
                
           case KeyEvent.VK_Q:
                openGlListener.changeLight0();
                break;
               
           case KeyEvent.VK_W:
                openGlListener.changeLight1();
                break;
               
           case KeyEvent.VK_E:
                openGlListener.changeLight2();
                break;
        }
        glPanel.display(); 
        
        };

    private void toggleAnimation() {
            if (animator.isAnimating()) {
            animator.stop();
        } else {
            animator.start();
        }    }

    private void toggleFullScreen() {
        fullscreen = !fullscreen;
        
        if (animator.isAnimating()) {
            animator.stop();
        }
        
        dispose();
        setUndecorated(fullscreen);
        pack();
        
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();
        
        if (fullscreen) {
            device.setFullScreenWindow(this);
        } else {
            device.setFullScreenWindow(null);
        }
        setVisible(true);
        animator.start();    
    }
    
        
}