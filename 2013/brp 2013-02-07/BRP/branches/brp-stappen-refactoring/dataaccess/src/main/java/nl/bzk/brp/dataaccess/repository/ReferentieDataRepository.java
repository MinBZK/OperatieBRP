/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverAdreshoudingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;

import org.springframework.stereotype.Repository;

/** Repository voor de Referentie data class. */
@Repository
public interface ReferentieDataRepository {

    /**
     * Zoek een woonplaats met behulp van de woonplaatscode.
     *
     * @param code woonplaatscode
     * @return de woonplaats met de gegeven code
     */
    Plaats vindWoonplaatsOpCode(final Woonplaatscode code);

    /**
     * Zoek een gemeente met behulp van de gemeentecode.
     *
     * @param code gemeentecode
     * @return de gemeente met de gegeven code
     */
    Partij vindGemeenteOpCode(final GemeenteCode code);

    /**
     * Zoek een land met behulp van de landcode.
     *
     * @param code landcode
     * @return het land met de gegeven code
     */
    Land vindLandOpCode(final Landcode code);

    /**
     * Zoek een nationaliteit op basis van een nationaliteit code.
     *
     * @param code De code waarop gezocht wordt.
     * @return De nationaliteit behorende bij code.
     */
    Nationaliteit vindNationaliteitOpCode(final Nationaliteitcode code);

    /**
     * Zoek een 'reden wijziging adres'  op basis van de code.
     *
     * @param code De code waarop gezocht wordt.
     * @return De 'reden wijziging adres' behorende bij code.
     */
    RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode code);

    /**
     * Zoek een 'reden beeindiging relatie' op basis van code
     *
     * @param code De code waarop gezocht wordt.
     * @return De 'reden beeinding relatie' behorende bij code.
     */
    RedenBeeindigingRelatie vindRedenBeeindigingRelatieOpCode(final RedenBeeindigingRelatieCode code);

    /**
     * Zoek een 'aangever adreshouding'  op basis van de code.
     *
     * @param code De code waarop gezocht wordt.
     * @return De 'aangever adreshouding' behorende bij code.
     */
    AangeverAdreshouding vindAangeverAdreshoudingOpCode(final AangeverAdreshoudingCode code);


    /**
     * Zoek een Adellijke titel op basis van de code.
     *
     * @param code de code
     * @return de adellijketitel
     */
    AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCode code);

    /**
     * Zoek een predikaat op basis van de code.
     *
     * @param code de code
     * @return de predikaat
     */
    Predikaat vindPredikaatOpCode(final PredikaatCode code);

    /**
     * Zoek RedenVerkrijgingNLNationaliteit op code.
     *
     * @param redenVerkrijgingCode de code.
     * @return RedenVerkrijgingNLNationaliteit
     */
    RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
        final RedenVerkrijgingCode redenVerkrijgingCode);

    /**
     * Zoek RedenVerliesNLNationaliteit op code.
     *
     * @param code de code.
     * @return RedenVerliesNLNationaliteit
     */
    RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpCode(final RedenVerliesCode code);

    /**
     * Zoek SoortDocument op naam.
     *
     * @param naam de naam.
     * @return SoortDocument
     */
    SoortDocument vindSoortDocumentOpNaam(final NaamEnumeratiewaarde naam);
}
