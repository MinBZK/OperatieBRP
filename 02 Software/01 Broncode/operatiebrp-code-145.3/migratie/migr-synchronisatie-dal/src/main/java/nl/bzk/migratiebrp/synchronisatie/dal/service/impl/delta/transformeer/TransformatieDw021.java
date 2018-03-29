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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Deze situatie herkent een gewijzigde rij waarbij DEG, AAG gevuld wordt en aand. verval,actie verval en tsverval leeg
 * blijven. In dit geval wordt de oude rij een M-rij en wordt de nieuwe rij vastgelegd.
 */
public final class TransformatieDw021 extends AbstractTransformatie {

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        return isWijzigingOpMaterieleHistorie(verschillen) && zijnVerwachteVeldenGevuld(verschillen);
    }

    private boolean zijnVerwachteVeldenGevuld(final VerschilGroep verschillen) {
        final List<Verschil> kopieVerschillen = new ArrayList<>(verschillen.getVerschillen());
        final boolean zijnVerwachteVervalVeldenGevuld = zijnVerwachteVervalVeldenGevuld(kopieVerschillen, true);
        final boolean zijnVerwachteGeldigheidVeldenGevuld = zijnVerwachteGeldigheidVeldenGevuld(kopieVerschillen);

        final List<Verschil> tsRegActieInhoudVerschillen = zoekActieInhoudTsRegVeldenGewijzigdVerschillen(verschillen);
        kopieVerschillen.removeAll(tsRegActieInhoudVerschillen);
        final boolean isDw901Verschil = tsRegActieInhoudVerschillen.isEmpty() || TransformatieDw901.isCorrectVerschilPaar(tsRegActieInhoudVerschillen);

        final boolean isNationaliteitOfIndicatieVerschil = verschillen.getFormeleHistorie() instanceof PersoonNationaliteitHistorie
                || verschillen.getFormeleHistorie() instanceof PersoonIndicatieHistorie;

        final Iterator<Verschil> iter = kopieVerschillen.iterator();
        while (iter.hasNext()) {
            final Verschil verschil = iter.next();
            final String veldnaam = verschil.getSleutel().getVeld();
            if (isNationaliteitOfIndicatieVerschil && (PersoonNationaliteitHistorie.VELD_REDEN_VERLIES_NL_NATIONALITEIT.equals(veldnaam)
                    || PersoonIndicatieHistorie.MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT.equals(veldnaam))) {
                iter.remove();
            }
        }

        return zijnVerwachteGeldigheidVeldenGevuld && !zijnVerwachteVervalVeldenGevuld && isDw901Verschil && kopieVerschillen.isEmpty();

    }

    @Override
    public VerschilGroep execute(
            final VerschilGroep verschilGroep,
            final BRPActie actieVervalTbvLeveringMuts,
            final DeltaBepalingContext deltaBepalingContext) {
        return maakMRijEnNieuweRijVerschilVoorVerschilGroep(verschilGroep, actieVervalTbvLeveringMuts, false, true, deltaBepalingContext);
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_021;
    }

}
