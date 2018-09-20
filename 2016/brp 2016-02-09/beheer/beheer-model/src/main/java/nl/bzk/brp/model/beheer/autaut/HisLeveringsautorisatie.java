/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity(name = "beheer.HisLeveringsautorisatie")
@Table(schema = "AutAut", name = "His_Levsautorisatie")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class HisLeveringsautorisatie implements FormeleHistorieModel, FormeelHistorisch {

    @Id
    @SequenceGenerator(name = "HIS_LEVERINGSAUTORISATIE", sequenceName = "AutAut.seq_His_Levsautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_LEVERINGSAUTORISATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Levsautorisatie")
    private Leveringsautorisatie leveringsautorisatie;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    private DatumTijdAttribuut datumTijdRegistratie;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsVerval"))
    private DatumTijdAttribuut datumTijdVerval;

    @Embedded
    @AttributeOverride(name = NadereAanduidingVervalAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NadereAandVerval"))
    private NadereAanduidingVervalAttribuut nadereAanduidingVerval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ActieInh")
    private ActieModel bRPActieInhoud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ActieVerval")
    private ActieModel bRPActieVerval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ActieVervalTbvLevMuts")
    private ActieModel bRPActieVervalTbvLeveringMutaties;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndVoorkomenTbvLevMuts"))
    private JaAttribuut indicatieVoorkomenTbvLeveringMutaties;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Column(name = "Protocolleringsniveau")
    @Enumerated
    private Protocolleringsniveau protocolleringsniveau;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAliasSrtAdmHndLeveren"))
    private JaNeeAttribuut indicatieAliasSoortAdministratieveHandelingLeveren;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = PopulatiebeperkingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Populatiebeperking"))
    private PopulatiebeperkingAttribuut populatiebeperking;

    @Embedded
    @AttributeOverride(name = NeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndPopbeperkingVolConv"))
    private NeeAttribuut indicatiePopulatiebeperkingVolledigGeconverteerd;

    @Embedded
    @AttributeOverride(name = OnbeperkteOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    private OnbeperkteOmschrijvingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Retourneert ID van His Leveringsautorisatie.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Leveringsautorisatie van His Leveringsautorisatie.
     *
     * @return Leveringsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Retourneert Datum/tijd registratie van His Leveringsautorisatie.
     *
     * @return Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Retourneert Datum/tijd verval van His Leveringsautorisatie.
     *
     * @return Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getDatumTijdVerval() {
        return datumTijdVerval;
    }

    /**
     * Retourneert Nadere aanduiding verval van His Leveringsautorisatie.
     *
     * @return Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NadereAanduidingVervalAttribuut getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * Retourneert BRP Actie inhoud van His Leveringsautorisatie.
     *
     * @return BRP Actie inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieInhoud() {
        return bRPActieInhoud;
    }

    /**
     * Retourneert BRP Actie verval van His Leveringsautorisatie.
     *
     * @return BRP Actie verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieVerval() {
        return bRPActieVerval;
    }

    /**
     * Retourneert BRP Actie verval tbv levering mutaties van His Leveringsautorisatie.
     *
     * @return BRP Actie verval tbv levering mutaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieVervalTbvLeveringMutaties() {
        return bRPActieVervalTbvLeveringMutaties;
    }

    /**
     * Retourneert Voorkomen tbv levering mutaties? van His Leveringsautorisatie.
     *
     * @return Voorkomen tbv levering mutaties?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieVoorkomenTbvLeveringMutaties() {
        return indicatieVoorkomenTbvLeveringMutaties;
    }

    /**
     * Retourneert Naam van His Leveringsautorisatie.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Protocolleringsniveau van His Leveringsautorisatie.
     *
     * @return Protocolleringsniveau.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Protocolleringsniveau getProtocolleringsniveau() {
        return protocolleringsniveau;
    }

    /**
     * Retourneert Alias soort administratieve handeling leveren? van His Leveringsautorisatie.
     *
     * @return Alias soort administratieve handeling leveren?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaNeeAttribuut getIndicatieAliasSoortAdministratieveHandelingLeveren() {
        return indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Retourneert Datum ingang van His Leveringsautorisatie.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Leveringsautorisatie.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Populatiebeperking van His Leveringsautorisatie.
     *
     * @return Populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PopulatiebeperkingAttribuut getPopulatiebeperking() {
        return populatiebeperking;
    }

    /**
     * Retourneert Populatiebeperking volledig geconverteerd? van His Leveringsautorisatie.
     *
     * @return Populatiebeperking volledig geconverteerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NeeAttribuut getIndicatiePopulatiebeperkingVolledigGeconverteerd() {
        return indicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Retourneert Toelichting van His Leveringsautorisatie.
     *
     * @return Toelichting.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OnbeperkteOmschrijvingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * Retourneert Geblokkeerd? van His Leveringsautorisatie.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet ID van His Leveringsautorisatie.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Leveringsautorisatie van His Leveringsautorisatie.
     *
     * @param pLeveringsautorisatie Leveringsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setLeveringsautorisatie(final Leveringsautorisatie pLeveringsautorisatie) {
        this.leveringsautorisatie = pLeveringsautorisatie;
    }

    /**
     * Zet Datum/tijd registratie van His Leveringsautorisatie.
     *
     * @param pDatumTijdRegistratie Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdRegistratie(final DatumTijdAttribuut pDatumTijdRegistratie) {
        this.datumTijdRegistratie = pDatumTijdRegistratie;
    }

    /**
     * Zet Datum/tijd verval van His Leveringsautorisatie.
     *
     * @param pDatumTijdVerval Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdVerval(final DatumTijdAttribuut pDatumTijdVerval) {
        this.datumTijdVerval = pDatumTijdVerval;
    }

    /**
     * Zet Nadere aanduiding verval van His Leveringsautorisatie.
     *
     * @param pNadereAanduidingVerval Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNadereAanduidingVerval(final NadereAanduidingVervalAttribuut pNadereAanduidingVerval) {
        this.nadereAanduidingVerval = pNadereAanduidingVerval;
    }

    /**
     * Zet BRP Actie inhoud van His Leveringsautorisatie.
     *
     * @param pBRPActieInhoud BRP Actie inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieInhoud(final ActieModel pBRPActieInhoud) {
        this.bRPActieInhoud = pBRPActieInhoud;
    }

    /**
     * Zet BRP Actie verval van His Leveringsautorisatie.
     *
     * @param pBRPActieVerval BRP Actie verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieVerval(final ActieModel pBRPActieVerval) {
        this.bRPActieVerval = pBRPActieVerval;
    }

    /**
     * Zet BRP Actie verval tbv levering mutaties van His Leveringsautorisatie.
     *
     * @param pBRPActieVervalTbvLeveringMutaties BRP Actie verval tbv levering mutaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieVervalTbvLeveringMutaties(final ActieModel pBRPActieVervalTbvLeveringMutaties) {
        this.bRPActieVervalTbvLeveringMutaties = pBRPActieVervalTbvLeveringMutaties;
    }

    /**
     * Zet Voorkomen tbv levering mutaties? van His Leveringsautorisatie.
     *
     * @param pIndicatieVoorkomenTbvLeveringMutaties Voorkomen tbv levering mutaties?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieVoorkomenTbvLeveringMutaties(final JaAttribuut pIndicatieVoorkomenTbvLeveringMutaties) {
        this.indicatieVoorkomenTbvLeveringMutaties = pIndicatieVoorkomenTbvLeveringMutaties;
    }

    /**
     * Zet Naam van His Leveringsautorisatie.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Protocolleringsniveau van His Leveringsautorisatie.
     *
     * @param pProtocolleringsniveau Protocolleringsniveau.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setProtocolleringsniveau(final Protocolleringsniveau pProtocolleringsniveau) {
        this.protocolleringsniveau = pProtocolleringsniveau;
    }

    /**
     * Zet Alias soort administratieve handeling leveren? van His Leveringsautorisatie.
     *
     * @param pIndicatieAliasSoortAdministratieveHandelingLeveren Alias soort administratieve handeling leveren?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieAliasSoortAdministratieveHandelingLeveren(final JaNeeAttribuut pIndicatieAliasSoortAdministratieveHandelingLeveren) {
        this.indicatieAliasSoortAdministratieveHandelingLeveren = pIndicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Zet Datum ingang van His Leveringsautorisatie.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van His Leveringsautorisatie.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Populatiebeperking van His Leveringsautorisatie.
     *
     * @param pPopulatiebeperking Populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPopulatiebeperking(final PopulatiebeperkingAttribuut pPopulatiebeperking) {
        this.populatiebeperking = pPopulatiebeperking;
    }

    /**
     * Zet Populatiebeperking volledig geconverteerd? van His Leveringsautorisatie.
     *
     * @param pIndicatiePopulatiebeperkingVolledigGeconverteerd Populatiebeperking volledig geconverteerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatiePopulatiebeperkingVolledigGeconverteerd(final NeeAttribuut pIndicatiePopulatiebeperkingVolledigGeconverteerd) {
        this.indicatiePopulatiebeperkingVolledigGeconverteerd = pIndicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet Toelichting van His Leveringsautorisatie.
     *
     * @param pToelichting Toelichting.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setToelichting(final OnbeperkteOmschrijvingAttribuut pToelichting) {
        this.toelichting = pToelichting;
    }

    /**
     * Zet Geblokkeerd? van His Leveringsautorisatie.
     *
     * @param pIndicatieGeblokkeerd Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieGeblokkeerd(final JaAttribuut pIndicatieGeblokkeerd) {
        this.indicatieGeblokkeerd = pIndicatieGeblokkeerd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public FormeleHistorieModel getFormeleHistorie() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public Verwerkingssoort getVerwerkingssoort() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public boolean isMagGeleverdWorden() {
        return false;
    }

}
