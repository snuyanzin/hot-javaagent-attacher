package ru.nuyanzin;

import ru.nuyanzin.xml.agents.AgentsType;
import ru.nuyanzin.xml.agents.ObjectFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Arrays;

public class ClientLoader {
    private static final String LOAD_AGENT = "loadAgent";
    private static final String PROPERTIES_CONFIG_XML = "PropertiesConfig.xml";
    private static final String AGENTS_XML = "Agents.xml";

    public static void main(String[] args) throws Exception {

        URLClassLoader loader = getClassLoader();
        Class<?> utilityClass = loader.loadClass("com.sun.tools.attach.VirtualMachine");

        if (args.length < 1 || !isNumeric(args[0])) {
            throw new RuntimeException("Please specify correct PID instead of "
                    + (args.length > 0 ? args[0] : Arrays.toString(args)));
        }

        Object vm = utilityClass.getMethod("attach", String.class).invoke(null, args[0]);
        File currentJar = new File(ClientLoader.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath());
        utilityClass.getMethod(LOAD_AGENT, String.class).invoke(vm,
                currentJar.getAbsolutePath() + "=" + currentJar.getParentFile().getAbsolutePath()
                        + File.separator + PROPERTIES_CONFIG_XML);
        AgentsType agentsType = getAgents(AGENTS_XML);
        if (agentsType == null || agentsType.getAgent() == null || agentsType.getAgent().isEmpty()) {
            System.out.println("No agents to load");
            return;
        }
        for (String agent : agentsType.getAgent()) {
            utilityClass.getMethod(LOAD_AGENT, String.class).invoke(vm, agent);
        }
    }

    private static URLClassLoader getClassLoader() throws IOException {
        File javaHome = new File(System.getProperty("java.home"));
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        if (javaCompiler == null) {
            System.out.println("Please specify path to jdk installed");
            System.exit(1);
        }
        String toolsPath = (javaHome.getName().equalsIgnoreCase("jre")
                ? ".." + File.separator : "") + "lib" + File.separator + "tools.jar";

        URL[] urls = new URL[]{
                ClientLoader.class.getProtectionDomain().getCodeSource().getLocation(),
                new File(javaHome, toolsPath).getCanonicalFile().toURI().toURL(),
        };

        return new URLClassLoader(urls, null);
    }

    private static AgentsType getAgents(String config) {
        try {
            File file = new File(config);
            if (!file.exists()) return null;  //not empty as it will be used for refill
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            return ((JAXBElement<AgentsType>) jaxbContext.createUnmarshaller().unmarshal(file)).getValue();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        System.out.println(pos.getIndex());
        return str.length() == pos.getIndex();
    }
}
