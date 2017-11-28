/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.message;

import javax.management.NotificationFilter;
import javax.management.ObjectName;

/**
 * Request to add a notification listener.
 */
public final class RequestAddNotificationListener extends Request {

    private static final long serialVersionUID = 1L;

    private final String notificationListenerId;
    private final ObjectName name;
    private final NotificationFilter filter;

    /**
     * Create a new add notification listener request.
     * @param notificationListenerId unique notification listener identification
     * @param name mbean to register the notification listener for
     * @param filter notification filter
     */
    public RequestAddNotificationListener(final String notificationListenerId, final ObjectName name,
                                          final NotificationFilter filter) {
        this.notificationListenerId = notificationListenerId;
        this.name = name;
        this.filter = filter;
    }

    public String getNotificationListenerId() {
        return notificationListenerId;
    }

    public ObjectName getName() {
        return name;
    }

    public NotificationFilter getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return "RequestAddNotificationListener [requestId=" + getRequestId() + ", notificationListenerId="
                + notificationListenerId + ", name=" + name + ", filter=" + filter + "]";
    }
}
