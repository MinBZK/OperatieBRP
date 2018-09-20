/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import javax.persistence.Embeddable;
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
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieGerelateerdenGroep;


/**
 * Gegevens in deze groep komen uit categorie 02, 03, 05 of 09.
 */
@Embeddable
public class StapelVoorkomenCategorieGerelateerdenGroepModel extends
    AbstractStapelVoorkomenCategorieGerelateerdenGroepModel implements StapelVoorkomenCategorieGerelateerdenGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected StapelVoorkomenCategorieGerelateerdenGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param aktenummer                  aktenummer van Categorie gerelateerden.
     * @param administratienummer         administratienummer van Categorie gerelateerden.
     * @param burgerservicenummer         burgerservicenummer van Categorie gerelateerden.
     * @param voornamen                   voornamen van Categorie gerelateerden.
     * @param predicaat                   predicaat van Categorie gerelateerden.
     * @param adellijkeTitel              adellijkeTitel van Categorie gerelateerden.
     * @param geslachtBijAdellijkeTitelPredikaat
     *                                    geslachtBijAdellijkeTitelPredikaat van Categorie gerelateerden.
     * @param voorvoegsel                 voorvoegsel van Categorie gerelateerden.
     * @param scheidingsteken             scheidingsteken van Categorie gerelateerden.
     * @param geslachtsnaamstam           geslachtsnaamstam van Categorie gerelateerden.
     * @param datumGeboorte               datumGeboorte van Categorie gerelateerden.
     * @param gemeenteGeboorte            gemeenteGeboorte van Categorie gerelateerden.
     * @param buitenlandsePlaatsGeboorte  buitenlandsePlaatsGeboorte van Categorie gerelateerden.
     * @param omschrijvingLocatieGeboorte omschrijvingLocatieGeboorte van Categorie gerelateerden.
     * @param landGebiedGeboorte          landGebiedGeboorte van Categorie gerelateerden.
     * @param geslachtsaanduiding         geslachtsaanduiding van Categorie gerelateerden.
     */
    public StapelVoorkomenCategorieGerelateerdenGroepModel(final AktenummerAttribuut aktenummer,
        final AdministratienummerAttribuut administratienummer,
        final BurgerservicenummerAttribuut burgerservicenummer, final VoornamenAttribuut voornamen,
        final PredicaatAttribuut predicaat, final AdellijkeTitelAttribuut adellijkeTitel,
        final GeslachtsaanduidingAttribuut geslachtBijAdellijkeTitelPredikaat,
        final VoorvoegselAttribuut voorvoegsel, final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam, final DatumEvtDeelsOnbekendAttribuut datumGeboorte,
        final GemeenteAttribuut gemeenteGeboorte, final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte,
        final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte,
        final LandGebiedAttribuut landGebiedGeboorte, final GeslachtsaanduidingAttribuut geslachtsaanduiding)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(aktenummer, administratienummer, burgerservicenummer, voornamen, predicaat, adellijkeTitel,
            geslachtBijAdellijkeTitelPredikaat, voorvoegsel, scheidingsteken, geslachtsnaamstam, datumGeboorte,
            gemeenteGeboorte, buitenlandsePlaatsGeboorte, omschrijvingLocatieGeboorte, landGebiedGeboorte,
            geslachtsaanduiding);
    }

}
