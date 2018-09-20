/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekExclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SeverityAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMeldingAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3Melding;


/**
 *
 *
 */
@Entity
@Table(schema = "VerConv", name = "LO3Melding")
public class LO3MeldingModel extends AbstractLO3MeldingModel implements LO3Melding, ModelIdentificeerbaar<Long> {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected LO3MeldingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Voorkomen lO3Voorkomen van LO3 Melding.
     * @param soort        soort van LO3 Melding.
     * @param logSeverity  logSeverity van LO3 Melding.
     * @param groep        groep van LO3 Melding.
     * @param rubriek      rubriek van LO3 Melding.
     */
    public LO3MeldingModel(final LO3VoorkomenModel lO3Voorkomen, final LO3SoortMeldingAttribuut soort,
        final LO3SeverityAttribuut logSeverity, final LO3GroepAttribuut groep,
        final LO3RubriekExclCategorieEnGroepAttribuut rubriek)
    {
        super(lO3Voorkomen, soort, logSeverity, groep, rubriek);
    }

}
