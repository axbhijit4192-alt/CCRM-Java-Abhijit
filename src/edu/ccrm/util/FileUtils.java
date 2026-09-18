package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {
    public static Path move(Path source, Path target) throws IOException {
        return Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    public static boolean deleteIfExists(Path path) throws IOException {
        return Files.deleteIfExists(path);
    }

    // Recursively compute total size
    public static long recursiveSize(Path dir) throws IOException {
        final long[] size = {0L};
        Files.walkFileTree(dir, new SimpleFileVisitor<>() {
            @Override public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                size[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });
        return size[0];
    }
}
