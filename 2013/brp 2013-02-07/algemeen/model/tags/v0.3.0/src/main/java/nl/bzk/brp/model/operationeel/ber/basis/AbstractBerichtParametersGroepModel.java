/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroep;
import nl.bzk.brp.model.logisch.ber.basis.BerichtParametersGroepBasis;
import org.hibernate.annotations.Type;


/**
 *
 *
 */
@MappedSuperclass
public abstract class AbstractBerichtParametersGroepModel implements BerichtParametersGroepBasis {

    @Type(type = "Verwerkingswijze")
    @Column(name = "Verwerkingswijze")
    @JsonProperty
    private Verwerkingswijze    verwerkingswijze;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "PeilmomMaterieel"))
    @JsonProperty
    private Datum               peilmomentMaterieel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "PeilmomFormeel"))
    @JsonProperty
    private DatumTijd           peilmomentFormeel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Aanschouwer"))
    @JsonProperty
    private Burgerservicenummer aanschouwer;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractBerichtParametersGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param verwerkingswijze verwerkingswijze van Parameters.
     * @param peilmomentMaterieel peilmomentMaterieel van Parameters.
     * @param peilmomentFormeel peilmomentFormeel van Parameters.
     * @param aanschouwer aanschouwer van Parameters.
     */
    public AbstractBerichtParametersGroepModel(final Verwerkingswijze verwerkingswijze,
            final Datum peilmomentMaterieel, final DatumTijd peilmomentFormeel, final Burgerservicenummer aanschouwer)
    {
        this.verwerkingswijze = verwerkingswijze;
        this.peilmomentMaterieel = peilmomentMaterieel;
        this.peilmomentFormeel = peilmomentFormeel;
        this.aanschouwer = aanschouwer;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtParametersGroep te kopieren groep.
     */
    public AbstractBerichtParametersGroepModel(final BerichtParametersGroep berichtParametersGroep) {
        this.verwerkingswijze = berichtParametersGroep.getVerwerkingswijze();
        this.peilmomentMaterieel = berichtParametersGroep.getPeilmomentMaterieel();
        this.peilmomentFormeel = berichtParametersGroep.getPeilmomentFormeel();
        this.aanschouwer = berichtParametersGroep.getAanschouwer();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Verwerkingswijze getVerwerkingswijze() {
        return verwerkingswijze;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getPeilmomentMaterieel() {
        return peilmomentMaterieel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getPeilmomentFormeel() {
        return peilmomentFormeel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Burgerservicenummer getAanschouwer() {
        return aanschouwer;
    }

}
