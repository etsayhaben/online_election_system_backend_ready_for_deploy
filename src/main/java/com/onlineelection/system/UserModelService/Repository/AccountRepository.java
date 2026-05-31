package com.onlineelection.system.UserModelService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onlineelection.system.UserModelService.Entity.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByStudentId(String studentId);

    Optional<Account> findByEmail(String email);

    // Removed the password-based method
    Optional<Account> findByStudentIdAndPassword(String studentId, String password);

    Optional<Account> findByStudentIdAndEmail(String studentId, String email);

    // Add custom query methods if needed
}
