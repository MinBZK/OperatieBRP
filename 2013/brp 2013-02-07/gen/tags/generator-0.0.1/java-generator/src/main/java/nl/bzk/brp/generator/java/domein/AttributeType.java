/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.ArrayList;
import java.util.List;


public class AttributeType {

    private List<String> imports = new ArrayList<String>();

    private String       name;

    private String       packageName;

    private Class<?>        baseType;

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

    public AttributeType(String naam) {
        this.name = naam;
    }

    public String getName() {
        return name.replace("ID", "Id");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getBaseTypeName() {
        return baseType.getSimpleName();
    }

    public String getBaseTypeImport() {
        return baseType.getName();
    }

    public void setBaseType(Class<?> baseType) {
        this.baseType = baseType;
    }

    public boolean isStatisch() {
        return statisch;
    }

    public void setStatisch(boolean statisch) {
        this.statisch = statisch;

    }

    public void setJavaDoc(String documentation) {
        this.javaDoc = documentation;

    }

    public String getJavaDoc() {
        return javaDoc;
    }

    public void addImport(String importForBasisType) {
        imports.add(importForBasisType);
    }

}
