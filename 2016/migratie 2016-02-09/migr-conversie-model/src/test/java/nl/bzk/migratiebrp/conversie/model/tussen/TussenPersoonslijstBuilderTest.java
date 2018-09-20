/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 */
public class TussenPersoonslijstBuilderTest extends TussenTestUtil {

    TussenPersoonslijstBuilder builder = new TussenPersoonslijstBuilder();

    @Test
    public void test() {
        Lo3Herkomst her = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1);
        List<TussenStapel<BrpNationaliteitInhoud>> nationaliteitList = new ArrayList<>();
        List<TussenStapel<BrpReisdocumentInhoud>> reisDocumentList = new ArrayList<>();
        List<TussenRelatie> tussenRelatieList = new ArrayList<>();
        List<TussenStapel<BrpVerificatieInhoud>> verificatieList = new ArrayList<>();
        List<TussenStapel<BrpIstRelatieGroepInhoud>> istKindList = new ArrayList<>();
        List<TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkGpList = new ArrayList<>();

        builder.adresStapel((TussenStapel<BrpAdresInhoud>) createTussenStapel(BrpAdresInhoudTest.createInhoud(), her));
        builder.behandeldAlsNederlanderIndicatieStapel((TussenStapel<BrpBehandeldAlsNederlanderIndicatieInhoud>) createTussenStapel(BrpBehandeldAlsNederlanderIndicatieInhoudTest.createInhoud(), her));
        builder.bijhoudingStapel((TussenStapel<BrpBijhoudingInhoud>) createTussenStapel(BrpBijhoudingInhoudTest.createInhoud(), her));
        builder.bijzondereVerblijfsrechtelijkePositieIndicatieStapel((TussenStapel<BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud>) createTussenStapel(BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoudTest.createInhoud(), her));
        builder.deelnameEuVerkiezingen((TussenStapel<BrpDeelnameEuVerkiezingenInhoud>) createTussenStapel(BrpDeelnameEuVerkiezingenInhoudTest.createInhoud(), her));
        builder.derdeHeeftGezagIndicatieStapel((TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud>) createTussenStapel(BrpDerdeHeeftGezagIndicatieInhoudTest.createInhoud(), her));
        builder.geboorteStapel((TussenStapel<BrpGeboorteInhoud>) createTussenStapel(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(), her));
        builder.geslachtsaanduidingStapel((TussenStapel<BrpGeslachtsaanduidingInhoud>) createTussenStapel(BrpGeslachtsaanduidingInhoudTest.createInhoud(), her));
        builder.identificatienummerStapel((TussenStapel<BrpIdentificatienummersInhoud>) createTussenStapel(BrpIdentificatienummersInhoudTest.createInhoud(), her));
        builder.inschrijvingStapel((TussenStapel<BrpInschrijvingInhoud>) createTussenStapel(BrpInschrijvingInhoudTest.createInhoud(), her));
        builder.istGezagsverhouding((TussenStapel<BrpIstGezagsVerhoudingGroepInhoud>) createTussenStapel(BrpIstGezagsVerhoudingGroepInhoudTest.createInhoud(), her));
        builder.istHuwelijkOfGpStapel((TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>) createTussenStapel(BrpIstHuwelijkOfGpGroepInhoudTest.createInhoud(), her));
        istHuwelijkGpList.add((TussenStapel<BrpIstHuwelijkOfGpGroepInhoud>) createTussenStapel(BrpIstHuwelijkOfGpGroepInhoudTest.createInhoud(), her));
        builder.istKindStapel((TussenStapel<BrpIstRelatieGroepInhoud>) createTussenStapel(BrpIstRelatieGroepInhoudTest.createInhoud(), her));
        istKindList.add((TussenStapel<BrpIstRelatieGroepInhoud>) createTussenStapel(BrpIstRelatieGroepInhoudTest.createInhoud(), her));
        builder.istOuder1((TussenStapel<BrpIstRelatieGroepInhoud>) createTussenStapel(BrpIstRelatieGroepInhoudTest.createInhoud(), her));
        builder.istOuder2((TussenStapel<BrpIstRelatieGroepInhoud>) createTussenStapel(BrpIstRelatieGroepInhoudTest.createInhoud(), her));
        builder.migratieStapel((TussenStapel<BrpMigratieInhoud>) createTussenStapel(BrpMigratieInhoudTest.createInhoud(), her));
        builder.naamgebruikStapel((TussenStapel<BrpNaamgebruikInhoud>) createTussenStapel(BrpNaamgebruikInhoudTest.createInhoud(), her));
        builder.nationaliteitStapel((TussenStapel<BrpNationaliteitInhoud>) createTussenStapel(BrpNationaliteitInhoudTest.createInhoud(), her));
        nationaliteitList.add((TussenStapel<BrpNationaliteitInhoud>) createTussenStapel(BrpNationaliteitInhoudTest.createInhoud(), her));
        builder.nummerverwijzingStapel((TussenStapel<BrpNummerverwijzingInhoud>) createTussenStapel(BrpNummerverwijzingInhoudTest.createInhoud(), her));
        builder.onderCurateleIndicatieStapel((TussenStapel<BrpOnderCurateleIndicatieInhoud>) createTussenStapel(BrpOnderCurateleIndicatieInhoudTest.createInhoud(), her));
        builder.overlijdenStapel((TussenStapel<BrpOverlijdenInhoud>) createTussenStapel(BrpOverlijdenInhoudTest.createInhoud(), her));
        builder.reisdocumentStapel((TussenStapel<BrpReisdocumentInhoud>) createTussenStapel(BrpReisdocumentInhoudTest.createInhoud(), her));
        reisDocumentList.add((TussenStapel<BrpReisdocumentInhoud>) createTussenStapel(BrpReisdocumentInhoudTest.createInhoud(), her));
        builder.relatie(TussenRelatieTest.createTussenRelatie(BrpSoortRelatieCode.HUWELIJK, BrpSoortBetrokkenheidCode.PARTNER));
        tussenRelatieList.add(TussenRelatieTest.createTussenRelatie(BrpSoortRelatieCode.HUWELIJK, BrpSoortBetrokkenheidCode.PARTNER));
        builder.samengesteldeNaamStapel((TussenStapel<BrpSamengesteldeNaamInhoud>) createTussenStapel(BrpSamengesteldeNaamInhoudTest.createInhoud(), her));
        builder.persoonAfgeleidAdministratiefStapel((TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud>) createTussenStapel(BrpPersoonAfgeleidAdministratiefInhoudTest.createInhoud(), her));
        builder.persoonskaartStapel((TussenStapel<BrpPersoonskaartInhoud>) createTussenStapel(BrpPersoonskaartInhoudTest.createInhoud(), her));
        builder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel((TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud>) createTussenStapel(BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoudTest.createInhoud(), her));
        builder.staatloosIndicatieStapel((TussenStapel<BrpStaatloosIndicatieInhoud>) createTussenStapel(BrpStaatloosIndicatieInhoudTest.createInhoud(), her));
        builder.uitsluitingKiesrecht((TussenStapel<BrpUitsluitingKiesrechtInhoud>) createTussenStapel(BrpUitsluitingKiesrechtInhoudTest.createInhoud(), her));
        builder.vastgesteldNietNederlanderIndicatieStapel((TussenStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>) createTussenStapel(BrpVastgesteldNietNederlanderIndicatieInhoudTest.createInhoud(false), her));
        builder.verblijfsrechtStapel((TussenStapel<BrpVerblijfsrechtInhoud>) createTussenStapel(BrpVerblijfsrechtInhoudTest.createInhoud(), her));
        builder.verificatieStapel((TussenStapel<BrpVerificatieInhoud>) createTussenStapel(BrpVerificatieInhoudTest.createInhoud(), her));
        verificatieList.add((TussenStapel<BrpVerificatieInhoud>) createTussenStapel(BrpVerificatieInhoudTest.createInhoud(), her));
        builder.verstrekkingsbeperkingIndicatieStapel((TussenStapel<BrpVerstrekkingsbeperkingIndicatieInhoud>) createTussenStapel(BrpVerstrekkingsbeperkingIndicatieInhoudTest.createInhoud(), her));
        TussenPersoonslijst tp_1 = builder.build();
        assertNotNull(tp_1);
        builder = new TussenPersoonslijstBuilder(tp_1);
        builder.nationaliteitStapels(nationaliteitList);
        builder.reisdocumentStapels(reisDocumentList);
        builder.relaties(tussenRelatieList);
        builder.verificatieStapels(verificatieList);
        builder.istKindStapels(istKindList);
        builder.istHuwelijkOfGpStapels(istHuwelijkGpList);
        TussenPersoonslijst tp_2 = builder.build();
        assertTrue(vergelijkTP(tp_1, tp_2));

    }


    public boolean vergelijkTP(TussenPersoonslijst tp1, TussenPersoonslijst tp2) {
        if (tp1.getNationaliteitStapels().size() != tp2.getNationaliteitStapels().size() ||
                tp1.getReisdocumentStapels().size() != tp2.getReisdocumentStapels().size() ||
                tp1.getVerificatieStapels().size() != tp2.getVerificatieStapels().size() ||
                tp1.getIstKindStapels().size() != tp2.getIstKindStapels().size() ||
                tp1.getIstHuwelijkOfGpStapels().size() != tp2.getIstHuwelijkOfGpStapels().size() ||
                tp1.getRelaties().size() != tp2.getRelaties().size()) {
            return false;
        }
        for (int i = 0; i < tp1.getNationaliteitStapels().size(); i++) {
            TussenStapel<BrpNationaliteitInhoud> ts1 = tp1.getNationaliteitStapels().get(i);
            TussenStapel<BrpNationaliteitInhoud> ts2 = tp2.getNationaliteitStapels().get(i);
            if (ts1.getGroepen().size() != ts2.getGroepen().size()) {
                return false;
            }
            for (int j = 0; j < ts1.getGroepen().size(); j++) {
                if (!ts2.getGroepen().contains(ts1.get(j))) {
                    return false;
                }

            }

        }
        return true;
    }


}