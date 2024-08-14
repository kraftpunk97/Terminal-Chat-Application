public class Screen {
    synchronized void cout(String str) { System.out.println(str); }
    synchronized void cout(int num) { System.out.println(num); }

    synchronized void cerr(String str) { System.err.println(str); }
}
