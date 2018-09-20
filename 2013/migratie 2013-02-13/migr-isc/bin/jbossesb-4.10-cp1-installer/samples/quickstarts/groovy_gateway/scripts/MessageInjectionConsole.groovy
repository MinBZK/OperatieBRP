/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * (C) 2005-2006, JBoss Inc.
 */
import groovy.swing.SwingBuilder
import java.awt.*

/**
 * Simple Groovy Gateway script that starts a Swing form through which you can
 * inject messages into the target service.  Useful for testing and prototyping.
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */

def targetService = "Target Service: " + deliveryAdapter.getDeliveryAdapter().getServiceCategory() + ":" + deliveryAdapter.getDeliveryAdapter().getServiceName()
def numRows = 7
def numCols = 50

swing = new SwingBuilder()
sendMessage = swing.action(name:'SendMessage', closure:this.&sendMessageToTarget, mnemonic:'R')

frame = swing.frame(title:'Message Injection Console', size:[600,400], location:[200,200]) {
    panel(layout:new BorderLayout()) {
        panel(layout:new BorderLayout(), constraints: BorderLayout.NORTH) {
            label(text: targetService, constraints: BorderLayout.NORTH)
            label(text: 'Message In:', constraints: BorderLayout.WEST)
            textArea(id:'messageInTB', columns: numCols, rows: numRows, constraints: BorderLayout.EAST)
        }
        panel(layout:new FlowLayout()) {
            button(text: 'Send Message', action:sendMessage)
	    comboBox(id: 'synchasync', items:["Asynchronous", "Synchronous"], selectedIndex:0);
        }
        panel(layout:new BorderLayout(), constraints: BorderLayout.SOUTH) {
            label(text: 'Message Out:', constraints: BorderLayout.WEST)
            textArea(id: 'messageOutTB', columns: numCols, rows: numRows, constraints: BorderLayout.EAST)
        }
    }
}
frame.pack()
frame.show()

def sendMessageToTarget(event) {
    swing.messageOutTB.text = ""
    if(swing.synchasync.selectedIndex == 0) {
	    deliveryAdapter.deliverAsync(swing.messageInTB.text)
    } else {
	    swing.messageOutTB.text = deliveryAdapter.deliverSync(swing.messageInTB.text, 20000)
    }
}

// Wait until the gateway is told to shutdown....
while(!gateway.waitUntilStopping(500)) {
}

frame.dispose()