package dev.gest.invest.repository;

import dev.gest.invest.dto.InvestLineDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class InvestLineRepositoryCustomImpl implements InvestLineRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<InvestLineDto> findAllInvestLinesByUser(UUID userId) {
        String sql = """
                SELECT
                    p.name AS portfolio_name,
                    il.price AS price_invest,
                    il.date AS invest_date,
                    il.fees,
                    il.asset_number,
                    tot.name AS trading_operation_type,
                    c.name AS category_name,
                    ass.name AS asset_name,
                    ass.symbol,
                    ass.price AS asset_price
                FROM invest_line AS il
                JOIN portfolio AS p ON portfolio_id = p.id
                JOIN "user" AS u ON user_id = u.id
                JOIN trading_operation_type AS tot ON trading_operation_type_id = tot.id
                JOIN asset AS ass ON asset_id = ass.id
                JOIN category AS c ON category_id = c.id
                WHERE u.id = :userId
                ORDER BY tot.name ASC
                """;

        Query query = entityManager.createNativeQuery(sql, "InvestLineMapping");
        query.setParameter("userId", userId);

        return query.getResultList();
    }
}
