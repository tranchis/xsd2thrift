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
package com.github.tranchis.xsd2thrift;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.github.tranchis.xsd2thrift.marshal.IMarshaller;
import com.sun.xml.xsom.XSAttGroupDecl;
import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.XSType;
import com.sun.xml.xsom.parser.XSOMParser;

public class XSDParser implements ErrorHandler {
	private File f;
	private Map<String, Struct> map;
	private Map<String, Enumeration> enums;
	private Map<String, String> simpleTypes;
	private Set<String> keywords, basicTypes;
	private TreeMap<String, String> xsdMapping;
	private IMarshaller marshaller;
	private OutputWriter writer;
	private boolean nestEnums = true;
	private int enumOrderStart = 1;
	private boolean typeInEnums = true;

	public XSDParser(String stFile) {
		this.xsdMapping = new TreeMap<String, String>();
		init(stFile);
	}

	private void init(String stFile) {

		this.f = new File(stFile);
		map = new TreeMap<String, Struct>();
		enums = new TreeMap<String, Enumeration>();
		simpleTypes = new TreeMap<String, String>();
		keywords = new TreeSet<String>();
		keywords.add("interface");
		keywords.add("is");
		keywords.add("class");
		keywords.add("optional");
		keywords.add("yield");
		keywords.add("abstract");
		keywords.add("required");
		keywords.add("volatile");
		keywords.add("transient");
		keywords.add("service");
		keywords.add("else");

		basicTypes = new TreeSet<String>();
		basicTypes.add("string");
		basicTypes.add("anyType");
		basicTypes.add("anyURI");
		basicTypes.add("anySimpleType");

		basicTypes.add("integer");
		basicTypes.add("positiveInteger");
		basicTypes.add("nonPositiveInteger");
		basicTypes.add("negativeInteger");
		basicTypes.add("nonNegativeInteger");

		basicTypes.add("unsignedLong");
		basicTypes.add("unsignedInt");
		basicTypes.add("unsignedShort");
		basicTypes.add("unsignedByte");

		basicTypes.add("base64Binary");
		basicTypes.add("hexBinary");
		// binary is not a valid XSD type, but used as a placeholder internally
		basicTypes.add("binary");
		basicTypes.add("boolean");
		basicTypes.add("date");
		basicTypes.add("dateTime");
		basicTypes.add("decimal");
		basicTypes.add("float");
		basicTypes.add("double");
		basicTypes.add("byte");
		basicTypes.add("short");
		basicTypes.add("long");
		basicTypes.add("int");
		basicTypes.add("ID");
		basicTypes.add("IDREF");
		basicTypes.add("NMTOKEN");
		basicTypes.add("NMTOKENS");
		// basicTypes.add("BaseObject");
	}

	public XSDParser(String stFile, TreeMap<String, String> xsdMapping) {
		this.xsdMapping = xsdMapping;
		init(stFile);
	}

	public void parse() throws Exception {
		XSOMParser parser;

		parser = new XSOMParser();
		parser.setErrorHandler(this);
		parser.parse(f);

		interpretResult(parser.getResult());
		writeMap();

		writer.postProcessNamespacedFilesForIncludes();
	}

	private void writeMap() throws Exception {
		Iterator<Struct> its;
		Struct st;
		Set<Struct> ss;
		Set<String> declared;
		Iterator<String> ite;
		boolean bModified;

		st = createSuperObject();

		if (!marshaller.isNestedEnums() || !isNestEnums()) {
			ite = enums.keySet().iterator();
			while (ite.hasNext()) {
				writeEnum(ite.next());
			}
		}

		ss = new TreeSet<Struct>(map.values());
		declared = new TreeSet<String>(basicTypes);
		declared.addAll(enums.keySet());
		declared.addAll(simpleTypes.keySet());

		writeStruct(st, declared);

		bModified = true;
		while (bModified && !ss.isEmpty()) {
			bModified = false;
			its = map.values().iterator();
			while (its.hasNext()) {
				st = its.next();
				if (ss.contains(st) && declared.containsAll(st.getTypes())) {
					writeStruct(st, declared);
					ss.remove(st);
					bModified = true;
				}
			}
		}

		if (!ss.isEmpty()) {
			// Check if we are missing a type or it's a circular dependency
			Set<String> requiredTypes = new TreeSet<String>();
			Set<String> notYetDeclaredTypes = new TreeSet<String>();
			for (Struct s : ss) {
				requiredTypes.addAll(s.getTypes());
				notYetDeclaredTypes.add(s.getName());
			}
			requiredTypes.removeAll(declared);
			requiredTypes.removeAll(notYetDeclaredTypes);
			if (requiredTypes.isEmpty()) {
				// Circular dependencies have been detected
				if (marshaller.isCircularDependencySupported()) {
					// Just dump the rest
					for (Struct s : ss) {
						writeStruct(s, declared);
					}
				} else {
					// Report circular dependency
					System.err
							.println("Source schema contains circular dependencies and the target marshaller does not support them. Refer to the reduced dependency graph below.");
					for (Struct s : ss) {
						s.getTypes().removeAll(declared);
						System.err.println(s.getName() + ": " + s.getTypes());
					}
					throw new Exception();
				}
			} else {
				// Missing types have been detected
				System.err
						.println("Source schema contains references missing types.");
				for (Struct s : ss) {
					s.getTypes().retainAll(requiredTypes);
					if (!s.getTypes().isEmpty()) {
						System.err.println(s.getName() + ": " + s.getTypes());
					}
				}
				throw new Exception();
			}
		}
	}

	private void writeStruct(Struct st, Set<String> declared)
			throws IOException {
		Iterator<Field> itf;
		Field f;
		String fname, type;
		Set<String> usedInEnums;
		int order;

		os(st.getNamespace()).write(
				marshaller.writeStructHeader(escape(st.getName())).getBytes());
		itf = orderedIteratorForFields(st.getFields());
		usedInEnums = new TreeSet<String>();
		order = 1;
		while (itf.hasNext()) {
			f = itf.next();
			fname = f.getName();
			type = f.getType();

			if (isNestEnums() && marshaller.isNestedEnums()
					&& enums.containsKey(type) && !usedInEnums.contains(type)) {
				usedInEnums.add(type);
				writeEnum(type);
			}

			if (simpleTypes.containsKey(type)) {
				type = simpleTypes.get(type);
			}

			if (!map.keySet().contains(type) && !basicTypes.contains(type)
					&& !enums.containsKey(type)) {
				type = "binary";
			}
			if (type.equals(fname)) {
				fname = "_" + fname;
			}

			String typeNameSpace = "";
			if (marshaller.getTypeMapping(type) != null) {
				type = marshaller.getTypeMapping(type);
				int qualifyingDot = type.lastIndexOf('.');
				if (qualifyingDot > -1) {
					typeNameSpace = type.substring(0, qualifyingDot + 1);
					writer.addInclusion(st.getNamespace(),
							type.substring(0, qualifyingDot));
					type = type.substring(qualifyingDot + 1);
				}
			} else if (!basicTypes.contains(type)
					&& f.getTypeNamespace() != null
					&& !f.getTypeNamespace().equals(st.getNamespace())) {
				typeNameSpace = f.getTypeNamespace() + ".";
				writer.addInclusion(st.getNamespace(), f.getTypeNamespace());
			}

			type = typeNameSpace + escapeType(type);

			os(st.getNamespace()).write(
					marshaller.writeStructParameter(order, f.isRequired(),
							f.isRepeat(), escape(fname), type).getBytes());
			order = order + 1;
		}
		os(st.getNamespace()).write(marshaller.writeStructFooter().getBytes());
		declared.add(st.getName());
	}

	private Iterator<Field> orderedIteratorForFields(List<Field> fields) {
		Collections.sort(fields, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				if (o1 == null) {
					if (o2 == null)
						return 0;
					return 1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		});
		return fields.iterator();
	}

	private void writeEnum(String type) throws IOException {
		String enumValue;
		Enumeration en;
		Iterator<String> itg;
		en = enums.get(type);
		enumValue = escape(en.getName());
		os(en.getNamespace()).write(
				marshaller.writeEnumHeader(enumValue).getBytes());
		itg = en.iterator();
		int enumOrder = this.enumOrderStart;
		String typePrefix;
		if (typeInEnums) {
			typePrefix = en.getName() + "_";
		} else {
			typePrefix = "";
		}
		if (itg.hasNext()) {
			while (itg.hasNext()) {
				os(en.getNamespace()).write(
						marshaller.writeEnumValue(enumOrder,
								escape(typePrefix + itg.next()))
								.getBytes());
				enumOrder++;
			}
		} else {
			os(en.getNamespace()).write(
					marshaller.writeEnumValue(enumOrder,
							escape(typePrefix + "UnspecifiedValue"))
							.getBytes());
		}

		os(en.getNamespace()).write(marshaller.writeEnumFooter().getBytes());
	}

	private Struct createSuperObject() {
		Struct st;

		st = new Struct("UnspecifiedType", null);

		st.addField("baseObjectType", "string", true, false, null, xsdMapping);
		st.addField("object", "binary", true, false, null, xsdMapping);

		return st;
	}

	private String escape(String name) {
		String res = escapeType(name);

		if (basicTypes.contains(res)) {
			res = "_" + res;
		}

		return res;
	}

	private String escapeType(String name) {
		String res;

		final char[] nameChars = name.toCharArray();

		for (int i = 0; i < nameChars.length; i++) {
			if (!Character.isJavaIdentifierPart(nameChars[i])) {
				nameChars[i] = '_';
			}
		}

		res = String.valueOf(nameChars);

		if (!Character.isJavaIdentifierStart(nameChars[0])
				|| keywords.contains(res)) {
			res = "_" + res;
		}

		return res;
	}

	private void interpretResult(XSSchemaSet sset) {
		XSSchema xs;
		Iterator<XSSchema> it;
		Iterator<XSElementDecl> itt;
		XSElementDecl el;

		it = sset.iterateSchema();
		while (it.hasNext()) {
			xs = it.next();
			if (!xs.getTargetNamespace().endsWith("/XMLSchema")) {
				itt = xs.iterateElementDecls();
				while (itt.hasNext()) {
					el = itt.next();
					interpretElement(el, sset);
				}
				final Iterator<XSComplexType> ict = xs.iterateComplexTypes();
				while (ict.hasNext()) {
					processComplexType(ict.next(), null, sset);
				}
				final Iterator<XSSimpleType> ist = xs.iterateSimpleTypes();
				while (ist.hasNext()) {
					processSimpleType(ist.next(), null);
				}
			}
		}
	}

	private void interpretElement(XSElementDecl el, XSSchemaSet sset) {
		XSComplexType cType;
		XSSimpleType xs;

		if (el.getType() instanceof XSComplexType
				&& el.getType() != sset.getAnyType()) {
			cType = (XSComplexType) el.getType();
			processComplexType(cType, el.getName(), sset);
		} else if (el.getType() instanceof XSSimpleType
				&& el.getType() != sset.getAnySimpleType()) {
			xs = el.getType().asSimpleType();
			processSimpleType(xs, el.getName());
		}
	}

	private String processType(XSType type, String elementName, XSSchemaSet sset) {
		if (type instanceof XSComplexType) {
			return processComplexType(type.asComplexType(), elementName, sset);
		} else {
			return processSimpleType(type.asSimpleType(), elementName);
		}
	}

	/**
	 * @param xs
	 * @param elementName
	 */
	private String processSimpleType(XSSimpleType xs, String elementName) {

		String typeName = xs.getName();
		String namespace = xs.getTargetNamespace();

		if (typeName == null) {
			if (xs.getFacet("enumeration") != null) {
				typeName = elementName != null ? elementName + "Type"
						: generateAnonymousName();
			} else {
				// can't use elementName here as it might not be unique
				// (test-range.xsd)
				typeName = generateAnonymousName();
			}
		}

		if (xs.isRestriction() && xs.getFacet("enumeration") != null) {
			createEnum(typeName, namespace, xs.asRestriction());
		} else {
			// This is just a restriction on a basic type, find parent and map
			// it to the type
			String baseTypeName = typeName;
			while (xs != null && !basicTypes.contains(baseTypeName)) {
				xs = xs.getBaseType().asSimpleType();
				if (xs != null) {
					baseTypeName = xs.getName();
				}
			}
			simpleTypes.put(typeName, xs != null ? xs.getName() : "string");
		}
		return typeName;
	}

	/**
	 * @param cType
	 * @param elementName
	 * @param sset
	 */
	private String processComplexType(XSComplexType cType, String elementName,
			XSSchemaSet sset) {
		Struct st = null;
		XSType parent;
		String typeName = cType.getName();
		String nameSpace = cType.getTargetNamespace();
		if (typeName == null) {
			typeName = elementName != null ? elementName + "Type"
					: generateAnonymousName();
		}
		st = map.get(typeName);
		if (st == null) {
			st = new Struct(typeName,
					NamespaceConverter.convertFromSchema(nameSpace));
			map.put(typeName, st);

			parent = cType;
			while (parent != sset.getAnyType()) {
				if (parent.isComplexType()) {
					write(st, parent.asComplexType(), true, sset);
				}
				parent = parent.getBaseType();
			}

			processInheritance(st, cType, sset);
			st.setParent(cType.getBaseType().getName());
		}
		return typeName;
	}

	private int anonymousCounter = 0;

	/**
	 * @return
	 */
	private String generateAnonymousName() {
		anonymousCounter++;
		return String.format("Anonymous%03d", anonymousCounter);
	}

	private void write(Struct st, XSComplexType type, boolean goingup,
			XSSchemaSet xss) {
		XSParticle particle;
		Iterator<? extends XSAttributeUse> it;
		XSAttributeUse att;
		XSAttributeDecl decl;
		Iterator<? extends XSAttGroupDecl> itt;

		particle = type.getContentType().asParticle();
		if (particle != null) {
			write(st, particle.getTerm(), true, xss);
		}

		itt = type.getAttGroups().iterator();
		while (itt.hasNext()) {
			write(st, itt.next(), true);
		}

		it = type.getAttributeUses().iterator();
		while (it.hasNext()) {
			att = it.next();
			decl = att.getDecl();
			write(st, decl, goingup && att.isRequired());
		}
	}

	private void write(Struct st, XSAttributeDecl decl, boolean goingup) {
		if (decl.getType().isRestriction()
				&& (decl.getType().getName() == null || !basicTypes
						.contains(decl.getType().getName()))) {
			String typeName = processSimpleType(decl.getType(), decl.getName());
			st.addField(decl.getName(), typeName, goingup, false,
					decl.getFixedValue(), xsdMapping);
		} else if (decl.getType().isList()) {
			st.addField(decl.getName(), decl.getType().asList().getItemType()
					.getName(), goingup, true, null, xsdMapping);
		} else {
			st.addField(decl.getName(), decl.getType().getName(), goingup,
					false, decl.getFixedValue(), xsdMapping);
		}
	}

	private void createEnum(String name, String namespace,
			XSRestrictionSimpleType type) {
		Enumeration en;
		Iterator<? extends XSFacet> it;

		if (!enums.containsKey(name)) {
			type = type.asRestriction();
			en = new Enumeration(name,
					NamespaceConverter.convertFromSchema(namespace));
			it = type.getDeclaredFacets().iterator();
			while (it.hasNext()) {
				en.addString(it.next().getValue().value);
			}
			enums.put(name, en);
		}
	}

	private void write(Struct st, XSAttGroupDecl attGroup, boolean goingup) {
		Iterator<? extends XSAttributeUse> it;
		Iterator<? extends XSAttGroupDecl> itg;
		XSAttributeUse att;
		XSAttributeDecl decl;

		itg = attGroup.getAttGroups().iterator();

		while (itg.hasNext()) {
			write(st, itg.next(), goingup);
		}

		it = attGroup.getDeclaredAttributeUses().iterator();
		while (it.hasNext()) {
			att = it.next();
			decl = att.getDecl();
			if (decl.getType().getName() == null) {
				if (decl.getType().isRestriction()) {
					String typeName = processSimpleType(decl.getType(),
							decl.getName());
					st.addField(decl.getName(), typeName,
							(goingup && att.isRequired()), false,
							decl.getFixedValue(), xsdMapping);
				}
			} else {
				write(st, decl, att.isRequired());
			}
		}
	}

	private void processInheritance(Struct st, XSComplexType cType,
			XSSchemaSet sset) {
		Iterator<XSType> ity;
		XSType xt;
		XSParticle particle;
		XSComplexType type;
		Iterator<? extends XSAttributeUse> itau;
		XSAttributeUse att;
		XSAttributeDecl decl;
		Iterator<? extends XSAttGroupDecl> itagd;

		ity = sset.iterateTypes();
		while (ity.hasNext()) {
			xt = ity.next();
			if (xt.getBaseType() == cType) {
				// NOTE: the below code is almost identical to the contents of
				// the write() function which takes an XSComplexType argument -
				// the only difference is in the "goingup" variable passed to
				// the other write() functions internally
				
				type = xt.asComplexType();
				
				particle = type.getContentType().asParticle();
				if (particle != null) {
					write(st, particle.getTerm(), false, sset);
				}

				itagd = type.getAttGroups().iterator();
				while (itagd.hasNext()) {
					write(st, itagd.next(), false);
				}

				itau = type.getAttributeUses().iterator();
				while (itau.hasNext()) {
					att = itau.next();
					decl = att.getDecl();
					write(st, decl, false);
				}

				processInheritance(st, xt.asComplexType(), sset);
			}
		}
	}

	private void write(Struct st, XSTerm term, boolean goingup, XSSchemaSet xss) {
		XSModelGroup modelGroup;
		XSParticle[] ps;
		XSParticle p;

		if (term != null && term.isModelGroup()) {
			modelGroup = term.asModelGroup();
			if (XSModelGroup.CHOICE.equals(modelGroup.getCompositor())) {
				goingup = false;
			}

			ps = modelGroup.getChildren();
			for (int i = 0; i < ps.length; i++) {
				p = ps[i];
				term = p.getTerm();
				if (term.isModelGroup()) {
					write(st, term, goingup, xss);
				} else if (term.isElementDecl()) {
					if (term.asElementDecl().getType().getName() == null) {
						final String typeName = processType(term
								.asElementDecl().getType(), term
								.asElementDecl().getName(), xss);
						String ns = null;
						if (map.containsKey(typeName)) {
							ns = map.get(typeName).getNamespace();
						}
						st.addField(term.asElementDecl().getName(), ns,
								typeName, (goingup && p.getMinOccurs()
										.intValue() != 0), p.getMaxOccurs()
										.intValue() != 1, term.asElementDecl()
										.getFixedValue(), xsdMapping);
					} else {
						st.addField(term.asElementDecl().getName(),
								term.asElementDecl().getType()
										.getTargetNamespace(), term
										.asElementDecl().getType().getName(),
								(goingup && p.getMinOccurs().intValue() != 0),
								p.getMaxOccurs().intValue() != 1, term
										.asElementDecl().getFixedValue(),
								xsdMapping);
					}
				}
			}
		}
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		System.out.println(exception.getMessage() + " at "
				+ exception.getSystemId());
		exception.printStackTrace();
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		System.out.println(exception.getMessage() + " at "
				+ exception.getSystemId());
		exception.printStackTrace();
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		System.out.println(exception.getMessage() + " at "
				+ exception.getSystemId());
		exception.printStackTrace();
	}

	public void addMarshaller(IMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setNestEnums(boolean nestEnums) {
		this.nestEnums = nestEnums;
	}

	public boolean isNestEnums() {
		return nestEnums;
	}

	private OutputStream os(String namespace) throws IOException {
		return writer.getStream(namespace);
	}

	public void setWriter(OutputWriter writer) {
		this.writer = writer;
	}

	public void setEnumOrderStart(int enumOrderStart) {
		this.enumOrderStart = enumOrderStart;
	}

	public void setTypeInEnums(boolean typeInEnums) {
		this.typeInEnums = typeInEnums;
	}
}
