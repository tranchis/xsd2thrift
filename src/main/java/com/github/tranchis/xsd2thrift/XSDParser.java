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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.github.tranchis.xsd2thrift.marshal.IMarshaller;
import com.sun.xml.xsom.parser.XSOMParser;
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

public class XSDParser implements ErrorHandler {
    private File f;
    private Map<String, Struct> map;
    private Map<String, Enumeration> enums;
    private Map<String, String> simpleTypes;
    private Set<String> keywords, basicTypes;
    private TreeMap<String, String> xsdMapping;
    private IMarshaller marshaller;
    private OutputStream os;
    private String namespace;
    private boolean nestEnums = true;
    private boolean forceCircular = false;
    private boolean debug = false;
    private String postfix = "Type";
    
    public XSDParser(String stFile) {
        this.xsdMapping = new TreeMap<String, String>();
        init(stFile);
    }

    private void init(String stFile) {
        os = System.out;

        this.f = new File(stFile);
        map = new HashMap<String, Struct>();
        enums = new HashMap<String, Enumeration>();
        simpleTypes = new HashMap<String, String>();
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
        basicTypes.add("binary");
        basicTypes.add("boolean");
        basicTypes.add("date");
        basicTypes.add("dateTime");
        basicTypes.add("decimal");
        basicTypes.add("double");
        basicTypes.add("byte");
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

    public void setPostfix(String pf) { 
    	postfix = pf;
    }
    
    public void forceCircular(boolean fc) {
    	forceCircular = fc;
    }
    
    public void parse() throws Exception {
        XSOMParser parser;

        parser = new XSOMParser();
        parser.setErrorHandler(this);
        parser.parse(f);

        interpretResult(parser.getResult());
        writeMap();
    }

    private void writeMap() throws Exception {
        Iterator<Struct> its;
        Struct st;
        Set<Struct> ss;
        Set<String> declared;
        Iterator<String> ite;
        boolean bModified;

        os.write(marshaller.writeHeader(namespace).getBytes());

        st = createSuperObject();

        if (!marshaller.isNestedEnums() || !isNestEnums()) {
            ite = enums.keySet().iterator();
            while (ite.hasNext()) {
                writeEnum(ite.next());
            }
        }

        ss = new HashSet<Struct>(map.values());
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
            Set<String> requiredTypes = new HashSet<String>();
            Set<String> notYetDeclaredTypes = new HashSet<String>();
            for (Struct s : ss) {
            	if (debug) { System.err.println("write: notYetDeclared: " + s.getName()); }
                requiredTypes.addAll(s.getTypes());
                notYetDeclaredTypes.add(s.getName());
            }
            requiredTypes.removeAll(declared);
            requiredTypes.removeAll(notYetDeclaredTypes);
            if (requiredTypes.isEmpty()) {
                // Circular dependencies have been detected
                if (marshaller.isCircularDependencySupported() || forceCircular == true) {
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
                System.err.println("Source schema contains references missing types.");
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

    private void writeStruct(Struct st, Set<String> declared) throws IOException {
        Iterator<Field> itf;
        Field f;
        String fname, type;
        Set<String> usedInEnums;
        int order;

        os.write(marshaller.writeStructHeader(escape(st.getName())).getBytes());
        itf = st.getFields().iterator();
        usedInEnums = new TreeSet<String>();
        order = 1;
        while (itf.hasNext()) {
            f = itf.next();
            fname = f.getName();
            type = f.getType();

            if (isNestEnums() && marshaller.isNestedEnums() && enums.containsKey(type) && !usedInEnums.contains(type)) {
                usedInEnums.add(type);
                writeEnum(type);
            }

            if (simpleTypes.containsKey(type)) {
                type = simpleTypes.get(type);
            }
            if (!map.keySet().contains(type) && !basicTypes.contains(type) && !enums.containsKey(type)) {
                type = "binary";
            }
            if (type.equals(fname)) {
                fname = "_" + fname;
            }
            if (marshaller.getTypeMapping(type) != null) {
                type = marshaller.getTypeMapping(type);
            }
            os.write(marshaller.writeStructParameter(order, f.isRequired(), f.isRepeat(), escape(fname), escapeType(type)).getBytes());
            order = order + 1;
        }
        os.write(marshaller.writeStructFooter().getBytes());
        declared.add(st.getName());
    }

	private void writeEnum(String type) throws IOException {
		String enumValue;
		Enumeration en;
		Iterator<String> itg;
		en = enums.get(type);
		enumValue = escape(en.getName());
		os.write(marshaller.writeEnumHeader(enumValue).getBytes());
		itg = en.iterator();
		int enumOrder = 1;

		if (itg.hasNext()) {
		    while (itg.hasNext()) {
		        os.write(marshaller.writeEnumValue(enumOrder, escape(en.getName() + "_" + itg.next())).getBytes());
		        enumOrder++;
		    }
		} else {
		    os.write(marshaller.writeEnumValue(enumOrder, escape(en.getName() + "_UnspecifiedValue")).getBytes());
		}

		os.write(marshaller.writeEnumFooter().getBytes());
	}

    private Struct createSuperObject() {
        Struct st;

        st = new Struct("UnspecifiedType");

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
        
        if (!Character.isJavaIdentifierStart(nameChars[0]) || keywords.contains(res)) {
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

        if (el.getType() instanceof XSComplexType && el.getType() != sset.getAnyType()) {
            cType = (XSComplexType) el.getType();
            processComplexType(cType, el.getName(), sset);
        } else if (el.getType() instanceof XSSimpleType && el.getType() != sset.getAnySimpleType()) {
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

        if (debug) { System.err.println("PST typeName:" + typeName + " elName: " + elementName); }

        if (typeName == null) {
            typeName = elementName != null ? elementName + postfix : generateAnonymousName();
        }

        //System.out.println("PST " + typeName);
		if (xs.isRestriction() && xs.getFacet("enumeration") != null) {
			if (debug) { System.err.println("\tPST isRestrict && getFacet/enum -> createEnum: " + xs.getFacet("enumeration")); }
            createEnum(typeName, xs.asRestriction());
        } else {
        	if (debug) { System.err.println("\tPST restrict on basic type, find parent"); }
            //This is just a restriction on a basic type, find parent and map it to the type
            while (xs != null && xs.getName() != null && !basicTypes.contains(xs.getName())) {
                xs = xs.getBaseType().asSimpleType();
                if (debug) { System.err.println("\t\tparent " + xs.getName()); }
            }

            if (debug) { System.err.println("\tPST record simpleType name:" + typeName + " val: " +  ((xs != null && xs.getName() != null) ? xs.getName() : "string")); }
            simpleTypes.put(typeName, (xs != null && xs.getName() != null) ? xs.getName() : "string");
        }

        return typeName;
    }

    /**
     * @param cType
     * @param elementName 
     * @param sset
     */
    private String processComplexType(XSComplexType cType, String elementName, XSSchemaSet sset) {
        Struct st = null;
        XSType parent;
        String typeName = cType.getName();
        if (typeName == null) {
        	if (debug) { System.err.println("PCT typeName is null"); }
            typeName = elementName != null ? elementName + postfix : generateAnonymousName();
        }
        if (debug) { System.err.println("PCT " + typeName); }

        st = map.get(typeName);
        if (st == null) {
        	if (debug) { System.err.println("\tPCT " + typeName + " doesnt exist in map, adding."); }
            st = new Struct(typeName);
            map.put(typeName, st);

            parent = cType;

            while (parent != sset.getAnyType()) { 
            	if (debug) { System.err.println("\tPCT parent.getName " + parent.getName()); }
                if (parent.isComplexType()) {
                	if (debug) { System.err.println("\t\tPCT parent isComplex"); }
                    write(st, parent.asComplexType(), true, sset);
                    parent = parent.getBaseType();
                } else {
                	if (debug) { System.err.println("\t\tPCT parent is not complex"); }
                	break;
                }
            }
            if (debug) { System.err.println("\t\tProcess inheritance"); }
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
        if (debug) { System.err.println("generateAnon " + anonymousCounter); }

        return String.format("Anonymous%03d", anonymousCounter);
    }

    private void write(Struct st, XSComplexType type, boolean goingup, XSSchemaSet xss) {
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
        if (decl.getType().isRestriction() && decl.getType().getName() != null && !basicTypes.contains(decl.getType().getName())) {
            String typeName = processSimpleType(decl.getType(), decl.getName());
            if (debug) { System.err.println("\twrite2 isRestrict==true " + typeName); }

            st.addField(decl.getName(), typeName, goingup, false, decl.getFixedValue(), xsdMapping);
        } else if (decl.getType().isList()) {
            if (debug) { System.err.println("\twrite2 isList==true " + decl.getType().getName()); }

            st.addField(decl.getName(), decl.getType().asList().getItemType().getName(), goingup, true, null, xsdMapping);
        } else {
            if (debug) { System.err.println("\twrite2 isRestrict==false isList==false " + decl.getName()  + " " + decl.getType().getName()); }

            st.addField(decl.getName(), decl.getType().getName(), goingup, false, decl.getFixedValue(), xsdMapping);
        }
    }

    private void createEnum(String name, XSRestrictionSimpleType type) {
        Enumeration en;
        Iterator<? extends XSFacet> it;

        if (debug) { System.err.println("\t\t\tcreateEnum name:\"" + name + "\""); }
        if (!enums.containsKey(name)) {
            type = type.asRestriction();
            en = new Enumeration(name);
            it = type.getDeclaredFacets().iterator();
            while (it.hasNext()) {
            	String jj = it.next().getValue().value;
            	if (debug) { System.err.println("\t\t\t\tcreateEnum add:" + jj); }
                en.addString(jj);
            }
            if (debug) { System.err.println("\t\t\tcreateEnum enum.puts name:\"" + name + "\""); }
            enums.put(name, en);
        } else {
        	if (debug) { System.err.println("\t\t\tcreateEnum: enums already containsKey name:\"" + name + "\""); }
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
                    String typeName = processSimpleType(decl.getType(), decl.getName());
                    if (debug) { System.err.println("\twrite3 isRestrict==true " + typeName); }
                    st.addField(decl.getName(), typeName, goingup, false, decl.getFixedValue(), xsdMapping);
                }
            } else {
                if (debug) { System.err.println("\twrite3 getName!=null " + decl.getType().getName()); }

                write(st, decl, true);
            }
        }
    }

    private void processInheritance(Struct st, XSComplexType cType, XSSchemaSet sset) {
        Iterator<XSType> ity;
        XSType xt;
        XSParticle particle;

        ity = sset.iterateTypes();
        while (ity.hasNext()) {
            xt = ity.next();
            if (debug) { System.err.println("PI struct:" + st.getName() + " | sset.base: " + xt.getBaseType().getName() + " ==? " + cType.getBaseType().getName()); }
            if (debug) { System.err.println("\tbase type is " + simpleTypes.get(cType.getBaseType().getName())); }
            
            String s1 = (xt != null && xt.getBaseType() != null) ? xt.getBaseType().getName() : null;
            String s2 = (cType != null) ? cType.getName() : null;
            if (s1 != null && s2 != null && s1.equals(s2)) {
            	if (debug) { System.err.println("\tPI yes xt == cType"); }
                particle = xt.asComplexType().getContentType().asParticle();
                if (particle != null) {
                    write(st, particle.getTerm(), false, sset);
                }

                processInheritance(st, xt.asComplexType(), sset);
            } else {
            	if (debug) { System.err.println("\tPI NO xt != cType"); }
            }
        }
    }

    private void write(Struct st, XSTerm term, boolean goingup, XSSchemaSet xss) {
        XSModelGroup modelGroup;
        XSParticle[] ps;
        XSParticle p;

        if (term != null && term.isModelGroup()) {
            modelGroup = term.asModelGroup();
            ps = modelGroup.getChildren();
            for (int i = 0; i < ps.length; i++) {
                p = ps[i];
                term = p.getTerm();
                if (term.isModelGroup()) {
                    write(st, term, goingup, xss);
                } else if (term.isElementDecl()) {
                    if (term.asElementDecl().getType().getName() == null) {
                        final String typeName = processType(term.asElementDecl().getType(), term.asElementDecl().getName(), xss);
                        if (debug) { System.err.println("\twrite4 getType.getName==null so " + typeName); }
                        st.addField(term.asElementDecl().getName(), typeName, goingup, p.getMaxOccurs().intValue() != 1, term
                                .asElementDecl().getFixedValue(), xsdMapping);
                    } else {
                        if (debug) { System.err.println("\twrite4 " + term.asElementDecl().getName()); }
                        st.addField(term.asElementDecl().getName(), term.asElementDecl().getType().getName(), goingup, p.getMaxOccurs()
                                .intValue() != 1, term.asElementDecl().getFixedValue(), xsdMapping);
                    }
                }
            }
        }
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage() + " at " + exception.getSystemId());
        exception.printStackTrace();
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage() + " at " + exception.getSystemId());
        exception.printStackTrace();
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage() + " at " + exception.getSystemId());
        exception.printStackTrace();
    }

    public void addMarshaller(IMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setOutputStream(FileOutputStream os) {
        this.os = os;
    }

    public void setPackage(String namespace) {
        this.namespace = namespace;
    }

	public void setNestEnums(boolean nestEnums) {
		this.nestEnums = nestEnums;
	}
	
	public boolean isNestEnums() {
		return nestEnums;
	}
}
