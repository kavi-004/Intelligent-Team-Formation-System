import com.iit.OOD.CW.Participant;
import com.iit.OOD.CW.Team;
import com.iit.OOD.CW.TeamBuilder;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TeamBuilderTest {

    @Test
    void testFormTeams() {
        Participant p1 = new Participant("1", "A", "a@mail.com",
                "Chess", 5, "Attacker", 80, "Leader");
        Participant p2 = new Participant("2", "B", "b@mail.com",
                "Chess", 6, "Defender", 60, "Thinker");
        Participant p3 = new Participant("3", "C", "c@mail.com",
                "Chess", 7, "Supporter", 70, "Balanced");

        List<Participant> pool = List.of(p1, p2, p3);

        TeamBuilder builder = new TeamBuilder(null, pool, 2);
        List<Team> teams = builder.formTeams();

        assertFalse(teams.isEmpty());
        assertTrue(teams.get(0).getMembers().size() <= 2);
    }
}
