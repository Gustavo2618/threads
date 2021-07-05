

public class ThreadCanibal implements Runnable {

	
	//atributos
	String Nome;
	int quantrefeicao;
	boolean faminto;
	Caldeira caldeirao;
	Object travaCanibal;
	Object travaCozinheiro;
	static int contador = 0;

	// construtor
	public ThreadCanibal(String nome,Caldeira prato, Object travacani,Object travacozinheiro)
	{
		this.caldeirao = prato;
		this.Nome = nome;
		this.quantrefeicao = 0;
		this.faminto = true;
		this.travaCanibal = travacani;
		this.travaCozinheiro = travacozinheiro;
		Thread t = new Thread(this);
		t.start();
	}
	//canibal se serve
	public synchronized boolean Servir()
	{
		synchronized (this) 
		{
			if (this.caldeirao.caldeira == 0 )
			{
			//	System.out.println("Caldeirão vazio!" + " Chama o Cozinheiro! "+ this.Nome);
				this.acordarCozinheiro();
				this.dormir();
				return false;
			}
			else
			{
				this.caldeirao.caldeira = this.caldeirao.caldeira-1;
				return true;
			}
		}
	}
	//canibal come
	public void Comer()
	{
		//System.out.println(this.Nome + " Comendo....");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.quantrefeicao = quantrefeicao + 1;
		System.out.println(this.Nome + " Comeu " + this.quantrefeicao);
		
	}
	//acorda cozinheiro apenas quandos os 3 canibais estiverem dormindo
	public synchronized void acordarCozinheiro()
	{
		contador++;
		if(contador == 3)
		{
			synchronized (travaCozinheiro) {
				travaCozinheiro.notifyAll();
				contador = 0;
			}
			return;
		}
	}
	//canibal dorme
	public void dormir()
	{
		synchronized (travaCanibal) 
		{
			System.out.println(this.Nome + " Dormindo........");
			try 
			{
				if(faminto)
				{
					travaCanibal.wait();
				}
				else
				{
					return;
				}
				
				//System.out.println(this.Nome + " Acorda........");
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(faminto)
		{
			if(faminto == true)
			{
				if (Servir())
				{
					Comer();
				}
				else if(faminto == false)
				{
					break;
				}
			}
			else 
			{
				break;
			}
		}
		System.out.println("O numero final de refeições que o " + this.Nome + " comeu " + this.quantrefeicao);
	}
	//encerrando threadCanibal
	public void PararCanibal()
	{
		synchronized (travaCanibal) {
			this.faminto = false;
			travaCanibal.notifyAll();
		}
			
	}
	

}
