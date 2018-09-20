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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Versienummer;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonInschrijvingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonInschrijvingGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonInschrijvingModel extends AbstractFormeleHistorieEntiteit implements
        PersoonInschrijvingGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONINSCHRIJVING", sequenceName = "Kern.seq_His_PersInschr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONINSCHRIJVING")
    @JsonProperty
    private Integer      iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatInschr"))
    @JsonProperty
    private Datum        datumInschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Versienr"))
    @JsonProperty
    private Versienummer versienummer;

    @ManyToOne
    @JoinColumn(name = "VorigePers")
    @JsonProperty
    private PersoonModel vorigePersoon;

    @ManyToOne
    @JoinColumn(name = "VolgendePers")
    @JsonProperty
    private PersoonModel volgendePersoon;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonInschrijvingModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonInschrijvingModel(final PersoonModel persoonModel,
            final PersoonInschrijvingGroepModel groep)
    {
        this.persoon = persoonModel;
        this.datumInschrijving = groep.getDatumInschrijving();
        this.versienummer = groep.getVersienummer();
        this.vorigePersoon = groep.getVorigePersoon();
        this.volgendePersoon = groep.getVolgendePersoon();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonInschrijvingModel(final AbstractHisPersoonInschrijvingModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        datumInschrijving = kopie.getDatumInschrijving();
        versienummer = kopie.getVersienummer();
        vorigePersoon = kopie.getVorigePersoon();
        volgendePersoon = kopie.getVolgendePersoon();

    }

    /**
     * Retourneert ID van His Persoon Inschrijving.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Inschrijving.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Datum inschrijving van His Persoon Inschrijving.
     *
     * @return Datum inschrijving.
     */
    public Datum getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Retourneert Versienummer van His Persoon Inschrijving.
     *
     * @return Versienummer.
     */
    public Versienummer getVersienummer() {
        return versienummer;
    }

    /**
     * Retourneert Vorige persoon van His Persoon Inschrijving.
     *
     * @return Vorige persoon.
     */
    public PersoonModel getVorigePersoon() {
        return vorigePersoon;
    }

    /**
     * Retourneert Volgende persoon van His Persoon Inschrijving.
     *
     * @return Volgende persoon.
     */
    public PersoonModel getVolgendePersoon() {
        return volgendePersoon;
    }

}
