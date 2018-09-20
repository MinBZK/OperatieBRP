/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractMaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

/**
 * Deze situatie herkent een gewijzigde rij waarbij tsverval, actie verval en aand. verval gevuld wordt. In dit geval
 * wordt GAAV gevuld zodat deze rij aan de nieuwe administratieve handeling hangt.
 */
public final class TransformatieDw025 extends AbstractTransformatie {

    private static final int INT_5 = 5;
    private static final int INT_6 = 6;

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        final boolean isVerwachteWijzigingOpHistorie =
                isWijzigingOpMaterieleHistorie(verschillen)
                                                       && zijnVerwachteVervalVeldenGevuld(verschillen)
                                                       && zijnVerwachteGeldigheidVeldenGewijzigd(verschillen);

        if (verschillen.getHistorieGroepClass().isAssignableFrom(PersoonNationaliteitHistorie.class)
            || verschillen.getHistorieGroepClass().isAssignableFrom(PersoonIndicatieHistorie.class))
        {
            final boolean result;
            if (zijnToegestaneNationaliteitVeldenGevuld(verschillen)) {
                result = isVerwachteWijzigingOpHistorie && zijnHetVerwachtAantalVeldenGevuld(verschillen, INT_6);
            } else {
                result = isVerwachteWijzigingOpHistorie && zijnHetVerwachtAantalVeldenGevuld(verschillen, INT_5);
            }
            return result;

        } else {
            return isVerwachteWijzigingOpHistorie && zijnHetVerwachtAantalVeldenGevuld(verschillen, INT_5);
        }
    }

    private boolean zijnToegestaneNationaliteitVeldenGevuld(final VerschilGroep verschillen) {
        boolean migratieRedenBeeindigingNationaliteitGevuld = false;
        for (final Verschil verschil : verschillen) {
            if ((PersoonNationaliteitHistorie.MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT.equals(verschil.getSleutel().getVeld())
                 ^ PersoonNationaliteitHistorie.VELD_REDEN_VERLIES_NL_NATIONALITEIT.equals(verschil.getSleutel().getVeld()))
                && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType()))
            {
                migratieRedenBeeindigingNationaliteitGevuld = true;
            }
        }
        return migratieRedenBeeindigingNationaliteitGevuld;
    }

    private boolean zijnVerwachteVervalVeldenGevuld(final VerschilGroep verschillen) {
        boolean datumTijdVervalGevuld = false;
        boolean actieVervalGevuld = false;
        boolean nadereAanduidingVervalGevuld = false;
        for (final Verschil verschil : verschillen) {
            if (AbstractFormeleHistorie.DATUM_TIJD_VERVAL.equals(verschil.getSleutel().getVeld())
                && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType()))
            {
                datumTijdVervalGevuld = true;
            } else if (AbstractFormeleHistorie.ACTIE_VERVAL.equals(verschil.getSleutel().getVeld())
                       && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType()))
            {
                actieVervalGevuld = true;
            } else if (AbstractFormeleHistorie.NADERE_AANDUIDING_VERVAL.equals(verschil.getSleutel().getVeld())
                       && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType()))
            {
                nadereAanduidingVervalGevuld = true;
            }
        }
        return datumTijdVervalGevuld && actieVervalGevuld && nadereAanduidingVervalGevuld;
    }

    private boolean zijnVerwachteGeldigheidVeldenGewijzigd(final VerschilGroep verschillen) {
        boolean datumEindeGeldigheidGevuld = false;
        boolean actieAanpassingGeldigheidGevuld = false;
        for (final Verschil verschil : verschillen) {
            if (AbstractMaterieleHistorie.DATUM_EINDE_GELDIGHEID.equals(verschil.getSleutel().getVeld())
                && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType()))
            {
                datumEindeGeldigheidGevuld = true;
            } else if (AbstractMaterieleHistorie.ACTIE_AANPASSING_GELDIGHEID.equals(verschil.getSleutel().getVeld())
                       && VerschilType.ELEMENT_NIEUW.equals(verschil.getVerschilType()))
            {
                actieAanpassingGeldigheidGevuld = true;
            }
        }
        return datumEindeGeldigheidGevuld && actieAanpassingGeldigheidGevuld;
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
        return DeltaWijziging.DW_025;
    }
}
