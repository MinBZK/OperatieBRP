/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ha01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import org.springframework.stereotype.Component;

/**
 * Controleer of de identificerende gegevens uit het ap01 bericht een uniek persoon oplevert.
 */
@Component("uc1003ControleerPersoonAction")
public final class ControleerPersoonAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "3d. Controle persoon mislukt (beeindigen)";
    private static final String FOUT_MSG_PERSOON = "Persoon identificatie mislukt met reden '%s'";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public ControleerPersoonAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final Map<String, Object> result = new HashMap<>();

        final Long adHocZoekPersoonAntwoordId = (Long) parameters.get("adHocZoekPersoonAntwoord");
        final AdHocZoekPersoonAntwoordBericht antwoord = (AdHocZoekPersoonAntwoordBericht) berichtenDao.leesBericht(adHocZoekPersoonAntwoordId);

        final String foutreden = controleerAntwoord(antwoord);
        if (antwoord != null) {
            bepaalFoutmelding(result, antwoord, foutreden);
        }

        LOG.debug("result: {}", result);
        return result;
    }

    private void bepaalFoutmelding(final Map<String, Object> result, final AdHocZoekPersoonAntwoordBericht antwoord, final String foutreden) {
        // vul context met aNummer, nodig als in een latere stap een af01 gestuurd gaat worden.
        if (antwoord.getInhoud() != null) {
            // Indien de inhoud van antwoord is gevuld, bevat deze een Ha01.
            final Ha01Bericht ha01Bericht = (Ha01Bericht) new Lo3BerichtFactory().getBericht(antwoord.getInhoud());
            result.put(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY,
                    ha01Bericht.getCategorieen().stream().filter(categorieWaarde -> categorieWaarde.getCategorie() == Lo3CategorieEnum.CATEGORIE_01).findFirst()
                            .orElse(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0)).getElement(Lo3ElementEnum.ELEMENT_0110));
        }

        if (foutreden != null) {
            LOG.debug(String.format(FOUT_MSG_PERSOON, foutreden));
            result.put(FOUTMELDING_VARIABELE, String.format(FOUT_MSG_PERSOON, foutreden));
            result.put(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY, foutreden);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
            // Vullen anummer op basis van meegestuurde gegevens Ap01/Av01.

        } else {
            // vul context met BSN, nodig in laatste stap, plaatsen afnemersindicatie.
            final Ha01Bericht ha01Bericht = (Ha01Bericht) new Lo3BerichtFactory().getBericht(antwoord.getInhoud());
            result.put(AfnemersIndicatieJbpmConstants.PERSOON_BSN, ha01Bericht.getCategorieen().get(0).getElement(Lo3ElementEnum.ELEMENT_0120));
        }
    }

    private String controleerAntwoord(final AdHocZoekPersoonAntwoordBericht antwoord) {
        String foutreden = null;

        if (antwoord != null && antwoord.getFoutreden() != null) {
            if (antwoord.getFoutreden() == AdHocZoekAntwoordFoutReden.G) {
                // Geen personen gevonden
                foutreden = AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_G;
            } else if (antwoord.getFoutreden() == AdHocZoekAntwoordFoutReden.U) {
                // Meerdere personen gevonden
                foutreden = AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_U;
            } else if (antwoord.getFoutreden() == AdHocZoekAntwoordFoutReden.X) {
                // Geen autorisatie
                foutreden = AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_X;
            } else {
                throw new IllegalStateException(
                        "Onverwachte foutreden uit adhoc zoeken voor plaatsen/verwijderen afnemersindicatie: " + antwoord.getFoutreden().value());
            }
        }

        return foutreden;
    }
}
