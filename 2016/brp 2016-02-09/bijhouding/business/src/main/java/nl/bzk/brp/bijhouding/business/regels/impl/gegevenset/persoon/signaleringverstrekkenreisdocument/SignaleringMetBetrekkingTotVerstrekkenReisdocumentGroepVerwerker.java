/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.signaleringverstrekkenreisdocument;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;

/**
 * VR00019: Verwerken Indicatie signalering m.b.t. verstrekking reisdocument.
 */
public class SignaleringMetBetrekkingTotVerstrekkenReisdocumentGroepVerwerker
        extends AbstractPersoonIndicatieGroepVerwerker<
        HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel,
        PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param persoonIndicatieBericht the persoon indicatie bericht
     * @param persoonIndicatieModel   the persoon indicatie model
     * @param actie                   de actie
     */
    public SignaleringMetBetrekkingTotVerstrekkenReisdocumentGroepVerwerker(
            final PersoonIndicatieBericht persoonIndicatieBericht,
            final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl
                    persoonIndicatieModel,
            final ActieModel actie)
    {
        super(persoonIndicatieBericht, persoonIndicatieModel, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00019;
    }

    @Override
    protected HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel maakHisRecord() {
        return new HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel(getModel(),
                getBericht().getStandaard(), getBericht().getStandaard(), getActie());
    }

}
