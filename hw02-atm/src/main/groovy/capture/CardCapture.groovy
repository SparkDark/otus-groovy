package capture

interface CardCapture {
    def injectCard()
    def rollbackCard()

}


class CustomDeviceCardCapture implements CardCapture {
    def injectCard(){
        return new MasterCard('1234567890','2100-02-01',"Service")
    }

    def rollbackCard(){
        println("return Card")
    }
}