package dev.gest.invest.repository;

import dev.gest.invest.dto.AssetCategoryProjection;
import dev.gest.invest.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {

    @Query(value = "SELECT asset.name AS asset_name, category.name AS category_name FROM asset JOIN category ON asset.category_id = category.id", nativeQuery = true)
    List<AssetCategoryProjection> findAllAssetNamesAndCategoryNames();

    @Query("SELECT a.id FROM Asset a WHERE a.name = :name")
    Optional<UUID> findAssetIdByName(@Param("name") String name);

    @Query("SELECT a.symbol FROM Asset a WHERE a.category = :categoryId")
    List<String> findSymbolByCategory(int categoryId);
}
