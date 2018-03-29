package nl.bzk.algemeenbrp.dal.domein;

import nl.bzk.algemeenbrp.dal.domein.brp.GetterSetterTester;

import org.junit.Test;

public class EntityGetterSetterTest {

    @Test
    public void testEntiteiten() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.algemeenbrp.dal.domein.brp.entity");
    }

    @Test(expected = IllegalStateException.class)
    public void testEntiteitenVerkeerdePackage() throws ReflectiveOperationException {
        new GetterSetterTester().testEntities("nl.bzk.algemeen.dal.domein.brp.kern.entity");
    }
}
