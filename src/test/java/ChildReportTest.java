import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class ChildReportTest {

    private String testFile = "test_reports.txt";

    @Before
    public void setUp() {
        new File(testFile).delete();
    }

    @After
    public void tearDown() {
        new File(testFile).delete();
    }

    @Test
    public void testWriteReportForTest() throws IOException {
        // استدعاء الميثود بالقيم الثابتة
        ChildReport.writeReportForTest(
            "Ahmad Youssof",
            "2025-11-26",
            "Calm",
            "Healthy",
            "Playing and drawing",
            "No notes",
            testFile
        );

        String content = new String(Files.readAllBytes(Paths.get(testFile)));
        assertTrue(content.contains("Ahmad Youssof"));
        assertTrue(content.contains("Behaviour: Calm"));
        assertTrue(content.contains("Health Status: Healthy"));
    }
}