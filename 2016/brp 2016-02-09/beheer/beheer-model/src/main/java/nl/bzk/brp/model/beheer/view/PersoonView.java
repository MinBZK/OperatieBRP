/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;

/**
 * Persoon view.
 */
public final class PersoonView extends ObjectView<PersoonHisVolledig> {

    private final List<ObjectView<PersoonAdresHisVolledig>> adressen = new ArrayList<>();
    private final List<ObjectView<PersoonGeslachtsnaamcomponentHisVolledig>> geslachtsnaamcomponenten = new ArrayList<>();
    private final List<ObjectView<PersoonNationaliteitHisVolledig>> nationaliteiten = new ArrayList<>();
    private final List<ObjectView<PersoonReisdocumentHisVolledig>> reisdocumenten = new ArrayList<>();
    private final List<ObjectView<PersoonVerificatieHisVolledig>> verificaties = new ArrayList<>();
    private final List<ObjectView<PersoonVerstrekkingsbeperkingHisVolledig>> verstrekkingsbeperkingen = new ArrayList<>();
    private final List<ObjectView<PersoonVoornaamHisVolledig>> voornamen = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param basisObject persoon
     */
    public PersoonView(final PersoonHisVolledig basisObject) {
        super(basisObject);
    }

    /**
     * Geef de view voor het gegeven adres.
     *
     * @param adres adres
     * @return view
     */
    public ObjectView<?> getView(final PersoonAdresHisVolledig adres) {
        return ViewUtil.geefViewVoor(adressen, adres);
    }

    /**
     * Geef de view voor het gegeven geslachtsnaamcomponent.
     *
     * @param geslachtsnaamcomponent geslachtsnaamcomponent
     * @return view
     */
    public ObjectView<?> getView(final PersoonGeslachtsnaamcomponentHisVolledig geslachtsnaamcomponent) {
        return ViewUtil.geefViewVoor(geslachtsnaamcomponenten, geslachtsnaamcomponent);
    }

    /**
     * Geef de view voor het gegeven nationaliteit.
     *
     * @param nationaliteit nationaliteit
     * @return view
     */
    public ObjectView<?> getView(final PersoonNationaliteitHisVolledig nationaliteit) {
        return ViewUtil.geefViewVoor(nationaliteiten, nationaliteit);
    }

    /**
     * Geef de view voor het gegeven reisdocument.
     *
     * @param reisdocument reisdocument
     * @return view
     */
    public ObjectView<?> getView(final PersoonReisdocumentHisVolledig reisdocument) {
        return ViewUtil.geefViewVoor(reisdocumenten, reisdocument);
    }

    /**
     * Geef de view voor het gegeven verificatie.
     *
     * @param verificatie verificatie
     * @return view
     */
    public ObjectView<?> getView(final PersoonVerificatieHisVolledig verificatie) {
        return ViewUtil.geefViewVoor(verificaties, verificatie);
    }

    /**
     * Geef de view voor het gegeven verstrekkingsbeperking.
     *
     * @param verstrekkingsbeperking verstrekkingsbeperking
     * @return view
     */
    public ObjectView<?> getView(final PersoonVerstrekkingsbeperkingHisVolledig verstrekkingsbeperking) {
        return ViewUtil.geefViewVoor(verstrekkingsbeperkingen, verstrekkingsbeperking);
    }

    /**
     * Geef de view voor het gegeven voornaam.
     *
     * @param voornaam voornaam
     * @return view
     */
    public ObjectView<?> getView(final PersoonVoornaamHisVolledig voornaam) {
        return ViewUtil.geefViewVoor(voornamen, voornaam);
    }

    /**
     * Geef alle adres views.
     *
     * @return adres views
     */
    public List<ObjectView<PersoonAdresHisVolledig>> getAdressen() {
        return adressen;
    }

    /**
     * Geef alle geslachtsnaamcomponent views.
     *
     * @return geslachtsnaamcomponent views
     */
    public List<ObjectView<PersoonGeslachtsnaamcomponentHisVolledig>> getGeslachtsnaamcomponenten() {
        return geslachtsnaamcomponenten;
    }

    /**
     * Geef alle nationaliteit views.
     *
     * @return nationaliteit views
     */
    public List<ObjectView<PersoonNationaliteitHisVolledig>> getNationaliteiten() {
        return nationaliteiten;
    }

    /**
     * Geef alle reisdocument views.
     *
     * @return reisdocument views
     */
    public List<ObjectView<PersoonReisdocumentHisVolledig>> getReisdocumenten() {
        return reisdocumenten;
    }

    /**
     * Geef alle verificatie views.
     *
     * @return verificatie views
     */
    public List<ObjectView<PersoonVerificatieHisVolledig>> getVerificaties() {
        return verificaties;
    }

    /**
     * Geef alle verstrekkingsbeperking views.
     *
     * @return verstrekkingsbeperking views
     */
    public List<ObjectView<PersoonVerstrekkingsbeperkingHisVolledig>> getVerstrekkingsbeperkingen() {
        return verstrekkingsbeperkingen;
    }

    /**
     * Geef alle voornaam views.
     *
     * @return voornaam views
     */
    public List<ObjectView<PersoonVoornaamHisVolledig>> getVoornamen() {
        return voornamen;
    }

}
