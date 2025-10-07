package itmo.programming.controllers;

import com.google.gson.Gson;
import itmo.programming.dtos.PointDTO;
import itmo.programming.services.AreaCheckService;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ApplicationScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

@ManagedBean(name="inputBean")
@ApplicationScoped
@Setter
@Getter
public class InputBean implements Serializable {

    @ManagedProperty("#{areaCheckService}")
    private AreaCheckService areaCheckService;

    private BigDecimal x;
    private BigDecimal y;
    private Map<String, Boolean> r;
    private BigDecimal currentMaxR;
    private BigDecimal hiddenR;
    private boolean inArea;

    @PostConstruct
    public void init() {
        r = new LinkedHashMap<>();
        for (int i = 1; i <= 5; i++) {
            r.put(String.valueOf(i), false);
        }
    }

    public void check() {
        boolean wasProbitie = false;
        if (hiddenR != null && !Objects.equals(hiddenR, BigDecimal.ZERO)) {
            inArea = areaCheckService.process(x, y, hiddenR);
            if (inArea) wasProbitie = true;
        } else {
            for (Map.Entry<String, Boolean> entry : r.entrySet()) {
                if (entry.getValue()) {
                    inArea = areaCheckService.process(x, y, new BigDecimal(entry.getKey()));
                    if (inArea) wasProbitie = true;
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("wasProbitie", wasProbitie);
    }

    public void updateFilteredPoints() {
        List<PointDTO> filteredPoints = areaCheckService.getPointRepository().getAllPoints();
        filteredPoints.forEach(p -> {
            p.setR(currentMaxR);
            p.setInArea(areaCheckService.check(p.getX(), p.getY(), p.getR()));
        });
        String json = new Gson().toJson(filteredPoints);
        PrimeFaces.current().ajax().addCallbackParam("pointsJson", json);
    }

    public void resetHiddenR() {
        hiddenR = BigDecimal.ZERO;
    }

    public void setCurrentMaxR() {
        FacesContext context = FacesContext.getCurrentInstance();
        String maxRParam = context.getExternalContext().getRequestParameterMap().get("maxR");
        if (maxRParam != null) {
            this.currentMaxR = new BigDecimal(maxRParam);
        } else {
            this.currentMaxR = BigDecimal.ZERO;
        }
    }

    public List<PointDTO> getAllPoints() {
        return areaCheckService.getPointRepository().getAllPoints();
    }

    public List<PointDTO> getReversedPoints() {
        return areaCheckService.getPointRepository().getAllPoints().reversed();
    }
}
