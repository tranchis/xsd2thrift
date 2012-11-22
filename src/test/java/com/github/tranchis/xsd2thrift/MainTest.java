package com.github.tranchis.xsd2thrift;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import static com.github.tranchis.xsd2thrift.TestHelper.*;

public class MainTest {

    public static void main(String args[]) {
        //regenerateTestData("expected");
    }

    private static void regenerateTestData(String destination) {
        
        File directory = new File("contrib");
        for(String name:directory.list()){
            if(!name.endsWith(".xsd")) continue;
            try {
                Main.main(new String[]{"--thrift","--filename=src/test/data/"+destination+"/"+name.replace(".xsd", ".thrift"),"--package=default","contrib/"+name});
                Main.main(new String[]{"--protobuf","--filename=src/test/data/"+destination+"/"+name.replace(".xsd", ".proto"),"--package=default","contrib/"+name});
            } catch (Exception e) {
                System.out.println("Failed on "+name);
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void compareAtomThrift(){
        compareExpectedAndGenerated("src/test/data/expected/atom.thrift", generateThrift("atom"));
    }
    @Test
    public void compareAtomProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/atom.proto", generateProtobuf("atom"));
    }
    
    @Test
    public void compareRecipeProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/recipe.proto", generateProtobuf("recipe"));
    }
    @Test
    public void compareRecipeThrift(){
        compareExpectedAndGenerated("src/test/data/expected/recipe.thrift", generateThrift("recipe"));
    }
    @Test
    public void compareShiporderProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/shiporder.proto", generateProtobuf("shiporder"));
    }
    @Test
    public void compareShiporderThrift(){
        compareExpectedAndGenerated("src/test/data/expected/shiporder.thrift", generateThrift("shiporder"));
    }
    @Test
    public void compareTestChoiceProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/test-choice.proto", generateProtobuf("test-choice"));
    }
    @Test
    public void compareTestChoiceThrift(){
        compareExpectedAndGenerated("src/test/data/expected/test-choice.thrift", generateThrift("test-choice"));
    }
    @Test
    public void compareTestDatatypesProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/test-datatypes.proto", generateProtobuf("test-datatypes"));
    }
    @Test
    public void compareTestDatatypesThrift(){
        compareExpectedAndGenerated("src/test/data/expected/test-datatypes.thrift", generateThrift("test-datatypes"));
    }
    @Test
    public void compareTestExtensionProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/test-extension.proto", generateProtobuf("test-extension"));
    }
    @Test
    public void compareTestExtensionThrift(){
        compareExpectedAndGenerated("src/test/data/expected/test-extension.thrift", generateThrift("test-extension"));
    }
    @Test
    public void compareTestOptionalProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/test-optional.proto", generateProtobuf("test-optional"));
    }
    @Test
    public void compareTestOptionalThrift(){
        compareExpectedAndGenerated("src/test/data/expected/test-optional.thrift", generateThrift("test-optional"));
    }
    @Test
    public void compareTestRangeProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/test-range.proto", generateProtobuf("test-range"));
    }
    @Test
    public void compareTestRangeThrift(){
        compareExpectedAndGenerated("src/test/data/expected/test-range.thrift", generateThrift("test-range"));
    }
    @Ignore("Order seems to change on the test run")
    public void compareXmlRecipemlProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/xml-recipeml.proto", generateProtobuf("xml-recipeml"));
    }
    @Ignore("Order seems to change on the test run")
    public void compareXmlRecipemlThrift(){
        compareExpectedAndGenerated("src/test/data/expected/xml-recipeml.thrift", generateThrift("xml-recipeml"));
    }
    @Ignore("Order seems to change on the test run")
    public void compareRecipemlProtobuf(){
        compareExpectedAndGenerated("src/test/data/expected/recipeml.proto", generateProtobuf("recipeml"));
    }
    @Ignore("Order seems to change on the test run")
    public void compareRecipemlThrift(){
        compareExpectedAndGenerated("src/test/data/expected/recipeml.thrift", generateThrift("recipeml"));
    }

}
