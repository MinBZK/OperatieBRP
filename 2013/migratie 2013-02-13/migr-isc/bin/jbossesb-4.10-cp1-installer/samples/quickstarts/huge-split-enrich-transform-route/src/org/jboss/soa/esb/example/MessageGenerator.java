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
package org.jboss.soa.esb.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class MessageGenerator {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        if(args.length != 1) {
            throw new RuntimeException("Must be a single 'numOrderItems' arg.");
        }

        int numOrderItems = Integer.parseInt(args[0].trim());

        File outFileFile = new File("build/xxx." + start + ".xxx");
        FileWriter messageFile = new FileWriter(outFileFile);
        try {
            messageFile.write("HDR*" + start + "*0*Wed Nov 15 13:45:28 EST 2006\n");
            messageFile.write("CUS*ACID\n");

            Random random = new Random();
            for (int i = 0; i < numOrderItems; i++) {
                String productId = products.get(random.nextInt(products.size()));

                messageFile.write("ORD*" + i + "*" + (random.nextInt(5) + 1) + "*" + productId + "\n");
                messageFile.flush();
            }
        } finally {            
            messageFile.flush();
            messageFile.close();

            outFileFile.renameTo(new File("build/" + start + ".edi"));
            System.out.println("Finished.  Took " + ((System.currentTimeMillis() - start) / 1000) + " seconds");
        }
    }

    private static List<String> products;

    static {
        products = Arrays.asList(
                new String[] {"FI-SW-01", "FI-SW-02", "FI-FW-01", "FI-FW-02", "K9-BD-01", "K9-PO-02", "K9-DL-01", "K9-RT-01", "K9-RT-02", "K9-CW-01", "RP-SN-01", "RP-LI-02", "FL-DSH-01", "FL-DLH-02", "AV-CB-01", "AV-SB-02"}
        );
    }
}