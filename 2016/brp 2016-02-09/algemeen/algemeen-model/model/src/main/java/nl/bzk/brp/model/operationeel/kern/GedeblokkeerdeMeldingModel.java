/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.GedeblokkeerdeMelding;


/**
 * Een melding die gedeblokkeerd is.
 * <p/>
 * Bij het controleren van een bijhoudingsbericht kunnen er ��n of meer meldingen zijn die gedeblokkeerd dienen te worden opdat de bijhouding ook
 * daadwerkelijk verricht kan worden. De gedeblokkeerde meldingen worden geadministreerd.
 */
@Entity
@Table(schema = "Kern", name = "GedeblokkeerdeMelding")
public class GedeblokkeerdeMeldingModel extends AbstractGedeblokkeerdeMeldingModel implements GedeblokkeerdeMelding,
    ModelIdentificeerbaar<Long>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected GedeblokkeerdeMeldingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     */
    public GedeblokkeerdeMeldingModel(final RegelAttribuut regel, final MeldingtekstAttribuut melding) {
        super(regel, melding);
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
