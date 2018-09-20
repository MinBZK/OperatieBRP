/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.jms;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

//CHECKSTYLE:OFF test klasse
public class TestOkMessage implements TextMessage {

    private final Map<String, String> properties = new TreeMap<String, String>();
    private String text;

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
        return properties.get(arg0);
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
        properties.put(arg0, arg1);

    }

    @Override
    public String getText() throws JMSException {
        return text;
    }

    @Override
    public void setText(final String arg0) throws JMSException {
        text = arg0;
    }

}
