/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto.proxies;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nl.bzk.brp.bevraging.business.handlers.TestPersoon;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.PersoonAdres;
import nl.bzk.brp.domein.kern.SoortPersoon;

import org.junit.Assert;
import org.junit.Test;


public class PersoonProxyTest {

    private DomeinObjectFactory factory = new PersistentDomeinObjectFactory();

    @Test
    public void testPersoonZonderAdressen() {
        Persoon persoon = factory.createPersoon();
        persoon.setSoort(SoortPersoon.INGESCHREVENE);
        Set<String> toegestaneVelden = new HashSet<String>();
        PersoonProxy persoonProxy = new PersoonProxy(persoon, toegestaneVelden);

        Assert.assertSame(persoon, PersoonProxy.getPersoon(persoonProxy));
        Assert.assertEquals(0, persoonProxy.getPersoonAdresen().size());
    }

    @Test
    public void testPersoonMetAdressenZonderAbonnement() {
        Persoon persoon = new TestPersoon();
        Set<String> toegestaneVelden = new HashSet<String>();
        PersoonProxy persoonProxy = new PersoonProxy(persoon, toegestaneVelden);

        Assert.assertSame(persoon, PersoonProxy.getPersoon(persoonProxy));
        Iterator<? extends PersoonAdres> adressen = persoonProxy.getPersoonAdresen().iterator();
        Assert.assertNull(adressen.next().getNaamOpenbareRuimte());
        Assert.assertFalse(adressen.hasNext());
    }

    @Test
    public void testPersoonMetAdressenMetNaamOpenbareRuimteAbonnement() {
        Persoon persoon = new TestPersoon();
        Set<String> toegestaneVelden = new HashSet<String>();
        toegestaneVelden.add((PersoonAdres.class.getSimpleName() + ".NaamOpenbareRuimte"));
        PersoonProxy persoonProxy = new PersoonProxy(persoon, toegestaneVelden);

        Assert.assertSame(persoon, PersoonProxy.getPersoon(persoonProxy));
        Iterator<? extends PersoonAdres> adressen = persoonProxy.getPersoonAdresen().iterator();
        Assert.assertEquals("Dam", adressen.next().getNaamOpenbareRuimte());
        Assert.assertFalse(adressen.hasNext());
    }

    @Test
    public void testPersoonMetAdressenMetNaamOpenbareRuimteAbonnementDynamisch() throws Throwable {
        testPersoonMetAdressenMetEnkelvoudigAbonnement(PersoonAdres.class.getMethod("getNaamOpenbareRuimte"));
    }

    /**
     * Alle velden worden getest voor alle mogelijke abonnementen op 1 gegevenselement.
     */
    @Test
    public void testVoerVerwerkingsStapUitVoorBerichVoorElkEnkelvoudigAbonnement() throws Throwable {
        Collection<Method> methoden = getMethodenAdres();
        for (Method method : methoden) {
            testPersoonMetAdressenMetEnkelvoudigAbonnement(method);
        }
    }

    /**
     * Deze methode test of het resultaat van de gegeven methode beschikbaar is via de proxy als alleen dat veld is
     * toegestaan.
     * Het test ook of de resultaten van alle andere methoden waarvoor een abonnement mogelijk is NIET worden
     * doorgegeven.
     *
     * @param methode een methode op {@link PersoonAdres} die in het te testen abonnement moet komen
     */
    private void testPersoonMetAdressenMetEnkelvoudigAbonnement(final Method methode) throws Throwable {
        Persoon persoon = new TestPersoon();
        Set<String> toegestaneVelden = new HashSet<String>();
        toegestaneVelden.add(PersoonAdres.class.getSimpleName() + "." + getVeld(methode));
        PersoonProxy persoonProxy = new PersoonProxy(persoon, toegestaneVelden);

        Assert.assertSame(persoon, PersoonProxy.getPersoon(persoonProxy));
        Iterator<? extends PersoonAdres> adressen = persoonProxy.getPersoonAdresen().iterator();
        Map<String, Object> resultaten = getInhoud(adressen.next());
        Assert.assertFalse(adressen.hasNext());
        resultaten.remove("getAdres");

        PersoonAdres adres = persoon.getPersoonAdresen().iterator().next();
        Object verwachtResultaat = null;
        try {
            verwachtResultaat = methode.invoke(adres);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertSame(methode.getName() + " onjuist", verwachtResultaat, resultaten.remove(methode.getName()));
        Assert.assertEquals(0, resultaten.size());
    }

    private String getVeld(final Method methode) {
        String resultaat = methode.getName();
        if (methode.getName().startsWith("get")) {
            resultaat = resultaat.substring(3);
        }
        return resultaat;
    }

    private Map<String, Object> getInhoud(final PersoonAdres adres) throws Throwable {
        Map<String, Object> resultaten = new HashMap<String, Object>();
        for (Method methode : getMethodenAdres()) {
            Object resultaat = null;
            try {
                resultaat = methode.invoke(adres);
            } catch (InvocationTargetException e) {
                if (!(e.getCause() instanceof UnsupportedOperationException)) {
                    throw e;
                }
            }
            if (resultaat != null) {
                resultaten.put(methode.getName(), resultaat);
            }
        }
        return resultaten;
    }

    private Collection<Method> getMethodenAdres() throws Throwable {
        Collection<Method> methoden = new HashSet<Method>();
        for (Method methode : PersoonAdres.class.getMethods()) {
            if (methode.getName().startsWith("get") && methode.getParameterTypes().length == 0) {
                methoden.add(methode);
            }
        }
        return methoden;
    }

}
