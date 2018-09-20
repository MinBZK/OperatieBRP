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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Een attribuut van een object type.
 */
@Entity
@DiscriminatorValue("A")
public class Attribuut extends GelaagdElement {

    /**
     * Vertaal een string (J/N) naar een Boolean.
     *
     * @param string te vertalen string.
     * @return {@link Boolean#TRUE} als de string "J" bevat, {@link Boolean#FALSE} als de string "N" bevat, en anders
     *         <code>null</code>.
     */
    private static boolean string2Boolean(final String string) {
        return "J".equals(string);
    }

    @ManyToOne
    @JoinColumn(name = "OUDER")
    private ObjectType          objectType;

    @ManyToOne
    @JoinColumn(name = "GROEP")
    private Groep               groep;

    @ManyToOne
    @JoinColumn(name = "TYPE_")
    @Fetch(FetchMode.JOIN)
    private Type                type;

    @Column(name = "HISTORIE_VASTLEGGEN")
    private Historie            historieVastleggen;

    private String              afleidbaar;

    private String              verplicht;

    @Column(name = "INVERSE_ASSOCIATIE_NAAM")
    private String              inverseAssociatieNaam;

    @Column(name = "INVERSE_ASSOCIATIE_IDENT_CODE")
    private String              inverseAssociatie;

    @ManyToMany(mappedBy = "attributen")
    @Fetch(FetchMode.SUBSELECT)
    private List<BedrijfsRegel> gebruiktInBedrijfsRegels;

    public List<BedrijfsRegel> getGebruiktInBedrijfsRegels() {
        return gebruiktInBedrijfsRegels;
    }

    public Groep getGroep() {
        return groep;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(final ObjectType objectType) {
        this.objectType = objectType;
    }

    public Type getType() {
        return type;
    }

    public boolean isAfleidbaar() {
        return string2Boolean(afleidbaar);
    }

    public Historie getHistorieVastleggen() {
        return historieVastleggen;
    }

    public boolean isIdentifier() {
        return Iterables.any(getGebruiktInBedrijfsRegels(), new Predicate<BedrijfsRegel>() {

            @Override
            public boolean apply(final BedrijfsRegel bedrijfsRegel) {
                return SoortBedrijfsRegel.ID == bedrijfsRegel.getSoortBedrijfsRegel();
            }
        });
    }

    public boolean isVerplicht() {
        return string2Boolean(verplicht);
    }

    /**
     * @return the inverseAssociatie
     */
    public String getInverseAssociatie() {
        return inverseAssociatie;
    }

    /**
     * @return the inverseAssociatieNaam
     */
    public String getInverseAssociatieNaam() {
        return inverseAssociatieNaam;
    }
}
