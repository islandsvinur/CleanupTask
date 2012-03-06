package nl.nogates.cleanuptask;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Christian Luijten <clui@oce.com>
 */
public final class CleanupEmptyDirectories implements FileVisitor<Path> {

    private final Set<Path> directoryHasFiles = new HashSet<>();

    public CleanupEmptyDirectories(final Collection<Path> protectedDirectories) {
        this.directoryHasFiles.addAll(protectedDirectories);
    }

    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        Path p = file.getParent();
        while (p != null) {
            directoryHasFiles.add(p);
            p = p.getParent();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
        if (!directoryHasFiles.contains(dir)) {
            Files.deleteIfExists(dir);
            System.out.println(dir);
        }
        return FileVisitResult.CONTINUE;
    }

    public Set<Path> getDirectoryHasFiles() {
        return Collections.unmodifiableSet(directoryHasFiles);
    }
}
