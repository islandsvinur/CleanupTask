package nl.nogates.cleanuptask;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Luijten <clui@oce.com>
 */
public final class CleanupEmptyDirectoriesTest {

    public CleanupEmptyDirectoriesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void test_visitFile() throws IOException {
        CleanupEmptyDirectories instance = new CleanupEmptyDirectories(new HashSet<Path>());

        Path file = mock(Path.class);
        Path parentFile = mock(Path.class);
        when(file.getParent()).thenReturn(parentFile);

        BasicFileAttributes attrs = mock(BasicFileAttributes.class);
        FileVisitResult visitFile = instance.visitFile(file, attrs);

        verify(file).getParent();
        verify(parentFile).getParent();

        assertFalse(instance.getDirectoryHasFiles().contains(file));
        assertTrue(instance.getDirectoryHasFiles().contains(parentFile));

        assertEquals(FileVisitResult.CONTINUE, visitFile);
    }

    @Test
    public void test_preVisitDirectory() throws IOException {
        CleanupEmptyDirectories instance = new CleanupEmptyDirectories(new HashSet<Path>());

        Path path = mock(Path.class);
        BasicFileAttributes attrs = mock(BasicFileAttributes.class);

        FileVisitResult preVisitDirectory = instance.preVisitDirectory(path, attrs);

        assertEquals(FileVisitResult.CONTINUE, preVisitDirectory);
    }

    @Test
    public void test_visitFileFailed() throws IOException {
        CleanupEmptyDirectories instance = new CleanupEmptyDirectories(new HashSet<Path>());

        Path path = mock(Path.class);
        BasicFileAttributes attrs = mock(BasicFileAttributes.class);

        FileVisitResult preVisitDirectory = instance.preVisitDirectory(path, attrs);

        assertEquals(FileVisitResult.CONTINUE, preVisitDirectory);
    }

//    @Test
    public void test_postVisitDirectory() throws IOException {
        CleanupEmptyDirectories instance = new CleanupEmptyDirectories(new HashSet<Path>());

        Path path = mock(Path.class);
        IOException exception = null;

        FileVisitResult preVisitDirectory = instance.postVisitDirectory(path, exception);

        assertEquals(FileVisitResult.CONTINUE, preVisitDirectory);
    }
}
