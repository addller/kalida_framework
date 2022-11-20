class ActionsLogin extends JMount{
    static REFRESH_TOKEN_PERIOD = 1000 * 60 * 15;
    static unlog;
    
    constructor(actionsMenu, actionsNavigation) {
        super({actionsMenu, actionsNavigation})
        const publicPages = ['index', 'sign_up'],
            nameLocation = actionsNavigation.nameLocation;

        this.loginRequired = !publicPages.includes(nameLocation)
        this.inPageRegistrarCliente = nameLocation == 'sing_up'
        this.viewLogin = new ViewLogin()
        this.mount_()
        ActionsLogin.unlog = _ => this.unlog()
        this.refreshToken()
    }

    _init() {
        let token = API_KALIDA.getToken(),
            jwt = token? new JWTSimpleParser(token) : null,
            loggedIn = jwt && !jwt.isExpired();
            
        if(loggedIn){
            if (this.inPageRegistrarCliente) return redirect('summary')
            this.actionsMenu.requestNotifications()
            this.findNotificationsByInterval = setInterval(() => this.actionsMenu.requestNotifications(), 30000);

            this.actionsMenu.requestImgPerfil()
            return this.displayItems(true);
        }
        this.unlog()
    }

    _events() {
        this.eventLogin();
        this.viewLogin.$btnSignUp.onclick = () => redirect('sign_up');
        this.viewLogin.$lblRecoverAccess.onclick = () => redirect('recover_access');
    }


    setContentViewLoginDestination(contentViewDestination){
        this.contentViewDestination = contentViewDestination
        contentViewDestination.a(this.viewLogin)
    }

    displayItems(hasLogin) {
        this.actionsNavigation.displayItems(hasLogin)
        this.actionsMenu.displayItems(hasLogin)
        this.viewLogin.$view.style.display = !hasLogin && !this.inPageRegistrarCliente ? 'block' : 'none';
    }

    eventLogin() {
        let {viewLogin} = this,
            {$formLogin} = viewLogin;

        $formLogin.onsubmit = e => {
            consume(e)
            let source = viewLogin.viewValues_(),
                waiter = new Waiter(LANG.processing_request).hideDivAnimation();
            
            JRequest.prepare(API_KALIDA.toAuth('signin'), source)
                .inSequence(
                    _ => waiter.wait(),
                    _ => waiter.free(),
                    (_, response) => ActionsLogin.saveLogin(response, $formLogin),
                    error => new Aspect(error)
                ).post()
        }
    }

    static saveLogin(response, formLogin, redirect_= true) {
        let token = response.getHeader('authorization'),
            jwt = new JWTSimpleParser(token),
            userDetails = jwt.getPayload();

        
        formLogin && API_KALIDA.removeUser()
        API_KALIDA.setUser(userDetails)
        API_KALIDA.setToken(token)

        Language.setCodLang(userDetails.lang_)

        formLogin?.reset()
        redirect_ && redirect('summary');
    }

    unlog(){
        API_KALIDA.removeUser()
        clearInterval(this.findNotificationsByInterval)
        this.loginRequired && redirect('index')
        this.displayItems()
    }

    refreshToken(){
        setInterval(() => {
            if(API_KALIDA.getToken()){
                let enderessable = API_KALIDA.toAuth("token/refresh")
                JRequest.prepare(enderessable)
                    .inResponse(
                    (_, response) => ActionsLogin.saveLogin(response, null, false),
                    _ => this.unlog(),
                ).get()
            }
        }, ActionsLogin.REFRESH_TOKEN_PERIOD)
    }
}