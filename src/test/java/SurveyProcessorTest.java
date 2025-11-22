import com.iit.OOD.CW.Participant;
import com.iit.OOD.CW.SurveyProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SurveyProcessorTest {

    SurveyProcessor processor = new SurveyProcessor();

    @Test
    void testValidateParticipantValid() {
        Participant p = new Participant("P01", "Test", "t@mail.com",
                "Chess", 8, "Strategist", 90, "Leader");

        Participant validated = processor.validateParticipant(p);
        assertNotNull(validated);
    }

    @Test
    void testValidateParticipantInvalidEmail() {
        Participant p = new Participant("P02", "Bob", "wrongEmail",
                "Valorant", 5, "Attacker", 60, "Thinker");

        Participant validated = processor.validateParticipant(p);
        assertNull(validated);
    }
}
