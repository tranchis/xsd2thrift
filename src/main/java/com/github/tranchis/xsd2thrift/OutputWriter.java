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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
	
    public void setDefaultExtension(String defaultExtension) {
        this.defaultExtension = defaultExtension;
    }

    private String namespace(String ns) {
        if (ns.contains("://")) {
            ns = ns.substring(ns.indexOf("://") + 3);
        }
        ns = ns.replaceAll("/", ".");
        if(ns.startsWith(".")) ns = ns.substring(1);
        if(ns.endsWith(".")) ns = ns.substring(0,ns.length()-1);
		return ns;
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

        return getNamespaceSpecificStream(namespace(ns));
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

	public void write(String namespace,String content) throws FileNotFoundException, IOException{
		getStream(namespace).write(content.getBytes());
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

}
