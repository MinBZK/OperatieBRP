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

/**
 * Fallback Tranformatie voor alle aanpassingen aan een bestaande rij die niet door specifieke transformaties worden
 * behandeld. Deze aanpassingen worden vertaald naar het verwijderen (omzetten naar 'M' rij) van de oude rij, en het
 * toevoegen van de nieuwe (aangepaste) rij.
 */
public final class TransformatieDw003 extends AbstractTransformatie {
    @Override
    public boolean accept(final VerschilGroep verschillen) {
        final Verschil verschil0 = verschillen.get(0);
        return verschillen.size() > 1
                || !(VerschilType.RIJ_TOEGEVOEGD.equals(verschil0.getVerschilType()) || VerschilType.RIJ_VERWIJDERD.equals(verschil0.getVerschilType()));
    }

    @Override
    public VerschilGroep execute(
            final VerschilGroep verschillen,
            final BRPActie actieVervalTbvLeveringMuts,
            final DeltaBepalingContext deltaBepalingContext) {
        return maakMRijEnNieuweRijVerschilVoorVerschilGroep(
                verschillen,
                actieVervalTbvLeveringMuts,
                false,
                verschillen.get(0).getBestaandeHistorieRij() instanceof PersoonBijhoudingHistorie,
                deltaBepalingContext);
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_003;
    }
}
