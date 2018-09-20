/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.logisch.ber.basis.BerichtResultaatGroepBasis;


/**
 *
 *
 */
@MappedSuperclass
public abstract class AbstractBerichtResultaatGroepModel implements BerichtResultaatGroepBasis {

    @Enumerated
    @Column(name = "Verwerking")
    @JsonProperty
    private Verwerkingsresultaat verwerking;

    @Enumerated
    @Column(name = "Bijhouding")
    @JsonProperty
    private Bijhoudingsresultaat bijhouding;

    @Enumerated
    @Column(name = "HoogsteMeldingsniveau")
    @JsonProperty
    private SoortMelding         hoogsteMeldingsniveau;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
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
    public AbstractBerichtResultaatGroepModel(final Verwerkingsresultaat verwerking,
            final Bijhoudingsresultaat bijhouding, final SoortMelding hoogsteMeldingsniveau)
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
    public Verwerkingsresultaat getVerwerking() {
        return verwerking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bijhoudingsresultaat getBijhouding() {
        return bijhouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMelding getHoogsteMeldingsniveau() {
        return hoogsteMeldingsniveau;
    }

}
