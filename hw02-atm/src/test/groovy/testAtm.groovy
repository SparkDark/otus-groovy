import assepter.CashAssepter
import assepter.Currency
import assepter.CustomCashAssepter
import assepter.CustomStorage
import assepter.Storage
import atm.Atm
import capture.CardCapture
import capture.CustomDeviceCardCapture
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import static org.junit.jupiter.api.Assertions.assertEquals


class Hw02 {
    private Atm atm
    private int balance
    private CashAssepter testCashAssepter
    private CardCapture testCardCapture
    private Storage testStorage
    private Currency[] testBanknote = [
            Currency.TEN,
            Currency.FIFTY,
            Currency.ONE_HUNDRED,
            Currency.FIVE,
            Currency.TEN,
            Currency.TWENTY_FIVE,
            Currency.TEN,
            Currency.TWENTY_FIVE,
            Currency.FIFTY,
            Currency.ONE_HUNDRED,
            Currency.TWENTY_FIVE,
            Currency.TEN,
            Currency.ONE_HUNDRED,
            Currency.FIFTY,
            Currency.TEN,
            Currency.FIVE,
            Currency.TEN,
            Currency.FIVE
    ]


    @Before
    void init() {

        testStorage = new CustomStorage()
        def banknotes = []
        testBanknote.each {
            banknotes.add(it)
            balance += it.faceValue()
        }


        testCashAssepter = new CustomCashAssepter(testStorage)
        testCardCapture = new CustomDeviceCardCapture()

        atm = new Atm(testCashAssepter, testCardCapture)
        atm.load(banknotes)
    }

    @DisplayName('ЗАгрузка купюр в хранилише')
    @Test
    void testLoad() {
        def banknotesToLoad = [Currency.FIVE, Currency.ONE_HUNDRED]
        atm.load(banknotesToLoad)
        assertEquals(balance + 105, atm.getBalance())
    }

    @DisplayName("Выдача банкнот - минимальная корректная сумма")
    @Test
    void cashOutMinCorrect() {
        def amount = 5;
        def return_sum = 0
        Collection<Currency> b1 = atm.cashOut(amount);

        assertEquals(1, b1.size());

        b1.each {
            return_sum += it.faceValue()
        }

        assertEquals(amount, return_sum);
    }

    @DisplayName("Выдача банкнот - максимальная корректная сумма")
    @Test
    void cashOutMaxCorrect() {
        int amount = balance
        def return_sum = 0
        Collection<Currency> b1 = atm.cashOut(amount)

        assertEquals(testBanknote.size(), b1.size())
        b1.each {
            return_sum += it.faceValue()
        }
        assertEquals(amount, return_sum)
    }

    @DisplayName("Выдача банкнот - корректная промежуточная сумма")
    @Test
    void cashCorrect() {
        int amount = 80;
        def return_sum = 0
        Collection<Currency> b2 = atm.cashOut(amount);
        b2.each {
            return_sum += it.faceValue()
        }
        assertEquals(3, b2.size());
        assertEquals(amount, return_sum);
    }

    @DisplayName("Выдача банкнот - отрицательная сумма")
    @Test(expected = java.lang.Exception)
    void cashOutNegative() {
        int amount = -1;

        atm.cashOut(amount);
    }

    @DisplayName("Выдача банкнот - некорректная сумма")
    @Test(expected = java.lang.Exception)
    void cashIncorrect() {
        int amount = 47;

        atm.cashOut(amount);
    }

    @DisplayName("Получение баланса")
    @Test
    void getBalance() {
        long balanceTotal = atm.getBalance();
        assertEquals(balance, balanceTotal);
    }
}
