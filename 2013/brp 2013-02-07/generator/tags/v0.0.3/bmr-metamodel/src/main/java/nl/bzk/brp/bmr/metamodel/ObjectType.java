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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Where;


/**
 * Een object type, zeg maar een klasse.
 */
@Entity
@DiscriminatorValue("OT")
public class ObjectType extends Type {

    private static final class InSetOfModelPredicate implements Predicate<Attribuut> {

        private final InSetOfModel inSom;

        private InSetOfModelPredicate(final InSetOfModel inSom) {
            this.inSom = inSom;
        }

        @Override
        public boolean apply(final Attribuut attribuut) {
            return Arrays.asList(inSom, InSetOfModel.B).contains(attribuut.getInSetOfModel());
        }
    }

    @ManyToOne
    @JoinColumn(name = "VERSIE")
    private Versie           versie;

    @Column(name = "SOORT_INHOUD")
    private SoortInhoud      soortInhoud;

    private String           afleidbaar;

    @Column(name = "HISTORIE_VASTLEGGEN")
    private Historie         historieVastleggen;

    private String           meervoudsNaam;

    @Column(name = "GEN_SLEUTEL")
    private String           kunstmatigeSleutel;

    private String           lookup;

    @ManyToOne
    @JoinColumn(name = "SUPERTYPE")
    private ObjectType       superType;

    @OneToMany(mappedBy = "superType")
    @Where(clause = "SOORT = 'OT'")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("volgnummer")
    private List<ObjectType> subTypen   = new ArrayList<ObjectType>();

    @OneToMany(mappedBy = "objectType")
    @Where(clause = "SOORT = 'A'")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("volgnummer")
    @Immutable
    private List<Attribuut>  attributen = new ArrayList<Attribuut>();

    @OneToMany(mappedBy = "objectType")
    @Where(clause = "SOORT = 'G'")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("volgnummer")
    @Immutable
    private List<Groep>      groepen    = new ArrayList<Groep>();

    @OneToMany(mappedBy = "objectType")
    @OrderBy("relatiefId")
    @Fetch(FetchMode.SUBSELECT)
    private List<Tuple>      tuples     = new ArrayList<Tuple>();

    public boolean isAfleidbaar() {
        return "J".equals(afleidbaar);
    }

    public List<Attribuut> getAttributen() {
        return attributen;
    }

    public Iterable<Attribuut> getAttributen(final InSetOfModel inSom) {
        return Iterables.filter(attributen, new InSetOfModelPredicate(inSom));
    }

    public Iterable<Attribuut> getSimpeleAttributen() {
        return Iterables.filter(getAttributen(), new Predicate<Attribuut>() {

            @Override
            public boolean apply(final Attribuut attribuut) {
                return attribuut.getType().isAttribuutType();
            }
        });
    }

    public Iterable<Attribuut> getSimpeleAttributen(final InSetOfModel inSom) {
        return Iterables.filter(getSimpeleAttributen(), new InSetOfModelPredicate(inSom));
    }

    public Iterable<Attribuut> getObjectTypeAttributen() {
        return Iterables.filter(getAttributen(), new Predicate<Attribuut>() {

            @Override
            public boolean apply(final Attribuut attribuut) {
                return attribuut.getType().isObjectType();
            }
        });
    }

    public Iterable<Attribuut> getObjectTypeAttributen(final InSetOfModel inSom) {
        return Iterables.filter(getObjectTypeAttributen(), new InSetOfModelPredicate(inSom));
    }

    public void setAttributen(final List<Attribuut> attributen) {
        this.attributen = attributen;
    }

    public List<Groep> getGroepen() {
        return groepen;
    }

    public Historie getHistorieVastleggen() {
        return historieVastleggen;
    }

    public Attribuut getIdAttribuut() {
        return Iterables.find(attributen, new Predicate<Attribuut>() {

            @Override
            public boolean apply(final Attribuut attribuut) {
                return attribuut.isIdentifier();
            }
        });
    }

    public boolean isKunstmatigeSleutel() {
        return "J".equals(kunstmatigeSleutel);
    }

    public boolean isLookup() {
        return "J".equals(lookup);
    }

    public String getMeervoudsNaam() {
        return meervoudsNaam;
    }

    public Schema getSchema() {
        return getVersie().getSchema();
    }

    public SoortInhoud getSoortInhoud() {
        return soortInhoud;
    }

    public void setSoortInhoud(final SoortInhoud soortInhoud) {
        this.soortInhoud = soortInhoud;
    }

    public List<ObjectType> getSubTypen() {
        return subTypen;
    }

    public ObjectType getSuperType() {
        return superType;
    }

    public List<Tuple> getTuples() {
        return tuples;
    }

    public Versie getVersie() {
        return versie;
    }

    public void setVersie(final Versie versie) {
        this.versie = versie;
    }

    /**
     * Handige methode om te bepalen of dit objectType tuples heeft waarvan de code niet <code>null</code> is. De
     * aanname is dat als één code niet <code>null</code> is, de andere codes dat ook niet zijn, en vice versa.
     *
     * @return <code>true</code> als dit objectType een tuple heeft met een niet <code>null</code> code, en anders
     *         <code>false</code>.
     */
    public boolean hasCode() {
        boolean result = false;
        if (SoortInhoud.X == getSoortInhoud()) {
            for (Tuple tuple : getTuples()) {
                result = tuple.getCode() != null;
                break;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttribuutType() {
        return false;
    }

    /**
     * Bepaalt of dit een objecttype is dat een historische versie van een ander objecttype te representeren.
     *
     * @return <code>true</code> als het een historie object is, en anders <code>false</code>.
     */
    public boolean isHistorieObject() {
        return getIdentifierCode().toLowerCase().startsWith("his_");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isObjectType() {
        return true;
    }
}
