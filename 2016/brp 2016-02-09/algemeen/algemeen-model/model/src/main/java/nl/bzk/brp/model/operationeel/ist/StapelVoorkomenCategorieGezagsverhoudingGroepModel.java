/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieGezagsverhoudingGroep;


/**
 *
 *
 */
@Embeddable
public class StapelVoorkomenCategorieGezagsverhoudingGroepModel extends
    AbstractStapelVoorkomenCategorieGezagsverhoudingGroepModel implements
    StapelVoorkomenCategorieGezagsverhoudingGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected StapelVoorkomenCategorieGezagsverhoudingGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuder1HeeftGezag indicatieOuder1HeeftGezag van Categorie gezagsverhouding.
     * @param indicatieOuder2HeeftGezag indicatieOuder2HeeftGezag van Categorie gezagsverhouding.
     * @param indicatieDerdeHeeftGezag  indicatieDerdeHeeftGezag van Categorie gezagsverhouding.
     * @param indicatieOnderCuratele    indicatieOnderCuratele van Categorie gezagsverhouding.
     */
    public StapelVoorkomenCategorieGezagsverhoudingGroepModel(final JaAttribuut indicatieOuder1HeeftGezag,
        final JaAttribuut indicatieOuder2HeeftGezag, final JaAttribuut indicatieDerdeHeeftGezag,
        final JaAttribuut indicatieOnderCuratele)
    {
        super(indicatieOuder1HeeftGezag, indicatieOuder2HeeftGezag, indicatieDerdeHeeftGezag, indicatieOnderCuratele);
    }

}
