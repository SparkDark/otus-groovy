package atm

import assepter.CashAssepter
import assepter.Currency
import capture.CardCapture

class Atm  {
    CashAssepter cashAssepter
    CardCapture  cardCapture

    Atm(CashAssepter cashAssepter,CardCapture cardCapture){
        this.cashAssepter = cashAssepter
        this.cardCapture = cardCapture
    }

    def load (Collection <Currency> banknotes){
        banknotes.each {
            cashAssepter.injectCash(it)
        }
    }

    def getBalance(){
        return cashAssepter.getBalance()
    }

    def cashOut(int amount){

        if (amount <= 0){
            throw new Exception("The amount of money can't be zero or negative.")
        }
        Collection<Currency> banknotes = cashAssepter.get(amount) as Collection<Currency>
        if (banknotes.size() == 0) {
            throw new Exception("Can't cash out amount.")
        }
        return banknotes

    }
}
