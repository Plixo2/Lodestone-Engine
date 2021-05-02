package net.plixo.paper.client.manager;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.engine.behaviors.Java_Addon;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FilenameUtils;

import javax.script.*;
import javax.tools.*;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScriptManager {

     static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    public static ScriptEngine getNewEngine() {
        return scriptEngineManager.getEngineByName("nashorn");
    }

    public static boolean setup(File file, ScriptEngine engine) {
        try {
            engine.eval("var util = Java.type(\"net.plixo.paper.client.util.Util\");");
            engine.eval("var mc = util.mc;");
            engine.eval(new FileReader(file));
            return true;
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        return false;
    }

    public static Object findIgnoreCase(String name, ScriptEngine engine) {
        Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        for (String str : b.keySet()) {
            if (str.equalsIgnoreCase(name)) {
                return b.get(str);
            }
        }
        return null;
    }

    public static Object find(String name, ScriptEngine engine) {
        try {
            return engine.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> getAllBindings(ScriptEngine engine) {
        Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        List<Object> list = new ArrayList<>();
        for (String str : b.keySet()) {
            list.add(b.get(str));
        }
        return list;
    }

    public static Object invokeFunction(String name, ScriptEngine engine, Object... objs) {
        try {
            Invocable invocable = (Invocable) engine;
            Object obj = find(name, engine);
            if (obj == null) {
                return null;
            }
            return invocable.invokeFunction(name, objs);
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        return null;
    }

    public static Object createClass(String JarPath, String JarCode , Object... objects) throws Exception {
        String className = JarPath;
        Path temp = Paths.get(System.getProperty("java.io.tmpdir"), className);
        Files.createDirectories(temp);
        File f = temp.toFile();
        Util.print("FIle"+f);
        for (File file : f.listFiles()) {
            if(file.isFile() && FilenameUtils.getBaseName(file.getAbsolutePath()).endsWith("class")) {
                file.delete();
                Util.print(file);
            }
        }
        Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
        System.out.println("The java source file is loacted at " + javaSourceFile);
        String code = JarCode;
        Files.write(javaSourceFile, code.getBytes());

        final String toolsJarFileName = "tools.jar";
        final String javaHome = System.getProperty("java.home");
        Path toolsJarFilePath = Paths.get(javaHome, "lib", toolsJarFileName);
        if (!Files.exists(toolsJarFilePath)) {
            System.out.println("The tools jar file (" + toolsJarFileName + ") could not be found at (" + toolsJarFilePath + ").");
        }


        File[] files1 = {javaSourceFile.toFile()};

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files1));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                null, compilationUnits
        );
        task.call();

        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.format("Error on line %d in %s%n",
                    diagnostic.getLineNumber(),
                    diagnostic.getSource());
            Util.print("Error on line "+ diagnostic.getLineNumber()+" in " + diagnostic.getSource());
        }

        fileManager.close();

        try {
            ClassLoader classLoader = ScriptManager.class.getClassLoader();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{temp.toUri().toURL()}, classLoader);
            Class javaDemoClass = urlClassLoader.loadClass(className);
            Object runnable = javaDemoClass.getConstructors()[0].newInstance(objects);
            return runnable;
        } catch (Exception e) {
          Util.print(e);
          e.printStackTrace();
        }
        return null;
    }

    public static Object loadClassFromFile(File file) {
        String name = FilenameUtils.removeExtension(file.getName());
        String code = SaveUtil.loadAsString(file.getAbsolutePath());
        try {
            return createClass(name,code);
        } catch (Exception e) {
          Util.print(e);
        }
        return null;
    }
}
