/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.migblok;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.migblok.BlokkeringHisVolledigBasis;

/**
 * HisVolledig klasse voor Blokkering.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractBlokkeringHisVolledigImpl implements HisVolledigImpl, BlokkeringHisVolledigBasis {

    @Transient
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ANr"))
    @JsonProperty
    private AdministratienummerAttribuut administratienummer;

    @Embedded
    @AttributeOverride(name = RedenBlokkeringAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "RdnBlokkering"))
    @JsonProperty
    private RedenBlokkeringAttribuut redenBlokkering;

    @Embedded
    @AttributeOverride(name = ProcessInstantieIDAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ProcessInstantieID"))
    @JsonProperty
    private ProcessInstantieIDAttribuut processInstantieID;

    @Embedded
    @AttributeOverride(name = LO3GemeenteCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LO3GemVestiging"))
    @JsonProperty
    private LO3GemeenteCodeAttribuut lO3GemeenteVestiging;

    @Embedded
    @AttributeOverride(name = LO3GemeenteCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LO3GemReg"))
    @JsonProperty
    private LO3GemeenteCodeAttribuut lO3GemeenteRegistratie;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsReg"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipRegistratie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractBlokkeringHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratienummer administratienummer van Blokkering.
     * @param redenBlokkering redenBlokkering van Blokkering.
     * @param processInstantieID processInstantieID van Blokkering.
     * @param lO3GemeenteVestiging lO3GemeenteVestiging van Blokkering.
     * @param lO3GemeenteRegistratie lO3GemeenteRegistratie van Blokkering.
     * @param tijdstipRegistratie tijdstipRegistratie van Blokkering.
     */
    public AbstractBlokkeringHisVolledigImpl(
        final AdministratienummerAttribuut administratienummer,
        final RedenBlokkeringAttribuut redenBlokkering,
        final ProcessInstantieIDAttribuut processInstantieID,
        final LO3GemeenteCodeAttribuut lO3GemeenteVestiging,
        final LO3GemeenteCodeAttribuut lO3GemeenteRegistratie,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this();
        this.administratienummer = administratienummer;
        this.redenBlokkering = redenBlokkering;
        this.processInstantieID = processInstantieID;
        this.lO3GemeenteVestiging = lO3GemeenteVestiging;
        this.lO3GemeenteRegistratie = lO3GemeenteRegistratie;
        this.tijdstipRegistratie = tijdstipRegistratie;

    }

    /**
     * Retourneert ID van Blokkering.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "BLOKKERING", sequenceName = "MigBlok.seq_Blokkering")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BLOKKERING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Administratienummer van Blokkering.
     *
     * @return Administratienummer.
     */
    public AdministratienummerAttribuut getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Retourneert Reden blokkering van Blokkering.
     *
     * @return Reden blokkering.
     */
    public RedenBlokkeringAttribuut getRedenBlokkering() {
        return redenBlokkering;
    }

    /**
     * Retourneert Process instantie ID van Blokkering.
     *
     * @return Process instantie ID.
     */
    public ProcessInstantieIDAttribuut getProcessInstantieID() {
        return processInstantieID;
    }

    /**
     * Retourneert LO3 gemeente vestiging van Blokkering.
     *
     * @return LO3 gemeente vestiging.
     */
    public LO3GemeenteCodeAttribuut getLO3GemeenteVestiging() {
        return lO3GemeenteVestiging;
    }

    /**
     * Retourneert LO3 gemeente registratie van Blokkering.
     *
     * @return LO3 gemeente registratie.
     */
    public LO3GemeenteCodeAttribuut getLO3GemeenteRegistratie() {
        return lO3GemeenteRegistratie;
    }

    /**
     * Retourneert Tijdstip registratie van Blokkering.
     *
     * @return Tijdstip registratie.
     */
    public DatumTijdAttribuut getTijdstipRegistratie() {
        return tijdstipRegistratie;
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

}
