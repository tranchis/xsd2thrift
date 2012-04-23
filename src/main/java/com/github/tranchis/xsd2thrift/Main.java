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
import java.util.TreeMap;

import com.github.tranchis.xsd2thrift.marshal.IMarshaller;
import com.github.tranchis.xsd2thrift.marshal.ProtobufMarshaller;
import com.github.tranchis.xsd2thrift.marshal.ThriftMarshaller;

public class Main
{
	private static boolean	correct;
	private static String	usage = "" + 
			"Usage: java xsd2thrift.jar [--thrift] [--protobuf] [--filename=FILENAME]\n" +
			"                           [--package=NAME] filename.xsd\n" + 
			"\n" + 
			"  --thrift          		: convert to Thrift\n" + 
			"  --protobuf        		: convert to Protocol Buffers\n" + 
			"  --filename=FILENAME 		: store the result in FILENAME instead of standard output\n" + 
			"  --package=NAME    		: set namespace/package of the output file\n" + 
			"  --nestEnums=true|false	: nest enum declaration within messages that reference them, only supported by protobuf, defaults to true\n" + 
			"  --force-circular         : force production of IDL with circular dependencies even if not supported (eg Thrift)\n" +
			"  --postfix=STRING         : postfix declarations with string, use \"\" for no postfix. default is Type\n" +
			"";
	

	private static void usage(String error)
	{
		System.err.println(error);
		usage();
	}

	private static void usage()
	{
		System.err.print(usage);
		correct = false;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		XSDParser				xp;
		TreeMap<String,String>	map;
		String					xsd, param;
		int						i;
		IMarshaller				im;
		
		correct = true;
		im = null;
		
		map = new TreeMap<String,String>();
		map.put("schema_._type", "binary");
		map.put("EString", "string");
		map.put("EBoolean", "boolean");
		map.put("EInt", "integer");
		map.put("EDate", "long");
		map.put("EChar", "byte");
		map.put("EFloat", "decimal");
		map.put("EObject", "binary");
		map.put("Extension", "binary");
		
		if(args.length == 0 || args[args.length-1].startsWith("--"))
		{
			usage();
		}
		else
		{
			xsd = args[args.length - 1];
			xp = new XSDParser(xsd, map);
			
			i = 0;
			while(correct && i < args.length - 1)
			{
				if(args[i].equals("--thrift"))
				{
					if(im == null)
					{
						im = new ThriftMarshaller();
						xp.addMarshaller(im);
					}
					else
					{
						usage("Only one marshaller can be specified at a time.");
					}
				}
				else if(args[i].equals("--protobuf"))
				{
					if(im == null)
					{
						im = new ProtobufMarshaller();
						xp.addMarshaller(im);
					}
					else
					{
						usage("Only one marshaller can be specified at a time.");
					}
				}
				else if(args[i].equals("--force-circular"))
				{
					xp.forceCircular(true);
				}
				else if(args[i].startsWith("--filename="))
				{
					param = args[i].split("=")[1];
					xp.setOutputStream(new FileOutputStream(new File(param)));
				}
				else if(args[i].startsWith("--postfix="))
				{
					try {
						param = args[i].split("=")[1];
					} catch(Exception e) {
						param = "";
					}
					xp.setPostfix(param);
				}
				else if(args[i].startsWith("--package="))
				{
					param = args[i].split("=")[1];
					xp.setPackage(param);
				}
				else if (args[i].startsWith("--nestEnums="))
				{
					param = args[i].split("=")[1];
					boolean nestEnums = Boolean.valueOf(param);
					xp.setNestEnums(nestEnums);
				}
				else
				{
					usage();
				}
				
				i = i + 1;
			}
			
			if(im == null)
			{
				usage("A marshaller has to be specified.");
			}
			
			if(correct)
			{
				xp.parse();
			}
		}
	}
}
