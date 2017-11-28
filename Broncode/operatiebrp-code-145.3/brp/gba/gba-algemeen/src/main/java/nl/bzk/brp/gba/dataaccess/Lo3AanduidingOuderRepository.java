/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;

/**
 * Interface voor de AanduidingOuder repository.
 */
public interface Lo3AanduidingOuderRepository {

    /**
     * haalt de aanduidingouder op die behoort bij een bepaalde ouderbetrokkenheid.
     *
     * @param ouderBetrokkenheidId
     *            id van de ouderbetrokkenheid
     * @return de aanduidingouder of null indien niet gevonden
     */
    AanduidingOuder getOuderIdentificatie(final Long ouderBetrokkenheidId);

    /**
     * plaatst aanduidingouder bij een ouderbetrokkenheid.
     *
     * @param ouderBetrokkenheidId
     *            id van de ouderbetrokkenheid
     * @param aanduidingOuder
     *            AanduidingOuder die bij de ouderbetrokkenheid geplaatst moet worden
     */
    void setAanduidingOuderBijOuderBetrokkenheid(final Long ouderBetrokkenheidId, final AanduidingOuder aanduidingOuder);
}
