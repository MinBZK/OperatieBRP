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
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.logisch.kern.basis.PersoonIndicatieBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonIndicatieStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Type;


/**
 * Indicaties bij een persoon.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractPersoonIndicatieModel extends AbstractDynamischObjectType implements
        PersoonIndicatieBasis
{

    @Id
    @SequenceGenerator(name = "PERSOONINDICATIE", sequenceName = "Kern.seq_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONINDICATIE")
    @JsonProperty
    private Integer                             iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel                        persoon;

    @Enumerated
    @Column(name = "Srt", updatable = false)
    @JsonProperty
    private SoortIndicatie                      soort;

    @Embedded
    @JsonProperty
    private PersoonIndicatieStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "PersIndicatieStatusHis")
    @JsonProperty
    private StatusHistorie                      persoonIndicatieStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonIndicatieModel() {
        this.persoonIndicatieStatusHis = StatusHistorie.X;

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Indicatie.
     * @param soort soort van Persoon \ Indicatie.
     */
    public AbstractPersoonIndicatieModel(final PersoonModel persoon, final SoortIndicatie soort) {
        this();
        this.persoon = persoon;
        this.soort = soort;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonIndicatie Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonIndicatieModel(final PersoonIndicatie persoonIndicatie, final PersoonModel persoon) {
        this();
        this.persoon = persoon;
        this.soort = persoonIndicatie.getSoort();
        if (persoonIndicatie.getStandaard() != null) {
            this.standaard = new PersoonIndicatieStandaardGroepModel(persoonIndicatie.getStandaard());
        }

    }

    /**
     * Retourneert ID van Persoon \ Indicatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Indicatie.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Soort van Persoon \ Indicatie.
     *
     * @return Soort.
     */
    public SoortIndicatie getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonIndicatieStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Persoon \ Indicatie StatusHis van Persoon \ Indicatie.
     *
     * @return Persoon \ Indicatie StatusHis.
     */
    public StatusHistorie getPersoonIndicatieStatusHis() {
        return persoonIndicatieStatusHis;
    }

    /**
     * Zet Standaard van Persoon \ Indicatie. Zet tevens het bijbehorende status his veld op 'A' als het argument niet
     * null
     * is.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonIndicatieStandaardGroepModel standaard) {
        this.standaard = standaard;
        if (standaard != null) {
            persoonIndicatieStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Persoon \ Indicatie StatusHis van Persoon \ Indicatie.
     *
     * @param persoonIndicatieStatusHis Persoon \ Indicatie StatusHis.
     */
    public void setPersoonIndicatieStatusHis(final StatusHistorie persoonIndicatieStatusHis) {
        this.persoonIndicatieStatusHis = persoonIndicatieStatusHis;
    }

}
