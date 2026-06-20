class ControllerSummary extends JMount{
    constructor() {
        super().mount_()
    }

    _init() {
        this.actionSet = new ActionSet('summary')
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewSummary = new ViewSummary()
        this.viewInitialize.setViewContent(this.viewSummary)
    }

    _events(){
        let {
            $viewSubjectClients,
            $viewSubjectTechnologies,
            $viewSubjectExperiences
        } = this.viewSummary;
        
        $viewSubjectClients.$view.onclick = _ => redirect("clients")
        $viewSubjectTechnologies.$view.onclick = _ => redirect("technologies")
        $viewSubjectExperiences.$view.onclick = _ => redirect('experiences')
    }
}
