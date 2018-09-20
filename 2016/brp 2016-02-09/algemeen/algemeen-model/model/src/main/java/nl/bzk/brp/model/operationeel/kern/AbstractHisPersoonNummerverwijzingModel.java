/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonNummerverwijzingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNummerverwijzingGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 10900)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonNummerverwijzingModel extends AbstractMaterieelHistorischMetActieVerantwoording implements
        PersoonNummerverwijzingGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONNUMMERVERWIJZING", sequenceName = "Kern.seq_His_PersNrverwijzing")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONNUMMERVERWIJZING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AttributeOverride(name = BurgerservicenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VorigeBSN"))
    @JsonProperty
    private BurgerservicenummerAttribuut vorigeBurgerservicenummer;

    @Embedded
    @AttributeOverride(name = BurgerservicenummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VolgendeBSN"))
    @JsonProperty
    private BurgerservicenummerAttribuut volgendeBurgerservicenummer;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VorigeANr"))
    @JsonProperty
    private AdministratienummerAttribuut vorigeAdministratienummer;

    @Embedded
    @AttributeOverride(name = AdministratienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VolgendeANr"))
    @JsonProperty
    private AdministratienummerAttribuut volgendeAdministratienummer;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonNummerverwijzingModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoon persoon van HisPersoonNummerverwijzingModel
     * @param vorigeBurgerservicenummer vorigeBurgerservicenummer van HisPersoonNummerverwijzingModel
     * @param volgendeBurgerservicenummer volgendeBurgerservicenummer van HisPersoonNummerverwijzingModel
     * @param vorigeAdministratienummer vorigeAdministratienummer van HisPersoonNummerverwijzingModel
     * @param volgendeAdministratienummer volgendeAdministratienummer van HisPersoonNummerverwijzingModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisPersoonNummerverwijzingModel(
        final PersoonHisVolledig persoon,
        final BurgerservicenummerAttribuut vorigeBurgerservicenummer,
        final BurgerservicenummerAttribuut volgendeBurgerservicenummer,
        final AdministratienummerAttribuut vorigeAdministratienummer,
        final AdministratienummerAttribuut volgendeAdministratienummer,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        this.persoon = persoon;
        this.vorigeBurgerservicenummer = vorigeBurgerservicenummer;
        this.volgendeBurgerservicenummer = volgendeBurgerservicenummer;
        this.vorigeAdministratienummer = vorigeAdministratienummer;
        this.volgendeAdministratienummer = volgendeAdministratienummer;
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonNummerverwijzingModel(final AbstractHisPersoonNummerverwijzingModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        vorigeBurgerservicenummer = kopie.getVorigeBurgerservicenummer();
        volgendeBurgerservicenummer = kopie.getVolgendeBurgerservicenummer();
        vorigeAdministratienummer = kopie.getVorigeAdministratienummer();
        volgendeAdministratienummer = kopie.getVolgendeAdministratienummer();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonNummerverwijzingModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonNummerverwijzingGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.vorigeBurgerservicenummer = groep.getVorigeBurgerservicenummer();
        this.volgendeBurgerservicenummer = groep.getVolgendeBurgerservicenummer();
        this.vorigeAdministratienummer = groep.getVorigeAdministratienummer();
        this.volgendeAdministratienummer = groep.getVolgendeAdministratienummer();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Nummerverwijzing.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Nummerverwijzing.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10941)
    public BurgerservicenummerAttribuut getVorigeBurgerservicenummer() {
        return vorigeBurgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10942)
    public BurgerservicenummerAttribuut getVolgendeBurgerservicenummer() {
        return volgendeBurgerservicenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10943)
    public AdministratienummerAttribuut getVorigeAdministratienummer() {
        return vorigeAdministratienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10944)
    public AdministratienummerAttribuut getVolgendeAdministratienummer() {
        return volgendeAdministratienummer;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (vorigeBurgerservicenummer != null) {
            attributen.add(vorigeBurgerservicenummer);
        }
        if (volgendeBurgerservicenummer != null) {
            attributen.add(volgendeBurgerservicenummer);
        }
        if (vorigeAdministratienummer != null) {
            attributen.add(vorigeAdministratienummer);
        }
        if (volgendeAdministratienummer != null) {
            attributen.add(volgendeAdministratienummer);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonNummerverwijzingModel kopieer() {
        return new HisPersoonNummerverwijzingModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_NUMMERVERWIJZING;
    }

}
