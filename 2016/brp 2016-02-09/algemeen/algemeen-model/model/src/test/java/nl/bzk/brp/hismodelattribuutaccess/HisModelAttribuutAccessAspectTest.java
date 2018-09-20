/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.hismodelattribuutaccess;

import java.lang.reflect.Method;
import java.util.Set;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class HisModelAttribuutAccessAspectTest {

    private HisModelAttribuutAccessAspect        aspect;
    private HisModelAttribuutAccessAdministratie administratie;
    private Regel                                regel1;
    private Regel                                regel2;
    private JoinPoint                            persoonAdresGetSoortJoinPoint;
    private long                                 persoonAdresVoorkomenId;
    private int                                  persoonAdresDbObjectId;
    private int                                  persoonAdresSoortDbObjectId;

    public HisModelAttribuutAccessAspectTest() {
        PersoonHisVolledigImpl persoon =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        PersoonAdresHisVolledigImpl persoonAdres = new PersoonAdresHisVolledigImpl(persoon);
        PersoonAdresStandaardGroepBericht persoonAdresStandaardGroep = new PersoonAdresStandaardGroepBericht();
        HisPersoonAdresModel persoonAdresModel =
                new HisPersoonAdresModel(persoonAdres, persoonAdresStandaardGroep, new MaterieleHistorieImpl(),
                        new ActieModel(new ActieRegistratieAdresBericht(), null));
        this.persoonAdresVoorkomenId = 919;
        ReflectionTestUtils.setField(persoonAdresModel, "iD", (int) this.persoonAdresVoorkomenId);
        this.persoonAdresGetSoortJoinPoint = this.mockJoinPoint("getSoort", persoonAdresModel);
        this.persoonAdresDbObjectId = 6063;
        this.persoonAdresSoortDbObjectId = 9771;
        this.regel1 = Regel.ALG0001;
        this.regel2 = Regel.ALG0002;
    }

    @Before
    public void before() {
        this.aspect = new HisModelAttribuutAccessAspect();
        this.administratie = new HisModelAttribuutAccessAdministratieImpl();
        this.aspect.setAdministratie(this.administratie);
    }

    @Test
    public void testAdministratieNull() {
        this.aspect.setAdministratie(null);
        this.aspect.voorHisModelGetter(this.persoonAdresGetSoortJoinPoint);
        // Geen regels geregistreerd, maar ook geen fout opgetreden.
        this.testAantalRegels(0);
    }

    @Test
    public void testAdministratieNietActief() {
        this.aspect.voorHisModelGetter(this.persoonAdresGetSoortJoinPoint);
        // Geen regels geregistreerd, maar ook geen fout opgetreden.
        this.testAantalRegels(0);
    }

    @Test
    public void testNormaleRegistratie() {
        this.administratie.activeer();
        this.administratie.setHuidigeRegel(this.regel1);
        this.aspect.voorHisModelGetter(this.persoonAdresGetSoortJoinPoint);

        this.testAantalRegels(1);
        this.testRegel(this.regel1, 1);

        // Check of de geregistreerde attribuut access data klopt.
        AttribuutAccess attribuutAccess =
                this.administratie.getRegelAttribuutAccess().get(this.regel1).iterator().next();
        Assert.assertEquals(this.persoonAdresDbObjectId, attribuutAccess.getGroepDbObjectId());
        Assert.assertEquals(this.persoonAdresVoorkomenId, attribuutAccess.getVoorkomenId().longValue());
        Assert.assertEquals(this.persoonAdresSoortDbObjectId, attribuutAccess.getAttribuutDbObjectId());

        this.administratie.setHuidigeRegel(this.regel2);
        this.aspect.voorHisModelGetter(this.persoonAdresGetSoortJoinPoint);

        this.testAantalRegels(2);
        this.testRegel(this.regel1, 1);
        this.testRegel(this.regel2, 1);
    }

    private void testAantalRegels(final int aantalRegels) {
        Assert.assertEquals(aantalRegels, this.administratie.getRegelAttribuutAccess().size());
    }

    private void testRegel(final Regel regel, final int aantalAttribuutAccessen) {
        Set<AttribuutAccess> attribuutAccessen = this.administratie.getRegelAttribuutAccess().get(regel);
        Assert.assertNotNull(attribuutAccessen);
        Assert.assertEquals(aantalAttribuutAccessen, attribuutAccessen.size());
    }

    /**
     * De enige onderdelen van het joinpoint die in deze context nodig zijn, zijn de methode naam
     * en het target object. De rest van de methodes worden niet geimplementeerd.
     *
     * @param methodeNaam de naam van de methode die intercept wordt
     * @param target      het object waarop de methode intercept wordt
     * @return het gemockte joinpoint
     */
    public JoinPoint mockJoinPoint(final String methodeNaam, final Object target) {
        return new JoinPoint() {

            @Override
            public String toShortString() {
                return null;
            }

            @Override
            public String toLongString() {
                return null;
            }

            @Override
            public Object getThis() {
                return null;
            }

            @Override
            public Object getTarget() {
                return target;
            }

            @Override
            public Object[] getArgs() {
                return null;
            }

            @Override
            public Signature getSignature() {
                return new MethodSignature() {

                    @Override
                    public String toShortString() {
                        return null;
                    }

                    @Override
                    public String toLongString() {
                        return null;
                    }

                    @Override
                    public String getName() {
                        return methodeNaam;
                    }

                    @Override
                    public int getModifiers() {
                        return 0;
                    }

                    @Override
                    public String getDeclaringTypeName() {
                        return null;
                    }

                    @Override
                    public Class<?> getDeclaringType() {
                        return null;
                    }

                    @Override
                    public Class getReturnType() {
                        return null;
                    }

                    @Override
                    public Method getMethod() {
                        Method method = null;
                        try {
                            method = HisPersoonAdresModel.class.getMethod(methodeNaam);
                        } catch (NoSuchMethodException e) {
                            throw new IllegalStateException(e);
                        }
                        return method;
                    }

                    @Override
                    public Class[] getParameterTypes() {
                        return new Class[0];
                    }

                    @Override
                    public String[] getParameterNames() {
                        return new String[0];
                    }

                    @Override
                    public Class[] getExceptionTypes() {
                        return new Class[0];
                    }
                };
            }

            @Override
            public SourceLocation getSourceLocation() {
                return null;
            }

            @Override
            public String getKind() {
                return null;
            }

            @Override
            public StaticPart getStaticPart() {
                return null;
            }
        };
    }
}
