/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
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
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.basis.*;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisLeveringsautorisatie extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer>,
        BestaansperiodeFormeelImplicietMaterieelAutaut
{

    @Id
    @SequenceGenerator(name = "HIS_LEVERINGSAUTORISATIE", sequenceName = "AutAut.seq_His_Levsautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_LEVERINGSAUTORISATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Levsautorisatie")
    private Leveringsautorisatie leveringsautorisatie;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = ProtocolleringsniveauAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Protocolleringsniveau"))
    @JsonProperty
    private ProtocolleringsniveauAttribuut protocolleringsniveau;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAliasSrtAdmHndLeveren"))
    @JsonProperty
    private JaNeeAttribuut indicatieAliasSoortAdministratieveHandelingLeveren;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = PopulatiebeperkingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Populatiebeperking"))
    @JsonProperty
    private PopulatiebeperkingAttribuut populatiebeperking;

    @Embedded
    @AttributeOverride(name = NeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndPopbeperkingVolConv"))
    @JsonProperty
    private NeeAttribuut indicatiePopulatiebeperkingVolledigGeconverteerd;

    @Embedded
    @AttributeOverride(name = OnbeperkteOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    @JsonProperty
    private OnbeperkteOmschrijvingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    @JsonProperty
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisLeveringsautorisatie() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param leveringsautorisatie leveringsautorisatie van HisLeveringsautorisatie
     * @param naam naam van HisLeveringsautorisatie
     * @param protocolleringsniveau protocolleringsniveau van HisLeveringsautorisatie
     * @param indicatieAliasSoortAdministratieveHandelingLeveren indicatieAliasSoortAdministratieveHandelingLeveren van
     *            HisLeveringsautorisatie
     * @param datumIngang datumIngang van HisLeveringsautorisatie
     * @param datumEinde datumEinde van HisLeveringsautorisatie
     * @param populatiebeperking populatiebeperking van HisLeveringsautorisatie
     * @param indicatiePopulatiebeperkingVolledigGeconverteerd indicatiePopulatiebeperkingVolledigGeconverteerd van
     *            HisLeveringsautorisatie
     * @param toelichting toelichting van HisLeveringsautorisatie
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisLeveringsautorisatie
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisLeveringsautorisatie(
        final Leveringsautorisatie leveringsautorisatie,
        final NaamEnumeratiewaardeAttribuut naam,
        final ProtocolleringsniveauAttribuut protocolleringsniveau,
        final JaNeeAttribuut indicatieAliasSoortAdministratieveHandelingLeveren,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut populatiebeperking,
        final NeeAttribuut indicatiePopulatiebeperkingVolledigGeconverteerd,
        final OnbeperkteOmschrijvingAttribuut toelichting,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.leveringsautorisatie = leveringsautorisatie;
        this.naam = naam;
        this.protocolleringsniveau = protocolleringsniveau;
        this.indicatieAliasSoortAdministratieveHandelingLeveren = indicatieAliasSoortAdministratieveHandelingLeveren;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.populatiebeperking = populatiebeperking;
        this.indicatiePopulatiebeperkingVolledigGeconverteerd = indicatiePopulatiebeperkingVolledigGeconverteerd;
        this.toelichting = toelichting;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisLeveringsautorisatie(final AbstractHisLeveringsautorisatie kopie) {
        super(kopie);
        leveringsautorisatie = kopie.getLeveringsautorisatie();
        naam = kopie.getNaam();
        protocolleringsniveau = kopie.getProtocolleringsniveau();
        indicatieAliasSoortAdministratieveHandelingLeveren = kopie.getIndicatieAliasSoortAdministratieveHandelingLeveren();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();
        populatiebeperking = kopie.getPopulatiebeperking();
        indicatiePopulatiebeperkingVolledigGeconverteerd = kopie.getIndicatiePopulatiebeperkingVolledigGeconverteerd();
        toelichting = kopie.getToelichting();
        indicatieGeblokkeerd = kopie.getIndicatieGeblokkeerd();

    }

    /**
     * Retourneert ID van His Leveringsautorisatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Leveringsautorisatie van His Leveringsautorisatie.
     *
     * @return Leveringsautorisatie.
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Retourneert Naam van His Leveringsautorisatie.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Protocolleringsniveau van His Leveringsautorisatie.
     *
     * @return Protocolleringsniveau.
     */
    public ProtocolleringsniveauAttribuut getProtocolleringsniveau() {
        return protocolleringsniveau;
    }

    /**
     * Retourneert Alias soort administratieve handeling leveren? van His Leveringsautorisatie.
     *
     * @return Alias soort administratieve handeling leveren?.
     */
    public JaNeeAttribuut getIndicatieAliasSoortAdministratieveHandelingLeveren() {
        return indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Retourneert Datum ingang van His Leveringsautorisatie.
     *
     * @return Datum ingang.
     */
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Leveringsautorisatie.
     *
     * @return Datum einde.
     */
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Populatiebeperking van His Leveringsautorisatie.
     *
     * @return Populatiebeperking.
     */
    public PopulatiebeperkingAttribuut getPopulatiebeperking() {
        return populatiebeperking;
    }

    /**
     * Retourneert Populatiebeperking volledig geconverteerd? van His Leveringsautorisatie.
     *
     * @return Populatiebeperking volledig geconverteerd?.
     */
    public NeeAttribuut getIndicatiePopulatiebeperkingVolledigGeconverteerd() {
        return indicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Retourneert Toelichting van His Leveringsautorisatie.
     *
     * @return Toelichting.
     */
    public OnbeperkteOmschrijvingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * Retourneert Geblokkeerd? van His Leveringsautorisatie.
     *
     * @return Geblokkeerd?.
     */
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (naam != null) {
            attributen.add(naam);
        }
        if (protocolleringsniveau != null) {
            attributen.add(protocolleringsniveau);
        }
        if (indicatieAliasSoortAdministratieveHandelingLeveren != null) {
            attributen.add(indicatieAliasSoortAdministratieveHandelingLeveren);
        }
        if (datumIngang != null) {
            attributen.add(datumIngang);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (populatiebeperking != null) {
            attributen.add(populatiebeperking);
        }
        if (indicatiePopulatiebeperkingVolledigGeconverteerd != null) {
            attributen.add(indicatiePopulatiebeperkingVolledigGeconverteerd);
        }
        if (toelichting != null) {
            attributen.add(toelichting);
        }
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
