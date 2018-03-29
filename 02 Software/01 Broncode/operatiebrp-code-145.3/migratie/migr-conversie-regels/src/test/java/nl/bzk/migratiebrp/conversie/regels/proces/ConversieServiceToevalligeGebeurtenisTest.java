/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3ToevalligeGebeurtenisParser;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConversieServiceToevalligeGebeurtenisTest extends AbstractComponentTest {

    @Inject
    private Lo3SyntaxControle syntaxControle;
    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Inject
    private PreconditiesService preconditiesService;

    @Before
    public void setup() {
        Logging.initContext();
    }

    @After
    public void destroy() {
        Logging.destroyContext();
    }

    @Test
    public void test3B() throws Exception {

        final String
                tb02Inhoud =
                "00418011060110010159525750501200091265564530210007Jantine0240007Hermans03100081996010103200040599033000460300410001V051930110010504206569701200095470648090210003Jan0240005Steen03100081996010103200040599033000460300410001M07100082016010107200040518073000460300740001S1510001H8110004051881200073 B1234851000820160101551040210003Jan0240005Steen031000819960101032000405990330004603006100082015010106200040518063000460301510001H";

        final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis = verwerk("3 B1234", "0599", "0518", tb02Inhoud);

        Assert.assertNotNull("Toevallige gebeurtenis moet gevuld zijn.", brpToevalligeGebeurtenis);
        Assert.assertNotNull("Verbintenis moet gevuld zijn.", brpToevalligeGebeurtenis.getVerbintenis());
        Assert.assertNotNull("Partner moet gevuld zijn.", brpToevalligeGebeurtenis.getVerbintenis().getPartner());
        Assert.assertNotNull("Anummer moet gevuld zijn.", brpToevalligeGebeurtenis.getVerbintenis().getPartner().getAdministratienummer());
    }

    private BrpToevalligeGebeurtenis verwerk(
            final String aktenummer,
            final String ontvangendeGemeente,
            final String verzendendeGemeente,
            final String tb02Inhoud) throws Exception {
        // Parse teletex string naar lo3 categorieen
        final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(tb02Inhoud);

        // Controleer syntax obv lo3 categorieen
        final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);

        final Lo3ToevalligeGebeurtenis lo3ToevalligeGebeurtenis =
                new Lo3ToevalligeGebeurtenisParser().parse(
                        lo3InhoudNaSyntaxControle,
                        new Lo3String(aktenummer),
                        new Lo3GemeenteCode(ontvangendeGemeente),
                        new Lo3GemeenteCode("0518"));

        preconditiesService.verwerk(lo3ToevalligeGebeurtenis);
        return converteerLo3NaarBrpService.converteerLo3ToevalligeGebeurtenis(lo3ToevalligeGebeurtenis);

    }

}
