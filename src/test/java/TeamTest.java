import com.iit.OOD.CW.Participant;
import com.iit.OOD.CW.Team;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void testTeamAddMember() {
        Team team = new Team("Team A");
        Participant p = new Participant("P01", "Alice", "a@mail.com", "FIFA",
                6, "Supporter", 70, "Balanced");

        team.addMember(p);

        assertEquals(1, team.getMembers().size());
        assertEquals("Alice", team.getMembers().get(0).getName());
    }
}
