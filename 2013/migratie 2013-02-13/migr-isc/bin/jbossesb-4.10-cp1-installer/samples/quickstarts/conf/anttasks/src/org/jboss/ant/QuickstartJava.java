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
package org.jboss.ant;

import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.ExecuteStreamHandler;
import org.apache.tools.ant.taskdefs.condition.Socket;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.CommandlineJava;
import org.jboss.internal.soa.esb.util.Exit;

import java.io.IOException;

/**
 * Quickstart executor.
 * <p/>
 * Just to provide some control over the shutdown when the process is forked.
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class QuickstartJava extends Java {

    public int executeJava() throws BuildException {
        CommandlineJava cmdl = getCommandLine();
        final Process process;
        final ExecuteStreamHandler streamHandler = redirector.createHandler();

        final int freePort = findFreePort();

        setupRedirector();
        try {
            cmdl.createArgument().setValue("exit-port=" + freePort);
            process = Execute.launch(getProject(), cmdl.getCommandline(), null, getProject().getBaseDir(), true);
            startStreamHandler(streamHandler, process);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    signalExit(freePort);
                    waitForProcessShutdown(process);
                    streamHandler.stop();
                    Execute.closeStreams(process);
                }
            });
        } catch (IOException e) {
            throw new BuildException("Failed to launch Quickstart processes.", e);
        }

        waitForProcessShutdown(process);

        return process.exitValue();
    }

    private void startStreamHandler(ExecuteStreamHandler streamHandler, Process process) {
        try {
            streamHandler.setProcessInputStream(process.getOutputStream());
            streamHandler.setProcessOutputStream(process.getInputStream());
            streamHandler.setProcessErrorStream(process.getErrorStream());
            streamHandler.start();
        } catch (IOException e) {
            throw new BuildException("Failed to setup redirect stream handler.", e);
        }
    }

    private void signalExit(int freePort) {
        try {
            Exit.signalExit(freePort);
        } catch (Throwable throwable) {
            throw new BuildException("Failed to exit forked process.", throwable);
        }
    }

    private int findFreePort() throws BuildException {
        Socket socket = new Socket();

        socket.setServer("localhost");
        for(int i = 60 * 1024; i < 65000; i++) {
            socket.setPort(i);
            if(!socket.eval()) {
                return i;
            }
        }

        throw new BuildException("Failed to locate a free port off which to launch StandAloneBootStrapper.");
    }

    private void waitForProcessShutdown(Process process) {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
