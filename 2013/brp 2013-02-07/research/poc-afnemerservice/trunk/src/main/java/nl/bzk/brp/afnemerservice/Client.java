/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package nl.bzk.brp.afnemerservice;

import java.io.Closeable;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import nl.bzk.brp.afnemerservice.service.LeveringPortType;
import nl.bzk.brp.levering.LEVLeveringBijgehoudenPersoonLv;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;


public final class Client {

    private Client() {
    }

    public static void main(final String args[]) throws Exception {
        try {

            SpringBusFactory bf = new SpringBusFactory();
            URL busFile = Client.class.getResource("/client.xml");
            Bus bus = bf.createBus(busFile.toString());
            BusFactory.setDefaultBus(bus);

            Service service = Service.create(Client.class.getResource("/xsd/levering.wsdl").toURI().toURL(), new QName("http://www.bprbzk.nl/BRP/levering/service", "LeveringService"));

            LeveringPortType port = service.getPort(LeveringPortType.class);

            LEVLeveringBijgehoudenPersoonLv levLeveringBijgehoudenPersoonLv = new LEVLeveringBijgehoudenPersoonLv();

            port.mutatiePersoon(levLeveringBijgehoudenPersoonLv);

            // allow aynchronous resends to occur
            Thread.sleep(30 * 1000);

            if (port instanceof Closeable) {
                ((Closeable)port).close();
            }
            bus.shutdown(true);

        } catch (UndeclaredThrowableException ex) {
            ex.getUndeclaredThrowable().printStackTrace();
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
