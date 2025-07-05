package Models;

public class Statistics {
    public int TotalConversions;
    public int FailedConversion;
    public int MostUsedBasePairs;
    public int MostUsedSourceBase;
    public int MostUsedTargetBase;
    public int TotalUndoCount;

    // ✅ Constructor to initialize all fields
    public Statistics(int TotalConversions,
                 int FailedConversion,
                 int MostUsedBasePairs,
                 int MostUsedSourceBase,
                 int MostUsedTargetBase,
                 int TotalUndoCount) {
        this.TotalConversions = TotalConversions;
        this.FailedConversion = FailedConversion;
        this.MostUsedBasePairs = MostUsedBasePairs;
        this.MostUsedSourceBase = MostUsedSourceBase;
        this.MostUsedTargetBase = MostUsedTargetBase;
        this.TotalUndoCount = TotalUndoCount;
    }

    // 📝 toString method for easy printing
    @Override
    public String toString() {
        return "Stats {" +
                "TotalConversions=" + TotalConversions +
                ", FailedConversion=" + FailedConversion +
                ", MostUsedBasePairs=" + MostUsedBasePairs +
                ", MostUsedSourceBase=" + MostUsedSourceBase +
                ", MostUsedTargetBase=" + MostUsedTargetBase +
                ", TotalUndoCount=" + TotalUndoCount +
                '}';
    }
}

    

