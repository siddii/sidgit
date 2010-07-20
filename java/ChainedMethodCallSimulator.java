import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class ChainedMethodCallSimulator {
  private static String mainClassHeader = "" +
    "public class $MAIN$ {\n" +
    "   public void main() {\n" +
    "   System.out.println(\"main\");\n" +
    "     method_0();\n" +
    "   }\n";


  public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
    try {
      String className = "ChainedMethodClass";

      File file = generateJavaFile(className, 7000);
      compileJavaFile(file);
      Class javaClass = loadClass(new File("."), className);
      Object obj = javaClass.newInstance();
      Method method = javaClass.getMethod("main");
      if (method != null) {
        method.setAccessible(true);
        method.invoke(obj);
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }


  private static void compileJavaFile(File file) throws IOException {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

    Iterable<? extends JavaFileObject> compilationUnits1 =
      fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file));
    compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
    fileManager.close();
  }

  private static File generateJavaFile(String javaClassName, int methodCount) throws IOException {
    File javaFile = new File(javaClassName + ".java");
    FileWriter fileWriter = new FileWriter(javaFile);
    fileWriter.write(mainClassHeader.replace("$MAIN$", javaClassName));
    for (int i = 0; i < methodCount; i++) {
      fileWriter.write("public void method_" + i + "(){\n");
      fileWriter.write("  System.out.println(\" I'm method " + i + " calling method " + (i + 1) + "!\");\n");
      if (i != methodCount - 1) {
        fileWriter.write(" method_" + (i + 1) + "();\n");
      }
      fileWriter.write("}\n");
    }
    fileWriter.write("}");
    fileWriter.flush();
    fileWriter.close();
    return javaFile;
  }

  private static Class loadClass(File rootDir, String fullyQualifiedClassName) {
    try {
      URL url = rootDir.toURI().toURL();
      URL[] urls = new URL[]{url};
      ClassLoader cl = new URLClassLoader(urls);
      return cl.loadClass(fullyQualifiedClassName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
