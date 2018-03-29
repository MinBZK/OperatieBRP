/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
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

@ContextConfiguration("classpath:/uc202-test-beans.xml")
public abstract class AbstractUc202Test extends AbstractUcTest {

    protected AbstractUc202Test() {
        super("/uc202/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @Override
    protected String generateMessageId() {
        return UUID.randomUUID().toString();
    }

    /* *** LG01 *************************************************************************************** */

    protected Lg01Bericht maakLg01(
            final String aNummerKop,
            final String aNummerInhoud,
            final String vorigANummerKop,
            final String vorigANummerInhoud,
            final String bronPartijCode,
            final String gemeenteInhoud) {
        return maakLg01(aNummerKop, aNummerInhoud, vorigANummerKop, vorigANummerInhoud, null, bronPartijCode, gemeenteInhoud);
    }

    protected Lg01Bericht maakLg01(
            final String aNummerKop,
            final String aNummerInhoud,
            final String vorigANummerKop,
            final String vorigANummerInhoud,
            final String burgerServicenummer,
            final String bronPartijCode,
            final String gemeenteInhoud) {

        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setMessageId(generateMessageId());
        lg01.setHeader(Lo3HeaderVeld.A_NUMMER, aNummerKop);
        lg01.setHeader(Lo3HeaderVeld.OUD_A_NUMMER, vorigANummerKop);
        lg01.setLo3Persoonslijst(maakPersoonslijst(aNummerInhoud, vorigANummerInhoud, burgerServicenummer, gemeenteInhoud));
        lg01.setBronPartijCode(bronPartijCode);
        lg01.setDoelPartijCode("199902");

        final String formattedLg01 = lg01.format();

        try {
            lg01.parse(formattedLg01);
        } catch (
                BerichtSyntaxException
                        | BerichtInhoudException e) {
            e.printStackTrace();
            return null;
        }

        return lg01;
    }

    protected Lo3Persoonslijst maakPersoonslijst(final String aNummerInhoud, final String vorigANummerInhoud, final String gemeenteInhoud) {

        return maakPersoonslijst(aNummerInhoud, "313131313", vorigANummerInhoud, gemeenteInhoud);
    }

    protected Lo3Persoonslijst maakPersoonslijst(final String aNummerInhoud, final String vorigANummerInhoud, final String burgerServicenummer,
                                                 final String gemeenteInhoud) {

        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        // @formatter:off
        builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper.lo3Persoon(aNummerInhoud,
                burgerServicenummer,
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

    /* *** SYNC NAAR BRP******************************************************************************************** */

    protected SynchroniseerNaarBrpAntwoordBericht maakSynchroniseerNaarBrpAntwoordBericht(
            final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek,
            final StatusType status,
            final List<Kandidaat> kandidaten) {
        final SynchroniseerNaarBrpAntwoordBericht result = new SynchroniseerNaarBrpAntwoordBericht();
        result.setStatus(status);
        result.setMessageId(generateMessageId());
        result.setCorrelationId(synchroniseerNaarBrpVerzoek.getMessageId());
        result.setKandidaten(kandidaten);

        return result;
    }

    protected List<Kandidaat> maakKandidaten(final String... aNummers) {
        if (aNummers == null || aNummers.length == 0) {
            return null;
        }

        final List<Kandidaat> result = new ArrayList<>();

        for (final String aNummer : aNummers) {
            final Lo3Persoonslijst pl = maakPersoonslijst(aNummer, null, "0599");
            final Kandidaat kandidaat = new Kandidaat();
            kandidaat.setPersoonId(Integer.valueOf(aNummer));
            kandidaat.setVersie(1);
            kandidaat.setLo3PersoonslijstAlsTeletexString(plToString(pl));

            result.add(kandidaat);
        }

        return result;
    }

    private String plToString(final Lo3Persoonslijst pl) {
        return Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(pl));
    }

    /* *** BLOKKERING INFO ***************************************************************************************** */

    protected BlokkeringInfoAntwoordBericht maakBlokkeringInfoAntwoordBericht(
            final BlokkeringInfoVerzoekBericht blokkeringInfoVerzoek,
            final PersoonsaanduidingType persoonsaanduiding,
            final String processId,
            final String gemeenteNaar) {
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

}
