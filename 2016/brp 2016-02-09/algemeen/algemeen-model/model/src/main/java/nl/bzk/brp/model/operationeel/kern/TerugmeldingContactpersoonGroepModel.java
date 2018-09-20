/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.EmailadresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TelefoonnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.logisch.kern.TerugmeldingContactpersoonGroep;


/**
 *
 *
 */
@Embeddable
public class TerugmeldingContactpersoonGroepModel extends AbstractTerugmeldingContactpersoonGroepModel implements
    TerugmeldingContactpersoonGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected TerugmeldingContactpersoonGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param email             email van Contactpersoon.
     * @param telefoonnummer    telefoonnummer van Contactpersoon.
     * @param voornamen         voornamen van Contactpersoon.
     * @param voorvoegsel       voorvoegsel van Contactpersoon.
     * @param scheidingsteken   scheidingsteken van Contactpersoon.
     * @param geslachtsnaamstam geslachtsnaamstam van Contactpersoon.
     */
    public TerugmeldingContactpersoonGroepModel(final EmailadresAttribuut email,
        final TelefoonnummerAttribuut telefoonnummer, final VoornamenAttribuut voornamen,
        final VoorvoegselAttribuut voorvoegsel, final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam)
    {
        super(email, telefoonnummer, voornamen, voorvoegsel, scheidingsteken, geslachtsnaamstam);
    }

}
