/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;

import org.springframework.stereotype.Service;

/**
 * Deze service bied de functionaliteit om het gemeente register op te halen.
 */
@Service
public class GemeenteRegisterService implements SynchronisatieBerichtService<LeesGemeenteRegisterVerzoekBericht, LeesGemeenteRegisterAntwoordBericht> {

    private static final DecimalFormat GEMEENTE_CODE_FORMAT = new DecimalFormat("0000");
    private static final DecimalFormat PARTIJ_CODE_FORMAT = new DecimalFormat("000000");

    private static final String RNI_GEMEENTE_CODE = GEMEENTE_CODE_FORMAT.format(1999);
    private static final String RNI_PARTIJ_CODE = PARTIJ_CODE_FORMAT.format(199901);

    @Inject
    private BrpDalService brpDalService;

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService#getVerzoekType()
     */
    @Override
    public final Class<LeesGemeenteRegisterVerzoekBericht> getVerzoekType() {
        return LeesGemeenteRegisterVerzoekBericht.class;
    }

    /**
     * Verwerk het lees verzoek.
     *
     * @param verzoek
     *            verzoek
     * @return antwoord
     */
    @Override
    public final LeesGemeenteRegisterAntwoordBericht verwerkBericht(final LeesGemeenteRegisterVerzoekBericht verzoek) {

        final LeesGemeenteRegisterAntwoordType type = new LeesGemeenteRegisterAntwoordType();
        type.setGemeenteRegister(new GemeenteRegisterType());
        final List<GemeenteType> gemeentenList = type.getGemeenteRegister().getGemeente();

        for (final Gemeente gemeente : brpDalService.geefAlleGemeenten()) {
            final GemeenteType gemeenteType = new GemeenteType();
            gemeenteType.setGemeenteCode(asGemeenteCode(gemeente.getCode()));
            gemeenteType.setPartijCode(asPartijCode(gemeente.getPartij().getCode()));

            if (gemeente.getPartij().getDatumOvergangNaarBrp() != null) {
                gemeenteType.setDatumBrp(BigInteger.valueOf(gemeente.getPartij().getDatumOvergangNaarBrp().longValue()));
            }

            gemeentenList.add(gemeenteType);

        }

        // Toevoegen RNI (is niet opgenomen als gemeente in BRP, wel in LO3)
        final GemeenteType rni = new GemeenteType();
        rni.setGemeenteCode(RNI_GEMEENTE_CODE);
        rni.setPartijCode(RNI_PARTIJ_CODE);
        gemeentenList.add(rni);

        final LeesGemeenteRegisterAntwoordBericht antwoord = new LeesGemeenteRegisterAntwoordBericht(type);
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

    private String asPartijCode(final int code) {
        return PARTIJ_CODE_FORMAT.format(code);
    }

    private String asGemeenteCode(final short code) {
        return GEMEENTE_CODE_FORMAT.format(code);
    }
}
