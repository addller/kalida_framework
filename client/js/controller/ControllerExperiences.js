class ControllerExperiences extends JMount{
    constructor(){
        super().mount_()
    }

    _init() {
        this.actionSet = new ActionSet('experiences')
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewContentExperience = new ViewContentExperience()
        this.viewInitialize.setViewContent(this.viewContentExperience)
    }
        
    _events(){
        this.eventIncludeExperience()
    }

    _requests(){
        this.requestExperiences()
        this.requestTechnologies()
    }

    eventIncludeExperience(){
        let {$viewIncludeExperience} = this.viewContentExperience,
            {$formIncludeExperience} = $viewIncludeExperience;
        $formIncludeExperience.onsubmit = e => {
            consume(e)
            let source = $viewIncludeExperience.viewValues_(),
                enderessable = API_KALIDA.toExperiences();

            if(source.technology == 'null'){
                toast(LANG.choose_technology)
                return
            }
            source.technology = {id: source.technology};
            JRequest.prepare(enderessable, source)
                .inResponse(
                    experience => this.addViewRowExperience(experience),
                    error => new Aspect(error)
                ).post()
        }
    }

    requestTechnologies(){
        JRequest.prepare(API_KALIDA.toTecnology('all'))
            .inResponse(
                technologies => {
                    technologies.forEach(technology => this.viewContentExperience
                        .$viewIncludeExperience
                        .addOptionTechnology(technology)
                    );
                },
                error => new Aspect(error)
            ).get()
    }

    requestExperiences(){
        JRequest.prepare(API_KALIDA.toExperiences())
            .inResponse(
                experiences => {
                    this.experiences = experiences;
                    experiences.forEach(experience => this.addViewRowExperience(experience))
                },
                error => new Aspect(error)
            ).get()
    }

    addViewRowExperience(experience){
        if(!this.removeTxtHaveNotExperiences){
            disapend(this.viewContentExperience.$txtHaveNotExperiences)
            this.removeTxtHaveNotExperiences = true
        }
        let viewRowExperience = new ViewRowExperience(experience),
            viewRowTechnology = new ViewRowTechnology(experience.technology);

        viewRowExperience.setViewRowTechnology(viewRowTechnology)
        this.viewContentExperience.addViewRowExperience(viewRowExperience)

    }

}