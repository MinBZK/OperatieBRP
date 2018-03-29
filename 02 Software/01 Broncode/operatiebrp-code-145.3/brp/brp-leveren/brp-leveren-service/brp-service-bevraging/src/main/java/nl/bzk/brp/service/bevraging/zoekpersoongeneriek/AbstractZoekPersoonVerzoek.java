/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.service.algemeen.request.VerzoekBasis;

/**
 * Het {@link nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek} specifiek voor Zoek Persoon en Zoek Persoon op Adres.
 */
public abstract class AbstractZoekPersoonVerzoek extends VerzoekBasis implements ZoekPersoonGeneriekVerzoek {

    private ZoekPersoonParameters parameters;
    private Set<ZoekCriteria> zoekCriteriaPersoon;

    /**
     * Geeft de parameters binnen een bevragingsverzoek.
     * @return de parameters
     */
    @Override
    public final ZoekPersoonParameters getParameters() {
        if (parameters == null) {
            parameters = new ZoekPersoonParameters();
        }
        return parameters;
    }

    /**
     * @return de zoekcriteria
     */
    @Override
    public final Set<ZoekCriteria> getZoekCriteriaPersoon() {
        if (zoekCriteriaPersoon == null) {
            zoekCriteriaPersoon = new HashSet<>();
        }
        return zoekCriteriaPersoon;
    }
}
