package monteCarlo;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class EuropeanCallOption implements I_PayOut {
	
	private double K;
	
	public EuropeanCallOption(double K){
		this.K = K;
	}

	@Override
	/*
	* This function gives the payout of European option given the stock path
	* */
	public double getPayout(I_StockPath path) {
		List<Pair<DateTime, Double>> prices = path.getPrices();
		int len = prices.size();
		return Math.max(0, prices.get(len-1).getValue() - K);
	}

}
