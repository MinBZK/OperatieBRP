/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class AttributeType {

    private final List<String> imports = new ArrayList<String>();

    private final Identifier   name;

    private String       packageName;

    private Class<?>     baseType;

    private boolean      statisch;

    private String       javaDoc;

    public List<String> getImports() {
        return imports;
    }

    public String getExtendsFrom() {
        if (statisch) {
            return "AbstractStatischAttribuutType";
        } else {
            return "AbstractGegevensAttribuutType";
        }
    }

    public AttributeType(final String naam) {
        this.name = new Identifier(naam);
    }

    public Identifier getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public String getBaseTypeName() {
        return baseType.getSimpleName();
    }

    public String getBaseTypeImport() {
        return baseType.getName();
    }

    public void setBaseType(final Class<?> baseType) {
        this.baseType = baseType;
    }

    public boolean isStatisch() {
        return statisch;
    }

    public void setStatisch(final boolean statisch) {
        this.statisch = statisch;

    }

    public void setJavaDoc(final String documentation) {
        if(StringUtils.isBlank(documentation)) {
            javaDoc = null;
        }
        this.javaDoc = documentation;

    }

    public String getJavaDoc() {
        if(StringUtils.isBlank(javaDoc) || javaDoc.compareTo(".")==0) {
            return String.format("%s.",name.toString());
        }
        return javaDoc;
    }

    public void addImport(final String importForBasisType) {
        imports.add(importForBasisType);
    }

}
