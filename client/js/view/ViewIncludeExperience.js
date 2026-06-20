class ViewIncludeExperience extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.form("formIncludeExperience").a(
            A.p('txtIncludeExperience').t(LANG.include_experience.toUpperCase()),
            A.div('groupStartYear').a(
                A.label('lblStartYear').t(LANG.start_year),
                A.inputNumber('inputStartYear').r()
            ),
            
            A.inputSelect('inputTechnology').a(
                A.inputOption('optSelectTechnology').otv(LANG.select_technology)
            ),
            A.inputSubmit('btnIncludeExperience').v(LANG.add)
        )
    }

    addOptionTechnology(technology){

        this.$inputTechnology.a(
            A.inputOption('opt'+technology.name).otv(technology.name, technology.id)
        )
    }

    _limits(){
        this.$inputStartYear.min = 1950
    }

}