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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.EmailadresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TelefoonnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.TerugmeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;
import nl.bzk.brp.model.logisch.kern.TerugmeldingContactpersoonGroep;
import nl.bzk.brp.model.logisch.kern.TerugmeldingContactpersoonGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 11095)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisTerugmeldingContactpersoonModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        TerugmeldingContactpersoonGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_TERUGMELDINGCONTACTPERSOON", sequenceName = "Kern.seq_His_TerugmeldingContactpers")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_TERUGMELDINGCONTACTPERSOON")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TerugmeldingHisVolledigImpl.class)
    @JoinColumn(name = "Terugmelding")
    @JsonBackReference
    private TerugmeldingHisVolledig terugmelding;

    @Embedded
    @AttributeOverride(name = EmailadresAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Email"))
    @JsonProperty
    private EmailadresAttribuut email;

    @Embedded
    @AttributeOverride(name = TelefoonnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Telefoonnr"))
    @JsonProperty
    private TelefoonnummerAttribuut telefoonnummer;

    @Embedded
    @AttributeOverride(name = VoornamenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voornamen"))
    @JsonProperty
    private VoornamenAttribuut voornamen;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private VoorvoegselAttribuut voorvoegsel;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private ScheidingstekenAttribuut scheidingsteken;

    @Embedded
    @AttributeOverride(name = GeslachtsnaamstamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Geslnaamstam"))
    @JsonProperty
    private GeslachtsnaamstamAttribuut geslachtsnaamstam;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisTerugmeldingContactpersoonModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param terugmelding terugmelding van HisTerugmeldingContactpersoonModel
     * @param email email van HisTerugmeldingContactpersoonModel
     * @param telefoonnummer telefoonnummer van HisTerugmeldingContactpersoonModel
     * @param voornamen voornamen van HisTerugmeldingContactpersoonModel
     * @param voorvoegsel voorvoegsel van HisTerugmeldingContactpersoonModel
     * @param scheidingsteken scheidingsteken van HisTerugmeldingContactpersoonModel
     * @param geslachtsnaamstam geslachtsnaamstam van HisTerugmeldingContactpersoonModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisTerugmeldingContactpersoonModel(
        final TerugmeldingHisVolledig terugmelding,
        final EmailadresAttribuut email,
        final TelefoonnummerAttribuut telefoonnummer,
        final VoornamenAttribuut voornamen,
        final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.terugmelding = terugmelding;
        this.email = email;
        this.telefoonnummer = telefoonnummer;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.geslachtsnaamstam = geslachtsnaamstam;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisTerugmeldingContactpersoonModel(final AbstractHisTerugmeldingContactpersoonModel kopie) {
        super(kopie);
        terugmelding = kopie.getTerugmelding();
        email = kopie.getEmail();
        telefoonnummer = kopie.getTelefoonnummer();
        voornamen = kopie.getVoornamen();
        voorvoegsel = kopie.getVoorvoegsel();
        scheidingsteken = kopie.getScheidingsteken();
        geslachtsnaamstam = kopie.getGeslachtsnaamstam();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param terugmeldingHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisTerugmeldingContactpersoonModel(
        final TerugmeldingHisVolledig terugmeldingHisVolledig,
        final TerugmeldingContactpersoonGroep groep,
        final ActieModel actieInhoud)
    {
        this.terugmelding = terugmeldingHisVolledig;
        this.email = groep.getEmail();
        this.telefoonnummer = groep.getTelefoonnummer();
        this.voornamen = groep.getVoornamen();
        this.voorvoegsel = groep.getVoorvoegsel();
        this.scheidingsteken = groep.getScheidingsteken();
        this.geslachtsnaamstam = groep.getGeslachtsnaamstam();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Terugmelding Contactpersoon.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Terugmelding van His Terugmelding Contactpersoon.
     *
     * @return Terugmelding.
     */
    public TerugmeldingHisVolledig getTerugmelding() {
        return terugmelding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11164)
    public EmailadresAttribuut getEmail() {
        return email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11165)
    public TelefoonnummerAttribuut getTelefoonnummer() {
        return telefoonnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11166)
    public VoornamenAttribuut getVoornamen() {
        return voornamen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11167)
    public VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11168)
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 11169)
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (email != null) {
            attributen.add(email);
        }
        if (telefoonnummer != null) {
            attributen.add(telefoonnummer);
        }
        if (voornamen != null) {
            attributen.add(voornamen);
        }
        if (voorvoegsel != null) {
            attributen.add(voorvoegsel);
        }
        if (scheidingsteken != null) {
            attributen.add(scheidingsteken);
        }
        if (geslachtsnaamstam != null) {
            attributen.add(geslachtsnaamstam);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.TERUGMELDING_CONTACTPERSOON;
    }

}
