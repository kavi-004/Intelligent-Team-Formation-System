import com.iit.OOD.CW.Main;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.lang.reflect.Method;


class MainTest {

    @Test
    void testMainMethodExists() throws Exception {
        Method m = Main.class.getMethod("main", String[].class);
        assertNotNull(m);
    }
}
