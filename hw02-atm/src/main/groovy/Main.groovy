import assepter.CustomStorage
import atm.Atm
import assepter.CustomCashAssepter
import capture.CustomDeviceCardCapture

static void main(String[] args) {

 def storage = new CustomStorage()
  new Atm(new CustomCashAssepter(storage),new CustomDeviceCardCapture())
}