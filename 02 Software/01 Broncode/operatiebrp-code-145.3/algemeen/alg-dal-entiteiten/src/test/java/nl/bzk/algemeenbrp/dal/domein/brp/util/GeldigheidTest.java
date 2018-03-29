/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * test voor geldigheid
 */
public class GeldigheidTest {

    @Test
    public void geldig() {
        Geldig geldig = new Geldig(19000101, null, true);
        Assert.assertTrue(geldig.isGeldig());
    }

    @Test
    public void geenDatumIngang() {
        Geldig geldig = new Geldig(null, null, true);
        Assert.assertFalse(geldig.isGeldig());
    }

    @Test
    public void geldigMetEindDatum() {
        Geldig geldig = new Geldig(19000101, 30000101, true);
        Assert.assertTrue(geldig.isGeldig());
    }

    @Test
    public void nogNietGeldig() {
        Geldig geldig = new Geldig(21000101, 30000101, true);
        Assert.assertFalse(geldig.isGeldig());
    }

    @Test
    public void inHetVerledenGeldig() {
        Geldig geldig = new Geldig(19000101, 19500101, true);
        Assert.assertFalse(geldig.isGeldig());
    }

    @Test
    public void nietActueelEnGeldig() {
        Geldig geldig = new Geldig(19000101, null, false);
        Assert.assertFalse(geldig.isGeldig());
    }

    @Test
    public void geldigOpPeilDatum() {
        Geldig geldig = new Geldig(19200101, 19500101, true);
        Assert.assertTrue(geldig.isGeldig(19400101));
    }

    @Test
    public void nietGeldigOpPeilDatumVoorIngangsdatum() {
        Geldig geldig = new Geldig(19200101, 19500101, true);
        Assert.assertFalse(geldig.isGeldig(19100101));
    }

    @Test
    public void nietGeldigOpPeilDatumNaEinddatum() {
        Geldig geldig = new Geldig(19200101, 19500101, true);
        Assert.assertFalse(geldig.isGeldig(19600101));
    }

    @Test
    public void nietGeldigOpPeilDatumGelijkAanEinddatum() {
        Geldig geldig = new Geldig(19200101, 19500101, true);
        Assert.assertFalse(geldig.isGeldig(19500101));
    }

    @Test
    public void geldigOpPeilDatumGelijkAanBegindatum() {
        Geldig geldig = new Geldig(19200101, 19500101, true);
        Assert.assertTrue(geldig.isGeldig(19200101));
    }

    private static class Geldig implements Geldigheid {

        private final Integer datumIngang;
        private final Integer datumEind;
        private final boolean actueelEnGeldig;

        Geldig(Integer datumIngang, Integer datumEind, boolean actueelEnGeldig) {
            this.datumIngang = datumIngang;
            this.datumEind = datumEind;
            this.actueelEnGeldig = actueelEnGeldig;
        }

        @Override
        public Integer getDatumIngang() {
            return datumIngang;
        }

        @Override
        public Integer getDatumEinde() {
            return datumEind;
        }

        @Override
        public boolean isActueelEnGeldig() {
            return actueelEnGeldig;
        }
    }
}