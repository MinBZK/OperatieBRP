/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.jsf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class JbpmJsfUtilTest {

    private void setFacesContext(FacesContext context) {
        try {
            Method setter = FacesContext.class.getDeclaredMethod("setCurrentInstance", new Class<?>[]{FacesContext.class});
            setter.setAccessible(true);
            setter.invoke(null, context);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Could not set facescontext", e);
        }
    }


    @Test
    public void testGetTaskInstance() {
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        setFacesContext(facesContext);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final TaskInstance task = Mockito.mock(TaskInstance.class);

        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("task")).thenReturn(task);

        Assert.assertEquals(task, JbpmJsfUtil.getTaskInstance());
    }


    @Test
    public void getJbpmContext() {
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        setFacesContext(facesContext);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        final JbpmContext jbpmContext = Mockito.mock(JbpmContext.class);

        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenReturn(jbpmContext);

        Assert.assertEquals(jbpmContext, JbpmJsfUtil.getJbpmContext());
    }


    @Test(expected = RuntimeException.class)
    public void getJbpmContextIllegalArgumentException() {
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        setFacesContext(facesContext);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);

        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(IllegalArgumentException.class);

        JbpmJsfUtil.getJbpmContext();
    }


    @Test(expected = RuntimeException.class)
    public void getJbpmContextSecurityException() {
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        setFacesContext(facesContext);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);

       Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(SecurityException.class);

        JbpmJsfUtil.getJbpmContext();
    }


    @Test(expected = RuntimeException.class)
    public void getJbpmContextIllegalAccessException() {
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        setFacesContext(facesContext);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);

        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(IllegalAccessException.class);

        JbpmJsfUtil.getJbpmContext();
    }


    @Test(expected = RuntimeException.class)
    public void getJbpmContextInvocationTargetException() {
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        setFacesContext(facesContext);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);

        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(InvocationTargetException.class);

        JbpmJsfUtil.getJbpmContext();
    }


    @Test(expected = RuntimeException.class)
    public void getJbpmContextNoSuchMethodException() {
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        setFacesContext(facesContext);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);

       Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(NoSuchMethodException.class);

        JbpmJsfUtil.getJbpmContext();
    }
}
