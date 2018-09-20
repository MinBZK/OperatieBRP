/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator;

import nl.bzk.brp.generator.java.domein.Identifier;

import org.apache.commons.lang3.StringUtils;


public enum GenerationPackageNames {

    BRP_MODEL_BASEPACKAGE(null, "nl.bzk.brp.model"), //
    BRP_MODEL_BASIS_PACKAGE(BRP_MODEL_BASEPACKAGE, "basis"), //
    ATTRIBUUTTYPE_PACKAGE(BRP_MODEL_BASEPACKAGE, "attribuuttype"), //
    ATTRIBUUTTYPE_BASIS_PACKAGE(ATTRIBUUTTYPE_PACKAGE, "basis"), //
    /* ObjectType packages */
    OBJECTTYPE_PACKAGE(BRP_MODEL_BASEPACKAGE, "objecttype"), //
    OBJECTTYPE_LOGISCH_PACKAGE(OBJECTTYPE_PACKAGE, "logisch"), //
    OBJECTTYPE_LOGISCH_BASIS_PACKAGE(OBJECTTYPE_LOGISCH_PACKAGE, "basis"), //
    OBJECTTYPE_OPERATIONEEL_PACKAGE(OBJECTTYPE_PACKAGE, "operationeel"), //
    OBJECTTYPE_OPERATIONEEL_BASIS_PACKAGE(OBJECTTYPE_OPERATIONEEL_PACKAGE, "basis"), //
    OBJECTTYPE_OPERATIONEEL_STATISCH_PACKAGE(OBJECTTYPE_OPERATIONEEL_PACKAGE, "statisch"), //
    OBJECTTYPE_BERICHT_PACKAGE(OBJECTTYPE_PACKAGE, "bericht"), //
    OBJECTTYPE_BERICHT_BASIS_PACKAGE(OBJECTTYPE_BERICHT_PACKAGE, "basis"), //
    /* Groep packages */
    GROEP_PACKAGE(BRP_MODEL_BASEPACKAGE, "groep"), //
    GROEP_BERICHT_PACKAGE(GROEP_PACKAGE, "bericht"), //
    GROEP_LOGISCH_PACKAGE(GROEP_PACKAGE, "logisch"), //
    GROEP_LOGISCH_BASIS_PACKAGE(GROEP_LOGISCH_PACKAGE, "basis"), //
    GROEP_OPERATIONEEL_PACKAGE(GROEP_PACKAGE, "operationeel"), //
    GROEP_OPERATIONEEL_ACTUEEL_PACKAGE(GROEP_OPERATIONEEL_PACKAGE, "actueel"), //
    GROEP_OPERATIONEEL_ACTUEEL_BASIS_PACKAGE(GROEP_OPERATIONEEL_ACTUEEL_PACKAGE, "basis"), //
    GROEP_OPERATIONEEL_HISTORISCH_PACKAGE(GROEP_OPERATIONEEL_PACKAGE, "historisch"), //
    GROEP_OPERATIONEEL_HISTORISCH_BASIS_PACKAGE(GROEP_OPERATIONEEL_HISTORISCH_PACKAGE, "basis"); //

    private GenerationPackageNames basePackage;
    private String                 subPackage;

    private GenerationPackageNames(final GenerationPackageNames basePackageName, final String subPackageName) {
        this.basePackage = basePackageName;
        this.subPackage = subPackageName;
    }

    public GenerationPackageNames getBasePackage() {
        return basePackage;
    }

    public String getBasePackageName() {
        if (basePackage != null) {
            return basePackage.getBasePackageName();
        } else {
            return null;
        }
    }

    public String getSubPackage() {
        return subPackage;
    }

    public String getPackage() {
        if (basePackage != null) {
            return String.format("%s.%s", basePackage.getPackage(), subPackage);
        } else {
            return subPackage;
        }
    }

    public String getPackageAsPath() {
        return StringUtils.replace(getPackage(), ".", "/");
    }

    public static String createImportStatement(final GenerationPackageNames basePackage, final String importClass) {
        if (basePackage == null) {
            throw new IllegalArgumentException("basisPackage moet meegegeven worden voor een import");
        }

        return String.format("%s.%s", basePackage.getPackage(), importClass);

    }

    public String createImportStatement(final Identifier identifier) {
        return createImportStatement(this,identifier.getUpperCamel());
    }
 
}
