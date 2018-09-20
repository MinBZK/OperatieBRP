/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * Een attribuut van een object type.
 */
@Entity
@DiscriminatorValue("W")
public class WaarderegelWaarde extends GelaagdElement {

    @ManyToOne
    @JoinColumn(name = "OUDER")
    private BedrijfsRegel bedrijfsRegel;

    private String        waarde;
    private String        weergave;

    public BedrijfsRegel getBedrijfsRegel() {
        return bedrijfsRegel;
    }

    public String getWaarde() {
        return waarde;
    }

    public String getWeergave() {
        return weergave;
    }
}
