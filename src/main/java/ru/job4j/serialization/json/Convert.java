package ru.job4j.serialization.json;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class Convert {
    public static void main(String[] args) throws JAXBException, IOException {
        Auto auto = new Auto(true, 2, "red",
                new Number("x103cx"), "Transmission", "Drive");
        JAXBContext context = JAXBContext.newInstance(Auto.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(auto, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            Auto result = (Auto) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }
    }
}
