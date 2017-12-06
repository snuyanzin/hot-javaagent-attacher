package ru.nuyanzin;

import ru.nuyanzin.xml.javaproperties.ObjectFactory;
import ru.nuyanzin.xml.javaproperties.PropertiesType;
import ru.nuyanzin.xml.javaproperties.PropertyType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import java.io.File;
import java.lang.instrument.Instrumentation;

public class JavaAgent
{
    private static Instrumentation inst;
    public static void premain(String args, Instrumentation inst) {
        if(JavaAgent.inst != null)
        {
            System.out.println("Looks like the client is attempted to be loaded twice. The second time will be ignored");
            return;
        }
        JavaAgent.inst = inst;
        setJavaProperties(args);
    }

    public static void agentmain(String args, Instrumentation inst) {
        if(JavaAgent.inst != null)
        {
            System.out.println("Looks like the client is attempted to be loaded twice. The second time will be ignored");
            return;
        }
        JavaAgent.inst = inst;
        setJavaProperties(args);
    }

    private static void setJavaProperties(String config)
    {
        for(PropertyType javaProperty: getJavaProperties(config).getProperty())
        {
            System.setProperty(javaProperty.getName(), javaProperty.getValue());
        }

    }

    private static PropertiesType getJavaProperties(String config)
    {
        try {
            File file = new File(config);
            if(!file.exists()) return null;  //not empty as it will be used for refill
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            PropertiesType javaProperties = ((JAXBElement<PropertiesType>) jaxbContext.createUnmarshaller().unmarshal(file)).getValue();

            return javaProperties;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
