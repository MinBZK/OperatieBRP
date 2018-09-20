/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractVoorvoegselConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.VoorvoegselConversie;

/**
 * Conversie van 'LO3 Voorvoegsel geslachtsnaam' naar een unieke combinatie van 'BRP Voorvoegsel' en 'BRP
 * Scheidingsteken'.
 * 
 */
public final class VoorvoegselConversietabel extends AbstractVoorvoegselConversietabel {

    /**
     * Maakt een VoorvoegselConversietabel object.
     * 
     * @param voorvoegsels
     *            de lijst met voorvoegsel conversies
     */
    public VoorvoegselConversietabel(final List<VoorvoegselConversie> voorvoegsels) {
        super(converteerVoorvoegsels(voorvoegsels));
    }

    private static List<Entry<Lo3String, VoorvoegselScheidingstekenPaar>> converteerVoorvoegsels(
        final List<VoorvoegselConversie> voorvoegsels)
    {
        final List<Entry<Lo3String, VoorvoegselScheidingstekenPaar>> result = new ArrayList<>();
        for (final VoorvoegselConversie voorvoegsel : voorvoegsels) {
            result.add(new ConversieMapEntry<>(Lo3String.wrap(voorvoegsel.getLo3Voorvoegsel()), new VoorvoegselScheidingstekenPaar(
                new BrpString(voorvoegsel.getVoorvoegsel()),
                new BrpCharacter(voorvoegsel.getScheidingsteken()))));
        }
        return result;
    }
}
