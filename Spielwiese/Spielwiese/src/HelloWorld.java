public class HelloWorld {

    private static final int MAX_ANZAHL = 10;

    public static void main(String[] args) {
//        System.out.println(pruefeZahle(4));
//        System.out.println(pruefeZahle(5));

        PositionCoordinate p1 = new PositionCoordinate(1, 2);
        PositionCoordinate p2 = new PositionCoordinate(1, 2);


        if (p1.equals(p2)) {
            System.out.println("Beide Strings sind gleich.");
        }
        else {
            System.out.println("Beide Strings sind ungleich.");
        }

//        print10Times("Hello World: ");
//        print10Times("Say Goodbye: ");
    }

    private static void print10Times(String msg) {
        for (int i = 0; i < MAX_ANZAHL; i++) {
            System.out.println(msg + i);
        }
    }

    private static String pruefeZahle(int zahl) {
        String result;
        int rest = zahl % 2;
        if (rest == 0) {
            result = "Die Zahl ist gerade.";
        }
        else {
            result = "Die Zahl ist ungerade.";
        }
        return result;
    }
}
