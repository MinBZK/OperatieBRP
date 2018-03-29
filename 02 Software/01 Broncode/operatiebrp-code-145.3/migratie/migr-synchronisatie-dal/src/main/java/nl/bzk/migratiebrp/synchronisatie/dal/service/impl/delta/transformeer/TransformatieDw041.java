/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

import java.util.ArrayList;

/**
 * Deze transformatie is specifiek voor het bijhouden van nationaliteit. Deze situatie lijkt op
 * {@link TransformatieDw024}, verschil is echter dat tevens einde bijhouding en migratie datum gevuld worden.
 */
public final class TransformatieDw041 extends AbstractTransformatie {

    private static final int VERWACHT_AANTAL_WIJZIGINGEN = 4;

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        return isBijhoudingNationaliteit(verschillen)
                && isWijzigingOpMaterieleHistorie(verschillen)
                && zijnHetVerwachtAantalVeldenGevuld(verschillen, VERWACHT_AANTAL_WIJZIGINGEN)
                && zijnVerwachteVeldenGevuld(verschillen);
    }

    private boolean isBijhoudingNationaliteit(final VerschilGroep verschilGroep) {
        return verschilGroep.getHistorieGroepClass().isAssignableFrom(PersoonNationaliteitHistorie.class);
    }

    private boolean zijnVerwachteVeldenGevuld(final VerschilGroep verschillen) {
        boolean migratieDatumGevuld = false;
        boolean eindeBijhoudingGevuld = false;

        boolean verwachteVervalVeldenGevuld = zijnVerwachteVervalVeldenGevuld(new ArrayList<>(verschillen.getVerschillen()), false);
        if (verwachteVervalVeldenGevuld) {
            for (final Verschil verschil : verschillen) {
                final String sleutelVeldnaam = verschil.getSleutel().getVeld();
                if (VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType())
                        && PersoonNationaliteitHistorie.EINDE_BIJHOUDING.equals(sleutelVeldnaam)) {
                    eindeBijhoudingGevuld = true;
                } else if (VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType())
                        && PersoonNationaliteitHistorie.MIGRATIE_DATUM.equals(sleutelVeldnaam)) {
                    migratieDatumGevuld = true;
                }
            }
            return migratieDatumGevuld && eindeBijhoudingGevuld;
        }
        return false;
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
        return DeltaWijziging.DW_041;
    }
}
