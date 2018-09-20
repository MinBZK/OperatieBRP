/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.NaamskeuzeOngeborenVruchtStandaardGroepBasis;


/**
 *
 *
 */
public abstract class AbstractNaamskeuzeOngeborenVruchtStandaardGroepBericht extends AbstractGroepBericht implements
        NaamskeuzeOngeborenVruchtStandaardGroepBasis
{

    private Datum datumNaamskeuzeOngeborenVrucht;

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumNaamskeuzeOngeborenVrucht() {
        return datumNaamskeuzeOngeborenVrucht;
    }

    /**
     * Zet Datum naamskeuze ongeboren vrucht van Standaard.
     *
     * @param datumNaamskeuzeOngeborenVrucht Datum naamskeuze ongeboren vrucht.
     */
    public void setDatumNaamskeuzeOngeborenVrucht(final Datum datumNaamskeuzeOngeborenVrucht) {
        this.datumNaamskeuzeOngeborenVrucht = datumNaamskeuzeOngeborenVrucht;
    }

}
