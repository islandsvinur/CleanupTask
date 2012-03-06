package nl.nogates.cleanuptask;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

/**
 *
 * @author Christian Luijten <clui@oce.com>
 */
public final class CleanupOldFiles implements FileVisitor<Path> {
    private final FileTime olderThanTime;

    public CleanupOldFiles(final Date aOlderThanTime) {
        this(FileTime.fromMillis(aOlderThanTime.getTime()));
    }

    public CleanupOldFiles(final FileTime aOlderThanTime) {
        this.olderThanTime = aOlderThanTime;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        if (attrs.lastAccessTime().compareTo(olderThanTime) < 0) {
            System.out.println("Cleaning: " + file);
            Files.deleteIfExists(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

}
