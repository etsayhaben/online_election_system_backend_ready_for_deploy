package com.onlineelection.system.RegisterationService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.onlineelection.system.RegisterationService.Entity.ElectionComNominees;
import com.onlineelection.system.RegisterationService.Repository.ElectionComNomineesRepository;

@Service
@Transactional
public class ElectionComNomineesService {

    @Autowired
    private ElectionComNomineesRepository committeeRegistrationRepository;

    /**
     * Saves the committee registration details to the database.
     *
     * @param registration The committee registration object containing the details.
     */
    public void saveCommitteeRegistration(ElectionComNominees registration) {
        // Perform any necessary business logic here before saving
        committeeRegistrationRepository.save(registration);
    }
}
