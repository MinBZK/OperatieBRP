/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIndicatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIstGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.springframework.stereotype.Component;

/**
 * In deze stap wordt alle herkomsten van onderzoeken die in de BRP persoonslijst voorkomen verzameld en terug gegeven.
 */
@Component
public final class Lo3VerzamelBrpOnderzoeken {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Verzameld alle herkomsten van onderzoeken die aan de opgegeven persoonslijst zijn gekoppeld.
     * @param brpPersoonslijst de BRP Persoonslijst waar onderzoeken aan gekoppeld zijn
     * @return een lijst {@link Lo3Herkomst} van onderzoeken die in de persoonslijst voorkomen
     */
    public List<Lo3Herkomst> verzamelHerkomstenBijOnderzoek(final BrpPersoonslijst brpPersoonslijst) {
        final List<Lo3Herkomst> herkomsten = new ArrayList<>();
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getNaamgebruikStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getAdresStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getPersoonAfgeleidAdministratiefStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getBehandeldAlsNederlanderIndicatieStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getSignaleringMetBetrekkingTotVerstrekkenReisdocumentStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getBijhoudingStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getDerdeHeeftGezagIndicatieStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getDeelnameEuVerkiezingenStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getGeboorteStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getGeslachtsaanduidingStapel());
        verzamelHerkomstenInStapels(herkomsten, brpPersoonslijst.getGeslachtsnaamcomponentStapels());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getIdentificatienummerStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getMigratieStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getInschrijvingStapel());
        verzamelHerkomstenInStapels(herkomsten, brpPersoonslijst.getNationaliteitStapels());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getNummerverwijzingStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getOnderCurateleIndicatieStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getOverlijdenStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getPersoonskaartStapel());
        verzamelHerkomstenInStapels(herkomsten, brpPersoonslijst.getReisdocumentStapels());
        verzamelHerkomstenInRelaties(herkomsten, brpPersoonslijst);
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getSamengesteldeNaamStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getStaatloosIndicatieStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getUitsluitingKiesrechtStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getVastgesteldNietNederlanderIndicatieStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getVerblijfsrechtStapel());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getVerstrekkingsbeperkingIndicatieStapel());
        verzamelHerkomstenInStapels(herkomsten, brpPersoonslijst.getVoornaamStapels());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getBijzondereVerblijfsrechtelijkePositieIndicatieStapel());
        verzamelHerkomstenInStapels(herkomsten, brpPersoonslijst.getVerificatieStapels());
        verzamelHerkomstenInStapels(herkomsten, brpPersoonslijst.getBuitenlandsPersoonsnummerStapels());
        verzamelHerkomstenInStapel(herkomsten, brpPersoonslijst.getOnverwerktDocumentAanwezigIndicatieStapel());

        return herkomsten;
    }

    private void verzamelHerkomstenInRelaties(final List<Lo3Herkomst> herkomsten, final BrpPersoonslijst brpPersoonslijst) {
        final List<Lo3Herkomst> gebruikteHerkomsten = new ArrayList<>();
        for (final BrpRelatie relatie : brpPersoonslijst.getRelaties()) {
            // Verzamelen van herkomsten uit de relaties.
            verzamelGebruikteRelatieHerkomsten(gebruikteHerkomsten, relatie);

            // Verzamelen van de onderzoek/herkomst uit de relaties.
            verzamelHerkomstenInBetrokkenheden(herkomsten, relatie.getBetrokkenheden());
            verzamelHerkomstenInStapel(herkomsten, relatie.getRelatieStapel());
            verzamelHerkomstenVanAttribuut(herkomsten, relatie);
        }
        // Verzamelen van IST onderzoek/herkomst, zonder de herkomsten die in de relaties worden gebruikt.
        verzamelHerkomstenInIstStapels(herkomsten, brpPersoonslijst, gebruikteHerkomsten);
    }

    private void verzamelHerkomstenInIstStapels(
            final List<Lo3Herkomst> herkomsten,
            final BrpPersoonslijst brpPersoonslijst,
            final List<Lo3Herkomst> gebruikteHerkomsten) {
        verzamelHerkomstenInIstStapel(herkomsten, brpPersoonslijst.getIstOuder1Stapel(), gebruikteHerkomsten);
        verzamelHerkomstenInIstStapel(herkomsten, brpPersoonslijst.getIstOuder2Stapel(), gebruikteHerkomsten);
        verzamelHerkomstenInIstStapels(herkomsten, brpPersoonslijst.getIstHuwelijkOfGpStapels(), gebruikteHerkomsten);
        verzamelHerkomstenInIstStapels(herkomsten, brpPersoonslijst.getIstKindStapels(), gebruikteHerkomsten);
        verzamelHerkomstenInIstStapel(herkomsten, brpPersoonslijst.getIstGezagsverhoudingsStapel(), gebruikteHerkomsten);
    }

    private <T extends AbstractBrpIstGroepInhoud> void verzamelHerkomstenInIstStapels(
            final List<Lo3Herkomst> herkomsten,
            final List<BrpStapel<T>> istStapels,
            final List<Lo3Herkomst> gebruikteHerkomsten) {
        for (final BrpStapel<T> istStapel : istStapels) {
            verzamelHerkomstenInIstStapel(herkomsten, istStapel, gebruikteHerkomsten);
        }
    }

    private <T extends AbstractBrpIstGroepInhoud> void verzamelHerkomstenInIstStapel(
            final List<Lo3Herkomst> herkomsten,
            final BrpStapel<T> istStapel,
            final List<Lo3Herkomst> gebruikteHerkomsten) {
        if (istStapel == null) {
            return;
        }

        for (final BrpGroep<T> istGroep : istStapel.getGroepen()) {
            final BrpIstStandaardGroepInhoud istInhoud = istGroep.getInhoud().getStandaardGegevens();
            if (BrpValidatie.isEenParameterGevuld(
                    istInhoud.getRubriek8310AanduidingGegevensInOnderzoek(),
                    istInhoud.getRubriek8320DatumIngangOnderzoek(),
                    istInhoud.getRubriek8330DatumEindeOnderzoek())) {
                final Lo3Herkomst herkomst = new Lo3Herkomst(istInhoud.getCategorie(), istInhoud.getStapel(), istInhoud.getVoorkomen());
                if (!gebruikteHerkomsten.contains(herkomst)) {
                    herkomsten.add(herkomst);
                }
            }
        }
    }

    private void verzamelGebruikteRelatieHerkomsten(final List<Lo3Herkomst> gebruikteHerkomsten, final BrpRelatie relatie) {
        verzamelGebruikteBetrokkenhedenHerkomsten(gebruikteHerkomsten, relatie.getBetrokkenheden());
        verzamelGebruikteHerkomsten(gebruikteHerkomsten, relatie.getRelatieStapel());
    }

    private void verzamelGebruikteBetrokkenhedenHerkomsten(final List<Lo3Herkomst> herkomsten, final List<BrpBetrokkenheid> betrokkenheden) {
        for (final BrpBetrokkenheid betrokkenheid : betrokkenheden) {
            verzamelGebruikteHerkomsten(herkomsten, betrokkenheid.getIdentificatienummersStapel());
            verzamelGebruikteHerkomsten(herkomsten, betrokkenheid.getGeslachtsaanduidingStapel());
            verzamelGebruikteHerkomsten(herkomsten, betrokkenheid.getGeboorteStapel());
            verzamelGebruikteHerkomsten(herkomsten, betrokkenheid.getSamengesteldeNaamStapel());
            verzamelGebruikteHerkomsten(herkomsten, betrokkenheid.getOuderlijkGezagStapel());
            verzamelGebruikteHerkomsten(herkomsten, betrokkenheid.getOuderStapel());
        }
    }

    private <T extends BrpGroepInhoud> void verzamelGebruikteHerkomsten(final List<Lo3Herkomst> herkomsten, final BrpStapel<T> stapel) {
        if (stapel == null) {
            return;
        }

        for (final BrpGroep<T> groep : stapel) {
            final Lo3Herkomst herkomst = groep.getActieInhoud().getLo3Herkomst();
            if (!herkomsten.contains(herkomst)) {
                herkomsten.add(herkomst);
            }
        }
    }

    private void verzamelHerkomstenInBetrokkenheden(final List<Lo3Herkomst> herkomsten, final List<BrpBetrokkenheid> betrokkenheden) {
        for (final BrpBetrokkenheid betrokkenheid : betrokkenheden) {
            verzamelHerkomstenInStapel(herkomsten, betrokkenheid.getIdentificatienummersStapel());
            verzamelHerkomstenInStapel(herkomsten, betrokkenheid.getGeslachtsaanduidingStapel());
            verzamelHerkomstenInStapel(herkomsten, betrokkenheid.getGeboorteStapel());
            verzamelHerkomstenInStapel(herkomsten, betrokkenheid.getSamengesteldeNaamStapel());
            verzamelHerkomstenInStapel(herkomsten, betrokkenheid.getOuderlijkGezagStapel());
            verzamelHerkomstenInStapel(herkomsten, betrokkenheid.getOuderStapel());
        }
    }

    private <T extends BrpGroepInhoud> void verzamelHerkomstenInStapels(final List<Lo3Herkomst> herkomsten, final List<BrpStapel<T>> stapels) {
        for (final BrpStapel<T> stapel : stapels) {
            verzamelHerkomstenInStapel(herkomsten, stapel);
        }
    }

    private <T extends BrpGroepInhoud> void verzamelHerkomstenInStapel(final List<Lo3Herkomst> herkomsten, final BrpStapel<T> stapel) {
        if (stapel == null) {
            return;
        }

        for (final BrpGroep<T> groep : stapel.getGroepen()) {
            final T inhoud = groep.getInhoud();
            final BrpActie actieGeldigheid = groep.getActieGeldigheid();

            verzamelHerkomstenInInhoud(herkomsten, inhoud);
            verzamelHerkomstenInActie(herkomsten, groep.getActieInhoud());
            verzamelHerkomstenInActie(herkomsten, groep.getActieVerval());
            verzamelHerkomstenInActie(herkomsten, actieGeldigheid);
            verzamelHerkomstenInHistorie(herkomsten, groep.getHistorie());
        }
    }

    private <T extends BrpGroepInhoud> void verzamelHerkomstenInInhoud(final List<Lo3Herkomst> herkomsten, final T inhoud) {
        verzamelHerkomstenVanAttribuut(herkomsten, inhoud);
    }

    private void verzamelHerkomstenVanAttribuut(final List<Lo3Herkomst> herkomsten, final Object object) {
        final Field[] fields;

        if (AbstractBrpIndicatieGroepInhoud.class.isAssignableFrom(object.getClass())) {
            fields = object.getClass().getSuperclass().getDeclaredFields();
        } else {
            fields = object.getClass().getDeclaredFields();
        }

        for (final Field field : fields) {
            if (AbstractBrpAttribuutMetOnderzoek.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
                    verzamelHerkomstVanAttribuut(herkomsten, (AbstractBrpAttribuutMetOnderzoek) field.get(object));
                } catch (final IllegalAccessException | IllegalArgumentException e) {
                    LOGGER.warn("Kan onderzoek niet bepalen voor BRP persoonslijst\n{}", e);
                }
            }
        }
    }

    private void verzamelHerkomstVanAttribuut(final List<Lo3Herkomst> herkomsten, final BrpAttribuutMetOnderzoek attribuut) {
        if (attribuut != null && attribuut.isInhoudelijkGevuld() && attribuut.getOnderzoek() != null) {
            final Lo3Onderzoek onderzoek = attribuut.getOnderzoek();
            final Lo3Herkomst lo3Herkomst = onderzoek.getLo3Herkomst();
            if (!herkomsten.contains(lo3Herkomst)) {
                herkomsten.add(lo3Herkomst);
            }
        }
    }

    private void verzamelHerkomstenInActie(final List<Lo3Herkomst> herkomsten, final BrpActie actie) {
        if (actie == null) {
            return;
        }

        verzamelHerkomstenVanAttribuut(herkomsten, actie);

        final List<BrpActieBron> actieBronnen = actie.getActieBronnen();
        if (actieBronnen != null) {
            for (final BrpActieBron actieBron : actieBronnen) {
                verzamelHerkomstenVanAttribuut(herkomsten, actieBron);
                final BrpStapel<BrpDocumentInhoud> documentStapel = actieBron.getDocumentStapel();
                verzamelHerkomstenVanDocument(herkomsten, documentStapel);
            }
        }
    }

    private void verzamelHerkomstenVanDocument(List<Lo3Herkomst> herkomsten, BrpStapel<BrpDocumentInhoud> documentStapel) {
        if (documentStapel != null) {
            for (final BrpGroep<BrpDocumentInhoud> groep : documentStapel.getGroepen()) {
                verzamelHerkomstenVanAttribuut(herkomsten, groep.getInhoud());
            }
        }
    }

    private void verzamelHerkomstenInHistorie(final List<Lo3Herkomst> herkomsten, final BrpHistorie historie) {
        verzamelHerkomstenVanAttribuut(herkomsten, historie);
    }
}
