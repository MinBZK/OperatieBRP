/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonIndicatieStandaardGroepModel;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:36 CET 2012.
 */
@Embeddable
public class PersoonIndicatieStandaardGroepModel extends AbstractPersoonIndicatieStandaardGroepModel implements
        PersoonIndicatieStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonIndicatieStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param waarde waarde van Standaard.
     */
    public PersoonIndicatieStandaardGroepModel(final Ja waarde) {
        super(waarde);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonIndicatieStandaardGroep te kopieren groep.
     */
    public PersoonIndicatieStandaardGroepModel(final PersoonIndicatieStandaardGroep persoonIndicatieStandaardGroep) {
        super(persoonIndicatieStandaardGroep);
    }

}
