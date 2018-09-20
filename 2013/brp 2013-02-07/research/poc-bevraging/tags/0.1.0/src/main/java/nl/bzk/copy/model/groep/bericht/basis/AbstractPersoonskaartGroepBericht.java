/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonskaartGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;


/**
 * .
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonskaartGroepBericht extends AbstractGroepBericht
        implements PersoonskaartGroepBasis
{
    private Partij gemeentePersoonskaart;
    private JaNee indicatiePersoonskaartVolledigGeconverteerd;

    @Override
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    @Override
    public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    public void setGemeentePersoonskaart(final Partij gemeentePersoonskaart) {
        this.gemeentePersoonskaart = gemeentePersoonskaart;
    }

    public void setIndicatiePersoonskaartVolledigGeconverteerd(
            final JaNee indicatiePersoonskaartVolledigGeconverteerd)
    {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }
}
