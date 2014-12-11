package monteCarlo;

import junit.framework.TestCase;

public class Test_StatsCollector extends TestCase {
	// test statsCollector various methods
	double tol = 0.0001;
	public void test_calculateMean(){
		double[] r = {1,2,3,4,5,6,7,8,9,10};
		StatsCollector st = new StatsCollector();
		for(int i = 0; i < r.length; i++){
			st.update(r[i]);
		}
		assertEquals(5.5, st.getMean(), tol);
		assertEquals(38.5, st.getSumOfSquare(), tol);
		assertEquals(8.25, st.getVariance(), tol);
	}
	public void test_calculateSquareMean(){
		double[] r = {1,2,3};
		StatsCollector st = new StatsCollector();
		for(int i = 0; i < r.length; i++){
			st.update(r[i]);
		}
		System.out.println(st.getVariance());
		assertEquals(2, st.getMean(), tol);
		assertEquals(2.0/3, st.getVariance(), tol);
		assertEquals(14.0/3, st.getSumOfSquare(), tol);
	}
	public void test_isReady(){
		double[] r = {1,2,3,4,5,6,7,8,9,10};
		StatsCollector st = new StatsCollector();
		for(int i = 0; i < r.length; i++){
			st.update(r[i]);
		}
		assertTrue(st.isReady(0.01, 2) == (2*Math.sqrt(8.25/10)<0.01));
	}

}
