/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


public class ObjectInterface {

    private Identifier        name;
    private String            javaDoc;
    private String            packageName;
    protected boolean         bInterface;
    private boolean           modifiableCode;

    private SortedSet<String> imports          = new TreeSet<String>();
    private List<Method>      accessors        = new ArrayList<Method>();
    private List<Method>      modifiers        = new ArrayList<Method>();
    private List<Method>      methods          = new ArrayList<Method>();
    private List<Identifier>  interfaces       = new ArrayList<Identifier>();
    private List<Identifier>  extendInterfaces = new ArrayList<Identifier>();

    public ObjectInterface(final String naam, final String documentatie) {
        this.name = new Identifier(naam);
        this.javaDoc = documentatie;
        this.bInterface = false;
    }

    public ObjectInterface(Identifier naam, String documentatie) {
        name = naam;
        this.javaDoc = documentatie;
        this.bInterface = false;
    }

    /**
     * @return the imports
     */
    public List<String> getImports() {
        List<String> resulaat = new ArrayList<String>();
        String vorige = "";
        for (String huidige : imports) {
            if (vorige.startsWith("java.") && !huidige.startsWith("java.") || vorige.startsWith("javax.")
                && !huidige.startsWith("javax."))
            {
                resulaat.add("");
            }
            resulaat.add("import " + huidige + ";");
            vorige = huidige;
        }
        return resulaat;
    }

    /**
     * @param imports the imports to set
     */
    public void addImports(final List<String> importLijst) {
        imports.addAll(importLijst);
    }

    public List<Method> getAccessors() {
        return accessors;
    }

    public Identifier getName() {
        return name;
    }

    public String getJavaDoc() {
        return javaDoc;
    }

    public boolean addAccessor(final Method accessor) {
        return accessors.add(accessor);
    }

    public boolean addModifier(final Method modifier) {
        return modifiers.add(modifier);
    }

    public boolean addMethod(final Method method) {
        return methods.add(method);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public boolean addImport(final String importStatement) {
        return imports.add(importStatement);

    }

    public void setJavaDoc(final String javaDocForObject) {
        this.javaDoc = javaDocForObject;

    }

    public List<Identifier> getInterfaces() {
        return interfaces;
    }

    public boolean addInterface(final String interfaceDefinition) {
        return interfaces.add(new Identifier(interfaceDefinition));
    }
    
    public boolean addInterface(final Identifier interfaceDefinition) {
        return interfaces.add(interfaceDefinition);
    }

    public List<Identifier> getExtendInterfaces() {
        return interfaces;
    }


    @Deprecated
    public boolean addExtendInterface(final String interfaceDefinition) {
        return interfaces.add(new Identifier(interfaceDefinition));
    }

    public boolean addExtendInterface(final Identifier interfaceDefinition) {
        return interfaces.add(interfaceDefinition);
    }

    /**
     * @return the modifiableCode
     */
    public boolean isModifiableCode() {
        return modifiableCode;
    }

    /**
     * @param modifiableCode the modifiableCode to set
     */
    public void setModifiableCode(boolean modifiableCode) {
        this.modifiableCode = modifiableCode;
    }

}
