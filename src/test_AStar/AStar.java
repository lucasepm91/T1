package test_Astar;

import java.util.ArrayList;
import java.util.Collections;

public class AStar {

    private Quadrado[][] grade;
    private Quadrado origem;
    private Quadrado destino;
    private ArrayList<Quadrado> listaAberta;
    private ArrayList<Quadrado> listaFechada;
    private ArrayList<Quadrado> listaCaminho;    

    public AStar(Quadrado grade[][], Quadrado origem, Quadrado destino) {
        this.grade = grade;
        this.origem = origem;
        this.destino = destino;
        listaAberta = new ArrayList<Quadrado>();
        listaFechada = new ArrayList<Quadrado>();
        listaCaminho = new ArrayList<Quadrado>();        
    }

    //A pesquisa do A* comeca aqui
    public boolean iniciarPesquisa() {
        if (getGrade() == null) {        //grade vazia
            return false;
        }
        if (getOrigem() == getDestino()) {  //a origem coincide com o destino
            return true;
        }

        listaAberta.add(getOrigem());       //adiciona origem a lista de elementos abertos
        if (pesquisar()) {                  //invoca metodo de pesquisa
            return salvarCaminho();         //apos concluir pesquisa preenche listaCaminho
        }
        return false;                       //nao foi possivel encontrar um caminho
    }

    private boolean pesquisar() {
    	    	
    	Quadrado corrente;
    	    	 
    	while(true){
    		// Pega o quadrado com menor custo de F na lista aberta     		
    		
    		Collections.sort(listaAberta);
    		corrente = listaAberta.get(0);    		  
    		       
    		listaFechada.add(corrente);  //move o quadrado corrente para lista fechada         
    		listaAberta.remove(corrente);
        
    		if (corrente == destino) {   //encontrou
    			return true;
    		}

        //Varre todos os quadrados adjacentes ao quadrado corrente
        
    		ArrayList<Quadrado> resp = vizinhos(corrente);
        
    		if(resp != null){
    			for(int i = 0;i < resp.size();i++){
    				Quadrado adjacente = resp.get(i);
    				if (!listaFechada.contains(adjacente)) {  //nao estiver na lista fechada
    					int custoG = corrente.getCustoG() + adjacente.getCustoTerreno(); //calcula custo G
    					int custoH = manhattan(adjacente);		//calcula custo H
        			
    					if (!listaAberta.contains(adjacente)) {    //se quadrado nao estiver na lista aberta
    						adjacente.setPai(corrente);           //faz quadrado corrente pai deste
    						listaAberta.add(adjacente);           //adiciona na lista aberta
    						adjacente.setCustoG(custoG);
    						adjacente.setCustoH(custoH);
    					} else {                                        //se quadrado ja estiver na lista aberta
    						if (adjacente.getCustoG() > custoG) { //e seu custo atual G for maior que o novo
    							adjacente.setPai(corrente);       //entao troca quadrado pai
    							adjacente.setCustoG(custoG);      // e atualiza custos
    							adjacente.setCustoH(custoH);
    						}
    					}
    				}
    			}
    			resp.clear(); 
    			resp = null;
    		}

    		if (listaAberta.isEmpty()) {    //nao ha nenhum caminho
    			return false;
    		}
    	}        
    }

    //retorna do destino ate origem salvando o caminho
    private boolean salvarCaminho() {
        Quadrado corrente = getDestino();

        if (corrente == null) {
            return false;
        }

        while(corrente != null) {
            listaCaminho.add(corrente);
            corrente = corrente.getPai();
        }
        return true;

    }   
       
    public ArrayList<Quadrado> vizinhos(Quadrado corrente){
    	
    	ArrayList<Quadrado> vizinho = new ArrayList<Quadrado>();
    	int x = corrente.getX();
        int y = corrente.getY();
        int direita = y + 1;
        int esquerda = y - 1;
        int acima = x - 1;
        int abaixo = x + 1;
        
        if (direita < grade[x].length) {
        	vizinho.add(grade[x][direita]);
        }
        
        if(esquerda >= 0){
        	vizinho.add(grade[x][esquerda]);
        }
        
        if(acima >= 0){
        	vizinho.add(grade[acima][y]);
        }
        
        if(abaixo < grade.length){
        	vizinho.add(grade[abaixo][y]);
        }
        
        if(vizinho.isEmpty()){
        	vizinho = null;
        }
    	
    	return vizinho;
    }
    
    public int manhattan(Quadrado corrente){
    	
    	int xi = corrente.getX();
    	int yi = corrente.getY();
    	int xf = destino.getX();
    	int yf = destino.getY();
    	
    	return Math.abs(xi - xf) + Math.abs(yi - yf);    	
    }
   
    //getters e setters
    public Quadrado[][] getGrade() {
        return grade;
    }

    public void setGrade(Quadrado[][] grade) {
        this.grade = grade;
    }

    public Quadrado getOrigem() {
        return origem;
    }

    public void setOrigem(Quadrado origem) {
        this.origem = origem;
    }

    public Quadrado getDestino() {
        return destino;
    }

    public void setDestino(Quadrado destino) {
        this.destino = destino;
    }

    public ArrayList<Quadrado> getListaCaminho() {
        return listaCaminho;
    }   

    public void LimpaAstar(){
		
	listaAberta.clear();
	listaFechada.clear();
	listaCaminho.clear();	
	grade = null;
	origem = null;
	destino = null;				
    }
}

