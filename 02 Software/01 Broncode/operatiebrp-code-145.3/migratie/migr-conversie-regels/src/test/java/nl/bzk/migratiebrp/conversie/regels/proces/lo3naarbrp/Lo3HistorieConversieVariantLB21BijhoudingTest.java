/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Lo3HistorieConversieVariantLB21BijhoudingTest extends AbstractLo3HistorieConversieVariantTest {

    private List<TussenGroep<BrpBijhoudingInhoud>> invoerLb21;
    private List<BrpGroep<BrpBijhoudingInhoud>> resultaatLb21;

    @Inject
    @Named("lo3HistorieConversieVariantLB21Bijhouding")
    private Lo3HistorieConversieVariantLB21 conversie;

    @Override
    @Before
    public void setup() {
        invoerLb21 = new ArrayList<>();
        resultaatLb21 = new ArrayList<>();
    }

    @Test
    public void testDatumGelijk() {
        // lo3cat vk geldighd opneming
        maakBijhoudingGroep(7, 0, 20000101, 20000202);
        maakBijhoudingGroep(8, 0, 20000101, 20000102);

        converteer();

        // brpVk Lo3Cat Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpBijhoudingGroep(0, 7, 0, 20000101, null, 20000202010000L, null);
        valideerBrpBijhoudingGroep(1, 8, 0, 20000101, null, 20000102010000L, 20000102010000L);
    }

    @Test
    public void testCat08Nieuwer() {
        // lo3cat vk geldighd opneming
        maakBijhoudingGroep(7, 0, 20000101, 20000202);
        maakBijhoudingGroep(8, 0, 20010101, 20000102);

        converteer();

        // brpVk Lo3Cat Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpBijhoudingGroep(1, 8, 0, 20010101, null, 20000102010000L, null);
        valideerBrpBijhoudingGroep(0, 7, 0, 20000101, 20010101, 20000202010000L, null);
    }

    @Test
    public void testCat07Nieuwer() {
        // lo3cat vk geldighd opneming
        maakBijhoudingGroep(7, 0, 20010101, 20000202);
        maakBijhoudingGroep(8, 0, 20000101, 20000102);

        converteer();

        // brpVk Lo3Cat Lo3Vk aanvangGh eindeGh tsReg tsVerval
        valideerBrpBijhoudingGroep(0, 7, 0, 20010101, null, 20000202010000L, null);
        valideerBrpBijhoudingGroep(1, 8, 0, 20000101, 20010101, 20000102010000L, null);
    }

    protected void maakBijhoudingGroep(final int categorie, final int voorkomen, final int ingang, final int opneming) {
        invoerLb21.add(
                new TussenGroep<>(
                        new BrpBijhoudingInhoud(BrpPartijCode.ONBEKEND, BrpBijhoudingsaardCode.INGEZETENE, BrpNadereBijhoudingsaardCode.ACTUEEL),
                        Lo3StapelHelper.lo3His(null, ingang, opneming),
                        Lo3StapelHelper.lo3Doc((long) (categorie + voorkomen), null, null, null),
                        new Lo3Herkomst(Lo3CategorieEnum.getLO3Categorie(categorie), 0, voorkomen)));
    }

    protected void valideerBrpBijhoudingGroep(
            final int brpVoorkomen,
            final int lo3Categorie,
            final int lo3Voorkomen,
            final Integer aanvangGeldigheid,
            final Integer eindeGeldigheid,
            final long tsReg,
            final Long tsVerval) {
        final BrpGroep<BrpBijhoudingInhoud> brpRij = resultaatLb21.get(brpVoorkomen);
        final BrpHistorie brpHistorie = brpRij.getHistorie();

        Assert.assertEquals(BrpPartijCode.ONBEKEND, brpRij.getInhoud().getBijhoudingspartijCode());
        if (aanvangGeldigheid != null) {
            Assert.assertEquals(aanvangGeldigheid, brpHistorie.getDatumAanvangGeldigheid().getWaarde());
        } else {
            Assert.assertNull(brpHistorie.getDatumAanvangGeldigheid());
        }
        if (eindeGeldigheid != null) {
            Assert.assertEquals(eindeGeldigheid, brpHistorie.getDatumEindeGeldigheid().getWaarde());
        } else {
            Assert.assertNull(brpHistorie.getDatumEindeGeldigheid());
        }
        Assert.assertEquals(tsReg, brpHistorie.getDatumTijdRegistratie().getDatumTijd());
        if (tsVerval != null) {
            Assert.assertEquals(tsVerval.longValue(), brpHistorie.getDatumTijdVerval().getDatumTijd());
        } else {
            Assert.assertNull(brpHistorie.getDatumTijdVerval());
        }
        Assert.assertEquals(lo3Categorie, brpRij.getActieInhoud().getLo3Herkomst().getCategorie().getCategorieAsInt());
        Assert.assertEquals(lo3Voorkomen, brpRij.getActieInhoud().getLo3Herkomst().getVoorkomen());
    }

    private void converteer() {
        resultaatLb21 = conversie.converteer(invoerLb21, new HashMap<Long, BrpActie>());
        Collections.reverse(resultaatLb21);
    }
}
