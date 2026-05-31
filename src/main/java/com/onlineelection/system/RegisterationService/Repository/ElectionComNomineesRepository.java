package com.onlineelection.system.RegisterationService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onlineelection.system.RegisterationService.Entity.ElectionComNominees;
@Repository
public interface ElectionComNomineesRepository extends JpaRepository<ElectionComNominees, Long> {
    // You can define custom query methods here if needed
}
