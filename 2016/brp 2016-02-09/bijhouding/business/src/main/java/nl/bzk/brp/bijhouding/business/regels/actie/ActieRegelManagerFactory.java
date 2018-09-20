/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import org.springframework.stereotype.Component;

/**
 * Factory voor het zoeken van de juiste ActieRegelManager implementatieklasse bij een SoortActie.
 */
@Component
public final class ActieRegelManagerFactory {

    private static final Map<SoortActie, Class<? extends ActieRegelManager>> SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP = new HashMap<>();

    static {
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.CORRECTIE_ADRES, CorrectieAdresActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            RegistratieAanvangHuwelijkGeregistreerdPartnerschapActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_ADRES, RegistratieAdresActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER,
            RegistratieBehandeldAlsNederlanderActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_CURATELE, RegistratieCurateleActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_DEELNAME_E_U_VERKIEZINGEN,
            RegistratieDeelnameEuVerkiezingenActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            RegistratieEindeHuwelijkGeregistreerdPartnerschapActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_GEBOORTE, RegistratieGeboorteActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_GESLACHTSAANDUIDING, RegistratieGeslachtsaanduidingActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_GEZAG, RegistratieGezagActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, RegistratieIdentificatienummersActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_MIGRATIE, RegistratieMigratieActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_NAAM_GESLACHT, RegistratieNaamGeslachtActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_NATIONALITEIT, RegistratieNationaliteitActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_NATIONALITEIT_NAAM, RegistratieNationaliteitNaamActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_OUDER, RegistratieOuderActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_OVERLIJDEN, RegistratieOverlijdenActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_REISDOCUMENT, RegistratieReisdocumentActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_STAATLOOS, RegistratieStaatloosActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_UITSLUITING_KIESRECHT, RegistratieUitsluitingKiesrechtActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_VASTGESTELD_NIET_NEDERLANDER,
            RegistratieVastgesteldNietNederlanderActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING,
            RegistratieVerstrekkingsbeperkingActieRegelManager.class);
        SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.put(SoortActie.REGISTRATIE_VOORNAAM, RegistratieVoornaamActieRegelManager.class);
    }

    /**
     * Retourneert een {@link ActieRegelManager} klasse op basis van een {@link SoortActie}.
     * @param soortActie de soort actie
     * @return de {@link ActieRegelManager} klasse
     */
    public Class<? extends ActieRegelManager> getActieRegelManager(final SoortActie soortActie) {
        if (SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.containsKey(soortActie)) {
            return SOORT_ACTIE_ACTIEREGELMANAGER_CLASS_MAP.get(soortActie);
        }
        return OverigeActieActieRegelManager.class;
    }

}
