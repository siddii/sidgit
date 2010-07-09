/**
 * Created by IntelliJ IDEA.
 * User: shameed
 * Date: Jul 9, 2010
 * Time: 2:41:18 PM
 */
public class StackOverflowLimitTest {

  private static int recursionLimit = 0;

  public static void main(String[] args) {
    System.out.println("recursionLimit++ = " + recursionLimit++);
    main(args);
  }
  
}
