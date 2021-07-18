package net.plixo.paper.client.manager;

import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.script.*;
import javax.tools.*;
//import javax.script.*;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptManager {

    public static ScriptEngine getNewEngine() {
        return new ScriptEngineManager(null).getEngineByName("Nashorn");
    }

    public static Object createClass(String jarPath, String jarCode, Object... objects) throws Exception {
        Path temp = Paths.get(System.getProperty("java.io.tmpdir") + "/VisualScript", jarPath);
        Files.createDirectories(temp);
        Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), jarPath + ".java");
        System.out.println("The java source file is located at " + javaSourceFile);
        Files.write(javaSourceFile, jarCode.getBytes());

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
            long line = diagnostic.getLineNumber();
            if (line >= 0)
                Util.print("Error on line " + line + " in " + diagnostic.getSource());
        }

        fileManager.close();

        try {
            ClassLoader classLoader = ScriptManager.class.getClassLoader();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{temp.toUri().toURL()}, classLoader);
            Class javaDemoClass = urlClassLoader.loadClass(jarPath);
            Object runnable = javaDemoClass.getConstructors()[0].newInstance(objects);
            return runnable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteTemp() {
        File file = new File(System.getProperty("java.io.tmpdir") + "/VisualScript");
        System.out.println("temp path: " + file.getAbsolutePath());
        if(!file.exists()) {
            return;
        }
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(listFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object loadClassFromFile(File file) {
        String name = FilenameUtils.removeExtension(file.getName());
        String code = SaveUtil.loadAsString(file.getAbsolutePath());
        try {
            return createClass(name, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
