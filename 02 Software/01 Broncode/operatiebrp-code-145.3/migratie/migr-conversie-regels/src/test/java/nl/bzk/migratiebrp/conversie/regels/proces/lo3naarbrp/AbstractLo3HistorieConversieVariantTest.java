/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conversie-test.xml")
public abstract class AbstractLo3HistorieConversieVariantTest {

    private static final BrpString ANUMMER = new BrpString("1000000000");
    private static final BrpString BSN = new BrpString("123456789");
    private List<TussenGroep<BrpIdentificatienummersInhoud>> invoer;
    private List<BrpGroep<BrpIdentificatienummersInhoud>> resultaat;

    @Before
    public void setup() {
        invoer = new ArrayList<>();
        resultaat = new ArrayList<>();
    }

    protected void maakLo3Groep(final int voorkomen, final String onjuist, final int ingang, final int opneming) {
        invoer.add(new TussenGroep<>(new BrpIdentificatienummersInhoud(new BrpString(ANUMMER.getWaarde() + voorkomen), BSN), Lo3StapelHelper.lo3His(
                onjuist,
                ingang,
                opneming), Lo3StapelHelper.lo3Doc((long) voorkomen, null, null, null), new Lo3Herkomst(
                voorkomen == 0 ? Lo3CategorieEnum.CATEGORIE_01 : Lo3CategorieEnum.CATEGORIE_61,
                0,
                voorkomen)));
    }

    protected void legeLo3Groep(final int voorkomen, final String onjuist, final int ingang, final int opneming) {
        invoer.add(new TussenGroep<>(
                new BrpIdentificatienummersInhoud(null, null),
                Lo3StapelHelper.lo3His(onjuist, ingang, opneming),
                Lo3StapelHelper.lo3Doc((long) voorkomen, null, null, null),
                new Lo3Herkomst(voorkomen == 0 ? Lo3CategorieEnum.CATEGORIE_01 : Lo3CategorieEnum.CATEGORIE_61, 0, voorkomen)));
    }

    protected void valideerBrpGroep(
            final int brpVoorkomen,
            final int lo3Voorkomen,
            final Integer aanvangGeldigheid,
            final Integer eindeGeldigheid,
            final long tsReg,
            final Long tsVerval) {
        final BrpGroep<BrpIdentificatienummersInhoud> brpRij = resultaat.get(brpVoorkomen);
        final BrpHistorie brpHistorie = brpRij.getHistorie();

        assertEquals(new BrpString(ANUMMER.getWaarde() + lo3Voorkomen).getWaarde(), brpRij.getInhoud().getAdministratienummer().getWaarde());
        if (aanvangGeldigheid != null) {
            assertEquals(aanvangGeldigheid, brpHistorie.getDatumAanvangGeldigheid().getWaarde());
        } else {
            assertNull(brpHistorie.getDatumAanvangGeldigheid());
        }
        if (eindeGeldigheid != null) {
            assertEquals(eindeGeldigheid, brpHistorie.getDatumEindeGeldigheid().getWaarde());
        } else {
            assertNull(brpHistorie.getDatumEindeGeldigheid());
        }
        assertEquals(tsReg, brpHistorie.getDatumTijdRegistratie().getDatumTijd());
        if (tsVerval != null) {
            assertEquals(tsVerval.longValue(), brpHistorie.getDatumTijdVerval().getDatumTijd());
        } else {
            assertNull(brpHistorie.getDatumTijdVerval());
        }
    }

    /**
     * Geef de waarde van invoer.
     * @return invoer
     */
    public List<TussenGroep<BrpIdentificatienummersInhoud>> getInvoer() {
        return invoer;
    }

    /**
     * Geef de waarde van resultaat.
     * @return resultaat
     */
    public List<BrpGroep<BrpIdentificatienummersInhoud>> getResultaat() {
        return resultaat;
    }

    /**
     * Zet de waarde van resultaat.
     * @param resultaat resultaat
     */
    public void setResultaat(final List<BrpGroep<BrpIdentificatienummersInhoud>> resultaat) {
        this.resultaat = resultaat;
    }
}
