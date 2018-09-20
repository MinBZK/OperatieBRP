/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;

/**
 * De mogelijke soort actie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortActie implements ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Registratie geboorte.
     */
    REGISTRATIE_GEBOORTE("Registratie geboorte"),
    /**
     * Beeindiging vastgesteld niet Nederlander.
     */
    BEEINDIGING_VASTGESTELD_NIET_NEDERLANDER("Beeindiging vastgesteld niet Nederlander"),
    /**
     * Beeindiging bijzondere verblijfsrechtelijke positie.
     */
    BEEINDIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("Beeindiging bijzondere verblijfsrechtelijke positie"),
    /**
     * Registratie naamgebruik.
     */
    REGISTRATIE_NAAMGEBRUIK("Registratie naamgebruik"),
    /**
     * Registratie nationaliteit.
     */
    REGISTRATIE_NATIONALITEIT("Registratie nationaliteit"),
    /**
     * Registratie behandeld als Nederlander.
     */
    REGISTRATIE_BEHANDELD_ALS_NEDERLANDER("Registratie behandeld als Nederlander"),
    /**
     * Conversie GBA.
     */
    CONVERSIE_G_B_A("Conversie GBA"),
    /**
     * Registratie signalering reisdocument.
     */
    REGISTRATIE_SIGNALERING_REISDOCUMENT("Registratie signalering reisdocument"),
    /**
     * Registratie gezag.
     */
    REGISTRATIE_GEZAG("Registratie gezag"),
    /**
     * Registratie curatele.
     */
    REGISTRATIE_CURATELE("Registratie curatele"),
    /**
     * Registratie staatloos.
     */
    REGISTRATIE_STAATLOOS("Registratie staatloos"),
    /**
     * Registratie vastgesteld niet Nederlander.
     */
    REGISTRATIE_VASTGESTELD_NIET_NEDERLANDER("Registratie vastgesteld niet Nederlander"),
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
    REGISTRATIE_ONDERZOEK("Registratie onderzoek"),
    /**
     * Registratie ouder.
     */
    REGISTRATIE_OUDER("Registratie ouder"),
    /**
     * Beeindiging nationaliteit.
     */
    BEEINDIGING_NATIONALITEIT("Beeindiging nationaliteit"),
    /**
     * Beeindiging ouderschap.
     */
    BEEINDIGING_OUDERSCHAP("Beeindiging ouderschap"),
    /**
     * Beeindiging behandeld als Nederlander.
     */
    BEEINDIGING_BEHANDELD_ALS_NEDERLANDER("Beeindiging behandeld als Nederlander"),
    /**
     * Beeindiging verstrekkingsbeperking.
     */
    BEEINDIGING_VERSTREKKINGSBEPERKING("Beeindiging verstrekkingsbeperking"),
    /**
     * Registratie voornaam.
     */
    REGISTRATIE_VOORNAAM("Registratie voornaam"),
    /**
     * Beeindiging voornaam.
     */
    BEEINDIGING_VOORNAAM("Beeindiging voornaam"),
    /**
     * Registratie geslachtsnaam.
     */
    REGISTRATIE_GESLACHTSNAAM("Registratie geslachtsnaam"),
    /**
     * Registratie bijhouding.
     */
    REGISTRATIE_BIJHOUDING("Registratie bijhouding"),
    /**
     * Registratie aanvang huwelijk geregistreerd partnerschap.
     */
    REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("Registratie aanvang huwelijk geregistreerd partnerschap"),
    /**
     * Registratie einde huwelijk geregistreerd partnerschap.
     */
    REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("Registratie einde huwelijk geregistreerd partnerschap"),
    /**
     * Registratie huwelijk geregistreerd partnerschap.
     */
    REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("Registratie huwelijk geregistreerd partnerschap"),
    /**
     * Registratie afnemerindicatie.
     */
    REGISTRATIE_AFNEMERINDICATIE("Registratie afnemerindicatie"),
    /**
     * Verval afnemerindicatie.
     */
    VERVAL_AFNEMERINDICATIE("Verval afnemerindicatie"),
    /**
     * Verval overlijden.
     */
    VERVAL_OVERLIJDEN("Verval overlijden"),
    /**
     * Verval huwelijk geregistreerd partnerschap.
     */
    VERVAL_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("Verval huwelijk geregistreerd partnerschap"),
    /**
     * Registratie naam geslacht.
     */
    REGISTRATIE_NAAM_GESLACHT("Registratie naam geslacht"),
    /**
     * Registratie geslachtsaanduiding.
     */
    REGISTRATIE_GESLACHTSAANDUIDING("Registratie geslachtsaanduiding"),
    /**
     * Beeindiging staatloos.
     */
    BEEINDIGING_STAATLOOS("Beeindiging staatloos"),
    /**
     * Registratie nationaliteit naam.
     */
    REGISTRATIE_NATIONALITEIT_NAAM("Registratie nationaliteit naam"),
    /**
     * Registratie bijzondere verblijfsrechtelijke positie.
     */
    REGISTRATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("Registratie bijzondere verblijfsrechtelijke positie"),
    /**
     * Registratie identificatienummers.
     */
    REGISTRATIE_IDENTIFICATIENUMMERS("Registratie identificatienummers"),
    /**
     * Registratie reisdocument.
     */
    REGISTRATIE_REISDOCUMENT("Registratie reisdocument"),
    /**
     * Registratie uitsluiting kiesrecht.
     */
    REGISTRATIE_UITSLUITING_KIESRECHT("Registratie uitsluiting kiesrecht"),
    /**
     * Registratie deelname EU verkiezingen.
     */
    REGISTRATIE_DEELNAME_E_U_VERKIEZINGEN("Registratie deelname EU verkiezingen"),
    /**
     * Conversie GBA Materiële historie.
     */
    CONVERSIE_G_B_A_MATERIELE_HISTORIE("Conversie GBA Materiële historie"),
    /**
     * Registratie migratie.
     */
    REGISTRATIE_MIGRATIE("Registratie migratie"),
    /**
     * Registratie kind.
     */
    REGISTRATIE_KIND("Registratie kind"),
    /**
     * Wijziging onderzoek.
     */
    WIJZIGING_ONDERZOEK("Wijziging onderzoek"),
    /**
     * Conversie GBA Lege onjuist categorie.
     */
    CONVERSIE_G_B_A_LEGE_ONJUIST_CATEGORIE("Conversie GBA Lege onjuist categorie"),
    /**
     * Registratie ouderschap.
     */
    REGISTRATIE_OUDERSCHAP("Registratie ouderschap"),
    /**
     * Registratie naam voornaam.
     */
    REGISTRATIE_NAAM_VOORNAAM("Registratie naam voornaam"),
    /**
     * Registratie verblijfsrecht.
     */
    REGISTRATIE_VERBLIJFSRECHT("Registratie verblijfsrecht"),
    /**
     * Verval verblijfsrecht.
     */
    VERVAL_VERBLIJFSRECHT("Verval verblijfsrecht"),
    /**
     * Correctie nationaliteit.
     */
    CORRECTIE_NATIONALITEIT("Correctie nationaliteit");

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

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.SOORTACTIE;
    }

}
