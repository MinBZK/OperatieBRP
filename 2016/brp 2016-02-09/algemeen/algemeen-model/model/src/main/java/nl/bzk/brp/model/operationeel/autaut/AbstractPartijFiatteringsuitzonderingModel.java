/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.PartijFiatteringsuitzonderingBasis;

/**
 * Bijhoudingsvoorstellen waarvan de Partij niet wil dat deze automatisch gefiatteerd worden.
 *
 * Fiatteringsuitzonderingen mogen alleen gedefinieerd zijn als de Partij (in principe) automatisch fiatteert
 * (A:"Partij.Automatisch fiatteren?" = Ja). Alle bijhoudingen van andere bijhouders die voldoen aan een criterium in
 * dit Objecttype zijn dan uitzonderingen op het automatisch fiatteren en zal deze Partij handmatig fiatteren.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPartijFiatteringsuitzonderingModel extends AbstractDynamischObject implements PartijFiatteringsuitzonderingBasis,
        ModelIdentificeerbaar<Integer>
{

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @JsonProperty
    private PartijFiatteringsuitzonderingStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPartijFiatteringsuitzonderingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Fiatteringsuitzondering.
     */
    public AbstractPartijFiatteringsuitzonderingModel(final PartijAttribuut partij) {
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
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijFiatteringsuitzonderingStandaardGroepModel getStandaard() {
        return standaard;
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
     * Zet Standaard van Partij \ Fiatteringsuitzondering.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PartijFiatteringsuitzonderingStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (standaard != null) {
            groepen.add(standaard);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (partij != null) {
            attributen.add(partij);
        }
        return attributen;
    }

}
