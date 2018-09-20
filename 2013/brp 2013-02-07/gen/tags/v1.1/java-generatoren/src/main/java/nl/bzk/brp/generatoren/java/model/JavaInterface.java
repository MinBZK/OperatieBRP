/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;


/**
 * Klasse die een java interface voorstelt.
 */
public class JavaInterface extends AbstractJavaType implements Cloneable {

    /**
     * Constructor.
     * @param javaType het java type.
     * @param javaDoc Documentatie voor de interface.
     */
    public JavaInterface(final JavaType javaType, final String javaDoc) {
        this(javaType.getNaam(), javaDoc, javaType.getPackagePad());
    }

    /**
     * Constructor.
     * @param naam Naam van de interface.
     * @param javaDoc Documentatie voor de interface.
     * @param generatiePackage Package waartoe de interface behoort.
     */
    public JavaInterface(final String naam, final String javaDoc, final String generatiePackage) {
        super(naam, javaDoc, generatiePackage);
    }

}
