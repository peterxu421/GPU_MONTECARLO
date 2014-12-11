package monteCarlo;

import junit.framework.TestCase;

public class Test_AntiTheticVectorGenerator extends TestCase {
	// test the antithetic getvector method
	public void test_getVector(){
		int N = 10;
		AntiTheticVectorGenerator rvg = new AntiTheticVectorGenerator(new UniformRandomNumberGenerator(N));
		double[] v1 = rvg.getVector();
		double[] v2 = rvg.getVector();
		double[] v3 = rvg.getVector();
		double[] v4 = rvg.getVector();
		// check whether v1 is negate of v2, v3 is negate of v4
		// and v1 is not the same as v3
		for(int i = 0; i < N; i++){
			assertTrue(v1[i] == -v2[i]);
			assertTrue(v3[i] == -v4[i]);
			assertFalse(v1[i] == v3[i]);
			assertFalse(v1[i] == v4[i]);
		}
	}
}
