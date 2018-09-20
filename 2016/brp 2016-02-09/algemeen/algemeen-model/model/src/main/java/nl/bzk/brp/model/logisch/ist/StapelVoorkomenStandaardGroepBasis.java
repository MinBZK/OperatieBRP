/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CoderingOnjuistAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekInclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;

/**
 * Groep die van toepassing is voor ALLE stapels, dus voor zowel categorie 02, 03, 05, 09 als 11.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface StapelVoorkomenStandaardGroepBasis extends Groep {

    /**
     * Retourneert Administratieve handeling van Standaard.
     *
     * @return Administratieve handeling.
     */
    AdministratieveHandeling getAdministratieveHandeling();

    /**
     * Retourneert Soort document van Standaard.
     *
     * @return Soort document.
     */
    SoortDocumentAttribuut getSoortDocument();

    /**
     * Retourneert Partij van Standaard.
     *
     * @return Partij.
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Rubriek 8220 Datum document van Standaard.
     *
     * @return Rubriek 8220 Datum document.
     */
    DatumEvtDeelsOnbekendAttribuut getRubriek8220DatumDocument();

    /**
     * Retourneert Document omschrijving van Standaard.
     *
     * @return Document omschrijving.
     */
    DocumentOmschrijvingAttribuut getDocumentOmschrijving();

    /**
     * Retourneert Rubriek 8310 Aanduiding gegevens in onderzoek van Standaard.
     *
     * @return Rubriek 8310 Aanduiding gegevens in onderzoek.
     */
    LO3RubriekInclCategorieEnGroepAttribuut getRubriek8310AanduidingGegevensInOnderzoek();

    /**
     * Retourneert Rubriek 8320 Datum ingang onderzoek van Standaard.
     *
     * @return Rubriek 8320 Datum ingang onderzoek.
     */
    DatumEvtDeelsOnbekendAttribuut getRubriek8320DatumIngangOnderzoek();

    /**
     * Retourneert Rubriek 8330 Datum einde onderzoek van Standaard.
     *
     * @return Rubriek 8330 Datum einde onderzoek.
     */
    DatumEvtDeelsOnbekendAttribuut getRubriek8330DatumEindeOnderzoek();

    /**
     * Retourneert Rubriek 8410 Indicatie onjuist strijdigheid openbare orde van Standaard.
     *
     * @return Rubriek 8410 Indicatie onjuist strijdigheid openbare orde.
     */
    LO3CoderingOnjuistAttribuut getRubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde();

    /**
     * Retourneert Rubriek 8510 Ingangsdatum geldigheid van Standaard.
     *
     * @return Rubriek 8510 Ingangsdatum geldigheid.
     */
    DatumEvtDeelsOnbekendAttribuut getRubriek8510IngangsdatumGeldigheid();

    /**
     * Retourneert Rubriek 8610 Datum van opneming van Standaard.
     *
     * @return Rubriek 8610 Datum van opneming.
     */
    DatumEvtDeelsOnbekendAttribuut getRubriek8610DatumVanOpneming();

}
