package Michael.Boersenkurs;

public class Main {

	public static void main(String[] args) {
		
		//Wij
		double[] gewichtungArray = new double[9];
		//Oi
		double[] werteArry =  new double[gewichtungArray.length];
		//tj
		double target = 2.0 + Math.sin(werteArry.length * 0.001);
		//n
		double lernrate = 0.0002;
		
		for (int i = 0; i < werteArry.length; i++) {
			werteArry[i] = 2.0 + Math.sin(i * 0.001);
			gewichtungArray[i] = Math.random() * 2 - 1;
		}
		
		double ausgabeNeuron;

		System.out.println("Zielwert: " + target);
		int durchlaufe = 0;
		
		do{
			//Oj
			ausgabeNeuron = 0;
			
			for (int i = 0; i < werteArry.length; i++) {
				ausgabeNeuron += werteArry[i] * gewichtungArray[i];
			}
			
			for (int i = 0; i < werteArry.length; i++) {
				double gewichtungsDelta = lernrate * werteArry[i] * (target - ausgabeNeuron);
				gewichtungArray[i] += gewichtungsDelta;
			}
			
			ausgabeNeuron = 0.0;
			
			for (int i = 0; i < werteArry.length; i++) {
				ausgabeNeuron += werteArry[i] * gewichtungArray[i];
			}
			
			durchlaufe++;
		}while(Math.abs(ausgabeNeuron - target) > 0.0005);
		
		System.out.println("Durchl�ufe: " + durchlaufe);
		System.out.println("B�rsenkurs nach 10 Tagen: " + ausgabeNeuron);
	}

}
