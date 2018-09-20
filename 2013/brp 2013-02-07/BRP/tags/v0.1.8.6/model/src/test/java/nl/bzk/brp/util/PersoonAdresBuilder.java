/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;

/** Een helper klasse voor het bouwen/instantieren en vullen van adressen ten behoeve van de unit tests. */
public final class PersoonAdresBuilder {

    private static final Map<String, String> OPENBARERUIMTE_AFKORTINGEN;

    static {
        Map<String, String> aanpasbareAfkortingenMap = new HashMap<String, String>();
        aanpasbareAfkortingenMap.put("straat", "str");
        aanpasbareAfkortingenMap.put("plein", "pln");
        aanpasbareAfkortingenMap.put("laan", "ln");
        aanpasbareAfkortingenMap.put("weg", "wg");
        OPENBARERUIMTE_AFKORTINGEN = Collections.unmodifiableMap(aanpasbareAfkortingenMap);
    }

    /** Lege en private constructor daar utility classes niet geinstantieerd dienen te worden. */
    private PersoonAdresBuilder() {
    }

    /**
     * Bouwt en retourneert een woon adres in een opgegeven plaats en gemeente, waarbij de opgegeven overige adres
     * gegevens worden gezet op het adres.
     *
     * @param naamOpenbareRuimte de naam van de openbare ruimte
     * @param huisnummer het huisnummer
     * @param postcode de postcode
     * @param woonplaats de woonplaats
     * @param gemeente de gemeente
     * @param datumAanvangAdreshouding de datum van aanvang van adreshouding
     * @return een Nederlands woon adres
     */
    public static PersoonAdresBericht bouwWoonadres(final String naamOpenbareRuimte, final Integer huisnummer,
        final String postcode, final Plaats woonplaats, final Partij gemeente, final Integer datumAanvangAdreshouding)
    {
        final PersoonAdresBericht adres = new PersoonAdresBericht();
        adres.setGegevens(new PersoonAdresStandaardGroepBericht());

        final PersoonAdresStandaardGroepBericht adresGegevens = adres.getGegevens();
        adresGegevens.setSoort(FunctieAdres.WOONADRES);
        if (naamOpenbareRuimte != null) {
            adresGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimte(naamOpenbareRuimte));
            adresGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimte(kortStraatnaamAf(
                naamOpenbareRuimte)));
        }
        if (huisnummer != null) {
            adresGegevens.setHuisnummer(new Huisnummer(huisnummer));
        }
        if (postcode != null) {
            adresGegevens.setPostcode(new Postcode(postcode));
        }
        adresGegevens.setWoonplaats(woonplaats);
        adresGegevens.setGemeente(gemeente);
        adresGegevens.setLand(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        if (datumAanvangAdreshouding != null) {
            adresGegevens.setDatumAanvangAdreshouding(new Datum(datumAanvangAdreshouding));
        }

        return adres;
    }

    /**
     * Kort de opgegeven naamOpenbareRuimte af door enkele veel voorkomende eindes van een naamOpenbareRuimte
     * (bijvoorbeeld 'laan') af
     * te korten naar een kortere versie (bijvoorbeeld 'laan' naar 'ln). Indien de opgegeven naamOpenbareRuimte niet
     * eindigt
     * met een bekend deel, dan wordt de gehele naamOpenbareRuimte geretourneerd (en dus niet afgekort).
     *
     * @param straatnaam de naamOpenbareRuimte die afgekort dient te worden.
     * @return de afgekorte naamOpenbareRuimte.
     */
    private static String kortStraatnaamAf(final String straatnaam) {
        String afgekorteStraatnaam = straatnaam;

        for (Map.Entry<String, String> afkorting : OPENBARERUIMTE_AFKORTINGEN.entrySet()) {
            if (straatnaam.endsWith(afkorting.getKey())) {
                afgekorteStraatnaam =
                    straatnaam.substring(0, straatnaam.lastIndexOf(afkorting.getKey())) + afkorting.getValue();
                break;
            }
        }

        return afgekorteStraatnaam;
    }
}
