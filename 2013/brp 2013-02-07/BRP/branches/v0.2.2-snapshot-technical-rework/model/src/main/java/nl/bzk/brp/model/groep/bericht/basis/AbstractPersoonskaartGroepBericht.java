/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonPersoonskaartGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;


/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonskaartGroepBericht extends AbstractGroepBericht implements PersoonPersoonskaartGroepBasis {
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
