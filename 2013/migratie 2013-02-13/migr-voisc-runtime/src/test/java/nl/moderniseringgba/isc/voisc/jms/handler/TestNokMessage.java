/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms.handler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;

// CHECKSTYLE:OFF test klasse
public class TestNokMessage implements MapMessage {

    private final Map<String, String> stringProperties = new HashMap<String, String>();

    @Override
    public void acknowledge() throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearBody() throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearProperties() throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getBooleanProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public byte getByteProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getDoubleProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getFloatProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getIntProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getJMSCorrelationID() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getJMSDeliveryMode() throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Destination getJMSDestination() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getJMSExpiration() throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getJMSMessageID() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getJMSPriority() throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getJMSRedelivered() throws JMSException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Destination getJMSReplyTo() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getJMSTimestamp() throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getJMSType() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getLongProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getObjectProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration<?> getPropertyNames() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public short getShortProperty(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getStringProperty(final String arg0) throws JMSException {
        return stringProperties.get(arg0);
    }

    @Override
    public boolean propertyExists(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setBooleanProperty(final String arg0, final boolean arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setByteProperty(final String arg0, final byte arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDoubleProperty(final String arg0, final double arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFloatProperty(final String arg0, final float arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setIntProperty(final String arg0, final int arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSCorrelationID(final String arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSCorrelationIDAsBytes(final byte[] arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSDeliveryMode(final int arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSDestination(final Destination arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSExpiration(final long arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSMessageID(final String arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSPriority(final int arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSRedelivered(final boolean arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSReplyTo(final Destination arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSTimestamp(final long arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setJMSType(final String arg0) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLongProperty(final String arg0, final long arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObjectProperty(final String arg0, final Object arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShortProperty(final String arg0, final short arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setStringProperty(final String arg0, final String arg1) throws JMSException {
        stringProperties.put(arg0, arg1);
    }

    @Override
    public boolean getBoolean(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public byte getByte(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public byte[] getBytes(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char getChar(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getDouble(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getFloat(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getInt(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long getLong(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Enumeration<?> getMapNames() throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getObject(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public short getShort(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getString(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean itemExists(final String arg0) throws JMSException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setBoolean(final String arg0, final boolean arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setByte(final String arg0, final byte arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBytes(final String arg0, final byte[] arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setBytes(final String arg0, final byte[] arg1, final int arg2, final int arg3) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setChar(final String arg0, final char arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDouble(final String arg0, final double arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFloat(final String arg0, final float arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setInt(final String arg0, final int arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLong(final String arg0, final long arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setObject(final String arg0, final Object arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setShort(final String arg0, final short arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setString(final String arg0, final String arg1) throws JMSException {
        // TODO Auto-generated method stub

    }

}
