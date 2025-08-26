public class Scope {
    public static double footToMeter(int foot) {
        double meter = foot * 0.305;
        return meter;
    }

    public static double meterToFoot(int meter) {
        double foot = meter * 3.279;
        return foot;
    }

    public static void main(String[] args) {
        System.out.println("Feet  Meters       Meters        Feet");
        for (int i = 1; i < 40; i++) 
        { System.out.print("-");}
        int meter = 20;
        for (int foot = 1; foot <= 10; foot++) {
            System.out.printf("\n%-8d %-10.3f   %-10d %.3f",foot,footToMeter(foot),meter,meterToFoot(meter));
            meter += 5;
        }
    }
}