/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.*;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de Referentie data class.
 */
@Repository
public interface ReferentieDataRepository {

    /**
     * Bepaalt of de opgegeven plaatsnaam een bestaande plaats is.
     *
     * @param woonplaatsnaam de woonplaatsnaam NaamEnumeratiewaardeAttribuut
     * @return Boolean true wanneer het een bestaande plaats is
     */
    Boolean isBestaandeWoonplaats(final NaamEnumeratiewaardeAttribuut woonplaatsnaam);

    /**
     * Zoek een woonplaats met behulp van de woonplaatsnaam.
     * Let op: deze methode gaat uit van 1 uniek resultaat. Sommige namen komen echter
     * meerdere keren voor en in dat geval zal deze methode een exceptie gooien.
     *
     * @param woonplaatsnaam de woonplaatsnaam NaamEnumeratiewaardeAttribuut
     * @return de woonplaats met de gegeven woonplaatsnaam
     */
    Plaats vindWoonplaatsOpNaam(final NaamEnumeratiewaardeAttribuut woonplaatsnaam);

    /**
     * Zoek een gemeente met behulp van de gemeentecode.
     *
     * @param code gemeentecode
     * @return de gemeente met de gegeven code
     */
    Gemeente vindGemeenteOpCode(final GemeenteCodeAttribuut code);

    /**
     * Nodig voor bericht verrijking bij binnengemeentelijke verhuizingen.
     *
     * @param persoonID persoon id
     * @return de huidige gemeente van de persoon
     */
    Gemeente findHuidigeGemeenteByPersoonID(final int persoonID);

    /**
     * Zoek een partij met behulp van de partijcode.
     *
     * @param code partijcode
     * @return de paertij met de gegeven code
     */
    Partij vindPartijOpCode(final PartijCodeAttribuut code);

    /**
     * Zoek een partij met behulp van de partijcode.
     *
     * @param oin oinCode
     * @return de partij met de gegeven oin
     */
    Partij vindPartijOpOIN(final OINAttribuut oin);

    /**
     * Zoek een land met behulp van de landcode.
     *
     * @param code landcode
     * @return het land met de gegeven code
     */
    LandGebied vindLandOpCode(final LandGebiedCodeAttribuut code);

    /**
     * Zoek een nationaliteit op basis van een nationaliteit code.
     *
     * @param code De code waarop gezocht wordt.
     * @return De nationaliteit behorende bij code.
     */
    Nationaliteit vindNationaliteitOpCode(final NationaliteitcodeAttribuut code);

    /**
     * Zoek een soort reisdocument op basis van de code.
     *
     * @param code De code waarop gezocht wordt.
     * @return Het soort reisdocument behorende bij code.
     */
    SoortNederlandsReisdocument vindSoortReisdocumentOpCode(final SoortNederlandsReisdocumentCodeAttribuut code);

    /**
     * Zoek een 'reden wijziging verblijf' op basis van de code.
     *
     * @param code De code waarop gezocht wordt.
     * @return De 'reden wijziging adres' behorende bij code.
     */
    RedenWijzigingVerblijf vindRedenWijzingVerblijfOpCode(final RedenWijzigingVerblijfCodeAttribuut code);

    /**
     * Zoek een 'reden einde relatie' op basis van code.
     *
     * @param code De code waarop gezocht wordt.
     * @return De 'reden einde relatie' behorende bij code.
     */
    RedenEindeRelatie vindRedenEindeRelatieOpCode(final RedenEindeRelatieCodeAttribuut code);

    /**
     * Zoek een 'aangever adreshouding'  op basis van de code.
     *
     * @param code De code waarop gezocht wordt.
     * @return De 'aangever adreshouding' behorende bij code.
     */
    Aangever vindAangeverAdreshoudingOpCode(final AangeverCodeAttribuut code);


    /**
     * Zoek een Adellijke titel op basis van de code.
     *
     * @param code de code
     * @return de adellijketitel
     */
    AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCodeAttribuut code);

    /**
     * Zoek een predicaat op basis van de code.
     *
     * @param code de code
     * @return de predicaat
     */
    Predicaat vindPredicaatOpCode(final PredicaatCodeAttribuut code);

    /**
     * Zoek RedenVerkrijgingNLNationaliteit op code.
     *
     * @param redenVerkrijgingCode de code.
     * @return RedenVerkrijgingNLNationaliteit
     */
    RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
            final RedenVerkrijgingCodeAttribuut redenVerkrijgingCode);

    /**
     * Zoek RedenVerliesNLNationaliteit op code.
     *
     * @param code de code.
     * @return RedenVerliesNLNationaliteit
     */
    RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpCode(final RedenVerliesCodeAttribuut code);

    /**
     * Zoek AanduidingInhoudingVermissingReisdocument op code.
     *
     * @param code de code.
     * @return AanduidingInhoudingVermissingReisdocument
     */
    AanduidingInhoudingVermissingReisdocument vindAanduidingInhoudingVermissingReisdocumentOpCode(
            final AanduidingInhoudingVermissingReisdocumentCodeAttribuut code);

    /**
     * Zoek SoortDocument op naam.
     *
     * @param naam de naam.
     * @return SoortDocument
     */
    SoortDocument vindSoortDocumentOpNaam(final NaamEnumeratiewaardeAttribuut naam);

    /**
     * Haal het land Nederland op.
     *
     * @return Nederland
     */
    LandGebied getNederland();

    /**
     * Haal de leveringsautorisatie op
     *
     * @param leveringsautorisatienaam De naam van de leveringsautorisatie.
     * @return De leveringsautorisatie.
     */
    @Deprecated
    Leveringsautorisatie vindLeveringsAutorisatieOpNaam(NaamEnumeratiewaardeAttribuut leveringsautorisatienaam);

    /**
     * Vind element op naam.
     *
     * @param elementNaam de naam van het element
     * @return Het element
     */
    Element vindElementOpNaam(NaamEnumeratiewaardeLangAttribuut elementNaam);

    /**
     * Vind een rechtsgrond op code.
     *
     * @param code de code
     * @return rechtsgrond
     */
    Rechtsgrond vindRechtsgrondOpCode(final RechtsgrondCodeAttribuut code);

    /**
     * Vind actie op soort.
     *
     * @param soortActie de soort van de actie
     * @return Het ActieModel
     */
    ActieModel vindActieModelOpSoortActie(SoortActie soortActie);

    /**
     * Checkt of de opgegeven combinatie van voorvoegsel en scheidingsteken bestaat.
     * Dit aan de hand van een stamtabel in de BRP die aangeeft welke combinaties zijn toegestaan.
     *
     * @param voorvoegsel     het voorvoegsel
     * @param scheidingsteken het scheidingsteken
     * @return of de combinatie bestaat (true) of niet (false)
     */
    boolean bestaatVoorvoegselScheidingsteken(final String voorvoegsel, final String scheidingsteken);

    /**
     * Laat Document uit DB.
     *
     * @return
     */
    SoortDocument laadAnySoortDocument();
}
