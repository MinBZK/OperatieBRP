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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity(name = "beheer.HisDienstbundel")
@Table(schema = "AutAut", name = "His_Dienstbundel")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class HisDienstbundel implements FormeleHistorieModel, FormeelHistorisch {

    @Id
    @SequenceGenerator(name = "HIS_DIENSTBUNDEL", sequenceName = "AutAut.seq_His_Dienstbundel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENSTBUNDEL")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Dienstbundel")
    private Dienstbundel dienstbundel;

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

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = PopulatiebeperkingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaderePopulatiebeperking"))
    private PopulatiebeperkingAttribuut naderePopulatiebeperking;

    @Embedded
    @AttributeOverride(name = NeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndNaderePopbeperkingVolConv"))
    private NeeAttribuut indicatieNaderePopulatiebeperkingVolledigGeconverteerd;

    @Embedded
    @AttributeOverride(name = OnbeperkteOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    private OnbeperkteOmschrijvingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Retourneert ID van His Dienstbundel.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van His Dienstbundel.
     *
     * @return Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Datum/tijd registratie van His Dienstbundel.
     *
     * @return Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Retourneert Datum/tijd verval van His Dienstbundel.
     *
     * @return Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getDatumTijdVerval() {
        return datumTijdVerval;
    }

    /**
     * Retourneert Nadere aanduiding verval van His Dienstbundel.
     *
     * @return Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NadereAanduidingVervalAttribuut getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * Retourneert BRP Actie inhoud van His Dienstbundel.
     *
     * @return BRP Actie inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieInhoud() {
        return bRPActieInhoud;
    }

    /**
     * Retourneert BRP Actie verval van His Dienstbundel.
     *
     * @return BRP Actie verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieVerval() {
        return bRPActieVerval;
    }

    /**
     * Retourneert BRP Actie verval tbv levering mutaties van His Dienstbundel.
     *
     * @return BRP Actie verval tbv levering mutaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieVervalTbvLeveringMutaties() {
        return bRPActieVervalTbvLeveringMutaties;
    }

    /**
     * Retourneert Voorkomen tbv levering mutaties? van His Dienstbundel.
     *
     * @return Voorkomen tbv levering mutaties?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieVoorkomenTbvLeveringMutaties() {
        return indicatieVoorkomenTbvLeveringMutaties;
    }

    /**
     * Retourneert Naam van His Dienstbundel.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum ingang van His Dienstbundel.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Dienstbundel.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Nadere populatiebeperking van His Dienstbundel.
     *
     * @return Nadere populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PopulatiebeperkingAttribuut getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Retourneert Nadere populatiebeperking volledig geconverteerd? van His Dienstbundel.
     *
     * @return Nadere populatiebeperking volledig geconverteerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NeeAttribuut getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Retourneert Toelichting van His Dienstbundel.
     *
     * @return Toelichting.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OnbeperkteOmschrijvingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * Retourneert Geblokkeerd? van His Dienstbundel.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet ID van His Dienstbundel.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Dienstbundel van His Dienstbundel.
     *
     * @param pDienstbundel Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDienstbundel(final Dienstbundel pDienstbundel) {
        this.dienstbundel = pDienstbundel;
    }

    /**
     * Zet Datum/tijd registratie van His Dienstbundel.
     *
     * @param pDatumTijdRegistratie Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdRegistratie(final DatumTijdAttribuut pDatumTijdRegistratie) {
        this.datumTijdRegistratie = pDatumTijdRegistratie;
    }

    /**
     * Zet Datum/tijd verval van His Dienstbundel.
     *
     * @param pDatumTijdVerval Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdVerval(final DatumTijdAttribuut pDatumTijdVerval) {
        this.datumTijdVerval = pDatumTijdVerval;
    }

    /**
     * Zet Nadere aanduiding verval van His Dienstbundel.
     *
     * @param pNadereAanduidingVerval Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNadereAanduidingVerval(final NadereAanduidingVervalAttribuut pNadereAanduidingVerval) {
        this.nadereAanduidingVerval = pNadereAanduidingVerval;
    }

    /**
     * Zet BRP Actie inhoud van His Dienstbundel.
     *
     * @param pBRPActieInhoud BRP Actie inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieInhoud(final ActieModel pBRPActieInhoud) {
        this.bRPActieInhoud = pBRPActieInhoud;
    }

    /**
     * Zet BRP Actie verval van His Dienstbundel.
     *
     * @param pBRPActieVerval BRP Actie verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieVerval(final ActieModel pBRPActieVerval) {
        this.bRPActieVerval = pBRPActieVerval;
    }

    /**
     * Zet BRP Actie verval tbv levering mutaties van His Dienstbundel.
     *
     * @param pBRPActieVervalTbvLeveringMutaties BRP Actie verval tbv levering mutaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieVervalTbvLeveringMutaties(final ActieModel pBRPActieVervalTbvLeveringMutaties) {
        this.bRPActieVervalTbvLeveringMutaties = pBRPActieVervalTbvLeveringMutaties;
    }

    /**
     * Zet Voorkomen tbv levering mutaties? van His Dienstbundel.
     *
     * @param pIndicatieVoorkomenTbvLeveringMutaties Voorkomen tbv levering mutaties?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieVoorkomenTbvLeveringMutaties(final JaAttribuut pIndicatieVoorkomenTbvLeveringMutaties) {
        this.indicatieVoorkomenTbvLeveringMutaties = pIndicatieVoorkomenTbvLeveringMutaties;
    }

    /**
     * Zet Naam van His Dienstbundel.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Datum ingang van His Dienstbundel.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van His Dienstbundel.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Nadere populatiebeperking van His Dienstbundel.
     *
     * @param pNaderePopulatiebeperking Nadere populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaderePopulatiebeperking(final PopulatiebeperkingAttribuut pNaderePopulatiebeperking) {
        this.naderePopulatiebeperking = pNaderePopulatiebeperking;
    }

    /**
     * Zet Nadere populatiebeperking volledig geconverteerd? van His Dienstbundel.
     *
     * @param pIndicatieNaderePopulatiebeperkingVolledigGeconverteerd Nadere populatiebeperking volledig geconverteerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(final NeeAttribuut pIndicatieNaderePopulatiebeperkingVolledigGeconverteerd) {
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = pIndicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet Toelichting van His Dienstbundel.
     *
     * @param pToelichting Toelichting.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setToelichting(final OnbeperkteOmschrijvingAttribuut pToelichting) {
        this.toelichting = pToelichting;
    }

    /**
     * Zet Geblokkeerd? van His Dienstbundel.
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
