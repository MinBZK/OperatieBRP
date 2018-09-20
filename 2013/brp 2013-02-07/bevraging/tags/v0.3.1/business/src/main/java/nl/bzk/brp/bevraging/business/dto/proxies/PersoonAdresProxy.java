/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.proxies;

import java.util.Set;

import nl.bzk.brp.domein.kern.AangeverAdreshouding;
import nl.bzk.brp.domein.kern.FunctieAdres;
import nl.bzk.brp.domein.kern.Land;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.PersoonAdres;
import nl.bzk.brp.domein.kern.Plaats;
import nl.bzk.brp.domein.kern.RedenWijzigingAdres;


/**
 * Proxy voor PersoonAdres die alleen gegevenselementen doorgeeft als deze in het abonnement zitten.
 */
public class PersoonAdresProxy extends AbstractAutorisatieProxy implements PersoonAdres {

    private static final String VELD_PREFIX = PersoonAdres.class.getSimpleName().toLowerCase() + ".";

    private final PersoonAdres  adres;

    /**
     * Constructor.
     *
     * @param persoonAdres de persoonAdres waarvoor dit object een proxy moet zijn
     * @param toegestaneVelden de java namen (zonder hoofdletters) van de velden die doorgegeven mogen worden
     */
    public PersoonAdresProxy(final PersoonAdres persoonAdres, final Set<String> toegestaneVelden) {
        super(toegestaneVelden);
        adres = persoonAdres;
    }

    /**
     * @return het adres waarvoor dit object een proxy is
     */
    public PersoonAdres getAdres() {
        return adres;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeente() {
        return filter(adres.getGemeente(), VELD_PREFIX + "Gemeente");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHuisnummer() {
        return filter(adres.getHuisnummer(), VELD_PREFIX + "HuisNummer");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNaamOpenbareRuimte() {
        return filter(adres.getNaamOpenbareRuimte(), VELD_PREFIX + "NaamOpenbareRuimte");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPostcode() {
        return filter(adres.getPostcode(), VELD_PREFIX + "Postcode");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plaats getWoonplaats() {
        return filter(adres.getWoonplaats(), VELD_PREFIX + "Woonplaats");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAfgekorteNaamOpenbareRuimte() {
        return filter(adres.getAfgekorteNaamOpenbareRuimte(), VELD_PREFIX + "AfgekorteNaamOpenbareRuimte");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGemeentedeel() {
        return filter(adres.getGemeentedeel(), VELD_PREFIX + "GemeenteDeel");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHuisletter() {
        return filter(adres.getHuisletter(), VELD_PREFIX + "Huisletter");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHuisnummertoevoeging() {
        return filter(adres.getHuisnummertoevoeging(), VELD_PREFIX + "Huisnummertoevoeging");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocatietovAdres() {
        return filter(adres.getLocatietovAdres(), VELD_PREFIX + "LocatieTOVAdres");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FunctieAdres getSoort() {
        return filter(adres.getSoort(), VELD_PREFIX + "Soort");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return adres.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getID() {
        return adres.getID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setID(final Long id) {
        adres.setID(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Persoon getPersoon() {
        return adres.getPersoon();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersoon(final Persoon persoon) {
        adres.setPersoon(persoon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSoort(final FunctieAdres soort) {
        adres.setSoort(soort);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenWijzigingAdres getRedenWijziging() {
        return adres.getRedenWijziging();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRedenWijziging(final RedenWijzigingAdres redenWijziging) {
        adres.setRedenWijziging(redenWijziging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AangeverAdreshouding getAangeverAdreshouding() {
        return adres.getAangeverAdreshouding();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAangeverAdreshouding(final AangeverAdreshouding aangeverAdreshouding) {
        adres.setAangeverAdreshouding(aangeverAdreshouding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getDatumAanvangAdreshouding() {
        return adres.getDatumAanvangAdreshouding();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDatumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        adres.setDatumAanvangAdreshouding(datumAanvangAdreshouding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAdresseerbaarObject() {
        return adres.getAdresseerbaarObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdresseerbaarObject(final String adresseerbaarObject) {
        adres.setAdresseerbaarObject(adresseerbaarObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdentificatiecodeNummeraanduiding() {
        return adres.getIdentificatiecodeNummeraanduiding();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        adres.setIdentificatiecodeNummeraanduiding(identificatiecodeNummeraanduiding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGemeente(final Partij gemeente) {
        adres.setGemeente(gemeente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        adres.setNaamOpenbareRuimte(naamOpenbareRuimte);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        adres.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGemeentedeel(final String gemeentedeel) {
        adres.setGemeentedeel(gemeentedeel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHuisnummer(final String huisnummer) {
        adres.setHuisnummer(huisnummer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHuisletter(final String huisletter) {
        adres.setHuisletter(huisletter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        adres.setHuisnummertoevoeging(huisnummertoevoeging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPostcode(final String postcode) {
        adres.setPostcode(postcode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWoonplaats(final Plaats woonplaats) {
        adres.setWoonplaats(woonplaats);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocatietovAdres(final String locatietovAdres) {
        adres.setLocatietovAdres(locatietovAdres);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocatieOmschrijving() {
        return adres.getLocatieOmschrijving();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        adres.setLocatieOmschrijving(locatieOmschrijving);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuitenlandsAdresRegel1() {
        return adres.getBuitenlandsAdresRegel1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        adres.setBuitenlandsAdresRegel1(buitenlandsAdresRegel1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuitenlandsAdresRegel2() {
        return adres.getBuitenlandsAdresRegel2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        adres.setBuitenlandsAdresRegel2(buitenlandsAdresRegel2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuitenlandsAdresRegel3() {
        return adres.getBuitenlandsAdresRegel3();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        adres.setBuitenlandsAdresRegel3(buitenlandsAdresRegel3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuitenlandsAdresRegel4() {
        return adres.getBuitenlandsAdresRegel4();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        adres.setBuitenlandsAdresRegel4(buitenlandsAdresRegel4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuitenlandsAdresRegel5() {
        return adres.getBuitenlandsAdresRegel5();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        adres.setBuitenlandsAdresRegel5(buitenlandsAdresRegel5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuitenlandsAdresRegel6() {
        return adres.getBuitenlandsAdresRegel6();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        adres.setBuitenlandsAdresRegel6(buitenlandsAdresRegel6);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Land getLand() {
        return adres.getLand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLand(final Land land) {
        adres.setLand(land);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getDatumVertrekUitNederland() {
        return adres.getDatumVertrekUitNederland();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDatumVertrekUitNederland(final Integer datumVertrekUitNederland) {
        adres.setDatumVertrekUitNederland(datumVertrekUitNederland);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPersoonAdresStatusHis() {
        return adres.getPersoonAdresStatusHis();
    }

}
