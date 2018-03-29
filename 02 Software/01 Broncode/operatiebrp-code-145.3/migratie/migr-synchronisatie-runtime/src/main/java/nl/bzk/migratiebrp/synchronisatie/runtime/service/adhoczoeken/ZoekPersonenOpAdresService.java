/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.adhoczoeken;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.gba.domain.bevraging.AdresvraagQueue;
import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdHocZoekAntwoordFoutReden;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersonenOpAdresVerzoekBericht;
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
 * Service voor het verwerken van een Xq01 bericht.
 */
public final class ZoekPersonenOpAdresService
        implements SynchronisatieBerichtService<AdHocZoekPersonenOpAdresVerzoekBericht, AdHocZoekPersonenOpAdresAntwoordBericht> {

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
    public ZoekPersonenOpAdresService(final ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen,
                                      final AdHocZoekenNaarBrpVerzender adHocZoekenNaarBrpVerzender) {
        this.conversieLo3NaarBrpVragen = conversieLo3NaarBrpVragen;
        this.adHocZoekenNaarBrpVerzender = adHocZoekenNaarBrpVerzender;
    }

    @Override
    public Class<AdHocZoekPersonenOpAdresVerzoekBericht> getVerzoekType() {
        return AdHocZoekPersonenOpAdresVerzoekBericht.class;
    }

    @Override
    public AdHocZoekPersonenOpAdresAntwoordBericht verwerkBericht(final AdHocZoekPersonenOpAdresVerzoekBericht verzoekBericht) {
        Logging.initContext();
        AdHocZoekPersonenOpAdresAntwoordBericht antwoordBericht = null;

        try {
            List<Lo3CategorieWaarde> waardes = AdhocInhoudParser.parse(verzoekBericht.getIdentificerendeGegevens());
            List<Integer> nietToegestaneRubrieken = filter.nietToegestaneRubrieken(waardes);
            if (nietToegestaneRubrieken.isEmpty()) {
                final Adresvraag brpVraag =
                        new VerzoekBerichtMapper(conversieLo3NaarBrpVragen).mapNaarBrpVraag(
                                new Adresvraag(),
                                verzoekBericht.getPartijCode(),
                                SoortDienst.valueOf(verzoekBericht.getSoortDienst().name()),
                                verzoekBericht.getGevraagdeRubrieken(),
                                waardes);

                brpVraag.setSoortIdentificatie(IdentificatieType.A == verzoekBericht.getIdentificatie() ? Adresvraag.SoortIdentificatie.ADRES : Adresvraag
                        .SoortIdentificatie.PERSOON);

                switch (verzoekBericht.getAdresFunctie()) {
                    case B:
                    case W:
                        // Voor B voegen we additioneel zoekcriterium op briefadres voor persoon_adres_soortcode toe.
                        // Voor W voegen we additioneel zoekcriterium op woonadres voor persoon_adres_soortcode toe.
                        voegAdditioneelZoekCriteriumEnZoekRubriekToe(brpVraag, Element.PERSOON_ADRES_SOORTCODE.getNaam(),
                                verzoekBericht.getAdresFunctie().value());
                        break;
                    case A:
                        // Voor A hoeven er geen additionele zoekcriteria en zoekrubrieken toegevoegd te worden.
                        break;
                    default:
                        throw new IllegalStateException("Ongeldige adresfunctie meegegeven.");
                }

                adHocZoekenNaarBrpVerzender.verstuurAdHocZoekenVraag(
                        brpVraag,
                        AdresvraagQueue.VERZOEK.getQueueNaam(),
                        verzoekBericht.getMessageId());
            } else {
                LOG.debug("Niet toegestaan te zoeken op de rubrieken: ", nietToegestaneRubrieken);
                antwoordBericht = maakFoutBericht(verzoekBericht.getMessageId());
            }
        } catch (ConversieExceptie e) {
            LOG.debug("Conversie exceptie", e);
            antwoordBericht = maakFoutBericht(verzoekBericht.getMessageId());
        } finally {
            Logging.destroyContext();
        }

        return antwoordBericht;
    }

    private void voegAdditioneelZoekCriteriumEnZoekRubriekToe(final Adresvraag brpVraag, final String naamZoekCriterium, final String waardeZoekCriterium) {
        ZoekCriterium additioneelZoekCriterium = new ZoekCriterium(naamZoekCriterium);
        additioneelZoekCriterium.setWaarde(waardeZoekCriterium);
        brpVraag.getZoekCriteria().add(additioneelZoekCriterium);
        brpVraag.getZoekRubrieken().add("08.10.10");
    }

    private AdHocZoekPersonenOpAdresAntwoordBericht maakFoutBericht(final String messageId) {
        AdHocZoekPersonenOpAdresAntwoordBericht antwoordBericht = new AdHocZoekPersonenOpAdresAntwoordBericht();
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
