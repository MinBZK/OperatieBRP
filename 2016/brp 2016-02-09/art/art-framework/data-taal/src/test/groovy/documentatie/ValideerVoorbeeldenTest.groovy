package documentatie
import groovy.io.FileType
import javax.inject.Inject
import javax.inject.Named
import javax.sql.DataSource
import nl.bzk.brp.dataaccess.test.Data
import nl.bzk.brp.dataaccess.test.DataSourceProvider
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.datataal.test.DBUnitLoaderTestExecutionListener
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestContextManager
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate
/**
 * "Unittest" die de voorbeelden uit src/test/asciidoc uitvoert, zodat we
 * zeker weten dat de voorbeelden correct zijn.
 *
 * @see <a href="https://stackoverflow.com/a/7342523/249995">Stack Overflow</a>
 */
@RunWith(Parameterized)
@ContextConfiguration(locations = ['/config/test-context.xml'])
@TestExecutionListeners([
    DependencyInjectionTestExecutionListener,
    DirtiesContextTestExecutionListener,
    DBUnitLoaderTestExecutionListener])
@Data(resources = [
    "classpath:/data/dsl/dataset.xml",
    "classpath:/data/blob/cleanup.xml"] )
class ValideerVoorbeeldenTest implements DataSourceProvider {
    private PersoonDSLExecutor executor = new PersoonDSLExecutor()

    @Inject @Named('lezenSchrijvenTransactionManager') PlatformTransactionManager txManager
    @Inject @Named('lezenSchrijvenDataSource') DataSource dataSource
    final File voorbeeld

    @Inject
    ApplicationContext context;

    ValideerVoorbeeldenTest(File file) {
        this.voorbeeld = file
    }

    @Before
    final void setupSpringContext() {
        new TestContextManager(getClass()).prepareTestInstance(this)
    }

    @Parameterized.Parameters(name = '{index}: {0}')
    static Iterable<? extends Object> voorbeelden() {
        def path = new File(new File(getClass().getResource('/').toURI()), '../../src/test/asciidoc')

        def files = []
        path.eachFileRecurse(FileType.FILES) {
            if (!it.absolutePath.toLowerCase().contains('.svn')) { files << it }
        }

        return files
    }

    @Test
    void 'valideer DSL voorbeelden'() {
        SpringBeanProvider.setContext(context)
        new TransactionTemplate(txManager).execute(new TransactionCallbackWithoutResult() {
            public void doInTransactionWithoutResult(TransactionStatus status) {
                status.setRollbackOnly()

                try {
                    executor.execute(voorbeeld.text)
                } catch (Exception any) {
                    throw new RuntimeException("Fout in voorbeeld: ${any.message}")
                }
            }
        })
    }
}
