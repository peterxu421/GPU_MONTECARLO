package monteCarlo;

/*
* The interface for calculating the payout function
* */
public interface I_PayOut {
	public double getPayout(I_StockPath path);
}
