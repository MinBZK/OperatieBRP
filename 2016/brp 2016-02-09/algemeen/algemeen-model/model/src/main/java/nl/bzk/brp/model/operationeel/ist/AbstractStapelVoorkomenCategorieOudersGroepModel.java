/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieOudersGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelVoorkomenCategorieOudersGroepModel implements StapelVoorkomenCategorieOudersGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr6210DatIngangFamilierech"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut rubriek6210DatumIngangFamilierechtelijkeBetrekking;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelVoorkomenCategorieOudersGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param rubriek6210DatumIngangFamilierechtelijkeBetrekking rubriek6210DatumIngangFamilierechtelijkeBetrekking van
     *            Categorie ouders.
     */
    public AbstractStapelVoorkomenCategorieOudersGroepModel(final DatumEvtDeelsOnbekendAttribuut rubriek6210DatumIngangFamilierechtelijkeBetrekking) {
        this.rubriek6210DatumIngangFamilierechtelijkeBetrekking = rubriek6210DatumIngangFamilierechtelijkeBetrekking;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getRubriek6210DatumIngangFamilierechtelijkeBetrekking() {
        return rubriek6210DatumIngangFamilierechtelijkeBetrekking;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (rubriek6210DatumIngangFamilierechtelijkeBetrekking != null) {
            attributen.add(rubriek6210DatumIngangFamilierechtelijkeBetrekking);
        }
        return attributen;
    }

}
