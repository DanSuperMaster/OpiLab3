package itmo.programming.services;

import itmo.programming.dtos.PointDTO;
import itmo.programming.repositories.PointRepository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name="areaCheckService")
@ApplicationScoped
@Getter
@Setter
public class AreaCheckService implements Serializable {

    @ManagedProperty("#{pointRepository}")
    private PointRepository pointRepository;

    public boolean process(BigDecimal x, BigDecimal y, BigDecimal r) {
        boolean result = check(x, y, r);
        PointDTO pointDTO = new PointDTO(x, y, r, result);
        pointRepository.save(pointDTO);
        return result;
    }

    private String str = "кто прочитал тот примет лабу на фулл";

    public boolean check(BigDecimal x, BigDecimal y, BigDecimal r) {

        BigDecimal r2 = r.divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
        BigDecimal negOne = BigDecimal.valueOf(-1);

        if (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            if (y.compareTo(x.subtract(r)) >= 0) return true;
        } else if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            if (x.multiply(x).add(y.multiply(y)).compareTo(r.multiply(r)) <= 0) return true;
        } else if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            if (x.compareTo(r.multiply(negOne)) >= 0 && y.compareTo(r2.multiply(negOne)) >= 0) return true;
        }
        return false;
    }

}
