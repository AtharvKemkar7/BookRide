package com.rideshare.trip.dto;

import java.math.BigDecimal;

public class TripStatsResponse {

    private long totalTrips;
    private long requested;
    private long inProgress;
    private long completed;
    private long cancelled;
    private BigDecimal completedRevenue;

    public long getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(long totalTrips) {
        this.totalTrips = totalTrips;
    }

    public long getRequested() {
        return requested;
    }

    public void setRequested(long requested) {
        this.requested = requested;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public long getCancelled() {
        return cancelled;
    }

    public void setCancelled(long cancelled) {
        this.cancelled = cancelled;
    }

    public BigDecimal getCompletedRevenue() {
        return completedRevenue;
    }

    public void setCompletedRevenue(BigDecimal completedRevenue) {
        this.completedRevenue = completedRevenue;
    }
}
