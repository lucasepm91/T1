package test_Astar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TesteAStar extends JPanel {

	public static final Color MONTANHOSO = new Color(96,96,96);
	public static final Color ROCHOSO = new Color(192,192,192);
	public static final Color PLANO = new Color(255,255,255);
	public static final Color BATERIA = new Color(255,51,51);
	public static final Color BASE = new Color(249,243,73);
	public static final Color INICIO = new Color(255,128,0);
	public static final Color DESTINO = new Color(51,102,0);
	public static final int pixels = 15;
	public static final int NUM_ROWS = 42;
	public static final  int NUM_COLS = 41;
	private static Quadrado[][] grid;
	private static ArrayList<Quadrado> caminho = new ArrayList<Quadrado>(); 
	private static int acumulado = 0;

	public TesteAStar(){

		setSize(630,650);
	}

	public static void main(String[] args) {

		Quadrado[][] grade = new Quadrado[42][41];        
		Scanner s = null; 
		Scanner sBase = null;
		char[] linha = new char[41];  
		Quadrado origem = null;
		Quadrado destino = null;
		int[] locBases = new int[3];

		Ataque.getInstance().carregaAvioes();

		try {
			s = new Scanner(new FileReader("src/test_Astar/mapa.txt"));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}

		// Carrega o mapa
		for (int i = 0; i < grade.length; i++) {
			for (int j = 0; j < grade[i].length; j++) {
				if(j==0){
					linha = s.nextLine().toCharArray();
				}    
				if(linha[j] == ".".toCharArray()[0])
					grade[i][j] = new Quadrado(i,j,"P".toCharArray()[0]);
				else
					grade[i][j] = new Quadrado(i,j,linha[j]);

				if(linha[j] == "M".toCharArray()[0]){
					grade[i][j].setCustoTerreno(200);
					grade[i][j].setCor(MONTANHOSO);
				}
				else
					if(linha[j] == "R".toCharArray()[0]){
						grade[i][j].setCustoTerreno(5);
						grade[i][j].setCor(ROCHOSO);
					}
					else
						if(linha[j] == "C".toCharArray()[0]){
							grade[i][j].setCustoTerreno(50);
							grade[i][j].setCor(BATERIA);
						}
						else
							if(linha[j] == "B".toCharArray()[0]){                      			
								grade[i][j].setCor(BASE);
							}
							else
								if(linha[j] == "I".toCharArray()[0]){
									grade[i][j].setCustoTerreno(1);
									origem = grade[i][j];
									grade[i][j].setCor(INICIO);
								}
								else
									if(linha[j] == "F".toCharArray()[0]){
										grade[i][j].setCustoTerreno(1);
										destino = grade[i][j];
										grade[i][j].setCor(DESTINO);
									}
									else
										if(linha[j] == ".".toCharArray()[0]){
											grade[i][j].setCustoTerreno(1);
											grade[i][j].setCor(PLANO);
										}
			}
		}        
		s.close();

		try {
			sBase = new Scanner(new FileReader("src/test_Astar/bases.txt"));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}

		// Carrega o poder das bases

		while(sBase.hasNextLine()){
			String temp = sBase.nextLine();
			String[] parse = temp.split(" ");
			for(int i = 0; i < parse.length;i++){
				locBases[i] = Integer.parseInt(parse[i]);
			}
			Ataque.getInstance().carregaBase(locBases[0],locBases[1],locBases[2], grade);
		}

		sBase.close();

		Ataque.getInstance().piorCaso();
		Ataque.getInstance().custoBases();                


		AStar aStar = new AStar(grade, origem, destino);
		boolean pesquisaOk = aStar.iniciarPesquisa();         

		if (pesquisaOk) {
			//Verifica se precisa redistribuir os aviões
			Ataque.getInstance().verificaAtaques(aStar.getListaCaminho());

		} else {
			System.out.println("Caminho nao foi encontrado.");
			System.exit(0);
		}        

		for(int i = 0; i < Ataque.getInstance().getAvAtq().size();i++){        	
			System.out.println("Avioes:");
			for(int j = 0; j < Ataque.getInstance().getAvAtq().get(i).getComb().size();j++){
				System.out.println(Ataque.getInstance().getAvAtq().get(i).getComb().get(j).getTipo()); 

			}
			System.out.print("Base: poder -> ");
			System.out.print(Ataque.getInstance().getBasesAtq().get(i).getPoder());
			System.out.print(" custo -> "); 
			System.out.println(Ataque.getInstance().getBasesAtq().get(i).getCustoTerreno()); 
			System.out.println("");
		}


		// Interface gráfica

		grid = grade; 
		JPanel custoAcumulado = new JPanel(){
			JLabel j = null;
			String s = new String("Custo: ");    	 
			@Override
			public void paintComponent(Graphics g) {    		 

				super.paintComponent(g);

				if(this.j != null)
					this.remove(j);

				j = new JLabel(s.concat(Integer.toString(acumulado)));
				j.setFont(new Font("Arial", Font.BOLD,16));
				j.setBounds(635,0,115,650);
				this.add(j);
			}
		};    
		custoAcumulado.setSize(115,650);
		custoAcumulado.setBounds(635,0,115,650);     
		JFrame frame = new JFrame("Trabalho 1");
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int larg=sl/2-670/2;
		int alt=sa/2-630/2;

		frame.setSize(630,670);
		frame.setBounds(larg,alt,750,670);
		TesteAStar map = new TesteAStar();     
		frame.add(map);
		frame.add(custoAcumulado);     
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
		frame.setVisible(true);


		ActionListener taskPerformer = new ActionListener() {
			private int cont = aStar.getListaCaminho().size()-1;

			public void actionPerformed(ActionEvent evt) {
				if(cont > -1){
					caminho.add(aStar.getListaCaminho().get(cont));
					acumulado += aStar.getListaCaminho().get(cont).getCustoTerreno();
					this.cont--;
					frame.repaint();
				}
				else{
					((Timer)evt.getSource()).stop();				
				}
			}
		};

		Timer timer = new Timer(100,taskPerformer);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		int rectWidth = pixels;
		int rectHeight = pixels;

		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				// coloca cor nos quadrados
				int x = i * rectWidth;
				int y = j * rectHeight;
				Color terrainColor = grid[i][j].getCor();
				g.setColor(terrainColor);
				g.drawRect(y, x, rectWidth, rectHeight);
				g.fillRect(y, x, rectWidth, rectHeight);

			}
		}

		// Faz as bordas nos quadrados
		int width = NUM_COLS * pixels;
		int height = NUM_ROWS * pixels;

		g.setColor(Color.BLACK);
		for (int i = 0; i < NUM_ROWS; i++)
			g.drawLine(0, i * pixels, width, i *pixels);

		for (int i = 0; i < NUM_COLS; i++)
			g.drawLine(i * pixels, 0, i * pixels, height);

		// Desenha o caminho

		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLUE);
		int metade = pixels/2;
		int totalX,totalX2;
		int totalY,totalY2;
		int xCam;
		int yCam;
		if(caminho.size() > 0){
			for(int i=0,j=1;j < caminho.size();i++,j++){
				xCam = caminho.get(i).getX();
				yCam = caminho.get(i).getY();
				totalX = xCam * pixels + metade;
				totalY = yCam * pixels + metade;

				xCam = caminho.get(j).getX();
				yCam = caminho.get(j).getY();
				totalX2 = xCam * pixels + metade;
				totalY2 = yCam * pixels + metade;

				g2.drawLine(totalY, totalX, totalY2,totalX2);
			}
		}
	}
}
