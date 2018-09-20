/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.logisch.ber.GedeblokkeerdeMelding;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractGedeblokkeerdeMeldingModel;


/**
 * Een melding die gedeblokkeerd is.
 *
 * Bij het controleren van een bijhoudingsbericht kunnen er ��n of meer meldingen zijn die gedeblokkeerd dienen te
 * worden opdat de bijhouding ook daadwerkelijk verricht kan worden. De gedeblokkeerde meldingen worden geadministreerd.
 *
 *
 *
 */
@Entity
@Table(schema = "Ber", name = "GedeblokkeerdeMelding")
public class GedeblokkeerdeMeldingModel extends AbstractGedeblokkeerdeMeldingModel implements GedeblokkeerdeMelding {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected GedeblokkeerdeMeldingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     * @param attribuut attribuut van Gedeblokkeerde melding.
     */
    public GedeblokkeerdeMeldingModel(final Regel regel, final Meldingtekst melding, final Element attribuut) {
        super(regel, melding, attribuut);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param gedeblokkeerdeMelding Te kopieren object type.
     */
    public GedeblokkeerdeMeldingModel(final GedeblokkeerdeMelding gedeblokkeerdeMelding) {
        super(gedeblokkeerdeMelding);
    }

}
