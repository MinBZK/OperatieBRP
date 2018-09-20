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

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsaardModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonBijhoudingsaardModel extends AbstractMaterieleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONBIJHOUDINGSAARD", sequenceName = "Kern.seq_His_PersBijhaard")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONBIJHOUDINGSAARD")
    @JsonProperty
    private Integer         iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel    persoon;

    @Enumerated
    @Column(name = "Bijhaard")
    @JsonProperty
    private Bijhoudingsaard bijhoudingsaard;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonBijhoudingsaardModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonBijhoudingsaardModel(final PersoonModel persoonModel,
            final PersoonBijhoudingsaardGroepModel groep)
    {
        this.persoon = persoonModel;
        this.bijhoudingsaard = groep.getBijhoudingsaard();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonBijhoudingsaardModel(final AbstractHisPersoonBijhoudingsaardModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        bijhoudingsaard = kopie.getBijhoudingsaard();

    }

    /**
     * Retourneert ID van His Persoon Bijhoudingsaard.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Bijhoudingsaard.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Bijhoudingsaard van His Persoon Bijhoudingsaard.
     *
     * @return Bijhoudingsaard.
     */
    public Bijhoudingsaard getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public HisPersoonBijhoudingsaardModel kopieer() {
        return new HisPersoonBijhoudingsaardModel(this);
    }
}
