
public class ThreadCozinheiro implements Runnable {

	String Nome;
	Caldeira panela;
	boolean CanibaisFamintos;
	int quantComidaPreparada;
	Object travaCanibal;
	Object travaCozinheiro;
	
	
	//Construtor
	public ThreadCozinheiro(Caldeira panelaVazia, String name, Object travaCozi,Object travaCani) {
		// TODO Auto-generated constructor stub
		
		this.quantComidaPreparada = 0;
		this.CanibaisFamintos = true;
		this.panela = panelaVazia;
		this.Nome = name;
		this.travaCozinheiro = travaCozi;
		this.travaCanibal = travaCani;
		Thread k = new Thread(this); 
		k.setName("Cozinheiro");
		k.start();
	}
	
	//thread
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(CanibaisFamintos)
		{
			this.dormir();
			System.out.println("Cozinheiro Acordou....");
			this.preparJantar(panela);
		}
		System.out.println("Quantidade de refeições preparadas: "+ quantComidaPreparada );
		
	}
	//enchendo a caldeira
	public synchronized void  preparJantar(Caldeira caldeiravazia)
	{
		if((caldeiravazia.caldeira ==  0) && (CanibaisFamintos == true))
		{
		
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.quantComidaPreparada++;
			caldeiravazia.caldeira = 5;
			System.out.println("\nJantar preparado!");
			System.out.println("\nAcordar canibais Famintos!\n");
			acordarCanibais();
			}
	}
	//cozinheiro dormindo
	public void dormir()
	{
		synchronized (travaCozinheiro) {
			System.out.println(this.Nome + " Dormindo........");
			try {
				travaCozinheiro.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	//acordar cozinheiro
	public void acordarCanibais()
	{
		synchronized (travaCanibal) {
			travaCanibal.notifyAll();
		}
		return;
	}
	//encerrando canibais
	

	//encerrando cozinheiro
	public synchronized void pararThreadCozinheiro()
	{
		synchronized (travaCozinheiro) {
		travaCozinheiro.notify();	
		}
		this.CanibaisFamintos = false;
		this.notify();
		
	}
}
