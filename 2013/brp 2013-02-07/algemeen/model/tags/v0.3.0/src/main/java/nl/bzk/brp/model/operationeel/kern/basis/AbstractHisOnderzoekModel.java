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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijving;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.OnderzoekStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.OnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.OnderzoekStandaardGroepModel;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisOnderzoekModel extends AbstractFormeleHistorieEntiteit implements
        OnderzoekStandaardGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_ONDERZOEK", sequenceName = "Kern.seq_His_Onderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_ONDERZOEK")
    @JsonProperty
    private Integer               iD;

    @ManyToOne
    @JoinColumn(name = "Onderzoek")
    private OnderzoekModel        onderzoek;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatBegin"))
    @JsonProperty
    private Datum                 datumBegin;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    @JsonProperty
    private Datum                 datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    @JsonProperty
    private OnderzoekOmschrijving omschrijving;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisOnderzoekModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param onderzoekModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisOnderzoekModel(final OnderzoekModel onderzoekModel, final OnderzoekStandaardGroepModel groep) {
        this.onderzoek = onderzoekModel;
        this.datumBegin = groep.getDatumBegin();
        this.datumEinde = groep.getDatumEinde();
        this.omschrijving = groep.getOmschrijving();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisOnderzoekModel(final AbstractHisOnderzoekModel kopie) {
        super(kopie);
        onderzoek = kopie.getOnderzoek();
        datumBegin = kopie.getDatumBegin();
        datumEinde = kopie.getDatumEinde();
        omschrijving = kopie.getOmschrijving();

    }

    /**
     * Retourneert ID van His Onderzoek.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Onderzoek van His Onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekModel getOnderzoek() {
        return onderzoek;
    }

    /**
     * Retourneert Datum begin van His Onderzoek.
     *
     * @return Datum begin.
     */
    public Datum getDatumBegin() {
        return datumBegin;
    }

    /**
     * Retourneert Datum einde van His Onderzoek.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Omschrijving van His Onderzoek.
     *
     * @return Omschrijving.
     */
    public OnderzoekOmschrijving getOmschrijving() {
        return omschrijving;
    }

}
