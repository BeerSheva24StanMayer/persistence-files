package telran.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeCommentsSeparation {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                "Fill all the obligatory fields: inputFile outputsComments outputCode");
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            Writer output_comments = new BufferedWriter(new FileWriter(args[1]));
            Writer output_code = new BufferedWriter(new FileWriter(args[2]));
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("//")) {
                    commentCodeSplitter(line, output_comments, output_code);
                }
                else if (!line.startsWith("\n")){
                    output_code.append(line + "\n");
                }
                
            }
            reader.close();
            output_comments.close();
            output_code.close();
        }

    }

    private static void commentCodeSplitter(String line, Writer output_comments, Writer output_code) throws IOException {
        String pattern = "^ *(?<comment>\\/\\/.*$)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        if(m.find()) {
            output_comments.append(m.group("comment") + "\n");
        }
        else {
            output_code.append(line + "\n");
        }
    }

}