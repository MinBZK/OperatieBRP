/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Representeert een Java enum type.
 */
public class JavaEnumeratie extends AbstractJavaImplementatieType {

    private Set<EnumeratieWaarde> enumeratieWaarden = new LinkedHashSet<>();

    /**
     * Maak een nieuwe java enumeratie aan met de opgegeven data.
     *
     * @param javaType het java type
     * @param javaDoc de javadoc voor deze enum
     */
    public JavaEnumeratie(final JavaType javaType, final String javaDoc) {
        this(javaType.getNaam(), javaDoc, javaType.getPackagePad());
    }

    /**
     * Maak een nieuwe java enumeratie aan met de opgegeven data.
     *
     * @param naam de naam van de enum
     * @param javaDoc de javadoc voor deze enum
     * @param packageNaam de package voor deze enum
     */
    public JavaEnumeratie(final String naam, final String javaDoc, final String packageNaam) {
        super(naam, javaDoc, packageNaam);
    }

    public Set<EnumeratieWaarde> getEnumeratieWaarden() {
        return enumeratieWaarden;
    }

    /**
     * Voeg een nieuwe mogelijke waarde voor deze enumeratie toe.
     *
     * @param value de enumeratie waarde.
     */
    public final void voegEnumeratieWaardeToe(final EnumeratieWaarde value) {
        enumeratieWaarden.add(value);
    }

}
