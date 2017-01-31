package com.oci.services.reposervices;

import com.oci.domain.Participant;
import com.oci.repositories.ParticipantRepository;
import com.oci.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Participant getById(Integer id) {
        return participantRepository.findOne(id);
    }

    @Override
    public Participant saveOrUpdate(Participant domainObject) {
        return participantRepository.save(domainObject);
    }

    @Override
    public List<?> listAll() {
        List<Participant> participants = new ArrayList<>();
        participantRepository.findAll().forEach(participants::add);
        return participants;
    }
}
