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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import com.github.tranchis.xsd2thrift.marshal.IMarshaller;
import com.github.tranchis.xsd2thrift.marshal.MarshallerFactory;
import com.github.tranchis.xsd2thrift.marshal.MarshallerFactory.Protocol;

public class Xsd2Thrift {

	private TreeMap<String, String> map;
	private String xschema;
	private String packageName;
	private IMarshaller iMarshaller;
	boolean nestEnums = true;

	/**
	 * @throws Xsd2ThriftException
	 *             if construction fails
	 * 
	 */
	public Xsd2Thrift() throws Xsd2ThriftException {
		this(null, null, null, true);
	}

	/**
	 * 
	 * @param xschema
	 *            the schema to transform
	 * @param packageName
	 *            the package name of the generated objects
	 * @param protocol
	 *            the {@link Protocol}
	 * @param nestEnums
	 *            only available for protobuf, default true
	 * @throws Xsd2ThriftException
	 *             if construction fails
	 * 
	 */
	public Xsd2Thrift(String xschema, String packageName, String protocol, Boolean nestEnums)
	        throws Xsd2ThriftException {
		map = initMap();
		this.xschema = xschema;
		this.packageName = packageName;
		if (null != protocol) {
			this.createMarshaller(protocol);
		}
		if (null != nestEnums) {
			this.nestEnums = nestEnums;
		}
	}

	private TreeMap<String, String> initMap() {
		TreeMap<String, String> map = new TreeMap<>();
		map.put("schema_._type", "binary");
		map.put("EString", "string");
		map.put("EBoolean", "boolean");
		map.put("EInt", "integer");
		map.put("EDate", "long");
		map.put("EChar", "byte");
		map.put("EFloat", "decimal");
		map.put("EObject", "binary");
		map.put("Extension", "binary");

		return map;
	}

	/**
	 * 
	 * @param fileParam
	 *            the output file name
	 * 
	 * @throws Xsd2ThriftException
	 *             if creation of the {@link XSDParser} failed
	 */
	private XSDParser createXsdParser(String fileParam) throws Xsd2ThriftException {
		XSDParser xsdParser = null;

		try {
			FileOutputStream fos = null;
			if (null != fileParam) {
				File destFile = new File(fileParam);
				if (!destFile.exists()) {
					destFile.getParentFile().mkdirs();
					destFile.createNewFile();
				}
				fos = new FileOutputStream(destFile);
			}

			xsdParser = new XSDParser(fos, map);
			xsdParser.addMarshaller(iMarshaller);
			xsdParser.setPackage(packageName);
			xsdParser.setNestEnums(nestEnums);
		} catch (IOException e) {
			throw new Xsd2ThriftException(e);
		}
		return xsdParser;
	}

	/**
	 * 
	 * @param streamFilename
	 *            the xsd file to parse
	 * @param destfileName
	 *            the output file name
	 * 
	 * @throws Xsd2ThriftException
	 *             a {@link Xsd2ThriftException} if creation of the {@link XSDParser} or parsing failed
	 */
	public void parseXsd(String streamFilename, String destfileName) throws Xsd2ThriftException {
		this.createXsdParser(destfileName).parse(streamFilename);
	}

	/**
	 * 
	 * @param protocol
	 *            the desired {@link Protocol} to marshal into
	 * 
	 * @throws Xsd2ThriftException
	 *             if creation failed
	 */
	public void createMarshaller(String protocol) throws Xsd2ThriftException {
		this.iMarshaller = MarshallerFactory.createMarshaller(Protocol.forName(protocol));
	}

	/**
	 * 
	 * @return true if a marshaller has been created
	 */
	public boolean hasMarshaller() {
		return iMarshaller != null;
	}

	public String getXschema() {
		return xschema;
	}

	public void setXschema(String xschema) {
		this.xschema = xschema;
	}

	public void setPackageName(String packageParam) {
		this.packageName = packageParam;
	}

	public void setNestEnums(boolean nestEnums) {
		this.nestEnums = nestEnums;
	}

}
