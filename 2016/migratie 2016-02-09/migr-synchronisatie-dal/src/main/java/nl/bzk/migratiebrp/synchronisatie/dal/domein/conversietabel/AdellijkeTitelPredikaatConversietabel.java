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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAdellijkeTitelPredikaatConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.AdellijkeTitelPredikaat;

/**
 * De conversietabel voor de converie van 'LO3 Adellijke Titel/Predikaat'-code naar 'BRP Adellijke Titel,
 * Predikaat'-paar en vice versa.
 * 
 */
public class AdellijkeTitelPredikaatConversietabel extends AbstractAdellijkeTitelPredikaatConversietabel {

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
        final List<AdellijkeTitelPredikaat> titels)
    {
        final List<Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>> result = new ArrayList<>();
        for (final AdellijkeTitelPredikaat titel : titels) {
            final Lo3AdellijkeTitelPredikaatCode lo3Waarde = new Lo3AdellijkeTitelPredikaatCode(titel.getLo3AdellijkeTitelPredikaat());
            final AdellijkeTitelPredikaatPaar brpWaarde =
                    new AdellijkeTitelPredikaatPaar(
                        adellijkeTitelToCharacter(titel.getAdellijkeTitel()),
                        predikaatToCharacter(titel.getPredikaat()),
                        new BrpGeslachtsaanduidingCode(titel.getGeslachtsaanduiding().getCode()));
            result.add(new ConversieMapEntry<>(lo3Waarde, brpWaarde));
        }
        return result;
    }

    private static BrpCharacter predikaatToCharacter(final Predicaat input) {
        if (input == null) {
            return null;
        } else {
            return new BrpCharacter(input.getCode().charAt(0), null);
        }
    }

    private static BrpCharacter adellijkeTitelToCharacter(final AdellijkeTitel input) {
        if (input == null) {
            return null;
        } else {
            return new BrpCharacter(input.getCode().charAt(0), null);
        }
    }
}
