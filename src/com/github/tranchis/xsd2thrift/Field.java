package com.github.tranchis.xsd2thrift;

import com.sun.xml.xsom.XmlString;

public class Field
{

	private String name;
	private String type;
	private boolean required;
	private boolean repeat;
	private XmlString def;

	public Field(String name, String type, boolean repeat, XmlString def, boolean required)
	{
		this.name = name;
		this.type = type;
		this.required = required;
		this.def = def;
		this.repeat = repeat;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public boolean isRepeat()
	{
		return repeat;
	}

	public void setRepeat(boolean repeat)
	{
		this.repeat = repeat;
	}

	public XmlString getDef()
	{
		return def;
	}

	public void setDef(XmlString def)
	{
		this.def = def;
	}

}
