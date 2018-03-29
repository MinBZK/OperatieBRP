/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.regels.tabel.ConversietabelFactoryImpl;

public abstract class AbstractLo3ToevalligeGebeurtenisProconditiesTest extends AbstractPreconditieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    protected Lo3ToevalligeGebeurtenisPrecondities subject = new Lo3ToevalligeGebeurtenisPrecondities(new ConversietabelFactoryImpl());

    protected void controleer(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis, final SoortMeldingCode soortMeldingCode) {

        subject.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);

        if (!Logging.getLogging().getRegels().isEmpty()) {
            LOGGER.info("Gevonden regels: ");
            for (final LogRegel regel : Logging.getLogging().getRegels()) {
                LOGGER.info(" - {}", regel);
            }
        }

        if (soortMeldingCode == null) {
            assertGeenLogRegels();
        } else {
            assertAantalInfos(0);
            assertAantalWarnings(0);
            assertAantalErrors(1);
            assertSoortMeldingCode(soortMeldingCode, 1);
        }
    }
}
