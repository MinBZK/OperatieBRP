/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.tabel.Lo3ConversieTabelFactory;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Nabewerking voor adellijke titel en predikaat obv geslacht.
 */
@Component
public final class PersoonGeslachtAdellijkeTitelPredikaatNabewerking {

    @Autowired
    private Lo3ConversieTabelFactory conversieTabelFactory;

    /**
     * Voer nabewerking uit. Bepaal of er een addellijk titel of een predikaat aanwezig is (in beide historie en
     * actueel). Kopieer vervolgens het geslacht uit hetzelfde record
     * @param wijziging wijziging
     */
    public void voerNabewerkingUit(final Lo3Wijzigingen<Lo3PersoonInhoud> wijziging) {
        wijziging.setActueleInhoud(verwerk(wijziging.getActueleInhoud()));
        wijziging.setHistorischeInhoud(verwerk(wijziging.getHistorischeInhoud()));
    }

    private Lo3Categorie<Lo3PersoonInhoud> verwerk(final Lo3Categorie<Lo3PersoonInhoud> categorie) {
        if (categorie != null
                && categorie.getInhoud() != null
                && categorie.getInhoud().getAdellijkeTitelPredikaatCode() != null
                && categorie.getInhoud().getGeslachtsaanduiding() != null) {
            final Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> conversietabel =
                    conversieTabelFactory.createAdellijkeTitelPredikaatConversietabel();

            final BrpGeslachtsaanduidingCode brpGeslacht =
                    conversieTabelFactory.createGeslachtsaanduidingConversietabel().converteerNaarBrp(categorie.getInhoud().getGeslachtsaanduiding());
            AdellijkeTitelPredikaatPaar brp = conversietabel.converteerNaarBrp(categorie.getInhoud().getAdellijkeTitelPredikaatCode());
            brp = new AdellijkeTitelPredikaatPaar(brp.getAdellijkeTitel(), brp.getPredikaat(), brpGeslacht);

            final Lo3AdellijkeTitelPredikaatCode lo3 = conversietabel.converteerNaarLo3(brp);

            final Lo3PersoonInhoud.Builder builder = new Lo3PersoonInhoud.Builder(categorie.getInhoud());
            builder.setAdellijkeTitelPredikaatCode(lo3);

            return new Lo3Categorie<>(builder.build(), categorie.getDocumentatie(), categorie.getHistorie(), categorie.getLo3Herkomst());
        }

        return categorie;
    }
}
