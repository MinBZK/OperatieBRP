/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.verconv.LO3VoorkomenMappingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3VoorkomenMappingGroepModel implements LO3VoorkomenMappingGroepBasis {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Actie")
    @JsonProperty
    private ActieModel actie;

    @Embedded
    @AttributeOverride(name = SorteervolgordeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LO3ConversieSortering"))
    @JsonProperty
    private SorteervolgordeAttribuut lO3ConversieSortering;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractLO3VoorkomenMappingGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param actie actie van Mapping.
     * @param lO3ConversieSortering lO3ConversieSortering van Mapping.
     */
    public AbstractLO3VoorkomenMappingGroepModel(final ActieModel actie, final SorteervolgordeAttribuut lO3ConversieSortering) {
        this.actie = actie;
        this.lO3ConversieSortering = lO3ConversieSortering;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActieModel getActie() {
        return actie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SorteervolgordeAttribuut getLO3ConversieSortering() {
        return lO3ConversieSortering;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (lO3ConversieSortering != null) {
            attributen.add(lO3ConversieSortering);
        }
        return attributen;
    }

}
