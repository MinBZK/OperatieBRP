/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

/**
 * Aangever adreshouding.Identiteit.
 *
 */
public enum AangeverAdreshoudingIdentiteit {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY(null, null, null),
    /** De gezaghouder is de ouder, de voogd of de curator van de ingeschrevene.*/
    GEZAGHOUDER("G", "Gezaghouder", "De gezaghouder is de ouder, de voogd of de curator van de ingeschrevene."),
    /** De hoofd van de instelling waar de persoon verblijft. */
    HOOFDINSTELLING("H", "Hoofd instelling", "De hoofd van de instelling waar de persoon verblijft."),
    /** De ingeschrevene zelf. */
    INGESCHREVENE("I", "Ingeschrevene", "De ingeschrevene zelf."),
    /** Een kind dat op hetzelfde adres woont als de ouder doet de adresaangifte. */
    KIND("K", "Meerderjarig inwonend kind voor ouder", "Een kind dat op hetzelfde adres woont als de ouder doet de adresaangifte."),
    /** Een door de ingeschrevenes gemachtigde. */
    MEERJARIGE("M", "Meerderjarig gemachtigde", "Een door de ingeschrevenes gemachtigde."),
    /** Een ouder die op hetzelfde adres woont doet aangifte van het adres van het kind. */
    INWONEND("O", "Inwonend ouder voor meerderjarig kind", "Een ouder die op hetzelfde adres woont doet aangifte van het adres van het kind."),
    /** De inwonende echtgenoot/geregistreerd partner doet aangifte van het adres. */
    PARTNER("P", "Echtgenoot/geregistreerd partner", "De inwonende echtgenoot/geregistreerd partner doet aangifte van het adres.");

    private final String code;

    private final String naam;

    private final String omschrijving;

    /**
     * Constructor.
     * @param code de code
     * @param naam de naam
     * @param omschrijving de omschrijving
     */
    private AangeverAdreshoudingIdentiteit(final String code, final String naam, final String omschrijving) {
        this.code = code; this.naam = naam; this.omschrijving = omschrijving;
    }
    public String getCode() {
        return code;
    }

    public String getNaam() {
        return naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
