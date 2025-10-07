package itmo.programming.repositories;

import itmo.programming.dtos.PointDTO;
import itmo.programming.entities.PointEntity;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name="pointRepository")
@ApplicationScoped
@Getter
@Setter
public class PointRepositoryORM implements PointRepository {

    private EntityManager entityManager;

    public PointRepositoryORM() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pointsPU");
        entityManager = emf.createEntityManager();
    }

    @Override
    public void save(PointDTO pointDTO) {
        entityManager.getTransaction().begin();

        PointEntity entity = new PointEntity(
                pointDTO.getX(),
                pointDTO.getY(),
                pointDTO.getR(),
                pointDTO.isInArea()
        );
        entityManager.persist(entity);

        entityManager.getTransaction().commit();
    }

    @Override
    public List<PointDTO> getAllPoints() {
        List<PointEntity> entities = entityManager.createQuery("SELECT e FROM PointEntity e", PointEntity.class).getResultList();
        return entities.stream()
                .map(e -> new PointDTO(e.getX(), e.getY(), e.getR(), e.isInArea()))
                .collect(Collectors.toList());
    }
}
