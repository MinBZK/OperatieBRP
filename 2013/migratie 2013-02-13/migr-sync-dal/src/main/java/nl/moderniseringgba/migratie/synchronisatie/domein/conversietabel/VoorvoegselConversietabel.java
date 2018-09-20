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
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.Voorvoegsel;

/**
 * Conversie van 'LO3 Voorvoegsel geslachtsnaam' naar een unieke combinatie van 'BRP Voorvoegsel' en 'BRP
 * Scheidingsteken'.
 * 
 */
public class VoorvoegselConversietabel extends AbstractConversietabel<String, VoorvoegselScheidingstekenPaar> {

    /**
     * Maakt een VoorvoegselConversietabel object.
     * 
     * @param voorvoegsels
     *            de lijst met voorvoegsel conversies
     */
    public VoorvoegselConversietabel(final List<Voorvoegsel> voorvoegsels) {
        super(converteerVoorvoegsels(voorvoegsels));
    }

    private static List<Entry<String, VoorvoegselScheidingstekenPaar>> converteerVoorvoegsels(
            final List<Voorvoegsel> voorvoegsels) {
        final List<Entry<String, VoorvoegselScheidingstekenPaar>> result =
                new ArrayList<Map.Entry<String, VoorvoegselScheidingstekenPaar>>();
        for (final Voorvoegsel voorvoegsel : voorvoegsels) {
            result.add(new ConversieMapEntry<String, VoorvoegselScheidingstekenPaar>(voorvoegsel.getLo3Voorvoegsel(),
                    new VoorvoegselScheidingstekenPaar(voorvoegsel.getBrpVoorvoegsel(), voorvoegsel
                            .getBrpScheidingsteken())));
        }
        return result;
    }
}
