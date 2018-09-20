/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.AanduidingBezitBuitenlandsReisdocumentConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.AanduidingEuropeesKiesrechtConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.AanduidingHuisnummerConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.AanduidingUitgeslotenKiesrechtConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.FunctieAdresConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.GeslachtsaanduidingConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.IndicatieCurateleConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.IndicatieDocumentConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.IndicatieGeheimConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.IndicatiePKConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.NaamgebruikConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.SignaleringConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch.SoortVerbintenisConversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;

/**
 * De implementatie van de ConversietabelFactory die gebruik maakt van JPA repositories om conversietabellen uit de
 * database in te lezen.
 */
// CHECKSTYLE:OFF - grote fan out complexity omdat alle conversietabellen via deze class worden aangemaakt.
public abstract class AbstractConversietabelFactory implements ConversietabelFactory {
    // CHECKSTYLE:ON

    @Override
    public final Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode>
            createGeslachtsaanduidingConversietabel() {
        return new GeslachtsaanduidingConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingNaamgebruikCode, BrpWijzeGebruikGeslachtsnaamCode>
            createWijzeGebruikGeslachtsnaamConversietabel() {
        return new NaamgebruikConversietabel();
    }

    @Override
    public final Conversietabel<Lo3FunctieAdres, BrpFunctieAdresCode> createFunctieAdresConversietabel() {
        return new FunctieAdresConversietabel();
    }

    @Override
    public final Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> createSoortRelatieConversietabel() {
        return new SoortVerbintenisConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingUitgeslotenKiesrecht, Boolean>
            createAanduidingUitgeslotenKiesrechtConversietabel() {
        return new AanduidingUitgeslotenKiesrechtConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingEuropeesKiesrecht, Boolean>
            createAanduidingEuropeesKiesrechtConversietabel() {
        return new AanduidingEuropeesKiesrechtConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatiePKVolledigGeconverteerdCode, Boolean> createIndicatiePKConversietabel() {
        return new IndicatiePKConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatieGeheimCode, Boolean> createIndicatieGeheimConversietabel() {
        return new IndicatieGeheimConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingHuisnummer, BrpAanduidingBijHuisnummerCode>
            createAanduidingHuisnummerConversietabel() {
        return new AanduidingHuisnummerConversietabel();
    }

    @Override
    public final Conversietabel<Lo3Signalering, Boolean> createSignaleringConversietabel() {
        return new SignaleringConversietabel();
    }

    @Override
    public final Conversietabel<Lo3AanduidingBezitBuitenlandsReisdocument, Boolean>
            createAanduidingBezitBuitenlandsReisdocumentConversietabel() {
        return new AanduidingBezitBuitenlandsReisdocumentConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatieDocument, Boolean> createIndicatieDocumentConversietabel() {
        return new IndicatieDocumentConversietabel();
    }

    @Override
    public final Conversietabel<Lo3IndicatieCurateleregister, Boolean> createIndicatieCurateleConversietabel() {
        return new IndicatieCurateleConversietabel();
    }
}
