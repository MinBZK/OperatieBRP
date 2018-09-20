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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRolAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledigBasis;
import nl.bzk.brp.model.operationeel.autaut.HisToegangBijhoudingsautorisatieModel;
import nl.bzk.brp.model.operationeel.autaut.ToegangBijhoudingsautorisatieStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Toegang bijhoudingsautorisatie.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractToegangBijhoudingsautorisatieHisVolledigImpl implements HisVolledigImpl, ToegangBijhoudingsautorisatieHisVolledigBasis,
        ALaagAfleidbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijRolAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Geautoriseerde"))
    @JsonProperty
    private PartijRolAttribuut geautoriseerde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Ondertekenaar"))
    @JsonProperty
    private PartijAttribuut ondertekenaar;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Transporteur"))
    @JsonProperty
    private PartijAttribuut transporteur;

    @Embedded
    private ToegangBijhoudingsautorisatieStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "toegangBijhoudingsautorisatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisToegangBijhoudingsautorisatieModel> hisToegangBijhoudingsautorisatieLijst;

    @Transient
    private FormeleHistorieSet<HisToegangBijhoudingsautorisatieModel> toegangBijhoudingsautorisatieHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractToegangBijhoudingsautorisatieHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geautoriseerde geautoriseerde van Toegang bijhoudingsautorisatie.
     * @param ondertekenaar ondertekenaar van Toegang bijhoudingsautorisatie.
     * @param transporteur transporteur van Toegang bijhoudingsautorisatie.
     */
    public AbstractToegangBijhoudingsautorisatieHisVolledigImpl(
        final PartijRolAttribuut geautoriseerde,
        final PartijAttribuut ondertekenaar,
        final PartijAttribuut transporteur)
    {
        this();
        this.geautoriseerde = geautoriseerde;
        this.ondertekenaar = ondertekenaar;
        this.transporteur = transporteur;

    }

    /**
     * Retourneert ID van Toegang bijhoudingsautorisatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "TOEGANGBIJHOUDINGSAUTORISATIE", sequenceName = "AutAut.seq_ToegangBijhautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TOEGANGBIJHOUDINGSAUTORISATIE")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Geautoriseerde van Toegang bijhoudingsautorisatie.
     *
     * @return Geautoriseerde.
     */
    public PartijRolAttribuut getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Retourneert Ondertekenaar van Toegang bijhoudingsautorisatie.
     *
     * @return Ondertekenaar.
     */
    public PartijAttribuut getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Retourneert Transporteur van Toegang bijhoudingsautorisatie.
     *
     * @return Transporteur.
     */
    public PartijAttribuut getTransporteur() {
        return transporteur;
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
        final HisToegangBijhoudingsautorisatieModel actueelStandaard = getToegangBijhoudingsautorisatieHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new ToegangBijhoudingsautorisatieStandaardGroepModel(
                        actueelStandaard.getDatumIngang(),
                        actueelStandaard.getDatumEinde(),
                        actueelStandaard.getIndicatieGeblokkeerd());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisToegangBijhoudingsautorisatieModel> getToegangBijhoudingsautorisatieHistorie() {
        if (hisToegangBijhoudingsautorisatieLijst == null) {
            hisToegangBijhoudingsautorisatieLijst = new HashSet<>();
        }
        if (toegangBijhoudingsautorisatieHistorie == null) {
            toegangBijhoudingsautorisatieHistorie =
                    new FormeleHistorieSetImpl<HisToegangBijhoudingsautorisatieModel>(hisToegangBijhoudingsautorisatieLijst);
        }
        return toegangBijhoudingsautorisatieHistorie;
    }

}
