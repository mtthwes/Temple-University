import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IndexTree extends BinaryTree {

	public void add(String word, int lineNumber) {
		root = add(root, word, lineNumber);
	}

	public IndexNode search(String word) {
		return search(root, word);
	}

	public void delete(String word) {
		root = delete(root, word);
	}

	public void printIndex() {
		printInOrder(root);
	}

	public static void main(String[] args) {
		IndexTree index = new IndexTree();
		try {
			File file = new File("src/shakespeare.txt");
			Scanner sc = new Scanner(file);
			int lineNumber = 1;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				line = line.replaceAll("[^a-zA-Z0-9 ]", " ");
				String[] words = line.split("\\s+");
				for (String word : words) {
					if (!word.isEmpty()) {
						index.add(word.toLowerCase(), lineNumber);
					}
				}
				lineNumber++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
			return;
		}
		System.out.println("Index:");
		index.printIndex();

		Scanner inputScanner = new Scanner(System.in);
		while (true) {
			System.out.print("\nEnter a keyword to search for (or type 'exit' to quit): ");
			String keyword = inputScanner.nextLine().trim().toLowerCase();
			if (keyword.equals("exit")) break;
			IndexNode result = index.search(keyword);
			if (result != null) System.out.println("Found: " + result);
			else System.out.println("Keyword '" + keyword + "' not found in the index.");
		}
		inputScanner.close();
	}
}
