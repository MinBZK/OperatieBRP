package org.jboss.soa.esb.samples.quickstart.webserviceproducer.webservice;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.Oneway;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;

import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.actions.ActionUtils;

/**
 * @author
 */
@WebService(name = "GoodbyeWorldWS", targetNamespace="http://webservice_producer/goodbyeworld")
// @SOAPBinding(style = SOAPBinding.Style.RPC)
public class GoodbyeWorldWS {

    @WebMethod
    public String sayGoodbye(@WebParam(name="message") String message) {
        System.out.println("Web Service Parameter - message=" + message);
        return "... Ah Goodbye then!!!! - " + message;
    }

    @WebMethod
    public String sayAdios(String message) {
        System.out.println("Web Service Parameter - message=" + message);
        return "... Adios Amigo!!!! - " + message;
    }
    
    @WebMethod
    @Oneway
    public void sayGoodbyeWithoutResponse(@WebParam(name="message") String message) {
        System.out.println("Web Service Parameter - message=" + message);
    }
    
}
