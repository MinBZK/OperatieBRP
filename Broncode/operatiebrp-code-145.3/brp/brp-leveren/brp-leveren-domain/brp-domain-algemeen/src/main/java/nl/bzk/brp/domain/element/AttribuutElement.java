/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import java.util.EnumSet;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.DatabaseType;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;

/**
 * AttribuutElement.
 */
public final class AttribuutElement implements ElementObject {

    private static final EnumSet<DatabaseType> DATABASE_TYPE_GETAL = EnumSet.of(DatabaseType.INTEGER, DatabaseType.BIGINT, DatabaseType.SMALLINT);
    private static final EnumSet<DatabaseType> DATABASE_TYPE_STRING = EnumSet.of(DatabaseType.VARCHAR, DatabaseType.CHAR, DatabaseType.TEXT);

    /**
     * Elementnaam voor datum aanvang geldigheid attribuut.
     */
    public static final String DATUM_AANVANG_GELDIGHEID = "Datum aanvang geldigheid";
    /**
     * Elementnaam voor datum einde geldigheid attribuut.
     */
    public static final String DATUM_EINDE_GELDIGHEID = "Datum einde geldigheid";
    /**
     * Elementnaam voor datum tijd verval attribuut.
     */
    public static final String DATUM_TIJD_VERVAL = "Datum/tijd verval";
    /**
     * Elementnaam voor datum tijd registratie attribuut.
     */
    public static final String DATUM_TIJD_REGISTRATIE = "Datum/tijd registratie";
    /**
     * Elementnaam voor verantwoording verval attribuut.
     */
    public static final String BRP_ACTIE_VERVAL = "Actie verval";
    /**
     * Elementnaam voor verantwoording verval tbv levering mutaties attribuut.
     */
    public static final String BRP_ACTIE_VERVAL_TBV_LEVERING_MUTATIES = "Actie verval tbv levering mutaties";
    /**
     * Elementnaam voor verantwoording inhoud attribuut.
     */
    public static final String BRP_ACTIE_INHOUD = "Actie inhoud";
    /**
     * Elementnaam voor verantwoording aanpassing geldigheid attribuut.
     */
    public static final String BRP_ACTIE_AANPASSING_GELDIGHEID = "Actie aanpassing geldigheid";
    /**
     * Elementnaam voor nadere aanduiding verval attribuut.
     */
    public static final String NADERE_AANDUIDING_VERVAL = "Nadere aanduiding verval";
    /**
     * Elementnaam voor voorkomen tbv levering mutaties attribuut.
     */
    public static final String VOORKOMEN_TBV_LEVERING_MUTATIES = "Voorkomen tbv levering mutaties?";
    /**
     * Elementnaam voor dienst verval.
     */
    public static final String BRP_DIENST_VERVAL = "Dienst verval";
    /**
     * Elementnaam voor dienst inhoud.
     */
    public static final String BRP_DIENST_INHOUD = "Dienst inhoud";

    private ElementBasisType datatype;
    private AttribuutElement aliasVan;
    private GroepElement groepElement;
    private boolean isStamgegevenReferentie;
    private boolean isGetal;
    private boolean isString;
    private boolean isDatum;
    private boolean isVerantwoording;
    private ElementObject typeObject;
    private Element element;

    /**
     * Constructor.
     * @param element het element record
     */
    public AttribuutElement(final Element element) {
        this.element = element;
        if (element.getBasisType() != null) {
            datatype = ElementBasisType.valueOf(element.getBasisType().name());
        }
    }

    @Override
    public void postCreate(final ElementLocator locator) {
        if (!(getElement().getElementWaarde().getTypeAlias() == null || getElement().getElementWaarde().getTypeAlias() < 1)) {
            aliasVan = locator.getAttribuut(getElement().getElementWaarde().getTypeAlias());
        }
        groepElement = locator.getGroep(getGroepId());
        typeObject = getTypeObject(locator);

        isGetal = databaseTypeIsGetal();
        isStamgegevenReferentie = isStamgegevenReferentieAttribuut();
        isVerantwoording = isActieInhoud() || isActieVerval() || isActieAanpassingGeldigheid();
        isString = isStringAttribuut();
        isDatum = isDatumAttribuut();
    }

    @Override
    public <T extends ElementObject> T getTypeObject() {
        return (T) typeObject;
    }

    @Override
    public Element getElement() {
        return element;
    }

    /**
     * @return de alias van het attribuut, of null indien het attribuut geen alias heeft.
     */
    public AttribuutElement getAliasVan() {
        return aliasVan;
    }

    /**
     * @return het groep element van dit attribuut
     */
    public GroepElement getGroep() {
        return groepElement;
    }

    /**
     * @return het datatype van het attribuut
     */
    public ElementBasisType getDatatype() {
        return datatype;
    }

    /**
     * @return de inverse associatiecode
     */
    public String getInverseAssociatieIdentCode() {
        return getElement().getElementWaarde().getInverseassociatieIdentcode();
    }

    /**
     * @return het id van het objectelement
     */
    public Integer getObjectType() {
        return getElement().getElementWaarde().getObjecttype();
    }

    /**
     * @return indicatie of het een actie verantwoording attribuut betreft
     */
    public boolean isVerantwoording() {
        return isVerantwoording;
    }

    /**
     * @return indicatie dat het record door migratie is toegevoegd om historie goed te kunnen leveren
     */
    public boolean isVoorkomenTbvLeveringMutaties() {
        return VOORKOMEN_TBV_LEVERING_MUTATIES.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de nadere aanduiding verval beschrijft
     */
    public boolean isNadereAanduidingVerval() {
        return NADERE_AANDUIDING_VERVAL.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de datum aanvang geldigheid beschrijft
     */
    public boolean isDatumAanvangGeldigheid() {
        return DATUM_AANVANG_GELDIGHEID.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attrbuut de datum einde geldigheid beschrijft
     */
    public boolean isDatumEindeGeldigheid() {
        return DATUM_EINDE_GELDIGHEID.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de datum tijd verval beschrijft
     */
    public boolean isDatumTijdVerval() {
        return DATUM_TIJD_VERVAL.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de datum tijd registratie beschrijft
     */
    public boolean isDatumTijdRegistratie() {
        return DATUM_TIJD_REGISTRATIE.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de actie verval beschrijft
     */
    public boolean isActieVerval() {
        return BRP_ACTIE_VERVAL.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de actie verval tbv levermutaties beschrijft
     */
    public boolean isActieVervalTbvLevermutaties() {
        return BRP_ACTIE_VERVAL_TBV_LEVERING_MUTATIES.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de actie inhoud beschrijft
     */
    public boolean isActieInhoud() {
        return BRP_ACTIE_INHOUD.equals(getElementNaam());
    }

    /**
     * @return indicatie of dit attribuut de actie aanpassing geldigheid beschrijft
     */
    public boolean isActieAanpassingGeldigheid() {
        return BRP_ACTIE_AANPASSING_GELDIGHEID.equals(getElementNaam());
    }

    /**
     * @return de minimum lengte aanduiding van het de waarde van dit attribuut in het bericht
     */
    public Integer getMininumLengte() {
        return getElement().getElementWaarde().getMinimumlengte();
    }

    /**
     * @return de maxium lengte aanduiding van het de waarde van dit attribuut in het bericht
     */
    public Integer getMaximumLengte() {
        return getElement().getElementWaarde().getMaximumlengte();
    }

    /**
     * @return het id van de groep waar dit attribuut toe behoort
     */
    public int getGroepId() {
        return getElement().getGroep().getId();
    }

    /**
     * is stamgegeven referentie.
     * @return is stamgegeven referentie
     */
    public boolean isStamgegevenReferentie() {
        return isStamgegevenReferentie;
    }

    /**
     * @return is getal
     */
    public boolean isGetal() {
        return isGetal;
    }

    /**
     * Geeft aan of attribuutelement van type string is (d.w.z. het {@link DatabaseType} is varchar, char of text)
     * @return true indien attribuutelement een string is.
     */
    public boolean isString() {
        return isString;
    }

    /**
     * Geeft aan of een attribuutelement van type datum is (d.w.z. het {@link ElementBasisType is datum})
     * @return true indien attribuutelement een datum is
     */
    public boolean isDatum() {
        return isDatum;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(element.getId());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {

            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AttribuutElement elem = (AttribuutElement) o;
        return this.element.getId() == elem.getElement().getId();

    }

    @Override
    public String toString() {
        return String.format("%s [%d]", element.getNaam(), element.getId());
    }

    private boolean databaseTypeIsGetal() {
        final DatabaseType typeIdentDb = getElement().getElementWaarde().getTypeidentdb();
        return DATABASE_TYPE_GETAL.contains(typeIdentDb);
    }

    private boolean isStamgegevenReferentieAttribuut() {
        return getElement().getElementWaarde().getType() != null
                && typeObject != null
                && !(SoortInhoud.D.getCode().equals(typeObject.getSoortInhoud()));
    }

    private boolean isStringAttribuut() {
        return !isStamgegevenReferentie && DATABASE_TYPE_STRING.contains(getElement().getElementWaarde().getTypeidentdb());
    }

    private boolean isDatumAttribuut() {
        return !isStamgegevenReferentie && getElement().getElementWaarde().getBasisType() == ElementBasisType.DATUM;
    }
}
