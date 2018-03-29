/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.message;

/**
 * Notification, initiated by the server, sent to the client.
 */
public final class Notification implements Message {

    private static final long serialVersionUID = 1L;

    private final String notificationListenerId;

    private final javax.management.Notification theNotification;

    /**
     * Create a new notification.
     * @param notificationListenerId unique notification listener identification to identify the listener this notification is for
     * @param theNotification notification
     */
    public Notification(final String notificationListenerId, final javax.management.Notification theNotification) {
        this.notificationListenerId = notificationListenerId;
        this.theNotification = theNotification;
    }

    public String getNotificationListenerId() {
        return notificationListenerId;
    }

    public javax.management.Notification getNotification() {
        return theNotification;
    }

    @Override
    public String toString() {
        return "Notification [notificationListenerId=" + notificationListenerId + ", theNotification=" + theNotification + "]";
    }
}
