/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonPersoonskaartGroepBasis;


/**
 * Vorm van historie: alleen formeel. Immers, een persoonskaart is wel-of-niet geconverteerd, en een persoonskaart
 * 'verhuist' niet mee (c.q.: de plaats van een persoonskaart veranderd in principe niet).
 *
 *
 *
 */
public abstract class AbstractPersoonPersoonskaartGroepBericht extends AbstractGroepBericht implements
        PersoonPersoonskaartGroepBasis
{

    private String gemeentePersoonskaartCode;
    private Partij gemeentePersoonskaart;
    private JaNee  indicatiePersoonskaartVolledigGeconverteerd;

    /**
     * Retourneert Gemeente persoonskaart van Persoonskaart.
     *
     * @return Gemeente persoonskaart.
     */
    public String getGemeentePersoonskaartCode() {
        return gemeentePersoonskaartCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Zet Gemeente persoonskaart van Persoonskaart.
     *
     * @param gemeentePersoonskaartCode Gemeente persoonskaart.
     */
    public void setGemeentePersoonskaartCode(final String gemeentePersoonskaartCode) {
        this.gemeentePersoonskaartCode = gemeentePersoonskaartCode;
    }

    /**
     * Zet Gemeente persoonskaart van Persoonskaart.
     *
     * @param gemeentePersoonskaart Gemeente persoonskaart.
     */
    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
        this.gemeentePersoonskaart = gemeentePersoonskaart;
    }

    /**
     * Zet Persoonskaart volledig geconverteerd? van Persoonskaart.
     *
     * @param indicatiePersoonskaartVolledigGeconverteerd Persoonskaart volledig geconverteerd?.
     */
    public void setIndicatiePersoonskaartVolledigGeconverteerd(final JaNee indicatiePersoonskaartVolledigGeconverteerd)
    {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }

}
