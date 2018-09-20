/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroep;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroepBasis;

/**
 * Gegevens met betrekking tot het ouderlijk gezag.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractOuderOuderlijkGezagGroepModel implements OuderOuderlijkGezagGroepBasis {

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOuderHeeftGezag"))
    @JsonProperty
    private JaNeeAttribuut indicatieOuderHeeftGezag;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractOuderOuderlijkGezagGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuderHeeftGezag indicatieOuderHeeftGezag van Ouderlijk gezag.
     */
    public AbstractOuderOuderlijkGezagGroepModel(final JaNeeAttribuut indicatieOuderHeeftGezag) {
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param ouderOuderlijkGezagGroep te kopieren groep.
     */
    public AbstractOuderOuderlijkGezagGroepModel(final OuderOuderlijkGezagGroep ouderOuderlijkGezagGroep) {
        this.indicatieOuderHeeftGezag = ouderOuderlijkGezagGroep.getIndicatieOuderHeeftGezag();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieOuderHeeftGezag != null) {
            attributen.add(indicatieOuderHeeftGezag);
        }
        return attributen;
    }

}
