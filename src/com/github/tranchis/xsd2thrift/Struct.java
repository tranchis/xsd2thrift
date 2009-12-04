package com.github.tranchis.xsd2thrift;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

	public void addField(String name, String type, boolean required, Map<String, String> typeMapping)
	{
		Field	f;
		
		if(map.get(name) == null)
		{
			if(typeMapping.containsKey(type))
			{
				type = typeMapping.get(type);
			}
			else if(type.equals(this.name))
			{
				type = "binary";
			}
			f = new Field(name, type, required);
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
