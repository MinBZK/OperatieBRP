/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.PartijOnderzoekStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Partij \ Onderzoek.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPartijOnderzoekHisVolledigImpl implements HisVolledigImpl, PartijOnderzoekHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    @JsonBackReference
    private OnderzoekHisVolledigImpl onderzoek;

    @Embedded
    private PartijOnderzoekStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "partijOnderzoek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPartijOnderzoekModel> hisPartijOnderzoekLijst;

    @Transient
    private FormeleHistorieSet<HisPartijOnderzoekModel> partijOnderzoekHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPartijOnderzoekHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param partij partij van Partij \ Onderzoek.
     * @param onderzoek onderzoek van Partij \ Onderzoek.
     */
    public AbstractPartijOnderzoekHisVolledigImpl(final PartijAttribuut partij, final OnderzoekHisVolledigImpl onderzoek) {
        this();
        this.partij = partij;
        this.onderzoek = onderzoek;

    }

    /**
     * Retourneert ID van Partij \ Onderzoek.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PARTIJONDERZOEK", sequenceName = "Kern.seq_PartijOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTIJONDERZOEK")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van Partij \ Onderzoek.
     *
     * @return Partij.
     */
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * Retourneert Onderzoek van Partij \ Onderzoek.
     *
     * @return Onderzoek.
     */
    public OnderzoekHisVolledigImpl getOnderzoek() {
        return onderzoek;
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
        final HisPartijOnderzoekModel actueelStandaard = getPartijOnderzoekHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard = new PartijOnderzoekStandaardGroepModel(actueelStandaard.getRol());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPartijOnderzoekModel> getPartijOnderzoekHistorie() {
        if (hisPartijOnderzoekLijst == null) {
            hisPartijOnderzoekLijst = new HashSet<>();
        }
        if (partijOnderzoekHistorie == null) {
            partijOnderzoekHistorie = new FormeleHistorieSetImpl<HisPartijOnderzoekModel>(hisPartijOnderzoekLijst);
        }
        return partijOnderzoekHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PARTIJENINONDERZOEK;
    }

}
