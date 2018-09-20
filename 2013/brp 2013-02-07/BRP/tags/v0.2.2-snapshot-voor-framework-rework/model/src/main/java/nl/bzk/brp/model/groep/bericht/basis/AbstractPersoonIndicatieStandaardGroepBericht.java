/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonIndicatieStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;

/**
 * Persoon / Indicatie.
 */
public abstract class AbstractPersoonIndicatieStandaardGroepBericht extends AbstractGroepBericht
        implements PersoonIndicatieStandaardGroepBasis
{

    private Ja waarde;
    private SoortIndicatie soort;

    public Ja getWaarde() {
        return waarde;
    }

    public SoortIndicatie getSoort() {
        return soort;
    }

    public void setWaarde(final Ja waarde) {
        this.waarde = waarde;
    }

    public void setSoort(final SoortIndicatie soort) {
        this.soort = soort;
    }
}

