/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ber.BerichtZoekcriteriaPersoonGroep;
import nl.bzk.brp.model.logisch.ber.BerichtZoekcriteriaPersoonGroepBasis;

/**
 * Zoekcriteria, waarmee een persoon - of meerdere personen - wordt gezocht.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtZoekcriteriaPersoonGroepModel implements BerichtZoekcriteriaPersoonGroepBasis {

    @Transient
    @JsonProperty
    private BurgerservicenummerAttribuut burgerservicenummer;

    @Transient
    @JsonProperty
    private AdministratienummerAttribuut administratienummer;

    @Transient
    @JsonProperty
    private SleutelwaardetekstAttribuut objectSleutel;

    @Transient
    @JsonProperty
    private GeslachtsnaamstamAttribuut geslachtsnaamstam;

    @Transient
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut geboortedatum;

    @Transient
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut geboortedatumKind;

    @Transient
    @JsonProperty
    private GemeenteCodeAttribuut gemeenteCode;

    @Transient
    @JsonProperty
    private NaamOpenbareRuimteAttribuut naamOpenbareRuimte;

    @Transient
    @JsonProperty
    private AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte;

    @Transient
    @JsonProperty
    private HuisnummerAttribuut huisnummer;

    @Transient
    @JsonProperty
    private HuisletterAttribuut huisletter;

    @Transient
    @JsonProperty
    private HuisnummertoevoegingAttribuut huisnummertoevoeging;

    @Transient
    @JsonProperty
    private PostcodeAttribuut postcode;

    @Transient
    @JsonProperty
    private IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBerichtZoekcriteriaPersoonGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param burgerservicenummer burgerservicenummer van Zoekcriteria persoon.
     * @param administratienummer administratienummer van Zoekcriteria persoon.
     * @param objectSleutel objectSleutel van Zoekcriteria persoon.
     * @param geslachtsnaamstam geslachtsnaamstam van Zoekcriteria persoon.
     * @param geboortedatum geboortedatum van Zoekcriteria persoon.
     * @param geboortedatumKind geboortedatumKind van Zoekcriteria persoon.
     * @param gemeenteCode gemeenteCode van Zoekcriteria persoon.
     * @param naamOpenbareRuimte naamOpenbareRuimte van Zoekcriteria persoon.
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte van Zoekcriteria persoon.
     * @param huisnummer huisnummer van Zoekcriteria persoon.
     * @param huisletter huisletter van Zoekcriteria persoon.
     * @param huisnummertoevoeging huisnummertoevoeging van Zoekcriteria persoon.
     * @param postcode postcode van Zoekcriteria persoon.
     * @param identificatiecodeNummeraanduiding identificatiecodeNummeraanduiding van Zoekcriteria persoon.
     */
    public AbstractBerichtZoekcriteriaPersoonGroepModel(
        final BurgerservicenummerAttribuut burgerservicenummer,
        final AdministratienummerAttribuut administratienummer,
        final SleutelwaardetekstAttribuut objectSleutel,
        final GeslachtsnaamstamAttribuut geslachtsnaamstam,
        final DatumEvtDeelsOnbekendAttribuut geboortedatum,
        final DatumEvtDeelsOnbekendAttribuut geboortedatumKind,
        final GemeenteCodeAttribuut gemeenteCode,
        final NaamOpenbareRuimteAttribuut naamOpenbareRuimte,
        final AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte,
        final HuisnummerAttribuut huisnummer,
        final HuisletterAttribuut huisletter,
        final HuisnummertoevoegingAttribuut huisnummertoevoeging,
        final PostcodeAttribuut postcode,
        final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.burgerservicenummer = burgerservicenummer;
        this.administratienummer = administratienummer;
        this.objectSleutel = objectSleutel;
        this.geslachtsnaamstam = geslachtsnaamstam;
        this.geboortedatum = geboortedatum;
        this.geboortedatumKind = geboortedatumKind;
        this.gemeenteCode = gemeenteCode;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.postcode = postcode;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtZoekcriteriaPersoonGroep te kopieren groep.
     */
    public AbstractBerichtZoekcriteriaPersoonGroepModel(final BerichtZoekcriteriaPersoonGroep berichtZoekcriteriaPersoonGroep) {
        this.burgerservicenummer = berichtZoekcriteriaPersoonGroep.getBurgerservicenummer();
        this.administratienummer = berichtZoekcriteriaPersoonGroep.getAdministratienummer();
        this.objectSleutel = berichtZoekcriteriaPersoonGroep.getObjectSleutel();
        this.geslachtsnaamstam = berichtZoekcriteriaPersoonGroep.getGeslachtsnaamstam();
        this.geboortedatum = berichtZoekcriteriaPersoonGroep.getGeboortedatum();
        this.geboortedatumKind = berichtZoekcriteriaPersoonGroep.getGeboortedatumKind();
        this.gemeenteCode = berichtZoekcriteriaPersoonGroep.getGemeenteCode();
        this.naamOpenbareRuimte = berichtZoekcriteriaPersoonGroep.getNaamOpenbareRuimte();
        this.afgekorteNaamOpenbareRuimte = berichtZoekcriteriaPersoonGroep.getAfgekorteNaamOpenbareRuimte();
        this.huisnummer = berichtZoekcriteriaPersoonGroep.getHuisnummer();
        this.huisletter = berichtZoekcriteriaPersoonGroep.getHuisletter();
        this.huisnummertoevoeging = berichtZoekcriteriaPersoonGroep.getHuisnummertoevoeging();
        this.postcode = berichtZoekcriteriaPersoonGroep.getPostcode();
        this.identificatiecodeNummeraanduiding = berichtZoekcriteriaPersoonGroep.getIdentificatiecodeNummeraanduiding();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BurgerservicenummerAttribuut getBurgerservicenummer() {
        return burgerservicenummer;
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
    public SleutelwaardetekstAttribuut getObjectSleutel() {
        return objectSleutel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeslachtsnaamstamAttribuut getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getGeboortedatumKind() {
        return geboortedatumKind;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteCodeAttribuut getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamOpenbareRuimteAttribuut getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AfgekorteNaamOpenbareRuimteAttribuut getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuisnummerAttribuut getHuisnummer() {
        return huisnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuisletterAttribuut getHuisletter() {
        return huisletter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HuisnummertoevoegingAttribuut getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostcodeAttribuut getPostcode() {
        return postcode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificatiecodeNummeraanduidingAttribuut getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (burgerservicenummer != null) {
            attributen.add(burgerservicenummer);
        }
        if (administratienummer != null) {
            attributen.add(administratienummer);
        }
        if (objectSleutel != null) {
            attributen.add(objectSleutel);
        }
        if (geslachtsnaamstam != null) {
            attributen.add(geslachtsnaamstam);
        }
        if (geboortedatum != null) {
            attributen.add(geboortedatum);
        }
        if (geboortedatumKind != null) {
            attributen.add(geboortedatumKind);
        }
        if (gemeenteCode != null) {
            attributen.add(gemeenteCode);
        }
        if (naamOpenbareRuimte != null) {
            attributen.add(naamOpenbareRuimte);
        }
        if (afgekorteNaamOpenbareRuimte != null) {
            attributen.add(afgekorteNaamOpenbareRuimte);
        }
        if (huisnummer != null) {
            attributen.add(huisnummer);
        }
        if (huisletter != null) {
            attributen.add(huisletter);
        }
        if (huisnummertoevoeging != null) {
            attributen.add(huisnummertoevoeging);
        }
        if (postcode != null) {
            attributen.add(postcode);
        }
        if (identificatiecodeNummeraanduiding != null) {
            attributen.add(identificatiecodeNummeraanduiding);
        }
        return attributen;
    }

}
