/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
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
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3BerichtBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;

/**
 * Bericht, op LO3 achtige wijze opgebouwd.
 *
 * Naast berichten zoals voorkomend op het GBA-V netwerk (zoals Lg01 en La01 berichten), gaat het ook om berichten
 * vormgegeven op eenzelfde manier. In casu zijn dit 'berichten' gebaseerd op tabel 35, die op een soortgelijke manier
 * zijn vormgegeven, edoch niet op het GBA-V netwerk voorkwamen.
 *
 * 1. LO3 bericht is gemodelleerd naar aanleiding van de behoefte om 'berichten' te kunnen 'loggen', en hier meldingen
 * van vast te leggen. In eerste instantie was dit toegespitst op de berichten die ook op het GBA-V netwerk werden
 * uitgewisseld. Bij eerste conceptoplevering van het nieuwe model bleek de constructie ook gebruikt te worden voor het
 * kunnen loggen van (meldingen bij) de conversie van tabelregels uit 'tabel 35' (de autorisatietabel uit het LO3
 * stelsel). Besloten is de definitie van LO3 bericht ietwat op te rekken, zodat deze 'berichten' (die overigens wel op
 * eenzelfde manier zijn opgebouwd) ook onder deze definitie te laten vallen. Nadeel van deze keuze is dat het model net
 * iets minder fraai is; voordeel is dat er minder structuren nodig zijn.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3BerichtModel extends AbstractDynamischObject implements LO3BerichtBasis, ModelIdentificeerbaar<Long> {

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
    private PersoonModel persoon;

    @Embedded
    @AttributeOverride(name = ByteaopslagAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Berdata"))
    @JsonProperty
    private ByteaopslagAttribuut berichtdata;

    @Embedded
    @AttributeOverride(name = ChecksumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Checksum"))
    @JsonProperty
    private ChecksumAttribuut checksum;

    @Embedded
    @JsonProperty
    private LO3BerichtConversieGroepModel conversie;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractLO3BerichtModel() {
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
    public AbstractLO3BerichtModel(
        final JaNeeAttribuut indicatieBerichtsoortOnderdeelLO3Stelsel,
        final LO3ReferentieAttribuut referentie,
        final LO3BerichtenBronAttribuut bron,
        final AdministratienummerAttribuut administratienummer,
        final PersoonModel persoon,
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
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieBerichtsoortOnderdeelLO3Stelsel() {
        return indicatieBerichtsoortOnderdeelLO3Stelsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3ReferentieAttribuut getReferentie() {
        return referentie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3BerichtenBronAttribuut getBron() {
        return bron;
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
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteaopslagAttribuut getBerichtdata() {
        return berichtdata;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChecksumAttribuut getChecksum() {
        return checksum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3BerichtConversieGroepModel getConversie() {
        return conversie;
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

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (conversie != null) {
            groepen.add(conversie);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieBerichtsoortOnderdeelLO3Stelsel != null) {
            attributen.add(indicatieBerichtsoortOnderdeelLO3Stelsel);
        }
        if (referentie != null) {
            attributen.add(referentie);
        }
        if (bron != null) {
            attributen.add(bron);
        }
        if (administratienummer != null) {
            attributen.add(administratienummer);
        }
        if (berichtdata != null) {
            attributen.add(berichtdata);
        }
        if (checksum != null) {
            attributen.add(checksum);
        }
        return attributen;
    }

}
