/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAfgeleidAdministratiefGroepBasis;

/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonAfgeleidAdministratiefGroepBericht extends AbstractGroepBericht implements
        PersoonAfgeleidAdministratiefGroepBasis
{
    private JaNee     indGegevensInOnderzoek;
    private DatumTijd tijdstipLaatsteWijziging;

    @Override
    public JaNee getIndGegevensInOnderzoek() {
        return indGegevensInOnderzoek;
    }

    @Override
    public DatumTijd getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    public void setIndGegevensInOnderzoek(final JaNee indGegevensInOnderzoek) {
        this.indGegevensInOnderzoek = indGegevensInOnderzoek;
    }

    public void setTijdstipLaatsteWijziging(final DatumTijd tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
    }

}
