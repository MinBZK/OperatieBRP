/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3ReferentieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBronAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.verconv.LO3BerichtHisVolledigBasis;
import nl.bzk.brp.model.operationeel.verconv.LO3BerichtConversieGroepModel;

/**
 * HisVolledig klasse voor LO3 Bericht.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3BerichtHisVolledigImpl implements HisVolledigImpl, LO3BerichtHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBersrtOnderdeelLO3Stelsel"))
    @JsonProperty
    private JaNeeAttribuut indicatieBerichtsoortOnderdeelLO3Stelsel;

    @Embedded
    @AttributeOverride(name = LO3ReferentieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Referentie"))
    @JsonProperty
    private LO3ReferentieAttribuut referentie;

    @Embedded
    @AttributeOverride(name = LO3BerichtenBronAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Bron"))
    @JsonProperty
    private LO3BerichtenBronAttribuut bron;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ANr"))
    @JsonProperty
    private AdministratienummerAttribuut administratienummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AttributeOverride(name = ByteaopslagAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Berdata"))
    @JsonProperty
    private ByteaopslagAttribuut berichtdata;

    @Embedded
    @AttributeOverride(name = ChecksumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Checksum"))
    @JsonProperty
    private ChecksumAttribuut checksum;

    @Embedded
    private LO3BerichtConversieGroepModel conversie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractLO3BerichtHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param indicatieBerichtsoortOnderdeelLO3Stelsel indicatieBerichtsoortOnderdeelLO3Stelsel van LO3 Bericht.
     * @param referentie referentie van LO3 Bericht.
     * @param bron bron van LO3 Bericht.
     * @param administratienummer administratienummer van LO3 Bericht.
     * @param persoon persoon van LO3 Bericht.
     * @param berichtdata berichtdata van LO3 Bericht.
     * @param checksum checksum van LO3 Bericht.
     */
    public AbstractLO3BerichtHisVolledigImpl(
        final JaNeeAttribuut indicatieBerichtsoortOnderdeelLO3Stelsel,
        final LO3ReferentieAttribuut referentie,
        final LO3BerichtenBronAttribuut bron,
        final AdministratienummerAttribuut administratienummer,
        final PersoonHisVolledigImpl persoon,
        final ByteaopslagAttribuut berichtdata,
        final ChecksumAttribuut checksum)
    {
        this();
        this.indicatieBerichtsoortOnderdeelLO3Stelsel = indicatieBerichtsoortOnderdeelLO3Stelsel;
        this.referentie = referentie;
        this.bron = bron;
        this.administratienummer = administratienummer;
        this.persoon = persoon;
        this.berichtdata = berichtdata;
        this.checksum = checksum;

    }

    /**
     * Retourneert ID van LO3 Bericht.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LO3BERICHT", sequenceName = "VerConv.seq_LO3Ber")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LO3BERICHT")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Berichtsoort onderdeel LO3 stelsel? van LO3 Bericht.
     *
     * @return Berichtsoort onderdeel LO3 stelsel?.
     */
    public JaNeeAttribuut getIndicatieBerichtsoortOnderdeelLO3Stelsel() {
        return indicatieBerichtsoortOnderdeelLO3Stelsel;
    }

    /**
     * Retourneert Referentie van LO3 Bericht.
     *
     * @return Referentie.
     */
    public LO3ReferentieAttribuut getReferentie() {
        return referentie;
    }

    /**
     * Retourneert Bron van LO3 Bericht.
     *
     * @return Bron.
     */
    public LO3BerichtenBronAttribuut getBron() {
        return bron;
    }

    /**
     * Retourneert Administratienummer van LO3 Bericht.
     *
     * @return Administratienummer.
     */
    public AdministratienummerAttribuut getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Retourneert Persoon van LO3 Bericht.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Berichtdata van LO3 Bericht.
     *
     * @return Berichtdata.
     */
    public ByteaopslagAttribuut getBerichtdata() {
        return berichtdata;
    }

    /**
     * Retourneert Checksum van LO3 Bericht.
     *
     * @return Checksum.
     */
    public ChecksumAttribuut getChecksum() {
        return checksum;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * Zet Conversie van LO3 Bericht.
     *
     * @param conversie Conversie.
     */
    public void setConversie(final LO3BerichtConversieGroepModel conversie) {
        this.conversie = conversie;
    }

}
