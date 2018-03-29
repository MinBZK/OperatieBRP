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

import java.util.List;

/**
 * Tranformatie voor gevallen die resulteren in een verwijderde rij.
 */
public final class TransformatieDw001 extends AbstractTransformatie {
    @Override
    public boolean accept(final VerschilGroep verschillen) {
        final Verschil verschil = verschillen.get(0);

        return verschillen.size() == 1 && VerschilType.RIJ_VERWIJDERD.equals(verschil.getVerschilType());
    }

    @Override
    public VerschilGroep execute(
            final VerschilGroep verschilGroep,
            final BRPActie actieVervalTbvLeveringMuts,
            final DeltaBepalingContext deltaBepalingContext) {
        final Verschil verschil = verschilGroep.get(0);
        final List<Verschil> mRijVerschillen = transformeerVerwijderdeRijNaarMRijVerschillen(verschil, actieVervalTbvLeveringMuts);

        final VerschilGroep kopieVerschilGroep = VerschilGroep.maakKopieZonderVerschillen(verschilGroep);
        kopieVerschilGroep.addVerschillen(mRijVerschillen);
        return kopieVerschilGroep;
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_001;
    }
}
