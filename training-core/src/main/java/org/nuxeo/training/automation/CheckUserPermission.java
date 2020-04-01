package org.nuxeo.training.automation;

import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.usermanager.UserManager;

/**
 *
 */
@Operation(id = CheckUserPermission.ID, category = "Training", label = "Check User Permission", description = "Describe here what your operation does.")
public class CheckUserPermission {

    public static final String ID = "Document.CheckUserPermission";

    @Context
    protected CoreSession session;

    @Context
    protected UserManager userManager;

    @Param(name = "permission", required = false)
    protected String permission;

    @Param(name = "username", required = false)
    protected String username;

    @OperationMethod
    public Boolean run(DocumentModel doc) {
        NuxeoPrincipal principal = userManager.getPrincipal(username);
        return session.hasPermission(principal, doc.getRef(), permission);
    }
}
