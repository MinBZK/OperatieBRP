/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
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
     * @param list de lijst met waarden uit de conversietabel.
     */
    public AdellijkeTitelPredikaatConversietabel(final List<AdellijkeTitelPredikaat> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link AdellijkeTitelPredikaat} naar een conversie map van de LO3 waarde
     * {@link Lo3AdellijkeTitelPredikaatCode} en de BRP waarde {@link AdellijkeTitelPredikaatPaar}.
     */
    private static final class Converter
            extends AbstractLijstConverter<AdellijkeTitelPredikaat, Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> {
        @Override
        protected Lo3AdellijkeTitelPredikaatCode maakLo3Waarde(final AdellijkeTitelPredikaat titel) {
            return new Lo3AdellijkeTitelPredikaatCode(titel.getLo3AdellijkeTitelPredikaat());
        }

        @Override
        protected AdellijkeTitelPredikaatPaar maakBrpWaarde(final AdellijkeTitelPredikaat titel) {
            final BrpCharacter adellijkeTitel;
            if (titel.getAdellijkeTitel() == null) {
                adellijkeTitel = null;
            } else {
                adellijkeTitel = new BrpCharacter(titel.getAdellijkeTitel().getCode().charAt(0), null);
            }

            final BrpCharacter predicaat;
            if (titel.getPredikaat() == null) {
                predicaat = null;
            } else {
                predicaat = new BrpCharacter(titel.getPredikaat().getCode().charAt(0), null);
            }

            return new AdellijkeTitelPredikaatPaar(adellijkeTitel, predicaat, new BrpGeslachtsaanduidingCode(titel.getGeslachtsaanduiding().getCode()));
        }
    }
}
