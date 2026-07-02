class ViewRowSelectedCountry extends JView{
    constructor(country){
        super(country).mount_()
    }

    _init(){
        A.div().a(
            A.span("iconDelete").c('delete'),
            A.span("txtCountry").t(this.country)
        )
    }

}