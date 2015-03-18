import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by hczhang on 12/03/15.
 */

public class test {
    public static String getURLContent(String urlString, String encoding) {
        String line;
        StringBuffer content = new StringBuffer();
        try {

            URL url = new URL(urlString);

            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        catch (MalformedURLException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        return content.toString();
    }

    public static int subCounter(String str1, String str2) {
        int counter = 0;
        for (int i = 0; i <= str1.length() - str2.length(); i++) {
            if (str1.substring(i, i + str2.length()).equalsIgnoreCase(str2)) {
                if(!str1.substring(i + str2.length(), i + str2.length() + 1).matches("[a-zA-Z]*")) {
                    counter++;

                }
            }
        }
        return counter;

    }

    public static boolean isletter(char ch){
        if((ch >='A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')){
            return true;
        }else{
            return false;
        }
    }


   public static boolean isequal(char ch1, char ch2){

        if(ch1 == ch2 || ch1 == ch2 + 32 || ch1 == ch2 - 32){
            return true;
        }else{
            return false;
        }
   }


    public static int count(String str1, String str2) {
        int counter = 0;
        int i, j;
        char arr1[] = str1.toCharArray();
        char arr2[] = str2.toCharArray();
        for(i=0;i<arr1.length;i++){
            if(isequal(arr1[i], arr2[0])){
                for(j=0;j<str2.length();j++){
                    if(!isequal(arr1[i+j],arr2[j])){
                        break;
                    }
                }
                if(j==str2.length()&&!isletter(arr1[i+j])){
                    counter++;
                }
            }
        }

        return counter;

    }



    public static String outTag(String s) {
        s = s.replaceAll("<script>[\\s\\S]*?</script>", "");
        s = s.replaceAll("class=\"printfooter\"[\\s\\S]*?</div>", "");
        s = s.replaceAll("<[^>]+>", "");
        s = s.replaceAll("java.util.regex.Matcher","");

        return s;
    }


    public static String hideMatcher(String s) {
        String dist = null;
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("<table[^>]+autocollapse[^>]+>[\\s\\S]*?</table>");
        Matcher m = p.matcher(s);
        while(m.find()) {
            sb.append(m.toMatchResult());

            dist = sb.toString();
        }

        dist = dist.replaceAll("<div[^>]+font-size:110%[^>]+>[\\s\\S]*?</div>","");
        return dist;
    }


    public static void main(String[] args) {

        String asciiRegex = "^\\p{Alpha}+$";
        if(!args[1].matches(asciiRegex)) {
            System.out.println("The searched keywords should be an English word");
            System.exit(0);
        }

        String content, hideContent;
        int start;
        int end;
        content = getURLContent(args[0], "gb2312");
        start = content.indexOf("<body");
        end = content.indexOf("</body");
        System.out.println(start);
        System.out.println(end);
        content = content.substring(start, end);
        hideContent = hideMatcher(content);
        content = outTag(content);
        hideContent = outTag(hideContent);
        System.out.println("In hidden blocks, the number of "+args[1]+" is "+count(hideContent, args[1]));
        System.out.println("The number of "+args[1]+" is "+count(content, args[1]));
    }
}



