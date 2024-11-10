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
            // Récupérer le sous-groupe de taille groupSize (ou moins si on est à la fin de la liste)
            List<String> subList = symbols.subList(i, Math.min(i + groupSize, symbols.size()));

            // Joindre les éléments du sous-groupe avec une virgule et ajouter au résultat
            String joinedGroup = String.join(",", subList);
            groups.add(joinedGroup);
        }

        return groups;
    }
}
