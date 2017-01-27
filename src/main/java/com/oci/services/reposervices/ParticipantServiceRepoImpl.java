package com.oci.services.reposervices;

import com.oci.domain.Participant;
import com.oci.repositories.ParticipantRepository;
import com.oci.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@Service
@Profile("springdatajpa")
public class ParticipantServiceRepoImpl implements ParticipantService{

    private ParticipantRepository participantRepository;
    private Participant participant;

    @Autowired
    public void setParticipantRepository(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Autowired
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public Participant saveOrUpdate(Participant domainObject) {
        return participantRepository.save(domainObject);
    }
}
