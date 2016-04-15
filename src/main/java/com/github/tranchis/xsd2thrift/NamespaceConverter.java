package com.github.tranchis.xsd2thrift;

public class NamespaceConverter {

    public static String convertFromSchema(String ns){
        if(ns==null) return null;
        if (ns.contains("://")) {
            ns = ns.substring(ns.indexOf("://") + 3);
        }
        ns = ns.replaceAll("/", ".").replace("-",".");
        if(ns.startsWith(".")) ns = ns.substring(1);
        if(ns.endsWith(".")) ns = ns.substring(0,ns.length()-1);
        return ns;
    }
    
}
