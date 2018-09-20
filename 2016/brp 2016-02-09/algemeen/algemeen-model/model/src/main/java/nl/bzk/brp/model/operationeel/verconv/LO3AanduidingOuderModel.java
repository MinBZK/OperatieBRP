/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3AanduidingOuder;
import nl.bzk.brp.model.operationeel.kern.OuderModel;

/**
 * Aanduiding welke BRP Ouder Betrokkenheid als Ouder 1/2 in LO3 berichten geleverd wordt.
 *
 * Deze mapping zorgt voor een consistente opbouw van LO3 berichten. De inhoud van deze tabel wordt bepaald op basis van
 * een GBA bijhouding of tijdens het (eerste keer) leveren van een LO3 Bericht.
 *
 *
 *
 */
@Entity
@Table(schema = "VerConv", name = "LO3AandOuder")
@Access(value = AccessType.FIELD)
@NamedQuery(name = "LO3AanduidingOuderModel.findByOuderBetrokkenheidId",
        query = "SELECT a FROM LO3AanduidingOuderModel a WHERE a.ouder.id = :betrokkenheidId")
public class LO3AanduidingOuderModel extends AbstractLO3AanduidingOuderModel implements LO3AanduidingOuder, ModelIdentificeerbaar<Long> {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected LO3AanduidingOuderModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param ouder ouder van LO3 Aanduiding Ouder.
     * @param aanduidingOuder aanduidingOuder van LO3 Aanduiding Ouder.
     */
    public LO3AanduidingOuderModel(final OuderModel ouder, final LO3SoortAanduidingOuderAttribuut aanduidingOuder) {
        super(ouder, aanduidingOuder);
    }

}
