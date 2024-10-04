package telran.persistence;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
public class StringStreamsTest {
    static final String PRINT_STREAM_FILE = "printStreamFile.txt";
    static final String PRINT_WRITER_FILE = "printWriterFile.txt";
    static final String BASE_DIRECTORY = "\\Users\\sthum\\OneDrive\\Documents\\Dir1";
    static final String OUTPUT_PATH_FILE = "outputPathFile.txt";
    @Test
    @Disabled
    void printStreamTest() throws Exception{
        PrintStream printStream = new PrintStream(PRINT_STREAM_FILE);
        printStream.println("HELLO");
        printStream.close();
    }
    @Test
    @Disabled
    void printWriterTest() throws Exception{
        PrintWriter printWriter = new PrintWriter(PRINT_WRITER_FILE);
        printWriter.println("HELLO");
        printWriter.close();
    }
    @Test
    @Disabled
    void bufferedReaderTest() throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(PRINT_WRITER_FILE));
        assertEquals("HELLO",reader.readLine());
        reader.close();
    }
    @Test
    void printingDirectoryTest() throws Exception {
        printDirectoryContent(BASE_DIRECTORY,3);
    }
    /**
     * 
     * @param path -  path to a directory
     * @param depth -  number of been walked levels
     * @throws IOException 
     */

    void printDirectoryContent(String path, int depth) throws IOException {
        Path startDirectory = Paths.get(path);

        try (PrintWriter printWriter = new PrintWriter(OUTPUT_PATH_FILE)) {
            Files.walkFileTree(startDirectory, new SimpleFileVisitor<>() {
                private int currentDepth = 0;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (currentDepth <= depth) {
                        printPathIntoFile(printWriter, dir);
                        currentDepth++;
                    }
                    return FileVisitResult.CONTINUE;

                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (currentDepth <= depth) {
                        printPathIntoFile(printWriter, file);
                    }
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    currentDepth--;
                    return FileVisitResult.CONTINUE;
                }

                private void printPathIntoFile(PrintWriter printWriter, Path path) {
                    printWriter.println("  ".repeat(currentDepth * 2) + path.getFileName());
                }
            });
        }
    }
}