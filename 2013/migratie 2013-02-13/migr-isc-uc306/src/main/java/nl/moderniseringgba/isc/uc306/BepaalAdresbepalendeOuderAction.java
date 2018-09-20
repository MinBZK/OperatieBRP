/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Het systeem ontleend aan het BRP-geboortebericht de gegevens van de ouder waarbij is vermeld: indicatieAdresgevend =
 * Ja.
 * 
 * Daaruit volgt de gemeente waar het kind ingeschreven dient te worden, de gemeente van inschrijving.
 * 
 */
@Component("uc306BepaalAdresbepalendeOuderAction")
public final class BepaalAdresbepalendeOuderAction implements SpringAction {

    /** Variabele: geboortebericht. */
    public static final String INPUT = "input";
    /** Variabele: adres bepalende ouder. */
    public static final String ADRES_BEPALENDE_OUDER = "adresBepalendeOuder";
    /** Variabele: adres bepalende ouder fout. */
    public static final String ADRES_BEPALENDE_OUDER_FOUT = "adresBepalendeOuderFout";

    /** Ouder: Vader. */
    public static final String VADER = "V";
    /** Ouder: Moeder. */
    public static final String MOEDER = "M";
    /** Ouder: Onbekend. */
    public static final String ONBEKEND = "O";

    /** Toelichting. */
    public static final String TOELICHTING =
            "Vaststellen van de oudergegevens - bepalend voor de gemeente van inschrijving - is mislukt. "
                    + "%s Inschrijving is niet doorgevoerd.";
    /** Reden: geen adres gevende ouder. */
    public static final String REDEN_GEEN_ADRESGEVENDE_OUDER =
            "De bijhouding beschrijft niet welke ouder 'adresgevend' is.";
    /** Reden: meer dan 1 ouder adresgevend. */
    public static final String REDEN_MEER_DAN_1_OUDER_ADRESGEVEND =
            "De bijhouding geeft bij meer dan 1 ouder aan dat deze 'adresgevend' is.";
    /** Reden: verkeerde geslachtsaanduiding. */
    public static final String REDEN_VERKEERDE_GESLACHTSAANDUIDING =
            "De Geslachtsaanduiding van de 'adresgevende' ouder uit de bijhouding bevat geen 'M' of 'V'.";

    /** Geldige geslachtsaanduidingen. */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final Set<String> GELDIGE_GESLACHTSAANDUIDINGEN = new HashSet(Arrays.asList(new String[] { VADER,
            MOEDER, }));

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final GeboorteVerzoekBericht geboorteBericht = (GeboorteVerzoekBericht) parameters.get(INPUT);
        return stelAdresbepalendeOuderVast(geboorteBericht);
    }

    private Map<String, Object> stelAdresbepalendeOuderVast(final GeboorteVerzoekBericht geboorteBericht) {
        final Map<String, Object> result = new HashMap<String, Object>();
        final List<BrpRelatie> relaties = geboorteBericht.getBrpPersoonslijst().getRelaties();
        final List<BrpBetrokkenheid> adresBepalendeOuders = new ArrayList<BrpBetrokkenheid>(1);

        for (final BrpRelatie brpRelatie : relaties) {
            if (isKind(brpRelatie)) {
                for (final BrpBetrokkenheid brpBetrokkenheid : brpRelatie.getBetrokkenheden()) {
                    if (isAdresgevendeOuder(brpBetrokkenheid)) {
                        adresBepalendeOuders.add(brpBetrokkenheid);
                    }
                }
            }
        }

        if (adresBepalendeOuders.size() > 1) {
            result.put(ADRES_BEPALENDE_OUDER_FOUT, String.format(TOELICHTING, REDEN_MEER_DAN_1_OUDER_ADRESGEVEND));
        } else if (adresBepalendeOuders.isEmpty()) {
            result.put(ADRES_BEPALENDE_OUDER_FOUT, String.format(TOELICHTING, REDEN_GEEN_ADRESGEVENDE_OUDER));
        } else {
            final String adresBepalendeOuder = geefAdresBepalendeOuder(adresBepalendeOuders.get(0));

            if (GELDIGE_GESLACHTSAANDUIDINGEN.contains(adresBepalendeOuder)) {
                result.put(ADRES_BEPALENDE_OUDER, geefAdresBepalendeOuder(adresBepalendeOuders.get(0)));
                LOG.info("adresBepalendeOuder: " + adresBepalendeOuder);
            } else {
                result.put(ADRES_BEPALENDE_OUDER_FOUT,
                        String.format(TOELICHTING, REDEN_VERKEERDE_GESLACHTSAANDUIDING));
            }
        }
        return result;
    }

    private static boolean isKind(final BrpRelatie brpRelatie) {
        return BrpSoortBetrokkenheidCode.KIND == brpRelatie.getRolCode();
    }

    private static boolean isAdresgevendeOuder(final BrpBetrokkenheid brpBetrokkenheid) {
        return brpBetrokkenheid != null && BrpSoortBetrokkenheidCode.OUDER == brpBetrokkenheid.getRol()
                && brpBetrokkenheid.getOuderStapel().getMeestRecenteElement().getInhoud().getHeeftIndicatie();
    }

    private static String geefAdresBepalendeOuder(final BrpBetrokkenheid brpBetrokkenheid) {
        final String result;
        if (BrpGeslachtsaanduidingCode.MAN == brpBetrokkenheid.getGeslachtsaanduidingStapel()
                .getMeestRecenteElement().getInhoud().getGeslachtsaanduiding()) {
            // MAN --> Vader
            result = VADER;
        } else if (BrpGeslachtsaanduidingCode.VROUW == brpBetrokkenheid.getGeslachtsaanduidingStapel()
                .getMeestRecenteElement().getInhoud().getGeslachtsaanduiding()) {
            // VROUW --> Moeder
            result = MOEDER;
        } else if (BrpGeslachtsaanduidingCode.ONBEKEND == brpBetrokkenheid.getGeslachtsaanduidingStapel()
                .getMeestRecenteElement().getInhoud().getGeslachtsaanduiding()) {
            result = ONBEKEND;
        } else {
            result = null;
        }
        return result;
    }
}
