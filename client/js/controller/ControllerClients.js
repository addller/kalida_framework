class ControllerClients extends JMount{
    constructor() {
        super({
            userId: API_KALIDA.getUserId(),
            userRows:[]
        }).mount_()
    }

    _init() {
        this.actionSet = new ActionSet('users')
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewClients = new ViewClients()
        this.viewInitialize.setViewContent(this.viewClients)
    }

    _requests(){

        JRequest.prepare(API_KALIDA.toUsers('all'))
            .inResponse(
                clients => {
                    clients.forEach(client => {
                        let rowFunction = (row) => {
                            selector('.delete', row).onclick = _ => this.requestDeleteUser(client, row)
                            selector('.edit', row).onclick = _ => this.requestEditUser(client)
                            selector('.details', row).onclick = _ => this.requestUserDetails(client)
                            this.userRows.push(row)
                        }
                        this.viewClients.addRowClient(client, rowFunction)
                    })
                },
                error => new Aspect(error)
            ).get()
    }

    requestDeleteUser(user, rowUser){

        if(this.userId == user.id){
            toast(LANG.cant_delete_yourself)
            return
        }

        let title = LANG.delete,
            message = LANG.confirm_delete_user;
        new Dialog(message, title, () => {

            JRequest.prepare(API_KALIDA.toUsers(user.id))
                .inResponse(
                    _ => {
                        disapend(rowUser)
                    },
                    error => new Aspect(error)
                ).delete()
        }).show();

        
    }

    requestEditUser(user){
        if(this.userId == user.id){
            toast(LANG.user_edit_profile_in_menu)
            return
        }
        API_KALIDA.setClientId(user.id)
        redirect('edit_client')
    }

    requestUserDetails(user){
        API_KALIDA.setClientId(user.id)
        redirect('client_details')
    }
}
