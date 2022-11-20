class ActionsMenu extends JMount{
    constructor(isSimpleMenu) {
        super({isSimpleMenu}).mount_()
    }

    _init() {
        this.viewMenu = new ViewMenu()
        this.viewMenuItemUser = this.viewMenu.$viewMenuItemUser
    }

    setImgPerfil(imgPerfil){
        this.viewMenuItemUser.setImgPerfil(imgPerfil)
    }
    
    _events() {
        this.exibicaoMenuItemUser()
        this.eventChangeLang()
        this.viewMenu.$spanLogo.onclick = _ => !isSamePage('index.html') && redirect('index')
        this.viewMenuItemUser.$spanEditarPerfil.onclick = _ => redirect('edit_profile')
    }

    displayItems(hasLogin){
        hasLogin? show(this.viewMenuItemUser, 'inline-block') : hide(this.viewMenuItemUser)
    }

    exibicaoMenuItemUser() {
        let {$divMenu, $imgUser, $iconNotificacao, $divNotifications} = this.viewMenuItemUser;
        $imgUser.onclick = () => {    
            $divNotifications.display = false
            hide($divNotifications)        
            $divMenu.display = !$divMenu.display;
            $divMenu.display? show($divMenu, 'block') : hide($divMenu)
        };

        $iconNotificacao.onclick = _ => {
            this.viewMenuItemUser.changeNumNotifications(0)
            $divMenu.display = false
            hide($divMenu)
            $divNotifications.display = !$divNotifications.display;
            $divNotifications.display? show($divNotifications, 'block') : hide($divNotifications)
        }
    }

    requestNotifications(){
        let fragment = {
            startId:ViewRowNotification.startId
        };
        JRequest.prepare(API_KALIDA.toNotification(fragment))
            .setOnConnectionError(null)
            .inResponse(
            notifications => {
                notifications.forEach(notification => {
                    let viewRowNotification = new ViewRowNotification(notification);
                    this.configureViewRowNotification(viewRowNotification)
                })
                this.viewMenuItemUser.changeNumNotifications()
                this.viewMenuItemUser.reverseInsertionNotification = true

            },
            error => new Aspect(error),
        ).get()
    }

    requestImgPerfil(){

        let userId = API_KALIDA.getUserId();

        JRequest.prepare(API_KALIDA.toImgPerfil(userId))
        .inResponse(
            imgPerfil => {
                API_KALIDA.setImagePerfil(imgPerfil)
                this.setImgPerfil(imgPerfil)
            },
            error => new Aspect(error)
        ).get()
    }

    configureViewRowNotification(viewRowNotification){
        this.viewMenuItemUser.addViewRowNotification(viewRowNotification)
        this.requestSetNotificationReaded(viewRowNotification)
        
    }

    requestSetNotificationReaded(viewRowNotification){
        viewRowNotification.$view.onclick = _ => {
            if(!viewRowNotification.lida){
                JRequest.prepare(API_KALIDA.toNotification(), viewRowNotification.id, 'text/plain').
                    inResponse(
                    _ => {
                        viewRowNotification.lida = true
                        this.viewMenuItemUser.numNotifications--
                        viewRowNotification.insertClasses_(viewRowNotification.$view, 'readed')
                        this.viewMenuItemUser.changeNumNotifications()
                    },
                    error => new Aspect(error),
                ).put()
            }
            this.redirectByNotification(viewRowNotification)
        }
    }

    redirectByNotification(viewRowNotification){
        if(viewRowNotification.destination) redirect(viewRowNotification.destination)
    }

    eventChangeLang(){
        this.viewMenu.$inputLang.onchange = _ => {
            Language.setCodLang(this.viewMenu.$inputLang.value)
            window.reload()
        }
    }
}
