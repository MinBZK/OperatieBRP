/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.migblok;

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
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.migblok.BlokkeringBasis;

/**
 * Indicatie dat de persoon (tijdelijk) geblokkeerd is voor mutaties omdat deze aan het verhuizen is van een GBA naar
 * BRP gemeente (of vice versa).
 *
 * De blokkering tabel heeft bestaansrecht totdat alle gemeenten over zijn naar de BRP, daarna kan deze vervallen.
 *
 * Deze tabel was reeds gemaakt door migratie en is achteraf opgenomen in BMR. Om het aantal wijzigingen te beperken in
 * de implementatie (mede gezien de tijdelijkheid van de tabel), is de gemeente geen Partij maar een vrije code.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBlokkeringModel extends AbstractDynamischObject implements BlokkeringBasis, ModelIdentificeerbaar<Integer> {

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
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBlokkeringModel() {
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
    public AbstractBlokkeringModel(
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
     * {@inheritDoc}
     */
    @Override
    public AdministratienummerAttribuut getAdministratienummer() {
        return administratienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenBlokkeringAttribuut getRedenBlokkering() {
        return redenBlokkering;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessInstantieIDAttribuut getProcessInstantieID() {
        return processInstantieID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3GemeenteCodeAttribuut getLO3GemeenteVestiging() {
        return lO3GemeenteVestiging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3GemeenteCodeAttribuut getLO3GemeenteRegistratie() {
        return lO3GemeenteRegistratie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (administratienummer != null) {
            attributen.add(administratienummer);
        }
        if (redenBlokkering != null) {
            attributen.add(redenBlokkering);
        }
        if (processInstantieID != null) {
            attributen.add(processInstantieID);
        }
        if (lO3GemeenteVestiging != null) {
            attributen.add(lO3GemeenteVestiging);
        }
        if (lO3GemeenteRegistratie != null) {
            attributen.add(lO3GemeenteRegistratie);
        }
        if (tijdstipRegistratie != null) {
            attributen.add(tijdstipRegistratie);
        }
        return attributen;
    }

}
