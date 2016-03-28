/**
 * 
 */
package com.github.tranchis.xsd2thrift;

/**
 * @author marug
 *
 */
public class Xsd2ThriftException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2201784203152975736L;

	public Xsd2ThriftException(String message) {
		super(message);
	}

	public Xsd2ThriftException(Throwable e) {
		super(e);
	}

}
