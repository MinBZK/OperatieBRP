/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelLo3RubriekHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;

import org.springframework.stereotype.Component;

/**
 * Map dienstbundel lo3 rubriek van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpDienstbundelLo3RubriekMapper extends AbstractBrpMapper<DienstbundelLo3RubriekHistorie, BrpDienstbundelLo3RubriekInhoud> {

    /**
     * Map een database entiteit dienstbundel lo3 rubriek naar een BRP conversie model object.
     *
     * @param dienstbundelLo3Rubriek
     *            database entiteit
     * @return conversie model object
     */
    public BrpDienstbundelLo3Rubriek mapDienstbundelLo3Rubriek(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {

        final BrpStapel<BrpDienstbundelLo3RubriekInhoud> dienstbundelLo3RubriekStapel =
                map(dienstbundelLo3Rubriek.getDienstbundelLo3RubriekHistorieSet(), null);

        return new BrpDienstbundelLo3Rubriek(dienstbundelLo3Rubriek.getLo3Rubriek().getNaam(), true, dienstbundelLo3RubriekStapel);
    }

    @Override
    protected BrpDienstbundelLo3RubriekInhoud mapInhoud(final DienstbundelLo3RubriekHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {

        return new BrpDienstbundelLo3RubriekInhoud(false);
    }
}
