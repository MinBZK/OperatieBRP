/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Typering van Partij.
 *
 * De soorten Partij zijn niet volledig disjunct. Zo is een willekeurige gemeente zowel een "Gemeente" als ook een
 * "Overheidsorgaan". De gebruikte typering is dan de meest beschrijvende typering: zo is de "Soort partij" voor een
 * gemeente gelijk aan "Gemeente", en niet aan "Overheidsorgaan".
 *
 * In de naamgeving van de Soorten partij is gekozen voor naamgeving die overlap suggereert: zo is een willekeurige
 * gemeente zowel een "Gemeente" als een "Overheidsorgaan". Een alternatieve naamgeving zou zijn geweest om dat
 * bijvoorbeeld in plaats van Overheidsorgaan het te hebben over "Overige overheidsorganen". Hiervoor is echter niet
 * gekozen. Het typeren van Partijen door middel van "Soort partij" is iets dat plaats vindt binnen een afdeling van ��n
 * (beheer)organisatie, waardoor verschillend gebruik van de typering niet heel waarschijnlijk is.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortPartij implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null, null),
    /**
     * Wetgever.
     */
    WETGEVER("Wetgever", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Vertegenwoordiger Regering.
     */
    VERTEGENWOORDIGER_REGERING("Vertegenwoordiger Regering", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Gemeente.
     */
    GEMEENTE("Gemeente", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Overheidsorgaan.
     */
    OVERHEIDSORGAAN("Overheidsorgaan", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Derde.
     */
    DERDE("Derde", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Samenwerkingsverband.
     */
    SAMENWERKINGSVERBAND("Samenwerkingsverband", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * BRP voorziening.
     */
    B_R_P_VOORZIENING("BRP voorziening", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231));

    private final String naam;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortPartij
     * @param datumAanvangGeldigheid DatumAanvangGeldigheid voor SoortPartij
     * @param datumEindeGeldigheid DatumEindeGeldigheid voor SoortPartij
     */
    private SoortPartij(
        final String naam,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Retourneert Naam van Soort partij.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Soort partij.
     *
     * @return Datum aanvang geldigheid voor Soort partij
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Soort partij.
     *
     * @return Datum einde geldigheid voor Soort partij
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.SOORTPARTIJ;
    }

}
