package assepter

import java.lang.invoke.LambdaMetafactory
import java.util.concurrent.atomic.AtomicInteger

interface Storage {
    /** Поместить банкноту в хранилище. */
    void put(Currency currency)

    /** Выдача банкнот, если их можно выдать. */
    Collection<Currency> get(int amount)

    /** Баланс в хранилище. */
    int getBalance()


}

/** Ячейка с банкнотами одного номинала. */
interface CurrencyCell {

    /** Номинал ячейки. */
    int faceValue();

    /** Добавление банкноты в ячейку. */
    void add(Currency banknote);

    /** Извлечение банкнот из ячейки. */
    Collection<Currency> extract(int count);

    /** Подсчет количества банкнот в ячейке. */
    int count();
}

/** Доступные банкноты.*/
enum Currency {
    FIVE(5),
    TEN(10),
    TWENTY_FIVE(25),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000);

    private final int faceValue;

    Currency(int faceValue) {
        this.faceValue = faceValue;
    }

    def faceValue() {
        return faceValue;
    }
}

class CustomCurrencyCell implements CurrencyCell {
    // банкноты, для которых предназначена ячейка
    private Currency сurrency;
    // банкноты, хранящиеся в ячейке
    private List<Currency> banknotes = new ArrayList<>();

    CustomCurrencyCell(Currency сurrency) {
        this.сurrency = сurrency

    }

    @Override
    int faceValue() {
        return сurrency.faceValue();
    }

    @Override
    void add(Currency сurrency) {
        if (this.сurrency.faceValue() != сurrency.faceValue()) {
            throw new Exception("Not supported face value");
        }
        banknotes.add(сurrency);
    }

    @Override
    Collection<Currency> extract(int count) {
        if (count <= 0 || count > banknotes.size()) throw new Exception("Incorrect count of banknotes : ${count} , ${banknotes.size()}");
        Collection<Currency> extracted = new ArrayList<>();
        while (count > 0) {
            extracted.add(banknotes.remove(banknotes.size() - 1));
            count--;
        }
        return extracted;
    }

    @Override
    int count() {
        return banknotes.size();
    }
}


class CustomStorage implements Storage {
    // для простоты считаем, что на каждый номинал может быть только одна ячейка
    private SortedMap<Integer, CurrencyCell> store = new TreeMap<>(Comparator.comparing(Integer::intValue,
            Comparator.reverseOrder()));

    CustomStorage() {
        // сразу создаем ячейки под все доступные номиналы
        for (Currency banknote : Currency.values()) {
            store.put(banknote.faceValue(), new CustomCurrencyCell(banknote));
        }
    }

    @Override
    void put(Currency banknote) {
        store.get(banknote.faceValue()).add(banknote);
    }

    @Override
    Collection<Currency> get(final int amount) {
        Collection<Currency> banknotes = new ArrayList<>();
        def cashCounter = amount
        if (canGet(amount)) {
            store.values().each {
                for (int i = it.count(); cashCounter >= 0 && i > 0; i--) {

                    if (cashCounter - i * it.faceValue() >= 0 && i > 0 && it.count() > 0) {

                        banknotes.addAll(it.extract(i));
                        cashCounter -= i * it.faceValue()

                    }

                }
                if (cashCounter == 0) return
            }
        }
        return banknotes;
    }

    @Override
    int getBalance() {
        AtomicInteger balance = new AtomicInteger();
        store.forEach(((faceValue, banknoteCell) -> balance.addAndGet(banknoteCell.count() * banknoteCell.faceValue())));
        return balance.get();
    }

    /** Проверка возможности выдачи запрашиваемой сумммы.*/
    private def canGet(final int amount) {
        def cashCounter = amount
        // используем жадный алгоритм, идем в порядке убывания номинала
        store.values().each {
            for (int i = it.count(); cashCounter >= 0 && i > 0; i--) {

                if (cashCounter - i * it.faceValue() >= 0 && i > 0 && it.count() > 0) {
                    cashCounter -= i * it.faceValue()
                }

            }

        }

        if (cashCounter == 0) {
            return true
        }

        return false
    }
}