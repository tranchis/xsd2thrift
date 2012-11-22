/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * XSD2Thrift
 * 
 * Copyright (C) 2009 Sergio Alvarez-Napagao http://www.sergio-alvarez.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 */
package com.github.tranchis.xsd2thrift;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.github.tranchis.xsd2thrift.marshal.IMarshaller;

/**
 * @author p14n
 * 
 * A class to handle the various forms of output - to console, to file, and to namespace-specific files
 *
 */
public class OutputWriter {

	private String filename,directory;
	private boolean splitBySchema;
	private OutputStream os;
	private Map<String,OutputStream> streams;
	private IMarshaller marshaller;
    private String defaultNamespace;
    private String defaultExtension;
    Map<String,Set<String>> inclusions = null;


	
    public void setDefaultExtension(String defaultExtension) {
        this.defaultExtension = defaultExtension;
    }


    
    public OutputStream getStream(String ns) throws IOException {
    	if(os==null&&streams==null){
            initializeOutputStream();
    	}
    	if(os!=null) return os;
        if (ns == null)
            ns = defaultNamespace;

        if (ns == null)
            ns = "default";

        return getNamespaceSpecificStream(ns);
    }

    private OutputStream getNamespaceSpecificStream(String cleanedNamespace) throws IOException {
        if (!streams.containsKey(cleanedNamespace)) {
            OutputStream os = new FileOutputStream(directory() + cleanedNamespace + "." + defaultExtension);
            streams.put(cleanedNamespace, os);
            os.write(marshaller.writeHeader(cleanedNamespace).getBytes());
    	}
        return streams.get(cleanedNamespace);
	}

    private void initializeOutputStream() throws IOException {
		if(splitBySchema){
			streams= new HashMap<String, OutputStream>();
		} else {
			if(filename==null){
				os = System.out;
                os.write(marshaller.writeHeader(defaultNamespace).getBytes());
			} else {
				os = new FileOutputStream(directory()+filename);
                os.write(marshaller.writeHeader(defaultNamespace).getBytes());
			}
		}
	}

    public void setDefaultNamespace(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
    }

    private String directory() {
		return directory==null?"":directory+"/";
	}

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setSplitBySchema(boolean splitBySchema) {
        this.splitBySchema = splitBySchema;
    }

    public void setMarshaller(IMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void addInclusion(String namespace, String includeNamespace) {
        if(inclusions==null)
            inclusions = new HashMap<String, Set<String>>();
        
        if(!inclusions.containsKey(namespace))
            inclusions.put(namespace, new TreeSet<String>());
        
        inclusions.get(namespace).add(includeNamespace);
    }



    public void postProcessNamespacedFilesForIncludes() throws IOException {
        
        if(streams!=null){
            Iterator<OutputStream> i = streams.values().iterator();
            while(i.hasNext()){
                OutputStream o = i.next();
                o.flush();
                o.close();
            }
            if(inclusions!=null){
                Iterator<String> namespaces = inclusions.keySet().iterator();
                while(namespaces.hasNext()){
                    String namespace = namespaces.next();
                    File f = new File(directory() + namespace + "." + defaultExtension);
                    if(f.exists()){
                        writeIncludes(f,inclusions.get(namespace));
                    }
                }
            }
        }
    }



    private void writeIncludes(File f, Set<String> toInclude) throws IOException {
        Iterator<String> i = toInclude.iterator();
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = null;
        StringBuffer output = new StringBuffer();
        int count =0;
        while((line = reader.readLine())!=null){
            output.append(line+"\n");
            if(count==1){
                while(i.hasNext()){
                    output.append(marshaller.writeInclude(i.next()));
                }
                output.append("\n");
            }
            count++;
        }
        reader.close();
        FileWriter writer = new FileWriter(f);
        writer.append(output.toString());
        writer.flush();
        writer.close();
    }
}
