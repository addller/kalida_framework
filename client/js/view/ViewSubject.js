class ViewSubject extends JView{
    constructor(subject){
        super()
        this.subject = subject;
        this.mount_()
    }

    _init(){
        A.p("txtSubject").t(this.subject)
    }

}