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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieGezagsverhoudingGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelVoorkomenCategorieGezagsverhoudingGroepModel implements StapelVoorkomenCategorieGezagsverhoudingGroepBasis {

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOuder1HeeftGezag"))
    @JsonProperty
    private JaAttribuut indicatieOuder1HeeftGezag;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOuder2HeeftGezag"))
    @JsonProperty
    private JaAttribuut indicatieOuder2HeeftGezag;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndDerdeHeeftGezag"))
    @JsonProperty
    private JaAttribuut indicatieDerdeHeeftGezag;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOnderCuratele"))
    @JsonProperty
    private JaAttribuut indicatieOnderCuratele;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractStapelVoorkomenCategorieGezagsverhoudingGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieOuder1HeeftGezag indicatieOuder1HeeftGezag van Categorie gezagsverhouding.
     * @param indicatieOuder2HeeftGezag indicatieOuder2HeeftGezag van Categorie gezagsverhouding.
     * @param indicatieDerdeHeeftGezag indicatieDerdeHeeftGezag van Categorie gezagsverhouding.
     * @param indicatieOnderCuratele indicatieOnderCuratele van Categorie gezagsverhouding.
     */
    public AbstractStapelVoorkomenCategorieGezagsverhoudingGroepModel(
        final JaAttribuut indicatieOuder1HeeftGezag,
        final JaAttribuut indicatieOuder2HeeftGezag,
        final JaAttribuut indicatieDerdeHeeftGezag,
        final JaAttribuut indicatieOnderCuratele)
    {
        this.indicatieOuder1HeeftGezag = indicatieOuder1HeeftGezag;
        this.indicatieOuder2HeeftGezag = indicatieOuder2HeeftGezag;
        this.indicatieDerdeHeeftGezag = indicatieDerdeHeeftGezag;
        this.indicatieOnderCuratele = indicatieOnderCuratele;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieOuder1HeeftGezag() {
        return indicatieOuder1HeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieOuder2HeeftGezag() {
        return indicatieOuder2HeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieDerdeHeeftGezag() {
        return indicatieDerdeHeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieOnderCuratele() {
        return indicatieOnderCuratele;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieOuder1HeeftGezag != null) {
            attributen.add(indicatieOuder1HeeftGezag);
        }
        if (indicatieOuder2HeeftGezag != null) {
            attributen.add(indicatieOuder2HeeftGezag);
        }
        if (indicatieDerdeHeeftGezag != null) {
            attributen.add(indicatieDerdeHeeftGezag);
        }
        if (indicatieOnderCuratele != null) {
            attributen.add(indicatieOnderCuratele);
        }
        return attributen;
    }

}
