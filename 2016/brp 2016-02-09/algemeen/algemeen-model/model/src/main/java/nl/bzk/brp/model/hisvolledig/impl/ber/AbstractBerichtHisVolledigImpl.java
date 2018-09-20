/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ber;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ber.BerichtHisVolledigBasis;
import nl.bzk.brp.model.operationeel.ber.BerichtParametersGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtResultaatGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtZoekcriteriaPersoonGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Bericht.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtHisVolledigImpl implements HisVolledigImpl, BerichtHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = SoortBerichtAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private SoortBerichtAttribuut soort;

    @Embedded
    @AttributeOverride(name = RichtingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Richting"))
    @JsonProperty
    private RichtingAttribuut richting;

    @Embedded
    private BerichtStuurgegevensGroepModel stuurgegevens;

    @Embedded
    private BerichtParametersGroepModel parameters;

    @Embedded
    private BerichtResultaatGroepModel resultaat;

    @Embedded
    private BerichtStandaardGroepModel standaard;

    @Embedded
    private BerichtZoekcriteriaPersoonGroepModel zoekcriteriaPersoon;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bericht", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<BerichtPersoonHisVolledigImpl> personen;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractBerichtHisVolledigImpl() {
        personen = new HashSet<BerichtPersoonHisVolledigImpl>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Bericht.
     * @param richting richting van Bericht.
     */
    public AbstractBerichtHisVolledigImpl(final SoortBerichtAttribuut soort, final RichtingAttribuut richting) {
        this();
        this.soort = soort;
        this.richting = richting;

    }

    /**
     * Retourneert ID van Bericht.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BERICHT", sequenceName = "Ber.seq_Ber")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BERICHT")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Bericht.
     *
     * @return Soort.
     */
    public SoortBerichtAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Richting van Bericht.
     *
     * @return Richting.
     */
    public RichtingAttribuut getRichting() {
        return richting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BerichtPersoonHisVolledigImpl> getPersonen() {
        return personen;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * Zet Stuurgegevens van Bericht.
     *
     * @param stuurgegevens Stuurgegevens.
     */
    public void setStuurgegevens(final BerichtStuurgegevensGroepModel stuurgegevens) {
        this.stuurgegevens = stuurgegevens;
    }

    /**
     * Zet Parameters van Bericht.
     *
     * @param parameters Parameters.
     */
    public void setParameters(final BerichtParametersGroepModel parameters) {
        this.parameters = parameters;
    }

    /**
     * Zet Resultaat van Bericht.
     *
     * @param resultaat Resultaat.
     */
    public void setResultaat(final BerichtResultaatGroepModel resultaat) {
        this.resultaat = resultaat;
    }

    /**
     * Zet Standaard van Bericht.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final BerichtStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Zoekcriteria persoon van Bericht.
     *
     * @param zoekcriteriaPersoon Zoekcriteria persoon.
     */
    public void setZoekcriteriaPersoon(final BerichtZoekcriteriaPersoonGroepModel zoekcriteriaPersoon) {
        this.zoekcriteriaPersoon = zoekcriteriaPersoon;
    }

    /**
     * Zet lijst van Bericht \ Persoon.
     *
     * @param personen lijst van Bericht \ Persoon
     */
    public void setPersonen(final Set<BerichtPersoonHisVolledigImpl> personen) {
        this.personen = personen;
    }

}
