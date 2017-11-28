/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtElementAttribuut;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.apache.commons.lang3.StringUtils;

/**
 * BerichtGroepBuilder. Bouwt de groep en de onderliggende records.
 */
final class BerichtGroepBuilder {

    private static final String PERSOON_START = "Persoon";

    private BerichtGroepBuilder() {
    }

    /**
     * @param metaGroep metaGroep
     * @param berichtgegevens berichtgegevens
     * @param berichtElement berichtElement
     * @param heeftStandaardMaarWordtNietGetoond heeftStandaardMaarWordtNietGetoond
     */
    public static void build(final MetaGroep metaGroep, final Berichtgegevens berichtgegevens, final BerichtElement.Builder berichtElement,
                             final boolean heeftStandaardMaarWordtNietGetoond) {
        //records
        final List<MetaRecord> tempRecordList = new ArrayList<>(metaGroep.getRecords());

        // sorteer de records
        if (metaGroep.getGroepElement().getHistoriePatroon() != HistoriePatroon.G) {
            tempRecordList.sort(BerichtRecordComparatorFactory.maakComparator(berichtgegevens));
        }
        for (MetaRecord metaRecord : tempRecordList) {
            bouwRecord(berichtgegevens, metaRecord, berichtElement, heeftStandaardMaarWordtNietGetoond);
        }
    }


    private static void bouwRecord(final Berichtgegevens berichtgegevens, final MetaRecord metaRecord, final BerichtElement.Builder parent,
                                   final boolean heeftStandaardMaarWordtNietGetoond) {
        if (!berichtgegevens.isGeautoriseerd(metaRecord)) {
            return;
        }
        final MetaGroep metaGroep = metaRecord.getParentGroep();
        final boolean wrapperNodig = !metaGroep.getGroepElement().isIdentiteitGroep() || heeftStandaardMaarWordtNietGetoond;
        final BerichtElement.Builder berichtElement;

        if (wrapperNodig) {
            final String xmlNaam = bepaalWrapperNaam(metaGroep, heeftStandaardMaarWordtNietGetoond);
            berichtElement = BerichtElement.builder().metNaam(xmlNaam);
            parent.metBerichtElement(berichtElement);

            schrijfWrapperAttributen(metaRecord, metaGroep, berichtElement, berichtgegevens);
        } else {
            berichtElement = parent;
        }
        final List<MetaAttribuut> attributenLijst = Lists.newArrayList(metaRecord.getAttributen().values());
        voegIdentiteitsAttributenToe(berichtgegevens, metaRecord, attributenLijst, heeftStandaardMaarWordtNietGetoond);

        attributenLijst.sort(Comparator.comparingInt(o -> o.getAttribuutElement().getVolgnummer()));
        for (MetaAttribuut metaAttribuut : attributenLijst) {
            if (berichtgegevens.isGeautoriseerd(metaAttribuut)) {
                final BerichtElement.Builder berichtElementAttr = BerichtAttribuutBuilder.build(metaAttribuut, berichtgegevens);
                berichtElement.metBerichtElement(berichtElementAttr);
            }
        }
    }


    private static void voegIdentiteitsAttributenToe(final Berichtgegevens berichtgegevens, final MetaRecord metaRecord,
                                                     final List<MetaAttribuut> attributenLijst, final boolean standaardAanIdentiteit) {
        if (metaRecord.getParentGroep().getParentObject().getObjectElement().getId().equals(
                Element.ONDERZOEK.getId())) {
            //Uitzondering onderzoek. Identiteit is platgeslagen op groep en standaard is los
            return;
        }
        if (standaardAanIdentiteit) {
            for (MetaGroep metaGroepParent : metaRecord.getParentGroep().getParentObject().getGroepen()) {
                if (metaGroepParent.getGroepElement().isStandaardGroep()) {
                    voegGroepAttributenToe(berichtgegevens, attributenLijst, metaGroepParent);
                    break;
                }
            }
        } else {
            //voeg identiteit attributen toe aan records van de standaard groep indien aanwezig.
            voegIdentiteitAttributenToeAanStandaardGroep(berichtgegevens, metaRecord, attributenLijst);
        }
    }

    private static void voegIdentiteitAttributenToeAanStandaardGroep(final Berichtgegevens berichtgegevens, final MetaRecord metaRecord,
                                                                     final List<MetaAttribuut> attributenLijst) {
        if (metaRecord.getParentGroep().getGroepElement().isStandaardGroep()) {
            for (MetaGroep metaGroepParent : metaRecord.getParentGroep().getParentObject().getGroepen()) {
                if (metaGroepParent.getGroepElement().isIdentiteitGroep()
                        && metaGroepParent.getGroepElement().getHistoriePatroon() == HistoriePatroon.G) {
                    voegGroepAttributenToe(berichtgegevens, attributenLijst, metaGroepParent);
                    break;
                }
            }
        }
    }

    private static void voegGroepAttributenToe(final Berichtgegevens berichtgegevens, final List<MetaAttribuut> attributenLijst,
                                               final MetaGroep metaGroepParent) {
        final MetaRecord next = metaGroepParent.getRecords().iterator().next();
        for (MetaAttribuut metaAttribuut : next.getAttributen().values()) {
            if (berichtgegevens.isGeautoriseerd(metaAttribuut)) {
                attributenLijst.add(metaAttribuut);
            }
        }
    }

    private static void schrijfWrapperAttributen(final MetaRecord metaRecord, final MetaGroep metaGroep, final BerichtElement.Builder berichtElement,
                                                 final Berichtgegevens berichtgegevens) {
        berichtElement.metBerichtElementAttribuut(
                BerichtElementAttribuut.maakBuilder(BerichtConstanten.VOORKOMEN_SLEUTEL, String.valueOf(metaRecord.getVoorkomensleutel())));
        if (berichtgegevens.isMutatiebericht() && berichtgegevens.getVerwerkingssoort(metaRecord) != null) {
            berichtElement.metBerichtElementAttribuut(
                    BerichtElementAttribuut.maakBuilder(BerichtConstanten.VERWERKINGSSOORT, berichtgegevens.getVerwerkingssoort(metaRecord).getNaam()));
        }

        /*
          als het record deel uitmaakt van een inverse object
          dan moet objectsleutel ook weggeschreven worden.
         */
        final boolean isRecordVanInverseObject = ElementHelper.getObjectAssociatiecode(metaGroep.getParentObject().getObjectElement()) != null;
        if (isRecordVanInverseObject
                && Element.PERSOON_OUDER_OUDERSCHAP != metaGroep.getGroepElement().getElement()
                && Element.GERELATEERDEOUDER_OUDERSCHAP != metaGroep.getGroepElement().getElement()
                || metaGroep.getGroepElement().isIndicatie()) {
            //zet de sleutel
            final String sleutel = StringUtils.defaultIfEmpty(berichtgegevens.getVersleuteldeObjectSleutel(metaGroep.getParentObject()),
                    String.valueOf(metaGroep.getParentObject().getObjectsleutel()));
            berichtElement.metBerichtElementAttribuut(
                    BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECT_SLEUTEL, sleutel));

            final String objecttype;
            if (metaGroep.getGroepElement().isIndicatie()) {
                //indicaties hebben verschillende objecttypen en worden ook als losse objecten getoond, het objecttype moet echter PersoonIndicatie zijn
                // voor alle indicaties
                objecttype = Element.PERSOON_INDICATIE.getElementWaarde().getXmlNaam();
            } else {
                objecttype = metaGroep.getParentObject().getObjectElement().getTypeObjectElement().getXmlNaam();
            }
            berichtElement.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECTTYPE, objecttype));
        }
    }

    private static String bepaalWrapperNaam(final MetaGroep metaGroep, final boolean standaardAanIdentiteit) {
        final String xmlNaam;
        if (metaGroep.getGroepElement().isStandaardGroep() || standaardAanIdentiteit) {
            if (metaGroep.getParentObject().getObjectElement().isVanType(ElementConstants.RELATIE)) {
                xmlNaam = "relatie";
            } else {
                final String xmlNaamVanParent = metaGroep.getParentObject().getObjectElement().getXmlNaam();
                if (xmlNaamVanParent.startsWith(PERSOON_START)) {
                    xmlNaam = BerichtUtil.lowercaseFirst(xmlNaamVanParent.replaceFirst(PERSOON_START, ""));
                } else {
                    xmlNaam = BerichtUtil.lowercaseFirst(xmlNaamVanParent);
                }
            }
        } else {
            xmlNaam = BerichtUtil.lowercaseFirst(metaGroep.getGroepElement().getXmlNaam());
        }
        return xmlNaam;
    }
}
