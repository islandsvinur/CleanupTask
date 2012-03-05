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
class CleanupOldFiles implements FileVisitor<Path> {
    private final FileTime olderThanTime;

    CleanupOldFiles(Date time) {
        this(FileTime.fromMillis(time.getTime()));
    }

    CleanupOldFiles(FileTime olderThanTime) {
        this.olderThanTime = olderThanTime;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.lastAccessTime().compareTo(olderThanTime) < 0) {
            System.out.println("Cleaning: " + file);
            Files.deleteIfExists(file);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

}
