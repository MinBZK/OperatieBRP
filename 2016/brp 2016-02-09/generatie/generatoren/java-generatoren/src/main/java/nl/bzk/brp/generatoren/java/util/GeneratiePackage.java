/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Deze enum bevat constanten voor java package namen.
 */
public enum GeneratiePackage {
    /** nl.bzk.brp .**/
    BRP_BASEPACKAGE(null, "nl.bzk.brp"),
    /** nl.bzk.brp.model .**/
    BRP_MODEL_BASEPACKAGE(BRP_BASEPACKAGE, "model"),
    /** nl.bzk.brp.model.basis .**/
    BRP_MODEL_BASIS_PACKAGE(BRP_MODEL_BASEPACKAGE, "basis"),
    /** nl.bzk.brp.model.algemeen. **/
    BRP_MODEL_ALGEMEEN_PACKAGE(BRP_MODEL_BASEPACKAGE, "algemeen"),
    /** nl.bzk.brp.model.algemeen.attribuuttype .**/
    ATTRIBUUTTYPE_PACKAGE(BRP_MODEL_ALGEMEEN_PACKAGE, "attribuuttype"),
    /** nl.bzk.brp.model.algemeen.stamgegeven .**/
    STAMGEGEVEN_STATISCH_PACKAGE(BRP_MODEL_ALGEMEEN_PACKAGE, "stamgegeven"),
    /** nl.bzk.brp.model.algemeen.stamgegeven .**/
    STAMGEGEVEN_DYNAMISCH_PACKAGE(BRP_MODEL_ALGEMEEN_PACKAGE, "stamgegeven"),
    /** nl.bzk.brp.model.algemeen.stamgegeven .**/
    BEHEER_PACKAGE(BRP_MODEL_BASEPACKAGE, "beheer"),
    /** nl.bzk.brp.model.logisch .**/
    OBJECTTYPE_LOGISCH_PACKAGE(BRP_MODEL_BASEPACKAGE, "logisch"),
    /** nl.bzk.brp.model.operationeel .**/
    OBJECTTYPE_OPERATIONEEL_PACKAGE(BRP_MODEL_BASEPACKAGE, "operationeel"),
    /** nl.bzk.brp.model.bericht .**/
    OBJECTTYPE_BERICHT_PACKAGE(BRP_MODEL_BASEPACKAGE, "bericht"),
    /** nl.bzk.brp.model.hisvolledig .**/
    OBJECTTYPE_HISVOLLEDIG_INTERFACES_PACKAGE(BRP_MODEL_BASEPACKAGE, "hisvolledig"),
    /** nl.bzk.brp.model.hisvolledig .**/
    OBJECTTYPE_HISVOLLEDIG_PACKAGE(OBJECTTYPE_HISVOLLEDIG_INTERFACES_PACKAGE, "impl"),
    /** nl.bzk.brp.model.levering .**/
    OBJECTTYPE_LEVERING_PACKAGE(BRP_MODEL_BASEPACKAGE, "levering"),
    /** nl.bzk.brp.model.hisvolledig.predikaat .**/
    OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_PACKAGE(OBJECTTYPE_HISVOLLEDIG_INTERFACES_PACKAGE, "predikaat"),
    /** nl.bzk.brp.model.hisvolledig.momentview .**/
    OBJECTTYPE_HISVOLLEDIG_MOMENT_VIEW_PACKAGE(OBJECTTYPE_HISVOLLEDIG_INTERFACES_PACKAGE, "momentview"),
    /** nl.bzk.brp.model.hisvolledig.predikaatview .**/
    OBJECTTYPE_HISVOLLEDIG_PREDIKAAT_VIEW_PACKAGE(OBJECTTYPE_HISVOLLEDIG_INTERFACES_PACKAGE, "predikaatview"),
    /** nl.bzk.brp.model.bericht .**/
    GROEP_BERICHT_PACKAGE(BRP_MODEL_BASEPACKAGE, "bericht"),
    /** nl.bzk.brp.model.logisch .**/
    GROEP_LOGISCH_PACKAGE(BRP_MODEL_BASEPACKAGE, "logisch"),
    /** nl.bzk.brp.model.operationeel .**/
    GROEP_OPERATIONEEL_PACKAGE(BRP_MODEL_BASEPACKAGE, "operationeel"),
    /** nl.bzk.brp.model.logisch.symbols. **/
    SYMBOLTABLE_LOGISCH_PACKAGE(OBJECTTYPE_LOGISCH_PACKAGE, "symbols"),
    /** nl.bzk.brp.util .**/
    BRP_BASEPACKAGE_UTIL(BRP_BASEPACKAGE, "util"),
    /** nl.bzk.brp.util.hisvolledig .**/
    BRP_BASEPACKAGE_UTIL_HISVOLLEDIG(BRP_BASEPACKAGE_UTIL, "hisvolledig");

    private GeneratiePackage basePackage;
    private String subPackage;

    /**
     * Constructor.
     *
     * @param basePackageName Basis package naam.
     * @param subPackageName Sub package naam.
     */
    private GeneratiePackage(final GeneratiePackage basePackageName, final String subPackageName) {
        this.basePackage = basePackageName;
        this.subPackage = subPackageName;
    }

    public GeneratiePackage getBasePackage() {
        return basePackage;
    }

    /**
     * Retourneert de base package naam van deze GeneratiePackage.
     *
     * @return Base package.
     */
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

    /**
     * Retourneert de volledige package naam van deze GeneratiePackage.
     *
     * @return De volledige package naam.
     */
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

    /**
     * Creeert een import statement op basis van de package en de class naam.
     * Oftewel 'Fully Qualified Class Name'.
     *
     * @param basePackage De package waar de class in zit.
     * @param className Naam van de te importeren class.
     * @return Fully Qualified Class Name.
     */
    public static String createImportClass(final GeneratiePackage basePackage, final String className) {
        if (basePackage == null) {
            throw new IllegalArgumentException("basisPackage moet meegegeven worden voor een import");
        }

        return String.format("%s.%s", basePackage.getPackage(), className);

    }

    /**
     * Genereert een import class name op basis van deze GeneratiePackage.
     *
     * @param naam Naam van de te importeren class.
     * @return Fully Qualified Class Name.
     */
    public String createImportClass(final String naam) {
        return createImportClass(this, naam);
    }
}
