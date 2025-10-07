package itmo.programming.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "points")
@Getter
@Setter
@NoArgsConstructor
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 30, scale = 10, nullable = false)
    private BigDecimal x;

    @Column(precision = 30, scale = 10, nullable = false)
    private BigDecimal y;

    @Column(precision = 30, scale = 10, nullable = false)
    private BigDecimal r;

    @Column(name = "in_area", nullable = false)
    private boolean inArea;

    public PointEntity(BigDecimal x, BigDecimal y, BigDecimal r, boolean inArea) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.inArea = inArea;
    }
}
