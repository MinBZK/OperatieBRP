/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.gba.domain.bevraging.PersoonsvraagQueue;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.vragen.ConversieExceptie;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import nl.bzk.migratiebrp.conversie.vragen.filter.AdHocZoekenFilter;
import nl.bzk.migratiebrp.conversie.vragen.mapper.VerzoekBerichtMapper;
import nl.bzk.migratiebrp.conversie.vragen.parser.AdhocInhoudParser;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;

/**
 * Service voor het verwerken van een Hq01 bericht.
 */
public final class ZoekPersoonService implements SynchronisatieBerichtService<AdHocZoekPersoonVerzoekBericht, AdHocZoekPersoonAntwoordBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen;
    private final AdHocZoekenNaarBrpVerzender adHocZoekenNaarBrpVerzender;
    private final AdHocZoekenFilter filter = new AdHocZoekenFilter();

    /**
     * Constructor.
     * @param conversieLo3NaarBrpVragen conversie voor adhoc vragen
     * @param adHocZoekenNaarBrpVerzender verzender voor jms berichten
     */
    @Inject
    public ZoekPersoonService(final ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen,
                              final AdHocZoekenNaarBrpVerzender adHocZoekenNaarBrpVerzender) {
        this.conversieLo3NaarBrpVragen = conversieLo3NaarBrpVragen;
        this.adHocZoekenNaarBrpVerzender = adHocZoekenNaarBrpVerzender;
    }

    @Override
    public Class<AdHocZoekPersoonVerzoekBericht> getVerzoekType() {
        return AdHocZoekPersoonVerzoekBericht.class;
    }

    @Override
    public AdHocZoekPersoonAntwoordBericht verwerkBericht(final AdHocZoekPersoonVerzoekBericht verzoekBericht) {
        Logging.initContext();
        AdHocZoekPersoonAntwoordBericht antwoordBericht = null;

        try {
            final List<Lo3CategorieWaarde> waardes = AdhocInhoudParser.parse(verzoekBericht.getPersoonIdentificerendeGegevens());
            final List<Integer> nietToegestaneRubrieken = filter.nietToegestaneRubrieken(waardes);
            if (nietToegestaneRubrieken.isEmpty()) {

                final Persoonsvraag brpVraag =
                        new VerzoekBerichtMapper(conversieLo3NaarBrpVragen).mapNaarBrpVraag(
                                new Persoonsvraag(),
                                verzoekBericht.getPartijCode(),
                                SoortDienst.valueOf(verzoekBericht.getSoortDienst().name()),
                                verzoekBericht.getGevraagdeRubrieken(),
                                waardes);

                adHocZoekenNaarBrpVerzender.verstuurAdHocZoekenVraag(
                        brpVraag,
                        PersoonsvraagQueue.VERZOEK.getQueueNaam(),
                        verzoekBericht.getMessageId());
            } else {
                LOG.debug("Niet toegestaan te zoeken op de rubrieken: ", nietToegestaneRubrieken);
                antwoordBericht = maakFoutBericht(verzoekBericht.getMessageId());
            }
        } catch (ConversieExceptie e) {
            LOG.debug("Conversie exceptie of zoekrubrieken zijn niet toegestaan", e);
            antwoordBericht = maakFoutBericht(verzoekBericht.getMessageId());
        } finally {
            Logging.destroyContext();
        }

        return antwoordBericht;
    }

    private AdHocZoekPersoonAntwoordBericht maakFoutBericht(final String messageId) {
        AdHocZoekPersoonAntwoordBericht antwoordBericht = new AdHocZoekPersoonAntwoordBericht();
        antwoordBericht.setCorrelationId(messageId);
        antwoordBericht.setMessageId(MessageId.generateSyncMessageId());
        antwoordBericht.setFoutreden(AdHocZoekAntwoordFoutReden.X);
        return antwoordBericht;
    }

    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }
}
