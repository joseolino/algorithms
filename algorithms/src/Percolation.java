import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
    private int virtualTopSite;
    
    private int virtualBottonSite;
	
	private boolean siteOpen[][];
	
	private int openSitesCount;
    
    private WeightedQuickUnionUF sitesWQUF;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if(n > 0) {
    	    this.siteOpen = new boolean[n][n];
    	    this.sitesWQUF = new WeightedQuickUnionUF(n * n + 2);
    	    this.virtualTopSite = n * n;
    	    this.virtualBottonSite = n * n + 1;
    	    for(int i = 0; i < n; i++) {
    	    	this.sitesWQUF.union(virtualTopSite, i);
    	    	this.sitesWQUF.union(virtualBottonSite, i + (n-1) * n);
    	    }
    	} else {
    		throw new IllegalArgumentException();
    	}
    }
    
    private int linearIndex(int row, int col) {
    	return (row * siteOpen.length) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	int r = row - 1;
    	int c = col - 1;
    	if(r < 0 || c < 0 || r >= siteOpen.length || c >= siteOpen.length) {
    		throw new IllegalArgumentException();
    	}
    	this.siteOpen[r][c] = true;
    	int index = this.linearIndex(r, c);
    	if(r - 1 >= 0 && this.siteOpen[r - 1][c]) {
    		this.sitesWQUF.union(index, this.linearIndex(r - 1, c));
    	}
    	if(r + 1 < siteOpen.length && this.siteOpen[r + 1][c]) {
    		this.sitesWQUF.union(index, this.linearIndex(r + 1, c));
    	}
    	if(c - 1 >= 0 && this.siteOpen[r][c - 1]) {
    		this.sitesWQUF.union(index, this.linearIndex(r, c - 1));
    	}
    	if(c + 1 < siteOpen.length && this.siteOpen[r][c + 1]) {
    		this.sitesWQUF.union(index, this.linearIndex(r, c + 1));
    	}
    	this.openSitesCount++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	int r = row - 1;
    	int c = col - 1;
    	if(r < 0 || c < 0 || r >= siteOpen.length || c >= siteOpen.length) {
    		throw new IllegalArgumentException();
    	}
    	return this.siteOpen[r][c];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	int r = row - 1;
    	int c = col - 1;
    	if(r < 0 || c < 0 || r >= siteOpen.length || c >= siteOpen.length) {
    		throw new IllegalArgumentException();
    	}
    	int index = this.linearIndex(r, c);
    	return (this.isOpen(row, col) && this.sitesWQUF.find(index) == this.sitesWQUF.find(this.virtualTopSite));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return this.openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
    	return this.sitesWQUF.find(this.virtualBottonSite) == this.sitesWQUF.find(this.virtualTopSite);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(20);
        for(int i = 0; i < 20; i++) {
        	p.open(i + 1, 20);
        	System.out.println(p.isOpen(i + 1, 20) + " - " + p.isFull(i + 1, 20));
        	System.out.println(p.isOpen(i + 1, 19) + " - " + p.isFull(i + 1, 19));
        }
        StdOut.println(p.percolates());
    }
}