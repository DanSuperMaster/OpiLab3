package itmo.programming.repositories;

import itmo.programming.dtos.PointDTO;
import java.util.List;

public interface PointRepository {
    void save(PointDTO pointDTO);
    List<PointDTO> getAllPoints();
}
