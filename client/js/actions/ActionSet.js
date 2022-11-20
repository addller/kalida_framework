class ActionSet{
    
    static MENU = 'menu'
    static NAVIGATION = 'navigation'
    static CONTENT = 'content'
    static FOOTER = 'footer'
    static unlog;

    constructor(location){
        this.viewInitialize = new ViewInitialize()
        this.actionsMenu = new ActionsMenu(this.viewInitialize)
        this.actionsSearch = new ActionsSearch(this.actionsMenu)
        this.actionsNavigation = new ActionsNavigation(location)
        this.actionsLogin = new ActionsLogin(this.actionsMenu, this.actionsNavigation)
        this._init()
    }

    _init(){
        this.setViewMenu(this.actionsMenu.viewMenu)
        this.setViewNavigation(this.actionsNavigation.viewNavigation)
        this.actionsLogin.setContentViewLoginDestination(this.viewInitialize.$navigation)
        this.actionsMenu.viewMenuItemUser.$spanSignOut.onclick = _ => this.actionsLogin.unlog()
        API_KALIDA.unlog = _ => this.actionsLogin.unlog()
    }

    getViewMenu = _ => this.viewInitialize.getViewMenu()

    setViewMenu = viewMenu => this.viewInitialize.setViewMenu(viewMenu)

    getViewNavigation = _ => this.viewInitialize.getViewNavigation()

    setViewNavigation = viewNavigation => this.viewInitialize.setViewNavigation(viewNavigation)

    getViewContent = _ => this.viewInitialize.getViewContent()

    setViewContent = viewContent => this.viewInitialize.setViewContent(viewContent)

    getViewFooter = _ => this.viewInitialize.getViewFooter()

    setViewFooter = viewFooter => this.viewInitialize.setViewFooter(viewFooter)

    getViewLogin = _ => this.actionsLogin.viewLogin

}