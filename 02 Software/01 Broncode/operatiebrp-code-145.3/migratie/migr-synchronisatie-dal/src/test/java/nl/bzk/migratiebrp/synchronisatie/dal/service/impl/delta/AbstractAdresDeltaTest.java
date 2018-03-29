/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.sql.Timestamp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;

public abstract class AbstractAdresDeltaTest extends AbstractDeltaTest {

    protected static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    protected static final Aangever AANGEVER_INGESCHREVENE = new Aangever('I', "Ingeschrevene", "De ingeschrevene zelf");
    protected static final RedenWijzigingVerblijf REDEN_WIJZIGING_PERSOON = new RedenWijzigingVerblijf('P', "Aangifte door persoon");
    protected static final RedenWijzigingVerblijf REDEN_WIJZIGING_AMBTSHALVE = new RedenWijzigingVerblijf('A', "Ambtshalve");
    protected static final RedenWijzigingVerblijf REDEN_WIJZIGING_INFRASTRUCTUREEL = new RedenWijzigingVerblijf('I', "Infrastructureel");
    protected static final RedenWijzigingVerblijf REDEN_WIJZIGING_BAG = new RedenWijzigingVerblijf('B', "Technische wijzigingen i.v.m. BAG");

    protected static final String STRAATNAAM_OUD = "Lange vijverberg";
    protected static final String POSTCODE_OUD = "2513AC";
    protected static final String STRAATNAAM_NIEUW = "Turfmarkt";
    protected static final String POSTCODE_NIEUW = "2511DP";
    protected static final int HUISNUMMER_OUD = 11;
    protected static final int DATUM_AANVANG_ADRESHOUDING_OUD = 20090101;
    protected static final int HUISNUMMER_NIEUW = 147;
    protected static final int DATUM_AANVANG_ADRESHOUDING_NIEUW = 20131101;

    private Persoon dbPersoon;
    private Persoon kluizenaar;
    private Gemeente gemeente;
    private Aangever aangeverAdreshouding;

    /**
     * Geef de waarde van db persoon.
     * @return db persoon
     */
    public Persoon getDbPersoon() {
        return dbPersoon;
    }

    /**
     * Geef de waarde van kluizenaar.
     * @return kluizenaar
     */
    public Persoon getKluizenaar() {
        return kluizenaar;
    }

    /**
     * Deze method aanroepen in de @Before van de implementerende classes.
     */
    public void setUp() {
        gemeente = new Gemeente((short) 521, "'s-Gravenhage", "0518", maakPartij());
        aangeverAdreshouding = new Aangever('I', "Ingeschrevene", "De ingeschrevene zelf");
        dbPersoon = maakPersoon(true);
        kluizenaar = maakPersoon(false);
    }

    /**
     * Voegt aan A-laag adres met 1 historie toe aan de persoon
     * @param persoon persoon waar het adres aangekoppeld gaat worden
     * @param isDbPersoon true als de persoon al bestaat (oude persoon).
     */
    protected final void voegAdresToeAanPersoon(final Persoon persoon, final boolean isDbPersoon) {
        final PersoonAdres adres = new PersoonAdres(persoon);
        final AdministratieveHandeling administratieveHandeling =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAfgeleidAdministratiefHistorieSet())
                        .getAdministratieveHandeling();

        final PersoonAdresHistorie historie =
                maakPersoonAdresHistorie(
                        adres,
                        isDbPersoon,
                        isDbPersoon ? REDEN_WIJZIGING_PERSOON : REDEN_WIJZIGING_AMBTSHALVE,
                        administratieveHandeling);
        adres.addPersoonAdresHistorie(historie);

        adres.setLandOfGebied(NEDERLAND);
        adres.setSoortAdres(SoortAdres.WOONADRES);
        adres.setGemeente(gemeente);
        adres.setAangeverAdreshouding(aangeverAdreshouding);

        if (isDbPersoon) {
            adres.setAfgekorteNaamOpenbareRuimte(STRAATNAAM_OUD);
            adres.setHuisnummer(HUISNUMMER_OUD);
            adres.setPostcode(POSTCODE_OUD);
            adres.setDatumAanvangAdreshouding(DATUM_AANVANG_ADRESHOUDING_OUD);
        } else {
            adres.setAfgekorteNaamOpenbareRuimte(STRAATNAAM_NIEUW);
            adres.setHuisnummer(HUISNUMMER_NIEUW);
            adres.setPostcode(POSTCODE_NIEUW);
            adres.setDatumAanvangAdreshouding(DATUM_AANVANG_ADRESHOUDING_NIEUW);
        }

        persoon.addPersoonAdres(adres);
    }

    protected final PersoonAdresHistorie maakPersoonAdresHistorie(
            final PersoonAdres adres,
            final boolean isDbPersoon,
            final RedenWijzigingVerblijf redenWijziging,
            final AdministratieveHandeling administratieveHandeling) {
        final PersoonAdresHistorie historie = new PersoonAdresHistorie(adres, SoortAdres.WOONADRES, NEDERLAND, redenWijziging);
        final Timestamp timestamp = isDbPersoon ? DATUMTIJD_STEMPEL_OUD : DATUMTIJD_STEMPEL_NIEUW;
        final BRPActie actieInhoud = maakBrpActie(administratieveHandeling, timestamp);

        historie.setGemeente(gemeente);
        historie.setActieInhoud(actieInhoud);
        historie.setDatumTijdRegistratie(timestamp);

        if (isDbPersoon) {
            historie.setAangeverAdreshouding(AANGEVER_INGESCHREVENE);
            historie.setAfgekorteNaamOpenbareRuimte(STRAATNAAM_OUD);
            historie.setHuisnummer(HUISNUMMER_OUD);
            historie.setPostcode(POSTCODE_OUD);
            historie.setDatumAanvangAdreshouding(DATUM_AANVANG_ADRESHOUDING_OUD);
            historie.setDatumAanvangGeldigheid(DATUM_AANVANG_ADRESHOUDING_OUD);
        } else {
            historie.setSoortAdres(SoortAdres.BRIEFADRES);
            historie.setAfgekorteNaamOpenbareRuimte(STRAATNAAM_NIEUW);
            historie.setHuisnummer(HUISNUMMER_NIEUW);
            historie.setPostcode(POSTCODE_NIEUW);
            historie.setDatumAanvangAdreshouding(DATUM_AANVANG_ADRESHOUDING_NIEUW);
            historie.setDatumAanvangGeldigheid(DATUM_AANVANG_ADRESHOUDING_NIEUW);
        }

        adres.addPersoonAdresHistorie(historie);
        return historie;
    }

    protected final PersoonAdresHistorie voerActueleBijhoudingUitOpAdres(final Persoon persoon) {
        final AdministratieveHandeling administratieveHandeling =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAfgeleidAdministratiefHistorieSet())
                        .getAdministratieveHandeling();

        final PersoonAdres adres = persoon.getPersoonAdresSet().iterator().next();
        final PersoonAdresHistorie huidigAdresVoorkomen = adres.getPersoonAdresHistorieSet().iterator().next();
        final Timestamp nieuweTsReg = new Timestamp(administratieveHandeling.getDatumTijdRegistratie().getTime() + 3600000);
        final AdministratieveHandeling nieuweAdministratieveHandeling = maakAdministratieveHandeling(false, nieuweTsReg);
        final Integer nieuweGeldigheid = huidigAdresVoorkomen.getDatumAanvangGeldigheid() + 1;
        final BRPActie nieuweActie = maakBrpActie(nieuweAdministratieveHandeling, nieuweTsReg);

        final PersoonAdresHistorie resultaat =
                new PersoonAdresHistorie(
                        huidigAdresVoorkomen.getPersoonAdres(),
                        huidigAdresVoorkomen.getSoortAdres(),
                        huidigAdresVoorkomen.getLandOfGebied(),
                        huidigAdresVoorkomen.getRedenWijziging());
        adres.addPersoonAdresHistorie(resultaat);

        // nieuwe historie
        resultaat.setActieInhoud(nieuweActie);
        resultaat.setDatumTijdRegistratie(nieuweTsReg);
        resultaat.setDatumAanvangGeldigheid(nieuweGeldigheid);
        // kopie inhoud
        resultaat.setGemeente(huidigAdresVoorkomen.getGemeente());
        resultaat.setAangeverAdreshouding(huidigAdresVoorkomen.getAangeverAdreshouding());
        resultaat.setAfgekorteNaamOpenbareRuimte(huidigAdresVoorkomen.getAfgekorteNaamOpenbareRuimte());
        resultaat.setHuisnummer(huidigAdresVoorkomen.getHuisnummer());
        resultaat.setPostcode(huidigAdresVoorkomen.getPostcode());
        resultaat.setDatumAanvangAdreshouding(huidigAdresVoorkomen.getDatumAanvangAdreshouding());
        // update oude historie
        huidigAdresVoorkomen.setDatumEindeGeldigheid(nieuweGeldigheid);
        huidigAdresVoorkomen.setActieAanpassingGeldigheid(nieuweActie);
        return resultaat;
    }
}
