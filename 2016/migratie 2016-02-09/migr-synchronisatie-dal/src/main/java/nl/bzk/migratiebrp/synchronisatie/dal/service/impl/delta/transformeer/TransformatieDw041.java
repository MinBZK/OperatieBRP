/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

/**
 * Deze transformatie is specifiek voor het bijhouden van nationaliteit. Deze situatie lijkt op
 * {@link TransformatieDw024}, verschil is echter dat tevens einde bijhouding en migratie datum gevuld worden.
 */
public final class TransformatieDw041 extends AbstractTransformatie {

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        final int verwachteAantalWijzigingen = 4;
        return isBijhoudingNationaliteit(verschillen)
               && isWijzigingOpMaterieleHistorie(verschillen)
               && zijnHetVerwachtAantalVeldenGevuld(verschillen, verwachteAantalWijzigingen)
               && zijnVerwachteVervalVeldenGevuld(verschillen);
    }

    private boolean isBijhoudingNationaliteit(final VerschilGroep verschilGroep) {
        return verschilGroep.getHistorieGroepClass().isAssignableFrom(PersoonNationaliteitHistorie.class);
    }

    private boolean zijnVerwachteVervalVeldenGevuld(final VerschilGroep verschillen) {
        boolean datumTijdVervalGevuld = false;
        boolean actieVervalGevuld = false;
        boolean migratieDatumGevuld = false;
        boolean eindeBijhoudingGevuld = false;
        for (final Verschil verschil : verschillen) {
            final String sleutelVeldnaam = verschil.getSleutel().getVeld();

            if (VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType())) {
                if (AbstractFormeleHistorie.DATUM_TIJD_VERVAL.equals(sleutelVeldnaam)) {
                    datumTijdVervalGevuld = true;
                } else if (AbstractFormeleHistorie.ACTIE_VERVAL.equals(sleutelVeldnaam)) {
                    actieVervalGevuld = true;
                } else if (PersoonNationaliteitHistorie.EINDE_BIJHOUDING.equals(sleutelVeldnaam)) {
                    eindeBijhoudingGevuld = true;
                } else if (PersoonNationaliteitHistorie.MIGRATIE_DATUM.equals(sleutelVeldnaam)) {
                    migratieDatumGevuld = true;
                }
            }
        }
        return datumTijdVervalGevuld && actieVervalGevuld && migratieDatumGevuld && eindeBijhoudingGevuld;
    }

    @Override
    public VerschilGroep execute(
        final VerschilGroep verschilGroep,
        final BRPActie actieVervalTbvLeveringMuts,
        final DeltaBepalingContext deltaBepalingContext)
    {
        return voegActieVervalTbvLeveringMutatiesToeAanVerschilGroep(verschilGroep, actieVervalTbvLeveringMuts);
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_041;
    }
}
