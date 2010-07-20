public class StackOverflowLimitTest {

  private static int recursionLimit = 0;

  public static void main(String[] args) {
    try {
      recursionLimit++;
      main(args);
    } catch (StackOverflowError e) {
      System.out.println("recursionLimit = " + recursionLimit);
    }
  }
}
