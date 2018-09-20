/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.AangeverAdreshouding;
import nl.bzk.brp.domein.kern.FunctieAdres;
import nl.bzk.brp.domein.kern.Land;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.PersoonAdres;
import nl.bzk.brp.domein.kern.Plaats;
import nl.bzk.brp.domein.kern.RedenWijzigingAdres;
import nl.bzk.brp.domein.kern.SoortPartij;


public class TestPersoonAdres implements PersoonAdres {

    private static final Partij PARTIJ;
    private static final Plaats PLAATS;
    static {
        PARTIJ = new PersistentDomeinObjectFactory().createPartij();
        PARTIJ.setSoort(SoortPartij.GEMEENTE);
        PLAATS = new PersistentDomeinObjectFactory().createPlaats();
        PLAATS.setNaam("Test");
    }

    @Override
    public Partij getGemeente() {
        return PARTIJ;
    }

    @Override
    public String getHuisnummer() {
        return "44";
    }

    @Override
    public String getNaamOpenbareRuimte() {
        return "Dam";
    }

    @Override
    public String getPostcode() {
        return "3063 NB";
    }

    @Override
    public Plaats getWoonplaats() {
        return PLAATS;
    }

    @Override
    public String getAfgekorteNaamOpenbareRuimte() {
        return "hoofdstr";
    }

    @Override
    public String getGemeentedeel() {
        return "Scheveningen";
    }

    @Override
    public String getHuisletter() {
        return "A";
    }

    @Override
    public String getHuisnummertoevoeging() {
        return "bis";
    }

    @Override
    public String getLocatietovAdres() {
        return "boven";
    }

    @Override
    public FunctieAdres getSoort() {
        return FunctieAdres.WOONADRES;
    }

    @Override
    public Long getID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setID(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Persoon getPersoon() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersoon(final Persoon persoon) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSoort(final FunctieAdres soort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RedenWijzigingAdres getRedenWijziging() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRedenWijziging(final RedenWijzigingAdres redenWijziging) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AangeverAdreshouding getAangeverAdreshouding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAangeverAdreshouding(final AangeverAdreshouding aangeverAdreshouding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumAanvangAdreshouding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDatumAanvangAdreshouding(final Integer datumAanvangAdreshouding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAdresseerbaarObject() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAdresseerbaarObject(final String adresseerbaarObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIdentificatiecodeNummeraanduiding() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGemeente(final Partij gemeente) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAfgekorteNaamOpenbareRuimte(final String afgekorteNaamOpenbareRuimte) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGemeentedeel(final String gemeentedeel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHuisnummer(final String huisnummer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHuisletter(final String huisletter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPostcode(final String postcode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWoonplaats(final Plaats woonplaats) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocatietovAdres(final String locatietovAdres) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLocatieOmschrijving() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLocatieOmschrijving(final String locatieOmschrijving) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBuitenlandsAdresRegel1() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandsAdresRegel1(final String buitenlandsAdresRegel1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBuitenlandsAdresRegel2() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandsAdresRegel2(final String buitenlandsAdresRegel2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBuitenlandsAdresRegel3() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandsAdresRegel3(final String buitenlandsAdresRegel3) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBuitenlandsAdresRegel4() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandsAdresRegel4(final String buitenlandsAdresRegel4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBuitenlandsAdresRegel5() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandsAdresRegel5(final String buitenlandsAdresRegel5) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBuitenlandsAdresRegel6() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBuitenlandsAdresRegel6(final String buitenlandsAdresRegel6) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Land getLand() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLand(final Land land) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDatumVertrekUitNederland() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDatumVertrekUitNederland(final Integer datumVertrekUitNederland) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPersoonAdresStatusHis() {
        throw new UnsupportedOperationException();
    }

}
