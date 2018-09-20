/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
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
import nl.bzk.brp.model.basis.AbstractBerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.BerichtZoekcriteriaPersoonGroepBasis;

/**
 * Zoekcriteria, waarmee een persoon - of meerdere personen - wordt gezocht.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBerichtZoekcriteriaPersoonGroepBericht extends AbstractBerichtIdentificeerbaar implements Groep,
        BerichtZoekcriteriaPersoonGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 11255;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(
        9840,
        9841,
        9842,
        10415,
        9843,
        9844,
        9845,
        9846,
        11420,
        9847,
        9849,
        9850,
        9848,
        11421);
    private BurgerservicenummerAttribuut burgerservicenummer;
    private AdministratienummerAttribuut administratienummer;
    private SleutelwaardetekstAttribuut objectSleutel;
    private GeslachtsnaamstamAttribuut geslachtsnaamstam;
    private DatumEvtDeelsOnbekendAttribuut geboortedatum;
    private DatumEvtDeelsOnbekendAttribuut geboortedatumKind;
    private GemeenteCodeAttribuut gemeenteCode;
    private NaamOpenbareRuimteAttribuut naamOpenbareRuimte;
    private AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte;
    private HuisnummerAttribuut huisnummer;
    private HuisletterAttribuut huisletter;
    private HuisnummertoevoegingAttribuut huisnummertoevoeging;
    private PostcodeAttribuut postcode;
    private IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding;

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
     * Zet Burgerservicenummer van Zoekcriteria persoon.
     *
     * @param burgerservicenummer Burgerservicenummer.
     */
    public void setBurgerservicenummer(final BurgerservicenummerAttribuut burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    /**
     * Zet Administratienummer van Zoekcriteria persoon.
     *
     * @param administratienummer Administratienummer.
     */
    public void setAdministratienummer(final AdministratienummerAttribuut administratienummer) {
        this.administratienummer = administratienummer;
    }

    /**
     * Zet Object sleutel van Zoekcriteria persoon.
     *
     * @param objectSleutel Object sleutel.
     */
    public void setObjectSleutel(final SleutelwaardetekstAttribuut objectSleutel) {
        this.objectSleutel = objectSleutel;
    }

    /**
     * Zet Geslachtsnaamstam van Zoekcriteria persoon.
     *
     * @param geslachtsnaamstam Geslachtsnaamstam.
     */
    public void setGeslachtsnaamstam(final GeslachtsnaamstamAttribuut geslachtsnaamstam) {
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Zet Geboortedatum van Zoekcriteria persoon.
     *
     * @param geboortedatum Geboortedatum.
     */
    public void setGeboortedatum(final DatumEvtDeelsOnbekendAttribuut geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    /**
     * Zet Geboortedatum kind van Zoekcriteria persoon.
     *
     * @param geboortedatumKind Geboortedatum kind.
     */
    public void setGeboortedatumKind(final DatumEvtDeelsOnbekendAttribuut geboortedatumKind) {
        this.geboortedatumKind = geboortedatumKind;
    }

    /**
     * Zet Gemeente code van Zoekcriteria persoon.
     *
     * @param gemeenteCode Gemeente code.
     */
    public void setGemeenteCode(final GemeenteCodeAttribuut gemeenteCode) {
        this.gemeenteCode = gemeenteCode;
    }

    /**
     * Zet Naam openbare ruimte van Zoekcriteria persoon.
     *
     * @param naamOpenbareRuimte Naam openbare ruimte.
     */
    public void setNaamOpenbareRuimte(final NaamOpenbareRuimteAttribuut naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    /**
     * Zet Afgekorte naam openbare ruimte van Zoekcriteria persoon.
     *
     * @param afgekorteNaamOpenbareRuimte Afgekorte naam openbare ruimte.
     */
    public void setAfgekorteNaamOpenbareRuimte(final AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    /**
     * Zet Huisnummer van Zoekcriteria persoon.
     *
     * @param huisnummer Huisnummer.
     */
    public void setHuisnummer(final HuisnummerAttribuut huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Zet Huisletter van Zoekcriteria persoon.
     *
     * @param huisletter Huisletter.
     */
    public void setHuisletter(final HuisletterAttribuut huisletter) {
        this.huisletter = huisletter;
    }

    /**
     * Zet Huisnummertoevoeging van Zoekcriteria persoon.
     *
     * @param huisnummertoevoeging Huisnummertoevoeging.
     */
    public void setHuisnummertoevoeging(final HuisnummertoevoegingAttribuut huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    /**
     * Zet Postcode van Zoekcriteria persoon.
     *
     * @param postcode Postcode.
     */
    public void setPostcode(final PostcodeAttribuut postcode) {
        this.postcode = postcode;
    }

    /**
     * Zet Identificatiecode nummeraanduiding van Zoekcriteria persoon.
     *
     * @param identificatiecodeNummeraanduiding Identificatiecode nummeraanduiding.
     */
    public void setIdentificatiecodeNummeraanduiding(final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding) {
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
