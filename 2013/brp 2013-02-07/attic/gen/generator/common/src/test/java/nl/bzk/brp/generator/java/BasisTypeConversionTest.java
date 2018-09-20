/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * 
 */
package nl.bzk.brp.generator.java;

import java.util.UnknownFormatConversionException;

import junit.framework.Assert;
import nl.bzk.brp.generator.java.DataTypeTranslator.BasisType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class BasisTypeConversionTest {

    private static final Logger logger = LoggerFactory.getLogger(BasisTypeConversionTest.class);

    @Test
    public void testConversieNaarJavaType() {

        String javaType = DataTypeTranslator.getJavaType(BasisType.ID_KLEIN.getBasisType());
        Assert.assertEquals(Short.class.getSimpleName(), javaType);
        logger.info("result:" + javaType);
    }

    @Test
    public void shouldConvertToNumericalCodeMiddle() {
        Class<?> javaClass = DataTypeTranslator.getJavaClass(BasisType.NUMERIEKE_CODE.getBasisType(),9);
        Assert.assertEquals(Integer.class.getSimpleName(), javaClass.getSimpleName());
        logger.info("result:" + javaClass.getSimpleName());
    }

    @Test
    public void shouldConvertAllBasisTypes() {
        for (BasisType basisType : BasisType.values()) {
            logger.info("Basistype '" + basisType.getBasisType() + "' omgezet naar " + DataTypeTranslator.getImportForBasisType(basisType.getBasisType(), 0));
        }
        Assert.assertTrue(true);
    }

    @Test
    public void shouldConvertToImportStatement() {
        Assert.assertEquals("ID Groot moet worden gemapped naar BigInteger maar dat is niet zo!","java.math.BigInteger", DataTypeTranslator.getImportForBasisType(BasisType.ID_GROOT.getBasisType(), 0));
    }

    @Test(expected = UnknownFormatConversionException.class)
    public void shouldNotConvertToJavaType() {
        DataTypeTranslator.getJavaType("onbekend type kan niet worden omgezet");
    }

}
