package monteCarlo;

import junit.framework.TestCase;

public class Test_UniformRandomNumberGenerator extends TestCase {
	// test the uniform getVector method
	public void test_getVector(){
		int n = 10000000;
		UniformRandomNumberGenerator rv = new UniformRandomNumberGenerator(n);
		double[] uniform = rv.getVector();
		// if the length is 10000, then mean should goes to 0 
		// and the std should goes to 1 with at least 0.01 tolerance
		double tol = 0.05;
		StatsCollector st = new StatsCollector();
		for(int i = 0; i < n; i++){
			//System.out.println(uniform[i]);
			st.update(uniform[i]);
		}
		System.out.println(st.getMean());
		System.out.println(st.getVariance());
		assertEquals(0.5, st.getMean(), tol);
		assertEquals(1.0/12, st.getVariance(), tol);
	}
}
