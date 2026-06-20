class ViewRowNotification extends JView{
    static startId = 0
    constructor(notification){
        super(notification).mount_()
        ViewRowNotification.startId = this.id
    }

    _init(){
        A.div("txtMessage").t(this.message)
    }

}