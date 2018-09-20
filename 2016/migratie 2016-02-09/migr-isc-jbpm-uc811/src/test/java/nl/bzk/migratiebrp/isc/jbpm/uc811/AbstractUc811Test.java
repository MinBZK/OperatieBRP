/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht.Foutreden;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.BlokkeringInfoVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.DeblokkeringVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractUcTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:/uc811-test-beans.xml")
public abstract class AbstractUc811Test extends AbstractUcTest {

    protected AbstractUc811Test() {
        super("/uc811/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    /*
     * *** Uc811 Bericht *****************************************************************************************
     */
    protected Uc811Bericht maakUc811Bericht(final String gemeenteCode, final Long aNummer) {
        final Uc811Bericht uc811Bericht = new Uc811Bericht();
        uc811Bericht.setANummer(aNummer);
        uc811Bericht.setGemeenteCode(gemeenteCode);

        return uc811Bericht;
    }

    /*
     * *** La01 *****************************************************************************************
     */

    protected Lo3Persoonslijst maakPersoonslijst(final Long aNummerInhoud, final Long vorigANummerInhoud, final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                                                                                                          null,
                                                                                                          "Jan",
                                                                                                          null,
                                                                                                          null,
                                                                                                          "Jansen",
                                                                                                          19700101,
                                                                                                          "0518",
                                                                                                          "6030",
                                                                                                          "M",
                                                                                                          vorigANummerInhoud,
                                                                                                          null,
                                                                                                          "E"),
                                                                               Lo3StapelHelper.lo3Akt(1),
                                                                               Lo3StapelHelper.lo3His(19700101),
                                                                               new Lo3Herkomst(Lo3CategorieEnum.PERSOON, 0, 0))));

        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Verblijfplaats(gemeenteInhoud,
                                                                                                                        1970101,
                                                                                                                        1970101,
                                                                                                                        "Straat",
                                                                                                                        15,
                                                                                                                        "9876AA",
                                                                                                                        "I"),
                                                                                      null,
                                                                                      Lo3StapelHelper.lo3His(19700101),
                                                                                      new Lo3Herkomst(Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0))));
        // @formatter:on

        return builder.build();
    }

    protected String plToString(final Lo3Persoonslijst pl) {
        return Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(pl));
    }

    protected La01Bericht maakLa01Bericht(final Lq01Bericht lq01Bericht, final boolean vulPersoonslijst) {
        final La01Bericht la01Bericht = new La01Bericht();
        la01Bericht.setBronGemeente(lq01Bericht.getDoelGemeente());
        la01Bericht.setDoelGemeente(lq01Bericht.getBronGemeente());
        if (vulPersoonslijst) {
            la01Bericht.setLo3Persoonslijst(maakPersoonslijst(Long.valueOf(lq01Bericht.getANummer()), null, lq01Bericht.getDoelGemeente()));
        }
        la01Bericht.setCorrelationId(lq01Bericht.getMessageId());
        la01Bericht.setMessageId(generateMessageId());

        return la01Bericht;
    }

    /*
     * *** Lf01 ********************************************************************************************
     */

    protected Lf01Bericht maakLf01Bericht(final Lq01Bericht lq01Bericht, final Foutreden foutreden, final String gemeente) {
        final Lf01Bericht bericht = new Lf01Bericht();
        bericht.setMessageId(generateMessageId());
        bericht.setCorrelationId(lq01Bericht.getMessageId());
        bericht.setBronGemeente(lq01Bericht.getDoelGemeente());
        bericht.setDoelGemeente(lq01Bericht.getBronGemeente());
        bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden.toString());
        bericht.setHeader(Lo3HeaderVeld.GEMEENTE, gemeente);

        return bericht;
    }

    /* *** BLOKKERING INFO ***************************************************************************************** */

    protected BlokkeringInfoAntwoordBericht maakBlokkeringInfoAntwoordBericht(
        final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek,
        final PersoonsaanduidingType persoonsaanduiding,
        final String processId,
        final String gemeenteNaar)
    {
        final BlokkeringInfoAntwoordBericht result = new BlokkeringInfoAntwoordBericht();
        result.setStatus(StatusType.OK);
        result.setPersoonsaanduiding(persoonsaanduiding);
        result.setProcessId(processId);
        result.setGemeenteNaar(gemeenteNaar);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(blokkeringInfoVerzoek.getMessageId());

        return result;
    }

    /* *** DEBLOKKERING ******************************************************************************************** */

    protected DeblokkeringAntwoordBericht maakDeblokkeringAntwoordBericht(final DeblokkeringVerzoekBericht deblokkeringVerzoek) {
        final DeblokkeringAntwoordBericht result = new DeblokkeringAntwoordBericht();
        result.setStatus(StatusType.OK);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(deblokkeringVerzoek.getMessageId());

        return result;
    }

    /* *** SYNC NAAR BRP******************************************************************************************** */

    protected SynchroniseerNaarBrpAntwoordBericht maakSynchroniseerNaarBrpAntwoordBericht(
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek,
        final StatusType status,
        final List<String> persoonslijsten)
    {
        final SynchroniseerNaarBrpAntwoordBericht result = new SynchroniseerNaarBrpAntwoordBericht();
        result.setStatus(status);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(synchroniseerNaarBrpVerzoek.getMessageId());
        result.setKandidaten(persoonslijsten);

        return result;
    }

    protected List<String> maakPersoonslijsten(final Long... aNummers) {
        if (aNummers == null || aNummers.length == 0) {
            return null;
        }

        final List<String> result = new ArrayList<>();

        for (final Long aNummer : aNummers) {
            final Lo3Persoonslijst pl = maakPersoonslijst(aNummer, null, "0599");
            result.add(plToString(pl));
        }

        return result;
    }

}
