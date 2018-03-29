/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.event.AbortProcessingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionHelperTest {

    @Mock
    private ELContext elContext;

    @Test
    public void testBoolean() {
        Assert.assertEquals(null, ExpressionHelper.getBoolean(null, elContext));

        Assert.assertEquals(null, ExpressionHelper.getBoolean(maakValueExpression(null), elContext));
        Assert.assertEquals(null, ExpressionHelper.getBoolean(maakValueExpression(""), elContext));
        Assert.assertEquals(Boolean.TRUE, ExpressionHelper.getBoolean(maakValueExpression(Boolean.TRUE), elContext));
        Assert.assertEquals(Boolean.TRUE, ExpressionHelper.getBoolean(maakValueExpression("true"), elContext));
    }

    @Test
    public void testString() {
        Assert.assertEquals(null, ExpressionHelper.getString(null, elContext));

        Assert.assertEquals(null, ExpressionHelper.getString(maakValueExpression(null), elContext));
        Assert.assertEquals("asdasda", ExpressionHelper.getString(maakValueExpression("asdasda"), elContext));
        Assert.assertEquals("whaaa", ExpressionHelper.getString(maakValueExpression(new Object() {
            @Override
            public String toString() {
                return "whaaa";
            }
        }), elContext));
    }

    @Test(expected = AbortProcessingException.class)
    public void testDate() {
        Assert.assertEquals(null, ExpressionHelper.getDate(null, elContext));

        Assert.assertEquals(null, ExpressionHelper.getDate(maakValueExpression(null), elContext));
        Assert.assertEquals(null, ExpressionHelper.getDate(maakValueExpression(""), elContext));
        final Date date = ExpressionHelper.getDate(maakValueExpression("2014-2-12"), elContext);
        Assert.assertEquals("12-02-2014", new SimpleDateFormat("dd-MM-yyyy").format(date));

        ExpressionHelper.getDate(maakValueExpression("qwererewq"), elContext);
    }

    private ValueExpression maakValueExpression(final Object value) {
        final ValueExpression result = Mockito.mock(ValueExpression.class);
        Mockito.when(result.getValue(elContext)).thenReturn(value);
        return result;
    }
}
