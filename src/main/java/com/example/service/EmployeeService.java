package com.example.service;

import com.example.entity.Employee;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.soap.MTOM;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@WebService
@MTOM
public class EmployeeService {
    @WebMethod
    public Employee getEmployee(@WebParam(name = "fileXML") String fileXML) throws IOException {
        Path filePath = Path.of("employees/" + fileXML);
        byte[] xmlBytes = Files.readAllBytes(filePath);

        return xmlScraper(xmlBytes);
    }

    private Employee xmlScraper(byte[] xmlBytes) throws IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlBytes));
            Element car = doc.getDocumentElement();
            String name = car.getElementsByTagName("Name")
                    .item(0).getTextContent();
            String position = car.getElementsByTagName("Position")
                    .item(0).getTextContent();
            String department = car.getElementsByTagName("Department")
                    .item(0).getTextContent();
            int salary = Integer.parseInt(car.getElementsByTagName("Salary")
                    .item(0).getTextContent());

            return new Employee(name, position, department, salary);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
