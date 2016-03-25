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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.TreeMap;

import com.github.tranchis.xsd2thrift.marshal.IMarshaller;
import com.github.tranchis.xsd2thrift.marshal.ProtobufMarshaller;
import com.github.tranchis.xsd2thrift.marshal.ThriftMarshaller;

public class Main {
	private static boolean correct;
	private static String usage = "" + "Usage: java xsd2thrift.jar [--thrift] [--protobuf] [--output=FILENAME]\n"
	        + "                           [--package=NAME] filename.xsd\n" + "\n"
	        + "  --thrift          		: convert to Thrift\n"
	        + "  --protobuf        		: convert to Protocol Buffers\n"
	        + "  --filename=FILENAME 		: store the result in FILENAME instead of standard output\n"
	        + "  --package=NAME    		: set namespace/package of the output file\n"
	        + "  --nestEnums=true|false	: nest enum declaration within messages that reference them, only supported by protobuf, defaults to true\n"
	        + "";

	private static void usage(String error) {
		System.err.println(error);
		usage();
	}

	private static void usage() {
		System.err.print(usage);
		correct = false;
	}

	public Main(String[] args) throws Xsd2ThriftException, FileNotFoundException {
		TreeMap<String, String> map = initMap();
		String xsd = null;
		String fileParam = null;
		String packageParam = null;
		String param = null;
		int i = 0;
		IMarshaller im = null;
		boolean nestEnums = true;

		correct = true;

		if (args.length == 0 || args[args.length - 1].startsWith("--")) {
			usage();
		} else {
			xsd = args[args.length - 1];

			while (correct && i < args.length - 1) {
				if (args[i].equals("--thrift")) {
					if (im == null) {
						im = new ThriftMarshaller();
						nestEnums = false;
					} else {
						usage("Only one marshaller can be specified at a time.");
					}
				} else if (args[i].equals("--protobuf")) {
					if (im == null) {
						im = new ProtobufMarshaller();
					} else {
						usage("Only one marshaller can be specified at a time.");
					}
				} else if (args[i].startsWith("--filename=")) {
					fileParam = args[i].split("=")[1];
				} else if (args[i].startsWith("--package=")) {
					packageParam = args[i].split("=")[1];
				} else if (args[i].startsWith("--nestEnums=")) {
					param = args[i].split("=")[1];
					nestEnums = Boolean.valueOf(param);
				} else {
					usage();
				}
				i = i + 1;
			}

			if (im == null) {
				usage("A marshaller has to be specified.");
			}

			if (correct) {
				FileOutputStream fos = null == fileParam ? null : new FileOutputStream(new File(fileParam));
				XSDParser xsdParser = new XSDParser(fos, map);
				xsdParser.addMarshaller(im);
				xsdParser.setPackage(packageParam);
				xsdParser.setNestEnums(nestEnums);

				xsdParser.parse(xsd);
			}
		}

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Main myMain =
		new Main(args);
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
}
