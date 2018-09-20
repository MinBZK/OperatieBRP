/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import nl.bzk.brp.blobifier.exceptie.PersoonCacheNietAanwezigExceptie;
import nl.bzk.brp.blobifier.exceptie.ProxyAanmakenMisluktExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;


/**
 * Deze klasse verzorgt het 'lazy' laden van betrokken persoon his volledig objecten. In de blobs in de database staan
 * alleen persoonId's van de betrokken personen opgeslagen. Deze worden bij het deserializeren omgezet naar
 * persoonHisVolledigImpl objecten. Vervolgens worden deze objecten mbv deze klasse omgezet naar proxy objecten zodat
 * ze 'lazy' geladen kunnen worden vanuit de database.
 */
public class PersoonHisVolledigImplLuieLader implements MethodHandler {

    private static final Logger    LOGGER                               = LoggerFactory.getLogger();
    private static final String    PERSOON_HIS_VOLLEDIG_ID_METHODE_NAAM = "getID";
    private static final String    FINALIZE_METHODE_NAAM                = "finalize";

    private final int              persoonId;
    private final BlobifierService blobifierService;
    private PersoonHisVolledig     persoonHisVolledig;

    private final MethodFilter     finalizeFilter                       = new MethodFilter() {

        @Override
        public boolean isHandled(final Method m) {
            // ignore finalize()
            return !m.getName().equals(
                    FINALIZE_METHODE_NAAM);
        }
    };

    /**
     * De constructor voor deze klasse.
     *
     * @param blobifierService De service waarmee de persoon opgehaald kan worden.
     * @param persoonId De persoonId van de persoon die opgehaald moet worden.
     */
    public PersoonHisVolledigImplLuieLader(final BlobifierService blobifierService, final int persoonId) {
        this.persoonId = persoonId;
        this.blobifierService = blobifierService;
    }

    /**
     * Creeert een proxy object voor de desbetreffende persoonHisVolledigImpl.
     *
     * @return Het proxy object waarmee de persoon 'lazy' geladen kan worden.
     */
    public PersoonHisVolledigImpl getProxy() {
        final ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(PersoonHisVolledigImpl.class);
        factory.setFilter(finalizeFilter);
        final Class clazz = factory.createClass();

        try {
            final Object instance = clazz.newInstance();
            ((ProxyObject) instance).setHandler(this);
            return (PersoonHisVolledigImpl) instance;
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            LOGGER.error(
                    "Het is niet gelukt om een instantie te maken voor de persoon his volledig proxy met persoonId {}",
                    persoonId);
            throw new ProxyAanmakenMisluktExceptie("Proxy aanmaken mislukt.", e);
        }
    }

    @Override
    public Object invoke(final Object self, final Method overschrevenMethode, final Method proceed, final Object[] args)
            throws IllegalAccessException, InvocationTargetException
    {
        final Object resultaat;
        if (aanroepIsOpReedsBekendId(overschrevenMethode)) {
            resultaat = persoonId;
        } else {
            haalBetrokkenPersoonOpAlsDitNogNietIsGedaan();
            valideerOfBetrokkenPersoonCacheAanwezigIs();

            resultaat = overschrevenMethode.invoke(persoonHisVolledig, args);
        }

        return resultaat;
    }

    /**
     * Als de aanroep op het al bekende id is.
     *
     * @param overschrevenMethode De aangeroepen methode.
     * @return true als het id is aangeroepen, anders {@code false}
     */
    private boolean aanroepIsOpReedsBekendId(final Method overschrevenMethode) {
        return PERSOON_HIS_VOLLEDIG_ID_METHODE_NAAM.equals(overschrevenMethode.getName());
    }

    /**
     * Haalt de betrokken persoon blob uit de database als dit nog niet is gedaan.
     */
    private void haalBetrokkenPersoonOpAlsDitNogNietIsGedaan() {
        if (persoonHisVolledig == null) {
            persoonHisVolledig = blobifierService.leesBlob(persoonId);
            LOGGER.info("Persoon his volledig lazy opgehaald voor betrokkene met persoonId {}", persoonId);
        }
    }

    /**
     * Valideert of de persoon cache van de betrokken persoon aanwezig is.
     */
    private void valideerOfBetrokkenPersoonCacheAanwezigIs() {
        if (persoonHisVolledig == null) {
            throw new PersoonCacheNietAanwezigExceptie("De betrokken persoon met id " + persoonId
                    + " is niet gevonden.");
        }
    }
}
