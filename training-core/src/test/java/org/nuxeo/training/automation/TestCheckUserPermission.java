package org.nuxeo.training.automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.RuntimeService;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("test.training.training-core")
public class TestCheckUserPermission {

    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    protected DocumentModel doc;

    @Before
    public void createDoc() {
        doc = session.createDocumentModel("/", "file", "File");
        doc = session.createDocument(doc);
        doc = session.saveDocument(doc);
    }

    @Ignore
    @Test
    public void shouldCallTheOperation() throws OperationException {
        OperationContext ctx = new OperationContext(session);
        DocumentModel doc = (DocumentModel) automationService.run(ctx, CheckUserPermission.ID);
        assertEquals("/", doc.getPathAsString());
    }

    @Test
    public void shouldCallWithParameters() throws OperationException {
        OperationContext ctx = new OperationContext(session);
        ctx.setInput(doc);
        Map<String, Object> params = new HashMap<>();
        params.put("permission", "Read");
        params.put("username", "Administrator");
        Boolean allowed = (Boolean) automationService.run(ctx, CheckUserPermission.ID, params);
        assertTrue(allowed);
    }

    @Test
    public void shouldDeployNuxeoRuntime() {
        RuntimeService runtime = Framework.getRuntime();
        assertNotNull(runtime);
        assertNotNull(session);
    }
}
