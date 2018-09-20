/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterVoorkomenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienstbundel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.SoortDienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import org.springframework.stereotype.Service;

/**
 * Deze service bied de functionaliteit om het afnemer register op te halen.
 */
@Service
public class AutorisatieRegisterService
        implements SynchronisatieBerichtService<LeesAutorisatieRegisterVerzoekBericht, LeesAutorisatieRegisterAntwoordBericht>
{
    private static final DecimalFormat PARTIJ_CODE_FORMAT = new DecimalFormat("000000");

    @Inject
    private BrpDalService brpDalService;

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public final Class<LeesAutorisatieRegisterVerzoekBericht> getVerzoekType() {
        return LeesAutorisatieRegisterVerzoekBericht.class;
    }

    /**
     * Verwerk het lees verzoek.
     *
     * @param verzoek
     *            verzoek
     * @return antwoord
     */
    @Override
    public final LeesAutorisatieRegisterAntwoordBericht verwerkBericht(final LeesAutorisatieRegisterVerzoekBericht verzoek) {
        final LeesAutorisatieRegisterAntwoordType type = new LeesAutorisatieRegisterAntwoordType();
        type.setAutorisatieRegister(new AutorisatieRegisterType());

        // Verwerk alle actieve gba autorisaties
        for (final Leveringsautorisatie leveringsautorisatie : brpDalService.geefAlleGbaAutorisaties()) {
            for (final ToegangLeveringsAutorisatie toegang : leveringsautorisatie.getToegangLeveringsautorisatieSet()) {
                final String partijCode = PARTIJ_CODE_FORMAT.format(toegang.getGeautoriseerde().getPartij().getCode());
                type.getAutorisatieRegister().getAutorisatie().add(maakAutorisatieRegisterVoorkomenType(partijCode, toegang));
            }
        }
        Collections.sort(type.getAutorisatieRegister().getAutorisatie(), new AutorisatieTypeComparator());

        final LeesAutorisatieRegisterAntwoordBericht antwoord = new LeesAutorisatieRegisterAntwoordBericht(type);
        antwoord.setStatus(StatusType.OK);
        antwoord.setMessageId(MessageId.generateSyncMessageId());
        antwoord.setCorrelationId(verzoek.getMessageId());
        return antwoord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

    private AutorisatieRegisterVoorkomenType maakAutorisatieRegisterVoorkomenType(
        final String partijCode,
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie)
    {
        final AutorisatieRegisterVoorkomenType resultaat = new AutorisatieRegisterVoorkomenType();
        resultaat.setPartijCode(partijCode);
        resultaat.setToegangLeveringsautorisatieId(toegangLeveringsautorisatie.getId());
        resultaat.setPlaatsenAfnemersindicatiesDienstId(
            bepaalDienstId(toegangLeveringsautorisatie.getLeveringsautorisatie(), SoortDienst.PLAATSEN_AFNEMERINDICATIE));
        resultaat.setVerwijderenAfnemersindicatiesDienstId(
            bepaalDienstId(toegangLeveringsautorisatie.getLeveringsautorisatie(), SoortDienst.VERWIJDEREN_AFNEMERINDICATIE));
        resultaat.setBevragenPersoonDienstId(bepaalDienstId(toegangLeveringsautorisatie.getLeveringsautorisatie(), SoortDienst.ZOEK_PERSOON));
        resultaat.setBevragenAdresDienstId(
            bepaalDienstId(toegangLeveringsautorisatie.getLeveringsautorisatie(), SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS));
        return resultaat;
    }

    private Integer bepaalDienstId(final Leveringsautorisatie leveringsautorisatie, final SoortDienst soortDienst) {
        for (final Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundelSet()) {
            for (final Dienst dienst : dienstbundel.getDienstSet()) {
                if (soortDienst == dienst.getSoortDienst()) {
                    return dienst.getId();
                }
            }
        }

        return null;
    }

    /**
     * Autorisatie vergelijker.
     */
    private static class AutorisatieTypeComparator implements Comparator<AutorisatieRegisterVoorkomenType>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final AutorisatieRegisterVoorkomenType o1, final AutorisatieRegisterVoorkomenType o2) {
            return o1.getPartijCode().compareTo(o2.getPartijCode());
        }
    }
}
