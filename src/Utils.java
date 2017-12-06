import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Nikhil on 06/11/17.
 */
class Utils {
    static String convertSLPtoDev(String input) {
        try {
            Runtime rt = Runtime.getRuntime();

            Process pr = rt.exec("./transcriber 6 0 " + input + "");
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String output = br.lines().collect(Collectors.joining());
            pr.waitFor();

            return output;
        } catch (Exception e) {
            System.out.println("Some error in running Transcriber!");
        }
        return "";
    }

    static String convertDevtoSLP(String input) {
        try {
            Runtime rt = Runtime.getRuntime();

            Process pr = rt.exec("./transcriber 0 6 " + input);
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String output = br.lines().collect(Collectors.joining());
            pr.waitFor();

            return output;
        } catch (Exception e) {
            System.out.println("Some error in running Transcriber!");
        }
        return "";
    }

    static String printSandhi(String[] input, String[] output) {
        String result = convertSLPtoDev(input[0]) + " + " + convertSLPtoDev(input[1]) + " -> ";
        if (output.length == 1)
            result += convertSLPtoDev(output[0]);
        else if (output.length == 2)
            result += convertSLPtoDev(output[0]) + " + " + convertSLPtoDev(output[1]);
        return result;
    }
}
