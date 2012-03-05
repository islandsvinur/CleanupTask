package nl.nogates.cleanuptask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.io.FileUtils;

/**
 * Hello world!
 *
 */
public final class App {

    private App() {
    }

    public static void main(final String[] args) throws IOException {
        File dirFile = new File(FileUtils.getUserDirectory(), "Downloads");
        Path dir = dirFile.toPath();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date cleanupBeforeDate = cal.getTime();

        Files.walkFileTree(dir, new CleanupOldFiles(cleanupBeforeDate));
        Files.walkFileTree(dir, new CleanupEmptyDirectories(Arrays.asList(dir)));
    }
}
