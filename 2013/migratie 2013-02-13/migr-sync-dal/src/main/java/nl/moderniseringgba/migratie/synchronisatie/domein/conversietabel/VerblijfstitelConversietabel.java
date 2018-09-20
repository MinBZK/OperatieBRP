/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AbstractConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversieMapEntry;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.Verblijfstitel;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfsrechtCode en vice
 * versa.
 * 
 */
public final class VerblijfstitelConversietabel extends
        AbstractConversietabel<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode> {

    /**
     * Maakt een VerblijfstitelConversietabel object.
     * 
     * @param verblijfstitelLijst
     *            de lijst met alle verblijfstitel conversies
     */
    public VerblijfstitelConversietabel(final List<Verblijfstitel> verblijfstitelLijst) {
        super(converteerVerblijfstitelLijst(verblijfstitelLijst));
    }

    private static List<Entry<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>> converteerVerblijfstitelLijst(
            final List<Verblijfstitel> verblijfstitelLijst) {
        final List<Entry<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>> result =
                new ArrayList<Map.Entry<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>>();
        for (final Verblijfstitel verblijfstitel : verblijfstitelLijst) {
            result.add(new ConversieMapEntry<Lo3AanduidingVerblijfstitelCode, BrpVerblijfsrechtCode>(
                    new Lo3AanduidingVerblijfstitelCode(verblijfstitel.getLo3Code()), new BrpVerblijfsrechtCode(
                            verblijfstitel.getBrpOmschrijving())));
        }
        return result;
    }
}
