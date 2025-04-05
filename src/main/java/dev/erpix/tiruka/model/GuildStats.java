package dev.erpix.tiruka.model;

public class GuildStats {

    private long totalMessagesSent;
    private long totalCommandsExecuted;
    private long totalBalance;

    public GuildStats(long totalMessagesSent, long totalCommandsExecuted, long totalBalance) {
        this.totalMessagesSent = totalMessagesSent;
        this.totalCommandsExecuted = totalCommandsExecuted;
        this.totalBalance = totalBalance;
    }

    public static GuildStats create() {
        return new GuildStats(0, 0, 0);
    }

    public long getTotalMessagesSent() {
        return totalMessagesSent;
    }

    public void setTotalMessagesSent(long totalMessagesSent) {
        this.totalMessagesSent = totalMessagesSent;
    }

    public long getTotalCommandsExecuted() {
        return totalCommandsExecuted;
    }

    public void setTotalCommandsExecuted(long totalCommandsExecuted) {
        this.totalCommandsExecuted = totalCommandsExecuted;
    }

    public long getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(long totalBalance) {
        this.totalBalance = totalBalance;
    }

}
