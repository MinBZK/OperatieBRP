/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;

/**
 * Het niveau van protocolleren dat van toepassing is.
 *
 * Aan de hand van het protocolleringsniveau wordt bepaald of een bepaalde verstrekking van gegevens aan de burger
 * getoond mag worden (indien de burger daar om vraagt), alsmede of er een beperking is voor gegevensambtenaren voor het
 * inzien van deze protocolgegevens.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Protocolleringsniveau implements BestaansPeriodeStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(((short) -1), "Dummy", "Dummy", null, null),
    /**
     * Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties..
     */
    GEEN_BEPERKINGEN(((short) 0), "Geen beperkingen",
            "Aan een burger desgevraagd inzage geven in de bij zijn persoonsgegevens gelegde afnemersindicaties.", new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot
     * deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd..
     */
    GEHEIM(
            ((short) 2),
            "Geheim",
            "Aan een burger wordt GEEN inzage verstrekt over de in kader van deze Autorisatie geleverde gegevens. Toegang tot deze gegevens is beperkt tot gemeenteambtenaren die daartoe expliciet zijn gautoriseerd.",
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231));

    private final Short code;
    private final String naam;
    private final String omschrijving;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param code Code voor Protocolleringsniveau
     * @param naam Naam voor Protocolleringsniveau
     * @param omschrijving Omschrijving voor Protocolleringsniveau
     * @param datumAanvangGeldigheid DatumAanvangGeldigheid voor Protocolleringsniveau
     * @param datumEindeGeldigheid DatumEindeGeldigheid voor Protocolleringsniveau
     */
    private Protocolleringsniveau(
        final Short code,
        final String naam,
        final String omschrijving,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.code = code;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Retourneert Code van Protocolleringsniveau.
     *
     * @return Code.
     */
    public Short getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Protocolleringsniveau.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Protocolleringsniveau.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Protocolleringsniveau.
     *
     * @return Datum aanvang geldigheid voor Protocolleringsniveau
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Protocolleringsniveau.
     *
     * @return Datum einde geldigheid voor Protocolleringsniveau
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
