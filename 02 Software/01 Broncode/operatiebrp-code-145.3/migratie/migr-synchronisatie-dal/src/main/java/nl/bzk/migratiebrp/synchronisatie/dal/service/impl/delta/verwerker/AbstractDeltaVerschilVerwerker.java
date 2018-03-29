/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer.AbstractTransformatie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.SleutelUtil;

/**
 * Abstract implementatie voor de {@link DeltaVerschilVerwerker} implementaties van deltabepaling. Deze class bevat de
 * logica om d.m.v. reflectie de gevonden verschillen in een bestaand entiteit (bv {@link Persoon}) te verwerken.
 */
abstract class AbstractDeltaVerschilVerwerker implements DeltaVerschilVerwerker {
    private final DeltaRootEntiteitModus modus;

    /**
     * Maakt een verschil verwerker en zet de modus waar deze verwerker in werkt.
     * @param modus een {@link DeltaRootEntiteitModus} waarmee aangegeven wordt voor welke {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit} deze
     * verwerker werkt.
     */
    AbstractDeltaVerschilVerwerker(final DeltaRootEntiteitModus modus) {
        this.modus = modus;
    }

    /**
     * Verwerkt de wijzigingen op een bestaand deltaEntiteit.
     * @param verschillen de verschillen die verwerkt moeten worden
     * @param deltaEntiteit de deltaEntiteit waar de verschillen (mogelijk) in verwerkt worden
     * @param eigenaar de eigenaar van de deltaEntiteit, mag null zijn
     * @param eigenaarSleutel sleutel van de eigenaar, mag null zijn.
     * @param administratieveHandeling de administratieve handeling die gekoppel moet worden aan de wijziging
     * @throws ReflectiveOperationException als er iets fout gaat tijden de reflectie operaties.
     */
    final void verwerkWijzigingen(
            final VergelijkerResultaat verschillen,
            final Entiteit deltaEntiteit,
            final Entiteit eigenaar,
            final EntiteitSleutel eigenaarSleutel,
            final AdministratieveHandeling administratieveHandeling) throws ReflectiveOperationException {
        final Entiteit deltaEntiteitPojo = Entiteit.convertToPojo(deltaEntiteit);

        for (final Field veld : DeltaUtil.getDeclaredEntityFields(deltaEntiteitPojo.getClass())) {
            veld.setAccessible(true);
            if (DeltaUtil.isSkipableVeld(veld, deltaEntiteitPojo, eigenaar, getSkippableEntiteiten())) {
                // noinspection UnnecessaryContinue
                continue;
            } else if (DeltaUtil.isWaardeVeld(veld) || DeltaUtil.isDynamischeStamtabelVeld(veld)) {
                // simpel waarde veld
                verwerkWaardeVeld(veld, verschillen, deltaEntiteitPojo, eigenaarSleutel);
            } else if (DeltaUtil.isEntiteitReferentieVeld(eigenaar, veld)) {
                // referentie naar een andere deltaEntiteit
                verwerkWijzigingenEntiteitVeld(veld, verschillen, deltaEntiteitPojo, eigenaarSleutel, administratieveHandeling);
            } else if (DeltaUtil.isEntiteitVerzamelingVeld(veld)) {
                // verwerk collections
                // noinspection unchecked
                final Collection<Entiteit> dbObjectVeldWaarde = (Collection<Entiteit>) veld.get(deltaEntiteitPojo);
                verwerkCollection(verschillen, veld, dbObjectVeldWaarde, deltaEntiteitPojo, eigenaarSleutel, administratieveHandeling);
            }
        }
    }

    private void verwerkWaardeVeld(
            final Field veld,
            final VergelijkerResultaat verschillen,
            final Entiteit deltaEntiteit,
            final EntiteitSleutel eigenaarSleutel) throws ReflectiveOperationException {
        final EntiteitSleutel sleutel = SleutelUtil.maakSleutel(deltaEntiteit, eigenaarSleutel, veld);

        if (BRPActie.class.isAssignableFrom(deltaEntiteit.getClass())) {
            final Verschil verschil = verschillen.zoekVerschil(sleutel);
            if (verschil != null) {
                final EntiteitSleutel verschilSleutel = (EntiteitSleutel) verschil.getSleutel();
                if (verschilSleutel.getId().equals(((BRPActie) deltaEntiteit).getId())) {
                    veld.set(deltaEntiteit, verschil.getNieuweWaarde());
                }
            }
        } else {
            if (verschillen.bevatSleutel(sleutel)) {
                final Verschil verschil = verschillen.zoekVerschil(sleutel);
                final Object nieuweWaarde = verschil.getNieuweWaarde();
                veld.set(deltaEntiteit, nieuweWaarde);
            }
        }
    }

    private void verwerkWijzigingenEntiteitVeld(
            final Field veld,
            final VergelijkerResultaat vergelijkerResultaat,
            final Entiteit deltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final AdministratieveHandeling administratieveHandeling) throws ReflectiveOperationException {
        final boolean entiteitHeeftWijzigingen = heeftEntiteitWijzigingen(vergelijkerResultaat, deltaEntiteit);

        if (DeltaUtil.isVeldAdministratieveHandelingVeld(veld)) {
            verwerkAdministratieveHandelingVeld(veld, deltaEntiteit, administratieveHandeling, entiteitHeeftWijzigingen);
        } else {
            final EntiteitSleutel sleutel = SleutelUtil.maakSleutel(deltaEntiteit, eigenaarSleutel, veld);
            if (vergelijkerResultaat.bevatSleutel(sleutel)) {
                final Verschil verschil = vergelijkerResultaat.zoekVerschil(sleutel);
                veld.set(deltaEntiteit, verschil.getNieuweWaarde());
            } else {
                final Entiteit deltaEntiteitWaarde = (Entiteit) veld.get(deltaEntiteit);
                if (deltaEntiteitWaarde != null) {
                    verwerkWijzigingen(vergelijkerResultaat, deltaEntiteitWaarde, deltaEntiteit, sleutel, administratieveHandeling);
                }
            }
        }
    }

    private void verwerkAdministratieveHandelingVeld(
            final Field veld,
            final Object entiteit,
            final AdministratieveHandeling administratieveHandeling,
            final boolean entiteitHeeftWijzigingen) throws IllegalAccessException {
        if (!(entiteit instanceof Persoon)) {
            final AdministratieveHandeling oudeAdminHandeling = Entiteit.convertToPojo((AdministratieveHandeling) veld.get(entiteit));
            if (entiteitHeeftWijzigingen && !oudeAdminHandeling.equals(administratieveHandeling)) {
                veld.set(entiteit, administratieveHandeling);
            }
        }
    }

    private void verwerkCollection(
            final VergelijkerResultaat vergelijkerResultaat,
            final Field veld,
            final Collection<Entiteit> deltaEntiteitCollectie,
            final Entiteit deltaEntiteit,
            final EntiteitSleutel eigenaarSleutel,
            final AdministratieveHandeling administratieveHandeling) throws ReflectiveOperationException {
        // Verwerken van verschillen in dezelfde rij.
        for (final Entiteit rij : deltaEntiteitCollectie) {
            if (!DeltaUtil.isMRij(rij)) {
                final EntiteitSleutel sleutel = SleutelUtil.maakRijSleutel(deltaEntiteit, eigenaarSleutel, rij, veld.getName());
                if (rij instanceof PersoonReisdocument && !((PersoonReisdocument) rij).bevatAlleenMrijen()) {
                    final PersoonReisdocument reisdocument = (PersoonReisdocument) rij;
                    final PersoonReisdocumentHistorie reisdocumentHistorie =
                            FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(reisdocument.getPersoonReisdocumentHistorieSet());
                    sleutel.vulSleutelAanVoorReisdocument((Long) rij.getId(), reisdocument, reisdocumentHistorie);
                }
                verwerkWijzigingen(vergelijkerResultaat, rij, deltaEntiteit, sleutel, administratieveHandeling);
            }
        }

        // Controleren of in de lijst rijen zijn toegevoegd/verwijderd
        final EntiteitSleutel rijSleutel = SleutelUtil.maakSleutel(deltaEntiteit, eigenaarSleutel, veld);
        for (final Verschil verschil : vergelijkerResultaat.getVerschillen()) {
            final EntiteitSleutel sleutel = (EntiteitSleutel) verschil.getSleutel();
            if (rijSleutel.equalsIgnoreOntbrekendeDelen(sleutel)) {
                if (VerschilType.RIJ_TOEGEVOEGD.equals(verschil.getVerschilType())) {
                    final Entiteit rij = (Entiteit) verschil.getNieuweWaarde();
                    final Entiteit nieuweRijPojo = Entiteit.convertToPojo(rij);
                    verwerkNieuweRijWijzigingen(vergelijkerResultaat, nieuweRijPojo, deltaEntiteit, sleutel);
                    kopieerOnderzoekNaarNieuweRij(vergelijkerResultaat, nieuweRijPojo, sleutel);
                    voegRijToeAanLijst(deltaEntiteit, rij, administratieveHandeling);
                } else {
                    final Entiteit oudeWaarde = (Entiteit) verschil.getOudeWaarde();
                    deltaEntiteitCollectie.remove(oudeWaarde);
                }
            }
        }
    }

    private void verwerkNieuweRijWijzigingen(
            final VergelijkerResultaat verschillen,
            final Entiteit nieuweRijEntiteit,
            final Entiteit eigenaar,
            final EntiteitSleutel rijToegevoegdSleutel) throws ReflectiveOperationException {
        final EntiteitSleutel rijInhoudSleutel = AbstractTransformatie.maakRijInhoudSleutel(rijToegevoegdSleutel, nieuweRijEntiteit);
        for (final Field veld : DeltaUtil.getDeclaredEntityFields(nieuweRijEntiteit.getClass())) {
            veld.setAccessible(true);
            if (!DeltaUtil.isSkipableVeld(veld, nieuweRijEntiteit, eigenaar, getSkippableEntiteiten())
                    && (DeltaUtil.isWaardeVeld(veld) || DeltaUtil.isDynamischeStamtabelVeld(veld) || DeltaUtil.isEntiteitReferentieVeld(eigenaar, veld))) {
                final EntiteitSleutel sleutel = new EntiteitSleutel(rijInhoudSleutel, veld.getName());
                final Verschil verschil = verschillen.zoekVerschil(sleutel, VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST);
                if (verschil != null) {
                    veld.set(nieuweRijEntiteit, verschil.getNieuweWaarde());
                }
            }
        }
    }

    private void kopieerOnderzoekNaarNieuweRij(
            final VergelijkerResultaat verschillen,
            final Entiteit nieuweRijEntiteit,
            final EntiteitSleutel rijToegevoegdSleutel) {
        final EntiteitSleutel rijInhoudSleutel = AbstractTransformatie.maakRijInhoudSleutel(rijToegevoegdSleutel, nieuweRijEntiteit);
        final EntiteitSleutel gegevenInOnderzoekSleutel = new EntiteitSleutel(rijInhoudSleutel, AbstractEntiteit.GEGEVEN_IN_ONDERZOEK);
        final Verschil verschil = verschillen.zoekVerschil(gegevenInOnderzoekSleutel, VerschilType.KOPIEER_ONDERZOEK_NAAR_NIEUWE_RIJ);
        if (verschil != null) {
            final Map<Element, GegevenInOnderzoek> gegevensInOnderzoek = verschil.getBestaandeHistorieRij().getGegevenInOnderzoekPerElementMap();
            for (final GegevenInOnderzoek gegevenInOnderzoek : gegevensInOnderzoek.values()) {
                final Onderzoek onderzoek = gegevenInOnderzoek.getOnderzoek();
                final GegevenInOnderzoek kopieGegevenInOnderzoek = new GegevenInOnderzoek(onderzoek, gegevenInOnderzoek.getSoortGegeven());
                final GegevenInOnderzoekHistorie gegevenInOnderzoekHistorie =
                        new GegevenInOnderzoekHistorie(kopieGegevenInOnderzoek, onderzoek.getOnderzoekHistorieSet().iterator().next().getActieInhoud());
                kopieGegevenInOnderzoek.addGegevenInOnderzoekHistorie(gegevenInOnderzoekHistorie);
                kopieGegevenInOnderzoek.setEntiteitOfVoorkomen(nieuweRijEntiteit);
                onderzoek.addGegevenInOnderzoek(kopieGegevenInOnderzoek);
            }
        }
    }

    /**
     * Zet de nieuwe administratieve handeling op alle acties van collectie.
     * @param entiteitCollectie De collectie
     * @param administratieveHandeling De nieuwe administratieve handeling
     */
    private void updateActiesVanCollectie(final Collection<?> entiteitCollectie, final AdministratieveHandeling administratieveHandeling)
            throws IllegalAccessException {
        for (final Object rij : entiteitCollectie) {
            updateActieVanRij(rij, administratieveHandeling);
        }
    }

    private void updateActieVanRij(final Object rij, final AdministratieveHandeling administratieveHandeling) throws IllegalAccessException {
        final Object entiteitPojo = Entiteit.convertToPojo(rij);
        final Class<?> entiteitClass = entiteitPojo.getClass();

        if (PersoonAfgeleidAdministratiefHistorie.class.isAssignableFrom(entiteitClass)) {
            ((PersoonAfgeleidAdministratiefHistorie) entiteitPojo).setAdministratieveHandeling(administratieveHandeling);
        }

        for (final Field veld : DeltaUtil.getDeclaredEntityFields(entiteitClass)) {
            veld.setAccessible(true);
            if (!FormeleHistorie.class.isAssignableFrom(entiteitPojo.getClass()) && Set.class.isAssignableFrom(veld.getType())) {
                // noinspection unchecked
                final Set<?> objectSet = (Set<?>) veld.get(rij);
                if (setBevatHistorie(objectSet)) {
                    updateActiesVanCollectie(objectSet, administratieveHandeling);
                }
            }
        }
    }

    private boolean setBevatHistorie(final Set<?> set) {
        if (set.isEmpty()) {
            return false;
        }

        final Object element = set.iterator().next();
        return FormeleHistorie.class.isAssignableFrom(element.getClass());
    }

    private void voegRijToeAanLijst(final Object eigenaar, final Object rij, final AdministratieveHandeling administratieveHandeling)
            throws ReflectiveOperationException {
        if (rij instanceof ActieBron && eigenaar instanceof BRPActie) {
            voegActieBronToeAanBRPActie((BRPActie) eigenaar, (ActieBron) rij, administratieveHandeling);
        } else {
            updateActieVanRij(rij, administratieveHandeling);
            voegNormaleRijToeAanLijst(eigenaar, rij);
        }
    }

    // Deep copy
    private void voegActieBronToeAanBRPActie(final BRPActie eigenaar, final ActieBron rij, final AdministratieveHandeling administratieveHandeling) {
        final ActieBron kopieActieBron = new ActieBron(eigenaar);

        final Document document = rij.getDocument();
        if (document != null) {
            final Document kopieDocument = new Document(document.getSoortDocument(), document.getPartij());
            kopieDocument.setAktenummer(document.getAktenummer());
            kopieDocument.setOmschrijving(document.getOmschrijving());
            kopieDocument.setPartij(document.getPartij());
            kopieActieBron.setDocument(kopieDocument);
        }

        final Rechtsgrond rechtsgrond = rij.getRechtsgrond();
        if (rechtsgrond != null) {
            final Rechtsgrond kopieRechtsgrond = new Rechtsgrond(rechtsgrond.getCode(), rechtsgrond.getOmschrijving());
            kopieRechtsgrond.setDatumAanvangGeldigheid(rechtsgrond.getDatumAanvangGeldigheid());
            kopieRechtsgrond.setDatumEindeGeldigheid(rechtsgrond.getDatumEindeGeldigheid());

            kopieActieBron.setRechtsgrond(kopieRechtsgrond);
        }

        kopieActieBron.setRechtsgrondOmschrijving(rij.getRechtsgrondOmschrijving());
        eigenaar.addActieBron(kopieActieBron);
        eigenaar.setAdministratieveHandeling(administratieveHandeling);
    }

    private void voegNormaleRijToeAanLijst(final Object eigenaar, final Object rij) throws ReflectiveOperationException {
        final Method[] methods = eigenaar.getClass().getDeclaredMethods();
        for (final Method method : methods) {
            if (method.getName().startsWith("add") && method.getParameterTypes()[0].isAssignableFrom(rij.getClass())) {
                method.setAccessible(true);
                method.invoke(eigenaar, rij);
                break;
            }
        }
    }

    private boolean heeftEntiteitWijzigingen(final VergelijkerResultaat vergelijkerResultaat, final Object entiteit) {
        boolean resultaat = false;
        for (final Verschil verschil : vergelijkerResultaat.getVerschillen()) {
            if (bepaalHistorieGroepKlasse((EntiteitSleutel) verschil.getSleutel()).equals(entiteit.getClass())) {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    private Class<?> bepaalHistorieGroepKlasse(final EntiteitSleutel sleutel) {
        final Class<?> historieGroepKlasse;
        if (BRPActie.class.equals(sleutel.getEntiteit())) {
            historieGroepKlasse = sleutel.getEigenaarSleutel().getEntiteit();
        } else if (ActieBron.class.equals(sleutel.getEntiteit())) {
            historieGroepKlasse = sleutel.getEigenaarSleutel().getEigenaarSleutel().getEntiteit();
        } else if (Document.class.equals(sleutel.getEntiteit())) {
            historieGroepKlasse = sleutel.getEigenaarSleutel().getEigenaarSleutel().getEigenaarSleutel().getEntiteit();
        } else {
            historieGroepKlasse = sleutel.getEntiteit();
        }
        return historieGroepKlasse;
    }

    private List<Class<?>> getSkippableEntiteiten() {
        return DeltaUtil.getSkippableEntiteiten(modus, true);
    }
}
