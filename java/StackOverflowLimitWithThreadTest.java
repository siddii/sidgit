public class StackOverflowLimitWithThreadTest {
  private static int recursionLimit = 0;
  public static void main(final String[] args) {
    new Thread(new Runnable(){
      public void run() {
        System.out.println("recursionLimit++ = " + recursionLimit++);
        main(args);
      }
    }).start();
  }
}