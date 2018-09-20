/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3CategorieWaardeFormatter;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3Format;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maakt een Tv01Bericht op basis van ontvangen persoonslijst.
 * 
 */
@Component("uc307MaakTv01Action")
public final class MaakTv01Action implements SpringAction {

    /**
     * Variabele voor logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                (LeesUitBrpAntwoordBericht) parameters.get("leesUitBrpAntwoordBericht");

        final Tb01Bericht tb01Bericht = (Tb01Bericht) parameters.get("input");
        final Tv01Bericht tv01Bericht =
                new Tv01Bericht(vulVerwijsgegevens(leesUitBrpAntwoordBericht.getLo3Persoonslijst()));
        tv01Bericht.setBronGemeente(tb01Bericht.getDoelGemeente());
        tv01Bericht.setDoelGemeente(tb01Bericht.getBronGemeente());

        final Object herhaling = parameters.get("tv01Herhaling");
        if (herhaling != null) {
            tv01Bericht.setHeader(Lo3HeaderVeld.HERHALING, String.valueOf(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) parameters.get(UC307Constants.TV01_BERICHT);
            tv01Bericht.setMessageId(orgineel.getMessageId());
        }

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(UC307Constants.TV01_BERICHT, tv01Bericht);

        return result;
    }

    private List<Lo3CategorieWaarde> vulVerwijsgegevens(final Lo3Persoonslijst pl) {

        final Lo3PersoonInhoud inhoud = pl.getPersoonStapel().getMeestRecenteElement().getInhoud();
        final Lo3Historie kindPersoonHistorie = pl.getPersoonStapel().getMeestRecenteElement().getHistorie();

        final Lo3InschrijvingInhoud inschrijving = pl.getInschrijvingStapel().getMeestRecenteElement().getInhoud();

        // 21.09.10 --> Verblijfplaats 08.09.20 of 58.09.20, met dezelfde datum als 07.68.10
        final Lo3VerblijfplaatsInhoud kindVerblijfplaatsInhoud;
        if (pl.getVerblijfplaatsStapel().size() > 1) {
            // gebruik 58.09.20
            kindVerblijfplaatsInhoud = pl.getVerblijfplaatsStapel().get(0).getInhoud();
        } else {
            // gebruik 08.09.20
            kindVerblijfplaatsInhoud = pl.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud();
        }

        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_21);

        // 21.01.10
        formatter.element(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(inhoud.getaNummer()));
        // 21.01.20
        formatter.element(Lo3ElementEnum.ELEMENT_0120, Lo3Format.format(inhoud.getBurgerservicenummer()));
        // 21.02.10
        formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(inhoud.getVoornamen()));
        // 21.02.20
        formatter.element(Lo3ElementEnum.ELEMENT_0220, Lo3Format.format(inhoud.getAdellijkeTitelPredikaatCode()));
        // 21.02.30
        formatter.element(Lo3ElementEnum.ELEMENT_0230, Lo3Format.format(inhoud.getVoorvoegselGeslachtsnaam()));
        // 21.02.40
        formatter.element(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format(inhoud.getGeslachtsnaam()));
        // 21.03.10
        formatter.element(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(inhoud.getGeboortedatum()));
        // 21.03.20
        formatter.element(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(inhoud.getGeboorteGemeenteCode()));
        // 21.03.30
        formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(inhoud.getGeboorteLandCode()));
        // 21.09.10
        formatter.element(Lo3ElementEnum.ELEMENT_0910,
                Lo3Format.format(kindVerblijfplaatsInhoud.getGemeenteInschrijving()));
        // 21.09.20 --> 07.68.10
        formatter.element(Lo3ElementEnum.ELEMENT_0920, Lo3Format.format(inschrijving.getDatumEersteInschrijving()));
        // 21.70.10 --> 07.70.10
        formatter.element(Lo3ElementEnum.ELEMENT_7010, Lo3Format.format(inschrijving.getIndicatieGeheimCode()));

        // ignored for now... formatter.element(Lo3ElementEnum.ELEMENT_8310, Lo3Format.format(null));
        // ignored for now... formatter.element(Lo3ElementEnum.ELEMENT_8320, Lo3Format.format(null));
        // ignored for now... formatter.element(Lo3ElementEnum.ELEMENT_8330, Lo3Format.format(null));

        formatter.element(Lo3ElementEnum.ELEMENT_8510,
                Lo3Format.format(kindPersoonHistorie.getIngangsdatumGeldigheid()));

        return formatter.getList();
    }
}
