
package com.onlineelection.system.RegisterationService.Repository;
import com.onlineelection.system.RegisterationService.Entity.Notice; // Import Notice entity
import com.onlineelection.system.RegisterationService.Entity.NoticeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRoleRepository extends JpaRepository<NoticeRole, Long> {

   @Query("SELECT n.content FROM NoticeRole nr JOIN nr.notice n WHERE nr.role = :role")
List<String> findNoticeContentsByRole(@Param("role") String role);

}
