/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractBerichtResultaatGroepModel;


/**
 *
 *
 */
@Embeddable
public class BerichtResultaatGroepModel extends AbstractBerichtResultaatGroepModel implements BerichtResultaatGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected BerichtResultaatGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param verwerking verwerking van Resultaat.
     * @param bijhouding bijhouding van Resultaat.
     * @param hoogsteMeldingsniveau hoogsteMeldingsniveau van Resultaat.
     */
    public BerichtResultaatGroepModel(final Verwerkingsresultaat verwerking, final Bijhoudingsresultaat bijhouding,
            final SoortMelding hoogsteMeldingsniveau)
    {
        super(verwerking, bijhouding, hoogsteMeldingsniveau);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtResultaatGroep te kopieren groep.
     */
    public BerichtResultaatGroepModel(final BerichtResultaatGroep berichtResultaatGroep) {
        super(berichtResultaatGroep);
    }

}
