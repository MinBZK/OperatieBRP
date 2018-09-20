/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;


/**
 * Versie metaelement.
 */
@Entity
@DiscriminatorValue("V")
public class Versie extends Element {

    @ManyToOne
    @JoinColumn(name = "OUDER")
    private Schema                schema;

    @Column(name = "versie_tag")
    private String                versieTag;

    @OneToMany(mappedBy = "versie")
    @OrderBy("volgnummer")
    @Where(clause = "SOORT = 'OT'")
    @Immutable
    @Filter(name = "ElementLaag", condition = "(laag is null or laag = :laag)")
    private List<ObjectType>      objectTypes      = new ArrayList<ObjectType>();

    @OneToMany(mappedBy = "versie")
    @Where(clause = "SOORT = 'AT'")
    @OrderBy("volgnummer")
    @Immutable
    @Filter(name = "ElementLaag", condition = "laag = :laag")
    private List<AttribuutType>   attribuutTypes   = new ArrayList<AttribuutType>();

    @OneToMany(mappedBy = "versie")
    @Where(clause = "SOORT = 'BS'")
    @OrderBy("volgnummer")
    @Immutable
    @Filter(name = "ElementLaag", condition = "laag = :laag")
    private List<BerichtSjabloon> berichtSjablonen = new ArrayList<BerichtSjabloon>();

    public List<AttribuutType> getAttribuutTypes() {
        return attribuutTypes;
    }

    public List<BerichtSjabloon> getBerichtSjablonen() {
        return berichtSjablonen;
    }

    /**
     * Een query voor alleen de A-laag objecten, geen 'his_' objecten.
     *
     * @return Alleen de A-laag objecten, geen 'his_' objecten.
     */
    public Iterable<ObjectType> getLogischeObjectTypes() {
        return Iterables.filter(getObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return !objectType.isHistorieObject();
            }

        });
    }

    /**
     * Een query om alleen objectypen terug te geven met statische stamgegevens en tuples.
     *
     * @return objectypen met statische stamgegevens en tuples.
     */
    public Iterable<ObjectType> getStatischeStamgegevens() {
        return Iterables.filter(getLogischeObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return objectType.getSoortInhoud() == SoortInhoud.X && !objectType.getTuples().isEmpty();
            }
        });

    }

    /**
     * Geef alleen de objecttypen die horen bij de gegeven waarde van inSom of beide.
     *
     * @return De objecttypen die horen bij de gegeven waarde van inSom of beide.
     */
    public Iterable<ObjectType> getObjectTypes(final InSetOfModel inSOM) {
        return Iterables.filter(getObjectTypes(), new Predicate<ObjectType>() {

            @Override
            public boolean apply(final ObjectType objectType) {
                return Arrays.asList(inSOM, InSetOfModel.B).contains(objectType.getInSetOfModel());
            }

        });
    }

    public List<ObjectType> getObjectTypes() {
        return objectTypes;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(final Schema schema) {
        this.schema = schema;
    }

    public String getVersieTag() {
        return versieTag;
    }

}
