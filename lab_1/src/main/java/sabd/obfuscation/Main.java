package sabd.obfuscation;

/**
Jackson XML parser articlesOBFUSCATION
1) https://mincong.io/2019/03/19/jackson-xml-mapper/
2) https://stackabuse.com/serialize-and-deserialize-xml-in-java-with-jackson/
*/

public class Main {

    public static void main(String[] args) throws IllegalStateException {
        String parameter = System.getenv("parameter");
        if(parameter.equals("obfuscation")){
            Obfuscation obfuscation = new Obfuscation();
            obfuscation.obfuscation();
            System.out.println("obfuscation - success");
        }

        if (parameter.equals("deobfuscation")){
            Deobfuscation deobfuscation = new Deobfuscation();
            deobfuscation.deobfuscation();
            System.out.println("deobfuscation - success");
        }
    }
}
