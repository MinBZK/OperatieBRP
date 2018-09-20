/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamStandaardGroep;


/**
 * Vorm van historie: beiden. Motivatie: conform samengestelde naam kan een individuele voornaam in de loop van de tijd (c.q.: in de werkelijkheid)
 * veranderen, dus nog los van eventuele registratiefouten. Daarom dus beide vormen van historie. RvdP 17 jan 2012.
 */
@Embeddable
public class PersoonVoornaamStandaardGroepModel extends AbstractPersoonVoornaamStandaardGroepModel implements
    PersoonVoornaamStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonVoornaamStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param naam naam van Standaard.
     */
    public PersoonVoornaamStandaardGroepModel(final VoornaamAttribuut naam) {
        super(naam);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVoornaamStandaardGroep te kopieren groep.
     */
    public PersoonVoornaamStandaardGroepModel(final PersoonVoornaamStandaardGroep persoonVoornaamStandaardGroep) {
        super(persoonVoornaamStandaardGroep);
    }

}
