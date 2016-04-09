
package test_Astar;

import java.awt.Color;

public class Quadrado implements Comparable<Quadrado>{

    private int custoG = 0;
    private int custoH = 9999;
    private int x = 0;
    private int y = 0;
    private Quadrado pai = null;
    private char terreno;
    private int custoTerreno = 0;
    private int poder = 0;
    private Color cor;

    public Quadrado(int x, int y, char terreno) {
        this.x = x;
        this.y = y;
        this.terreno = terreno;
    }

    public int getCustoF() {
        return custoG + custoH;
    }

    public int getCustoG() {
        return custoG;
    }

    public void setCustoG(int custoG) {
        this.custoG = custoG;
    }

    public int getCustoH() {
        return custoH;
    }

    public void setCustoH(int custoH) {
        this.custoH = custoH;
    }

    public int getX() {
        return x;
    }

    public void setPosicaoX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setPosicaoY(int y) {
        this.y = y;
    }

    public Quadrado getPai() {
        return pai;
    }

    public void setPai(Quadrado pai) {
        this.pai = pai;
    }
   
    public char getTerreno() {
		return terreno;
	}
    
    public void setTerreno(char terreno) {
		this.terreno = terreno;
	}
    
    public int getCustoTerreno() {
		return custoTerreno;
	}
    
    public void setCustoTerreno(int custoTerreno) {
		this.custoTerreno = custoTerreno;
	}
    
    public int getPoder() {
		return poder;
	}
    
    public void setPoder(int poder) {
		this.poder = poder;
	}
    
    public Color getCor() {
		return cor;
	}
    
    public void setCor(Color cor) {
		this.cor = cor;
	}

	@Override
	public int compareTo(Quadrado o) {
		
		return Integer.compare(this.getCustoF(),o.getCustoF());
	}
    
}
