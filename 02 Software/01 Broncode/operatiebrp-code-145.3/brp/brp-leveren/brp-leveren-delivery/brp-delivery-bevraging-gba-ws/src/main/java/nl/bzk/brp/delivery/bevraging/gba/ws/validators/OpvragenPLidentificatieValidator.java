/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum.CATEGORIE_01;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_0110;
import static nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum.ELEMENT_0120;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.VraagPLWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Valideert of de zoekcriteria persoonsidentificerende gegevens bevat.
 */
public class OpvragenPLidentificatieValidator implements Validator<VraagPLWebserviceVraagBericht> {
    private static final Map<Lo3CategorieEnum, List<Lo3ElementEnum>> verplichteRubrieken =
            Collections.unmodifiableMap(Collections.singletonMap(CATEGORIE_01, Arrays.asList(ELEMENT_0110, ELEMENT_0120)));

    @Override
    public Optional<Antwoord> apply(final VraagPLWebserviceVraagBericht vraag) {
        return hasPersoonidentificerendeGegevens(vraag.getZoekCriteria())
                ? Optional.empty()
                : Optional.of(Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_019));
    }

    private boolean hasPersoonidentificerendeGegevens(final List<Lo3CategorieWaarde> criteria) {
        return criteria.stream().anyMatch(categorie ->
                verplichteRubrieken.getOrDefault(categorie.getCategorie(), Collections.emptyList()).stream()
                        .anyMatch(element -> categorie.getElement(element) != null));
    }
}
