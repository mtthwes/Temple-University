import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class testing {

    public static int countBlankLines(String fileName) throws FileNotFoundException {
        int blankLineCount = 0;

        // Open the file and create a Scanner to read the file
        File file = new File(fileName);
        Scanner fileScanner = new Scanner(file);

        // Iterate through each line in the file
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            // Check if the line is blank by checking its length
            if (line.length() == 0) {
                blankLineCount++;
            }
        }

        // Close the file scanner
        fileScanner.close();

        // Return the total count of blank lines
        return blankLineCount;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "data.txt"; // Replace with the actual file name
        int blankLines = countBlankLines(fileName);
        System.out.println("Number of blank lines: " + blankLines);
    }
}
