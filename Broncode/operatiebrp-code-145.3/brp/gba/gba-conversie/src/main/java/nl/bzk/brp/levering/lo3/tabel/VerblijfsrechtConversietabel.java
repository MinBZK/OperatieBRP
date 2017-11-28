/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractVerblijfsrechtConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfstitelCode en vice
 * versa.
 */
public final class VerblijfsrechtConversietabel extends AbstractVerblijfsrechtConversietabel {
    /**
     * Maakt een VerblijfsrechtConversietabel object.
     * @param list de lijst met waarden uit de conversietabel.
     */
    public VerblijfsrechtConversietabel(final List<Verblijfsrecht> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link Verblijfsrecht} naar een conversie map van de LO3 waarde
     * {@link Lo3AanduidingVerblijfstitelCode} en de BRP waarde {@link BrpVerblijfsrechtCode}.
     */
    private static final class Converter extends AbstractLijstConverter<Verblijfsrecht, Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> {
        @Override
        protected Lo3AanduidingVerblijfstitelCode maakLo3Waarde(final Verblijfsrecht verblijfstitel) {
            return new Lo3AanduidingVerblijfstitelCode(verblijfstitel.getCode());
        }

        @Override
        protected BrpVerblijfsrechtCode maakBrpWaarde(final Verblijfsrecht verblijfstitel) {
            return new BrpVerblijfsrechtCode(verblijfstitel.getCode());
        }
    }
}
