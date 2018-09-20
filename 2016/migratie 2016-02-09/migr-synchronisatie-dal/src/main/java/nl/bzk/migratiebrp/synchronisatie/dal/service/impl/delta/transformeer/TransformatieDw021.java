/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractMaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

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
        boolean datumEindeGeldigheidGevuld = false;
        boolean actieAanpassingGeldigheidGevuld = false;
        boolean datumTijdRegistratieGewijzigd = false;
        boolean actieInhoudGewijzigd = false;
        boolean overigVeldGevuld = false;

        for (final Verschil verschil : verschillen) {
            final String veldnaam = verschil.getSleutel().getVeld();
            final VerschilType verschilType = verschil.getVerschilType();

            if (VerschilType.ELEMENT_NIEUW.equals(verschilType)) {
                if (AbstractMaterieleHistorie.DATUM_EINDE_GELDIGHEID.equals(veldnaam)) {
                    datumEindeGeldigheidGevuld = true;
                } else if (AbstractMaterieleHistorie.ACTIE_AANPASSING_GELDIGHEID.equals(veldnaam)) {
                    actieAanpassingGeldigheidGevuld = true;

                } else if (!PersoonNationaliteitHistorie.VELD_REDEN_VERLIES_NL_NATIONALITEIT.equals(veldnaam)
                           && !PersoonIndicatieHistorie.MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT.equals(veldnaam))
                {
                    overigVeldGevuld = true;
                }
            } else if (VerschilType.ELEMENT_AANGEPAST.equals(verschilType)) {
                if (AbstractMaterieleHistorie.DATUM_TIJD_REGISTRATIE.equals(veldnaam)) {
                    datumTijdRegistratieGewijzigd = true;
                } else if (AbstractMaterieleHistorie.ACTIE_INHOUD.equals(veldnaam)) {
                    actieInhoudGewijzigd = true;
                } else {
                    overigVeldGevuld = true;
                }
            } else {
                overigVeldGevuld = true;
            }
        }

        final boolean zijnDw021VeldenGevuld = datumEindeGeldigheidGevuld && actieAanpassingGeldigheidGevuld;
        final boolean zijnDw901VeldenGewijzigd = datumTijdRegistratieGewijzigd || actieInhoudGewijzigd;
        return zijnDw021VeldenGevuld && (!zijnDw901VeldenGewijzigd || TransformatieDw901.isCorrectVerschilPaar(verschillen)) && !overigVeldGevuld;
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
        return DeltaWijziging.DW_021;
    }

}
