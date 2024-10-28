package demo2;

public class Main {

    public static void main(String[] args) {
        executeMain(args);
    }
    public static void executeMain(String[] args) {

        Input input = new Input();
        input.input("D:\\txt\\input.txt");
        Shift shift = new Shift(input.getLineTxt());
        shift.shift();
        Alphabetizer alphabetizer = new Alphabetizer(shift.getKwicList());
        alphabetizer.sort();
        Output output = new Output(alphabetizer.getKwicList());
        output.output("D:\\txt\\output.txt");

    }
}