class ControllerClientDetails extends JMount{
    constructor() {
        super().mount_()
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
                        let viewRowExperience = new ViewRowExperience(experience),
                            viewRowTechnology = new ViewRowTechnology(experience.technology);

                        this.viewClientDetails.addViewRowExperience(viewRowExperience)
                        viewRowExperience.setViewRowTechnology(viewRowTechnology)
                    })
                    this.requestImgPerfil(idClient)
                },
                error => new Aspect(error)
            ).get()
    }

    requestImgPerfil(idClient){     
        JRequest.prepare(API_KALIDA.toImgPerfil(idClient))
            .inResponse(
                imgPerfil =>{
                    if(imgPerfil) this.viewClientDetails.$imgPerfil.src = imgPerfil
                },
                error => new Aspect(error)
            ).get()
    }
}
