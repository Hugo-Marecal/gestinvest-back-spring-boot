package dev.gest.invest.model;

import dev.gest.invest.dto.InvestLineDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@SqlResultSetMapping(
        name = "InvestLineMapping",
        classes = @ConstructorResult(
                targetClass = InvestLineDto.class,
                columns = {
                        @ColumnResult(name = "portfolio_name", type = String.class),
                        @ColumnResult(name = "price_invest", type = BigDecimal.class),
                        @ColumnResult(name = "invest_date", type = LocalDate.class),
                        @ColumnResult(name = "fees", type = double.class),
                        @ColumnResult(name = "asset_number", type = BigDecimal.class),
                        @ColumnResult(name = "trading_operation_type", type = String.class),
                        @ColumnResult(name = "category_name", type = String.class),
                        @ColumnResult(name = "asset_name", type = String.class),
                        @ColumnResult(name = "symbol", type = String.class),
                        @ColumnResult(name = "asset_price", type = BigDecimal.class)
                }
        )
)
public class InvestLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double fees;

    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal asset_number;


    @Column(name = "asset_id", nullable = false)
    private UUID asset;

    @Column(name = "portfolio_id", nullable = false)
    private UUID portfolio;

    @Column(name = "trading_operation_type_id", nullable = false)
    private UUID tradingOperationType;
}
