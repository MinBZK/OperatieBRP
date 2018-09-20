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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBijhoudingsgemeenteGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingsgemeenteModel;
import nl.bzk.brp.model.operationeel.kern.PersoonBijhoudingsgemeenteGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonBijhoudingsgemeenteModel extends AbstractMaterieleHistorieEntiteit implements
        PersoonBijhoudingsgemeenteGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONBIJHOUDINGSGEMEENTE", sequenceName = "Kern.seq_His_PersBijhgem")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONBIJHOUDINGSGEMEENTE")
    @JsonProperty
    private Integer      iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @ManyToOne
    @JoinColumn(name = "Bijhgem")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij       bijhoudingsgemeente;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatInschrInGem"))
    @JsonProperty
    private Datum        datumInschrijvingInGemeente;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndOnverwDocAanw"))
    @JsonProperty
    private JaNee        indicatieOnverwerktDocumentAanwezig;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonBijhoudingsgemeenteModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonBijhoudingsgemeenteModel(final PersoonModel persoonModel,
            final PersoonBijhoudingsgemeenteGroepModel groep)
    {
        this.persoon = persoonModel;
        this.bijhoudingsgemeente = groep.getBijhoudingsgemeente();
        this.datumInschrijvingInGemeente = groep.getDatumInschrijvingInGemeente();
        this.indicatieOnverwerktDocumentAanwezig = groep.getIndicatieOnverwerktDocumentAanwezig();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonBijhoudingsgemeenteModel(final AbstractHisPersoonBijhoudingsgemeenteModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        bijhoudingsgemeente = kopie.getBijhoudingsgemeente();
        datumInschrijvingInGemeente = kopie.getDatumInschrijvingInGemeente();
        indicatieOnverwerktDocumentAanwezig = kopie.getIndicatieOnverwerktDocumentAanwezig();

    }

    /**
     * Retourneert ID van His Persoon Bijhoudingsgemeente.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Bijhoudingsgemeente.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Bijhoudingsgemeente van His Persoon Bijhoudingsgemeente.
     *
     * @return Bijhoudingsgemeente.
     */
    public Partij getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * Retourneert Datum inschrijving in gemeente van His Persoon Bijhoudingsgemeente.
     *
     * @return Datum inschrijving in gemeente.
     */
    public Datum getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }

    /**
     * Retourneert Onverwerkt document aanwezig? van His Persoon Bijhoudingsgemeente.
     *
     * @return Onverwerkt document aanwezig?.
     */
    public JaNee getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Deze functie maakt een kopie van het object dmv het aanroepen van de copy constructor met zichzelf als argument.
     *
     * @return de kopie
     */
    @Override
    public HisPersoonBijhoudingsgemeenteModel kopieer() {
        return new HisPersoonBijhoudingsgemeenteModel(this);
    }
}
