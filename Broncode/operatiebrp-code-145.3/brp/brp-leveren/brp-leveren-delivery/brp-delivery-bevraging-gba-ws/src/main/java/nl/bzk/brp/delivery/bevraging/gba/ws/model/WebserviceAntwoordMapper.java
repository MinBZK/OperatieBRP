/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.Categoriestapel;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.Categorievoorkomen;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.Element;
import nl.bzk.brp.delivery.bevraging.gba.ws.lo3.PL;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Mapt instanties van Vraag op instanties van Persoonsvraag.
 */
public interface WebserviceAntwoordMapper {

    /**
     * Mapt een lijst van een lijst van Lo3CategorieWaarde naar een lijst van PL'en.
     * @param listOfcategorieWaardes een lijst van een lijst van Lo3CategorieWaarde
     * @return lijst van PL'en
     */
    static List<PL> map(final List<List<Lo3CategorieWaarde>> listOfcategorieWaardes) {
        return listOfcategorieWaardes.stream().map(WebserviceAntwoordMapper::mapCategorieWaardes).collect(Collectors.toList());
    }

    /**
     * Mapt de categoriewaardes op de stapels per categorie voor het XML persoonslijst object.
     * @param categorieWaardes de lijst met te mappen categoriewaardes
     * @return XML persoonslijstobject met de lijst met stapels
     */
    static PL mapCategorieWaardes(final List<Lo3CategorieWaarde> categorieWaardes) {
        PL pl = new PL();
        pl.setCategoriestapels(categorieWaardes.stream().map(WebserviceAntwoordMapper::createStapel).collect(Collectors.toList()));
        return pl;
    }

    /**
     * Mapt de categoriewaarde op het categorievoorkomen voor het XML persoonslijst object.
     * @param categorieWaarde de te mappen categoriewaarde
     * @return categorievoorkomen op basis van de categoriewaarde
     */
    static Categorievoorkomen mapCategorieWaarde(final Lo3CategorieWaarde categorieWaarde) {
        Categorievoorkomen voorkomen = new Categorievoorkomen();
        voorkomen.setCategorienummer((byte) categorieWaarde.getCategorie().getCategorieAsInt());
        voorkomen.setElementen(categorieWaarde.getElementen().entrySet().stream()
                .map(e -> createElement(e.getKey().getElementNummer(), e.getValue()))
                .collect(Collectors.toList()));
        return voorkomen;
    }

    /**
     * Mapt de categoriewaardes op de stapel per categorie voor het XML persoonslijst object.
     * @param categorieWaarde de te mappen categoriewaardes
     * @return categoriestapel op basis van de categoriewaarde
     */
    static Categoriestapel createStapel(final Lo3CategorieWaarde categorieWaarde) {
        Categoriestapel stapel = new Categoriestapel();
        stapel.setCategorievoorkomens(Collections.singletonList(mapCategorieWaarde(categorieWaarde)));
        return stapel;
    }

    /**
     * Maakt op basis van rubrieknummer en waarde een element object aan voor het XML persoonslijst object.
     * @param nummer het rubrieknummer
     * @param waarde de te zetten waarde voor de rubriek
     * @return element op basis van rubrieknummer en waarde
     */
    static Element createElement(final String nummer, final String waarde) {
        Element element = new Element();
        element.setNummer(Short.valueOf(nummer));
        element.setWaarde(waarde);
        return element;
    }
}
