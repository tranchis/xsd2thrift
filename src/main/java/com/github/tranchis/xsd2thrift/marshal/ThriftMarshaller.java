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
	private String indent = "";

	public ThriftMarshaller()
	{
		typeMapping = new TreeMap<String,String>();
		typeMapping.put("positiveInteger", "i64");
		typeMapping.put("nonPositiveInteger", "i64");
		typeMapping.put("negativeInteger", "i64");
		typeMapping.put("nonNegativeInteger", "i64");
		typeMapping.put("int", "i32");
		typeMapping.put("integer", "i64");

		// no unsigned types in thrift
		typeMapping.put("unsignedLong", "int64");
		typeMapping.put("unsignedInt", "int32");
		typeMapping.put("unsignedShort", "int16"); 
		typeMapping.put("unsignedByte", "byte"); 

		typeMapping.put("short", "i16");
		typeMapping.put("long", "i64");
		typeMapping.put("decimal", "double");
		typeMapping.put("float", "double"); // No float type in thrift
		typeMapping.put("ID", "string");
		typeMapping.put("IDREF", "string");
		typeMapping.put("NMTOKEN", "string");
		typeMapping.put("NMTOKENS", "list<string>");
		typeMapping.put("anySimpleType", "UnspecifiedType");
		typeMapping.put("anyType", "UnspecifiedType");
		typeMapping.put("anyURI", "UnspecifiedType");
		typeMapping.put("boolean", "bool");
		typeMapping.put("base64Binary", "binary");
		typeMapping.put("hexBinary", "binary");
		typeMapping.put("date", "i32"); //Number of days since January 1st, 1970
		typeMapping.put("dateTime", "i64"); //Number of milliseconds since January 1st, 1970
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
    public String writeInclude(String namespace)
    {
        String res;
        
        if(namespace != null && !namespace.isEmpty())
        {
            res = "include \"" + namespace + ".thrift\"\n";
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
		final String result = writeIndent() + "enum " + name + "\n{\n";
		increaseIndent();
		return result;
	}

	@Override
	public String writeEnumValue(int order, String value)
	{
		return(writeIndent() + value + ",\n");
	}

	@Override
	public String writeEnumFooter()
	{
		decreaseIndent();
		return writeIndent() + "}\n\n";
	}

	@Override
	public String writeStructHeader(String name)
	{
		final String result = writeIndent() + "struct " + name + "\n{\n";
		increaseIndent();
		return result;
	}

	@Override
	public String writeStructParameter(int order, boolean required, boolean repeated, String name, String type)
	{
		String	sType, sRequired;
		
		sRequired = getRequired(required);
		
		int lastPeriod = type.lastIndexOf(".");
		if(lastPeriod>-1){
		    type = type.substring(0, lastPeriod).replace(".", "_")+type.substring(lastPeriod);
		}
		
		sType = type;
		if(repeated)
		{
			sType = "list<" + type + ">";
		}
		
		return writeIndent() + order + " : " + sRequired + " " + sType + " " + name + ",\n";
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
		decreaseIndent();
		return writeIndent() + "}\n\n";
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
	
    @Override
    public boolean isCircularDependencySupported() {
        return false;
    }

	public void increaseIndent() {
    	indent += "\t";
    }
    
	public void decreaseIndent() {
    	indent = indent.substring(0, indent.length() > 0 ? indent.length() - 1 : 0);
    }
    
    private String writeIndent() {
    	return indent;
    }

	@Override
	public void setDatesAsStrings() {
		typeMapping.put("date", "i32"); // Number of days since January 1st,
										// 1970
		typeMapping.put("dateTime", "i64"); // Number of milliseconds since
											// January 1st, 1970
	}
}
