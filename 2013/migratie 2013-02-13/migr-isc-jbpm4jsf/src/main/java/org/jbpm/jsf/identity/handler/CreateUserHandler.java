/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.handler;

import org.jboss.gravel.common.annotation.TldAttribute;
import org.jboss.gravel.common.annotation.TldTag;
import org.jbpm.identity.User;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.jsf.identity.action.CreateUserActionListener;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;

/**
 *
 */
@TldTag (
    name = "createUser",
    description = "Create a new user and save it in the database.",
    attributes = {
        @TldAttribute (
            name = "userName",
            required = true,
            description = "The user name of the user to create."
        ),
        @TldAttribute (
            name = "password",
            description = "The password of the new user."
        ),
        @TldAttribute (
            name = "passwordConfirm",
            description = "The password of the new user again.  If this value does not match the value of the " +
                "<code>password</code> attribute, user creation will fail."
        ),
        @TldAttribute (
            name = "email",
            description = "The email of the new user."
        ),
        @TldAttribute (
            name = "target",
            description = "An EL expression into which the created user should be stored.",
            deferredType = User.class
        )
    }
)
public final class CreateUserHandler extends AbstractHandler {
    private final TagAttribute userNameTagAttribute;
    private final TagAttribute passwordTagAttribute;
    private final TagAttribute passwordConfirmTagAttribute;
    private final TagAttribute emailTagAttribute;
    private final TagAttribute targetTagAttribute;

    public CreateUserHandler(final TagConfig config) {
        super(config);
        userNameTagAttribute = getRequiredAttribute("userName");
        passwordTagAttribute = getAttribute("password");
        passwordConfirmTagAttribute = getAttribute("passwordConfirm");
        emailTagAttribute = getAttribute("email");
        targetTagAttribute = getAttribute("target");
    }

    protected JbpmActionListener getListener(final FaceletContext ctx) {
        return new CreateUserActionListener(
            getValueExpression(userNameTagAttribute, ctx, String.class),
            getValueExpression(passwordTagAttribute, ctx, String.class),
            getValueExpression(passwordConfirmTagAttribute, ctx, String.class),
            getValueExpression(emailTagAttribute, ctx, String.class),
            getValueExpression(targetTagAttribute, ctx, User.class)
        );
    }
}
