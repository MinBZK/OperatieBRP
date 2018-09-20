/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

/**
 * Deze situatie herkent een gewijzigde rij waarbij tsverval, actie verval en aand. verval gevuld wordt. In dit geval
 * wordt GAAV gevuld zodat deze rij aan de nieuwe administratieve handeling hangt.
 */
public final class TransformatieDw022 extends AbstractTransformatie {

    private static final int INT_3 = 3;

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        return isWijzigingOpMaterieleHistorie(verschillen)
               && zijnHetVerwachtAantalVeldenGevuld(verschillen, INT_3)
               && zijnVerwachteVeldenGevuld(verschillen);
    }

    private boolean zijnVerwachteVeldenGevuld(final VerschilGroep verschillen) {
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
        return DeltaWijziging.DW_022;
    }
}
