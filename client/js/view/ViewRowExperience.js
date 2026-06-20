class ViewRowExperience extends JView{
    constructor(experience){
        super(experience).mount_()
    }

    _init(){
        A.div('view').a(
            A.div('divTechnology'),
            A.div('groupStartYear').a(
                A.span('txtStartYear').t(LANG.start_year),
                A.span('spanStartYear').t(this.startYear)
            )
        )
    }

    setViewRowTechnology(viewRowTechnology){
        removeChildren(this.$divTechnology)
        this.$divTechnology.a(viewRowTechnology)
    }

}