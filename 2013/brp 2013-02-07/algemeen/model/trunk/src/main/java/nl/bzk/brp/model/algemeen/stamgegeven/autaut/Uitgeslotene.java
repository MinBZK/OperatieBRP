/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractUitgeslotene;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;


/**
 * Het expliciet uitgesloten zijn van een Partij in een Bijhoudingsautorisatie.
 *
 * Een Bijhoudingsautorisatie is meestal positief verwoord: een Partij die expliciet is geautoriseerd wordt als
 * geautoriseerde vastgelegd.
 * Indien alle partijen van een soort worden geautoriseerd, is er de mogelijkheid om specifieke Partijen uit te sluiten.
 * In dat geval wordt de negatie vastgelegd: de Partij die expliciet wordt uitgesloten.
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "Uitgeslotene")
public class Uitgeslotene extends AbstractUitgeslotene {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Uitgeslotene() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie van Uitgeslotene.
     * @param uitgeslotenPartij uitgeslotenPartij van Uitgeslotene.
     * @param uitgesloteneStatusHis uitgesloteneStatusHis van Uitgeslotene.
     */
    protected Uitgeslotene(final Bijhoudingsautorisatie bijhoudingsautorisatie, final Partij uitgeslotenPartij,
            final StatusHistorie uitgesloteneStatusHis)
    {
        super(bijhoudingsautorisatie, uitgeslotenPartij, uitgesloteneStatusHis);
    }

}
