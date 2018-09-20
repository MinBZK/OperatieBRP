/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.jms.Destination;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.repository.GbavRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.InitieleVullingService;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendAfnemersIndicatieBerichtVerwerker;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendAutorisatieBerichtVerwerker;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendSynchronisatieBerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * Deze service wordt gebruikt voor de acties van initiele vulling. In dit geval: - Het lezen en zetten van berichten op
 * de queue. - Het aanmaken van een initiele vulling tabel adhv de gbav database
 */
// TODO: refactoring. Door de anonymous classes in VerzendXXXBerichtVerwerker te vervangen door toplevel classes, die
// door Spring van de juiste dependencies worden voorzien, kan hier de Fan-out complexity omlaag.
@Service("initieleVullingService")
public final class InitieleVullingServiceImpl implements InitieleVullingService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String TOTAAL_VERWERKTE_BERICHTEN = "Totaal verwerkte berichten: {}";

    @Inject
    private GbavRepository gbavRepository;

    @Inject
    private AutorisatieRepository autorisatieRepository;

    @Inject
    private AfnemersIndicatieRepository afnemersIndicatieRepository;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    private Destination destination;

    @Inject
    private ExcelAdapter excelAdapter;

    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

    private Integer batchSize;

    @Override
    @Value("${batch.aantal:100}")
    public void setBatchSize(final Integer batchSize) {
        this.batchSize = batchSize;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** INITIELE VULLING PERSONEN ******************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public void laadInitieleVullingTable() {
        LOG.info("De initiele vulling tabel wordt nu gevuld, dit kan even duren!");
        gbavRepository.laadInitVullingTable();
        LOG.info("De initiele vulling tabel is gevuld.");
    }

    @Override
    public void vulBerichtenTabelExcel(final String excelFolder) {
        LOG.info("Lees de Excel bestanden in en maak op basis hiervan een initiele vulling tabel");
        LOG.info("Aanmaken tabel als deze nog niet bestaat");
        gbavRepository.createInitVullingTable();

        final List<File> files = bepaalExcelBestanden(excelFolder);
        LOG.info("Aantal gevonden excel files: " + files.size());
        verwerkExcelBestanden(files);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean synchroniseerPersonen() throws ParseException {
        LOG.info("Start lezen en versturen (van set) LO3 synchronisatie berichten.");

        boolean doorgaan;
        final VerzendSynchronisatieBerichtVerwerker verwerker =
                new VerzendSynchronisatieBerichtVerwerker(destination, jmsTemplate, gbavRepository, ConversieResultaat.VERZONDEN);
        doorgaan = gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, verwerker, batchSize);

        LOG.info("Klaar met versturen (van set) van Lo3Berichten.");
        LOG.info(TOTAAL_VERWERKTE_BERICHTEN, verwerker.aantalBerichten());
        return doorgaan;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** INITIELE VULLING PERSONEN (EXCEL) *********************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    @SuppressWarnings("checkstyle:illegalcatch")
    private void verwerkExcelBestanden(final List<File> files) {
        for (final File file : files) {
            try {
                // Lees excel
                final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new FileInputStream(file));

                // Parsen input *ZONDER* syntax en precondite controles
                final List<Lo3Persoonslijst> lo3Persoonslijsten = new ArrayList<>();
                for (final ExcelData excelData : excelDatas) {
                    lo3Persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
                }

                for (final Lo3Persoonslijst pl : lo3Persoonslijsten) {
                    final String lg01 = formateerAlsLg01(pl);
                    final Lo3GemeenteCode gemeenteVanInschrijving = pl.getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving();
                    final Integer gemeenteVanInschrijvingCode;
                    if (gemeenteVanInschrijving.isValideNederlandseGemeenteCode()) {
                        gemeenteVanInschrijvingCode = Integer.parseInt(gemeenteVanInschrijving.getWaarde());
                    } else {
                        gemeenteVanInschrijvingCode = null;
                    }
                    gbavRepository.saveLg01(lg01, pl.getActueelAdministratienummer(), gemeenteVanInschrijvingCode, ConversieResultaat.TE_VERZENDEN);
                }
            } catch (final
                FileNotFoundException
                | ExcelAdapterException
                | Lo3SyntaxException
                | NumberFormatException e)
            {
                LOG.error("Probleem bij het inlezen van van de persoonslijst in file: " + file + ". Inlezen wordt voortgezet.", e);
            }
        }
    }

    private List<File> bepaalExcelBestanden(final String excelFolder) {
        final File inputFolder = new File(excelFolder);
        LOG.info(String.format("Input folder %s %s gevonden", inputFolder, inputFolder.exists() ? "is" : "niet"));
        final List<File> files = new ArrayList<>(FileUtils.listFiles(inputFolder, new String[] {"xls" }, true));
        Collections.sort(files);
        return files;
    }

    private String formateerAlsLg01(final Lo3Persoonslijst lo3) {
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(lo3);
        return Lo3Inhoud.formatInhoud(lg01.formatInhoud());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** INITIELE AUTORISATIES *********************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

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
                new VerzendAutorisatieBerichtVerwerker(destination, jmsTemplate, autorisatieRepository, ConversieResultaat.VERZONDEN);
        final boolean doorgaan = autorisatieRepository.verwerkAutorisatie(ConversieResultaat.TE_VERZENDEN, verwerker, batchSize);

        LOG.info("Klaar met versturen (van set) van AutorisatieBerichten.");
        LOG.info(TOTAAL_VERWERKTE_BERICHTEN, verwerker.aantalBerichten());

        return doorgaan;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** INITIELE AFNEMERSINDICATIES ***************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

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
                new VerzendAfnemersIndicatieBerichtVerwerker(destination, jmsTemplate, afnemersIndicatieRepository, ConversieResultaat.VERZONDEN);
        final boolean doorgaan = afnemersIndicatieRepository.verwerkAfnemerindicaties(ConversieResultaat.TE_VERZENDEN, verwerker, batchSize);

        LOG.info("Klaar met versturen (van set) van AfnemersindicatiesBerichten.");
        LOG.info(TOTAAL_VERWERKTE_BERICHTEN, verwerker.aantalBerichten());
        return doorgaan;
    }

}
