/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingssituatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrecht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AutoriteittypeVanAfgifteReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategorieAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Voorvoegsel;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Een Enum benodigd voor de functionaliteit van synchronisatie stamgegevens. Deze enum bevat een mappingtussen een
 * tabelnaam zoals de in het request wordt aangeduid in de parameters van het berichten een stamgegeven klasse.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SynchronisatieStamgegevensEnumGenerator")
public enum SynchronisatieStamgegeven {

    /**
     * De PlaatsTabel.
     */
    PLAATS("PlaatsTabel", Plaats.class),
    /**
     * De LandGebiedTabel.
     */
    LANDGEBIED("LandGebiedTabel", LandGebied.class),
    /**
     * De NationaliteitTabel.
     */
    NATIONALITEIT("NationaliteitTabel", Nationaliteit.class),
    /**
     * De PredicaatTabel.
     */
    PREDICAAT("PredicaatTabel", Predicaat.class),
    /**
     * De AdellijkeTitelTabel.
     */
    ADELLIJKETITEL("AdellijkeTitelTabel", AdellijkeTitel.class),
    /**
     * De PartijTabel.
     */
    PARTIJ("PartijTabel", Partij.class),
    /**
     * De SoortDocumentTabel.
     */
    SOORTDOCUMENT("SoortDocumentTabel", SoortDocument.class),
    /**
     * De RedenEindeRelatieTabel.
     */
    REDENEINDERELATIE("RedenEindeRelatieTabel", RedenEindeRelatie.class),
    /**
     * De RedenVerkrijgingNLNationaliteitTabel.
     */
    REDENVERKRIJGINGNLNATIONALITEIT("RedenVerkrijgingNLNationaliteitTabel", RedenVerkrijgingNLNationaliteit.class),
    /**
     * De RedenVerliesNLNationaliteitTabel.
     */
    REDENVERLIESNLNATIONALITEIT("RedenVerliesNLNationaliteitTabel", RedenVerliesNLNationaliteit.class),
    /**
     * De AangeverTabel.
     */
    AANGEVER("AangeverTabel", Aangever.class),
    /**
     * De AanduidingVerblijfsrechtTabel.
     */
    AANDUIDINGVERBLIJFSRECHT("AanduidingVerblijfsrechtTabel", AanduidingVerblijfsrecht.class),
    /**
     * De RedenWijzigingVerblijfTabel.
     */
    REDENWIJZIGINGVERBLIJF("RedenWijzigingVerblijfTabel", RedenWijzigingVerblijf.class),
    /**
     * De SoortNederlandsReisdocumentTabel.
     */
    SOORTNEDERLANDSREISDOCUMENT("SoortNederlandsReisdocumentTabel", SoortNederlandsReisdocument.class),
    /**
     * De AutoriteittypeVanAfgifteReisdocumentTabel.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT("AutoriteittypeVanAfgifteReisdocumentTabel", AutoriteittypeVanAfgifteReisdocument.class),
    /**
     * De AanduidingInhoudingVermissingReisdocumentTabel.
     */
    AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT("AanduidingInhoudingVermissingReisdocumentTabel", AanduidingInhoudingVermissingReisdocument.class),
    /**
     * De RechtsgrondTabel.
     */
    RECHTSGROND("RechtsgrondTabel", Rechtsgrond.class),
    /**
     * De VoorvoegselTabel.
     */
    VOORVOEGSEL("VoorvoegselTabel", Voorvoegsel.class),
    /**
     * De GemeenteTabel.
     */
    GEMEENTE("GemeenteTabel", Gemeente.class),
    /**
     * De SoortPersoonTabel.
     */
    SOORTPERSOON("SoortPersoonTabel", SoortPersoon.class),
    /**
     * De ElementTabel.
     */
    ELEMENT("ElementTabel", Element.class),
    /**
     * De FunctieAdresTabel.
     */
    FUNCTIEADRES("FunctieAdresTabel", FunctieAdres.class),
    /**
     * De BijhoudingsaardTabel.
     */
    BIJHOUDINGSAARD("BijhoudingsaardTabel", Bijhoudingsaard.class),
    /**
     * De NaamgebruikTabel.
     */
    NAAMGEBRUIK("NaamgebruikTabel", Naamgebruik.class),
    /**
     * De GeslachtsaanduidingTabel.
     */
    GESLACHTSAANDUIDING("GeslachtsaanduidingTabel", Geslachtsaanduiding.class),
    /**
     * De RolTabel.
     */
    ROL("RolTabel", Rol.class),
    /**
     * De SoortMeldingTabel.
     */
    SOORTMELDING("SoortMeldingTabel", SoortMelding.class),
    /**
     * De VerwerkingsresultaatTabel.
     */
    VERWERKINGSRESULTAAT("VerwerkingsresultaatTabel", Verwerkingsresultaat.class),
    /**
     * De BijhoudingsresultaatTabel.
     */
    BIJHOUDINGSRESULTAAT("BijhoudingsresultaatTabel", Bijhoudingsresultaat.class),
    /**
     * De SoortRechtsgrondTabel.
     */
    SOORTRECHTSGROND("SoortRechtsgrondTabel", SoortRechtsgrond.class),
    /**
     * De SoortAdministratieveHandelingTabel.
     */
    SOORTADMINISTRATIEVEHANDELING("SoortAdministratieveHandelingTabel", SoortAdministratieveHandeling.class),
    /**
     * De CategorieAdministratieveHandelingTabel.
     */
    CATEGORIEADMINISTRATIEVEHANDELING("CategorieAdministratieveHandelingTabel", CategorieAdministratieveHandeling.class),
    /**
     * De StatusTerugmeldingTabel.
     */
    STATUSTERUGMELDING("StatusTerugmeldingTabel", StatusTerugmelding.class),
    /**
     * De SoortPersoonOnderzoekTabel.
     */
    SOORTPERSOONONDERZOEK("SoortPersoonOnderzoekTabel", SoortPersoonOnderzoek.class),
    /**
     * De SoortPartijOnderzoekTabel.
     */
    SOORTPARTIJONDERZOEK("SoortPartijOnderzoekTabel", SoortPartijOnderzoek.class),
    /**
     * De StatusOnderzoekTabel.
     */
    STATUSONDERZOEK("StatusOnderzoekTabel", StatusOnderzoek.class),
    /**
     * De NadereBijhoudingsaardTabel.
     */
    NADEREBIJHOUDINGSAARD("NadereBijhoudingsaardTabel", NadereBijhoudingsaard.class),
    /**
     * De SoortMigratieTabel.
     */
    SOORTMIGRATIE("SoortMigratieTabel", SoortMigratie.class),
    /**
     * De SoortDienstTabel.
     */
    SOORTDIENST("SoortDienstTabel", SoortDienst.class),
    /**
     * De EffectAfnemerindicatiesTabel.
     */
    EFFECTAFNEMERINDICATIES("EffectAfnemerindicatiesTabel", EffectAfnemerindicaties.class),
    /**
     * De VerwerkingswijzeTabel.
     */
    VERWERKINGSWIJZE("VerwerkingswijzeTabel", Verwerkingswijze.class),
    /**
     * De SoortSynchronisatieTabel.
     */
    SOORTSYNCHRONISATIE("SoortSynchronisatieTabel", SoortSynchronisatie.class),
    /**
     * De BijhoudingssituatieTabel.
     */
    BIJHOUDINGSSITUATIE("BijhoudingssituatieTabel", Bijhoudingssituatie.class);

    private String tabelNaam;
    private Class<? extends SynchroniseerbaarStamgegeven> stamgegevenKlasse;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param tabelNaam TabelNaam voor SynchronisatieStamgegeven
     * @param stamgegevenKlasse StamgegevenKlasse voor SynchronisatieStamgegeven
     */
    private SynchronisatieStamgegeven(final String tabelNaam, final Class<? extends SynchroniseerbaarStamgegeven> stamgegevenKlasse) {
        this.tabelNaam = tabelNaam;
        this.stamgegevenKlasse = stamgegevenKlasse;
    }

    /**
     * Retourneert tabelNaam van SynchronisatieStamgegeven.
     *
     * @return tabelNaam.
     */
    public String getTabelNaam() {
        return tabelNaam;
    }

    /**
     * Retourneert tabelNaam van SynchronisatieStamgegeven.
     *
     * @return tabelNaam.
     */
    public Class<? extends SynchroniseerbaarStamgegeven> getStamgegevenKlasse() {
        return stamgegevenKlasse;
    }

    /**
     * Vind bij een stamgegeven tabel naam zoals deze wordt aangeduid in het koppelvlak de bijbehorende enumeratie.
     *
     * @param stamgegevenTabelnaam
     * @return De enum behorende bij stamgegevenTabelnaam.
     */
    public static SynchronisatieStamgegeven vindEnumVoorTabelNaam(final String stamgegevenTabelnaam) {
        for (SynchronisatieStamgegeven synchronisatieStamgegeven : values()) {
            if (synchronisatieStamgegeven.getTabelNaam().equalsIgnoreCase(stamgegevenTabelnaam)) {
                return synchronisatieStamgegeven;
            }
        }
        return null;
    }

}
