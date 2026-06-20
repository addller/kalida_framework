class ViewRowTechnology extends JView{
    constructor(technology){
        super(technology).mount_()
    }

    _init(){
        A.div("view").a(
            A.div('groupTechnology').a(
                A.span('txtTechnology').t(LANG.technology+":"),
                A.span('spanTechnology').t(this.name)
            ),
            A.div('groupTypeTecnology').a(
                A.span('txtTypeTechnology').t(LANG.type+":"),
                A.span('spanTechonology').t(this.typeTechnology.toLowerCase().replace('_',' '))
            ),
            A.div('groupDescription').a(
                A.span('txtDescription').t(LANG.description+":"),
                A.span('spanDescription').t(this.descritpion)
            )
        )
    }

}