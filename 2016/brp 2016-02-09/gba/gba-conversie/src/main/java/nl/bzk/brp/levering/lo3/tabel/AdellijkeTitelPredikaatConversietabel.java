/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAdellijkeTitelPredikaat;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAdellijkeTitelPredikaatConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;

/**
 * De conversietabel voor de converie van 'LO3 Adellijke Titel/Predikaat'-code naar 'BRP Adellijke Titel,
 * Predikaat'-paar en vice versa.
 */
public final class AdellijkeTitelPredikaatConversietabel extends AbstractAdellijkeTitelPredikaatConversietabel {
    /**
     * Maakt een AdellijkeTitelPredikaatConversietabel object.
     *
     * @param list de lijst met waarden uit de conversietabel.
     */
    public AdellijkeTitelPredikaatConversietabel(final List<ConversieAdellijkeTitelPredikaat> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link ConversieAdellijkeTitelPredikaat} naar een conversie map van de LO3 waarde
     * {@link Lo3AdellijkeTitelPredikaatCode} en de BRP waarde {@link AdellijkeTitelPredikaatPaar}.
     */
    private static final class Converter extends
            AbstractLijstConverter<ConversieAdellijkeTitelPredikaat, Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>
    {
        @Override
        protected Lo3AdellijkeTitelPredikaatCode maakLo3Waarde(final ConversieAdellijkeTitelPredikaat titel) {
            return new Lo3AdellijkeTitelPredikaatCode(titel.getRubriek0221AdellijkeTitelPredikaat().getWaarde());
        }

        @Override
        protected AdellijkeTitelPredikaatPaar maakBrpWaarde(final ConversieAdellijkeTitelPredikaat titel) {
            final BrpCharacter adellijkeTitel;
            if (titel.getAdellijkeTitel() == null) {
                adellijkeTitel = null;
            } else {
                adellijkeTitel = new BrpCharacter(titel.getAdellijkeTitel().getCode().getWaarde().charAt(0), null);
            }

            final BrpCharacter predicaat;
            if (titel.getPredicaat() == null) {
                predicaat = null;
            } else {
                predicaat = new BrpCharacter(titel.getPredicaat().getCode().getWaarde().charAt(0), null);
            }

            return new AdellijkeTitelPredikaatPaar(adellijkeTitel, predicaat, new BrpGeslachtsaanduidingCode(titel.getGeslachtsaanduiding().getCode()));
        }
    }
}
