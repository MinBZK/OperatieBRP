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

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonOpschortingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOpschortingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOpschortingGroepModel;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonOpschortingModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonOpschortingGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONOPSCHORTING", sequenceName = "Kern.seq_His_PersOpschorting")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONOPSCHORTING")
    @JsonProperty
    private Integer          iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel     persoon;

    @Enumerated
    @Column(name = "RdnOpschortingBijhouding")
    @JsonProperty
    private RedenOpschorting redenOpschortingBijhouding;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonOpschortingModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonOpschortingModel(final PersoonModel persoonModel, final PersoonOpschortingGroepModel groep)
    {
        this.persoon = persoonModel;
        this.redenOpschortingBijhouding = groep.getRedenOpschortingBijhouding();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonOpschortingModel(final AbstractHisPersoonOpschortingModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        redenOpschortingBijhouding = kopie.getRedenOpschortingBijhouding();

    }

    /**
     * Retourneert ID van His Persoon Opschorting.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Opschorting.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Reden opschorting bijhouding van His Persoon Opschorting.
     *
     * @return Reden opschorting bijhouding.
     */
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return redenOpschortingBijhouding;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonOpschortingModel kopieer() {
        return new HisPersoonOpschortingModel(this);
    }
}
