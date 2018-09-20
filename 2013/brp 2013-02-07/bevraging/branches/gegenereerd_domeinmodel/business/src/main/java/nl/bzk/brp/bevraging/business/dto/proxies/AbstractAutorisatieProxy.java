/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.proxies;

import java.util.HashSet;
import java.util.Set;


/**
 * Superclass voor alle proxy classes ten behoeven van gegevensautorisatie.
 * @brp.bedrijfsregel BRAU0018
 */
public abstract class AbstractAutorisatieProxy {

    private final Set<String> toegestaneVelden;

    /**
     * Constructor die direct de toegestane velden initialiseert.
     *
     * @param toegestaneVelden de java namen van de velden die doorgegeven mogen worden
     */
    protected AbstractAutorisatieProxy(final Set<String> toegestaneVelden) {
        this.toegestaneVelden = new HashSet<String>();
        for (String veld : toegestaneVelden) {
            this.toegestaneVelden.add(veld.toLowerCase());
        }
    }

    /**
     * Controleert of het opgegeven veld deel uitmaakt van de toegestane velden en als dat zo is, wordt de opgegeven
     * waarde geretourneerd. Merk op dat de veld naam case insensitive wordt vergeleken. Indien het veld niet is
     * toegestaan wordt <code>null</code> geretourneerd.
     *
     * @param <T> het java type van het veld
     * @param waarde de waarde van het veld uit de onderliggende persoon
     * @param veld de java naam van het veld (niet hoofdlettergevoelig)
     * @return de gegeven waarde als het veld is toegestaan, anders null
     * @brp.bedrijfsregel BRAU0045
     */
    protected <T> T filter(final T waarde, final String veld) {
        T resultaat = null;
        if (toegestaneVelden.contains(veld.toLowerCase())) {
            resultaat = waarde;
        }
        return resultaat;
    }

}
