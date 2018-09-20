/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.Stapel;

import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert een LO3 Categorie Stapel. Dit is een lijst met LO3 categorieen die samen de actuele en
 * historische gegevens van deze categorie representeren.
 * 
 * De volgorde is van oud naar nieuw. Dus bij een Lo3 Stapel van n elementen is Lo3Stapel.get(0) de oudste en
 * Lo3Stapel.get(n-1) die nieuwste (laatste) wijziging.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 * @param <T>
 *            een Lo3Categorie
 */
public final class Lo3Stapel<T extends Lo3CategorieInhoud> extends Stapel<Lo3Categorie<T>> {

    /**
     * Maakt een LO3-categorie stapel aan.
     * 
     * @param categorieen
     *            de lijst met LO3 categorieen. Deze mag niet null of leeg zijn.
     * @throws IllegalArgumentException
     *             als categorieen een lege lijst is
     * @throws NullPointerException
     *             als categorieen null is
     */
    public Lo3Stapel(
            @ElementList(name = "categorieen", inline = true, type = Lo3Categorie.class) final List<Lo3Categorie<T>> categorieen) {
        super(categorieen);
    }

    /**
     * @return de lijst met LO3 categorieen (is niet null of leeg).
     */
    @ElementList(name = "categorieen", inline = true, type = Lo3Categorie.class)
    public List<Lo3Categorie<T>> getCategorieen() {
        return super.getElementen();
    }

    // /**
    // * Valideer de inhoud van deze stapel.
    // *
    // * @throws IllegalArgumentException
    // * als een waarde niet een toegestane vulling heeft
    // * @throws NullPointerException
    // * als een verplichte waarde niet gevuld is
    // * @see Lo3CategorieInhoud.valideer
    // */
    // public void valideer() {
    // for (final Lo3Categorie<T> categorie : getElementen()) {
    // categorie.getInhoud().valideer();
    // categorie.getDocumentatie().valideer();
    // }
    // }

}
