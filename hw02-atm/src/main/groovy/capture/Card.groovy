package capture

interface Card {
   String getBean()
   String getExpireDate()
   String getUserName()
}

class MasterCard implements Card{
    def bean
    def expireDate
    def userName
    @Override
    String getBean(){
        return bean
    }

    @Override
    String getExpireDate(){
        return expireDate
    }

    @Override
    String getUserName(){
        return userName
    }

    MasterCard(bean,expireDate,userName){
        this.bean = bean
        this.expireDate = map?.expireDate
        this.userName = map?.userName
    }

}