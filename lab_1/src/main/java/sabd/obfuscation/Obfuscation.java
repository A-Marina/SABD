package sabd.obfuscation;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import sabd.obfuscation.xml.Employee;
import sabd.obfuscation.xml.Employees;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;




public class Obfuscation {
    public static void writeToFile(String res, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(res);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static final String SOURCE="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final String TARGET="A17yCzqfjKnE3XkwebphaYl8P5i04BQdTVIGcSrUtWgsH6vLuZRDxo2NFJOmM9";

    public void obfuscation() {
        obfuscation("source_file.xml", "obfuscated.txt");
    }

    public void obfuscation(String fromFile, String toFile) {
        Employees employees = deserializeFromXML(fromFile);
        String res = "";
        try {
            res = obfuscateData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
        writeToFile(res, toFile);
    }

    private Employees deserializeFromXML(String file) {
        Employees employees = new Employees();
        try {
            XmlMapper xmlMapper = new XmlMapper();

            // read file and put contents into the string
            String readContent = new String(Files.readAllBytes(Paths.get(file)));

            // deserialize from the XML into a Employees object
            employees = xmlMapper.readValue(readContent, Employees.class);
        } catch (IOException e) {
            System.err.println("Error in deserializing XML");
            e.printStackTrace();
        }
        return employees;
    }

    private String obfuscateData(Employees employees) throws Exception {
        // create map where
        // key - sign from XML
        // List - encoded data from read data
        Map<String, List<String>> map = new HashMap<>();
        for (String key : Employee.getValues()) {
            map.put(key, new ArrayList<>());
            // put encoded sign value in list head
            map.get(key).add(encodeString(key));
        }

        for (Employee e : employees.getEmployeeList()) {
            for (String key : Employee.getValues()) {
                String encodedValue = encodeString(e.getValue(key));
                map.get(key).add(encodedValue);
            }
        }

        // collect all values
        StringBuilder stringBuilder = new StringBuilder();
        for(List<String> value : map.values()) {
            stringBuilder.append(String.join(".", value)).append('\n');
        }
        return stringBuilder.toString();
    }

    private String encodeString(String s) {
        return s.chars()
                .mapToObj(c -> encodeChar((char) c))
                .map(Objects::toString)
                .collect(Collectors.joining());
    }

    private char encodeChar(char c) {
        int index = SOURCE.indexOf(c);
        return TARGET.charAt(index);
    }
}
