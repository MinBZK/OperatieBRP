/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtPersoonslijstWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtVerwerkingsResultaat;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.DefaultPersoonComparator;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.service.algemeen.Mappings;
import nl.bzk.brp.service.algemeen.util.MeldingUtil;
import nl.bzk.brp.service.bevraging.algemeen.BevragingCallback;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;

/**
 * BevragingCallback.
 */
public final class BevragingCallbackImpl implements BevragingCallback<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Map<SoortDienst, Comparator<BijgehoudenPersoon>> DIENST_COMPARATOR_MAP = Maps.newEnumMap(SoortDienst.class);

    static {
        DIENST_COMPARATOR_MAP.put(SoortDienst.GEEF_DETAILS_PERSOON, DefaultPersoonComparator.INSTANCE);
        DIENST_COMPARATOR_MAP.put(SoortDienst.ZOEK_PERSOON, ZoekPersoonComparator.INSTANCE);
        DIENST_COMPARATOR_MAP.put(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS, ZoekPersoonComparator.INSTANCE);
        DIENST_COMPARATOR_MAP.put(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON, ZoekPersoonComparator.INSTANCE);
    }

    private String xml;

    @Override
    public void verwerkResultaat(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat berichtResultaat) {
        final VerwerkPersoonBericht bericht = getVerwerkPersoonBericht(bevragingVerzoek, berichtResultaat);

        if (!Mappings.isBevragingSoortDienst(bevragingVerzoek.getSoortDienst())) {
            throw new UnsupportedOperationException("Incorrectie dienst voor Bevraging.");
        }
        final BerichtWriterTemplate
                template =
                new BerichtWriterTemplate(Mappings.getBevragingSoortBerichten(bevragingVerzoek.getSoortDienst()).getResponseElement());
        template.metResultaat(writer -> BerichtWriterTemplate.DEFAULT_RESULTAAT_CONSUMER.accept(bericht, writer));
        template.metInvullingDienstSpecifiekDeel(writer -> {
                    if (!bericht.getBijgehoudenPersonen().isEmpty()) {
                        writer.startElement("personen");
                        final List<BijgehoudenPersoon> gesorteerdePersonen = new ArrayList<>(bericht.getBijgehoudenPersonen());
                        gesorteerdePersonen.sort(DIENST_COMPARATOR_MAP.get(bevragingVerzoek.getSoortDienst()));
                        for (final BijgehoudenPersoon persoon : gesorteerdePersonen) {
                            BerichtPersoonslijstWriter.write(persoon.getBerichtElement(), writer);
                        }
                        writer.endElement();
                    }
                }
        );
        try {
            xml = template.toXML(bericht);
        } catch (final BerichtException e) {
            LOGGER.error(e.getMessage(), e);
            throw new XmlSchrijfException("FATAL: Maken antwoordbericht mislukt", e);
        }
    }

    private VerwerkPersoonBericht getVerwerkPersoonBericht(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat berichtResultaat) {
        VerwerkPersoonBericht leverBericht = berichtResultaat.getBericht();
        if (leverBericht == null) {
            //fout situatie
            //@formatter:off
            final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                    .metReferentienummer(UUID.randomUUID().toString())
                    .metCrossReferentienummer(bevragingVerzoek.getStuurgegevens().getReferentieNummer())
                    .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                    .metZendendePartij(berichtResultaat.getBrpPartij())
                    .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .eindeStuurgegevens()
                .metMeldingen(berichtResultaat.getMeldingen())
                .metResultaat(
                    BerichtVerwerkingsResultaat.builder()
                        .metVerwerking(VerwerkingsResultaat.FOUTIEF)
                        .metHoogsteMeldingsniveau(MeldingUtil.bepaalHoogsteMeldingNiveau(berichtResultaat
                                .getMeldingen())
                                .getNaam())
                        .build()
                    ).build();
            //@formatter:on
            leverBericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, Collections.emptyList()) ;
        }
        return leverBericht;
    }

    @Override
    public String getResultaat() {
        return xml;
    }
}
