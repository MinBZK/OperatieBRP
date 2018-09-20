/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.usr.objecttype;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.logisch.gen.objecttype.AbstractRelatie;

/**
 * Relatie
  */
@Entity
@Table(schema = "Kern", name = "Relatie")
@Access(AccessType.FIELD)
public class Relatie extends AbstractRelatie implements RootObject {

    @Transient
    private Set<Betrokkenheid> betrokkenheden;

    public Set<Betrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    public void voegBetrokkenHeidToe(final Betrokkenheid betrokkene) {
        if (betrokkenheden == null) {
            betrokkenheden = new HashSet<Betrokkenheid>();
        }
        betrokkenheden.add(betrokkene);
    }
}
