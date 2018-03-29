package nl.bzk.brp.distributie.shaded.tools.mailboxclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.tools.neoload.BerichtFacade;
import nl.bzk.migratiebrp.tools.neoload.MailboxFacade;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.io.IOUtils;

/**
 * Bericht versturen.
 */
final class CommandSend implements Command {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Send a message.
     * @param mailbox mailbox
     * @param mainOptions main options
     * @param arguments command arguments
     */
    public void execute(final MailboxFacade mailbox, final CommandLine mainOptions, final String[] arguments) {
        final Configuration configuration = new ConfigurationSend();
        final CommandLine sendOptions = configuration.parse(arguments);
        if (sendOptions == null) {
            configuration.printHelp();
            return;
        }

        final BerichtFacade bericht = new BerichtFacade();
        bericht.setVerzendendeMailbox(mainOptions.getOptionValue(ConfigurationMain.MAILBOX_OPTION));
        bericht.setOntvangendeMailbox(sendOptions.getOptionValue(ConfigurationSend.RECIPIENT));
        bericht.setMessageId(sendOptions.getOptionValue(ConfigurationSend.MESSAGE_ID));
        bericht.setCorrelationId(sendOptions.getOptionValue(ConfigurationSend.CORRELATION_ID));
        if (sendOptions.hasOption(ConfigurationSend.FILE)) {
            // read data from file
            try {
                bericht.setInhoud(readFile(sendOptions.getOptionValue(ConfigurationSend.FILE)));
            } catch (IOException e) {
                LOG.error("Onverwachte fout tijdens inlezen bestand", e);
                return;
            }
        } else {
            bericht.setInhoud(sendOptions.getArgList().stream().collect(Collectors.joining()));
        }
        try {
            mailbox.verstuur(mainOptions.getOptionValue(ConfigurationMain.PASS_OPTION), bericht);
        } catch (VoaException e) {
            LOG.error("Onverwachte fout tijdens versturen bericht", e);
        }
    }

    private static String readFile(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            if (filename.endsWith(".xls")) {
                final ExcelAdapter excelAdapter = new ExcelAdapterImpl();
                final List<ExcelData> excelDatas;
                try {
                    excelDatas = excelAdapter.leesExcelBestand(fis);
                } catch (ExcelAdapterException | Lo3SyntaxException e) {
                    throw new IOException("Onverwachte fout bij lezen excel bestand", e);
                }
                if (excelDatas.isEmpty()) {
                    throw new IOException("Excel bestand bevatte geen lo3 bericht");
                } else if (excelDatas.size() > 1) {
                    LOG.warn("Excel bestand bevatte meerdere lo3 berichten; enkel de eerste wordt verstuurd!");
                }
                return maakBericht(excelDatas.get(0));
            } else {
                return IOUtils.toString(fis);
            }
        }
    }

    private static String maakBericht(ExcelData excelData) throws IOException {
        final String header = Arrays.asList(excelData.getHeaders()).stream().collect(Collectors.joining());
        if (header.isEmpty()) {
            throw new IOException("Lo3 bericht in excel bestand bevatte geen header");
        }
        return header + Lo3Inhoud.formatInhoud(excelData.getCategorieLijst());
    }
}
