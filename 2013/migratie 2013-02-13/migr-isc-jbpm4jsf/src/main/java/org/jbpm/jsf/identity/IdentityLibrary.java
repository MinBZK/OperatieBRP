/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity;

import org.jbpm.jsf.identity.handler.AddMembershipHandler;
import org.jbpm.jsf.identity.handler.CreateGroupHandler;
import org.jbpm.jsf.identity.handler.CreateUserHandler;
import org.jbpm.jsf.identity.handler.DeleteGroupHandler;
import org.jbpm.jsf.identity.handler.DeleteMembershipHandler;
import org.jbpm.jsf.identity.handler.DeleteUserHandler;
import org.jbpm.jsf.identity.handler.ListGroupsHandler;
import org.jbpm.jsf.identity.handler.ListUsersHandler;
import org.jbpm.jsf.identity.handler.LoadGroupByNameHandler;
import org.jbpm.jsf.identity.handler.LoadGroupHandler;
import org.jbpm.jsf.identity.handler.LoadUserHandler;
import org.jbpm.jsf.identity.handler.VerifyUserHandler;

import com.sun.facelets.tag.AbstractTagLibrary;

/**
 *
 */
public final class IdentityLibrary extends AbstractTagLibrary {
    public IdentityLibrary() {
        super("http://jbpm.org/jbpm4jsf/identity");

        // Actions

        addTagHandler("loadUser", LoadUserHandler.class);
        addTagHandler("loadGroup", LoadGroupHandler.class);
        addTagHandler("loadGroupByName", LoadGroupByNameHandler.class);
        addTagHandler("createUser", CreateUserHandler.class);
        addTagHandler("createGroup", CreateGroupHandler.class);
        addTagHandler("addMembership", AddMembershipHandler.class);
        addTagHandler("verifyUser", VerifyUserHandler.class);
        addTagHandler("listUsers", ListUsersHandler.class);
        addTagHandler("listGroups", ListGroupsHandler.class);
        addTagHandler("deleteUser", DeleteUserHandler.class);
        addTagHandler("deleteMembership", DeleteMembershipHandler.class);
        addTagHandler("deleteGroup", DeleteGroupHandler.class);
    }
}
