class ControllerClients extends JMount{
    constructor() {
        super({userRows:[]}).mount_()
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
                    jsonInMatrix(clients).forEach(client => {
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

        let title = LANG.delete,
            message = LANG.confirm_delete_user;
        new Dialog(message, title, () => {

            JRequest.prepare(API_KALIDA.toUsers(user.id))
                .inResponse(
                    _ => {
                        disapend(rowUser)
                        this.viewClients.$viewTableUsers.zebrar()
                    },
                    error => new Aspect(error)
                ).delete()
        }).show();

        
    }

    requestEditUser(user){
        API_KALIDA.setClientId(user.id)
        redirect('edit_client')
    }

    requestUserDetails(user){
        API_KALIDA.setClientId(user.id)
        redirect('client_details')
    }
}
