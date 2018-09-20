/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


/**
 * Een attribuut van een object type.
 */
@Entity
@DiscriminatorValue("G")
public class Groep extends GelaagdElement {

    @ManyToOne
    @JoinColumn(name = "OUDER")
    private ObjectType      objectType;

    @OneToMany(mappedBy = "groep")
    private List<Attribuut> attributen;

    @Column(name = "HISTORIE_VASTLEGGEN")
    private Historie        historieVastleggen;

    private String          verplicht;

    public List<Attribuut> getAttributen() {
        return attributen;
    }

    public Historie getHistorieVastleggen() {
        return historieVastleggen;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public boolean isVerplicht() {
        return "J".equals(getVerplicht());
    }

    public String getVerplicht() {
        return verplicht;
    }
}
