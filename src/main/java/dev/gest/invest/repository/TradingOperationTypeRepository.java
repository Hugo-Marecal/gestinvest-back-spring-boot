package dev.gest.invest.repository;

import dev.gest.invest.model.TradingOperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TradingOperationTypeRepository extends JpaRepository<TradingOperationType, UUID> {

    @Query("SELECT tot.id FROM TradingOperationType tot WHERE name= :name")
    UUID findTradingOperationTypeIdByName(@Param("name") String name);
}
