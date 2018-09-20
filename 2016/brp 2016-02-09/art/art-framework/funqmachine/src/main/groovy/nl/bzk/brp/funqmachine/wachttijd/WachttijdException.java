/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
Copyright 2007-2009 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package nl.bzk.brp.funqmachine.wachttijd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.funqmachine.informatie.BuildInfo;


public class WachttijdException extends RuntimeException {

    public static final String SESSION_ID  = "Session ID";
    public static final String DRIVER_INFO = "Driver info";

    private static final long serialVersionUID = 98754376543634L;

    private Map<String, String> extraInfo = new HashMap<String, String>();

    public WachttijdException() {
        super();
    }

    public WachttijdException(String message) {
        super(message);
    }

    public WachttijdException(Throwable cause) {
        super(cause);
    }

    public WachttijdException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return createMessage(super.getMessage());
    }

    private String createMessage(String originalMessageString) {
        String supportMessage = getSupportUrl() == null ?
            "" : "For documentation on this error, please visit: " + getSupportUrl() + "\n";

        return (originalMessageString == null ? "" : originalMessageString + "\n")
            + supportMessage
            + getBuildInformation() + "\n"
            + getSystemInformation()
            + getAdditionalInformation();
    }

    public String getSystemInformation() {
        String host = "N/A";
        String ip = "N/A";

        try {
            host = InetAddress.getLocalHost().getHostName();
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException throw_away) {
        }

        return String.format("System info: host: '%s', ip: '%s', os.name: '%s', os.arch: '%s', os.version: '%s', java.version: '%s'",
            host,
            ip,
            System.getProperty("os.name"),
            System.getProperty("os.arch"),
            System.getProperty("os.version"),
            System.getProperty("java.version"));
    }

    public String getSupportUrl() {
        return null;
    }

    public BuildInfo getBuildInformation() {
        return new BuildInfo();
    }

    public void addInfo(String key, String value) {
        extraInfo.put(key, value);
    }

    public String getAdditionalInformation() {
        String result = "";
        for (Map.Entry<String, String> entry : extraInfo.entrySet()) {
            if (entry.getValue() != null && entry.getValue().startsWith(entry.getKey())) {
                result += "\n" + entry.getValue();
            } else {
                result += "\n" + entry.getKey() + ": " + entry.getValue();
            }
        }
        return result;
    }
}
