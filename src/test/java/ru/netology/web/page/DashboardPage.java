package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final SelenideElement reloadButton = $("[data-test-id=action-reload]");

    public DashboardPage() {

        heading.shouldBe(visible);

    }

    public int getCardBalance(String maskedCardNumber) {

        var text = cards.findBy(Condition.text(maskedCardNumber)).getText();
        return extractBalance(text);

    }

    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {

        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestID())).$("button").click();
        return new TransferPage();

    }

    public void reloadDashboardPage() {

        reloadButton.click();
        heading.shouldBe(visible);

    }

    private int extractBalance(String text) {

        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);

    }

}