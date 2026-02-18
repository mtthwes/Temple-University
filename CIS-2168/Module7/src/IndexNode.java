import java.util.ArrayList;
import java.util.List;

public class IndexNode {
	String word;
	int occurrences;
	List<Integer> list;
	IndexNode left;
	IndexNode right;

	public IndexNode(String word, int lineNumber) {
		this.word = word;
		this.occurrences = 1;
		this.list = new ArrayList<>();
		this.list.add(lineNumber);
		this.left = null;
		this.right = null;
	}

	@Override
	public String toString() {
		return word + ": " + occurrences + " occurrence(s), lines " + list;
	}
}
