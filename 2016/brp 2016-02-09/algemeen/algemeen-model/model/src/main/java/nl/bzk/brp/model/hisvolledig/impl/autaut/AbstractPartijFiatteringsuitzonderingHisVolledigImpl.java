/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.autaut;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.autaut.PartijFiatteringsuitzonderingHisVolledigBasis;
import nl.bzk.brp.model.operationeel.autaut.HisPartijFiatteringsuitzonderingModel;
import nl.bzk.brp.model.operationeel.autaut.PartijFiatteringsuitzonderingStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Partij \ Fiatteringsuitzondering.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPartijFiatteringsuitzonderingHisVolledigImpl implements HisVolledigImpl, PartijFiatteringsuitzonderingHisVolledigBasis,
        ALaagAfleidbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    private PartijFiatteringsuitzonderingStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "partijFiatteringsuitzondering", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPartijFiatteringsuitzonderingModel> hisPartijFiatteringsuitzonderingLijst;

    @Transient
    private FormeleHistorieSet<HisPartijFiatteringsuitzonderingModel> partijFiatteringsuitzonderingHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPartijFiatteringsuitzonderingHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Fiatteringsuitzondering.
     */
    public AbstractPartijFiatteringsuitzonderingHisVolledigImpl(final PartijAttribuut partij) {
        this();
        this.partij = partij;

    }

    /**
     * Retourneert ID van Partij \ Fiatteringsuitzondering.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PARTIJFIATTERINGSUITZONDERING", sequenceName = "AutAut.seq_PartijFiatuitz")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTIJFIATTERINGSUITZONDERING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van Partij \ Fiatteringsuitzondering.
     *
     * @return Partij.
     */
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisPartijFiatteringsuitzonderingModel actueelStandaard = getPartijFiatteringsuitzonderingHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new PartijFiatteringsuitzonderingStandaardGroepModel(
                        actueelStandaard.getDatumIngang(),
                        actueelStandaard.getDatumEinde(),
                        actueelStandaard.getPartijBijhoudingsvoorstel(),
                        actueelStandaard.getSoortDocument(),
                        actueelStandaard.getSoortAdministratieveHandeling(),
                        actueelStandaard.getIndicatieGeblokkeerd());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPartijFiatteringsuitzonderingModel> getPartijFiatteringsuitzonderingHistorie() {
        if (hisPartijFiatteringsuitzonderingLijst == null) {
            hisPartijFiatteringsuitzonderingLijst = new HashSet<>();
        }
        if (partijFiatteringsuitzonderingHistorie == null) {
            partijFiatteringsuitzonderingHistorie =
                    new FormeleHistorieSetImpl<HisPartijFiatteringsuitzonderingModel>(hisPartijFiatteringsuitzonderingLijst);
        }
        return partijFiatteringsuitzonderingHistorie;
    }

}
