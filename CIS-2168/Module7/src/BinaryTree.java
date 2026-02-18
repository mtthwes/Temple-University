public class BinaryTree {
    protected IndexNode root;

    protected IndexNode add(IndexNode root, String word, int lineNumber) {
        if (root == null) {
            return new IndexNode(word, lineNumber);
        }
        int cmp = word.compareToIgnoreCase(root.word);
        if (cmp < 0) {
            root.left = add(root.left, word, lineNumber);
        } else if (cmp > 0) {
            root.right = add(root.right, word, lineNumber);
        } else {
            root.occurrences++;
            if (!root.list.contains(lineNumber)) {
                root.list.add(lineNumber);
            }
        }
        return root;
    }

    protected IndexNode search(IndexNode root, String word) {
        if (root == null) return null;
        int cmp = word.compareToIgnoreCase(root.word);
        if (cmp < 0) return search(root.left, word);
        else if (cmp > 0) return search(root.right, word);
        else return root;
    }

    protected IndexNode delete(IndexNode root, String word) {
        if (root == null) return null;
        int cmp = word.compareToIgnoreCase(root.word);
        if (cmp < 0) root.left = delete(root.left, word);
        else if (cmp > 0) root.right = delete(root.right, word);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            IndexNode minNode = findMin(root.right);
            root.word = minNode.word;
            root.occurrences = minNode.occurrences;
            root.list = minNode.list;
            root.right = delete(root.right, minNode.word);
        }
        return root;
    }

    protected IndexNode findMin(IndexNode root) {
        while (root.left != null) root = root.left;
        return root;
    }

    protected void printInOrder(IndexNode root) {
        if (root != null) {
            printInOrder(root.left);
            System.out.println(root);
            printInOrder(root.right);
        }
    }
}