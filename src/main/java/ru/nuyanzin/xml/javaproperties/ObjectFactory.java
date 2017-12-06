//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.06 at 08:26:21 PM MSK 
//


package ru.nuyanzin.xml.javaproperties;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.nuyanzin.xml.javaproperties package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Properties_QNAME = new QName("", "properties");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.nuyanzin.xml.javaproperties
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PropertiesType }
     * 
     */
    public PropertiesType createPropertiesType() {
        return new PropertiesType();
    }

    /**
     * Create an instance of {@link PropertyType }
     * 
     */
    public PropertyType createPropertyType() {
        return new PropertyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertiesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "properties")
    public JAXBElement<PropertiesType> createProperties(PropertiesType value) {
        return new JAXBElement<PropertiesType>(_Properties_QNAME, PropertiesType.class, null, value);
    }

}
