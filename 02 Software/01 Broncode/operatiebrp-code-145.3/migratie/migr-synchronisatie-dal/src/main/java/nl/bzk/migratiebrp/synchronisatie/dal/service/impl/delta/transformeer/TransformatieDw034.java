/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

import java.util.ArrayList;

/**
 * Deze situatie herkent een gewijzigde rij waarbij tsverval, actie verval en aand. verval gevuld wordt. In dit geval
 * wordt GAAV gevuld zodat deze rij aan de nieuwe administratieve handeling hangt.
 */
public final class TransformatieDw034 extends AbstractTransformatie {

    private static final int VERWACHT_AANTAL_WIJZIGINGEN = 4;

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        return isWijzigingOpMaterieleHistorie(verschillen)
                && zijnHetVerwachtAantalVeldenGevuld(verschillen, VERWACHT_AANTAL_WIJZIGINGEN)
                && zijnVerwachteVervalVeldenGevuld(new ArrayList<>(verschillen.getVerschillen()), true)
                && zijnVerwachteBijhoudingVeldenGewijzigd(verschillen);
    }


    private boolean zijnVerwachteBijhoudingVeldenGewijzigd(final VerschilGroep verschillen) {
        boolean nadereBijhoudingsaardGewijzigd = false;
        for (final Verschil verschil : verschillen) {
            if (PersoonBijhoudingHistorie.NADERE_BIJHOUDINGSAARD_ID.equals(verschil.getSleutel().getVeld())
                    && VerschilType.ELEMENT_AANGEPAST.equals(verschil.getVerschilType())) {
                nadereBijhoudingsaardGewijzigd = true;
            }
        }
        return nadereBijhoudingsaardGewijzigd;
    }

    @Override
    public VerschilGroep execute(
            final VerschilGroep verschilGroep,
            final BRPActie actieVervalTbvLeveringMuts,
            final DeltaBepalingContext deltaBepalingContext) {
        return voegActieVervalTbvLeveringMutatiesToeAanVerschilGroep(verschilGroep, actieVervalTbvLeveringMuts);
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_034;
    }
}
