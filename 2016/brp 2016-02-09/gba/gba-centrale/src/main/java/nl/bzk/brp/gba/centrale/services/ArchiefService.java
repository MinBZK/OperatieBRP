/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.dataaccess.repository.archivering.BerichtRepository;
import nl.bzk.brp.gba.centrale.berichten.GbaArchiveringVerzoek;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import org.springframework.jms.JmsException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verwerk archivering verzoek.
 */
public final class ArchiefService implements GbaService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Inject
    private BerichtRepository berichtRepository;
    @Inject
    private ReferentieDataRepository referentieRepository;

    @Override
    @Transactional(transactionManager = "lezenSchrijvenTransactionManager")
    public String verwerk(final String bericht, final String berichtReferentie) {
        // Unmarshal JSON
        nl.bzk.brp.gba.centrale.berichten.GbaArchiveringVerzoek verzoek;
        try {
            verzoek = JSON_MAPPER.readValue(bericht, GbaArchiveringVerzoek.class);
        } catch (final IOException e) {
            throw new JmsException("Kan bericht niet deserialiseren naar GbaArchiveringVerzoek.", e) {
                private static final long serialVersionUID = 1L;
            };
        }

        // Archiveer
        berichtRepository.save(converteerNaarBerichtModel(verzoek));

        // Geen antwoord
        return null;
    }

    private BerichtModel converteerNaarBerichtModel(final GbaArchiveringVerzoek verzoek) {
        final BerichtModel bericht =
                new BerichtModel(converteerNaarSoortBerichtAttribuut(verzoek.getSoortBericht()), new RichtingAttribuut(verzoek.getRichting()));

        bericht.setStuurgegevens(
            new BerichtStuurgegevensGroepModel(
                bepaalPartijId(verzoek.getZendendePartijCode()),
                converteerNaarSysteemNaamAttribuut(verzoek.getZendendeSysteem()),
                bepaalPartijId(verzoek.getOntvangendePartijCode()),
                converteerNaarSysteemNaamAttribuut(verzoek.getOntvangendeSysteem()),
                converteerNaarReferentienummerAttribuut(verzoek.getReferentienummer()),
                converteerNaarReferentienummerAttribuut(verzoek.getCrossReferentienummer()),
                converteerNaarDatumTijdAttribuut(verzoek.getTijdstipVerzending()),
                converteerNaarDatumTijdAttribuut(verzoek.getTijdstipOntvangst())));

        bericht.setStandaard(new BerichtStandaardGroepModel(null, new BerichtdataAttribuut(verzoek.getData()), null));
        return bericht;
    }

    private Short bepaalPartijId(final Integer partijCode) {
        if (partijCode == null) {
            return null;
        }

        final Partij partij = referentieRepository.vindPartijOpCode(new PartijCodeAttribuut(partijCode));
        return partij.getID();
    }

    private SoortBerichtAttribuut converteerNaarSoortBerichtAttribuut(final String soortBericht) {
        SoortBerichtAttribuut result;
        if (soortBericht == null || "".equals(soortBericht)) {
            result = null;
        } else {
            try {
                result = new SoortBerichtAttribuut(SoortBericht.valueOf(soortBericht.toUpperCase()));
            } catch (final IllegalArgumentException e) {
                LOGGER.warn("Soort bericht {} niet bekend. Soort bericht wordt niet opgeslagen.", soortBericht);
                result = null;
            }
        }
        return result;
    }

    private SysteemNaamAttribuut converteerNaarSysteemNaamAttribuut(final String systeemNaam) {
        return systeemNaam == null || "".equals(systeemNaam) ? null : new SysteemNaamAttribuut(systeemNaam);
    }

    private ReferentienummerAttribuut converteerNaarReferentienummerAttribuut(final String referentienummer) {
        return referentienummer == null || "".equals(referentienummer) ? null : new ReferentienummerAttribuut(referentienummer);
    }

    private DatumTijdAttribuut converteerNaarDatumTijdAttribuut(final Date datumTijd) {
        return datumTijd == null ? null : new DatumTijdAttribuut(datumTijd);
    }
}
