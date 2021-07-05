
public class JantarDosCanibais {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Object travacanibal = new Object();
		Object travacozinheiro = new Object();
		Caldeira comida = new Caldeira(5);

		
		ThreadCozinheiro Cozinheiro = new ThreadCozinheiro(comida, "Master Chefe",travacozinheiro, travacanibal);
		ThreadCanibal canibal1 = new ThreadCanibal("Canibal 1",comida, travacanibal,travacozinheiro);
		ThreadCanibal canibal2 = new ThreadCanibal("Canibal 2",comida, travacanibal,travacozinheiro);
		ThreadCanibal canibal3 = new ThreadCanibal("Canibal 3",comida, travacanibal,travacozinheiro);
		
		try {
			
			Thread.sleep(2*1000*60);
			
			canibal1.PararCanibal();
			canibal2.PararCanibal();
			canibal3.PararCanibal();
			Cozinheiro.pararThreadCozinheiro();
			synchronized (travacanibal) {
				travacanibal.notifyAll();
			}
			
			System.out.println("quantidade de comida no caldeirão: " + comida.caldeira);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
