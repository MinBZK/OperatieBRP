/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtElementAttribuut;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.Actiebron;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.Document;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * BerichtAdministratieveHandelingBuilder.
 */
@Bedrijfsregel(Regel.R2062)
final class BerichtAdministratieveHandelingBuilder {

    /**
     * document.
     */
    private static final String DOCUMENT = "document";

    /**
     * bronnen.
     */
    private static final String BRONNEN = "bronnen";

    /**
     * bron.
     */
    private static final String BRON = "bron";

    private BerichtAdministratieveHandelingBuilder() {
    }

    /**
     * Maakt een builder voor een {@link BerichtElement} voor een administratieve handeling.
     * @param berichtgegevens berichtgegevens
     * @param admhnd admhnd
     * @return bericht element
     */
    public static BerichtElement.Builder build(final Berichtgegevens berichtgegevens, final AdministratieveHandeling admhnd) {
        final MetaObject admhndMetaObject = admhnd.getMetaObject();
        final BerichtElement.Builder
                berichtElement =
                BerichtElement.builder().metNaam(BerichtUtil.lowercaseFirst(admhndMetaObject.getObjectElement().getXmlNaam()));
        berichtElement.metBerichtElementAttribuut(
                BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECT_SLEUTEL, String.valueOf(admhndMetaObject.getObjectsleutel())));
        berichtElement.metBerichtElementAttribuut(BerichtElementAttribuut.
                maakBuilder(BerichtConstanten.OBJECTTYPE, admhndMetaObject.getObjectElement().getXmlNaam()));
        final List<MetaAttribuut>
                handelingAttributenLijst =
                geefGesorteerdeGroepsAutorisatieAttributenVanGroep(admhndMetaObject,
                        ElementHelper.getGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT));
        for (MetaAttribuut metaAttribuut : handelingAttributenLijst) {
            if (Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId() == metaAttribuut.getAttribuutElement().getId()) {
                final String soortNaam = bepaalAdmHndSoortnaam(berichtgegevens, admhnd);
                final BerichtElement.Builder
                        berichtElementAttr =
                        BerichtElement.builder()
                                .metNaam(BerichtUtil.lowercaseFirst(metaAttribuut.getAttribuutElement().getXmlNaam()))
                                .metWaarde(soortNaam);
                berichtElement.metBerichtElement(berichtElementAttr);
                berichtElement.metBerichtElement(BerichtElement.builder()
                        .metNaam(BerichtUtil.lowercaseFirst(Element.ADMINISTRATIEVEHANDELING_CATEGORIENAAM.getElementWaarde().getXmlNaam()))
                        .metWaarde(admhnd.getSoort().getCategorie().getNaam()));
            } else {
                final BerichtElement.Builder berichtElementAttr = BerichtAttribuutBuilder.build(metaAttribuut, berichtgegevens);
                berichtElement.metBerichtElement(berichtElementAttr);
            }
        }

        voegGedeblokkeerdeMeldingenToe(berichtgegevens, admhnd, berichtElement);

        if (!admhnd.getActies().isEmpty()) {
            final List<Actiebron> admhndBronnen = new ArrayList<>();
            final List<Actie> acties = Lists.newArrayList(admhnd.getActies());
            acties.sort(ActieComparator.INSTANCE);
            //maak de actie elementen en verzamel de bijbehorende bronnen
            final List<BerichtElement.Builder> actieElementen = maakActieElementenEnVerzamelBronnen(berichtgegevens, admhndBronnen, acties);
            //maak de bron elementen
            maakBronnen(berichtgegevens, berichtElement, admhndBronnen);
            //maak het bijgehoudenActies element met de onderliggende acties
            final BerichtElement.Builder bijgehoudenActieElement = BerichtElement.builder().metNaam("bijgehoudenActies");
            bijgehoudenActieElement.metBerichtElementen(actieElementen);
            berichtElement.metBerichtElement(bijgehoudenActieElement);
        }
        return berichtElement;
    }

    private static void voegGedeblokkeerdeMeldingenToe(final Berichtgegevens berichtgegevens, final AdministratieveHandeling admhnd,
                                                       final BerichtElement.Builder berichtElement) {
        if (berichtgegevens.isGeautoriseerdVoorGedeblokkeerdeMeldingen()) {

            final Collection<MetaObject> gedeblokkeerdeMeldingen = admhnd.getMetaObject().getObjecten(ElementHelper.getObjectElement(Element.REGEL));

            if (gedeblokkeerdeMeldingen != null) {

                final List<MetaRecord> records = gedeblokkeerdeMeldingen.stream().
                        map(metaObject -> metaObject.getGroep(Element.REGEL_IDENTITEIT).getRecords())
                        .flatMap(Collection::stream).collect(Collectors.toList());

                if (!records.isEmpty()) {
                    final BerichtElement.Builder gedeblokkeerdeMeldingenBuilder = BerichtElement.builder();
                    gedeblokkeerdeMeldingenBuilder.metNaam("gedeblokkeerdeMeldingen");

                    records.forEach(metaRecord -> {
                        final BerichtElement.Builder berichtElementBuilder = BerichtElement.builder();
                        berichtElementBuilder.metNaam("gedeblokkeerdeMelding");

                        berichtElementBuilder.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(
                                BerichtConstanten.OBJECTTYPE, "GedeblokkeerdeMelding"));
                        berichtElementBuilder.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECT_SLEUTEL,
                                String.valueOf(metaRecord.getParentGroep().getParentObject().getObjectsleutel())));
                        berichtElementBuilder.metBerichtElement(BerichtElement.builder().metNaam(
                                BerichtUtil.lowercaseFirst(Element.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEREGEL_REGELCODE.getElementWaarde().getXmlNaam())).
                                metWaarde(metaRecord.getAttribuut(Element.REGEL_CODE).getGeformatteerdeWaarde()));
                        berichtElementBuilder.metBerichtElement(BerichtElement.builder().metNaam("melding").
                                metWaarde(metaRecord.getAttribuut(Element.REGEL_MELDING).getGeformatteerdeWaarde()));
                        gedeblokkeerdeMeldingenBuilder.metBerichtElement(berichtElementBuilder);
                    });

                    berichtElement.metBerichtElement(gedeblokkeerdeMeldingenBuilder);
                }
            }
        }
    }

    private static List<BerichtElement.Builder> maakActieElementenEnVerzamelBronnen(final Berichtgegevens berichtgegevens, final List<Actiebron> admhndBronnen,
                                                                                    final List<Actie> acties) {
        final List<BerichtElement.Builder> actieElementen = new ArrayList<>();

        for (final Actie actie : acties) {
            if (!berichtgegevens.getGeautoriseerdeActies().contains(actie)) {
                continue;
            }
            final MetaObject actieMetaObject = actie.getMetaObject();
            final BerichtElement.Builder
                    actieElement =
                    BerichtElement.builder().metNaam(BerichtUtil.lowercaseFirst(actieMetaObject.getObjectElement().getXmlNaam()));
            actieElementen.add(actieElement);
            actieElement.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECT_SLEUTEL, actie.getId().toString()));
            actieElement.metBerichtElementAttribuut(
                    BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECTTYPE, actieMetaObject.getObjectElement().getXmlNaam()));

            final List<MetaAttribuut> actieAttributenLijst = new ArrayList<>();
            for (MetaRecord metaRecord : actieMetaObject.getGroep(ElementHelper.getGroepElement(Element.ACTIE_IDENTITEIT)).getRecords()) {
                actieAttributenLijst.addAll(metaRecord.getAttributen().values());
            }

            actieAttributenLijst.sort(Comparator.comparingInt(o -> o.getAttribuutElement().getVolgnummer()));
            voegBerichtElementenToe(actieAttributenLijst, actieElement, actie, berichtgegevens);
            voegGesorteerdeBronnenToe(admhndBronnen, actie, actieElement);
        }
        return actieElementen;
    }

    private static void voegBerichtElementenToe(final List<MetaAttribuut> actieAttributenLijst,
                                                final BerichtElement.Builder actieElement, final Actie actie, final Berichtgegevens berichtgegevens) {
        for (MetaAttribuut metaAttribuut : actieAttributenLijst) {
            if (metaAttribuut.getAttribuutElement().getId() == Element.ACTIE_SOORTNAAM.getId()) {
                actieElement.metBerichtElement(BerichtElement.builder()
                        .metNaam(BerichtUtil.lowercaseFirst(Element.ACTIE_SOORTNAAM.getElementWaarde().getXmlNaam()))
                        .metWaarde(actie.getSoort().getNaam()));
            } else if (metaAttribuut.getAttribuutElement().getAutorisatie() == SoortElementAutorisatie.VIA_GROEPSAUTORISATIE) {
                actieElement.metBerichtElement(BerichtAttribuutBuilder.build(metaAttribuut, berichtgegevens));
            }
        }
    }

    private static void voegGesorteerdeBronnenToe(final List<Actiebron> admhndBronnen, final Actie actie, final BerichtElement.Builder actieElement) {
        if (!actie.getBronnen().isEmpty()) {
            final List<Actiebron> gesorteerdeBronnen = Lists.newArrayList(actie.getBronnen());
            gesorteerdeBronnen.sort(ActiebronComparator.INSTANCE);
            final BerichtElement.Builder bronnen = BerichtElement.builder().metNaam(BRONNEN);
            actieElement.metBerichtElement(bronnen);

            for (final Actiebron actieBron : gesorteerdeBronnen) {
                final MetaObject actieBronMetaObject = actieBron.getMetaObject();
                final BerichtElement.Builder bron = BerichtElement.builder().metNaam(BRON);
                bronnen.metBerichtElement(bron);

                bron.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECT_SLEUTEL, actieBron.getId().toString()));
                bron.metBerichtElementAttribuut(BerichtElementAttribuut
                        .maakBuilder(BerichtConstanten.OBJECTTYPE, actieBronMetaObject.getObjectElement().getXmlNaam()));
                bron.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.REFERENTIE_ID, getCommunicatieId(actieBron)));
            }
            admhndBronnen.addAll(gesorteerdeBronnen);
        }
    }

    private static void maakBronnen(final Berichtgegevens berichtgegevens, final BerichtElement.Builder berichtElement, final List<Actiebron> admhndBronnen) {
        if (!admhndBronnen.isEmpty()) {
            final BerichtElement.Builder bronnen = BerichtElement.builder().metNaam(BRONNEN);
            berichtElement.metBerichtElement(bronnen);
            for (Actiebron actiebron : admhndBronnen) {
                final MetaObject actieBronMetaObject = actiebron.getMetaObject();
                final BerichtElement.Builder bron = BerichtElement.builder().metNaam(BRON);
                bronnen.metBerichtElement(bron);
                bron.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECTTYPE, "AdministratieveHandelingBron"));
                bron.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.COMMUNICATIE_ID, getCommunicatieId(actiebron)));
                //maak document
                maakDocument(berichtgegevens, actiebron, bron);
                //maak actiebron attributen
                maakAttribuutElementen(berichtgegevens, actieBronMetaObject, bron);
            }
        }
    }

    private static void maakAttribuutElementen(Berichtgegevens berichtgegevens, MetaObject metaObject, BerichtElement.Builder builder) {
        final List<MetaAttribuut>
                attributen =
                geefGesorteerdeGroepsAutorisatieAttributenVanGroep(metaObject, ElementHelper.getGroepElement(Element.ACTIEBRON_IDENTITEIT));
        for (MetaAttribuut metaAttribuut : attributen) {
            final BerichtElement.Builder berichtElementAttr = BerichtAttribuutBuilder.build(metaAttribuut, berichtgegevens);
            builder.metBerichtElement(berichtElementAttr);
        }
    }

    private static void maakDocument(final Berichtgegevens berichtgegevens, final Actiebron actiebron, final BerichtElement.Builder bron) {
        final Document document = actiebron.getDocument();
        if (document != null) {
            final MetaObject documentMetaObject = document.getMetaObject();
            final BerichtElement.Builder
                    documentElement =
                    BerichtElement.builder().metNaam(BerichtUtil.lowercaseFirst(documentMetaObject.getObjectElement().getXmlNaam()));
            bron.metBerichtElement(documentElement);
            documentElement.metBerichtElementAttribuut(
                    BerichtElementAttribuut.maakBuilder(BerichtConstanten.VOORKOMEN_SLEUTEL, String.valueOf(document.getiD())));
            documentElement.metBerichtElementAttribuut(
                    BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECT_SLEUTEL, String.valueOf(document.getObjectSleutel())));

            documentElement.metBerichtElementAttribuut(
                    BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECTTYPE, documentMetaObject.getObjectElement().getXmlNaam()));

            final List<MetaAttribuut>
                    documentAttributenLijst =
                    geefGesorteerdeGroepsAutorisatieAttributenVanGroep(documentMetaObject, ElementHelper.getGroepElement(Element.DOCUMENT_IDENTITEIT));
            for (MetaAttribuut metaAttribuut : documentAttributenLijst) {
                if (Element.DOCUMENT_SOORTNAAM.getId() == metaAttribuut.getAttribuutElement().getId()) {
                    documentElement.metBerichtElement(
                            BerichtElement.builder().metNaam(BerichtUtil.lowercaseFirst(metaAttribuut.getAttribuutElement().getXmlNaam()))
                                    .metWaarde(document.getSoortNaam()));
                } else {
                    final BerichtElement.Builder berichtElementAttr = BerichtAttribuutBuilder.build(metaAttribuut, berichtgegevens);
                    documentElement.metBerichtElement(berichtElementAttr);
                }
            }
        }
    }

    private static List<MetaAttribuut> geefGesorteerdeGroepsAutorisatieAttributenVanGroep(MetaObject metaObject, final GroepElement groepElement) {
        final List<MetaAttribuut> attributenLijst = new ArrayList<>();
        for (MetaRecord metaRecord : metaObject.getGroep(groepElement).getRecords()) {
            for (MetaAttribuut metaAttribuut : metaRecord.getAttributen().values()) {
                if (metaAttribuut.getAttribuutElement().getAutorisatie() != SoortElementAutorisatie.VIA_GROEPSAUTORISATIE) {
                    continue;
                }
                attributenLijst.add(metaAttribuut);
            }
        }
        attributenLijst.sort(Comparator.comparingInt(o -> o.getAttribuutElement().getVolgnummer()));
        return attributenLijst;
    }

    /**
     * Bepaalt een communicatieID voor deze bron.
     * @param actiebron actieBron
     * @return een uniek communicatieID
     */
    private static String getCommunicatieId(final Actiebron actiebron) {
        String commId = null;
        if (actiebron.getDocument() != null) {
            commId = DOCUMENT + actiebron.getDocument().getiD();
        } else if (actiebron.getRechtsgrond() != null) {
            commId = "rechtsgrond" + actiebron.getRechtsgrond();
        } else if (actiebron.getRechtsgrondomschrijving() != null) {
            commId = "rechtsgrondoms" + (actiebron.getRechtsgrondomschrijving().hashCode() + actiebron.getId().hashCode());
        }
        return commId;
    }

    /**
     * Bepaal of SoortAdministratief.SoortNaam of de Alias in bericht moet komen.
     */
    @Bedrijfsregel(Regel.R2062)
    private static String bepaalAdmHndSoortnaam(final Berichtgegevens berichtgegevens, final AdministratieveHandeling admhnd) {
        final String soortNaam;
        if (Boolean.TRUE.equals(berichtgegevens.getAutorisatiebundel().getLeveringsautorisatie().getIndicatieAliasSoortAdministratieveHandelingLeveren())) {
            soortNaam = admhnd.getSoort().getAlias() != null ? admhnd.getSoort().getAlias().getNaam() : admhnd.getSoort().getNaam();
        } else {
            soortNaam = admhnd.getSoort().getNaam();
        }
        return soortNaam;
    }
}
