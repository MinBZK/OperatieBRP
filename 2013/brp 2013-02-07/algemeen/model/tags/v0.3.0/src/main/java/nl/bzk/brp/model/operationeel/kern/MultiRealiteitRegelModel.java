/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMultiRealiteitRegel;
import nl.bzk.brp.model.logisch.kern.MultiRealiteitRegel;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractMultiRealiteitRegelModel;


/**
 * De regel waarmee de Multirealiteit wordt vastgelegd.
 *
 * De BRP bevat normaal gesproken op elk moment in de tijd ��n consistent beeld van heden en verleden van de
 * werkelijkheid. In uitzonderlijke gevallen dient de BRP in staat te zijn verschillende, onderling conflicterende,
 * werkelijkheden vast te leggen.
 * Dit gebeurt door de constructie van Multirealiteit.
 * Zie voor verdere toelichting het LO BRP.
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "MultiRealiteitRegel")
public class MultiRealiteitRegelModel extends AbstractMultiRealiteitRegelModel implements MultiRealiteitRegel {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected MultiRealiteitRegelModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geldigVoor geldigVoor van Multi-realiteit regel.
     * @param soort soort van Multi-realiteit regel.
     * @param persoon persoon van Multi-realiteit regel.
     * @param multiRealiteitPersoon multiRealiteitPersoon van Multi-realiteit regel.
     * @param relatie relatie van Multi-realiteit regel.
     * @param betrokkenheid betrokkenheid van Multi-realiteit regel.
     */
    public MultiRealiteitRegelModel(final PersoonModel geldigVoor, final SoortMultiRealiteitRegel soort,
            final PersoonModel persoon, final PersoonModel multiRealiteitPersoon, final RelatieModel relatie,
            final BetrokkenheidModel betrokkenheid)
    {
        super(geldigVoor, soort, persoon, multiRealiteitPersoon, relatie, betrokkenheid);
    }

}
