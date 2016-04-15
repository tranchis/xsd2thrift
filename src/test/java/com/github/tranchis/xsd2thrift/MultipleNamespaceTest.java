package com.github.tranchis.xsd2thrift;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.github.tranchis.xsd2thrift.TestHelper.*;

public class MultipleNamespaceTest {
    
    /*
     * For regeneration of expected data
     * public static void main(String args[]){
        try {
            //Main.main(new String[]{"--protobuf","--splitBySchema=true","--directory=src/test/data/expected/","--package=schemas.com.domain.common","contrib/ns-person.xsd"});
            //Main.main(new String[]{"--thrift","--splitBySchema=true","--directory=src/test/data/expected/","--package=schemas.com.domain.common","contrib/ns-person.xsd"});
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
    @BeforeClass
    public static void generateProtobufForTests(){
        try {
            Main.main(new String[]{"--protobuf","--splitBySchema=true","--directory=src/test/data/actual/","--package=schemas.com.domain.common","contrib/ns-person.xsd"});
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void shouldCreateANamespacedThriftPersonFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas_com_domain_person.thrift",
                "src/test/data/actual/schemas_com_domain_person.thrift");
    }
    @Test
    public void shouldCreateANamespacedThriftCommonFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas_com_domain_common.thrift",
                "src/test/data/actual/schemas_com_domain_common.thrift");
    }
    @Test
    public void shouldCreateANamespacedThriftAddressFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas_com_domain_address.thrift",
                "src/test/data/actual/schemas_com_domain_address.thrift");
    }
    @Test
    public void shouldCreateANamespacedProtobufPersonFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas_com_domain_person.proto",
                "src/test/data/actual/schemas_com_domain_person.proto");
    }
    @Test
    public void shouldCreateANamespacedProtobufCommonFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas_com_domain_common.proto",
                "src/test/data/actual/schemas_com_domain_common.proto");
    }
    @Test
    public void shouldCreateANamespacedProtobufAddressFile(){
        compareExpectedAndGenerated(
                "src/test/data/expected/schemas_com_domain_address.proto",
                "src/test/data/actual/schemas_com_domain_address.proto");
    }
}
