package com.checktipsplitter.model;

import android.content.Context;

import com.checktipsplitter.utils.PrefUtils;
import com.checktipsplitter.wizard.model.AbstractWizardModel;
import com.checktipsplitter.wizard.model.FreeTextPage;
import com.checktipsplitter.wizard.model.PageList;
import com.checktipsplitter.wizard.model.SingleFixedChoicePage;

public class WizardModel extends AbstractWizardModel {
    public static final String YOUR_NAME_PAGE_KEY = "your_name_key";
    public static final String HOW_MANY_PAGE_KEY = "how_many_page_key";
    public static final String BILL_AMOUNT_KEY = "bill_amount_key";
    public static final String GRATIFICATION_AMOUNT_KEY = "gratification_amount_key";
    public static final String ACADEMIC_LEVEL_KEY = "academic_level_page_key";

    public WizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {

        String[] currencyOptions = PrefUtils.getCurrencyValuesDescription(mContext);

        return new PageList(
                new SingleFixedChoicePage(this, "Moneda base").setChoices(currencyOptions).setRequired(true).setKey(ACADEMIC_LEVEL_KEY),
                new FreeTextPage(this, "¿Cómo te llamas?", "your_name", "Tu nombre", "Nombre").setRequired(true).setKey(YOUR_NAME_PAGE_KEY),
                new FreeTextPage(this, "¿Total de la cuenta?", "bill_amount", "Total de la cuenta", "Cuenta total").setRequired(true).setKey(BILL_AMOUNT_KEY),
                new FreeTextPage(this, "¿Tu y cuántos más?", "how_many", "No. Personas", "Número de personas").setRequired(true).setKey(HOW_MANY_PAGE_KEY),
                new FreeTextPage(this, "Porcentaje de propina", "gratification__amount", "% Propina", "Porcentaje").setRequired(true).setKey(GRATIFICATION_AMOUNT_KEY)
        );
    }
}