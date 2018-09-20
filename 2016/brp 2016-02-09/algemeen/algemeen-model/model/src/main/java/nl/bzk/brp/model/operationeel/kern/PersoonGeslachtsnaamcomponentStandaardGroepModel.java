/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;


/**
 * Vorm van historie: beiden. Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele geslachtsnaamcomponent (bijv. die met volgnummer 1
 * voor persoon X) in de loop van de tijd veranderen, dus nog los van eventuele registratiefouten. Er is dus ��k sprake van materi�le historie. RvdP 17 jan
 * 2012.
 */
@Embeddable
public class PersoonGeslachtsnaamcomponentStandaardGroepModel extends
    AbstractPersoonGeslachtsnaamcomponentStandaardGroepModel implements PersoonGeslachtsnaamcomponentStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonGeslachtsnaamcomponentStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param predicaat       predicaat van Standaard.
     * @param adellijkeTitel  adellijkeTitel van Standaard.
     * @param voorvoegsel     voorvoegsel van Standaard.
     * @param scheidingsteken scheidingsteken van Standaard.
     * @param naam            naam van Standaard.
     */
    public PersoonGeslachtsnaamcomponentStandaardGroepModel(final PredicaatAttribuut predicaat,
        final AdellijkeTitelAttribuut adellijkeTitel, final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken, final GeslachtsnaamstamAttribuut naam)
    {
        super(predicaat, adellijkeTitel, voorvoegsel, scheidingsteken, naam);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsnaamcomponentStandaardGroep
     *         te kopieren groep.
     */
    public PersoonGeslachtsnaamcomponentStandaardGroepModel(
        final PersoonGeslachtsnaamcomponentStandaardGroep persoonGeslachtsnaamcomponentStandaardGroep)
    {
        super(persoonGeslachtsnaamcomponentStandaardGroep);
    }

}
