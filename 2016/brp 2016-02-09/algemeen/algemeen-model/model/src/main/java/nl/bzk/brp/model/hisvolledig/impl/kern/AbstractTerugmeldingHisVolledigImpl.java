/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisTerugmeldingContactpersoonModel;
import nl.bzk.brp.model.operationeel.kern.HisTerugmeldingModel;
import nl.bzk.brp.model.operationeel.kern.TerugmeldingContactpersoonGroepModel;
import nl.bzk.brp.model.operationeel.kern.TerugmeldingStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Terugmelding.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractTerugmeldingHisVolledigImpl implements HisVolledigImpl, TerugmeldingHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "TerugmeldendePartij"))
    @JsonProperty
    private PartijAttribuut terugmeldendePartij;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Bijhgem"))
    @JsonProperty
    private PartijAttribuut bijhoudingsgemeente;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipRegistratie;

    @Embedded
    private TerugmeldingStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "terugmelding", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisTerugmeldingModel> hisTerugmeldingLijst;

    @Transient
    private FormeleHistorieSet<HisTerugmeldingModel> terugmeldingHistorie;

    @Embedded
    private TerugmeldingContactpersoonGroepModel contactpersoon;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "terugmelding", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisTerugmeldingContactpersoonModel> hisTerugmeldingContactpersoonLijst;

    @Transient
    private FormeleHistorieSet<HisTerugmeldingContactpersoonModel> terugmeldingContactpersoonHistorie;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "terugmelding", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<GegevenInTerugmeldingHisVolledigImpl> gegevens;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractTerugmeldingHisVolledigImpl() {
        gegevens = new HashSet<GegevenInTerugmeldingHisVolledigImpl>();

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmeldendePartij terugmeldendePartij van Terugmelding.
     * @param persoon persoon van Terugmelding.
     * @param bijhoudingsgemeente bijhoudingsgemeente van Terugmelding.
     * @param tijdstipRegistratie tijdstipRegistratie van Terugmelding.
     */
    public AbstractTerugmeldingHisVolledigImpl(
        final PartijAttribuut terugmeldendePartij,
        final PersoonHisVolledigImpl persoon,
        final PartijAttribuut bijhoudingsgemeente,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this();
        this.terugmeldendePartij = terugmeldendePartij;
        this.persoon = persoon;
        this.bijhoudingsgemeente = bijhoudingsgemeente;
        this.tijdstipRegistratie = tijdstipRegistratie;

    }

    /**
     * Retourneert ID van Terugmelding.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "TERUGMELDING", sequenceName = "Kern.seq_Terugmelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TERUGMELDING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Terugmeldende partij van Terugmelding.
     *
     * @return Terugmeldende partij.
     */
    public PartijAttribuut getTerugmeldendePartij() {
        return terugmeldendePartij;
    }

    /**
     * Retourneert Persoon van Terugmelding.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Bijhoudingsgemeente van Terugmelding.
     *
     * @return Bijhoudingsgemeente.
     */
    public PartijAttribuut getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * Retourneert Tijdstip registratie van Terugmelding.
     *
     * @return Tijdstip registratie.
     */
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GegevenInTerugmeldingHisVolledigImpl> getGegevens() {
        return gegevens;
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
     * Zet lijst van Gegeven in terugmelding.
     *
     * @param gegevens lijst van Gegeven in terugmelding
     */
    public void setGegevens(final Set<GegevenInTerugmeldingHisVolledigImpl> gegevens) {
        this.gegevens = gegevens;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisTerugmeldingModel actueelStandaard = getTerugmeldingHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new TerugmeldingStandaardGroepModel(
                        actueelStandaard.getOnderzoek(),
                        actueelStandaard.getStatus(),
                        actueelStandaard.getToelichting(),
                        actueelStandaard.getKenmerkMeldendePartij());
        } else {
            this.standaard = null;
        }
        final HisTerugmeldingContactpersoonModel actueelContactpersoon = getTerugmeldingContactpersoonHistorie().getActueleRecord();
        if (actueelContactpersoon != null) {
            this.contactpersoon =
                    new TerugmeldingContactpersoonGroepModel(
                        actueelContactpersoon.getEmail(),
                        actueelContactpersoon.getTelefoonnummer(),
                        actueelContactpersoon.getVoornamen(),
                        actueelContactpersoon.getVoorvoegsel(),
                        actueelContactpersoon.getScheidingsteken(),
                        actueelContactpersoon.getGeslachtsnaamstam());
        } else {
            this.contactpersoon = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisTerugmeldingModel> getTerugmeldingHistorie() {
        if (hisTerugmeldingLijst == null) {
            hisTerugmeldingLijst = new HashSet<>();
        }
        if (terugmeldingHistorie == null) {
            terugmeldingHistorie = new FormeleHistorieSetImpl<HisTerugmeldingModel>(hisTerugmeldingLijst);
        }
        return terugmeldingHistorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisTerugmeldingContactpersoonModel> getTerugmeldingContactpersoonHistorie() {
        if (hisTerugmeldingContactpersoonLijst == null) {
            hisTerugmeldingContactpersoonLijst = new HashSet<>();
        }
        if (terugmeldingContactpersoonHistorie == null) {
            terugmeldingContactpersoonHistorie = new FormeleHistorieSetImpl<HisTerugmeldingContactpersoonModel>(hisTerugmeldingContactpersoonLijst);
        }
        return terugmeldingContactpersoonHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.TERUGMELDING;
    }

}
