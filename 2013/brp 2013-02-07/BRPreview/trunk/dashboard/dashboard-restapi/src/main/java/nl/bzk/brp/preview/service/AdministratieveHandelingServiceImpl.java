/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.preview.dataaccess.AdministratieveHandelingDao;
import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.dataaccess.CorrectieAdresDao;
import nl.bzk.brp.preview.dataaccess.GeboorteDao;
import nl.bzk.brp.preview.dataaccess.HuwelijkDao;
import nl.bzk.brp.preview.dataaccess.OverlijdenDao;
import nl.bzk.brp.preview.dataaccess.VerhuizingDao;
import nl.bzk.brp.preview.model.AdministratieveHandeling;
import nl.bzk.brp.preview.model.AdministratieveHandelingBericht;
import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.CorrectieAdres;
import nl.bzk.brp.preview.model.CorrectieAdresBericht;
import nl.bzk.brp.preview.model.Geboorte;
import nl.bzk.brp.preview.model.GeboorteBericht;
import nl.bzk.brp.preview.model.Huwelijk;
import nl.bzk.brp.preview.model.HuwelijkBericht;
import nl.bzk.brp.preview.model.HuwelijkDatumAanvangEnPlaats;
import nl.bzk.brp.preview.model.Melding;
import nl.bzk.brp.preview.model.Overlijden;
import nl.bzk.brp.preview.model.OverlijdenBericht;
import nl.bzk.brp.preview.model.Persoon;
import nl.bzk.brp.preview.model.Verhuizing;
import nl.bzk.brp.preview.model.VerhuizingBericht;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de {@link AdministratieveHandelingService} interface.
 */
@Service
public class AdministratieveHandelingServiceImpl implements AdministratieveHandelingService {

    /**
     * De sleutel van administratieve handeling in het JMS bericht.
     */
    public static final String ADMINISTRATIEVE_HANDELING_ID = "administratieveHandelingId";

    /**
     * De berichten dao.
     */
    @Autowired
    private BerichtenDao berichtenDao;

    /** De ah dao. */
    @Autowired
    private AdministratieveHandelingDao ahDao;

    /** De huwelijk dao. */
    @Autowired
    private HuwelijkDao huwelijkDao;

    /** De geboorte dao. */
    @Autowired
    private GeboorteDao geboorteDao;

    /** De verhuizing dao. */
    @Autowired
    private VerhuizingDao verhuizingDao;

    /** De overlijden dao. */
    @Autowired
    private OverlijdenDao overlijdenDao;

    /** De correctie adres dao. */
    @Autowired
    private CorrectieAdresDao correctieAdresDao;

    /** De Constante SOORT_CODES_HUWELIJK. */
    private static final List<String> SOORT_CODES_HUWELIJK        =
            Arrays.asList(new String[]{"02001", "02003", "02005"});

    /** De Constante SOORT_CODES_GEBOORTE. */
    private static final List<String> SOORT_CODES_GEBOORTE        = Arrays.asList(new String[]{"01002", "01003"});

    /** De Constante SOORT_CODES_CORRECTIE_ADRES. */
    private static final List<String> SOORT_CODES_CORRECTIE_ADRES = Arrays.asList(new String[]{"03003", "03004"});

    /** De Constante SOORT_CODES_VERHUIZING. */
    private static final List<String> SOORT_CODES_VERHUIZING      = Arrays.asList(new String[]{"03001", "03002"});

    /** De Constante SOORT_CODES_OVERLIJDEN. */
    private static final List<String> SOORT_CODES_OVERLIJDEN      = Arrays.asList(new String[]{"04001", "04002"});

    /**
     * Deze methode maakt een bericht voor een administratieve handeling id.
     *
     * @param handelingId de identificatie van een AH
     * @return Het bericht voor de administratieve handeling.
     */
    @Override
    public Bericht maakBericht(final Long handelingId) {

        final AdministratieveHandeling administratieveHandeling = haalOpCompleteAdministratieveHandeling(handelingId);

        final AdministratieveHandelingBericht administratieveHandelingBericht =
                maakBerichtVoorAdministratieveHandeling(administratieveHandeling);

        final Bericht bericht =
                AdministratieveHandelingBerichtConverteerder.converteerNaarBericht(administratieveHandelingBericht);

        return bericht;
    }

    /**
     * Haal op complete administratieve handeling op basis van id.
     *
     * @param handelingId de handeling id
     * @return de administratieve handeling
     */
    private AdministratieveHandeling haalOpCompleteAdministratieveHandeling(final Long handelingId) {
        final AdministratieveHandeling resultaat;

        final AdministratieveHandeling administratieveHandelingBasis = ahDao.haalOp(handelingId);

        haalMeldingenOpEnVoegToe(administratieveHandelingBasis);

        final String soortCode = administratieveHandelingBasis.getSoortAdministratieveHandelingCode();

        if (SOORT_CODES_HUWELIJK.contains(soortCode)) {
            resultaat = haalHuwelijk(administratieveHandelingBasis);
        } else if (SOORT_CODES_GEBOORTE.contains(soortCode)) {
            resultaat = haalGeboorte(administratieveHandelingBasis);
        } else if (SOORT_CODES_CORRECTIE_ADRES.contains(soortCode)) {
            resultaat = haalCorrectieAdres(administratieveHandelingBasis);
        } else if (SOORT_CODES_VERHUIZING.contains(soortCode)) {
            resultaat = haalVerhuizing(administratieveHandelingBasis);
        } else if (SOORT_CODES_OVERLIJDEN.contains(soortCode)) {
            resultaat = haalOverlijden(administratieveHandelingBasis);
        } else {
            resultaat = administratieveHandelingBasis;
        }

        return resultaat;
    }

    /**
     * Haal overlijden op op basis van een administratieve handeling.
     *
     * @param administratieveHandelingBasis de administratieve handeling basis
     * @return de administratieve handeling
     */
    private AdministratieveHandeling haalOverlijden(final AdministratieveHandeling administratieveHandelingBasis) {
        final Overlijden overlijdenIncompleet = overlijdenDao.haalOpOverlijden(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        final Overlijden overlijden = new Overlijden(administratieveHandelingBasis);
        overlijden.setDatumOverlijden(overlijdenIncompleet.getDatumOverlijden());
        overlijden.setGemeenteOverlijden(overlijdenIncompleet.getGemeenteOverlijden());
        overlijden.setPersoon(overlijdenIncompleet.getPersoon());
        return overlijden;
    }

    /**
     * Haal verhuizing op op basis van een administratieve handeling.
     *
     * @param administratieveHandelingBasis de administratieve handeling basis
     * @return de administratieve handeling
     */
    private AdministratieveHandeling haalVerhuizing(final AdministratieveHandeling administratieveHandelingBasis) {
        Verhuizing verhuizing = new Verhuizing(administratieveHandelingBasis);
        Persoon persoon = verhuizingDao.haalOpPersoon(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        verhuizing.setPersoon(persoon);
        Adres oudAdres = verhuizingDao.haalOpOudAdres(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        verhuizing.setOudAdres(oudAdres);
        Adres nieuwAdres = verhuizingDao.haalOpNieuwAdres(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        verhuizing.setNieuwAdres(nieuwAdres);
        return verhuizing;
    }

    /**
     * Haal correctie adres op op basis van een administratieve handeling.
     *
     * @param administratieveHandelingBasis de administratieve handeling basis
     * @return de administratieve handeling
     */
    private AdministratieveHandeling haalCorrectieAdres(final AdministratieveHandeling administratieveHandelingBasis) {
        CorrectieAdres correctieAdres = new CorrectieAdres(administratieveHandelingBasis);
        Persoon persoon = correctieAdresDao.haalOpPersoon(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        correctieAdres.setPersoon(persoon);
        List<Adres> adressen = correctieAdresDao.haalOpAdressen(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        correctieAdres.setAdressen(adressen);
        return correctieAdres;
    }

    /**
     * Haal geboorte op op basis van een administratieve handeling.
     *
     * @param administratieveHandelingBasis de administratieve handeling basis
     * @return de administratieve handeling
     */
    private AdministratieveHandeling haalGeboorte(final AdministratieveHandeling administratieveHandelingBasis) {
        final Geboorte geboorte = new Geboorte(administratieveHandelingBasis);
        final Persoon nieuwGeborene = geboorteDao.haalOpGeboorteNieuwGeborene(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        geboorte.setNieuwgeborene(nieuwGeborene);
        final List<Persoon> ouders = geboorteDao.haalOpGeboorteOuders(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        geboorte.setOuder1(ouders.get(0));
        if (ouders.size() > 1) {
            geboorte.setOuder2(ouders.get(1));
        }
        return geboorte;
    }

    /**
     * Haal huwelijk op op basis van een administratieve handeling.
     *
     * @param administratieveHandelingBasis de administratieve handeling basis
     * @return de administratieve handeling
     */
    private AdministratieveHandeling haalHuwelijk(final AdministratieveHandeling administratieveHandelingBasis)
    {
        final HuwelijkDatumAanvangEnPlaats huwelijkDatumAanvangEnPlaats =
                huwelijkDao.haalOpHuwelijkDatumAanvangEnPlaats(
                        administratieveHandelingBasis.getAdministratieveHandelingId());
        final List<Persoon> huwelijkPersonsen = huwelijkDao.haalOpHuwelijkPersonen(
                administratieveHandelingBasis.getAdministratieveHandelingId());
        final Huwelijk huwelijk = new Huwelijk(administratieveHandelingBasis);
        huwelijk.setDatumAanvang(huwelijkDatumAanvangEnPlaats.getDatumAanvang());
        huwelijk.setPlaats(huwelijkDatumAanvangEnPlaats.getPlaats());
        huwelijk.setPersoon1(huwelijkPersonsen.get(0));
        huwelijk.setPersoon2(huwelijkPersonsen.get(1));
        return huwelijk;
    }

    /**
     * Haal meldingen op en voeg toe.
     *
     * @param administratieveHandeling de administratieve handeling
     */
    private void haalMeldingenOpEnVoegToe(final AdministratieveHandeling administratieveHandeling) {
        final List<Melding> meldingen = ahDao.haalMeldingenOp(administratieveHandeling.getAdministratieveHandelingId());
        Collections.sort(meldingen, new MeldingComparator());
        administratieveHandeling.getMeldingen().addAll(meldingen);
    }

    /**
     * Slaat een bericht op in de database.
     *
     * @param bericht het bericht om op te slaan
     */
    @Override
    public void opslaan(final Bericht bericht) {
        berichtenDao.opslaan(bericht);
    }

    /**
     * Maak bericht voor administratieve handeling.
     *
     * @param administratieveHandeling de administratieve handeling
     * @return de administratieve handeling bericht
     */
    private AdministratieveHandelingBericht maakBerichtVoorAdministratieveHandeling(
            final AdministratieveHandeling administratieveHandeling)
    {
        final AdministratieveHandelingBericht resultaat;

        if (administratieveHandeling instanceof Geboorte) {
            resultaat = new GeboorteBericht((Geboorte) administratieveHandeling);
        } else if (administratieveHandeling instanceof Huwelijk) {
            resultaat = new HuwelijkBericht((Huwelijk) administratieveHandeling);
        } else if (administratieveHandeling instanceof Verhuizing) {
            resultaat = new VerhuizingBericht((Verhuizing) administratieveHandeling);
        } else if (administratieveHandeling instanceof Overlijden) {
            resultaat = new OverlijdenBericht((Overlijden) administratieveHandeling);
        } else if (administratieveHandeling instanceof CorrectieAdres) {
            resultaat = new CorrectieAdresBericht((CorrectieAdres) administratieveHandeling);
        } else {
            resultaat = new AdministratieveHandelingBericht(administratieveHandeling);
        }

        return resultaat;
    }

}
