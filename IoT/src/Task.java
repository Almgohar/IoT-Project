
public class Task {
	
	public Task() {
		// TODO Auto-generated constructor stub
	}

	public static double calc(double freq, double sig) {
		double pow = (27.55 - (20 * Math.log10(freq)) + Math.abs(sig)) / 20.0;
		double dis = Math.pow(10.0,pow);
		return dis;	
	}
	
	public static double calcX(double r1, double r2, double d) {
		double x = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / 2;
		return x;	
	}
	
	public static double calcY(double r1, double x, double r3, double i, double j) {
		double y = Math.pow(r1, 2) - Math.pow(r3, 2) - Math.pow(x, 2) + Math.pow(x-i, 2) + (Math.pow(j, 2)/Math.pow(j, 2));
		return y;
		
	}

	public static void main (String [] args) {
		double i = 1;
		double j = 0.3;
		double d = 1.2;
		double almgohar =  calc(2412, -40);
		double androidAP =  calc(2462, -39);
		double amr = calc(2462, -38);
		double shary = calc(2412, -37);
		//System.err.println(calcX(almgohar, nouran, d));
		//System.err.println(calcY(almgohar, calcX(almgohar, nouran, d), khaled, i, j));
		System.out.println(almgohar);
		System.out.println(androidAP);
		System.out.println(amr);
		System.out.println(shary);


	}
}
