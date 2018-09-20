/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

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
 * Deze enum is voor alle database objecten uit het AutAut schema.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.DatabaseObjectGenerator")
public enum DatabaseObjectAutAut implements DatabaseObject, PersistentEnum {

    /**
     * Representeert de tabel: 'AandMedium'.
     */
    AANDUIDING_MEDIUM(11208, "Aanduiding medium", "AandMedium", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AandMedium'.
     */
    AANDUIDING_MEDIUM__I_D(11211, "Aanduiding medium - ID", "AandMedium.ID", AANDUIDING_MEDIUM),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'AandMedium'.
     */
    AANDUIDING_MEDIUM__NAAM(11213, "Aanduiding medium - Naam", "AandMedium.Naam", AANDUIDING_MEDIUM),
    /**
     * Representeert de tabel: 'Abonnement'.
     */
    ABONNEMENT(4856, "Abonnement", "Abonnement", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__I_D(4901, "Abonnement - ID", "Abonnement.ID", ABONNEMENT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__NAAM(10195, "Abonnement - Naam", "Abonnement.Naam", ABONNEMENT),
    /**
     * Representeert de kolom: 'Populatiebeperking' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__POPULATIEBEPERKING(4904, "Abonnement - Populatiebeperking", "Abonnement.Populatiebeperking", ABONNEMENT),
    /**
     * Representeert de kolom: 'Protocolleringsniveau' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__PROTOCOLLERINGSNIVEAU(4883, "Abonnement - Protocolleringsniveau", "Abonnement.Protocolleringsniveau", ABONNEMENT),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__DATUM_INGANG(10205, "Abonnement - Datum ingang", "Abonnement.DatIngang", ABONNEMENT),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__DATUM_EINDE(10206, "Abonnement - Datum einde", "Abonnement.DatEinde", ABONNEMENT),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__TOESTAND(10207, "Abonnement - Toestand", "Abonnement.Toestand", ABONNEMENT),
    /**
     * Representeert de kolom: 'IndAliasSrtAdmHndLeveren' van de tabel: 'Abonnement'.
     */
    ABONNEMENT__INDICATIE_ALIAS_SOORT_ADMINISTRATIEVE_HANDELING_LEVEREN(11439, "Abonnement - Alias soort administratieve handeling leveren?",
            "Abonnement.IndAliasSrtAdmHndLeveren", ABONNEMENT),
    /**
     * Representeert de tabel: 'AbonnementAttribuut'.
     */
    ABONNEMENT_ATTRIBUUT(4857, "Abonnement \\ Attribuut", "AbonnementAttribuut", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AbonnementAttribuut'.
     */
    ABONNEMENT_ATTRIBUUT__I_D(4914, "Abonnement \\ Attribuut - ID", "AbonnementAttribuut.ID", ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'AbonnementAttribuut'.
     */
    ABONNEMENT_ATTRIBUUT__ABONNEMENT(4907, "Abonnement \\ Attribuut - Abonnement", "AbonnementAttribuut.Abonnement", ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'Attribuut' van de tabel: 'AbonnementAttribuut'.
     */
    ABONNEMENT_ATTRIBUUT__ATTRIBUUT(4908, "Abonnement \\ Attribuut - Attribuut", "AbonnementAttribuut.Attribuut", ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'Actief' van de tabel: 'AbonnementAttribuut'.
     */
    ABONNEMENT_ATTRIBUUT__ACTIEF(10436, "Abonnement \\ Attribuut - Actief", "AbonnementAttribuut.Actief", ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de tabel: 'AbonnementDoelbinding'.
     */
    ABONNEMENT_DOELBINDING(10198, "Abonnement \\ Doelbinding", "AbonnementDoelbinding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AbonnementDoelbinding'.
     */
    ABONNEMENT_DOELBINDING__I_D(10200, "Abonnement \\ Doelbinding - ID", "AbonnementDoelbinding.ID", ABONNEMENT_DOELBINDING),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'AbonnementDoelbinding'.
     */
    ABONNEMENT_DOELBINDING__ABONNEMENT(10201, "Abonnement \\ Doelbinding - Abonnement", "AbonnementDoelbinding.Abonnement", ABONNEMENT_DOELBINDING),
    /**
     * Representeert de kolom: 'Doelbinding' van de tabel: 'AbonnementDoelbinding'.
     */
    ABONNEMENT_DOELBINDING__DOELBINDING(10202, "Abonnement \\ Doelbinding - Doelbinding", "AbonnementDoelbinding.Doelbinding", ABONNEMENT_DOELBINDING),
    /**
     * Representeert de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP(10491, "Abonnement \\ Groep", "AbonnementGroep", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__I_D(10494, "Abonnement \\ Groep - ID", "AbonnementGroep.ID", ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__ABONNEMENT(10496, "Abonnement \\ Groep - Abonnement", "AbonnementGroep.Abonnement", ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'Groep' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__GROEP(10497, "Abonnement \\ Groep - Groep", "AbonnementGroep.Groep", ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndFormeleHistorie' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__INDICATIE_FORMELE_HISTORIE(10502, "Abonnement \\ Groep - Formele historie?", "AbonnementGroep.IndFormeleHistorie", ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndMaterieleHistorie' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__INDICATIE_MATERIELE_HISTORIE(10501, "Abonnement \\ Groep - Materi\uFFFDle historie?", "AbonnementGroep.IndMaterieleHistorie",
            ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndNadereVerantwoording' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__INDICATIE_NADERE_VERANTWOORDING(10499, "Abonnement \\ Groep - Nadere verantwoording?", "AbonnementGroep.IndNadereVerantwoording",
            ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndDocInformatie' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__INDICATIE_DOCUMENT_INFORMATIE(14189, "Abonnement \\ Groep - Document informatie?", "AbonnementGroep.IndDocInformatie",
            ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndDoc' van de tabel: 'AbonnementGroep'.
     */
    ABONNEMENT_GROEP__INDICATIE_DOCUMENT(10500, "Abonnement \\ Groep - Document?", "AbonnementGroep.IndDoc", ABONNEMENT_GROEP),
    /**
     * Representeert de tabel: 'AbonnementLO3Rubriek'.
     */
    ABONNEMENT_L_O3_RUBRIEK(14190, "Abonnement \\ LO3 Rubriek", "AbonnementLO3Rubriek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AbonnementLO3Rubriek'.
     */
    ABONNEMENT_L_O3_RUBRIEK__I_D(14194, "Abonnement \\ LO3 Rubriek - ID", "AbonnementLO3Rubriek.ID", ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'AbonnementLO3Rubriek'.
     */
    ABONNEMENT_L_O3_RUBRIEK__ABONNEMENT(14195, "Abonnement \\ LO3 Rubriek - Abonnement", "AbonnementLO3Rubriek.Abonnement", ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'Rubr' van de tabel: 'AbonnementLO3Rubriek'.
     */
    ABONNEMENT_L_O3_RUBRIEK__RUBRIEK(14221, "Abonnement \\ LO3 Rubriek - Rubriek", "AbonnementLO3Rubriek.Rubr", ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'Aktief' van de tabel: 'AbonnementLO3Rubriek'.
     */
    ABONNEMENT_L_O3_RUBRIEK__AKTIEF(14196, "Abonnement \\ LO3 Rubriek - Aktief", "AbonnementLO3Rubriek.Aktief", ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de tabel: 'Afleverwijze'.
     */
    AFLEVERWIJZE(10598, "Afleverwijze", "Afleverwijze", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Afleverwijze'.
     */
    AFLEVERWIJZE__I_D(10601, "Afleverwijze - ID", "Afleverwijze.ID", AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'ToegangAbonnement' van de tabel: 'Afleverwijze'.
     */
    AFLEVERWIJZE__TOEGANG_ABONNEMENT(10602, "Afleverwijze - Toegang abonnement", "Afleverwijze.ToegangAbonnement", AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'Kanaal' van de tabel: 'Afleverwijze'.
     */
    AFLEVERWIJZE__KANAAL(10613, "Afleverwijze - Kanaal", "Afleverwijze.Kanaal", AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'Uri' van de tabel: 'Afleverwijze'.
     */
    AFLEVERWIJZE__URI(9862, "Afleverwijze - Uri", "Afleverwijze.Uri", AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'Afleverwijze'.
     */
    AFLEVERWIJZE__DATUM_INGANG(10615, "Afleverwijze - Datum ingang", "Afleverwijze.DatIngang", AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Afleverwijze'.
     */
    AFLEVERWIJZE__DATUM_EINDE(10616, "Afleverwijze - Datum einde", "Afleverwijze.DatEinde", AFLEVERWIJZE),
    /**
     * Representeert de tabel: 'Authenticatiemiddel'.
     */
    AUTHENTICATIEMIDDEL(2228, "Authenticatiemiddel", "Authenticatiemiddel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Authenticatiemiddel'.
     */
    AUTHENTICATIEMIDDEL__I_D(2231, "Authenticatiemiddel - ID", "Authenticatiemiddel.ID", AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'Authenticatiemiddel'.
     */
    AUTHENTICATIEMIDDEL__PARTIJ(2232, "Authenticatiemiddel - Partij", "Authenticatiemiddel.Partij", AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'Rol' van de tabel: 'Authenticatiemiddel'.
     */
    AUTHENTICATIEMIDDEL__ROL(2233, "Authenticatiemiddel - Rol", "Authenticatiemiddel.Rol", AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'Certificaat' van de tabel: 'Authenticatiemiddel'.
     */
    AUTHENTICATIEMIDDEL__CERTIFICAAT(4668, "Authenticatiemiddel - Certificaat", "Authenticatiemiddel.Certificaat", AUTHENTICATIEMIDDEL),
    /**
     * Representeert de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT(4852, "Autorisatiebesluit", "Autorisatiebesluit", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__I_D(4861, "Autorisatiebesluit - ID", "Autorisatiebesluit.ID", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__NAAM(10194, "Autorisatiebesluit - Naam", "Autorisatiebesluit.Naam", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Srt' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__SOORT(4863, "Autorisatiebesluit - Soort", "Autorisatiebesluit.Srt", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Besluittekst' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__BESLUITTEKST(4865, "Autorisatiebesluit - Besluittekst", "Autorisatiebesluit.Besluittekst", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Autoriseerder' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__AUTORISEERDER(4866, "Autorisatiebesluit - Autoriseerder", "Autorisatiebesluit.Autoriseerder", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatBesluit' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__DATUM_BESLUIT(5636, "Autorisatiebesluit - Datum besluit", "Autorisatiebesluit.DatBesluit", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__DATUM_INGANG(6086, "Autorisatiebesluit - Datum ingang", "Autorisatiebesluit.DatIngang", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__DATUM_EINDE(6094, "Autorisatiebesluit - Datum einde", "Autorisatiebesluit.DatEinde", AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'Autorisatiebesluit'.
     */
    AUTORISATIEBESLUIT__TOESTAND(5677, "Autorisatiebesluit - Toestand", "Autorisatiebesluit.Toestand", AUTORISATIEBESLUIT),
    /**
     * Representeert de tabel: 'AutorisatiebesluitPartij'.
     */
    AUTORISATIEBESLUIT_PARTIJ(10217, "Autorisatiebesluit \\ Partij", "AutorisatiebesluitPartij", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'AutorisatiebesluitPartij'.
     */
    AUTORISATIEBESLUIT_PARTIJ__I_D(10219, "Autorisatiebesluit \\ Partij - ID", "AutorisatiebesluitPartij.ID", AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'Autorisatiebesluit' van de tabel: 'AutorisatiebesluitPartij'.
     */
    AUTORISATIEBESLUIT_PARTIJ__AUTORISATIEBESLUIT(10220, "Autorisatiebesluit \\ Partij - Autorisatiebesluit",
            "AutorisatiebesluitPartij.Autorisatiebesluit", AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'AutorisatiebesluitPartij'.
     */
    AUTORISATIEBESLUIT_PARTIJ__PARTIJ(10221, "Autorisatiebesluit \\ Partij - Partij", "AutorisatiebesluitPartij.Partij", AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'AutorisatiebesluitPartij'.
     */
    AUTORISATIEBESLUIT_PARTIJ__DATUM_INGANG(10225, "Autorisatiebesluit \\ Partij - Datum ingang", "AutorisatiebesluitPartij.DatIngang",
            AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'AutorisatiebesluitPartij'.
     */
    AUTORISATIEBESLUIT_PARTIJ__DATUM_EINDE(10226, "Autorisatiebesluit \\ Partij - Datum einde", "AutorisatiebesluitPartij.DatEinde",
            AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE(5684, "Bijhoudingsautorisatie", "Bijhautorisatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__I_D(5687, "Bijhoudingsautorisatie - ID", "Bijhautorisatie.ID", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'Bijhautorisatiebesluit' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__BIJHOUDINGSAUTORISATIEBESLUIT(5691, "Bijhoudingsautorisatie - Bijhoudingsautorisatiebesluit",
            "Bijhautorisatie.Bijhautorisatiebesluit", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'SrtBevoegdheid' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__SOORT_BEVOEGDHEID(5713, "Bijhoudingsautorisatie - Soort bevoegdheid", "Bijhautorisatie.SrtBevoegdheid", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'GeautoriseerdeSrtPartij' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__GEAUTORISEERDE_SOORT_PARTIJ(5704, "Bijhoudingsautorisatie - Geautoriseerde soort partij",
            "Bijhautorisatie.GeautoriseerdeSrtPartij", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'GeautoriseerdePartij' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__GEAUTORISEERDE_PARTIJ(5703, "Bijhoudingsautorisatie - Geautoriseerde partij", "Bijhautorisatie.GeautoriseerdePartij",
            BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__TOESTAND(5689, "Bijhoudingsautorisatie - Toestand", "Bijhautorisatie.Toestand", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'CategoriePersonen' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__CATEGORIE_PERSONEN(6165, "Bijhoudingsautorisatie - Categorie personen", "Bijhautorisatie.CategoriePersonen",
            BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__OMSCHRIJVING(5690, "Bijhoudingsautorisatie - Omschrijving", "Bijhautorisatie.Oms", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__DATUM_INGANG(6095, "Bijhoudingsautorisatie - Datum ingang", "Bijhautorisatie.DatIngang", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Bijhautorisatie'.
     */
    BIJHOUDINGSAUTORISATIE__DATUM_EINDE(6096, "Bijhoudingsautorisatie - Datum einde", "Bijhautorisatie.DatEinde", BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de tabel: 'Bijhsituatie'.
     */
    BIJHOUDINGSSITUATIE(5692, "Bijhoudingssituatie", "Bijhsituatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Bijhsituatie'.
     */
    BIJHOUDINGSSITUATIE__I_D(5695, "Bijhoudingssituatie - ID", "Bijhsituatie.ID", BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'Bijhautorisatie' van de tabel: 'Bijhsituatie'.
     */
    BIJHOUDINGSSITUATIE__BIJHOUDINGSAUTORISATIE(5701, "Bijhoudingssituatie - Bijhoudingsautorisatie", "Bijhsituatie.Bijhautorisatie", BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'SrtAdmHnd' van de tabel: 'Bijhsituatie'.
     */
    BIJHOUDINGSSITUATIE__SOORT_ADMINISTRATIEVE_HANDELING(6039, "Bijhoudingssituatie - Soort administratieve handeling", "Bijhsituatie.SrtAdmHnd",
            BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'SrtDoc' van de tabel: 'Bijhsituatie'.
     */
    BIJHOUDINGSSITUATIE__SOORT_DOCUMENT(5698, "Bijhoudingssituatie - Soort document", "Bijhsituatie.SrtDoc", BIJHOUDINGSSITUATIE),
    /**
     * Representeert de tabel: 'CatalogusOptie'.
     */
    CATALOGUS_OPTIE(4905, "Catalogus optie", "CatalogusOptie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'CatalogusOptie'.
     */
    CATALOGUS_OPTIE__I_D(4928, "Catalogus optie - ID", "CatalogusOptie.ID", CATALOGUS_OPTIE),
    /**
     * Representeert de kolom: 'CategorieDienst' van de tabel: 'CatalogusOptie'.
     */
    CATALOGUS_OPTIE__CATEGORIE_DIENST(11199, "Catalogus optie - Categorie dienst", "CatalogusOptie.CategorieDienst", CATALOGUS_OPTIE),
    /**
     * Representeert de kolom: 'EffectAfnemerindicaties' van de tabel: 'CatalogusOptie'.
     */
    CATALOGUS_OPTIE__EFFECT_AFNEMERINDICATIES(11205, "Catalogus optie - Effect afnemerindicaties", "CatalogusOptie.EffectAfnemerindicaties",
            CATALOGUS_OPTIE),
    /**
     * Representeert de kolom: 'AandMedium' van de tabel: 'CatalogusOptie'.
     */
    CATALOGUS_OPTIE__AANDUIDING_MEDIUM(11218, "Catalogus optie - Aanduiding medium", "CatalogusOptie.AandMedium", CATALOGUS_OPTIE),
    /**
     * Representeert de tabel: 'CategorieDienst'.
     */
    CATEGORIE_DIENST(11198, "Categorie dienst", "CategorieDienst", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'CategorieDienst'.
     */
    CATEGORIE_DIENST__I_D(11221, "Categorie dienst - ID", "CategorieDienst.ID", CATEGORIE_DIENST),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'CategorieDienst'.
     */
    CATEGORIE_DIENST__NAAM(11222, "Categorie dienst - Naam", "CategorieDienst.Naam", CATEGORIE_DIENST),
    /**
     * Representeert de tabel: 'Certificaat'.
     */
    CERTIFICAAT(4693, "Certificaat", "Certificaat", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Certificaat'.
     */
    CERTIFICAAT__I_D(4700, "Certificaat - ID", "Certificaat.ID", CERTIFICAAT),
    /**
     * Representeert de kolom: 'Subject' van de tabel: 'Certificaat'.
     */
    CERTIFICAAT__SUBJECT(4697, "Certificaat - Subject", "Certificaat.Subject", CERTIFICAAT),
    /**
     * Representeert de kolom: 'Serial' van de tabel: 'Certificaat'.
     */
    CERTIFICAAT__SERIAL(4698, "Certificaat - Serial", "Certificaat.Serial", CERTIFICAAT),
    /**
     * Representeert de kolom: 'Signature' van de tabel: 'Certificaat'.
     */
    CERTIFICAAT__SIGNATURE(5034, "Certificaat - Signature", "Certificaat.Signature", CERTIFICAAT),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'Certificaat'.
     */
    CERTIFICAAT__PARTIJ(10235, "Certificaat - Partij", "Certificaat.Partij", CERTIFICAAT),
    /**
     * Representeert de tabel: 'Dienst'.
     */
    DIENST(10191, "Dienst", "Dienst", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Dienst'.
     */
    DIENST__I_D(10193, "Dienst - ID", "Dienst.ID", DIENST),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'Dienst'.
     */
    DIENST__ABONNEMENT(10210, "Dienst - Abonnement", "Dienst.Abonnement", DIENST),
    /**
     * Representeert de kolom: 'CatalogusOptie' van de tabel: 'Dienst'.
     */
    DIENST__CATALOGUS_OPTIE(4903, "Dienst - Catalogus optie", "Dienst.CatalogusOptie", DIENST),
    /**
     * Representeert de kolom: 'NaderePopulatiebeperking' van de tabel: 'Dienst'.
     */
    DIENST__NADERE_POPULATIEBEPERKING(10212, "Dienst - Nadere populatiebeperking", "Dienst.NaderePopulatiebeperking", DIENST),
    /**
     * Representeert de kolom: 'Attenderingscriterium' van de tabel: 'Dienst'.
     */
    DIENST__ATTENDERINGSCRITERIUM(11196, "Dienst - Attenderingscriterium", "Dienst.Attenderingscriterium", DIENST),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'Dienst'.
     */
    DIENST__DATUM_INGANG(10214, "Dienst - Datum ingang", "Dienst.DatIngang", DIENST),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'Dienst'.
     */
    DIENST__DATUM_EINDE(10215, "Dienst - Datum einde", "Dienst.DatEinde", DIENST),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'Dienst'.
     */
    DIENST__TOESTAND(10213, "Dienst - Toestand", "Dienst.Toestand", DIENST),
    /**
     * Representeert de kolom: 'EersteSelectiedat' van de tabel: 'Dienst'.
     */
    DIENST__EERSTE_SELECTIEDATUM(10531, "Dienst - Eerste selectiedatum", "Dienst.EersteSelectiedat", DIENST),
    /**
     * Representeert de kolom: 'SelectieperiodeInMaanden' van de tabel: 'Dienst'.
     */
    DIENST__SELECTIEPERIODE_IN_MAANDEN(10533, "Dienst - Selectieperiode in maanden", "Dienst.SelectieperiodeInMaanden", DIENST),
    /**
     * Representeert de tabel: 'Doelbinding'.
     */
    DOELBINDING(4853, "Doelbinding", "Doelbinding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Doelbinding'.
     */
    DOELBINDING__I_D(4875, "Doelbinding - ID", "Doelbinding.ID", DOELBINDING),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Doelbinding'.
     */
    DOELBINDING__NAAM(10208, "Doelbinding - Naam", "Doelbinding.Naam", DOELBINDING),
    /**
     * Representeert de kolom: 'Levsautorisatiebesluit' van de tabel: 'Doelbinding'.
     */
    DOELBINDING__LEVERINGSAUTORISATIEBESLUIT(4885, "Doelbinding - Leveringsautorisatiebesluit", "Doelbinding.Levsautorisatiebesluit", DOELBINDING),
    /**
     * Representeert de kolom: 'TekstDoelbinding' van de tabel: 'Doelbinding'.
     */
    DOELBINDING__TEKST_DOELBINDING(4887, "Doelbinding - Tekst doelbinding", "Doelbinding.TekstDoelbinding", DOELBINDING),
    /**
     * Representeert de tabel: 'EffectAfnemerindicaties'.
     */
    EFFECT_AFNEMERINDICATIES(11200, "Effect afnemerindicaties", "EffectAfnemerindicaties", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'EffectAfnemerindicaties'.
     */
    EFFECT_AFNEMERINDICATIES__I_D(11203, "Effect afnemerindicaties - ID", "EffectAfnemerindicaties.ID", EFFECT_AFNEMERINDICATIES),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'EffectAfnemerindicaties'.
     */
    EFFECT_AFNEMERINDICATIES__NAAM(11204, "Effect afnemerindicaties - Naam", "EffectAfnemerindicaties.Naam", EFFECT_AFNEMERINDICATIES),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'EffectAfnemerindicaties'.
     */
    EFFECT_AFNEMERINDICATIES__OMSCHRIJVING(11363, "Effect afnemerindicaties - Omschrijving", "EffectAfnemerindicaties.Oms", EFFECT_AFNEMERINDICATIES),
    /**
     * Representeert de tabel: 'Functie'.
     */
    FUNCTIE(4663, "Functie", "Functie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Functie'.
     */
    FUNCTIE__I_D(4674, "Functie - ID", "Functie.ID", FUNCTIE),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Functie'.
     */
    FUNCTIE__NAAM(4675, "Functie - Naam", "Functie.Naam", FUNCTIE),
    /**
     * Representeert de tabel: 'Kanaal'.
     */
    KANAAL(10603, "Kanaal", "Kanaal", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Kanaal'.
     */
    KANAAL__I_D(10610, "Kanaal - ID", "Kanaal.ID", KANAAL),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Kanaal'.
     */
    KANAAL__NAAM(10611, "Kanaal - Naam", "Kanaal.Naam", KANAAL),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Kanaal'.
     */
    KANAAL__OMSCHRIJVING(10612, "Kanaal - Omschrijving", "Kanaal.Oms", KANAAL),
    /**
     * Representeert de tabel: 'PartijIPAdres'.
     */
    PARTIJ_I_P_ADRES(10238, "Partij \\ IP adres", "PartijIPAdres", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PartijIPAdres'.
     */
    PARTIJ_I_P_ADRES__I_D(10240, "Partij \\ IP adres - ID", "PartijIPAdres.ID", PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'PartijIPAdres'.
     */
    PARTIJ_I_P_ADRES__PARTIJ(10242, "Partij \\ IP adres - Partij", "PartijIPAdres.Partij", PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'IPAdres' van de tabel: 'PartijIPAdres'.
     */
    PARTIJ_I_P_ADRES__I_P_ADRES(10243, "Partij \\ IP adres - IP adres", "PartijIPAdres.IPAdres", PARTIJ_I_P_ADRES),
    /**
     * Representeert de tabel: 'PersAfnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIE(10317, "Persoon \\ Afnemerindicatie", "PersAfnemerindicatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'PersAfnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIE__I_D(10321, "Persoon \\ Afnemerindicatie - ID", "PersAfnemerindicatie.ID", PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'Pers' van de tabel: 'PersAfnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIE__PERSOON(10324, "Persoon \\ Afnemerindicatie - Persoon", "PersAfnemerindicatie.Pers", PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'Afnemer' van de tabel: 'PersAfnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIE__AFNEMER(10343, "Persoon \\ Afnemerindicatie - Afnemer", "PersAfnemerindicatie.Afnemer", PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'PersAfnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIE__ABONNEMENT(10323, "Persoon \\ Afnemerindicatie - Abonnement", "PersAfnemerindicatie.Abonnement", PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'DatAanvMaterielePeriode' van de tabel: 'PersAfnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIE__DATUM_AANVANG_MATERIELE_PERIODE(10327, "Persoon \\ Afnemerindicatie - Datum aanvang materi\uFFFDle periode",
            "PersAfnemerindicatie.DatAanvMaterielePeriode", PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'TsEindeVolgen' van de tabel: 'PersAfnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIE__DATUM_TIJD_EINDE_VOLGEN(10328, "Persoon \\ Afnemerindicatie - Datum/tijd einde volgen",
            "PersAfnemerindicatie.TsEindeVolgen", PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de tabel: 'Protocolleringsniveau'.
     */
    PROTOCOLLERINGSNIVEAU(4881, "Protocolleringsniveau", "Protocolleringsniveau", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Protocolleringsniveau'.
     */
    PROTOCOLLERINGSNIVEAU__I_D(4921, "Protocolleringsniveau - ID", "Protocolleringsniveau.ID", PROTOCOLLERINGSNIVEAU),
    /**
     * Representeert de kolom: 'Code' van de tabel: 'Protocolleringsniveau'.
     */
    PROTOCOLLERINGSNIVEAU__CODE(5030, "Protocolleringsniveau - Code", "Protocolleringsniveau.Code", PROTOCOLLERINGSNIVEAU),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Protocolleringsniveau'.
     */
    PROTOCOLLERINGSNIVEAU__NAAM(4922, "Protocolleringsniveau - Naam", "Protocolleringsniveau.Naam", PROTOCOLLERINGSNIVEAU),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Protocolleringsniveau'.
     */
    PROTOCOLLERINGSNIVEAU__OMSCHRIJVING(4934, "Protocolleringsniveau - Omschrijving", "Protocolleringsniveau.Oms", PROTOCOLLERINGSNIVEAU),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Protocolleringsniveau'.
     */
    PROTOCOLLERINGSNIVEAU__DATUM_AANVANG_GELDIGHEID(4945, "Protocolleringsniveau - Datum aanvang geldigheid", "Protocolleringsniveau.DatAanvGel",
            PROTOCOLLERINGSNIVEAU),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Protocolleringsniveau'.
     */
    PROTOCOLLERINGSNIVEAU__DATUM_EINDE_GELDIGHEID(4946, "Protocolleringsniveau - Datum einde geldigheid", "Protocolleringsniveau.DatEindeGel",
            PROTOCOLLERINGSNIVEAU),
    /**
     * Representeert de tabel: 'SrtAutorisatiebesluit'.
     */
    SOORT_AUTORISATIEBESLUIT(4876, "Soort autorisatiebesluit", "SrtAutorisatiebesluit", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtAutorisatiebesluit'.
     */
    SOORT_AUTORISATIEBESLUIT__I_D(4879, "Soort autorisatiebesluit - ID", "SrtAutorisatiebesluit.ID", SOORT_AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtAutorisatiebesluit'.
     */
    SOORT_AUTORISATIEBESLUIT__NAAM(4932, "Soort autorisatiebesluit - Naam", "SrtAutorisatiebesluit.Naam", SOORT_AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtAutorisatiebesluit'.
     */
    SOORT_AUTORISATIEBESLUIT__OMSCHRIJVING(4880, "Soort autorisatiebesluit - Omschrijving", "SrtAutorisatiebesluit.Oms", SOORT_AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'SrtAutorisatiebesluit'.
     */
    SOORT_AUTORISATIEBESLUIT__DATUM_AANVANG_GELDIGHEID(4949, "Soort autorisatiebesluit - Datum aanvang geldigheid", "SrtAutorisatiebesluit.DatAanvGel",
            SOORT_AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'SrtAutorisatiebesluit'.
     */
    SOORT_AUTORISATIEBESLUIT__DATUM_EINDE_GELDIGHEID(4950, "Soort autorisatiebesluit - Datum einde geldigheid", "SrtAutorisatiebesluit.DatEindeGel",
            SOORT_AUTORISATIEBESLUIT),
    /**
     * Representeert de tabel: 'SrtBevoegdheid'.
     */
    SOORT_BEVOEGDHEID(5707, "Soort bevoegdheid", "SrtBevoegdheid", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'SrtBevoegdheid'.
     */
    SOORT_BEVOEGDHEID__I_D(5710, "Soort bevoegdheid - ID", "SrtBevoegdheid.ID", SOORT_BEVOEGDHEID),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'SrtBevoegdheid'.
     */
    SOORT_BEVOEGDHEID__NAAM(5711, "Soort bevoegdheid - Naam", "SrtBevoegdheid.Naam", SOORT_BEVOEGDHEID),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'SrtBevoegdheid'.
     */
    SOORT_BEVOEGDHEID__OMSCHRIJVING(5712, "Soort bevoegdheid - Omschrijving", "SrtBevoegdheid.Oms", SOORT_BEVOEGDHEID),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'SrtBevoegdheid'.
     */
    SOORT_BEVOEGDHEID__DATUM_AANVANG_GELDIGHEID(5719, "Soort bevoegdheid - Datum aanvang geldigheid", "SrtBevoegdheid.DatAanvGel", SOORT_BEVOEGDHEID),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'SrtBevoegdheid'.
     */
    SOORT_BEVOEGDHEID__DATUM_EINDE_GELDIGHEID(5720, "Soort bevoegdheid - Datum einde geldigheid", "SrtBevoegdheid.DatEindeGel", SOORT_BEVOEGDHEID),
    /**
     * Representeert de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT(10227, "Toegang abonnement", "ToegangAbonnement", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT__I_D(10229, "Toegang abonnement - ID", "ToegangAbonnement.ID", TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'Partij' van de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT__PARTIJ(10230, "Toegang abonnement - Partij", "ToegangAbonnement.Partij", TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'Authenticatiemiddel' van de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT__AUTHENTICATIEMIDDEL(10231, "Toegang abonnement - Authenticatiemiddel", "ToegangAbonnement.Authenticatiemiddel", TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'Intermediair' van de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT__INTERMEDIAIR(10233, "Toegang abonnement - Intermediair", "ToegangAbonnement.Intermediair", TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT__ABONNEMENT(10232, "Toegang abonnement - Abonnement", "ToegangAbonnement.Abonnement", TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT__DATUM_INGANG(10251, "Toegang abonnement - Datum ingang", "ToegangAbonnement.DatIngang", TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'ToegangAbonnement'.
     */
    TOEGANG_ABONNEMENT__DATUM_EINDE(10252, "Toegang abonnement - Datum einde", "ToegangAbonnement.DatEinde", TOEGANG_ABONNEMENT),
    /**
     * Representeert de tabel: 'Toestand'.
     */
    TOESTAND(5678, "Toestand", "Toestand", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Toestand'.
     */
    TOESTAND__I_D(5681, "Toestand - ID", "Toestand.ID", TOESTAND),
    /**
     * Representeert de kolom: 'Naam' van de tabel: 'Toestand'.
     */
    TOESTAND__NAAM(5682, "Toestand - Naam", "Toestand.Naam", TOESTAND),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'Toestand'.
     */
    TOESTAND__OMSCHRIJVING(5683, "Toestand - Omschrijving", "Toestand.Oms", TOESTAND),
    /**
     * Representeert de kolom: 'DatAanvGel' van de tabel: 'Toestand'.
     */
    TOESTAND__DATUM_AANVANG_GELDIGHEID(5721, "Toestand - Datum aanvang geldigheid", "Toestand.DatAanvGel", TOESTAND),
    /**
     * Representeert de kolom: 'DatEindeGel' van de tabel: 'Toestand'.
     */
    TOESTAND__DATUM_EINDE_GELDIGHEID(5722, "Toestand - Datum einde geldigheid", "Toestand.DatEindeGel", TOESTAND),
    /**
     * Representeert de tabel: 'Uitgeslotene'.
     */
    UITGESLOTENE(5775, "Uitgeslotene", "Uitgeslotene", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'Uitgeslotene'.
     */
    UITGESLOTENE__I_D(5778, "Uitgeslotene - ID", "Uitgeslotene.ID", UITGESLOTENE),
    /**
     * Representeert de kolom: 'Bijhautorisatie' van de tabel: 'Uitgeslotene'.
     */
    UITGESLOTENE__BIJHOUDINGSAUTORISATIE(5779, "Uitgeslotene - Bijhoudingsautorisatie", "Uitgeslotene.Bijhautorisatie", UITGESLOTENE),
    /**
     * Representeert de kolom: 'UitgeslotenPartij' van de tabel: 'Uitgeslotene'.
     */
    UITGESLOTENE__UITGESLOTEN_PARTIJ(5780, "Uitgeslotene - Uitgesloten partij", "Uitgeslotene.UitgeslotenPartij", UITGESLOTENE),
    /**
     * Representeert de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT(4899, "His Abonnement", "His_Abonnement", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__I_D(4996, "His Abonnement - ID", "His_Abonnement.ID", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'Abonnement' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__ABONNEMENT(4952, "His Abonnement - Abonnement", "His_Abonnement.Abonnement", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__DATUM_TIJD_REGISTRATIE(4953, "His Abonnement - Datum/tijd registratie", "His_Abonnement.TsReg", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__DATUM_TIJD_VERVAL(4954, "His Abonnement - Datum/tijd verval", "His_Abonnement.TsVerval", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__NADERE_AANDUIDING_VERVAL(11106, "His Abonnement - Nadere aanduiding verval", "His_Abonnement.NadereAandVerval", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__B_R_P_ACTIE_INHOUD(4955, "His Abonnement - BRP Actie inhoud", "His_Abonnement.ActieInh", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__B_R_P_ACTIE_VERVAL(4956, "His Abonnement - BRP Actie verval", "His_Abonnement.ActieVerval", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'Populatiebeperking' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__POPULATIEBEPERKING(9681, "His Abonnement - Populatiebeperking", "His_Abonnement.Populatiebeperking", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'Protocolleringsniveau' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__PROTOCOLLERINGSNIVEAU(9705, "His Abonnement - Protocolleringsniveau", "His_Abonnement.Protocolleringsniveau", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__DATUM_INGANG(10257, "His Abonnement - Datum ingang", "His_Abonnement.DatIngang", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__DATUM_EINDE(10258, "His Abonnement - Datum einde", "His_Abonnement.DatEinde", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__TOESTAND(10259, "His Abonnement - Toestand", "His_Abonnement.Toestand", HIS__ABONNEMENT),
    /**
     * Representeert de kolom: 'IndAliasSrtAdmHndLeveren' van de tabel: 'His_Abonnement'.
     */
    HIS__ABONNEMENT__INDICATIE_ALIAS_SOORT_ADMINISTRATIEVE_HANDELING_LEVEREN(11440, "His Abonnement - Alias soort administratieve handeling leveren?",
            "His_Abonnement.IndAliasSrtAdmHndLeveren", HIS__ABONNEMENT),
    /**
     * Representeert de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT(10435, "His Abonnement \\ Attribuut", "His_AbonnementAttribuut", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__I_D(10452, "His Abonnement \\ Attribuut - ID", "His_AbonnementAttribuut.ID", HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'AbonnementAttribuut' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__ABONNEMENT_ATTRIBUUT(14197, "His Abonnement \\ Attribuut - Abonnement \\ Attribuut",
            "His_AbonnementAttribuut.AbonnementAttribuut", HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__DATUM_TIJD_REGISTRATIE(10442, "His Abonnement \\ Attribuut - Datum/tijd registratie", "His_AbonnementAttribuut.TsReg",
            HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__DATUM_TIJD_VERVAL(10443, "His Abonnement \\ Attribuut - Datum/tijd verval", "His_AbonnementAttribuut.TsVerval",
            HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__NADERE_AANDUIDING_VERVAL(11107, "His Abonnement \\ Attribuut - Nadere aanduiding verval",
            "His_AbonnementAttribuut.NadereAandVerval", HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__B_R_P_ACTIE_INHOUD(10444, "His Abonnement \\ Attribuut - BRP Actie inhoud", "His_AbonnementAttribuut.ActieInh",
            HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__B_R_P_ACTIE_VERVAL(10445, "His Abonnement \\ Attribuut - BRP Actie verval", "His_AbonnementAttribuut.ActieVerval",
            HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de kolom: 'Actief' van de tabel: 'His_AbonnementAttribuut'.
     */
    HIS__ABONNEMENT_ATTRIBUUT__ACTIEF(10446, "His Abonnement \\ Attribuut - Actief", "His_AbonnementAttribuut.Actief", HIS__ABONNEMENT_ATTRIBUUT),
    /**
     * Representeert de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP(10498, "His Abonnement \\ Groep", "His_AbonnementGroep", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__I_D(10517, "His Abonnement \\ Groep - ID", "His_AbonnementGroep.ID", HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'AbonnementGroep' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__ABONNEMENT_GROEP(10504, "His Abonnement \\ Groep - Abonnement \\ Groep", "His_AbonnementGroep.AbonnementGroep",
            HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__DATUM_TIJD_REGISTRATIE(10505, "His Abonnement \\ Groep - Datum/tijd registratie", "His_AbonnementGroep.TsReg",
            HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__DATUM_TIJD_VERVAL(10506, "His Abonnement \\ Groep - Datum/tijd verval", "His_AbonnementGroep.TsVerval", HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__NADERE_AANDUIDING_VERVAL(11108, "His Abonnement \\ Groep - Nadere aanduiding verval", "His_AbonnementGroep.NadereAandVerval",
            HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__B_R_P_ACTIE_INHOUD(10507, "His Abonnement \\ Groep - BRP Actie inhoud", "His_AbonnementGroep.ActieInh", HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__B_R_P_ACTIE_VERVAL(10508, "His Abonnement \\ Groep - BRP Actie verval", "His_AbonnementGroep.ActieVerval",
            HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndFormeleHistorie' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__INDICATIE_FORMELE_HISTORIE(10512, "His Abonnement \\ Groep - Formele historie?", "His_AbonnementGroep.IndFormeleHistorie",
            HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndMaterieleHistorie' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__INDICATIE_MATERIELE_HISTORIE(10511, "His Abonnement \\ Groep - Materi\uFFFDle historie?",
            "His_AbonnementGroep.IndMaterieleHistorie", HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndNadereVerantwoording' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__INDICATIE_NADERE_VERANTWOORDING(10509, "His Abonnement \\ Groep - Nadere verantwoording?",
            "His_AbonnementGroep.IndNadereVerantwoording", HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndDocInformatie' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__INDICATIE_DOCUMENT_INFORMATIE(14198, "His Abonnement \\ Groep - Document informatie?", "His_AbonnementGroep.IndDocInformatie",
            HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de kolom: 'IndDoc' van de tabel: 'His_AbonnementGroep'.
     */
    HIS__ABONNEMENT_GROEP__INDICATIE_DOCUMENT(10510, "His Abonnement \\ Groep - Document?", "His_AbonnementGroep.IndDoc", HIS__ABONNEMENT_GROEP),
    /**
     * Representeert de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK(14192, "His Abonnement \\ LO3 Rubriek", "His_AbonnementLO3Rubriek", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__I_D(14208, "His Abonnement \\ LO3 Rubriek - ID", "His_AbonnementLO3Rubriek.ID", HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'AbonnementLO3Rubriek' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__ABONNEMENT_L_O3_RUBRIEK(14199, "His Abonnement \\ LO3 Rubriek - Abonnement \\ LO3 Rubriek",
            "His_AbonnementLO3Rubriek.AbonnementLO3Rubriek", HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__DATUM_TIJD_REGISTRATIE(14200, "His Abonnement \\ LO3 Rubriek - Datum/tijd registratie",
            "His_AbonnementLO3Rubriek.TsReg", HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__DATUM_TIJD_VERVAL(14201, "His Abonnement \\ LO3 Rubriek - Datum/tijd verval", "His_AbonnementLO3Rubriek.TsVerval",
            HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__NADERE_AANDUIDING_VERVAL(14202, "His Abonnement \\ LO3 Rubriek - Nadere aanduiding verval",
            "His_AbonnementLO3Rubriek.NadereAandVerval", HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__B_R_P_ACTIE_INHOUD(14203, "His Abonnement \\ LO3 Rubriek - BRP Actie inhoud", "His_AbonnementLO3Rubriek.ActieInh",
            HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__B_R_P_ACTIE_VERVAL(14204, "His Abonnement \\ LO3 Rubriek - BRP Actie verval", "His_AbonnementLO3Rubriek.ActieVerval",
            HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de kolom: 'Aktief' van de tabel: 'His_AbonnementLO3Rubriek'.
     */
    HIS__ABONNEMENT_L_O3_RUBRIEK__AKTIEF(14205, "His Abonnement \\ LO3 Rubriek - Aktief", "His_AbonnementLO3Rubriek.Aktief", HIS__ABONNEMENT_L_O3_RUBRIEK),
    /**
     * Representeert de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE(10614, "His Afleverwijze", "His_Afleverwijze", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__I_D(10628, "His Afleverwijze - ID", "His_Afleverwijze.ID", HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'Afleverwijze' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__AFLEVERWIJZE(10618, "His Afleverwijze - Afleverwijze", "His_Afleverwijze.Afleverwijze", HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__DATUM_TIJD_REGISTRATIE(10619, "His Afleverwijze - Datum/tijd registratie", "His_Afleverwijze.TsReg", HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__DATUM_TIJD_VERVAL(10620, "His Afleverwijze - Datum/tijd verval", "His_Afleverwijze.TsVerval", HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__NADERE_AANDUIDING_VERVAL(11109, "His Afleverwijze - Nadere aanduiding verval", "His_Afleverwijze.NadereAandVerval",
            HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__B_R_P_ACTIE_INHOUD(10621, "His Afleverwijze - BRP Actie inhoud", "His_Afleverwijze.ActieInh", HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__B_R_P_ACTIE_VERVAL(10622, "His Afleverwijze - BRP Actie verval", "His_Afleverwijze.ActieVerval", HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__DATUM_INGANG(10623, "His Afleverwijze - Datum ingang", "His_Afleverwijze.DatIngang", HIS__AFLEVERWIJZE),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_Afleverwijze'.
     */
    HIS__AFLEVERWIJZE__DATUM_EINDE(10624, "His Afleverwijze - Datum einde", "His_Afleverwijze.DatEinde", HIS__AFLEVERWIJZE),
    /**
     * Representeert de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL(4664, "His Authenticatiemiddel", "His_Authenticatiemiddel", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__I_D(4690, "His Authenticatiemiddel - ID", "His_Authenticatiemiddel.ID", HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'Authenticatiemiddel' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__AUTHENTICATIEMIDDEL(4678, "His Authenticatiemiddel - Authenticatiemiddel", "His_Authenticatiemiddel.Authenticatiemiddel",
            HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__DATUM_TIJD_REGISTRATIE(4679, "His Authenticatiemiddel - Datum/tijd registratie", "His_Authenticatiemiddel.TsReg",
            HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__DATUM_TIJD_VERVAL(4680, "His Authenticatiemiddel - Datum/tijd verval", "His_Authenticatiemiddel.TsVerval",
            HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__NADERE_AANDUIDING_VERVAL(11110, "His Authenticatiemiddel - Nadere aanduiding verval",
            "His_Authenticatiemiddel.NadereAandVerval", HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__B_R_P_ACTIE_INHOUD(4681, "His Authenticatiemiddel - BRP Actie inhoud", "His_Authenticatiemiddel.ActieInh",
            HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__B_R_P_ACTIE_VERVAL(4682, "His Authenticatiemiddel - BRP Actie verval", "His_Authenticatiemiddel.ActieVerval",
            HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de kolom: 'Certificaat' van de tabel: 'His_Authenticatiemiddel'.
     */
    HIS__AUTHENTICATIEMIDDEL__CERTIFICAAT(9684, "His Authenticatiemiddel - Certificaat", "His_Authenticatiemiddel.Certificaat", HIS__AUTHENTICATIEMIDDEL),
    /**
     * Representeert de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT(4870, "His Autorisatiebesluit", "His_Autorisatiebesluit", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__I_D(4999, "His Autorisatiebesluit - ID", "His_Autorisatiebesluit.ID", HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Autorisatiebesluit' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__AUTORISATIEBESLUIT(4959, "His Autorisatiebesluit - Autorisatiebesluit", "His_Autorisatiebesluit.Autorisatiebesluit",
            HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__DATUM_TIJD_REGISTRATIE(4960, "His Autorisatiebesluit - Datum/tijd registratie", "His_Autorisatiebesluit.TsReg",
            HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__DATUM_TIJD_VERVAL(4961, "His Autorisatiebesluit - Datum/tijd verval", "His_Autorisatiebesluit.TsVerval",
            HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__NADERE_AANDUIDING_VERVAL(11111, "His Autorisatiebesluit - Nadere aanduiding verval",
            "His_Autorisatiebesluit.NadereAandVerval", HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__B_R_P_ACTIE_INHOUD(4962, "His Autorisatiebesluit - BRP Actie inhoud", "His_Autorisatiebesluit.ActieInh",
            HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__B_R_P_ACTIE_VERVAL(4963, "His Autorisatiebesluit - BRP Actie verval", "His_Autorisatiebesluit.ActieVerval",
            HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatBesluit' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__DATUM_BESLUIT(9687, "His Autorisatiebesluit - Datum besluit", "His_Autorisatiebesluit.DatBesluit", HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__DATUM_INGANG(9688, "His Autorisatiebesluit - Datum ingang", "His_Autorisatiebesluit.DatIngang", HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__DATUM_EINDE(9689, "His Autorisatiebesluit - Datum einde", "His_Autorisatiebesluit.DatEinde", HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'His_Autorisatiebesluit'.
     */
    HIS__AUTORISATIEBESLUIT__TOESTAND(9690, "His Autorisatiebesluit - Toestand", "His_Autorisatiebesluit.Toestand", HIS__AUTORISATIEBESLUIT),
    /**
     * Representeert de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ(10224, "His Autorisatiebesluit \\ Partij", "His_AutorisatiebesluitPartij", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__I_D(10305, "His Autorisatiebesluit \\ Partij - ID", "His_AutorisatiebesluitPartij.ID", HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'AutorisatiebesluitPartij' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__AUTORISATIEBESLUIT_PARTIJ(10260, "His Autorisatiebesluit \\ Partij - Autorisatiebesluit \\ Partij",
            "His_AutorisatiebesluitPartij.AutorisatiebesluitPartij", HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__DATUM_TIJD_REGISTRATIE(10261, "His Autorisatiebesluit \\ Partij - Datum/tijd registratie",
            "His_AutorisatiebesluitPartij.TsReg", HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__DATUM_TIJD_VERVAL(10262, "His Autorisatiebesluit \\ Partij - Datum/tijd verval",
            "His_AutorisatiebesluitPartij.TsVerval", HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__NADERE_AANDUIDING_VERVAL(11112, "His Autorisatiebesluit \\ Partij - Nadere aanduiding verval",
            "His_AutorisatiebesluitPartij.NadereAandVerval", HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__B_R_P_ACTIE_INHOUD(10263, "His Autorisatiebesluit \\ Partij - BRP Actie inhoud",
            "His_AutorisatiebesluitPartij.ActieInh", HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__B_R_P_ACTIE_VERVAL(10264, "His Autorisatiebesluit \\ Partij - BRP Actie verval",
            "His_AutorisatiebesluitPartij.ActieVerval", HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__DATUM_INGANG(10265, "His Autorisatiebesluit \\ Partij - Datum ingang", "His_AutorisatiebesluitPartij.DatIngang",
            HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_AutorisatiebesluitPartij'.
     */
    HIS__AUTORISATIEBESLUIT_PARTIJ__DATUM_EINDE(10266, "His Autorisatiebesluit \\ Partij - Datum einde", "His_AutorisatiebesluitPartij.DatEinde",
            HIS__AUTORISATIEBESLUIT_PARTIJ),
    /**
     * Representeert de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE(5688, "His Bijhoudingsautorisatie", "His_Bijhautorisatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__I_D(5756, "His Bijhoudingsautorisatie - ID", "His_Bijhautorisatie.ID", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'Bijhautorisatie' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__BIJHOUDINGSAUTORISATIE(5730, "His Bijhoudingsautorisatie - Bijhoudingsautorisatie",
            "His_Bijhautorisatie.Bijhautorisatie", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__DATUM_TIJD_REGISTRATIE(5731, "His Bijhoudingsautorisatie - Datum/tijd registratie", "His_Bijhautorisatie.TsReg",
            HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__DATUM_TIJD_VERVAL(5732, "His Bijhoudingsautorisatie - Datum/tijd verval", "His_Bijhautorisatie.TsVerval",
            HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__NADERE_AANDUIDING_VERVAL(11115, "His Bijhoudingsautorisatie - Nadere aanduiding verval",
            "His_Bijhautorisatie.NadereAandVerval", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__B_R_P_ACTIE_INHOUD(5733, "His Bijhoudingsautorisatie - BRP Actie inhoud", "His_Bijhautorisatie.ActieInh",
            HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__B_R_P_ACTIE_VERVAL(5734, "His Bijhoudingsautorisatie - BRP Actie verval", "His_Bijhautorisatie.ActieVerval",
            HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'SrtBevoegdheid' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__SOORT_BEVOEGDHEID(9693, "His Bijhoudingsautorisatie - Soort bevoegdheid", "His_Bijhautorisatie.SrtBevoegdheid",
            HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'GeautoriseerdeSrtPartij' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__GEAUTORISEERDE_SOORT_PARTIJ(9694, "His Bijhoudingsautorisatie - Geautoriseerde soort partij",
            "His_Bijhautorisatie.GeautoriseerdeSrtPartij", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'GeautoriseerdePartij' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__GEAUTORISEERDE_PARTIJ(9695, "His Bijhoudingsautorisatie - Geautoriseerde partij",
            "His_Bijhautorisatie.GeautoriseerdePartij", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__TOESTAND(9696, "His Bijhoudingsautorisatie - Toestand", "His_Bijhautorisatie.Toestand", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'CategoriePersonen' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__CATEGORIE_PERSONEN(9697, "His Bijhoudingsautorisatie - Categorie personen", "His_Bijhautorisatie.CategoriePersonen",
            HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'Oms' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__OMSCHRIJVING(9698, "His Bijhoudingsautorisatie - Omschrijving", "His_Bijhautorisatie.Oms", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__DATUM_INGANG(9699, "His Bijhoudingsautorisatie - Datum ingang", "His_Bijhautorisatie.DatIngang",
            HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_Bijhautorisatie'.
     */
    HIS__BIJHOUDINGSAUTORISATIE__DATUM_EINDE(9700, "His Bijhoudingsautorisatie - Datum einde", "His_Bijhautorisatie.DatEinde", HIS__BIJHOUDINGSAUTORISATIE),
    /**
     * Representeert de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE(5693, "His Bijhoudingssituatie", "His_Bijhsituatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE__I_D(5759, "His Bijhoudingssituatie - ID", "His_Bijhsituatie.ID", HIS__BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'Bijhsituatie' van de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE__BIJHOUDINGSSITUATIE(5740, "His Bijhoudingssituatie - Bijhoudingssituatie", "His_Bijhsituatie.Bijhsituatie",
            HIS__BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE__DATUM_TIJD_REGISTRATIE(5741, "His Bijhoudingssituatie - Datum/tijd registratie", "His_Bijhsituatie.TsReg",
            HIS__BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE__DATUM_TIJD_VERVAL(5742, "His Bijhoudingssituatie - Datum/tijd verval", "His_Bijhsituatie.TsVerval", HIS__BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE__NADERE_AANDUIDING_VERVAL(11116, "His Bijhoudingssituatie - Nadere aanduiding verval", "His_Bijhsituatie.NadereAandVerval",
            HIS__BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE__B_R_P_ACTIE_INHOUD(5743, "His Bijhoudingssituatie - BRP Actie inhoud", "His_Bijhsituatie.ActieInh", HIS__BIJHOUDINGSSITUATIE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Bijhsituatie'.
     */
    HIS__BIJHOUDINGSSITUATIE__B_R_P_ACTIE_VERVAL(5744, "His Bijhoudingssituatie - BRP Actie verval", "His_Bijhsituatie.ActieVerval",
            HIS__BIJHOUDINGSSITUATIE),
    /**
     * Representeert de tabel: 'His_Dienst'.
     */
    HIS__DIENST(10211, "His Dienst", "His_Dienst", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__I_D(10308, "His Dienst - ID", "His_Dienst.ID", HIS__DIENST),
    /**
     * Representeert de kolom: 'Dienst' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__DIENST(10268, "His Dienst - Dienst", "His_Dienst.Dienst", HIS__DIENST),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__DATUM_TIJD_REGISTRATIE(10269, "His Dienst - Datum/tijd registratie", "His_Dienst.TsReg", HIS__DIENST),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__DATUM_TIJD_VERVAL(10270, "His Dienst - Datum/tijd verval", "His_Dienst.TsVerval", HIS__DIENST),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__NADERE_AANDUIDING_VERVAL(11117, "His Dienst - Nadere aanduiding verval", "His_Dienst.NadereAandVerval", HIS__DIENST),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__B_R_P_ACTIE_INHOUD(10271, "His Dienst - BRP Actie inhoud", "His_Dienst.ActieInh", HIS__DIENST),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__B_R_P_ACTIE_VERVAL(10272, "His Dienst - BRP Actie verval", "His_Dienst.ActieVerval", HIS__DIENST),
    /**
     * Representeert de kolom: 'NaderePopulatiebeperking' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__NADERE_POPULATIEBEPERKING(10273, "His Dienst - Nadere populatiebeperking", "His_Dienst.NaderePopulatiebeperking", HIS__DIENST),
    /**
     * Representeert de kolom: 'Attenderingscriterium' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__ATTENDERINGSCRITERIUM(11237, "His Dienst - Attenderingscriterium", "His_Dienst.Attenderingscriterium", HIS__DIENST),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__DATUM_INGANG(10274, "His Dienst - Datum ingang", "His_Dienst.DatIngang", HIS__DIENST),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__DATUM_EINDE(10275, "His Dienst - Datum einde", "His_Dienst.DatEinde", HIS__DIENST),
    /**
     * Representeert de kolom: 'Toestand' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__TOESTAND(10276, "His Dienst - Toestand", "His_Dienst.Toestand", HIS__DIENST),
    /**
     * Representeert de kolom: 'EersteSelectiedat' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__EERSTE_SELECTIEDATUM(10535, "His Dienst - Eerste selectiedatum", "His_Dienst.EersteSelectiedat", HIS__DIENST),
    /**
     * Representeert de kolom: 'SelectieperiodeInMaanden' van de tabel: 'His_Dienst'.
     */
    HIS__DIENST__SELECTIEPERIODE_IN_MAANDEN(10536, "His Dienst - Selectieperiode in maanden", "His_Dienst.SelectieperiodeInMaanden", HIS__DIENST),
    /**
     * Representeert de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING(4882, "His Doelbinding", "His_Doelbinding", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__I_D(5002, "His Doelbinding - ID", "His_Doelbinding.ID", HIS__DOELBINDING),
    /**
     * Representeert de kolom: 'Doelbinding' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__DOELBINDING(4966, "His Doelbinding - Doelbinding", "His_Doelbinding.Doelbinding", HIS__DOELBINDING),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__DATUM_TIJD_REGISTRATIE(4969, "His Doelbinding - Datum/tijd registratie", "His_Doelbinding.TsReg", HIS__DOELBINDING),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__DATUM_TIJD_VERVAL(4970, "His Doelbinding - Datum/tijd verval", "His_Doelbinding.TsVerval", HIS__DOELBINDING),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__NADERE_AANDUIDING_VERVAL(11119, "His Doelbinding - Nadere aanduiding verval", "His_Doelbinding.NadereAandVerval", HIS__DOELBINDING),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__B_R_P_ACTIE_INHOUD(4971, "His Doelbinding - BRP Actie inhoud", "His_Doelbinding.ActieInh", HIS__DOELBINDING),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__B_R_P_ACTIE_VERVAL(4972, "His Doelbinding - BRP Actie verval", "His_Doelbinding.ActieVerval", HIS__DOELBINDING),
    /**
     * Representeert de kolom: 'TekstDoelbinding' van de tabel: 'His_Doelbinding'.
     */
    HIS__DOELBINDING__TEKST_DOELBINDING(9706, "His Doelbinding - Tekst doelbinding", "His_Doelbinding.TekstDoelbinding", HIS__DOELBINDING),
    /**
     * Representeert de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES(10239, "His Partij \\ IP adres", "His_PartijIPAdres", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES__I_D(10311, "His Partij \\ IP adres - ID", "His_PartijIPAdres.ID", HIS__PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'PartijIPAdres' van de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES__PARTIJ_I_P_ADRES(10278, "His Partij \\ IP adres - Partij \\ IP adres", "His_PartijIPAdres.PartijIPAdres", HIS__PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES__DATUM_TIJD_REGISTRATIE(10279, "His Partij \\ IP adres - Datum/tijd registratie", "His_PartijIPAdres.TsReg",
            HIS__PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES__DATUM_TIJD_VERVAL(10280, "His Partij \\ IP adres - Datum/tijd verval", "His_PartijIPAdres.TsVerval", HIS__PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES__NADERE_AANDUIDING_VERVAL(11124, "His Partij \\ IP adres - Nadere aanduiding verval", "His_PartijIPAdres.NadereAandVerval",
            HIS__PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES__B_R_P_ACTIE_INHOUD(10281, "His Partij \\ IP adres - BRP Actie inhoud", "His_PartijIPAdres.ActieInh", HIS__PARTIJ_I_P_ADRES),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_PartijIPAdres'.
     */
    HIS__PARTIJ_I_P_ADRES__B_R_P_ACTIE_VERVAL(10282, "His Partij \\ IP adres - BRP Actie verval", "His_PartijIPAdres.ActieVerval", HIS__PARTIJ_I_P_ADRES),
    /**
     * Representeert de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE(10319, "His Persoon \\ Afnemerindicatie", "His_PersAfnemerindicatie", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__I_D(10341, "His Persoon \\ Afnemerindicatie - ID", "His_PersAfnemerindicatie.ID", HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'PersAfnemerindicatie' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__PERSOON_AFNEMERINDICATIE(10348, "His Persoon \\ Afnemerindicatie - Persoon \\ Afnemerindicatie",
            "His_PersAfnemerindicatie.PersAfnemerindicatie", HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__DATUM_TIJD_REGISTRATIE(10331, "His Persoon \\ Afnemerindicatie - Datum/tijd registratie",
            "His_PersAfnemerindicatie.TsReg", HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__DATUM_TIJD_VERVAL(10332, "His Persoon \\ Afnemerindicatie - Datum/tijd verval", "His_PersAfnemerindicatie.TsVerval",
            HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__NADERE_AANDUIDING_VERVAL(11142, "His Persoon \\ Afnemerindicatie - Nadere aanduiding verval",
            "His_PersAfnemerindicatie.NadereAandVerval", HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'DienstInh' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__DIENST_INHOUD(11418, "His Persoon \\ Afnemerindicatie - Dienst inhoud", "His_PersAfnemerindicatie.DienstInh",
            HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'DienstVerval' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__DIENST_VERVAL(11419, "His Persoon \\ Afnemerindicatie - Dienst verval", "His_PersAfnemerindicatie.DienstVerval",
            HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'DatAanvMaterielePeriode' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__DATUM_AANVANG_MATERIELE_PERIODE(10335, "His Persoon \\ Afnemerindicatie - Datum aanvang materi\uFFFDle periode",
            "His_PersAfnemerindicatie.DatAanvMaterielePeriode", HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de kolom: 'TsEindeVolgen' van de tabel: 'His_PersAfnemerindicatie'.
     */
    HIS__PERSOON_AFNEMERINDICATIE__DATUM_TIJD_EINDE_VOLGEN(10336, "His Persoon \\ Afnemerindicatie - Datum/tijd einde volgen",
            "His_PersAfnemerindicatie.TsEindeVolgen", HIS__PERSOON_AFNEMERINDICATIE),
    /**
     * Representeert de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT(10234, "His Toegang abonnement", "His_ToegangAbonnement", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__I_D(10314, "His Toegang abonnement - ID", "His_ToegangAbonnement.ID", HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'ToegangAbonnement' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__TOEGANG_ABONNEMENT(10284, "His Toegang leveringsautorisatie - Toegang leveringsautorisatie", "His_ToegangAbonnement.ToegangAbonnement",
            HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__DATUM_TIJD_REGISTRATIE(10285, "His Toegang abonnement - Datum/tijd registratie", "His_ToegangAbonnement.TsReg",
            HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__DATUM_TIJD_VERVAL(10286, "His Toegang abonnement - Datum/tijd verval", "His_ToegangAbonnement.TsVerval",
            HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__NADERE_AANDUIDING_VERVAL(11171, "His Toegang abonnement - Nadere aanduiding verval",
            "His_ToegangAbonnement.NadereAandVerval", HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__B_R_P_ACTIE_INHOUD(10287, "His Toegang abonnement - BRP Actie inhoud", "His_ToegangAbonnement.ActieInh",
            HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__B_R_P_ACTIE_VERVAL(10288, "His Toegang abonnement - BRP Actie verval", "His_ToegangAbonnement.ActieVerval",
            HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'DatIngang' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__DATUM_INGANG(10289, "His Toegang abonnement - Datum ingang", "His_ToegangAbonnement.DatIngang", HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de kolom: 'DatEinde' van de tabel: 'His_ToegangAbonnement'.
     */
    HIS__TOEGANG_ABONNEMENT__DATUM_EINDE(10290, "His Toegang abonnement - Datum einde", "His_ToegangAbonnement.DatEinde", HIS__TOEGANG_ABONNEMENT),
    /**
     * Representeert de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE(5776, "His Uitgeslotene", "His_Uitgeslotene", null),
    /**
     * Representeert de kolom: 'ID' van de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE__I_D(5794, "His Uitgeslotene - ID", "His_Uitgeslotene.ID", HIS__UITGESLOTENE),
    /**
     * Representeert de kolom: 'Uitgeslotene' van de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE__UITGESLOTENE(5797, "His Uitgeslotene - Uitgeslotene", "His_Uitgeslotene.Uitgeslotene", HIS__UITGESLOTENE),
    /**
     * Representeert de kolom: 'TsReg' van de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE__DATUM_TIJD_REGISTRATIE(5785, "His Uitgeslotene - Datum/tijd registratie", "His_Uitgeslotene.TsReg", HIS__UITGESLOTENE),
    /**
     * Representeert de kolom: 'TsVerval' van de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE__DATUM_TIJD_VERVAL(5786, "His Uitgeslotene - Datum/tijd verval", "His_Uitgeslotene.TsVerval", HIS__UITGESLOTENE),
    /**
     * Representeert de kolom: 'NadereAandVerval' van de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE__NADERE_AANDUIDING_VERVAL(11172, "His Uitgeslotene - Nadere aanduiding verval", "His_Uitgeslotene.NadereAandVerval",
            HIS__UITGESLOTENE),
    /**
     * Representeert de kolom: 'ActieInh' van de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE__B_R_P_ACTIE_INHOUD(5787, "His Uitgeslotene - BRP Actie inhoud", "His_Uitgeslotene.ActieInh", HIS__UITGESLOTENE),
    /**
     * Representeert de kolom: 'ActieVerval' van de tabel: 'His_Uitgeslotene'.
     */
    HIS__UITGESLOTENE__B_R_P_ACTIE_VERVAL(5788, "His Uitgeslotene - BRP Actie verval", "His_Uitgeslotene.ActieVerval", HIS__UITGESLOTENE);

    private Integer id;
    private String naam;
    private String databaseNaam;
    private DatabaseObjectAutAut ouder;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param id Id voor DatabaseObjectAutAut
     * @param naam Naam voor DatabaseObjectAutAut
     * @param databaseNaam DatabaseNaam voor DatabaseObjectAutAut
     * @param ouder Ouder voor DatabaseObjectAutAut
     */
    private DatabaseObjectAutAut(final Integer id, final String naam, final String databaseNaam, final DatabaseObjectAutAut ouder) {
        this.id = id;
        this.naam = naam;
        this.databaseNaam = databaseNaam;
        this.ouder = ouder;
    }

    /**
     * Retourneert id van DatabaseObjectAutAut.
     *
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retourneert naam van DatabaseObjectAutAut.
     *
     * @return naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert databaseNaam van DatabaseObjectAutAut.
     *
     * @return databaseNaam.
     */
    public String getDatabaseNaam() {
        return databaseNaam;
    }

    /**
     * Retourneert ouder van DatabaseObjectAutAut.
     *
     * @return ouder.
     */
    public DatabaseObjectAutAut getOuder() {
        return ouder;
    }

    /**
     * Geeft het database object met een bepaald id terug.
     *
     * @param id het id van het gezochte database object
     * @return het java type of null indien niet gevonden
     */
    public static DatabaseObjectAutAut findById(final Integer id) {
        DatabaseObjectAutAut databaseObject = null;
        for (DatabaseObjectAutAut enumWaarde : DatabaseObjectAutAut.values()) {
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
