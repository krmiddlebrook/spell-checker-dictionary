
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/** CompactPrefixTree class, implements Dictionary ADT and
 *  several additional methods. Can be used as a spell checker.
 *  Fill in code in the methods of this class. You may add additional methods. */
public class CompactPrefixTree implements Dictionary {

    private Node root; // the root of the tree

    /**
     * Default constructor.
     * Creates an empty "dictionary" (compact prefix tree).
     */
    public CompactPrefixTree() {
        root = new Node();
    }


    /**
     * Creates a dictionary ("compact prefix tree")
     * using words from the given file.
     *
     * @param filename the name of the file with words
     */
    public CompactPrefixTree(String filename) {
        // Read each word from the file, add it to the tree
        try {
            root = new Node();
            BufferedReader br;
            br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    add(line);
                }
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Adds a given word to the dictionary.
     *
     * @param word the word to add to the dictionary
     */
    public void add(String word) {
        root = add(word.toLowerCase(), root); // Calling private add method
    }

    /**
     * Checks if a given word is in the dictionary
     *
     * @param word the word to check
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean check(String word) {
        return check(word.toLowerCase(), root); // Calling private check method
    }

    /**
     * Checks if a given prefix is stored in the dictionary
     *
     * @param prefix The prefix of a word
     * @return true if the prefix is in the dictionary, false otherwise
     */
    public boolean checkPrefix(String prefix) {
        return checkPrefix(prefix.toLowerCase(), root); // Calling private checkPrefix method
    }


    /**
     * Prints all the words in the dictionary, in alphabetical order,
     * one word per line.
     */
    public void print() {
        print("", root); // Calling private print method
    }

    /**
     * Print out the nodes of the compact prefix tree, in a pre-order fashion.
     * First, print out the root at the current indentation level
     * (followed by * if the node's valid bit is set to true),
     * then print out the children of the node at a higher indentation level.
     */
    public void printTree() {
        // FILL IN CODE
        System.out.println(toString("", root, 0));
    }

    /**
     * Print out the nodes of the tree to a file, using indentations to specify the level
     * of the node.
     *
     * @param filename the name of the file where to output the tree
     */
    public void printTree(String filename) {
        // Same as printTree, but outputs info to a file
        try {
            FileWriter file = new FileWriter(filename);
            file.write(toString("", root, 0));
            file.close();

        }
        catch (IOException e1) {
            e1.printStackTrace();
        }


    }

    /**
     * Return an array of the entries in the dictionary that are as close as possible to
     * the parameter word.  If the word passed in is in the dictionary, then
     * return an array of length 1 that contains only that word.  If the word is
     * not in the dictionary, then return an array of numSuggestions different words
     * that are in the dictionary, that are as close as possible to the target word.
     * Implementation details are up to you, but you are required to make it efficient
     * and make good use ot the compact prefix tree.
     *
     * @param word           The word to check
     * @param numSuggestions The length of the array to return.  Note that if the word is
     *                       in the dictionary, this parameter will be ignored, and the array will contain a
     *                       single world.
     * @return An array of the closest entries in the dictionary to the target word
     */

    public String[] suggest(String word, int numSuggestions) {
        if (check(word)) {
            String[] foundWord = new String[1];
            foundWord[0] = word;
            return foundWord;
        }
        String[] suggestions =  suggest(word, root, numSuggestions);


        return suggestions; // don't forget to change it
    }


    /**
     * Method to calculate index of character in the array of node's children.
     * @param ch char to check
     * @return index value of the first letter in the string s
     */
    public int getIndexOfChar(char ch) {
        int index = (int) ch - (int) 'a';
        return index;
    }

    /**
     * Method to find the longest common prefix of two strings
     * @param s1 first string to compare
     * @param s2 second string to compare
     * @return string of longest common prefix
     */
    public String findLongestCommonPrefix(String s1, String s2) {
        String prefix = "";
        int length;
        if (s1.length() > s2.length()) {
            length = s2.length();
        } else {
            length = s1.length();
        }
        boolean sameLetter = true;
        int index = 0;
        while (sameLetter && index < length) {
            if (s1.charAt(index) == s2.charAt(index)) {
                prefix += s1.charAt(index);
                index++;
            } else {
                sameLetter = false;
            }
        }
        return prefix;
    }


    /**
     * Finds the longest prefix in the tree for a given word
     *
     * @param word the string of the word we want a common prefix for
     * @return a string with the longest prefix of our tree and the word we input
     */
    public String getLongestPrefix(String word) {
        return getLongestPrefixHelper(root, word, "");
    }

    /**
     * Finds the last node in the tree that shares a common prefix  with the given word
     *
     * @param word the prefix to search for
     * @return a string with the longest prefix of our tree and the word we input
     */
    public Node getNodeOfPrefix(String word) {
        return getNodeOfPrefix(root, word);
    }

    public String getWords(String s) {
        return getWords(root, "");
    }
    // ---------- Private helper methods -----r----------

    /**
     * A private suggest method to find words that partial
     *
     * @param node the root of the tree
     * @param word the word to give suggestions on
     * @param numSuggestions the number of suggested words to return
    //     * @param common_prefix string to build of the longest common prefix
     * @return array of words
     */
    private String[] suggest(String word, Node node, int numSuggestions) {
        String[] suggestions = new String[numSuggestions];

        String commonPrefix = getLongestPrefix(word);
        if (commonPrefix.length() > 0) {
            commonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
        }
        Node nodeCommonPrefix = getNodeOfPrefix(word);
        String words = getWords(nodeCommonPrefix, "");
        String[] listOfWords = words.split(" ");

        if (listOfWords.length < numSuggestions) {

            word = word.substring(0, word.length() - 1);
            return suggest(word, numSuggestions);
        }
        else {
            for (int i = 0; i < numSuggestions; i++) {
                if (commonPrefix.charAt(commonPrefix.length()-1) == listOfWords[i].charAt(0)) {
                    char lastLetterCommonPrefix = commonPrefix.charAt(commonPrefix.length()-1);
                    commonPrefix = commonPrefix.substring(0, commonPrefix.length()-1);
                    suggestions[i] = commonPrefix.concat(listOfWords[i]);
                    commonPrefix = commonPrefix + lastLetterCommonPrefix;
                }
                else {
                    suggestions[i] = commonPrefix.concat(listOfWords[i]);
                }
            }
        }

        return suggestions;
    }




    /**
     * Helper method for getLongestPrefix to find the longest prefix in the tree for a given word
     *
     * @param node root of the tree
     * @param word the string of the word we want a common prefix for
     * @param commonPrefix the common prefix string to build
     * @return a string with the longest prefix of our tree and the word we input
     */
    private String getLongestPrefixHelper(Node node, String word, String commonPrefix) {
        String sharedPrefix = "";
        if (node == null) {
            return commonPrefix;
        }
        if (node != root) {
            if (node.prefix.length() < 1 || word.charAt(0) != node.prefix.charAt(0)) {
                return commonPrefix;
            }
        }
        sharedPrefix = findLongestCommonPrefix(word, node.prefix);
        commonPrefix = commonPrefix.concat(sharedPrefix);

        word = word.substring(sharedPrefix.length());

        if (word.length() < 1) {
            return commonPrefix;
        }
        else {
            int indexWord = getIndexOfChar(word.charAt(0));

            if (node.children[indexWord] != null) {
                commonPrefix = commonPrefix.concat(getLongestPrefixHelper(node.children[indexWord], word, ""));
            }
        }
        return commonPrefix;
    }

    /**
     * Helper method for getLongestPrefix to find the longest prefix in the tree for a given word
     *
     * @param node root of the tree
     * @param word the string of the word we want a common prefix for
     * @return the node with the longest prefix of our tree and the word we input
     */
    private Node getNodeOfPrefix(Node node, String word) {
        String sharedPrefix = "";
        if (node == null) {
            return null;
        }
        if (node != root) {

            sharedPrefix = findLongestCommonPrefix(word, node.prefix);

            word = word.substring(sharedPrefix.length());

            if (word.length() <= 1) {
                return node;
            } else {
                int indexPrefix = getIndexOfChar(word.charAt(0));

                return getNodeOfPrefix(node.children[indexPrefix], word);
            }
        }
        else {
            if (word.length() < 1) {
                return node;
            }
            else {
                int indexPrefix = getIndexOfChar(word.charAt(0));
                return getNodeOfPrefix(node.children[indexPrefix], word);
            }
        }
    }


    /**
     * A private add method that adds a given string to the tree
     *
     * @param s    the string to add
     * @param node the root of a tree where we want to add a new string
     * @return a reference to the root of the tree that contains s
     */
    private Node add(String s, Node node) {
        // base case 1: tree is empty at the index of the first letter of the new word
        int indexOfWord = getIndexOfChar(s.charAt(0));
        if (node == root && node.children[indexOfWord] == null) {
            Node newWord = new Node(s, true);
            node.children[indexOfWord] = newWord;
            return node;
        }
        else if (node.children[indexOfWord] == null) {
            Node newWord = new Node(s, true);
            node.children[indexOfWord] = newWord;
            return root;
        }
        else if (node.children[indexOfWord] != null) {
            // base case 2 & 3: the node.children[indexOfWord].prefix is in the dict --> set the node.children[indexOfWord].isWord = true
            if (node.children[indexOfWord].prefix.equals(s)) {
                node.children[indexOfWord].isWord = true;
                return root;
            }
            else {
                // base case 4: the word is not in the list
                String common_prefix = findLongestCommonPrefix(s, node.children[indexOfWord].prefix);
                String ogPrefix = node.children[indexOfWord].prefix;
                boolean ogIsWord = node.children[indexOfWord].isWord;
                if (!common_prefix.equals("")) {
                    Node newPrefix = new Node(common_prefix, false); // new node with prefix = the longest common prefix between the current node and String s

                    if (!common_prefix.equals(ogPrefix)) {
                        String suffix = ogPrefix.substring(common_prefix.length());
                        int indexOfSuffix = getIndexOfChar(suffix.charAt(0));

                        String suffixWord = s.substring(common_prefix.length());
                        int indexOfSuffixWord = getIndexOfChar(suffixWord.charAt(0));

                        node.children[indexOfWord].prefix = suffix;
                        newPrefix.children[indexOfSuffix] = node.children[indexOfWord];
                        newPrefix.children[indexOfSuffixWord] = new Node(suffixWord, true);
                        node.children[indexOfWord] = newPrefix;
                        return node;
                    }
                    else {
                        String remainingWord = s.substring(ogPrefix.length());
                        int indexRemainingWord = getIndexOfChar(remainingWord.charAt(0));
                        add(remainingWord, node.children[indexOfWord]);
                        return node;
                    }

                }
                else if (common_prefix.equals("")) {
                    node.children[indexOfWord].isWord = true;
                    return node;
                }
            }
        }
        return add(s, node);
    }

    /**
     * Private method to make the suffix of a given word
     * @param ogWord string of the original word
     * @param prefix the prefix of the word
     * @return the suffix of the word
     */
    private String makeSuffix(String prefix, String ogWord) {
        String suffix = ogWord.substring(prefix.length());
        return suffix;
    }

    /**
     * A private method to check whether a given string is stored in the tree.
     *
     * @param s    the string to check
     * @param node the root of a tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean check(String s, Node node) {
        if (node == null) {
            return false;
        }

        if (s.equals(node.prefix)) {
            if (node.isWord) {
                return true;
            }
            return false;
        }
        else {

            String commonPrefix = findLongestCommonPrefix(node.prefix, s);
            String suffix = s.substring(commonPrefix.length());

            if(suffix.length() < 1) {
                return false;
            }
            else {
                int charIndex = getIndexOfChar(suffix.charAt(0));
                return check(suffix, node.children[charIndex]);
            }
        }
    }

    /**
     * A private recursive method to check whether a given prefix is in the tree
     *
     * @param prefix the prefix to check
     * @param node   the root of the tree
     * @return true if the prefix is in the dictionary, false otherwise
     */
    private boolean checkPrefix(String prefix, Node node) {
        String sharedPrefix = "";
        if (node == null) {
            return false;
        }
        if (node != root) {

            sharedPrefix = findLongestCommonPrefix(prefix, node.prefix);

            prefix = prefix.substring(sharedPrefix.length());

            if (prefix.length() < 1) {
                return true;
            } else {
                int indexPrefix = getIndexOfChar(prefix.charAt(0));

                return checkPrefix(prefix, node.children[indexPrefix]);
            }
        }
        else {
            if (prefix.length() < 1) {
                return true;
            }
            else {
                int indexPrefix = getIndexOfChar(prefix.charAt(0));
                return checkPrefix(prefix, node.children[indexPrefix]);
            }
        }

    }

    /**
     * Outputs all the words stored in the dictionary
     * to the console, in alphabetical order, one word per line.
     *
     * @param s the string obtained by concatenating prefixes on the way to this node
     * @param node the root of the tree
     */
    private void print(String s, Node node) {
        if (node == null) {
            System.out.println(" ");
        }
        else {
            if (node.isWord) {
                System.out.println(s.concat(node.prefix));
            }

            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    print(s.concat(node.prefix), node.children[i]);
                }
            }
        }
    }

    /**
     * Private method to make a string of all words related to a given node
     * @param node the root of the tree
     * @return the string containing all words related to the prefix of node
     */
    public String getWords(Node node, String prefix) {
        String words = "";
        if (node == null) {
            words = words.concat(" ");
        }
        else {
            prefix = prefix.concat(node.prefix);
            if (node.isWord) {
                words = words.concat(prefix).concat(" ");
            }

            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    String str = getWords(node.children[i], prefix);
                    words = words.concat(str);
                }
            }
        }
        return words;
    }

    /**
     * Private helper method that makes a string with user specified number of spaces
     *
     * @param num number of spaces to add to the string
     * @return string with num spaces
     */
    private String indent(int num) {
        String spaces = "";
        for (int i = 0; i < 2*(num-1); i++) {
            spaces += " ";
        }
        return spaces;
    }

    /**
     * Private method that returns the string representation of the tree
     *
     * @param treeString original string to build
     * @param node the root of the tree
     * @param height the height of the tree
     * @return treeString the string representation of the tree
     */
    private String toString(String treeString,Node node, int height) {
        String s = "";
        if (node == null) {
            return "";
        }
        else {
            s = indent(height);
            s = s.concat(node.prefix);
            if (node.isWord) {
                s = s.concat("*");
            }
            s = s.concat(System.lineSeparator());
        }
        treeString = treeString.concat(s);

        for (int i = 0; i < node.children.length; i ++) {
            treeString = treeString.concat(toString("",node.children[i], height + 1));
        }

        return treeString;
    }


    // --------- Private class Node ------------
    // Represents a node in a compact prefix tree
    private class Node {
        String prefix; // prefix stored in the node
        Node children[]; // array of children (26 children)
        boolean isWord; // true if by concatenating all prefixes on the path from the root to this node, we get a valid word

        Node() {
            isWord = false;
            prefix = "";
            children = new Node[26]; // initialize the array of children
        }

        Node(String s, boolean validWord) {
            prefix = s;
            isWord = validWord;
            children = new Node[26];
        }

        Node(String s, boolean validWord, Node[] kids) {
            prefix = s;
            isWord = validWord;
            children = kids;
        }
    }

}