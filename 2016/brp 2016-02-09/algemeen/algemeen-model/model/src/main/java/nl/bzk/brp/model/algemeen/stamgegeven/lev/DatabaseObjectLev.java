/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev;

import javax.annotation.Generated;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.jpa.usertypes.PersistentEnum;

/**
 * Een in de Database voorkomend object, waarvan kennis noodzakelijk is voor het kunnen uitvoeren van de
 * functionaliteit.
 *
 * In een database is er geen sprake van objecttypen, groepen en attributen, maar van tabellen, kolommen, indexen,
 * constraints e.d. Deze worden hier opgesomd, voor zover het nodig is voor de functionaliteit, bijvoorbeeld doordat er
 * een onderzoek kan zijn gestart naar een gegeven dat daarin staat, of omdat er verantwoording over is vastgelegd.
 *
 * De populatie wordt beperkt tot de historie tabellen en de kolommen daarbinnen; over indexen, constraint e.d. is nog
 * geen informatiebehoefte. Deze worden derhalve in deze tabel 'niet gekend'. Ook andere tabellen (zoals de actual-tabel
 * in de A-laag) wordt niet opgenomen. De reden hiervoor is dat het vullen van de database-object tabel weliswaar
 * gebeurd door gegenereerde code; voor elk soort database-object dan wel elke extra populatie (naast historie tabellen
 * ook actual tabellen? Extra code!) inspanning kost. Nu is het criterium eenvoudig: pas als er een behoefte is, wordt
 * het erin gestopt.
 *
 *
 * Deze enum is voor alle database objecten uit het Lev schema.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.DatabaseObjectGenerator")
public enum DatabaseObjectLev implements DatabaseObject, PersistentEnum {

    /**
     * Representeert de tabel: 'Lev'.
     */
    LEVERING(4799, "Levering", "Lev", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Lev'.
     */
    LEVERING__I_D(4812, "Levering - ID", "Lev.ID", LEVERING),
    /**
     * Representeert de kolom: 'ToegangAbonnement' van de tabel: 'Lev'.
     */
    LEVERING__TOEGANG_ABONNEMENT(4821, "Levering - Toegang abonnement", "Lev.ToegangAbonnement", LEVERING),
    /**
     * Representeert de kolom: 'Dienst' van de tabel: 'Lev'.
     */
    LEVERING__DIENST(11374, "Levering - Dienst", "Lev.Dienst", LEVERING),
    /**
     * Representeert de kolom: 'TsKlaarzettenLev' van de tabel: 'Lev'.
     */
    LEVERING__DATUM_TIJD_KLAARZETTEN_LEVERING(4823, "Levering - Datum/tijd klaarzetten levering", "Lev.TsKlaarzettenLev", LEVERING),
    /**
     * Representeert de kolom: 'DatMaterieelSelectie' van de tabel: 'Lev'.
     */
    LEVERING__DATUM_MATERIEEL_SELECTIE(11415, "Levering - Datum materieel selectie", "Lev.DatMaterieelSelectie", LEVERING),
    /**
     * Representeert de kolom: 'DatAanvMaterielePeriodeRes' van de tabel: 'Lev'.
     */
    LEVERING__DATUM_AANVANG_MATERIELE_PERIODE_RESULTAAT(11375, "Levering - Datum aanvang materi\uFFFDle periode resultaat",
            "Lev.DatAanvMaterielePeriodeRes", LEVERING),
    /**
     * Representeert de kolom: 'DatEindeMaterielePeriodeRes' van de tabel: 'Lev'.
     */
    LEVERING__DATUM_EINDE_MATERIELE_PERIODE_RESULTAAT(11416, "Levering - Datum einde materi\uFFFDle periode resultaat", "Lev.DatEindeMaterielePeriodeRes",
            LEVERING),
    /**
     * Representeert de kolom: 'TsAanvFormelePeriodeRes' van de tabel: 'Lev'.
     */
    LEVERING__DATUM_TIJD_AANVANG_FORMELE_PERIODE_RESULTAAT(11417, "Levering - Datum/tijd aanvang formele periode resultaat",
            "Lev.TsAanvFormelePeriodeRes", LEVERING),
    /**
     * Representeert de kolom: 'TsEindeFormelePeriodeRes' van de tabel: 'Lev'.
     */
    LEVERING__DATUM_TIJD_EINDE_FORMELE_PERIODE_RESULTAAT(4822, "Levering - Datum/tijd einde formele periode resultaat", "Lev.TsEindeFormelePeriodeRes",
            LEVERING),
    /**
     * Representeert de kolom: 'AdmHnd' van de tabel: 'Lev'.
     */
    LEVERING__ADMINISTRATIEVE_HANDELING(9573, "Levering - Administratieve handeling", "Lev.AdmHnd", LEVERING),
    /**
     * Representeert de kolom: 'SrtSynchronisatie' van de tabel: 'Lev'.
     */
    LEVERING__SOORT_SYNCHRONISATIE(11373, "Levering - Soort synchronisatie", "Lev.SrtSynchronisatie", LEVERING),
    /**
     * Representeert de tabel: 'LevPers'.
     */
    LEVERING_PERSOON(4803, "Levering \\ Persoon", "LevPers", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'LevPers'.
     */
    LEVERING_PERSOON__I_D(4841, "Levering \\ Persoon - ID", "LevPers.ID", LEVERING_PERSOON),
    /**
     * Representeert de kolom: 'Lev' van de tabel: 'LevPers'.
     */
    LEVERING_PERSOON__LEVERING(4842, "Levering \\ Persoon - Levering", "LevPers.Lev", LEVERING_PERSOON),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'LevPers'.
     */
    LEVERING_PERSOON__PERSOON(4843, "Levering \\ Persoon - Persoon", "LevPers.Pers", LEVERING_PERSOON);

    private Integer id;
    private String naam;
    private String databaseNaam;
    private DatabaseObjectLev ouder;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param id Id voor DatabaseObjectLev
     * @param naam Naam voor DatabaseObjectLev
     * @param databaseNaam DatabaseNaam voor DatabaseObjectLev
     * @param ouder Ouder voor DatabaseObjectLev
     */
    private DatabaseObjectLev(final Integer id, final String naam, final String databaseNaam, final DatabaseObjectLev ouder) {
        this.id = id;
        this.naam = naam;
        this.databaseNaam = databaseNaam;
        this.ouder = ouder;
    }

    /**
     * Retourneert id van DatabaseObjectLev.
     *
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retourneert naam van DatabaseObjectLev.
     *
     * @return naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert databaseNaam van DatabaseObjectLev.
     *
     * @return databaseNaam.
     */
    public String getDatabaseNaam() {
        return databaseNaam;
    }

    /**
     * Retourneert ouder van DatabaseObjectLev.
     *
     * @return ouder.
     */
    public DatabaseObjectLev getOuder() {
        return ouder;
    }

    /**
     * Geeft het database object met een bepaald id terug.
     *
     * @param id het id van het gezochte database object
     * @return het java type of null indien niet gevonden
     */
    public static DatabaseObjectLev findById(final Integer id) {
        DatabaseObjectLev databaseObject = null;
        for (DatabaseObjectLev enumWaarde : DatabaseObjectLev.values()) {
            if (enumWaarde.getId().equals(id)) {
                databaseObject = enumWaarde;
                break;
            }
        }
        return databaseObject;

    }

    /**
     * Geeft aan of dit database object een tabel is of niet.
     *
     * @return tabel (true) of kolom (false)
     */
    public Boolean isTabel() {
        return this.ouder == null;
    }

    /**
     * Geeft aan of dit database object een kolom is of niet.
     *
     * @return kolom (true) of tabel (false)
     */
    public Boolean isKolom() {
        return this.ouder != null;
    }

}
