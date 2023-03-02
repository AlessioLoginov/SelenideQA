package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDelivery {
    private String getNewDate(int addDays, String pattern) { //функция генерации даты по паттерну
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldCompleted () {
        open("http://localhost:9999");
        String needDate = getNewDate(5,"dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(needDate);
        $("[data-test-id='name'] input").setValue("Алексей Николаевич Логинов");
        $("[data-test-id='phone'] input").setValue("+79213338877");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + needDate));
    }

    @Test
    public void shouldAllCompleted () {

        open("http://localhost:9999");
        String myCity = "Кострома";
        $("[data-test-id='city'] input").setValue(myCity.substring(0, 2));
        $$(".menu-item__control").findBy(text(myCity)).click();
        $("[data-test-id='date'] input").click();
        if (!getNewDate(0, "MM").equals(getNewDate(7, "MM"))) {
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(text(getNewDate(7, "d"))).click();
        $("[data-test-id='name'] input").setValue("Алексей Николаевич Логинов");
        $("[data-test-id='phone'] input").setValue("+79213338877");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + getNewDate(7, "dd.MM.yyyy")));


    }



}
