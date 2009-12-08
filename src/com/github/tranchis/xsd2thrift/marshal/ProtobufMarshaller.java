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
package com.github.tranchis.xsd2thrift.marshal;

import java.util.TreeMap;

public class ProtobufMarshaller implements IMarshaller
{
	private TreeMap<String, String> typeMapping;

	public ProtobufMarshaller()
	{
		typeMapping = new TreeMap<String,String>();
		typeMapping.put("positiveInteger", "i16");
		typeMapping.put("integer", "i16");
		typeMapping.put("long", "i32");
		typeMapping.put("decimal", "double");
		typeMapping.put("ID", "string");
		typeMapping.put("IDREF", "string");
		typeMapping.put("NMTOKEN", "string");
		typeMapping.put("NMTOKENS", "string"); // TODO: Fix this
		typeMapping.put("anySimpleType", "BaseObject");
		typeMapping.put("anyType", "BaseObject");
		typeMapping.put("anyURI", "BaseObject");
	}
	
	@Override
	public String writeHeader()
	{
		return "";
	}

	@Override
	public String writeEnumHeader(String name)
	{
		return "\tenum " + name + "\n\t{\n";
	}

	@Override
	public String writeEnumValue(int order, String value)
	{
		return("\t\t" + value + " = " + order + ";\n");
	}

	@Override
	public String writeEnumFooter()
	{
		return "\t}\n";
	}

	@Override
	public String writeStructHeader(String name)
	{
		return "message " + name + "\n{\n";
	}

	@Override
	public String writeStructParameter(int order, boolean required, boolean repeated, String name, String type)
	{
		String	sRequired;
		
		sRequired = getRequired(required, repeated);
		
		return "\t" + sRequired + " " + type + " " + name + " = " + order + ";\n";
	}

	private String getRequired(boolean required, boolean repeated)
	{
		String res;

		if(repeated)
		{
			res = "repeated";
		}
		else
		{
			if(required)
			{
				res = "required";
			}
			else
			{
				res = "optional";
			}
		}
		
		return res;
	}

	@Override
	public String writeStructFooter()
	{
		return "}\n\n";
	}

	@Override
	public String getTypeMapping(String type)
	{
		return typeMapping.get(type);
	}

	@Override
	public boolean isNestedEnums()
	{
		return true;
	}
}
