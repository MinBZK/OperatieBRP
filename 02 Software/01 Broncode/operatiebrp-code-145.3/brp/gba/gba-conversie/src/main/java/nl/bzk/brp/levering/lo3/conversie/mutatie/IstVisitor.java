/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.text.DecimalFormat;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.stereotype.Component;

/**
 * IST-visitor.
 */
@Component
public class IstVisitor {

    private final ConversietabelFactory conversietabellen;

    /**
     * Constructor.
     * @param conversietabellen conversie tabellen
     */
    @Inject
    public IstVisitor(final ConversietabelFactory conversietabellen) {
        this.conversietabellen = conversietabellen;
    }

    /**
     * Verwerk IST stapels.
     * @param istStapels IST-stapels
     * @param mutaties mutaties
     */
    public final void visit(final List<Stapel> istStapels, final Lo3Mutaties mutaties) {

        visit(istStapels, Lo3CategorieEnum.CATEGORIE_02, mutaties.geefOuder1Wijziging());
        visit(istStapels, Lo3CategorieEnum.CATEGORIE_03, mutaties.geefOuder2Wijziging());
    }

    private void visit(final List<Stapel> stapels, final Lo3CategorieEnum categorie, final Lo3WijzigingenCategorieOuder wijzigingen) {
        if (stapels != null && !stapels.isEmpty()) {
            final Stapel stapel = getStapel(stapels, categorie);

            if (stapel != null) {
                StapelVoorkomen oudsteVoorkomen = bepaalOudsteVoorkomen(stapel);
                if (oudsteVoorkomen != null) {
                    wijzigingen.registreerOudsteVoorkomenUitIstStapel(maakCategorie(categorie, oudsteVoorkomen));
                }
            }
        }
    }

    private Stapel getStapel(final List<Stapel> stapels, final Lo3CategorieEnum categorie) {
        final int categorieAsInt = categorie.getCategorieAsInt();

        for (final Stapel stapel : stapels) {
            final int stapelCategorie = Integer.parseInt(stapel.getCategorie());
            if (stapelCategorie == categorieAsInt) {
                return stapel;
            }
        }

        return null;
    }

    private StapelVoorkomen bepaalOudsteVoorkomen(final Stapel stapel) {
        StapelVoorkomen resultaat = null;
        for (final StapelVoorkomen voorkomen : stapel.getStapelvoorkomens()) {
            if (resultaat == null || voorkomen.getVolgnummer() > resultaat.getVolgnummer()) {
                resultaat = voorkomen;
            }
        }

        return resultaat;
    }

    private Lo3CategorieWaarde maakCategorie(final Lo3CategorieEnum categorie, final StapelVoorkomen voorkomen) {
        final Lo3CategorieWaarde resultaat = new Lo3CategorieWaarde(categorie, 0, 0);

        if (voorkomen.getAktenummer() != null) {
            // 81 Akte
            resultaat.addElement(Lo3ElementEnum.ELEMENT_8110, formatPartij(voorkomen.getPartij()));
            resultaat.addElement(Lo3ElementEnum.ELEMENT_8120, voorkomen.getAktenummer());
        } else if (voorkomen.getDocumentOmschrijving() != null) {
            // 82 Document
            resultaat.addElement(Lo3ElementEnum.ELEMENT_8210, formatPartij(voorkomen.getPartij()));
            resultaat.addElement(Lo3ElementEnum.ELEMENT_8220, formatDatum(voorkomen.getRubriek8220DatumDocument()));
            resultaat.addElement(Lo3ElementEnum.ELEMENT_8230, voorkomen.getDocumentOmschrijving());
        }

        // 83 Procedure
        resultaat.addElement(Lo3ElementEnum.ELEMENT_8310, formatOnderzoek(voorkomen.getRubriek8310AanduidingGegevensInOnderzoek()));
        resultaat.addElement(Lo3ElementEnum.ELEMENT_8320, formatDatum(voorkomen.getRubriek8320DatumIngangOnderzoek()));
        resultaat.addElement(Lo3ElementEnum.ELEMENT_8330, formatDatum(voorkomen.getRubriek8330DatumEindeOnderzoek()));

        // 84 Onjuist
        resultaat.addElement(Lo3ElementEnum.ELEMENT_8410, formatOnjuist(voorkomen.getRubriek8410OnjuistOfStrijdigOpenbareOrde()));

        // 85 Geldigheid
        resultaat.addElement(Lo3ElementEnum.ELEMENT_8510, formatDatum(voorkomen.getRubriek8510IngangsdatumGeldigheid()));

        // 86 Opneming
        resultaat.addElement(Lo3ElementEnum.ELEMENT_8610, formatDatum(voorkomen.getRubriek8610DatumVanOpneming()));

        return resultaat;
    }

    private String formatPartij(final Partij partij) {
        if (partij == null) {
            return null;
        }

        final BrpPartijCode partijCode = new BrpPartijCode(partij.getCode());
        final Lo3GemeenteCode gemeenteCode = conversietabellen.createPartijConversietabel().converteerNaarLo3(partijCode);
        return gemeenteCode.getWaarde();
    }

    private String formatOnderzoek(final Integer onderzoek) {
        return onderzoek == null ? null : new DecimalFormat("000000").format(onderzoek);
    }

    private String formatOnjuist(final Character onjuist) {
        return onjuist == null ? null : onjuist.toString();
    }

    private String formatDatum(final Integer datum) {
        return datum == null ? null : new Lo3Datum(datum).getWaarde();
    }

}
