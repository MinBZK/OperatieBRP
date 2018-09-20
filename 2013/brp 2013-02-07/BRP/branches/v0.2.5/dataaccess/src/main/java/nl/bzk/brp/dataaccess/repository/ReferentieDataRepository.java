/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortDocument;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de Referentie data class.
 */
@Repository
public interface ReferentieDataRepository {

    /**
     * Zoek een woonplaats met behulp van de woonplaatscode.
     *
     * @param code woonplaatscode
     * @return de woonplaats met de gegeven code
     */
    Plaats vindWoonplaatsOpCode(final PlaatsCode code);

    /**
     * Zoek een gemeente met behulp van de gemeentecode.
     *
     * @param code gemeentecode
     * @return de gemeente met de gegeven code
     */
    Partij vindGemeenteOpCode(final Gemeentecode code);

    /**
     * Zoek een land met behulp van de landcode.
     *
     * @param code landcode
     * @return het land met de gegeven code
     */
    Land vindLandOpCode(final Landcode code);

    /**
     * Zoek een nationaliteit op basis van een nationaliteit code.
     * @param code De code waarop gezocht wordt.
     * @return De nationaliteit behorende bij code.
     */
    Nationaliteit vindNationaliteitOpCode(final Nationaliteitcode code);

    /**
     * Zoek een 'reden wijziging adres'  op basis van de code.
     * @param code De code waarop gezocht wordt.
     * @return De 'reden wijziging adres' behorende bij code.
     */
    RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode code);

    /**
     * Zoek een 'reden beeindiging relatie' op basis van code
     * @param code De code waarop gezocht wordt.
     * @return De 'reden beeinding relatie' behorende bij code.
     */
    RedenBeeindigingRelatie vindRedenBeeindigingRelatieOpCode(final RedenBeeindigingRelatieCode code);
    /**
     * Zoek een 'aangever adreshouding'  op basis van de code.
     * @param code De code waarop gezocht wordt.
     * @return De 'aangever adreshouding' behorende bij code.
     */
    AangeverAdreshouding vindAangeverAdreshoudingOpCode(final AangeverAdreshoudingCode code);


    /**
     * Zoek een Adellijke titel op basis van de code.
     * @param code de code
     * @return de adellijketitel
     */
    AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCode code);

    /**
     * Zoek een predikaat op basis van de code.
     * @param code de code
     * @return de predikaat
     */
    Predikaat vindPredikaatOpCode(final PredikaatCode code);

    /**
     * Zoek RedenVerkrijgingNLNationaliteit op code.
     * @param redenVerkrijgingCode de code.
     * @return RedenVerkrijgingNLNationaliteit
     */
    RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
            final RedenVerkrijgingCode redenVerkrijgingCode);

    /**
     * Zoek RedenVerliesNLNationaliteit op naam.
     * @param naam de naam.
     * @return RedenVerliesNLNationaliteit
     */
    RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpNaam(final RedenVerliesNaam naam);

    /**
     * Zoek SoortDocument op naam.
     * @param naam de naam.
     * @return SoortDocument
     */
    SoortDocument vindSoortDocumentOpNaam(final Naam naam);
}
