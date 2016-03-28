/**
 * 
 */
package com.github.tranchis.xsd2thrift;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author marug
 *
 */
public class Xsd2ProtobufTest {

	@Before
	public void setup() {
		new File("target/generated-sources/proto").mkdir();
	}

	@Test
	@Ignore
	public void shouldDislplayUsage() throws FileNotFoundException, Xsd2ThriftException {

		List<String> args = new LinkedList<String>();

		Main myMain = new Main();
		Main.main(args.toArray(new String[args.size()]));
		assertNotNull(myMain);
	}

	@Test
	public void shouldPrintToSysout() throws Xsd2ThriftException, IOException {

		String xschema = "atom.xsd";
		List<String> args = new LinkedList<String>();
		args.add("--protobuf");
		args.add("--package=com.github.tranchis.xsd2proto");
		args.add("--nestEnums=true");
		args.add("contrib/" + xschema);

		Main myMain = new Main();
		Main.main(args.toArray(new String[args.size()]));
		assertNotNull(myMain);

	}

	@Test
	public void shouldTransformXsd2Protobuf() throws Xsd2ThriftException, IOException {

		String targetDir = "contrib/";
		List<String> xschemas = listXschemas(targetDir);
		for (String xschema : xschemas) {
			String filename = xschema.replace('-', '_').replace(".xsd", ".proto");
			String targetFilename = "target/generated-sources/proto/" + filename;
			// new File(targetFilename).createNewFile();

			List<String> args = new LinkedList<String>();
			args.add("--protobuf");
			args.add("--output=" + targetFilename);
			args.add("--package=com.github.tranchis.xsd2proto");
			args.add("--nestEnums=true");
			args.add("--debug");
			args.add(targetDir + xschema);

			Main myMain = new Main();
			Main.main(args.toArray(new String[args.size()]));
			assertNotNull(myMain);
		}
	}

	private List<String> listXschemas(String targetDir) {
		File contrib = new File(targetDir);
		String[] list = contrib.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xsd");
			}
		});
		System.out.println(list.length + " xschemas");
		return Arrays.asList(list);
	}

}
