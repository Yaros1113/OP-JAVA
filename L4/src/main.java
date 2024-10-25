public class main
{
    String input_string;
    public static void main(final String[] args) throws InterruptedException {
        new main();
    }

    public main()
    {
        input_string1 = "ФИО, дд.мм.гг, место";
        input_string2 = "ФИО, дд.мм.гг, место";

        System.out.println("string <" + input_string1 "> correct = " + isCorrect(input_string1));
        System.out.println("string <" + input_string2 "> correct = " + isCorrect(input_string2));
    }

    static bool isCorrect(String str)
    {
        Pattern p1 = Pattern.compile("w.s");
        Matcher m1 = p1.matcher(str);
        if (m1.find()) { System.out.println("Found: " + m1.group());
        }

        Pattern p2 = Pattern.compile("w[abc]s");
        Matcher m2 = p2.matcher(str);
        if (m2.find()) { System.out.println("Found: " + m2.group());
        }

        Pattern p3 = Pattern.compile("t[^eou]mes");
        Matcher m3 = p3.matcher(str);
        if (m3.find()) {
            System.out.println("Found: " + m3.group());
        }
    }
}