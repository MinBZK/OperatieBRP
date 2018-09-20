/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroep;


/**
 *
 *
 */
@Embeddable
public class StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel extends
    AbstractStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel implements
    StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param datumAanvang               datumAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param gemeenteAanvang            gemeenteAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param buitenlandsePlaatsAanvang  buitenlandsePlaatsAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param landAanvang                landAanvang van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param redenEinde                 redenEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param datumEinde                 datumEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param gemeenteEinde              gemeenteEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param buitenlandsePlaatsEinde    buitenlandsePlaatsEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param omschrijvingLocatieEinde   omschrijvingLocatieEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param landEinde                  landEinde van Categorie Huwelijk/Geregistreerd partnerschap.
     * @param soortRelatie               soortRelatie van Categorie Huwelijk/Geregistreerd partnerschap.
     */
    public StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumAanvang, final GemeenteAttribuut gemeenteAanvang,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang,
        final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang, final LandGebiedAttribuut landAanvang,
        final RedenEindeRelatieAttribuut redenEinde, final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final GemeenteAttribuut gemeenteEinde, final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde,
        final LocatieomschrijvingAttribuut omschrijvingLocatieEinde, final LandGebiedAttribuut landEinde,
        final SoortRelatieAttribuut soortRelatie)
    {

        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(datumAanvang, gemeenteAanvang, buitenlandsePlaatsAanvang, omschrijvingLocatieAanvang, landAanvang,
            redenEinde, datumEinde, gemeenteEinde, buitenlandsePlaatsEinde, omschrijvingLocatieEinde, landEinde,
            soortRelatie);
    }

}
