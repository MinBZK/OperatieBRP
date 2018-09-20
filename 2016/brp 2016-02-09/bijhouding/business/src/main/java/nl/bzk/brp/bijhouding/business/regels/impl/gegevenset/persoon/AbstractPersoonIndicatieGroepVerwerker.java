/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;

/**
 * Abstracte verwerkingsregel voor persoon indicaties.
 *
 * @param <T> subtype historie indicatie waarop deze verwerker van toepassing is
 * @param <M> subtype indicatie waarop deze verwerker van toepassing is
 */
public abstract class AbstractPersoonIndicatieGroepVerwerker<T extends HisPersoonIndicatieModel,
        M extends PersoonIndicatieHisVolledigImpl<T>>
        extends AbstractVerwerkingsregel<PersoonIndicatieBericht, M>
{

    /**
     * Forwarding constructor.
     *
     * @param persoonIndicatieBericht the persoon indicatie bericht
     * @param persoonIndicatieModel the persoon indicatie model
     * @param actie de actie
     */
    public AbstractPersoonIndicatieGroepVerwerker(final PersoonIndicatieBericht persoonIndicatieBericht,
                                                  final M persoonIndicatieModel,
                                                  final ActieModel actie)
    {
        super(persoonIndicatieBericht, persoonIndicatieModel, actie);
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        getModel().getPersoonIndicatieHistorie().voegToe(maakHisRecord());
    }

    /**
     * Maak een his record aan van het juiste type.
     *
     * @return het his record
     */
    protected abstract T maakHisRecord();

}
