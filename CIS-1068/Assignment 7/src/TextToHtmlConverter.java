import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextToHtmlConverter {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input file name (must end with .txt): ");
        String inputFileName = scanner.nextLine();

        if (!inputFileName.endsWith(".txt")) {
            System.out.println("Input file must end with .txt");
            scanner.close();
            return;
        }

        String outputFileName = inputFileName.substring(0, inputFileName.length() - 4) + ".html";

        File inputFile = new File(inputFileName);
        Scanner fileScanner = new Scanner(inputFile);
        PrintWriter writer = new PrintWriter(outputFileName);

        writer.println("<html>");
        writer.println("<body>");

        boolean inList = false;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();

            if (line.isEmpty()) {
                writer.println("<p>");
            } else if (line.startsWith("#") && line.endsWith("#")) {
                String headerText = line.substring(1, line.length() - 1).trim();
                writer.println("<h1>" + headerText + "</h1>");
            } else if (line.startsWith("-")) {
                if (!inList) {
                    writer.println("<ul>");
                    inList = true;
                }
                String listItem = line.substring(1).trim();
                writer.println("<li>" + listItem + "</li>");
            } else {
                if (inList) {
                    writer.println("</ul>");
                    inList = false;
                }
                line = convertLinks(line);
                line = convertBoldAndItalic(line);
                writer.println(line + "<br />");
            }
        }

        if (inList) {
            writer.println("</ul>");
        }

        writer.println("</body>");
        writer.println("</html>");

        scanner.close();
        fileScanner.close();
        writer.close();

        System.out.println("HTML file has been created: " + outputFileName);
    }

    static String convertLinks(String line) {
        while (line.contains("[[") && line.contains("]]") && line.indexOf("[[") < line.indexOf("]]")) {
            int start = line.indexOf("[[");
            int end = line.indexOf("]]");
            String linkText = line.substring(start + 2, end);

            String[] parts = linkText.split("\\]\\[");
            if (parts.length == 2) {
                String url = parts[0];
                String displayText = parts[1];
                String hyperlink = "<a href=\"" + url + "\">" + displayText + "</a>";
                line = line.substring(0, start) + hyperlink + line.substring(end + 2);
            } else {
                break;
            }
        }
        return line;
    }

    static String convertBoldAndItalic(String line) {
        while (line.contains("**")) {
            int start = line.indexOf("**");
            int end = line.indexOf("**", start + 2);
            if (end == -1) {
                break;
            }
            String boldText = line.substring(start + 2, end);
            line = line.substring(0, start) + "<b>" + boldText + "</b>" + line.substring(end + 2);
        }


        while (line.contains("*")) {
            int start = line.indexOf("*");
            int end = line.indexOf("*", start + 1);
            if (end == -1) {
                break;
            }
            String italicText = line.substring(start + 1, end);
            line = line.substring(0, start) + "<i>" + italicText + "</i>" + line.substring(end + 1);
        }

        return line;
    }
}
