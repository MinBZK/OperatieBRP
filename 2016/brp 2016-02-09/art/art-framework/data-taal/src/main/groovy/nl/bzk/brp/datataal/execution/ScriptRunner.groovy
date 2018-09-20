package nl.bzk.brp.datataal.execution

import groovy.transform.TypeChecked
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate

/**
 * Scriptrunner is verantwoordelijk voor het uitvoeren van een {@link Script}.
 * Omdat de PersoonDSL dmv (algemeen-opslag) repositories informatie uit de database haalt,
 * is het nodig om een transactie te starten. Dit gebeurt in de {@link ScriptRunner#runScript(groovy.lang.Script)} methode.
 */
@TypeChecked
class ScriptRunner {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ScriptRunner)

    private PlatformTransactionManager txManager

    /**
     * Constructor, die een {@link javax.transaction.TransactionManager} ophaalt.
     */
    ScriptRunner() {
        txManager = (PlatformTransactionManager) SpringBeanProvider.getBean('lezenSchrijvenTransactionManager')
    }

    /**
     * Voer een script uit, binnen een transactie.
     *
     * @param script een valide (Groovy) script
     */
    void runScript(Script script) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager)

        final Object result;

        try {
            result = transactionTemplate.execute(new TransactionCallback() {
                @Override
                Object doInTransaction(final TransactionStatus transactionStatus) {
                    try {
                        script.run()
                    } catch (OnbekendeReferentieExceptie ore) {
                        LOGGER.error('onbekende data gebruikt: {}', ore.message)
                        transactionStatus.setRollbackOnly()

                        throw ore
                    } catch(GroovyRuntimeException gre) {
                        def cause = rootCause(gre)
                        def line = cause.stackTrace.find {
                            it.fileName ==~ /^DSL$/
                        }.lineNumber

                        transactionStatus.setRollbackOnly()

                        throw new RuntimeException("Fout in DSL op regel $line", gre)
                    } catch (Exception e) {
                        LOGGER.error('onverwachte fout in DSL: {}', e.message)
                        transactionStatus.setRollbackOnly()

                        throw e
                    }
                }
            })
        } catch (DataAccessException dae) {
            LOGGER.error('fout in opslaan persoon: {}', dae.message)
            throw dae
        }

        if (result instanceof PersoonHisVolledig) {
            final PersoonHisVolledig pers = (PersoonHisVolledig) result
            LOGGER.info("Persoon met id $pers.ID is opgeslagen")
        }
    }

    private Throwable rootCause(Exception e) {
        Throwable t = e
        while (t.cause != null) { t = t.cause }
        t
    }
}
