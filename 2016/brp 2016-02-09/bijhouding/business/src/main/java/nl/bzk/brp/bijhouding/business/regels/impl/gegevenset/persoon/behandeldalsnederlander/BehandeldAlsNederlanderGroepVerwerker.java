/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.behandeldalsnederlander;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;

/**
 * Verwerkingsregel van de behandeld als nederlander indicatie.
 */
public class BehandeldAlsNederlanderGroepVerwerker extends AbstractPersoonIndicatieGroepVerwerker<
    HisPersoonIndicatieBehandeldAlsNederlanderModel, PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param persoonIndicatieBericht the persoon indicatie bericht
     * @param persoonIndicatieModel the persoon indicatie model
     * @param actie de actie
     */
    public BehandeldAlsNederlanderGroepVerwerker(final PersoonIndicatieBericht persoonIndicatieBericht,
            final PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl persoonIndicatieModel,
            final ActieModel actie)
    {
        super(persoonIndicatieBericht, persoonIndicatieModel, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00007;
    }

    @Override
    protected final HisPersoonIndicatieBehandeldAlsNederlanderModel maakHisRecord() {
        return new HisPersoonIndicatieBehandeldAlsNederlanderModel(getModel(),
                getBericht().getStandaard(), getBericht().getStandaard(), getActie());
    }

}
