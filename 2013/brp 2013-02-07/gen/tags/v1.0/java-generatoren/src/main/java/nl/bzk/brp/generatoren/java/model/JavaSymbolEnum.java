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
public class JavaSymbolEnum extends AbstractJavaType {

    private Set<EnumeratieWaarde> enumeratieWaarden = new LinkedHashSet<EnumeratieWaarde>();

    /**
     * Maak een nieuwe java enumeratie aan met de opgegeven data.
     *
     * @param naam de naam van de enumeratie.
     * @param packageNaam naam van het package waartoe de enumeratie behoort.
     */
    public JavaSymbolEnum(final String naam, final String packageNaam) {
        super(naam, "", packageNaam);
    }

    public Set<EnumeratieWaarde> getSymbols() {
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
