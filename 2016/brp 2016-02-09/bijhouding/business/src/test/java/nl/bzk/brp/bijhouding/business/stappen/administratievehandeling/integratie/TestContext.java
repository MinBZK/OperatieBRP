/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.integratie;

import nl.bzk.brp.business.stappen.StappenContext;


public class TestContext implements StappenContext {

    private Long referentieId;

    private Long resultaatId;


    public void setResultaatId(final Long resultaatId) {
        this.resultaatId = resultaatId;
    }

    @Override
    public Long getReferentieId() {
        return referentieId;
    }

    @Override
    public Long getResultaatId() {
        return resultaatId;
    }

    public void setReferentieId(final Long referentieId) {
        this.referentieId = referentieId;
    }

}
