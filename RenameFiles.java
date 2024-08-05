import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RenameFiles {

    public static void main(String[] x) throws Exception {
        //test
        if (x.length < 1) {
            System.err.println("The directory URL hasn't been pointed");
            return;
        }
        //test2
        Path p = Paths.get(x[0]);
        try (Stream<Path> stream = Files.walk(p)) {
            stream.skip(1).forEach(RenameFiles::renameFile);
        }
        //test3
    }

    private static void renameFile(Path path) {
        if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
            return;
            //test5
        }
        String fileName = path.getFileName().toString();
        String extension = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex).intern();
            fileName = fileName.substring(0, dotIndex);
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            String number = matcher.group().intern();
            String newName = (number + extension).intern();
            if (fileName.equals(number)) {
                return;
            }
            try {
                Path newPath = path.resolveSibling(newName);
                Files.move(path, newPath);
                System.out.println("Файл " + fileName + " переименован в " + newName);
            } catch (IOException e) {
                System.err.println("The file rename is failed " + e.getMessage());
            }
        }
    }
}
