package monteCarlo;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.*;

import junit.framework.TestCase;

public class Test_GBMRandomPathGenerator extends TestCase {
	// test GBM getPrice method
	public void test_getPrices(){
		double rate = 0.01; 
		int N = 10;
		double sigma = 0.01;
		double S0 = 1.0;
		double tol = 0.01;
		int M = 10000;
		double[] price = new double[M];
		int batch = 32*32;
		GPUNormalRandomNumberGenerator gpu = new GPUNormalRandomNumberGenerator(N, batch);
		I_RandomVectorGenerator rvg = new AntiTheticVectorGenerator(gpu);
		DateTime d1 = new DateTime(2014,10,15,16,59);
		DateTime d2 = new DateTime(2014,10,15,17,59);
		StatsCollector st = new StatsCollector();
		GBMRandomPathGenerator g = new GBMRandomPathGenerator(rate, N, sigma, S0, d1, d2, rvg);
		for(int i = 0; i < M; i++){
			List<Pair<DateTime, Double>> prices = g.getPrices();
			int len = prices.size();
			price[i] = prices.get(len - 1).getValue();
			st.update(price[i]);
		}
		// the true value of the stock
		double truePrice = S0 * Math.exp(rate * (N-1));
		System.out.println(truePrice);
		System.out.println(st.getMean());
		assertEquals(truePrice,st.getMean(),tol);
		
	}
}
