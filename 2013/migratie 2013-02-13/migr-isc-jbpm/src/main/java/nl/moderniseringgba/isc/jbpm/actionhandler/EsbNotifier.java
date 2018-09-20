/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//CHECKSTYLE:OFF - Gekloonde code uit JBPM tree
package nl.moderniseringgba.isc.jbpm.actionhandler;

import static org.jboss.soa.esb.services.jbpm.actionhandlers.ActionUtil.isReplyToOrFaultToSet;
import static org.jboss.soa.esb.services.jbpm.actionhandlers.ActionUtil.setRelatesToMessageId;

import org.dom4j.tree.DefaultElement;
import org.jboss.internal.soa.esb.addressing.helpers.EPRHelper;
import org.jboss.internal.soa.esb.util.LRUCache;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.addressing.EPR;
import org.jboss.soa.esb.addressing.eprs.LogicalEPR;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.couriers.Courier;
import org.jboss.soa.esb.couriers.CourierFactory;
import org.jboss.soa.esb.couriers.CourierUtil;
import org.jboss.soa.esb.listeners.message.MessageDeliverException;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.services.jbpm.Constants;
import org.jboss.soa.esb.services.jbpm.JBpmObjectMapper;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Sends ESB messages to ESB services from jBPM in an asynchronous way.
 * 
 * <li/>esbCategoryName - for ESB registry lookup <li/>esbServiceName - for ESB registry lookup
 * 
 */
public class EsbNotifier implements ActionHandler {

    /** Message property waarin de process instance id (String) wordt bewaard. */
    public static final String PROPERTY_PROCESS_INSTANCE_ID = "jbpm-processinstance-id";

    private static final long serialVersionUID = 1L;

    private static transient LRUCache<String, ServiceInvoker> siCache = new LRUCache<String, ServiceInvoker>(20);
    /**
     * The ESB Service Category Name, specified in the processdefinition.xml
     */
    public String esbCategoryName;
    /**
     * The ESB Service Name, specified in the processdefinition.xml
     */
    public String esbServiceName;
    /**
     * Default setting of the process scope, can be overriden by setting the process-scope attribute on the mapping
     * element.
     */
    public Boolean globalProcessScope;
    /**
     * XML element to specify the mapping of variables from jBPM to ESB.
     */
    public DefaultElement bpmToEsbVars;
    /**
     * Use the replyTo or faultTo EPR from the originator.
     */
    public String replyToOriginator;

    /**
     * Constructs an ESB Message and sends sends to the an ESB Service as defined in the processdefinition.xml.
     */
    @Override
    public void execute(final ExecutionContext executionContext) throws Exception {
        if (replyToOriginator != null) {
            if (!(Constants.EPR_REPLY.equals(replyToOriginator) || Constants.EPR_FAULT.equals(replyToOriginator))) {
                throw new ConfigurationException("EPR type (replyToOriginator) must be \"" + Constants.EPR_REPLY
                        + "\" or \"" + Constants.EPR_FAULT + "\"");
            }
        } else {
            if (null == esbCategoryName) {
                throw new ConfigurationException("Service category (esbCategoryName element) must not be null");
            }
            if (null == esbServiceName) {
                throw new ConfigurationException("Service name (esbServiceName element) must not be null");
            }
        }
        final JBpmObjectMapper mapper = new JBpmObjectMapper();
        final Message message = mapper.mapFromJBpmToEsbMessage(bpmToEsbVars, globalProcessScope, executionContext);

        // MIG:START
        message.getBody().add(PROPERTY_PROCESS_INSTANCE_ID,
                String.valueOf(executionContext.getProcessInstance().getId()));

        // MIG:END

        final ContextInstance contextInstance = executionContext.getContextInstance();

        if (isReplyToOrFaultToSet(contextInstance)) {
            setRelatesToMessageId(contextInstance, message);
        }

        if (replyToOriginator != null) {
            final EPR epr;
            final Object replyToEPR = contextInstance.getVariable(Constants.REPLY_TO);
            final Object faultToEPR = contextInstance.getVariable(Constants.FAULT_TO);

            if (Constants.EPR_FAULT.equals(replyToOriginator) && (faultToEPR != null)) {
                epr = EPRHelper.fromXMLString(faultToEPR.toString());
            } else if (replyToEPR != null) {
                epr = EPRHelper.fromXMLString(replyToEPR.toString());
            } else {
                throw new ConfigurationException("No EPR present in process instance");
            }
            if (epr instanceof LogicalEPR) {
                final ServiceInvoker invoker = ((LogicalEPR) epr).getServiceInvoker();
                invoker.deliverAsync(message);
            } else {
                final Courier courier = CourierFactory.getCourier(epr);
                try {
                    courier.deliver(message);
                } finally {
                    CourierUtil.cleanCourier(courier);
                }
            }
        } else {
            getServiceInvoker().deliverAsync(message);
        }
    }

    /**
     * Caches the most recently used ServiceInvokers.
     * 
     * @return a ServiceInvoker for the current esbService and esbCategoryName.
     * @throws MessageDeliverException
     */
    private ServiceInvoker getServiceInvoker() throws MessageDeliverException {
        final String key = esbCategoryName + esbServiceName;
        if (siCache.containsKey(key)) {
            return siCache.get(key);
        } else {
            final ServiceInvoker invoker = new ServiceInvoker(esbCategoryName, esbServiceName);
            siCache.put(key, invoker);
            return invoker;
        }
    }

}
