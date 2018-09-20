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
 * Het voorkomen van een melding in een uitgaand bericht.
 *
 * Een bijhoudingsbericht (een inkomend bericht) kan leiden tot ��n of meer meldingen. Deze worden via een uitgaand
 * bericht gecommuniceerd naar de partij die het inkomende bericht had gestuurd.
 *
 * Een melding had zowel aan een inkomend als aan een uitgaand bericht gekoppeld kunnen zijn. De reden voor het opnemen
 * van het objecttype in het BMR is echter vooral om het uitgaand bericht te kunnen vormgeven. Om die reden is bij de
 * definitie aangesloten op het uitgaand bericht (waar de melding in wordt medegedeeld), en niet aan het inkomend
 * bericht (die heeft geleid tot de melding). Indien wordt besloten om de bericht/melding te persisteren, kan dit
 * besluit eventueel worden herzien: het is dan denkbaar dat (direct) aan het inkomend bericht de daaruit ontstane
 * meldingen worden gekoppeld. Vooralsnog is dit niet aan de orde, en is een 'bericht/melding' alleen te koppelen aan
 * een inkomend bericht door vanuit het inkomend bericht te beschouwen welk uitgaand bericht daarop volgde, en welke
 * meldingen daar in werden vermeld.
 * RvdP 4-12-2012.
 *
 *
 *
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
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param bericht bericht van Bericht \ Melding.
     * @param melding melding van Bericht \ Melding.
     */
    public BerichtMeldingModel(final BerichtModel bericht, final MeldingModel melding) {
        super(bericht, melding);
    }

}
