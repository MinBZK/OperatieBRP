/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

import java.util.ArrayList;

/**
 * Deze situatie herkent een gewijzigde rij waarbij tsverval, actie verval en aand. verval gevuld wordt. In dit geval
 * wordt GAAV gevuld zodat deze rij aan de nieuwe administratieve handeling hangt.
 */
public final class TransformatieDw025 extends AbstractTransformatie {

    private static final int VERWACHT_AANTAL_WIJZIGINGEN_5 = 5;
    private static final int VERWACHT_AANTAL_WIJZIGINGEN_6 = 6;

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        final boolean isVerwachteWijzigingOpHistorie =
                isWijzigingOpMaterieleHistorie(verschillen)
                        && zijnVerwachteVervalVeldenGevuld(new ArrayList<>(verschillen.getVerschillen()), true)
                        && zijnVerwachteGeldigheidVeldenGevuld(new ArrayList<>(verschillen.getVerschillen()));

        if (isNationaliteitOfIndicatieHistorie(verschillen)) {
            final boolean result;
            if (zijnToegestaneNationaliteitVeldenGevuld(verschillen)) {
                result = isVerwachteWijzigingOpHistorie && zijnHetVerwachtAantalVeldenGevuld(verschillen, VERWACHT_AANTAL_WIJZIGINGEN_6);
            } else {
                result = isVerwachteWijzigingOpHistorie && zijnHetVerwachtAantalVeldenGevuld(verschillen, VERWACHT_AANTAL_WIJZIGINGEN_5);
            }
            return result;

        } else {
            return isVerwachteWijzigingOpHistorie && zijnHetVerwachtAantalVeldenGevuld(verschillen, VERWACHT_AANTAL_WIJZIGINGEN_5);
        }
    }

    private boolean isNationaliteitOfIndicatieHistorie(VerschilGroep verschillen) {
        return verschillen.getHistorieGroepClass().isAssignableFrom(PersoonNationaliteitHistorie.class)
                || verschillen.getHistorieGroepClass().isAssignableFrom(PersoonIndicatieHistorie.class);
    }

    private boolean zijnToegestaneNationaliteitVeldenGevuld(final VerschilGroep verschillen) {
        boolean migratieRedenBeeindigingNationaliteitGevuld = false;
        for (final Verschil verschil : verschillen) {
            if ((PersoonNationaliteitHistorie.MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT.equals(verschil.getSleutel().getVeld())
                    ^ PersoonNationaliteitHistorie.VELD_REDEN_VERLIES_NL_NATIONALITEIT.equals(verschil.getSleutel().getVeld()))
                    && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType())) {
                migratieRedenBeeindigingNationaliteitGevuld = true;
            }
        }
        return migratieRedenBeeindigingNationaliteitGevuld;
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
        return DeltaWijziging.DW_025;
    }
}
