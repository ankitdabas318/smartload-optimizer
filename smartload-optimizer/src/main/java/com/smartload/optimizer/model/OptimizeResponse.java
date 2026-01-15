package com.smartload.optimizer.model;

import java.util.List;

public class OptimizeResponse {
    public String truckId;
    public List<String> selectedOrderIds;
    public long totalPayoutCents;
    public long totalWeightLbs;
    public long totalVolumeCuft;
    public double utilizationWeightPercent;
    public double utilizationVolumePercent;
}
