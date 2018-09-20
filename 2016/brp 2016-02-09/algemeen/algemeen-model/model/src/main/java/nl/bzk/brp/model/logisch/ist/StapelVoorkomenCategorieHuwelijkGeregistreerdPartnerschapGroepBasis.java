/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepBasis extends Groep {

    /**
     * Retourneert Datum aanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Datum aanvang.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvang();

    /**
     * Retourneert Gemeente aanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Gemeente aanvang.
     */
    GemeenteAttribuut getGemeenteAanvang();

    /**
     * Retourneert Buitenlandse plaats aanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Buitenlandse plaats aanvang.
     */
    BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsAanvang();

    /**
     * Retourneert Omschrijving locatie aanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Omschrijving locatie aanvang.
     */
    LocatieomschrijvingAttribuut getOmschrijvingLocatieAanvang();

    /**
     * Retourneert Land/gebied aanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Land/gebied aanvang.
     */
    LandGebiedAttribuut getLandGebiedAanvang();

    /**
     * Retourneert Reden einde van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Reden einde.
     */
    RedenEindeRelatieAttribuut getRedenEinde();

    /**
     * Retourneert Datum einde van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Datum einde.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumEinde();

    /**
     * Retourneert Gemeente einde van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Gemeente einde.
     */
    GemeenteAttribuut getGemeenteEinde();

    /**
     * Retourneert Buitenlandse plaats einde van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Buitenlandse plaats einde.
     */
    BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsEinde();

    /**
     * Retourneert Omschrijving locatie einde van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Omschrijving locatie einde.
     */
    LocatieomschrijvingAttribuut getOmschrijvingLocatieEinde();

    /**
     * Retourneert Land/gebied einde van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Land/gebied einde.
     */
    LandGebiedAttribuut getLandGebiedEinde();

    /**
     * Retourneert Soort relatie van Categorie Huwelijk/Geregistreerd partnerschap.
     *
     * @return Soort relatie.
     */
    SoortRelatieAttribuut getSoortRelatie();

}
