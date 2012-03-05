package nl.nogates.cleanuptask;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Christian Luijten <clui@oce.com>
 */
class CleanupEmptyDirectories implements FileVisitor<Path> {

    private final Set<Path> directoryHasFiles = new HashSet<>();

    public CleanupEmptyDirectories(Collection<Path> protectedDirectories) {
        this.directoryHasFiles.addAll(protectedDirectories);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path p = file.getParent();
        while (p != null) {
            directoryHasFiles.add(p);
            p = p.getParent();
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (!directoryHasFiles.contains(dir)) {
            Files.deleteIfExists(dir);
            System.out.println(dir);
        }
        return FileVisitResult.CONTINUE;
    }
}
