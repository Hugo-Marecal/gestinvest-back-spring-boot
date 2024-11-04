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
                        @ColumnResult(name = "fees", type = BigDecimal.class),
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
    private BigDecimal price;
    private LocalDate date;
    private BigDecimal fees;
    private BigDecimal asset_number;
}
