/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service.impl;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.jms.Destination;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.PersoonRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.ProtocolleringRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.jdbc.RemoteBrpDatabase;
import nl.bzk.migratiebrp.init.naarbrp.service.InitieleVullingService;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendAfnemersIndicatieBerichtVerwerker;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendAutorisatieBerichtVerwerker;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendPersoonBerichtVerwerker;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendProtocolleringBerichtVerwerker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Deze service wordt gebruikt voor de acties van initiele vulling. In dit geval: - Het lezen en
 * zetten van berichten op de queue. - Het aanmaken van een initiele vulling tabel adhv de gbav
 * database
 */
@Service("initieleVullingService")
public final class InitieleVullingServiceImpl implements InitieleVullingService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String TOTAAL_VERWERKTE_BERICHTEN = "Totaal verwerkte berichten: {}";
    private static final String TOTAAL_VERWERKTE_PROTOCOLLERINGEN = "Totaal verwerkte protocolleringen: {}";

    private final PersoonRepository persoonRepository;
    private final AutorisatieRepository autorisatieRepository;
    private final AfnemersIndicatieRepository afnemersIndicatieRepository;
    private final ProtocolleringRepository protocolleringRepository;
    private final RemoteBrpDatabase remoteBrpDatabase;
    private final JmsTemplate jmsTemplate;
    private final Destination destination;
    private final ExcelBerichtenService excelBerichtenService;

    private Integer batchPersoon;
    private Integer batchAutorisatie;
    private Integer batchAfnemersindicatie;
    private Integer batchProtocollering;

    private Integer aantalProtocollering;

    /**
     * Constructor.
     * @param persoonRepository persoonRepository
     * @param autorisatieRepository autorisatieRepository
     * @param afnemersIndicatieRepository afnemersIndicatieRepository
     * @param protocolleringRepository protocolleringRepository
     * @param remoteBrpDatabase remoteBrpDatabase
     * @param jmsTemplate jmsTemplate
     * @param destination destination
     * @param excelBerichtenService excelBerichtenService
     */
    @Inject
    public InitieleVullingServiceImpl(final PersoonRepository persoonRepository,
                                      final AutorisatieRepository autorisatieRepository,
                                      final AfnemersIndicatieRepository afnemersIndicatieRepository,
                                      final ProtocolleringRepository protocolleringRepository,
                                      final RemoteBrpDatabase remoteBrpDatabase,
                                      final JmsTemplate jmsTemplate,
                                      final Destination destination,
                                      final ExcelBerichtenService excelBerichtenService) {
        this.persoonRepository = persoonRepository;
        this.autorisatieRepository = autorisatieRepository;
        this.afnemersIndicatieRepository = afnemersIndicatieRepository;
        this.protocolleringRepository = protocolleringRepository;
        this.remoteBrpDatabase = remoteBrpDatabase;
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.excelBerichtenService = excelBerichtenService;
    }

    @Override
    @Value("${batch.persoon:100}")
    public void setBatchPersoon(final Integer batchPersoon) {
        this.batchPersoon = batchPersoon;
    }

    @Override
    @Value("${batch.autorisatie:100}")
    public void setBatchAutorisatie(final Integer batchAutorisatie) {
        this.batchAutorisatie = batchAutorisatie;
    }

    @Override
    @Value("${batch.afnemersindicatie:100}")
    public void setBatchAfnemersindicatie(final Integer batchAfnemersindicatie) {
        this.batchAfnemersindicatie = batchAfnemersindicatie;
    }

    @Override
    @Value("${batch.protocollering:10}")
    public void setBatchProtocollering(final Integer batchProtocollering) {
        this.batchProtocollering = batchProtocollering;
    }

    @Override
    @Value("${aantal.protocollering:1000}")
    public void setAantalProtocollering(final Integer aantalProtocollering) {
        this.aantalProtocollering = aantalProtocollering;
    }

    /* ******************************************************************************************* */
    /* ******************************************************************************************* */
    /* *** INITIELE VULLING PERSONEN ************************************************************* */
    /* ******************************************************************************************* */
    /* ******************************************************************************************* */

    @Override
    public void laadInitieleVullingTable() {
        LOG.info("De initiele vulling tabel wordt nu gevuld, dit kan even duren!");
        persoonRepository.laadInitVullingTable();
        LOG.info("De initiele vulling tabel is gevuld.");
    }

    @Override
    public void vulBerichtenTabelExcel(final String excelFolder) {
        LOG.info("Lees de Excel bestanden in en maak op basis hiervan een initiele vulling tabel");
        LOG.info("Aanmaken tabel als deze nog niet bestaat");
        persoonRepository.createInitVullingTable();
        excelBerichtenService.verwerkFolder(excelFolder);
    }

    @Override
    public boolean synchroniseerPersonen() throws ParseException {
        LOG.info("Start lezen en versturen (van set) LO3 synchronisatie berichten.");
        final Instant t1 = Instant.now();
        final boolean doorgaan;
        final VerzendPersoonBerichtVerwerker verwerker =
                new VerzendPersoonBerichtVerwerker(destination, jmsTemplate, persoonRepository);
        doorgaan = persoonRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, batchPersoon);

        LOG.info("Klaar met versturen (van set) van Lo3Berichten. totale tijd (sec):" + Duration.between(t1, Instant.now()).getSeconds());
        LOG.info(TOTAAL_VERWERKTE_BERICHTEN, verwerker.aantalBerichten());
        return doorgaan;
    }

    /* ******************************************************************************************* */
    /* ******************************************************************************************* */
    /* *** INITIELE AUTORISATIES ***************************************************************** */
    /* ******************************************************************************************* */
    /* ******************************************************************************************* */

    @Override
    public void laadInitAutorisatieRegelTabel() {
        LOG.info("De initiele vulling tabel voor de autorisatieregels wordt nu gevuld.");
        autorisatieRepository.laadInitVullingAutTable();
        LOG.info("De initiele vulling tabel voor de autorisatieregels is gevuld.");
    }

    @Override
    public boolean synchroniseerAutorisaties() {
        LOG.info("Start lezen en versturen (van set) autorisatie berichten.");
        final VerzendAutorisatieBerichtVerwerker verwerker =
                new VerzendAutorisatieBerichtVerwerker(destination, jmsTemplate, autorisatieRepository);
        final boolean doorgaan = autorisatieRepository.verwerkAutorisatie(ConversieResultaat.TE_VERZENDEN, verwerker, batchAutorisatie);

        LOG.info("Klaar met versturen (van set) van AutorisatieBerichten.");
        LOG.info(TOTAAL_VERWERKTE_BERICHTEN, verwerker.aantalBerichten());

        return doorgaan;
    }

    /* ******************************************************************************************* */
    /* ******************************************************************************************* */
    /* *** INITIELE AFNEMERSINDICATIES *********************************************************** */
    /* ******************************************************************************************* */
    /* ******************************************************************************************* */

    @Override
    public void laadInitAfnemersIndicatieTabel() {
        LOG.info("De initiele vulling tabel voor de afnemersindicaties wordt nu gevuld.");
        afnemersIndicatieRepository.laadInitVullingAfnIndTable();
        LOG.info("De initiele vulling tabel voor de afnemersindicaties is gevuld.");
    }

    @Override
    public boolean synchroniseerAfnemerIndicaties() {
        LOG.info("Start lezen en versturen (van set) afnemersindicaties berichten.");
        final VerzendAfnemersIndicatieBerichtVerwerker verwerker =
                new VerzendAfnemersIndicatieBerichtVerwerker(destination, jmsTemplate, afnemersIndicatieRepository);
        final boolean doorgaan = afnemersIndicatieRepository.verwerkAfnemerindicaties(ConversieResultaat.TE_VERZENDEN, verwerker, batchAfnemersindicatie);

        LOG.info("Klaar met versturen (van set) van AfnemersindicatiesBerichten.");
        LOG.info(TOTAAL_VERWERKTE_BERICHTEN, verwerker.aantalVerzonden());
        return doorgaan;
    }

    /* ******************************************************************************************* */
    /* ******************************************************************************************* */
    /* *** INITIELE PROTOCOLLERING *************************************************************** */
    /* ******************************************************************************************* */
    /* ******************************************************************************************* */

    @Override
    public void laadInitProtocolleringTabel() {
        initProtocolleringTabel(protocolleringRepository::laadInitVullingTable);
    }

    @Override
    public void laadInitProtocolleringTabel(final LocalDateTime vanafDatum) {
        initProtocolleringTabel(() -> protocolleringRepository.laadInitVullingTable(vanafDatum));
    }

    private void initProtocolleringTabel(final Runnable r) {
        LOG.info("De initiele vulling tabel voor de protocollering wordt nu gevuld.");
        remoteBrpDatabase.initialize();
        r.run();
        LOG.info("De initiele vulling tabel voor de protocollering is gevuld.");
    }

    @Override
    public boolean synchroniseerProtocollering() {
        LOG.info("Start lezen en versturen (van set) protocollering berichten. Batch size: " + batchProtocollering + ", aantal protocoleringen per bericht: "
                + aantalProtocollering);
        final VerzendProtocolleringBerichtVerwerker verwerker =
                new VerzendProtocolleringBerichtVerwerker(destination, jmsTemplate, protocolleringRepository);
        final boolean doorgaan = protocolleringRepository.verwerk(ConversieResultaat.TE_VERZENDEN, verwerker, batchProtocollering, aantalProtocollering);

        LOG.info("Klaar met versturen (van set) van ProtocolleringBerichten.");
        LOG.info(TOTAAL_VERWERKTE_PROTOCOLLERINGEN, verwerker.aantalVerzonden());
        return doorgaan;
    }

}
