/**
 * 
 */
package com.github.tranchis.xsd2thrift.marshal;

import java.util.TreeMap;

/**
 * @author marug
 *
 */
public abstract class AbstractMarshaller implements IMarshaller {

	protected TreeMap<String, String> typeMapping;

	protected String indent = "";

	protected AbstractMarshaller() {
		initTypeMapping();
	}

	protected void initTypeMapping() {
		// to override
	}
}
