/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Where;


/**
 * Element is het supertype van veel BMR modelelementen.
 */
@Entity
@Table(name = "SUPERTYPE")
@Immutable
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SOORT", discriminatorType = DiscriminatorType.STRING)
@FilterDef(name = "ElementLaag", parameters = @ParamDef(type = "integer", name = "laag"))
@Filter(name = "ElementLaag", condition = "(laag is null or laag = :laag)")
public class Element extends NamedModelElement {

    private Integer                syncId;
    private Integer                laag;
    private ElementSoort           soort;
    private String                 beschrijving;

    @OneToMany(mappedBy = "element")
    @Where(clause = "SOORT = 'BR'")
    @Fetch(FetchMode.SELECT)
    @OrderBy("soortBedrijfsRegel, naam")
    @Immutable
    private List<BedrijfsRegel>    bedrijfsRegels;
    //
    // TODO: op dit moment zitten de teksten niet in de operationele laag, zodat ze als gevolg van de
    // filterdefinities
    // hierboven niet gelezen worden.
    //
    @OneToMany(mappedBy = "element")
    @MapKey(name = "soort")
    private Map<SoortTekst, Tekst> teksten;

    public List<BedrijfsRegel> getBedrijfsRegels() {
        return bedrijfsRegels;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    @Override
    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public ElementSoort getSoort() {
        return soort;
    }

    public Integer getSyncId() {
        return syncId;
    }

    /**
     * Geef de tekst van de gegeven soort.
     *
     * @param soortTekst het soort tekst dat gevraagd wordt.
     * @return de tekst van de gegeven soort.
     */
    public Tekst getTekst(final SoortTekst soortTekst) {
        return teksten.get(soortTekst);
    }

    public Map<SoortTekst, Tekst> getTeksten() {
        return teksten;
    }

    public Integer getLaag() {
        return laag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
