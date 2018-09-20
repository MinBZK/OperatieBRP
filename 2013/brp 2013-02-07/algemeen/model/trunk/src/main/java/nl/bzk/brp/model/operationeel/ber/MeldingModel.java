/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.logisch.ber.Melding;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractMeldingModel;


/**
 * Het optreden van een soort melding naar aanleiding van het controleren van een Regel.
 *
 * Vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP, en het kunnen garanderen van een
 * correcte werking van de BRP, worden inkomende berichten gecontroleerd door bepaalde wetmatigheden te controleren:
 * Regels. Als een Regel iets constateerd, zal dat leiden tot een specifieke soort melding, en zal bekend zijn welk
 * attribuut of welke attributen daarbij het probleem veroorzaken.
 *
 *
 *
 */
@Entity
@Table(schema = "Ber", name = "Melding")
public class MeldingModel extends AbstractMeldingModel implements Melding {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected MeldingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Melding.
     * @param soort soort van Melding.
     * @param melding melding van Melding.
     * @param attribuut attribuut van Melding.
     */
    public MeldingModel(final Regel regel, final SoortMelding soort, final Meldingtekst melding, final Element attribuut)
    {
        super(regel, soort, melding, attribuut);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param melding Te kopieren object type.
     */
    public MeldingModel(final Melding melding) {
        super(melding);
    }

}
