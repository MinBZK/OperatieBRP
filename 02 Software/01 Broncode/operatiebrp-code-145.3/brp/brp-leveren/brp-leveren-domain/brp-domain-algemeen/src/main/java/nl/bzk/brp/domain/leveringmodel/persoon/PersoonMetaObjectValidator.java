/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import org.springframework.util.Assert;

/**
 * Valideert het Persoon MetaObject model.
 */
final class PersoonMetaObjectValidator {

    private static final Multimap<Integer, Integer> PARENT_CHILD_OBJECT_MAP;

    private PersoonMetaObjectValidator() {
    }

    static {
        PARENT_CHILD_OBJECT_MAP = HashMultimap.create();

        // objecten onder persoon
        PARENT_CHILD_OBJECT_MAP.putAll(
                Element.PERSOON.getId(),
                Sets.newHashSet(
                        Element.ONDERZOEK.getId(),
                        Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING.getId(),
                        Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER.getId(),
                        Element.PERSOON_INDICATIE_STAATLOOS.getId(),
                        Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER.getId(),
                        Element.PERSOON_INDICATIE_ONDERCURATELE.getId(),
                        Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE.getId(),
                        Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG.getId(),
                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT.getId(),
                        Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG.getId(),
                        Element.PERSOON_ADRES.getId(),
                        Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId(),
                        Element.PERSOON_AFNEMERINDICATIE.getId(),
                        Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId(),
                        Element.PERSOON_NATIONALITEIT.getId(),
                        Element.PERSOON_REISDOCUMENT.getId(),
                        Element.PERSOON_VERIFICATIE.getId(),
                        Element.PERSOON_VOORNAAM.getId(),
                        Element.PERSOON_VERSTREKKINGSBEPERKING.getId(),
                        Element.ONDERZOEK.getId(),
                        Element.PERSOON_KIND.getId(),
                        Element.PERSOON_OUDER.getId(),
                        Element.PERSOON_PARTNER.getId()));

        // onderzoek
        PARENT_CHILD_OBJECT_MAP.putAll(Element.ONDERZOEK.getId(), Sets.newHashSet(Element.GEGEVENINONDERZOEK.getId()));

        // objecten onder kind
        PARENT_CHILD_OBJECT_MAP.putAll(Element.PERSOON_KIND.getId(), Sets.newHashSet(Element.FAMILIERECHTELIJKEBETREKKING.getId()));
        // objecten onder ouder
        PARENT_CHILD_OBJECT_MAP.putAll(Element.PERSOON_OUDER.getId(), Sets.newHashSet(Element.FAMILIERECHTELIJKEBETREKKING.getId()));
        // objecten onder partner
        PARENT_CHILD_OBJECT_MAP.putAll(Element.PERSOON_PARTNER.getId(), Sets.newHashSet(Element.HUWELIJK.getId(), Element.GEREGISTREERDPARTNERSCHAP.getId()));
        // objecten onder familierechtelijke betrekking
        PARENT_CHILD_OBJECT_MAP.putAll(
                Element.FAMILIERECHTELIJKEBETREKKING.getId(),
                Sets.newHashSet(Element.GERELATEERDEKIND.getId(), Element.GERELATEERDEOUDER.getId()));
        PARENT_CHILD_OBJECT_MAP.putAll(Element.GERELATEERDEKIND.getId(), Sets.newHashSet(Element.GERELATEERDEKIND_PERSOON.getId()));
        PARENT_CHILD_OBJECT_MAP.putAll(Element.GERELATEERDEOUDER.getId(), Sets.newHashSet(Element.GERELATEERDEOUDER_PERSOON.getId()));

        // huwelijk
        PARENT_CHILD_OBJECT_MAP.putAll(Element.HUWELIJK.getId(), Sets.newHashSet(Element.GERELATEERDEHUWELIJKSPARTNER.getId()));
        PARENT_CHILD_OBJECT_MAP.putAll(
                Element.GERELATEERDEHUWELIJKSPARTNER.getId(),
                Sets.newHashSet(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON.getId()));

        // geregistreerd partnerschap
        PARENT_CHILD_OBJECT_MAP.putAll(Element.GEREGISTREERDPARTNERSCHAP.getId(), Sets.newHashSet(Element.GERELATEERDEGEREGISTREERDEPARTNER.getId()));
        PARENT_CHILD_OBJECT_MAP.putAll(
                Element.GERELATEERDEGEREGISTREERDEPARTNER.getId(),
                Sets.newHashSet(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON.getId()));

        // verantwoording
        PARENT_CHILD_OBJECT_MAP.putAll(Element.ADMINISTRATIEVEHANDELING.getId(), Sets.newHashSet(Element.ACTIE.getId()));
        PARENT_CHILD_OBJECT_MAP.putAll(Element.ACTIE.getId(), Sets.newHashSet(Element.ACTIEBRON.getId()));
        PARENT_CHILD_OBJECT_MAP.putAll(Element.ACTIEBRON.getId(), Sets.newHashSet(Element.DOCUMENT.getId()));
    }

    /**
     * Valideert de MetaObject structuur.
     * @param metaObject het metaobject
     */
    static void valideer(final MetaObject metaObject) {
        if (metaObject == null || Element.PERSOON != metaObject.getObjectElement().getElement()) {
            throw new IllegalStateException("Geen Persoon!");
        }

        metaObject.accept(new ObjectValidator(metaObject));
    }

    private static final class ObjectValidator extends ParentFirstModelVisitor {

        private final MetaObject rootObject;

        private ObjectValidator(final MetaObject rootObject) {
            this.rootObject = rootObject;
        }

        @Override
        protected void doVisit(final MetaObject object) {
            if (object != rootObject) {
                assertParentObjectCorrect(object);
            }
        }

        @Override
        protected void doVisit(final MetaGroep groep) {
            assertParentObjectCorrect(groep);
        }

        @Override
        protected void doVisit(final MetaAttribuut attribuut) {
            assertAttribuutWaardeCorrect(attribuut);
        }


        private static void assertParentObjectCorrect(final MetaObject metaObject) {
            final ObjectElement objectElement = metaObject.getObjectElement();
            final ObjectElement parentObjectElement = metaObject.getParentObject().getObjectElement();
            if (!PARENT_CHILD_OBJECT_MAP.get(parentObjectElement.getId()).contains(objectElement.getId())) {
                throw new IllegalStateException(
                        String.format("Ongeldige hierarchie. Object '%s' heeft ongeldige parent '%s'", objectElement.getNaam(), parentObjectElement.getNaam()));
            }
        }

        /**
         * Valideert de MetaGroep structuur.
         * @param groep de groep
         */
        private static void assertParentObjectCorrect(final MetaGroep groep) {
            Assert.notNull(groep.getParentObject(), "Groep heeft geen parent!");
            if (!groep.getGroepElement().getObjectElement().equals(groep.getParentObject().getObjectElement())) {
                throw new IllegalStateException(
                        String.format(
                                "Groep '%s' kan niet onder Object '%s' bestaan",
                                groep.getGroepElement().getNaam(),
                                groep.getParentObject().getObjectElement()));
            }
        }

        /**
         * Valideert het MetaAttribuut.
         * @param attribuut het metaattribuut
         */
        private static void assertAttribuutWaardeCorrect(final MetaAttribuut attribuut) {
            final AttribuutElement element = attribuut.getAttribuutElement();
            if (isAanActieGerelateerdAttribuutElement(element)) {
                assertAttribuutWaardeCorrect(attribuut, Actie.class);
            } else {
                switch (element.getDatatype()) {
                    case BOOLEAN:
                        assertAttribuutWaardeCorrect(attribuut, Boolean.class);
                        break;
                    case DATUM:
                        assertAttribuutWaardeCorrect(attribuut, Integer.class);
                        break;
                    case DATUMTIJD:
                        assertAttribuutWaardeCorrect(attribuut, ZonedDateTime.class);
                        break;
                    case GETAL:
                    case GROOTGETAL:
                        assertAttribuutWaardeCorrect(attribuut, Number.class);
                        break;
                    case STRING:
                        assertStringAttribuutWaardeCorrect(attribuut);
                        break;
                    default:
                        throw new IllegalStateException();
                }
            }
        }

        private static void assertAttribuutWaardeCorrect(final MetaAttribuut metaAttribuut, final Class<?> type) {
            if (metaAttribuut.getWaarde() != null && !type.isInstance(metaAttribuut.getWaarde())) {
                throw new IllegalStateException(String.format("Ongeldige waarde voor attribuut '%s', verwacht '%s' ipv '%s'",
                        metaAttribuut.getAttribuutElement().getNaam(), type.getSimpleName(), metaAttribuut.getWaarde().getClass().getSimpleName()));
            }
        }

        //STRING basistypen kunnen corresponderen met String,Char of Number
        private static void assertStringAttribuutWaardeCorrect(final MetaAttribuut metaAttribuut) {
            final boolean isString = String.class.isInstance(metaAttribuut.getWaarde());
            final boolean isChar = Character.class.isInstance(metaAttribuut.getWaarde());
            final boolean isNumber = Number.class.isInstance(metaAttribuut.getWaarde()) && metaAttribuut.getAttribuutElement().isGetal();

            if (metaAttribuut.getWaarde() != null && !(isString || isChar || isNumber)) {
                throw new IllegalStateException(String.format("Ongeldige waarde voor attribuut '%s', verwacht '%s' ipv '%s'",
                        metaAttribuut.getAttribuutElement().getNaam(),
                        String.class.getSimpleName() + "," + Character.class.getSimpleName() + "," + Number.class.getSimpleName(),
                        metaAttribuut.getWaarde().getClass().getSimpleName()));
            }
        }


        private static boolean isAanActieGerelateerdAttribuutElement(final AttribuutElement element) {
            return element.isActieInhoud()
                    || element.isActieVerval()
                    || element.isActieAanpassingGeldigheid()
                    || element.isActieVervalTbvLevermutaties();
        }
    }
}
