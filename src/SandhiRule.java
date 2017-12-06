import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Nikhil on 22/10/17.
 */

class SandhiRule {
    private String id;
    private String[] firstWordSuffix;
    private String[] secondWordPrefix;

    private Pattern leftContext;
    private Pattern rightContext;

    private String sandhiType;
    private String[] modification;

    SandhiRule(
            String id,
            String[] firstWordSuffix,
            String[] secondWordPrefix,
            Pattern leftContext,
            Pattern rightContext,
            String sandhiType,
            String[] modification
    ) {
        this.id = id;

        this.firstWordSuffix = firstWordSuffix;
        this.secondWordPrefix = secondWordPrefix;

        this.leftContext = leftContext;
        this.rightContext = rightContext;

        this.sandhiType = sandhiType;
        this.modification = modification;
    }

    String applySandhi(String leftWord, String rightWord) {
        String output = "";

        if (this.modification.length == 1)
            output = modification[0];
        else if (this.modification.length == 0)
            output = "";
        else if (this.sandhiType.startsWith("Pre")) {
            for (int i = 0; i < firstWordSuffix.length; i++) {
                if (leftWord.endsWith(firstWordSuffix[i])) {
                    output = modification[i];
                    break;
                }
            }
        }
        else if (this.sandhiType.startsWith("Post")) {
            for (int i = 0; i < secondWordPrefix.length; i++) {
                if (rightWord.startsWith(secondWordPrefix[i])) {
                    output = modification[i];
                    break;
                }
            }
        }

        if (this.id.equals("8.3.60") || this.id.equals("8.3.80")) {
            switch (rightWord.charAt(1)) {
                case 't' : rightWord = rightWord.replaceFirst("t", "w");
                    break;
                case 'T' : rightWord = rightWord.replaceFirst("T", "W");
                    break;
                case 'd' : rightWord = rightWord.replaceFirst("d", "q");
                    break;
                case 'D' : rightWord = rightWord.replaceFirst("D", "Q");
                    break;
            }
        }

        switch (this.sandhiType) {

            case "Pre-Aagam":
                return leftWord + output + rightWord;
            case "Post-Aagam":
                return leftWord + output + rightWord;

            case "Pre-Aadesh":
                return leftWord.substring(0, leftWord.length()-1) + output + rightWord;
            case "Post-Aadesh":
                return leftWord + output + rightWord.substring(1);

            case "Pre-Ekadesh":
                return leftWord.substring(0, leftWord.length()-1) + output + rightWord.substring(1);
            case "Post-Ekadesh":
                return leftWord.substring(0, leftWord.length()-1) + output + rightWord.substring(1);

            case "Pre-Elision":
                return leftWord.substring(0, leftWord.length()-1) + rightWord;
            case "Post-Elision":
                return leftWord + rightWord.substring(1);

            case "Prakritibhaav":
                return leftWord + rightWord;
        }
        return "";
    }

    boolean checkIfSandhiApplicable(String leftWord, String rightWord) {

        if (!isLeftWordSuffix(leftWord))
            return false;

        if (!isRightWordPrefix(rightWord))
            return false;

        if (leftContext != null && !leftContext.matcher(leftWord).matches())
            return false;

        if (rightContext != null && !rightContext.matcher(rightWord).matches())
            return false;

        return true;
    }

    private boolean isLeftWordSuffix(String word) {
        for(String suffix: firstWordSuffix) {
            if (word.endsWith(suffix))
                return true;
        }
        return false;
    }

    private boolean isRightWordPrefix(String word) {
        for(String prefix: secondWordPrefix) {
            if (word.startsWith(prefix))
                return true;
        }
        return false;
    }

    String getId() {
        return id;
    }
}
