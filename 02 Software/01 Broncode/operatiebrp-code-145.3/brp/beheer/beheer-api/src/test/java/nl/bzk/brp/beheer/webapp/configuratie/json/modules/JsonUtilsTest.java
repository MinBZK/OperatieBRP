/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test van JsonUtils.
 */
public class JsonUtilsTest {

    @Mock
    private JsonNode parentNode;

    @Mock
    private JsonNode theNode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetAsShort() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asInt()).thenReturn(3);
        Assert.assertEquals("Waarde moet overeenkomen", Short.valueOf((short) 3), JsonUtils.getAsShort(parentNode, "test"));
    }

    @Test
    public void testGetAsShortEmpty() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("");
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsShort(parentNode, "test"));
    }

    @Test
    public void testGetAsShortisNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.getNodeType()).thenReturn(JsonNodeType.NULL);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsShort(parentNode, "test"));
    }

    @Test
    public void testGetAsShortNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(null);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsShort(parentNode, "test"));
    }

    @Test
    public void testGetAsInteger() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asInt()).thenReturn(3);
        Assert.assertEquals("Waarde moet overeenkomen", Integer.valueOf(3), JsonUtils.getAsInteger(parentNode, "test"));
    }

    @Test
    public void testGetAsIntegerEmpty() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("");
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsInteger(parentNode, "test"));
    }

    @Test
    public void testGetAsIntegerisNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.getNodeType()).thenReturn(JsonNodeType.NULL);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsInteger(parentNode, "test"));
    }

    @Test
    public void testGetAsIntegerNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(null);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsInteger(parentNode, "test"));
    }

    @Test
    public void testGetAsString() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("test");
        Assert.assertEquals("Waarde moet overeenkomen", "test", JsonUtils.getAsString(parentNode, "test"));
    }

    @Test
    public void testGetAsStringEmpty() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("");
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsString(parentNode, "test"));
    }

    @Test
    public void testGetAsStringisNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.getNodeType()).thenReturn(JsonNodeType.NULL);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsString(parentNode, "test"));
    }

    @Test
    public void testGetAsStringNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(null);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsString(parentNode, "test"));
    }

    @Test
    public void testGetAsCharacter() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("-");
        Assert.assertEquals("Waarde moet overeenkomen", Character.valueOf('-'), JsonUtils.getAsCharacter(parentNode, "-"));
    }

    @Test
    public void testGetAsCharacterEmpty() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("");
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsCharacter(parentNode, "test"));
    }

    @Test
    public void testGetAsCharacterisNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.getNodeType()).thenReturn(JsonNodeType.NULL);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsCharacter(parentNode, "test"));
    }

    @Test
    public void testGetAsCharacterNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(null);
        Assert.assertNull("Waarde moet null zijn", JsonUtils.getAsCharacter(parentNode, "test"));
    }

    @Test
    public void testGetAsBooleanTrue() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("test");
        Assert.assertTrue("Waarde moet waar zijn", JsonUtils.getAsBoolean(parentNode, "test", "test", Boolean.TRUE, Boolean.FALSE));
    }

    @Test
    public void testGetAsBooleanFalse() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.asText()).thenReturn("geentest");
        Assert.assertFalse("Waarde moet false zijn", JsonUtils.getAsBoolean(parentNode, "test", "test", Boolean.TRUE, Boolean.FALSE));
    }

    @Test
    public void testGetAsBooleanisNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(theNode);
        Mockito.when(theNode.getNodeType()).thenReturn(JsonNodeType.NULL);
        Assert.assertFalse("Waarde moet false zijn", JsonUtils.getAsBoolean(parentNode, "test", "test", Boolean.TRUE, Boolean.FALSE));
    }

    @Test
    public void testGetAsBooleanNull() {
        Mockito.when(parentNode.get(Mockito.anyString())).thenReturn(null);
        Assert.assertFalse("Waarde moet false zijn", JsonUtils.getAsBoolean(parentNode, "test", "test", Boolean.TRUE, Boolean.FALSE));
    }
}
