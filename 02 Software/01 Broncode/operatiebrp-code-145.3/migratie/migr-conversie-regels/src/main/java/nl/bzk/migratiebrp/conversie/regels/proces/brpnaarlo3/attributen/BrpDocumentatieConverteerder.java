/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.FoutmeldingUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

/**
 * Converteerder met de logica om een brp actie om te zetten naar lo3 documentatie.
 */
public class BrpDocumentatieConverteerder {

    private final BrpAttribuutConverteerder converteerder;

    /**
     * constructor.
     * @param attribuutConverteerder attribuut converteerder
     */
    public BrpDocumentatieConverteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
        this.converteerder = attribuutConverteerder;
    }

    /**
     * Maak Lo3 documentatie obv een actie.
     * @param actie actie
     * @return documentatie
     */
    @Requirement(Requirements.CBA001_BL01)
    @Definitie({Definities.DEF047, Definities.DEF048, Definities.DEF049, Definities.DEF050, Definities.DEF085, Definities.DEF086})
    @Preconditie(SoortMeldingCode.PRE085)
    public Lo3Documentatie maakDocumentatie(final BrpActie actie) {
        if (actie == null) {
            return null;
        }

        Lo3RNIDeelnemerCode rniDeelnemer = null;
        Lo3String rechtsgrondOmschrijving = null;

        // DEF085
        if (converteerder.valideerRNIDeelnemerTegenBrp(actie.getPartijCode())) {
            rniDeelnemer = converteerder.converteerRNIDeelnemer(actie.getPartijCode());
            final List<BrpActieBron> actieBronnen = actie.getActieBronnen();
            rechtsgrondOmschrijving = bepaalRechtsgrondOmschrijving(actieBronnen);
        }

        final List<BrpStapel<BrpDocumentInhoud>> brpDocumentStapels = actie.getDocumentStapelsViaActieBron();

        final Lo3Documentatie documentatie;
        if (brpDocumentStapels == null || brpDocumentStapels.isEmpty()) {
            // DEF049 Er is geen akte of ander brondocument
            documentatie = new Lo3Documentatie(actie.getId(), null, null, null, null, null, rniDeelnemer, rechtsgrondOmschrijving);
        } else if (brpDocumentStapels.size() == 1) {
            documentatie = maakDocumentUitEnkelvoudigeStapel(actie, rniDeelnemer, rechtsgrondOmschrijving, brpDocumentStapels);
        } else {
            documentatie = maakDocumentatieUitMeervoudigeStapel(actie, rniDeelnemer, rechtsgrondOmschrijving, brpDocumentStapels);

        }

        return documentatie;

    }

    private Lo3Documentatie maakDocumentatieUitMeervoudigeStapel(final BrpActie actie, final Lo3RNIDeelnemerCode rniDeelnemer,
                                                                 final Lo3String rechtsgrondOmschrijving,
                                                                 final List<BrpStapel<BrpDocumentInhoud>> brpDocumentStapels) {
        final Lo3Documentatie documentatie;// DEF050
        final Lo3Datum datumDocument = converteerDatumDocument(actie);
        final StringBuilder omschrijving = new StringBuilder();
        Lo3GemeenteCode partij = null;
        BrpPartijCode docBrpPartijCode = null;

        for (final BrpStapel<BrpDocumentInhoud> stapel : brpDocumentStapels) {
            final BrpDocumentInhoud docInhoud = stapel.getLaatsteElement().getInhoud();
            if (docBrpPartijCode == null) {
                docBrpPartijCode = docInhoud.getPartijCode();
                partij = converteerPartijCode(docBrpPartijCode);
            }
            // Preconditie 108 dwingt af dat dezelfde partij over alle documentatie hetzelfde is
            controleerPreconditie108(docBrpPartijCode, docInhoud);
            // Preconditie 085 dwingt af dat er of een omschrijving of aktenummer is.
            controleerPreconditie085(docInhoud);

            if (omschrijving.length() > 0) {
                omschrijving.append(", ");
            }

            if (heeftOmschrijving(docInhoud)) {
                omschrijving.append(docInhoud.getOmschrijving());
            } else {
                omschrijving.append(docInhoud.getAktenummer());
            }
        }

        documentatie =
                new Lo3Documentatie(actie.getId(), null, null, partij, datumDocument, Lo3String.wrap(omschrijving.toString()), rniDeelnemer,
                        rechtsgrondOmschrijving);
        return documentatie;
    }

    private Lo3Documentatie maakDocumentUitEnkelvoudigeStapel(final BrpActie actie, final Lo3RNIDeelnemerCode rniDeelnemer,
                                                              final Lo3String rechtsgrondOmschrijving,
                                                              final List<BrpStapel<BrpDocumentInhoud>> brpDocumentStapels) {
        final Lo3Documentatie documentatie;
        final BrpDocumentInhoud brpDocument = brpDocumentStapels.get(0).getLaatsteElement().getInhoud();

        // Preconditie 085 dwingt af dat er of een omschrijving of aktenummer is.
        controleerPreconditie085(brpDocument);
        final Lo3GemeenteCode gemeenteDocument = converteerPartijCode(brpDocument.getPartijCode());
        if (heeftOmschrijving(brpDocument)) {
            // DEF047
            final Lo3Datum datumDocument = converteerDatumDocument(actie);
            documentatie =
                    new Lo3Documentatie(actie.getId(), null, null, gemeenteDocument, datumDocument,
                            converteerder.converteerString(brpDocument.getOmschrijving()), rniDeelnemer, rechtsgrondOmschrijving);
        } else {
            // DEF048
            documentatie =
                    new Lo3Documentatie(actie.getId(), gemeenteDocument, converteerder.converteerString(brpDocument.getAktenummer()), null, null, null,
                            rniDeelnemer, rechtsgrondOmschrijving);
        }
        return documentatie;
    }

    private Lo3String bepaalRechtsgrondOmschrijving(final List<BrpActieBron> actieBronnen) {
        Lo3String rechtsgrondOmschrijving = null;
        if (actieBronnen != null && !actieBronnen.isEmpty()) {
            // 1 van de actiebronnen bevat de rechtsgrondomschrijving
            for (final BrpActieBron actieBron : actieBronnen) {
                if (actieBron.getRechtsgrondOmschrijving() != null) {
                    rechtsgrondOmschrijving = converteerder.converteerString(actieBron.getRechtsgrondOmschrijving());
                }
            }
        }
        return rechtsgrondOmschrijving;
    }

    private Lo3Datum converteerDatumDocument(final BrpActie actie) {
        Lo3Datum lo3DatumOntlening = actie.getDatumTijdRegistratie().converteerNaarLo3Datum();
        if (actie.getDatumOntlening() != null) {
            lo3DatumOntlening = actie.getDatumOntlening().converteerNaarLo3Datum();
        }
        return lo3DatumOntlening;
    }

    private Lo3GemeenteCode converteerPartijCode(final BrpPartijCode brpPartijCode) {
        return brpPartijCode == null ? null : converteerder.converteerGemeenteCode(brpPartijCode);
    }

    private void controleerPreconditie085(final BrpDocumentInhoud brpDocument) {
        // PRE085
        if (heeftAkteNummer(brpDocument) == heeftOmschrijving(brpDocument)) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE085, brpDocument);
        }
    }

    private boolean heeftOmschrijving(final BrpDocumentInhoud brpDocument) {
        final BrpString omschrijving = brpDocument.getOmschrijving();
        return omschrijving != null && omschrijving.isInhoudelijkGevuld();
    }

    private boolean heeftAkteNummer(final BrpDocumentInhoud brpDocument) {
        final BrpString akteNummer = brpDocument.getAktenummer();
        return akteNummer != null && akteNummer.isInhoudelijkGevuld();
    }

    private void controleerPreconditie108(final BrpPartijCode docPartij, final BrpDocumentInhoud brpDocument) {
        if (!AbstractBrpAttribuutMetOnderzoek.equalsWaarde(brpDocument.getPartijCode(), docPartij)) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE108, brpDocument);
        }
    }
}
