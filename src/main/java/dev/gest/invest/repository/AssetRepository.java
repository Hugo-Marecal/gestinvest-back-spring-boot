package dev.gest.invest.repository;

import dev.gest.invest.dto.AssetCategoryProjection;
import dev.gest.invest.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {

    @Query("SELECT a.name AS assetName, c.name AS categoryName FROM Asset a JOIN a.category c")
    List<AssetCategoryProjection> findAllAssetNamesAndCategoryNames();
}
