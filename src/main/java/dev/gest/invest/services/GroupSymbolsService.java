package dev.gest.invest.services;

import dev.gest.invest.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupSymbolsService {

    private final AssetRepository assetRepository;

    public GroupSymbolsService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<String> getSymbolsInGroups(int categoryId, int groupSize) {
        List<String> symbols = assetRepository.findSymbolByCategory(categoryId);
        List<String> groups = new ArrayList<>();

        for (int i = 0; i < symbols.size(); i += groupSize) {
            List<String> subList = symbols.subList(i, Math.min(i + groupSize, symbols.size()));

            String joinedGroup = String.join(",", subList);
            groups.add(joinedGroup);
        }

        return groups;
    }
}
