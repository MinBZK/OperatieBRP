/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import nl.bzk.brp.preview.dataaccess.BerichtenDao;
import nl.bzk.brp.preview.model.Bericht;
import nl.bzk.brp.preview.model.BerichtenResponse;
import nl.bzk.brp.preview.model.DashboardSettings;
import nl.bzk.brp.preview.model.Gemeente;
import nl.bzk.brp.preview.model.OndersteundeBijhoudingsTypes;
import nl.bzk.brp.preview.model.VerhuisBerichtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementatie van de berichtenservice die verantwoordelijk is voor het ophalen en opslaan van de berichten van de BRP
 * die getoond moeten worden op het scherm.
 */
@Service
public class BerichtenServiceImpl implements BerichtenService {

    @Autowired
    private DashboardSettings settings;

    @Autowired
    private BerichtenDao      berichtenDao;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.service.BerichtenService#opslaan(nl.bzk.brp.preview.model.Bericht)
     */
    @Override
    public void opslaan(final VerhuisBerichtRequest inkomendBericht) {

        if (inkomendBericht.getBijhoudingsverzoek() == null) {
            throw new IllegalArgumentException("Bijhoudingsverzoek ontbreekt");
        }
        if (inkomendBericht.getBijhoudingsverzoek().getVerzendendePartij() == null) {
            throw new IllegalArgumentException("Verzendende partij ontbreekt");
        }
        if (inkomendBericht.getBijhoudingsverzoek().getBurgerZakenModuleNaam() == null) {
            throw new IllegalArgumentException("Verzendende partij ontbreekt");
        }
        if (inkomendBericht.getOudAdres() == null) {
            throw new IllegalArgumentException("oud adres ontbreekt");
        }
        if (inkomendBericht.getPersoon() == null) {
            throw new IllegalArgumentException("persoon ontbreekt");
        }
        if (inkomendBericht.getPersoon().getVoornamen() == null) {
            throw new IllegalArgumentException("voornamen ontbreken");
        }
        if (inkomendBericht.getPersoon().getGeslachtsnaamcomponenten() == null) {
            throw new IllegalArgumentException("geslachtsnaamcomponenten ontbreken");
        }
        if (inkomendBericht.getPersoon().getAdres() == null) {
            throw new IllegalArgumentException("nieuw adres ontbreekt");
        }
        Gemeente gemeenteNieuw = inkomendBericht.getPersoon().getAdres().getGemeente();
        if (gemeenteNieuw == null) {
            throw new IllegalArgumentException("gemeente van nieuw adres ontbreekt");
        }
        if (inkomendBericht.getPersoon().getAdres().getStraat() == null) {
            throw new IllegalArgumentException("straat van nieuw adres ontbreekt");
        }
        if (inkomendBericht.getPersoon().getAdres().getHuisnummer() == null) {
            throw new IllegalArgumentException("huisnummer van nieuw adres ontbreekt");
        }
        Gemeente gemeenteOud = inkomendBericht.getOudAdres().getGemeente();
        if (gemeenteOud == null) {
            throw new IllegalArgumentException("gemeente van oud adres ontbreekt");
        }

        String verzendendePartij = inkomendBericht.getBijhoudingsverzoek().getVerzendendePartij().getNaam();
        String voornaam = inkomendBericht.getPersoon().getVoornamen().get(0);
        String geslachtsnaam = inkomendBericht.getPersoon().getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String nieuweWoonplaats = gemeenteNieuw.getNaam();
        String nieuweStraat = inkomendBericht.getPersoon().getAdres().getStraat();
        String nieuwHuisnummer = inkomendBericht.getPersoon().getAdres().getHuisnummer();
        int verhuisDatum = inkomendBericht.getPersoon().getAdres().getDatumAanvangAdreshouding();
        int verhuisDag = verhuisDatum % 100;
        int verhuisMaand = (verhuisDatum - verhuisDag) / 100 % 100;
        int verhuisJaar = verhuisDatum / 10000;

        Bericht bericht = new Bericht();

        String oudeGemeenteTekst = creeerOudeGemeenteTekst(gemeenteOud, gemeenteNieuw);
        String berichtTemplate = "%s: %s van %s %s.";
        bericht.setBericht(String
                .format(berichtTemplate, verzendendePartij, oudeGemeenteTekst, voornaam, geslachtsnaam));

        String detailsTemplate = "Adres per %02d-%02d-%04d: %s %s (%s).";
        bericht.setBerichtDetails(String.format(detailsTemplate, verhuisDag, verhuisMaand, verhuisJaar, nieuweStraat,
                nieuwHuisnummer, nieuweWoonplaats));

        bericht.setBurgerZakenModule(inkomendBericht.getBijhoudingsverzoek().getBurgerZakenModuleNaam());

        bericht.setPartij(verzendendePartij);

        bericht.setSoortBijhouding(OndersteundeBijhoudingsTypes.VERHUIZING);

        berichtenDao.opslaan(bericht);
    }

    private String creeerOudeGemeenteTekst(final Gemeente gemeenteOud, final Gemeente gemeenteNieuw) {
        String resultaat;
        if (gemeenteOud.equals(gemeenteNieuw)) {
            resultaat = "verhuizing binnen gemeente";
        } else {
            resultaat = String.format("verhuizing vanuit %s", gemeenteOud.getNaam());
        }
        return resultaat;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.preview.service.BerichtenService#getBerichtenResponse()
     */
    @Override
    public BerichtenResponse getBerichtenResponse() {
        BerichtenResponse response = new BerichtenResponse();

        response.setAantalBerichtenVolledig(settings.getAantalBerichtenVolledig());
        response.setPagina(DashboardSettings.DEFAULT_PAGINA);
        response.setMaximumAantalBerichtenPerResponse(settings.getMaximumAantalBerichtenPerResponse());
        response.setBerichten(berichtenDao.getAlleBerichten());
        return response;
    }

}
