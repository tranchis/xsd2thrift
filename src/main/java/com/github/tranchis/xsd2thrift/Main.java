/**
 * 
 */
package com.github.tranchis.xsd2thrift;

/**
 * @author marug
 *
 */
public class Main {

	private static boolean correct;

	private static String usage = "Usage: java xsd2thrift.jar [--thrift] [--protobuf] [--output=FILENAME]\n"
	        + "                           [--package=NAME] filename.xsd\n" + "\n"
	        + "  --thrift          		: convert to Thrift\n"
	        + "  --protobuf        		: convert to Protocol Buffers\n"
	        + "  --output=FILENAME 		: store the result in FILENAME instead of standard output\n"
	        + "  --package=NAME    		: set namespace/package of the output file\n"
	        + "  --nestEnums=true|false	: nest enum declaration within messages that reference them, only supported by protobuf, defaults to true\n\n";

	private static void usage(String error) {
		System.err.println(error);
		usage();
	}

	private static void usage() {
		System.err.print(usage);
		correct = false;
	}

	/**
	 * @param args
	 *            see usage
	 * 
	 * @throws Xsd2ThriftException
	 *             if operation failed
	 */
	public static void main(String[] args) throws Xsd2ThriftException {
		Xsd2Thrift myMain = new Xsd2Thrift();
		int i = 0;
		String fileParam = null;

		correct = true;

		if (args.length == 0 || args[args.length - 1].startsWith("--")) {
			usage();
		} else {
			myMain.setXschema(args[args.length - 1]);

			while (correct && i < args.length - 1) {
				if (args[i].equals("--thrift")) {
					if (!myMain.hasMarshaller()) {
						myMain.createMarshaller("thrift");
						myMain.setNestEnums(false);
					} else {
						usage("Only one marshaller can be specified at a time.");
					}
				} else if (args[i].equals("--protobuf")) {
					if (!myMain.hasMarshaller()) {
						myMain.createMarshaller("protobuf");
					} else {
						usage("Only one marshaller can be specified at a time.");
					}
				} else if (args[i].startsWith("--output=")) {
					fileParam = args[i].split("=")[1];
				} else if (args[i].startsWith("--package=")) {
					myMain.setPackageName(args[i].split("=")[1]);
				} else if (args[i].startsWith("--nestEnums=")) {
					String param = args[i].split("=")[1];
					myMain.setNestEnums(Boolean.valueOf(param));
				} else {
					usage();
				}
				i = i + 1;
			}

			if (!myMain.hasMarshaller()) {
				usage("A marshaller has to be specified.");
			}
			myMain.parseXsd(myMain.getXschema(), fileParam);

		}
	}
}
