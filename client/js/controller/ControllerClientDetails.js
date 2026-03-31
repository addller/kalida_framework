class ControllerClientDetails extends JMount{
    constructor() {
        super({
            userId: API_KALIDA.getUserId(),
            clientId: API_KALIDA.getClientId()
        }).mount_()
    }

    _init() {
        this.actionSet = new ActionSet('user')
        this.viewInitialize = this.actionSet.viewInitialize
    }

    _requests(){
        let idClient = API_KALIDA.getClientId();
        this.requestUserDetails(idClient)
    }

    requestUserDetails(idClient){
        JRequest.prepare(API_KALIDA.toUsers(idClient))
            .inResponse(
                user => {
                    this.viewClientDetails = new ViewClientDetails(user)
                    this.viewInitialize.setViewContent(this.viewClientDetails)
                    user.experiences.forEach(experience => {
                        
                        let spanDelete = E.span().c('sprite', 'delete'),
                            viewRowExperience = new ViewRowExperience(experience),
                            viewRowTechnology = new ViewRowTechnology(experience.technology);

                        this.viewClientDetails.addViewRowExperience(viewRowExperience)
                        viewRowExperience.setViewRowTechnology(viewRowTechnology)
                        viewRowExperience.$view.a(
                            E.br(),
                            spanDelete
                        )
                        this.requestDeleteExperience(spanDelete, viewRowExperience)
                    })
                    this.requestImgPerfil(idClient)
                },
                error => new Aspect(error)
            ).get()
    }

    requestDeleteExperience(deleteElement, viewRowExperience){
        if(this.userId != this.clientId){
            disapend(deleteElement)
            return
        }

        deleteElement.onclick = _ => {

            JRequest.prepare(API_KALIDA.toExperiences(viewRowExperience.id))
                .inResponse(
                    _ => disapend(viewRowExperience),
                    error => new Aspect(error)
                ).delete()
        }
    }

    requestImgPerfil(idClient){    
        //not implemented in backend, but if it was, it would be like this:
        return 
        JRequest.prepare(API_KALIDA.toImgPerfil(idClient))
            .inResponse(
                imgPerfil =>{
                    if(imgPerfil) this.viewClientDetails.$imgPerfil.src = imgPerfil
                },
                error => new Aspect(error)
            ).get()
    }
}
