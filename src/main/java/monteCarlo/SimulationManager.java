package monteCarlo;

import org.joda.time.DateTime;
/*
* Simulation manager: main function
* */
public class SimulationManager {
	// main function
	public static void main(String[] args){
		// Question1
		double K = 165;
		// note that actually we only have 251 days in total
		int N = 252;
		double S0 = 152.35;
		// rate and sigma are per day basis
		double rate = 0.0001; 
		double sigma = 0.01;
		// with 96% confident level, the error is only 0.01
		double error = 0.01;
		double y = 2.05375;
		// set dummy dates since it is not used in this version
		DateTime d1 = new DateTime(2014,10,15,16,59);
		DateTime d2 = new DateTime(2014,10,15,17,59);
		// set initial iterations first
		int M = 10000;
		// batch number
		int batch = 1024*1024;

		StatsCollector st = new StatsCollector();
		// create the GPU normal random generator
		GPUNormalRandomNumberGenerator gpu = new GPUNormalRandomNumberGenerator(N, batch);
		// create the antithetic random generator
		I_RandomVectorGenerator rvg = new AntiTheticVectorGenerator(gpu);
		// create the european object
		EuropeanCallOption ec = new EuropeanCallOption(K);
		GBMRandomPathGenerator g = new GBMRandomPathGenerator(rate, N, sigma, S0, d1, d2, rvg);		
		for(int i = 0; i < M; i++){
			st.update(ec.getPayout(g));
		}
		while(!st.isReady(error, y)){
			st.update(ec.getPayout(g));
		}
		// discount based on 251 days
		System.out.println("Price of European Call option is:");
		System.out.printf("%.6f, with %d number of iterations. \n", st.getMean()*Math.exp(-rate*(N-1)), st.getNum());

		// Question2
		double KAsian = 164;
		StatsCollector stAsian = new StatsCollector();
		I_RandomVectorGenerator rvgAsian = new AntiTheticVectorGenerator(gpu);
		// create the asian object
		AsianCallOption asian = new AsianCallOption(KAsian);
		GBMRandomPathGenerator gAsian = new GBMRandomPathGenerator(rate, N, sigma, S0, d1, d2, rvgAsian);		
		for(int i = 0; i < M; i++){
			stAsian.update(asian.getPayout(gAsian));
		}
		while(!stAsian.isReady(error, y)){
			stAsian.update(asian.getPayout(gAsian));
		}
		// discount based on 251 days
		System.out.println("Price of Asian Call option is:");
		System.out.printf("%.6f, with %d number of iterations. \n", stAsian.getMean()*Math.exp(-rate*(N-1)), stAsian.getNum());
	}
}
