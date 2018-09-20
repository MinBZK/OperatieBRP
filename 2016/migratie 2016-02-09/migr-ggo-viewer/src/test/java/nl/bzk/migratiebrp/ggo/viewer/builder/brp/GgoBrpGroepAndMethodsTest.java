/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Sinds de verbouwing naar het Entity-model controleert deze test of de Dbobjects van het Entity-model zijn
 * gerepresenteerd in de GgoBrpGroepEnum en GgoBrpElementEnum structuren.
 *
 * In de meeste gevallen wordt er namelijk gebruik gemaakt van deze representatie om het label voor een Entity te
 * bepalen. Omdat dit echter niet altijd het geval is moet er in deze test wat filtering plaatsvinden om de relevante
 * elementen over te houden.
 */
public class GgoBrpGroepAndMethodsTest {

    @Test
    public void testRelevantieVanWeergegevenGroepen() {
        for (final Element element : Element.values()) {
            if (SoortElement.GROEP.equals(element.getSoort()) || SoortElement.OBJECTTYPE.equals(element.getSoort())) {
                final boolean relevant = tabelLijktRelevant(element);
                final GgoBrpGroepEnum groep = GgoBrpGroepEnum.findByElement(element);
                if (relevant) {
                    Assert.assertTrue(element.toString() + " niet gevonden in GgoBrpGroepEnum", groep != null);
                }
            }
        }
    }

    @Test
    public void testRelevantieVanWeergegevenElementen() {
        for (final Element element : Element.values()) {
            if (SoortElement.ATTRIBUUT.equals(element.getSoort())) {

                final boolean tabelRelevant =
                        !isVerwijzingNaarAndereTabel(element) && (tabelLijktRelevant(element.getGroep()) || tabelRelevantVoorKolommen(element.getGroep()));
                final boolean kolomRelevant = !kolomWordtNietWeergegeven(element) && kolomLijktRelevant(element);

                final GgoBrpElementEnum ggoElement = GgoBrpElementEnum.findByElement(element);
                if (tabelRelevant && kolomRelevant) {
                    Assert.assertTrue(element.toString() + " niet gevonden in GgoBrpElementEnum", ggoElement != null);
                }
            }
        }
    }

    private boolean kolomLijktRelevant(final Element element) {
        final String naam = element.getLaatsteNaamDeel();

        boolean relevant = !"ID".equals(naam) && !"Persoon".equals(naam);
        // Standaard gegevens die niet zijn uitgeschreven in de GgoBrpElementEnum
        relevant &= !"TijdstipRegistratie".equals(naam) && !"TijdstipVerval".equals(naam);
        relevant &= !"DatumAanvangGeldigheid".equals(naam) && !"DatumEindeGeldigheid".equals(naam) && !"NadereAanduidingVerval".equals(naam);
        // Acties
        relevant &= !"ActieInhoud".equals(naam) && !"ActieVerval".equals(naam) && !"ActieAanpassingGeldigheid".equals(naam);
        // Gegevens op naam
        relevant |= Element.ACTIE_TIJDSTIPREGISTRATIE.equals(element) || Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.equals(element);
        relevant |= Element.ACTIE_IDENTITEIT.equals(element) || Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.equals(element);

        return relevant;
    }

    private boolean kolomWordtNietWeergegeven(final Element element) {
        // Niet weergegeven velden
        boolean nietWeergegeven =
                Element.BETROKKENHEID_ROLCODE.equals(element)
                        || Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.equals(element);
        nietWeergegeven |=
                Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE.equals(element)
                        || Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.equals(element);
        nietWeergegeven |= Element.PERSOON_VERIFICATIE_GEVERIFIEERDE.equals(element);
        return nietWeergegeven;
    }

    private boolean tabelLijktRelevant(final Element element) {
        if (element == null) {
            return false;
        }

        final String naam = element.getLaatsteNaamDeel();
        // Relevante generieke patronen
        boolean relevant = naam.startsWith("His_Pers") || "Doc".equals(naam);
        // Relevante specifieke tabelnamen
        relevant |= "ActieBron".equals(naam) || "AdministratieveHandeling".equals(naam);
        relevant |= "His_Doc".equals(naam) || "MultirealiteitRegel".equals(naam) || "His_MultirealiteitRegel".equals(naam);
        relevant |= "His_HuwelijkGeregistreerdPar".equals(naam) || "His_OuderOuderlijkGezag".equals(naam);
        relevant |= "His_OuderOuderschap".equals(naam) || "His_RelatieAfgeleidAdministr".equals(naam);
        // Tabellen waarvan we ook actuele gegevens gebruiken
        relevant |= "PersNation".equals(naam) || "PersReisdoc".equals(naam) || "PersVoornaam".equals(naam);
        relevant |= "PersVerificatie".equals(naam) || "PersGeslnaamcomp".equals(naam);
        // Tabellen die we op een andere manier uitlepelen
        relevant &= !"His_PersIndicatie".equals(naam) && !"His_PersOnderzoek".equals(naam);
        return relevant;
    }

    private boolean tabelRelevantVoorKolommen(final Element element) {
        if (element == null) {
            return false;
        }

        final String naam = element.getNaam();

        return "Actie".equals(naam) || "Betrokkenheid".equals(naam);
    }

    private boolean isVerwijzingNaarAndereTabel(final Element element) {
        if (element == null) {
            return false;
        }

        final String naam = element.getNaam();

        boolean result = Element.PERSOON_REISDOCUMENT_PERSOONREISDOCUMENT.equals(element) || "Document".equals(naam) || "Relatie".equals(naam);
        result |= "Betrokkenheid".equals(naam) || "Actie".equals(naam) || "Rechtsgrond".equals(naam);
        result |= naam.endsWith("AdmHnd") || naam.startsWith("Persoon");
        return result;
    }

}
