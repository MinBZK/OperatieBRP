/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAfgeleidAdministratiefGroepBasis;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractPersoonAfgeleidAdministratiefGroepBericht extends AbstractGroepBericht implements
        PersoonAfgeleidAdministratiefGroepBasis
{

    private DatumTijd tijdstipLaatsteWijziging;
    private JaNee     indicatieGegevensInOnderzoek;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieGegevensInOnderzoek() {
        return indicatieGegevensInOnderzoek;
    }

    /**
     * Zet Tijdstip laatste wijziging van Afgeleid administratief.
     *
     * @param tijdstipLaatsteWijziging Tijdstip laatste wijziging.
     */
    public void setTijdstipLaatsteWijziging(final DatumTijd tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
    }

    /**
     * Zet Gegevens in onderzoek? van Afgeleid administratief.
     *
     * @param indicatieGegevensInOnderzoek Gegevens in onderzoek?.
     */
    public void setIndicatieGegevensInOnderzoek(final JaNee indicatieGegevensInOnderzoek) {
        this.indicatieGegevensInOnderzoek = indicatieGegevensInOnderzoek;
    }

}
