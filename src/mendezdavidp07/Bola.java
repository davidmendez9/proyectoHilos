/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mendezdavidp07;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author David
 */


public class Bola extends Thread {
    int x, y;
    static final int dimension = 40;
    int velocidad;
    Juego principal;
    Timer timer;
    private long tiempoCarrera;
    String nombre;
    public int posicion;
    
     public Bola(int x, int y, int velocidad, Juego principal, String nombre) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.principal = principal;
        this.tiempoCarrera = 0;
        this.nombre = nombre;
        this.posicion = 0;
        
        // Configura el Timer para llamar al ActionListener cada cierto tiempo
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
                principal.repaint();
            }
        });
        timer.start();  // Inicia el timer
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void run() {
        super.run();
        Random random = new Random();
        while (x < Juego.META_X - dimension) {
            try {
                Thread.sleep(50 / (random.nextInt(9) + 1) + 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Bola.class.getName()).log(Level.SEVERE, null, ex);
            }
            move();
            principal.repaint();
        }
    }
    
    
    void move() {
    x += velocidad;

    // Verifica si la bola ha cruzado la línea de meta
    if (x + dimension >= Juego.META_X && tiempoCarrera == 0) {
        x = Juego.META_X - dimension; // Ajusta la posición para que no se pase de la línea de meta
        timer.stop(); // Detiene el timer
        tiempoCarrera = System.currentTimeMillis(); // Registra el tiempo de carrera
    }
}
    
    public long getTiempoCarrera() {
        return tiempoCarrera;
    }
    
}
