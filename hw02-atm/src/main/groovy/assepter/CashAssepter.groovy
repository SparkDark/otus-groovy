package assepter


import assepter.Storage
interface CashAssepter {
    Storage storage
    String сurrency
    boolean status

    void injectStorage(final Storage storage)
    void collectionCash()
    void injectCash(Currency banknote)
    def getBalance()
    def get(final int amount)
}


class CustomCashAssepter implements CashAssepter{
    Storage storage
    String сurrency
    boolean status

    CustomCashAssepter(Storage storage){
        println('Обнаружен Денежный накопитель ')
        this.storage = storage
        println('инициализация Хранилища')
        status = true
    }


    void collectionCash(){
        println('Инкасакция')
        status =false
    }

    void injectStorage(final Storage storage){
        this.storage = storage
        println('Установлено новое хранилище денег')
        status=true
    }

    void injectCash(Currency banknote){
        storage.put(banknote)
    }

    def getBalance(){
        return storage.getBalance()
    }


    def get(final int amount){
        return storage.get(amount)
    }
}

