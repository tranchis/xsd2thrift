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
 */package com.github.tranchis.xsd2thrift;

import com.sun.xml.xsom.XmlString;

public class Field
{

    @Override
	public String toString() {
		return "Field [name=" + name + ", typeNamespace=" + typeNamespace
				+ ", type=" + type + ", required=" + required + ", repeat="
				+ repeat + ", def=" + def + "]";
	}

	private String name;
    private String typeNamespace;
	private String type;
	private boolean required;
	private boolean repeat;
	private XmlString def;

	public Field(String name, String typeNamespace, String type, boolean repeat, XmlString def, boolean required)
	{
		this.name = name;
		this.type = type;
		this.required = required;
		this.def = def;
		this.repeat = repeat;
		this.typeNamespace = typeNamespace;
	}

	public String getTypeNamespace() {
        return typeNamespace;
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
