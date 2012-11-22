package com.github.tranchis.xsd2thrift;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class TestHelper {

    public static void compareExpectedAndGenerated(String expected,String generated){
        try {
            List<String> exlines = linesFromFile(expected);
            List<String> genlines = linesFromFile(generated);
            
            for(int i=0;i<exlines.size();i++){
                String exline = exlines.get(i);
                String genline = null;
                if(genlines.size()>i) genline = genlines.get(i);
                Assert.assertEquals("Unexpected difference between "+expected+" and "+generated+" on line "+i, exline, genline);
            }
        } catch (Exception e){
            Assert.fail("Failure while comparing "+expected+" and "+generated);
            e.printStackTrace();
        }
    }
    public static String generateThrift(String name) {
        return generate(name,"thrift","thrift");
    }
    public static String generateProtobuf(String name) {
        return generate(name,"protobuf","proto");
    }
    private static String generate(String name,String type,String extension) {
        File dir = new File("src/test/data/actual/");
        if(!dir.exists())dir.mkdir();
        String filename = "src/test/data/actual/"+name+"."+extension;
        try {
            Main.main(new String[]{"--"+type,"--filename="+filename,"--package=default","contrib/"+name+".xsd"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    private static List<String> linesFromFile(String file) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while((line = reader.readLine())!=null){
            lines.add(line);
        }
        return lines;
    }

}
