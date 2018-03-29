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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.berichtmodel.BerichtElement;
import nl.bzk.brp.domain.berichtmodel.BerichtElementAttribuut;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.apache.commons.lang3.StringUtils;

/**
 * BerichtObjectBuilder.
 */
public final class BerichtBuilder {

    private static final ObjectElement ONDERZOEK = ElementHelper.getObjectElement(Element.ONDERZOEK);
    private static final ObjectElement GEGEVENINONDERZOEK = ElementHelper.getObjectElement(Element.GEGEVENINONDERZOEK);
    private static final ObjectElement PERSOON_VERSTREKKINGSBEPERKING = ElementHelper.getObjectElement(Element.PERSOON_VERSTREKKINGSBEPERKING);
    private static final ObjectElement HUWELIJKGEREGISTREERD_PARTNERSCHAP = ElementHelper.getObjectElement(Element.HUWELIJKGEREGISTREERDPARTNERSCHAP);


    //onderzoeken hebben geen inverser asso code maar wel zo behandeld. Inverse asso code betekent groepering en wrapper met code
    private static final String ONDERZOEK_INVERSE_ASSOC_CODE = "onderzoeken";
    //normaal hebben alleen standaard groepen een wrapper. De volgende objecten hebben een (extra) wrapper.
    //onderzoek heeft standaardgroep maar extra wrapper. Gegeven in onderzoek heeft alleen identiteitsgroep
    // maar extra wrapper. Meerdere gegevens.
    private static final ObjectElement[] OBJECTEN_WELKE_WRAPPER_BEHOEVEN = new ObjectElement[]{
            ElementConstants.PERSOON,
            ElementConstants.BETROKKENHEID,
            ElementConstants.RELATIE,
            ONDERZOEK,
            GEGEVENINONDERZOEK,
            PERSOON_VERSTREKKINGSBEPERKING,
    };


    private final Berichtgegevens berichtgegevens;
    private Integer communicatieId;

    /**
     * Constructor voor persoon metaobject. Root.
     * @param berichtgegevens de berichtgegevens
     * @param communicatieId communicatieId
     */
    public BerichtBuilder(final Berichtgegevens berichtgegevens, final Integer communicatieId) {
        this.berichtgegevens = berichtgegevens;
        this.communicatieId = communicatieId;
    }

    /**
     * @param metaObject metaObject
     * @return berichtElement
     */
    public BerichtElement build(final MetaObject metaObject) {
        final BerichtElement.Builder berichtElement = wrap(metaObject, null);
        return build(metaObject, berichtElement).build();
    }

    /**
     * @param metaObject metaObject
     * @param parentElement parentElement
     * @return builder
     */
    private BerichtElement.Builder build(final MetaObject metaObject, final BerichtElement.Builder parentElement) {
        final BerichtElement.Builder parent = wrap(metaObject, parentElement);
        //sorteer en groepeer de onderliggende metamodellen
        final Map<String, BerichtObjectGroep> metaModelObjectMap = new HashMap<>();
        final Map<String, BerichtGroep> metaModelGroepMap = new HashMap<>();
        for (MetaGroep metaGroep : metaObject.getGroepen()) {
            if (toonGroep(metaGroep) || heeftStandaardMaarWordtNietGetoond(metaGroep)) {
                metaModelGroepMap.put(metaGroep.getGroepElement().getNaam(), new BerichtGroep(metaGroep, parent));
            }
        }
        for (MetaObject metaOjectKind : metaObject.getObjecten()) {
            if (inBericht(metaOjectKind)) {
                //als objecten associatiecode hebben worden ze gegroepeerd. Ze hebben meerdere volgnummers
                //we gebruiken er 1, ze zouden opvolgend moeten zijn. Objectcomparator zorgt voor verdere sortering
                final String objectAssociatiecode = ElementHelper.getObjectAssociatiecode(metaOjectKind.getObjectElement().getTypeObjectElement());
                final String groeperingNaam;
                if (objectAssociatiecode != null) {
                    groeperingNaam = objectAssociatiecode;
                } else {
                    groeperingNaam = metaOjectKind.getObjectElement().getNaam();
                }
                BerichtObjectGroep berichtMetaModelBuilder = metaModelObjectMap.get(groeperingNaam);
                if (berichtMetaModelBuilder == null) {
                    berichtMetaModelBuilder = new BerichtObjectGroep(metaOjectKind, objectAssociatiecode, parent);
                    metaModelObjectMap.put(groeperingNaam, berichtMetaModelBuilder);
                } else {
                    berichtMetaModelBuilder.voegToe(metaOjectKind);
                }
            }
        }
        final ArrayList<BerichtMetaModelBuilder> models = new ArrayList<>(metaModelObjectMap.values());
        models.addAll(metaModelGroepMap.values());
        models.sort(Comparator.comparing(BerichtMetaModelBuilder::getVolgnummer));
        models.forEach(BerichtBuilder.BerichtMetaModelBuilder::bouw);
        //voor persoon maak ook de onderzoeken en handelingen. Onderaan.
        if (metaObject.getObjectElement().getElement() == ElementConstants.PERSOON.getElement()) {
            //onderzoeken
            bouwOnderzoeken(parent);
            bouwHandelingen(parent);
        }
        return parent;
    }

    private BerichtElement.Builder wrap(final MetaObject metaObject, final BerichtElement.Builder parentElement) {
        BerichtElement.Builder parent;
        final boolean wrapperNodig = bepaalSchrijfWrapperVoorObject(metaObject.getObjectElement());
        if (wrapperNodig) {
            final BerichtElement.Builder wrapper = maakWrapperVoorObject(metaObject);
            if (parentElement != null) {
                parentElement.metBerichtElement(wrapper);
            }
            parent = wrapper;
        } else {
            parent = parentElement;
        }
        return parent;
    }

    private boolean inBericht(final MetaObject huidig) {
        //onderzoeken bouwen we apart
        if (ONDERZOEK.equals(huidig.getObjectElement().getTypeObjectElement())) {
            return false;
        }
        final boolean result;
        if (berichtgegevens.isVolledigBericht()) {
            result = berichtgegevens.isGeautoriseerd(huidig);
        } else {
            //mutatieberichten bevatten geen geautoriseerde objecten zonder inhoud
            final Collection<MetaModel> onderliggendeAutorisaties = berichtgegevens.getOnderliggendeAutorisaties(huidig);
            result = berichtgegevens.isGeautoriseerd(huidig) && !onderliggendeAutorisaties.isEmpty();
        }

        return result;
    }

    private boolean toonGroep(final MetaGroep metaGroep) {
        //identiteitgroepen niet tonen als het object ook een standaardgroep heeft. In berichtrecord worden
        // rijen van identiteitsgroep aan standaardgroep samengevoegd als metaobject een standaard
        // groep heeft.
        boolean toonGroep = true;
        //onderzoek standaard behandelen we niet als standaard. Identiteit wordt platgeslagen op object.
        if (metaGroep.getGroepElement().isIdentiteitGroep() && !metaGroep.getParentObject().getObjectElement().getId().equals(Element.ONDERZOEK.getId())) {
            boolean heeftStdGroep = false;
            for (GroepElement groepElement : metaGroep.getParentObject().getObjectElement().getGroepen()) {
                if (groepElement.isStandaardGroep()) {
                    heeftStdGroep = true;
                    break;
                }
            }
            if (heeftStdGroep) {
                toonGroep = false;
            }
        }
        return toonGroep;
    }

    private boolean heeftStandaardMaarWordtNietGetoond(final MetaGroep groep) {
        if (groep.getGroepElement().isIdentiteitGroep()) {
            final Set<MetaGroep> metaGroepen = groep.getParentObject().getGroepen();
            for (MetaGroep metaGroep : metaGroepen) {
                if (metaGroep.getGroepElement().isStandaardGroep() && !inBericht(metaGroep, berichtgegevens)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Bedrijfsregel(Regel.R1973)
    private static boolean inBericht(final MetaGroep metaGroep, final Berichtgegevens berichtgegevens) {
        final boolean isGeautoriseerd = berichtgegevens.isGeautoriseerd(metaGroep);
        final boolean inBericht;
        if (berichtgegevens.isVolledigBericht()) {
            inBericht = isGeautoriseerd;
        } else {
            inBericht = isGeautoriseerd && !berichtgegevens.getOnderliggendeAutorisaties(metaGroep).isEmpty();
        }
        return inBericht;
    }

    private void bouwOnderzoeken(final BerichtElement.Builder berichtElementParent) {
        //geautoriseerde en ongefilterde onderzoeken (alleen voor persoon root object)
        final Set<MetaObject> geautoriseerdeOnderzoekObjecten = berichtgegevens.getGeautoriseerdeObjecten(ONDERZOEK);
        final List<MetaObject> geautoriseerdeOnderzoekObjectenSorted = new ArrayList<>(geautoriseerdeOnderzoekObjecten);
        geautoriseerdeOnderzoekObjectenSorted.sort(new BerichtObjectComparator(berichtgegevens));
        if (!geautoriseerdeOnderzoekObjecten.isEmpty()) {
            final BerichtElement.Builder onderzoeken = BerichtElement.builder();
            onderzoeken.metNaam(ONDERZOEK_INVERSE_ASSOC_CODE);
            berichtElementParent.metBerichtElement(onderzoeken);
            for (MetaObject onderzoek : geautoriseerdeOnderzoekObjectenSorted) {
                build(onderzoek, onderzoeken);
            }
        }
    }

    private void bouwHandelingen(final BerichtElement.Builder berichtElementParent) {
        if (berichtgegevens.getGeautoriseerdeHandelingen() != null && !berichtgegevens.getGeautoriseerdeHandelingen().isEmpty()) {
            final BerichtElement.Builder handelingenElement = BerichtElement.builder().metNaam("administratieveHandelingen");
            berichtElementParent.metBerichtElement(handelingenElement);

            final List<AdministratieveHandeling> handelingen = Lists.newArrayList(berichtgegevens.getGeautoriseerdeHandelingen());
            handelingen.sort(AdministratievehandelingComparator.INSTANCE);

            for (final AdministratieveHandeling admhnd : handelingen) {
                final BerichtElement.Builder admhndElement = BerichtAdministratieveHandelingBuilder.build(berichtgegevens, admhnd);
                handelingenElement.metBerichtElement(admhndElement);
            }
        }
    }

    private static boolean bepaalSchrijfWrapperVoorObject(final ObjectElement objectElement) {
        boolean wrapperNodig = false;
        if (objectElement.isAliasVan(ElementConstants.PERSOON)) {
            wrapperNodig = true;
        }
        if (!wrapperNodig) {
            for (final ObjectElement element : OBJECTEN_WELKE_WRAPPER_BEHOEVEN) {
                if (objectElement.isVanType(element)) {
                    wrapperNodig = true;
                    break;

                }
            }
        }
        return wrapperNodig;
    }

    private BerichtElement.Builder maakWrapperVoorObject(final MetaObject metaObject) {
        final ObjectElement objectElement = metaObject.getObjectElement();
        final String objectElementNaam;
        final String objecttype;
        if (objectElement.isVanType(HUWELIJKGEREGISTREERD_PARTNERSCHAP)) {
            objectElementNaam = objectElement.getXmlNaam();
            //pak XML naam van relatie, het supertype
            //nog te doen ROOD-2021
            objecttype = ElementConstants.RELATIE.getXmlNaam();
        } else if (objectElement.isVanType(PERSOON_VERSTREKKINGSBEPERKING)) {
            //nog te doen ROOD-2021
            objectElementNaam = "verstrekkingsbeperking";
            objecttype = PERSOON_VERSTREKKINGSBEPERKING.getXmlNaam();
        } else {
            objectElementNaam = objectElement.getXmlNaam();
            objecttype = objectElement.getTypeObjectElement().getXmlNaam();
        }
        final BerichtElement.Builder berichtElement = BerichtElement.builder();
        berichtElement.metNaam(BerichtUtil.lowercaseFirst(objectElementNaam));
        //zet de sleutel
        final String sleutel = StringUtils.defaultIfEmpty(berichtgegevens.getVersleuteldeObjectSleutel(metaObject),
                String.valueOf(metaObject.getObjectsleutel()));
        berichtElement
                .metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECT_SLEUTEL, sleutel));
        berichtElement.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.OBJECTTYPE, objecttype));
        if (berichtgegevens.isMutatiebericht()) {
            final Verwerkingssoort verwerkingssoort = berichtgegevens.getObjectVerwerkingssoort(metaObject);
            if (verwerkingssoort != null) {
                berichtElement
                        .metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.VERWERKINGSSOORT, verwerkingssoort.getNaam()));
            }
        }

        if (metaObject.getObjectElement().getElement() == Element.PERSOON && communicatieId != null) {
            berichtElement.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.COMMUNICATIE_ID, communicatieId.toString()));
        }

        final String voorkomenSleutelBetrokkenheid = bepaalVoorkomenSleutelVoorBetrokkenheid(metaObject);
        if (voorkomenSleutelBetrokkenheid != null) {
            berichtElement.metBerichtElementAttribuut(BerichtElementAttribuut.maakBuilder(BerichtConstanten.VOORKOMEN_SLEUTEL, voorkomenSleutelBetrokkenheid));
        }
        return berichtElement;
    }

    private String bepaalVoorkomenSleutelVoorBetrokkenheid(final MetaObject metaObject) {
        //Als betrokkeheid zoek dan de voorkomensleutel van de betrokkenheid.
        if (metaObject.getObjectElement().isVanType(ElementConstants.BETROKKENHEID)) {
            for (final MetaGroep metaGroep : metaObject.getGroepen()) {
                final String voorkomensleutel = berichtgegevens.getPersoonslijst().getActueleRecord(metaGroep)
                        .filter(metaRecord -> metaRecord.getParentGroep().getGroepElement().isIdentiteitGroep())
                        .map(MetaRecord::getVoorkomensleutel)
                        .map(String::valueOf)
                        .orElse(null);
                if (voorkomensleutel != null) {
                    return voorkomensleutel;
                }
            }
        }
        return null;
    }

    /**
     * BerichtMetaModelBuilder
     */
    private interface BerichtMetaModelBuilder {
        void bouw();

        Integer getVolgnummer();
    }

    /**
     * BerichtObjectGroep.
     */
    private final class BerichtObjectGroep implements BerichtMetaModelBuilder {

        private final BerichtElement.Builder parent;
        private String associatieCode;
        private List<MetaObject> objecten = new ArrayList<>();
        private Integer volgnummer;

        private BerichtObjectGroep(final MetaObject metaObject, final String associatieCode, final BerichtElement.Builder parent) {
            this.associatieCode = associatieCode;
            this.parent = parent;
            this.objecten.add(metaObject);
            this.volgnummer = berichtgegevens.getStatischePersoongegevens().getVolgnummerMap().get(metaObject);
        }

        @Override
        public void bouw() {
            BerichtElement.Builder nieuwParent = parent;
            //een objecten groep heeft soms een wrapper (adressen etc)
            if (associatieCode != null) {
                final BerichtElement.Builder assoc = BerichtElement.builder().metNaam(BerichtUtil.lowercaseFirst(associatieCode));
                parent.metBerichtElement(assoc);
                nieuwParent = assoc;
            }
            objecten.sort(new BerichtObjectComparator(berichtgegevens));
            for (MetaObject objectChild : objecten) {
                build(objectChild, nieuwParent);
            }
        }

        /**
         * Voeg {@link MetaObject} toe.
         * @param metaModel metaObject
         */
        public void voegToe(final MetaObject metaModel) {
            objecten.add(metaModel);
        }

        @Override
        public Integer getVolgnummer() {
            return volgnummer;
        }
    }

    /**
     * BerichtGroep.
     */
    private final class BerichtGroep implements BerichtMetaModelBuilder {
        private MetaGroep groep;
        private BerichtElement.Builder parent;
        private Integer volgnummer;

        private BerichtGroep(final MetaGroep groep, final BerichtElement.Builder parent) {
            this.groep = groep;
            this.parent = parent;
            volgnummer = groep.getGroepElement().getVolgnummer();
        }

        @Override
        public void bouw() {
            final boolean toonGroep = toonGroep(groep);
            final boolean standaardWordtNietGetoond = heeftStandaardMaarWordtNietGetoond(groep);
            if ((toonGroep || standaardWordtNietGetoond) && (inBericht(groep, berichtgegevens) && !groep.getRecords().isEmpty())) {
                BerichtGroepBuilder.build(groep, berichtgegevens, parent, standaardWordtNietGetoond);
            }
        }

        @Override
        public Integer getVolgnummer() {
            return volgnummer;
        }
    }
}
