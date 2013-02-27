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

import java.util.Map;

public interface IMarshaller {
	String writeInclude(String namespace);

	String writeHeader(String namespace);

	String writeEnumHeader(String name);

	String writeEnumValue(int order, String value);

	String writeEnumFooter();

	String writeStructHeader(String name);

	String writeStructParameter(int order, boolean required, boolean repeated,
			String name, String type);

	String writeStructFooter();

	String getTypeMapping(String type);

	boolean isNestedEnums();

	boolean isCircularDependencySupported();

	void setCustomMappings(Map<String, String> customMappings);
}
