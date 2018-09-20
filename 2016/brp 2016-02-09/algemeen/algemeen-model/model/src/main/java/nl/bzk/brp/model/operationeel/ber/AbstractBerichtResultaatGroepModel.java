/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtResultaatGroepModel implements BerichtResultaatGroepBasis {

    @Embedded
    @AttributeOverride(name = VerwerkingsresultaatAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Verwerking"))
    @JsonProperty
    private VerwerkingsresultaatAttribuut verwerking;

    @Embedded
    @AttributeOverride(name = BijhoudingsresultaatAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Bijhouding"))
    @JsonProperty
    private BijhoudingsresultaatAttribuut bijhouding;

    @Embedded
    @AttributeOverride(name = SoortMeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "HoogsteMeldingsniveau"))
    @JsonProperty
    private SoortMeldingAttribuut hoogsteMeldingsniveau;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBerichtResultaatGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param verwerking verwerking van Resultaat.
     * @param bijhouding bijhouding van Resultaat.
     * @param hoogsteMeldingsniveau hoogsteMeldingsniveau van Resultaat.
     */
    public AbstractBerichtResultaatGroepModel(
        final VerwerkingsresultaatAttribuut verwerking,
        final BijhoudingsresultaatAttribuut bijhouding,
        final SoortMeldingAttribuut hoogsteMeldingsniveau)
    {
        this.verwerking = verwerking;
        this.bijhouding = bijhouding;
        this.hoogsteMeldingsniveau = hoogsteMeldingsniveau;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtResultaatGroep te kopieren groep.
     */
    public AbstractBerichtResultaatGroepModel(final BerichtResultaatGroep berichtResultaatGroep) {
        this.verwerking = berichtResultaatGroep.getVerwerking();
        this.bijhouding = berichtResultaatGroep.getBijhouding();
        this.hoogsteMeldingsniveau = berichtResultaatGroep.getHoogsteMeldingsniveau();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerwerkingsresultaatAttribuut getVerwerking() {
        return verwerking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BijhoudingsresultaatAttribuut getBijhouding() {
        return bijhouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMeldingAttribuut getHoogsteMeldingsniveau() {
        return hoogsteMeldingsniveau;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (verwerking != null) {
            attributen.add(verwerking);
        }
        if (bijhouding != null) {
            attributen.add(bijhouding);
        }
        if (hoogsteMeldingsniveau != null) {
            attributen.add(hoogsteMeldingsniveau);
        }
        return attributen;
    }

}
