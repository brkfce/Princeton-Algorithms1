import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] gridPercFrac;  //This will contain the percolation threshold for each trial

    public PercolationStats(int n, int trials) {
        gridPercFrac = new double[trials];

        for (int trialCount = 0; trialCount < trials; trialCount = trialCount + 1) {
            Percolation percTrial = new Percolation(n); //Creates a new trial
            int row, col;
            while (!percTrial.percolates()) {
                row = StdRandom.uniform(n) + 1; //Randomly selects a grid and opens it
                col = StdRandom.uniform(n) + 1;
                percTrial.open(row, col);
            }   //Stops when the grid percolates
            gridPercFrac[trialCount] = percTrial.numberOfOpenSites() / (n * n / 1.0);
        }
    }

    public double mean() {  //Finds the average of the percolation thresholds
        return StdStats.mean(gridPercFrac);
    }

    public double stddev() {    //Finds the standard deviation of the percolation thresholds
        return StdStats.stddev(gridPercFrac);
    }

    public double confidenceLo() {  //Finds the bounds of the 95% confidence interval
        return (mean() - ((1.96 * stddev()) / Math.sqrt(gridPercFrac.length)));
    }

    public double confidenceHi() {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(gridPercFrac.length)));
    }

    public static void main(String[] args) {
        System.out.println("Please enter the width of the grid, and the number of trials to take place: ");
        int n = Integer.parseInt(args[0]), trials = Integer.parseInt(args[1]);
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid arguments - grid size and trial number must be postive integers");
        }
        PercolationStats percStats = new PercolationStats(n, trials);
        System.out.println(String.format("Mean: %1.16f", percStats.mean()));
        System.out.println(String.format("Standard Deviation: %1.16f", percStats.stddev()));
        System.out.println(String.format("95%% Confidence interval: [%1$1.16f, %2$1.16f]", percStats.confidenceLo(), percStats.confidenceHi()));
    }

}
