/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.vastgesteldnietnederlander;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;

/**
 * VR00006.
 * Verwerkingsregel voor de vastgesteld niet Nederlander indicatie.
 *
 * @brp.bedrijfsregel VR00006
 */
public class VastgesteldNietNederlanderGroepVerwerker extends AbstractPersoonIndicatieGroepVerwerker<
        HisPersoonIndicatieVastgesteldNietNederlanderModel, PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param persoonIndicatieBericht the persoon indicatie bericht
     * @param persoonIndicatieModel   the persoon indicatie model
     * @param actie                   de actie
     */
    public VastgesteldNietNederlanderGroepVerwerker(
            final PersoonIndicatieBericht persoonIndicatieBericht,
            final PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl persoonIndicatieModel,
            final ActieModel actie)
    {
        super(persoonIndicatieBericht, persoonIndicatieModel, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00006;
    }

    @Override
    protected HisPersoonIndicatieVastgesteldNietNederlanderModel maakHisRecord() {
        return new HisPersoonIndicatieVastgesteldNietNederlanderModel(getModel(),
                getBericht().getStandaard(), getBericht().getStandaard(), getActie());
    }

}
