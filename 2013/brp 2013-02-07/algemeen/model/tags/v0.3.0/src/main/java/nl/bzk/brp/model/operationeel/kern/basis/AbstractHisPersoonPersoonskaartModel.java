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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieEntiteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonPersoonskaartGroepBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonPersoonskaartGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisPersoonPersoonskaartModel extends AbstractFormeleHistorieEntiteit implements
        PersoonPersoonskaartGroepBasis
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONPERSOONSKAART", sequenceName = "Kern.seq_His_PersPK")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONPERSOONSKAART")
    @JsonProperty
    private Integer      iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @ManyToOne
    @JoinColumn(name = "GemPK")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij       gemeentePersoonskaart;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndPKVolledigGeconv"))
    @JsonProperty
    private JaNee        indicatiePersoonskaartVolledigGeconverteerd;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisPersoonPersoonskaartModel() {
    }

    /**
     * Copy Constructor die op basis van een A-laag klasse en groep een C/D laag klasse construeert.
     *
     * @param persoonModel instantie van A-laag klasse.
     * @param groep Groep uit de A Laag.
     */
    public AbstractHisPersoonPersoonskaartModel(final PersoonModel persoonModel,
            final PersoonPersoonskaartGroepModel groep)
    {
        this.persoon = persoonModel;
        this.gemeentePersoonskaart = groep.getGemeentePersoonskaart();
        this.indicatiePersoonskaartVolledigGeconverteerd = groep.getIndicatiePersoonskaartVolledigGeconverteerd();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public AbstractHisPersoonPersoonskaartModel(final AbstractHisPersoonPersoonskaartModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        gemeentePersoonskaart = kopie.getGemeentePersoonskaart();
        indicatiePersoonskaartVolledigGeconverteerd = kopie.getIndicatiePersoonskaartVolledigGeconverteerd();

    }

    /**
     * Retourneert ID van His Persoon Persoonskaart.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Persoonskaart.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Gemeente persoonskaart van His Persoon Persoonskaart.
     *
     * @return Gemeente persoonskaart.
     */
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * Retourneert Persoonskaart volledig geconverteerd? van His Persoon Persoonskaart.
     *
     * @return Persoonskaart volledig geconverteerd?.
     */
    public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

}
