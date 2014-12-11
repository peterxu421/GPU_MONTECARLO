package monteCarlo;

import java.util.*;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class GBMRandomPathGenerator implements I_StockPath {
	
	// daily parameters
	private double rate;
	private double sigma;
	private double S0;
	private int N;
	private DateTime startDate;
	private DateTime endDate;
	private I_RandomVectorGenerator rvg;
	
	public GBMRandomPathGenerator(double rate, int N,
			double sigma, double S0,
			DateTime startDate, DateTime endDate,
			I_RandomVectorGenerator rvg){
		this.startDate = startDate;
		this.endDate = endDate;
		this.rate = rate;
		this.S0 = S0;
		this.sigma = sigma;
		this.N = N;
		this.rvg = rvg;
	}

	@Override
	/*
	* This function gives the prices of GBM as a list
	* */
	public List<Pair<DateTime, Double>> getPrices() {
		double[] n = rvg.getVector();
		DateTime current = new DateTime(startDate.getMillis());
		long delta = (endDate.getMillis() - startDate.getMillis())/N;
		List<Pair<DateTime, Double>> path = new ArrayList<Pair<DateTime,Double>>();
		path.add(new Pair<DateTime, Double>(current, S0));
		for ( int i=1; i < N; ++i){
			current.plusMillis((int) delta);
			path.add(new Pair<DateTime, Double>(current, 
					path.get(path.size()-1).getValue()*Math.exp((rate-sigma*sigma/2)+sigma * n[i-1])));
		}
		return path;
	}

}
