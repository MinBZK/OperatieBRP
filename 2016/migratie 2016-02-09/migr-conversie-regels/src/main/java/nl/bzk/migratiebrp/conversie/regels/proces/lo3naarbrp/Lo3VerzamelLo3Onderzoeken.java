/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.springframework.stereotype.Component;

/**
 * In deze stap wordt alle herkomsten die bij de onderzoeken in de LO3 persoonslijst voorkomen verzameld en terug
 * gegeven.
 */
@Component
public final class Lo3VerzamelLo3Onderzoeken {

    /**
     * Verzameld alle herkomsten bij de onderzoeken die aan de opgegeven persoonslijst zijn gekoppeld.
     * 
     * @param lo3Persoonslijst
     *            de LO3 persoonslijst waar onderzoeken aan gekoppeld zijn.
     * @return een lijst van {@link Lo3Herkomst} van onderzoeken die in deze persoonslijst aanwezig zijn.
     */
    public List<Lo3Herkomst> verzamelHerkomstenBijOnderzoek(final Lo3Persoonslijst lo3Persoonslijst) {
        final List<Lo3Herkomst> herkomsten = new ArrayList<>();
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getPersoonStapel());
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getOuder1Stapel());
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getOuder2Stapel());
        verzamelHerkomstenInStapels(herkomsten, lo3Persoonslijst.getNationaliteitStapels());
        verzamelHerkomstenInStapels(herkomsten, lo3Persoonslijst.getHuwelijkOfGpStapels());
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getOverlijdenStapel());
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getVerblijfplaatsStapel());
        verzamelHerkomstenInStapels(herkomsten, lo3Persoonslijst.getKindStapels());
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getVerblijfstitelStapel());
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getGezagsverhoudingStapel());
        verzamelHerkomstenInStapels(herkomsten, lo3Persoonslijst.getReisdocumentStapels());
        verzamelHerkomstenInStapel(herkomsten, lo3Persoonslijst.getKiesrechtStapel());

        return herkomsten;
    }

    private <T extends Lo3CategorieInhoud> void verzamelHerkomstenInStapels(final List<Lo3Herkomst> herkomsten, final List<Lo3Stapel<T>> stapels) {
        for (final Lo3Stapel<T> stapel : stapels) {
            verzamelHerkomstenInStapel(herkomsten, stapel);
        }
    }

    private <T extends Lo3CategorieInhoud> void verzamelHerkomstenInStapel(final List<Lo3Herkomst> herkomsten, final Lo3Stapel<T> stapel) {
        if (stapel == null) {
            return;
        }

        for (final Lo3Categorie<T> voorkomen : stapel.getCategorieen()) {
            final Lo3Onderzoek onderzoek = voorkomen.getOnderzoek();
            final Lo3Herkomst herkomst = onderzoek != null ? onderzoek.getLo3Herkomst() : null;
            if (herkomst != null && !herkomsten.contains(herkomst)) {
                herkomsten.add(voorkomen.getLo3Herkomst());
            }
        }
    }
}
