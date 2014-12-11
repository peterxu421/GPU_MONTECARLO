package monteCarlo;

import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class AsianCallOption implements I_PayOut {
	
	private double K;
	
	public AsianCallOption(double K){
		this.K = K;
	}

	@Override
	/*
	* This function gives the payout of Asian option given the stock path
	* */
	public double getPayout(I_StockPath path) {
		List<Pair<DateTime, Double>> prices = path.getPrices();
		double sum = 0.0;
		// calculate the average of prices
		for (int i = 0; i < prices.size(); i++){
			sum += prices.get(i).getValue();
		}
		double avg = sum/prices.size();
		return Math.max(0, avg - K);
	}

}
