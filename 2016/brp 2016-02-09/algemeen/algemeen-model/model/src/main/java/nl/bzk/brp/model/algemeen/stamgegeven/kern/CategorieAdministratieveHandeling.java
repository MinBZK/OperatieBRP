/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Een typering van administratieve handelingen.
 *
 * De categorieën zijn ontstaan om de Administratieve Handelingen in de BRP te kunnen kenmerken. Enerzijds voor de
 * Afnemers die mutatie- en vulberichten ontvangen en anderzijds voor de Afnemers en Bijhouders die bij het bevragen van
 * de BRP geautoriseerd zijn voor Verantwoordingsinformatie.
 *
 * Actualiseringen moeten hierbij beschouwd worden als de reguliere registratie van nieuwe feiten. Een doorgevoerde
 * ‘Geboorte in Nederland’ of ‘Intergemeentelijk verhuizing’ zijn in die zin ‘Actualiseringen’. Zijn er verkeerde
 * gegevens opgenomen in de BRP en de boel moet worden hersteld, dan is er sprake van Administratieve handelingen met de
 * categorie ‘Correctie’. Voorbeelden hiervan zijn ‘Correctie adres’, maar bv. ook ‘Herroeping adoptie’ of
 * ‘Ongedaanmaking huwelijk’.
 *
 * Het kenmerk ‘Synchronisatie’ wordt richting de Afnemers gebruikt als een Administratieve handeling leidt tot bv. een
 * Vulbericht (Bijvoorbeeld ‘Geef synchronisatie persoon’).
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum CategorieAdministratieveHandeling implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Actualisering.
     */
    ACTUALISERING("Actualisering"),
    /**
     * Correctie.
     */
    CORRECTIE("Correctie"),
    /**
     * Synchronisatie.
     */
    SYNCHRONISATIE("Synchronisatie"),
    /**
     * GBA - Initiele vulling.
     */
    G_B_A_INITIELE_VULLING("GBA - Initiele vulling"),
    /**
     * GBA - Synchronisatie.
     */
    G_B_A_SYNCHRONISATIE("GBA - Synchronisatie");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor CategorieAdministratieveHandeling
     */
    private CategorieAdministratieveHandeling(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Categorie administratieve handeling.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.CATEGORIEADMINISTRATIEVEHANDELING;
    }

}
