/** The Driver class for CompactPrefixTree */
public class Driver {
    public static void main(String[] args) {
//            Dictionary dict = new CompactPrefixTree();
//            dict.add("cat");
//            dict.add("cart");
//            dict.add("carts");
//            dict.add("case");
//            dict.add("doge");
//            dict.add("doghouse");
//            dict.add("wrist");
//            dict.add("wrath");
//            dict.add("wristle");
//            dict.print();

            // Add other "tests"
        /** ------------------------------------------------------ */
//        /** Testing print() &  check()  */
//        CompactPrefixTree tree = new CompactPrefixTree();
//        tree.createTestTree();
//        System.out.println("print() test:");
//        tree.print(); // actual: car, care, cart, dare, dared, dares, darted (, = a new line)
//        System.out.println();
//
        /** ------------------------------------------------------ */
//        System.out.println("check() test:");
//        System.out.println(tree.check("cared")); // actual: false
//        System.out.println(tree.check("car")); // actual: true
//        System.out.println();
//
////        System.out.println(tree.findLongestCommonPrefix("short", "shake")); // actual = "sh"
//
//////      tree.print();      // base 1: empty tree
//////        tree.createTestTree();     // set remove all nodes accept for root. Set root.prefix to word, change root.isWord to true
//////        tree.print();      // base 2: node is a valid word
//
        /** --------------------checkPrefix() test:--------------------------- */
//        System.out.println("checkPrefix() test:");
//        tree.printTree();
//        System.out.println(tree.checkPrefix("ca")); // actual: true
//        System.out.println(tree.checkPrefix("dr")); // actual: false
//        System.out.println(tree.checkPrefix("dart")); // actual: true

        /** ------------------add() test:---------------------------- */
        System.out.println("add() test: ");
        CompactPrefixTree dict = new CompactPrefixTree();
        dict.printTree();

            /* tests printTree(filename) */
        dict.printTree("testPrintTree.txt");

        System.out.println("------above tree is original tree-------");
//        System.out.println("------after adding dogs---------");
//        dict.add("dogs");
//        dict.printTree();
        System.out.println("-----after adding time--------");
        dict.add("time");
        dict.printTree();
        System.out.println("-----after adding tie-------");
        dict.add("tie");
        dict.printTree();
//        dict.print();
        System.out.println("-----final tree-----");

            /* base case 1: tree is empty */
//        dict.add("in"); // add "in" to empty list
//        dict.print(); // actual: in
//        System.out.println("---------");
//        dict.printTree(); // actual: in
//        dict.add("in"); // add "in" to list when "in" is already in list --> base case 2
//        dict.printTree(); // actual: in*

//        dict.createTestTree();
            /* base case 2: prefix is in tree but isWord = false --> set isWord = true */
//        dict.printTree(); // "dar" should be printed in tree as "dar"
//        dict.add("dar");  // add dar to list of words in dictionary
//        dict.printTree(); // "dar" should now be printed in tree as "dar*" because it is a valid word

            /* base case 3: prefix is in tree and isWord = true --> return tree */
//        dict.add("dare"); // add "dare" to list of words in dictionary
//        dict.printTree();  // "dare" should be printed in tree as "dare*" because it was a valid word before we added it again

            /* base case 4 & recursive case */
//        dict.add("apple");
//        dict.print();
//        System.out.println("--------------");
////        dict.printTree(); // actual: apple*, in*
//        dict.add("ape");
//        dict.printTree();
//        dict.print();

        /** ----------------------setIsWord() test:--------------------- */
//        System.out.println("setIsWord() test: ");
//        CompactPrefixTree aDict = new CompactPrefixTree();
//        aDict.createTestTree();
//        aDict.setIsWord("dar", true);
//        aDict.print();
//        aDict.printTree();

    }
}
