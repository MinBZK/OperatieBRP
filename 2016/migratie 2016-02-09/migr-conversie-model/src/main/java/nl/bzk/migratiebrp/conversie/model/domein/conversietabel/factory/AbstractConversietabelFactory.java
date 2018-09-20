/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.AanduidingEuropeesKiesrechtConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.AanduidingHuisnummerConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.AanduidingUitgeslotenKiesrechtConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.FunctieAdresConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.GeslachtsaanduidingConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.IndicatieCurateleConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.IndicatieDocumentConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.IndicatieGeheimConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.IndicatiePKConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.NaamgebruikConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.RegelConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.SignaleringConversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch.SoortVerbintenisConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;

/**
 * Gedeeltelijke implementatie van de conversietabel factory waarin de statische conversietabellen zijn geimplementeerd.
 */
@SuppressWarnings("checkstyle:classfanoutcomplexity")
public abstract class AbstractConversietabelFactory implements ConversietabelFactory {
    /* grote fan out complexity omdat alle conversietabellen via deze class worden aangemaakt. */

    @Override
    public final Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode> createGeslachtsaanduidingConversietabel() {
        return new GeslachtsaanduidingConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingNaamgebruikCode, BrpNaamgebruikCode> createNaamgebruikConversietabel() {
        return new NaamgebruikConversietabel();
    }

    @Override
    public final Conversietabel<Lo3FunctieAdres, BrpSoortAdresCode> createFunctieAdresConversietabel() {
        return new FunctieAdresConversietabel();
    }

    @Override
    public final Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> createSoortRelatieConversietabel() {
        return new SoortVerbintenisConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingUitgeslotenKiesrecht, BrpBoolean> createAanduidingUitgeslotenKiesrechtConversietabel() {
        return new AanduidingUitgeslotenKiesrechtConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingEuropeesKiesrecht, BrpBoolean> createAanduidingEuropeesKiesrechtConversietabel() {
        return new AanduidingEuropeesKiesrechtConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatiePKVolledigGeconverteerdCode, BrpBoolean> createIndicatiePKConversietabel() {
        return new IndicatiePKConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatieGeheimCode, BrpBoolean> createIndicatieGeheimConversietabel() {
        return new IndicatieGeheimConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingHuisnummer, BrpAanduidingBijHuisnummerCode> createAanduidingHuisnummerConversietabel() {
        return new AanduidingHuisnummerConversietabel();
    }

    @Override
    public final Conversietabel<Lo3Signalering, BrpBoolean> createSignaleringConversietabel() {
        return new SignaleringConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatieDocument, BrpBoolean> createIndicatieDocumentConversietabel() {
        return new IndicatieDocumentConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatieCurateleregister, BrpBoolean> createIndicatieCurateleConversietabel() {
        return new IndicatieCurateleConversietabel();
    }

    @Override
    public final Conversietabel<Character, String> createRegelConversietabel() {
        return new RegelConversietabel();
    }
}
