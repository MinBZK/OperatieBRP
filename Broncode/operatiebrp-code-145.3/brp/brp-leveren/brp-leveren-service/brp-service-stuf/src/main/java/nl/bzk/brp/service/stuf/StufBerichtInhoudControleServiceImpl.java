/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.service.algemeen.request.SchemaValidatorService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.stamgegevens.StamTabelService;
import org.springframework.stereotype.Service;

/**
 * Controleer inhoud van het stuf bericht.
 */
@Service
@Bedrijfsregel(Regel.R2439)
@Bedrijfsregel(Regel.R2440)
@Bedrijfsregel(Regel.R2441)
@Bedrijfsregel(Regel.R2442)
@Bedrijfsregel(Regel.R2443)
@Bedrijfsregel(Regel.R2444)
final class StufBerichtInhoudControleServiceImpl implements StufBerichtInhoudControleService {

    /**
     * Het {@link Schema} tbv request validatie.
     */
    private static final Schema SCHEMA = SchemaValidatorService.maakSchema("/xsd/BRP0200/brp0200_lvgSynchronisatie_Berichten.xsd");

    private static final Logger LOGGER = LoggerFactory.getLogger();


    @Inject
    private StamTabelService stamTabelService;

    @Inject
    private SchemaValidatorService schemaValidatorService;

    private StufBerichtInhoudControleServiceImpl() {
    }

    @Override
    public void controleerInhoud(final StufBerichtVerzoek verzoek) throws StapMeldingException {
        controleerXmlInhoud(verzoek);
        controleerVertalingBerichtSoort(verzoek);
        final Map<String, Object>
                versieStufStamgegeven =
                bepaalStamgegeven(verzoek.getParameters().getVersieStufbg(), Element.VERSIESTUFBG, Element.VERSIESTUFBG_NUMMER);
        if (versieStufStamgegeven == null) {
            throw new StapMeldingException(Regel.R2439);
        } else if (!
                stamgegevenGeldig(versieStufStamgegeven)) {
            throw new StapMeldingException(Regel.R2440);
        }

    }

    private void controleerVertalingBerichtSoort(StufBerichtVerzoek verzoek) throws StapMeldingException {
        final Map<String, Object>
                soortBerichtStamgegeven =
                bepaalStamgegeven(verzoek.getParameters().getVertalingBerichtsoortBRP(), Element.VERTALINGBERICHTSOORTBRP,
                        Element.VERTALINGBERICHTSOORTBRP_NAAM);
        if (soortBerichtStamgegeven == null) {
            throw new StapMeldingException(Regel.R2441);
        } else if (!stamgegevenGeldig(soortBerichtStamgegeven)) {
            throw new StapMeldingException(Regel.R2442);
        } else if (verzoek.getStufBericht().getSoortSynchronisatie() == null || !verzoek.getStufBericht().getSoortSynchronisatie()
                .equals(verzoek.getParameters().getVertalingBerichtsoortBRP())) {
            throw new StapMeldingException(Regel.R2444);
        }
    }

    private void controleerXmlInhoud(StufBerichtVerzoek verzoek) throws StapMeldingException {
        if (verzoek.getStufBericht().getInhoud() == null) {
            throw new StapMeldingException(Regel.R2443);
        }
        try {
            schemaValidatorService.valideer(new StreamSource(new StringReader(verzoek.getStufBericht().getInhoud())), SCHEMA);
        } catch (final SchemaValidatorService.SchemaValidatieException e) {
            LOGGER.debug("Foutief stuf xml verzoek ontvangen", e);
            throw new StapMeldingException(Regel.R2443);
        }
    }

    private Map<String, Object> bepaalStamgegeven(final String berichtWaarde, final Element stamgegeven, final Element attribuut) {
        final StamtabelGegevens
                stamtabelGegevensSoortBericht =
                stamTabelService.geefStamgegevens(stamgegeven.getNaam() + StamtabelGegevens.TABEL_POSTFIX);
        final List<Map<String, Object>> gegevens = stamtabelGegevensSoortBericht.getStamgegevens();
        for (final Map<String, Object> gegeven : gegevens) {
            final Object waarde = gegeven.get(attribuut.getElementWaarde().getIdentdb().toLowerCase());
            if (waarde != null && berichtWaarde.equals(waarde.toString())) {
                return gegeven;
            }
        }
        return null;
    }

    private static boolean stamgegevenGeldig(final Map<String, Object> gegeven) {
        final Integer datAanvGeldigheid = (Integer) gegeven.get("dataanvgel");
        final Integer datEindeGeldigheid = (Integer) gegeven.get("dateindegel");
        return beginDatumCorrect(datAanvGeldigheid) && eindDatumCorrect(datEindeGeldigheid);
    }

    private static boolean eindDatumCorrect(final Integer datEindeGeldigheid) {
        return datEindeGeldigheid == null || datEindeGeldigheid > DatumUtil.vandaag();
    }

    private static boolean beginDatumCorrect(final Integer datAanvGeldigheid) {
        return datAanvGeldigheid == null || datAanvGeldigheid <= DatumUtil.vandaag();
    }
}
