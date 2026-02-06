/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import javax.swing.*;


/**
 *
 * @author michael
 */
public class zeichenPanel extends JPanel{
    
    private Image image;
    private Graphics2D g2d;
    
    public static int b = 20;
    public static int h = 20;
    public static Color backgroundColor = Color.GREEN;
    
    //public static int[][] feld = new int[b][h];
    
    public Color[][] fieldColor = new Color[20][20];
    public Color[][] numberColor = new Color[20][20];
    public int[][] numbers = new int[20][20];
    public boolean[][] bombs = new boolean[20][20];
    public boolean[][] explored = new boolean[20][20];

    
    public zeichenPanel() {                                                    //Konstruktor setzt bevorzugte Gre, legt das Layout fest
        super();
        this.setPreferredSize(new Dimension(440, 440));
        this.setLayout(null);
        
        initFeld();
    }
    
    private void initFeld() {
        fieldColor = new Color[b][h];
        numbers = new int[b][h];
        bombs = new boolean[b][h];
        explored = new boolean[b][h];
        numberColor = new Color[b][h];
        for(int i = 0; i<b; i++) {
            for(int j=0; j<h; j++) {
                fieldColor[i][j] = backgroundColor;
                numberColor[i][j] = backgroundColor;
                numbers[i][j] = 0;
                bombs[i][j] = false;
                explored[i][j] = false;
            }
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        if (image != null) {
            size.width = image.getWidth(this);
            size.height = image.getHeight(this);
        }
        return size;
    }
  
    
    public void setPaintColor(final Color color) {
        g2d.setColor(color);
    }
  
    
    public void clearPaint() {
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        repaint();
        g2d.setColor(Color.BLACK);
    }
    
    public void setDim(Integer b, Integer h) {
        this.b = b;
        this.h = h;
        
        initFeld();
        
        repaint();
    }
    
    public void setBreite(int w) {
        b = w;
        
        setSize(22*b+1, 22*h+1);
        
        initFeld();
        repaint();
    }
    
    public void setHoehe(int h) {
        this.h = h;
        
        setSize(22*b+1, 22*h+1);
        
        initFeld();
        repaint();
    }
    
    
    public int getBreite() {
        return b;
    }
    public int getHoehe() {
        return h;
    }
    
    public void setFieldColor(int x, int y, Color c){
        if (-1 < x && x < b && -1 < y && y < h) {
            fieldColor[x][y] = c;
            repaint();
        }
    }
    public Color getFieldColor(int x, int y){
        if (-1 < x && x < b && -1 < y && y < h) {
            return fieldColor[x][y];
        }
        else
            return Color.white;
    }

    public void setNumber(int x, int y, int number){
        if (-1 < x && x < b && -1 < y && y < h) {
            numbers[x][y] = number;
            repaint();
        }
    }
    public int getNumber(int x, int y){
        if (-1 < x && x < b && -1 < y && y < h) {
            return numbers[x][y];
        }
        else
            return 0;
    }

    public void setBombs(int x, int y, boolean bol){
        if (-1 < x && x < b && -1 < y && y < h) {
            bombs[x][y] = bol;
            repaint();
        }
    }
    public boolean getBomb(int x, int y){
        if (-1 < x && x < b && -1 < y && y < h) {
            return bombs[x][y];
        }
        else
            return false;
    }

    public void setExplored(int x, int y, boolean bol){
        if (-1 < x && x < b && -1 < y && y < h) {
            explored[x][y] = bol;
            repaint();
        }
    }
    public boolean getExplored(int x, int y){
        if (-1 < x && x < b && -1 < y && y < h) {
            return explored[x][y];
        }
        else
            return false;
    }

    public void setNumberColor(int x, int y, Color c){
        if (-1 < x && x < b && -1 < y && y < h) {
            numberColor[x][y] = c;
            repaint();
        }
    }
    public Color getNumberColor(int x, int y){
        if (-1 < x && x < b && -1 < y && y < h) {
            return numberColor[x][y];
        }
        else
            return Color.white;
    }
    
    
    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
    
        if (image == null || image.getWidth(this) < getSize().width                 //Image initialisieren (ging vorher nicht) oder an Gre des Simulatorpanels anpassen
            || image.getHeight(this) < getSize().height) {
            resetImage();
        } // end of if
        Graphics2D g2 = (Graphics2D) g;                                             //Erst jetzt kann man auch den Grafikkontext erhalten
        Rectangle r = g.getClipBounds();
        this.updateImage();
        g2.drawImage(image, r.x, r.y, r.width + r.x, r.height + r.y,
            r.x, r.y, r.width + r.x, r.height + r.y, this);
    }
    
    
    public void resetImage() {                                                    
        Image saveImage = image;                                                    //Verhindert das Flackern (so gut es geht)
        Graphics2D saveG2d = g2d;
        image = createImage(getWidth(), getHeight());
        g2d = (Graphics2D) image.getGraphics();
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        if (saveG2d != null) {
            g2d.setColor(saveG2d.getColor());
            g2d.drawImage(saveImage, 0, 0, this);
            saveG2d.dispose();
        } // end of if
    }
  
    
    public Graphics2D getG2d() {
        return g2d;
    }
  
    
    public void updateImage() {                                                   
        clearPaint();
        //g2d.drawString("x="+x+", y="+y, 20, 20);
        
        g2d.setColor(Color.WHITE);
        
        g2d.fillRect(0, 0, 450, 450);
        
        g2d.setColor(new Color(50,50,50));
        for ( int i = 0; i<=b; i++) {
            g2d.drawLine(22*i,0,22*i,22*20);
        }
        g2d.setColor(new Color(50,50,50));
        for ( int i = 0; i<=h; i++) {
            g2d.drawLine(0,22*i,22*20,22*i);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        for(int i=0; i<b; i++) {
            for(int j=0; j<h; j++) {
                g2d.setColor(fieldColor[i][j]);
                g2d.fillRect(1+22*i,1+22*j,20,20);
                
                if (numbers[i][j] > 0) {
                    g2d.setColor(numberColor[i][j]);
                    g2d.drawString("" + numbers[i][j], 1+22*i+6, 1+22*j+16);
                }
            }
        }
    }
  
}
