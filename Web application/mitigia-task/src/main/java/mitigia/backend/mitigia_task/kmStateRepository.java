package mitigia.backend.mitigia_task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mitigia.backend.mitigia_task.Model.kmState;

@Repository
public interface kmStateRepository extends JpaRepository<kmState, Long>{

}
