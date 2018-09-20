/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.ws.service.mapper;

import java.math.BigDecimal;

import nl.bzk.brp.bevraging.ws.basis.Persoon;


/**
 * Utility class voor het mappen van een {@link nl.bzk.brp.bevraging.domein.Persoon Persoon} domein object naar een
 * {@link Persoon} DTO object.
 */
public final class PersoonMapper {

    /**
     * Private constructor daar het hier om een utility class gaat die niet geinstantieert dient te worden.
     */
    private PersoonMapper() {
    }

    /**
     * Utility methode voor het mappen van een @link nl.bzk.brp.bevraging.domein.Persoon Persoon} domein object naar
     * een {@link Persoon} DTO object.
     * @param persoonDO het persoon domein object.
     * @return een persoon DTO object.
     */
    public static Persoon mapDomeinObjectNaarDTO(final nl.bzk.brp.bevraging.domein.Persoon persoonDO) {
        if (persoonDO == null) {
            return null;
        }

        Persoon persoon = new Persoon();
        if (persoonDO.getBurgerservicenummer() != null) {
            persoon.setBsn(new BigDecimal(persoonDO.getBurgerservicenummer()));
        }
        if (persoonDO.getGeslachtsAanduiding() != null) {
            persoon.setGeslachtsAanduiding(persoonDO.getGeslachtsAanduiding().getOmschrijving());
            persoon.setGeslachtsAanduidingCode(persoonDO.getGeslachtsAanduiding().getCode());
        }
        persoon.setGeslachtsNaam(persoonDO.getGeslachtsnaam());
        persoon.setVoornamen(persoonDO.getVoornamen());
        persoon.setVoorvoegsel(persoonDO.getVoorvoegsel());
        if (persoonDO.getGeboorteDatum() != null) {
            persoon.setDatumGeboorte(new BigDecimal(persoonDO.getGeboorteDatum()));
        }
        if (persoonDO.getAdressen() != null && !persoonDO.getAdressen().isEmpty()) {
            persoon.setAdres(AdresMapper.mapDomeinObjectNaarDTO(persoonDO.getAdressen().iterator().next()));
        }
        if (persoonDO.getBijhoudingsGemeente() != null) {
            persoon.setBijhoudingsGemeente(persoonDO.getBijhoudingsGemeente().getNaam());
            persoon.setBijhoudingsGemeenteId(persoonDO.getBijhoudingsGemeente().getId());
        }
        persoon.setBuitenlandseGeboortePlaats(persoonDO.getBuitenlandseGeboorteplaats());
        persoon.setBuitenlandseGeboorteRegio(persoonDO.getBuitenlandseRegioGeboorte());
        persoon.setBuitenlandsePlaatsOverlijden(persoonDO.getBuitenlandsePlaatsOverlijden());
        if (persoonDO.getGeboorteDatum() != null) {
            persoon.setDatumGeboorte(new BigDecimal(persoonDO.getGeboorteDatum()));
        }
        persoon.setLocatieOmschrijvingGeboorte(persoonDO.getOmschrijvingGeboorteLocatie());
        if (persoonDO.getGemeenteGeboorte() != null) {
            persoon.setGemeenteGeboorte(persoonDO.getGemeenteGeboorte().getNaam());
            persoon.setGemeenteIdGeboorte(persoonDO.getGemeenteGeboorte().getId());
        }
        if (persoonDO.getLandGeboorte() != null) {
            persoon.setLandGeboorte(persoonDO.getLandGeboorte().getNaam());
            persoon.setLandIdGeboorte(persoonDO.getLandGeboorte().getId());
        }
        if (persoonDO.getWoonplaatsGeboorte() != null) {
            persoon.setWoonplaatsGeboorte(persoonDO.getWoonplaatsGeboorte().getNaam());
            persoon.setWoonplaatsIdGeboorte(persoonDO.getWoonplaatsGeboorte().getId());
        }
        if (persoonDO.getRedenOpschortingBijhouding() != null) {
            persoon.setRedenOpschorting(persoonDO.getRedenOpschortingBijhouding().getNaam());
            persoon.setRedenOpschortingCode(persoonDO.getRedenOpschortingBijhouding().getCode());
        }
        if (persoonDO.verstrekkingsBeperking() != null) {
            persoon.setVerstrekkingsBeperking(persoonDO.verstrekkingsBeperking().toString());
        }

        return persoon;
    }
}
