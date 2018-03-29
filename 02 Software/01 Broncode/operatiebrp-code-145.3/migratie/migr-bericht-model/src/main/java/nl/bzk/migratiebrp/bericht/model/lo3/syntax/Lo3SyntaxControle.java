/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.syntax;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Lo3 Syntax Controles.
 */
public enum Lo3SyntaxControle implements SyntaxControle {

    /**
     * Standaard Lo3 controles.
     */
    STANDAARD(Controles::controleerType, Controles::controleerNietLeeg, Controles::controleerExacteLengte, Controles::controleerMinimumLengte,
            Controles::controleerMaximumLengte),

    /**
     * Standaard Lo3 controles voor vraag en mutatie berichten.
     */
    LENGTE_NUL_TOEGESTAAN(Controles::controleerType, Controles::controleerExacteLengte, Controles::controleerMinimumLengte, Controles::controleerMaximumLengte);

    private static final String GROEP_ERROR = "Categorie %s mag groep %s niet bevatten.";

    private final List<Controle> controles;

    /**
     * Constructor.
     * @param controles controles
     */
    Lo3SyntaxControle(final Controle... controles) {
        this.controles = Arrays.asList(controles);
    }

    /**
     * Controleer bericht inhoud.
     * @param categorieWaarden bericht inhoud
     * @throws BerichtInhoudException als een fout is aangetroffen
     */
    @Override
    public void controleerInhoud(final List<Lo3CategorieWaarde> categorieWaarden) throws BerichtInhoudException {
        for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
            controleerCategorie(categorieWaarde);
        }
    }

    private void controleerCategorie(final Lo3CategorieWaarde categorieWaarde) throws BerichtInhoudException {
        controleerGroepen(categorieWaarde);
        controleerElementen(categorieWaarde);
    }

    private void controleerGroepen(final Lo3CategorieWaarde categorieWaarde) throws BerichtInhoudException {
        final List<Lo3GroepEnum> groepen = categorieWaarde.getCategorie().getGroepen();

        for (final Lo3ElementEnum definitie : categorieWaarde.getElementen().keySet()) {
            final Lo3GroepEnum groep = definitie.getGroep();

            if (!groepen.contains(groep)) {
                throw new BerichtInhoudException(String.format(GROEP_ERROR, categorieWaarde.getCategorie().getCategorie(), groep));
            }
        }
    }

    private void controleerElementen(final Lo3CategorieWaarde categorieWaarde) throws BerichtInhoudException {
        for (final Map.Entry<Lo3ElementEnum, String> element : categorieWaarde.getElementen().entrySet()) {
            controleerElement(element);
        }
    }

    private void controleerElement(final Entry<Lo3ElementEnum, String> element) throws BerichtInhoudException {
        if (element.getValue() == null) {
            return;
        }

        // Loop over controles
        for (final Controle controle : controles) {
            controle.controleer(element);
        }
    }

}
