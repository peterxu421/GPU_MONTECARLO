package monteCarlo;

/**
 * Created by jingweixu on 11/12/14.
 */

import junit.framework.TestCase;

public class Test_GPUNormalRandomNumberGenerator extends TestCase {

    // test GPU getVector method
    public void test_getVector(){
        int n = 100000;
        int batch = 32*32;
        GPUNormalRandomNumberGenerator rv = new GPUNormalRandomNumberGenerator (n, batch);
        double[] normal = rv.getVector();
        // if the length is 10000, then mean should goes to 0
        // and the std should goes to 1 with at least 0.01 tolerance
        double tol = 0.01;

        StatsCollector st = new StatsCollector();
        for(int i = 0; i < n; i++){
            st.update(normal[i]);
        }

        System.out.println(st.getMean());
        System.out.println(st.getVariance());
        // check whether all conditions are correct or not
        assertTrue(normal.length == n);
        assertEquals(0., st.getMean(), tol);
        assertEquals(1.0, st.getVariance(), tol);
    }
}
