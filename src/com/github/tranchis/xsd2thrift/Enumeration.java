package com.github.tranchis.xsd2thrift;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Enumeration
{
	private String		name;
	private Set<String>	strings;

	public Enumeration(String name)
	{
		this.setName(name);
		this.strings = new TreeSet<String>();
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void addString(String value)
	{
		strings.add(value);
	}

	public Iterator<String> iterator()
	{
		return strings.iterator();
	}
}
