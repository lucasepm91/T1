package test_Astar;

import java.util.ArrayList;

public class Combinacao implements Comparable<Combinacao> {

	// lista de aviões que lutam em uma base
	private ArrayList<Aviao> comb = null;
	private double poderTotal = 0;
	private int numAv = 0;
	
	public Combinacao(){		
		
	}	
	
	public Combinacao(ArrayList<Aviao> comb){		
		this.comb = comb;
		for(int i = 0; i < comb.size();i++){
			poderTotal += comb.get(i).getPoder();
		}
		this.numAv = comb.size();
	}	
	
	public ArrayList<Aviao> getComb() {
		return comb;
	}
		
	public void setComb(ArrayList<Aviao> comb) {
		this.comb = comb;
	}
	
	public void adiciona(Aviao a){
		if(comb != null){
			comb.add(a);
			poderTotal += a.getPoder();
			numAv++;
		}			
	}	
	
	public double getPoderTotal() {
		return poderTotal;
	}
	
	public int getNumAv() {
		return numAv;
	}

	@Override
	public int compareTo(Combinacao o) {
		
		return Double.compare(this.poderTotal,o.getPoderTotal());
	}	
		
}
