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

public class ThriftMarshaller implements IMarshaller
{
	private TreeMap<String, String> typeMapping;

	public ThriftMarshaller()
	{
		typeMapping = new TreeMap<String,String>();
		typeMapping.put("positiveInteger", "i16");
		typeMapping.put("int", "i16");
		typeMapping.put("integer", "i16");
		typeMapping.put("long", "i32");
		typeMapping.put("decimal", "double");
		typeMapping.put("ID", "string");
		typeMapping.put("IDREF", "string");
		typeMapping.put("NMTOKEN", "string");
		typeMapping.put("NMTOKENS", "list<string>");
		typeMapping.put("anySimpleType", "UnspecifiedType");
		typeMapping.put("anyType", "UnspecifiedType");
		typeMapping.put("anyURI", "UnspecifiedType");
		typeMapping.put("boolean", "bool");
		typeMapping.put("binary", "binary");
	}
	
	@Override
	public String writeHeader(String namespace)
	{
		String res;
		
		if(namespace != null && !namespace.isEmpty())
		{
			res = "namespace * " + namespace + "\n\n";
		}
		else
		{
			res = "";
		}
		
		return res;
	}

	@Override
	public String writeEnumHeader(String name)
	{
		return "enum " + name + "\n{\n";
	}

	@Override
	public String writeEnumValue(int order, String value)
	{
		return("\t" + value + ",\n");
	}

	@Override
	public String writeEnumFooter()
	{
		return "}\n\n";
	}

	@Override
	public String writeStructHeader(String name)
	{
		return "struct " + name + "\n{\n";
	}

	@Override
	public String writeStructParameter(int order, boolean required, boolean repeated, String name, String type)
	{
		String	sType, sRequired;
		
		sRequired = getRequired(required);
		sType = type;
		if(repeated)
		{
			sType = "list<" + type + ">";
		}
		
		return "\t" + order + " : " + sRequired + " " + sType + " " + name + ",\n";
	}

	private String getRequired(boolean required)
	{
		String res;

		if(required)
		{
			res = "required";
		}
		else
		{
			res = "optional";
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
		return false;
	}
}
