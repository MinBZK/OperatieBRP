/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Gegevens in deze groep komen uit categorie 02, 03, 05 of 09.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface StapelVoorkomenCategorieGerelateerdenGroepBasis extends Groep {

    /**
     * Retourneert Aktenummer van Categorie gerelateerden.
     *
     * @return Aktenummer.
     */
    AktenummerAttribuut getAktenummer();

    /**
     * Retourneert Administratienummer van Categorie gerelateerden.
     *
     * @return Administratienummer.
     */
    AdministratienummerAttribuut getAdministratienummer();

    /**
     * Retourneert Burgerservicenummer van Categorie gerelateerden.
     *
     * @return Burgerservicenummer.
     */
    BurgerservicenummerAttribuut getBurgerservicenummer();

    /**
     * Retourneert Voornamen van Categorie gerelateerden.
     *
     * @return Voornamen.
     */
    VoornamenAttribuut getVoornamen();

    /**
     * Retourneert Predicaat van Categorie gerelateerden.
     *
     * @return Predicaat.
     */
    PredicaatAttribuut getPredicaat();

    /**
     * Retourneert Adellijke titel van Categorie gerelateerden.
     *
     * @return Adellijke titel.
     */
    AdellijkeTitelAttribuut getAdellijkeTitel();

    /**
     * Retourneert Geslacht bij adellijke titel/predicaat van Categorie gerelateerden.
     *
     * @return Geslacht bij adellijke titel/predicaat.
     */
    GeslachtsaanduidingAttribuut getGeslachtBijAdellijkeTitelPredicaat();

    /**
     * Retourneert Voorvoegsel van Categorie gerelateerden.
     *
     * @return Voorvoegsel.
     */
    VoorvoegselAttribuut getVoorvoegsel();

    /**
     * Retourneert Scheidingsteken van Categorie gerelateerden.
     *
     * @return Scheidingsteken.
     */
    ScheidingstekenAttribuut getScheidingsteken();

    /**
     * Retourneert Geslachtsnaamstam van Categorie gerelateerden.
     *
     * @return Geslachtsnaamstam.
     */
    GeslachtsnaamstamAttribuut getGeslachtsnaamstam();

    /**
     * Retourneert Datum geboorte van Categorie gerelateerden.
     *
     * @return Datum geboorte.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumGeboorte();

    /**
     * Retourneert Gemeente geboorte van Categorie gerelateerden.
     *
     * @return Gemeente geboorte.
     */
    GemeenteAttribuut getGemeenteGeboorte();

    /**
     * Retourneert Buitenlandse plaats geboorte van Categorie gerelateerden.
     *
     * @return Buitenlandse plaats geboorte.
     */
    BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsGeboorte();

    /**
     * Retourneert Omschrijving locatie geboorte van Categorie gerelateerden.
     *
     * @return Omschrijving locatie geboorte.
     */
    LocatieomschrijvingAttribuut getOmschrijvingLocatieGeboorte();

    /**
     * Retourneert Land/gebied geboorte van Categorie gerelateerden.
     *
     * @return Land/gebied geboorte.
     */
    LandGebiedAttribuut getLandGebiedGeboorte();

    /**
     * Retourneert Geslachtsaanduiding van Categorie gerelateerden.
     *
     * @return Geslachtsaanduiding.
     */
    GeslachtsaanduidingAttribuut getGeslachtsaanduiding();

}
