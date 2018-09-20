/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeentedeelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroepBasis;

/**
 * Voor de modellering van buitenlands adres waren enkele opties: - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel: Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht) RNI heeft een actie gestart om te kijken of
 * binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet lukken. (Voorlopig) nog geen optie. - Adres
 * per regel opnemen. - Adresregels in een aparte tabel. Is ook mogelijk mits aantal regels beperkt wordt. Uiteindelijk
 * is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels geplaatst kunnen
 * worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het maximale aantal
 * karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI inzake de maximale
 * grootte van internationale adressen.
 *
 * 2. De groep standaard is NIET verplicht: in geval van VOW wordt een actueel adres beëindigd, en is er dus géén sprake
 * van een 'actueel' record in de C/D laag.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonAdresStandaardGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep,
        PersoonAdresStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 6063;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(
        3263,
        3715,
        3301,
        3730,
        3284,
        3286,
        3788,
        3269,
        3267,
        3265,
        3271,
        3273,
        3275,
        3281,
        3282,
        3278,
        3288,
        3291,
        3292,
        3293,
        3709,
        3710,
        3711,
        3289,
        9540);
    private FunctieAdresAttribuut soort;
    private String redenWijzigingCode;
    private RedenWijzigingVerblijfAttribuut redenWijziging;
    private String aangeverAdreshoudingCode;
    private AangeverAttribuut aangeverAdreshouding;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangAdreshouding;
    private IdentificatiecodeAdresseerbaarObjectAttribuut identificatiecodeAdresseerbaarObject;
    private IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding;
    private String gemeenteCode;
    private GemeenteAttribuut gemeente;
    private NaamOpenbareRuimteAttribuut naamOpenbareRuimte;
    private AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte;
    private GemeentedeelAttribuut gemeentedeel;
    private HuisnummerAttribuut huisnummer;
    private HuisletterAttribuut huisletter;
    private HuisnummertoevoegingAttribuut huisnummertoevoeging;
    private PostcodeAttribuut postcode;
    private NaamEnumeratiewaardeAttribuut woonplaatsnaam;
    private LocatieTenOpzichteVanAdresAttribuut locatieTenOpzichteVanAdres;
    private LocatieomschrijvingAttribuut locatieomschrijving;
    private AdresregelAttribuut buitenlandsAdresRegel1;
    private AdresregelAttribuut buitenlandsAdresRegel2;
    private AdresregelAttribuut buitenlandsAdresRegel3;
    private AdresregelAttribuut buitenlandsAdresRegel4;
    private AdresregelAttribuut buitenlandsAdresRegel5;
    private AdresregelAttribuut buitenlandsAdresRegel6;
    private String landGebiedCode;
    private LandGebiedAttribuut landGebied;
    private NeeAttribuut indicatiePersoonAangetroffenOpAdres;

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctieAdresAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Reden wijziging van Standaard.
     *
     * @return Reden wijziging.
     */
    public String getRedenWijzigingCode() {
        return redenWijzigingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingVerblijfAttribuut getRedenWijziging() {
        return redenWijziging;
    }

    /**
     * Retourneert Aangever adreshouding van Standaard.
     *
     * @return Aangever adreshouding.
     */
    public String getAangeverAdreshoudingCode() {
        return aangeverAdreshoudingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAttribuut getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificatiecodeAdresseerbaarObjectAttribuut getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificatiecodeNummeraanduidingAttribuut getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Retourneert Gemeente van Standaard.
     *
     * @return Gemeente.
     */
    public String getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GemeenteAttribuut getGemeente() {
        return gemeente;
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
    public GemeentedeelAttribuut getGemeentedeel() {
        return gemeentedeel;
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
    public NaamEnumeratiewaardeAttribuut getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieTenOpzichteVanAdresAttribuut getLocatieTenOpzichteVanAdres() {
        return locatieTenOpzichteVanAdres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieomschrijvingAttribuut getLocatieomschrijving() {
        return locatieomschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * Retourneert Land/gebied van Standaard.
     *
     * @return Land/gebied.
     */
    public String getLandGebiedCode() {
        return landGebiedCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LandGebiedAttribuut getLandGebied() {
        return landGebied;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NeeAttribuut getIndicatiePersoonAangetroffenOpAdres() {
        return indicatiePersoonAangetroffenOpAdres;
    }

    /**
     * Zet Soort van Standaard.
     *
     * @param soort Soort.
     */
    public void setSoort(final FunctieAdresAttribuut soort) {
        this.soort = soort;
    }

    /**
     * Zet Reden wijziging van Standaard.
     *
     * @param redenWijzigingCode Reden wijziging.
     */
    public void setRedenWijzigingCode(final String redenWijzigingCode) {
        this.redenWijzigingCode = redenWijzigingCode;
    }

    /**
     * Zet Reden wijziging van Standaard.
     *
     * @param redenWijziging Reden wijziging.
     */
    public void setRedenWijziging(final RedenWijzigingVerblijfAttribuut redenWijziging) {
        this.redenWijziging = redenWijziging;
    }

    /**
     * Zet Aangever adreshouding van Standaard.
     *
     * @param aangeverAdreshoudingCode Aangever adreshouding.
     */
    public void setAangeverAdreshoudingCode(final String aangeverAdreshoudingCode) {
        this.aangeverAdreshoudingCode = aangeverAdreshoudingCode;
    }

    /**
     * Zet Aangever adreshouding van Standaard.
     *
     * @param aangeverAdreshouding Aangever adreshouding.
     */
    public void setAangeverAdreshouding(final AangeverAttribuut aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    /**
     * Zet Datum aanvang adreshouding van Standaard.
     *
     * @param datumAanvangAdreshouding Datum aanvang adreshouding.
     */
    public void setDatumAanvangAdreshouding(final DatumEvtDeelsOnbekendAttribuut datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    /**
     * Zet Identificatiecode adresseerbaar object van Standaard.
     *
     * @param identificatiecodeAdresseerbaarObject Identificatiecode adresseerbaar object.
     */
    public void setIdentificatiecodeAdresseerbaarObject(final IdentificatiecodeAdresseerbaarObjectAttribuut identificatiecodeAdresseerbaarObject) {
        this.identificatiecodeAdresseerbaarObject = identificatiecodeAdresseerbaarObject;
    }

    /**
     * Zet Identificatiecode nummeraanduiding van Standaard.
     *
     * @param identificatiecodeNummeraanduiding Identificatiecode nummeraanduiding.
     */
    public void setIdentificatiecodeNummeraanduiding(final IdentificatiecodeNummeraanduidingAttribuut identificatiecodeNummeraanduiding) {
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    /**
     * Zet Gemeente van Standaard.
     *
     * @param gemeenteCode Gemeente.
     */
    public void setGemeenteCode(final String gemeenteCode) {
        this.gemeenteCode = gemeenteCode;
    }

    /**
     * Zet Gemeente van Standaard.
     *
     * @param gemeente Gemeente.
     */
    public void setGemeente(final GemeenteAttribuut gemeente) {
        this.gemeente = gemeente;
    }

    /**
     * Zet Naam openbare ruimte van Standaard.
     *
     * @param naamOpenbareRuimte Naam openbare ruimte.
     */
    public void setNaamOpenbareRuimte(final NaamOpenbareRuimteAttribuut naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    /**
     * Zet Afgekorte naam openbare ruimte van Standaard.
     *
     * @param afgekorteNaamOpenbareRuimte Afgekorte naam openbare ruimte.
     */
    public void setAfgekorteNaamOpenbareRuimte(final AfgekorteNaamOpenbareRuimteAttribuut afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    /**
     * Zet Gemeentedeel van Standaard.
     *
     * @param gemeentedeel Gemeentedeel.
     */
    public void setGemeentedeel(final GemeentedeelAttribuut gemeentedeel) {
        this.gemeentedeel = gemeentedeel;
    }

    /**
     * Zet Huisnummer van Standaard.
     *
     * @param huisnummer Huisnummer.
     */
    public void setHuisnummer(final HuisnummerAttribuut huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Zet Huisletter van Standaard.
     *
     * @param huisletter Huisletter.
     */
    public void setHuisletter(final HuisletterAttribuut huisletter) {
        this.huisletter = huisletter;
    }

    /**
     * Zet Huisnummertoevoeging van Standaard.
     *
     * @param huisnummertoevoeging Huisnummertoevoeging.
     */
    public void setHuisnummertoevoeging(final HuisnummertoevoegingAttribuut huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    /**
     * Zet Postcode van Standaard.
     *
     * @param postcode Postcode.
     */
    public void setPostcode(final PostcodeAttribuut postcode) {
        this.postcode = postcode;
    }

    /**
     * Zet Woonplaatsnaam van Standaard.
     *
     * @param woonplaatsnaam Woonplaatsnaam.
     */
    public void setWoonplaatsnaam(final NaamEnumeratiewaardeAttribuut woonplaatsnaam) {
        this.woonplaatsnaam = woonplaatsnaam;
    }

    /**
     * Zet Locatie ten opzichte van adres van Standaard.
     *
     * @param locatieTenOpzichteVanAdres Locatie ten opzichte van adres.
     */
    public void setLocatieTenOpzichteVanAdres(final LocatieTenOpzichteVanAdresAttribuut locatieTenOpzichteVanAdres) {
        this.locatieTenOpzichteVanAdres = locatieTenOpzichteVanAdres;
    }

    /**
     * Zet Locatieomschrijving van Standaard.
     *
     * @param locatieomschrijving Locatieomschrijving.
     */
    public void setLocatieomschrijving(final LocatieomschrijvingAttribuut locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    /**
     * Zet Buitenlands adres regel 1 van Standaard.
     *
     * @param buitenlandsAdresRegel1 Buitenlands adres regel 1.
     */
    public void setBuitenlandsAdresRegel1(final AdresregelAttribuut buitenlandsAdresRegel1) {
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    /**
     * Zet Buitenlands adres regel 2 van Standaard.
     *
     * @param buitenlandsAdresRegel2 Buitenlands adres regel 2.
     */
    public void setBuitenlandsAdresRegel2(final AdresregelAttribuut buitenlandsAdresRegel2) {
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    /**
     * Zet Buitenlands adres regel 3 van Standaard.
     *
     * @param buitenlandsAdresRegel3 Buitenlands adres regel 3.
     */
    public void setBuitenlandsAdresRegel3(final AdresregelAttribuut buitenlandsAdresRegel3) {
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    /**
     * Zet Buitenlands adres regel 4 van Standaard.
     *
     * @param buitenlandsAdresRegel4 Buitenlands adres regel 4.
     */
    public void setBuitenlandsAdresRegel4(final AdresregelAttribuut buitenlandsAdresRegel4) {
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    /**
     * Zet Buitenlands adres regel 5 van Standaard.
     *
     * @param buitenlandsAdresRegel5 Buitenlands adres regel 5.
     */
    public void setBuitenlandsAdresRegel5(final AdresregelAttribuut buitenlandsAdresRegel5) {
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    /**
     * Zet Buitenlands adres regel 6 van Standaard.
     *
     * @param buitenlandsAdresRegel6 Buitenlands adres regel 6.
     */
    public void setBuitenlandsAdresRegel6(final AdresregelAttribuut buitenlandsAdresRegel6) {
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    /**
     * Zet Land/gebied van Standaard.
     *
     * @param landGebiedCode Land/gebied.
     */
    public void setLandGebiedCode(final String landGebiedCode) {
        this.landGebiedCode = landGebiedCode;
    }

    /**
     * Zet Land/gebied van Standaard.
     *
     * @param landGebied Land/gebied.
     */
    public void setLandGebied(final LandGebiedAttribuut landGebied) {
        this.landGebied = landGebied;
    }

    /**
     * Zet Persoon aangetroffen op adres? van Standaard.
     *
     * @param indicatiePersoonAangetroffenOpAdres Persoon aangetroffen op adres?.
     */
    public void setIndicatiePersoonAangetroffenOpAdres(final NeeAttribuut indicatiePersoonAangetroffenOpAdres) {
        this.indicatiePersoonAangetroffenOpAdres = indicatiePersoonAangetroffenOpAdres;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (soort != null) {
            attributen.add(soort);
        }
        if (redenWijziging != null) {
            attributen.add(redenWijziging);
        }
        if (aangeverAdreshouding != null) {
            attributen.add(aangeverAdreshouding);
        }
        if (datumAanvangAdreshouding != null) {
            attributen.add(datumAanvangAdreshouding);
        }
        if (identificatiecodeAdresseerbaarObject != null) {
            attributen.add(identificatiecodeAdresseerbaarObject);
        }
        if (identificatiecodeNummeraanduiding != null) {
            attributen.add(identificatiecodeNummeraanduiding);
        }
        if (gemeente != null) {
            attributen.add(gemeente);
        }
        if (naamOpenbareRuimte != null) {
            attributen.add(naamOpenbareRuimte);
        }
        if (afgekorteNaamOpenbareRuimte != null) {
            attributen.add(afgekorteNaamOpenbareRuimte);
        }
        if (gemeentedeel != null) {
            attributen.add(gemeentedeel);
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
        if (woonplaatsnaam != null) {
            attributen.add(woonplaatsnaam);
        }
        if (locatieTenOpzichteVanAdres != null) {
            attributen.add(locatieTenOpzichteVanAdres);
        }
        if (locatieomschrijving != null) {
            attributen.add(locatieomschrijving);
        }
        if (buitenlandsAdresRegel1 != null) {
            attributen.add(buitenlandsAdresRegel1);
        }
        if (buitenlandsAdresRegel2 != null) {
            attributen.add(buitenlandsAdresRegel2);
        }
        if (buitenlandsAdresRegel3 != null) {
            attributen.add(buitenlandsAdresRegel3);
        }
        if (buitenlandsAdresRegel4 != null) {
            attributen.add(buitenlandsAdresRegel4);
        }
        if (buitenlandsAdresRegel5 != null) {
            attributen.add(buitenlandsAdresRegel5);
        }
        if (buitenlandsAdresRegel6 != null) {
            attributen.add(buitenlandsAdresRegel6);
        }
        if (landGebied != null) {
            attributen.add(landGebied);
        }
        if (indicatiePersoonAangetroffenOpAdres != null) {
            attributen.add(indicatiePersoonAangetroffenOpAdres);
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
