/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import junit.framework.Assert;
import nl.bzk.brp.model.basis.AbstractAttribuutType;
import org.junit.Test;


public class AttribuutTypeUtilTest {

    @Test
    public void testIsNotBlank() {
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(null));
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<String>(null) {
        }));
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<String>("") {
        }));
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<String>(" ") {
        }));
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<String>("  ") {
        }));
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<Long>(null) {
        }));
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<Integer>(null) {
        }));
        Assert.assertFalse(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<Short>(null) {
        }));


        Assert.assertTrue(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<String>("A") {
        }));
        Assert.assertTrue(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<String>("AB") {
        }));
        Assert.assertTrue(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<String>(" A ") {
        }));
        Assert.assertTrue(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<Long>(Long.valueOf(1)) {
        }));
        Assert.assertTrue(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<Integer>(Integer.valueOf(2)) {
        }));
        Assert.assertTrue(AttribuutTypeUtil.isNotBlank(new AbstractAttribuutType<Short>(Short.valueOf("3")) {
        }));
    }
}
