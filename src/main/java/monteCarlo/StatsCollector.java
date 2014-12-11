package monteCarlo;

/*
* The collector of all statistics
* */
public class StatsCollector {

	private double mean;
	private double variance;
	private double sumOfSquare;
	private int num;
	
	public StatsCollector(){
		this.mean = 0;
		this.variance = 0;
		this.sumOfSquare = 0;
		this.num = 0;
	}

	/*
	* Update all statistics given the new value
	* */
	public void update(double newPrice){
		mean = mean*num/(num+1)+newPrice/(num+1);
		sumOfSquare = sumOfSquare*num/(num+1)+newPrice*newPrice/(num+1);
		variance = sumOfSquare - mean*mean;
		num++;
	}
	
	/*
	 * return true is already reach the criteria, otherwise false
	 */
	public boolean isReady(double error, double y){
		if(num <= 0){
			return false;
		}
		return y*Math.sqrt(variance/num)<error;
	}
	
	public int getNum(){
		return num;
	}
	
	public double getMean(){
		return mean;
	}
	
	public double getSumOfSquare(){
		return sumOfSquare;
	}
	
	public double getVariance(){
		return variance;
	}
}
