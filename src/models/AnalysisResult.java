package models;

import java.util.ArrayList;

public class AnalysisResult {
    private ArrayList<Stat> stats = new ArrayList<>();

    public AnalysisResult(ArrayList<Stat> stats) {
        this.stats = stats;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Stat> stats) {
        this.stats = stats;
    }
}
