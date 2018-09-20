/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.VasteAttribuutWaarde;

/**
 * Dit attribuuttype wordt intern in de Java code (en mogelijk in XSD's) gebruikt en heeft dus geen koppeling met een
 * attribuut.
 *
 * Gekozen naam wijkt af van andere soort typeringen. Echter naam reeds in gebruik, om extra werk te voorkomen huidige
 * naam gehandhaafd.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.AttribuutTypenGenerator")
public enum Verwerkingssoort implements VasteAttribuutWaarde<String> {

    /**
     * Object of gegevensgroep opgenomen ter identificatie.
     */
    IDENTIFICATIE("Identificatie", "Object of gegevensgroep opgenomen ter identificatie"),
    /**
     * Object of gegevensgroep opgenomen ter referentie.
     */
    REFERENTIE("Referentie", "Object of gegevensgroep opgenomen ter referentie"),
    /**
     * Toevoeging van een object of gegevensgroep.
     */
    TOEVOEGING("Toevoeging", "Toevoeging van een object of gegevensgroep"),
    /**
     * Wijziging van een object of gegevensgroep.
     */
    WIJZIGING("Wijziging", "Wijziging van een object of gegevensgroep"),
    /**
     * Verval van een object of gegevensgroep.
     */
    VERVAL("Verval", "Verval van een object of gegevensgroep"),
    /**
     * Verwijdering van een object of gegevensgroep.
     */
    VERWIJDERING("Verwijdering", "Verwijdering van een object of gegevensgroep");

    private final String vasteWaarde;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param vasteWaarde VasteWaarde voor Verwerkingssoort
     * @param omschrijving Omschrijving voor Verwerkingssoort
     */
    private Verwerkingssoort(final String vasteWaarde, final String omschrijving) {
        this.vasteWaarde = vasteWaarde;
        this.omschrijving = omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVasteWaarde() {
        return vasteWaarde;
    }

    /**
     * Retourneert omschrijving. voor Verwerkingssoort
     *
     * @return omschrijving voor Verwerkingssoort
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s - %s", vasteWaarde, omschrijving);
    }

}
