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
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.beheer.kern.Partij;
import nl.bzk.brp.model.beheer.kern.SoortDocument;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity(name = "beheer.HisPartijFiatteringsuitzondering")
@Table(schema = "AutAut", name = "His_PartijFiatuitz")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class HisPartijFiatteringsuitzondering implements FormeleHistorieModel, FormeelHistorisch {

    @Id
    @SequenceGenerator(name = "HIS_PARTIJFIATTERINGSUITZONDERING", sequenceName = "AutAut.seq_His_PartijFiatuitz")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJFIATTERINGSUITZONDERING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PartijFiatuitz")
    private PartijFiatteringsuitzondering partijFiatteringsuitzondering;

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
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PartijBijhvoorstel")
    private Partij partijBijhoudingsvoorstel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SrtDoc")
    private SoortDocument soortDocument;

    @Column(name = "SrtAdmHnd")
    @Enumerated
    private SoortAdministratieveHandeling soortAdministratieveHandeling;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Retourneert ID van His Partij \ Fiatteringsuitzondering.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij \ Fiatteringsuitzondering van His Partij \ Fiatteringsuitzondering.
     *
     * @return Partij \ Fiatteringsuitzondering.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PartijFiatteringsuitzondering getPartijFiatteringsuitzondering() {
        return partijFiatteringsuitzondering;
    }

    /**
     * Retourneert Datum/tijd registratie van His Partij \ Fiatteringsuitzondering.
     *
     * @return Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Retourneert Datum/tijd verval van His Partij \ Fiatteringsuitzondering.
     *
     * @return Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumTijdAttribuut getDatumTijdVerval() {
        return datumTijdVerval;
    }

    /**
     * Retourneert Nadere aanduiding verval van His Partij \ Fiatteringsuitzondering.
     *
     * @return Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NadereAanduidingVervalAttribuut getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * Retourneert BRP Actie inhoud van His Partij \ Fiatteringsuitzondering.
     *
     * @return BRP Actie inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieInhoud() {
        return bRPActieInhoud;
    }

    /**
     * Retourneert BRP Actie verval van His Partij \ Fiatteringsuitzondering.
     *
     * @return BRP Actie verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieVerval() {
        return bRPActieVerval;
    }

    /**
     * Retourneert BRP Actie verval tbv levering mutaties van His Partij \ Fiatteringsuitzondering.
     *
     * @return BRP Actie verval tbv levering mutaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ActieModel getBRPActieVervalTbvLeveringMutaties() {
        return bRPActieVervalTbvLeveringMutaties;
    }

    /**
     * Retourneert Voorkomen tbv levering mutaties? van His Partij \ Fiatteringsuitzondering.
     *
     * @return Voorkomen tbv levering mutaties?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieVoorkomenTbvLeveringMutaties() {
        return indicatieVoorkomenTbvLeveringMutaties;
    }

    /**
     * Retourneert Datum ingang van His Partij \ Fiatteringsuitzondering.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Partij \ Fiatteringsuitzondering.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Partij bijhoudingsvoorstel van His Partij \ Fiatteringsuitzondering.
     *
     * @return Partij bijhoudingsvoorstel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getPartijBijhoudingsvoorstel() {
        return partijBijhoudingsvoorstel;
    }

    /**
     * Retourneert Soort document van His Partij \ Fiatteringsuitzondering.
     *
     * @return Soort document.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Retourneert Soort administratieve handeling van His Partij \ Fiatteringsuitzondering.
     *
     * @return Soort administratieve handeling.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Retourneert Geblokkeerd? van His Partij \ Fiatteringsuitzondering.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet ID van His Partij \ Fiatteringsuitzondering.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Partij \ Fiatteringsuitzondering van His Partij \ Fiatteringsuitzondering.
     *
     * @param pPartijFiatteringsuitzondering Partij \ Fiatteringsuitzondering.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartijFiatteringsuitzondering(final PartijFiatteringsuitzondering pPartijFiatteringsuitzondering) {
        this.partijFiatteringsuitzondering = pPartijFiatteringsuitzondering;
    }

    /**
     * Zet Datum/tijd registratie van His Partij \ Fiatteringsuitzondering.
     *
     * @param pDatumTijdRegistratie Datum/tijd registratie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdRegistratie(final DatumTijdAttribuut pDatumTijdRegistratie) {
        this.datumTijdRegistratie = pDatumTijdRegistratie;
    }

    /**
     * Zet Datum/tijd verval van His Partij \ Fiatteringsuitzondering.
     *
     * @param pDatumTijdVerval Datum/tijd verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumTijdVerval(final DatumTijdAttribuut pDatumTijdVerval) {
        this.datumTijdVerval = pDatumTijdVerval;
    }

    /**
     * Zet Nadere aanduiding verval van His Partij \ Fiatteringsuitzondering.
     *
     * @param pNadereAanduidingVerval Nadere aanduiding verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNadereAanduidingVerval(final NadereAanduidingVervalAttribuut pNadereAanduidingVerval) {
        this.nadereAanduidingVerval = pNadereAanduidingVerval;
    }

    /**
     * Zet BRP Actie inhoud van His Partij \ Fiatteringsuitzondering.
     *
     * @param pBRPActieInhoud BRP Actie inhoud.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieInhoud(final ActieModel pBRPActieInhoud) {
        this.bRPActieInhoud = pBRPActieInhoud;
    }

    /**
     * Zet BRP Actie verval van His Partij \ Fiatteringsuitzondering.
     *
     * @param pBRPActieVerval BRP Actie verval.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieVerval(final ActieModel pBRPActieVerval) {
        this.bRPActieVerval = pBRPActieVerval;
    }

    /**
     * Zet BRP Actie verval tbv levering mutaties van His Partij \ Fiatteringsuitzondering.
     *
     * @param pBRPActieVervalTbvLeveringMutaties BRP Actie verval tbv levering mutaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBRPActieVervalTbvLeveringMutaties(final ActieModel pBRPActieVervalTbvLeveringMutaties) {
        this.bRPActieVervalTbvLeveringMutaties = pBRPActieVervalTbvLeveringMutaties;
    }

    /**
     * Zet Voorkomen tbv levering mutaties? van His Partij \ Fiatteringsuitzondering.
     *
     * @param pIndicatieVoorkomenTbvLeveringMutaties Voorkomen tbv levering mutaties?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieVoorkomenTbvLeveringMutaties(final JaAttribuut pIndicatieVoorkomenTbvLeveringMutaties) {
        this.indicatieVoorkomenTbvLeveringMutaties = pIndicatieVoorkomenTbvLeveringMutaties;
    }

    /**
     * Zet Datum ingang van His Partij \ Fiatteringsuitzondering.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van His Partij \ Fiatteringsuitzondering.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Partij bijhoudingsvoorstel van His Partij \ Fiatteringsuitzondering.
     *
     * @param pPartijBijhoudingsvoorstel Partij bijhoudingsvoorstel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartijBijhoudingsvoorstel(final Partij pPartijBijhoudingsvoorstel) {
        this.partijBijhoudingsvoorstel = pPartijBijhoudingsvoorstel;
    }

    /**
     * Zet Soort document van His Partij \ Fiatteringsuitzondering.
     *
     * @param pSoortDocument Soort document.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoortDocument(final SoortDocument pSoortDocument) {
        this.soortDocument = pSoortDocument;
    }

    /**
     * Zet Soort administratieve handeling van His Partij \ Fiatteringsuitzondering.
     *
     * @param pSoortAdministratieveHandeling Soort administratieve handeling.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling pSoortAdministratieveHandeling) {
        this.soortAdministratieveHandeling = pSoortAdministratieveHandeling;
    }

    /**
     * Zet Geblokkeerd? van His Partij \ Fiatteringsuitzondering.
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
