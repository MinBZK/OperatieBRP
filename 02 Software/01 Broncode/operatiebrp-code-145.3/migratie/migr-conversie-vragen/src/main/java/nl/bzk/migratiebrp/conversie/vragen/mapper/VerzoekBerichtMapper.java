/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.gba.domain.bevraging.Basisvraag;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import nl.bzk.migratiebrp.conversie.vragen.ZoekCriterium;

/**
 * Mapt lo3 bevraging informatie naar een instantie van een basisvraag (adres- of persoonsvraag).
 */
public class VerzoekBerichtMapper {

    private final ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen;

    /**
     * Constructor.
     * @param conversieLo3NaarBrpVragen conversie tool om lo3- naar brp vragen te converteren
     */
    public VerzoekBerichtMapper(final ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen) {
        this.conversieLo3NaarBrpVragen = conversieLo3NaarBrpVragen;
    }

    /**
     * Mapt lo3 bevraging informatie naar een instantie van een basisvraag (adres- of persoonsvraag).
     * @param vraag een instantie van een basisvraag (adres- of persoonsvraag)
     * @param partijCode partij code
     * @param soortDienst soort dienst
     * @param gevraagdeRubrieken lijst van gevraagde rubrieken
     * @param zoekcriteria lijst van zoekcriteria
     * @param <T> subtype van basisvraag
     * @return een gevulde instantie van een basisvraag (adres- of persoonsvraag)
     */
    public <T extends Basisvraag> T mapNaarBrpVraag(final T vraag,
                                                    final String partijCode,
                                                    final SoortDienst soortDienst,
                                                    final List<String> gevraagdeRubrieken,
                                                    final List<Lo3CategorieWaarde> zoekcriteria) {
        vulAlgemeen(vraag, partijCode, soortDienst, gevraagdeRubrieken);
        vulZoekRubrieken(vraag, zoekcriteria);
        vulZoekCriteria(vraag, zoekcriteria);
        return vraag;
    }

    private void vulAlgemeen(final Basisvraag vraag, final String partijCode, final SoortDienst soortDienst, final List<String> gevraagdeRubrieken) {
        vulGevraagdeRubrieken(vraag, gevraagdeRubrieken);
        vraag.setSoortDienst(soortDienst);
        vraag.setPartijCode(partijCode);
    }

    private void vulGevraagdeRubrieken(final Basisvraag vraag, final List<String> rubrieken) {
        vraag.setGevraagdeRubrieken(rubrieken.stream().map(this::formatteerRubriek).collect(Collectors.toList()));
    }

    private void vulZoekRubrieken(final Basisvraag vraag, final List<Lo3CategorieWaarde> zoekWaardes) {
        vraag.setZoekRubrieken(converteerIdentificerendeGegevens(zoekWaardes));
    }

    private void vulZoekCriteria(final Basisvraag vraag, final List<Lo3CategorieWaarde> zoekWaardes) {
        vraag.setZoekCriteria(conversieLo3NaarBrpVragen.converteer(zoekWaardes).stream()
                .map(this::maakBrpZoekCriterium)
                .collect(Collectors.toList()));
    }

    private List<String> converteerIdentificerendeGegevens(final List<Lo3CategorieWaarde> zoekWaardes) {
        return zoekWaardes.stream()
                .collect(Collectors.toMap(
                        Lo3CategorieWaarde::getCategorie,
                        waarde -> waarde.getElementen().keySet()))
                .entrySet()
                .stream()
                .flatMap(this::elementStream).collect(Collectors.toList());
    }

    private Stream<String> elementStream(final Map.Entry<Lo3CategorieEnum, Set<Lo3ElementEnum>> entry) {
        return entry.getValue().stream().map(element -> entry.getKey().getCategorie() + "." + element.getElementNummer(true));
    }

    private String formatteerRubriek(final String rubriek) {
        return rubriek.replaceAll("^(\\d{2})(\\d{2})(\\d{2})$", "$1.$2.$3");
    }

    private nl.bzk.brp.gba.domain.bevraging.ZoekCriterium maakBrpZoekCriterium(final ZoekCriterium zoekCriterium) {
        final nl.bzk.brp.gba.domain.bevraging.ZoekCriterium resultaat = new nl.bzk.brp.gba.domain.bevraging.ZoekCriterium(zoekCriterium.getElement());
        resultaat.setWaarde(zoekCriterium.getWaarde());
        if (zoekCriterium.getOf() != null) {
            resultaat.setOf(maakBrpZoekCriterium(zoekCriterium.getOf()));
        }
        return resultaat;
    }
}
