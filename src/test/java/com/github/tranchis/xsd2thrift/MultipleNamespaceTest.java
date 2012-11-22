package com.github.tranchis.xsd2thrift;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.github.tranchis.xsd2thrift.TestHelper.*;

public class MultipleNamespaceTest {
    
    /*public static void main(String args[]){
        try {
            Main.main(new String[]{"--thrift","--splitBySchema=true","--directory=src/test/data/expected/","--package=schemas.com.domain.common","contrib/ns-person.xsd"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    
    @BeforeClass
    public static void generateThriftForTests(){
        try {
            Main.main(new String[]{"--thrift","--splitBySchema=true","--directory=src/test/data/actual/","--package=schemas.com.domain.common","contrib/ns-person.xsd"});
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void shouldCreateANamespacedThriftPersonFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas.com.domain.person.thrift",
                "src/test/data/actual/schemas.com.domain.person.thrift");
    }
    @Test
    public void shouldCreateANamespacedThriftCommonFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas.com.domain.common.thrift",
                "src/test/data/actual/schemas.com.domain.common.thrift");
    }
    @Test
    public void shouldCreateANamespacedThriftAddressFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas.com.domain.address.thrift",
                "src/test/data/actual/schemas.com.domain.address.thrift");
    }
}
