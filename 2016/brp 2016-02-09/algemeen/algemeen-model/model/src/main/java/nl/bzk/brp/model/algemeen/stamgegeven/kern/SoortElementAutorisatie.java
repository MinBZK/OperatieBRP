/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;

/**
 * De indeling van soorten autorisaties op de element tabel.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortElementAutorisatie implements ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Het attribuut kan niet direct geautoriseerd worden maar wordt geleverd als de groepsautorisatie aanwezig is. B.v.
     * tijdstip verval van een groep wordt alleen geleverd als de partij geautoriseerd is om formele historie te zien
     * voor de groep..
     */
    VIA_GROEPSAUTORISATIE(
            "Via groepsautorisatie",
            "Het attribuut kan niet direct geautoriseerd worden maar wordt geleverd als de groepsautorisatie aanwezig is. B.v. tijdstip verval van een groep wordt alleen geleverd als de partij geautoriseerd is om formele historie te zien voor de groep."),
    /**
     * Een afnemer mag dit attribuut op grond van H3 wet BRP niet verstrekt krijgen. Het attribuut kan mogelijk wel op
     * grond van andere wetgeving geleverd worden..
     */
    NIET_VERSTREKKEN(
            "Niet verstrekken",
            "Een afnemer mag dit attribuut op grond van H3 wet BRP niet verstrekt krijgen. Het attribuut kan mogelijk wel op grond van andere wetgeving geleverd worden."),
    /**
     * Een afnemer mag voor dit attribuut geautoriseerd worden..
     */
    OPTIONEEL("Optioneel", "Een afnemer mag voor dit attribuut geautoriseerd worden."),
    /**
     * Expliciete autorisatie niet mogelijk. Het attribuut is echter wel aanwezig in de berichten. Bijvoorbeeld als
     * objectsleutel of als hiërarchische relatie..
     */
    STRUCTUUR(
            "Structuur",
            "Expliciete autorisatie niet mogelijk. Het attribuut is echter wel aanwezig in de berichten. Bijvoorbeeld als objectsleutel of als hi\u00EBrarchische relatie."),
    /**
     * Een afnemer moet voor dit attribuut geautoriseerd worden volgens de wet BRP (o.a. artikel 3.10)..
     */
    VERPLICHT("Verplicht", "Een afnemer moet voor dit attribuut geautoriseerd worden volgens de wet BRP (o.a. artikel 3.10)."),
    /**
     * Een afnemer zou bij voorkeur een autorisatie voor dit attribuut moeten krijgen. Er is echter geen wettelijke
     * grondslag..
     */
    AANBEVOLEN("Aanbevolen", "Een afnemer zou bij voorkeur een autorisatie voor dit attribuut moeten krijgen. Er is echter geen wettelijke grondslag."),
    /**
     * Het attribuut wordt alleen verstrekt aan bijhouders..
     */
    BIJHOUDINGSGEGEVENS("Bijhoudingsgegevens", "Het attribuut wordt alleen verstrekt aan bijhouders."),
    /**
     * Er geldt een niet-standaard autorisatie voor dit attribuut. Deze uitzondering wordt afgedwongen middels
     * bedrijfsregels in de beheerapplicatie..
     */
    UITZONDERING("Uitzondering",
            "Er geldt een niet-standaard autorisatie voor dit attribuut. Deze uitzondering wordt afgedwongen middels bedrijfsregels in de beheerapplicatie.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortElementAutorisatie
     * @param omschrijving Omschrijving voor SoortElementAutorisatie
     */
    private SoortElementAutorisatie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort element autorisatie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort element autorisatie.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.SOORTELEMENTAUTORISATIE;
    }

}
