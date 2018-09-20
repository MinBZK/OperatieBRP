/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.logisch.ber.BerichtMelding;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractBerichtMeldingModel;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:35 CET 2012.
 */
@Entity
@Table(schema = "Ber", name = "BerMelding")
public class BerichtMeldingModel extends AbstractBerichtMeldingModel implements BerichtMelding {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected BerichtMeldingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param bericht bericht van Bericht \ Melding.
     * @param melding melding van Bericht \ Melding.
     */
    public BerichtMeldingModel(final BerichtModel bericht, final MeldingModel melding) {
        super(bericht, melding);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtMelding Te kopieren object type.
     * @param bericht Bijbehorende Bericht.
     * @param melding Bijbehorende Melding.
     */
    public BerichtMeldingModel(final BerichtMelding berichtMelding, final BerichtModel bericht,
            final MeldingModel melding)
    {
        super(berichtMelding, bericht, melding);
    }

}
