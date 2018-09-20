/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.jsf;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.jbpm.JbpmContext;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FacesContext.class)
public class JbpmJsfUtilTest {

    
    @Test
    public void testGetTaskInstance() {
        PowerMockito.mockStatic(FacesContext.class);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final TaskInstance task = Mockito.mock(TaskInstance.class);

        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("task")).thenReturn(task);

        Assert.assertEquals(task, JbpmJsfUtil.getTaskInstance());
    }

    
    @Test
    public void getJbpmContext() {
        PowerMockito.mockStatic(FacesContext.class);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        final JbpmContext JbpmContext = Mockito.mock(JbpmContext.class);

        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenReturn(JbpmContext);

        Assert.assertEquals(JbpmContext, JbpmJsfUtil.getJbpmContext());
    }

    
    @Test(expected = RuntimeException.class)
    public void getJbpmContextIllegalArgumentException() {
        PowerMockito.mockStatic(FacesContext.class);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        // final JbpmContext JbpmContext = Mockito.mock(JbpmContext.class);

        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(IllegalArgumentException.class);

        JbpmJsfUtil.getJbpmContext();
    }

    
    @Test(expected = RuntimeException.class)
    public void getJbpmContextSecurityException() {
        PowerMockito.mockStatic(FacesContext.class);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        // final JbpmContext JbpmContext = Mockito.mock(JbpmContext.class);

        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(SecurityException.class);

        JbpmJsfUtil.getJbpmContext();
    }

    
    @Test(expected = RuntimeException.class)
    public void getJbpmContextIllegalAccessException() {
        PowerMockito.mockStatic(FacesContext.class);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        // final JbpmContext JbpmContext = Mockito.mock(JbpmContext.class);

        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(IllegalAccessException.class);

        JbpmJsfUtil.getJbpmContext();
    }

    
    @Test(expected = RuntimeException.class)
    public void getJbpmContextInvocationTargetException() {
        PowerMockito.mockStatic(FacesContext.class);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        // final JbpmContext JbpmContext = Mockito.mock(JbpmContext.class);

        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(InvocationTargetException.class);

        JbpmJsfUtil.getJbpmContext();
    }

    
    @Test(expected = RuntimeException.class)
    public void getJbpmContextNoSuchMethodException() {
        PowerMockito.mockStatic(FacesContext.class);
        final FacesContext facesContext = Mockito.mock(FacesContext.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
        final Map<String, Object> requestMap = Mockito.mock(Map.class);
        final JbpmJsfContext jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        // final JbpmContext JbpmContext = Mockito.mock(JbpmContext.class);

        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        Mockito.when(facesContext.getExternalContext()).thenReturn(externalContext);
        Mockito.when(externalContext.getRequestMap()).thenReturn(requestMap);
        Mockito.when(requestMap.get("org.jbpm.jsf.CONTEXT")).thenReturn(jbpmJsfContext);
        Mockito.when(jbpmJsfContext.getJbpmContext()).thenThrow(NoSuchMethodException.class);

        JbpmJsfUtil.getJbpmContext();
    }
}
