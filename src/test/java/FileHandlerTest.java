import com.iit.OOD.CW.FileHandler;
import com.iit.OOD.CW.Participant;
import com.iit.OOD.CW.Team;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    FileHandler fileHandler = new FileHandler();
    File tempFile;

    @BeforeEach
    void setup() throws Exception {
        tempFile = File.createTempFile("test_participants", ".csv");
        tempFile.deleteOnExit();
    }

    @Test
    void testAppendAndReadParticipants() {
        Participant p = new Participant("P01", "John", "john@mail.com",
                "DOTA 2", 7, "Attacker", 80, "Leader");

        fileHandler.appendParticipantToCSV(p, tempFile.getAbsolutePath());

        List<Participant> list = fileHandler.readParticipantsFromCSV(tempFile.getAbsolutePath());
        assertEquals(1, list.size());
        assertEquals("John", list.get(0).getName());
    }

    @Test
    void testSaveAndReadTeams() throws Exception {
        Participant p = new Participant("P01", "Mike", "m@mail.com",
                "FIFA", 5, "Supporter", 60, "Thinker");

        Team team = new Team("Team X");
        team.addMember(p);

        File outFile = File.createTempFile("teams", ".csv");
        outFile.deleteOnExit();

        fileHandler.saveTeamsToCSV(List.of(team), outFile.getAbsolutePath());

        List<Team> loaded = fileHandler.readTeamsFromCSV(outFile.getAbsolutePath());
        assertEquals(1, loaded.size());
        assertEquals(1, loaded.get(0).getMembers().size());
        assertEquals("Mike", loaded.get(0).getMembers().get(0).getName());
    }
}
