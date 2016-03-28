/**
 * 
 */
package com.github.tranchis.xsd2thrift.marshal;

/**
 * @author marug
 *
 */
public class Protobuf3Marshaller extends AbstractMarshaller {

	Protobuf3Marshaller() {
		super();
	}

	@Override
	protected void initTypeMapping() {
		// TODO to override
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#writeHeader(java.lang.String)
	 */
	@Override
	public String writeHeader(String namespace) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#writeEnumHeader(java.lang.String)
	 */
	@Override
	public String writeEnumHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#writeEnumValue(int, java.lang.String)
	 */
	@Override
	public String writeEnumValue(int order, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#writeEnumFooter()
	 */
	@Override
	public String writeEnumFooter() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#writeStructHeader(java.lang.String)
	 */
	@Override
	public String writeStructHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#writeStructParameter(int, boolean, boolean,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String writeStructParameter(int order, boolean required, boolean repeated, String name, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#writeStructFooter()
	 */
	@Override
	public String writeStructFooter() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#getTypeMapping(java.lang.String)
	 */
	@Override
	public String getTypeMapping(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#isNestedEnums()
	 */
	@Override
	public boolean isNestedEnums() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.tranchis.xsd2thrift.marshal.IMarshaller#isCircularDependencySupported()
	 */
	@Override
	public boolean isCircularDependencySupported() {
		// TODO Auto-generated method stub
		return false;
	}

}
