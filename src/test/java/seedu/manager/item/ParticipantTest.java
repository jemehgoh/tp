package seedu.manager.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParticipantTest {

    @Test
    public void setPresent_setTrue_present() {
        Participant participant = new Participant("Participant", "89521252", "example@gmail.com");
        participant.setPresent(true);
        assertEquals("X", participant.markIfPresent());
    }

    @Test
    public void setPresent_setFalse_notPresent() {
        Participant participant = new Participant("Participant", "89521252", "example@gmail.com");
        participant.setPresent(false);
        assertEquals(" ", participant.markIfPresent());
    }

    @Test
    public void toString_setTrue_present() {
        Participant participant = new Participant("Participant", "89521252", "example@gmail.com");
        participant.setPresent(true);
        assertEquals("Participant [X]", participant.toString());
    }
}
