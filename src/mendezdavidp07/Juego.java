package mendezdavidp07;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Juego extends JPanel {
    
    static Bola bola1, bola2, bola3, bola4;
    static final int ANCHO_VENTANA = 1920;
    static final int ALTO_VENTANA = 600;
    static final int META_X = ANCHO_VENTANA - 100; // Posición x de la línea de meta
    static final int ANCHO_META = 10; // Ancho de la línea de meta
    boolean carreraFinalizada = false;
    JLabel resultadosLabel;
    Image imagen = new ImageIcon(getClass().getResource("/Imagenes/carriles.jpg")).getImage();
    ImageIcon lineMetaImagen = new ImageIcon(getClass().getResource("/Imagenes/lineaMeta.jpg"));
    Image iconoCoche = new ImageIcon(getClass().getResource("/Imagenes/iconoCoche.png")).getImage();

    public Juego() throws InterruptedException {
        Random random = new Random();

//        bola1 = new Bola(10, 100, random.nextInt(4) + 1, this, "Bola1");
//        bola2 = new Bola(10, 200, random.nextInt(4) + 1, this, "Bola2");
//        bola3 = new Bola(10, 300, random.nextInt(4) + 1, this, "Bola3");
//        bola4 = new Bola(10, 400, random.nextInt(4) + 1, this, "Bola4");
        bola1 = new Bola(10, 50, 2, this, "Coche 1");
        bola2 = new Bola(10, 160, 2, this, "Coche 2");
        bola3 = new Bola(10, 310, 2, this, "Coche 3");
        bola4 = new Bola(10, 410, 2, this, "Coche 4");

        resultadosLabel = new JLabel();
        add(resultadosLabel);
    }

//    public static void main(String[] args) throws InterruptedException {
//        JFrame frame = new JFrame("JUEGO");
//        Juego game = new Juego();
//        frame.add(game);
//        frame.setSize(ANCHO_VENTANA, ALTO_VENTANA);
//        frame.setResizable(false); // Ventana no redimensionable
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        
//        game.iniciarJuego();
//    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(),
                        this);
 
        setOpaque(false);
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //g2d.fillRect(META_X - ANCHO_META, 0, ANCHO_META, getHeight());
        
        ImageObserver obs = null;
        lineMetaImagen.paintIcon(this, g2d, META_X - ANCHO_META, 0);
       
        
        
        //g2d.setColor(Color.white);
        
//        g2d.fillOval(bola1.getX(), bola1.getY(), Bola.dimension, Bola.dimension);
//        g2d.fillOval(bola2.getX(), bola2.getY(), Bola.dimension, Bola.dimension);
//        g2d.fillOval(bola3.getX(), bola3.getY(), Bola.dimension, Bola.dimension);
//        g2d.fillOval(bola4.getX(), bola4.getY(), Bola.dimension, Bola.dimension);
        
        g2d.drawImage(iconoCoche, bola1.getX(), bola1.getY(), this);
        g2d.drawImage(iconoCoche, bola2.getX(), bola2.getY(), this);
        g2d.drawImage(iconoCoche, bola3.getX(), bola3.getY(), this);
        g2d.drawImage(iconoCoche, bola4.getX(), bola4.getY(), this);
        
        
    }
    
     public void iniciarJuego() {
        bola1.start();
        bola2.start();
        bola3.start();
        bola4.start();

        // Espera a que todos los hilos terminen antes de mostrar los resultados
        try {
            bola1.join();
            bola2.join();
            bola3.join();
            bola4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Solo se llegará a este punto cuando todos los hilos hayan terminado
        carreraFinalizada = true;
        mostrarPosicionesFinales();
    }
     
    private void mostrarPosicionesFinales() {
    // Ordena las bolas por su tiempo de carrera (menor tiempo primero)
        ArrayList<Bola> bolas = new ArrayList<>();
        bolas.add(bola1);
        bolas.add(bola2);
        bolas.add(bola3);
        bolas.add(bola4);

        Collections.sort(bolas, Comparator.comparingLong(Bola::getTiempoCarrera));

        // Muestra las posiciones en el JLabel
        StringBuilder resultados = new StringBuilder("<html><br><br><br>");
        for (int i = 0; i < bolas.size(); i++) {
            resultados.append(getLugar(i + 1)).append(": ").append(bolas.get(i).getNombre()).append("<br>");
        }
        resultados.append("</html>");

        resultadosLabel.setText(resultados.toString());
        resultadosLabel.setForeground(Color.white);

       
        carreraFinalizada = true;
    }

    
      private String getLugar(int posicion) {
        // Devuelve el lugar en función de la posición final
        switch (posicion) {
            case 1:
                return "Primer lugar";
            case 2:
                return "Segundo lugar";
            case 3:
                return "Tercer lugar";
            case 4:
                return "Cuarto lugar";
            default:
                return "Posición desconocida";
        }
    }
      
    private void detenerAnimacion() {
        // Detener la animación (puedes implementar esto según tu lógica)
        bola1.interrupt();
        bola2.interrupt();
        bola3.interrupt();
        bola4.interrupt();
    }

    
}
