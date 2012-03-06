package nl.nogates.cleanuptask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * Hello world!
 *
 */
public final class App {

    private App() {
    }

    public static void main(final String[] args) throws IOException {
        final File dirFile = new File(FileUtils.getUserDirectory(), "Downloads");
        final Path dir = dirFile.toPath();
        final List<Path> protectedDirectories = Arrays.asList(dir);

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date cleanupFilesBeforeThisDate = cal.getTime();

        Files.walkFileTree(dir, new CleanupOldFiles(cleanupFilesBeforeThisDate));
        Files.walkFileTree(dir, new CleanupEmptyDirectories(protectedDirectories));
    }
}
