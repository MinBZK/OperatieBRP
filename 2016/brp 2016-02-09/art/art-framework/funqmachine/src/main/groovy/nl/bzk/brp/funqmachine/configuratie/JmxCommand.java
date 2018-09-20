/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.configuratie;

import org.apache.commons.lang3.StringUtils;

/**
 * Beschrijving van applicatiecache welke gereset dient te worden
 */
public class JmxCommand {


    public static final String OBJECT_NAME_BASIS_AUTAUT_CACHE = "-nl.bzk.brp.levering.business.toegang.abonnement.cache:name=AutAutCache";
    public static final String OBJECT_NAME_BASIS_STAMTABEL_CACHE = "-nl.bzk.brp.levering.algemeen.cache:name=StamTabelCache";
    public static final String OBJECT_NAME_VERZENDER          = "-nl.bzk.brp.levering.verzending:name=Verzender";

    private String   jmxURL;
    private String   objectNamePrefix;
    private String   objectNameBasis;
    private String   methodName;
    private String   attributeName;
    private Object[] params;

    public JmxCommand(final String jmxURL, final String objectNamePrefix, final String objectNameBasis, final String methodName) {
        this.objectNameBasis = objectNameBasis;
        this.jmxURL = jmxURL;
        this.objectNamePrefix = objectNamePrefix;
        this.methodName = methodName;
    }

    public void setParams(final Object[] params) {
        this.params = params;
    }

    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }

    public void setAttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }

    public String getJmxURL() {
        return jmxURL;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public Object[] getParams() {
        return params;
    }

    public String getObjectNamePrefix() {
        return objectNamePrefix;
    }

    public String getObjectNameBasis() {
        return objectNameBasis;
    }

    public String getJMXObjectName() {
        return objectNamePrefix + objectNameBasis;
    }

    @Override
    public String toString() {
        return "JmxCommand{" +
            "jmxURL='" + jmxURL + '\'' +
            ", objectNamePrefix='" + objectNamePrefix + '\'' +
            ", objectNameBasis='" + objectNameBasis + '\'' +
            (StringUtils.isNotEmpty(methodName) ? ", methodName='" + methodName : ", attributeName='" + attributeName) +
            '\''  + '}';
    }
}
