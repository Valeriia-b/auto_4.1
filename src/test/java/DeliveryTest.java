import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryTest {

    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    String currentdate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Test
    void shouldSubmitIfAllCorrect(){
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Архангельск");
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL+"a"+Keys.BACK_SPACE);
        $("[placeholder='Дата встречи'").setValue(date);
        $("[name='name']").setValue("Иван Петров");
        $("[name='phone']").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(withText ("Встреча успешно забронирована")).waitUntil(Condition.visible, 15000);
        }

    @Test
    void shouldSubmitIfCityInCorrect(){
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Арх");
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL+"a"+Keys.BACK_SPACE);
        $("[placeholder='Дата встречи'").setValue(date);
        $("[name='name']").setValue("Иван Петров");
        $("[name='phone']").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] span.input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldSubmitIfDateInCorrect(){
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Архангельск");
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL+"a"+Keys.BACK_SPACE);
        $("[placeholder='Дата встречи'").setValue(currentdate);
        $("[name='name']").setValue("Иван Петров");
        $("[name='phone']").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] span.input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldSubmitIfNameInCorrect(){
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Архангельск");
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL+"a"+Keys.BACK_SPACE);
        $("[placeholder='Дата встречи'").setValue(date);
        $("[name='name']").setValue("Ivan Petrov");
        $("[name='phone']").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] span.input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSubmitIfPhoneInCorrect(){
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Архангельск");
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL+"a"+Keys.BACK_SPACE);
        $("[placeholder='Дата встречи'").setValue(date);
        $("[name='name']").setValue("Иван Петров");
        $("[name='phone']").setValue("+7999111223");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] span.input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSubmitIfCheckboxIsEmpty(){
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Архангельск");
        $("[placeholder='Дата встречи']").sendKeys(Keys.CONTROL+"a"+Keys.BACK_SPACE);
        $("[placeholder='Дата встречи'").setValue(date);
        $("[name='name']").setValue("Иван Петров");
        $("[name='phone']").setValue("+79991112233");
        $(byText("Забронировать")).click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
