/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * De mogelijke soort actie.
 *
 * De soorten (BRP-)acties zijn nog niet bekend. Het zullen er ongetwijfeld meerdere worden. Voor nu ��n exemplaar erin
 * gestopt; omdat er geen goede naam is deze maar de naam 'dummy' gegevens. RvdP 23 november 2011.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum SoortActie {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Conversie GBA.
     */
    CONVERSIE_G_B_A("Conversie GBA"),
    /**
     * Registratie geboorte.
     */
    REGISTRATIE_GEBOORTE("Registratie geboorte"),
    /**
     * Registratie erkenning.
     */
    REGISTRATIE_ERKENNING("Registratie erkenning"),
    /**
     * Registratie aanschrijving.
     */
    REGISTRATIE_AANSCHRIJVING("Registratie aanschrijving"),
    /**
     * Registratie nationaliteit.
     */
    REGISTRATIE_NATIONALITEIT("Registratie nationaliteit"),
    /**
     * Registratie behandeld als nederlander.
     */
    REGISTRATIE_BEHANDELD_ALS_NEDERLANDER("Registratie behandeld als nederlander"),
    /**
     * Registratie belemmering verstrekking reisdocument.
     */
    REGISTRATIE_BELEMMERING_VERSTREKKING_REISDOCUMENT("Registratie belemmering verstrekking reisdocument"),
    /**
     * Registratie derde heeft gezag.
     */
    REGISTRATIE_DERDE_HEEFT_GEZAG("Registratie derde heeft gezag"),
    /**
     * Registratie onder curatele.
     */
    REGISTRATIE_ONDER_CURATELE("Registratie onder curatele"),
    /**
     * Registratie staatloos.
     */
    REGISTRATIE_STAATLOOS("Registratie staatloos"),
    /**
     * Registratie vastgesteld niet nederlander.
     */
    REGISTRATIE_VASTGESTELD_NIET_NEDERLANDER("Registratie vastgesteld niet nederlander"),
    /**
     * Registratie verstrekkingsbeperking.
     */
    REGISTRATIE_VERSTREKKINGSBEPERKING("Registratie verstrekkingsbeperking"),
    /**
     * Registratie afstamming.
     */
    REGISTRATIE_AFSTAMMING("Registratie afstamming"),
    /**
     * Correctie afstamming.
     */
    CORRECTIE_AFSTAMMING("Correctie afstamming"),
    /**
     * Registratie overlijden.
     */
    REGISTRATIE_OVERLIJDEN("Registratie overlijden"),
    /**
     * Correctie overlijden.
     */
    CORRECTIE_OVERLIJDEN("Correctie overlijden"),
    /**
     * Registratie adres.
     */
    REGISTRATIE_ADRES("Registratie adres"),
    /**
     * Correctie adres.
     */
    CORRECTIE_ADRES("Correctie adres"),
    /**
     * Registratie huwelijk.
     */
    REGISTRATIE_HUWELIJK("Registratie huwelijk"),
    /**
     * Registratie geregistreerd partnerschap.
     */
    REGISTRATIE_GEREGISTREERD_PARTNERSCHAP("Registratie geregistreerd partnerschap"),
    /**
     * Correctie huwelijk.
     */
    CORRECTIE_HUWELIJK("Correctie huwelijk"),
    /**
     * Correctie geregistreerd partnerschap.
     */
    CORRECTIE_GEREGISTREERD_PARTNERSCHAP("Correctie geregistreerd partnerschap"),
    /**
     * Registratie onderzoek.
     */
    REGISTRATIE_ONDERZOEK("Registratie onderzoek");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortActie
     */
    private SoortActie(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort actie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
