package com.smartload.optimizer.service;

import com.smartload.optimizer.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoadOptimizerService {

    public OptimizeResponse optimize(OptimizeRequest request) {

        Truck truck = request.truck;
        List<Order> orders = request.orders;
        int n = orders.size();

        long bestPayout = 0;
        int bestMask = 0;

        for (int mask = 0; mask < (1 << n); mask++) {

            long weight = 0, volume = 0, payout = 0;
            String origin = null, destination = null;
            LocalDate maxPickup = null, minDelivery = null;
            boolean hazmat = false, nonHazmat = false;
            boolean valid = true;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) continue;

                Order o = orders.get(i);

                weight += o.weightLbs;
                volume += o.volumeCuft;
                payout += o.payoutCents;

                if (weight > truck.maxWeightLbs ||
                    volume > truck.maxVolumeCuft) {
                    valid = false;
                    break;
                }

                if (origin == null) {
                    origin = o.origin;
                    destination = o.destination;
                } else if (!origin.equals(o.origin) ||
                           !destination.equals(o.destination)) {
                    valid = false;
                    break;
                }

                maxPickup = (maxPickup == null)
                        ? o.pickupDate
                        : maxPickup.isAfter(o.pickupDate) ? maxPickup : o.pickupDate;

                minDelivery = (minDelivery == null)
                        ? o.deliveryDate
                        : minDelivery.isBefore(o.deliveryDate) ? minDelivery : o.deliveryDate;

                if (maxPickup.isAfter(minDelivery)) {
                    valid = false;
                    break;
                }

                if (o.isHazmat) hazmat = true;
                else nonHazmat = true;

                if (hazmat && nonHazmat) {
                    valid = false;
                    break;
                }
            }

            if (valid && payout > bestPayout) {
                bestPayout = payout;
                bestMask = mask;
            }
        }

        return buildResponse(truck, orders, bestMask, bestPayout);
    }

    private OptimizeResponse buildResponse(
            Truck truck, List<Order> orders, int mask, long payout) {

        OptimizeResponse res = new OptimizeResponse();
        res.truckId = truck.id;
        res.selectedOrderIds = new ArrayList<>();

        long weight = 0, volume = 0;

        for (int i = 0; i < orders.size(); i++) {
            if ((mask & (1 << i)) != 0) {
                Order o = orders.get(i);
                res.selectedOrderIds.add(o.id);
                weight += o.weightLbs;
                volume += o.volumeCuft;
            }
        }

        res.totalPayoutCents = payout;
        res.totalWeightLbs = weight;
        res.totalVolumeCuft = volume;
        res.utilizationWeightPercent =
                round(weight * 100.0 / truck.maxWeightLbs);
        res.utilizationVolumePercent =
                round(volume * 100.0 / truck.maxVolumeCuft);

        return res;
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
