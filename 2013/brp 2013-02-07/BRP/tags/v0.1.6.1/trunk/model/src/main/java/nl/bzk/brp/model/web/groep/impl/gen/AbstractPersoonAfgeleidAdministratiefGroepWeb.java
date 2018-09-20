/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonAfgeleidAdministratiefGroepBasis;
import nl.bzk.brp.model.web.AbstractGroepWeb;

/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonAfgeleidAdministratiefGroepWeb extends AbstractGroepWeb
    implements PersoonAfgeleidAdministratiefGroepBasis
{
    private JaNee indGegevensInOnderzoek;
    private DatumTijd tijdstipLaatsteWijziging;

    @Override
    public JaNee getIndGegevensInOnderzoek() {
        return indGegevensInOnderzoek;
    }

    @Override
    public DatumTijd getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

}
