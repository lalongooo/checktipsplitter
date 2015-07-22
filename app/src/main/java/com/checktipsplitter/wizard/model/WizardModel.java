package com.checktipsplitter.wizard.model;

import android.content.Context;
import android.text.InputType;

import com.checktipsplitter.utils.PrefUtils;

public class WizardModel extends AbstractWizardModel {
    public static final String BASE_EXCHANGE = "base_exchange";
    public static final String TARGET_EXCHANGE = "target_exchange";
    public static final String YOUR_NAME_PAGE_KEY = "your_name_key";
    public static final String BILL_AMOUNT_KEY = "bill_amount_key";
    public static final String HOW_MANY_PAGE_KEY = "how_many_page_key";
    public static final String GRATIFICATION_AMOUNT_KEY = "gratification_amount_key";

    public WizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {

        String[] currencyOptions = PrefUtils.getCurrencyValuesDescription(mContext);

        return new PageList(
                new SingleFixedChoicePage(this, "Moneda base").setChoices(currencyOptions).setRequired(true).setKey(BASE_EXCHANGE),
                new SingleFixedChoicePage(this, "Moneda destino").setChoices(currencyOptions).setRequired(true).setKey(TARGET_EXCHANGE),
                new FreeTextPage(this, "¿Cómo te llamas?", "your_name", "Tu nombre", "Nombre", InputType.TYPE_TEXT_FLAG_CAP_WORDS).setRequired(true).setKey(YOUR_NAME_PAGE_KEY),
                new FreeTextPage(this, "¿Total de la cuenta?", "bill_amount", "Total de la cuenta", "Cuenta total", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED).setRequired(true).setKey(BILL_AMOUNT_KEY),
                new FreeTextPage(this, "¿Tu y cuántos más?", "how_many", "No. Personas", "Número de personas", InputType.TYPE_CLASS_NUMBER).setRequired(true).setKey(HOW_MANY_PAGE_KEY),
                new FreeTextPage(this, "Porcentaje de propina", "gratification__amount", "% Propina", "Porcentaje", InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED).setRequired(true).setKey(GRATIFICATION_AMOUNT_KEY)
        );
    }
}