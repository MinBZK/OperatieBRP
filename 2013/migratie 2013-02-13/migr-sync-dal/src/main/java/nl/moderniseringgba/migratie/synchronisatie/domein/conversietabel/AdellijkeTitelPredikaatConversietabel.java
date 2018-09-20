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
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversieMapEntry;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AdellijkeTitelPredikaat;

/**
 * De conversietabel voor de converie van 'LO3 Adellijke Titel/Predikaat'-code naar 'BRP Adellijke Titel,
 * Predikaat'-paar en vice versa.
 * 
 */
public class AdellijkeTitelPredikaatConversietabel extends
        AbstractConversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> {

    /**
     * Maakt een AdellijkeTitelPredikaatConversietabel object.
     * 
     * @param titels
     *            de lijst met titel conversies
     */
    public AdellijkeTitelPredikaatConversietabel(final List<AdellijkeTitelPredikaat> titels) {
        super(converteerTitels(titels));
    }

    private static List<Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>> converteerTitels(
            final List<AdellijkeTitelPredikaat> titels) {
        final List<Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>> result =
                new ArrayList<Map.Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>>();
        for (final AdellijkeTitelPredikaat titel : titels) {
            final Lo3AdellijkeTitelPredikaatCode lo3Waarde =
                    new Lo3AdellijkeTitelPredikaatCode(titel.getLo3AdellijkeTitelPredikaat());
            final AdellijkeTitelPredikaatPaar brpWaarde =
                    new AdellijkeTitelPredikaatPaar(titel.getBrpAdellijkeTitel(), titel.getBrpPredikaat(),
                            BrpGeslachtsaanduidingCode.valueOfBrpCode(Geslachtsaanduiding.parseId(
                                    titel.getBrpGeslachtsaanduidingId()).getCode()));
            result.add(new ConversieMapEntry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>(lo3Waarde,
                    brpWaarde));
        }
        return result;
    }
}
