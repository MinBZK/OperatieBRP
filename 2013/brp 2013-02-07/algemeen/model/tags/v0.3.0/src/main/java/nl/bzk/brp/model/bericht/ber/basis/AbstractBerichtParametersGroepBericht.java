/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.ber.basis.BerichtParametersGroepBasis;


/**
 *
 *
 */
public abstract class AbstractBerichtParametersGroepBericht extends AbstractGroepBericht implements
        BerichtParametersGroepBasis
{

    private Verwerkingswijze    verwerkingswijze;
    private Datum               peilmomentMaterieel;
    private DatumTijd           peilmomentFormeel;
    private Burgerservicenummer aanschouwer;

    /**
     * {@inheritDoc}
     */
    @Override
    public Verwerkingswijze getVerwerkingswijze() {
        return verwerkingswijze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getPeilmomentMaterieel() {
        return peilmomentMaterieel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getPeilmomentFormeel() {
        return peilmomentFormeel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Burgerservicenummer getAanschouwer() {
        return aanschouwer;
    }

    /**
     * Zet Verwerkingswijze van Parameters.
     *
     * @param verwerkingswijze Verwerkingswijze.
     */
    public void setVerwerkingswijze(final Verwerkingswijze verwerkingswijze) {
        this.verwerkingswijze = verwerkingswijze;
    }

    /**
     * Zet Peilmoment Materieel van Parameters.
     *
     * @param peilmomentMaterieel Peilmoment Materieel.
     */
    public void setPeilmomentMaterieel(final Datum peilmomentMaterieel) {
        this.peilmomentMaterieel = peilmomentMaterieel;
    }

    /**
     * Zet Peilmoment Formeel van Parameters.
     *
     * @param peilmomentFormeel Peilmoment Formeel.
     */
    public void setPeilmomentFormeel(final DatumTijd peilmomentFormeel) {
        this.peilmomentFormeel = peilmomentFormeel;
    }

    /**
     * Zet Aanschouwer van Parameters.
     *
     * @param aanschouwer Aanschouwer.
     */
    public void setAanschouwer(final Burgerservicenummer aanschouwer) {
        this.aanschouwer = aanschouwer;
    }

}
