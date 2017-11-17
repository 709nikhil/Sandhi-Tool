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
        boolean sandhiDone = false;

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Checking for Possible Sandhi Rules...\n");
            ArrayList<SandhiRule> rules = checkIfRuleApplicable(words[0], words[1]);

            if (rules.isEmpty()) {
                if (sandhiDone) {
                    System.out.println("No further possible Sandhi found!\n\n");
                    return words[0] + words[1];
                } else {
                    System.out.println("No possible Sandhi found!\n\n");
                    return null;
                }
            }

            System.out.println("The following Sandhis are possible:");
            for (int i = 0; i < rules.size(); i++) {
                SandhiRule rule = rules.get(i);
                String[] output = rule.applySandhi(words[0], words[1]);
                System.out.println((i+1) + ") " + rule.getId() + ": " + Utils.printSandhi(words, output));
            }
            System.out.println();

            int choice = 0;
            while (choice <= 0 || choice > rules.size()) {
                System.out.print("Please select the Sandhi you wish to perform: ");

                while (!sc.hasNextInt())
                    sc.next();

                choice = sc.nextInt();
                System.out.println();
            }

            SandhiRule rule = rules.get(choice - 1);
            System.out.println("Performing Sandhi " + rule.getId() + "...");
            String[] output = rule.applySandhi(words[0], words[1]);
            System.out.println(Utils.printSandhi(words, output));
            System.out.println();
            System.out.println();

            sandhiDone = true;

            if (output.length == 1)
                return output[0];

            words = output;
        }
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
            String[] rule = sc.nextLine().split("\\t");

            Pattern leftContext = (rule[3].isEmpty())? null: Pattern.compile(rule[3]);
            Pattern rightContext = (rule[4].isEmpty())? null: Pattern.compile(rule[4]);
            Pattern overallContext = (rule[5].isEmpty())? null: Pattern.compile(rule[5]);

            rules.add(new SandhiRule(
                    rule[0],
                    rule[1].split(";"),
                    rule[2].split(";"),
                    leftContext,
                    rightContext,
                    overallContext,
                    rule[8].equals("TRUE"),
                    rule[6],
                    rule[7].split(";")
            ));
        }
    }
}
