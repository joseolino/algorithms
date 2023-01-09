import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	
	private int n;
	
	private int trials;
	
	private int sitesNeeded[];
	
	private int indexSN;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if(n > 0 && trials > 0) {
    		this.n = n;
    		this.trials = trials;
    		this.sitesNeeded = new int[trials];
    		this.init();
    	} else {
    		throw new IllegalArgumentException();
    	}
    }

    private void init() {
    	for(int i = 0; i < trials; i++) {
    	    Percolation p = new Percolation(n);
    	    while(!p.percolates()) {
    	    	int row = StdRandom.uniformInt(n);
    	    	int col = StdRandom.uniformInt(n);
    	    	if(!p.isOpen(row + 1, col + 1)) {
    	    		p.open(row + 1, col + 1);
    	    	}
    	    }
    	    this.addStat(p.numberOfOpenSites());
    	}
		
	}

	// sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(this.sitesNeeded) / Math.pow(this.n, 2);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(this.sitesNeeded) / Math.pow(this.n, 2);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return this.confidenceLoHi(true);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return this.confidenceLoHi(false);
    }
    
    private double confidenceLoHi(boolean low) {
    	double mean = this.mean();
    	double stddev = this.stddev();
    	double factor = (1.96) * stddev / Math.sqrt(this.trials);
    	double result = 0;
    	if(low) {
    		result = mean - factor;
    	} else {
    		result = mean + factor;
    	}
    	return result;
    }
    
    private void addStat(int stat) {
    	this.sitesNeeded[this.indexSN] = stat;
    	this.indexSN++;
    }

    // test client (see below)
    public static void main(String[] args) {
    	if(args.length < 2) {
    		throw new IllegalArgumentException("You must provide 2 params.");
    	}
    	int n = Integer.parseInt(args[0]);
    	int trials = Integer.parseInt(args[1]);
    	PercolationStats ps = new PercolationStats(n,trials);
    	double mean = ps.mean();
    	double stddev = ps.stddev();
    	double confidenceLo = ps.confidenceLo();
    	double confidenceHi = ps.confidenceHi();
    	StdOut.println("mean                    = " + mean);
    	StdOut.println("stddev                  = " + stddev);
    	StdOut.println("95% confidence interval	= [" 
    	    + confidenceLo + ", " + confidenceHi + "]");
    }

}
