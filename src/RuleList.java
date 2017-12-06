/**
 * Created by Nikhil on 22/10/17.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

class RuleList {
    private ArrayList<SandhiRule> rules;

    String performSandhi(String[] words) {

        String word1 = words[0];
        String word2 = words[1];

        Scanner sc = new Scanner(System.in);
        ArrayList<SandhiRule> rules = checkIfRuleApplicable(word1, word2);

        int locR = word1.indexOf("r");
        int locZ = word1.indexOf("z");
        int locN = word2.indexOf("n");
        boolean lastN = locN == word2.length() - 1;
        char nextN = (lastN) ? ' ' : word2.charAt(locN + 1);
        boolean nextNTU = nextN == 't' || nextN == 'T' || nextN == 'd' || nextN == 'D';
        if ((locR != - 1 && locN != -1 && !lastN && !nextNTU) || (locZ != - 1 && locN != -1 && !lastN && !nextNTU))
            word2 = word2.replaceFirst("n", "R");

        if (word2.equals("us"))
            word2 = "uH";

        if (rules.isEmpty()) {
            String swar = "aAiIoOuUeEfFxX";
            String vyanjana = "kKgGNcCjJYwWqQRtTdDnpPbBmyrlvSzsh";

            int endsWithSwar = swar.indexOf(word1.charAt(word1.length()-1));
            int endsWithVyanjana = vyanjana.indexOf(word1.charAt(word1.length()-1));

            int startsWithSwar = swar.indexOf(word2.charAt(0));
            int startsWithVyanjana = vyanjana.indexOf(word2.charAt(0));

            if (endsWithSwar != -1 && startsWithVyanjana != -1)
                return word1 + word2;

            if (endsWithVyanjana != -1 && startsWithSwar != -1)
                return word1 + word2;

            return word1 + "_" + word2;
        }

        if (rules.size() == 1) {
            return rules.get(0).applySandhi(word1, word2);
        }

        System.out.println(Utils.convertSLPtoDev(words[0]) + " + " + Utils.convertSLPtoDev(words[1]) + " ->");
        for (int i = 0; i < rules.size(); i++) {
            SandhiRule rule = rules.get(i);
            String output = rule.applySandhi(word1, word2);
            System.out.println((i+1) + ") " + Utils.convertSLPtoDev(output) + "(" + rule.getId() + ")");
        }
        System.out.println();

        int choice = 0;
        while (choice <= 0 || choice > rules.size()) {
            System.out.print("Please select the Sandhi you wish to perform: ");

            /*while (!sc.hasNextInt())
                sc.next();

            choice = sc.nextInt();*/
            choice = rules.size();
            System.out.println();
        }

        SandhiRule rule = rules.get(choice - 1);

        return rule.applySandhi(word1, word2);
    }

    private ArrayList<SandhiRule> checkIfRuleApplicable(String leftWord, String rightWord) {
        ArrayList<SandhiRule> rules = new ArrayList<>();
        for (SandhiRule rule : this.rules) {
            if (rule.checkIfSandhiApplicable(leftWord, rightWord))
                rules.add(rule);
        }
        return rules;
    }

    RuleList(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        rules = new ArrayList<>();

        while (sc.hasNextLine()) {
            String[] rule = sc.nextLine().split("\\t", -1);

            //System.out.println(rule[0] + " " + rule.length);

            Pattern leftContext = (rule[5].isEmpty())? null: Pattern.compile(rule[5]);
            Pattern rightContext = (rule[6].isEmpty())? null: Pattern.compile(rule[6]);

            rules.add(new SandhiRule(
                    rule[0],
                    rule[1].split(" "),
                    rule[2].split(" "),
                    leftContext,
                    rightContext,
                    rule[3],
                    rule[4].split(" ")
            ));
        }
    }
}
