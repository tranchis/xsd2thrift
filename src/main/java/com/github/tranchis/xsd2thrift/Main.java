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

/**
 * @author marug
 *
 */
public class Main {

	private static boolean correct;

	private static String usage = "Usage: java xsd2thrift.jar [--thrift] [--protobuf] [--output=FILENAME]\n"
	        + "                           [--package=NAME] [--debug] filename.xsd\n\n"
	        + "  --thrift          		: convert to Thrift\n"
	        + "  --protobuf        		: convert to Protocol Buffers\n"
	        + "  --output=FILENAME 		: store the result in FILENAME instead of standard output\n"
	        + "  --package=NAME    		: set namespace/package of the output file\n"
	        + "  --nestEnums=true|false	: nest enum declaration within messages that reference them, only supported by protobuf, defaults to true\n"
	        + "  --debug                : report SAX parsing errors; defaults to false\n\n";

	private static void usage(String error) {
		System.err.println(error);
		usage();
	}

	private static void usage() {
		System.err.print(usage);
		correct = false;
	}

	/**
	 * @param args
	 *            see usage
	 * 
	 * @throws Xsd2ThriftException
	 *             if operation failed
	 */
	public static void main(String[] args) throws Xsd2ThriftException {
		String xschema = null;
		String protocol = null;
		String fileParam = null;
		String packageName = null;
		boolean nestEnums = true;
		boolean debug = false;

		int i = 0;
		correct = true;

		if (args.length == 0 || args[args.length - 1].startsWith("--") || !args[args.length - 1].endsWith(".xsd")) {
			usage();
		} else {
			xschema = args[args.length - 1];

			while (correct && i < args.length - 1) {
				if (args[i].equals("--thrift")) {
					if (null == protocol) {
						protocol = "thrift";
						nestEnums = false;
					} else {
						usage("Only one protocol can be specified at a time.");
					}
				} else if (args[i].equals("--protobuf")) {
					if (null == protocol) {
						protocol = "protobuf";
					} else {
						usage("Only one protocol can be specified at a time.");
					}
				} else if (args[i].startsWith("--output=")) {
					fileParam = args[i].split("=")[1];
				} else if (args[i].startsWith("--package=")) {
					packageName = args[i].split("=")[1];
				} else if (args[i].startsWith("--nestEnums=")) {
					String param = args[i].split("=")[1];
					nestEnums = Boolean.valueOf(param);
				} else if (args[i].startsWith("--debug")) {
					debug = true;
				} else {
					usage();
				}
				i = i + 1;
			}
			if (null == protocol) {
				usage("A protocol has to be specified.");
			}

			Xsd2Thrift xsd2Thrift = new Xsd2Thrift(packageName, protocol, nestEnums);
			xsd2Thrift.parseXsd(xschema, fileParam, debug);

		}
	}
}
