/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import org.springframework.stereotype.Component;


/**
 * Factory voor het aanmaken van ActieUitvoerder instanties.
 */
@Component
public class ActieFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final Map<SoortActie, Class<? extends ActieUitvoerder<ActieBericht>>> SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP = new HashMap<>();

    static {
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS, RegistratieIdentificatienummersUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_ADRES, RegistratieAdresUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_MIGRATIE, RegistratieMigratieUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.CORRECTIE_ADRES, CorrectieAdresUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_BEHANDELD_ALS_NEDERLANDER, RegistratieBehandeldAlsNederlanderUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_STAATLOOS, RegistratieStaatloosUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_VASTGESTELD_NIET_NEDERLANDER, RegistratieVastgesteldNietNederlanderUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING, RegistratieVerstrekkingsbeperkingUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_CURATELE, RegistratieCurateleUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_GEZAG, RegistratieGezagUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_OUDER, ActualiseringAfstammingUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_NATIONALITEIT, RegistratieNationaliteitUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_REISDOCUMENT, RegistratieReisdocumentUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_NAAM_GESLACHT, RegistratieNaamGeslachtUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_GESLACHTSAANDUIDING, RegistratieNaamGeslachtUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_VOORNAAM, RegistratieNaamGeslachtUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_GESLACHTSNAAM, RegistratieNaamGeslachtUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_NAAM_VOORNAAM, RegistratieNaamGeslachtUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP,
            RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_NAAMGEBRUIK, RegistratieNaamgebruikUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_OVERLIJDEN, RegistratieOverlijdenUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_BIJHOUDING, RegistratieBijhoudingUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_NATIONALITEIT_NAAM, RegistratieNationaliteitNaamUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_SIGNALERING_REISDOCUMENT, RegistratieSignaleringReisdocument.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_DEELNAME_E_U_VERKIEZINGEN, RegistratieDeelnameEUVerkiezingenUitvoerder.class);
        SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.put(SoortActie.REGISTRATIE_UITSLUITING_KIESRECHT, RegistratieUitsluitingKiesrechtUitvoerder.class);
    }

    /**
     * Geeft de correcte {@link ActieUitvoerder} instanties voor een
     * opgegeven {@link SoortActie} en {@link SoortAdministratieveHandeling}.
     *
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param soortActie de soort actie
     * @return de actieuitvoerder
     */
    public ActieUitvoerder<ActieBericht> getActieUitvoerder(final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final SoortActie soortActie)
    {
        ActieUitvoerder<ActieBericht> actieUitvoerder = null;
        if (soortActie == SoortActie.REGISTRATIE_GEBOORTE) {
            actieUitvoerder = getGeboorteUitvoerder(soortAdministratieveHandeling);
        } else if (SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.containsKey(soortActie)) {
            final Class<? extends ActieUitvoerder<ActieBericht>> actieUitvoerderType = SOORT_ACTIE_ACTIEUITVOERDER_CLASS_MAP.get(soortActie);
            try {
                actieUitvoerder = actieUitvoerderType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("Kon de ActieUitvoerder voor SoortActie {} niet instantieren.", soortActie);
            }
        } else {
            final String msg = String.format("Geen correcte actieuitvoerder gevonden voor de opgegeven actie soort: %s", soortActie);
            LOGGER.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return actieUitvoerder;
    }

    /**
     * Geef de correcte geboorte uitvoerder terug voor de correcte administratieve handeling.
     *
     * @param soortAdministratieveHandeling de administratieve handeling in het bericht.
     * @return de actieuitvoerder die uitgevoerd dient te worden voor de actie en administratieve handeling combi.
     */
    private ActieUitvoerder<ActieBericht> getGeboorteUitvoerder(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final ActieUitvoerder<ActieBericht> actieUitvoerder;
        switch (soortAdministratieveHandeling) {
            case GEBOORTE_IN_NEDERLAND:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING:
                actieUitvoerder = new RegistratieGeboorteUitvoerder();
                break;
            case TOEVOEGING_GEBOORTEAKTE:
            case VERBETERING_GEBOORTEAKTE:
                // Update van een bestaand kind
                actieUitvoerder = new ActualiseringAfstammingUitvoerder();
                break;
            default:
                final String msg =
                    String.format("Geen correcte actieuitvoerder gevonden voor de actie geboorte en "
                        + "administratieve handeling: %s", soortAdministratieveHandeling);
                LOGGER.error(msg);
                throw new IllegalArgumentException(msg);
        }
        return actieUitvoerder;
    }
}
