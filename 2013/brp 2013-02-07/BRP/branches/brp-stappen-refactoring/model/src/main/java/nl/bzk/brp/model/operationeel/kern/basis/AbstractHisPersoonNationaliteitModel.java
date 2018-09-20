/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


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
public abstract class AbstractHisPersoonNationaliteitModel extends AbstractMaterieleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONNATIONALITEIT", sequenceName = "Kern.seq_His_PersNation")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONNATIONALITEIT")
    @JsonProperty
    private Integer                         iD;

    @ManyToOne
    @JoinColumn(name = "PersNation")
    private PersoonNationaliteitModel       persoonNationaliteit;

    @ManyToOne
    @JoinColumn(name = "RdnVerk")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenVerkrijgingNLNationaliteit redenVerkrijging;

    @ManyToOne
    @JoinColumn(name = "RdnVerlies")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenVerliesNLNationaliteit     redenVerlies;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonNationaliteitModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonNationaliteitModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonNationaliteitModel(final PersoonNationaliteitModel persoonNationaliteitModel,
            final PersoonNationaliteitStandaardGroepModel groep)
    {
        this.persoonNationaliteit = persoonNationaliteitModel;
        this.redenVerkrijging = groep.getRedenVerkrijging();
        this.redenVerlies = groep.getRedenVerlies();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonNationaliteitModel(final AbstractHisPersoonNationaliteitModel kopie) {
        super(kopie);
        persoonNationaliteit = kopie.getPersoonNationaliteit();
        redenVerkrijging = kopie.getRedenVerkrijging();
        redenVerlies = kopie.getRedenVerlies();

    }

    /**
     * Retourneert ID van His Persoon \ Nationaliteit.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Nationaliteit van His Persoon \ Nationaliteit.
     *
     * @return Persoon \ Nationaliteit.
     */
    public PersoonNationaliteitModel getPersoonNationaliteit() {
        return persoonNationaliteit;
    }

    /**
     * Retourneert Reden verkrijging van His Persoon \ Nationaliteit.
     *
     * @return Reden verkrijging.
     */
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
        return redenVerkrijging;
    }

    /**
     * Retourneert Reden verlies van His Persoon \ Nationaliteit.
     *
     * @return Reden verlies.
     */
    public RedenVerliesNLNationaliteit getRedenVerlies() {
        return redenVerlies;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public HisPersoonNationaliteitModel kopieer() {
        return new HisPersoonNationaliteitModel(this);
    }
}
