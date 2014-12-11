package monteCarlo;

import java.util.Random;

/**
 * Created by jingweixu on 11/12/14.
 */
public class UniformRandomNumberGenerator  implements I_RandomVectorGenerator{

    private int N;

    public UniformRandomNumberGenerator(int N){
        this.N = N;
    }

    /*
    * This function aims to generate the uniform double vector between 0 and 1
    * @Input: number of sequence
    * @Output: uniform double array
    * */
    public double[] getVector(){
        Random r = new Random();
        double[] vector = new double[N];
        for ( int i = 0; i < vector.length; ++i){
            vector[i] = r.nextDouble();
        }
        return vector;
    }
}
