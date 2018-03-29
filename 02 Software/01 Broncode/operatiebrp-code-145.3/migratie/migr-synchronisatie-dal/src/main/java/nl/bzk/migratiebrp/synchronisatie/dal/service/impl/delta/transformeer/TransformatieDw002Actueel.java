/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

/**
 * Tranformatie voor gevallen die resulteren in een nieuwe rij waarbij DEG en tsverval leeg zijn.
 */
public final class TransformatieDw002Actueel extends AbstractTransformatie {
    @Override
    public boolean accept(final VerschilGroep verschillen) {
        final Verschil verschil = verschillen.get(0);

        return verschillen.size() == 1 && VerschilType.RIJ_TOEGEVOEGD.equals(verschil.getVerschilType()) && verschil.getNieuweHistorieRij().isActueel();
    }

    @Override
    public VerschilGroep execute(
            final VerschilGroep verschillen,
            final BRPActie actieVervalTbvLeveringMuts,
            final DeltaBepalingContext deltaBepalingContext) {
        return verschillen;
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_002_ACT;
    }
}
