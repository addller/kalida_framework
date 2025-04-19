class ControllerEditClient extends JMount{
    constructor() {
        super().mount_()
    }

    _init() {
        this.actionSet = new ActionSet('edit_user')
        this.viewInitialize = this.actionSet.viewInitialize
    }

    _requests(){
        let clientId = API_KALIDA.getClientId();
        JRequest.prepare(API_KALIDA.toUsers(clientId))
            .inResponse(
                user => {
                    let viewEditUser = new ViewEditClient(user)
                    this.viewInitialize.setViewContent(viewEditUser)
                    this.configureViewEditUser(viewEditUser)
                },
                error => new Aspect(error)
            ).get()
    }

    configureViewEditUser(viewEditUser){
        this.requestEditUser(viewEditUser)
        this.eventBackToUsers(viewEditUser)
    }

    requestEditUser(viewEditUser){
        viewEditUser.$formEditUser.onsubmit = e => {
            consume(e)
            let source = viewEditUser.viewValues_(true),
                enderessable = API_KALIDA.toUsers("/other_profile");
            
            if(viewEditUser.isUpdated_()){
                toast(LANG.nothing_changed)
                return
            }
            
            JRequest.prepare(enderessable, source)
                .inResponse(
                    (_, response) => {
                        viewEditUser.updateView_(source)

                        let title = LANG.information,
                            message = LANG.user_saved;

                        new Dialog(message, title, () => {
                            redirect('clients')
                        }).hideBtnNegate(LANG.close).show();
                    },
                    error => new Aspect(error),
                ).put()
        }
    }

    eventBackToUsers(viewEditUser){
        viewEditUser.$btnBack.onclick = _ => redirect("clients")
    }
}
