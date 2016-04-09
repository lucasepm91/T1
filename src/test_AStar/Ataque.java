package test_Astar;

import java.util.ArrayList;
import java.util.Collections;

public class Ataque {

	private static Ataque ataque = null;
	private ArrayList<Aviao> av = new ArrayList<Aviao>();	// lista de aviões
	private ArrayList<Combinacao> cb = new ArrayList<Combinacao>(); // lista de formações
	private ArrayList<Quadrado> bases = new ArrayList<Quadrado>(); // lista de bases
	private ArrayList<Quadrado> basesAtq = new ArrayList<Quadrado>(); // lista de bases atacadas
	private ArrayList<Combinacao> avAtq = new ArrayList<Combinacao>(); // lista de formações usadas
	
	
	private Ataque(){		
		
	}
	
	public static Ataque getInstance(){
		
		if(ataque == null)
			ataque = new Ataque();
		
		return ataque;
	}	
	
	// Carrega poder das bases na ordem correta e adiciona na lista correspondente
	public void carregaBase(int x,int y, int poder,Quadrado[][] grade){
		
		grade[x][y].setPoder(poder);
		bases.add(grade[x][y]);
	}
	
	// Carrega o poder dos aviões e adiciona na lista correspondente
	public void carregaAvioes(){
						
		av.add(new Aviao("F-22 Raptor",1.5));
		av.add(new Aviao("F-35 Lightning II",1.4));
		av.add(new Aviao("T-50 PAK FA",1.3));
		av.add(new Aviao("Su-46",1.2));
		av.add(new Aviao("MiG-35",1.1));
	}
	
	// Combinação para passar por todas as bases
	public void piorCaso(){
		
		Combinacao temp;
		ArrayList<Aviao> equipe;
		
		// lista de aviões em ordem crescente
		Collections.sort(av);		
		
		equipe = new ArrayList<Aviao>();
		
		equipe.add(av.get(4));
		equipe.add(av.get(3));
		equipe.add(av.get(0));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(3));
		equipe.add(av.get(4));
		equipe.add(av.get(0));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(4));
		equipe.add(av.get(2));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(2));
		equipe.add(av.get(4));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(3));
		equipe.add(av.get(2));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(2));
		equipe.add(av.get(3));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(4));
		equipe.add(av.get(1));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(1));
		equipe.add(av.get(3));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(1));
		equipe.add(av.get(2));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(1));
		equipe.add(av.get(0));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
		equipe = new ArrayList<Aviao>();
		equipe.add(av.get(0));
		equipe.add(av.get(1));
		temp = new Combinacao(equipe);
		cb.add(temp);
		
	}
	
	// Calcula o custo para cada base no pior caso
	public void custoBases(){		
		
		int custo = 0;
		double avPod = 0;
		int basePod = 0;		
		
		Collections.sort(cb);
		bases.sort(new ComparaBase());		
		
		for(int i = 0; i < bases.size();i++){
			// O poder da base
			basePod = bases.get(i).getPoder();			
			// O poder da formação			
			avPod = cb.get(i).getPoderTotal();				
			
			custo = (int)Math.ceil(basePod / avPod);
			bases.get(i).setCustoTerreno(custo);			
		}
	}
	
	// Verifica se todas as bases foram atacadas
	public void verificaAtaques(ArrayList<Quadrado> listaCaminho){
		
		for(int i = 0; i < listaCaminho.size();i++){
			if(listaCaminho.get(i).getTerreno() == "B".toCharArray()[0]){
				// Coloca a base encontrada na lista de atacadas
				this.basesAtq.add(listaCaminho.get(i));	
				int indice = bases.indexOf(listaCaminho.get(i));
				// Coloca a formação correspondente na lista de usadas
				this.avAtq.add(cb.get(indice));
			}
		}
		
		basesAtq.sort(new ComparaBase());
		Collections.sort(avAtq);		
		
		if(basesAtq.size() < bases.size()){
			// Distribui os aviões das formações não utilizadas
			// nas formações que participaram de ataque
			redistribui();
		}
	}
	
	// Redistribui os aviões e calcula o novo custo
	public void redistribui(){
		
		ArrayList<Aviao> sobra = new ArrayList<Aviao>();		
		
		// Coloca os aviões não usados na lista sobra
		for(int i = 0; i < cb.size();i++){
			if(!avAtq.contains(cb.get(i))){
				for(int j = 0; j < cb.get(i).getComb().size();j++){
					sobra.add(cb.get(i).getComb().get(j));				
				}								
			}
		}
		Collections.sort(sobra);		
		
		// Distribui os aviões que sobraram
		for(int i = 0; i < sobra.size(); i++){
			for(int j = 0; j < avAtq.size(); j++){
				// Se a formação não comtém o avião
				if(!avAtq.get(j).getComb().contains(sobra.get(i))){						
					avAtq.get(j).adiciona(sobra.get(i));
					break;
				}
			}
		}
		
		// Calcula o novo custo para as bases atacadas		
		int custo = 0;
		double avPod = 0;
		int basePod = 0;		
		
		Collections.sort(avAtq);		
		
		for(int i = 0; i < basesAtq.size();i++){
			// O poder da base
			basePod = basesAtq.get(i).getPoder();			
			// O poder da formação			
			avPod = avAtq.get(i).getPoderTotal();			
					
			custo = (int)Math.ceil(basePod / avPod);
			basesAtq.get(i).setCustoTerreno(custo);			
		}
	}
	
	public ArrayList<Aviao> getAv() {
		return av;
	}
	
	public ArrayList<Quadrado> getBases() {
		return bases;
	}
	
	public ArrayList<Combinacao> getAvAtq() {
		return avAtq;
	}
	
	public ArrayList<Quadrado> getBasesAtq() {
		return basesAtq;
	}
	
}
