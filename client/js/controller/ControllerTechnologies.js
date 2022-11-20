class ControllerTechnologies extends JMount{
    constructor(){
        super().mount_()
    }

    _init(){
        this.actionSet = new ActionSet('technologies')
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewTechnologies = new ViewTechnologies()
        this.viewInitialize.setViewContent(this.viewTechnologies)
    }

    _requests(){
        JRequest.prepare(API_KALIDA.toTecnology('all'))
            .inResponse(
                tecnologies => {
                    tecnologies.forEach(technology => {
                        let rowTechnology = this.viewTechnologies.addRowTechnology(technology);
                        this.configureRowTecnology(rowTechnology, technology)
                    })
                },
                error => new Aspect(error)
            ).get()
    }

    configureRowTecnology(rowTechnology, technology){
        this.deleteTecnology(rowTechnology, technology)
        this.editTechnology(rowTechnology, technology)
        this.viewTechnologies.$viewTableTechnologies.zebrar()
    }

    deleteTecnology(rowTecnology, technology){
        selector('.delete', rowTecnology).onclick = _ => {
            alert(technology.id+"delete")
            disapend(rowTecnology)
            this.viewTechnologies.$viewTableTechnologies.zebrar()
        }
    }

    editTechnology(rowTechnology, technology){
        selector('.edit', rowTechnology).onclick = _ =>{
            alert(technology.id+"edit")
        }
    }

}