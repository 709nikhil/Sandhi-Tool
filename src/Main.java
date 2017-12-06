import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws Exception {

        RuleList ruleList = new RuleList("sandhiRules.tsv");

        //String[] input = new String[] {Utils.convertDevtoSLP(args[0]), Utils.convertDevtoSLP(args[1])};
        String[] input = new String[] {args[0], args[1]};
        //input = new String[] {"antar", "ayanam"};
        String result = ruleList.performSandhi(input);

        System.out.print("FINAL OUTPUT: ");
        if (result == null)
            System.out.println(Utils.printSandhi(input, new String[]{}) + "No Sandhi Possible!");
        else
            System.out.println(Utils.printSandhi(input, new String[]{result}));
    }
}