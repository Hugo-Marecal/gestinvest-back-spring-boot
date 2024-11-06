package dev.gest.invest.repository;

import dev.gest.invest.dto.AssetLineByUserProjection;
import dev.gest.invest.model.InvestLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestLineRepository extends JpaRepository<InvestLine, UUID> {

    @Query(value = """
            SELECT
                il.id,
                il.price AS price_invest,
                il.date AS invest_date,
                il.fees,
                il.asset_number,
                tot.name AS trading_operation_type,
                c.name AS category_name,
                ass.local,
                ass.name,
                ass.symbol,
                ass.id AS asset_id,
                ass.price
            FROM invest_line AS il
                JOIN portfolio AS p
                        ON portfolio_id = p.id
                JOIN "user" AS u
                        ON user_id = u.id
                JOIN trading_operation_type AS tot
                        ON trading_operation_type_id = tot.id
                JOIN asset AS ass
                        ON asset_id = ass.id
                JOIN category AS c
                        ON category_id = c.id
            WHERE u.id = :userId and ass.symbol = :symbol
            """,
            nativeQuery = true
    )
    List<AssetLineByUserProjection> findAllAssetLinesByUserAndSymbol(@Param("userId") UUID userId, @Param("symbol") String symbol);
}
