package com.waresense.backend.service;

import com.waresense.backend.entity.Shelf;
import com.waresense.backend.repository.ShelfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoutingService {

    private final ShelfRepository shelfRepository;

    /**
     * Calculates the optimal order to visit shelves using a Nearest Neighbor algorithm (TSP approximation).
     * @param shelfIds List of shelf IDs to visit.
     * @return Ordered list of Shelves.
     */
    public List<Shelf> calculateOptimalRoute(List<Long> shelfIds) {
        List<Shelf> shelvesToVisit = shelfRepository.findAllById(shelfIds);
        
        if (shelvesToVisit.isEmpty()) {
            return new ArrayList<>();
        }

        List<Shelf> optimizedRoute = new ArrayList<>();
        Set<Shelf> visited = new HashSet<>();

        // Start from the first shelf in the list (or a virtual "Door" point in a real app)
        Shelf current = shelvesToVisit.get(0);
        optimizedRoute.add(current);
        visited.add(current);

        while (optimizedRoute.size() < shelvesToVisit.size()) {
            Shelf nearest = findNearestShelf(current, shelvesToVisit, visited);
            if (nearest != null) {
                optimizedRoute.add(nearest);
                visited.add(nearest);
                current = nearest;
            } else {
                break; 
            }
        }

        return optimizedRoute;
    }

    private Shelf findNearestShelf(Shelf current, List<Shelf> allShelves, Set<Shelf> visited) {
        Shelf nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Shelf candidate : allShelves) {
            if (!visited.contains(candidate)) {
                double distance = calculateDistance(current, candidate);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = candidate;
                }
            }
        }
        return nearest;
    }

    // Heuristic distance calculation based on Shelf Codes (e.g., "A-01" vs "A-05")
    // Assuming code format like "ZONE-NUMBER"
    private double calculateDistance(Shelf s1, Shelf s2) {
        // Simple heuristic: 
        // Same zone + difference in number = close
        // Different zone = far (artificial high cost)
        
        String zone1 = s1.getZone().getCode();
        String zone2 = s2.getZone().getCode();
        
        if (!zone1.equals(zone2)) {
            return 1000.0; // High cost to switch zones
        }
        
        // Extract number from code if possible. This is a simplification.
        // Assuming code is like "Z-A-01" -> extract ints
        int num1 = extractNumber(s1.getCode());
        int num2 = extractNumber(s2.getCode());
        
        return Math.abs(num1 - num2);
    }

    private int extractNumber(String code) {
        try {
            return Integer.parseInt(code.replaceAll("\\D+", "")); // Remove non-digits
        } catch (NumberFormatException e) {
            return 0; 
        }
    }
}
