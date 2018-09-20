/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging.zoekcriteria;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;

/**
 * Zoek crieria voor vraag Kandidaat Vader.
 *
 */
public class ZoekCriteriaKandidaatVader extends ZoekCriteriaBsn {

    private Datum geboortedatumKind;

    public Datum getGeboortedatumKind() {
        return geboortedatumKind;
    }

    public void setGeboortedatumKind(final Datum geboortedatumKind) {
        this.geboortedatumKind = geboortedatumKind;
    }

}
