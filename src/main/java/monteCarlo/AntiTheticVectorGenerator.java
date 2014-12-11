package monteCarlo;

import java.util.Arrays;
/*
* This class aims to generate the negative pair of the original random variable
* */
public class AntiTheticVectorGenerator implements I_RandomVectorGenerator{
	
	private I_RandomVectorGenerator rvg;
	private double[] lastVector;


	public AntiTheticVectorGenerator(I_RandomVectorGenerator rvg){
		this.rvg = rvg;
	}

	@Override
	/*
	* This function aims to cater for generating negation of the previous random variable
	* */
	public double[] getVector() {
		if ( lastVector == null ){
			lastVector = rvg.getVector();
			return lastVector;
		} else {
			double[] tmp =Arrays.copyOf(lastVector, lastVector.length);
			lastVector = null;
			for (int i = 0; i < tmp.length; ++i){ tmp[i] = -tmp[i];}
			return tmp;
		}
	}
}

