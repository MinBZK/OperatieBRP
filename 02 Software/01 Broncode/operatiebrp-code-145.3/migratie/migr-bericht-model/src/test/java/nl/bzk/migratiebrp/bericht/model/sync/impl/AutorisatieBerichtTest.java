/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRecordsType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieType;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Assert;
import org.junit.Test;

public class AutorisatieBerichtTest {

    private final SyncBerichtFactory factory = SyncBerichtFactory.SINGLETON;

    /**
     * Test voor autorisatie bericht met alleen een actueel voorkomen, zonder einddatum.
     */
    @Test
    public void testAutorisatieBerichtActueel() {
        final String afnemerCode = "123456";
        final Integer datumIngang = 2012_01_01;
        final AutorisatieBericht bericht = maakAutorisatieBericht(afnemerCode, datumIngang, null);
        bericht.setMessageId(MessageIdGenerator.generateId());
        final Lo3Autorisatie lo3Autorisatie = maakLo3Autorisatie(afnemerCode, datumIngang, null);

        assertBericht(lo3Autorisatie, bericht);
    }

    /**
     * Test voor autorisatie bericht met afgesloten autorisatie.
     */
    @Test
    public void testAutorisatieBerichtMetHistorie() {
        final String afnemerCode = "002244";
        final Integer datumIngang = 1992_02_01;
        final Integer datumEinde = 1994_02_01;
        final AutorisatieBericht bericht = maakAutorisatieBericht(afnemerCode, datumIngang, datumEinde);
        bericht.setMessageId(MessageIdGenerator.generateId());
        final Lo3Autorisatie lo3Autorisatie = maakLo3Autorisatie(afnemerCode, datumIngang, datumEinde);

        assertBericht(lo3Autorisatie, bericht);
    }

    /**
     * Vergelijkt de twee autorisaties
     */
    private void assertBericht(final Lo3Autorisatie lo3Autorisatie, final AutorisatieBericht bericht) {
        final StringBuilder verschillenLog = new StringBuilder();
        final boolean result = Lo3AutorisatieVergelijker.vergelijk(verschillenLog, lo3Autorisatie, bericht.getAutorisatie());

        Assert.assertTrue(verschillenLog.toString(), result);

        final String xml = bericht.format();
        final SyncBericht parsed = factory.getBericht(xml);

        Assert.assertEquals(AutorisatieBericht.class, parsed.getClass());
        parsed.setMessageId(bericht.getMessageId());
        Assert.assertEquals(bericht, parsed);
    }

    /**
     * Maak een AutorisatieBericht aan voor gegeven afnemer.
     * @return AutorisatieBericht
     */
    private AutorisatieBericht maakAutorisatieBericht(final String afnemerCode, final Integer datumIngang, final Integer datumEinde) {
        final AutorisatieType type = new AutorisatieType();
        type.setAfnemerCode(afnemerCode);
        type.setAutorisatieTabelRegels(new AutorisatieRecordsType());
        type.getAutorisatieTabelRegels().getAutorisatieTabelRegel().add(maakBerichtInhoud(datumIngang, datumEinde));
        return new AutorisatieBericht(type);
    }

    /**
     * Maak een AutorisatieRecordType aan met verplichte velden en een startdatum en evt einddatum.
     * @return AutorisatieRecordType
     */
    private AutorisatieRecordType maakBerichtInhoud(final Integer datumIngang, final Integer datumEinde) {
        final AutorisatieRecordType record = new AutorisatieRecordType();
        record.setGeheimhoudingInd(new Short("0"));
        record.setVerstrekkingsBeperking(new Short("0"));
        record.setTabelRegelStartDatum(new BigInteger(datumIngang.toString()));
        record.setTabelRegelEindDatum(datumEinde == null ? null : new BigInteger(datumEinde.toString()));
        return record;
    }

    /**
     * Maak een Lo3Autorisatie aan met verplichte velden en een startdatum en evt einddatum.
     * @return Lo3Autorisatie
     */
    private Lo3Autorisatie maakLo3Autorisatie(final String afnemerCode, final Integer datumIngang, final Integer datumEinde) {
        final List<Lo3Categorie<Lo3AutorisatieInhoud>> groepen = new ArrayList<>();

        groepen.add(
                Lo3StapelHelper.lo3Cat(
                        maakInhoud(afnemerCode, datumIngang, datumEinde),
                        null,
                        Lo3StapelHelper.lo3His(null, datumIngang, datumIngang),
                        Lo3StapelHelper.lo3Her(35, 0, 0)));

        return new Lo3Autorisatie(new Lo3Stapel<>(groepen));

    }

    /**
     * Maak een Lo3AutorisatieInhoud aan met verplichte velden.
     * @return Lo3AutorisatieInhoud
     */
    private Lo3AutorisatieInhoud maakInhoud(final String afnemerCode, final Integer datumIngang, final Integer datumEinde) {
        final Lo3AutorisatieInhoud inhoud = new Lo3AutorisatieInhoud();
        inhoud.setAfnemersindicatie(afnemerCode);
        inhoud.setIndicatieGeheimhouding(0);
        inhoud.setVerstrekkingsbeperking(0);
        inhoud.setDatumIngang(new Lo3Datum(datumIngang));
        inhoud.setDatumEinde(datumEinde == null ? null : new Lo3Datum(datumEinde));
        return inhoud;
    }
}
