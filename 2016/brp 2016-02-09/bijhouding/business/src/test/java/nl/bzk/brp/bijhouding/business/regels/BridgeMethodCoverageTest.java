/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegelMetHistorischBesef;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.reflections.Reflections;

/**
 * Alle bedrijfsregels hebben een extra 'bridge' method na compilatie die standaard niet gecovered wordt.
 * In deze test worden ze allemaal in 1 keer (met reflectie) geraakt, zodat de coverage hierdoor niet
 * negatief beinvloed wordt.
 *
 * Voor meer info over bridge methods, zie:
 * http://stas-blogspot.blogspot.nl/2010/03/java-bridge-methods-explained.html
 * http://sourceforge.net/p/cobertura/bugs/92/ (comment Kevin Hunter)
 */
public class BridgeMethodCoverageTest {

    @Test
    public void testBridgeMethods() {
        Reflections reflections = new Reflections("nl.bzk.brp.business.regels.impl");

        // Zoek alle bericht regel klasses bij elkaar. We moeten een subtype van de meest specifieke
        // interface opvragen, anders worden geen klasses teruggegeven.
        List<Set<Class<?>>> regelKlassesLijst = new ArrayList<>();
        regelKlassesLijst.add(zonderGenerics(reflections.getSubTypesOf(VoorBerichtRegel.class)));
        regelKlassesLijst.add(zonderGenerics(reflections.getSubTypesOf(VoorActieRegelMetMomentopname.class)));
        regelKlassesLijst.add(zonderGenerics(reflections.getSubTypesOf(VoorActieRegelMetHistorischBesef.class)));
        regelKlassesLijst.add(zonderGenerics(reflections.getSubTypesOf(NaActieRegelMetMomentopname.class)));

        for (Set<Class<?>> regelKlasses : regelKlassesLijst) {
            for (Class<?> regelKlasse : regelKlasses) {
                // Abstracte klasses overslaan.
                if (!Modifier.isAbstract(regelKlasse.getModifiers())) {
                    try {
                        Class<?>[] parameterTypes = bepaalParameterTypes(regelKlasse);
                        final Method method = regelKlasse.getMethod("voerRegelUit", parameterTypes);
                        method.invoke(regelKlasse.newInstance(), mockParameterValues(parameterTypes));
                    } catch (final InvocationTargetException e) {
                        // Als we de methode succesvol hebben aangeroepen, verwachten we een ClassCastException vanwege
                        // onrechtmatig gebruik van de bridge methode, nl. met een te generiek type.
                        Assert.assertEquals(ClassCastException.class, e.getCause().getClass());
                    } catch (final NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                        Assert.fail("Onverwachte fout opgetreden bij testen bridge methode voor klasse: "
                                + regelKlasse.getSimpleName());
                    }
                }
            }
        }
    }

    private Object[] mockParameterValues(final Class<?>[] parameterTypes) {
        Object[] parameterValues = new Object[parameterTypes.length];
        for (int i = 0; i < parameterValues.length; i++) {
            parameterValues[i] = Mockito.mock(parameterTypes[i]);
        }
        return parameterValues;
    }

    private Class<?>[] bepaalParameterTypes(final Class<?> regelKlasse) {
        Class<?>[] parameterTypes = new Class<?>[0];
        if (VoorBerichtRegel.class.isAssignableFrom(regelKlasse)) {
            parameterTypes = new Class[] { BerichtBericht.class };
        } else if (VoorActieRegel.class.isAssignableFrom(regelKlasse)) {
            parameterTypes = new Class[] { RootObject.class, BerichtRootObject.class, Actie.class, Map.class };
        } else if (NaActieRegel.class.isAssignableFrom(regelKlasse)) {
            parameterTypes = new Class[] { RootObject.class, BerichtRootObject.class };
        }
        return parameterTypes;
    }

    private <T> Set<Class<?>> zonderGenerics(final Set<Class<? extends T>> metGenerics) {
        Set<Class<?>> zonderGenerics = new HashSet<>();
        for (Class<? extends T> klasse : metGenerics) {
            zonderGenerics.add(klasse);
        }
        return zonderGenerics;
    }

}
