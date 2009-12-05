package com.github.tranchis.xsd2thrift;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sun.xml.xsom.XmlString;

public class Struct
{
	private Map<String,Field>	map;
	private Set<String>			types;
	private String				name;
	private String				parent;

	public Struct(String name)
	{
		this.name = name;
		map = new HashMap<String,Field>();
		types = new TreeSet<String>();
	}

	public void addField(String name, String type, boolean required, boolean repeat, XmlString def, Map<String, String> typeMapping)
	{
		Field	f;
		
		if(map.get(name) == null)
		{
			if(type == null)
			{
				type = new String(name);
			}
			else
			{
				if(typeMapping.containsKey(type))
				{
					type = typeMapping.get(type);
				}
				else if(type.equals(this.name))
				{
					type = "binary";
				}
			}
//			System.out.println("name: " + name + ", type: " + type);
			f = new Field(name, type, repeat, def, required);
			map.put(name, f);
			if(!type.equals(this.name))
			{
				types.add(type);
			}
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Collection<Field> getFields()
	{
		return map.values();
	}

	public Collection<String> getTypes()
	{
		return types;
	}

	public void setParent(String parent)
	{
		this.parent = parent;
	}

	public String getParent()
	{
		return parent;
	}
}
