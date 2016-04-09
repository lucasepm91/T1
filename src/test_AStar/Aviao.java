package test_Astar;

public class Aviao implements Comparable<Aviao> {

	private String tipo;
	private double poder = 0;	
	
	public Aviao(){
		
	}
	
	public Aviao(String tipo,double poder){
		this.tipo = tipo;
		this.poder = poder;		
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public double getPoder() {
		return poder;
	}
	
	public void setPoder(double poder) {
		this.poder = poder;
	}

	@Override
	public int compareTo(Aviao o) {
		
		return Double.compare(this.poder,o.getPoder());
	}
		
}
