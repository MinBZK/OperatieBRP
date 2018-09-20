/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonImmigratieModel;
import nl.bzk.brp.model.operationeel.kern.PersoonImmigratieGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
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
public abstract class AbstractHisPersoonImmigratieModel extends AbstractMaterieleHistorieEntiteit {

    @Id
    @SequenceGenerator(name = "HIS_PERSOONIMMIGRATIE", sequenceName = "Kern.seq_His_PersImmigratie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONIMMIGRATIE")
    @JsonProperty
    private Integer      iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @ManyToOne
    @JoinColumn(name = "LandVanwaarGevestigd")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Land         landVanwaarGevestigd;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVestigingInNederland"))
    @JsonProperty
    private Datum        datumVestigingInNederland;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonImmigratieModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonImmigratieModel(final PersoonModel persoonModel, final PersoonImmigratieGroepModel groep) {
        this.persoon = persoonModel;
        this.landVanwaarGevestigd = groep.getLandVanwaarGevestigd();
        this.datumVestigingInNederland = groep.getDatumVestigingInNederland();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonImmigratieModel(final AbstractHisPersoonImmigratieModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        landVanwaarGevestigd = kopie.getLandVanwaarGevestigd();
        datumVestigingInNederland = kopie.getDatumVestigingInNederland();

    }

    /**
     * Retourneert ID van His Persoon Immigratie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Immigratie.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Land vanwaar gevestigd van His Persoon Immigratie.
     *
     * @return Land vanwaar gevestigd.
     */
    public Land getLandVanwaarGevestigd() {
        return landVanwaarGevestigd;
    }

    /**
     * Retourneert Datum vestiging in Nederland van His Persoon Immigratie.
     *
     * @return Datum vestiging in Nederland.
     */
    public Datum getDatumVestigingInNederland() {
        return datumVestigingInNederland;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public HisPersoonImmigratieModel kopieer() {
        return new HisPersoonImmigratieModel(this);
    }
}
