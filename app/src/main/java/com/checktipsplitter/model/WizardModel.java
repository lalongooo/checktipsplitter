package com.checktipsplitter.model;

import android.content.Context;

import com.checktipsplitter.wizard.model.AbstractWizardModel;
import com.checktipsplitter.wizard.model.FreeTextPage;
import com.checktipsplitter.wizard.model.PageList;
import com.checktipsplitter.wizard.model.SingleFixedChoicePage;
import com.checktipsplitter.wizard.model.WelcomePage;

public class WizardModel extends AbstractWizardModel {
    public static final String WELCOME_PAGE__KEY = "welcome_page_key";
    public static final String YOUR_NAME_PAGE_KEY = "your_name_key";
    public static final String HOW_MANY_PAGE_KEY = "how_many_page_key";
    public static final String BILL_AMOUNT_KEY = "bill_amount_key";
    public static final String GRATIFICATION_AMOUNT_KEY = "gratification_amount_key";

    public static final String POST_TEXT_KEY = "post_text_page_key";
    public static final String CONTACT_INFO_KEY = "contact_info_page_key";
    public static final String CITY_FROM_KEY = "city_from_page_key";
    public static final String CITY_TO_KEY = "city_to_page_key";
    public static final String POSITION_TYPE_KEY = "position_type_page_key";
    public static final String WORKDAY_TYPE_KEY = "workday_type_page_key";
    public static final String TEACHING_CAREER_KEY = "teaching_career_page_key";
    public static final String ACADEMIC_LEVEL_KEY = "academic_level_page_key";
    public WizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new WelcomePage(this, "").setKey(WELCOME_PAGE__KEY),
                new FreeTextPage(this, "¿Cómo te llamas?", "your_name", "Tu nombre", "Nombre").setRequired(true).setKey(YOUR_NAME_PAGE_KEY),
                new FreeTextPage(this, "¿Tu y cuántos más?", "how_many", "Personas", "Número de personas").setRequired(true).setKey(HOW_MANY_PAGE_KEY),
                new FreeTextPage(this, "¿Total de la cuenta?", "bill_amount", "Total de la cuenta", "Cuenta total").setRequired(true).setKey(BILL_AMOUNT_KEY),
                new FreeTextPage(this, "Porcentaje de propina", "gratification__amount", "Porcentaje", "% Propina").setRequired(true).setKey(GRATIFICATION_AMOUNT_KEY),

                new SingleFixedChoicePage(this, "Nivel académico").setChoices("Educación Especial", "Pre-escolar", "Primaria", "Secundaria", "Tele-secundaria", "Medio superior", "Administrativo", "Intendencia").setRequired(true).setKey(ACADEMIC_LEVEL_KEY)
        );
    }
}