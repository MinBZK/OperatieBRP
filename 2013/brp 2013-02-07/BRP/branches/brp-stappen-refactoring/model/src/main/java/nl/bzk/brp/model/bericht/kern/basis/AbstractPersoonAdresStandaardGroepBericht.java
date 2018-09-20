/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Adresregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAdresStandaardGroepBasis;


/**
 * Voor de modellering van buitenlands adres waren enkele opties:
 * - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel:
 * Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht)
 * RNI heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet
 * lukken. (Voorlopig) nog geen optie.
 * - Adres per regel opnemen.
 * - Adresregels in een aparte tabel.
 * Is ook mogelijk mits aantal regels beperkt wordt.
 * Uiteindelijk is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels
 * geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het
 * maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen.
 * RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractPersoonAdresStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonAdresStandaardGroepBasis
{

    private FunctieAdres                      soort;
    private String                            redenWijzigingCode;
    private RedenWijzigingAdres               redenWijziging;
    private String                            aangeverAdreshoudingCode;
    private AangeverAdreshouding              aangeverAdreshouding;
    private Datum                             datumAanvangAdreshouding;
    private AanduidingAdresseerbaarObject     adresseerbaarObject;
    private IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding;
    private String                            gemeenteCode;
    private Partij                            gemeente;
    private NaamOpenbareRuimte                naamOpenbareRuimte;
    private AfgekorteNaamOpenbareRuimte       afgekorteNaamOpenbareRuimte;
    private Gemeentedeel                      gemeentedeel;
    private Huisnummer                        huisnummer;
    private Huisletter                        huisletter;
    private Huisnummertoevoeging              huisnummertoevoeging;
    private Postcode                          postcode;
    private String                            woonplaatsCode;
    private Plaats                            woonplaats;
    private AanduidingBijHuisnummer           locatietovAdres;
    private LocatieOmschrijving               locatieOmschrijving;
    private Datum                             datumVertrekUitNederland;
    private Adresregel                        buitenlandsAdresRegel1;
    private Adresregel                        buitenlandsAdresRegel2;
    private Adresregel                        buitenlandsAdresRegel3;
    private Adresregel                        buitenlandsAdresRegel4;
    private Adresregel                        buitenlandsAdresRegel5;
    private Adresregel                        buitenlandsAdresRegel6;
    private String                            landCode;
    private Land                              land;
    private JaNee                             indicatiePersoonNietAangetroffenOpAdres;

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctieAdres getSoort() {
        return soort;
    }

    /**
     *
     *
     * @return
     */
    public String getRedenWijzigingCode() {
        return redenWijzigingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingAdres getRedenWijziging() {
        return redenWijziging;
    }

    /**
     *
     *
     * @return
     */
    public String getAangeverAdreshoudingCode() {
        return aangeverAdreshoudingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAdreshouding getAangeverAdreshouding() {
        return aangeverAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingAdresseerbaarObject getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentificatiecodeNummeraanduiding getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     *
     *
     * @return
     */
    public String getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeente() {
        return gemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NaamOpenbareRuimte getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AfgekorteNaamOpenbareRuimte getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gemeentedeel getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Huisnummer getHuisnummer() {
        return huisnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Huisletter getHuisletter() {
        return huisletter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Huisnummertoevoeging getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Postcode getPostcode() {
        return postcode;
    }

    /**
     *
     *
     * @return
     */
    public String getWoonplaatsCode() {
        return woonplaatsCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaats() {
        return woonplaats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingBijHuisnummer getLocatietovAdres() {
        return locatietovAdres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocatieOmschrijving getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Adresregel getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     *
     *
     * @return
     */
    public String getLandCode() {
        return landCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLand() {
        return land;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatiePersoonNietAangetroffenOpAdres() {
        return indicatiePersoonNietAangetroffenOpAdres;
    }

    /**
     * Zet Soort van Standaard.
     *
     * @param soort Soort.
     */
    public void setSoort(final FunctieAdres soort) {
        this.soort = soort;
    }

    /**
     *
     *
     * @param redenWijzigingCode
     */
    public void setRedenWijzigingCode(final String redenWijzigingCode) {
        this.redenWijzigingCode = redenWijzigingCode;
    }

    /**
     * Zet Reden wijziging van Standaard.
     *
     * @param redenWijziging Reden wijziging.
     */
    public void setRedenWijziging(final RedenWijzigingAdres redenWijziging) {
        this.redenWijziging = redenWijziging;
    }

    /**
     *
     *
     * @param aangeverAdreshoudingCode
     */
    public void setAangeverAdreshoudingCode(final String aangeverAdreshoudingCode) {
        this.aangeverAdreshoudingCode = aangeverAdreshoudingCode;
    }

    /**
     * Zet Aangever adreshouding van Standaard.
     *
     * @param aangeverAdreshouding Aangever adreshouding.
     */
    public void setAangeverAdreshouding(final AangeverAdreshouding aangeverAdreshouding) {
        this.aangeverAdreshouding = aangeverAdreshouding;
    }

    /**
     * Zet Datum aanvang adreshouding van Standaard.
     *
     * @param datumAanvangAdreshouding Datum aanvang adreshouding.
     */
    public void setDatumAanvangAdreshouding(final Datum datumAanvangAdreshouding) {
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
    }

    /**
     * Zet Adresseerbaar object van Standaard.
     *
     * @param adresseerbaarObject Adresseerbaar object.
     */
    public void setAdresseerbaarObject(final AanduidingAdresseerbaarObject adresseerbaarObject) {
        this.adresseerbaarObject = adresseerbaarObject;
    }

    /**
     * Zet Identificatiecode nummeraanduiding van Standaard.
     *
     * @param identificatiecodeNummeraanduiding Identificatiecode nummeraanduiding.
     */
    public void setIdentificatiecodeNummeraanduiding(
            final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding)
    {
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
    }

    /**
     *
     *
     * @param gemeenteCode
     */
    public void setGemeenteCode(final String gemeenteCode) {
        this.gemeenteCode = gemeenteCode;
    }

    /**
     * Zet Gemeente van Standaard.
     *
     * @param gemeente Gemeente.
     */
    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
    }

    /**
     * Zet Naam openbare ruimte van Standaard.
     *
     * @param naamOpenbareRuimte Naam openbare ruimte.
     */
    public void setNaamOpenbareRuimte(final NaamOpenbareRuimte naamOpenbareRuimte) {
        this.naamOpenbareRuimte = naamOpenbareRuimte;
    }

    /**
     * Zet Afgekorte Naam Openbare Ruimte van Standaard.
     *
     * @param afgekorteNaamOpenbareRuimte Afgekorte Naam Openbare Ruimte.
     */
    public void setAfgekorteNaamOpenbareRuimte(final AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte) {
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
    }

    /**
     * Zet Gemeentedeel van Standaard.
     *
     * @param gemeentedeel Gemeentedeel.
     */
    public void setGemeentedeel(final Gemeentedeel gemeentedeel) {
        this.gemeentedeel = gemeentedeel;
    }

    /**
     * Zet Huisnummer van Standaard.
     *
     * @param huisnummer Huisnummer.
     */
    public void setHuisnummer(final Huisnummer huisnummer) {
        this.huisnummer = huisnummer;
    }

    /**
     * Zet Huisletter van Standaard.
     *
     * @param huisletter Huisletter.
     */
    public void setHuisletter(final Huisletter huisletter) {
        this.huisletter = huisletter;
    }

    /**
     * Zet Huisnummertoevoeging van Standaard.
     *
     * @param huisnummertoevoeging Huisnummertoevoeging.
     */
    public void setHuisnummertoevoeging(final Huisnummertoevoeging huisnummertoevoeging) {
        this.huisnummertoevoeging = huisnummertoevoeging;
    }

    /**
     * Zet Postcode van Standaard.
     *
     * @param postcode Postcode.
     */
    public void setPostcode(final Postcode postcode) {
        this.postcode = postcode;
    }

    /**
     *
     *
     * @param woonplaatsCode
     */
    public void setWoonplaatsCode(final String woonplaatsCode) {
        this.woonplaatsCode = woonplaatsCode;
    }

    /**
     * Zet Woonplaats van Standaard.
     *
     * @param woonplaats Woonplaats.
     */
    public void setWoonplaats(final Plaats woonplaats) {
        this.woonplaats = woonplaats;
    }

    /**
     * Zet Locatie t.o.v. adres van Standaard.
     *
     * @param locatietovAdres Locatie t.o.v. adres.
     */
    public void setLocatietovAdres(final AanduidingBijHuisnummer locatietovAdres) {
        this.locatietovAdres = locatietovAdres;
    }

    /**
     * Zet Locatie omschrijving van Standaard.
     *
     * @param locatieOmschrijving Locatie omschrijving.
     */
    public void setLocatieOmschrijving(final LocatieOmschrijving locatieOmschrijving) {
        this.locatieOmschrijving = locatieOmschrijving;
    }

    /**
     * Zet Datum vertrek uit Nederland van Standaard.
     *
     * @param datumVertrekUitNederland Datum vertrek uit Nederland.
     */
    public void setDatumVertrekUitNederland(final Datum datumVertrekUitNederland) {
        this.datumVertrekUitNederland = datumVertrekUitNederland;
    }

    /**
     * Zet Buitenlands adres regel 1 van Standaard.
     *
     * @param buitenlandsAdresRegel1 Buitenlands adres regel 1.
     */
    public void setBuitenlandsAdresRegel1(final Adresregel buitenlandsAdresRegel1) {
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
    }

    /**
     * Zet Buitenlands adres regel 2 van Standaard.
     *
     * @param buitenlandsAdresRegel2 Buitenlands adres regel 2.
     */
    public void setBuitenlandsAdresRegel2(final Adresregel buitenlandsAdresRegel2) {
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
    }

    /**
     * Zet Buitenlands adres regel 3 van Standaard.
     *
     * @param buitenlandsAdresRegel3 Buitenlands adres regel 3.
     */
    public void setBuitenlandsAdresRegel3(final Adresregel buitenlandsAdresRegel3) {
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
    }

    /**
     * Zet Buitenlands adres regel 4 van Standaard.
     *
     * @param buitenlandsAdresRegel4 Buitenlands adres regel 4.
     */
    public void setBuitenlandsAdresRegel4(final Adresregel buitenlandsAdresRegel4) {
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
    }

    /**
     * Zet Buitenlands adres regel 5 van Standaard.
     *
     * @param buitenlandsAdresRegel5 Buitenlands adres regel 5.
     */
    public void setBuitenlandsAdresRegel5(final Adresregel buitenlandsAdresRegel5) {
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
    }

    /**
     * Zet Buitenlands adres regel 6 van Standaard.
     *
     * @param buitenlandsAdresRegel6 Buitenlands adres regel 6.
     */
    public void setBuitenlandsAdresRegel6(final Adresregel buitenlandsAdresRegel6) {
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    /**
     *
     *
     * @param landCode
     */
    public void setLandCode(final String landCode) {
        this.landCode = landCode;
    }

    /**
     * Zet Land van Standaard.
     *
     * @param land Land.
     */
    public void setLand(final Land land) {
        this.land = land;
    }

    /**
     * Zet Persoon niet aangetroffen op adres? van Standaard.
     *
     * @param indicatiePersoonNietAangetroffenOpAdres Persoon niet aangetroffen op adres?.
     */
    public void setIndicatiePersoonNietAangetroffenOpAdres(final JaNee indicatiePersoonNietAangetroffenOpAdres) {
        this.indicatiePersoonNietAangetroffenOpAdres = indicatiePersoonNietAangetroffenOpAdres;
    }

}
