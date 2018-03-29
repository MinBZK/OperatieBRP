/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository.jdbc;


import java.sql.Timestamp;
import java.time.Instant;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class RowSetUtilTest {

    @Test
    public void testInteger() {
        SqlRowSet rs = Mockito.mock(SqlRowSet.class);
        Mockito.when(rs.getInt("col1")).thenReturn(0);
        Mockito.when(rs.wasNull()).thenReturn(false).thenReturn(true);

        Assert.assertEquals(Integer.valueOf(0), RowSetUtil.getInteger(rs, "col1"));
        Assert.assertEquals(null, RowSetUtil.getInteger(rs, "col1"));
    }

    @Test
    public void testLong() {
        SqlRowSet rs = Mockito.mock(SqlRowSet.class);
        Mockito.when(rs.getLong("col1")).thenReturn(0L);
        Mockito.when(rs.wasNull()).thenReturn(false).thenReturn(true);

        Assert.assertEquals(Long.valueOf(0), RowSetUtil.getLong(rs, "col1"));
        Assert.assertEquals(null, RowSetUtil.getLong(rs, "col1"));
    }

    @Test
    public void testTimestampAsLocalDateTime() {
        SqlRowSet rs = Mockito.mock(SqlRowSet.class);
        Timestamp timestamp = Timestamp.from(Instant.now());
        Mockito.when(rs.getTimestamp("col1")).thenReturn(timestamp);
        Mockito.when(rs.wasNull()).thenReturn(false).thenReturn(true);

        Assert.assertEquals(timestamp.toLocalDateTime(), RowSetUtil.getTimestampAsLocalDateTime(rs, "col1"));
        Assert.assertEquals(null, RowSetUtil.getTimestampAsLocalDateTime(rs, "col1"));
    }

}
