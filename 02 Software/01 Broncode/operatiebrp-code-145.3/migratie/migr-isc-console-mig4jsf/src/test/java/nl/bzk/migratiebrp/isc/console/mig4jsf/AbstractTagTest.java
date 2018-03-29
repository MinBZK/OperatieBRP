/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.Location;
import com.sun.facelets.tag.Tag;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagAttributes;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.core.handler.AbstractHandler;
import org.jbpm.persistence.PersistenceService;
import org.jbpm.svc.Services;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FacesContext.class, JbpmContext.class})
public abstract class AbstractTagTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<String, Object> expressionValues = new HashMap<>();
    private final List<TagAttribute> tagAttributeList = new ArrayList<>();
    private FaceletContext faceletContext;
    private String jbpmJsfContextOutcome;

    protected JbpmJsfContext jbpmJsfContext;
    protected JbpmContext jbpmContext;
    protected ActionEvent actionEvent;
    protected FacesContext facesContext;

    protected void addTagAttribute(final String name, final Object value) {
        expressionValues.put(name, value);
        tagAttributeList.add(new TagAttribute(new Location("test", 1, 1), "", name, name, name));
    }

    public JbpmActionListener initializeSubject(final Class<? extends AbstractHandler> clazz) throws Exception {
        final Constructor<? extends AbstractHandler> handlerConstructor = clazz.getConstructor(new Class<?>[]{TagConfig.class,});

        final TagHandler handler = handlerConstructor.newInstance(makeTagConfig());
        final Method getListenerMethod = clazz.getDeclaredMethod("getListener", new Class<?>[]{FaceletContext.class,});
        getListenerMethod.setAccessible(true);
        return (JbpmActionListener) getListenerMethod.invoke(handler, faceletContext);
    }

    protected ActionListener initializeBasicSubject(final Class<? extends TagHandler> clazz) throws Exception {
        final Constructor<? extends TagHandler> handlerConstructor = clazz.getConstructor(new Class<?>[]{TagConfig.class,});

        final TagHandler handler = handlerConstructor.newInstance(makeTagConfig());
        final UIComponent parent = Mockito.mock(UIComponent.class, Mockito.withSettings().extraInterfaces(ActionSource.class));
        handler.apply(faceletContext, parent);

        final ArgumentCaptor<ActionListener> actionListenerCaptor = ArgumentCaptor.forClass(ActionListener.class);
        Mockito.verify((ActionSource) parent).addActionListener(actionListenerCaptor.capture());
        return actionListenerCaptor.getValue();
    }

    private TagConfig makeTagConfig() {
        final TagAttributes tagAttributes = new TagAttributes(tagAttributeList.toArray(new TagAttribute[]{}));
        final Tag tag = new Tag(new Location("test", 1, 1), "", "test", "test", tagAttributes);

        final TagConfig tagConfig = Mockito.mock(TagConfig.class);
        Mockito.when(tagConfig.getTag()).thenReturn(tag);

        return tagConfig;
    }

    @Before
    public void setup() {
        LOGGER.debug("setup");
        // Setup faces context
        PowerMockito.mockStatic(FacesContext.class);
        facesContext = Mockito.mock(FacesContext.class);
        Mockito.when(FacesContext.getCurrentInstance()).thenReturn(facesContext);
        final ELContext elContext = Mockito.mock(ELContext.class);
        Mockito.when(facesContext.getELContext()).thenReturn(elContext);

        // Setup facelet context
        final ExpressionFactory expressionFactory = Mockito.mock(ExpressionFactory.class);
        faceletContext = Mockito.mock(FaceletContext.class);

        Mockito.when(faceletContext.getExpressionFactory()).thenReturn(expressionFactory);
        Mockito.when(expressionFactory.createValueExpression(Matchers.eq(faceletContext), Matchers.anyString(), Matchers.<Class<?>>any())).then(invocation -> {
            final ValueExpression result = Mockito.mock(ValueExpression.class);
            final String expression = (String) invocation.getArguments()[1];

            // hier de 'when' doen dat bij het opvragen de value geidentifieerd door het tweede
            // argument
            // uit de map experssion values wordt teruggegeven.
            Mockito.when(result.getValue(Matchers.<ELContext>anyObject())).then(invocation1 -> expressionValues.get(expression));

            Mockito.doAnswer(invocation1 -> {
                expressionValues.put(expression, invocation1.getArguments()[1]);
                return null;
            }).when(result).setValue(Matchers.<ELContext>anyObject(), Matchers.anyObject());

            return result;
        });

        // Setup JBPM JSF Context
        jbpmJsfContext = Mockito.mock(JbpmJsfContext.class);
        PowerMockito.mockStatic(JbpmContext.class);
        jbpmContext = Mockito.mock(JbpmContext.class);
        Mockito.when(JbpmContext.getCurrentJbpmContext()).thenReturn(jbpmContext);
        actionEvent = Mockito.mock(ActionEvent.class);

        Mockito.when(jbpmJsfContext.getJbpmContext()).thenReturn(jbpmContext);

        // Mock outcome
        Mockito.when(jbpmJsfContext.getOutcome()).thenAnswer(invocation -> jbpmJsfContextOutcome);
        Mockito.doAnswer(invocation -> {
            jbpmJsfContextOutcome = (String) invocation.getArguments()[0];
            return null;
        }).when(jbpmJsfContext).selectOutcome(Matchers.<String>anyObject());
    }

    /**
     * Geef de waarde van expression values.
     * @return expression values
     */
    public Map<String, Object> getExpressionValues() {
        return expressionValues;
    }

    protected DataSource dataSource;
    protected Session session;
    protected Criteria criteria;
    protected GraphSession graphSession;

    protected void setupDatabase(final String... sqls) {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        dataSource = new SingleConnectionDataSource("jdbc:hsqldb:mem:test", true);

        final ResourceLoader resourceLoader = new DefaultResourceLoader();
        for (final String sql : sqls) {
            try (Connection connection = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(connection, new EncodedResource(resourceLoader.getResource(sql)), true, false, ScriptUtils.DEFAULT_COMMENT_PREFIX,
                        ScriptUtils.DEFAULT_STATEMENT_SEPARATOR, ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER,
                        ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);
            } catch (final SQLException e) {
                throw new IllegalArgumentException("Kan SQL niet uitvoeren", e);
            }
        }

        setupMockDatabase();

        final Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        Mockito.doAnswer(invocation -> {
            final Work work = (Work) invocation.getArguments()[0];
            work.execute(connection);

            return null;
        }).when(session).doWork(Matchers.<Work>anyObject());
    }

    protected void setupMockDatabase() {
        session = Mockito.mock(Session.class);
        Mockito.when(jbpmContext.getSession()).thenReturn(session);

        criteria = Mockito.mock(Criteria.class);
        Mockito.when(session.createCriteria(Matchers.anyString())).thenReturn(criteria);

        graphSession = Mockito.mock(GraphSession.class);
        Mockito.when(jbpmContext.getGraphSession()).thenReturn(graphSession);

        // Session kan ook via PersistenceService worden opgehaald
        final Services services = Mockito.mock(Services.class);
        Mockito.when(jbpmContext.getServices()).thenReturn(services);
        final PersistenceService persistenceService = Mockito.mock(PersistenceService.class);
        Mockito.when(services.getPersistenceService()).thenReturn(persistenceService);
        Mockito.when(persistenceService.getCustomSession(Matchers.eq(Session.class))).thenReturn(session);
    }

    protected SpringServiceFactory springServiceFactory;
    protected BeanFactory beanFactory;

    protected void setupMockSpringService() {
        beanFactory = Mockito.mock(BeanFactory.class);

        final SpringServiceFactory springServiceFactory = new SpringServiceFactory();
        springServiceFactory.setBeanFactory(beanFactory);

        Mockito.when(jbpmContext.getServiceFactory(SpringServiceFactory.SERVICE_NAME)).thenReturn(springServiceFactory);
    }

    @After
    public void teardown() {
        if (dataSource != null) {
            ((SingleConnectionDataSource) dataSource).destroy();
        }
    }
}
