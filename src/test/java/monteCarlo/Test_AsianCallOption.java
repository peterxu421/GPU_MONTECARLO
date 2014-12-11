package monteCarlo;

import junit.framework.TestCase;

import org.joda.time.DateTime;

public class Test_AsianCallOption extends TestCase {
	public void test_getPayOut(){
		double K = 1;
		AsianCallOption ec = new AsianCallOption(K);
		double rate = 0.01; 
		int N = 10;
		double sigma = 0.01;
		double S0 = 1.0;
		double tol = 0.01;
		int M = 10000;
		int batch = 1024*1024;
		GPUNormalRandomNumberGenerator gpu = new GPUNormalRandomNumberGenerator(N, batch);
		I_RandomVectorGenerator rvg = new AntiTheticVectorGenerator(gpu);
		DateTime d1 = new DateTime(2014,10,15,16,59);
		DateTime d2 = new DateTime(2014,10,15,17,59);
		GBMRandomPathGenerator g = new GBMRandomPathGenerator(rate, N, sigma, S0, d1, d2, rvg);		
		double[] payout = new double[M];
		StatsCollector st = new StatsCollector();
		for(int i = 0; i < M; i++){
			payout[i] = ec.getPayout(g);
			assertTrue(payout[i]>=0);
			st.update(payout[i]);
		}
		System.out.println(st.getMean());
		double truePrice = 0.0425*Math.exp(rate*N);
		System.out.println(truePrice);
		assertEquals(truePrice, st.getMean(), tol);
	}
}
