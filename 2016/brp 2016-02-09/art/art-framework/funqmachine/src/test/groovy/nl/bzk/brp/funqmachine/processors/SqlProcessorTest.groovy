package nl.bzk.brp.funqmachine.processors

import nl.bzk.brp.funqmachine.configuratie.Database
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class SqlProcessorTest {
    private SqlProcessor processor

    @Rule
    public ExpectedException thrown = ExpectedException.none()

    @Test
    void kanEenvoudigStatementUitvoeren() {
        processor = new SqlProcessor(Database.KERN)

        def rows = processor.select('SELECT * FROM kern.pers;')

        assert rows != null
    }

    @Test
    void kanPersoonResetDoen() {
        processor = new SqlProcessor(Database.KERN)

        thrown.expectMessage('invalid schema name: TEST')

        processor.resetPersonen(['1234321'])
    }

    @Test
    void kanLaatsteLeveringResetten() {
        processor = new SqlProcessor(Database.KERN)

        Long handelingId = processor.resetLevering('340014155', 1)
        assert handelingId == null
    }

    @Test
    void kanEersteLeveringResetten() {
        processor = new SqlProcessor(Database.KERN)

        Long handelingId = processor.resetLevering('340014155', -1)
        assert handelingId == null
    }

    @Test
    void kanGearchiveerdSynchroonBerichtNietVinden() {
        processor = new SqlProcessor(Database.BER)

        boolean isGearchiveerd = processor.isSynchroonBerichtGearchiveerd('Ingaand', '')
        assert !isGearchiveerd
    }

    @Test(expected = IllegalArgumentException.class)
    void kanGearchiveerdSynchroonBerichtNietVindenDoorFoutieveRichting() {
        processor = new SqlProcessor(Database.BER)

        processor.isSynchroonBerichtGearchiveerd('Ingaaaaaaaaaaand', '')
    }

    @Test
    void kanGearchiveerdLeveringBerichtNietVinden() {
        processor = new SqlProcessor(Database.BER)

        boolean isGearchiveerd = processor.isLeveringBerichtGearchiveerd('')
        assert !isGearchiveerd
    }
}
