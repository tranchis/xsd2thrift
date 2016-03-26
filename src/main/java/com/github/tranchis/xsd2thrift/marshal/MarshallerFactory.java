/**
 * 
 */
package com.github.tranchis.xsd2thrift.marshal;

import com.github.tranchis.xsd2thrift.Xsd2ThriftException;

/**
 * @author marug
 *
 */
public class MarshallerFactory {

	private MarshallerFactory() {
		// private
	}

	public enum Protocol {
		THRIFT("thrift") {

			@Override
			public IMarshaller createMarshaller() {
				return new ThriftMarshaller();
			}
		},
		PROTOBUF("protobuf") {

			@Override
			public IMarshaller createMarshaller() {
				return new ProtobufMarshaller();
			}
		},
		PROTOBUF3("protobuf3") {

			@Override
			public IMarshaller createMarshaller() throws Xsd2ThriftException {
				throw new Xsd2ThriftException("not yet implemented.");
				// return new Protobuf3Marshaller();
			}
		};

		private String name;

		private Protocol(String name) {
			this.name = name;
		}

		public static Protocol forName(String name) {
			for (Protocol protocol : Protocol.values()) {
				if (protocol.name.equals(name)) {
					return protocol;
				}
			}
			return null;
		}

		public abstract IMarshaller createMarshaller() throws Xsd2ThriftException;
	}

	public static IMarshaller createMarshaller(Protocol protocol) throws Xsd2ThriftException {
		if (null == protocol) {
			throw new Xsd2ThriftException("Unknown protocol ");
		}
		return protocol.createMarshaller();
	}
}
