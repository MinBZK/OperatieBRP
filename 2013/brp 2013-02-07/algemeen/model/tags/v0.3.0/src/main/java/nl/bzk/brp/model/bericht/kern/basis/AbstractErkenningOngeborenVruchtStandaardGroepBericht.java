/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.ErkenningOngeborenVruchtStandaardGroepBasis;


/**
 *
 *
 */
public abstract class AbstractErkenningOngeborenVruchtStandaardGroepBericht extends AbstractGroepBericht implements
        ErkenningOngeborenVruchtStandaardGroepBasis
{

    private Datum datumErkenningOngeborenVrucht;

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumErkenningOngeborenVrucht() {
        return datumErkenningOngeborenVrucht;
    }

    /**
     * Zet Datum erkenning ongeboren vrucht van Standaard.
     *
     * @param datumErkenningOngeborenVrucht Datum erkenning ongeboren vrucht.
     */
    public void setDatumErkenningOngeborenVrucht(final Datum datumErkenningOngeborenVrucht) {
        this.datumErkenningOngeborenVrucht = datumErkenningOngeborenVrucht;
    }

}
