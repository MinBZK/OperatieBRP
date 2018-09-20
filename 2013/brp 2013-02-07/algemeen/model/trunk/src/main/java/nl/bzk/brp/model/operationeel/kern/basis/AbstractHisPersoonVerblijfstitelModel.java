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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Verblijfstitel;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVerblijfstitelGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfstitelModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerblijfstitelGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonVerblijfstitelModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonVerblijfstitelGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONVERBLIJFSTITEL", sequenceName = "Kern.seq_His_PersVerblijfstitel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONVERBLIJFSTITEL")
    @JsonProperty
    private Integer        iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel   persoon;

    @ManyToOne
    @JoinColumn(name = "Verblijfstitel")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Verblijfstitel verblijfstitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvVerblijfstitel"))
    @JsonProperty
    private Datum          datumAanvangVerblijfstitel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzEindeVerblijfstitel"))
    @JsonProperty
    private Datum          datumVoorzienEindeVerblijfstitel;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonVerblijfstitelModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonVerblijfstitelModel(final PersoonModel persoonModel,
            final PersoonVerblijfstitelGroepModel groep)
    {
        this.persoon = persoonModel;
        this.verblijfstitel = groep.getVerblijfstitel();
        this.datumAanvangVerblijfstitel = groep.getDatumAanvangVerblijfstitel();
        this.datumVoorzienEindeVerblijfstitel = groep.getDatumVoorzienEindeVerblijfstitel();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonVerblijfstitelModel(final AbstractHisPersoonVerblijfstitelModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        verblijfstitel = kopie.getVerblijfstitel();
        datumAanvangVerblijfstitel = kopie.getDatumAanvangVerblijfstitel();
        datumVoorzienEindeVerblijfstitel = kopie.getDatumVoorzienEindeVerblijfstitel();

    }

    /**
     * Retourneert ID van His Persoon Verblijfstitel.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Verblijfstitel.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Verblijfstitel van His Persoon Verblijfstitel.
     *
     * @return Verblijfstitel.
     */
    public Verblijfstitel getVerblijfstitel() {
        return verblijfstitel;
    }

    /**
     * Retourneert Datum aanvang verblijfstitel van His Persoon Verblijfstitel.
     *
     * @return Datum aanvang verblijfstitel.
     */
    public Datum getDatumAanvangVerblijfstitel() {
        return datumAanvangVerblijfstitel;
    }

    /**
     * Retourneert Datum voorzien einde verblijfstitel van His Persoon Verblijfstitel.
     *
     * @return Datum voorzien einde verblijfstitel.
     */
    public Datum getDatumVoorzienEindeVerblijfstitel() {
        return datumVoorzienEindeVerblijfstitel;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonVerblijfstitelModel kopieer() {
        return new HisPersoonVerblijfstitelModel(this);
    }
}
