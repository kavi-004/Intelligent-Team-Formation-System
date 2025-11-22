import com.iit.OOD.CW.Participant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    @Test
    void testParticipantCreation() {
        Participant p = new Participant("P01", "John", "john@mail.com",
                "DOTA 2", 7, "Attacker", 85, "Leader");

        assertEquals("P01", p.getId());
        assertEquals("John", p.getName());
        assertEquals("john@mail.com", p.getEmail());
        assertEquals("DOTA 2", p.getGame());
        assertEquals(7, p.getSkillLevel());
        assertEquals("Attacker", p.getRole());
        assertEquals(85, p.getPersonalityScore());
        assertEquals("Leader", p.getPersonalityType());
    }
}
