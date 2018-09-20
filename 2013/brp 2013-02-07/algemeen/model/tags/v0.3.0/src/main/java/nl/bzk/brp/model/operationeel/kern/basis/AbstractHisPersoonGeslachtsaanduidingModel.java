/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsaanduidingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsaanduidingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonGeslachtsaanduidingModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonGeslachtsaanduidingGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONGESLACHTSAANDUIDING", sequenceName = "Kern.seq_His_PersGeslachtsaand")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONGESLACHTSAANDUIDING")
    @JsonProperty
    private Integer             iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel        persoon;

    @Enumerated
    @Column(name = "Geslachtsaand")
    @JsonProperty
    private Geslachtsaanduiding geslachtsaanduiding;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonGeslachtsaanduidingModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonGeslachtsaanduidingModel(final PersoonModel persoonModel,
            final PersoonGeslachtsaanduidingGroepModel groep)
    {
        this.persoon = persoonModel;
        this.geslachtsaanduiding = groep.getGeslachtsaanduiding();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonGeslachtsaanduidingModel(final AbstractHisPersoonGeslachtsaanduidingModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        geslachtsaanduiding = kopie.getGeslachtsaanduiding();

    }

    /**
     * Retourneert ID van His Persoon Geslachtsaanduiding.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Geslachtsaanduiding.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Geslachtsaanduiding van His Persoon Geslachtsaanduiding.
     *
     * @return Geslachtsaanduiding.
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonGeslachtsaanduidingModel kopieer() {
        return new HisPersoonGeslachtsaanduidingModel(this);
    }
}
