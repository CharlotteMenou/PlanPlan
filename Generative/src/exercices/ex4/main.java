package exercices.ex4;

public class main {
    public static void main(String[] args) {

        // XML to Class
        AnalyseurXmlToClass analyseurXmlToClass = new AnalyseurXmlToClass();
        analyseurXmlToClass.parseXMLFile("src/exercices/ex4/test.xml");

        // Class to XML
        AnalyseurClassToXml analyseurClassToXml = new AnalyseurClassToXml(analyseurXmlToClass.getAInstances(), analyseurXmlToClass.getbInstances());
        analyseurClassToXml.generateXML();
    }
}
