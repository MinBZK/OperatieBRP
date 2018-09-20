/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.ui;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.NamedModelElement;

import org.hibernate.annotations.Immutable;


@Entity
@Immutable
public class BronAttribuut extends NamedModelElement {

    private Integer   volgnummer;

    @ManyToOne
    @JoinColumn(name = "BRON")
    private Bron      bron;

    @ManyToOne
    @JoinColumn(name = "ATTRIBUUT")
    private Attribuut attribuut;

    public Attribuut getAttribuut() {
        return attribuut;
    }

    public Bron getBron() {
        return bron;
    }

    @Override
    public String getNaam() {
        if (super.getNaam() != null) {
            return super.getNaam();
        } else
            return attribuut.getIdentifierCode();
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }
}
