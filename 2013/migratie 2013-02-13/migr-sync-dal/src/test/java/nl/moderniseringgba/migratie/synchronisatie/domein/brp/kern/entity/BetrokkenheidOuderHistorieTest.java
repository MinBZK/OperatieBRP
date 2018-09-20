/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import junit.framework.Assert;

import org.junit.Test;

public class BetrokkenheidOuderHistorieTest {
    private static final BigDecimal DATUM = new BigDecimal(20121211);
    private static final Timestamp TIJDSTIP = new Timestamp(1L);
    private static final BRPActie ACTIE = new BRPActie() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isInhoudelijkGelijkAan(final BRPActie actie2) {
            return false;
        }
    };
    private final BetrokkenheidOuderHistorie betOudGezagHistorie = new BetrokkenheidOuderHistorie();
    private final BetrokkenheidOuderHistorie betOudGezagHistorie2 = new BetrokkenheidOuderHistorie();

    @Test
    public void isInhoudelijkGelijk() {
        Assert.assertTrue(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        final BRPActie actie = new BRPActie() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isInhoudelijkGelijkAan(final BRPActie actie2) {
                return true;
            }
        };
        betOudGezagHistorie2.setActieAanpassingGeldigheid(actie);
        betOudGezagHistorie.setActieAanpassingGeldigheid(actie);
        betOudGezagHistorie2.setActieInhoud(actie);
        betOudGezagHistorie.setActieInhoud(actie);
        betOudGezagHistorie2.setActieVerval(actie);
        betOudGezagHistorie.setActieVerval(actie);
        betOudGezagHistorie2.setDatumAanvangGeldigheid(DATUM);
        betOudGezagHistorie.setDatumAanvangGeldigheid(DATUM);
        betOudGezagHistorie2.setDatumEindeGeldigheid(DATUM);
        betOudGezagHistorie.setDatumEindeGeldigheid(DATUM);
        betOudGezagHistorie2.setDatumTijdRegistratie(TIJDSTIP);
        betOudGezagHistorie.setDatumTijdRegistratie(TIJDSTIP);
        betOudGezagHistorie2.setDatumTijdVerval(TIJDSTIP);
        betOudGezagHistorie.setDatumTijdVerval(TIJDSTIP);
        betOudGezagHistorie2.setIndicatieOuder(true);
        betOudGezagHistorie.setIndicatieOuder(true);

        Assert.assertTrue(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        Assert.assertTrue(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie));
    }

    @Test
    public void isInhoudelijkGelijkNull() {
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(null));
    }

    @Test
    public void isInhoudelijkGelijkActieAanpassingGeldigheid() {
        betOudGezagHistorie2.setActieAanpassingGeldigheid(ACTIE);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setActieAanpassingGeldigheid(ACTIE);
        betOudGezagHistorie2.setActieAanpassingGeldigheid(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

    @Test
    public void isInhoudelijkGelijkActieInhoud() {
        betOudGezagHistorie2.setActieInhoud(ACTIE);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setActieInhoud(ACTIE);
        betOudGezagHistorie2.setActieInhoud(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

    @Test
    public void isInhoudelijkGelijkActieVerval() {
        betOudGezagHistorie2.setActieVerval(ACTIE);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setActieVerval(ACTIE);
        betOudGezagHistorie2.setActieVerval(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

    @Test
    public void isInhoudelijkGelijkDatumAanvangGeldigheid() {
        betOudGezagHistorie2.setDatumAanvangGeldigheid(DATUM);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setDatumAanvangGeldigheid(DATUM);
        betOudGezagHistorie2.setDatumAanvangGeldigheid(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

    @Test
    public void isInhoudelijkGelijkDatumEindeGeldigheid() {
        betOudGezagHistorie2.setDatumEindeGeldigheid(DATUM);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setDatumEindeGeldigheid(DATUM);
        betOudGezagHistorie2.setDatumEindeGeldigheid(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

    @Test
    public void isInhoudelijkGelijkDatumTijdRegistratie() {
        betOudGezagHistorie2.setDatumTijdRegistratie(TIJDSTIP);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setDatumTijdRegistratie(TIJDSTIP);
        betOudGezagHistorie2.setDatumTijdRegistratie(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

    @Test
    public void isInhoudelijkGelijkDatumTijdVerval() {
        betOudGezagHistorie2.setDatumTijdVerval(TIJDSTIP);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setDatumTijdVerval(TIJDSTIP);
        betOudGezagHistorie2.setDatumTijdVerval(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

    @Test
    public void isInhoudelijkGelijkIndicatieOuderHeeftGezag() {
        betOudGezagHistorie2.setIndicatieOuder(true);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));

        betOudGezagHistorie.setIndicatieOuder(true);
        betOudGezagHistorie2.setIndicatieOuder(null);
        Assert.assertFalse(betOudGezagHistorie.isInhoudelijkGelijkAan(betOudGezagHistorie2));
    }

}
