/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;


/**
 * Een attribuut van een object type.
 */
@Entity
@DiscriminatorValue("BR")
public class BedrijfsRegel extends GelaagdElement {

    @ManyToOne
    @JoinColumn(name = "OUDER", insertable = false, updatable = false)
    private Element                 element;

    @Column(name = "SOORT_BEDRIJFSREGEL")
    private SoortBedrijfsRegel      soortBedrijfsRegel;

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "BEDRIJFSREGELATTRIBUUT", joinColumns = @JoinColumn(name = "BEDRIJFSREGEL"),
               inverseJoinColumns = @JoinColumn(name = "ATTRIBUUT"))
    @OrderColumn(name = "VOLGNUMMER")
    private List<Attribuut>         attributen = new ArrayList<Attribuut>();

    @OneToMany(mappedBy = "bedrijfsRegel")
    @Where(clause = "SOORT = 'W'")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("volgnummer")
    @Immutable
    private List<WaarderegelWaarde> waarden;

    public List<Attribuut> getAttributen() {
        return attributen;
    }

    public Element getElement() {
        return element;
    }

    public SoortBedrijfsRegel getSoortBedrijfsRegel() {
        return soortBedrijfsRegel;
    }

    public List<WaarderegelWaarde> getWaarden() {
        return waarden;
    }

}
