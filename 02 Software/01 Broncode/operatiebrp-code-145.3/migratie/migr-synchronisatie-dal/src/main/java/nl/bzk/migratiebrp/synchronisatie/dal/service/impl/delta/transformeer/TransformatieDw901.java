/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractFormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.SleutelUtil;

import java.util.Collections;
import java.util.List;

/**
 * Tranformatie aanpassingen aan groepen met historie uit Cat 07 en Cat 13, waarbij alleen de datum-tijd registratie is
 * aangepast. Voor deze gevallen worden de aanpassingen niet doorgevoerd, en wordt een lege VerschilGroep opgeleverd.
 */
public final class TransformatieDw901 extends AbstractTransformatie {

    private static final int VERWACHT_AANTAL_WIJZIGINGEN = 2;

    /**
     * @param verschillen de lijst met verschillen
     * @return true als de verschillen een tsreg en actie inhoud verschil hebben afkomstig van een datumtijdstempel wijziging, anders false
     */
    static boolean isCorrectVerschilPaar(final List<Verschil> verschillen) {
        boolean result = false;
        if (verschillen.size() == VERWACHT_AANTAL_WIJZIGINGEN) {
            for (final Verschil verschil : verschillen) {
                if (AbstractFormeleHistorie.DATUM_TIJD_REGISTRATIE.equals(verschil.getSleutel().getVeld())) {
                    result = SleutelUtil.isBrpGroepMetTsRegAfkomstigUitDatumTijdStempel(verschil.getBestaandeHistorieRij()
                    );
                }
            }
        }
        return result;
    }

    @Override
    public boolean accept(final VerschilGroep verschillen) {
        boolean result = false;
        if (verschillen.size() == VERWACHT_AANTAL_WIJZIGINGEN) {
            final List<Verschil> tsRegActiInhoudVerschillen = zoekActieInhoudTsRegVeldenGewijzigdVerschillen(verschillen);
            result = isCorrectVerschilPaar(tsRegActiInhoudVerschillen);
        }
        return result;
    }

    @Override
    public VerschilGroep execute(
            final VerschilGroep verschilGroep,
            final BRPActie actieVervalTbvLeveringMuts,
            final DeltaBepalingContext deltaBepalingContext) {
        final List<Verschil> geenVerschillen = Collections.emptyList();
        final VerschilGroep kopieVerschilGroep = VerschilGroep.maakKopieZonderVerschillen(verschilGroep);
        kopieVerschilGroep.addVerschillen(geenVerschillen);
        return kopieVerschilGroep;
    }

    @Override
    public DeltaWijziging getDeltaWijziging() {
        return DeltaWijziging.DW_901;
    }
}
