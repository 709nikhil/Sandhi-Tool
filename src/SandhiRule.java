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
    private Pattern overallContext;

    private boolean optional;

    private String sandhiType;
    private String[] modification;

    SandhiRule(
            String id,
            String[] firstWordSuffix,
            String[] secondWordPrefix,
            Pattern leftContext,
            Pattern rightContext,
            Pattern overallContext,
            boolean optional,
            String sandhiType,
            String[] modification
    ) {
        this.id = id;

        this.firstWordSuffix = firstWordSuffix;
        this.secondWordPrefix = secondWordPrefix;

        this.leftContext = leftContext;
        this.rightContext = rightContext;
        this.overallContext = overallContext;

        this.optional = optional;

        this.sandhiType = sandhiType;
        this.modification = modification;
    }

    String[] applySandhi(String leftWord, String rightWord) {
        String output = this.modification[0];
        switch (this.sandhiType) {

            case "Aagam":
                return new String[] {leftWord + output + rightWord};
            case "Pre-Aagam":
                return new String[] {leftWord + output, rightWord};
            case "Post-Aagam":
                return new String[] {leftWord, output + rightWord};

            case "Pre-Aadesh":
                return new String[] {leftWord.substring(0, leftWord.length()-1) + output, rightWord};
            case "Post-Aadesh":
                return new String[] {leftWord, output + rightWord.substring(1)};

            case "Ekadesh":
                return new String[] {leftWord.substring(0, leftWord.length()-1) + output + rightWord.substring(1)};

            case "Pre-Elision":
                return new String[] {leftWord.substring(0, leftWord.length()-1) + rightWord};
            case "Post-Elision":
                return new String[] {leftWord + rightWord.substring(1)};

            case "Prakritibhaav":
                return new String[] {leftWord + rightWord};
        }
        return new String[]{};
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

    public String getId() {
        return id;
    }
}
