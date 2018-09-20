/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractVerblijfsrechtConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfsrechtCode en vice
 * versa.
 *
 */
public final class VerblijfsrechtConversietabel extends AbstractVerblijfsrechtConversietabel {

    private static final DecimalFormat VERBLIJFSRECHT_CODE_FORMAT = new DecimalFormat("00");

    /**
     * Maakt een VerblijfsrechtConversietabel object.
     *
     * @param verblijfsrechtLijst
     *            de lijst met alle verblijfsrecht conversies
     */
    public VerblijfsrechtConversietabel(final List<Verblijfsrecht> verblijfsrechtLijst) {
        super(VerblijfsrechtConversietabel.converteerVerblijfsrechtLijst(verblijfsrechtLijst));
    }

    private static List<Entry<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>> converteerVerblijfsrechtLijst(
        final List<Verblijfsrecht> verblijfsrechtLijst)
    {
        final List<Entry<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>> result = new ArrayList<>();
        for (final Verblijfsrecht verblijfsrecht : verblijfsrechtLijst) {
            result.add(new ConversieMapEntry<>(
                new Lo3AanduidingVerblijfstitelCode(VERBLIJFSRECHT_CODE_FORMAT.format(verblijfsrecht.getCode())),
                new BrpVerblijfsrechtCode(verblijfsrecht.getCode())));
        }
        return result;
    }
}
