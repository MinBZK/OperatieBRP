/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import nl.bzk.brp.blobifier.exceptie.AfnemerindicatiesNietAanwezigExceptie;
import nl.bzk.brp.blobifier.exceptie.ProxyAanmakenMisluktExceptie;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;


/**
 * Luie lader voor het lazy ophalen van de afnemerindicaties blob.
 */
public final class AfnemerindicatiesHisVolledigImplLuieLader implements MethodHandler {

    private static final Logger                         LOGGER                = LoggerFactory.getLogger();
    private static final String                         FINALIZE_METHODE_NAAM = "finalize";

    private final Integer                               technischId;
    private final AfnemerIndicatieBlobifierService      afnemerIndicatieBlobifierService;
    private Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties;

    private final MethodFilter                          finalizeFilter        = new MethodFilter() {

        @Override
        public boolean isHandled(final Method m) {
            // ignore finalize()
            return !m.getName().equals(FINALIZE_METHODE_NAAM);
        }
    };

    /**
     * Constructor van deze klasse.
     *
     * @param afnemerIndicatieBlobifierService service waarmee afnemerindicaties opgehaald kunnen worden
     * @param technischId id verwijzend naar afnemerindicaties
     */
    public AfnemerindicatiesHisVolledigImplLuieLader(final AfnemerIndicatieBlobifierService afnemerIndicatieBlobifierService, final Integer technischId) {
        this.technischId = technischId;
        this.afnemerIndicatieBlobifierService = afnemerIndicatieBlobifierService;
    }

    /**
     * Geeft een proxy object voor de set van afnemerindicaties.
     *
     * @return proxy object waarmee indicaties lazy geladen worden
     */
    public Set<PersoonAfnemerindicatieHisVolledigImpl> getProxy() {
        final ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(HashSet.class);
        factory.setFilter(finalizeFilter);
        final Class clazz = factory.createClass();

        try {
            final Object instance = clazz.newInstance();
            ((ProxyObject) instance).setHandler(this);
            return (Set<PersoonAfnemerindicatieHisVolledigImpl>) instance;
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            LOGGER.error("Het is niet gelukt om een instantie te maken voor de afnemerindicaties proxy met id {}", technischId);
            throw new ProxyAanmakenMisluktExceptie("Proxy aanmaken mislukt.", e);
        }
    }

    @Override
    public Object invoke(final Object self, final Method overschrevenMethode, final Method proceed, final Object[] args)
            throws IllegalAccessException, InvocationTargetException
    {
        haalDataOpAlsDitNogNietIsGedaan();
        valideerOfDataAanwezigIs();

        return overschrevenMethode.invoke(afnemerindicaties, args);
    }

    /**
     * Haalt de data op als dit nog niet is gedaan.
     */
    private void haalDataOpAlsDitNogNietIsGedaan() {
        if (afnemerindicaties == null) {
            afnemerindicaties = afnemerIndicatieBlobifierService.leesBlob(technischId);
            if (afnemerindicaties != null) {
                LOGGER.info("{} afnemerindicaties opgehaald voor persoon met id {}", afnemerindicaties.size(), technischId);
            } else {
                LOGGER.info("Geen afnemerindicaties opgehaald voor persoon met id {}", technischId);
            }
        }
    }

    /**
     * Valideert of de data al opgehaald is.
     */
    private void valideerOfDataAanwezigIs() {
        if (afnemerindicaties == null) {
            throw new AfnemerindicatiesNietAanwezigExceptie(String.format("De afnemerindicaties voor persoon met id %s zijn niet gevonden.", technischId));
        }
    }

}
