/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractMaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

/**
 * Deze situatie herkent een gewijzigde rij waarbij DEG wijzigt of gelijk blijft of AAG gevuld is en wijzigt. In dit
 * geval wordt de oude rij een M-rij en wordt de nieuwe rij vastgelegd.
 */
public final class TransformatieDw023 extends AbstractTransformatie {

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        return isWijzigingOpMaterieleHistorie(verschillen) && zijnVerwachteVeldenGewijzigd(verschillen);
    }

    private boolean zijnVerwachteVeldenGewijzigd(final VerschilGroep verschillen) {
        boolean isAagGewijzigd = false;
        boolean isOverigeWijziging = false;
        for (final Verschil verschil : verschillen) {
            final EntiteitSleutel verschilSleutel = (EntiteitSleutel) verschil.getSleutel();
            if (AbstractMaterieleHistorie.ACTIE_AANPASSING_GELDIGHEID.equals(verschilSleutel.getVeld())
                && VerschilType.ELEMENT_AANGEPAST.equals(verschil.getVerschilType()))
            {
                /*
                 * Een verschil binnen een actie wordt geinterpreteerd als een ELEMENT_AANGEPAST van het historisch
                 * voorkomen (de eigenaar).
                 */
                isAagGewijzigd = true;
            } else if (!AbstractMaterieleHistorie.DATUM_EINDE_GELDIGHEID.equals(verschilSleutel.getVeld())
                       || !VerschilType.ELEMENT_AANGEPAST.equals(verschil.getVerschilType()))
            {
                isOverigeWijziging = true;
            }
        }
        return isAagGewijzigd && !isOverigeWijziging;
    }

    @Override
    public VerschilGroep execute(
        final VerschilGroep verschilGroep,
        final BRPActie actieVervalTbvLeveringMuts,
        final DeltaBepalingContext deltaBepalingContext)
    {
        return maakMRijEnNieuweRijVerschilVoorVerschilGroep(verschilGroep, actieVervalTbvLeveringMuts, false, true, deltaBepalingContext);
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_023;
    }

}
