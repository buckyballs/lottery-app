package com.oci.util;

import com.oci.domain.Participant;

import java.util.List;

/**
 * Created by maqsoodi on 2/6/2017.
 */
public class ObjectOperations {

    public static boolean containsEmail(final List<Participant> participants, Participant participant) {
        return participants.stream().map(Participant::getEmail).filter(participant.getEmail()::equals).findFirst().isPresent();
    }
}
