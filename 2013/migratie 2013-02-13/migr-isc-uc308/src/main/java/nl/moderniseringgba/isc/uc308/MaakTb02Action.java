/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningNotarieelVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVernietigingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.ErkenningVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GerechtelijkeVaststellingVaderschapVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeslachtsnaamwijzigingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OntkenningVaderschapDoorMoederVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OntkenningVaderschapVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.OverlijdenVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.TransseksualiteitVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VoornaamswijzigingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02ErkenningBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02ErkenningNotarieelBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02GerechtelijkeVaststellingVaderschapBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02GeslachtsnaamwijzigingBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OntkenningVaderschapBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OntkenningVaderschapDoorMoederBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02OverlijdenBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02TransseksualiteitBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02VernietigingErkenningBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb02VoornaamswijzigingBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak Tb02.
 */
// CHECKSTYLE:OFF Vanwege het grote aantal berichten dat de generieke flow van uc308 volgt is de class-fan out
// complexity hier te hoog (>20).
@Component("uc308MaakTb02Action")
public final class MaakTb02Action implements SpringAction {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    // Constantes voor categotie/rubriek gebruikt voor een overzichtelijkere opbouw van de header identificatienummers.
    private static final String[] RUBRIEKEN = { Lo3ElementEnum.ELEMENT_0210.getElementNummer(),
            Lo3ElementEnum.ELEMENT_0220.getElementNummer(), Lo3ElementEnum.ELEMENT_0230.getElementNummer(),
            Lo3ElementEnum.ELEMENT_0240.getElementNummer(), Lo3ElementEnum.ELEMENT_0310.getElementNummer(),
            Lo3ElementEnum.ELEMENT_0320.getElementNummer(), Lo3ElementEnum.ELEMENT_0330.getElementNummer(),
            Lo3ElementEnum.ELEMENT_0410.getElementNummer(), };

    private String identificerendeRubriekenBuilder(final Lo3CategorieEnum categorie) {

        final StringBuilder builder = new StringBuilder();

        for (final String huidigElementnummer : RUBRIEKEN) {
            builder.append(categorie.getCategorie());
            builder.append(huidigElementnummer);
        }

        return builder.toString();
    }

    // CHECKSTYLE:OFF Vanwege het aantal berichten is de excecutable statement count hier > 30. Omwille van
    // centralisatie en overzichtelijkheid van de code vatten we alle berichten hier samen in één methode.
    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        // CHECKSTYLE:ON
        LOG.info("execute(parameters={})", parameters);

        final BrpBijhoudingVerzoekBericht brpBijhoudingVerzoekBericht =
                (BrpBijhoudingVerzoekBericht) parameters.get(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT);

        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                (ConverteerNaarLo3AntwoordBericht) parameters.get(UC308Constants.CONVERTEER_NAAR_LO3_ANTWOORD);

        final String registratieGemeente = brpBijhoudingVerzoekBericht.getRegistratiegemeente();
        final String aktenummer = brpBijhoudingVerzoekBericht.getAktenummer();
        final Lo3Datum ingangsdatumGeldigheid =
                new Lo3Datum(brpBijhoudingVerzoekBericht.getIngangsdatumGeldigheid().intValue());

        Tb02Bericht tb02Bericht = null;

        boolean gebruikHistorieGegevens = false;

        // Maak op basis van het verzoek het juiste Tb02 bericht aan.
        if (brpBijhoudingVerzoekBericht instanceof ErkenningVerzoekBericht) {
            tb02Bericht = new Tb02ErkenningBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
        } else if (brpBijhoudingVerzoekBericht instanceof ErkenningNotarieelVerzoekBericht) {
            tb02Bericht = new Tb02ErkenningNotarieelBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
        } else if (brpBijhoudingVerzoekBericht instanceof ErkenningVernietigingVerzoekBericht) {
            tb02Bericht =
                    new Tb02VernietigingErkenningBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
        } else if (brpBijhoudingVerzoekBericht instanceof GerechtelijkeVaststellingVaderschapVerzoekBericht) {
            tb02Bericht =
                    new Tb02GerechtelijkeVaststellingVaderschapBericht(registratieGemeente, aktenummer,
                            ingangsdatumGeldigheid);
        } else if (brpBijhoudingVerzoekBericht instanceof OntkenningVaderschapVerzoekBericht) {
            tb02Bericht =
                    new Tb02OntkenningVaderschapBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
        } else if (brpBijhoudingVerzoekBericht instanceof OntkenningVaderschapDoorMoederVerzoekBericht) {
            tb02Bericht =
                    new Tb02OntkenningVaderschapDoorMoederBericht(registratieGemeente, aktenummer,
                            ingangsdatumGeldigheid);
        } else if (brpBijhoudingVerzoekBericht instanceof GeslachtsnaamwijzigingVerzoekBericht) {
            tb02Bericht =
                    new Tb02GeslachtsnaamwijzigingBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
            gebruikHistorieGegevens = true;
        } else if (brpBijhoudingVerzoekBericht instanceof VoornaamswijzigingVerzoekBericht) {
            tb02Bericht = new Tb02VoornaamswijzigingBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
            gebruikHistorieGegevens = true;
        } else if (brpBijhoudingVerzoekBericht instanceof TransseksualiteitVerzoekBericht) {
            tb02Bericht = new Tb02TransseksualiteitBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
            gebruikHistorieGegevens = true;
        } else if (brpBijhoudingVerzoekBericht instanceof OverlijdenVerzoekBericht) {
            tb02Bericht = new Tb02OverlijdenBericht(registratieGemeente, aktenummer, ingangsdatumGeldigheid);
        }

        // Zet algemene en header informatie.
        if (tb02Bericht != null && converteerNaarLo3Antwoord != null) {
            tb02Bericht.setLo3Persoonslijst(converteerNaarLo3Antwoord.getLo3Persoonslijst());
            tb02Bericht.setBronGemeente(brpBijhoudingVerzoekBericht.getBrpGemeente().getFormattedStringCode());
            tb02Bericht.setDoelGemeente(brpBijhoudingVerzoekBericht.getLo3Gemeente().getFormattedStringCode());
            tb02Bericht.setHeader(Lo3HeaderVeld.AANTAL, String.valueOf(RUBRIEKEN.length));
            tb02Bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, brpBijhoudingVerzoekBericht.getAktenummer());

            // Identificerende gegevens normaliter uit categorie 01, echter bij wijzigen persoonsgegevens komen deze uit
            // categorie 51.
            if (!gebruikHistorieGegevens) {
                tb02Bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN,
                        identificerendeRubriekenBuilder(Lo3CategorieEnum.CATEGORIE_01));
            } else {
                tb02Bericht.setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN,
                        identificerendeRubriekenBuilder(Lo3CategorieEnum.CATEGORIE_51));
            }
        }

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(UC308Constants.TB02_BERICHT, tb02Bericht);

        return result;
    }
}
