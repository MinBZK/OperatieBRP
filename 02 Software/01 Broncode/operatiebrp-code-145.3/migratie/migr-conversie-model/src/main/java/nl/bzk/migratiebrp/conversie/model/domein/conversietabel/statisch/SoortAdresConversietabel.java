/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3FunctieAdresEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP functie adres.
 */
public final class SoortAdresConversietabel implements Conversietabel<Lo3FunctieAdres, BrpSoortAdresCode> {

    @Override
    public BrpSoortAdresCode converteerNaarBrp(final Lo3FunctieAdres input) {
        if (input == null) {
            return null;
        }

        return new BrpSoortAdresCode(input.getWaarde(), input.getOnderzoek());
    }

    @Override
    public Lo3FunctieAdres converteerNaarLo3(final BrpSoortAdresCode input) {
        if (input == null) {
            return null;
        }

        return new Lo3FunctieAdres(input.getWaarde(), input.getOnderzoek());
    }

    @Override
    public boolean valideerLo3(final Lo3FunctieAdres input) {
        return !Lo3Validatie.isElementGevuld(input) || Lo3FunctieAdresEnum.getByCode(input.getWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpSoortAdresCode input) {
        // Enum
        return true;
    }
}
